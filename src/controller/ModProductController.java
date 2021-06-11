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

/**This class  is the controller for the modify product view.
 * This class contains all the methods that are called when the user navigates with the buttons on the modify product screen.
 * @author Greg Newby (959900)
 */
public class ModProductController implements Initializable {


    private Inventory inv;
    private Product selectedProduct;
    private int selectedProductIndex;
    private Stage stage;
    private DecimalFormat df = new DecimalFormat(".00");

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> ascPartInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();

    /**This is a default constuctor. */
    public ModProductController() {}

    /**This contructor takes an inventory and the selected product's index as input.
     * @param inv the inventory that is passed to this class and set to the screens current inventory
     * @param selectedProductIndex the index of the selected product in inventory
     */
    public ModProductController(Inventory inv, int selectedProductIndex) {
        this.inv = inv;
        this.selectedProductIndex = selectedProductIndex;
        this.selectedProduct = inv.getAllProducts().get(selectedProductIndex);

    }

    /**This method initializes the ModProductController class.
     * The method reads the selected product and uses an if loop to distinguish which radial button should be selected and which
     * label should be displayed. Then all text fields are displayed with the current products values for each label.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        generatePartsTable();
        generateAscPartsTable();

        modProdId.setText(String.valueOf(selectedProduct.getId()));
        modProdName.setText(selectedProduct.getName());
        modProdInv.setText(String.valueOf(selectedProduct.getStock()));
        modProdPrice.setText(String.valueOf(df.format(selectedProduct.getPrice())));
        modProdMax.setText(String.valueOf(selectedProduct.getMax()));
        modProdMin.setText(String.valueOf(selectedProduct.getMin()));

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

    /**This method generates the second tableview using all the associated parts from the selected product.
     */
    private void generateAscPartsTable() {
        ascPartInventory.setAll(selectedProduct.getAssociatedParts());

        ascPartId.setCellValueFactory(new PropertyValueFactory<>("partId"));
        ascPartName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        ascPartInv.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        ascPartPrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        addAscPartsTbl.setItems(ascPartInventory);
        addAscPartsTbl.refresh();
    }

    @FXML
    private TextField modProdId;

    @FXML
    private TextField modProdName;

    @FXML
    private TextField modProdInv;

    @FXML
    private TextField modProdPrice;

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
    private Button modProdAddBtn;

    @FXML
    private Button modProdRemoveBtn;

    @FXML
    private Button modProdSaveBtn;

    @FXML
    private Button modProdCancelBtn;

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
    private TextField modProdMax;

    @FXML
    private TextField modProdMin;

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

    /**The method adds the selected part to the associated parts table.
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
     * This method reads all of the text field input and saves the modified product in the same spot in inventory.
     *
     * LOGICAL ERROR: Stock should be between Min and Max. The solution is to first use an if loop to make sure that Min is less than Max. If
     * not display a warning alert. Then use an if loop to make sure that stock is less than or equal to Max and greater or equal to Min.
     * If not then display a warning alert.
     *
     * RUNTIME ERROR: If there is any missing information from the text fields or incorrect data types used you will get a NumberFormatException.
     * A try catch block is used to capture these situations and display an error alert.
     */
    @FXML
    void onClickModifyProd(MouseEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modProdId.getText());
            String name = modProdName.getText();
            double price = Double.parseDouble(modProdPrice.getText());
            int stock = Integer.parseInt(modProdInv.getText());
            int min = Integer.parseInt(modProdMin.getText());
            int max = Integer.parseInt(modProdMax.getText());

            if (min <= max) {
                if (stock <= max && stock >= min) {
                    // Create new product using the text field inputs
                    Product newProduct = (new Product(id, name, price, stock, min, max));

                    // add all associated parts to the new product
                    for (Part part : ascPartInventory) {
                        newProduct.addAssociatedPart(part);
                    }

                    // update new product to inventory
                    inv.updateProduct(selectedProductIndex, newProduct);

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
        catch (NumberFormatException nfe){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("Must enter only a valid value for all fields\n\tID (integer)\n\tName (string)\n\tInv (integer)\n\tPrice/Cost (double)\n\tMax (integer)\n\tMin (integer)");
            error.showAndWait();
        }
    }

    /**This method returns to the main screen view without modifying the product. */
    @FXML
    void onClickGoToMainView(MouseEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainScreenView.fxml"));
        controller.MainScreenController controller = new controller.MainScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Greg_Newby C482");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

}
