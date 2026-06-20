package br.edu.utfpr.td.tsi.agencia.noticias.servico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AutorRepository;

@Service
public class AutorServiceImpl implements AutorService {

	private final AutorRepository autorRepository;

	public AutorServiceImpl(AutorRepository autorRepository) {
		this.autorRepository = autorRepository;
	}

	@Override
	public List<Autor> listarTodos() {
		return autorRepository.findAll();
	}

	@Override
	public Optional<Autor> buscarPorId(String id) {
		return autorRepository.findById(id);
	}

	@Override
	public void cadastrar(Autor autor) {
		autor.setId(UUID.randomUUID().toString());
		autorRepository.save(autor);
	}

	@Override
	public void editar(Autor autor) {
		autorRepository.save(autor);
	}

	@Override
	public void remover(String id) {
		autorRepository.deleteById(id);
	}
}
