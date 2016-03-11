/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda.functionalInterface;

/**
 *
 * @author salim
 */
public class WorkerInterfaceTest {
    
    public static void execute(WorkerInterface worker) {
        worker.doSomeWork();
    }
    
    public static void main(String[] args) {
        WorkerInterface workerIntf;
        
        workerIntf = new WorkerInterface() {
            @Override
            public void doSomeWork() {
                System.out.println("Worker invoked using Anonymous class");
            }
        };
        //invoke doSomeWork using Annonymous class
        execute(workerIntf);
        //invoke doSomeWork using Lambda expression 
        execute(()-> System.out.println("Worker invoked using Lambda expression"));
        
    }
    
}
