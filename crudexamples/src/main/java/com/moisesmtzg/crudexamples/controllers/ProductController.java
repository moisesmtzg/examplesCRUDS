package com.moisesmtzg.crudexamples.controllers;

import com.moisesmtzg.crudexamples.entities.Product;
import com.moisesmtzg.crudexamples.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll(){
        System.out.println("trying to fetch all products");
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id){
        System.out.println("trying to fetch product with id:" + id);
        return productService.findProduct(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id){
        System.out.println("trying to delete product with id:" + id);
        productService.deleteProduct(id);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product newProduct){
        System.out.println("trying to add new product");
        return productService.saveProduct(newProduct);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

    @PatchMapping("{id}")
    public Product partialUpdateProduct(@PathVariable long id, @RequestBody Map<String, Object> valuesToUpdate){
        return productService.patchProduct(id, valuesToUpdate);
    }
}
