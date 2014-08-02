package mcsaplugins.route;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.util.Enumeration;
import java.util.Vector;

public class NetworkNode
  implements Cloneable
{
  private static final Image IMAGE_FIREWALL = route.getRoute() == null ? null : route.getRoute().getAppletImage("firewall.gif");
  private static final Image IMAGE_UNKOWN = route.getRoute() == null ? null : route.getRoute().getAppletImage("unknown.gif");
  private static final Color cBlue = new Color(31, 89, 200);
  private static final Color cYellow = new Color(255, 255, 204);
  private static final Color cGreen = Color.green.brighter();
  private static final Color cRed = new Color(255, 64, 64);
  private static final Color cOrange = new Color(254, 173, 0);
  public static int TYPE_NONE = 0;
  public static int TYPE_JOIN = 1;
  public static int TYPE_LOAD_BALANCER = 2;
  public static int TYPE_SOURCE = 4;
  public static int TYPE_DESTINATION = 8;
  public static int TYPE_LOAD_BALANCED = 16;
  public static int TYPE_TTL = 32;
  public static int TYPE_FIREWALL = 64;
  public static int TYPE_SHOW_TTL = 128;
  public static int TYPE_SHOW_TTL_TOOLTIP = 256;
  public static int TYPE_UNKOWN = 512;
  public static int TYPE_FAKE = 1024;
  public static int TYPE_IP_V6 = 2048;
  public static int TYPE_POINTS_TO_MANY = 128;
  public static int TYPE_POINTS_FROM_MANY = 256;
  private static int ID = 0;
  private int m_nUid = ID++;
  private int m_nType = TYPE_NONE;
  private int m_nAffinity = -1;
  private int m_nTTL = -1;
  private int m_ms = -1;
  private int m_msMax = -1;
  private int m_msMin = -1;
  private int m_pktLoss = -1;
  private int m_nIPOffset = 1;
  private Point m_pt = null;
  private Point m_ptDraw = new Point();
  private long m_lOrder = 0L;
  private TraceRouteHop m_hs;
  private boolean m_bHighlight = false;
  private Vector m_joinDests = new Vector();
  private Vector m_joinSources = new Vector();
  private Vector m_traceIDs = new Vector();
  public String m_dest = null;
  public String m_source = null;
  public String m_ip = null;
  public String m_fireDest = null;
  private int m_nMouseState = 0;

  public int getUID()
  {
    return this.m_nUid;
  }

  public void addSource(String paramString)
  {
    if (!this.m_joinSources.contains(paramString))
      this.m_joinSources.addElement(paramString);
  }

  public void addTraceID(int paramInt)
  {
    if (!containsTraceID(paramInt))
      this.m_traceIDs.addElement(new Int(paramInt));
  }

  public void addTraceIDsFromNode(NetworkNode paramNetworkNode)
  {
    Enumeration localEnumeration = paramNetworkNode.getTraceIDs().elements();
    while (localEnumeration.hasMoreElements())
    {
      Int localInt = (Int)localEnumeration.nextElement();
      addTraceID(localInt.v);
    }
  }

  public Vector getTraceIDs()
  {
    return this.m_traceIDs;
  }

  public boolean containsTraceID(int paramInt)
  {
    Enumeration localEnumeration = this.m_traceIDs.elements();
    while (localEnumeration.hasMoreElements())
    {
      Int localInt = (Int)localEnumeration.nextElement();
      if (localInt.v == paramInt)
        return true;
    }
    return false;
  }

  public void addDest(String paramString)
  {
    if (!this.m_joinDests.contains(paramString))
      this.m_joinDests.addElement(paramString);
  }

  public Vector getDests()
  {
    return this.m_joinDests;
  }

  public Vector getSources()
  {
    return this.m_joinSources;
  }

  public void clearJoinReferences()
  {
    this.m_joinSources.removeAllElements();
    this.m_joinDests.removeAllElements();
  }

  public boolean containsNodeReference(String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
      return this.m_joinDests.contains(paramString);
    return this.m_joinSources.contains(paramString);
  }

  public void addType(int paramInt)
  {
    if (paramInt == TYPE_JOIN)
    {
      this.m_nType = TYPE_JOIN;
      this.m_nType |= TYPE_FAKE;
      return;
    }
    if (paramInt == TYPE_UNKOWN)
    {
      this.m_nType = TYPE_UNKOWN;
      this.m_nType |= TYPE_FAKE;
      return;
    }
    this.m_nType |= paramInt;
  }

  public void setDestination(String paramString)
  {
    addType(TYPE_DESTINATION);
    this.m_dest = paramString;
  }

  public void setSource(String paramString)
  {
    addType(TYPE_SOURCE);
    this.m_source = paramString;
  }

  public void setDrawPoint(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
      this.m_ptDraw.x = paramInt;
    else
      this.m_ptDraw.y = paramInt;
  }

  public Point getDrawPoint()
  {
    return this.m_ptDraw;
  }

  public void setAffinity(int paramInt)
  {
    if (paramInt < 0)
      return;
    addType(TYPE_LOAD_BALANCED);
    this.m_nAffinity = paramInt;
  }

  public void setHighlight(boolean paramBoolean)
  {
    this.m_bHighlight = paramBoolean;
  }

  public boolean getHighlight()
  {
    return this.m_bHighlight;
  }

  public boolean isType(int paramInt)
  {
    return (this.m_nType & paramInt) != 0;
  }

  public void setType(int paramInt)
  {
    this.m_nType = TYPE_NONE;
    addType(paramInt);
  }

  public int getTTL()
  {
    return this.m_nTTL;
  }

  public void setTTL(int paramInt)
  {
    addType(TYPE_TTL);
    this.m_nTTL = paramInt;
  }

  public void setFirewallDestination(String paramString)
  {
    this.m_fireDest = paramString;
  }

  public String getFirewallDestination()
  {
    return this.m_fireDest;
  }

  public void setFirewall()
  {
    this.m_ip = "Firewall";
    addType(TYPE_FIREWALL);
  }

  public void setMetrics(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.m_ms = paramInt1;
    this.m_msMax = paramInt2;
    this.m_msMin = paramInt3;
    this.m_pktLoss = paramInt4;
  }

  public void setHopSummary(TraceRouteHop paramTraceRouteHop)
  {
    this.m_hs = paramTraceRouteHop;
  }

  public TraceRouteHop getHopSummary()
  {
    return this.m_hs;
  }

  public int getMS()
  {
    return this.m_ms;
  }

  public int getMaxMS()
  {
    return this.m_msMax;
  }

  public int getMinMS()
  {
    return this.m_msMin;
  }

  public int getPktLoss()
  {
    return this.m_pktLoss;
  }

  public int getAffinity()
  {
    return this.m_nTTL == -1 ? -1 : this.m_nAffinity;
  }

  public void setIP(String paramString)
  {
    this.m_ip = paramString;
  }

  public String getIP()
  {
    return this.m_ip;
  }

  public void setOrder(long paramLong)
  {
    this.m_lOrder = paramLong;
  }

  public long getOrder()
  {
    return this.m_lOrder;
  }

  public void setIPOffset(int paramInt)
  {
    this.m_nIPOffset = paramInt;
  }

  public int getIPOffset()
  {
    return this.m_nIPOffset;
  }

  public int getMouseState()
  {
    return this.m_nMouseState;
  }

  public Point getPosition()
  {
    return this.m_pt;
  }

  public void setPosition(int paramInt1, int paramInt2)
  {
    setPosition(new Point(paramInt1, paramInt2));
  }

  public void setPosition(Point paramPoint)
  {
    this.m_pt = paramPoint;
  }

  public Rectangle draw(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Vector paramVector, Point paramPoint)
  {
    if (this.m_nType == TYPE_JOIN)
    {
      int i = 20;
      return new Rectangle(paramInt1 - i, paramInt2 - i, paramInt1 + i, paramInt2 + i);
    }
    Rectangle localRectangle1 = null;
    Point localPoint1 = new Point(paramInt1, paramInt2);
    Point localPoint2 = new Point(paramInt1, paramInt2);
    if (!isType(TYPE_JOIN))
    {
      drawNode(paramGraphics, paramInt1, paramInt2, localPoint1, localPoint2);
      localRectangle1 = new Rectangle(localPoint1.x, localPoint1.y, localPoint2.x - localPoint1.x, localPoint2.y - localPoint1.y);
      if (paramVector != null)
      {
        Rectangle localRectangle2 = localRectangle1;
        localRectangle2.translate(paramPoint.x, paramPoint.y);
        NodeHitRegion localNodeHitRegion = new NodeHitRegion();
        localNodeHitRegion.node = this;
        localNodeHitRegion.type = 1;
        localNodeHitRegion.rect = localRectangle2;
        localNodeHitRegion.order = 0;
        paramVector.addElement(localNodeHitRegion);
      }
      drawIP2(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, localPoint1, localPoint2, paramPoint);
    }
    localRectangle1 = new Rectangle(localPoint1.x, localPoint1.y, localPoint2.x - localPoint1.x, localPoint2.y - localPoint1.y);
    return localRectangle1;
  }

  private void drawNode(Graphics paramGraphics, int paramInt1, int paramInt2, Point paramPoint1, Point paramPoint2)
  {
    if (paramGraphics != null)
    {
      int i;
      if (isType(TYPE_DESTINATION))
      {
        i = 6;
        Polygon localPolygon1 = new Polygon();
        localPolygon1.addPoint(paramInt1 - i, paramInt2);
        localPolygon1.addPoint(paramInt1, paramInt2 - i);
        localPolygon1.addPoint(paramInt1 + i, paramInt2);
        localPolygon1.addPoint(paramInt1, paramInt2 + i);
        paramGraphics.setColor(cBlue);
        paramGraphics.fillPolygon(localPolygon1);
        localPolygon1 = new Polygon();
        localPolygon1.addPoint(paramInt1 - (i - 1), paramInt2);
        localPolygon1.addPoint(paramInt1, paramInt2 - (i - 1));
        localPolygon1.addPoint(paramInt1 + (i - 1), paramInt2);
        paramGraphics.setColor(getUpperColour());
        paramGraphics.fillPolygon(localPolygon1);
        localPolygon1 = new Polygon();
        localPolygon1.addPoint(paramInt1 - (i - 1), paramInt2);
        localPolygon1.addPoint(paramInt1, paramInt2 + (i - 1));
        localPolygon1.addPoint(paramInt1 + (i - 1), paramInt2);
        paramGraphics.setColor(getLowerColour());
        paramGraphics.fillPolygon(localPolygon1);
      }
      else
      {
        int j;
        if (isType(TYPE_FIREWALL))
        {
          if (IMAGE_FIREWALL != null)
          {
            i = 13;
            j = IMAGE_FIREWALL.getHeight(null) * i / IMAGE_FIREWALL.getWidth(null);
            paramGraphics.drawImage(IMAGE_FIREWALL, paramInt1 - i / 2, paramInt2 - j / 2, i, j, null);
          }
        }
        else if (isType(TYPE_UNKOWN))
        {
          if (IMAGE_UNKOWN != null)
          {
            i = 13;
            j = IMAGE_UNKOWN.getHeight(null) * i / IMAGE_UNKOWN.getWidth(null);
            paramGraphics.drawImage(IMAGE_UNKOWN, paramInt1 - i / 2, paramInt2 - j / 2, i, j, null);
          }
        }
        else
        {
          Object localObject;
          if (isType(TYPE_SOURCE))
          {
            i = 5;
            localObject = new Polygon();
            ((Polygon)localObject).addPoint(paramInt1 - i, paramInt2 + i);
            ((Polygon)localObject).addPoint(paramInt1 + i, paramInt2 + i);
            ((Polygon)localObject).addPoint(paramInt1 - i, paramInt2 - i);
            ((Polygon)localObject).addPoint(paramInt1 + i, paramInt2 - i);
            paramGraphics.setColor(getUpperColour());
            paramGraphics.fillPolygon((Polygon)localObject);
            Polygon localPolygon2 = new Polygon();
            localPolygon2.addPoint(paramInt1, paramInt2);
            localPolygon2.addPoint(paramInt1 - i, paramInt2 - i);
            localPolygon2.addPoint(paramInt1 + i, paramInt2 - i);
            paramGraphics.setColor(getLowerColour());
            paramGraphics.fillPolygon(localPolygon2);
            paramGraphics.setColor(cBlue);
            paramGraphics.drawPolygon((Polygon)localObject);
          }
          else
          {
            i = 5;
            paramGraphics.setColor(cBlue);
            localObject = paramGraphics.getClip();
            paramGraphics.fillOval(paramInt1 - i, paramInt2 - i, i * 2, i * 2);
            paramGraphics.setClip(paramInt1 - (i - 1), paramInt2 - (i - 1), 2 * (i - 1), i - 1);
            paramGraphics.setColor(getUpperColour());
            paramGraphics.fillOval(paramInt1 - (i - 1), paramInt2 - (i - 1), 2 * (i - 1), 2 * (i - 1));
            paramGraphics.setClip(paramInt1 - (i - 1), paramInt2, 2 * (i - 1), i - 1);
            paramGraphics.setColor(getLowerColour());
            paramGraphics.fillOval(paramInt1 - (i - 1), paramInt2 - (i - 1), 2 * (i - 1), 2 * (i - 1));
            paramGraphics.setClip((Shape)localObject);
          }
        }
      }
    }
    adjust(paramInt1 - 5, paramInt2 - 5, paramInt1 + 5, paramInt2 + 5, paramPoint1, paramPoint2);
  }

  private void drawIP2(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Point paramPoint1, Point paramPoint2, Point paramPoint3)
  {
    if (isType(TYPE_UNKOWN))
      this.m_ip = "FFF.FFF";
    if (this.m_ip == null)
      return;
    Object localObject = null;
    localObject = isType(TYPE_DESTINATION) ? this.m_dest : localObject;
    if (localObject == null)
      localObject = isType(TYPE_SOURCE) ? this.m_source : localObject;
    if (localObject == null)
      localObject = this.m_ip;
    Font localFont1 = new Font("Helvetica", 0, 10);
    if ((isType(TYPE_DESTINATION)) || (isType(TYPE_SOURCE)))
      localFont1 = new Font("Helvetica", 1, 11);
    int i = getMetrics(paramGraphics, localFont1).stringWidth((String)localObject);
    int j = getMetrics(paramGraphics, localFont1).getHeight();
    int k;
    if (paramGraphics == null)
    {
      if (paramBoolean)
      {
        k = 0;
        int n = 0;
        if (getIPOffset() == 2)
        {
          k = paramInt2 - 8 - 2 * j;
          n = paramPoint1.y;
        }
        else if (getIPOffset() == -1)
        {
          n = paramInt2 + 3 * j / 2;
          k = paramInt2;
        }
        else if (getIPOffset() == -2)
        {
          n = paramInt2 + 5 * j / 2;
          k = paramInt2;
        }
        else
        {
          k = paramInt2 - 8 - j;
          n = paramPoint1.y;
        }
        adjust(paramInt1 - i / 2 - 2, k, paramInt1 + i / 2 + 2, n, paramPoint1, paramPoint2);
      }
      return;
    }
    if (getMouseState() == 0)
    {
      if ((!isType(TYPE_FIREWALL)) && (!isType(TYPE_JOIN)) && (!isType(TYPE_UNKOWN)))
      {
        paramGraphics.setFont(localFont1);
        if (getIPOffset() == 2)
          paramGraphics.setColor(Color.black);
        else if (getIPOffset() == -1)
          paramGraphics.setColor(cBlue.brighter());
        else if (getIPOffset() == -2)
          paramGraphics.setColor(Color.black.brighter());
        else
          paramGraphics.setColor(cBlue);
        if (isType(TYPE_DESTINATION))
          paramGraphics.setColor(cRed);
        if (paramBoolean)
        {
          k = 0;
          if (getIPOffset() == 2)
            k = paramInt2 - 8 - j;
          else if (getIPOffset() == -1)
            k = paramInt2 + 3 * j / 2;
          else if (getIPOffset() == -2)
            k = paramInt2 + 5 * j / 2;
          else
            k = paramInt2 - 8;
          paramGraphics.drawString((String)localObject, paramInt1 - i / 2, k);
        }
        if ((isType(TYPE_SHOW_TTL)) && (paramBoolean) && (getTTL() <= 255))
        {
          Font localFont2 = new Font("Helvetica", 0, 9);
          paramGraphics.setFont(localFont2);
          String str = "TTL=" + getTTL();
          paramGraphics.drawString(str, paramInt1 + 4, paramInt2);
        }
        int m;
        if (getIPOffset() == 2)
        {
          paramGraphics.setColor(cBlue);
          for (m = paramInt2 - 8 - j; m < paramInt2 - 5; m++)
            if (m % 2 == 0)
              paramGraphics.drawLine(paramInt1, m, paramInt1, m);
        }
        else if (getIPOffset() == -1)
        {
          paramGraphics.setColor(cBlue);
          for (m = paramInt2 + 5; m < paramInt2 + 5 + j / 2; m++)
            if (m % 2 == 0)
              paramGraphics.drawLine(paramInt1, m, paramInt1, m);
        }
        else if (getIPOffset() == -2)
        {
          paramGraphics.setColor(cBlue);
          for (m = paramInt2 + 5; m < paramInt2 + 5 + 3 * j / 2; m++)
            if (m % 2 == 0)
              paramGraphics.drawLine(paramInt1, m, paramInt1, m);
        }
      }
    }
    else
    {
      int[] arrayOfInt = drawPopup(null, 0, 0);
      int i1 = 1;
      int i2 = 1;
      int i3 = 1;
      int i4 = 1;
      int i5 = paramPoint3.y + paramInt2 + arrayOfInt[1] / 2;
      int i6 = paramPoint3.x + paramInt1;
      if (i6 - arrayOfInt[0] / 2 < 0)
        i3 = 0;
      if (i6 + arrayOfInt[0] / 2 > paramInt3)
        i4 = 0;
      if (i5 > paramInt4)
        i2 = 0;
      if (i5 - arrayOfInt[1] < 0)
        i1 = 0;
      if (i1 == 0)
        i5 = arrayOfInt[1] - paramPoint3.y + 2;
      else if (i2 == 0)
        i5 = paramInt4 - paramPoint3.y - 2;
      else
        i5 -= paramPoint3.y;
      if (i3 == 0)
        i6 = arrayOfInt[0] / 2 - paramPoint3.x + 2;
      else if (i4 == 0)
        i6 = paramInt3 - arrayOfInt[0] / 2 - paramPoint3.x - 2;
      else
        i6 -= paramPoint3.x;
      drawPopup(paramGraphics, i6, i5);
    }
  }

  private int[] drawPopup(Graphics paramGraphics, int paramInt1, int paramInt2)
  {
    Font localFont1 = new Font("Helvetica", 0, 10);
    Font localFont2 = new Font("Helvetica", (isType(TYPE_SOURCE)) || (isType(TYPE_DESTINATION)) ? 1 : 0, 11);
    String str = this.m_hs == null ? null : this.m_hs.getDisplayDNS();
    if (("-".equals(str)) || ("".equals(str)))
      str = null;
    str = isType(TYPE_DESTINATION) ? this.m_dest : str;
    if (str == null)
      str = isType(TYPE_SOURCE) ? this.m_source : str;
    if (str == null)
      str = this.m_ip;
    int i = getMetrics(paramGraphics, localFont2).stringWidth(str);
    int j = getMetrics(paramGraphics, localFont2).getHeight();
    int k = 0;
    if (this.m_ip != null)
      k = this.m_ip.equals(str) ? 0 : 1;
    int m;
    int n;
    int i1;
    int i2;
    if (isType(TYPE_UNKOWN))
    {
      localObject = G.getParisNoResponsesSectionText();
      m = getMetrics(paramGraphics, localFont1).stringWidth((String)localObject);
      n = getMetrics(paramGraphics, localFont1).getHeight();
      i1 = m + 6;
      i2 = n + 4;
      if (paramGraphics != null)
      {
        paramGraphics.setColor(cYellow);
        paramGraphics.fillRoundRect(paramInt1 - i1 / 2, paramInt2 - i2, i1, i2, 8, 8);
        paramGraphics.setColor(cBlue);
        paramGraphics.drawRoundRect(paramInt1 - i1 / 2, paramInt2 - i2, i1, i2, 8, 8);
        paramGraphics.setFont(localFont1);
        paramGraphics.drawString((String)localObject, paramInt1 - m / 2, paramInt2 - 4);
      }
      return new int[] { i1, i2 };
    }
    if (isType(TYPE_FIREWALL))
    {
      localObject = G.getParisFirewallText();
      m = getMetrics(paramGraphics, localFont1).stringWidth((String)localObject);
      n = getMetrics(paramGraphics, localFont1).getHeight();
      i1 = m + 6;
      i2 = n + 4;
      if (paramGraphics != null)
      {
        paramGraphics.setColor(cYellow);
        paramGraphics.fillRoundRect(paramInt1 - i1 / 2, paramInt2 - i2, i1, i2, 8, 8);
        paramGraphics.setColor(cBlue);
        paramGraphics.drawRoundRect(paramInt1 - i1 / 2, paramInt2 - i2, i1, i2, 8, 8);
        paramGraphics.setFont(localFont1);
        paramGraphics.drawString((String)localObject, paramInt1 - m / 2, paramInt2 - 4);
      }
      return new int[] { i1, i2 };
    }
    if (getMS() == -1)
    {
      localObject = G.getParisNoResponsesText();
      m = Math.max(getMetrics(paramGraphics, localFont1).stringWidth((String)localObject), getMetrics(paramGraphics, localFont2).stringWidth(str));
      n = getMetrics(paramGraphics, localFont1).getHeight();
      i1 = m + 6;
      i2 = n + j + 4;
      if (k != 0)
        i2 += n;
      if (paramGraphics != null)
      {
        paramGraphics.setColor(cYellow);
        paramGraphics.fillRoundRect(paramInt1 - i1 / 2, paramInt2 - i2, i1, i2, 8, 8);
        paramGraphics.setColor(cBlue);
        paramGraphics.drawRoundRect(paramInt1 - i1 / 2, paramInt2 - i2, i1, i2, 8, 8);
        int i3 = paramInt2;
        paramGraphics.setFont(localFont2);
        paramGraphics.drawString(str, paramInt1 - i / 2, i3 - 2);
        i3 -= 2;
        i3 -= j;
        paramGraphics.setFont(localFont1);
        if (k != 0)
        {
          paramGraphics.drawString(this.m_ip, paramInt1 - getMetrics(paramGraphics, localFont1).stringWidth(this.m_ip) / 2, i3);
          i3 -= n;
        }
        paramGraphics.drawString((String)localObject, paramInt1 - m / 2, i3);
        i3 -= n;
      }
      return new int[] { i1, i2 };
    }
    Object localObject = { "msAvg", this.m_ms };
    String[] arrayOfString1 = { "msMin", this.m_msMin };
    String[] arrayOfString2 = { "msMax", this.m_msMax };
    String[] arrayOfString3 = { "Pkt Loss", this.m_pktLoss + "%" };
    String[] arrayOfString4 = { "Prob", getAffinity() + "%" };
    String[] arrayOfString5 = { "TTL", getTTL() };
    int i4 = 3;
    int i5 = 0;
    int i6 = drawStrings(null, localObject, 0, 0)[0];
    i5 += drawStrings(null, localObject, 0, 0)[0];
    i5 += i4;
    i5 += drawStrings(null, arrayOfString1, 0, 0)[0];
    i5 += i4;
    i5 += drawStrings(null, arrayOfString2, 0, 0)[0];
    i5 += i4;
    i5 += drawStrings(null, arrayOfString3, 0, 0)[0];
    if (isType(TYPE_SHOW_TTL_TOOLTIP))
    {
      i5 += i4;
      i5 += drawStrings(null, arrayOfString5, 0, 0)[0];
    }
    if (isType(TYPE_LOAD_BALANCED))
    {
      i5 += i4;
      i5 += drawStrings(null, arrayOfString4, 0, 0)[0];
    }
    int i7 = getMetrics(paramGraphics, localFont1).getHeight();
    int i8 = Math.max(i5 + 2, getMetrics(paramGraphics, localFont2).stringWidth(str) + 2);
    int i9 = i6 + j + 4;
    if (k != 0)
      i9 += i7;
    if (paramGraphics != null)
    {
      paramGraphics.setColor(cYellow);
      paramGraphics.fillRoundRect(paramInt1 - i8 / 2, paramInt2 - i9, i8, i9, 8, 8);
      paramGraphics.setColor(cBlue);
      paramGraphics.drawRoundRect(paramInt1 - i8 / 2, paramInt2 - i9, i8, i9, 8, 8);
      int i10 = paramInt1;
      int i11 = paramInt2 - 2;
      paramGraphics.setFont(localFont2);
      paramGraphics.drawString(str, i10 - i / 2, i11);
      i11 -= j;
      paramGraphics.setFont(localFont1);
      if (k != 0)
      {
        paramGraphics.drawString(this.m_ip, i10 - getMetrics(paramGraphics, localFont1).stringWidth(this.m_ip) / 2, i11);
        i11 -= i7;
      }
      i10 = paramInt1 - i5 / 2;
      i10 += drawStrings(paramGraphics, localObject, i10, i11)[0];
      i10 += i4;
      i10 += drawStrings(paramGraphics, arrayOfString1, i10, i11)[0];
      i10 += i4;
      i10 += drawStrings(paramGraphics, arrayOfString2, i10, i11)[0];
      i10 += i4;
      i10 += drawStrings(paramGraphics, arrayOfString3, i10, i11)[0];
      if (isType(TYPE_LOAD_BALANCED))
      {
        i10 += i4;
        i10 += drawStrings(paramGraphics, arrayOfString4, i10, i11)[0];
      }
      if (isType(TYPE_SHOW_TTL_TOOLTIP))
      {
        i10 += i4;
        i10 += drawStrings(paramGraphics, arrayOfString5, i10, i11)[0];
      }
    }
    return new int[] { i8, i9 };
  }

  private int[] drawStrings(Graphics paramGraphics, String[] paramArrayOfString, int paramInt1, int paramInt2)
  {
    Font localFont = new Font("Helvetica", 0, 10);
    int i = getMetrics(paramGraphics, localFont).getHeight();
    int j = getMetrics(paramGraphics, localFont).stringWidth(paramArrayOfString[0]);
    int k = getMetrics(paramGraphics, localFont).stringWidth(paramArrayOfString[1]);
    int m = j > k ? j : k;
    int n = i * 2;
    if (paramGraphics != null)
    {
      paramGraphics.setFont(localFont);
      paramGraphics.setColor(cBlue);
      if (j > k)
      {
        paramGraphics.drawString(paramArrayOfString[0], paramInt1, paramInt2 - i - 3);
        paramGraphics.drawString(paramArrayOfString[1], paramInt1 + (j - k) / 2, paramInt2);
      }
      else
      {
        paramGraphics.drawString(paramArrayOfString[0], paramInt1 + (k - j) / 2, paramInt2 - i);
        paramGraphics.drawString(paramArrayOfString[1], paramInt1, paramInt2);
      }
    }
    return new int[] { m, n };
  }

  private void adjust(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Point paramPoint1, Point paramPoint2)
  {
    if (paramPoint1.x > paramInt1)
      paramPoint1.x = paramInt1;
    if (paramPoint1.y > paramInt2)
      paramPoint1.y = paramInt2;
    if (paramPoint2.x < paramInt3)
      paramPoint2.x = paramInt3;
    if (paramPoint2.y < paramInt4)
      paramPoint2.y = paramInt4;
  }

  private FontMetrics getMetrics(Graphics paramGraphics, Font paramFont)
  {
    if (paramGraphics == null)
      return Toolkit.getDefaultToolkit().getFontMetrics(paramFont);
    return paramGraphics.getFontMetrics(paramFont);
  }

  private Color getUpperColour()
  {
    if (getMaxMS() <= G.getNodeMSLower())
      return cGreen;
    if (getMaxMS() < G.getNodeMSUpper())
      return cOrange;
    return cRed;
  }

  private Color getLowerColour()
  {
    if (getPktLoss() <= G.getNodePktLossLower())
      return cGreen;
    if (getPktLoss() < G.getNodePktLossUpper())
      return cOrange;
    return cRed;
  }

  public void handleHitRegion(int paramInt, NodeHitRegion paramNodeHitRegion)
  {
    this.m_nMouseState = paramInt;
  }

  public boolean compare(String paramString, int paramInt)
  {
    if (isType(TYPE_JOIN))
      return false;
    if (isType(TYPE_UNKOWN))
      return false;
    if (getTTL() == -1)
      return this.m_ip.equals(paramString);
    return (getTTL() == paramInt) && (this.m_ip.equals(paramString));
  }

  public boolean equals(NetworkNode paramNetworkNode)
  {
    return paramNetworkNode.getUID() == getUID();
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.NetworkNode
 * JD-Core Version:    0.6.2
 */