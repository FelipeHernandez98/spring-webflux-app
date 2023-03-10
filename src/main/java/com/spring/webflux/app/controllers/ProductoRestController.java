package com.spring.webflux.app.controllers;

import com.spring.webflux.app.models.dao.ProductoDao;
import com.spring.webflux.app.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private ProductoDao productoDao;

    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping
    public Flux<Producto> index(){
        Flux<Producto> productos = productoDao.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        });
        return productos;
    }

    @GetMapping("/{id}")
    public Mono<Producto> show(@PathVariable String id){

        // OPCIONES PARA HACER ESTA OPERACION DE BUSCAR POR ID

        // -- Flux<Producto> productos = productoDao.findAll();
        //Mono<Producto> prodcuto = productos.filter( p -> p.getId().equals(id)).next();
        //return producto;

        // -- Mono<Producto> producto = productoDao.findById(id);
        //return producto;

        return productoDao.findById(id);  // -> Retorna un Mono de producto directamente
    }
}
