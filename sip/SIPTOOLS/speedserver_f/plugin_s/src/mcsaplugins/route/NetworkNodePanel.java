package mcsaplugins.route;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class NetworkNodePanel extends SmoothUpdatePanel
  implements Runnable, MouseMotionListener, MouseListener
{
  private static final int DRAG_DIM = 2;
  private static final Color cRed = new Color(255, 64, 64);
  public static final int TYPE_SINGLE_ROUTE = 0;
  public static final int TYPE_MULTIPLE_ROUTES = 1;
  public static final int ORDER_AVG_LATENCY = 0;
  public static final int ORDER_MAX_LATENCY = 1;
  public static final int ORDER_MIN_LATENCY = 2;
  public static final int ORDER_PACKET_LOSS = 3;
  public static final int ORDER_AFFINITY = 4;
  public static final int MODE_ROUTE = 0;
  public static final int MODE_NODE = 1;
  private Object m_lock = new Object();
  private boolean m_bMoPrev = false;
  private boolean m_bMoNext = false;
  private boolean m_bShowLabels = true;
  private boolean m_bAutoSelectRoute = true;
  private boolean m_bPaintInit = true;
  private boolean m_bPaintInitHelp = false;
  private int m_type = 1;
  private int m_order = 0;
  private int m_scale = 1;
  private Vector m_hits = new Vector();
  private Vector m_routes = new Vector();
  private Point m_ptClick = null;
  private Point m_ptTrans = new Point(0, 0);
  private Thread m_thread = null;
  private NetworkNodeLayoutManager m_nnlp = new NetworkNodeLayoutManager();
  private boolean m_bNodeUpdate = false;
  private boolean m_bRunning = true;

  public NetworkNodePanel()
  {
    startThread();
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void kill()
  {
    this.m_bRunning = false;
    this.m_nnlp.kill();
    if (this.m_thread != null)
    {
      for (int i = 0; (this.m_thread != null) && (this.m_thread.isAlive()) && (i < 150); i++)
        U.sleep(100L);
      if ((this.m_thread != null) && (this.m_thread.isAlive()))
        this.m_thread = null;
      this.m_thread = null;
    }
    this.m_nnlp = null;
  }

  public void reset()
  {
    synchronized (this.m_lock)
    {
      this.m_routes.removeAllElements();
      this.m_nnlp.reset();
      this.m_ptClick = null;
      this.m_bPaintInitHelp = true;
      repaint();
    }
  }

  public void addRoute(TraceRoute paramTraceRoute)
  {
    if (!valid(paramTraceRoute))
      return;
    if (this.m_bPaintInit)
    {
      this.m_bPaintInit = false;
      this.m_bPaintInitHelp = true;
    }
    this.m_routes.removeAllElements();
    this.m_routes.addElement(paramTraceRoute);
    this.m_bNodeUpdate = true;
  }

  private void startThread()
  {
    this.m_thread = new Thread(this, "VR-NNP-" + hashCode());
    this.m_thread.start();
  }

  public void removeRoute(TraceRoute paramTraceRoute)
  {
  }

  private void highlightExtremum(boolean paramBoolean)
  {
    if (this.m_nnlp.getNodes().size() == 0)
      return;
    if (this.m_nnlp.getNodes().size() == 1)
      setHighlight(this.m_nnlp.getNodes());
    Vector localVector1 = new Vector();
    int i = 0;
    Vector localVector2 = null;
    int j = 0;
    while (((localVector2 = getNodesAt(i++)) != null) && (j == 0))
      if (containsDestination(localVector2))
      {
        localVector1.addElement(getDestinationNode(localVector2));
        j = 1;
      }
      else
      {
        Enumeration localEnumeration = localVector2.elements();
        while (localEnumeration.hasMoreElements())
        {
          NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
          if ((localNetworkNode.getOrder() == 0L) && (!localVector1.contains(localNetworkNode)))
            localVector1.addElement(localNetworkNode);
        }
      }
    if (localVector1.size() != 0)
      setHighlight(localVector1);
  }

  private boolean containsDestination(Vector paramVector)
  {
    if (paramVector == null)
      return false;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.isType(NetworkNode.TYPE_DESTINATION))
        return true;
    }
    return false;
  }

  private Vector getNodesAt(int paramInt)
  {
    if (this.m_nnlp.getNodes() == null)
      return null;
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_nnlp.getNodes().elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((localNetworkNode.getPosition() != null) && (localNetworkNode.getPosition().x == paramInt))
        localVector.addElement(localNetworkNode);
    }
    return localVector.size() == 0 ? null : localVector;
  }

  public void smoothPaint(Graphics paramGraphics)
  {
    synchronized (this.m_lock)
    {
      this.m_hits.removeAllElements();
      paintBackground(paramGraphics);
      if (paintDisabledText(paramGraphics))
        return;
      paintInit(paramGraphics);
      paintInitHelp(paramGraphics);
      centre();
      int i = this.m_ptTrans.x;
      int j = this.m_ptTrans.y;
      paramGraphics.translate(i, j);
      paintLines(paramGraphics);
      paintNodes(paramGraphics);
      paramGraphics.translate(-i, -j);
    }
  }

  public void centre()
  {
    if (this.m_bPaintInitHelp)
    {
      Vector localVector = this.m_nnlp.getNodes();
      if (localVector == null)
        return;
      Enumeration localEnumeration = localVector.elements();
      int i = 2147483647;
      int j = -2147483648;
      while (localEnumeration.hasMoreElements())
      {
        NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
        if (localNetworkNode.getDrawPoint().x < i)
          i = localNetworkNode.getDrawPoint().x;
        if (localNetworkNode.getDrawPoint().x > j)
          j = localNetworkNode.getDrawPoint().x;
      }
      int k = j - i;
      int m = (getSize().width - k) / 2;
      m = m < 0 ? 0 : m;
      this.m_ptTrans.setLocation(m, 0);
    }
  }

  private boolean paintDisabledText(Graphics paramGraphics)
  {
    if (this.m_nnlp.getNodes() != null)
      return false;
    if (G.getParis())
      return false;
    int i = getSize().width;
    int j = getSize().height;
    paramGraphics.setColor(new Color(226, 234, 243).darker());
    Font localFont = new Font("Helvetica", 1, 13);
    paramGraphics.setFont(localFont);
    String str = G.getParisDisabledText();
    drawString(paramGraphics, str, i, j);
    return true;
  }

  private void drawString(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = paramGraphics.getFontMetrics().getHeight();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "\n");
    int k = localStringTokenizer.countTokens();
    int m = (paramInt2 - k * j) / 2;
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      int n = paramGraphics.getFontMetrics().stringWidth(str);
      paramGraphics.drawString(str, (paramInt1 - n) / 2, (paramInt2 - m) / 2 + i);
      i += j;
    }
  }

  private void paintInitHelp(Graphics paramGraphics)
  {
    if (!this.m_bPaintInitHelp)
      return;
    int i = getSize().height;
    paramGraphics.setColor(new Color(226, 234, 243).darker());
    Font localFont = new Font("Helvetica", 1, 13);
    paramGraphics.setFont(localFont);
    String str = G.getParisHelpText();
    paramGraphics.drawString(str, 3, i - 3);
  }

  private void paintInit(Graphics paramGraphics)
  {
    if (!this.m_bPaintInit)
      return;
    int i = getSize().width;
    int j = getSize().height;
    paramGraphics.setColor(new Color(226, 234, 243).darker());
    Font localFont = new Font("Helvetica", 1, 13);
    paramGraphics.setFont(localFont);
    String str = G.getParisInitText();
    int k = paramGraphics.getFontMetrics().stringWidth(str);
    int m = paramGraphics.getFontMetrics().getHeight();
    int n = (i - k) / 2;
    int i1 = (j + m - paramGraphics.getFontMetrics().getAscent()) / 2;
    n = n < 0 ? 0 : n;
    paramGraphics.drawString(str, n, i1);
    this.m_hits.addElement(new RectHitRegion("HELP", null, n, i1 - m, n + k, i1 + m));
  }

  private void paintBackground(Graphics paramGraphics)
  {
    if (paramGraphics == null)
      return;
    Dimension localDimension = getSize();
    if (localDimension == null)
      return;
    int i = localDimension.width;
    int j = localDimension.height;
    U.gradientFill(paramGraphics, 0, 0, i, j, new Color(247, 247, 247), new Color(226, 234, 243));
  }

  private void paintLines(Graphics paramGraphics)
  {
    Vector localVector1 = this.m_nnlp.getNodeCouplets();
    if (localVector1 == null)
      return;
    Enumeration localEnumeration = localVector1.elements();
    Vector localVector2 = new Vector();
    paramGraphics.setColor(Color.black);
    NetworkNodeCouplet localNetworkNodeCouplet;
    NetworkNode localNetworkNode1;
    NetworkNode localNetworkNode2;
    boolean bool1;
    while (localEnumeration.hasMoreElements())
    {
      localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      localNetworkNode1 = localNetworkNodeCouplet.getLeftmostNode();
      localNetworkNode2 = localNetworkNodeCouplet.getRightmostNode();
      bool1 = (localNetworkNode1.getHighlight()) && (localNetworkNode2.getHighlight());
      if (!bool1)
      {
        boolean bool2 = (localNetworkNode1.isType(NetworkNode.TYPE_UNKOWN)) || (localNetworkNode2.isType(NetworkNode.TYPE_UNKOWN));
        paintLine(paramGraphics, localNetworkNode1.getDrawPoint(), localNetworkNode2.getDrawPoint(), bool1, bool2);
      }
      else
      {
        localVector2.addElement(localNetworkNodeCouplet);
      }
    }
    localEnumeration = localVector2.elements();
    while (localEnumeration.hasMoreElements())
    {
      localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      localNetworkNode1 = localNetworkNodeCouplet.getLeftmostNode();
      localNetworkNode2 = localNetworkNodeCouplet.getRightmostNode();
      bool1 = (localNetworkNode1.isType(NetworkNode.TYPE_UNKOWN)) || (localNetworkNode2.isType(NetworkNode.TYPE_UNKOWN));
      paintLine(paramGraphics, localNetworkNode1.getDrawPoint(), localNetworkNode2.getDrawPoint(), true, bool1);
    }
  }

  private void paintLine(Graphics paramGraphics, Point paramPoint1, Point paramPoint2, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = 2;
    int j = 15;
    paramGraphics.setColor(Color.black);
    if ((paramPoint1 != null) && (paramPoint2 != null))
      if (paramPoint1.y == paramPoint2.y)
      {
        int k = toPixels(paramPoint1.x);
        int m = toPixels(paramPoint1.y);
        int n = toPixels(paramPoint2.x);
        int i1 = toPixels(paramPoint2.y);
        if (paramBoolean1)
        {
          paramGraphics.setColor(!paramBoolean2 ? Color.blue : cRed);
          drawLine(k, m - 1, n, i1 - 1, paramGraphics, paramBoolean2);
          drawLine(k, m + 1, n, i1 + 1, paramGraphics, paramBoolean2);
        }
        paramGraphics.setColor(!paramBoolean2 ? Color.blue : cRed);
        drawLine(k, m, n, i1, paramGraphics, paramBoolean2);
      }
      else if (paramPoint1.y < paramPoint2.y)
      {
        if (paramBoolean1)
        {
          paramGraphics.setColor(!paramBoolean2 ? Color.blue : cRed);
          drawLine(paramPoint1.x, paramPoint1.y + 1, paramPoint1.x + i, paramPoint1.y + 1, paramGraphics, paramBoolean2);
          drawLine(paramPoint1.x, paramPoint1.y, paramPoint1.x + i, paramPoint1.y, paramGraphics, paramBoolean2);
          drawLine(paramPoint1.x, paramPoint1.y - 1, paramPoint1.x + i, paramPoint1.y - 1, paramGraphics, paramBoolean2);
          drawLine(i + toPixels(paramPoint1.x) - 1, toPixels(paramPoint1.y), i + toPixels(paramPoint1.x) - 1, toPixels(paramPoint2.y) - j, paramGraphics, paramBoolean2);
          paramGraphics.drawArc(i + toPixels(paramPoint1.x) - 1, toPixels(paramPoint2.y) - 2 * j, 2 * j, 2 * j, 180, 90);
          paramGraphics.drawArc(i + toPixels(paramPoint1.x) - 1, toPixels(paramPoint2.y) - 2 * j + 1, 2 * j, 2 * j, 180, 120);
          drawLine(i + toPixels(paramPoint1.x) + j, toPixels(paramPoint2.y) - 1, i + toPixels(paramPoint2.x), toPixels(paramPoint2.y) - 1, paramGraphics, paramBoolean2);
          drawLine(i + toPixels(paramPoint1.x) + 1, toPixels(paramPoint1.y), i + toPixels(paramPoint1.x) + 1, toPixels(paramPoint2.y) - j, paramGraphics, paramBoolean2);
          paramGraphics.drawArc(i + toPixels(paramPoint1.x) + 1, toPixels(paramPoint2.y) - 2 * j, 2 * j, 2 * j, 180, 90);
          drawLine(i + toPixels(paramPoint1.x) + j, toPixels(paramPoint2.y) + 1, i + toPixels(paramPoint2.x), toPixels(paramPoint2.y) + 1, paramGraphics, paramBoolean2);
        }
        paramGraphics.setColor(!paramBoolean2 ? Color.blue : cRed);
        drawLine(paramPoint1.x, paramPoint1.y, paramPoint1.x + i, paramPoint1.y, paramGraphics, paramBoolean2);
        drawLine(i + toPixels(paramPoint1.x), toPixels(paramPoint1.y), i + toPixels(paramPoint1.x), toPixels(paramPoint2.y) - j, paramGraphics, paramBoolean2);
        paramGraphics.drawArc(i + toPixels(paramPoint1.x), toPixels(paramPoint2.y) - 2 * j, 2 * j, 2 * j, 180, 90);
        drawLine(i + toPixels(paramPoint1.x) + j, toPixels(paramPoint2.y), i + toPixels(paramPoint2.x), toPixels(paramPoint2.y), paramGraphics, paramBoolean2);
      }
      else
      {
        if (paramBoolean1)
        {
          paramGraphics.setColor(!paramBoolean2 ? Color.blue : cRed);
          drawLine(paramPoint2.x - i, paramPoint2.y + 1, paramPoint2.x, paramPoint2.y + 1, paramGraphics, paramBoolean2);
          drawLine(paramPoint2.x - i, paramPoint2.y, paramPoint2.x, paramPoint2.y, paramGraphics, paramBoolean2);
          drawLine(paramPoint2.x - i, paramPoint2.y - 1, paramPoint2.x, paramPoint2.y - 1, paramGraphics, paramBoolean2);
          drawLine(-i + toPixels(paramPoint1.x), toPixels(paramPoint1.y) - 1, -i + toPixels(paramPoint2.x) - j, toPixels(paramPoint1.y) - 1, paramGraphics, paramBoolean2);
          paramGraphics.drawArc(-i + toPixels(paramPoint2.x) - 2 * j, toPixels(paramPoint1.y) - 2 * j - 1, 2 * j, 2 * j, -90, 90);
          drawLine(-i + toPixels(paramPoint2.x) - 1, toPixels(paramPoint1.y) - j, -i + toPixels(paramPoint2.x) - 1, toPixels(paramPoint2.y), paramGraphics, paramBoolean2);
          drawLine(-i + toPixels(paramPoint1.x), toPixels(paramPoint1.y) + 1, -i + toPixels(paramPoint2.x) - j, toPixels(paramPoint1.y) + 1, paramGraphics, paramBoolean2);
          paramGraphics.drawArc(-i + toPixels(paramPoint2.x) - 2 * j, toPixels(paramPoint1.y) - 2 * j + 1, 2 * j, 2 * j, -90, 90);
          paramGraphics.drawArc(-i + toPixels(paramPoint2.x) - 2 * j + 1, toPixels(paramPoint1.y) - 2 * j + 1, 2 * j, 2 * j, -90, 120);
          drawLine(-i + toPixels(paramPoint2.x) + 1, toPixels(paramPoint1.y) - j, -i + toPixels(paramPoint2.x) + 1, toPixels(paramPoint2.y), paramGraphics, paramBoolean2);
        }
        paramGraphics.setColor(!paramBoolean2 ? Color.blue : cRed);
        drawLine(paramPoint2.x - i, paramPoint2.y, paramPoint2.x, paramPoint2.y, paramGraphics, paramBoolean2);
        drawLine(-i + toPixels(paramPoint1.x), toPixels(paramPoint1.y), -i + toPixels(paramPoint2.x) - j, toPixels(paramPoint1.y), paramGraphics, paramBoolean2);
        paramGraphics.drawArc(-i + toPixels(paramPoint2.x) - 2 * j, toPixels(paramPoint1.y) - 2 * j, 2 * j, 2 * j, -90, 90);
        drawLine(-i + toPixels(paramPoint2.x), toPixels(paramPoint1.y) - j, -i + toPixels(paramPoint2.x), toPixels(paramPoint2.y), paramGraphics, paramBoolean2);
      }
  }

  private int toPixels(int paramInt)
  {
    return paramInt;
  }

  private void paintNodes(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    Vector localVector1 = this.m_nnlp.getNodes();
    if (localVector1 == null)
      return;
    boolean bool = this.m_scale == 0 ? false : this.m_bShowLabels;
    Vector localVector2 = new Vector();
    Enumeration localEnumeration = localVector1.elements();
    NetworkNode localNetworkNode;
    while (localEnumeration.hasMoreElements())
    {
      localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.getMouseState() == 0)
        localNetworkNode.draw(paramGraphics, localNetworkNode.getDrawPoint().x, localNetworkNode.getDrawPoint().y, i, j, bool, this.m_hits, this.m_ptTrans);
      else
        localVector2.addElement(localNetworkNode);
    }
    localEnumeration = localVector2.elements();
    while (localEnumeration.hasMoreElements())
    {
      localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      localNetworkNode.draw(paramGraphics, localNetworkNode.getDrawPoint().x, localNetworkNode.getDrawPoint().y, i, j, bool, this.m_hits, this.m_ptTrans);
    }
  }

  private void reDoLayout(boolean paramBoolean)
  {
    synchronized (this.m_lock)
    {
      if (this.m_type == 0)
        reDoSingleRoute();
      reDoPaintLayout();
      if (paramBoolean)
        highlightExtremum(true);
      else
        validateHighlight();
    }
  }

  private void reDoPaintLayout()
  {
    reDoXLayout();
    reDoYLayout();
  }

  private void reDoYLayout()
  {
    int i = 0;
    int j = 0;
    int k = 0;
    for (Vector localVector = getAllNodesAt(i, false); localVector != null; localVector = getAllNodesAt(i, false))
    {
      Enumeration localEnumeration = localVector.elements();
      NetworkNode localNetworkNode;
      while (localEnumeration.hasMoreElements())
      {
        localNetworkNode = (NetworkNode)localEnumeration.nextElement();
        if ((!localNetworkNode.isType(NetworkNode.TYPE_JOIN)) && (!localNetworkNode.isType(NetworkNode.TYPE_FIREWALL)))
        {
          int m = localNetworkNode.draw(null, 0, 0, 0, 0, this.m_bShowLabels, null, this.m_ptTrans).height;
          if (k < m)
            k = m;
        }
      }
      j += k;
      if (!this.m_bShowLabels)
        j += 10;
      localEnumeration = localVector.elements();
      while (localEnumeration.hasMoreElements())
      {
        localNetworkNode = (NetworkNode)localEnumeration.nextElement();
        localNetworkNode.setDrawPoint(j, false);
      }
      i++;
      k = 0;
    }
  }

  private void reDoXLayout()
  {
    if (this.m_scale == 2)
      reDoXLayoutLarge();
    else if (this.m_scale == 0)
      reDoXLayoutSmall();
    else
      reDoXLayoutMedium();
  }

  private void reDoXLayoutSmall()
  {
    Vector localVector = this.m_nnlp.getNodes();
    Enumeration localEnumeration = localVector.elements();
    int i = 25;
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      localNetworkNode.setDrawPoint(50 + localNetworkNode.getPosition().x * i, true);
      localNetworkNode.setIPOffset(1);
    }
  }

  private void reDoXLayoutMedium()
  {
    int i = 0;
    int j = 20;
    int k = 5;
    Vector localVector = getAllNodesAt(i, true);
    int m = 1;
    while (localVector != null)
    {
      Enumeration localEnumeration = localVector.elements();
      int n = 0;
      int i1 = 0;
      NetworkNode localNetworkNode;
      while (localEnumeration.hasMoreElements())
      {
        localNetworkNode = (NetworkNode)localEnumeration.nextElement();
        if (!this.m_bShowLabels)
          localNetworkNode.setIPOffset(1);
        else if (!localNetworkNode.isType(NetworkNode.TYPE_UNKOWN))
          if (!localNetworkNode.isType(NetworkNode.TYPE_LOAD_BALANCED))
          {
            if (m != 0)
              localNetworkNode.setIPOffset(1);
            else
              localNetworkNode.setIPOffset(2);
            if (!localNetworkNode.isType(NetworkNode.TYPE_FIREWALL))
              m = m != 0 ? 0 : 1;
          }
          else
          {
            m = 0;
            localNetworkNode.setIPOffset(1);
          }
        int i2 = localNetworkNode.draw(null, 0, 0, 0, 0, this.m_bShowLabels, null, this.m_ptTrans).width;
        if (i1 < i2)
          i1 = i2;
      }
      if (!this.m_bShowLabels)
        i1 = 30;
      j += k;
      localEnumeration = localVector.elements();
      while (localEnumeration.hasMoreElements())
      {
        localNetworkNode = (NetworkNode)localEnumeration.nextElement();
        if (!this.m_bShowLabels)
        {
          localNetworkNode.setDrawPoint(j + i1 / 2 + k, true);
        }
        else if (localNetworkNode.getIPOffset() == 2)
        {
          localNetworkNode.setDrawPoint(j, true);
          n = 1;
        }
        else if (!localNetworkNode.isType(NetworkNode.TYPE_LOAD_BALANCED))
        {
          localNetworkNode.setDrawPoint(j, true);
          n = 1;
        }
        else
        {
          localNetworkNode.setDrawPoint(j + i1 / 2, true);
        }
      }
      j += (n != 0 ? i1 / 2 : i1);
      j += k;
      i++;
      localVector = getAllNodesAt(i, true);
    }
  }

  private void reDoXLayoutLarge()
  {
    Vector localVector = this.m_nnlp.getNodes();
    Enumeration localEnumeration = localVector.elements();
    int i = 85;
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      localNetworkNode.setDrawPoint(50 + localNetworkNode.getPosition().x * i, true);
      localNetworkNode.setIPOffset(1);
    }
  }

  private Vector getAllNodesAt(int paramInt, boolean paramBoolean)
  {
    Vector localVector1 = this.m_nnlp.getNodes();
    if (localVector1 == null)
      return null;
    Vector localVector2 = new Vector();
    Enumeration localEnumeration = localVector1.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.getPosition() != null)
        if (paramBoolean)
        {
          if (localNetworkNode.getPosition().x == paramInt)
            localVector2.addElement(localNetworkNode);
        }
        else if (localNetworkNode.getPosition().y == paramInt)
          localVector2.addElement(localNetworkNode);
    }
    return localVector2.size() == 0 ? null : localVector2;
  }

  private void reDoSingleRoute()
  {
    int i = -1;
    TraceRoute localTraceRoute = getRoute();
    localTraceRoute = removeNullHops(localTraceRoute);
    if (localTraceRoute == null)
      return;
    if ((localTraceRoute.hops != null) && (localTraceRoute.hops.length == 1))
    {
      localObject1 = localTraceRoute.hops[0];
      localObject2 = makeNode((TraceRouteHop)localObject1, localTraceRoute);
      NetworkNodeCouplet localNetworkNodeCouplet1 = new NetworkNodeCouplet((NetworkNode)localObject2, (NetworkNode)localObject2);
      localVector1 = new Vector();
      localVector1.add(localNetworkNodeCouplet1);
      this.m_nnlp.setNodeCouplets(localVector1);
      this.m_nnlp.arrange();
      return;
    }
    Object localObject1 = null;
    Object localObject2 = getNextTTLHops(localTraceRoute, i);
    int j = 0;
    Vector localVector1 = new Vector();
    if (localObject2 != null)
      i = ((TraceRouteHop)((Vector)localObject2).firstElement()).sentTTL;
    int k = i != 0 ? 1 : 0;
    while (localObject2 != null)
    {
      Vector localVector2 = getNextTTLHops(localTraceRoute, i);
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      NetworkNodeCouplet localNetworkNodeCouplet2;
      if (k != 0)
      {
        k = 0;
        localObject3 = createFakeNode();
        localObject4 = makeNodes((Vector)localObject2, localTraceRoute);
        localObject5 = ((Vector)localObject4).elements();
        while (((Enumeration)localObject5).hasMoreElements())
        {
          localObject6 = (NetworkNode)((Enumeration)localObject5).nextElement();
          localNetworkNodeCouplet2 = new NetworkNodeCouplet((NetworkNode)localObject3, (NetworkNode)localObject6);
          localNetworkNodeCouplet2.setPosition((NetworkNode)localObject3, j);
          localNetworkNodeCouplet2.setPosition((NetworkNode)localObject6, j + 1);
          localVector1.addElement(localNetworkNodeCouplet2);
        }
        j++;
      }
      else if (localObject1 != null)
      {
        localObject3 = makeNodes((Vector)localObject2, localTraceRoute);
        localObject4 = ((Vector)localObject3).elements();
        while (((Enumeration)localObject4).hasMoreElements())
        {
          localObject5 = (NetworkNode)((Enumeration)localObject4).nextElement();
          localObject6 = new NetworkNodeCouplet((NetworkNode)localObject1, (NetworkNode)localObject5);
          ((NetworkNodeCouplet)localObject6).setPosition((NetworkNode)localObject1, j);
          ((NetworkNodeCouplet)localObject6).setPosition((NetworkNode)localObject5, j + 1);
          localVector1.addElement(localObject6);
        }
        localObject1 = null;
        j++;
      }
      else if (localVector2 == null)
      {
        if (localVector1.size() == 0)
        {
          localObject3 = makeNode((TraceRouteHop)((Vector)localObject2).firstElement(), localTraceRoute);
          if (localObject3 != null)
          {
            localObject4 = new NetworkNodeCouplet((NetworkNode)localObject3, (NetworkNode)localObject3);
            ((NetworkNodeCouplet)localObject4).unifyPositions(true);
            localVector1.addElement(localObject4);
          }
          else
          {
            System.out.println("NNP-n=null-1");
          }
        }
        localObject2 = null;
      }
      else if (!hopsSequential((Vector)localObject2, localVector2))
      {
        localObject1 = createFakeNode();
        localObject3 = makeNodes((Vector)localObject2, localTraceRoute);
        localObject4 = ((Vector)localObject3).elements();
        while (((Enumeration)localObject4).hasMoreElements())
        {
          localObject5 = (NetworkNode)((Enumeration)localObject4).nextElement();
          if (!((NetworkNode)localObject5).isType(NetworkNode.TYPE_DESTINATION))
          {
            localObject6 = new NetworkNodeCouplet((NetworkNode)localObject5, (NetworkNode)localObject1);
            ((NetworkNodeCouplet)localObject6).setPosition((NetworkNode)localObject5, j);
            ((NetworkNodeCouplet)localObject6).setPosition((NetworkNode)localObject1, j + 1);
            localVector1.addElement(localObject6);
          }
        }
        i = getTTL(localVector2);
        localObject2 = localVector2;
        j++;
      }
      else if (localVector2.size() == 1)
      {
        localObject3 = makeNode((TraceRouteHop)localVector2.firstElement(), localTraceRoute);
        if (localObject3 == null)
        {
          System.out.println("NNP-n=null-2");
          localObject2 = null;
        }
        else
        {
          localObject4 = makeNodes((Vector)localObject2, localTraceRoute);
          localObject5 = ((Vector)localObject4).elements();
          while (((Enumeration)localObject5).hasMoreElements())
          {
            localObject6 = (NetworkNode)((Enumeration)localObject5).nextElement();
            if (!((NetworkNode)localObject6).isType(NetworkNode.TYPE_DESTINATION))
            {
              localNetworkNodeCouplet2 = new NetworkNodeCouplet((NetworkNode)localObject6, (NetworkNode)localObject3);
              localNetworkNodeCouplet2.setPosition((NetworkNode)localObject6, j);
              localNetworkNodeCouplet2.setPosition((NetworkNode)localObject3, j + 1);
              localVector1.addElement(localNetworkNodeCouplet2);
            }
          }
          i = getTTL(localVector2);
          localObject2 = localVector2;
          j++;
        }
      }
      else
      {
        localObject1 = createJoiningNode();
        localObject3 = makeNodes((Vector)localObject2, localTraceRoute);
        localObject4 = ((Vector)localObject3).elements();
        while (((Enumeration)localObject4).hasMoreElements())
        {
          localObject5 = (NetworkNode)((Enumeration)localObject4).nextElement();
          if (!((NetworkNode)localObject5).isType(NetworkNode.TYPE_DESTINATION))
          {
            localObject6 = new NetworkNodeCouplet((NetworkNode)localObject5, (NetworkNode)localObject1);
            ((NetworkNodeCouplet)localObject6).setPosition((NetworkNode)localObject5, j);
            ((NetworkNodeCouplet)localObject6).setPosition((NetworkNode)localObject1, j + 1);
            localVector1.addElement(localObject6);
          }
        }
        i = getTTL(localVector2);
        localObject2 = localVector2;
        j++;
      }
    }
    this.m_nnlp.setNodeCouplets(localVector1);
    this.m_nnlp.arrange();
  }

  private NetworkNode makeNode(TraceRouteHop paramTraceRouteHop, TraceRoute paramTraceRoute)
  {
    NetworkNode localNetworkNode = this.m_nnlp.createNode(paramTraceRouteHop, true);
    if (localNetworkNode == null)
    {
      System.out.println("NNP-n=null-3");
      System.out.println("hs.displayIP=" + paramTraceRouteHop.ip);
    }
    localNetworkNode.addType(NetworkNode.TYPE_SHOW_TTL_TOOLTIP);
    if (localNetworkNode.isType(NetworkNode.TYPE_DESTINATION))
      localNetworkNode.addType(NetworkNode.TYPE_SHOW_TTL);
    return localNetworkNode;
  }

  private Vector makeNodes(Vector paramVector, TraceRoute paramTraceRoute)
  {
    int i = getMin(paramVector);
    int j = 0;
    Vector localVector = new Vector();
    if (paramVector.size() == 1)
    {
      localObject = makeNode((TraceRouteHop)paramVector.firstElement(), paramTraceRoute);
      if (localObject != null)
      {
        ((NetworkNode)localObject).setOrder(0L);
        localVector.addElement(localObject);
      }
      else
      {
        System.out.println("NNP-n=null-4");
      }
      return localVector;
    }
    Object localObject = new Vector();
    for (TraceRouteHop localTraceRouteHop = getNextMin(paramVector, i, (Vector)localObject); localTraceRouteHop != null; localTraceRouteHop = getNextMin(paramVector, i, (Vector)localObject))
    {
      NetworkNode localNetworkNode = makeNode(localTraceRouteHop, paramTraceRoute);
      if (localNetworkNode != null)
      {
        localNetworkNode.setOrder(j);
        j++;
        localVector.addElement(localNetworkNode);
      }
      else
      {
        System.out.println("NNP-n=null-5");
      }
      i = getMin(localTraceRouteHop);
    }
    return localVector;
  }

  private TraceRouteHop getNextMin(Vector paramVector1, int paramInt, Vector paramVector2)
  {
    Enumeration localEnumeration = paramVector1.elements();
    Object localObject = null;
    int i = 2147483647;
    while (localEnumeration.hasMoreElements())
    {
      TraceRouteHop localTraceRouteHop = (TraceRouteHop)localEnumeration.nextElement();
      int j = getMin(localTraceRouteHop);
      if ((j == paramInt) && (!paramVector2.contains(localTraceRouteHop)))
      {
        paramVector2.addElement(localTraceRouteHop);
        return localTraceRouteHop;
      }
      if ((j > paramInt) && (j < i))
      {
        i = j;
        localObject = localTraceRouteHop;
      }
    }
    paramVector2.addElement(localObject);
    return localObject;
  }

  private NetworkNode getDestinationNode(Vector paramVector)
  {
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.isType(NetworkNode.TYPE_DESTINATION))
        return localNetworkNode;
    }
    return null;
  }

  private int getMin(NetworkNode paramNetworkNode, int paramInt)
  {
    if (paramInt == 1)
      return paramNetworkNode.getMaxMS();
    if (paramInt == 2)
      return paramNetworkNode.getMinMS();
    if (paramInt == 3)
      return paramNetworkNode.getPktLoss();
    if (paramInt == 4)
      return paramNetworkNode.getAffinity();
    return paramNetworkNode.getMS();
  }

  private NetworkNode getMaxNode(Vector paramVector)
  {
    return getMaxNode(paramVector, this.m_order);
  }

  private NetworkNode getMaxNode(Vector paramVector, int paramInt)
  {
    int i = -2147483648;
    Object localObject = null;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      int j = getMin(localNetworkNode, paramInt);
      if ((j == i) && (localNetworkNode.getOrder() > localObject.getOrder()))
      {
        localObject = localNetworkNode;
      }
      else if (j > i)
      {
        i = j;
        localObject = localNetworkNode;
      }
    }
    return localObject;
  }

  private NetworkNode getMinNode(Vector paramVector)
  {
    return getMinNode(paramVector, this.m_order);
  }

  private NetworkNode getMinNode(Vector paramVector, int paramInt)
  {
    int i = 2147483647;
    Object localObject = null;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      int j = getMin(localNetworkNode, paramInt);
      if (j < i)
      {
        i = j;
        localObject = localNetworkNode;
      }
    }
    return localObject;
  }

  private int getMin(Vector paramVector)
  {
    int i = 2147483647;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      TraceRouteHop localTraceRouteHop = (TraceRouteHop)localEnumeration.nextElement();
      int j = getMin(localTraceRouteHop);
      if (j < i)
        i = j;
    }
    return i;
  }

  private int getMin(TraceRouteHop paramTraceRouteHop)
  {
    if (this.m_order == 1)
      return paramTraceRouteHop.maxMs;
    if (this.m_order == 2)
      return paramTraceRouteHop.minMs;
    if (this.m_order == 3)
      return paramTraceRouteHop.loss;
    if (this.m_order == 4)
      return paramTraceRouteHop.nAffinity;
    return paramTraceRouteHop.ms;
  }

  private NetworkNode createFakeNode()
  {
    NetworkNode localNetworkNode = new NetworkNode();
    localNetworkNode.setType(NetworkNode.TYPE_UNKOWN);
    return localNetworkNode;
  }

  private NetworkNode createJoiningNode()
  {
    NetworkNode localNetworkNode = new NetworkNode();
    localNetworkNode.setType(NetworkNode.TYPE_JOIN);
    return localNetworkNode;
  }

  public void nextRoute()
  {
    Vector localVector = getNextRoute(true);
    if (localVector != null)
    {
      setHighlight(localVector);
      repaint();
    }
  }

  public void prevRoute()
  {
    Vector localVector = getNextRoute(false);
    if (localVector != null)
    {
      setHighlight(localVector);
      repaint();
    }
  }

  public void setRoute(boolean paramBoolean, int paramInt)
  {
    NetworkNode localNetworkNode1 = getDestinationNode(paramBoolean);
    if (localNetworkNode1 == null)
      return;
    Vector localVector1 = new Vector();
    localVector1.addElement(localNetworkNode1);
    for (int i = 0; i < localNetworkNode1.getPosition().x; i++)
    {
      Vector localVector2 = getNodesAt(i);
      if (localVector2.size() == 1)
      {
        localVector1.addElement((NetworkNode)localVector2.firstElement());
      }
      else
      {
        NetworkNode localNetworkNode2 = getDestinationNode(localVector2);
        if (localNetworkNode2 != null)
          localVector2.removeElement(localNetworkNode2);
        if (paramBoolean)
          localVector1.addElement(getMinNode(localVector2, paramInt));
        else
          localVector1.addElement(getMaxNode(localVector2, paramInt));
      }
    }
    setHighlight(localVector1);
    repaint();
  }

  public void setSelectionMode(int paramInt)
  {
  }

  public void setScale(int paramInt)
  {
    if (paramInt != this.m_scale)
    {
      this.m_scale = paramInt;
      reDoLayout(this.m_bAutoSelectRoute);
      repaint();
    }
  }

  public void setOrder(int paramInt)
  {
    if (paramInt != this.m_order)
    {
      this.m_order = paramInt;
      reDoLayout(true);
      repaint();
    }
  }

  public void setShowLabels(boolean paramBoolean)
  {
    if (paramBoolean != this.m_bShowLabels)
    {
      this.m_bShowLabels = paramBoolean;
      reDoPaintLayout();
      repaint();
    }
  }

  private TraceRoute getRoute()
  {
    if (this.m_routes.size() == 0)
      return null;
    return ((TraceRoute)this.m_routes.firstElement()).doClone();
  }

  public void run()
  {
    while (this.m_bRunning)
    {
      if (this.m_bNodeUpdate)
        try
        {
          reDoLayout(this.m_bAutoSelectRoute);
          this.m_bNodeUpdate = false;
          repaint();
        }
        catch (Exception localException1)
        {
          System.out.println("E NNP");
          localException1.printStackTrace();
        }
      try
      {
        Thread.sleep(200L);
      }
      catch (Exception localException2)
      {
      }
    }
  }

  private static int getTTL(Vector paramVector)
  {
    return ((TraceRouteHop)paramVector.firstElement()).sentTTL;
  }

  private static Vector getNextTTLHops(TraceRoute paramTraceRoute, int paramInt)
  {
    int i = 2147483647;
    for (int j = 0; j < paramTraceRoute.hops.length; j++)
      if ((paramTraceRoute.hops[j].sentTTL > paramInt) && (paramTraceRoute.hops[j].sentTTL < i))
        i = paramTraceRoute.hops[j].sentTTL;
    return getTTLHops(paramTraceRoute, i);
  }

  private static Vector getTTLHops(TraceRoute paramTraceRoute, int paramInt)
  {
    Vector localVector = new Vector();
    for (int i = 0; i < paramTraceRoute.hops.length; i++)
      if (paramTraceRoute.hops[i].sentTTL == paramInt)
        localVector.addElement(paramTraceRoute.hops[i]);
    return localVector.size() == 0 ? null : localVector;
  }

  private NodeHitRegion getHit(MouseEvent paramMouseEvent)
  {
    Enumeration localEnumeration = this.m_hits.elements();
    while (localEnumeration.hasMoreElements())
      try
      {
        NodeHitRegion localNodeHitRegion = (NodeHitRegion)localEnumeration.nextElement();
        if (localNodeHitRegion.rect.contains(paramMouseEvent.getPoint()))
          return localNodeHitRegion;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private HitRegion getControlHit(MouseEvent paramMouseEvent)
  {
    Enumeration localEnumeration = this.m_hits.elements();
    while (localEnumeration.hasMoreElements())
      try
      {
        HitRegion localHitRegion = (HitRegion)localEnumeration.nextElement();
        if (localHitRegion.inside(paramMouseEvent.getPoint().x, paramMouseEvent.getPoint().y))
          return localHitRegion;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    if ((this.m_ptClick != null) && ((Math.abs(paramMouseEvent.getPoint().x - this.m_ptClick.x) > 2) || (Math.abs(paramMouseEvent.getPoint().y - this.m_ptClick.y) > 2)))
    {
      this.m_ptTrans.translate(paramMouseEvent.getPoint().x - this.m_ptClick.x, paramMouseEvent.getPoint().y - this.m_ptClick.y);
      this.m_ptClick = paramMouseEvent.getPoint();
      repaint();
    }
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    NodeHitRegion localNodeHitRegion = getHit(paramMouseEvent);
    HitRegion localHitRegion = getControlHit(paramMouseEvent);
    if (setMouseOver(localHitRegion))
    {
      repaint();
    }
    else if (setMouseOver(localNodeHitRegion))
    {
      repaint();
      setCursor(new Cursor(0));
    }
    else
    {
      setCursor(new Cursor(0));
    }
  }

  private boolean setMouseOver(HitRegion paramHitRegion)
  {
    if (paramHitRegion == null)
    {
      if ((!this.m_bMoPrev) && (!this.m_bMoNext))
        return false;
      this.m_bMoPrev = false;
      this.m_bMoNext = false;
      return true;
    }
    boolean bool = false;
    if ((paramHitRegion.isa("prev")) && (!this.m_bMoPrev))
    {
      this.m_bMoPrev = true;
      this.m_bMoNext = false;
      bool = true;
      setCursor(new Cursor(12));
    }
    else if ((paramHitRegion.isa("next")) && (!this.m_bMoNext))
    {
      setCursor(new Cursor(12));
      this.m_bMoNext = true;
      this.m_bMoPrev = false;
      bool = true;
    }
    else if (paramHitRegion.isa("HELP"))
    {
      setCursor(new Cursor(12));
      bool = true;
    }
    else if (paramHitRegion.isa("UPGR"))
    {
      setCursor(new Cursor(12));
      bool = true;
    }
    else
    {
      setCursor(new Cursor(0));
    }
    return bool;
  }

  public boolean setMouseOver(NodeHitRegion paramNodeHitRegion)
  {
    boolean bool = false;
    Enumeration localEnumeration = this.m_hits.elements();
    while (localEnumeration.hasMoreElements())
      try
      {
        NodeHitRegion localNodeHitRegion = (NodeHitRegion)localEnumeration.nextElement();
        if (localNodeHitRegion == paramNodeHitRegion)
        {
          if (localNodeHitRegion.node.getMouseState() != 1)
          {
            bool = true;
            localNodeHitRegion.node.handleHitRegion(1, localNodeHitRegion);
          }
        }
        else if (localNodeHitRegion.node.getMouseState() != 0)
        {
          bool = true;
          localNodeHitRegion.node.handleHitRegion(0, localNodeHitRegion);
        }
      }
      catch (Exception localException)
      {
      }
    return bool;
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    if (paramMouseEvent.getClickCount() == 2)
    {
      this.m_ptTrans.setLocation(0, 0);
      repaint();
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.m_bPaintInitHelp = false;
    NodeHitRegion localNodeHitRegion = getHit(paramMouseEvent);
    HitRegion localHitRegion = getControlHit(paramMouseEvent);
    if (localHitRegion == null)
      if (localNodeHitRegion != null)
      {
        if (!localNodeHitRegion.node.getHighlight())
        {
          this.m_bAutoSelectRoute = false;
          validateHighlight(localNodeHitRegion);
          repaint();
        }
      }
      else
        this.m_ptClick = paramMouseEvent.getPoint();
  }

  public void setHighlight(Vector paramVector)
  {
    if (this.m_nnlp.getNodes() == null)
      return;
    Enumeration localEnumeration = this.m_nnlp.getNodes().elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((paramVector != null) && (paramVector.contains(localNetworkNode)))
        localNetworkNode.setHighlight(true);
      else
        localNetworkNode.setHighlight(false);
    }
    updateListeners();
  }

  public void setAutoSelectRoute(boolean paramBoolean)
  {
    this.m_bAutoSelectRoute = paramBoolean;
  }

  public void setHighlight(NodeHitRegion paramNodeHitRegion)
  {
    paramNodeHitRegion.node.setHighlight(true);
    if (this.m_nnlp.getNodes() == null)
      return;
    Enumeration localEnumeration = this.m_nnlp.getNodes().elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((!localNetworkNode.equals(paramNodeHitRegion.node)) && (localNetworkNode.getTTL() == paramNodeHitRegion.node.getTTL()) && (localNetworkNode.getTTL() != -1))
        localNetworkNode.setHighlight(false);
    }
  }

  public void setHighlight(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return;
    if (paramTraceRoute.hops == null)
      return;
    if (this.m_nnlp.getNodes() == null)
      return;
    Enumeration localEnumeration1 = this.m_nnlp.getNodes().elements();
    while (localEnumeration1.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration1.nextElement();
      boolean bool = false;
      if ((localNetworkNode.isType(NetworkNode.TYPE_FIREWALL)) || (localNetworkNode.isType(NetworkNode.TYPE_JOIN)))
      {
        bool = true;
      }
      else
      {
        Vector localVector = getTTLHops(paramTraceRoute, localNetworkNode.getTTL());
        if (localVector != null)
        {
          Enumeration localEnumeration2 = localVector.elements();
          while (localEnumeration2.hasMoreElements())
          {
            TraceRouteHop localTraceRouteHop = (TraceRouteHop)localEnumeration2.nextElement();
            if (localNetworkNode.getIP().equals(localTraceRouteHop.ip))
              bool = true;
          }
        }
      }
      localNetworkNode.setHighlight(bool);
    }
    repaint();
  }

  private Vector getNextRoute(boolean paramBoolean)
  {
    if (paramBoolean)
      return getNextHighestRoute();
    return getPreviousHighestRoute();
  }

  private Vector getNextHighestRoute()
  {
    Vector localVector = getHighlightedRoute();
    if (localVector == null)
      return null;
    int i = 0;
    NetworkNode localNetworkNode1;
    Object localObject1;
    Object localObject2;
    if (keepDestination(localVector, true))
    {
      localNetworkNode1 = getNextHighestNode(localVector);
      localObject1 = null;
      Enumeration localEnumeration1 = localVector.elements();
      while (localEnumeration1.hasMoreElements())
      {
        localObject2 = (NetworkNode)localEnumeration1.nextElement();
        if (((NetworkNode)localObject2).getPosition().x == localNetworkNode1.getPosition().x)
          localObject1 = localObject2;
      }
      localVector.removeElement(localObject1);
      localVector.addElement(localNetworkNode1);
      i = 1;
    }
    else
    {
      localNetworkNode1 = getDestinationNode(localVector);
      localObject1 = getClosestDestTo(localNetworkNode1, true);
      if (localObject1 != null)
      {
        localVector.removeAllElements();
        i = 1;
        localVector.addElement(localObject1);
        for (int j = 0; j < ((NetworkNode)localObject1).getPosition().x; j++)
        {
          localObject2 = getNodesAt(j);
          NetworkNode localNetworkNode2 = getDestinationNode((Vector)localObject2);
          int k = 0;
          if ((localNetworkNode2 != null) && (localNetworkNode2.getOrder() == 0L))
            k = 1;
          Enumeration localEnumeration2 = ((Vector)localObject2).elements();
          while (localEnumeration2.hasMoreElements())
          {
            NetworkNode localNetworkNode3 = (NetworkNode)localEnumeration2.nextElement();
            if (localNetworkNode3.getOrder() == k)
              localVector.addElement(localNetworkNode3);
          }
        }
      }
    }
    return i == 0 ? null : localVector;
  }

  private NetworkNode getNextHighestNode(Vector paramVector)
  {
    Vector localVector1 = new Vector();
    Enumeration localEnumeration1 = paramVector.elements();
    while (localEnumeration1.hasMoreElements())
    {
      NetworkNode localNetworkNode1 = (NetworkNode)localEnumeration1.nextElement();
      Vector localVector2 = getNodesAt(localNetworkNode1.getPosition().x);
      NetworkNode localNetworkNode2 = getHighlightedNode(localVector2);
      if (!localNetworkNode2.isType(NetworkNode.TYPE_DESTINATION))
      {
        int i = (int)localNetworkNode2.getOrder();
        Enumeration localEnumeration2 = localVector2.elements();
        while (localEnumeration2.hasMoreElements())
        {
          NetworkNode localNetworkNode3 = (NetworkNode)localEnumeration2.nextElement();
          if ((!localNetworkNode3.isType(NetworkNode.TYPE_DESTINATION)) && (localNetworkNode3.getOrder() > i))
            localVector1.addElement(localNetworkNode3);
        }
      }
    }
    if (localVector1.size() == 0)
      return null;
    return getMinNode(localVector1);
  }

  private boolean keepDestination(Vector paramVector, boolean paramBoolean)
  {
    Enumeration localEnumeration1 = paramVector.elements();
    while (localEnumeration1.hasMoreElements())
    {
      NetworkNode localNetworkNode1 = (NetworkNode)localEnumeration1.nextElement();
      Vector localVector = getNodesAt(localNetworkNode1.getPosition().x);
      NetworkNode localNetworkNode2 = getHighlightedNode(localVector);
      if (!localNetworkNode2.isType(NetworkNode.TYPE_DESTINATION))
      {
        int i = (int)localNetworkNode2.getOrder();
        Enumeration localEnumeration2 = localVector.elements();
        while (localEnumeration2.hasMoreElements())
        {
          NetworkNode localNetworkNode3 = (NetworkNode)localEnumeration2.nextElement();
          if (!localNetworkNode3.isType(NetworkNode.TYPE_DESTINATION))
          {
            if ((paramBoolean) && (localNetworkNode3.getOrder() > i))
              return true;
            if ((!paramBoolean) && (localNetworkNode3.getOrder() < i))
              return true;
          }
        }
      }
    }
    return false;
  }

  private NetworkNode getHighlightedNode(Vector paramVector)
  {
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.getHighlight())
        return localNetworkNode;
    }
    return null;
  }

  private Vector getHighlightedRoute()
  {
    if (this.m_nnlp.getNodes() == null)
      return null;
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_nnlp.getNodes().elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.getHighlight())
        localVector.addElement(localNetworkNode);
    }
    return localVector.size() == 0 ? null : localVector;
  }

  private Vector getPreviousHighestRoute()
  {
    Vector localVector = getHighlightedRoute();
    if (localVector == null)
      return null;
    int i = 0;
    NetworkNode localNetworkNode1;
    Object localObject1;
    Object localObject2;
    if (keepDestination(localVector, false))
    {
      localNetworkNode1 = getPreviousHighestNode(localVector);
      localObject1 = null;
      Enumeration localEnumeration = localVector.elements();
      while (localEnumeration.hasMoreElements())
      {
        localObject2 = (NetworkNode)localEnumeration.nextElement();
        if (((NetworkNode)localObject2).getPosition().x == localNetworkNode1.getPosition().x)
          localObject1 = localObject2;
      }
      localVector.removeElement(localObject1);
      localVector.addElement(localNetworkNode1);
      i = 1;
    }
    else
    {
      localNetworkNode1 = getDestinationNode(localVector);
      localObject1 = getClosestDestTo(localNetworkNode1, false);
      if (localObject1 != null)
      {
        localVector.removeAllElements();
        i = 1;
        localVector.addElement(localObject1);
        for (int j = 0; j < ((NetworkNode)localObject1).getPosition().x; j++)
        {
          localObject2 = getNodesAt(j);
          NetworkNode localNetworkNode2 = getDestinationNode((Vector)localObject2);
          if (localNetworkNode2 != null)
            ((Vector)localObject2).removeElement(localNetworkNode2);
          NetworkNode localNetworkNode3 = getMaxNode((Vector)localObject2);
          if (localNetworkNode3 != null)
            localVector.addElement(localNetworkNode3);
        }
      }
    }
    return i == 0 ? null : localVector;
  }

  private NetworkNode getPreviousHighestNode(Vector paramVector)
  {
    Vector localVector1 = new Vector();
    Enumeration localEnumeration1 = paramVector.elements();
    while (localEnumeration1.hasMoreElements())
    {
      NetworkNode localNetworkNode1 = (NetworkNode)localEnumeration1.nextElement();
      Vector localVector2 = getNodesAt(localNetworkNode1.getPosition().x);
      NetworkNode localNetworkNode2 = getHighlightedNode(localVector2);
      if (!localNetworkNode2.isType(NetworkNode.TYPE_DESTINATION))
      {
        int i = (int)localNetworkNode2.getOrder();
        Enumeration localEnumeration2 = localVector2.elements();
        while (localEnumeration2.hasMoreElements())
        {
          NetworkNode localNetworkNode3 = (NetworkNode)localEnumeration2.nextElement();
          if ((!localNetworkNode3.isType(NetworkNode.TYPE_DESTINATION)) && (localNetworkNode3.getOrder() < i))
            localVector1.addElement(localNetworkNode3);
        }
      }
    }
    if (localVector1.size() == 0)
      return null;
    return getMaxNode(localVector1);
  }

  private void validateHighlight(NodeHitRegion paramNodeHitRegion)
  {
    NetworkNode localNetworkNode1 = getHighlightedDest();
    Object localObject1;
    Vector localVector1;
    Object localObject2;
    Object localObject3;
    if (paramNodeHitRegion.node.isType(NetworkNode.TYPE_DESTINATION))
    {
      if (paramNodeHitRegion.node.getPosition().x < localNetworkNode1.getPosition().x)
      {
        int i = paramNodeHitRegion.node.getPosition().x;
        Vector localVector2 = null;
        while ((localVector2 = getNodesAt(i++)) != null)
        {
          localObject1 = localVector2.elements();
          while (((Enumeration)localObject1).hasMoreElements())
            ((NetworkNode)((Enumeration)localObject1).nextElement()).setHighlight(false);
        }
        paramNodeHitRegion.node.setHighlight(true);
      }
      else
      {
        localVector1 = null;
        paramNodeHitRegion.node.setHighlight(true);
        for (int k = localNetworkNode1.getPosition().x; k < paramNodeHitRegion.node.getPosition().x; k++)
        {
          localVector1 = getNodesAt(k);
          if (containsDestination(localVector1))
          {
            localObject1 = getDestinationNode(localVector1);
            localObject2 = localVector1.elements();
            while (((Enumeration)localObject2).hasMoreElements())
            {
              localObject3 = (NetworkNode)((Enumeration)localObject2).nextElement();
              if ((((NetworkNode)localObject1).getOrder() == 0L) && (((NetworkNode)localObject3).getOrder() == 1L))
                ((NetworkNode)localObject3).setHighlight(true);
              else if ((((NetworkNode)localObject3).getOrder() == 0L) && (((NetworkNode)localObject1).getOrder() != 0L))
                ((NetworkNode)localObject3).setHighlight(true);
              else
                ((NetworkNode)localObject3).setHighlight(false);
            }
          }
          else
          {
            localObject1 = localVector1.elements();
            while (((Enumeration)localObject1).hasMoreElements())
            {
              localObject2 = (NetworkNode)((Enumeration)localObject1).nextElement();
              if (((NetworkNode)localObject2).getOrder() == 0L)
                ((NetworkNode)localObject2).setHighlight(true);
              else
                ((NetworkNode)localObject2).setHighlight(false);
            }
          }
        }
      }
    }
    else if (paramNodeHitRegion.node.getPosition().x < localNetworkNode1.getPosition().x)
    {
      localVector1 = getNodesAt(paramNodeHitRegion.node.getPosition().x);
      Enumeration localEnumeration = localVector1.elements();
      while (localEnumeration.hasMoreElements())
      {
        localObject1 = (NetworkNode)localEnumeration.nextElement();
        if (((NetworkNode)localObject1).equals(paramNodeHitRegion.node))
          ((NetworkNode)localObject1).setHighlight(true);
        else
          ((NetworkNode)localObject1).setHighlight(false);
      }
    }
    else
    {
      int j = 0;
      for (int m = localNetworkNode1.getPosition().x; j == 0; m++)
      {
        localObject1 = getNodesAt(m);
        if (containsDestination((Vector)localObject1))
        {
          if (m == paramNodeHitRegion.node.getPosition().x)
          {
            localObject2 = ((Vector)localObject1).elements();
            while (((Enumeration)localObject2).hasMoreElements())
            {
              localObject3 = (NetworkNode)((Enumeration)localObject2).nextElement();
              if (((NetworkNode)localObject3).equals(paramNodeHitRegion.node))
                ((NetworkNode)localObject3).setHighlight(true);
              else
                ((NetworkNode)localObject3).setHighlight(false);
            }
          }
          else if (m < paramNodeHitRegion.node.getPosition().x)
          {
            localObject2 = getDestinationNode((Vector)localObject1);
            localObject3 = ((Vector)localObject1).elements();
            while (((Enumeration)localObject3).hasMoreElements())
            {
              NetworkNode localNetworkNode2 = (NetworkNode)((Enumeration)localObject3).nextElement();
              if ((((NetworkNode)localObject2).getOrder() == 0L) && (localNetworkNode2.getOrder() == 1L))
                localNetworkNode2.setHighlight(true);
              else if ((((NetworkNode)localObject2).getOrder() != 0L) && (localNetworkNode2.getOrder() == 0L))
                localNetworkNode2.setHighlight(true);
              else
                localNetworkNode2.setHighlight(false);
            }
          }
          else
          {
            localObject2 = getDestinationNode((Vector)localObject1);
            ((NetworkNode)localObject2).setHighlight(true);
            j = 1;
          }
        }
        else
        {
          localObject2 = ((Vector)localObject1).elements();
          while (((Enumeration)localObject2).hasMoreElements())
          {
            localObject3 = (NetworkNode)((Enumeration)localObject2).nextElement();
            if (((NetworkNode)localObject3).equals(paramNodeHitRegion.node))
              ((NetworkNode)localObject3).setHighlight(true);
            else if (((NetworkNode)localObject3).getOrder() == 0L)
              ((NetworkNode)localObject3).setHighlight(true);
            else
              ((NetworkNode)localObject3).setHighlight(false);
          }
        }
      }
    }
    updateListeners("USER_SELECT");
  }

  private void validateHighlight()
  {
    Vector localVector1 = getDestinationNodes();
    Vector localVector2 = getHighlightedRoute();
    NetworkNode localNetworkNode1 = getRightmostNode(localVector2);
    int i = 0;
    if ((localVector1 != null) && (!localNetworkNode1.isType(NetworkNode.TYPE_DESTINATION)))
      localNetworkNode1 = getClosestDestTo(localNetworkNode1, false);
    for (int j = 0; j < localNetworkNode1.getPosition().x; j++)
    {
      Vector localVector3 = getNodesAt(j);
      if (getHighlightedNode(localVector3) == null)
      {
        NetworkNode localNetworkNode2 = getDestinationNode(localVector3);
        int k = 0;
        if ((localNetworkNode2 != null) && (localNetworkNode2.getOrder() == 0L))
          k = 1;
        Enumeration localEnumeration = localVector3.elements();
        while (localEnumeration.hasMoreElements())
        {
          NetworkNode localNetworkNode3 = (NetworkNode)localEnumeration.nextElement();
          if (localNetworkNode3.getOrder() == k)
          {
            localNetworkNode3.setHighlight(true);
            i = 1;
          }
        }
      }
    }
    if (i != 0)
      updateListeners();
  }

  private NetworkNode getRightmostNode(Vector paramVector)
  {
    if (paramVector == null)
      return null;
    Object localObject = null;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localObject == null)
        localObject = localNetworkNode;
      else if (localNetworkNode.getPosition().x > localObject.getPosition().x)
        localObject = localNetworkNode;
    }
    return localObject;
  }

  public int countAllPossibleRoutes()
  {
    Vector localVector = getDestinationNodes();
    if (localVector == null)
      return 0;
    Enumeration localEnumeration = localVector.elements();
    int i = 0;
    while (localEnumeration.hasMoreElements())
      i += countRoutesTo((NetworkNode)localEnumeration.nextElement());
    return i;
  }

  private int countRoutesTo(NetworkNode paramNetworkNode)
  {
    int i = 1;
    for (int j = 0; j < paramNetworkNode.getPosition().x; j++)
    {
      Vector localVector = getNodesAt(j);
      if (localVector != null)
      {
        int k = localVector.size();
        if (containsDestination(localVector))
          k--;
        i *= k;
      }
    }
    return i;
  }

  private NetworkNode getDestinationNode(boolean paramBoolean)
  {
    Vector localVector = getDestinationNodes();
    if (localVector == null)
      return null;
    if (localVector.size() == 1)
      return (NetworkNode)localVector.firstElement();
    Enumeration localEnumeration = localVector.elements();
    Object localObject = null;
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.isType(NetworkNode.TYPE_DESTINATION))
        if (localObject == null)
          localObject = localNetworkNode;
        else if (paramBoolean)
        {
          if (localNetworkNode.getPosition().x < localObject.getPosition().x)
            localObject = localNetworkNode;
        }
        else if (localNetworkNode.getPosition().x > localObject.getPosition().x)
          localObject = localNetworkNode;
    }
    return localObject;
  }

  private Vector getDestinationNodes()
  {
    if (this.m_nnlp.getNodes() == null)
      return null;
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_nnlp.getNodes().elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.isType(NetworkNode.TYPE_DESTINATION))
        localVector.addElement(localNetworkNode);
    }
    return localVector.size() == 0 ? null : localVector;
  }

  private NetworkNode getClosestDestTo(NetworkNode paramNetworkNode, boolean paramBoolean)
  {
    Enumeration localEnumeration = this.m_nnlp.getNodes().elements();
    Object localObject = null;
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.isType(NetworkNode.TYPE_DESTINATION))
        if (paramBoolean)
        {
          if ((localObject == null) && (localNetworkNode.getPosition().x > paramNetworkNode.getPosition().x))
            localObject = localNetworkNode;
          else if ((localObject != null) && (localNetworkNode.getPosition().x < localObject.getPosition().x) && (localNetworkNode.getPosition().x > paramNetworkNode.getPosition().x))
            localObject = localNetworkNode;
        }
        else if ((localObject == null) && (localNetworkNode.getPosition().x < paramNetworkNode.getPosition().x))
          localObject = localNetworkNode;
        else if ((localObject != null) && (localNetworkNode.getPosition().x > localObject.getPosition().x) && (localNetworkNode.getPosition().x < paramNetworkNode.getPosition().x))
          localObject = localNetworkNode;
    }
    return localObject;
  }

  private NetworkNode getHighlightedDest()
  {
    Enumeration localEnumeration = this.m_nnlp.getNodes().elements();
    Object localObject = null;
    int i = 2147483647;
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((localNetworkNode.getHighlight()) && (localNetworkNode.isType(NetworkNode.TYPE_DESTINATION)) && (localNetworkNode.getPosition().x < i))
      {
        i = localNetworkNode.getPosition().x;
        localObject = localNetworkNode;
      }
    }
    return localObject;
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    this.m_ptClick = null;
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
    if (setMouseOver(null))
      repaint();
    if (setMouseOver(null))
      repaint();
    setCursor(new Cursor(0));
    this.m_ptClick = null;
  }

  public void updateEdition()
  {
    repaint();
  }

  private void updateListeners()
  {
    updateListeners("UPDATE");
  }

  private void updateListeners(String paramString)
  {
    fireActionEvent(paramString);
  }

  public TraceRoute getHopsSummary()
  {
    return getHopsSummary(true);
  }

  public TraceRoute getHopsSummary(boolean paramBoolean)
  {
    return getHopsSummary(paramBoolean, true);
  }

  public TraceRoute getHopsSummary(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.m_nnlp.getNodes() == null)
      return null;
    if (this.m_nnlp.getNodes().size() == 1)
    {
      localObject1 = ((TraceRoute)this.m_routes.firstElement()).doClone();
      localObject2 = new TraceRouteHop[] { ((NetworkNode)this.m_nnlp.getNodes().firstElement()).getHopSummary() };
      ((TraceRoute)localObject1).hops = ((TraceRouteHop[])localObject2);
      return localObject1;
    }
    Object localObject1 = new Vector();
    Object localObject2 = getHighlightedDest();
    if (localObject2 == null)
    {
      Vector localVector = getHighlightedRoute();
      if (localVector == null)
        return null;
      Enumeration localEnumeration1 = localVector.elements();
      localObject2 = (NetworkNode)localVector.firstElement();
      while (localEnumeration1.hasMoreElements())
      {
        localObject3 = (NetworkNode)localEnumeration1.nextElement();
        if (((NetworkNode)localObject3).getPosition().x > ((NetworkNode)localObject2).getPosition().x)
          localObject2 = localObject3;
      }
    }
    int i = 0;
    for (int j = 0; (j <= ((NetworkNode)localObject2).getPosition().x) && (i == 0); j++)
    {
      localObject3 = getNodesAt(j);
      Enumeration localEnumeration2 = ((Vector)localObject3).elements();
      NetworkNode localNetworkNode;
      TraceRouteHop localTraceRouteHop;
      while (localEnumeration2.hasMoreElements())
      {
        localNetworkNode = (NetworkNode)localEnumeration2.nextElement();
        if (!localNetworkNode.isType(NetworkNode.TYPE_FAKE))
          if ((localNetworkNode.isType(NetworkNode.TYPE_FIREWALL)) && (!paramBoolean2))
          {
            i = 1;
          }
          else
          {
            localTraceRouteHop = localNetworkNode.getHopSummary();
            if (localNetworkNode.getHighlight())
              ((Vector)localObject1).addElement(localTraceRouteHop);
          }
      }
      if (paramBoolean1)
      {
        localEnumeration2 = ((Vector)localObject3).elements();
        while (localEnumeration2.hasMoreElements())
        {
          localNetworkNode = (NetworkNode)localEnumeration2.nextElement();
          if (!localNetworkNode.isType(NetworkNode.TYPE_FAKE))
          {
            localTraceRouteHop = localNetworkNode.getHopSummary();
            if (!localNetworkNode.getHighlight())
              ((Vector)localObject1).addElement(localTraceRouteHop);
          }
        }
      }
    }
    TraceRoute localTraceRoute = ((TraceRoute)this.m_routes.firstElement()).doClone();
    Object localObject3 = new TraceRouteHop[((Vector)localObject1).size()];
    for (int k = 0; k < localObject3.length; k++)
      localObject3[k] = ((TraceRouteHop)((Vector)localObject1).elementAt(k));
    localTraceRoute.hops = ((TraceRouteHop[])localObject3);
    return addNullHops(localTraceRoute);
  }

  public Dimension getMinimumSize()
  {
    return new Dimension(100, 60);
  }

  public Dimension getPreferredSize()
  {
    return getMinimumSize();
  }

  private static boolean hopsSequential(Vector paramVector1, Vector paramVector2)
  {
    if ((paramVector1 == null) || (paramVector2 == null))
      return true;
    TraceRouteHop localTraceRouteHop1 = (TraceRouteHop)paramVector1.firstElement();
    TraceRouteHop localTraceRouteHop2 = (TraceRouteHop)paramVector2.firstElement();
    int i = (!"...".equals(localTraceRouteHop1.ip)) && (!"...".equals(localTraceRouteHop2.ip)) ? 0 : 1;
    return (i != 0) || (localTraceRouteHop1.sentTTL + 1 == localTraceRouteHop2.sentTTL);
  }

  private static void drawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Graphics paramGraphics, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      paramGraphics.drawLine(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    else
    {
      int i;
      if (paramInt1 == paramInt3)
      {
        if (paramInt2 < paramInt4)
          for (i = paramInt2; i <= paramInt4; i++)
            if (i % 2 == 0)
              paramGraphics.drawLine(paramInt1, i, paramInt3, i);
        else
          for (i = paramInt2; i >= paramInt4; i--)
            if (i % 2 == 0)
              paramGraphics.drawLine(paramInt1, i, paramInt3, i);
      }
      else
        for (i = paramInt1; i <= paramInt3; i++)
          if (i % 2 == 0)
            paramGraphics.drawLine(i, paramInt2, i, paramInt4);
    }
  }

  private static boolean valid(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return false;
    if (paramTraceRoute.hops == null)
      return false;
    return paramTraceRoute.hops.length != 0;
  }

  private static TraceRoute addNullHops(TraceRoute paramTraceRoute)
  {
    if ((paramTraceRoute == null) || (paramTraceRoute.hops == null) || (paramTraceRoute.hops.length == 0))
      return paramTraceRoute;
    int i = 0;
    int j = 0;
    int k = -1;
    while (j < paramTraceRoute.hops.length)
    {
      if ((k != -1) && (paramTraceRoute.hops[j].sentTTL <= 256) && (k + 1 != paramTraceRoute.hops[j].sentTTL) && (k != paramTraceRoute.hops[j].sentTTL))
        i += paramTraceRoute.hops[j].sentTTL - k - 1;
      k = paramTraceRoute.hops[j].sentTTL;
      j++;
    }
    TraceRouteHop[] arrayOfTraceRouteHop = new TraceRouteHop[paramTraceRoute.hops.length + i];
    k = 0;
    int m = -1;
    int n = 0;
    while (k < paramTraceRoute.hops.length)
    {
      if (m == -1)
      {
        arrayOfTraceRouteHop[(n++)] = paramTraceRoute.hops[k];
      }
      else if (paramTraceRoute.hops[k].sentTTL > 256)
      {
        arrayOfTraceRouteHop[(n++)] = paramTraceRoute.hops[k];
      }
      else if ((m + 1 == paramTraceRoute.hops[k].sentTTL) || (m == paramTraceRoute.hops[k].sentTTL))
      {
        arrayOfTraceRouteHop[(n++)] = paramTraceRoute.hops[k];
      }
      else
      {
        for (int i1 = 0; i1 < paramTraceRoute.hops[k].sentTTL - m - 1; i1++)
          arrayOfTraceRouteHop[(n++)] = makeNullHop(m + i1 + 1);
        arrayOfTraceRouteHop[(n++)] = paramTraceRoute.hops[k];
      }
      m = paramTraceRoute.hops[k].sentTTL;
      k++;
    }
    paramTraceRoute.hops = arrayOfTraceRouteHop;
    return paramTraceRoute;
  }

  private static TraceRouteHop makeNullHop(int paramInt)
  {
    TraceRouteHop localTraceRouteHop = new TraceRouteHop();
    localTraceRouteHop.hop = paramInt;
    localTraceRouteHop.sentTTL = paramInt;
    localTraceRouteHop.dns = "-";
    localTraceRouteHop.ip = "?";
    localTraceRouteHop.location = "-";
    localTraceRouteHop.loss = 100;
    localTraceRouteHop.maxMs = -1;
    localTraceRouteHop.minMs = -1;
    localTraceRouteHop.ms = -1;
    localTraceRouteHop.network = "-";
    localTraceRouteHop.nAffinity = -1;
    return localTraceRouteHop;
  }

  private static TraceRoute removeNullHops(TraceRoute paramTraceRoute)
  {
    if ((paramTraceRoute == null) || (paramTraceRoute.hops == null) || (paramTraceRoute.hops.length == 0))
      return paramTraceRoute;
    int i = 0;
    for (int j = 0; j < paramTraceRoute.hops.length; j++)
      if ((paramTraceRoute.hops[j].ms != -1) || (paramTraceRoute.hops[j].sentTTL > 255))
        i++;
    TraceRouteHop[] arrayOfTraceRouteHop = new TraceRouteHop[i];
    int k = 0;
    int m = 0;
    while (k < paramTraceRoute.hops.length)
    {
      if ((paramTraceRoute.hops[k].ms != -1) || (paramTraceRoute.hops[k].sentTTL > 255))
        arrayOfTraceRouteHop[(m++)] = paramTraceRoute.hops[k];
      k++;
    }
    paramTraceRoute.hops = arrayOfTraceRouteHop;
    return paramTraceRoute;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.NetworkNodePanel
 * JD-Core Version:    0.6.2
 */