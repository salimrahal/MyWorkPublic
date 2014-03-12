/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import vo.PresenceVO;
import bo.xmlparser.SAXParserPresence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 *
 * @author salim
 * each notify will call its own thread
 */
@Deprecated
public class PresenceRunnable implements Runnable {

    SAXParserPresence saxparser;
    PresenceVO presence;
    InputStream presenceInputStream;
    //int cycle = 24; //how many time we invoke the Subscription every 1 hour.
    //int sleepTime;//in millisecond:  is the same value as the expires*1000ms [s*1000 = in ms]

    public PresenceRunnable(SAXParserPresence saxparser,  InputStream presenceInputStream, PresenceVO presence) {
        this.saxparser = saxparser;
        this.presenceInputStream = presenceInputStream;
        this.presence = presence;
    }

    @Override
    public void run() {          
            System.out.println("PresenceRunnable:"+new Date() +"/thread name:"+ Thread.currentThread().getName());
        try {
            //retreive the obj given the xml as input stream
            this.presence = saxparser.buildPresence(this.presenceInputStream);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(PresenceRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PresenceRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PresenceRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
}


}


