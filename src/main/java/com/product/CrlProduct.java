package com.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrlProduct {

    private CrudCategory crudCategory;

    @GetMapping("/category")
    public Category[] getCategories() {
        return crudCategory.getCategories();
    }
}
