# Backend

Backend responsável pela disponibilização de serviços, regras de negócio e gerenciamento dos dados da aplicação.

O projeto utiliza **Java, Spring Boot e Gradle**, seguindo convenções de desenvolvimento RESTful, versionamento de API, documentação de endpoints com Swagger/OpenAPI e controle de alterações no banco de dados por meio do Liquibase.

## Tecnologias utilizadas

- **Java 25** — linguagem de desenvolvimento.
- **Spring Boot** — framework para construção da aplicação.
- **Spring Web** — desenvolvimento de endpoints REST.
- **Spring Data JPA / Hibernate** — persistência e mapeamento objeto-relacional.
- **PostgreSQL** — banco de dados relacional.
- **Liquibase** — gerenciamento e versionamento do schema do banco de dados.
- **Swagger / OpenAPI** — documentação e exploração dos endpoints da API.
- **Gradle** — gerenciamento de dependências e build.

## Organização da aplicação

A aplicação segue o padrão arquitetural do Spring Boot, separando as responsabilidades entre as camadas do sistema.

| Camada | Responsabilidade |
|---|---|
| Controller | Recebimento e tratamento das requisições HTTP. |
| Service | Implementação das regras de negócio. |
| Repository | Comunicação e persistência dos dados. |
| Entity | Representação das entidades persistidas no banco. |
| DTO | Estruturação dos dados de entrada e saída da API. |

Essa separação permite manter o código organizado, facilitar a manutenção e evitar que regras de negócio fiquem diretamente acopladas aos endpoints.

## Convenções RESTful

Os endpoints seguem convenções RESTful, utilizando recursos como referência para a construção das rotas e métodos HTTP para representar as operações realizadas.

As rotas devem utilizar substantivos no plural para representar coleções de recursos, evitando verbos desnecessários em seus caminhos.

### Métodos HTTP

| Método | Utilização |
|---|---|
| GET | Consultar recursos ou coleções. |
| POST | Criar novos recursos. |
| PUT | Substituir ou atualizar integralmente um recurso. |
| PATCH | Atualizar parcialmente um recurso. |
| DELETE | Remover um recurso. |

### Exemplos de endpoints

Considerando um recurso chamado `customers`:

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/v1/customers` | Listar clientes. |
| GET | `/api/v1/customers/{id}` | Consultar um cliente específico. |
| POST | `/api/v1/customers` | Criar um cliente. |
| PUT | `/api/v1/customers/{id}` | Atualizar um cliente. |
| PATCH | `/api/v1/customers/{id}` | Atualizar parcialmente um cliente. |
| DELETE | `/api/v1/customers/{id}` | Remover um cliente. |

Os exemplos representam a convenção de rotas adotada e servem como referência para a criação de novos endpoints.

### Códigos HTTP

As respostas devem utilizar os códigos HTTP adequados para representar o resultado das operações.

- `200 OK` — operação realizada com sucesso.
- `201 Created` — recurso criado com sucesso.
- `204 No Content` — operação concluída sem conteúdo de resposta.
- `400 Bad Request` — requisição inválida.
- `401 Unauthorized` — autenticação necessária ou inválida.
- `403 Forbidden` — usuário sem permissão para realizar a operação.
- `404 Not Found` — recurso não encontrado.
- `409 Conflict` — conflito durante a operação.
- `500 Internal Server Error` — erro interno inesperado.

## Versionamento da API

A API utiliza versionamento explícito nas rotas, permitindo evoluir os contratos dos endpoints sem comprometer a compatibilidade com consumidores de versões anteriores.

O versionamento é identificado pelo prefixo da rota:

`/api/v1/`

Por exemplo:

`GET /api/v1/customers`

Quando houver alterações incompatíveis com contratos existentes, uma nova versão poderá ser disponibilizada:

`GET /api/v2/customers`

Dessa maneira, diferentes versões podem coexistir durante períodos de transição, permitindo que os consumidores migrem gradualmente para novos contratos.

Alterações que não comprometem a compatibilidade devem, preferencialmente, continuar utilizando a versão existente.

## Documentação da API — Swagger / OpenAPI

O projeto disponibiliza documentação interativa da API utilizando Swagger, baseada na especificação OpenAPI.

A documentação permite visualizar os endpoints disponíveis, compreender os contratos das requisições e respostas e explorar as funcionalidades da API sem precisar consultar diretamente o código-fonte.

Entre as informações disponibilizadas estão:

- Endpoints e seus respectivos métodos HTTP.
- Descrição das operações disponíveis.
- Parâmetros de rota e query parameters.
- Estruturas dos DTOs utilizados nas requisições.
- Modelos de resposta.
- Códigos HTTP retornados pelas operações.
- Informações de autenticação, quando configuradas.

### Acessando a documentação

Com a aplicação em execução, a interface do Swagger UI pode ser acessada pelo navegador.

Na configuração padrão do Springdoc OpenAPI, o endereço costuma ser:

`http://localhost:8080/swagger-ui/index.html`

A especificação OpenAPI também pode ser consultada diretamente por meio de:

`http://localhost:8080/v3/api-docs`

Os caminhos podem variar conforme a configuração da aplicação.

### Explorando os endpoints

Por meio do Swagger UI, é possível:

1. Visualizar os recursos disponibilizados pela API.
2. Expandir os endpoints para consultar seus parâmetros e descrições.
3. Verificar os modelos de requisição e resposta.
4. Informar os dados necessários para uma operação.
5. Executar requisições diretamente pela interface, quando permitido pela configuração de segurança.

A documentação deve acompanhar a evolução da API, mantendo os contratos e descrições atualizados conforme novas funcionalidades e endpoints forem implementados.

## Gerenciamento do banco de dados — Liquibase

O projeto utiliza o **Liquibase** para controlar e versionar as alterações realizadas na estrutura do banco de dados.

Em vez de realizar alterações manualmente no banco, as modificações são descritas em arquivos de migration, permitindo manter o histórico de evolução do schema junto ao código-fonte.

### Como funcionam as migrations

Cada migration representa uma alteração específica no banco de dados, como:

- Criação de tabelas.
- Adição ou remoção de colunas.
- Alteração de tipos de dados.
- Criação de índices.
- Adição de constraints e relacionamentos.
- Outras modificações necessárias à evolução do banco.

As migrations são organizadas por meio de changelogs, que definem a sequência de alterações que devem ser aplicadas.

### Execução das migrations

Durante a inicialização da aplicação, o Liquibase verifica o histórico de alterações registradas no banco de dados e identifica quais migrations ainda precisam ser executadas.

O controle é realizado por meio das tabelas internas do Liquibase:

- `DATABASECHANGELOG`: registra os changesets já executados.
- `DATABASECHANGELOGLOCK`: controla a execução concorrente das migrations.

Dessa forma, migrations já executadas não são aplicadas novamente, evitando alterações duplicadas no banco.

### Criação de novas migrations

Toda alteração estrutural no banco de dados deve ser acompanhada de uma migration.

O fluxo recomendado é:

1. Criar um novo arquivo de migration.
2. Definir um changeset com identificação única.
3. Descrever as alterações necessárias.
4. Registrar o novo arquivo no changelog principal, caso não seja incluído automaticamente.
5. Executar a aplicação e verificar se a migration foi aplicada corretamente.
6. Versionar o arquivo junto com as demais alterações do projeto.

**Importante:** migrations já executadas em ambientes compartilhados não devem ser modificadas retroativamente. Caso seja necessária uma correção, deve-se criar um novo changeset.

Isso garante que o histórico de alterações permaneça consistente entre os diferentes ambientes.

## Executando o projeto localmente

### Pré-requisitos

Antes de executar a aplicação, certifique-se de possuir:

- JDK 25.
- Git.
- Docker e Docker Compose (quando utilizados para disponibilizar os serviços de infraestrutura).
- PostgreSQL.
- Gradle Wrapper, disponibilizado no próprio projeto.

### Clonando o repositório

```bash
git clone <REPOSITORY_URL>
cd <PROJECT_DIRECTORY>
```

### Configuração do ambiente

Configure as variáveis de ambiente necessárias para a conexão com o banco de dados e demais serviços utilizados pela aplicação.

As configurações devem ser fornecidas conforme as propriedades definidas no projeto, evitando armazenar credenciais diretamente no código-fonte.

### Executando a aplicação

No Windows:

```bash
gradlew.bat bootRun
```

No Linux ou macOS:

```bash
./gradlew bootRun
```

### Executando os testes

```bash
./gradlew test
```

No Windows:

```bash
gradlew.bat test
```

## Boas práticas de desenvolvimento

Para manter a consistência e facilitar a manutenção do projeto, algumas convenções devem ser respeitadas:

- Manter a separação de responsabilidades entre Controllers, Services e Repositories.
- Utilizar DTOs para definir os contratos de entrada e saída da API.
- Seguir as convenções RESTful na criação de novos endpoints.
- Respeitar o versionamento existente das rotas.
- Utilizar os códigos HTTP adequados para cada operação.
- Centralizar as regras de negócio na camada de Service.
- Evitar consultas e operações de persistência diretamente nos Controllers.
- Manter a documentação Swagger/OpenAPI atualizada.
- Registrar alterações estruturais do banco exclusivamente por meio de migrations do Liquibase.
- Não modificar changesets que já tenham sido executados em ambientes compartilhados.
- Manter testes automatizados para funcionalidades e regras de negócio relevantes.

---

**Backend**

Desenvolvido com Java e Spring Boot.
