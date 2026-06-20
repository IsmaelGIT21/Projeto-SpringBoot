package br.edu.utfpr.td.tsi.agencia.noticias.indice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;

@Repository
public class SolrIndiceNoticia implements IndiceNoticia {

	private final SolrClient solrClient;

	public SolrIndiceNoticia(SolrClient solrClient) {
		this.solrClient = solrClient;
	}

	@Override
	public void indexar(Noticia noticia) {
		try {
			SolrInputDocument documento = new SolrInputDocument();
			documento.addField("id", noticia.getId());
			documento.addField("conteudo", noticia.getConteudo());
			solrClient.add(documento);
			solrClient.commit();
		} catch (SolrServerException | IOException e) {
			throw new RuntimeException("Erro ao indexar a noticia no Solr", e);
		}
	}

	@Override
	public void remover(String id) {
		try {
			solrClient.deleteById(id);
			solrClient.commit();
		} catch (SolrServerException | IOException e) {
			throw new RuntimeException("Erro ao remover a noticia do indice Solr", e);
		}
	}

	@Override
	public List<String> buscarIdsPorTermo(String termo) {
		try {
			String[] termos = termo.trim().split("\\s+");
			StringBuilder textoConsulta = new StringBuilder("conteudo:(");
			
			for (int i = 0; i < termos.length; i++) {
				if (i > 0) {
					textoConsulta.append(" OR ");
				}
				
				textoConsulta.append(ClientUtils.escapeQueryChars(termos[i]));
			}
			
			textoConsulta.append(")");

			SolrQuery consulta = new SolrQuery();
			consulta.setQuery(textoConsulta.toString());
			consulta.setFields("id");
			consulta.setRows(1000);

			QueryResponse resposta = solrClient.query(consulta);

			List<String> ids = new ArrayList<>();
			for (SolrDocument documento : resposta.getResults()) {
				ids.add((String) documento.getFieldValue("id"));
			}
			
			return ids;
		
		} catch (SolrServerException | IOException e) {
			throw new RuntimeException("Erro ao consultar o indice Solr", e);
		}
	}
}
