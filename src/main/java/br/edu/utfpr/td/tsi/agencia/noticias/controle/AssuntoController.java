package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AssuntoRepository;

/**
 * Controller do CRUD de Assunto.
 *
 * Mesma estrutura do AutorController — cadastrar, listar, editar,
 * remover. Os assuntos devem ser cadastrados previamente para que
 * possam ser referenciados nas notícias (requisito do enunciado).
 */
@Controller
public class AssuntoController {

	// Injeção via IoC do Spring; ver detalhes em AutorController.
	@Autowired
	private AssuntoRepository assuntoRepository;

	@GetMapping("/cadastrarAssunto")
	public String exibirPaginaCadastrarAssunto() {
		return "assunto/cadastrar";
	}

	@PostMapping("/cadastrarAssunto")
	public String cadastrarAssunto(Assunto assunto) {
		// Gera ID único antes do INSERT (mesmo padrão usado em Autor).
		assunto.setId(UUID.randomUUID().toString());
		assuntoRepository.save(assunto);
		return "redirect:/listarAssuntos";
	}

	@GetMapping("/listarAssuntos")
	public String listarAssuntos(Model model) {
		model.addAttribute("assuntos", assuntoRepository.findAll());
		return "assunto/listar";
	}

	@GetMapping("/editarAssunto")
	public String exibirPaginaEditarAssunto(@RequestParam String id, Model model) {
		Optional<Assunto> assunto = assuntoRepository.findById(id);
		if (assunto.isEmpty()) {
			return "redirect:/listarAssuntos";
		}
		model.addAttribute("assunto", assunto.get());
		return "assunto/editar";
	}

	@PostMapping("/editarAssunto")
	public String editarAssunto(Assunto assunto) {
		assuntoRepository.save(assunto);
		return "redirect:/listarAssuntos";
	}

	@GetMapping("/removerAssunto")
	public String removerAssunto(@RequestParam String id) {
		assuntoRepository.deleteById(id);
		return "redirect:/listarAssuntos";
	}
}
