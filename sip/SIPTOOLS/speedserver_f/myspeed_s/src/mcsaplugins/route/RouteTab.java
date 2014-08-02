package mcsaplugins.route;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.ErrorCode;

public class RouteTab extends AppletTab
  implements MouseListener
{
  private route m_r = null;
  private AppletPanel m_apPanel = null;
  private boolean m_bDetached = false;
  private Vector m_hits = new Vector();

  public RouteTab(route paramroute, String paramString, Image paramImage)
  {
    super(paramroute.getParentApplet(), paramroute, paramImage, paramString);
    this.m_r = paramroute;
  }

  public void postStartEvent(Component paramComponent)
  {
    fireActionEvent(new ActionEvent(paramComponent, 1001, isDetached() ? "start_noFocusRequest" : "start"));
  }

  public void initAppPanel()
  {
    if (this.m_apPanel == null)
      return;
    this.m_apPanel.doFirstTimeInit();
  }

  public void doFirstTimeInit()
  {
    setLayout(new GridBagLayout());
    try
    {
      this.m_apPanel = new AppletPanel(this.m_r, this, false);
      U.gbAdd(this, this.m_apPanel, 0, 0, "x1y1f3");
      this.m_apPanel.invalidate();
    }
    catch (Exception localException)
    {
      System.out.println("Exception in init:");
      localException.printStackTrace();
    }
    addMouseListener(this);
  }

  public void reset()
  {
    this.m_apPanel.reset();
  }

  public boolean isDetached()
  {
    return this.m_bDetached;
  }

  public void setDetached(boolean paramBoolean)
  {
    this.m_bDetached = paramBoolean;
    this.m_apPanel.setDetached(paramBoolean);
    if (paramBoolean)
    {
      removeAll();
      repaint();
    }
    else
    {
      U.gbAdd(this, this.m_apPanel, 0, 0, "x1y1f3");
      this.m_apPanel.invalidate();
      validate();
    }
  }

  public AppletPanel getAppletPanel()
  {
    return this.m_apPanel;
  }

  public ErrorCode getErrorCode()
  {
    if (this.m_apPanel == null)
      return null;
    return this.m_apPanel.getErrorCode();
  }

  public String getReportMSS()
  {
    if (this.m_apPanel == null)
      return null;
    return this.m_apPanel.getReportMSS();
  }

  public String getDetailReport()
  {
    if (this.m_apPanel == null)
      return null;
    return this.m_apPanel.getDetailReport();
  }

  public void panelPaint(Graphics paramGraphics)
  {
    if (this.m_bDetached)
    {
      this.m_hits.removeAllElements();
      int i = getSize().width;
      int j = getSize().height;
      paramGraphics.setColor(Color.blue);
      int k = paramGraphics.getFontMetrics().getHeight();
      int m = (j - 3 * k) / 2;
      String str = "MyRoute is running in a separate window (click here to view).";
      int n = paramGraphics.getFontMetrics().stringWidth(str);
      paramGraphics.drawString(str, (i - n) / 2, m);
      RectHitRegion localRectHitRegion = new RectHitRegion("SHOW", "SHOW", (i - n) / 2, m - k, (i + n) / 2, m + 5);
      addHit(localRectHitRegion);
      m += 2 * k;
      str = "To reattach close the window or click here";
      n = paramGraphics.getFontMetrics().stringWidth(str);
      paramGraphics.drawString(str, (i - n) / 2, m);
      localRectHitRegion = new RectHitRegion("ATTACH", "ATTACH", (i - n) / 2, m - k, (i + n) / 2, m + 5);
      addHit(localRectHitRegion);
      m += k;
    }
  }

  public void addHit(HitRegion paramHitRegion)
  {
    this.m_hits.addElement(paramHitRegion);
  }

  public void runTest()
  {
    this.m_apPanel.runTest();
  }

  public Image createAppletImage(byte[] paramArrayOfByte)
  {
    Image localImage = paramArrayOfByte == null ? null : getToolkit().createImage(paramArrayOfByte, 0, paramArrayOfByte.length);
    if (localImage != null)
    {
      MediaTracker localMediaTracker = new MediaTracker(this);
      localMediaTracker.addImage(localImage, 1);
      try
      {
        localMediaTracker.waitForAll();
      }
      catch (Exception localException)
      {
      }
    }
    return localImage;
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    if (this.m_bDetached)
    {
      Enumeration localEnumeration = this.m_hits.elements();
      while (localEnumeration.hasMoreElements())
      {
        HitRegion localHitRegion = (HitRegion)localEnumeration.nextElement();
        if (localHitRegion.find(paramMouseEvent.getPoint().x, paramMouseEvent.getPoint().y) != null)
        {
          if ("SHOW".equals(localHitRegion.what()))
            this.m_r.detach();
          else if ("ATTACH".equals(localHitRegion.what()))
            this.m_r.attach();
          return;
        }
      }
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.RouteTab
 * JD-Core Version:    0.6.2
 */