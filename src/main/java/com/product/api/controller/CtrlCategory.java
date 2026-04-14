package com.product.api.controller;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategoryImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    @Autowired
    SvcCategoryImp svcCategory;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return new ResponseEntity<>(svcCategory.findAll(), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> findActive() {
        return new ResponseEntity<>(svcCategory.findActive(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody DtoCategoryIn in) {
        svcCategory.create(in);
        return new ResponseEntity<>("La categoría ha sido registrada", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody DtoCategoryIn in, @PathVariable Integer id) {
        svcCategory.update(in, id);
        return new ResponseEntity<>("La categoría ha sido actualizada", HttpStatus.OK);
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Integer id) {
        svcCategory.enable(id);
        return new ResponseEntity<>("La categoría ha sido activada", HttpStatus.OK);
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Integer id) {
        svcCategory.disable(id);
        return new ResponseEntity<>("La categoría ha sido desactivada", HttpStatus.OK);
    }

}
