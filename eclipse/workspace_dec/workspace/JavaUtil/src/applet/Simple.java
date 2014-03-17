package applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.TextArea;
/**
 * Note:In this example, the Applet class is extended,
 * not the Swing JApplet class, as Swing components do not need to be added to this applet. 
 * Note: Unlike Java applications, applets do not need to implement a main method.
 * @author salim
 *
 */
//No need to extend JApplet, since we don't add any components;
//we just paint.
public class Simple extends Applet {
	 java.awt.Window Window;
	 java.awt.Panel panel;
	StringBuffer buffer;

	public void init() {
		buffer = new StringBuffer();
		addItem("initializing... ");
	}

	public void start() {
		addItem("starting... ");
	}

	public void stop() {
		addItem("stopping... ");
	}

	public void destroy() {
		addItem("(destroy)preparing for unloading...");
	}

	private void addItem(String newWord) {
		System.out.println(newWord);
		buffer.append(newWord);
		repaint();
	}

	public void paint(Graphics grph) {
		// Draw a Rectangle around the applet's display area.
		grph.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

		// Draw the current string inside the rectangle.
		grph.drawString(buffer.toString(), 10, 15);
		grph.setColor(Color.red.green.yellow.red.cyan);
		grph.drawLine(0, 0, 100,100);
		//
	    int fontSize = 12;
		int fontStyle = Font.BOLD;
		String fontName = "Monospaced";
		Font font = new Font(fontName, fontStyle, fontSize);
		//FontMetrics  fm = new FontMetrics(font);
		int rows = 10;
		int columns = 20;
		TextArea ta =new TextArea(rows, columns);
	}
}