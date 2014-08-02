package mcsaplugins.route;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class ViewGraphPanel extends JPanel
{
  private GraphicRoutePanel m_graph;

  public ViewGraphPanel(AppletPanel paramAppletPanel)
  {
    this.m_graph = new GraphicRoutePanel(paramAppletPanel, false);
    setLayout(new GridBagLayout());
    U.gbAdd(this, this.m_graph, 1, 1, "x1y1f3i5");
    setBackground(Color.white);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_graph.setTraceRoute(paramTraceRoute);
  }

  public void updateData()
  {
    this.m_graph.repaint();
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.ViewGraphPanel
 * JD-Core Version:    0.6.2
 */