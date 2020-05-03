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
        if (alcoholCounter > 0 && userAge < alcoholLegalAge) {
            throw new UnderAgeException("This user is not of age to purchase alcohol!");
        }
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
        /*
        while (produceCounter > 2) {
            produceCounter -= 3;
            subTotal = subTotal - produceDisc;
        }
        */

        //Calculate alcohol/frozen discount
        subTotal = subTotal - calculateAlcoholFrozenDiscount(alcoholCounter, frozenFoodCounter);
        
        /*
        while (alcoholCounter > 0 && frozenFoodCounter > 0) {
            alcoholCounter--;
            frozenFoodCounter--;
            subTotal = subTotal - alcoholFrozenDiscount;
        }
        */

        return subTotal + getTax(subTotal, "AZ");
    }
    
    
    /**
     * Returns a count of the product type that matches the provided name
     * @param typeName String repsesentation of that product type
     * @return count of that type
     */
    private int countProductType(String typeName) {
        int counter = 0;
        for (Product p : cart) {
            if(p.getClass().toString().equalsIgnoreCase(typeName)) {
                counter++;
            }
        }
        return counter;
    }
    
    /**
     * Returns the discount for produce purchases
     * @param produceCount number of produce items in the cart
     * @return the discount
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
        int subTotal = 0;
        int costAfterSavings = 0;

        int produceCounter = 0;
        int alcoholCounter = 0;
        int frozenFoodCounter = 0;
        //dairyCounter not needed or used
        //int dairyCounter = 0;

        for (int i = 0; i < cart.size(); i++) {
            subTotal += cart.get(i).getCost();
            costAfterSavings = costAfterSavings + cart.get(i).getCost();
            //use .equals() instead of ==
            if (cart.get(i).getClass().toString().equals(Produce.class.toString())) {
                produceCounter++;

                if (produceCounter >= 3) {
                    costAfterSavings -= 1;
                    produceCounter = 0;
                }
            //use .equals() instead of ==
            } else if (cart.get(i).getClass().toString().equals(Alcohol.class.toString())) {
                alcoholCounter++;
                if (userAge < 21) {
                    throw new UnderAgeException("The User is not of age to purchase alcohol!");
                }
            //use .equals() instead of ==
            } else if (cart.get(i).getClass().toString().equals(FrozenFood.class.toString())) {
                frozenFoodCounter++;
            }
            //use .equals() instead of == | Also replaced FrozenFood with Dairy
            //dairy not involved in any discounts. else-if removed
            /*
            else if (cart.get(i).getClass().toString().equals(Dairy.class.toString()))
                dairyCounter++;
            }
             */
            if (alcoholCounter >= 1 && frozenFoodCounter >= 1) {
                //fixed -> cost after savings MINUS 3 not PLUS 3
                costAfterSavings = costAfterSavings - 3;
                alcoholCounter--;
                frozenFoodCounter--;
            }
        }
        //fixed -> should be subTotal minus costAfterSavings, not plus
        return subTotal - costAfterSavings;
    }


    /**
     * Gets the tax based on state and the total.
     * @param totalBt subtotal
     * @param twoLetterStateAbbreviation state
     * @return
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
     * @return true if sucessful
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
