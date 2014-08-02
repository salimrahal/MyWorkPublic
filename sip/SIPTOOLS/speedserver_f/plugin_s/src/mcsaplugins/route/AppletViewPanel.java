package mcsaplugins.route;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.JPanel;

public class AppletViewPanel extends SmoothUpdatePanel
{
  private String m_tabname;
  private Image m_iTab = null;
  private JPanel m_panel;

  public AppletViewPanel(String paramString, JPanel paramJPanel)
  {
    this.m_tabname = paramString;
    this.m_panel = paramJPanel;
    setBackground(Color.white);
    setLayout(new GridBagLayout());
    U.gbAdd(this, this.m_panel, 1, 1, "x1y1f3i1t4");
  }

  public Image getTabImage()
  {
    return this.m_iTab;
  }

  public JPanel getTabPanel()
  {
    return this.m_panel;
  }

  public String getTabName()
  {
    return this.m_tabname;
  }

  public void smoothPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    paramGraphics.setColor(new Color(9286371));
    paramGraphics.drawRect(0, -1, i - 1, j);
    paramGraphics.setColor(new Color(15397628));
    paramGraphics.drawLine(1, 0, i - 2, 1);
    paramGraphics.drawLine(2, 0, i - 2, 2);
    paramGraphics.drawLine(3, 0, i - 2, 3);
    paramGraphics.setColor(new Color(15857661));
    paramGraphics.drawLine(4, 1, i - 2, 4);
    paramGraphics.setColor(new Color(16317438));
    paramGraphics.drawLine(5, 2, i - 2, 5);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.AppletViewPanel
 * JD-Core Version:    0.6.2
 */