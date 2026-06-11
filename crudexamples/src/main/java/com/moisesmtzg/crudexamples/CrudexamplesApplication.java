package com.moisesmtzg.crudexamples;

import com.moisesmtzg.crudexamples.entities.Product;
import com.moisesmtzg.crudexamples.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class CrudexamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudexamplesApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            System.out.println("====== Insertando productos de prueba ======");

            Product p1 = new Product();
            p1.setNombre("Laptop Gamer");
            p1.setDescription("16GB RAM, 512GB SSD");
            p1.setPrecio(1200.50);

            Product p2 = new Product();
            p2.setNombre("Mouse Óptico");
            p2.setDescription("Inalámbrico, 4000 DPI");
            p2.setPrecio(45.99);

            repository.save(p1);
            repository.save(p2);

            System.out.println("====== ¡Productos guardados con éxito! ======");
        };
    }
}
