/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda;

/**
 * LE : stands for Lambda expression
 * @author salim
 */
public class TestLambdaExpression {

    public static void main(String[] args) {
        //  Runnable r = () -> System.out.println("hello world");
      passingLEToThread();

    }

    /**
     * in above code, compiler automatically deduced that lambda expression can be casted to
     * Runnable interface from Thread class’s constructor signature public Thread(Runnable r) { }.
     */
    public static void passingLEToThread() {
        new Thread(
                () -> System.out.println("hello world")
        ).start();
    }

}
