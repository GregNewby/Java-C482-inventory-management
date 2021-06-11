package model;

/**This class inherits from the Part Class and creates a part that is created In-House.
 * @author Greg Newby (959900)
 */
public class InHouse extends Part {

    private int machineID;

    /**
     * This is a InHouse class constructor.
     * This constructor creates an Inhouse part object with the class variables filled.
     * @param id The id of the part
     * @param name The name of the part
     * @param price The price of the part
     * @param stock The current stock in inventory
     * @param min The min number that can be held in inventory
     * @param max The max number that can be held in inventory
     * @param machineID The machineID that made this part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** This methods gets the parts machineID.
     * @return the machineID
     */
    public int getMachineID() {
        return machineID;
    }

    /** This method sets the parts machineID.
     * @param machineID the machineID to set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
