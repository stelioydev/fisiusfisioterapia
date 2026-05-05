# Fisius - Projeto Premium Local

Sistema completo para clínica de Fisioterapia e Pilates, com site público, painel administrativo, backend Spring Boot e banco MySQL.

## O que foi melhorado nesta versão premium

### Site público
- Layout responsivo.
- Logo sem fundo aplicado no padrão visual.
- Carrossel, serviços, funcionamento, localização, agendamento e footer premium.
- Blog preparado para posts do painel administrativo.

### Painel administrativo
- Logo igual ao frontend.
- Dashboard com indicadores rápidos.
- Pacientes com editar e excluir.
- Agenda inteligente com editar, excluir e status visual: agendado, presente, faltou e desmarcou.
- Prontuário simplificado com evolução/observação e anexo por URL.
- Financeiro com entradas, saídas, pendências e botão de gerar relatório/impressão.
- Blog com título, categoria, conteúdo e imagem por URL.

### Backend
- Segurança JWT removida temporariamente para facilitar execução local.
- Rotas liberadas no `SecurityConfig`.
- Código comentado para outro programador entender.
- Remoção da dependência prática do Lombok nas classes principais, evitando erros no Eclipse.
- Banco configurado com senha MySQL `12345678`.

## Como rodar o banco

No MySQL Workbench, execute:

```sql
CREATE DATABASE IF NOT EXISTS fisius_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE fisius_db;
```

As tabelas são criadas automaticamente pelo Spring Boot por causa desta configuração:

```properties
spring.jpa.hibernate.ddl-auto=update
```

## Como rodar o backend no Eclipse

1. Importe a pasta `backend` no Eclipse como projeto Maven.
2. Abra o arquivo:

```txt
src/main/java/br/com/fisius/FisiusApiApplication.java
```

3. Clique com botão direito e selecione:

```txt
Run As > Spring Boot App
```

4. Confirme no console se aparece:

```txt
Tomcat started on port 8080
```

5. Teste no navegador:

```txt
http://localhost:8080/api/public/status
```

## Login do painel

```txt
E-mail: admin@fisius.com
Senha: 123456
```

## Como rodar o frontend

Abra a pasta `frontend` no VS Code e use Live Server no `index.html`.

Painel administrativo:

```txt
frontend/admin/login.html
```

## Principais erros corrigidos

1. **Lombok não funcionando no Eclipse**  
   O Eclipse não estava gerando getters, setters, constructors e builders. Por isso apareciam erros como `setNome undefined`, `builder undefined` e campos `final` não inicializados.

2. **JWT travando a inicialização**  
   O filtro JWT dependia de configuração e beans que não estavam prontos. Por enquanto, o JWT foi removido do fluxo principal para o sistema rodar primeiro.

3. **Senha do MySQL incorreta**  
   Ajustada para `12345678` no arquivo `application.properties`.

4. **Erro `Public Key Retrieval is not allowed`**  
   Corrigido com `allowPublicKeyRetrieval=true` na URL do banco.

5. **Campo duplicado no prontuário**  
   `pacienteId` foi marcado como `@Transient` para não duplicar a coluna `paciente_id` gerada pelo relacionamento JPA.

## Próximo passo futuro

Depois que tudo estiver rodando, a segurança pode voltar de forma controlada:

- Login com JWT.
- Rotas protegidas.
- Refresh token.
- Perfis de acesso.
- Senhas fortes e usuários reais.
