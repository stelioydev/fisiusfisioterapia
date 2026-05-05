// ==========================================================
// FISIUS ADMIN - JS PRINCIPAL
// Versão premium local: sem JWT obrigatório para facilitar testes.
// ==========================================================
const API = 'http://localhost:8080/api';
let cache = { pacientes: [], agendamentos: [], financeiro: [], prontuarios: [], blog: [] };

function getToken(){ return localStorage.getItem('fisiusToken'); }
function headers(){ return {'Content-Type':'application/json', ...(getToken()?{'Authorization':'Bearer '+getToken()}:{})}; }
async function api(path, opts={}){
  const response = await fetch(API + path, {...opts, headers:{...headers(), ...(opts.headers||{})}});
  if(!response.ok) throw new Error(await response.text() || 'Erro na API');
  return response.status === 204 ? null : response.json();
}
function money(v){return Number(v||0).toLocaleString('pt-BR',{style:'currency',currency:'BRL'});}
function statusIcon(status){return {AGENDADO:'📅',PRESENTE:'✅',FALTOU:'❌',DESMARCOU:'↩️'}[status] || '📌';}
function escapeHtml(v){return String(v??'').replace(/[&<>"]/g, s=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[s]));}

// LOGIN: continua existindo para a experiência do administrador.
const login = document.getElementById('loginForm');
if(login){
  login.addEventListener('submit', e=>{
    e.preventDefault();

    // 🔥 LOGIN LIBERADO (SEM BACKEND)
    localStorage.setItem('fisiusToken', 'liberado');
    localStorage.setItem('fisiusAdminNome', 'Administradora Fisius');

    location.href='dashboard.html';
  });
}

document.getElementById('logout')?.addEventListener('click',()=>{
  localStorage.removeItem('fisiusToken');
  location.href='login.html';
});

// Carrega todos os dados do painel.
async function load(){
  if(!location.pathname.includes('dashboard')) return;
  document.getElementById('adminNome').textContent = localStorage.getItem('fisiusAdminNome') || 'Administradora';
  try{
    const [pac, ags, fin, pro, blog] = await Promise.all([
      api('/pacientes'), api('/agendamentos'), api('/financeiro'), api('/prontuarios'), api('/blog')
    ]);
    cache = {pacientes:pac, agendamentos:ags, financeiro:fin, prontuarios:pro, blog};
    renderPacientes(pac); renderAgenda(ags); renderFinanceiro(fin); renderProntuarios(pro); renderBlog(blog); renderMetricas();
  }catch(e){
    console.error(e);
    if(!document.querySelector('.api-alert')) document.body.insertAdjacentHTML('afterbegin','<div class="api-alert">Backend não conectado. Rode o Spring Boot em http://localhost:8080.</div>');
  }
}

function renderMetricas(){
  mAtend.textContent = cache.agendamentos.length;
  mRecebido.textContent = money(cache.financeiro.filter(f=>f.status==='PAGO'&&f.tipo==='ENTRADA').reduce((s,f)=>s+Number(f.valor||0),0));
  mPendente.textContent = money(cache.financeiro.filter(f=>f.status==='PENDENTE').reduce((s,f)=>s+Number(f.valor||0),0));
  const counts = {};
  cache.agendamentos.forEach(a => counts[a.tipo]=(counts[a.tipo]||0)+1);
  mServico.textContent = Object.entries(counts).sort((a,b)=>b[1]-a[1])[0]?.[0] || 'Pilates';
}
function renderPacientes(pac){
  pacienteLista.innerHTML = pac.map(p=>`<div class="row"><b>#${p.id} ${escapeHtml(p.nome)}</b><span>${escapeHtml(p.telefone)}</span><span>${escapeHtml(p.email)}</span><span>${p.dataNascimento||''}</span><span>${escapeHtml(p.convenio)}</span><span>${escapeHtml(p.observacoes)}</span><div class="actions"><button class="icon-btn" onclick="editPaciente(${p.id})">✏️</button><button class="icon-btn danger" onclick="del('/pacientes/${p.id}')">🗑️</button></div></div>`).join('') || '<p>Nenhum paciente cadastrado.</p>';
}
function renderAgenda(ags){
  agendaLista.innerHTML = ags.map(a=>`<div class="row"><b>${escapeHtml(a.pacienteNome || a.paciente?.nome || 'Bloqueio')}</b><span>${a.data||''}</span><span>${a.hora||''}</span><span class="badge ${a.tipo}">${a.tipo}</span><span class="badge status ${a.status}">${statusIcon(a.status)} ${a.status}</span><select onchange="updateStatus(${a.id},this.value)"><option ${a.status==='AGENDADO'?'selected':''}>AGENDADO</option><option ${a.status==='PRESENTE'?'selected':''}>PRESENTE</option><option ${a.status==='FALTOU'?'selected':''}>FALTOU</option><option ${a.status==='DESMARCOU'?'selected':''}>DESMARCOU</option></select><div class="actions"><button class="icon-btn" onclick="editAgenda(${a.id})">✏️</button><button class="icon-btn danger" onclick="del('/agendamentos/${a.id}')">🗑️</button></div></div>`).join('') || '<p>Nenhum agendamento cadastrado.</p>';
}
function renderFinanceiro(fin){
  financeiroLista.innerHTML = fin.map(f=>`<div class="row"><b>${escapeHtml(f.descricao)}</b><span>${money(f.valor)}</span><span>${f.tipo}</span><span class="badge status ${f.status}">${f.status}</span><span>${f.data||''}</span><span></span><div class="actions"><button class="icon-btn danger" onclick="del('/financeiro/${f.id}')">🗑️</button></div></div>`).join('') || '<p>Nenhum lançamento financeiro.</p>';
}
function renderProntuarios(pro){
  prontuarioLista.innerHTML = pro.map(p=>`<div class="row"><b>#${p.id} ${escapeHtml(p.paciente?.nome || 'Paciente não vinculado')}</b><span>${p.criadoEm ? p.criadoEm.replace('T',' ').slice(0,16):''}</span><span>${escapeHtml(p.anexoUrl)}</span><span>${escapeHtml(p.evolucao)}</span><span></span><span></span><div class="actions"><button class="icon-btn danger" onclick="del('/prontuarios/${p.id}')">🗑️</button></div></div>`).join('') || '<p>Nenhum prontuário cadastrado.</p>';
}
function renderBlog(posts){
  blogLista.innerHTML = posts.map(p=>`<div class="post-card">${p.imagemUrl?`<img src="${escapeHtml(p.imagemUrl)}" alt="">`:''}<small>${escapeHtml(p.categoria||'Blog')}</small><h3>${escapeHtml(p.titulo)}</h3><p>${escapeHtml((p.conteudo||'').slice(0,120))}</p><div class="actions"><button class="icon-btn" onclick="editBlog(${p.id})">✏️</button><button class="icon-btn danger" onclick="del('/blog/${p.id}')">🗑️</button></div></div>`).join('') || '<p>Nenhum post cadastrado.</p>';
}

window.updateStatus = async(id,status)=>{ await api('/agendamentos/'+id+'/status',{method:'PATCH', body:JSON.stringify({status})}); load(); };
async function del(path){ if(confirm('Deseja excluir este registro?')){ await api(path,{method:'DELETE'}); load(); } }
window.del = del;

function fillForm(formId, data){
  const form = document.getElementById(formId);
  Object.entries(data).forEach(([k,v])=>{ if(form.elements[k]) form.elements[k].value = v ?? ''; });
}
window.editPaciente = id => { const p=cache.pacientes.find(x=>x.id===id); fillForm('pacienteForm',p); pacienteSubmit.textContent='Salvar alterações'; location.hash='pacientes'; };
window.editAgenda = id => { const a=cache.agendamentos.find(x=>x.id===id); fillForm('agendaForm',a); agendaSubmit.textContent='Salvar alterações'; location.hash='agenda'; };
window.editBlog = id => { const p=cache.blog.find(x=>x.id===id); fillForm('blogForm',p); blogSubmit.textContent='Salvar alterações'; location.hash='blog'; };
window.cancelEdit = (formId, buttonId, label)=>{ document.getElementById(formId).reset(); document.getElementById(buttonId).textContent=label; };
window.gerarRelatorioFinanceiro = ()=> window.print();

async function bindForm(id,path,submitLabel){
  const f=document.getElementById(id); if(!f) return;
  f.addEventListener('submit', async e=>{
    e.preventDefault();
    const obj = Object.fromEntries(new FormData(f).entries());
    const editing = obj.id; if(!obj.id) delete obj.id;
    try{
      await api(editing ? path+'/'+editing : path, {method: editing ? 'PUT' : 'POST', body:JSON.stringify(obj)});
      f.reset(); if(submitLabel) document.getElementById(submitLabel.id).textContent=submitLabel.text; load(); alert('Salvo com sucesso!');
    }catch(err){ alert('Erro ao salvar: '+err.message); }
  });
}
bindForm('pacienteForm','/pacientes',{id:'pacienteSubmit',text:'Cadastrar'});
bindForm('agendaForm','/agendamentos',{id:'agendaSubmit',text:'Adicionar'});
bindForm('financeiroForm','/financeiro');
bindForm('blogForm','/blog',{id:'blogSubmit',text:'Publicar'});
const pf=document.getElementById('prontuarioForm');
if(pf){
  template.addEventListener('change',()=>pf.elements.evolucao.value=template.value);
  pf.elements.evolucao.value=template.value;
  bindForm('prontuarioForm','/prontuarios');
}
load();
