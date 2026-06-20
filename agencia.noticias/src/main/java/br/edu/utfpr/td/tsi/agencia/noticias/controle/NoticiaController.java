package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Situacao;
import br.edu.utfpr.td.tsi.agencia.noticias.servico.AssuntoService;
import br.edu.utfpr.td.tsi.agencia.noticias.servico.AutorService;
import br.edu.utfpr.td.tsi.agencia.noticias.servico.NoticiaService;
import br.edu.utfpr.td.tsi.agencia.noticias.servico.RegraNegocioException;

@Controller
public class NoticiaController {

	private final NoticiaService noticiaService;
	private final AutorService autorService;
	private final AssuntoService assuntoService;

	public NoticiaController(NoticiaService noticiaService, AutorService autorService,
			AssuntoService assuntoService) {
		this.noticiaService = noticiaService;
		this.autorService = autorService;
		this.assuntoService = assuntoService;
	}

	@GetMapping("/cadastrarNoticia")
	public String exibirPaginaCadastrarNoticia(Model model) {
		model.addAttribute("autores", autorService.listarTodos());
		model.addAttribute("assuntos", assuntoService.listarTodos());
		model.addAttribute("situacoes", Situacao.values());
		return "noticia/cadastrar";
	}

	@PostMapping("/cadastrarNoticia")
	public String cadastrarNoticia(Noticia noticia, Model model) {
		try {
			noticiaService.cadastrar(noticia);
			return "redirect:/listarNoticias";
		} catch (RegraNegocioException e) {
			model.addAttribute("erro", e.getMessage());
			model.addAttribute("noticia", noticia);
			model.addAttribute("autores", autorService.listarTodos());
			model.addAttribute("assuntos", assuntoService.listarTodos());
			model.addAttribute("situacoes", Situacao.values());
			return "noticia/cadastrar";
		}
	}

	@GetMapping("/listarNoticias")
	public String listarNoticias(@RequestParam(required = false) String autorId,
			@RequestParam(required = false) String assuntoId,
			@RequestParam(required = false) Situacao situacao,
			@RequestParam(required = false) String termo, Model model) {

		List<Noticia> noticias;
		if (termo != null && !termo.isBlank()) {
			noticias = noticiaService.buscarPorConteudo(termo);
		} else if (autorId != null && !autorId.isBlank()) {
			noticias = noticiaService.listarPorAutor(autorId);
		} else if (assuntoId != null && !assuntoId.isBlank()) {
			noticias = noticiaService.listarPorAssunto(assuntoId);
		} else if (situacao != null) {
			noticias = noticiaService.listarPorSituacao(situacao);
		} else {
			noticias = noticiaService.listarTodas();
		}

		Map<String, Autor> autoresMap = new HashMap<>();
		for (Autor a : autorService.listarTodos()) {
			autoresMap.put(a.getId(), a);
		}

		Map<String, Assunto> assuntosMap = new HashMap<>();
		for (Assunto a : assuntoService.listarTodos()) {
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
		model.addAttribute("filtroTermo", termo);

		return "noticia/listar";
	}

	@GetMapping("/editarNoticia")
	public String exibirPaginaEditarNoticia(@RequestParam String id, Model model) {
		Optional<Noticia> noticia = noticiaService.buscarPorId(id);
		if (noticia.isEmpty()) {
			return "redirect:/listarNoticias";
		}
		model.addAttribute("noticia", noticia.get());
		model.addAttribute("autores", autorService.listarTodos());
		model.addAttribute("assuntos", assuntoService.listarTodos());
		model.addAttribute("situacoes", Situacao.values());
		return "noticia/editar";
	}

	@PostMapping("/editarNoticia")
	public String editarNoticia(Noticia noticia) {
		noticiaService.editar(noticia);
		return "redirect:/listarNoticias";
	}

	@GetMapping("/removerNoticia")
	public String removerNoticia(@RequestParam String id) {
		noticiaService.remover(id);
		return "redirect:/listarNoticias";
	}
}
