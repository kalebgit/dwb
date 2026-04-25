package com.product.api.controller;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.service.SvcProductImageImp;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Image", description = "Carga de imágenes de productos")
@RestController
@RequestMapping("/product/{id}/image")
public class CtrlProductImage {

    @Autowired
    SvcProductImageImp svc;

    @GetMapping
    public ResponseEntity<List<ProductImage>> getProductImages(@PathVariable Integer id) {
        return new ResponseEntity<>(svc.getProductImages(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createProductImage(@PathVariable Integer id, @Valid @RequestBody DtoProductImageIn in) {
        svc.createProductImage(id, in);
        return new ResponseEntity<>("La imagen ha sido registrada", HttpStatus.CREATED);
    }

    @DeleteMapping("/{product-image-id}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Integer id, @PathVariable("product-image-id") Integer productImageId) {
        svc.deleteProductImage(id, productImageId);
        return new ResponseEntity<>("La imagen ha sido eliminada", HttpStatus.OK);
    }
}
