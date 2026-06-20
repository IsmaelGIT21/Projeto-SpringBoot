package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;
import br.edu.utfpr.td.tsi.agencia.noticias.servico.AssuntoService;

@Controller
public class AssuntoController {

	private final AssuntoService assuntoService;

	public AssuntoController(AssuntoService assuntoService) {
		this.assuntoService = assuntoService;
	}

	@GetMapping("/cadastrarAssunto")
	public String exibirPaginaCadastrarAssunto() {
		return "assunto/cadastrar";
	}

	@PostMapping("/cadastrarAssunto")
	public String cadastrarAssunto(Assunto assunto) {
		assuntoService.cadastrar(assunto);
		return "redirect:/listarAssuntos";
	}

	@GetMapping("/listarAssuntos")
	public String listarAssuntos(Model model) {
		model.addAttribute("assuntos", assuntoService.listarTodos());
		return "assunto/listar";
	}

	@GetMapping("/editarAssunto")
	public String exibirPaginaEditarAssunto(@RequestParam String id, Model model) {
		Optional<Assunto> assunto = assuntoService.buscarPorId(id);
		
		if (assunto.isEmpty()) {
			return "redirect:/listarAssuntos";
		}
		
		model.addAttribute("assunto", assunto.get());
		return "assunto/editar";
	}

	@PostMapping("/editarAssunto")
	public String editarAssunto(Assunto assunto) {
		assuntoService.editar(assunto);
		return "redirect:/listarAssuntos";
	}

	@GetMapping("/removerAssunto")
	public String removerAssunto(@RequestParam String id) {
		assuntoService.remover(id);
		return "redirect:/listarAssuntos";
	}
}
