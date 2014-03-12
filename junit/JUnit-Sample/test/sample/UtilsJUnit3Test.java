/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.util.concurrent.TimeoutException;
import junit.framework.TestCase;

/**
 * https://netbeans.org/kb/docs/java/junit-intro.html#Exercise_344
 *
 * @author salim
 */
public class UtilsJUnit3Test extends TestCase {

    public UtilsJUnit3Test(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.out.println("* UtilsJUnit3Test: setUp() method");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.out.println("* UtilsJUnit3Test: tearDown() method");
    }

    /**
     * Test of concatWords method, of class Utils.
     */
    public void testConcatWords() {
        System.out.println("* UtilsJUnit3Test: test method 1 - testHelloWorld()");
        assertEquals("Hello, world!", Utils.concatWords("Hello", ", ", "world", "!"));
    }

    /**
     * Test of computeFactorial method, of class Utils.
     */
    /*--testing using timeout----*/
    public void testWithTimeout() throws InterruptedException, TimeoutException {
        System.out.println("* UtilsJUnit3Test: test method 2 - testWithTimeout()");
        final int factorialOf = 1 + (int) (30000 * Math.random());
        System.out.println("computing " + factorialOf + '!');

        Thread testThread = new Thread() {
            public void run() {
                System.out.println(factorialOf + "! = " + Utils.computeFactorial(factorialOf));
            }
        };
        testThread.start();
        Thread.sleep(1000);
        testThread.interrupt();
        if (testThread.isInterrupted()) {
            throw new TimeoutException("the test took too long to complete");
        }
        /*The test method invokes the computeFactorial method in Utils.java.
         * You can assume that the computeFactorial method is correct,
         * but in this case you want to test if the computation is completed within 1000 milliseconds. The computeFactorial thread and a test thread are started at the same time. The test thread will stop after 1000 milliseconds and throw a TimeoutException unless the computeFactorial thread completes first. 
         * You will add a message so that a message is displayed if a TimeoutException is thrown.*/
    }
    
    public void testExpectedException() {
         System.out.println("* UtilsJUnit3Test: test method 3 - testExpectedException()");
    try {
        final int factorialOf = -5;
        System.out.println(factorialOf + "! = " + Utils.computeFactorial(factorialOf));
        fail("IllegalArgumentException was expected");
    } catch (IllegalArgumentException ex) {
    }
}
  /*Disabled from test*/
  public void DISABLED_testTemporarilyDisabled() throws Exception {
    System.out.println("* UtilsJUnit3Test: test method 4 - checkExpectedException()");
    assertEquals("Malm\u00f6", Utils.normalizeWord("Malmo\u0308"));
}
}
