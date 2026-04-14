package com.product.api.repository;

import com.product.api.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoCategory extends JpaRepository<Category, Integer> {

    @Query(value="SELECT * FROM category ORDER BY category_id", nativeQuery = true)
    List<Category> getCategories();

    List<Category> getCategoriesByStatus(Integer status);

    boolean existsCategoryByCategory(String category);

    boolean existsCategoryByTag(String tag);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO category(category, tag, status) VALUES (:category, :tag, 1)",
    nativeQuery = true)
    void create(@Param("category") String category, @Param("tag") String tag);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE category SET category = :category, tag = :tag WHERE category_id = :id",
            nativeQuery = true)
    void update(@Param("category") String category, @Param("tag") String tag, @Param("id") Integer id);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE category SET status = :status WHERE category_id = :id",
            nativeQuery = true)
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);





}