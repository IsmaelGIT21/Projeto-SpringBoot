package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AutorRepository;

@Controller
public class AutorController {

	@Autowired
	private AutorRepository autorRepository;

	@GetMapping("/cadastrarAutor")
	public String exibirPaginaCadastrarAutor() {
		return "autor/cadastrar";
	}

	@PostMapping("/cadastrarAutor")
	public String cadastrarAutor(Autor autor) {
		autor.setId(UUID.randomUUID().toString());
		autorRepository.save(autor);
		return "redirect:/listarAutores";
	}

	@GetMapping("/listarAutores")
	public String listarAutores(Model model) {
		model.addAttribute("autores", autorRepository.findAll());
		return "autor/listar";
	}

	@GetMapping("/editarAutor")
	public String exibirPaginaEditarAutor(@RequestParam String id, Model model) {
		Optional<Autor> autor = autorRepository.findById(id);
		if (autor.isEmpty()) {
			return "redirect:/listarAutores";
		}
		model.addAttribute("autor", autor.get());
		return "autor/editar";
	}

	@PostMapping("/editarAutor")
	public String editarAutor(Autor autor) {
		autorRepository.save(autor);
		return "redirect:/listarAutores";
	}

	@GetMapping("/removerAutor")
	public String removerAutor(@RequestParam String id) {
		autorRepository.deleteById(id);
		return "redirect:/listarAutores";
	}
}
