[![Kotlin](https://img.shields.io/badge/kotlin-1.6.21-red.svg?logo=kotlin "Kotlin Version")](http://kotlinlang.org)
[![Ktor Server Version](https://img.shields.io/badge/ktor-2.0.1-red.svg "Ktor Server Version")](https://github.com/ktorio/ktor)
[![PostgreSQL Version](https://img.shields.io/badge/exposed-0.38.2-red.svg?logo=postgresql "PostgreSQL Version")](https://www.postgresql.org/)

# Welcome to Login API!

In this example repository, you will be able to see KTOR based backend application working with a couple of techniques ;

- [KTOR](https://ktor.io/)
  > Kotlin based web framework.
- [JWT](https://jwt.io)
- [Exposed](https://github.com/JetBrains/Exposed)
  > ORM Framework for Kotlin.
- [PostgreSQL](https://www.postgresql.org/)
- [Flyway](https://github.com/flyway/flyway)
  > Migration tool for database

## Installation

With docker, this application will be exposed on 8080 port.

```bash
docker compose build
docker compose up
```

## Environment Configuration

In [**
application.conf**](https://github.com/mfarkan/k-account-login-api/blob/master/src/main/resources/application.conf)
you will see some environment for working in local environment or if you want to run this app
in another database, you should change these configurations for your data;

- PORT
  > If you change this config, your application will be working on that port.
- JWT_SECRET
  > Secret for JWT creation.
- DB_PASSWORD
  > database password
- DB_CONNECTION
  > database connection string
- DB_USERNAME
  > database username

## JWT UML

In the example below, you see Json Web Token Authentication between client and server.

```mermaid
sequenceDiagram
Client ->> Resource: Hello Resource, I want this data from you
Resource ->> Client: I don't know who you are(401)
Client ->> JWT Server: Hello, here is my username&password for data
JWT Server ->> Client: Okey, I know you. Here is your token(access_token)
Client ->> Resource: Here I am again with Bearer access_token
Resource ->> Client: Okey, I know you. This is your data(200)
```

> **Note:** The **Login API** is working on current KTOR 2.0.1 version and working with dockerized PostgreSQL in
> development environment. If you want to use, please change configuration file inside resources file.
