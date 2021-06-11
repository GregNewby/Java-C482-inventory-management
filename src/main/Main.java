package main;

import controller.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/** This program creates a simple inventory management system for parts and products of a business.
 * The inventory system allows you to search, add, modify, and delete both parts and products. The program starts
 * with test data that must be deleted to use live time.
 *
 * JavaDocs are located in src\JavaDocs\index
 *
 * FUTURE ENHANCEMENT: In next versions there will be a field that differentiates the cost to obtain and the current selling price.
 * There will also be a column added to each table view to show the total cost in dollars in inventory.
 *
 * @author Greg Newby (959900)
 */
public class Main extends Application {

    Inventory inv = new Inventory();

    /** This is the start method.
     This method is brings up the first screen the user sees.
     @param primaryStage This is the primary stage.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //fill inventory with test data
        fillTestInv();
        //bring up main screen view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainScreenView.fxml"));
        controller.MainScreenController controller = new controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Greg_Newby C482");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**This method fills the inventory with test data.
     2 in-house and 2 outsouced parts are created. Then 2 products are created out of the 4 parts
     */
    private void fillTestInv() {
        //create and add Inhouse objects to inventory
        Part ih1 = new InHouse(1,"kickstand",10.99,55,5,100,1000);
        Part ih2 = new InHouse(2,"handle bars",15.00,44,3,100,1002);
        inv.addPart(ih1);
        inv.addPart(ih2);
        //create and add Outsourced objects to inventory
        Part os1 = new Outsourced(3,"leather seat", 22.00,33,5,100,"Louis Vuitton");
        Part os2 = new Outsourced(4,"tires", 22.00,22,5,100,"Michelin");
        inv.addPart(os1);
        inv.addPart(os2);
        //Create Products
        Product bike = new Product(1,"mongoose", 75.00,9,5,250);
        bike.addAssociatedPart(ih1);
        bike.addAssociatedPart(ih2);
        bike.addAssociatedPart(os1);
        bike.addAssociatedPart(os2);
        Product unicycle = new Product( 2,"unicycle", 99.00,9,5,100);
        unicycle.addAssociatedPart(os1);
        unicycle.addAssociatedPart(os2);
        unicycle.addAssociatedPart(ih1);
        unicycle.deleteAssociatedPart(ih1);
        //add Products to inventory
        inv.addProduct(bike);
        inv.addProduct(unicycle);

    }

    /**This is the main method. This method starts the JavaFX application.*/
    public static void main(String[] args) {launch(args);}
}
