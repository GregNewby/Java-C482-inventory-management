package model;

/**This class inherits from the Part Class and creates a part that is supplied by another company.
 * @author Greg Newby (959900)
 */
public class Outsourced extends Part{

    private String companyName;

    /**
     * This is a Outsourced class constructor.
     * This constructor creates an Outsource part object with the class variables filled.
     * @param id The id of the part
     * @param name The name of the part
     * @param price The price of the part
     * @param stock The current stock in inventory
     * @param min The min number that can be held in inventory
     * @param max The max number that can be held in inventory
     * @param companyName  The company who manufactures this part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This methods gets the parts companyName.
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /** This method sets the parts companyName.
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
