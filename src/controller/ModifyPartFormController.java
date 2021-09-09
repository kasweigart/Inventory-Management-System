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


/**
 * This class is the controller for the Modify Part Form.
 */
public class ModifyPartFormController implements Initializable {

    /**
     * Private static data object for transfering data from scene to scene.
     * Variables for text fields, radio buttons, and labels.
     */
    private static Part partData = null;
    public RadioButton outsourced;
    public RadioButton inHouse;
    public TextField dependentField;
    public Label dependentLabel;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TextField idField;
    Main validInput = new Main();

    /**
     * Function that receives the part data from the main form controller.
     * @param part part passed which is then received
     */
    public static void receivePart(Part part) {
        partData = part;
    }

    /**
     * Initialize function sets the text fields to the selected part's class members.
     * Conditionals to decide whether the given part data is an instance of an in-house part or an outsourced part.
     * @param url url as first parameter
     * @param resourceBundle resource bundle as second parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setText(String.valueOf(partData.getId()));
        nameField.setText(partData.getName());
        invField.setText(String.valueOf(partData.getStock()));
        priceField.setText(String.valueOf(partData.getPrice()));
        maxField.setText(String.valueOf(partData.getMax()));
        minField.setText(String.valueOf(partData.getMin()));

        if (partData instanceof In_House) {
            dependentField.setPromptText("Machine ID");
            dependentLabel.setText("Machine ID");
            dependentField.setText(String.valueOf((((In_House) partData).getMachineId())));
        }

        if (partData instanceof Outsourced) {
            dependentLabel.setText("Company");
            dependentField.setPromptText("Company");
            dependentField.setText(((Outsourced) partData).getCompanyName());
        }

        System.out.println("Initialized");
    }

    /**
     * Event handler that sets assigns all text field input to variables.
     * Input must pass as valid input. The part then may be updated based on the input values.
     * @param actionEvent clicking the save button
     * @throws IOException exception is produced if an error were to occur
     */
    public void onClickSave(ActionEvent actionEvent) throws IOException {
        try {
            if (inHouse.isSelected()) {
                int partId = partData.getId();
                String name = nameField.getText();
                double price = Float.parseFloat(priceField.getText());
                int stock = Integer.parseInt(invField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                int machineId = Integer.parseInt(dependentField.getText());
                if (validInput.validInput(stock, min, max)) {
                    Part selectedPart = Inventory.lookupPartId(partId);
                    if (selectedPart instanceof In_House) {
                        selectedPart.setName(name);
                        selectedPart.setPrice(price);
                        selectedPart.setStock(stock);
                        selectedPart.setMax(max);
                        selectedPart.setMin(min);
                        ((In_House) selectedPart).setMachineId(machineId);
                        Inventory.updatePart(Inventory.getAllParts().indexOf(selectedPart), selectedPart);
                    }

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
                int partId = partData.getId();
                String name = nameField.getText();
                double price = Float.parseFloat(priceField.getText());
                int stock = Integer.parseInt(invField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                String companyName = dependentField.getText();
                if (validInput.validInput(stock, min, max)) {
                    Part selectedPart = Inventory.lookupPartId(partId);

                    if (selectedPart instanceof Outsourced) {
                        selectedPart.setName(name);
                        selectedPart.setPrice(price);
                        selectedPart.setStock(stock);
                        selectedPart.setMax(max);
                        selectedPart.setMin(min);
                        ((Outsourced) selectedPart).setCompanyName(companyName);
                        Inventory.updatePart(Inventory.getAllParts().indexOf(selectedPart), selectedPart);
                    }

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

    /**
     * Event handler to set the scene on the stage for the main form.
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
     * Event handler that changes the dependent field and label to an in-house part's machine ID.
     * @param actionEvent clicking the in-house radio button
     */
    public void onInHouse(ActionEvent actionEvent) {
        dependentField.setPromptText("Machine ID");
        dependentLabel.setText("Machine ID");
    }

    /**
     * Event handler that changes the dependent field and label to an outsourced part's company name.
     * @param actionEvent clicking the outsourced radio button
     */
    public void onOutsourced(ActionEvent actionEvent) {
        dependentLabel.setText("Company");
        dependentField.setPromptText("Company");
    }
}