package mcsaplugins.route;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class MapPanel extends SmoothUpdatePanel
  implements Runnable, MouseListener, MouseMotionListener
{
  private static final Dimension MAP_SIZE = new Dimension(630, 300);
  private TraceRoute m_tr;
  private route m_applet;
  private AppletPanel m_appPanel;
  private boolean m_bSummary;
  private Image m_map;
  private int m_zoom;
  private Object m_mapLock = new Object();
  private Point m_offset = new Point();
  private Hashtable m_mapcache = new Hashtable();
  private Thread m_mapThread;
  private boolean m_bCentreMap;
  private double m_loadingLat;
  private double m_loadingLon;
  private int m_loadingZoom;
  private Point __down;
  private Point __offsetDown;
  private boolean __bDragged;
  private Point m_loadingCentreOn;

  public MapPanel(route paramroute, AppletPanel paramAppletPanel, boolean paramBoolean)
  {
    this.m_applet = paramroute;
    this.m_appPanel = paramAppletPanel;
    this.m_bSummary = paramBoolean;
    this.m_bCentreMap = true;
    loadMap(0.0D, 0.0D, paramBoolean ? 0 : 1, null);
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void setTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_tr = paramTraceRoute;
    repaint();
  }

  public void smoothPaint(Graphics paramGraphics)
  {
    int i = Math.min(getSize().width, MAP_SIZE.width);
    int j = Math.min(getSize().height, MAP_SIZE.height);
    if (!this.m_bSummary)
    {
      paramGraphics.setColor(new Color(11184810));
      paramGraphics.setClip(1, 1, Math.min(i - 1, MAP_SIZE.width) - 1, Math.min(j - 1, MAP_SIZE.height) - 1);
      paramGraphics.setColor(Color.black);
    }
    if (this.m_mapThread != null)
    {
      paramGraphics.drawString("Loading...", i / 2, j / 2);
    }
    else if (this.m_map == null)
    {
      paramGraphics.drawString("Map could not be found", i / 2, j / 2);
    }
    else
    {
      paramGraphics.setFont(new Font("Helvetica", 0, 9));
      FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
      if (this.m_bCentreMap)
        this.m_offset = new Point(i / 2 - this.m_map.getWidth(null) / 2, j / 2 - this.m_map.getHeight(null) / 2);
      paramGraphics.drawImage(this.m_map, this.m_offset.x, this.m_offset.y, null);
      Object localObject = null;
      Vector localVector = new Vector();
      if ((this.m_tr != null) && (this.m_tr.hops != null))
        for (int k = this.m_tr.hops.length - 1; k >= 0; k--)
        {
          TraceRouteHop localTraceRouteHop = this.m_tr.hops[k];
          Place localPlace = (localTraceRouteHop == null) || ((!localTraceRouteHop.bAuthoritativeLocation) && (localObject != null)) ? null : U.getPlace(localTraceRouteHop.location);
          if ((localPlace != null) && (localPlace.bHaveLatLon))
          {
            String str = localPlace.mapname;
            Point localPoint = latLonToPoint(localPlace.lat, localPlace.lon);
            drawDot(paramGraphics, localPoint.x, localPoint.y, new Color(11141120));
            Font localFont = null;
            if (!localTraceRouteHop.bAuthoritativeLocation)
            {
              localFont = paramGraphics.getFont();
              paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() | 0x2, localFont.getSize()));
              localFontMetrics = paramGraphics.getFontMetrics();
            }
            int m = localFontMetrics.stringWidth(str);
            Rectangle localRectangle = new Rectangle(localPoint.x - m / 2 - 2, localPoint.y + 6, m + 4, localFontMetrics.getHeight() + 4);
            if ((!this.m_bSummary) && (G.isMapShowLabels()) && (!hits(localRectangle, localVector)))
            {
              localVector.addElement(localRectangle);
              U.gradientFill(paramGraphics, localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height, new Color(16774822), new Color(16773504));
              paramGraphics.setColor(Color.darkGray);
              paramGraphics.drawRect(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
              paramGraphics.drawString(str, localRectangle.x + 2, localRectangle.y + 2 + localFontMetrics.getAscent());
            }
            if (localObject != null)
            {
              paramGraphics.setColor(new Color(G.getMapLineColour()));
              paramGraphics.drawLine(localObject.x, localObject.y, localPoint.x, localPoint.y);
            }
            if (localFont != null)
            {
              paramGraphics.setFont(localFont);
              localFontMetrics = paramGraphics.getFontMetrics();
            }
            localObject = localPoint;
          }
        }
    }
    if (!this.m_bSummary)
      U.drawCopyright(paramGraphics, 0, j - 30, i, Color.white);
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

  private Point latLonToPoint(double paramDouble1, double paramDouble2)
  {
    synchronized (this.m_mapLock)
    {
      int i = this.m_map.getWidth(null);
      int j = this.m_map.getHeight(null);
      int k = this.m_offset.x + (int)(i * (paramDouble2 + 180.0D) / 360.0D);
      int m = this.m_offset.y + (int)(j * (paramDouble1 + 90.0D) / 180.0D);
      return new Point(k, m);
    }
  }

  private double[] pointToLatLon(int paramInt1, int paramInt2)
  {
    synchronized (this.m_mapLock)
    {
      int i = this.m_map.getWidth(null);
      int j = this.m_map.getHeight(null);
      double d1 = 180.0D * (paramInt2 - this.m_offset.y) / j - 90.0D;
      double d2 = 360.0D * (paramInt1 - this.m_offset.x) / i - 180.0D;
      return new double[] { d1, d2 };
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    if (this.m_bSummary)
    {
      this.m_appPanel.setView("map");
    }
    else
    {
      this.m_bCentreMap = false;
      this.__bDragged = false;
      this.__down = new Point(localPoint.x, localPoint.y);
      this.__offsetDown = new Point(this.m_offset);
    }
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    if (!this.m_bSummary)
    {
      int i = paramMouseEvent.isMetaDown() ? this.m_zoom - 1 : this.m_zoom + 1;
      if ((!this.__bDragged) && (i >= 0) && (i < G.getMapMaxZoom()))
      {
        double[] arrayOfDouble = pointToLatLon(localPoint.x, localPoint.y);
        loadMap(arrayOfDouble[0], arrayOfDouble[1], i, localPoint);
      }
    }
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    if (!this.m_bSummary)
    {
      this.__bDragged = true;
      this.m_offset.x = (localPoint.x - this.__down.x + this.__offsetDown.x);
      this.m_offset.y = (localPoint.y - this.__down.y + this.__offsetDown.y);
      repaint();
    }
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

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  private synchronized void loadMap(double paramDouble1, double paramDouble2, int paramInt, Point paramPoint)
  {
    if (this.m_mapThread == null)
    {
      if (paramDouble1 > 90.0D)
        paramDouble1 = 90.0D;
      else if (paramDouble1 < -90.0D)
        paramDouble1 = -90.0D;
      if (paramDouble2 > 180.0D)
        paramDouble2 = 180.0D;
      else if (paramDouble2 < -180.0D)
        paramDouble2 = -180.0D;
      this.m_loadingLat = paramDouble1;
      this.m_loadingLon = paramDouble2;
      this.m_loadingZoom = paramInt;
      this.m_loadingCentreOn = paramPoint;
      this.m_mapThread = new Thread(this);
      this.m_mapThread.start();
    }
  }

  public void run()
  {
    Image localImage = null;
    if (this.m_mapcache.get(this.m_loadingZoom) != null)
      localImage = (Image)this.m_mapcache.get(this.m_loadingZoom);
    InputStream localInputStream = null;
    try
    {
      Object localObject1;
      if (localImage == null)
      {
        URL localURL1 = new URL(this.m_applet.getAppletCodeBase(), "plugin/route/applet/md?x=" + this.m_loadingLon + "&y=" + this.m_loadingLat + "&z=" + this.m_loadingZoom + "&id=" + G.getMap() + (this.m_loadingCentreOn != null ? "&px=" + this.m_loadingCentreOn.x + "&py=" + this.m_loadingCentreOn.y : ""));
        localInputStream = localURL1.openStream();
        localObject1 = new long[] { 1431655765L, 1437226451L, 287454029L };
        U.readLineEnc(localInputStream, (long[])localObject1);
        String str = U.readLineEnc(localInputStream, (long[])localObject1);
        str = str.replace('\\', '/');
        localInputStream.close();
        URL localURL2 = new URL(this.m_applet.getAppletCodeBase(), "plugin/route/applet/" + str);
        localImage = this.m_applet.getRemoteImage(localURL2);
        MediaTracker localMediaTracker = new MediaTracker(this);
        localMediaTracker.addImage(localImage, 1);
        try
        {
          localMediaTracker.waitForAll();
        }
        catch (Exception localException2)
        {
        }
      }
      this.m_mapcache.put(this.m_loadingZoom, localImage);
      synchronized (this.m_mapLock)
      {
        this.m_map = localImage;
        this.m_zoom = this.m_loadingZoom;
        this.m_offset = new Point(0, 0);
        localObject1 = latLonToPoint(this.m_loadingLat, this.m_loadingLon);
        int i = Math.min(getSize().width, MAP_SIZE.width);
        int j = Math.min(getSize().height, MAP_SIZE.height);
        int k = i / 2 - ((Point)localObject1).x;
        int m = j / 2 - ((Point)localObject1).y;
        this.m_offset = new Point(k, m);
      }
      repaint();
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      this.m_mapThread = null;
      repaint(0L);
      try
      {
        localInputStream.close();
      }
      catch (Exception localException3)
      {
      }
    }
    finally
    {
      this.m_mapThread = null;
      repaint(0L);
      try
      {
        localInputStream.close();
      }
      catch (Exception localException4)
      {
      }
    }
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.MapPanel
 * JD-Core Version:    0.6.2
 */