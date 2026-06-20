package br.edu.utfpr.td.tsi.agencia.noticias.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpJdkSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracao de Inversao de Controle (IoC) do cliente Solr.
 *
 * Expoe um {@link SolrClient} como bean gerenciado pelo Spring, apontando para
 * o servico de indexacao externo (Solr 10). A URL e o core sao lidos do
 * application.properties.
 */
@Configuration
public class SolrConfig {

	@Value("${solr.url}")
	private String solrUrl;

	@Value("${solr.core}")
	private String solrCore;

	@Bean
	public SolrClient solrClient() {
		return new HttpJdkSolrClient.Builder(solrUrl)
				.withDefaultCollection(solrCore)
				.build();
	}
}
