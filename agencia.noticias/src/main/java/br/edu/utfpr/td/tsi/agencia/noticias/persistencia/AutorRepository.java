package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;

/**
 * Repositório de Autor — interface da camada de persistência (DAO).
 *
 * Ao estender MongoRepository<Autor, String>, herdamos GRÁTIS:
 *   - save(autor)          → INSERT ou UPDATE
 *   - findAll()            → lista todos
 *   - findById(id)         → busca por ID
 *   - deleteById(id)       → remove por ID
 *   - count(), existsById(), ...
 *
 * O Spring Data gera a implementação concreta em tempo de execução
 * via proxy dinâmico. Não precisamos criar AutorRepositoryImpl.
 *
 * Esse é o padrão DAO/Repository do Spring — uma das instâncias do
 * princípio IoC (a implementação não é instanciada por nós; o Spring
 * injeta automaticamente nos @Autowired dos controllers).
 *
 * <Autor, String>: Autor é a entidade gerenciada; String é o tipo do @Id.
 */
public interface AutorRepository extends MongoRepository<Autor, String> {

}
