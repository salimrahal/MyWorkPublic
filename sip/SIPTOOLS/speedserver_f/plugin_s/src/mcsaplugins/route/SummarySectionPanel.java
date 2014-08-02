package mcsaplugins.route;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class SummarySectionPanel extends JPanel
{
  private String m_name;
  private final int TITLEHEIGHT;
  private Font m_titlefont;

  public SummarySectionPanel(JPanel paramJPanel, String paramString)
  {
    this.m_name = paramString;
    this.m_titlefont = new Font("Helvetica", 1, 9);
    FontMetrics localFontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(this.m_titlefont);
    this.TITLEHEIGHT = localFontMetrics.getHeight();
    setLayout(new GridBagLayout());
    U.gbAdd(this, paramJPanel, 1, 1, "x1y1f3r2t" + (this.TITLEHEIGHT + 2));
  }

  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    int i = getSize().width;
    int j = getSize().height;
    int k = 16;
    paramGraphics.setFont(new Font("Helvetica", 1, 9));
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    k = localFontMetrics.getHeight();
    U.horzGradientFill(paramGraphics, 0, 0, i, k, new Color(14871283), Color.white);
    paramGraphics.setColor(new Color(14737632));
    paramGraphics.drawRect(-1, 0, i, j);
    paramGraphics.setColor(new Color(10988210));
    paramGraphics.drawString(this.m_name, 5, k - localFontMetrics.getDescent());
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.SummarySectionPanel
 * JD-Core Version:    0.6.2
 */