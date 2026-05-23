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

/**
 * Controller do CRUD de Notícia — a entidade central do sistema.
 *
 * Depende de TRÊS repositórios (todos injetados via IoC):
 *   - NoticiaRepository: persistência da própria notícia
 *   - AutorRepository:   para popular o <select> de autor nos forms
 *   - AssuntoRepository: para popular o <select> de assunto
 *
 * Como Noticia guarda só autorId/assuntoId (referências), os
 * outros dois repos também são usados pra resolver os NOMES na
 * tela de listagem.
 */
@Controller
public class NoticiaController {

	@Autowired
	private NoticiaRepository noticiaRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private AssuntoRepository assuntoRepository;

	/**
	 * GET /cadastrarNoticia — exibe o formulário.
	 *
	 * Carrega autores e assuntos para popular os dropdowns, e
	 * passa o array de Situacao.values() para o select de situação.
	 */
	@GetMapping("/cadastrarNoticia")
	public String exibirPaginaCadastrarNoticia(Model model) {
		model.addAttribute("autores", autorRepository.findAll());
		model.addAttribute("assuntos", assuntoRepository.findAll());
		model.addAttribute("situacoes", Situacao.values());
		return "noticia/cadastrar";
	}

	/**
	 * POST /cadastrarNoticia — persiste a notícia.
	 *
	 * O Spring monta o objeto Noticia automaticamente, incluindo:
	 *   - autorId e assuntoId (vêm dos <select>)
	 *   - situacao (string convertida pro enum)
	 *
	 * Geramos UUID e setamos a data atual antes de salvar.
	 */
	@PostMapping("/cadastrarNoticia")
	public String cadastrarNoticia(Noticia noticia) {
		noticia.setId(UUID.randomUUID().toString());
		noticia.setData(LocalDate.now());
		noticiaRepository.save(noticia);
		return "redirect:/listarNoticias";
	}

	/**
	 * GET /listarNoticias — listagem com filtros opcionais.
	 *
	 * Aceita três query params (todos opcionais, mutuamente
	 * exclusivos por simplicidade): autorId, assuntoId, situacao.
	 *
	 * O 'required = false' faz com que o parâmetro seja nulo se
	 * não enviado, permitindo o "todas as notícias" como default.
	 *
	 * Também monta dois mapas (autoresMap, assuntosMap) indexados
	 * por id — a view usa-os para mostrar o NOME do autor/assunto
	 * em vez do ID na tabela. Isso é o "lookup" típico de modelos
	 * por referência.
	 */
	@GetMapping("/listarNoticias")
	public String listarNoticias(
			@RequestParam(required = false) String autorId,
			@RequestParam(required = false) String assuntoId,
			@RequestParam(required = false) Situacao situacao,
			Model model) {

		// Aplica filtro de acordo com o que foi enviado. Se nada
		// foi informado, lista todas.
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

		// Indexa autores e assuntos por ID. Custo: O(N+M) em memória,
		// mas evita N+1 (uma busca por noticia) na hora de renderizar.
		Map<String, Autor> autoresMap = new HashMap<>();
		for (Autor a : autorRepository.findAll()) {
			autoresMap.put(a.getId(), a);
		}
		Map<String, Assunto> assuntosMap = new HashMap<>();
		for (Assunto a : assuntoRepository.findAll()) {
			assuntosMap.put(a.getId(), a);
		}

		// Disponibiliza tudo para a view:
		model.addAttribute("noticias", noticias);
		model.addAttribute("autoresMap", autoresMap);     // lookup id→Autor
		model.addAttribute("assuntosMap", assuntosMap);   // lookup id→Assunto
		model.addAttribute("autores", autoresMap.values()); // pra dropdown de filtro
		model.addAttribute("assuntos", assuntosMap.values());
		model.addAttribute("situacoes", Situacao.values());
		// Mantém o valor selecionado nos dropdowns após o submit:
		model.addAttribute("filtroAutorId", autorId);
		model.addAttribute("filtroAssuntoId", assuntoId);
		model.addAttribute("filtroSituacao", situacao);
		return "noticia/listar";
	}

	/**
	 * GET /editarNoticia?id=... — exibe o form pré-preenchido.
	 */
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

	/**
	 * POST /editarNoticia — atualiza a notícia preservando a data
	 * original de criação (não queremos sobrescrever pela data
	 * atual só porque o usuário editou um título).
	 */
	@PostMapping("/editarNoticia")
	public String editarNoticia(Noticia noticia) {
		// Recupera a data de criação original do banco antes do save.
		Optional<Noticia> existente = noticiaRepository.findById(noticia.getId());
		if (existente.isPresent()) {
			noticia.setData(existente.get().getData());
		}
		noticiaRepository.save(noticia);
		return "redirect:/listarNoticias";
	}

	/**
	 * GET /removerNoticia?id=... — exclui a notícia.
	 */
	@GetMapping("/removerNoticia")
	public String removerNoticia(@RequestParam String id) {
		noticiaRepository.deleteById(id);
		return "redirect:/listarNoticias";
	}
}
