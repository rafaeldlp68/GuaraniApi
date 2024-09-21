# GuaraniApi

GuaraniApi é uma API desenvolvida em Java usando o Spring Boot. O objetivo deste projeto é fornecer uma estrutura RESTful com uma conexão ao banco de dados MySQL.

## Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven 3+](https://maven.apache.org/download.cgi)
- [MySQL](https://www.mysql.com/)

## Configuração do projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/guaraniapi.git
   cd guaraniapi

2. **Configuração do MySQL:**
    Certifique-se de que o MySQL esteja rodando e crie o banco de dados necessário:
        CREATE DATABASE guaraniapi;

3. **Configurar as credenciais no arquivo application.properties:**
    Atualize as configurações do banco de dados no arquivo src/main/resources/application.properties:
        spring.datasource.url=jdbc:mysql://localhost:3306/guaraniapi
        spring.datasource.username=root
        spring.datasource.password=SenhaTeste123!

4. **Rodar a aplicação:**
    No diretório raiz do projeto, execute:
        mvn spring-boot:run

Endpoints
    A aplicação possui os seguintes endpoints:

Estrutura do Projeto
    O projeto está organizado da seguinte maneira:
        src
            └── main
                └── java
                    └── com
                        └── example
                            └── guaraniapi
                                ├── controller
                                ├── service
                                ├── repository
                                ├── model
                                └── config
                └── resources
                    ├── application.properties
                    ├── static
                    └── templates
            └── test
                └── java
                    └── com
                        └── example
                            └── guaraniapi

Tecnologias Utilizadas:
    Java 17
    Spring Boot
    Spring Data JPA
    MySQL
    Maven

Contribuição
    Se você quiser contribuir para o projeto, siga estas etapas:

        1. Faça um fork do projeto.
        2. Crie uma nova branch: git checkout -b minha-branch.
        3. Faça suas alterações e commit: git commit -m 'Adiciona uma nova feature'.
        4. Envie para o seu fork: git push origin minha-branch.
        5. Abra um Pull Request.

Licença
    Este projeto está licenciado sob os termos da MIT License.
        Basta criar ou atualizar o arquivo `README.md` na raiz do seu projeto e copiar o conteúdo acima. Se houver mais alguma modificação ou melhoria que você queira fazer, me avise!
