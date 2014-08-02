package mcsaplugins.route;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import javax.swing.JScrollBar;

public class TablePanel extends SmoothUpdatePanel
  implements AdjustmentListener, MouseListener, MouseMotionListener
{
  private static Image IM_EXPAND = route.getRoute() == null ? null : route.getRoute().getAppletImage("zoomup.gif");
  private static Image IM_COLLAPSE = route.getRoute() == null ? null : route.getRoute().getAppletImage("zoomdown.gif");
  private static final String[] g_cols = { "hop", "loss", "ip", "nname", "loc", "ms", "graph", "net" };
  private String[] m_colNames;
  private int[] m_colWidths = { 35, 50, 100, 100, 100, 50, 50, 100 };
  private boolean[] m_visibleCols = { true, true, true, true, true, true, true, true };
  private TraceRoute m_tr;
  private JScrollBar m_scroll;
  private Vector m_collapsed = new Vector();
  private AppletPanel m_appPanel;
  private Point __down;
  private int __downCol = -1;
  private int __downColWidth = -1;

  public TablePanel(AppletPanel paramAppletPanel)
  {
    this.m_appPanel = paramAppletPanel;
    this.m_colNames = new String[g_cols.length];
    for (int i = 0; i < g_cols.length; i++)
      this.m_colNames[i] = U.text("col" + g_cols[i]);
    this.m_scroll = new JScrollBar();
    this.m_scroll.addAdjustmentListener(this);
    setLayout(new GridBagLayout());
    U.gbAdd(this, this.m_scroll, 1, 1, "x1y1a6f2");
    addMouseListener(this);
    addMouseMotionListener(this);
    setBackground(Color.white);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_tr = paramTraceRoute;
    repaint();
  }

  public String snap()
  {
    if ((this.m_tr == null) || (this.m_tr.hops == null))
      return "";
    int i = this.m_tr.hops.length;
    int j = 0;
    for (int k = 0; k < i; k++)
      j = Math.max(this.m_tr.hops[k].maxMs, j);
    StringBuffer localStringBuffer = new StringBuffer();
    int[] arrayOfInt = new int[g_cols.length];
    String[][] arrayOfString = new String[g_cols.length][i];
    int m = 0;
    for (int n = 0; n < g_cols.length; n++)
    {
      for (i1 = -1; i1 < i; i1++)
        if (i1 >= 0)
        {
          if ("graph".equals(g_cols[n]))
            arrayOfString[n][i1] = getGraphData(this.m_tr.hops[i1], j, 10);
          else
            arrayOfString[n][i1] = dataFor(n, this.m_tr.hops[i1]);
          arrayOfInt[n] = (this.m_visibleCols[n] != 0 ? Math.max(arrayOfInt[n], arrayOfString[n][i1] == null ? 0 : arrayOfString[n][i1].length()) : 0);
        }
        else
        {
          arrayOfInt[n] = this.m_colNames[n].length();
        }
      m += arrayOfInt[n] + 2;
      if (n > 0)
        m++;
    }
    m += 2;
    String str = "";
    for (int i1 = 0; i1 < m; i1++)
      str = str + "-";
    localStringBuffer.append(str + "\n");
    for (i1 = -1; i1 < arrayOfString[0].length; i1++)
    {
      for (int i2 = 0; i2 < arrayOfString.length; i2++)
        if (arrayOfInt[i2] > 0)
        {
          if (i2 > 0)
            localStringBuffer.append(" ");
          localStringBuffer.append("| ");
          localStringBuffer.append(U.pad(i1 == -1 ? this.m_colNames[i2] : arrayOfString[i2][i1], arrayOfInt[i2]));
        }
      localStringBuffer.append(" |\n");
      if (i1 == -1)
        localStringBuffer.append(str + "\n");
    }
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }

  private String getGraphData(TraceRouteHop paramTraceRouteHop, int paramInt1, int paramInt2)
  {
    if ((paramInt2 == 0) || (paramTraceRouteHop.ms == -1))
      return "";
    char[] arrayOfChar = new char[paramInt2];
    for (int i = 0; i < paramInt2; i++)
      arrayOfChar[i] = ' ';
    i = paramTraceRouteHop.minMs * (paramInt2 - 1) / paramInt1;
    int j = paramTraceRouteHop.maxMs * (paramInt2 - 1) / paramInt1;
    int k = paramTraceRouteHop.ms * (paramInt2 - 1) / paramInt1;
    for (int m = i; m <= j; m++)
      arrayOfChar[m] = '-';
    arrayOfChar[k] = '+';
    return new String(arrayOfChar, 0, paramInt2);
  }

  public void smoothPaint(Graphics paramGraphics)
  {
    try
    {
      int i = getSize().width - (this.m_scroll.isVisible() ? this.m_scroll.getSize().width : 0);
      int j = getSize().height;
      FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
      int k = localFontMetrics.getHeight();
      int m = (this.m_tr == null) || (this.m_tr.hops == null) ? 0 : this.m_tr.hops.length;
      int n = countVisibleHops();
      int i1 = this.m_scroll.isVisible() ? this.m_scroll.getValue() : 0;
      boolean bool = isMultiPathType(this.m_tr);
      paramGraphics.translate(0, -i1);
      int[] arrayOfInt = new int[g_cols.length];
      int i2 = 0;
      int i3 = 0;
      for (int i4 = 0; i4 < arrayOfInt.length; i4++)
        i3 += (this.m_visibleCols[i4] != 0 ? this.m_colWidths[i4] : 0);
      i3 = Math.max(i3, 1);
      for (i4 = 0; i4 < arrayOfInt.length - 1; i4++)
      {
        arrayOfInt[i4] = Math.max(20, (int)(this.m_colWidths[i4] * (i / i3)));
        if (this.m_visibleCols[i4] != 0)
          i2 += arrayOfInt[i4];
      }
      arrayOfInt[(arrayOfInt.length - 1)] = Math.max(20, i - i2);
      Vector localVector = new Vector();
      Color localColor1 = new Color(11388911);
      int i5 = k + 16;
      int i6 = k + 4;
      int i7 = i5 + n * i6;
      int i8 = 0;
      int i9 = i6;
      int i10 = -1;
      int i11 = -1;
      Object localObject2;
      int i18;
      for (int i12 = -1; i12 < m; i12++)
      {
        TraceRouteHop localTraceRouteHop1 = i12 == -1 ? null : this.m_tr.hops[i12];
        if ((!bool) || (showRow(localTraceRouteHop1, localVector)))
        {
          Color localColor2 = i12 == -1 ? new Color(16773527) : hopColour(localTraceRouteHop1);
          Color localColor3 = i12 == -1 ? new Color(16776667) : U.lighter(localColor2);
          U.gradientFill(paramGraphics, 0, i9 - i6 + 1, i, (i12 == -1 ? i5 : i6) - 1, localColor3, localColor2);
          paramGraphics.setColor(localColor1);
          paramGraphics.drawLine(0, i9 - i6, i, i9 - i6);
          paramGraphics.setColor(new Color(0));
          for (int i16 = 0; i16 < g_cols.length; i16++)
          {
            paramGraphics.setColor(localColor1);
            if (this.m_visibleCols[i16] != 0)
            {
              paramGraphics.drawLine(i8 + arrayOfInt[i16], 0, i8 + arrayOfInt[i16], i7);
              addHitRegion(new SmoothUpdatePanel.HitRegion(this, "colwidth", new Integer(i16), new Rectangle(i8 + arrayOfInt[i16] - 2, -i1, 4, i7)));
            }
            paramGraphics.setClip(new Rectangle(i8 + 2, i1, arrayOfInt[i16] - 4, j));
            paramGraphics.setColor(Color.black);
            if (this.m_visibleCols[i16] != 0)
            {
              localObject2 = null;
              String str = null;
              i18 = 0;
              Font localFont;
              if (i12 == -1)
              {
                localFont = paramGraphics.getFont();
                paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() | 0x1, localFont.getSize()));
                paramGraphics.drawString(this.m_colNames[i16], i8 + 2, i9);
                paramGraphics.setFont(localFont);
              }
              else
              {
                localObject2 = dataFor(i16, localTraceRouteHop1);
                i18 = (localObject2 != null) && (g_cols[i16].equals("loc")) && (((String)localObject2).endsWith("?")) ? 1 : 0;
                if ((g_cols[i16].equals("nname")) && (localObject2 != null) && (!((String)localObject2).trim().equals("-")))
                {
                  str = "domwhois";
                }
                else if (g_cols[i16].equals("graph"))
                {
                  i10 = i8;
                  i11 = arrayOfInt[i16];
                }
                else if ((g_cols[i16].equals("net")) && (localObject2 != null) && (!((String)localObject2).trim().equals("-")))
                {
                  str = "netwhois";
                }
                else if ((g_cols[i16].equals("hop")) && (localObject2 != null))
                {
                  str = "exp=" + (String)localObject2;
                }
              }
              if (localObject2 != null)
              {
                paramGraphics.setColor(str == null ? Color.black : new Color(0, 0, 150));
                localFont = paramGraphics.getFont();
                paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() | (i18 != 0 ? 2 : 0), localFont.getSize()));
                int i19 = 0;
                if ((bool) && (g_cols[i16].equals("hop")))
                {
                  Image localImage = getImageFor(localTraceRouteHop1);
                  int i21 = localImage != null ? localImage.getWidth(null) : 0;
                  int i22 = localImage != null ? localImage.getHeight(null) : 0;
                  i19 = i21;
                  if ((localTraceRouteHop1 != null) && (!localVector.contains(localTraceRouteHop1.hop)) && (localTraceRouteHop1.nAffinity != -1) && (localImage != null))
                    paramGraphics.drawImage(localImage, i8 + 2, i9 - i22, null);
                }
                paramGraphics.drawString((String)localObject2, i19 + i8 + 2, i9 - localFontMetrics.getDescent() - 1);
                paramGraphics.setFont(localFont);
                if (str != null)
                {
                  int i20 = localFontMetrics.stringWidth((String)localObject2) + i19;
                  addHitRegion(new SmoothUpdatePanel.HitRegion(this, str, new Integer(i12), makeTranslatedRectangle(new Rectangle(i8 + 2, i9 - i6, i20, i6), 0, -i1)));
                  if ((!bool) || (!g_cols[i16].equals("hop")))
                    paramGraphics.drawLine(i19 + i8 + 2, i9 - localFontMetrics.getDescent() + 1, i8 + 2 + i20, i9 - localFontMetrics.getDescent() + 1);
                }
              }
              i8 += arrayOfInt[i16];
            }
            paramGraphics.setClip(0, i1, i, j);
          }
          i8 = 0;
          i9 += (i12 == -1 ? i5 : i6);
          if ((localTraceRouteHop1 != null) && (localTraceRouteHop1.hop != null))
            localVector.addElement(localTraceRouteHop1.hop);
        }
      }
      i9 = i6 + i5;
      Object localObject1 = null;
      int i13 = 0;
      int i14 = 0;
      for (int i15 = 0; i15 < m; i15++)
        i14 = Math.max(i14, this.m_tr.hops[i15] != null ? this.m_tr.hops[i15].maxMs : 0);
      i11 -= 4;
      i10 = i10 > 0 ? i10 + 2 : i10;
      localVector.removeAllElements();
      for (i15 = 0; (i10 >= 0) && (i11 > 0) && (i15 < m); i15++)
      {
        TraceRouteHop localTraceRouteHop2 = this.m_tr.hops[i15];
        if (showRow(localTraceRouteHop2, localVector))
        {
          if (localVector.contains(localTraceRouteHop2.hop))
            localObject1 = null;
          localObject2 = localTraceRouteHop2.ms == -1 ? null : new Point(i10 + (int)(localTraceRouteHop2.ms * i11 / i14), i9 - i6 / 2);
          if (localObject2 != null)
          {
            paramGraphics.setColor(Color.darkGray);
            paramGraphics.fillRect(((Point)localObject2).x - 1, ((Point)localObject2).y - 1, 3, 3);
            int i17 = i10 + (int)(localTraceRouteHop2.minMs * i11 / i14);
            i18 = i10 + (int)(localTraceRouteHop2.maxMs * i11 / i14);
            paramGraphics.drawLine(i17, ((Point)localObject2).y, i18, ((Point)localObject2).y);
            paramGraphics.drawLine(i17, ((Point)localObject2).y - 3, i17, ((Point)localObject2).y + 3);
            paramGraphics.drawLine(i18, ((Point)localObject2).y - 3, i18, ((Point)localObject2).y + 3);
            if (localObject1 != null)
            {
              paramGraphics.setColor(i13 != 0 ? Color.red : Color.gray);
              paramGraphics.drawLine(localObject1.x, localObject1.y, ((Point)localObject2).x, ((Point)localObject2).y);
            }
            localObject1 = localObject2;
            i13 = 0;
          }
          else
          {
            i13 = 1;
          }
          i9 += i6;
        }
        if (localTraceRouteHop2 != null)
          localVector.addElement(localTraceRouteHop2.hop);
      }
      paramGraphics.setColor(localColor1);
      paramGraphics.drawRect(0, 0, i - 1, i7);
      activateHitRegions();
      if (i9 > j != this.m_scroll.isVisible())
      {
        i15 = i9 > j ? 1 : 0;
        this.m_scroll.setVisible(i15);
        this.m_scroll.setValue(0);
        repaint();
      }
      if (this.m_scroll.isVisible())
      {
        this.m_scroll.setValue(Math.min(this.m_scroll.getValue(), i9));
        this.m_scroll.setMaximum(i9);
        this.m_scroll.setMinimum(0);
        this.m_scroll.setVisibleAmount(j);
      }
      U.drawCopyright(paramGraphics, 0, j - 30, i, Color.white);
      paramGraphics.translate(0, i1);
      paramGraphics.setClip(null);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private int countVisibleHops()
  {
    if ((this.m_tr == null) || (this.m_tr.hops == null) || (this.m_tr.hops.length == 0))
      return 0;
    Vector localVector = new Vector();
    int i = 0;
    for (int j = 0; j < this.m_tr.hops.length; j++)
      if (showRow(this.m_tr.hops[j], localVector))
      {
        i++;
        localVector.addElement(this.m_tr.hops[j].hop);
      }
    return i;
  }

  private Image getImageFor(TraceRouteHop paramTraceRouteHop)
  {
    if (paramTraceRouteHop == null)
      return null;
    if (isExpanded(paramTraceRouteHop))
      return IM_COLLAPSE;
    return IM_EXPAND;
  }

  private boolean showRow(TraceRouteHop paramTraceRouteHop, Vector paramVector)
  {
    if (paramTraceRouteHop == null)
      return true;
    String str = paramTraceRouteHop.hop;
    if (paramVector.contains(str))
      return isExpanded(paramTraceRouteHop);
    return true;
  }

  private boolean isExpanded(TraceRouteHop paramTraceRouteHop)
  {
    if (paramTraceRouteHop == null)
      return true;
    return !this.m_collapsed.contains(paramTraceRouteHop.hop);
  }

  private String dataFor(int paramInt, TraceRouteHop paramTraceRouteHop)
  {
    if (g_cols[paramInt].equals("hop"))
    {
      try
      {
        int i = paramTraceRouteHop.sentTTL;
        if (i > 256)
          return "?";
      }
      catch (Exception localException)
      {
      }
      return paramTraceRouteHop.hop;
    }
    if (g_cols[paramInt].equals("loss"))
      return paramTraceRouteHop.loss;
    if (g_cols[paramInt].equals("ip"))
      return paramTraceRouteHop.ip;
    if (g_cols[paramInt].equals("nname"))
      return paramTraceRouteHop.getDisplayDNS();
    if (g_cols[paramInt].equals("loc"))
    {
      if ("*".equals(paramTraceRouteHop.location))
        return "Querying for information...";
      if ("-".equals(paramTraceRouteHop.location))
        return paramTraceRouteHop.location;
      Place localPlace = U.getPlace(paramTraceRouteHop.location);
      return localPlace.fullname + (paramTraceRouteHop.bAuthoritativeLocation ? "" : "?");
    }
    if (g_cols[paramInt].equals("ms"))
      return paramTraceRouteHop.ms >= 0 ? paramTraceRouteHop.ms : "-";
    if (g_cols[paramInt].equals("net"))
    {
      if ("*".equals(paramTraceRouteHop.network))
        return "Querying for information...";
      return paramTraceRouteHop.network;
    }
    return null;
  }

  private Color hopColour(TraceRouteHop paramTraceRouteHop)
  {
    if ("0".equals(paramTraceRouteHop.hop))
      return U.latencyColour(0.0F, "table");
    if (paramTraceRouteHop.ms < 0)
      return U.latencyColour(99999.0F, "table");
    return U.latencyColour(paramTraceRouteHop.ms, "table");
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    SmoothUpdatePanel.HitRegion localHitRegion = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    int i = -1;
    i = (i == -1) && (localHitRegion != null) && (localHitRegion.isa("colwidth")) ? 10 : i;
    i = (i == -1) && (localHitRegion != null) && ((localHitRegion.isa("domwhois")) || (localHitRegion.isa("netwhois"))) ? 12 : i;
    setCursor(new Cursor(i == -1 ? 0 : i));
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(0));
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.__down = paramMouseEvent.getPoint();
    SmoothUpdatePanel.HitRegion localHitRegion = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if ((localHitRegion != null) && (localHitRegion.isa("colwidth")))
    {
      this.__downCol = ((Integer)localHitRegion.what).intValue();
      this.__downColWidth = this.m_colWidths[this.__downCol];
    }
    else
    {
      Object localObject;
      if ((localHitRegion != null) && (localHitRegion.type != null) && (localHitRegion.type.startsWith("exp=")))
      {
        localObject = localHitRegion.type.substring("exp=".length());
        if (this.m_collapsed.contains(localObject))
          this.m_collapsed.removeElement(localObject);
        else
          this.m_collapsed.addElement(localObject);
        repaint();
      }
      else if ((localHitRegion != null) && (localHitRegion.isa("domwhois")))
      {
        localObject = this.m_tr.hops[((Integer)localHitRegion.what).intValue()];
        if ((((TraceRouteHop)localObject).dns != null) && (!"".equals(((TraceRouteHop)localObject).dns)) && (!"-".equals(((TraceRouteHop)localObject).getDisplayDNS())))
          this.m_appPanel.doWhois(((TraceRouteHop)localObject).dns, true);
      }
      else if ((localHitRegion != null) && (localHitRegion.isa("netwhois")))
      {
        localObject = this.m_tr.hops[((Integer)localHitRegion.what).intValue()];
        if ((((TraceRouteHop)localObject).ip != null) && (!"".equals(((TraceRouteHop)localObject).ip)) && (!"-".equals(((TraceRouteHop)localObject).ip)))
          this.m_appPanel.doWhois(((TraceRouteHop)localObject).ip, false);
      }
    }
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    if ((this.__down != null) && (this.__downCol != -1) && (this.__downColWidth != -1))
    {
      int i = paramMouseEvent.getX() - this.__down.x;
      int j = Math.max(1, this.__downColWidth + i);
      if (j != this.m_colWidths[this.__downCol])
      {
        this.m_colWidths[this.__downCol] = j;
        repaint();
      }
    }
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    this.__down = null;
    this.__downCol = -1;
    this.__downColWidth = -1;
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
  {
    if (paramAdjustmentEvent.getSource() == this.m_scroll)
      repaint();
  }

  private static boolean isMultiPathType(TraceRoute paramTraceRoute)
  {
    if ((paramTraceRoute == null) || (paramTraceRoute.hops == null) || (paramTraceRoute.hops.length == 0))
      return false;
    String str = null;
    for (int i = 0; i < paramTraceRoute.hops.length; i++)
    {
      if ((str != null) && (str.equals(paramTraceRoute.hops[i].hop)))
        return true;
      str = paramTraceRoute.hops[i].hop;
    }
    return false;
  }

  private static Rectangle makeTranslatedRectangle(Rectangle paramRectangle, int paramInt1, int paramInt2)
  {
    paramRectangle.translate(paramInt1, paramInt2);
    return paramRectangle;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.TablePanel
 * JD-Core Version:    0.6.2
 */