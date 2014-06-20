/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package progressbar;

import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author salim
 */
class BoundedChangeListener implements ChangeListener {
  public void stateChanged(ChangeEvent changeEvent) {
    Object source = changeEvent.getSource();
    if (source instanceof JProgressBar) {
      JProgressBar theJProgressBar = (JProgressBar) source;
      System.out.println("ProgressBar changed: " + theJProgressBar.getValue());
      //theJProgressBar.setValue(theJProgressBar.getValue());//sr
    } else {
      System.out.println("Something changed: " + source);
    }
  }
}
