package applet;

/*
 */
import gui.TrfJPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class MainJapplet extends JApplet {

    TrfJPanel trfJpanel;

    //Called when this applet is loaded into the browser.

    @Override
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try {
                        String fontsize = getParameter("fontSize");
                        String cstr = getParameter("cust");
                        System.out.println("cust nme:" + cstr+"/font="+fontsize);
                        //parse the XML config to class VO by passing URI
                        //Alb alb = new Alb();

                        //get the host name
                        String hostname = getCodeBase().getHost();
                        if (hostname == null || hostname.isEmpty()) {
                            hostname = "localhost";
                        }
                        //append http
                        hostname = "http://" + hostname;
                        //String configUri = new StringBuilder().append(getCodeBase()).append(algBo.CONFIG_FILE_NAME).toString();
                        //retrieve the config values and assign the proper values to ALGBo properties
                        //alb.pc(alb.getCU(hostname));
                        crg(cstr);
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
        super.destroy(); //To change body of generated methods, choose Tools | Templates
    }

    /**
     * Create the GUI. For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    private void crg(String cstr) throws Exception {
 //           try {
        //
        trfJpanel = new TrfJPanel(cstr);
        trfJpanel.setOpaque(true);
        setContentPane(trfJpanel);

    }
}
