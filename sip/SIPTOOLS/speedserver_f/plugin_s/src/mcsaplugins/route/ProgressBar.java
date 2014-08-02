package mcsaplugins.route;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ProgressBar extends SmoothUpdatePanel
  implements Runnable
{
  private long m_lHalfLife = 30L;
  private double m_lLambda = 0.0D;
  private int m_percent = 0;
  private String m_label;
  private long m_lStart;
  private long m_lWait = 0L;
  private boolean m_bRunning = false;

  public ProgressBar(String paramString)
  {
    this.m_label = paramString;
    this.m_lLambda = getLambda();
    setOpaque(false);
  }

  public void start()
  {
    this.m_bRunning = true;
    this.m_percent = 0;
    Thread localThread = new Thread(this, "route-PB-" + hashCode());
    localThread.start();
  }

  public void smoothPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    int k = 9;
    paramGraphics.setColor(new Color(33, 33, 148));
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int m = localFontMetrics.stringWidth(this.m_label);
    paramGraphics.drawString(this.m_label, 0, j / 2 + localFontMetrics.getHeight() / 2 - localFontMetrics.getDescent());
    int n = this.m_percent * (i - m - 5) / 100;
    paramGraphics.setColor(new Color(110, 140, 255));
    paramGraphics.fillRoundRect(m + 5, j / 2 - k / 2, n, k, 3, 3);
    paramGraphics.setColor(new Color(33, 33, 148));
    paramGraphics.drawRoundRect(m + 5, j / 2 - k / 2, i - m - 5 - 1, k - 1, 3, 3);
  }

  public void kill()
  {
    this.m_bRunning = false;
  }

  public void run()
  {
    try
    {
      Thread.sleep(this.m_lWait);
    }
    catch (Exception localException1)
    {
    }
    this.m_lStart = System.currentTimeMillis();
    this.m_percent = 0;
    while ((this.m_percent != 100) && (this.m_bRunning))
    {
      this.m_percent = getNextPercent();
      repaint();
      try
      {
        Thread.sleep(200L);
      }
      catch (Exception localException2)
      {
      }
    }
    this.m_percent = 100;
  }

  public int getNextPercent()
  {
    long l = (System.currentTimeMillis() - this.m_lStart) / 1000L;
    double d1 = -1.0D * this.m_lLambda * l;
    double d2 = Math.pow(2.718281828459045D, d1);
    return (int)(100.0D - d2 * 100.0D);
  }

  private double getLambda()
  {
    return Math.log(2.0D) / this.m_lHalfLife;
  }

  public Dimension getPreferredSize()
  {
    return getMinimumSize();
  }

  public Dimension getMinimumSize()
  {
    return new Dimension(100, 15);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.ProgressBar
 * JD-Core Version:    0.6.2
 */