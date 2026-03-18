package com.product.api.controller;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CrlProduct {

    @Autowired
    SvcCategory svcCategory;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        return svcCategory.getCategories();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> getActiveCategories(){
        return svcCategory.getActiveCategories();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DtoCategoryIn in){
        return svcCategory.create(in);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DtoCategoryIn in, @PathVariable Integer id){
        return svcCategory.update(in, id);
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<?> enable(@PathVariable Integer id){
        return svcCategory.enable(id);
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<?> disable(@PathVariable Integer id){
        return svcCategory.disable(id);
    }

}
