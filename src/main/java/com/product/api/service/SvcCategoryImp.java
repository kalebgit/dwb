package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcCategoryImp implements SvcCategory{
    @Autowired
    RepoCategory repoCategory;

    @Override
    public ResponseEntity<List<Category>> getCategories() {
        try {
            return new ResponseEntity<>(repoCategory.getCategories(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<List<Category>> getActiveCategories() {
        return new ResponseEntity<>(repoCategory.getCategoriesByStatus(1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> create(DtoCategoryIn in) {
        if(repoCategory.existsCategoryByCategory(in.getCategory())){
           throw new ApiException(HttpStatus.CONFLICT, "Ya existe un categoria con ese nombre");
        }else if (repoCategory.existsCategoryByTag(in.getTag())){
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una categoria con ese tag");
        }

        try {
            repoCategory.create(in.getCategory(), in.getTag());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataAccessException e ){
            throw new DBAccessException(e);
        }

    }

    @Override
    public ResponseEntity<?> update(DtoCategoryIn in, Integer id) {

        validateId(id);

        if(repoCategory.existsCategoryByCategory(in.getCategory())){
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe un categoria con ese nombre, ingresa otros valores para actualizar");
        }else if (repoCategory.existsCategoryByTag(in.getTag())){
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe un categoria con ese tag, ingresa otros valores para actualizar");
        }

        try {
            repoCategory.update(in.getCategory(), in.getTag(), id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DataAccessException e ){
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<Object> enable(Integer id) {
        validateId(id);

        try {
            repoCategory.updateStatus(id, 1);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DataAccessException e ){
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<Object> disable(Integer id) {
        validateId(id);

        try {
            repoCategory.updateStatus(id, 0);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DataAccessException e ){
            throw new DBAccessException(e);
        }
    }


    public void validateId(Integer id){
        if(repoCategory.findById(id).isEmpty()){
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la category no existe");
        }
    }
}
