package br.edu.utfpr.td.tsi.agencia.noticias.indice;

import java.util.List;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;

/**
 * Abstracao da camada de indexacao (padrao Repository/DAO) sobre o servico
 * externo de busca textual (Solr).
 *
 * Mantem no indice apenas o necessario para localizar a reportagem: o id (valor
 * de referencia) e o texto pesquisavel. A busca devolve os ids, que sao usados
 * para obter os dados completos no MongoDB.
 */
public interface IndiceNoticia {

	void indexar(Noticia noticia);

	void remover(String id);

	List<String> buscarIdsPorTermo(String termo);
}
