package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This is the model for the product class.
 */
public class Product {
    /**
     * An observable array list of parts and six variables are initialized.
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Six variables are used to build the product object.
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock product stock
     * @param min product minimum inventory
     * @param max product maximum inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param selectedAssociatedPart part chosen to be added to the associated parts list
     */
    public void addAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.add(selectedAssociatedPart);
    }

    /**
     * @param selectedAssociatedPart part chosen to be added to the associated parts list
     * @return returns boolean, true or false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * @return returns the associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * @return returns the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name sets the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return returns the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price sets the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return returns the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock sets the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return returns the minimum inventory level
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min sets the minimum inventory level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return returns the maximum inventory level
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max sets the maximum inventory level
     */
    public void setMax(int max) {
        this.max = max;
    }
}
