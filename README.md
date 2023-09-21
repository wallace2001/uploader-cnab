
<h1 align="center">
  <br>
  <a href="#"><img src="https://github.com/wallace2001/uploader-cnab/blob/master/src/main/resources/static/image/spring-security.png?raw=true" alt="Uploader CNAB" width="200"></a>
  <br>
  Uploader CNAB
  <br>
</h1>

<h4 align="center">Sistema de upload de arquivos cnab utilizando<a href="https://spring.io/projects/spring-boot" target="_blank"> Spring Boot</a>.</h4>

![screenshot](/src/main/resources/static/image/login.png)

## Key Features

* Sistema de autorização, utilizando tanto o Spring Security quanto o OAuth2 com logins pelas redes sociais (Google e Github)
* Sistema de Upload de arquivos .txt para importar pro sistema operações de empresas.
* Sistema de Listagem com paginação das operações já importadas
* Só é possivel entrar na home usuários que estejam logados (está bem simples o sistema de autorização)

## Spring boot versão 3.1.3

## Java versão 17

## Como Instalar o Projeto

Para clonar essa aplicação, você vai precisar do [Git](https://git-scm.com), [maven](https://maven.apache.org/download.cgi), [java](https://jdk.java.net/archive/) e [docker](https://docs.docker.com/compose/install/)) instalados no seu computador. Para rodar o seguinte trecho de código:

```bash
# Clone this repository
$ git clone https://github.com/wallace2001/uploader-cnab.git

# Go into the repository
$ cd uploader-cnab
```

## Configurando o ambiente local
Para facilitar, ao clonar o arquivo application.yaml já vai está configurado e junto vai ir o docker-compose.yaml.

após a IDE finalizar de baixar as dependências do projeto, rode o seguinte comando:

```bash
# running postgres
$ docker-compose up -d
```

Agora com o banco de dados rodando, é possivel entrar no pgadmin que vai está na porta 8080 da sua máquina.

```JS
  // pgadmin
  hostname: admin@admin.com
  password: admin

  // Database
  databaseName: postgres
  userDatabase: myuser
  passwordDatabase: mypassword
  host: localhost
  port: 5432
```

## Prontinho :)
Agora com tudo no ar, pode rodar o projeto pela IDE.

## Telas

### Upload view
![screenshot](/src/main/resources/static/image/upload.png)

### List view
![screenshot](/src/main/resources/static/image/list.png)
## License

MIT

---

> GitHub [Wallace2001](https://github.com/wallace2001) &nbsp;&middot;&nbsp;

