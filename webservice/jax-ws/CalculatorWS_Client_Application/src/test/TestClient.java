/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author salim
 */
public class TestClient {

    public static void main(String[] args) {
        try {
            int i = 3;
            int j = 4;
            int result = add(i, j);
            System.out.println("local webservice Result = " + result);
        } catch (Exception e) {
            System.out.println("exception:"+e);
        }
        
          try {
            int i = 3;
            int j = 4;
            int result = add_remote(i, j);
            System.out.println("remote webservice:74.117.31.73:8080/CalculatorWS/CalculatorWS"
                    + "\n Result = " + result);
        } catch (Exception e) {
            System.out.println("exception:"+e);
        }
    }

    private static int add(int i, int j) {
        org.me.calculator.CalculatorWS_Service service = new org.me.calculator.CalculatorWS_Service();
        org.me.calculator.CalculatorWS port = service.getCalculatorWSPort();
        return port.add(i, j);
    }

    private static int add_remote(int i, int j) {
        org.me.calculator.CalculatorWS_Service service = new org.me.calculator.CalculatorWS_Service();
        org.me.calculator.CalculatorWS port = service.getCalculatorWSPort();
        return port.add(i, j);
    }
    
    
}
