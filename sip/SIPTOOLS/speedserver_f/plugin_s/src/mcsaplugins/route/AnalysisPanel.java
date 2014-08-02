package mcsaplugins.route;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JScrollBar;

public class AnalysisPanel extends SmoothUpdatePanel
  implements AdjustmentListener, MouseListener, MouseMotionListener
{
  private AppletPanel m_appPanel;
  private TraceRoute m_tr;
  private PortInfo m_pi;
  private int m_dns = -1;
  private Image m_iGood;
  private Image m_iWarn;
  private Image m_iBad;
  private boolean m_bSummary;
  private JScrollBar m_vScroll;

  public AnalysisPanel(AppletPanel paramAppletPanel, boolean paramBoolean)
  {
    this.m_appPanel = paramAppletPanel;
    this.m_bSummary = paramBoolean;
    this.m_iGood = U.getImage("good.gif");
    this.m_iWarn = U.getImage("warn.gif");
    this.m_iBad = U.getImage("bad.gif");
    this.m_vScroll = new JScrollBar();
    this.m_vScroll.setVisible(false);
    this.m_vScroll.addAdjustmentListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    setBackground(Color.white);
    setLayout(new GridBagLayout());
    U.gbAdd(this, this.m_vScroll, 1, 1, "x1y1f2a6");
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_tr = paramTraceRoute;
    repaint();
  }

  public void setPortInfo(PortInfo paramPortInfo)
  {
    this.m_pi = paramPortInfo;
    repaint();
  }

  public void setDNSTime(int paramInt)
  {
    this.m_dns = paramInt;
    repaint();
  }

  public void smoothPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    int k = this.m_iGood.getWidth(null);
    int m = this.m_iGood.getHeight(null);
    if (k < 0)
      k = 24;
    if (m < 0)
      m = 24;
    int n = 5;
    Point localPoint = new Point();
    if ((this.m_tr != null) && (this.m_tr.hops != null))
    {
      if (this.m_vScroll.isVisible())
      {
        localPoint.y += -this.m_vScroll.getValue();
        paramGraphics.translate(0, localPoint.y);
        i -= this.m_vScroll.getSize().width;
      }
      TraceRouteHop[] arrayOfTraceRouteHop = this.m_tr.hops;
      int i1 = this.m_tr.hops.length;
      int i2 = 0;
      int i3 = 0;
      FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
      int i4 = -localFontMetrics.getDescent();
      String str1 = i1;
      int i5 = (i1 > 0) && (arrayOfTraceRouteHop[(i1 - 1)].loss < 100) && (arrayOfTraceRouteHop[(i1 - 1)].ms >= 0) ? 1 : 0;
      if (i5 == 0)
        str1 = U.text("atleast", str1);
      if ((i5 == 0) && (i1 <= 2))
      {
        i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("nonumhops"), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
      }
      else if (i1 >= G.getBadNumHops())
      {
        i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("badnumhops", str1), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
      }
      else if (i1 >= G.getWarnNumHops())
      {
        i3 = Math.max(this.m_iWarn.getHeight(null), drawWrappedText(paramGraphics, U.text("warnnumhops", str1), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iWarn, 0, i2 - i4, null);
      }
      else
      {
        i3 = Math.max(this.m_iGood.getHeight(null), drawWrappedText(paramGraphics, U.text("goodnumhops", str1), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iGood, 0, i2 - i4, null);
      }
      i2 += i3;
      int i6 = -1;
      int i7 = 0;
      int i8 = 0;
      int i9 = -1;
      for (int i10 = 0; i10 < arrayOfTraceRouteHop.length; i10++)
      {
        i6 = Math.max(arrayOfTraceRouteHop[i10].ms, i6);
        i9 = Math.max((i10 != 0) && (arrayOfTraceRouteHop[i10].ms == -1) ? 100 : arrayOfTraceRouteHop[i10].loss, i9);
        i7 += (arrayOfTraceRouteHop[i10].ms < 0 ? 0 : 1);
        i8 += (arrayOfTraceRouteHop[i10].ms < 0 ? 0 : arrayOfTraceRouteHop[i10].ms);
      }
      if (i6 == -1)
      {
        i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("nomaxresponse"), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
      }
      else if (i6 >= G.getBadMaxResponse())
      {
        i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("badmaxresponse", i6), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
      }
      else if (i6 >= G.getWarnMaxResponse())
      {
        i3 = Math.max(this.m_iWarn.getHeight(null), drawWrappedText(paramGraphics, U.text("warnmaxresponse", i6), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iWarn, 0, i2 - i4, null);
      }
      else
      {
        i3 = Math.max(this.m_iGood.getHeight(null), drawWrappedText(paramGraphics, U.text("goodmaxresponse", i6), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iGood, 0, i2 - i4, null);
      }
      i2 += i3;
      i10 = i7 > 0 ? i8 / i7 : -1;
      if (i10 == -1)
      {
        i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("noavgresponse"), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
      }
      else if (i10 >= G.getBadAvgResponse())
      {
        i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("badavgresponse", i10), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
      }
      else if (i10 >= G.getWarnAvgResponse())
      {
        i3 = Math.max(this.m_iWarn.getHeight(null), drawWrappedText(paramGraphics, U.text("warnavgresponse", i10), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iWarn, 0, i2 - i4, null);
      }
      else
      {
        i3 = Math.max(this.m_iGood.getHeight(null), drawWrappedText(paramGraphics, U.text("goodavgresponse", i10), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iGood, 0, i2 - i4, null);
      }
      i2 += i3;
      if (i9 == -1)
      {
        i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("nomaxloss"), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
      }
      else if (i9 >= G.getBadMaxLoss())
      {
        i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("badmaxloss", i9), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
      }
      else if (i9 >= G.getWarnMaxLoss())
      {
        i3 = Math.max(this.m_iWarn.getHeight(null), drawWrappedText(paramGraphics, U.text("warnmaxloss", i9), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iWarn, 0, i2 - i4, null);
      }
      else
      {
        i3 = Math.max(this.m_iGood.getHeight(null), drawWrappedText(paramGraphics, U.text("goodmaxloss", i9), k + n, i2, i - k - n, 1.0F));
        paramGraphics.drawImage(this.m_iGood, 0, i2 - i4, null);
      }
      i2 += i3;
      if (this.m_dns >= 0)
      {
        if (this.m_dns <= 2)
        {
          i3 = Math.max(this.m_iGood.getHeight(null), drawWrappedText(paramGraphics, U.text("instantdnstime"), k + n, i2, i - k - n, 1.0F));
          paramGraphics.drawImage(this.m_iGood, 0, i2 - i4, null);
        }
        else if (this.m_dns >= G.getWarnDNSTime())
        {
          i3 = Math.max(this.m_iWarn.getHeight(null), drawWrappedText(paramGraphics, U.text("warndnstime", this.m_dns), k + n, i2, i - k - n, 1.0F));
          paramGraphics.drawImage(this.m_iWarn, 0, i2 - i4, null);
        }
        else if (this.m_dns >= G.getBadDNSTime())
        {
          i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("baddnstime", this.m_dns), k + n, i2, i - k - n, 1.0F));
          paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
        }
        else
        {
          i3 = Math.max(this.m_iGood.getHeight(null), drawWrappedText(paramGraphics, U.text("gooddnstime", this.m_dns), k + n, i2, i - k - n, 1.0F));
          paramGraphics.drawImage(this.m_iGood, 0, i2 - i4, null);
        }
        i2 += i3;
      }
      if (this.m_pi != null)
      {
        String[] arrayOfString = { this.m_pi.protocol, this.m_pi.port, this.m_pi.desc };
        if (this.m_pi.bConnected)
        {
          String str2 = this.m_pi.desc == null ? "connected" : "connectedserver";
          i3 = Math.max(this.m_iGood.getHeight(null), drawWrappedText(paramGraphics, U.text(str2, arrayOfString), k + n, i2, i - k - n, 1.0F));
          paramGraphics.drawImage(this.m_iGood, 0, i2 - i4, null);
        }
        else if (this.m_pi.bRefused)
        {
          i3 = Math.max(this.m_iWarn.getHeight(null), drawWrappedText(paramGraphics, U.text("refused", arrayOfString), k + n, i2, i - k - n, 1.0F));
          paramGraphics.drawImage(this.m_iWarn, 0, i2 - i4, null);
        }
        else
        {
          i3 = Math.max(this.m_iBad.getHeight(null), drawWrappedText(paramGraphics, U.text("didnotconnect", arrayOfString), k + n, i2, i - k - n, 1.0F));
          paramGraphics.drawImage(this.m_iBad, 0, i2 - i4, null);
        }
        i2 += i3;
      }
      for (int i11 = 0; i11 < i1; i11++)
        if (isErratic(this.m_tr.hops[i11]))
        {
          i3 = Math.max(this.m_iWarn.getHeight(null), drawWrappedText(paramGraphics, U.text("warnerratic", new String[] { this.m_tr.hops[i11].hop, this.m_tr.hops[i11].minMs, this.m_tr.hops[i11].maxMs }), k + n, i2, i - k - n, 1.0F));
          paramGraphics.drawImage(this.m_iWarn, 0, i2 - i4, null);
          i2 += i3;
        }
      if (i2 > j != this.m_vScroll.isVisible())
      {
        this.m_vScroll.setVisible(i2 > j);
        if (i2 <= j)
          this.m_vScroll.setValue(0);
        validate();
        repaint();
      }
      if (i2 > j)
      {
        this.m_vScroll.setMaximum(i2);
        this.m_vScroll.setMinimum(0);
        this.m_vScroll.setVisibleAmount(j);
      }
    }
    else
    {
      drawWrappedText(paramGraphics, "<font color=#808080>" + U.text("enterhost") + "</font>", 0, 0, i, 1.0F);
    }
    paramGraphics.translate(-localPoint.x, -localPoint.y);
  }

  private boolean isErratic(TraceRouteHop paramTraceRouteHop)
  {
    if ((paramTraceRouteHop == null) || (paramTraceRouteHop.ms == -1))
      return false;
    int i = G.getErraticMS();
    int j = G.getErraticPercent();
    if ((i == 0) || (j == 0))
      return false;
    return (paramTraceRouteHop.maxMs - paramTraceRouteHop.minMs > i) && (100 * (paramTraceRouteHop.maxMs - paramTraceRouteHop.minMs) / Math.max(1, paramTraceRouteHop.minMs) > j);
  }

  public Dimension getMinimumSize()
  {
    return new Dimension(50, 50);
  }

  public Dimension getPreferredSize()
  {
    return getMinimumSize();
  }

  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
  {
    repaint();
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    if (this.m_bSummary)
      this.m_appPanel.setView("analysis");
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    if (this.m_bSummary)
      setCursor(new Cursor(12));
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(0));
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.AnalysisPanel
 * JD-Core Version:    0.6.2
 */