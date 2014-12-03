package algApplet;

/*
 */
import algBo.Alb;
import algcr.Cc;
import algGui.AlgJPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
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
                        String fontsize = getParameter("fontSize");
                        String cstr = getParameter("cust");
                        crg(cstr);
                        Alb alb = new Alb();

                        String hostname = getCodeBase().getHost();
                        if (hostname == null || hostname.isEmpty()) {
                            hostname = "localhost";
                        }
                        hostname = "http://" + hostname;
                        alb.pc(alb.getCU(hostname));
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
        Cc sipClient = AlgJPanel.getCc();
        if (sipClient != null) {//Do something
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
    private void crg(String custname) throws Exception {

        algJpanel = new AlgJPanel();
        algJpanel.setCustnm(custname);
        algJpanel.setOpaque(true);
        setContentPane(algJpanel);

    }
}
