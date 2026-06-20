package br.edu.utfpr.td.tsi.agencia.noticias.indice;

import java.util.List;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;

public interface IndiceNoticia {

	void indexar(Noticia noticia);

	void remover(String id);

	List<String> buscarIdsPorTermo(String termo);
}
