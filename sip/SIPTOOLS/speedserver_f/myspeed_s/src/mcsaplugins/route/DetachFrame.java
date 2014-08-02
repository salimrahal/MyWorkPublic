package mcsaplugins.route;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class DetachFrame extends JFrame
  implements WindowListener
{
  private AppletPanel m_panel;
  private route m_r;

  public DetachFrame(AppletPanel paramAppletPanel, route paramroute)
  {
    this.m_panel = paramAppletPanel;
    this.m_r = paramroute;
    setTitle(U.text("detachtitle"));
    Container localContainer = getContentPane();
    localContainer.setLayout(new GridBagLayout());
    U.gbAdd(localContainer, this.m_panel, 1, 1, "x1y1f3");
    addWindowListener(this);
    setSize(750, 550);
    setVisible(true);
  }

  private void close()
  {
    setVisible(false);
    removeAll();
    dispose();
    this.m_r.attach();
  }

  public void windowClosing(WindowEvent paramWindowEvent)
  {
    close();
  }

  public void windowActivated(WindowEvent paramWindowEvent)
  {
  }

  public void windowClosed(WindowEvent paramWindowEvent)
  {
  }

  public void windowDeactivated(WindowEvent paramWindowEvent)
  {
  }

  public void windowDeiconified(WindowEvent paramWindowEvent)
  {
  }

  public void windowIconified(WindowEvent paramWindowEvent)
  {
  }

  public void windowOpened(WindowEvent paramWindowEvent)
  {
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.DetachFrame
 * JD-Core Version:    0.6.2
 */