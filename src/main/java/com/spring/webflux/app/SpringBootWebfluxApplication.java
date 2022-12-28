package com.spring.webflux.app;

import com.spring.webflux.app.models.dao.ProductoDao;
import com.spring.webflux.app.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {
	@Autowired
	private ProductoDao productoDao;
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		Flux.just(new Producto("TV Panasonic Pantalla LCD", 750.99),
						new Producto("Sony Camara HD Digital", 750.99),
						new Producto("Apple iPod", 750.99),
						new Producto("Sony Notebook", 750.99),
						new Producto("Hewlett Packard Multifuncional", 750.99),
						new Producto("Bianchi Bicicleta", 750.99),
						new Producto("HP Notebook Omen 17", 750.99),
						new Producto("Mica Cómoda 5 Cajones", 750.99),
						new Producto("TV Sony Bravia OLED 4K Ultra HD", 750.99)
				)
				.flatMap(producto -> {
					producto.setCreateAt(new Date());
					return productoDao.save(producto); 
				})
				.subscribe( producto -> log.info("Insert: " + producto.getId() + " "+ producto.getNombre()));
	}
}
