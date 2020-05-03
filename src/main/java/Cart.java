package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart {

    protected int userAge;
    public List<Product> cart;
    //SER316 TASK 2 SPOTBUGS FIX
    //public int cartStorage;

    /*
    private final int  DAIRY_PRICE = 3;
    private final int  MEAT_PRICE = 10;
    private final int  PRODUCE_PRICE = 2;
    private final int ALCOHOL_PRICE = 8;
    private final int FROZEN_PRICE = 5;
     */

    //316 TASK 2 SPOTBUGS FIX
    private static final int produceDisc = 1;
    //316 TASK 2 SPOTBUGS FIX
    private static final int alcoholFrozenDiscount = 3;
    //316 TASK 2 SPOTBUGS FIX
    private static final int alcoholLegalAge = 21;

    /**
     * Calculates the final cost after all savings and tax has been applied. Also checks
     * that the user is of age to purchase alcohol if it is in their cart at checkout.
     * Sales tax is always AZ tax.
     * Calculation is based off of the following prices and deals:
     * Dairy -> $3
     * Meat -> $10
     * Produce -> $2 or 3 for $5
     * Alcohol -> $8
     * Frozen Food -> $5
     * Alcohol + Frozen Food -> $10
     * 
     * <p>If there is an alcohol product in the cart and the user is under 21, then an
     * UnderAgeException should be thrown.
     *
     * @return double totalCost
     * @throws UnderAgeException if customer is under 21
     */
    public double calcCost() throws UnderAgeException {
        double  subTotal = 0.0;
        int savings = 0;

        int alcoholCounter = countProductType(Alcohol.class.toString());
        int frozenFoodCounter = countProductType(FrozenFood.class.toString());
        int dairyCounter = countProductType(Dairy.class.toString());
        int produceCounter = countProductType(Produce.class.toString());
        int meatCounter = countProductType(Meat.class.toString());
        
        for (int i = 0; i < cart.size(); i++) {
            Product currentP = cart.get(i);
            subTotal += currentP.getCost();
        }
        
        //Calculate produce discount
        subTotal = subTotal - calculateProduceDiscount(produceCounter);

        //Calculate alcohol/frozen discount
        subTotal = subTotal - calculateAlcoholFrozenDiscount(alcoholCounter, frozenFoodCounter);

        return subTotal + getTax(subTotal, "AZ");
    }
    
    
    /**
     * Returns a count of the product type that matches the provided name
     * @param typeName String representation of that product type
     * @return count of that type
     * @throws UnderAgeException if user is under 21
     */
    private int countProductType(String typeName) throws UnderAgeException {
        int counter = 0;
        for (Product p : cart) {
            if(p.getClass().toString().equalsIgnoreCase(typeName)) {
                counter++;
            }
        }
        if(typeName.equalsIgnoreCase(Alcohol.class.toString()) && counter > 0 && userAge < alcoholLegalAge) {
            throw new UnderAgeException("This user is not of age to purchase alcohol!");
        }
        return counter;
    }
    
    /**
     * Returns the discount for produce purchases
     * @param produceCount number of produce items in the cart
     * @return the discount as int
     */
    private int calculateProduceDiscount(int produceCount) {
        int produceCounter = produceCount;
        int discount = 0;
        while (produceCounter > 2) {
            produceCounter -= 3;
            discount += produceDisc;
        }
        return discount;
    }
    
    /**
     * Returns the discount for alcohol and frozen food purchased together
     * @param alcoholCount number of alcohol items
     * @param frozenCount number of frozen items
     * @return the discount
     */
    private int calculateAlcoholFrozenDiscount(int alcoholCount, int frozenCount) {
        int alcoholCounter = alcoholCount;
        int frozenCounter = frozenCount;
        int discount = 0;
        while (alcoholCounter > 0 && frozenCounter > 0) {
            alcoholCounter--;
            frozenCounter--;
            discount += alcoholFrozenDiscount;
        }
        return discount;
    }
    
    /**
     * calculates how much was saved in the current shopping cart based on the deals, returns 
     * the saved amount.
     * @return int
     * @throws UnderAgeException if customer is under 21
     */
    public int amountSaved() throws UnderAgeException {
        int alcoholCounter = countProductType(Alcohol.class.toString());
        int frozenFoodCounter = countProductType(FrozenFood.class.toString());
        int produceCounter = countProductType(Produce.class.toString());
        
        int discount = calculateAlcoholFrozenDiscount(alcoholCounter, frozenFoodCounter) 
                + calculateProduceDiscount(produceCounter);
        return discount;
    }


    /**
     * Gets the tax based on state and the total.
     * @param totalBt subtotal
     * @param twoLetterStateAbbreviation state
     * @return the sales tax a double
     */
    public double getTax(double totalBt, String twoLetterStateAbbreviation) {
        HashMap<String, Double> taxByState = new HashMap<String, Double>();
        taxByState.put("AZ", .08);
        taxByState.put("CA", .09);
        taxByState.put("NY", .1);
        taxByState.put("CO", .07);
        
        Double taxRate = taxByState.get(twoLetterStateAbbreviation);
        
        if(taxRate == null) {
            return totalBt;
        }
        return taxRate.doubleValue() * totalBt;
        
    }

    public void addItem(Product np) {
        cart.add(np);
    }
    
    /**
     * Removed item from the shopping cart.
     * @param productToRemove Product type item in cart
     * @return true if successful
     */
    public boolean removeItem(Product productToRemove) {
        boolean test = false;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i) == productToRemove) {
                cart.remove(i);
                test = true;
                return test;
            }
        }
        return false;
    }

    public Cart(int age) {
        userAge = age;
        cart = new ArrayList<Product>();
    }
}
