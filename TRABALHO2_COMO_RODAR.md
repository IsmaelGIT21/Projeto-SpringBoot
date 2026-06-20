# Trabalho 2 — Como rodar (VM Ubuntu)

Agência de Notícias Web — Spring Boot 4.1 + MongoDB + **Solr 10** (indexação/busca por conteúdo).

Arquitetura em 4 camadas: **modelo → persistência/índice → serviço → controle → view**.

---

## 1. Pré-requisitos na VM

```bash
# JDK 17 (a aplicação roda em Java 17; o Solr usa Java 21 dentro do container)
sudo apt update
sudo apt install -y openjdk-17-jdk git

# Docker + Compose (provavelmente já instalados das atividades de conteinização)
docker --version
docker compose version
```

> Não precisa instalar Maven: o projeto traz o **Maven Wrapper** (`./mvnw`), que baixa
> o Maven sozinho na primeira execução.

## 2. Pegar o código (transferência Windows → VM)

```bash
git clone https://github.com/IsmaelGIT21/Projeto-SpringBoot.git
cd Projeto-SpringBoot
# (depois, para atualizar:  git pull)
```

## 3. Subir os serviços (Mongo + Solr) com Docker

Na **raiz do repositório** (onde está o `docker-compose.yml`):

```bash
docker compose up -d
docker compose ps          # mongo e solr devem estar "running"
```

- Solr admin: <http://localhost:8983>  → *Core Selector* → **noticias** (criado automaticamente)
- MongoDB: `localhost:27017`

Parar depois: `docker compose down` (mantém os dados) ou `docker compose down -v` (apaga tudo).

## 4. Rodar a aplicação

### Opção A — linha de comando (mais simples)
```bash
cd agencia.noticias
./mvnw spring-boot:run
```

### Opção B — pelo Spring Tool Suite (STS)
1. Baixar o **STS 4** para Linux: <https://spring.io/tools> (arquivo `.tar.gz`).
2. Extrair e abrir:
   ```bash
   tar -xzf spring-tool-suite-4-*-linux.gtk.x86_64.tar.gz
   cd sts-4*/ && ./SpringToolSuite4
   ```
3. **File → Import → Maven → Existing Maven Projects** → selecionar a pasta `agencia.noticias`.
4. Rodar a classe `Main` como **Spring Boot App** (ou botão direito → Run As → Java Application).

Aplicação no ar: <http://localhost:9091/agencia.noticias>

> A app conecta no Mongo (`localhost:27017`) e no Solr (`localhost:8983`) — ambos expostos
> pelos containers no `localhost` da VM, então funciona rodando a app pela IDE ou pelo `mvnw`.

## 5. Gerar o JAR para a entrega

```bash
cd agencia.noticias
./mvnw clean package
# -> target/agencia.noticias-0.0.1-SNAPSHOT.jar
java -jar target/agencia.noticias-0.0.1-SNAPSHOT.jar   # (com os containers no ar)
```

---

## Roteiro de demonstração (apresentação)

1. **Cadastros**: criar 1–2 autores e 1–2 assuntos.
2. **Cadastrar notícia**: associar autor + assunto + situação, com título e conteúdo.
3. **Filtros**: listar notícias por autor, por assunto e por situação.
4. **Regra de negócio**: tentar cadastrar a **3ª notícia** do mesmo autor + mesmo assunto
   no mesmo dia → o sistema bloqueia com mensagem (limite de 2/dia).
5. **Busca por conteúdo (o ponto central do Trabalho 2)**: na tela *Listar Notícias*, usar
   o campo **"Buscar no conteúdo"** com uma palavra que só aparece no corpo do texto.
   - Fluxo: o termo é consultado no **índice Solr** (campo `conteudo_txt`) → o Solr devolve
     o **id** (valor de referência) → a aplicação busca no **MongoDB** os dados completos.
   - Dá para mostrar no Solr admin (*Query*, `conteudo_txt:<palavra>`) que o índice retorna o id.
6. **Remoção**: remover uma notícia → some do Mongo e do índice Solr.

## Mapa das camadas (para explicar o código)

| Camada            | Pacote / arquivos                                                        |
|-------------------|--------------------------------------------------------------------------|
| Modelo            | `modelo/` (Autor, Assunto, Noticia, Situacao)                            |
| Persistência      | `persistencia/` (repositories Spring Data MongoDB)                       |
| Índice (Solr)     | `indice/` (`IndiceNoticia` + `SolrIndiceNoticia`), `config/SolrConfig`   |
| Serviço (regras)  | `servico/` (`*Service` + `*ServiceImpl`, `RegraNegocioException`)        |
| Controle          | `controle/` (controllers finos, injetam os serviços)                    |
| View              | `templates/` (Thymeleaf + Tailwind)                                      |

**IoC/DI**: o `SolrClient` é um `@Bean` (`config/SolrConfig`); serviços e controllers usam
injeção por construtor; persistência e índice são abstraídos por interfaces.
