package mcsaplugins.route;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ViewNetworkNodePanel extends JPanel
  implements ActionListener
{
  private NetworkNodePanel m_nnp = null;
  private TraceRoute m_tr = null;
  private AppletPanel m_ap;

  public ViewNetworkNodePanel(AppletPanel paramAppletPanel)
  {
    try
    {
      this.m_ap = paramAppletPanel;
      this.m_nnp = new NetworkNodePanel();
      this.m_nnp.addActionListener(this);
      setLayout(new GridBagLayout());
      U.gbAdd(this, this.m_nnp, 0, 0, "x1y1f3");
      setBackground(Color.white);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Object localObject = paramActionEvent.getSource();
    String str = paramActionEvent.getActionCommand();
    if (((localObject != this.m_nnp) || (!"UPDATE".equals(str))) && (localObject == this.m_nnp) && ("USER_SELECT".equals(str)))
    {
      TraceRoute localTraceRoute = this.m_nnp.getHopsSummary(false, true);
      this.m_ap.setHighlight(localTraceRoute);
    }
  }

  public void reset()
  {
    this.m_nnp.reset();
  }

  public void setHighlight(TraceRoute paramTraceRoute)
  {
    this.m_nnp.setHighlight(paramTraceRoute);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_tr = paramTraceRoute;
    this.m_nnp.addRoute(this.m_tr);
  }

  public void updateData()
  {
    setTraceRoute(this.m_tr);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.ViewNetworkNodePanel
 * JD-Core Version:    0.6.2
 */