package com.product.api.service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;

import java.util.List;

public interface SvcProductImage {
    List<ProductImage> getProductImages(Integer productId);
    void createProductImage(Integer productId, DtoProductImageIn in);
    void deleteProductImage(Integer productId, Integer productImageId);
}
