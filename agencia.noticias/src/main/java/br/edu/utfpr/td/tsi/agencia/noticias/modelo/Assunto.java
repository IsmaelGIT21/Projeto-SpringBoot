package br.edu.utfpr.td.tsi.agencia.noticias.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidade Assunto — categoria/tema ao qual cada notícia é associada.
 *
 * Persistido na coleção "Assunto" do MongoDB. O cadastro prévio de
 * assuntos é exigência do enunciado: notícias só podem ser criadas
 * referenciando um assunto que já exista.
 */
@Document(collection = "Assunto")
public class Assunto {

	@Id
	private String id;

	private String nome;
	private String descricao;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
