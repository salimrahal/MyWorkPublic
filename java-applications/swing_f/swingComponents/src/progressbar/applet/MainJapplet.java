package progressbar.applet;

/*
 */
import java.net.URL;
import java.text.ParseException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import progressbar.FormProgressJpanel;
import progressbar.FormProgressJpanel1;

public class MainJapplet extends JApplet {

  //  FormProgressJpanel1 formJpanel;
     FormProgressJpanel formJpanel;

    //Called when this applet is loaded into the browser.
    @Override
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try {
                        createGUI();
                    } catch (Exception ex) {
                        Logger.getLogger(MainJapplet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            );
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }

    @Override
    public void destroy() {
        System.out.println("Final cleanup ....destroy...");
        super.destroy(); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Create the GUI. For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    private void createGUI() throws Exception {
        //           try {
        //
        formJpanel = new FormProgressJpanel();
        //formJpanel = new FormProgressJpanel1();
        formJpanel.setOpaque(true);
        setContentPane(formJpanel);

    }
}
