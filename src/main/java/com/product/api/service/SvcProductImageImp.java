package com.product.api.service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class SvcProductImageImp implements SvcProductImage {

    @Autowired
    RepoProductImage repo;

    @Autowired
    RepoProduct repoProduct;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.images}")
    private String uploadImages;

    @Override
    public List<ProductImage> getProductImages(Integer productId) {
        try {
            validateProductId(productId);
            return repo.findAllByProductId(productId);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void createProductImage(Integer productId, DtoProductImageIn in) {
        try {
            validateProductId(productId);

            List<ProductImage> existing = repo.findAllByProductId(productId);

            String imagePath;
            if (!existing.isEmpty()) {
                // Ya existe al menos una imagen: reutiliza el mismo directorio del producto
                String existingPath = existing.get(0).getImage();
                String existingDir = existingPath.substring(0, existingPath.lastIndexOf('/'));

                byte[] imageBytes = Base64.getDecoder().decode(in.getImage());
                String fileName = UUID.randomUUID().toString() + ".png";
                Path filePath = Paths.get(uploadDir, existingDir, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageBytes);

                imagePath = existingDir + "/" + fileName;
            } else {
                // Primera imagen: crea el directorio del producto
                byte[] imageBytes = Base64.getDecoder().decode(in.getImage());
                String fileName = UUID.randomUUID().toString() + ".png";
                Path filePath = Paths.get(uploadDir, uploadImages, String.valueOf(productId), fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageBytes);

                imagePath = "/" + uploadImages + "/" + productId + "/" + fileName;
            }

            ProductImage productImage = new ProductImage();
            productImage.setProductId(productId);
            productImage.setImage(imagePath);
            productImage.setStatus(1);
            repo.save(productImage);

        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void deleteProductImage(Integer productId, Integer productImageId) {
        try {
            validateProductId(productId);
            ProductImage productImage = repo.findById(productImageId)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "La imagen no existe"));

            if (!productImage.getProductId().equals(productId)) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "La imagen no pertenece al producto indicado");
            }

            productImage.setStatus(0);
            repo.save(productImage);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private void validateProductId(Integer productId) {
        if (repoProduct.findById(productId).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
        }
    }
}
