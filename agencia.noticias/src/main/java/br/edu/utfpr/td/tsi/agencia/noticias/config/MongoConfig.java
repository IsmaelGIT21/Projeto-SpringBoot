package br.edu.utfpr.td.tsi.agencia.noticias.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

	private static final String NOME_BANCO = "agencia-noticias";

	@Value("${spring.data.mongodb.uri}")
	private String uri;
	
	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(uri);
	}
	
	@Bean
	public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
		return new SimpleMongoClientDatabaseFactory(mongoClient, NOME_BANCO);
	}
}
