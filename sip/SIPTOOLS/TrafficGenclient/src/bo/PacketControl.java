/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

/**
 *
 * @author salim
 * http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ScheduledExecutorService.html
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import vp.vo.CdcVo;

class PacketControl {

    int count = 0;
    DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portDest;
    DatagramPacket outgoingPacketLocal = null;

    public PacketControl(DatagramSocket dgmsocket, InetAddress addressDest, Integer portDest) {
        this.dgmsocket = dgmsocket;
        this.addressDest = addressDest;
        this.portDest = portDest;
    }

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
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
            }
        }, 60 * 60, SECONDS);
    }

    public boolean sndPktForAnGivenTime(String codec, int timeLength) {
        boolean res = false;
        System.out.println("PacketControl:sndPktForAnGivenTime: Sending packet:: start time= " + new Date());
             System.out.println("PacketControl:sndPktForAnGivenTime::Thread name"+Thread.currentThread().getName()+" Priority=" + Thread.currentThread().getPriority());
         System.out.println("PacketControlsndPktForAnGivenTime: sending to host="+addressDest.getHostAddress()+"/portdest="+portDest);
        int pps = CdcVo.returnPPSbyCodec(codec);
        int periodbetweenPkt = CdcVo.computePeriodBetweenPkt(pps);
        final Runnable sndrRunnable = new SndrRunnable(codec);
        /*
         public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
         command - the task to execute
         initialDelay - the time to delay first execution
         period - the period between successive executions
         unit - the time unit of the initialDelay and period parameters
         */
        final ScheduledFuture<?> sndrHandle = scheduler.scheduleAtFixedRate(sndrRunnable, 0, periodbetweenPkt, MILLISECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                boolean res = sndrHandle.cancel(true);
                System.out.println("sndPktForAnGivenTime: task is finished and cancled="+res+"--- total packet sent ="+count);
                dgmsocket.close();
            }
        }, timeLength, SECONDS);
        try {
             System.out.println("sndPktForAnGivenTime: waiting for termination..");
            boolean succterm = scheduler.awaitTermination(timeLength, SECONDS);
             if(succterm){System.out.println("sndPktForAnGivenTime: finished successfully");}else{
                 System.out.println("sndPktForAnGivenTime: task termination has triggered before finishing the task. (timeout)");
             }
             res = true;
        } catch (InterruptedException ex) {
            Logger.getLogger(PacketControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    class SndrRunnable implements Runnable {
        String codec;
        byte[] buf = null;
        public SndrRunnable(String codec) {
            this.codec = codec;
            buf = CdcVo.returnPayloadybyCodec(codec);
        }

        public void run() {
            try {
                outgoingPacketLocal = new DatagramPacket(buf, buf.length, addressDest, portDest);
                //send the packet back to the client
                dgmsocket.send(outgoingPacketLocal);
                count++;
            } catch (IOException ex) {
                Logger.getLogger(PacketControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public static void main(String[] args) {
        /*
         total sent = pps * timelength
         example: 50pps * 1 sec = 50 pkt
         50pps * 15 = 750 pkt : tested
         50pps * 120 = 6000 pkt: tested
         500pps
         */
        //PacketControl bc = new PacketControl();
        //bc.beepForAnHour();
//        int timelength = 15;//sec
//        int periodbtwPkt = 12;//ms
//        bc.sndPktForAnGivenTime(12, timelength);
    }
}
