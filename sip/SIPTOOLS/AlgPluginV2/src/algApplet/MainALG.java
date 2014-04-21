package algApplet;

/*
 */ 

import algController.ClientController;
import algGui.AlgJPanel;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import java.text.ParseException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sip.InvalidArgumentException;
import javax.sip.ObjectInUseException;
import javax.sip.PeerUnavailableException;
import javax.sip.TransportNotSupportedException;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

public class MainALG extends JApplet {
        AlgJPanel algJpanel;
    //Called when this applet is loaded into the browser.
        @Override
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {                   
                     createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
         ClientController sipClient = AlgJPanel.getSipClientController();
         if(sipClient!=null){
            try {
                sipClient.reset();
            } catch (Exception ex) {
                Logger.getLogger(MainALG.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    
    /**
     * Create the GUI. For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    private void createGUI() {
 //           try {
                //
        algJpanel = new AlgJPanel(); 
        algJpanel.setOpaque(true);
        setContentPane(algJpanel);

    }
}
