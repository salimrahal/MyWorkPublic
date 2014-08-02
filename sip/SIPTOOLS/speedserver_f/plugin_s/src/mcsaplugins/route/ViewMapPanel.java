package mcsaplugins.route;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class ViewMapPanel extends JPanel
{
  private MapPanel m_map;

  public ViewMapPanel(route paramroute, AppletPanel paramAppletPanel)
  {
    this.m_map = new MapPanel(paramroute, paramAppletPanel, false);
    setLayout(new GridBagLayout());
    U.gbAdd(this, this.m_map, 1, 1, "x1y1f3i5");
    setBackground(Color.white);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_map.setTraceRoute(paramTraceRoute);
  }

  public void updateData()
  {
    this.m_map.repaint();
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.ViewMapPanel
 * JD-Core Version:    0.6.2
 */