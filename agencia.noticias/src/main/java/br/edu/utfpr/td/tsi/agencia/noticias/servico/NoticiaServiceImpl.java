package br.edu.utfpr.td.tsi.agencia.noticias.servico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.noticias.indice.IndiceNoticia;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Situacao;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.NoticiaRepository;

@Service
public class NoticiaServiceImpl implements NoticiaService {

	private static final int LIMITE_POR_ASSUNTO_DIA = 2;

	private final NoticiaRepository noticiaRepository;
	private final IndiceNoticia indiceNoticia;

	public NoticiaServiceImpl(NoticiaRepository noticiaRepository, IndiceNoticia indiceNoticia) {
		this.noticiaRepository = noticiaRepository;
		this.indiceNoticia = indiceNoticia;
	}

	@Override
	public List<Noticia> listarTodas() {
		return noticiaRepository.findAll();
	}

	@Override
	public List<Noticia> listarPorAutor(String autorId) {
		return noticiaRepository.findByAutorId(autorId);
	}

	@Override
	public List<Noticia> listarPorAssunto(String assuntoId) {
		return noticiaRepository.findByAssuntoId(assuntoId);
	}

	@Override
	public List<Noticia> listarPorSituacao(Situacao situacao) {
		return noticiaRepository.findBySituacao(situacao);
	}
	
	@Override
	public List<Noticia> buscarPorConteudo(String termo) {
		List<String> ids = indiceNoticia.buscarIdsPorTermo(termo);
		if (ids.isEmpty()) {
			return new ArrayList<>();
		}
		List<Noticia> noticias = new ArrayList<>();
		noticiaRepository.findAllById(ids).forEach(noticias::add);
		return noticias;
	}

	@Override
	public Optional<Noticia> buscarPorId(String id) {
		return noticiaRepository.findById(id);
	}

	@Override
	public void cadastrar(Noticia noticia) {
		LocalDate hoje = LocalDate.now();
		int quantidade = noticiaRepository.countByAutorIdAndAssuntoIdAndData(
				noticia.getAutorId(), noticia.getAssuntoId(), hoje);
		if (quantidade >= LIMITE_POR_ASSUNTO_DIA) {
			throw new RegraNegocioException("Limite atingido: este autor ja cadastrou 2 noticias deste assunto hoje.");
		}
		noticia.setId(UUID.randomUUID().toString());
		noticia.setData(hoje);
		noticiaRepository.save(noticia);
		indiceNoticia.indexar(noticia);
	}

	@Override
	public void editar(Noticia noticia) {
		Optional<Noticia> existente = noticiaRepository.findById(noticia.getId());
		existente.ifPresent(atual -> noticia.setData(atual.getData()));
		noticiaRepository.save(noticia);
		indiceNoticia.indexar(noticia);
	}

	@Override
	public void remover(String id) {
		noticiaRepository.deleteById(id);
		indiceNoticia.remover(id);
	}
}
