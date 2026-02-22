package com.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrlProduct {

    private CrudCategory crudCategory = new CrudCategory();

    @GetMapping("/category")
    public Category[] getCategories(){
        return crudCategory.getCategories();
    }
}
