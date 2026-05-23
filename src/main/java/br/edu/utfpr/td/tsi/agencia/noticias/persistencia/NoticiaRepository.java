package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Situacao;

public interface NoticiaRepository extends MongoRepository<Noticia, String> {

	List<Noticia> findByAutorId(String autorId);

	List<Noticia> findByAssuntoId(String assuntoId);

	List<Noticia> findBySituacao(Situacao situacao);
}
