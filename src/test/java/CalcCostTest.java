package test.java;
import main.java.Alcohol;
import main.java.Cart;
import main.java.Dairy;
import main.java.FrozenFood;
import main.java.Meat;
import main.java.Produce;
import main.java.UnderAgeException;
import static org.junit.Assert.assertEquals;
import java.lang.reflect.Constructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalcCostTest {

    Cart cart1;
    double cart1Expected;
    
    Cart myCart;
    Cart cartNegOne;    
    Cart cartZero;
    Cart cartOne;
    Cart cart15;
    Cart cart20;
    Cart cart21;
    Cart cart22;
    Cart cart35;
       

    double alcoholExpected;
    double dairyExpected;
    double meatExpected;
    double produceExpected;
    double frozenExpected;
    
    
    @Before
    public void setUp() throws Exception {
    	
        // cart created with an age 40 shopper
        cart1 = new Cart(40);
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
        
        
        alcoholExpected = 8.64;
        dairyExpected = 3.24;
        meatExpected = 10.80;
        produceExpected = 2.16;
        frozenExpected = 5.40;

        
        cartZero = new Cart(0);
        cartOne = new Cart(1);
        cart15 = new Cart(15);
        cart20 = new Cart(20);
        cart21 = new Cart(21);
        cart22 = new Cart(22);
        cart35 = new Cart(35);
        cartNegOne = new Cart(-1);
        
        myCart = new Cart(40);
        }
    
    
    /**
     * Example Test from Instructor
     * @throws UnderAgeException
     */
    @Test
    public void calcCostCart1Test() throws UnderAgeException {
    	double amount = cart1.calcCost();
        assertEquals(cart1Expected, amount, .01);
    }
    
    
    /**
     * Test Case 2
     * @throws Exception
     * @throws UnderAgeException
     */
    @Test (expected = UnderAgeException.class)
    public void age0Test() throws Exception, UnderAgeException{
    	cartZero.addItem(new Alcohol());
    	cartZero.calcCost();
    }
    
    
    /**
     * Test Case 3
     * @throws Exception
     * @throws UnderAgeException
     */
    @Test (expected = UnderAgeException.class)
    public void age1Test() throws Exception, UnderAgeException{
    	cartZero.addItem(new Alcohol());
    	cartZero.calcCost();
    }
  
    
    /**
     * Test Case 4
     * @throws UnderAgeException
     */
    @Test (expected = UnderAgeException.class)
    public void underAge15ExceptionTest() throws UnderAgeException {
    	cart15.addItem(new Alcohol());
    	cart15.calcCost();
    }
    
    /**
     * Test Case 5
     * @throws UnderAgeException
     */
    @Test (expected = UnderAgeException.class)
    public void underAge20ExceptionTest() throws UnderAgeException {
    	cart20.addItem(new Alcohol());
    	cart20.calcCost();
    }
    
    /**
     * Test Case 6
     * @throws UnderAgeException
     */
    @Test 
    public void ofAge21ExceptionTest() throws UnderAgeException {
    	cart21.addItem(new Alcohol());
    	cart21.calcCost();
    }
    
    /**
     * Test Case 7
     * @throws UnderAgeException
     */
    @Test 
    public void ofAge22ExceptionTest() throws UnderAgeException {
    	cart22.addItem(new Alcohol());
    	cart22.calcCost();
    }
    
    /**
     * Test Case 8
     * @throws UnderAgeException
     */
    @Test 
    public void ofAge35ExceptionTest() throws UnderAgeException {
    	cart35.addItem(new Alcohol());
    	cart35.calcCost();
    }    

    
    /**
     * Test Case 10
     * @throws UnderAgeException
     */
    @Test 
    public void alcoholPriceTest() throws UnderAgeException {
    	myCart.addItem(new Alcohol());
    	assertEquals(alcoholExpected, myCart.calcCost(), .01);
    }
    
    
    /**
     * Test Case 11
     * @throws UnderAgeException
     */
    @Test 
    public void dairyPriceTest() throws UnderAgeException {
    	myCart.addItem(new Dairy());
    	assertEquals(dairyExpected, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 12
     * @throws UnderAgeException
     */
    @Test 
    public void meatPriceTest() throws UnderAgeException {
    	myCart.addItem(new Meat());
    	assertEquals(meatExpected, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 13
     * @throws UnderAgeException
     */
    @Test 
    public void producePriceTest() throws UnderAgeException {
    	myCart.addItem(new Produce());
    	assertEquals(produceExpected, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 14
     * @throws UnderAgeException
     */
    @Test 
    public void frozenPriceTest() throws UnderAgeException {
    	myCart.addItem(new FrozenFood());
    	assertEquals(frozenExpected, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 15
     * @throws UnderAgeException
     */
    @Test 
    public void produce3Test() throws UnderAgeException{
    	for (int i = 0; i < 3; i++)
    		myCart.addItem(new Produce());
    	assertEquals(5.40, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 16
     * @throws UnderAgeException
     */
    @Test 
    public void produce4Test() throws UnderAgeException{
    	for (int i = 0; i < 4; i++)
    		myCart.addItem(new Produce());
    	assertEquals(7.56, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 17
     * @throws UnderAgeException
     */
    @Test 
    public void produce6Test() throws UnderAgeException{
    	for (int i = 0; i < 6; i++)
    		myCart.addItem(new Produce());
    	assertEquals(10.80, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 18
     * @throws UnderAgeException
     */
    @Test 
    public void alcoholFrozenTest() throws UnderAgeException {
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new FrozenFood());
    	assertEquals(10.80, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 19
     * @throws UnderAgeException
     */
    @Test 
    public void alcoholFrozen12Test() throws UnderAgeException {
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new FrozenFood());
    	myCart.addItem(new FrozenFood());
    	assertEquals(16.20, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 20
     * @throws UnderAgeException
     */
    @Test 
    public void alcoholFrozen21Test() throws UnderAgeException {
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new FrozenFood());
    	assertEquals(19.44, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 21
     * @throws UnderAgeException
     */
    @Test 
    public void alcoholFrozenTwiceTest() throws UnderAgeException {
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new FrozenFood());
    	myCart.addItem(new FrozenFood());
    	assertEquals(21.60, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 22
     * @throws Exception
     */
    @Test
    public void doubleStackBothDiscountsTest() throws Exception {
    	for (int i=0; i < 6; i++)
    		myCart.addItem(new Produce());
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new FrozenFood());
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new FrozenFood());
    	assertEquals(32.40, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 23
     * @throws UnderAgeException
     */
    @Test
    public void removeDiscountedAlcohol() throws UnderAgeException{
    	Alcohol coors = new Alcohol();
    	myCart.addItem(coors);
    	myCart.addItem(new FrozenFood());
    	myCart.RemoveItem(coors);
    	assertEquals(5.40, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 24
     * @throws UnderAgeException
     */
    @Test
    public void removeDiscountedProduce() throws UnderAgeException{
    	Produce apple = new Produce();
    	myCart.addItem(apple);
    	myCart.addItem(new Produce());
    	myCart.addItem(new Produce());
    	myCart.RemoveItem(apple);
    	assertEquals(4.32, myCart.calcCost(), .01);
    }
    
    /**
     * Test Case 25
     * @throws Exception
     */
    @Test
    public void doubleStackBothDiscountsThenRemove() throws Exception {
    	for (int i=0; i < 5; i++)
    		myCart.addItem(new Produce());
    	Produce apple = new Produce();
    	myCart.addItem(apple);
    	Alcohol coors = new Alcohol();
    	myCart.addItem(coors);
    	myCart.addItem(new FrozenFood());
    	myCart.addItem(new Alcohol());
    	myCart.addItem(new FrozenFood());
    	myCart.RemoveItem(apple);    	
    	myCart.RemoveItem(coors);
    	assertEquals(25.92, myCart.calcCost(), .01);
    }
    
 
    
}
