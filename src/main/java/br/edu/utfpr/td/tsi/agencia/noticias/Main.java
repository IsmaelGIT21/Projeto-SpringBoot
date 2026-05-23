package br.edu.utfpr.td.tsi.agencia.noticias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação — ponto de entrada do Spring Boot.
 *
 * A anotação @SpringBootApplication combina três coisas:
 *   - @Configuration: marca a classe como fonte de bean definitions.
 *   - @EnableAutoConfiguration: Spring detecta o que está no classpath
 *     (web, mongodb, thymeleaf) e configura automaticamente.
 *   - @ComponentScan: varre o pacote atual e subpacotes procurando
 *     @Controller, @Repository, @Service, etc. para registrar como
 *     beans no contêiner de IoC.
 */
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		// SpringApplication.run inicializa o contêiner, registra todos
		// os beans (controllers, repositories) e sobe o Tomcat embutido.
		SpringApplication.run(Main.class, args);
	}
}
