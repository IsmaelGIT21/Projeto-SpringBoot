package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Situacao;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AssuntoRepository;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AutorRepository;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.NoticiaRepository;

@Controller
public class NoticiaController {

	@Autowired
	private NoticiaRepository noticiaRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private AssuntoRepository assuntoRepository;

	@GetMapping("/cadastrarNoticia")
	public String exibirPaginaCadastrarNoticia(Model model) {
		model.addAttribute("autores", autorRepository.findAll());
		model.addAttribute("assuntos", assuntoRepository.findAll());
		model.addAttribute("situacoes", Situacao.values());
		return "noticia/cadastrar";
	}

	@PostMapping("/cadastrarNoticia")
	public String cadastrarNoticia(Noticia noticia) {
		noticia.setId(UUID.randomUUID().toString());
		noticia.setData(LocalDate.now());
		noticiaRepository.save(noticia);
		return "redirect:/listarNoticias";
	}

	@GetMapping("/listarNoticias")
	public String listarNoticias(
			@RequestParam(required = false) String autorId,
			@RequestParam(required = false) String assuntoId,
			@RequestParam(required = false) Situacao situacao,
			Model model) {

		List<Noticia> noticias;
		if (autorId != null && !autorId.isBlank()) {
			noticias = noticiaRepository.findByAutorId(autorId);
		} else if (assuntoId != null && !assuntoId.isBlank()) {
			noticias = noticiaRepository.findByAssuntoId(assuntoId);
		} else if (situacao != null) {
			noticias = noticiaRepository.findBySituacao(situacao);
		} else {
			noticias = noticiaRepository.findAll();
		}

		Map<String, Autor> autoresMap = new HashMap<>();
		for (Autor a : autorRepository.findAll()) {
			autoresMap.put(a.getId(), a);
		}
		Map<String, Assunto> assuntosMap = new HashMap<>();
		for (Assunto a : assuntoRepository.findAll()) {
			assuntosMap.put(a.getId(), a);
		}

		model.addAttribute("noticias", noticias);
		model.addAttribute("autoresMap", autoresMap);
		model.addAttribute("assuntosMap", assuntosMap);
		model.addAttribute("autores", autoresMap.values());
		model.addAttribute("assuntos", assuntosMap.values());
		model.addAttribute("situacoes", Situacao.values());
		model.addAttribute("filtroAutorId", autorId);
		model.addAttribute("filtroAssuntoId", assuntoId);
		model.addAttribute("filtroSituacao", situacao);
		return "noticia/listar";
	}

	@GetMapping("/editarNoticia")
	public String exibirPaginaEditarNoticia(@RequestParam String id, Model model) {
		Optional<Noticia> noticia = noticiaRepository.findById(id);
		if (noticia.isEmpty()) {
			return "redirect:/listarNoticias";
		}
		model.addAttribute("noticia", noticia.get());
		model.addAttribute("autores", autorRepository.findAll());
		model.addAttribute("assuntos", assuntoRepository.findAll());
		model.addAttribute("situacoes", Situacao.values());
		return "noticia/editar";
	}

	@PostMapping("/editarNoticia")
	public String editarNoticia(Noticia noticia) {
		Optional<Noticia> existente = noticiaRepository.findById(noticia.getId());
		if (existente.isPresent()) {
			noticia.setData(existente.get().getData());
		}
		noticiaRepository.save(noticia);
		return "redirect:/listarNoticias";
	}

	@GetMapping("/removerNoticia")
	public String removerNoticia(@RequestParam String id) {
		noticiaRepository.deleteById(id);
		return "redirect:/listarNoticias";
	}
}
