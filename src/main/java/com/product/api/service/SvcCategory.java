package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface SvcCategory {
    public ResponseEntity<List<Category>> getCategories();
    public ResponseEntity<List<Category>> getActiveCategories();
    public ResponseEntity<Object> create(DtoCategoryIn in);
    public ResponseEntity<Object> update(DtoCategoryIn in, Integer id);
    public ResponseEntity<Object> enable(Integer id);
    public ResponseEntity<Object> disable(Integer id);
}
