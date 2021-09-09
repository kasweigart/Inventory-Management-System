package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the Main Form.
 */
public class MainFormController implements Initializable {

    /**
     * Two static integers that are incremented to generate unique ID numbers.
     * Variables for the search text fields, tables, and buttons.
     */
    public static int generatedPartId = 14;
    public static int generatedProductId = 14;
    public Button exit;
    public Button deletePart;
    public TextField searchPart;
    public TextField searchProduct;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Double> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Integer> partPriceCostPerUnitColumn;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Integer> productPriceCostPerUnitColumn;

    /**
     * Function returns a static integer as a part ID.
     * @param id
     * @return
     */
    public static int generatePartId(int id) {
        id = id + 1;
        return id;
    }

    /**
     * Function returns a static integer as a product ID.
     * @param id static integer
     */
    public static int generateProductId(int id) {
        id = id + 1;
        return id;
    }

    /**
     * Initialize function that gets all the current parts and products in the inventory.
     * Initializes the parts and products tables with their respective columns.
     * @param url url as first parameter
     * @param resourceBundle resource bundle as second parmeter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Initialized");

        partsTableView.setItems(Inventory.getAllParts());
        productsTableView.setItems(Inventory.getAllProducts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * Event handler to set the scene on the stage for the add part form.
     * @param actionEvent clicking the add button
     * @throws IOException exception is produced if an error were to occur
     */
    public void toAddPartForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event handler to set the scene on the stage for the modify part form.
     * @param actionEvent clicking the modify button
     * @throws IOException exception is produced if an error were to occur
     */
    public void toModifyPartForm(ActionEvent actionEvent) throws IOException {
        try {
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            ModifyPartFormController.receivePart(selectedPart);
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please select a part from the list to modify.");
            alert.showAndWait();
        }
    }

    /**
     * Event handler to set the scene on the stage for the add product form.
     * @param actionEvent clicking the add button
     * @throws IOException exception is produced if an error were to occur
     */
    public void toAddProductForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event handler to set the scene on the stage for the modify product form.
     * @param actionEvent clicking the modify button
     * @throws IOException exception is produced if an error were to occur
     */
    public void toModifyProductForm(ActionEvent actionEvent) throws IOException {
        try {
            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            ModifyProductFormController.receiveProduct(selectedProduct);
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please select a product from the list to modify.");
            alert.showAndWait();
        }
    }

    /**
     * Event handler for exiting the program.
     * @param actionEvent clicking the exit button
     */
    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Event handler that takes in a search query and attempts to match it to a part.
     * @param actionEvent clicking the search button
     */
    public void onSearchPart(ActionEvent actionEvent) {
        String q = searchPart.getText();

        ObservableList<Part> parts = Inventory.lookupPartName(q);

        try {
            if (parts.size() == 0) {
                int id = Integer.parseInt(q);
                Part part = Inventory.lookupPartId(id);
                if (part != null) {
                    parts.add(part);
                }
            }
            partsTableView.setItems(parts);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Part");
            alert.setContentText("No matches found.");
            alert.showAndWait();
        }
    }

    /**
     * Event handler that takes in a search query and attempts to match it to a product.
     * @param actionEvent clicking the search button
     */
    public void onSearchProduct(ActionEvent actionEvent) {
        String q = searchProduct.getText();

        ObservableList<Product> products = Inventory.lookupProductName(q);

        try {
            if (products.size() == 0) {
                int id = Integer.parseInt(q);
                Product product = Inventory.lookupProductId(id);
                if (product != null) {
                    products.add(product);
                }
            }
            productsTableView.setItems(products);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Product");
            alert.setContentText("No matches found.");
            alert.showAndWait();
        }
    }

    /**
     * Event handler for deleting a part.
     * Confirmation alert is displayed before the delete request is fulfilled.
     * @param actionEvent clicking the delete button
     */
    public void onDeletePart(ActionEvent actionEvent) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the part with ID: " + selectedPart.getId()
                    + "?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Delete Part");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                partsTableView.getItems().remove(selectedPart);
                Inventory.deletePart(selectedPart);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please select a part from the list to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Event handler for deleting a product.
     * Confirmation alert is displayed before the delete request is fulfilled.
     * @param actionEvent clicking the delete button
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (selectedProduct.getAllAssociatedParts().size() != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Forbidden Request");
                alert.setContentText("You may not delete a Product with associated Parts.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the product with ID: " + selectedProduct.getId()
                        + "?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Delete Product");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    productsTableView.getItems().remove(selectedProduct);
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please select a product from the list to delete.");
            alert.showAndWait();
        }
    }
}

