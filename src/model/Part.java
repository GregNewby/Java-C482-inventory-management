package model;
/**
 * Supplied class Part.java
 */

import java.text.DecimalFormat;

/**
 * This class is an abstract class for parts.
 * @author Greg Newby (959900)
 */
public abstract class Part {
    /**the parts id*/
    protected int partId;
    /**the parts name*/
    protected String partName;
    /**the parts price*/
    protected double partPrice;
    /**the parts current stock in inventory*/
    protected int partStock;
    /**the parts min number that can be held in inventory*/
    protected int min;
    /**the parts max number that can be held in inventory*/
    protected int max;

    /**
     * This is a part class constructor.
     * This constructor creates a part object with the class variables filled.
     * @param id The id of the part
     * @param name The name of the part
     * @param price The price of the part
     * @param stock The current stock in inventory
     * @param min The min number that can be held in inventory
     * @param max The max number that can be held in inventory
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.partId = id;
        this.partName = name;
        this.partPrice = price;
        this.partStock = stock;
        this.min = min;
        this.max = max;
        }
    /** This method returns the parts id.
     * @return the id
     */
    public int getPartId() {
        return partId;
    }

    /** This method sets the parts id.
     * @param id the id to set
     */
    public void setPartId(int id) {
        this.partId = id;
    }

    /** This methods gets the part name.
     * @return the name
     */
    public String getPartName() {
        return partName;
    }

    /** This method sets the part name.
     * @param name the name to set
     */
    public void setPartName(String name) {
        this.partName = name;
    }

    /**This method gets the parts price.
     * @return the price
     */
    public double getPartPrice() {
        return partPrice;
    }

    /**This method sets the parts price.
     * @param price the price to set
     */
    public void setPartPrice(double price) {
        this.partPrice = price;
    }

    /**This method gets the parts stock.
     * @return the stock
     */
    public int getPartStock() {
        return partStock;
    }

    /** This method sets the part stock.
     * @param stock the stock to set
     */
    public void setPartStock(int stock) {
        this.partStock = stock;
    }

    /**This method gets the parts min.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**This method sets the parts min.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method gets the parts max.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /** This method sets the parts max.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}