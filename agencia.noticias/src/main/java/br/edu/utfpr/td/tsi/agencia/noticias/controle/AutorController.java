package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.servico.AutorService;

@Controller
public class AutorController {

	private final AutorService autorService;

	public AutorController(AutorService autorService) {
		this.autorService = autorService;
	}

	@GetMapping("/cadastrarAutor")
	public String exibirPaginaCadastrarAutor() {
		return "autor/cadastrar";
	}

	@PostMapping("/cadastrarAutor")
	public String cadastrarAutor(Autor autor) {
		autorService.cadastrar(autor);
		return "redirect:/listarAutores";
	}

	@GetMapping("/listarAutores")
	public String listarAutores(Model model) {
		model.addAttribute("autores", autorService.listarTodos());
		return "autor/listar";
	}

	@GetMapping("/editarAutor")
	public String exibirPaginaEditarAutor(@RequestParam String id, Model model) {
		Optional<Autor> autor = autorService.buscarPorId(id);
		if (autor.isEmpty()) {
			return "redirect:/listarAutores";
		}
		model.addAttribute("autor", autor.get());
		return "autor/editar";
	}

	@PostMapping("/editarAutor")
	public String editarAutor(Autor autor) {
		autorService.editar(autor);
		return "redirect:/listarAutores";
	}

	@GetMapping("/removerAutor")
	public String removerAutor(@RequestParam String id) {
		autorService.remover(id);
		return "redirect:/listarAutores";
	}
}
