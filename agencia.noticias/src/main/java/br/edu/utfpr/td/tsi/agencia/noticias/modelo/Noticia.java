package br.edu.utfpr.td.tsi.agencia.noticias.modelo;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "Noticia")
public class Noticia {

	@Id
	private String id;
	private String titulo;
	private String conteudo;
	private String autorId;
	private String assuntoId;
	private Situacao situacao;

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
