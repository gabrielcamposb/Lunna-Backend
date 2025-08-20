# Lunna-Backend

(Em Desenvolvimento) App criado para acompanhar o ciclo menstrual de forma simples, informativa e acolhedora. O projeto visa combater o tabu, a desinformação e os impactos da pobreza menstrual na vida escolar de meninas.

👥 Equipe

Este projeto está sendo desenvolvido por:

- Gabriel Campos
- Hellen Verena
- Renivaldo Júnior

Orientador: Prof. Lucas Almeida

UNEX - Centro Universitário de Excelência
Curso: Sistemas de Informação

# 🚀 Como executar o projeto
🔧 Dependências

Para rodar o projeto, você precisa ter instalado no seu ambiente:

- Java 17 (obrigatório)
- Gradle 8.x (ou utilizar o wrapper incluído ./gradlew)
- Spring Boot 3.5.4 (gerenciado automaticamente pelo Gradle)
- Banco de dados H2 (em memória, já configurado no projeto — não é necessário instalar)

# ⚠️ Importante: versões de Java anteriores a 17 não são compatíveis.
Recomendamos também utilizar um IDE como IntelliJ IDEA ou VS Code com suporte a Spring Boot

# Passo a passo para rodar localmente:

1. Clonar o repositório
git clone https://github.com/seu-usuario/lunna-backend.git
cd lunna-backend

2. Verificar versão do Java
java -version
O retorno deve indicar Java 17

3. Baixar as dependências
./gradlew build
(No Windows, usar gradlew.bat build)

4. Rodar a aplicação
./gradlew bootRun
A aplicação subirá na porta 8080:
http://localhost:8080

# 🛠 Endpoints principais:

Cadastro de usuário:
POST /auth/register

Login e geração de token:
POST /auth/login

Consultar usuário autenticado:
GET /auth/me (necessário Bearer Token no header)

# ⚙️ Configurações extras

Banco de dados H2 Console:
Disponível em http://localhost:8080/h2-console
(usuário: sa, senha: vazio)

Configurações adicionais:
Podem ser ajustadas em src/main/resources/application.properties
