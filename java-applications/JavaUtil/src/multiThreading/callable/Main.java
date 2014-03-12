/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiThreading.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 *
 * @author salim
 * How can a Thread return a value after finishing its job?
 * http://stackoverflow.com/questions/3141158/how-can-a-thread-return-a-value-after-finishing-its-job
 */
public class Main
{
    public static void main(final String[] argv)
    {
        final ExecutorService service;
        final Future<String>  task;
        final Future<Presence> taskPre;

        service = Executors.newFixedThreadPool(10);        
        task    = service.submit(new Foo());
        
        //get the presence Obj
        taskPre = service.submit(new PresenceCallable());

        try 
        {
            final String str;

            // waits the 10 seconds for the Callable.call to finish.
            str = task.get();
            System.out.println(str);
            
            //get the presence
            Presence presObj = taskPre.get();
            System.out.println("computation completed, object returned: "+presObj.toString());

        }
        catch(final InterruptedException ex)
        {
            ex.printStackTrace();
        }
        catch(final ExecutionException ex)
        {
            ex.printStackTrace();
        }

        service.shutdownNow();
    }
}
