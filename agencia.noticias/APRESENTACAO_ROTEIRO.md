# Roteiro de Apresentação — Trabalho 1: Agência de Notícias Web

**Disciplina:** Banco de Dados NoSQL (TSIN34D) — UTFPR Câmpus Toledo
**Professor:** Ivan Luiz Salvadori
**Integrantes:**
- João Pedro Berwanger Stroparo (RA 2707608) — stroparo@alunos.utfpr.edu.br
- Ismael Felipe Elger Correa (RA 2728397) — ismaelelger@hotmail.com

**Duração-alvo:** ~10 minutos · **Formato:** vídeo · **Apresentadores:** A e B
**Stack:** Spring Boot 3.3.4 · Java 17 · Spring Data MongoDB · Thymeleaf · Tailwind CSS

---

## Antes de gravar (checklist)

- [ ] MongoDB local rodando na porta 27017
- [ ] Aplicação subindo limpa (`Run As → Java Application` no `Main.java`)
- [ ] **Pré-popular o banco** com 2 autores, 2 assuntos e 2-3 notícias (uma de cada situação) pra demo não ficar vazia
- [ ] Eclipse com abas pinadas, na ordem: `Main.java`, `Autor.java`, `Noticia.java`, `NoticiaRepository.java`, `AutorController.java`, `NoticiaController.java`, `noticia/listar.html`
- [ ] Browser em `http://localhost:9091/agencia.noticias/` em outra janela/aba
- [ ] Resolução de gravação que mostre código legível (mínimo 1080p, fonte do Eclipse >= 12pt)

---

## Distribuição

| Apresentador | Foco | Blocos |
|---|---|---|
| **A** | Arquitetura / Backend / Persistência | 1, 3, 4, 5, 10a |
| **B** | Web / Controllers / Demo | 2, 6, 7, 8, 9, 10b |

---

## Bloco 1 — Abertura (A, 30s)

**Mostrar:** câmera/slide com nome do trabalho + integrantes do grupo, ou só o Eclipse aberto.

**Falar (A):**
> "Boa tarde, eu sou [A] e este é [B]. Vamos apresentar nosso Trabalho 1 da disciplina de Banco de Dados NoSQL, professor Ivan Salvadori. O sistema é uma aplicação web para uma agência digital de notícias, que gerencia autores, assuntos e reportagens — desenvolvida em Spring Boot com MongoDB."

---

## Bloco 2 — Requisitos e stack (B, 1min)

**Mostrar:** rápida passada no PDF do enunciado OU lista no slide.

**Falar (B):**
> "O sistema atende três cadastros: jornalistas/autores, assuntos e notícias — todos com CRUD completo. Cada notícia é associada a um autor e a um assunto, e tem uma situação: Em produção, Publicada ou Cancelada. A listagem de notícias pode ser filtrada por autor, por assunto ou por situação.
>
> Para a stack, usamos Spring Boot 3.3.4 como framework, Java 17, Spring Data MongoDB na persistência, Thymeleaf como engine de templates server-side, e Tailwind CSS via CDN no front. Banco MongoDB, conforme exigido."

---

## Bloco 3 — Arquitetura em camadas (A, 1min)

**Mostrar:** Project Explorer no Eclipse, expandindo `br.edu.utfpr.td.tsi.agencia.noticias`.

**Falar (A):**
> "O projeto segue a separação MVC clássica do Spring, em três pacotes mais a camada de view. Em **modelo** ficam as entidades — Autor, Assunto, Noticia e o enum Situacao. Em **persistencia** ficam os repositórios — nossa camada DAO. Em **controle** ficam os controllers que recebem requisições HTTP. E em `resources/templates` ficam as views Thymeleaf, separadas por entidade.
>
> A regra é: cada camada só conversa com a adjacente. Controller chama repository, repository fala com o Mongo. A view só vê o que o controller coloca no Model."

---

## Bloco 4 — Modelos e mapeamento Mongo (A, 1min30)

**Mostrar:** abrir `Autor.java`, depois `Noticia.java`, depois `Situacao.java`.

**Falar (A) — no Autor.java:**
> "A anotação `@Document(collection = "Autor")` informa ao Spring Data que essa classe é persistida na coleção Autor do Mongo — equivalente NoSQL do @Entity do JPA. O `@Id` marca o identificador único — geramos UUID no controller na hora do cadastro. O `@DateTimeFormat` instrui o Spring a converter a string do `<input type="date">` em LocalDate."

**Falar (A) — no Noticia.java, apontando autorId/assuntoId:**
> "Aqui uma decisão importante de modelagem: a Notícia guarda apenas `autorId` e `assuntoId` — referências por ID, não documentos embarcados. A vantagem é que se o nome do autor mudar, a alteração reflete automaticamente em todas as notícias. Se embarcássemos, teríamos que sincronizar manualmente."

**Falar (A) — no Situacao.java:**
> "Situacao é um enum com três valores: EM_PRODUCAO, PUBLICADA e CANCELADA. Garantir como enum em vez de string solta é mais seguro — o banco só aceita esses três valores, e o Spring faz a conversão automática string ↔ enum quando recebe do formulário."

---

## Bloco 5 — Camada de persistência: DAO via Spring Data (A, 1min)

**Mostrar:** abrir `NoticiaRepository.java`.

**Falar (A):**
> "Essa é nossa camada DAO. A interface estende `MongoRepository<Noticia, String>` — Noticia é a entidade gerenciada, String é o tipo do ID. Só pelo `extends`, o Spring Data já nos dá de graça: `save`, `findAll`, `findById`, `deleteById`, `count`, `existsById`. Nenhuma linha de código nossa.
>
> E essas três declarações aqui — `findByAutorId`, `findByAssuntoId` e `findBySituacao` — são o que o Spring Data chama de **queries derivadas**. O Spring lê o nome do método e gera a query Mongo automaticamente em tempo de execução. Não precisa SQL, não precisa implementação manual, não precisa anotar `@Query`.
>
> Não existe `NoticiaRepositoryImpl` no projeto. Quando a aplicação sobe, o Spring gera uma classe proxy em runtime que implementa essa interface. Esse é o padrão DAO/Repository do Spring."

---

## Bloco 6 — Inversão de Controle (IoC) (B, 1min)

**Mostrar:** abrir `AutorController.java`, apontar o `@Autowired`.

**Falar (B):**
> "Aqui está a Inversão de Controle. O controller declara `@Autowired private AutorRepository autorRepository` — note que ele **não instancia** o repository com `new`. Ele pede para o contêiner do Spring: 'me forneça uma implementação dessa interface'.
>
> Isso desacopla as camadas. O controller nem sabe que existe um Mongo por baixo — só conversa com a interface. Se um dia quiséssemos trocar Mongo por outro banco, ou mockar para testes, mudamos só a implementação injetada, sem tocar no controller. O contêiner IoC é o que liga as peças."

---

## Bloco 7 — Controllers e fluxo request/response (B, 1min30)

**Mostrar:** alternar entre `AutorController.java` (simples) e `NoticiaController.java` (rico).

**Falar (B) — no AutorController:**
> "Cada método aqui mapeia uma URL. `@GetMapping("/cadastrarAutor")` retorna o nome da view — o Spring vai renderizar `templates/autor/cadastrar.html`. No `@PostMapping`, recebemos o objeto Autor automaticamente preenchido — o Spring faz databinding pelos nomes dos campos do form. Salvamos com UUID gerado, e retornamos `redirect:/listarAutores` — esse é o padrão PRG, Post/Redirect/Get, que evita reenviar o form se o usuário der F5 na página de sucesso."

**Falar (B) — no NoticiaController, método listarNoticias:**
> "Esse é o método mais rico. Recebe três query params **opcionais** — autorId, assuntoId e situacao — e aplica o filtro correspondente. Quando nenhum filtro é passado, retorna todas. Atende o requisito da listagem filtrada.
>
> Como a notícia guarda só IDs, montamos esses dois mapas `autoresMap` e `assuntosMap` indexados por ID. A view vai usar esses mapas pra mostrar o NOME do autor e do assunto em vez do ID na tabela. É o lookup que substitui o JOIN do mundo relacional."

---

## Bloco 8 — Templates Thymeleaf (B, 1min)

**Mostrar:** abrir `templates/index.html`, depois `templates/noticia/listar.html`.

**Falar (B) — no index.html:**
> "Os templates usam Thymeleaf. Aqui no topo, `th:replace="~{fragments/nav :: navbar}"` inclui o cabeçalho compartilhado, definido em `fragments/nav.html`. Evita repetir a navbar em toda página — princípio DRY."

**Falar (B) — no listar.html, apontando elementos:**
> "Na listagem, `th:each="n : ${noticias}"` itera sobre a lista que o controller injetou. Aqui — `${autoresMap[n.autorId].nome}` — é o lookup que mencionei: usamos o `autorId` da notícia como chave do mapa para mostrar o nome do autor. O ternário trata o caso de autor removido.
>
> O form de filtros usa `th:selected` para preservar o valor escolhido após o submit. E o visual todo é Tailwind via CDN — utility-first, sem build de assets."

---

## Bloco 9 — Demonstração ao vivo (alternando, 1min30)

**B na primeira metade:**

**Mostrar:** `http://localhost:9091/agencia.noticias/`

**Falar (B):**
> "Esta é a home — seis cards pra cada operação. Vou cadastrar um autor rápido (preenche e salva), depois um assunto (preenche e salva)."

**Mostrar:** ir em Cadastrar Notícia.

**Falar (B):**
> "Aqui no cadastro de notícia, os três selects — autor, assunto, situação — vêm populados pelo controller. Preencho título e conteúdo, cadastro."

**A na segunda metade:**

**Mostrar:** Listar Notícias com filtros.

**Falar (A):**
> "Na listagem aparece o NOME do autor e do assunto — não o ID. E aqui os filtros: vou filtrar só por Publicadas... agora por Autor específico... agora limpar."

**Mostrar:** editar notícia, mudar situação, salvar.

**Falar (A):**
> "Edição: vou pegar uma notícia Em Produção e mudar pra Publicada. Salvo, e na listagem já aparece atualizada.
>
> Tudo isso está conversando com o MongoDB local na porta 27017, no banco `agencia-noticias`, nas coleções `Autor`, `Assunto` e `Noticia` — que o Spring Data criou automaticamente quando salvamos o primeiro documento."

---

## Bloco 10 — Encerramento (A + B, 30s)

**Mostrar:** voltar pra home.

**Falar (A):**
> "Resumindo o que cobrimos: padrão DAO via Spring Data MongoDB com queries derivadas, Inversão de Controle via @Autowired, três CRUDs completos, listagem filtrada por autor, assunto e situação."

**Falar (B):**
> "Camadas bem separadas, modelagem por referência consistente com NoSQL, e visual limpo com Thymeleaf + Tailwind. Obrigado, encerramos por aqui."

---

## Perguntas prováveis (caso o professor pergunte depois)

| Pergunta | Resposta |
|---|---|
| "Por que MongoRepository é DAO?" | "Repository é uma especialização do padrão DAO/Repository — declara o contrato de persistência, e a implementação concreta é gerada em runtime pelo Spring. Mesma ideia do DAO clássico: separar lógica de negócio da lógica de acesso a dados." |
| "Por que referência por ID e não documento embarcado?" | "Consistência de dados. Embarcar o autor inteiro na notícia significa que se o nome mudar, eu precisaria atualizar todas as notícias antigas. Com referência por ID, edito o autor em um lugar só. O custo é uma busca extra no controller, que mitigamos montando um mapa em vez de buscar por ID a cada linha (evita N+1)." |
| "Como funciona findByAutorId?" | "Spring Data lê o nome do método em tempo de inicialização, identifica o padrão `findBy` + nome do campo, e gera a query Mongo `{ autorId: ? }` automaticamente. É reflection + convenção de nome, sem código nosso." |
| "Por que @Autowired e não new?" | "Primeiro porque MongoRepository é interface — não dá pra instanciar com new. Segundo, porque IoC permite trocar a implementação sem mexer no consumidor: testes, mocks, ou trocar de banco." |
| "E se alguém editar o HTML pra mandar uma situação inválida?" | "O Spring rejeita: o campo é tipado como enum Situacao, e qualquer string que não seja EM_PRODUCAO/PUBLICADA/CANCELADA causa erro de binding antes mesmo de chegar ao controller. Mesmo princípio do @Id ser String — o framework valida na conversão." |
| "Por que UUID e não ObjectId do Mongo?" | "Seguimos o padrão usado pelo reference do professor — UUID gerado na aplicação é portável (não depende do Mongo) e legível como string. Funciona porque o @Id aceita qualquer tipo serializável." |

---

## Dicas pra gravação

- **Falar devagar e pausar entre blocos** — vídeo permite recortar pausas longas, mas não acelerar fala atropelada.
- **Apontar o cursor / destacar** o código que está sendo discutido — quem assiste não acompanha sem isso.
- **Errou? Não recomeça do zero** — só pausa, respira, e refaz o trecho. Edita depois.
- **Encurtar se passar dos 10 min** — o bloco 9 (demo) é o primeiro candidato a apertar, depois o 8 (templates) que repete conceitos.
