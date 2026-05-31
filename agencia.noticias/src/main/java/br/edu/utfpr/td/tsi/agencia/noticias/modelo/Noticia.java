package br.edu.utfpr.td.tsi.agencia.noticias.modelo;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entidade Noticia — reportagem produzida por um autor sobre um assunto.
 *
 * Persistida na coleção "Noticia" do MongoDB.
 *
 * Modelagem por REFERÊNCIA, não por embarcamento:
 *   - Guardamos apenas autorId e assuntoId (strings), não os objetos.
 *   - Vantagem: se o nome do autor mudar, a alteração reflete em todas
 *     as notícias automaticamente (sem precisar atualizar cópias).
 *   - O lookup do nome do autor/assunto é feito no controller quando
 *     a listagem é montada.
 */
@Document(collection = "Noticia")
public class Noticia {

	@Id
	private String id;

	private String titulo;
	private String conteudo;

	/** Referência ao Autor (apenas o _id, não o documento inteiro). */
	private String autorId;

	/** Referência ao Assunto (apenas o _id). */
	private String assuntoId;

	/**
	 * Estado da notícia (EM_PRODUCAO, PUBLICADA, CANCELADA). O Mongo
	 * armazena o nome do enum como string.
	 */
	private Situacao situacao;

	/**
	 * Data de criação da notícia, preenchida automaticamente pelo
	 * controller no momento do cadastro (LocalDate.now()).
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getAutorId() {
		return autorId;
	}

	public void setAutorId(String autorId) {
		this.autorId = autorId;
	}

	public String getAssuntoId() {
		return assuntoId;
	}

	public void setAssuntoId(String assuntoId) {
		this.assuntoId = assuntoId;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
}
