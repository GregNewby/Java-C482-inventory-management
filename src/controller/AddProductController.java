package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**This class  is the controller for the add product view.
 * This class contains all the methods that are called when the user navigates with the buttons on the add product screen.
 * @author Greg Newby (959900)
 */
public class AddProductController implements Initializable {

    private Random randomId = new Random();
    private Inventory inv;
    private Stage stage;
    private DecimalFormat df = new DecimalFormat(".00");

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> ascPartInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();

    /**This is a default constructor. */
    public AddProductController() {}

    /**This contructor takes an inventory as input and sets it as this screens current inventory.
     * @param inv the inventory that is passed to this class and set to the screens current inventory
     */
    public AddProductController(Inventory inv) {
        this.inv = inv;
    }

    /**This method initializes the AddProductController class.
     * The method calls another method to generates all the parts to fill the first tableview.
     * The method then calls another method to generate a random integer for the product ID
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        generatePartsTable();
        generateProductID();
    }

    /**This method generates all the parts in inventory to fill the first tableview.
     */
    private void generatePartsTable() {
        partInventory.setAll(inv.getAllParts());

        //   partsTbl.getColumns().addAll();
        partId.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        partsTbl.setItems(partInventory);
        partsTbl.refresh();
    }

    /**This method generates a random integer from 1-1000.
     * The first integer is checked to see if there is another product with the same ID. If there is, it then chooses another integer until
     * until it finds an unused integer between 1-1000.
     */
    private void generateProductID(){
        int Number = 1+randomId.nextInt(100);

        for(Product product : inv.getAllProducts()){
            if(Number == product.getId()){
                generateProductID();
            }
            else{
                addProdId.setText(String.valueOf(Number));
            }
        }
    }


    @FXML
    private TextField addProdId;

    @FXML
    private TextField addProdName;

    @FXML
    private TextField addProdInv;

    @FXML
    private TextField addProdPrice;

    @FXML
    private TableView<Part> partsTbl;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partStock;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private Button addProdAddBtn;

    @FXML
    private Button addProdRemoveBtn;

    @FXML
    private Button addProdSaveBtn;

    @FXML
    private Button addProdCancelBtn;

    @FXML
    private TextField searchPartBar;

    @FXML
    private TableView<Part> addAscPartsTbl;

    @FXML
    private TableColumn<Part, Integer> ascPartId;

    @FXML
    private TableColumn<Part, String> ascPartName;

    @FXML
    private TableColumn<Part, Integer> ascPartInv;

    @FXML
    private TableColumn<Part, Double> ascPartPrice;

    @FXML
    private TextField addProdMax;

    @FXML
    private TextField addProdMin;

    /**This method takes the input from the search bar and displays a list of Parts that contain the searched string when enter is pressed.
     * If no part is found containing the string it then searches if the input in the search bar is one of the part's ID.
     *
     * RUNTIME ERROR: If nothing is found from both searches an error alert is displayed using a try catch block.
     */
    @FXML
    void onActionPartSearch(ActionEvent event) {
        //set partsInventorySearch to the list of objects that contain the string in the search bar
        partsInventorySearch.setAll(inv.lookupPart(searchPartBar.getText()));

        try {
            if (partsInventorySearch.size() == 0) {
                int searchID = Integer.parseInt(searchPartBar.getText());

                for (Part part : inv.getAllParts()) {
                    if (part.getPartId() == searchID)
                        partsInventorySearch.add(part);
                }
            }

            partsTbl.setItems(partsInventorySearch);
            partsTbl.refresh();
            searchPartBar.clear();
        }

        catch (NumberFormatException nfe){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("Part cannot be found!" + nfe.getMessage());
            error.showAndWait();
        }

    }

    /**This method adds the selected part to the associated parts table.
     * LOGICAL ERROR: If no part is selected the screen stays the same which is a logical error. The solution is to use an if/else statement
     * to display a warning error.
     *
     * LOGICAL ERROR: If the part has already been added to the associated parts list it should not be added again. This is resolved by using
     * if associated parts table does not contain selected part then add the selected part.
     */
    @FXML
    void onClickAddPartToAscParts(MouseEvent event) {
        if(partsTbl.getSelectionModel().getSelectedItem() != null) {
            if (!ascPartInventory.contains(partsTbl.getSelectionModel().getSelectedItem())) {
                ascPartInventory.add(partsTbl.getSelectionModel().getSelectedItem());
            }
            ascPartId.setCellValueFactory(new PropertyValueFactory<>("partId"));
            ascPartName.setCellValueFactory(new PropertyValueFactory<>("partName"));
            ascPartInv.setCellValueFactory(new PropertyValueFactory<>("partStock"));
            ascPartPrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
            addAscPartsTbl.setItems(ascPartInventory);
            addAscPartsTbl.refresh();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("You must select a part to add");
            alert.showAndWait();
        }
    }

    /**This method removes the selected part from the associated parts table.
     * LOGICAL ERROR: If no part is selected the screen stays the same which is a logical error. The solution is to use an if/else statement
     * to display a warning error.
     *
     * A confirmation alert is then displayed to verify that the user still wishes to remove the part.
     */
    @FXML
    void onClickRemoveAscPart(MouseEvent event) {
        if (addAscPartsTbl.getSelectionModel().getSelectedItem() != null){
            //  window are you sure you want to delete this item
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {
                Part removePart = addAscPartsTbl.getSelectionModel().getSelectedItem();
                ascPartInventory.remove(removePart);
                addAscPartsTbl.refresh();
            }
        }
        // Warning box you must select a part to be deleted
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("You must select a part to remove");
            alert.showAndWait();
        }
    }

    /**This method is called when the save button is pressed.
     * This method reads all of the text field input and saves a new product into inventory.
     *
     * LOGICAL ERROR: Stock should be between Min and Max. The solution is to first use an if loop to make sure that Min is less than Max. If
     * not display a warning alert. Then use an if loop to make sure that stock is less than or equal to Max and greater or equal to Min.
     * If not then display a warning alert.
     *
     * RUNTIME ERROR: If there is any missing information from the text fields or incorrect data types used you will get a NumberFormatException.
     * A try catch block is used to capture these situations and display an error alert.
     */
    @FXML
    void onClickSaveProd(MouseEvent event) throws IOException {
        try {
            int id = Integer.parseInt(addProdId.getText());
            String name = addProdName.getText();
            double price = Double.parseDouble(addProdPrice.getText());
            int stock = Integer.parseInt(addProdInv.getText());
            int min = Integer.parseInt(addProdMin.getText());
            int max = Integer.parseInt(addProdMax.getText());

            if (min <= max) {
                if (stock <= max && stock >= min) {
                    // Create new product using the text field inputs
                    Product newProduct = (new Product(id, name, price, stock, min, max));

                    // add all associated parts to the new product
                    for (Part part : ascPartInventory) {
                        newProduct.addAssociatedPart(part);
                    }

                    // add new product to inventory
                    inv.addProduct(newProduct);

                    //Go back to Main Screen
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainScreenView.fxml"));
                    controller.MainScreenController controller = new controller.MainScreenController(inv);
                    loader.setController(controller);
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Greg_Newby C482");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setContentText("Inv must be between Min and Max");
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Min must be less than Max");
                alert.showAndWait();
            }
        }
        catch(NumberFormatException nfe){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("Must enter only a valid value for all fields\n\tID (integer)\n\tName (string)\n\tInv (integer)\n\tPrice/Cost (double)\n\tMax (integer)\n\tMin (integer)");
            error.showAndWait();
        }
    }

    /**This method returns to the main screen view without adding the new product. */
    @FXML
    void onClickGoToMainView(MouseEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainScreenView.fxml"));
        controller.MainScreenController controller = new controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Greg_Newby C482");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

}
