# Fisius — Gestão para Fisioterapia e Pilates

Sistema web full stack para clínica de fisioterapia e Pilates, com site institucional, painel administrativo, API REST e banco de dados MySQL.

## Funcionalidades

### Site público

- Apresentação da clínica e dos serviços.
- Informações de funcionamento e localização.
- Agendamento e canal de contato.
- Blog integrado ao painel administrativo.
- Layout responsivo.

### Painel administrativo

- Dashboard com indicadores.
- Gestão de pacientes.
- Agenda com edição, exclusão e status visual.
- Prontuário simplificado com evolução e anexos por URL.
- Controle financeiro de entradas, saídas e pendências.
- Relatórios para impressão.
- Gestão de publicações do blog.

## Tecnologias

- **Frontend:** HTML5, CSS3 e JavaScript.
- **Backend:** Java 17, Spring Boot, Spring Web, Spring Data JPA e Bean Validation.
- **Banco de dados:** MySQL.
- **Documentação:** Swagger/OpenAPI.

## Estrutura

```text
├── backend/                       API REST
├── database/                      arquivos do banco de dados
├── frontend/                      site e painel administrativo
├── rodar-backend-windows.bat      inicialização no Windows
└── testar-backend-windows.bat     teste do backend no Windows
```

## Como executar

### 1. Banco de dados

```sql
CREATE DATABASE IF NOT EXISTS fisius_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Ajuste as credenciais do MySQL em `backend/src/main/resources/application.properties`.

### 2. Backend

```bash
cd backend
mvn spring-boot:run
```

Teste a API em `http://localhost:8080/api/public/status`.

### 3. Frontend

Abra `frontend/index.html` com Live Server no VS Code. O painel administrativo está em `frontend/admin/login.html`.

## Acesso de demonstração

- E-mail: `admin@fisius.com`
- Senha: `123456`

> Projeto configurado para desenvolvimento local. Antes de publicar, habilite autenticação segura, use senhas fortes e remova credenciais fixas.
