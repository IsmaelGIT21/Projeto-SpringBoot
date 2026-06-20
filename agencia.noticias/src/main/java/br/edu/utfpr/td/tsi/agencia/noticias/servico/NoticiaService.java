package br.edu.utfpr.td.tsi.agencia.noticias.servico;

import java.util.List;
import java.util.Optional;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Situacao;

public interface NoticiaService {

	List<Noticia> listarTodas();

	List<Noticia> listarPorAutor(String autorId);

	List<Noticia> listarPorAssunto(String assuntoId);

	List<Noticia> listarPorSituacao(Situacao situacao);

	List<Noticia> buscarPorConteudo(String termo);

	Optional<Noticia> buscarPorId(String id);

	void cadastrar(Noticia noticia);

	void editar(Noticia noticia);

	void remover(String id);

	void reindexarTudo();
}
