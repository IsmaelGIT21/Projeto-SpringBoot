package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Situacao;

/**
 * Repositório de Noticia — declara queries derivadas (derived queries).
 *
 * O Spring Data INTERPRETA o nome do método e gera a query
 * automaticamente. Exemplos:
 *   - findByAutorId(x)   → WHERE autorId = x
 *   - findBySituacao(x)  → WHERE situacao = x
 *
 * Sintaxe: findBy + NomeDoCampo (com inicial maiúscula). Nada de
 * SQL ou @Query — basta seguir a convenção e o Spring gera. Esse
 * recurso é exclusivo de Spring Data e poupa muito código de DAO.
 */
public interface NoticiaRepository extends MongoRepository<Noticia, String> {

	/** Retorna todas as notícias de um autor (usado no filtro da listagem). */
	List<Noticia> findByAutorId(String autorId);

	/** Retorna todas as notícias de um assunto (filtro da listagem). */
	List<Noticia> findByAssuntoId(String assuntoId);

	/** Retorna todas as notícias com determinada situação (filtro). */
	List<Noticia> findBySituacao(Situacao situacao);
}
