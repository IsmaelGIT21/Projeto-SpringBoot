package br.edu.utfpr.td.tsi.agencia.noticias.config;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

	private static final String NOME_BANCO = "agencia-noticias";

	@Bean
	public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
		return new SimpleMongoClientDatabaseFactory(mongoClient, NOME_BANCO);
	}
}
