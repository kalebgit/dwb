package kal.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Category> categories = new ArrayList<>();
        CrudCategory crud = new CrudCategory(categories);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String option = readLine(scanner, "Opción");
            if (option == null) continue;

            // en este caso lo que quise hacer es separar las funcionalidades
            // para que no quede tan encimado todo en un mismo metodo
            switch (option.trim()) {
                case "1" -> crud.getCategories();
                case "2" -> doCreateCategory(scanner, crud);
                case "3" -> doDeleteCategory(scanner, crud);
                case "4" -> {
                    System.out.println("Saliendo.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opción no válida. Elige 1, 2, 3 o 4.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Menú CRUD Categorías ---");
        System.out.println("1. Listar categorías (status=1)");
        System.out.println("2. Crear categoría");
        System.out.println("3. Eliminar categoría (marcar status=0)");
        System.out.println("4. Salir");
    }

    private static void doCreateCategory(Scanner scanner, CrudCategory crud) {
        try {
            String idStr = readLine(scanner, "category_id (número)");
            if (idStr == null) return;
            int categoryId = Integer.parseInt(idStr.trim());

            String categoryName = readLine(scanner, "nombre categoría");
            if (categoryName == null || categoryName.isBlank()) {
                System.out.println("Error: el nombre de categoría no puede estar vacío");
                return;
            }

            String tag = readLine(scanner, "tag");
            if (tag == null || tag.isBlank()) {
                System.out.println("Error: el tag no puede estar vacío");
                return;
            }

            String statusStr = readLine(scanner, "status (1=activa, 0=eliminada)");
            if (statusStr == null) return;
            int status = Integer.parseInt(statusStr.trim());
            if (status != 0 && status != 1) {
                System.out.println("Error: status debe ser 0 o 1");
                return;
            }

            Category c = new Category();
            c.category_id = categoryId;
            c.category = categoryName.trim();
            c.tag = tag.trim();
            c.status = status;
            crud.createCategory(c);
            //por si ponen algo no correcto o difernete a un numero
        } catch (NumberFormatException e) {
            System.out.println("Error: category_id y status deben ser números enteros. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al crear categoría: " + e.getMessage());
        }
    }

    private static void doDeleteCategory(Scanner scanner, CrudCategory crud) {
        try {
            String idStr = readLine(scanner, "category_id a eliminar");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr.trim());
            crud.deleteCategory(id);
        } catch (NumberFormatException e) {
            System.out.println("Error: category_id debe ser un número entero. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al eliminar: " + e.getMessage());
        }
    }

    private static String readLine(Scanner scanner, String prompt) {
        try {
            System.out.print(prompt + ": ");
            if (!scanner.hasNextLine()) return null;
            return scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error leyendo entrada: " + e.getMessage());
            return null;
        }
    }
}
