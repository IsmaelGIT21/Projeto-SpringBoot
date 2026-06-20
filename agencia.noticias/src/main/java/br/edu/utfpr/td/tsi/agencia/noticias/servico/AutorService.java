package br.edu.utfpr.td.tsi.agencia.noticias.servico;

import java.util.List;
import java.util.Optional;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;

public interface AutorService {

	List<Autor> listarTodos();

	Optional<Autor> buscarPorId(String id);

	void cadastrar(Autor autor);

	void editar(Autor autor);

	void remover(String id);
}
