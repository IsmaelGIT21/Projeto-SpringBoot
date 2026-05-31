package br.edu.utfpr.td.tsi.agencia.noticias.modelo;

/**
 * Enumeração das três situações possíveis de uma notícia, conforme o
 * enunciado do trabalho:
 *   - EM_PRODUCAO: notícia em fase de elaboração (rascunho).
 *   - PUBLICADA:   notícia já disponível ao público.
 *   - CANCELADA:   notícia descartada antes da publicação.
 *
 * Uso de enum garante que apenas esses três valores são aceitos —
 * o Spring converte automaticamente o nome do enum vindo do form
 * (string "PUBLICADA") para a constante correspondente.
 *
 * O campo 'descricao' armazena o rótulo formatado para exibir na UI
 * (com acento e espaço), evitando que a view tenha que mapear isso.
 */
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
