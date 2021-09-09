package controller;

import javafx.collections.FXCollections;
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
import main.Main;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the Modify Product Form.
 */
public class ModifyProductFormController implements Initializable {
    /**
     * Static product object set to null, ready to be assigned an object.
     * Initialize text fields and table columns.
     */
    Main validInput = new Main();
    private static Product productData = null;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TextField idField;
    public TextField searchPart;
    @FXML
    private TableView<Part> addPartsTableView;
    @FXML
    private TableColumn<Part, Integer> addPartIDColumn;
    @FXML
    private TableColumn<Part, String> addPartNameColumn;
    @FXML
    private TableColumn<Part, Double> addPartInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Integer> addPartPriceCostPerUnitColumn;
    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Double> associatedPartInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartPriceCostPerUnitColumn;

    /**
     * Function that receives the product data from the main form controller.
     * @param product product passed which is then received
     */
    public static void receiveProduct(Product product) {
        productData = product;
    }

    /**
     * Initialize function sets the text fields to the selected part's class members.
     * @param url url as first parameter
     * @param resourceBundle resource bundle as second parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idField.setText(String.valueOf(productData.getId()));
        nameField.setText(productData.getName());
        invField.setText(String.valueOf(productData.getStock()));
        priceField.setText(String.valueOf(productData.getPrice()));
        maxField.setText(String.valueOf(productData.getMax()));
        minField.setText(String.valueOf(productData.getMin()));

        addPartsTableView.setItems(Inventory.getAllParts());

        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(productData.getAllAssociatedParts());

        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        System.out.println("Initialized");
    }

    /**
     * Event handler for setting scene on stage for the main form
     * @param actionEvent clicking the cancel button
     * @throws IOException exception is produced if an error were to occur
     */
    public void backToMainForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 400);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Event handler for removing associated parts from the table.
     * Confirmation alert is displayed before the remove request is fulfilled.
     * @param actionEvent clicking the remove button
     * @throws IOException exception is produced if an error were to occur
     */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) throws IOException {
        Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to remove the part with ID: " + selectedPart.getId()
                            + "?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Remove Part");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                associatedPartsTableView.getItems().remove(selectedPart);
                productData.deleteAssociatedPart(selectedPart);
                selectedPart.setStock(selectedPart.getStock() + 1);
            }
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please select a part from the list to remove.");
            alert.showAndWait();
        }
    }

    /**
     * Event handler for adding a part to the associated parts list.
     * @param actionEvent clicking the add button
     * @throws IOException exception is produced if an error were to occur
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Part selectedPart = addPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedPartsTableView.getItems().add(selectedPart);
            productData.addAssociatedPart(selectedPart);
            selectedPart.setStock(selectedPart.getStock() - 1);
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please select a part from the list to add.");
            alert.showAndWait();
        }
    }

    /**
     * RUNTIME ERROR
     * Testing found that incorrect text fields returned a NumberFormatException.
     * Error was solved through the use of a try/catch block to handle the exception and alert the user.
     * Event handler that saves a product to the inventory.
     * @param actionEvent clicking the save button
     * @throws IOException exception is produced if an error were to occur
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        try {
            int productId = productData.getId();
            String name = nameField.getText();
            double price = Float.parseFloat(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());

            if (validInput.validInput(stock, min, max)) {
                Product selectedProduct = Inventory.lookupProductId(productId);

                selectedProduct.setName(name);
                selectedProduct.setPrice(price);
                selectedProduct.setStock(stock);
                selectedProduct.setMax(max);
                selectedProduct.setMin(min);

                Inventory.updateProduct(Inventory.getAllProducts().indexOf(selectedProduct), selectedProduct);

                Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 850, 400);
                stage.setTitle("Home");
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setContentText("Please correct your input data. (Min < Inventory < Max)");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please input the correct data in all fields to save the Product.");
            alert.showAndWait();
        }

    }

    /**
     * Event handler that takes in a search query and attempts to match it to a part.
     * @param actionEvent exception is produced if an error were to occur
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
            addPartsTableView.setItems(parts);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Part");
            alert.setContentText("No matches found.");
            alert.showAndWait();
        }
    }
}

