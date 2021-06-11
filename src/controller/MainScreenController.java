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
        import java.util.ResourceBundle;

/**This class  is the controller for the main screen view.
 * This class contains all the methods that are called when the user navigates with the buttons on the main screen.
 * @author Greg Newby (959900)
 */
public class MainScreenController implements Initializable {

    private Inventory inv;
    private Stage stage;
    private DecimalFormat df = new DecimalFormat(".00");

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();

    /**This is a default constuctor. */
    public MainScreenController() {}

    /**This contructor takes an inventory as input and sets it as this screens current inventory.
     * @param inv the inventory that is passed to this class and set to the screens current inventory
     */
    public MainScreenController(Inventory inv){
    this.inv = inv;
    }

    /**This method initializes the MainScreenController class.
     * The method calls two functions to generate the parts table and the products table
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        generatePartsTable();
        generateProductsTable();
    }

    /**This method generates all the products in inventory to fill the second tableview.
     */
    private void generateProductsTable() {
        productInventory.setAll(inv.getAllProducts());

        //   prodTbl.getColumns().addAll();
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTbl.setItems(productInventory);
        prodTbl.refresh();
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


    @FXML
    private Button addPartBtn;

    @FXML
    private Button modPartBtn;

    @FXML
    private Button deletePartBtn;

    @FXML
    private TextField searchPartBar;

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
    private Button addProdBtn;

    @FXML
    private Button modProdBtn;

    @FXML
    private Button deleteProdBtn;

    @FXML
    private TextField searchProductBar;

    @FXML
    private TableView<Product> prodTbl;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productInv;

    @FXML
    private TableColumn<Product, Double> productPrice;

    @FXML
    private Button exitBtn;

    /**This method exits the program. */
    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    /**This method takes the input from the parts search bar and displays a list of parts that contain the searched string when enter is pressed.
     * If no part is found containing the string it then searches if the input in the search bar is one of the part's ID.
     *
     * RUNTIME ERROR: If nothing is found from both searches an error alert is displayed using a try catch block.
     */
    @FXML
    void onActionPartSearch(ActionEvent event) {
        try {
            //set partsInventorySearch to the list of objects that contain the string in the search bar
            partsInventorySearch.setAll(inv.lookupPart(searchPartBar.getText()));

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
            error.setContentText("Part cannot be found! " + nfe.getMessage());
            error.showAndWait();
        }

    }

    /**This method takes the input from the products search bar and displays a list of products that contain the searched string when enter is pressed.
     * If no product is found containing the string it then searches if the input in the search bar is one of the product's ID.
     *
     * RUNTIME ERROR: If nothing is found from both searches an error alert is displayed using a try catch block.
     */
    @FXML
    void onActionProductSearch(ActionEvent event) {

        productInventorySearch.setAll(inv.lookupProduct(searchProductBar.getText()));
        try {

            if (productInventorySearch.size() == 0) {
                int searchID = Integer.parseInt(searchProductBar.getText());

                for (Product product : inv.getAllProducts()) {
                    if (product.getId() == searchID)
                        productInventorySearch.add(product);
                }
            }


            prodTbl.setItems(productInventorySearch);
            prodTbl.refresh();
            searchProductBar.clear();
        }
        catch (NumberFormatException nfe){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("Product cannot be found!" + nfe.getMessage());
            error.showAndWait();
        }
    }

    /**This method deletes the selected part from inventory.
     * LOGICAL ERROR: If no part is selected the screen stays the same which is a logical error. The solution is to use an if/else statement
     * to display a warning error.
     *
     * A confirmation alert is then displayed to verify that the user still wishes to delete the part.
     */
    @FXML
    void onClickDeletePart(MouseEvent event) {

        if (partsTbl.getSelectionModel().getSelectedItem() != null){
            //  window are you sure you want to delete this item
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {
                Part deletedPart = partsTbl.getSelectionModel().getSelectedItem();
                inv.deletePart(deletedPart);
                generatePartsTable();
            }
        }
        // Warning box you must select a part to be deleted
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("You must select a part to delete");
            alert.showAndWait();
        }
    }

    /**This method deletes the selected product from inventory if the part does not have any associated parts.
     * LOGICAL ERROR: If no part is selected the screen stays the same which is a logical error. The solution is to use an if/else statement
     * to display a warning error.
     *
     * LOGICAL ERROR: If the selected part has associated part you should not be able to delete it. To implement this an if loop is used to
     * check if the selected products associated list is empty first. If this is true then the product can be deleted.
     *
     * A confirmation alert is then displayed to verify that the user still wishes to delete the part.
     */
    @FXML
    void onClickDeleteProduct(MouseEvent event) {
        if (prodTbl.getSelectionModel().getSelectedItem() != null) {
            if(prodTbl.getSelectionModel().getSelectedItem().getAssociatedParts().isEmpty()) {
                //  window are you sure you want to delete this item
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Product deletedProduct = prodTbl.getSelectionModel().getSelectedItem();
                    inv.deleteProduct(deletedProduct);
                    generateProductsTable();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("You cannot delete a product with associated parts");
                alert.showAndWait();
            }
        }
        // Warning box you must select a product to be deleted
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("You must select a product to delete");
            alert.showAndWait();
        }
    }


    /**This method takes the user to the add part screen and passes the current inventory. */
    @FXML
    void onClickGoToAddPart(MouseEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addPartView.fxml"));
        controller.AddPartController controller = new controller.AddPartController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Greg_Newby C482");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    /**This method takes the user to the add product screen and passes the current inventory. */
    @FXML
    void onClickGoToAddProd(MouseEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addProductView.fxml"));
        controller.AddProductController controller = new controller.AddProductController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Greg_Newby C482");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    /**This method modifies the selected part in inventory.
     * The method gets the selected parts index and passes that and the inventory to the modify part screen.
     *
     * LOGICAL ERROR: If no part is selected the screen stays the same which is a logical error. The solution is to use an if/else statement
     * to display a warning error.
     */
    @FXML
    void onClickGoToModPart(MouseEvent event) throws IOException {
        int selectedPartIndex = partsTbl.getSelectionModel().getSelectedIndex();

        if (partsTbl.getSelectionModel().getSelectedItem() != null) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyPartView.fxml"));
            controller.ModPartController controller = new controller.ModPartController(inv, selectedPartIndex);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Greg_Newby C482");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("You must select a part to modify");
            alert.showAndWait();
        }

    }

    /**This method modifies the selected product in inventory.
     * The method gets the selected product index and passes that and the inventory to the modify product screen.
     *
     * LOGICAL ERROR: If no product is selected the screen stays the same which is a logical error. The solution is to use an if/else statement
     * to display a warning error.
     */
    @FXML
    void onClickGoToModProd(MouseEvent event) throws IOException {
        int selectedProductIndex = prodTbl.getSelectionModel().getSelectedIndex();

        if(prodTbl.getSelectionModel().getSelectedItem() != null) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyProductView.fxml"));
            controller.ModProductController controller = new controller.ModProductController(inv, selectedProductIndex);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Greg_Newby C482");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("You must select a product to modify");
            alert.showAndWait();
        }

    }
}
