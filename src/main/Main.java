package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * JavaDocs folder is located directly in the folder after the zip file is unzipped.
 */

/**
 * FUTURE ENHANCEMENT
 * Create a separate class for all search methods/members.
 * This will greatly reduce code redundancy and speed up development times.
 */

/**
 * This is the main class for the program.
 */
public class Main extends Application {

    /**
     * Main function in the starting class.
     * @param args String args is passed into the main function and then passed to the launch method.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start function begins the program.
     * @param primaryStage first stage of the program is set
     * @throws Exception exception is produced if an error were to occur
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        insertTestEntries();

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setTitle("Home");
        primaryStage.setScene(new Scene(root, 850, 400));
        primaryStage.show();
    }

    /**
     * Function that determines whether the input is valid.
     * @param stock inventory stock for a part or product
     * @param min minimum stock for a part or product
     * @param max maximum stock for a part or product
     * @return returns a boolean, true or false
     */
    public boolean validInput(int stock, int min, int max) {
        if (min < max && stock > min && stock < max) {
            return true;
        } else return false;
    }

    /**
     * This function creates several test data objects and is called above in the start function.
     */
    void insertTestEntries() {
        Part wing = new In_House(10, "Wing", 27741.10, 56, 35, 90, 599);
        Inventory.addPart(wing);

        Part winglet = new Outsourced(11, "Winglet", 55312.74, 47, 20, 60, "Boeing");
        Inventory.addPart(winglet);

        Part turbineEngine = new Outsourced(12, "Turbine Engine", 2893514.65, 14, 10, 50, "General Electric");
        Inventory.addPart(turbineEngine);

        Part fuselage = new In_House(13, "Fuselage", 43821.83, 33, 25, 120, 442);
        Inventory.addPart(fuselage);

        Part cockpit = new In_House(14, "Cockpit", 84044.07, 26, 10, 75, 292);
        Inventory.addPart(cockpit);


        Product boeing737 = new Product(10, "Boeing 737", 89380122.43, 7, 5, 10);
        Inventory.addProduct(boeing737);

        Product airbusA320 = new Product(11, "Airbus A320", 80573455.09, 6, 5, 10);
        Inventory.addProduct(airbusA320);

        Product boeing747 = new Product(12, "Boeing 747", 418651091.91, 4, 3, 6);
        Inventory.addProduct(boeing747);

        Product boeing777 = new Product(13,"Boeing 777", 421544035.26, 3, 3, 6);
        Inventory.addProduct(boeing777);

        Product airbusA330 = new Product(14, "Airbus A330", 241704305.55, 4, 3, 7);
        Inventory.addProduct(airbusA330);


        boeing737.addAssociatedPart(wing);
        boeing737.addAssociatedPart(winglet);

        airbusA320.addAssociatedPart(turbineEngine);
        airbusA320.addAssociatedPart(fuselage);

        boeing747.addAssociatedPart(cockpit);
        boeing747.addAssociatedPart(wing);

        boeing777.addAssociatedPart(turbineEngine);
        boeing777.addAssociatedPart(winglet);

        airbusA330.addAssociatedPart(fuselage);
        airbusA330.addAssociatedPart(cockpit);

    }
}
