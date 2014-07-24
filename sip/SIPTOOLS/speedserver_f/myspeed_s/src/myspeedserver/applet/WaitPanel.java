package myspeedserver.applet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class WaitPanel extends JPanel
{
  private static final int IF = 15;
  private int FF = -1;
  private int HF;
  private String GF;

  public void setLabel(String paramString)
  {
    this.GF = paramString;
  }

  public void setWait(int paramInt)
  {
    this.HF = paramInt;
  }

  public void doWait()
  {
    long l1 = System.currentTimeMillis();
    long l2;
    while ((l2 = System.currentTimeMillis()) < l1 + this.HF)
    {
      Object localObject;
      this.FF = ((int)(localObject - l1) * 100 / this.HF);
      try
      {
        Thread.sleep(50L);
      }
      catch (Exception localException)
      {
      }
      repaint();
    }
  }

  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    int i = getSize().width;
    int j = getSize().height;
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    paramGraphics.setColor(Color.blue);
    paramGraphics.drawRect(0, 0, i - 1, j - 1);
    paramGraphics.setColor(new Color(15658751));
    paramGraphics.fillRect(1, 1, i - 2, j - 2);
    paramGraphics.setColor(Color.black);
    int k = localFontMetrics.stringWidth(this.GF);
    paramGraphics.drawString(this.GF, 5, 13 - localFontMetrics.getHeight() / 2 + localFontMetrics.getAscent());
    paramGraphics.setColor(new Color(190, 190, 145));
    paramGraphics.fillRect(5 + k + 5, j / 2 - 7, this.FF, 15);
    paramGraphics.setColor(new Color(190, 190, 145).darker());
    paramGraphics.drawRect(5 + k + 5, j / 2 - 7, 100, 15);
  }

  public Dimension getPreferredSize()
  {
    int i = this.GF.length() * 15;
    try
    {
      i = getGraphics().getFontMetrics().stringWidth(this.GF);
    }
    catch (Exception localException)
    {
    }
    return new Dimension(i + 10 + (this.HF > 0 ? 105 : 0), 26);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.WaitPanel
 * JD-Core Version:    0.6.2
 */