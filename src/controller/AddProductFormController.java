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

import static controller.MainFormController.generateProductId;
import static controller.MainFormController.generatedProductId;

/**
 * This class is the controller for the Add Product Form.
 */
public class AddProductFormController implements Initializable {

    /**
     * New observable array list for assigning parts to a product. Variables for various text fields and table columns.
     */
    Main validInput = new Main();
    private static final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
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
     * Initialize will assign columns to cells. The add parts table is populated with the current inventory.
     * The associated parts table is updated as parts are chosen from the add parts table.
     * @param url url as first parameter
     * @param resourceBundle resource bundle as second parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        addPartsTableView.setItems(Inventory.getAllParts());

        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(associatedParts);

        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        System.out.println("Initialized");
    }

    /**
     * Sets the scene on the stage as the main form.
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
     * Event handler that takes a query as an argument into a search function.
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
            addPartsTableView.setItems(parts);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Part");
            alert.setContentText("No matches found.");
            alert.showAndWait();
        }
    }
    /**
     * Event handler that allows a product to be added to the inventory.
     * @param actionEvent
     * @throws IOException exception is produced if an error were to occur
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        try {
            String name = nameField.getText();
            double price = Float.parseFloat(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());
            int id = generateProductId(generatedProductId);

            if (validInput.validInput(stock, min, max)) {
                Product product = new Product(id, name, price, stock, min, max);
                for (Part associatedPart : associatedParts) {
                    product.addAssociatedPart(associatedPart);
                }
                Inventory.addProduct(product);

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
     * Event handler that allows a part to be added to the associated parts list.
     * @param actionEvent clicking the add button
     * @throws IOException exception is produced if an error were to occur
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Part selectedPart = addPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedPartsTableView.getItems().add(selectedPart);
            selectedPart.setStock(selectedPart.getStock() - 1);
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
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
     * Event handler that allows a part to be removed from the associated parts list.
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

                selectedPart.setStock(selectedPart.getStock() + 1);
            }
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
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
}