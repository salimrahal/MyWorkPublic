package mcsaplugins.route;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class ViewTablePanel extends JPanel
{
  private TablePanel m_table;

  public ViewTablePanel(AppletPanel paramAppletPanel)
  {
    this.m_table = new TablePanel(paramAppletPanel);
    setLayout(new GridBagLayout());
    U.gbAdd(this, this.m_table, 1, 1, "x1y1f3i5");
    setBackground(Color.white);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_table.setTraceRoute(paramTraceRoute);
  }

  public void updateData()
  {
    this.m_table.repaint();
  }

  public String snap()
  {
    return this.m_table.snap();
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.ViewTablePanel
 * JD-Core Version:    0.6.2
 */