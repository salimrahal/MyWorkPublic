package algApplet;

/*
 */ 

import algController.ClientController;
import algGui.AlgJPanel;
import java.net.URL;
import java.text.ParseException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                    try {
                        createGUI();
                       // System.out.println("getCodeBase;"+getCodeBase());
                      //usefull for reading from XML file
                       // algBo.ALGBo.readFile( "config.xml",getCodeBase());
                    } catch (Exception ex) {
                        Logger.getLogger(MainALG.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }

    @Override
    public void destroy() {
        System.out.println("Final cleanup ....destroy...");
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
         //System.exit(0);
         ClientController sipClient = AlgJPanel.getSipClientController();
         if(sipClient!=null){//Do something
            try {
//                  System.out.println("destroy...sipClient not null..");
//               String res = sipClient.reset();//reset msg:Object is in use
//                 if(res.equalsIgnoreCase("OK")){
//                    sipClient = null;
//                }else{//TODO: alg clean the sipListeners and close existing connections
//                    System.out.println("Reset failed! "+res);
//                   
//                }
            } catch (Exception ex) {
                Logger.getLogger(MainALG.class.getName()).log(Level.SEVERE, null, ex);
           }
         }
    }
    
    /**
     * Create the GUI. For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    private void createGUI() throws Exception {
 //           try {
                //
        algJpanel = new AlgJPanel(); 
        algJpanel.setOpaque(true);
        setContentPane(algJpanel);

    }
}
