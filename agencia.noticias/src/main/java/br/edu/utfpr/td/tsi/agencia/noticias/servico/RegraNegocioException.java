package br.edu.utfpr.td.tsi.agencia.noticias.servico;

/**
 * Excecao usada para sinalizar a violacao de uma regra de negocio
 * (ex.: limite de noticias por assunto/dia).
 */
public class RegraNegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegraNegocioException(String mensagem) {
		super(mensagem);
	}
}
