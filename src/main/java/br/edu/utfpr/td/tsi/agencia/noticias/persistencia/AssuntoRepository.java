package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;

/**
 * Repositório de Assunto. Mesmo padrão do AutorRepository:
 * herda CRUD completo do MongoRepository e o Spring gera a
 * implementação em runtime.
 */
public interface AssuntoRepository extends MongoRepository<Assunto, String> {

}
