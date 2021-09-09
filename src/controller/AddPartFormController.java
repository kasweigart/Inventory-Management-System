package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.In_House;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainFormController.generatePartId;
import static controller.MainFormController.generatedPartId;


/**
 * This class is the controller for the Modify Part Form.
 */
public class AddPartFormController implements Initializable {

    /**
     * Variables for the radio buttons and the text fields.
     */
    Main validInput = new Main();
    public RadioButton outsourced;
    public RadioButton inHouse;
    public TextField dependentField;
    public Label dependentLabel;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;

    /**
     * Initialize is called when the scene is set to the stage.
     * @param url url passed as first parameter
     * @param resourceBundle resource bundle passed as second parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Initialized");
    }

    /**
     * Function sets scene on stage as the main form.
     * @param actionEvent action received as input into event handler
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
     * Event handler that switches the label and text field to the member specific to the In_House class.
     * @param actionEvent clicking the in-house radio button
     */
    public void onInHouse(ActionEvent actionEvent) {
        dependentField.setPromptText("Machine ID");
        dependentLabel.setText("Machine ID");
    }

    /**
     * Event handler that switches the label and text field to the member specific to the Outsourced class.
     * @param actionEvent clicking the outsourced radio button
     */
    public void onOutsourced(ActionEvent actionEvent) {
        dependentLabel.setText("Company");
        dependentField.setPromptText("Company");
    }


    /**
     * Event handler to save an In_House part or an Outsourced part.
     * @param actionEvent clicking the save button
     * @throws IOException exception is produced if an error were to occur
     */
    public void onClickSave(ActionEvent actionEvent) throws IOException {
        try {
            if (inHouse.isSelected()) {
                String name = nameField.getText();
                double price = Float.parseFloat(priceField.getText());
                int stock = Integer.parseInt(invField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                int machineId = Integer.parseInt(dependentField.getText());
                int id = generatePartId(generatedPartId);

                if (validInput.validInput(stock, min, max)) {
                    Part inHousePart = new In_House(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(inHousePart);

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
            }

            if (outsourced.isSelected()) {
                String name = nameField.getText();
                double price = Float.parseFloat(priceField.getText());
                int stock = Integer.parseInt(invField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                String companyName = dependentField.getText();
                int id = generatePartId(generatedPartId);

                if (validInput.validInput(stock, min, max)) {
                    Part outsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(outsourcedPart);

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
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please input the correct data in all fields to save the Part.");
            alert.showAndWait();
        }

    }

}