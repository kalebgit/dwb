package com.product.api.controller;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrlProduct {

    @Autowired
    SvcCategory svcCategory;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategories(){
        return svcCategory.getCategories();
    }
}
