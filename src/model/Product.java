package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates products.
 * @author Greg Newby (959900)
 */
public class Product {
    /**an observable list of all the associated parts for the product*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**the id of the product*/
    private int id;
    /**the name of the product*/
    private String name;
    /**the price of the product*/
    private double price;
    /**the current stock in inventory*/
    private int stock;
    /**the min number that can be held in inventory*/
    private int min;
    /**the max number that can be held in inventory*/
    private int max;

    /**
     * This is a product class constructor.
     * This constructor creates a part object with the class variables filled.
     * @param id The id of the product
     * @param name The name of the product
     * @param price The price of the product
     * @param stock The current stock in inventory
     * @param min The min number that can be held in inventory
     * @param max The max number that can be held in inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This method returns the products id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /** This method sets the products id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /** This method returns the products name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /** This method sets the products name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** This method returns the products price.
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /** This method sets the products price.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** This method returns the products stock.
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /** This method sets the current stock in inventory .
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This method returns the products min.
     * @return min
     */
    public int getMin() {
        return min;
    }

    /** This method sets the min number that can be held in inventory .
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method returns the products max.
     * @return max
     */
    public int getMax() {
        return max;
    }

    /** This method sets the max number that can be held in inventory .
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method adds an associated part to the associated parts list for that product.
     * @param part the part to add to the associatedParts list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /** This method removes an associated part from the associated parts list for that product.
     * @param part the part to remove from the associatedParts list
     */
    public boolean deleteAssociatedPart(Part part){
        if(associatedParts.remove(part)) {
            return true;
        }
        return false;
    }

    /** This method returns the products associated parts list.
     * @return associatedParts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

}

