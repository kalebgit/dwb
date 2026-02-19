package kal.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrudCategory {

    private List<Category> categories;

    public CrudCategory(List<Category> categories) {
        this.categories = categories != null ? categories : new ArrayList<>();
    }

    public int getCategories() {
        if (categories == null || categories.isEmpty()) {
            System.out.println("No existen categorías registradas");
            return 1;
        }
        List<Category> activas = categories.stream().filter(cat -> cat != null && cat.status != null && cat.status == 1).toList();
        if (activas.isEmpty()) {
            System.out.println("Si hay categorías, pero no existen categorías registradas con status 1");
            return 1;
        }
        System.out.println("Estas son las categorías que tienen status 1:");
        activas.forEach(cat -> System.out.println("{" + cat.category_id + ",\"" + cat.category + "\",\"" + cat.tag + "\"," + cat.status + "}"));
        return 0;
    }

    public boolean createCategory(Category category) {
        try {
            if (category == null) {
                System.out.println("Error: la categoría no puede ser null");
                return false;
            }
            if (categories.stream().anyMatch(cat -> cat != null && (
                    Objects.equals(cat.category_id, category.category_id)
                            || Objects.equals(cat.category, category.category)
                            || Objects.equals(cat.tag, category.tag)))) {
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

    public int deleteCategory(Integer id) {
        try {
            if (id == null) {
                System.out.println("Error: el id no puede ser null");
                return -1;
            }
            boolean found = false;
            for (Category c : categories) {
                if (c != null && id.equals(c.category_id)) {
                    c.status = 0;
                    found = true;
                }
            }
            if (found) {
                System.out.println("Categoría marcada como eliminada (status=0)");
            } else {
                System.out.println("No se encontró categoría con id " + id);
            }
            return found ? 0 : -1;
        } catch (Exception e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
            return -1;
        }
    }
}