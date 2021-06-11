package controller;

        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Stage;
        import model.InHouse;
        import model.Inventory;
        import model.Outsourced;
        import model.Part;

        import java.io.IOException;
        import java.net.URL;
        import java.text.DecimalFormat;
        import java.util.ResourceBundle;

/**This class  is the controller for the modify part view.
 * This class contains all the methods that are called when the user navigates with the buttons on the modify part screen.
 * @author Greg Newby (959900)
 */
public class ModPartController implements Initializable {

    private Inventory inv;
    private Part selectedPart;
    private int selectedPartIndex;
    private Stage stage;
    private DecimalFormat df = new DecimalFormat(".00");

    /**This is a default constructor. */
    public ModPartController() {}

    /**This contructor takes an inventory and the selected part's index as input.
     * @param inv the inventory that is passed to this class and set to the screens current inventory
     * @param selectedPartIndex the index of the selected part in inventory
     */
    public ModPartController(Inventory inv, int selectedPartIndex) {
        this.inv = inv;
        this.selectedPartIndex = selectedPartIndex;
        this.selectedPart = inv.getAllParts().get(selectedPartIndex);
    }

    /**This method initializes the ModPartController class.
     * The method reads the selected part and uses an if loop to distinguish which radial button should be selected and which
     * label should be displayed. Then all text fields are displayed with the current parts values for each label.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //if part is an Inhouse set inhouse to be selected else if part is Outsource set Outsource to be selected.
        // then set label and retrieve last textfield data in view
        if(selectedPart instanceof InHouse) {
            modPartInBtn.fire();
            machCompLbl.setText("Machine ID");
            machCompId.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }

        else if(selectedPart instanceof Outsourced) {
            modPartOutBtn.fire();
            machCompLbl.setText("Company Name");
            machCompId.setText(((Outsourced) selectedPart).getCompanyName());
        }

        modPartId.setText(String.valueOf(selectedPart.getPartId()));
        modPartName.setText(selectedPart.getPartName());
        modPartInv.setText(String.valueOf(selectedPart.getPartStock()));
        modPartPrice.setText(String.valueOf(df.format(selectedPart.getPartPrice())));
        modPartMax.setText(String.valueOf(selectedPart.getMax()));
        modPartMin.setText(String.valueOf(selectedPart.getMin()));
    }

    @FXML
    private TextField modPartId;

    @FXML
    private TextField modPartName;

    @FXML
    private TextField modPartInv;

    @FXML
    private TextField modPartPrice;

    @FXML
    private TextField modPartMax;

    @FXML
    private TextField machCompId;

    @FXML
    private TextField modPartMin;

    @FXML
    private Button modPartCancelBtn;

    @FXML
    private Button modPartSaveBtn;

    @FXML
    private RadioButton modPartInBtn;

    @FXML
    private ToggleGroup modInOutTG;

    @FXML
    private RadioButton modPartOutBtn;

    @FXML
    private Label machCompLbl;

    /**This method is run when the radial button for Outsource part is selected.
     * The method sets the last text field label to Company Name.
     */
    @FXML
    void onClickDisplayCompanyName(MouseEvent event) {
        machCompLbl.setText("Company Name");
    }

    /**This method is run when the radial button for In-house part is selected.
     * The method sets the last text field label to Machine ID.
     */
    @FXML
    void onClickDisplayMachineID(MouseEvent event) {
        machCompLbl.setText("Machine ID");
    }

    /**This method is called when the save button is pressed.
     * This method reads all of the text field input and modifies the part into the same spot in inventory.
     *
     * LOGICAL ERROR: Without checking if the In-house button is selected or
     * the Outsource button is selected we get a logical error. The solution is to use if/else-if statement to check the status of the
     * radial buttons.
     *
     * LOGICAL ERROR: Stock should be between Min and Max. The solution is to first use an if loop to make sure that Min is less than Max. If
     * not display a warning alert. Then use an if loop to make sure that stock is less than or equal to Max and greater or equal to Min.
     * If not then display a warning alert.
     *
     * RUNTIME ERROR: If there is any missing information from the text fields or incorrect data types used you will get a NumberFormatException.
     * A try catch block is used to capture these situations and display an error alert.
     */
    @FXML
    void onClickModPart(MouseEvent event) throws IOException {
        try {
            //Get info from text fields and put them into the variables for the modified Part
            int id = Integer.parseInt(modPartId.getText());
            String name = modPartName.getText();
            double price = Double.parseDouble(modPartPrice.getText());
            int stock = Integer.parseInt(modPartInv.getText());
            int min = Integer.parseInt(modPartMin.getText());
            int max = Integer.parseInt(modPartMax.getText());
            int machineID;
            String companyName = machCompId.getText();

            if (min <= max) {
                if (stock <= max && stock >= min) {
                    if (modPartInBtn.isSelected()) {
                        machineID = Integer.parseInt(machCompId.getText());
                        inv.updatePart(selectedPartIndex, new InHouse(id, name, price, stock, min, max, machineID));
                        //Go Back to Main Screen
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
                    else if (modPartOutBtn.isSelected()) {
                        inv.updatePart(selectedPartIndex, new Outsourced(id, name, price, stock, min, max, companyName));
                        //Go Back to Main Screen
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
                        alert.setContentText("You must select if the part is In-House or Outsourced");
                        alert.showAndWait();
                    }
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
            error.setContentText("Must enter only a value for all fields\n\tID (integer)\n\tName (string)\n\tInv (integer)\n\tPrice/Cost (double)\n\tMax (integer)\n\tMin (integer)\n\tMachine ID (integer)\n\tCompany ID (string)");
            error.showAndWait();
        }
    }

    /**This method returns to the main screen view without modifying the part. */
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