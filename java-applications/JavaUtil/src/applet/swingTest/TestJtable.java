package applet.swingTest;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

public class TestJtable extends JApplet {

	final Runnable doHelloWorld = new Runnable() {
		public void run() {
			//we can Add component to the applet here ...Jpanel
			//TableModel jm = TableModel();
			JTable jt = new JTable();
		
		}
	};

	// Called when this applet is loaded into the browser.
	public void init() {
		Thread appThread = new Thread() {
			public void run() {
				try {
					SwingUtilities.invokeAndWait(doHelloWorld);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Finished on " + Thread.currentThread());
			}
		};
		appThread.start();
	}
}
