package test.java;

import main.java.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BlackBoxGiven {

    private Class<Cart> classUnderTest;

    @SuppressWarnings("unchecked")
    public BlackBoxGiven(Object classUnderTest) {
        this.classUnderTest = (Class<Cart>) classUnderTest;
    }

    // Define all classes to be tested
    @Parameterized.Parameters
    public static Collection<Object[]> cartClassUnderTest() {
        Object[][] classes = {
            {Cart0.class},
            {Cart1.class},
            {Cart2.class},
            {Cart3.class},
            {Cart4.class},
            {Cart5.class},
        };
        return Arrays.asList(classes);
    }

    private Cart createCart(int age) throws Exception {
        Constructor<Cart> constructor = classUnderTest.getConstructor(Integer.TYPE);
        return constructor.newInstance(age);
    }

    // A sample Cart

    Cart cart1;
    double cart1Expected;
    
    Cart cartNegOne;
    Cart cartZero;
    Cart cartOne;
    Cart cart15;
    Cart cart20;
    Cart cart21;
    Cart cart22;
    Cart cart35;
    
    
    Cart ageCart20;
    Cart ageCart21;
    Cart ageCart22;
    Cart ageCart15;
    Cart ageCart35;
    
    Cart alcohol;
    double alcoholExpected;
    
    Cart dairy;
    double dairyExpected;
    
    Cart alcoholFrozen11;
    double alcoholFrozen11Expected; 
    
    Cart alcoholFrozen12;
    double alcoholFrozen12Expected;
    
    Cart produce3;
    double produce3Expected;
    

    @org.junit.Before
    public void setUp() throws Exception {

        // all carts should be set up like this
    	

        // cart created with an age 40 shopper
        cart1 = createCart(40);
        for (int i = 0; i < 2; i++) {
            cart1.addItem(new Alcohol());
        }
        for(int i = 0; i < 3; i++) {
            cart1.addItem(new Dairy());
        }
        for(int i = 0; i < 4; i++) {
            cart1.addItem(new Meat());
        }
        
        cart1Expected = 70.2;
        
        //cart created with an age 20 shopper, containing alcohol
        ageCart20 = createCart(20);
        ageCart20.addItem(new Alcohol());
        
        //cart created with an age 21 shopper, containing alcohol
        ageCart21 = createCart(21);
        ageCart21.addItem(new Alcohol());
        
        //cart created with an age 22 shopper, containing alcohol
        ageCart22 = createCart(22);
        ageCart22.addItem(new Alcohol());
        
        //cart created with an age 15 shopper, containing alcohol
        ageCart15 = createCart(15);
        ageCart15.addItem(new Alcohol());
        
        //cart created with an age 35 shopper, containing alcohol
        ageCart35 = createCart(35);
        ageCart35.addItem(new Alcohol());
        
        //cart created with of-age shopper w/ 1 alcohol
        alcohol = createCart(40);
        alcohol.addItem(new Alcohol());
        alcoholExpected = 8.64;
        
        //cart created with of-age shopper w/ 1 dairy
        dairy = createCart(40);
        dairy.addItem(new Dairy());
        dairyExpected = 3.24;
        
        //cart created with of-age shopper with 1 alcohol, 1 frozen
        alcoholFrozen11 = createCart(40);
        alcoholFrozen11.addItem(new Alcohol());
        alcoholFrozen11.addItem(new FrozenFood());
        alcoholFrozen11Expected = 10.8;
        
        //cart created with of-age shopper with 1 alcohol, 2 frozen
        alcoholFrozen12 = createCart(40);
        alcoholFrozen12.addItem(new Alcohol());
        alcoholFrozen12.addItem(new FrozenFood());
        alcoholFrozen12.addItem(new FrozenFood());
        alcoholFrozen12Expected = 16.2;
        
        //cart created with 3 produce
        produce3 = createCart(40);
        produce3.addItem(new Produce());
        produce3.addItem(new Produce());
        produce3.addItem(new Produce());
        produce3Expected = 5.40;
        
        cartNegOne = new Cart(-1);
        cartZero = new Cart(0);
        cartOne = new Cart(1);
        cart15; = new Cart(15);
        cart20 = new Cart(20);
        cart21 = new Cart(21);
        cart22 = new Cart(22);
        cart35 = new Cart(35);
        
    }

    @Test
    public void calcCostCart1Test() throws UnderAgeException {
        double amount = cart1.calcCost();
        assertEquals(cart1Expected, amount, .01);
    }

    
    @Test (expected = UnderAgeException.class)
    public void underAge20ExceptionTest() throws UnderAgeException {
    	ageCart20.calcCost();
    }
    
    @Test (expected = UnderAgeException.class)
    public void underAge15ExceptionTest() throws UnderAgeException {
    	ageCart15.calcCost();
    }
    
    @Test 
    public void ofAge21ExceptionTest() throws UnderAgeException {
    	ageCart21.calcCost();
    }
    
    @Test 
    public void ofAge35ExceptionTest() throws UnderAgeException {
    	ageCart35.calcCost();
    }
    
    @Test (expected = UnderAgeException.class)
    public void age0Test() throws Exception, UnderAgeException{
    	Cart cart = createCart(0);
    	cart.addItem(new Alcohol());
    	cart.calcCost();
    }
    
    @Test (expected = Exception.class)
    public void ageNegTest() throws Exception{
    	Cart cart = createCart(-1);
    	cart.calcCost();
    }
    
    @Test 
    public void alcoholTest() throws UnderAgeException {
    	assertEquals(alcoholExpected, alcohol.calcCost(), .01);
    }
    
    @Test 
    public void dairyTest() throws UnderAgeException {
    	assertEquals(dairyExpected, dairy.calcCost(), .01);
    }
    
    @Test 
    public void alcoholFrozen11Test() throws UnderAgeException {
    	assertEquals(alcoholFrozen11Expected, alcoholFrozen11.calcCost(), .01);
    }
    
    @Test 
    public void alcoholFrozen12Test() throws UnderAgeException {
    	assertEquals(alcoholFrozen12Expected, alcoholFrozen12.calcCost(), .01);
    }
    
    @Test 
    public void produce3Test() throws UnderAgeException{
    	assertEquals(produce3Expected, produce3.calcCost(), .01);
    }
    
    @Test
    public void allItemsOfAge1EachTest() throws UnderAgeException, Exception{
    	Cart cart = createCart(40);
    	cart.addItem(new Dairy());
    	cart.addItem(new Meat());
    	cart.addItem(new Produce());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	double cartExpected = 27.0;
    	assertEquals(cartExpected, cart.calcCost(), .01);
    }
    
    @Test
    public void allItemsOfAge3ProduceTest() throws UnderAgeException, Exception{
    	Cart cart = createCart(40);
    	cart.addItem(new Dairy());
    	cart.addItem(new Meat());
    	cart.addItem(new Produce());
    	cart.addItem(new Produce());
    	cart.addItem(new Produce());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	double cartExpected = 30.24;
    	assertEquals(cartExpected, cart.calcCost(), .01);
    }
    
    @Test (expected = UnderAgeException.class)
    public void allItemsUnderAge1EachTest() throws UnderAgeException, Exception{
    	Cart cart = createCart(15);
    	cart.addItem(new Dairy());
    	cart.addItem(new Meat());
    	cart.addItem(new Produce());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	double cartExpected = 27.0;
    	cart.calcCost();
    }
    
    @Test
    public void doubleStackBothDiscountsTest() throws Exception {
    	Cart cart = createCart(40);
    	for (int i=0; i==5; i++)
    		cart.addItem(new Produce());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	double cartExpected = 32.4;
    	assertEquals(cartExpected, cart.calcCost(), .01);
    }
    
    @Test
    public void bothDiscountsPlus1Produce() throws Exception {
    	Cart cart = createCart(40);
    	for (int i=0; i==3; i++)
    		cart.addItem(new Produce());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	double cartExpected = 18.36;
    	assertEquals(cartExpected, cart.calcCost(), .01);
    }
    
    @Test
    public void bothDiscountsPlus1Alcohol() throws Exception {
    	Cart cart = createCart(40);
    	for (int i=0; i==2; i++)
    		cart.addItem(new Produce());
    	cart.addItem(new Alcohol());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	double cartExpected = 24.84;
    	assertEquals(cartExpected, cart.calcCost(), .01);
    }
    
    @Test
    public void savings1eachtest() throws Exception, UnderAgeException {
    	Cart cart = createCart(40);
    	cart.addItem(new Dairy());
    	cart.addItem(new Meat());
    	cart.addItem(new Produce());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	int savedExpected = 3;
    	assertEquals(savedExpected, cart.Amount_saved());
    }
    
    @Test
    public void savingsAlcoholFrozen11Test() throws Exception, UnderAgeException {
    	Cart cart = createCart(40);
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	int savedExpected = 3;
    	assertEquals(savedExpected, cart.Amount_saved());
    }
    
    @Test
    public void savingsProduce3Test() throws Exception, UnderAgeException {
    	Cart cart = createCart(40);
    	for (int i=0; i==2; i++)
    		cart.addItem(new Produce());;
    	int savedExpected = 1;
    	assertEquals(savedExpected, cart.Amount_saved());
    }
    
    
}