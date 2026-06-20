package br.edu.utfpr.td.tsi.agencia.noticias.servico;

import java.util.List;
import java.util.Optional;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;

public interface AssuntoService {

	List<Assunto> listarTodos();

	Optional<Assunto> buscarPorId(String id);

	void cadastrar(Assunto assunto);

	void editar(Assunto assunto);

	void remover(String id);
}
