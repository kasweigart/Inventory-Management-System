package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class contains all current parts and products of the Inventory Management Program.
 */
public class Inventory {
    /**
     * Two observable array lists to hold all current parts and products.
     */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Function that finds a part in hte inventory.
     * @param partId takes in a part ID
     * @return returns a part
     */
    public static Part lookupPartId(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int index = 0; index < allParts.size(); index++) {
            Part part = allParts.get(index);
            if (part.getId() == partId) {
                return part;
            }
        }

        return null;
    }

    /**
     * Function that finds a product in the inventory.
     * @param productId takes in a product ID
     * @return returns a product
     */
    public static Product lookupProductId(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (int index = 0; index < allProducts.size(); index++) {
            Product product = allProducts.get(index);
            if (product.getId() == productId) {
                return product;
            }
        }

        return null;

    }

    /**
     * Function that finds part names.
     * @param name takes in a name
     * @return returns a list of named parts
     */
    public static ObservableList<Part> lookupPartName(String name) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part part : allParts) {
            if (part.getName().contains(name)) {
                namedParts.add(part);
            }
        }

        return namedParts;
    }

    /**
     * Function that finds product names
     * @param name takes in a name
     * @return returns a list of named products
     */
    public static ObservableList<Product> lookupProductName(String name) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product product : allProducts) {
            if (product.getName().contains(name)) {
                namedProducts.add(product);
            }
        }

        return namedProducts;
    }

    /**
     * Function that updates a part.
     * @param index position in part list
     * @param selectedPart part to be updated
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Function that udpates a product.
     * @param index position in product list
     * @param newProduct product to be updated
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Function that deletes a part.
     * @param selectedPart takes in a part
     * @return returns boolean, true or false
     */
    public static boolean deletePart(Part selectedPart) {
        int index;
        for (index = 0; index < allParts.size(); index++) {
            if (allParts.get(index).getId() == selectedPart.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function that deletes a product.
     * @param selectedProduct takes in a product
     * @return returns boolean, true or false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        int index;
        for (index = 0; index < allProducts.size(); index++) {
            if (allProducts.get(index).getId() == selectedProduct.getId()) {
                allProducts.remove(index);
                return true;
            }
        }
        return false;
    }

    /**
     * @return Getter method to return the parts list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return Getter method to return the products list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Function that adds a part to the parts list.
     * @param newPart takes in a part
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Function that adds a product to the products list.
     * @param newProduct takes in a product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
}