# API de Reserva de Mesas

Uma API REST robusta para sistema de reserva de mesas de restaurante, construída com **Java 21** e **Spring Boot 3**.

## Funcionalidades Principais

*   **Gerenciamento de Usuários**: Registro (o primeiro usuário vira SUPER ADMIN), Login, Autenticação JWT.
*   **Gerenciamento de Mesas**: Criar mesas (apenas Admin), Buscar mesas disponíveis por capacidade.
*   **Reservas**: Reservar mesas, prevenção de reserva dupla (janelas de 2 horas), Cancelamento.
*   **Segurança**: Controle de Acesso Baseado em Cargos (RBAC), Criptografia de senha BCrypt, JWT.
*   **Arquitetura**: Domain-Driven Design (DDD), Arquitetura em Camadas, Código Limpo.

## Tecnologias

*   **Linguagem**: Java 21
*   **Framework**: Spring Boot 3.4.1
*   **Banco de Dados**: PostgreSQL
*   **Segurança**: Spring Security + JWT
*   **Ferramentas**: Maven, Hibernate

## Como Rodar o Projeto (Passo a Passo)

Siga estes passos simples para iniciar a aplicação na sua máquina:

### 1. Pré-requisitos
Certifique-se de ter instalado:
*   Java 21
*   Maven (opcional, pois o projeto tem o wrapper `mvnw`)
*   PostgreSQL instalado e rodando

### 2. Configurar o Banco de Dados
1.  Abra seu gerenciador de banco de dados (pgAdmin, DBeaver, ou terminal).
2.  Crie um banco de dados chamado `reservation_db`.
3.  Abra o arquivo `src/main/resources/application.properties` e coloque sua senha:
    ```properties
    spring.datasource.username=postgres
    spring.datasource.password=SUA_SENHA_AQUI
    ```

### 3. Iniciar a Aplicação
Abra o terminal na pasta do projeto e rode:

**No Linux/Mac:**
```bash
./mvnw spring-boot:run
```

**No Windows:**
```bash
mvnw.cmd spring-boot:run
```

Aguarde até ver a mensagem de sucesso no terminal. A API estará rodando em `http://localhost:8080`.

## Como Testar

Você pode usar ferramentas visuais como **Postman** ou **Insomnia** para testar os endpoints.

### Rotas Disponíveis

**Autenticação:**
*   `POST /api/auth/register` - Criar conta (Envia JSON com username, email, password)
*   `POST /api/auth/login` - Fazer login

**Mesas (Precisa estar logado):**
*   `GET /api/tables` - Listar todas as mesas
*   `POST /api/tables` - Criar mesa (Só Admin)

**Reservas (Precisa estar logado):**
*   `POST /api/reservations` - Fazer reserva
*   `GET /api/reservations/my-reservations` - Ver minhas reservas

### Observações:
O **primeiro usuário** que você registrar será automaticamente **ADMIN**. Todos os outros serão usuários normais.

