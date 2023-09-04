package com.dva.springboot.app.springWebreactivo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dva.springboot.app.springWebreactivo.model.dao.ProductoDao;
import com.dva.springboot.app.springWebreactivo.model.documents.Productos;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/rest")
public class RestController {

	public static final Logger LOG = LoggerFactory.getLogger(ProductoController.class);
	@Autowired
	private ProductoDao productosDao;
	
	@GetMapping
	public Flux<Productos> getAll(){
		
		Flux<Productos> productos = productosDao.findAll().map(p->{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		}).doOnNext(p -> LOG.info(p.getNombre()));
		
		return productos;
	}
	
	@GetMapping("/{id}")
	public Mono<Productos> getById(@PathVariable String id){
		Flux<Productos> proFlux = productosDao.findAll();
		Mono<Productos> producto = proFlux.filter(p -> p.getId().equals(id))
				.next().doOnNext(p -> LOG.info(p.getNombre()));
		//return productosDao.findById(id);
		return producto;
	}
	
}
