package com.dva.springboot.app.springWebreactivo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.dva.springboot.app.springWebreactivo.model.dao.ProductoDao;
import com.dva.springboot.app.springWebreactivo.model.documents.Productos;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringWebreactivoApplication implements CommandLineRunner {

	@Autowired
	private ProductoDao productoDao;
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringWebreactivoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringWebreactivoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		Flux.just(new Productos("Jugo del valle", 9.90), new Productos("Jugo del valle", 9.90),
				new Productos("Jugo del valle", 9.90), new Productos("Jugo del valle", 9.90))
		.flatMap(p -> {
			p.setCreateAt(new Date());
			return productoDao.save(p);
		})
		.subscribe(producto -> log.info(""+producto));
	}

}
