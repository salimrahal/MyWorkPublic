package mcsaplugins.route;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;

public class GraphicRoutePanel extends SmoothUpdatePanel
  implements MouseListener, MouseMotionListener
{
  private static final int[] LATENCYLABELS = { 0, 3, 10, 25, 65, 170, 450, 1200, 3200, 8000 };
  private static final int X_AXIS_HEIGHT = 20;
  private TraceRoute m_tr;
  private AppletPanel m_appPanel;
  private Dimension m_oldSize = new Dimension();
  private Image m_background;
  private int m_bkgMaxMs = 300;
  private Image m_plug;
  private int m_x;
  private int m_nStatusHop = -1;
  private int m_bh;
  private Point m_peering;
  private boolean m_bSummary;

  public GraphicRoutePanel(AppletPanel paramAppletPanel, boolean paramBoolean)
  {
    this.m_appPanel = paramAppletPanel;
    this.m_bSummary = paramBoolean;
    this.m_plug = U.getImage("plug.gif");
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_tr = paramTraceRoute;
    repaint();
  }

  private Image drawBackground(int paramInt)
  {
    Color localColor1 = new Color(247, 247, 247);
    Color localColor2 = new Color(226, 234, 243);
    int i = getSize().width;
    int j = getSize().height;
    Image localImage = createImage(i, j);
    if (localImage != null)
    {
      this.m_bkgMaxMs = paramInt;
      Graphics localGraphics = localImage.getGraphics();
      U.gradientFill(localGraphics, 0, 0, i, j, localColor1, localColor2);
      localGraphics.translate(0, 10);
      int k = j - 20 - 20;
      localGraphics.setFont(new Font("Helvetica", 0, 10));
      FontMetrics localFontMetrics = localGraphics.getFontMetrics();
      int m = localFontMetrics.stringWidth("XXX XX") + 4;
      this.m_x = m;
      localGraphics.setColor(Color.gray);
      localGraphics.drawRect(1, 0, m - 2, k);
      for (int n = 0; n < k; n++)
      {
        float f = yToMS(n);
        localGraphics.setColor(U.latencyColour(f, "routegraph"));
        localGraphics.drawLine(0, n, m - 2, n);
      }
      localGraphics.translate(0, -10);
      U.gradientFill(localGraphics, 0, 0, m - 1, 10, localColor1, U.latencyColour(yToMS(0), "routegraph"));
      U.gradientFill(localGraphics, m - 1, 0, 0, 10, localColor1, Color.gray);
      U.gradientFill(localGraphics, 0, k + 10, m - 1, 10, U.latencyColour(0.0F, "routegraph"), localColor2);
      U.gradientFill(localGraphics, m - 1, k + 10, 0, 10, Color.gray, localColor2);
      n = localFontMetrics.getAscent();
      int i1 = 8;
      int i2 = 0;
      for (int i3 = LATENCYLABELS.length - 1; i3 >= 0; i3--)
        if (LATENCYLABELS[i3] <= paramInt)
        {
          i2++;
          int i4 = i2 == i1 + 1 ? 0 : LATENCYLABELS[i3];
          int i5 = msToY(i4);
          String str = i4 + "ms";
          localGraphics.setColor(Color.gray);
          localGraphics.drawString(str, m - 2 - localFontMetrics.stringWidth(str), i5 + n / 2);
          localGraphics.setColor(Color.lightGray);
          localGraphics.drawLine(m + 1, i5, i, i5);
          if (i2 > i1)
            break;
        }
      this.m_bkgMaxMs = paramInt;
    }
    return localImage;
  }

  public void smoothPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height - 20;
    if (j > 0)
      this.m_bh = (j - 20);
    int k = 200;
    if (this.m_tr != null)
    {
      TraceRouteHop[] arrayOfTraceRouteHop1 = this.m_tr.hops;
      for (int n = 0; (arrayOfTraceRouteHop1 != null) && (n < arrayOfTraceRouteHop1.length); n++)
        k = arrayOfTraceRouteHop1[n] == null ? k : Math.max(k, arrayOfTraceRouteHop1[n].maxMs);
    }
    if (this.m_bkgMaxMs != k)
      this.m_background = null;
    if (!new Dimension(i, j).equals(this.m_oldSize))
    {
      this.m_oldSize = new Dimension(i, j);
      this.m_background = null;
    }
    if (this.m_background == null)
      this.m_background = drawBackground(k);
    if (this.m_background != null)
      paramGraphics.drawImage(this.m_background, 0, 0, this);
    int m = (this.m_tr == null) || (this.m_tr.hops == null) ? 0 : this.m_tr.hops.length;
    paramGraphics.setFont(new Font("Helvetica", 0, 10));
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    paramGraphics.translate(0, -10);
    Vector localVector = new Vector();
    paramGraphics.translate(this.m_x, 10);
    if (m > 0)
    {
      TraceRouteHop[] arrayOfTraceRouteHop2 = this.m_tr.hops;
      int i1 = i - this.m_x - 10;
      float f1 = i1 / (m - 1);
      Point localPoint = null;
      TraceRouteHop localTraceRouteHop = null;
      int i2 = 0;
      int i5;
      for (int i3 = 0; i3 < m; i3++)
      {
        float f2 = arrayOfTraceRouteHop2[i3].ms;
        if ((f2 >= 0.0F) || (localPoint != null))
        {
          i5 = hopToY(arrayOfTraceRouteHop2, i3);
          int i6 = (int)(i3 * f1);
          int i10;
          if (f2 >= 0.0F)
          {
            int i8 = msToY(arrayOfTraceRouteHop2[i3].maxMs);
            i10 = msToY(arrayOfTraceRouteHop2[i3].minMs);
            paramGraphics.setColor(U.latencyColour(arrayOfTraceRouteHop2[i3].maxMs, "routegraph").darker().darker());
            paramGraphics.drawLine(i6, i8, i6, i5);
            paramGraphics.drawLine(i6 - 6, i8, i6 + 6, i8);
            paramGraphics.fillRect(i6 - 3, i8 - 1, 7, 3);
            paramGraphics.setColor(U.latencyColour(arrayOfTraceRouteHop2[i3].minMs, "routegraph").darker().darker());
            paramGraphics.drawLine(i6, i10, i6, i5);
            paramGraphics.drawLine(i6 - 6, i10, i6 + 6, i10);
            paramGraphics.fillRect(i6 - 3, i10 - 1, 7, 3);
          }
          drawDot(paramGraphics, i6, i5, (f2 == -1.0F) || (arrayOfTraceRouteHop2[i3].loss > 0) ? Color.red : i3 == this.m_nStatusHop ? Color.yellow : Color.lightGray);
          Rectangle localRectangle1 = new Rectangle(i6 - 4, i5 - 4, 8, 8);
          localVector.addElement(localRectangle1);
          addHitRegion(new SmoothUpdatePanel.HitRegion(this, "hop", new Integer(i3), 3, new Rectangle(localRectangle1.x + this.m_x, localRectangle1.y, localRectangle1.width, localRectangle1.height)));
          if (localPoint != null)
          {
            paramGraphics.setColor((f2 == -1.0F) || (i2 != 0) ? Color.red : Color.blue);
            if (isPeeringPoint(localTraceRouteHop, arrayOfTraceRouteHop2[i3]))
            {
              i10 = 1;
              int i12 = this.m_plug.getWidth(null);
              int i14 = this.m_plug.getHeight(null);
              if ((i12 == -1) || (i14 == -1))
              {
                i12 = 10;
                i14 = 10;
                i10 = 0;
              }
              int i15 = (localPoint.x + 4 + i6 - 4) / 2;
              int i16 = (localPoint.y + i5) / 2;
              drawDottedLine(paramGraphics, localPoint.x + 4, localPoint.y, i15 - i12 / 2, i16);
              drawDottedLine(paramGraphics, i15 + i12 / 2, i16, i6 - 4, i5);
              if (i10 != 0)
              {
                paramGraphics.drawImage(this.m_plug, i15 - i12 / 2, i16 - i14 / 2, null);
              }
              else
              {
                paramGraphics.setColor(Color.red);
                paramGraphics.fillRect(i15 - i12 / 2, i16 - i14 / 2, i12, i14);
                paramGraphics.setColor(Color.black);
                paramGraphics.drawRect(i15 - i12 / 2, i16 - i14 / 2, i12, i14);
              }
              localVector.addElement(new Rectangle(i15 - i12 / 2, i16 - i14 / 2, i12, i14));
              addHitRegion(new SmoothUpdatePanel.HitRegion(this, "peering", new Point(i15, i16), 10, new Rectangle(this.m_x + i15 - i12 / 2, i16 - i14 / 2, i12, i14)));
            }
            else
            {
              drawDottedLine(paramGraphics, localPoint.x + 4, localPoint.y, i6 - 4, i5);
            }
          }
          localPoint = new Point(i6, i5);
          localTraceRouteHop = arrayOfTraceRouteHop2[i3];
          i2 = f2 == -1.0F ? 1 : 0;
          Rectangle localRectangle2 = (Rectangle)paramGraphics.getClip();
          paramGraphics.setClip(i6 - (int)(f1 / 2.0F), 0, (int)f1, j + 350);
          paramGraphics.setColor(Color.black);
          paramGraphics.setClip(localRectangle2);
        }
      }
      localPoint = null;
      Object localObject1 = null;
      int i4 = this.m_bSummary ? G.getSummRouteGraphLabelThreshold() : G.getRGRouteGraphLabelThreshold();
      int i9;
      int i11;
      String str2;
      String str3;
      String str4;
      Object localObject2;
      int i20;
      Object localObject3;
      if (i4 >= 0)
        for (i5 = 0; i5 < m; i5++)
        {
          float f3 = arrayOfTraceRouteHop2[i5].ms;
          if ((f3 >= 0.0F) || (localPoint != null))
          {
            i9 = hopToY(arrayOfTraceRouteHop2, i5);
            i11 = (int)(i5 * f1);
            if ((i5 == this.m_nStatusHop) || (f3 == -1.0F) || (f3 >= i4))
            {
              str2 = arrayOfTraceRouteHop2[i5].ip;
              str3 = arrayOfTraceRouteHop2[i5].dns;
              str4 = (this.m_bSummary ? "" : new StringBuilder().append(arrayOfTraceRouteHop2[i5].sentTTL > 256 ? "?" : new StringBuilder().append(arrayOfTraceRouteHop2[i5].hop).toString()).append("| ").toString()) + (str2 == null ? "(unknown)" : str2);
              localObject2 = str3 == null ? "-" : this.m_bSummary ? null : getDomain(str3);
              int i17 = Math.max(localFontMetrics.stringWidth(str4), this.m_bSummary ? 0 : localFontMetrics.stringWidth((String)localObject2));
              int i18 = i11 - i17 / (i5 == m - 1 ? 1 : 2);
              Dimension localDimension = new Dimension(i17 + 4, localFontMetrics.getHeight() * (this.m_bSummary ? 1 : 2) + 2);
              i20 = 0;
              localObject3 = new Rectangle(i18 - 1, i9 + 25, localDimension.width, localDimension.height);
              if ((((Rectangle)localObject3).y + ((Rectangle)localObject3).height > j) || (((Rectangle)localObject3).y < 0) || (hits((Rectangle)localObject3, localVector)))
                localObject3 = new Rectangle(i18 - 1, Math.max(-2147483648, i9 - 25 - (localFontMetrics.getHeight() + 2)), localDimension.width, localDimension.height);
              while ((((Rectangle)localObject3).y < 0) || (hits((Rectangle)localObject3, localVector)))
              {
                i20++;
                localObject3 = new Rectangle(i18 - 1, i9 + 4 + (localFontMetrics.getHeight() + 4) * i20, localDimension.width, localDimension.height);
                if (((Rectangle)localObject3).y + ((Rectangle)localObject3).height > j)
                {
                  localObject3 = null;
                  break;
                }
              }
              while ((localObject3 == null) || (hits((Rectangle)localObject3, localVector)))
              {
                i20--;
                localObject3 = new Rectangle(i18 - 1, i9 + 4 + (localFontMetrics.getHeight() + 4) * i20, localDimension.width, localDimension.height);
              }
              localVector.addElement(localObject3);
              addHitRegion(new SmoothUpdatePanel.HitRegion(this, "hop", new Integer(i5), 3, new Rectangle(((Rectangle)localObject3).x + this.m_x, ((Rectangle)localObject3).y, localDimension.width, localDimension.height)));
              if (i5 != this.m_nStatusHop)
              {
                Color localColor1 = U.latencyColour(f3, "routegraph");
                U.gradientFill(paramGraphics, ((Rectangle)localObject3).x, ((Rectangle)localObject3).y, ((Rectangle)localObject3).width, ((Rectangle)localObject3).height, localColor1.brighter(), localColor1);
                paramGraphics.setColor(Color.black);
                paramGraphics.drawRect(((Rectangle)localObject3).x, ((Rectangle)localObject3).y, ((Rectangle)localObject3).width, ((Rectangle)localObject3).height);
                paramGraphics.drawString(str4, ((Rectangle)localObject3).x + 2, ((Rectangle)localObject3).y + localFontMetrics.getAscent());
                if (!this.m_bSummary)
                  paramGraphics.drawString((String)localObject2, ((Rectangle)localObject3).x + 2, ((Rectangle)localObject3).y + localFontMetrics.getAscent() + localFontMetrics.getHeight());
              }
              else
              {
                localObject1 = localObject3;
              }
              paramGraphics.setColor(new Color(155, 155, 155));
              drawDottedLine(paramGraphics, i11, i9 + (((Rectangle)localObject3).y > i9 ? 4 : -4), ((Rectangle)localObject3).x + ((Rectangle)localObject3).width / 2, ((Rectangle)localObject3).y + (((Rectangle)localObject3).y > i9 ? 0 : ((Rectangle)localObject3).height + 1));
            }
            localPoint = new Point(i11, i9);
          }
        }
      int i7;
      if ((this.m_nStatusHop != -1) && (localObject1 != null))
      {
        i5 = arrayOfTraceRouteHop2[this.m_nStatusHop].maxMs;
        i7 = arrayOfTraceRouteHop2[this.m_nStatusHop].minMs;
        i9 = arrayOfTraceRouteHop2[this.m_nStatusHop].ms;
        i11 = arrayOfTraceRouteHop2[this.m_nStatusHop].loss;
        str2 = arrayOfTraceRouteHop2[this.m_nStatusHop].ip;
        str3 = arrayOfTraceRouteHop2[this.m_nStatusHop].dns;
        str4 = arrayOfTraceRouteHop2[this.m_nStatusHop].network;
        localObject2 = U.getPlace(arrayOfTraceRouteHop2[this.m_nStatusHop].location);
        String str5 = localObject2 == null ? null : ((Place)localObject2).fullname;
        String[] arrayOfString = { "Hop " + (str2 == null ? "(unknown)" : str2), "Avg / min / max ms: " + (i9 != -1 ? i9 : "--") + " / " + (i7 != -1 ? i7 : "--") + " / " + (i5 != -1 ? i5 : "--"), trim(str3 == null ? "(Finding name...)" : str3, 90), trim(str4 == null ? "(Finding network...)" : str4, 90), trim(str5 == null ? "-" : str5, 90), "Packet loss: " + i11 + "%" };
        localObject3 = new String[] { 0, 0, "dns", "net" };
        paramGraphics.setFont(new Font("Helvetica", 1, 11));
        localFontMetrics = paramGraphics.getFontMetrics();
        j += 10;
        int i19 = 0;
        i20 = 0;
        for (int i21 = 0; i21 < arrayOfString.length; i21++)
          if (arrayOfString[i21] != null)
          {
            i20++;
            i19 = Math.max(i19, localFontMetrics.stringWidth(arrayOfString[i21]) + 10);
          }
        i21 = i20 * localFontMetrics.getHeight() + 10;
        if (localObject1.y + i21 > j)
          localObject1.y = (j - i21);
        if (localObject1.x + i19 > i1)
          localObject1.x = (i1 - i19);
        Color localColor2 = U.latencyColour(i9, "routegraph");
        paramGraphics.setColor(localColor2);
        paramGraphics.fillRect(localObject1.x, localObject1.y, i19, i21);
        paramGraphics.setColor(Color.black);
        paramGraphics.drawRect(localObject1.x, localObject1.y, i19, i21);
        addHitRegion(new SmoothUpdatePanel.HitRegion(this, "hop", new Integer(this.m_nStatusHop), 2, new Rectangle(localObject1.x + this.m_x, localObject1.y, i19, i21)));
        int i22 = 0;
        for (int i23 = 0; i23 < arrayOfString.length; i23++)
        {
          paramGraphics.setFont(new Font("Helvetica", i23 == 0 ? 1 : 0, 11));
          localFontMetrics = paramGraphics.getFontMetrics();
          if (arrayOfString[i23] != null)
          {
            i22++;
            Color localColor3 = paramGraphics.getColor();
            if (localObject3[i23] != null)
            {
              paramGraphics.setColor(Color.blue);
              int i24 = localObject1.y + localFontMetrics.getHeight() * i22 + 2;
              int i25 = localFontMetrics.stringWidth(arrayOfString[i23]);
              paramGraphics.drawLine(localObject1.x + 5, i24, localObject1.x + 5 + i25, i24);
              addHitRegion(new SmoothUpdatePanel.HitRegion(this, localObject3[i23], new Integer(this.m_nStatusHop), 1, new Rectangle(localObject1.x + 5 + this.m_x, i24 - localFontMetrics.getAscent(), i25, localFontMetrics.getHeight())));
            }
            paramGraphics.drawString(arrayOfString[i23], localObject1.x + 5, localObject1.y + localFontMetrics.getHeight() * i22);
            paramGraphics.setColor(localColor3);
          }
        }
      }
      if (this.m_peering != null)
      {
        String str1 = "This is a link between networks. Hops on either side of the plug symbol are known as peering points.";
        i7 = localFontMetrics.stringWidth(str1);
        i9 = localFontMetrics.getHeight();
        i11 = this.m_peering.x - i7 / 2;
        int i13 = this.m_peering.y;
        if (i11 + this.m_x + 5 + i7 > i)
          i11 = i - i7 - 5 - this.m_x;
        if (i11 < 0)
          i11 = 0;
        paramGraphics.setColor(new Color(255, 255, 200));
        paramGraphics.fillRect(i11, i13, i7 + 4, i9 + 2);
        paramGraphics.setColor(new Color(255, 255, 200).darker().darker());
        paramGraphics.drawRect(i11, i13, i7 + 4, i9 + 2);
        paramGraphics.setColor(Color.black);
        paramGraphics.drawString(str1, i11 + 2, i13 + localFontMetrics.getAscent() + 1);
        addHitRegion(new SmoothUpdatePanel.HitRegion(this, "peering", this.m_peering, 10, new Rectangle(i11 + this.m_x, i13, i7 + 4, i9 + 2)));
      }
    }
    U.drawCopyright(paramGraphics, 0, j - 3, i - this.m_x, new Color(226, 234, 243));
    activateHitRegions();
  }

  private String trim(String paramString, int paramInt)
  {
    return paramString.length() + 3 > paramInt ? paramString.substring(0, paramInt - 3) + "..." : paramString;
  }

  private boolean isPeeringPoint(TraceRouteHop paramTraceRouteHop1, TraceRouteHop paramTraceRouteHop2)
  {
    String str1 = paramTraceRouteHop1 == null ? null : paramTraceRouteHop1.ip;
    String str2 = paramTraceRouteHop2 == null ? null : paramTraceRouteHop2.ip;
    String str3 = str1 == null ? null : paramTraceRouteHop1.dns;
    String str4 = str2 == null ? null : paramTraceRouteHop1.dns;
    String str5 = getDomain(str3 == null ? null : str3);
    String str6 = getDomain(str4 == null ? null : str4);
    if (str5.equals(str6))
      return false;
    String str7 = str1 == null ? null : paramTraceRouteHop1.network;
    String str8 = str2 == null ? null : paramTraceRouteHop2.network;
    if ((str7 == null) || (str8 == null))
      return false;
    return !str7.toLowerCase().trim().equals(str8.toLowerCase().trim());
  }

  private String getDomain(String paramString)
  {
    String[] arrayOfString = { "uk", "jp", "co", "au" };
    if (paramString == null)
      return "-";
    for (int i = 0; i < arrayOfString.length; i++)
      if (paramString.endsWith("." + arrayOfString[i]))
        return getOneBarrelDomain(paramString.substring(0, paramString.length() - arrayOfString[i].length() - 1)) + "." + arrayOfString[i];
    return getOneBarrelDomain(paramString);
  }

  private String getOneBarrelDomain(String paramString)
  {
    int i = paramString.lastIndexOf(".");
    i = i <= 0 ? -1 : paramString.substring(0, i).lastIndexOf(".");
    return i < 0 ? paramString.toLowerCase() : paramString.substring(i + 1).toLowerCase();
  }

  private boolean hits(Rectangle paramRectangle, Vector paramVector)
  {
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      Rectangle localRectangle = (Rectangle)localEnumeration.nextElement();
      if (localRectangle.intersects(paramRectangle))
        return true;
    }
    return false;
  }

  private int hopToY(TraceRouteHop[] paramArrayOfTraceRouteHop, int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= paramArrayOfTraceRouteHop.length) || (paramArrayOfTraceRouteHop == null))
      return msToY(0.0F);
    while (paramInt >= 0)
    {
      if ((paramArrayOfTraceRouteHop[paramInt] != null) && (paramArrayOfTraceRouteHop[paramInt].ms >= 0))
        return msToY(paramArrayOfTraceRouteHop[paramInt].ms);
      paramInt--;
    }
    return msToY(0.0F);
  }

  private int msToY(float paramFloat)
  {
    return (int)(this.m_bh * (1.0D - log10(1.0F + 9.0F * paramFloat / this.m_bkgMaxMs)));
  }

  private float yToMS(int paramInt)
  {
    return (float)(this.m_bkgMaxMs * (pow10(1.0D - paramInt / this.m_bh) - 1.0D) / 9.0D);
  }

  private double log10(double paramDouble)
  {
    return Math.log(paramDouble) / Math.log(10.0D);
  }

  private double pow10(double paramDouble)
  {
    return Math.pow(10.0D, paramDouble);
  }

  private void drawDot(Graphics paramGraphics, int paramInt1, int paramInt2, Color paramColor)
  {
    Color localColor = paramGraphics.getColor();
    paramGraphics.setColor(Color.black);
    paramGraphics.fillRect(paramInt1 - 3, paramInt2 - 1, 7, 3);
    paramGraphics.fillRect(paramInt1 - 2, paramInt2 - 2, 5, 5);
    paramGraphics.fillRect(paramInt1 - 1, paramInt2 - 3, 3, 7);
    paramGraphics.setColor(paramColor);
    paramGraphics.fillRect(paramInt1 - 2, paramInt2 - 1, 5, 3);
    paramGraphics.fillRect(paramInt1 - 1, paramInt2 - 2, 3, 5);
    paramGraphics.setColor(localColor);
  }

  private void drawDottedLine(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    double d = Math.sqrt(Math.pow(paramInt3 - paramInt1, 2.0D) + Math.pow(paramInt4 - paramInt2, 2.0D));
    int i = 2;
    int j = 2;
    for (int k = 0; i * k + (i - j) < d; k++)
      paramGraphics.drawLine((int)(paramInt1 + i * k * (paramInt3 - paramInt1) / d), (int)(paramInt2 + i * k * (paramInt4 - paramInt2) / d), (int)(paramInt1 + (i * k + (i - j)) * (paramInt3 - paramInt1) / d), (int)(paramInt2 + (i * k + (i - j)) * (paramInt4 - paramInt2) / d));
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    Point localPoint1 = paramMouseEvent.getPoint();
    SmoothUpdatePanel.HitRegion localHitRegion = getHitRegion(localPoint1.x, localPoint1.y);
    int i = -1;
    Point localPoint2 = null;
    boolean bool = this.m_bSummary;
    if (!this.m_bSummary)
      if ((localHitRegion != null) && (localHitRegion.isa("hop")))
      {
        try
        {
          i = ((Integer)localHitRegion.what).intValue();
        }
        catch (Exception localException1)
        {
        }
      }
      else if ((localHitRegion != null) && (localHitRegion.isa("peering")))
      {
        localPoint2 = (Point)localHitRegion.what;
      }
      else if ((localHitRegion != null) && (localHitRegion.isa("net")))
      {
        try
        {
          i = ((Integer)localHitRegion.what).intValue();
        }
        catch (Exception localException2)
        {
        }
        bool = true;
      }
      else if ((localHitRegion != null) && (localHitRegion.isa("dns")))
      {
        try
        {
          i = ((Integer)localHitRegion.what).intValue();
        }
        catch (Exception localException3)
        {
        }
        bool = true;
      }
      else if ((localHitRegion != null) && (localHitRegion.isa("geo")))
      {
        try
        {
          i = ((Integer)localHitRegion.what).intValue();
        }
        catch (Exception localException4)
        {
        }
        bool = true;
      }
    setCursor(new Cursor(bool ? 12 : 0));
    if ((i != this.m_nStatusHop) || (this.m_peering == null ? localPoint2 != null : !this.m_peering.equals(localPoint2)))
    {
      this.m_nStatusHop = i;
      this.m_peering = localPoint2;
      repaint();
    }
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(0));
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    SmoothUpdatePanel.HitRegion localHitRegion = getHitRegion(localPoint.x, localPoint.y);
    if ((localHitRegion != null) && (localHitRegion.isa("net")))
    {
      String str1 = ipFor(((Integer)localHitRegion.what).intValue());
      if (str1 != null)
        this.m_appPanel.doWhois(str1, false);
    }
    else if ((localHitRegion != null) && (localHitRegion.isa("dns")))
    {
      int i = ((Integer)localHitRegion.what).intValue();
      String str2 = (this.m_tr == null) || (this.m_tr.hops == null) || (this.m_tr.hops.length <= i) ? null : this.m_tr.hops[i].getDisplayDNS();
      if (str2 != null)
        this.m_appPanel.doWhois(str2, true);
    }
    if (this.m_bSummary)
      this.m_appPanel.setView("graph");
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
  }

  private String ipFor(int paramInt)
  {
    try
    {
      return this.m_tr.hops[paramInt].ip;
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.GraphicRoutePanel
 * JD-Core Version:    0.6.2
 */