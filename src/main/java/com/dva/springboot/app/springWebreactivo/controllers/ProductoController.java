package com.dva.springboot.app.springWebreactivo.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.dva.springboot.app.springWebreactivo.model.dao.ProductoDao;
import com.dva.springboot.app.springWebreactivo.model.documents.Productos;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {
	
	public static final Logger LOG = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoDao productosDao;
	
	
	@GetMapping({"/listar", "/"})
	public String getAll(Model model) {
		Flux<Productos> productos = productosDao.findAll().map(p->{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		});
		productos.subscribe(prod -> LOG.info(prod.getNombre()));
		model.addAttribute("producto", productos);
		model.addAttribute("titulo", "Obteniendo los valores");
		return "listar";
	}
	
	
	@GetMapping({"/listar-dataDriven"})
	public String getAllDelay(Model model) {
		Flux<Productos> productos = productosDao.findAll().map(p->{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		}).delayElements(Duration.ofSeconds(1));
		
		productos.subscribe(prod -> LOG.info(prod.getNombre()));
		model.addAttribute("producto", new ReactiveDataDriverContextVariable(productos , 1));
		model.addAttribute("titulo", "Obteniendo los valores");
		return "listar";
	}
	
	@GetMapping({"/listar-chunked"})
	public String getAllChunked(Model model) {
		Flux<Productos> productos = productosDao.findAll().map(p->{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		}).delayElements(Duration.ofSeconds(1));
		
		productos.subscribe(prod -> LOG.info(prod.getNombre()));
		model.addAttribute("producto", new ReactiveDataDriverContextVariable(productos , 1));
		model.addAttribute("titulo", "Obteniendo los valores");
		return "listar-chunked";
	}
	
}
