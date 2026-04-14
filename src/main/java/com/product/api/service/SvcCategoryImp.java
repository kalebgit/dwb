package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcCategoryImp implements SvcCategory {
    @Autowired
    RepoCategory repoCategory;

    @Override
    public List<Category> findAll() {
        try {
            return repoCategory.getCategories();
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findActive() {
        try {
            return repoCategory.getCategoriesByStatus(1);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void create(DtoCategoryIn in) {
        if (repoCategory.existsCategoryByCategory(in.getCategory())) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una categoría con ese nombre");
        } else if (repoCategory.existsCategoryByTag(in.getTag())) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una categoría con ese tag");
        }

        try {
            repoCategory.create(in.getCategory(), in.getTag());
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void update(DtoCategoryIn in, Integer id) {
        validateId(id);

        if (repoCategory.existsCategoryByCategory(in.getCategory())) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una categoría con ese nombre");
        } else if (repoCategory.existsCategoryByTag(in.getTag())) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una categoría con ese tag");
        }

        try {
            repoCategory.update(in.getCategory(), in.getTag(), id);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void enable(Integer id) {
        validateId(id);

        try {
            repoCategory.updateStatus(id, 1);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void disable(Integer id) {
        validateId(id);

        try {
            repoCategory.updateStatus(id, 0);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private void validateId(Integer id) {
        if (repoCategory.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "No existe una categoría con ese id");
        }
    }
}
