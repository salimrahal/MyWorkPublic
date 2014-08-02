package mcsaplugins.route;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ViewSummaryPanel extends JPanel
  implements ActionListener
{
  private GraphicRoutePanel m_graph;
  private AnalysisPanel m_analysis;
  private MapPanel m_map;
  private NetworkNodePanel m_nnp;
  private AppletPanel m_appPanel;

  public ViewSummaryPanel(route paramroute, AppletPanel paramAppletPanel)
  {
    this.m_graph = new GraphicRoutePanel(paramAppletPanel, true);
    this.m_analysis = new AnalysisPanel(paramAppletPanel, true);
    this.m_map = new MapPanel(paramroute, paramAppletPanel, true);
    this.m_nnp = new NetworkNodePanel();
    this.m_appPanel = paramAppletPanel;
    this.m_nnp.addActionListener(this);
    setLayout(new GridBagLayout());
    if (G.getParis())
      U.gbAdd(this, new SummarySectionPanel(this.m_nnp, U.text("netnode")), 1, 0, "x1y2f3w2b3r5l5t5");
    U.gbAdd(this, new SummarySectionPanel(this.m_graph, U.text("graph")), 1, 1, "x1y3f3w2b3r5l5t5");
    U.gbAdd(this, new SummarySectionPanel(this.m_analysis, U.text("analysis")), 1, 2, "x1y2f3r3t2b5l5");
    U.gbAdd(this, new SummarySectionPanel(this.m_map, U.text("map")), 2, 2, "x1y2f3l2b5r5t2");
    setBackground(Color.white);
  }

  public void setHighlight(TraceRoute paramTraceRoute)
  {
    this.m_nnp.setHighlight(paramTraceRoute);
    this.m_graph.setTraceRoute(paramTraceRoute);
    this.m_analysis.setTraceRoute(paramTraceRoute);
    this.m_map.setTraceRoute(paramTraceRoute);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    if (G.getParis())
    {
      this.m_nnp.addRoute(paramTraceRoute);
    }
    else
    {
      this.m_graph.setTraceRoute(paramTraceRoute);
      this.m_analysis.setTraceRoute(paramTraceRoute);
      this.m_map.setTraceRoute(paramTraceRoute);
    }
  }

  public void reset()
  {
    if (G.getParis())
      this.m_nnp.reset();
    this.m_graph.setTraceRoute(null);
    this.m_analysis.setTraceRoute(null);
    this.m_map.setTraceRoute(null);
  }

  public void setPortInfo(PortInfo paramPortInfo)
  {
    this.m_analysis.setPortInfo(paramPortInfo);
  }

  public void setDNSTime(int paramInt)
  {
    this.m_analysis.setDNSTime(paramInt);
  }

  public void updateData()
  {
    this.m_graph.repaint();
    this.m_analysis.repaint();
    this.m_map.repaint();
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Object localObject = paramActionEvent.getSource();
    String str = paramActionEvent.getActionCommand();
    TraceRoute localTraceRoute;
    if ((localObject == this.m_nnp) && ("UPDATE".equals(str)))
    {
      localTraceRoute = this.m_nnp.getHopsSummary(false, true);
      if (localTraceRoute != null)
      {
        this.m_graph.setTraceRoute(localTraceRoute);
        this.m_analysis.setTraceRoute(localTraceRoute);
        this.m_map.setTraceRoute(localTraceRoute);
        this.m_appPanel.updateSomePanels(localTraceRoute);
      }
    }
    else if ((localObject == this.m_nnp) && ("USER_SELECT".equals(str)))
    {
      localTraceRoute = this.m_nnp.getHopsSummary(false, true);
      if (localTraceRoute != null)
      {
        this.m_graph.setTraceRoute(localTraceRoute);
        this.m_analysis.setTraceRoute(localTraceRoute);
        this.m_map.setTraceRoute(localTraceRoute);
        this.m_appPanel.updateSomePanels(localTraceRoute);
        this.m_appPanel.getNetViewPanel().setHighlight(localTraceRoute);
      }
    }
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.ViewSummaryPanel
 * JD-Core Version:    0.6.2
 */