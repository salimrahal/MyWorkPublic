package algApplet;

/*
 */ 

import algBo.ALGBo;
import algController.ClientController;
import algGui.AlgJPanel;
import java.net.URL;
import java.text.ParseException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class MainJapplet extends JApplet {
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
                       
                        //TODO parse the XML config to class VO by passing URI
                        ALGBo algBo = new ALGBo();
                        //ALGBo.readFile( algBo.CONFIG_FILE_NAME,getCodeBase());
                        //the Confi URI: configUri = http://siptools.safirasoft.com/alg/config.xml
                        String configUri = new StringBuilder().append(getCodeBase()).append(algBo.CONFIG_FILE_NAME).toString();
                        //retrieve the config values and assign the proper values to ALGBo properties
                        algBo.performConfigParsing(configUri);
                    } catch (Exception ex) {
                        Logger.getLogger(MainJapplet.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MainJapplet.class.getName()).log(Level.SEVERE, null, ex);
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
