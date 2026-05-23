package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsável pela página inicial (home).
 *
 * @Controller marca a classe como bean MVC do Spring — ela é
 * detectada pelo @ComponentScan e registrada no contêiner de IoC.
 * Diferente de @RestController, retorna nomes de view (Thymeleaf)
 * e não JSON.
 */
@Controller
public class IndexController {

	/**
	 * Rota raiz da aplicação. Considerando o context-path
	 * /agencia.noticias, o endereço completo é:
	 *   http://localhost:9091/agencia.noticias/
	 *
	 * Retornar "index" instrui o Spring a renderizar o template
	 * src/main/resources/templates/index.html.
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}
}
