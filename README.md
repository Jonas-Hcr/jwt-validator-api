
# JWT Validator API

## 🧪 Descrição

Esta API em Java 21 com Spring Boot foi desenvolvida para validar tokens JWT conforme regras de negócio específicas. É um projeto técnico focado em boas práticas de arquitetura como SOLID, Clean Code e Design Patterns.

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Maven
- GitHub Actions (CI/CD)
- AWS EC2 (Deploy)
- NGINX (Reverso Proxy)
- Terraform (Provisionamento de Infraestrutura)
- CloudWatch (Observabilidade)
- Postman / Insomnia (Testes)

## 🔒 Validações do JWT

A API executa as seguintes validações:
- Estrutura do token (3 partes: header.payload.signature)
- Assinatura (HMAC SHA256)
- Regras customizadas no payload (issuer, audience, expiration, etc.)

## 🧠 Funcionalidades e Decisões Técnicas

- **Validação de JWT**:
  - Verificação da estrutura do token
  - Verificação da assinatura
  - Validação de regras de negócio específicas no payload

- **Adoção de Boas Práticas**:
  - **Design Pattern: Strategy**
    - Foi utilizado o padrão Strategy no módulo de regras (`JwtValidatorRules`) para permitir a **flexibilidade e extensibilidade** na validação de tokens JWT.
    - Cada regra de validação (como verificar a expiração, o issuer, o audience, etc.) foi implementada como uma estratégia independente.
    - Isso permite adicionar ou modificar regras **sem alterar o fluxo principal** de validação, promovendo **Open/Closed Principle** do SOLID.
    - Ideal para cenários em que as regras de negócio são **modulares, opcionais ou configuráveis**.
  - Clean Code & SOLID
  - DTOs com validação via `@Valid`
  - Exceptions tratadas com `@ControllerAdvice`
  - Logs com contexto (correlation ID via AOP)

## ☁️ Deploy na AWS

A infraestrutura foi provisionada com Terraform:
- Criação de instância EC2
- Configuração de Security Groups
- Deploy automático via GitHub Actions
- Reverse proxy com NGINX

## ⚙️ Como rodar localmente
```bash
# Compile o projeto
mvn clean package

# Rode a aplicação
java -jar target/jwt-validator-api.jar
```
A API ficará disponível em http://localhost:8080/jwt-validator.

## ⚙️ Observabilidade

Logs da aplicação enviados ao **AWS CloudWatch** com:
- Coleta de logs via agente CloudWatch
- Centralização dos logs de produção
- Permite troubleshooting e alertas no futuro

## ✅ Como Acessar

Você pode testar a API com ferramentas como **Postman** ou **Insomnia**.

### Endpoint:

```
POST http://13.222.59.208:8080/api/v1/jwt-validator
Content-Type: application/json

{
  "token": "seu-token-jwt-aqui"
}
```

## 📁 Estrutura do Projeto

- `src/main/java/.../controller` – Controllers REST
- `src/main/java/.../service` – Regras de negócio
- `src/main/java/.../strategy` – Regras de validação com Strategy Pattern
- `infra/` – Scripts Terraform e provisionamento da AWS
- `.github/workflows/deploy.yml` – Deploy automático

## ✖️ Itens não implementados (com justificativas)

- **Kubernetes e Helm Chart**  
  Não utilizei orquestração com Kubernetes nem Helm Chart neste projeto devido ao escopo limitado e à natureza simples da aplicação. Além disso, por se tratar de um teste técnico com prazo reduzido e sem recursos adicionais, optei por uma abordagem mais direta e funcional: provisionar uma instância EC2 via Terraform.

- **Containerização com Docker**  
  Embora a containerização seja uma prática recomendada, neste cenário considerei que rodar a aplicação diretamente em uma instância EC2 atenderia bem ao objetivo do projeto. Como é um serviço único e independente, optei por não adicionar a camada de Docker para manter a solução mais simples e objetiva.
---

Desenvolvido para um desafio técnico utilizando as melhores práticas modernas de desenvolvimento backend com Java.
