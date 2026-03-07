package com.product.api.controller;

import com.product.CrudCategory;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrlProduct {

    @Autowired
    SvcCategory svcCategory;

    @GetMapping("/category")
    public Category[] getCategories(){
        return svcCategory.getCategories().toArray(new Category[0]);
    }
}
