# Locatar.io

**Locatar.io** é uma API backend desenvolvida para gerenciamento de propriedades imobiliárias, permitindo que usuários registrem, organizem e administrem imóveis como casas, apartamentos ou outros tipos de propriedades.

O sistema foi projetado seguindo boas práticas modernas de desenvolvimento backend, com **arquitetura em camadas**, **separação clara de responsabilidades**, **segurança baseada em autenticação** e **modelagem extensível de entidades**.

O objetivo do projeto é fornecer uma base sólida para aplicações de gestão imobiliária, podendo ser integrado com aplicações web ou mobile.

---

# Visão Geral

O **Locatar.io** permite que usuários autenticados:

* registrem propriedades
* atualizem informações de imóveis
* gerenciem endereços
* consultem seus imóveis cadastrados
* organizem propriedades por status

A arquitetura foi desenhada para permitir crescimento do domínio, suportando múltiplos tipos de propriedades através de **herança JPA**.

---

# Arquitetura do Projeto

O projeto segue uma arquitetura baseada em **Domain Driven Design simplificado**, separando claramente:

```
src/main/java/io/locatar
│
├── api
│   └── v1
│       ├── controller
│       ├── dto
│       └── mapper
│
├── domain
│   ├── house
│   ├── property
│   ├── address
│   └── user
│
├── security
│
└── config
```

Cada camada possui responsabilidades bem definidas.

## API Layer

Responsável por expor os endpoints REST da aplicação.

Contém:

* Controllers
* DTOs
* Mappers

Objetivos:

* não expor entidades diretamente
* validar entrada de dados
* converter dados entre DTO e entidades

---

## Domain Layer

Contém as regras de negócio da aplicação.

Inclui:

* entidades
* serviços
* repositórios
* regras de domínio

Exemplo:

```
domain
 ├── property
 │   └── PropertyEntity
 │
 ├── house
 │   ├── HouseEntity
 │   ├── HouseRepository
 │   └── HouseService
```

---

# Modelagem de Entidades

A aplicação utiliza **herança de entidades com JPA** para representar diferentes tipos de propriedades.

## PropertyEntity

Classe abstrata que representa uma propriedade genérica.

Campos principais:

* `id`
* `name`
* `status`
* `address`
* `user`

Relacionamentos:

* **User → Property (ManyToOne)**
* **Property → Address (OneToOne)**

Estratégia de herança utilizada:

```
InheritanceType.JOINED
```

Isso permite que cada tipo de propriedade tenha sua própria tabela.

---

## HouseEntity

Especialização de `PropertyEntity`.

Campos específicos:

* `hasGarage`
* `hasYard`
* `lotSize`
* `numberOfFloors`

Essa estrutura permite adicionar novos tipos futuramente, como:

* Apartment
* CommercialProperty
* Land

sem alterar o modelo existente.

---

# Segurança

O sistema utiliza **Spring Security** para proteger os endpoints.

As regras são aplicadas diretamente nos serviços utilizando **segurança por método**.

Exemplo:

```
@PreAuthorize("isAuthenticated()")
```

Isso garante que apenas usuários autenticados possam executar determinadas operações.

---

# Autorização por Perfil

A aplicação permite diferentes níveis de acesso.

Exemplo:

* **Usuário comum**

  * pode criar e gerenciar apenas suas próprias propriedades

* **Super usuário**

  * pode criar propriedades para qualquer usuário
  * possui permissões administrativas

Exemplo de regra:

```
@PreAuthorize("hasRole('SUPER_USER')")
```

---

# Fluxo de Criação de Propriedade

1. Usuário autentica na API
2. Usuário envia requisição para criar propriedade
3. O sistema identifica o usuário autenticado
4. A propriedade é vinculada automaticamente ao usuário
5. O registro é persistido no banco de dados

---

# Persistência

O projeto utiliza:

* **Spring Data JPA**
* **Hibernate**

Os repositórios estendem `JpaRepository`, permitindo:

* consultas automáticas
* paginação
* geração de queries baseada em nome de método

Exemplo:

```
List<HouseEntity> findByUserId(UUID userId);
```

---

# Mapeamento de Dados

A API utiliza **DTOs** para transporte de dados.

Motivações:

* evitar exposição direta das entidades
* controlar o formato da API
* permitir versionamento

Conversões são feitas através de **mappers**.

Exemplo:

```
HouseMapper.toDTO(entity)
HouseMapper.toEntity(dto)
```

---

# Tecnologias Utilizadas

Principais tecnologias do projeto:

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* Lombok
* Maven
* PostgreSQL (ou outro banco relacional)

---

# Boas Práticas Aplicadas

O projeto segue diversas boas práticas de desenvolvimento backend.

Entre elas:

* separação de camadas
* uso de DTOs
* segurança por método
* herança de entidades
* modelagem orientada a domínio
* transações declarativas
* repositórios desacoplados

---

# Possíveis Evoluções

O projeto foi desenhado para permitir expansão futura.

Algumas evoluções possíveis:

* suporte a múltiplos tipos de propriedades
* sistema de contratos de aluguel
* gerenciamento de inquilinos
* sistema de pagamentos
* upload de imagens de propriedades
* notificações
* dashboards de gestão

---

# Como Executar o Projeto

## Pré-requisitos

* Java 17+
* Maven
* Banco de dados relacional

---

## Clonar o repositório

```
git clone https://github.com/JonathasSC/luga-backend
```

---

## Executar a aplicação

```
mvn spring-boot:run
```

A API estará disponível em:

```
http://localhost:8080
```

---

# Autor

Projeto desenvolvido por **Jonathas** como parte de um sistema backend para gerenciamento imobiliário.
