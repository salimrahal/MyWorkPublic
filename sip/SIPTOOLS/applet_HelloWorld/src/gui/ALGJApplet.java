/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JLabel;

/**
 *
 * @author salim
 */
public class ALGJApplet extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    AlgJPanel algJpanel;

    public void init() {
        // TODO start asynchronous download of heavy resources
        try {
            /* Create and display the form */
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("create GUI didn't complete successfully");
        }
    }

    /**
     * Create the GUI. For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    private void createGUI() {
        JLabel lbl = new JLabel("Hello World heeheheh");
        add(lbl);
        algJpanel = new AlgJPanel(); 
        algJpanel.setOpaque(true);
       setContentPane(algJpanel);

    }
}
