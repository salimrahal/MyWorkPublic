package applet.swingTest;

import java.awt.List;

import javax.swing.JApplet;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
/** install the package for applet to run applet under the browser
 * sudo apt-install icedtea-7-plugin
 * @author salim
 *
 */
public class HelloWorld extends JApplet {
	// Called when this applet is loaded into the browser.
	public void init() {
		// Execute a job on the event-dispatching thread; creating this applet's
		// GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JLabel lbl = new JLabel("Hello World -Jlabel");
					add(lbl);
//					JCheckBox jcheck = new JCheckBox("Checkbox Component");
//					add(jcheck);
					List l =new List(3, false);
					//getting the size of this component
					System.out.println(l.getSize());
				}
			});
		} catch (Exception e) {
			System.err.println("createGUI didn't complete successfully");
		}
	}
}