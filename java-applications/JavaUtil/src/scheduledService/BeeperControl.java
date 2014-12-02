/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduledService;

/**
 *
 * @author salim
 * http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ScheduledExecutorService.html
 */
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;
import org.omg.CORBA.DATA_CONVERSION;

class BeeperControl {

    private final ScheduledExecutorService scheduler
            = Executors.newScheduledThreadPool(1);

    public void beepForAnHour() {
        final Runnable beeper = new Runnable() {
            public void run() {
                System.out.println("beep");
            }
        };
        /*
         public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
         command - the task to execute
         initialDelay - the time to delay first execution
         period - the period between successive executions
         unit - the time unit of the initialDelay and period parameters
         */
        final ScheduledFuture<?> beeperHandle
                = scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
            }
        }, 60 * 60, SECONDS);
    }

    public void beepForAnGivenTime(int periodbetweenPkt, int timeLength) {
        System.out.println("start time= " + new Date());

        final Runnable beeper = new Runnable() {
            int count = 0;

            public void run() {
                System.out.println(Thread.currentThread().getName() + " is Started");
                System.out.println("send packet.." + count);
                count++;
                System.out.println(Thread.currentThread().getName() + " is completed");
            }
        };
        /*
         public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
         command - the task to execute
         initialDelay - the time to delay first execution
         period - the period between successive executions
         unit - the time unit of the initialDelay and period parameters
         */
        final ScheduledFuture<?> beeperHandle
                = scheduler.scheduleAtFixedRate(beeper, 0, periodbetweenPkt, MILLISECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is Started");
                beeperHandle.cancel(true);
                System.out.println(Thread.currentThread().getName() + " is completed");
            }
        }, timeLength, SECONDS);

    }

    public static void main(String[] args) {
        BeeperControl bc = new BeeperControl();
        //bc.beepForAnHour();
        /*
         total sent = pps * timelength
         example: 50pps * 1 sec = 50 pkt
         50pps * 15 = 750 pkt : tested
         50pps * 120 = 6000 pkt: tested
         */
        int timelength = 15;//sec
        int periodbtwPkt = 20;//ms
           System.out.println(Thread.currentThread().getName() + " is Started");
        bc.beepForAnGivenTime(periodbtwPkt, timelength);
           System.out.println(Thread.currentThread().getName() + " is Completed");
    }
}
