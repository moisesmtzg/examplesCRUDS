package com.moisesmtzg.crudexamples.services;

import com.moisesmtzg.crudexamples.entities.Product;
import com.moisesmtzg.crudexamples.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public void deleteProduct(long id){
        repository.deleteById(id);
    }

    public List<Product> getProducts(){
        return repository.findAll();
    }

    public Product saveProduct(Product newProduct){
        return repository.save(newProduct);
    }

    public Optional<Product> findProduct(long id){
        return repository.findById(id);
    }

    public Product updateProduct(long id, Product p){
         return repository.findById(id)
                .map(existingProduct ->{
                    existingProduct.setPrecio(p.getPrecio());
                    existingProduct.setNombre(p.getNombre());
                    existingProduct.setDescription(p.getDescription());
                    return repository.save(existingProduct);
                }).orElseGet(()->{
                     p.setId(id);
                     return repository.save(p);
                 });
    }

    public Product patchProduct(long id, Map<String, Object> mapValues){
        Product patchProduct = repository.findById(id).orElseThrow(() ->new RuntimeException("no product id matched"));
        mapValues.forEach((key, value) ->{
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field != null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, patchProduct, value);
            }
        });
        return repository.save(patchProduct);
    }
}
