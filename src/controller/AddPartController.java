package controller;

        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Stage;
        import model.*;

        import java.io.IOException;
        import java.net.URL;
        import java.text.DecimalFormat;
        import java.util.Random;
        import java.util.ResourceBundle;

/**This class  is the controller for the add part view.
 * This class contains all the methods that are called when the user navigates with the buttons on the add part screen.
 * @author Greg Newby (959900)
 */
public class AddPartController implements Initializable {

    private Random randomId = new Random();
    private Inventory inv;
    private Stage stage;
    private DecimalFormat df = new DecimalFormat(".00");

    /**This is the default constructor. */
    public AddPartController() {}

    /**This contructor takes an inventory as input and sets it as this screens current inventory.
     * @param inv the inventory that is passed to this class and set to the screens current inventory
     */
    public AddPartController(Inventory inv){
        this.inv = inv;
    }

    /**This method initializes the AddPartController class.
     * This starts the screen with the In-house button selected including setting the last label
     * to Machine ID. The method then calls another method to generate a random integer for the part ID*/
    @Override
    public void initialize(URL url, ResourceBundle rb){
        addPartInBtn.fire();
        machCompLbl.setText("Machine ID");

        generatePartID();
    }

    /**This method generates a random integer from 1-1000.
     * The first integer is checked to see if there is another part with the same ID. If there is, it then chooses another integer until
     * until it finds an unused integer between 1-1000.
     */
    private void generatePartID(){
        int Number = 1+randomId.nextInt(1000);

        for(Part part : inv.getAllParts()){
            if(Number == part.getPartId()){
                generatePartID();
            }
            else{
                addPartId.setText(String.valueOf(Number));
            }
        }
    }



    @FXML
    private TextField addPartId;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartPrice;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField machCompId;

    @FXML
    private TextField addPartMin;

    @FXML
    private Button addPartCancelBtn;

    @FXML
    private Button addPartSaveBtn;

    @FXML
    private RadioButton addPartInBtn;

    @FXML
    private ToggleGroup addInOutTG;

    @FXML
    private RadioButton addPartOutBtn;

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
     * This method reads all of the text field input and creates a new part.
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
    void onClickAddPartToList(MouseEvent event) throws IOException {
        try {
            int id = Integer.parseInt(addPartId.getText());
            String name = addPartName.getText();
            double price = Double.parseDouble(addPartPrice.getText());
            int stock = Integer.parseInt(addPartInv.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());

            int machineID;
            String companyName = machCompId.getText();

            if (min <= max) {
                if (stock <= max && stock >= min) {
                    if (addPartInBtn.isSelected()) {
                        machineID = Integer.parseInt(machCompId.getText());
                        inv.addPart(new InHouse(id, name, price, stock, min, max, machineID));

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
                    else if (addPartOutBtn.isSelected()) {
                        inv.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

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
            error.setContentText("Must enter only a valid value for all fields\n\tID (integer)\n\tName (string)\n\tInv (integer)\n\tPrice/Cost (double)\n\tMax (integer)\n\tMin (integer)\n\tMachine ID (integer)\n\tCompany ID (string)");
            error.showAndWait();
        }
    }

    /**This method returns to the main screen view without adding the new part. */
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
