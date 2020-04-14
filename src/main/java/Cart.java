package main.java;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    protected int userAge;
    public List<Product> cart;
    public int cartStorage;
    
    /*
    private final int  DAIRY_PRICE = 3;
    private final int  MEAT_PRICE = 10;
    private final int  PRODUCE_PRICE = 2;
    private final int ALCOHOL_PRICE = 8;
    private final int FROZEN_PRICE = 5;
    */
    
    private final int produceDisc = 1;
    private final int ALCOHOL_FROZEN_DISC = 3;
    private final int ALCOHOL_LEGAL_AGE = 21;

    /**
     * Calculates the final cost after all savings and tax has been applied. Also checks
     * that the user is of age to purchase alcohol if it is in their cart at checkout. Sales tax is always AZ tax.
     *
     * Calculation is based off of the following prices and deals:
     * Dairy -> $3
     * Meat -> $10
     * Produce -> $2 or 3 for $5
     * Alcohol -> $8
     * Frozen Food -> $5
     * Alcohol + Frozen Food -> $10
     *
     * If there is an alcohol product in the cart and the user is under 21, then an
     * UnderAgeException should be thrown.
     *
     * @return double totalCost
     * @throws UnderAgeException
     */
    public double calcCost() throws UnderAgeException {
    	double  subTotal = 0.0;
    	int savings = 0;
    	
    	int alcoholCounter = 0;
        int frozenFoodCounter = 0;
        int dairyCounter = 0;
        int produceCounter = 0;
        int meatCounter = 0;

        for(int i = 0; i < cart.size(); i++) {
        	Product currentP = cart.get(i);
        	subTotal += currentP.getCost();
        	if(currentP.getClass().toString().equals(Dairy.class.toString())) {
        		dairyCounter++;
        	}
        	else if(currentP.getClass().toString().equals(Meat.class.toString())) {
        		meatCounter++;
        	}
        	else if(currentP.getClass().toString().equals(Alcohol.class.toString())) {
        		if (userAge < ALCOHOL_LEGAL_AGE) {
        			throw new UnderAgeException("The User is not of age to purchase alcohol!");
        		}
        		alcoholCounter++;
        	}
        	else if(currentP.getClass().toString().equals(FrozenFood.class.toString())) {
        		frozenFoodCounter++;
            }
            else if (currentP.getClass().toString().equals(Produce.class.toString())) {
                produceCounter++;
            }
        }
        
        //Calculate produce discount
        while (produceCounter > 2) {
            produceCounter -= 3;
            subTotal = subTotal - produceDisc;
        }
        
        //Calculate alcohol/frozen discount
        while (alcoholCounter > 0 && frozenFoodCounter > 0) {
            alcoholCounter--;
            frozenFoodCounter--;
            subTotal = subTotal - ALCOHOL_FROZEN_DISC;
        }
        
        return subTotal + getTax(subTotal, "AZ");
    }

    // calculates how much was saved in the current shopping cart based on the deals, returns 
    //the saved amount
    // throws exception if alcohol is bought from underaged person
    // TODO: Create node graph for this method in assign 4: create white box tests and fix the
    //method, reach at least 98% coverage
    public int Amount_saved() throws UnderAgeException {
        int subTotal = 0;
        int costAfterSavings = 0;

        double produce_counter = 0;
        int alcoholCounter = 0;
        int frozenFoodCounter = 0;
        //dairyCounter not needed or used
        //int dairyCounter = 0;

        for (int i = 0; i < cart.size(); i++) {
            subTotal += cart.get(i).getCost();
            costAfterSavings = costAfterSavings + cart.get(i).getCost();
            //use .equals() instead of ==
            if (cart.get(i).getClass().toString().equals(Produce.class.toString())) {
                produce_counter++;

                if (produce_counter >= 3) {
                    costAfterSavings -= 1;
                    produce_counter = 0;
                 }
            
                //use .equals() instead of ==
                else if (cart.get(i).getClass().toString().equals(Alcohol.class.toString())) {
                    alcoholCounter++;
                    if (userAge < 21) {
                        throw new UnderAgeException("The User is not of age to purchase alcohol!");
                    }
            
                   //use .equals() instead of ==
                   else if (cart.get(i).getClass().toString().equals(FrozenFood.class.toString())) {
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
        }
        return 0;
    }

    // Gets the tax based on state and the total
    public double getTax(double totalBT, String twoLetterUSStateAbbreviation) {
        double newTotal = 0;
        switch (twoLetterUSStateAbbreviation) {
            case "AZ":
                newTotal = totalBT * .08;
                break;
            case "CA":
                newTotal = totalBT * .09;
                break;
            case "NY":
                newTotal = totalBT * .1;
                break; //this break was missing
            case "CO":
                newTotal = totalBT * .07;
                break;
            default:
                return totalBT;
        }
        return newTotal;
    }

    public void addItem(Product np) {
      cart.add(np);
    }

    public boolean RemoveItem(Product productToRemove)
    {
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
