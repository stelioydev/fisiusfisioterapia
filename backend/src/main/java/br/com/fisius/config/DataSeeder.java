package br.com.fisius.config;

import br.com.fisius.entity.*;
import br.com.fisius.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Carrega dados iniciais para o sistema não abrir vazio.
 * Cria o administrador padrão, alguns serviços, posts e avaliações.
 */
@Component
public class DataSeeder implements CommandLineRunner {
    private final UsuarioRepository usuarios;
    private final ServicoRepository servicos;
    private final AvaliacaoRepository avaliacoes;
    private final BlogPostRepository blog;
    private final PasswordEncoder encoder;

    public DataSeeder(UsuarioRepository usuarios, ServicoRepository servicos,
                      AvaliacaoRepository avaliacoes, BlogPostRepository blog,
                      PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.servicos = servicos;
        this.avaliacoes = avaliacoes;
        this.blog = blog;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        usuarios.findByEmail("admin@fisius.com").ifPresentOrElse(adminExistente -> {
            // Garante que o login demo continue funcionando mesmo se o banco já existir.
            if (!encoder.matches("123456", adminExistente.getSenha())) {
                adminExistente.setSenha(encoder.encode("123456"));
                usuarios.save(adminExistente);
            }
        }, () -> {
            Usuario admin = new Usuario();
            admin.setNome("Administradora Fisius");
            admin.setEmail("admin@fisius.com");
            admin.setSenha(encoder.encode("123456"));
            admin.setPerfil("ADMIN");
            usuarios.save(admin);
        });

        if (servicos.count() == 0) {
            criarServico("Pilates", "Fisioterapia", 100.0);
            criarServico("Fisioterapia Ortopédica", "Fisioterapia", 100.0);
            criarServico("Limpeza de pele", "Estética Facial", 120.0);
            criarServico("Drenagem linfática", "Estética Corporal", 130.0);
        }

        if (avaliacoes.count() == 0) {
            Avaliacao a = new Avaliacao();
            a.setNome("Paciente Google");
            a.setEstrelas(5);
            a.setComentario("Atendimento excelente, acolhedor e muito profissional.");
            a.setDestaque(true);
            avaliacoes.save(a);
        }

        if (blog.count() == 0) {
            BlogPost p = new BlogPost();
            p.setTitulo("Benefícios do Pilates para a postura");
            p.setCategoria("Pilates");
            p.setImagemUrl("https://images.unsplash.com/photo-1518611012118-696072aa579a?auto=format&fit=crop&w=900&q=80");
            p.setConteudo("O Pilates contribui para força, mobilidade, respiração e consciência corporal.");
            p.setPublicado(true);
            p.setCriadoEm(java.time.LocalDate.now());
            blog.save(p);
        }
    }

    private void criarServico(String nome, String categoria, Double valor) {
        Servico s = new Servico();
        s.setNome(nome);
        s.setCategoria(categoria);
        s.setValor(valor);
        s.setAtivo(true);
        servicos.save(s);
    }
}
