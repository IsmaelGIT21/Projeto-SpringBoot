package br.edu.utfpr.td.tsi.agencia.noticias.modelo;

public enum Situacao {
	EM_PRODUCAO("Em produção"),
	PUBLICADA("Publicada"),
	CANCELADA("Cancelada");

	private final String descricao;

	Situacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
