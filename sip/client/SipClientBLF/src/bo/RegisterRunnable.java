/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import controller.ClientController;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.message.Request;

/**
 *
 * @author salim
 */
public class RegisterRunnable implements Runnable {

    ClientController sipClientController;
    String reqRegister;
    public Request req = null;
    Integer expires;
    //int cycle = 24; //how many time we invoke the Subscription every 1 hour.
    //int sleepTime;//in millisecond:  is the same value as the expires*1000ms [s*1000 = in ms]

    public RegisterRunnable(ClientController sipClientController, String reqRegister, Integer expires) {
        //super();
        this.sipClientController = sipClientController;
        this.reqRegister = reqRegister;
        this.expires = expires;
    }

    public Request getReq() {
        return req;
    }

    public void setReq(Request req) {
        this.req = req;
    }

    @Override
    public void run() {
        try {
            long sleepTime = this.expires * 1000;
            System.out.println(new Date() +"/thread name:"+ Thread.currentThread().getName()+",sleep" + sleepTime + " ms before re-sending the Register");
            Thread.sleep(sleepTime);
            req = sipClientController.registerStateful(this.reqRegister, null, this.expires);
        } catch (ParseException ex) {
            Logger.getLogger(RegisterRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidArgumentException ex) {
            Logger.getLogger(RegisterRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SipException ex) {
            Logger.getLogger(RegisterRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RegisterRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        setReq(req);
    }
}
//		for (int x = 1; x <= cycle; x++) {
//			System.out.println("Run by " + Thread.currentThread().getName()
//					+ ", x is " + x);			
//			/**----------Using SLEEP--------------------
//			 * using sleep() is the best way to help all threads get a chance to run!
//			 * using sleep() to try to force the threads to alternate rather 
//			 * than letting one thread dominate for any period of time.
//                         * */
//			try {
//                            
//                                req = sipClientController.subscribe(reqSubscribe, null);
//                                setReq(req);
//                                //it puts the current running thread to sleep: sleeptime in millisecond     
//                                Thread.sleep(3600000);                    
//				
//			} catch (InterruptedException e) {
//				
//				e.printStackTrace();
//			} catch (ParseException ex) {
//                        Logger.getLogger(SubscribeRunnable.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (InvalidArgumentException ex) {
//                        Logger.getLogger(SubscribeRunnable.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (SipException ex) {
//                        Logger.getLogger(SubscribeRunnable.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (Exception ex) {
//                        Logger.getLogger(SubscribeRunnable.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//		}//end for loop

