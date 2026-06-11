package com.moisesmtzg.crudexamples.controllers;

import com.moisesmtzg.crudexamples.entities.Product;
import com.moisesmtzg.crudexamples.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Product controller that handles http requests
 */
@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll(){
        log.info("Fetching all products");
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id){
        log.info("Getting product by id:{}", id);
        return productService.findProduct(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id){
        log.info("Delete product with id:{}",id);
        productService.deleteProduct(id);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product newProduct){
        log.info("Saving product: {}", newProduct);
        return productService.saveProduct(newProduct);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product){
        log.info("Updating product with id:{}", id);
        return productService.updateProduct(id, product);
    }

    @PatchMapping("{id}")
    public Product partialUpdateProduct(@PathVariable long id, @RequestBody Map<String, Object> valuesToUpdate){
        log.info("Patching product with id:{}", id);
        return productService.patchProduct(id, valuesToUpdate);
    }
}
