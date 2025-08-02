# GitHub Copilot Custom Instructions

## 🧠 Sobre o Projeto
Este é um projeto Java 21 utilizando Spring Boot que implementa uma API para validar tokens JWT conforme regras específicas. O foco é aplicar boas práticas de arquitetura como SOLID, Clean Code, Design Patterns e princípios de Orientação a Objetos.

## ✅ Convenções e Boas Práticas

- Seguir os princípios **SOLID**
- Aplicar práticas de **Clean Code**:
  - Nomes significativos
  - Métodos curtos e coesos
  - Evitar comentários desnecessários
- Separação de responsabilidades por camada:
  - `controller` → Entrada da API (leve)
  - `service` → Regras de negócio
  - `validator` → Validações unitárias por regra
  - `model/dto` → Representações de dados
- Aplicar **Design Patterns** quando fizer sentido:
  - Strategy Pattern nas validações
- Usar **DTOs** para entrada e saída (nunca entidades diretamente)
- Priorizar **imutabilidade** (`record`, `final`, etc.)
- Manter cobertura de testes adequada

## ☕️ Padrões específicos para projetos Spring

- Utilizar `@RestController` para controladores REST
- Utilizar `@Service` para regras de negócio
- Utilizar `@Component` ou `@Service` com `@Qualifier` para validadores
- Validar entrada com `@Valid`, `@NotBlank`, etc.
- Não lançar `Exception` genérica. Tratar ou criar exceções específicas
- Criar `record` para dados imutáveis como Claims e DTOs
- Evitar lógica complexa ou ifs aninhados nas controllers

## 🔗 Estrutura recomendada de pacotes

```
com.seuprojeto
├── controller
├── dto
├── entity
├── service
│   ├── JwtValidationService.java
│   └── validator
├── exception
└── config
```

## 📦 Objetivo do Copilot

Copilot deve sugerir:
- Validações de JWT com base nas regras
- Classes pequenas, bem separadas
- Testes automatizados para validadores
- Uso correto de beans Spring (`@Service`, `@Component`)
- Design orientado a responsabilidade única

Copilot **não deve**:
- Misturar camadas (ex: lógica de negócio na controller)
- Sugerir código sem tratamento de erro ou sem testes
- Usar entidades diretamente em controllers