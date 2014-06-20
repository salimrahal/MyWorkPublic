/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package progressbar.changeListener;

/**
 *
 * @author salim
 * the below model doesn't update the UI in parallele with a background model
 */
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class BoundedChangeListener implements ChangeListener {
  public void stateChanged(ChangeEvent changeEvent) {
    Object source = changeEvent.getSource();
    if (source instanceof JProgressBar) {
      JProgressBar theJProgressBar = (JProgressBar) source;
      System.out.println("ProgressBar changed: " + theJProgressBar.getValue());
    } else {
      System.out.println("Something changed: " + source);
    }
  }
}

public class ProgressBarStepBoundedChangeListener {

  public static void main(String args[]) throws Exception {
    JFrame frame = new JFrame("Stepping Progress");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final JProgressBar aJProgressBar = new JProgressBar(JProgressBar.VERTICAL);
    aJProgressBar.setStringPainted(true);

    aJProgressBar.addChangeListener(new BoundedChangeListener());

    for (int i = 0; i < 50; i++) {
      aJProgressBar.setValue(i++);
      Thread.sleep(100);
    }

    frame.add(aJProgressBar, BorderLayout.NORTH);
    frame.setSize(300, 200);
    frame.setVisible(true);
  }
}