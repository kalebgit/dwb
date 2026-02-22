package com.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrudCategory {

    private List<Category> categories = new ArrayList<>(List.of(
            new Category(1, "Electronica", "electronica", 1),
            new Category(2, "Ropa", "ropa", 1),
            new Category(3, "Alimentos", "alimentos", 1),
            new Category(4, "Deportes", "deportes", 1),
            new Category(5, "Hogar", "hogar", 1),
            new Category(6, "Libros", "libros", 0),
            new Category(7, "Juguetes", "juguetes", 1),
            new Category(8, "Belleza", "belleza", 0)));;

    public CrudCategory(List<Category> categories) {
        this.categories = categories != null ? categories : new ArrayList<>();
    }

    public Category[] getCategories() {
        if (categories == null || categories.isEmpty()) {
            System.out.println("No existen categorías registradas");
            return null;
        }
        List<Category> activas = categories.stream().filter(cat -> cat != null && cat.getStatus() != null && cat.getStatus() == 1).toList();
        if (activas.isEmpty()) {
            System.out.println("Si hay categorías, pero no existen categorías registradas con status 1");
            return null;
        }
        System.out.println("Estas son las categorías que tienen status 1:");
        activas.forEach(cat -> System.out.println("{" + cat.getCategory_id() + ",\"" + cat.getCategory() + "\",\"" + cat.getTag() + "\"," + cat.getStatus() + "}"));
        return activas.toArray(Category[]::new);
    }

    public boolean createCategory(Category category) {
        try {
            if (category == null) {
                System.out.println("Error: la categoría no puede ser null");
                return false;
            }
            if (categories.stream().anyMatch(cat -> cat != null && (
                    Objects.equals(cat.getCategory_id(), category.getCategory_id())
                            || Objects.equals(cat.getCategory(), category.getCategory())
                            || Objects.equals(cat.getTag(), category.getTag())))) {
                System.out.println("Categoría ya existente: no puedes crear una categoría con el mismo id, nombre de categoría o tag");
                return false;
            }
            categories.add(category);
            System.out.println("Categoría creada correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error al crear categoría: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCategory(Integer id) {
        try {
            if (id == null) {
                System.out.println("Error: el id no puede ser null");
                return false;
            }
            boolean found = false;
            for (Category c : categories) {
                if (c != null && id.equals(c.getCategory_id())) {
                    c.status = 0;
                    found = true;
                }
            }
            if (found) {
                System.out.println("Categoría marcada como eliminada (status=0)");
            } else {
                System.out.println("No se encontró categoría con id " + id);
            }
            return found ? true : false;
        } catch (Exception e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }
}
