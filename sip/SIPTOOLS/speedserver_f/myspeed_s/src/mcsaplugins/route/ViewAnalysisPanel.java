package mcsaplugins.route;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class ViewAnalysisPanel extends JPanel
{
  private AnalysisPanel m_analysis;

  public ViewAnalysisPanel(AppletPanel paramAppletPanel)
  {
    this.m_analysis = new AnalysisPanel(paramAppletPanel, false);
    setLayout(new GridBagLayout());
    U.gbAdd(this, this.m_analysis, 1, 1, "x1y1f3i5");
    setBackground(Color.white);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_analysis.setTraceRoute(paramTraceRoute);
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
    this.m_analysis.repaint();
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.ViewAnalysisPanel
 * JD-Core Version:    0.6.2
 */