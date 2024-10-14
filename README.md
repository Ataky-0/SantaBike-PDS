# SantaBike
*Projeto da matéria 'Projeto Detalhado de Software' com banco de dados Postgres e padrões Singleton (DataBase) e Decorator (Relatorio).*

## Configure seu Banco de Dados

**Antes de compilar o projeto, opte por configurar um banco de dados (Postgres)! pois seu funcionamento é totalmente dependente de um.**

Para fazer isso, **dentro da pasta app** use o arquivo `db.properties.example` para criar o arquivo `db.properties` *(ou simplesmente retirando o .example de seu nome)*, ainda dentro da pasta app.

## Como Compilar e Rodar

### -> Instalar Gradle ⬇ (opcional)
O Gradle é o gerenciador do projeto, existem binários `gradlew` dentro do diretório primário que podem serem usados no lugar do gradle instalado no sistema, caso queira instalá-lo em seu sistema [Clique aqui](https://gradle.org/install/) para um guia de instalação, escolha a versão "binary-only" caso esteja no Windows para instalar apenas o necessário.

### -> Compilar ⚙
Para compilar use o comando:

**Atenção! caso queiram usar o gradlew já presente no repositório basta trocar as ocorrências de gradle por ./gradlew no UNIX ou por gradlew no Windows (sem o ./).**

```sh
gradle clean build
```

```sh
## Gradlew em sistema UNIX (Mac/Linux)
./gradlew clean build
```

```sh
## Gradlew em sistema Windows
gradlew clean build
```

**Importante que esteja na pasta certa, pode checar usando "dir" ou "tree" no Windows, "ls" ou "pwd" no Linux.**

Neste comando em específico, o Gradle irá limpar o ambiente de compilações passadas, e por fim compilar novamente o projeto inteiro.

### -> Rodar *▶*
Para rodar o projeto recém compilado:

```sh
gradle run -q --console=plain --warning-mode none
```

```sh
## Gradlew em sistema UNIX (Mac/Linux)
./gradlew run -q --console=plain --warning-mode none
```

```sh
## Gradlew em sistema Windows
gradlew run -q --console=plain --warning-mode none
```
Este comando vai executar o projeto de maneira direta e limpa, sem exibir avisos ou elementos visuais extras no console, proporcionando uma saída mais simples e objetiva.