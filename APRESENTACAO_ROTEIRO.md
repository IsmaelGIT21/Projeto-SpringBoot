# Roteiro de Apresentação — Trabalho 1: Agência de Notícias Web

**Disciplina:** Banco de Dados NoSQL (TSIN34D) — UTFPR Câmpus Toledo
**Professor:** Ivan Luiz Salvadori
**Integrantes:**
- João Pedro Berwanger Stroparo (RA 2707608) — stroparo@alunos.utfpr.edu.br
- Ismael Felipe Elger Correa (RA 2728397) — ismaelelger@hotmail.com

**Duração-alvo:** ~10 minutos · **Formato:** vídeo · **Apresentadores:** A e B

---

## O que o professor pediu

A apresentação deve cobrir três pontos:
1. **Estrutura de camadas** do projeto Web
2. **Principais classes** do sistema
3. **Demonstração** do sistema em execução

O roteiro abaixo foi montado em torno desses três tópicos. Sem exageros, sem teoria além do necessário.

---

## Antes de gravar

- [ ] MongoDB local rodando na porta 27017
- [ ] Aplicação subindo limpa (`Run As → Java Application` no `Main.java`)
- [ ] Banco com **pelo menos** 2 autores, 2 assuntos e 3 notícias (uma de cada situação) — senão a demo fica vazia
- [ ] Eclipse com abas pinadas: `Main.java`, `Noticia.java`, `NoticiaRepository.java`, `NoticiaController.java`, `noticia/listar.html`
- [ ] Browser em `http://localhost:9091/agencia.noticias/` numa janela ao lado

---

## Distribuição

| Apresentador | Foco |
|---|---|
| **A** | Camadas + classes de modelo e persistência |
| **B** | Classes de controle + templates + demo |

---

## Bloco 1 — Abertura (A, 30s)

**Falar:**
> "Boa tarde, somos [A] e [B]. Vamos apresentar nosso Trabalho 1 da disciplina de Banco de Dados NoSQL — um sistema web de agência de notícias feito com Spring Boot e MongoDB. A apresentação vai cobrir a estrutura de camadas, as principais classes, e uma demonstração do sistema rodando."

---

## Bloco 2 — Estrutura de camadas (A, 2 min)

**Mostrar:** Project Explorer do Eclipse, expandindo `br.edu.utfpr.td.tsi.agencia.noticias`.

**Falar:**
> "O projeto segue uma arquitetura em quatro camadas, organizada em pacotes:
>
> - **modelo** — as entidades de domínio: Autor, Assunto, Noticia e o enum Situacao.
> - **persistencia** — os repositórios, que são nossa camada DAO de acesso ao MongoDB.
> - **controle** — os controllers Spring MVC que recebem as requisições HTTP.
> - **templates** (em `src/main/resources/templates`) — as views renderizadas com Thymeleaf.
>
> O fluxo de uma requisição é sempre o mesmo: o navegador chama uma URL, o **controller** atende, conversa com o **repository** quando precisa persistir ou buscar dados, recebe os objetos do **modelo**, coloca no Model, e o Spring renderiza o **template** correspondente.
>
> Cada camada só conversa com a camada adjacente — controller nunca acessa o Mongo diretamente, template nunca toca em repository. Isso mantém o código desacoplado."

---

## Bloco 3 — Principais classes (split A + B, ~4 min)

### 3a — Modelos e persistência (A, 2 min)

**Mostrar:** `Autor.java`.

**Falar:**
> "Esta é a classe Autor. A anotação `@Document(collection = "Autor")` informa ao Spring Data MongoDB que essa classe é persistida na coleção Autor — é o equivalente NoSQL do @Entity do JPA. O `@Id` marca o identificador único do documento."

**Mostrar:** `Noticia.java`.

**Falar:**
> "A classe Noticia é o centro do sistema. Repare que ela guarda apenas `autorId` e `assuntoId` — referências por ID, não os documentos inteiros. Optamos por essa modelagem porque se o nome do autor mudar, a alteração reflete automaticamente em todas as notícias.
>
> A `situacao` é tipada como enum (Em produção, Publicada ou Cancelada) — garante que só esses três valores são aceitos."

**Mostrar:** `NoticiaRepository.java`.

**Falar:**
> "Esta é a camada DAO. A interface estende `MongoRepository<Noticia, String>` — só isso já nos dá `save`, `findAll`, `findById` e `deleteById` automaticamente, sem precisar implementar.
>
> Os três métodos declarados — `findByAutorId`, `findByAssuntoId`, `findBySituacao` — são **queries derivadas**: o Spring Data lê o nome do método e gera a query Mongo automaticamente em runtime. Não existe NoticiaRepositoryImpl no projeto."

### 3b — Controllers (B, 2 min)

**Mostrar:** `AutorController.java`.

**Falar:**
> "Os controllers ficam no pacote `controle`. Aqui está o do Autor — um CRUD simples: `cadastrarAutor` exibe o form e salva, `listarAutores` mostra a tabela, `editarAutor` e `removerAutor` completam o ciclo.
>
> Repare nesta linha: `@Autowired private AutorRepository autorRepository`. O controller não instancia o repository — pede para o Spring fornecer uma implementação. Isso é Inversão de Controle: o framework liga as peças, e os controllers não precisam conhecer detalhes da camada de persistência."

**Mostrar:** `NoticiaController.java`, método `listarNoticias`.

**Falar:**
> "O NoticiaController é o mais rico. Esse método aceita três query params opcionais — autorId, assuntoId e situacao — e atende o requisito da listagem filtrada.
>
> Como a Noticia guarda só IDs, montamos esses dois mapas — `autoresMap` e `assuntosMap` — para a view poder mostrar o nome do autor e do assunto em vez do ID na tabela."

**Mostrar (rápido):** `templates/noticia/listar.html`.

**Falar:**
> "As views usam Thymeleaf. O `th:each` itera sobre a lista, `${autoresMap[n.autorId].nome}` faz o lookup pelo ID, e fragments compartilham o cabeçalho entre todas as páginas."

---

## Bloco 4 — Demonstração (B + A, ~3 min)

**B:**

**Mostrar:** browser em `http://localhost:9091/agencia.noticias/`.

**Falar:**
> "Esta é a home. Vou cadastrar um autor rápido... agora um assunto... agora uma notícia escolhendo autor, assunto e situação."

**A:**

**Mostrar:** Listar Notícias, aplicar filtros.

**Falar:**
> "Na listagem, a tabela já mostra os nomes do autor e do assunto — vindos do lookup que mostramos no controller. Vou filtrar por situação: só publicadas. Agora por autor específico. E limpar.
>
> Edição: pego uma notícia Em Produção, mudo para Publicada, salvo, e a listagem já reflete.
>
> Tudo isso está persistido no MongoDB, no banco `agencia-noticias`, nas coleções Autor, Assunto e Noticia — criadas automaticamente pelo Spring Data."

---

## Bloco 5 — Encerramento (A + B, 30s)

**A:**
> "Resumindo: arquitetura em quatro camadas, padrão DAO via Spring Data MongoDB, três CRUDs completos com listagem filtrada."

**B:**
> "Obrigado!"

---

## Perguntas frequentes (caso o professor questione)

| Pergunta | Resposta curta |
|---|---|
| "MongoRepository é DAO mesmo?" | "Sim — Repository é especialização do padrão DAO. Declara o contrato, e a implementação é gerada pelo Spring em runtime." |
| "Por que referência por ID e não documento embarcado?" | "Consistência. Se embarcasse, mudar o nome do autor exigiria atualizar todas as notícias antigas." |
| "Como funciona findByAutorId sem código?" | "Spring Data lê o nome do método em tempo de inicialização e gera a query Mongo automaticamente — convenção sobre configuração." |
| "Por que @Autowired e não new?" | "MongoRepository é interface, não dá pra instanciar com new. Além disso, IoC permite trocar a implementação sem alterar o controller." |

---

## Dicas para a gravação

- Apontar o cursor sobre o código que está sendo explicado.
- Falar pausado — vídeo permite cortar pausas longas, mas não dá pra reduzir fala atropelada.
- Se errar no meio do bloco, pause e refaça o bloco inteiro — corta depois.
- Encurtar se passar de 10min: a demo (bloco 4) é o primeiro candidato.
