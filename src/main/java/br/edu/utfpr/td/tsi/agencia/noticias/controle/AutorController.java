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

/**
 * Controller do CRUD de Autor (jornalista).
 *
 * Cada método aqui mapeia uma URL para uma operação. O fluxo padrão é:
 *   1. Browser faz request → Spring chama o método mapeado
 *   2. Método interage com o repository (DAO)
 *   3. Retorna nome de view OU "redirect:/outra-rota"
 */
@Controller
public class AutorController {

	/**
	 * Inversão de Controle (IoC) em ação: o Spring injeta uma
	 * implementação concreta de AutorRepository (gerada em runtime
	 * pelo Spring Data) sem que precisemos chamar 'new'. Isso
	 * desacopla a camada web da camada de persistência.
	 */
	@Autowired
	private AutorRepository autorRepository;

	/**
	 * GET /cadastrarAutor — exibe o formulário em branco.
	 * Resolve para templates/autor/cadastrar.html.
	 */
	@GetMapping("/cadastrarAutor")
	public String exibirPaginaCadastrarAutor() {
		return "autor/cadastrar";
	}

	/**
	 * POST /cadastrarAutor — recebe o formulário submetido.
	 *
	 * Spring monta o objeto Autor automaticamente a partir dos
	 * campos do form (nome, email, dataNascimento) via databinding
	 * por nome de propriedade.
	 *
	 * Geramos um UUID como ID antes de salvar (o Mongo aceita
	 * qualquer string como _id; o reference do professor faz assim).
	 *
	 * Após salvar, redirecionamos para a listagem (padrão PRG —
	 * Post/Redirect/Get — para evitar reenvio do form ao atualizar
	 * a página).
	 */
	@PostMapping("/cadastrarAutor")
	public String cadastrarAutor(Autor autor) {
		autor.setId(UUID.randomUUID().toString());
		autorRepository.save(autor);
		return "redirect:/listarAutores";
	}

	/**
	 * GET /listarAutores — exibe a tabela com todos os autores.
	 * Adiciona a lista ao Model para o Thymeleaf iterar via th:each.
	 */
	@GetMapping("/listarAutores")
	public String listarAutores(Model model) {
		model.addAttribute("autores", autorRepository.findAll());
		return "autor/listar";
	}

	/**
	 * GET /editarAutor?id=... — exibe o formulário pré-preenchido
	 * com os dados do autor. Se o ID não existir, redireciona pra
	 * lista (evita NullPointer na view).
	 */
	@GetMapping("/editarAutor")
	public String exibirPaginaEditarAutor(@RequestParam String id, Model model) {
		Optional<Autor> autor = autorRepository.findById(id);
		if (autor.isEmpty()) {
			return "redirect:/listarAutores";
		}
		model.addAttribute("autor", autor.get());
		return "autor/editar";
	}

	/**
	 * POST /editarAutor — persiste as alterações.
	 *
	 * O 'save' do MongoRepository funciona como upsert: se o ID já
	 * existe, faz UPDATE; se não, INSERT. Como o form envia o ID
	 * num campo hidden, cai no caminho do UPDATE.
	 */
	@PostMapping("/editarAutor")
	public String editarAutor(Autor autor) {
		autorRepository.save(autor);
		return "redirect:/listarAutores";
	}

	/**
	 * GET /removerAutor?id=... — exclui o autor pelo ID.
	 * deleteById é idempotente (não falha se o ID não existir).
	 */
	@GetMapping("/removerAutor")
	public String removerAutor(@RequestParam String id) {
		autorRepository.deleteById(id);
		return "redirect:/listarAutores";
	}
}
