package br.edu.utfpr.td.tsi.agencia.noticias.modelo;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entidade Autor (jornalista) — representa um autor de notícias.
 *
 * @Document(collection = "Autor") informa ao Spring Data MongoDB que
 * essa classe deve ser persistida na coleção "Autor". É o equivalente
 * NoSQL ao @Entity/@Table do JPA.
 */
@Document(collection = "Autor")
public class Autor {

	/**
	 * @Id marca o campo como identificador único do documento. O Mongo
	 * gera/usa esse valor como _id. Aqui geramos manualmente via UUID
	 * no controller (estilo do reference do professor).
	 */
	@Id
	private String id;

	private String nome;
	private String email;

	/**
	 * @DateTimeFormat: instrui o Spring MVC a converter a string
	 * "yyyy-MM-dd" recebida do <input type="date"> do form HTML em
	 * um LocalDate Java. Sem isso, o bind do form falha.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;

	// === Getters e Setters ===
	// Spring MVC usa-os para popular o objeto a partir dos campos do
	// formulário (databinding por nome de propriedade). Thymeleaf
	// também usa-os para ler valores na view (${autor.nome}).

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}
