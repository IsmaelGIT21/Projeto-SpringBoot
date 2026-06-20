package br.edu.utfpr.td.tsi.agencia.noticias.servico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AssuntoRepository;

@Service
public class AssuntoServiceImpl implements AssuntoService {

	private final AssuntoRepository assuntoRepository;

	public AssuntoServiceImpl(AssuntoRepository assuntoRepository) {
		this.assuntoRepository = assuntoRepository;
	}

	@Override
	public List<Assunto> listarTodos() {
		return assuntoRepository.findAll();
	}

	@Override
	public Optional<Assunto> buscarPorId(String id) {
		return assuntoRepository.findById(id);
	}

	@Override
	public void cadastrar(Assunto assunto) {
		assunto.setId(UUID.randomUUID().toString());
		assuntoRepository.save(assunto);
	}

	@Override
	public void editar(Assunto assunto) {
		assuntoRepository.save(assunto);
	}

	@Override
	public void remover(String id) {
		assuntoRepository.deleteById(id);
	}
}
