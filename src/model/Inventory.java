package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates an inventory using 2 observable lists to hold parts and products.
 * @author Greg Newby (959900)
 */
public class Inventory {

    private static ObservableList<Part>     allParts = FXCollections.observableArrayList();
    private static ObservableList<Product>  allProducts = FXCollections.observableArrayList();

    /**This method adds a part to the observable list of parts in the inventory.
     * @param newPart the part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**This method adds a product to the observable list of products in the inventory.
     * @param newProduct the part to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**This method returns the part in the inventory given the id of the part you are looking for.
     * @param partId the id of the part the user is looking for
     * @return the part
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getPartId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**This method returns the product in the inventory given the id of the product you are looking for.
     * @param productId the id of the part the user is looking for
     * @return the product
     */
    public static Product lookupProduct(int productId){
        for(Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**This method returns an observable list of parts that contain a given input string.
     * @param partName the input string that we use to check if it is contained in each part
     * @return matchingParts an observable list of parts that contain the searched string
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for(Part part : allParts) {
            if (part.getPartName().contains(partName)) {
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /**This method returns an observable list of products that contain a given input string.
     * @param productName the input string that we use to check if it is contained in each part
     * @return matchingProducts an observable list of products that contain the searched string
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for(Product product : allProducts) {
            if (product.getName().contains(productName)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;

    }

    /**This method replaces a part in the inventory given the index and the replacement part.
     * @param index the index of the part that you would like to replace
     * @param selectedPart the part you would like to use to replace the old part in the inventory
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**This method replaces a product in the inventory given the index and the replacement product.
     * @param index the index of the part that you would like to replace
     * @param newProduct the new product you would like use to replace the old product in the inventory
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**This method deletes a part from inventory given the part to delete.
     * @param selectedPart the part you would like delete from inventory
     */
    public static boolean deletePart(Part selectedPart){
        if(allParts.remove(selectedPart)) {
            return true;
        }
        return false;
    }

    /**This method deletes a product from inventory given the product to delete.
     * @param selectedProduct the product you would like delete from inventory
     */
    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.remove(selectedProduct)) {
            return true;
        }
        return false;
    }

    /**This method returns all of the parts in the inventory.
     * @return allParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**This method returns all of the products in the inventory.
     * @return allProducts
     */
    public static ObservableList<Product>getAllProducts(){
        return allProducts;
    }


}
