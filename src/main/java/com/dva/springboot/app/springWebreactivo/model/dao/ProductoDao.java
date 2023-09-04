package com.dva.springboot.app.springWebreactivo.model.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.dva.springboot.app.springWebreactivo.model.documents.Productos;

public interface ProductoDao extends ReactiveMongoRepository<Productos, String> {

	
}
