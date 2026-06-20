package br.edu.utfpr.td.tsi.agencia.noticias.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.jetty.HttpJettySolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

	@Value("${solr.url}")
	private String solrUrl;

	@Value("${solr.core}")
	private String solrCore;

	@Bean
	public SolrClient solrClient() {
		return new HttpJettySolrClient.Builder(solrUrl)
				.withDefaultCollection(solrCore)
				.build();
	}
}
