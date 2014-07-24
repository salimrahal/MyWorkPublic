package myspeedserver.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

public abstract class AppletTab extends JPanel
  implements MouseListener, MouseMotionListener
{
  private Image CG;
  private String DG;
  private myspeed LF;
  private Hashtable YG = new Hashtable();
  private Hashtable FG;
  private boolean GG;
  private long IG;
  private AppletPlugin BG;
  private boolean ZG = false;
  private AppletTab.FadeCopyrightThread HG;
  private Hashtable EG;
  private EventListenerList AG = new EventListenerList();

  public AppletTab(Applet paramApplet, AppletPlugin paramAppletPlugin, Image paramImage, String paramString)
  {
    this.LF = ((myspeed)paramApplet);
    this.BG = paramAppletPlugin;
    this.CG = paramImage;
    this.DG = paramString;
    this.EG = new Hashtable();
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  protected synchronized Image getImage(String paramString)
  {
    Image localImage = (Image)this.EG.get(paramString);
    if (localImage == null)
    {
      localImage = this.BG == null ? null : this.BG.getImage(paramString);
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
        if (localImage.getHeight(null) > 0)
          this.EG.put(paramString, localImage);
        else
          localImage = null;
      }
    }
    return localImage;
  }

  public abstract void doFirstTimeInit();

  public abstract void reset();

  protected String getUnregisteredMessage()
  {
    return this.LF.getUnregisteredMessage();
  }

  public Image getImage()
  {
    return this.CG;
  }

  public Image getBackgroundOverlay()
  {
    return this.LF.getBackgroundOverlay(this.DG);
  }

  public String getName()
  {
    return this.DG;
  }

  protected String RC(String paramString)
  {
    return this.LF.RC(paramString);
  }

  protected String RC(String paramString1, String paramString2)
  {
    return this.LF.RC(paramString1, paramString2);
  }

  protected String RC(String paramString, String[] paramArrayOfString)
  {
    return this.LF.RC(paramString, paramArrayOfString);
  }

  protected int iniGetInteger(String paramString, int paramInt)
  {
    return this.LF.iniGetInteger(paramString, null, paramInt);
  }

  protected long iniGetLong(String paramString, long paramLong)
  {
    return this.LF.iniGetLong(paramString, null, paramLong);
  }

  protected String iniGetString(String paramString)
  {
    return this.LF.iniGetString(paramString, null);
  }

  protected String iniGetProfessionalString(String paramString)
  {
    return this.LF.iniGetProfessionalString(paramString);
  }

  protected boolean isShowingMessageOnGlass()
  {
    return this.LF.isShowingMessageOnGlass();
  }

  protected void setFindClosestHitRegion(boolean paramBoolean)
  {
    this.ZG = paramBoolean;
  }

  protected void addHitRegion(String paramString, Rectangle paramRectangle)
  {
    if (this.FG == null)
      this.FG = new Hashtable();
    this.FG.put(paramRectangle, paramString);
  }

  private void updateHitRegions()
  {
    this.YG = (this.FG == null ? new Hashtable() : this.FG);
    this.FG = null;
  }

  protected Object[] getHitRegionDetail(int paramInt1, int paramInt2)
  {
    Enumeration localEnumeration = this.YG.keys();
    Object[] arrayOfObject = null;
    Object localObject = null;
    double d1 = -1.0D;
    while (localEnumeration.hasMoreElements())
    {
      Rectangle localRectangle = (Rectangle)localEnumeration.nextElement();
      String str = (String)this.YG.get(localRectangle);
      double d2 = Math.pow(paramInt1 - (localRectangle.x + localRectangle.width / 2.0D), 2.0D) + Math.pow(paramInt2 - (localRectangle.y + localRectangle.height / 2.0D), 2.0D);
      if (localRectangle.contains(paramInt1, paramInt2))
        if ((!this.ZG) && ((localObject == null) || (localObject.width * localObject.height > localRectangle.width * localRectangle.height)))
        {
          arrayOfObject = new Object[] { str, localRectangle };
          localObject = localRectangle;
        }
        else if ((this.ZG) && ((d1 == -1.0D) || (d1 > d2)))
        {
          arrayOfObject = new Object[] { str, localRectangle };
          d1 = d2;
        }
    }
    return arrayOfObject;
  }

  protected String getHitRegion(int paramInt1, int paramInt2)
  {
    Object[] arrayOfObject = getHitRegionDetail(paramInt1, paramInt2);
    return (arrayOfObject == null) || (arrayOfObject.length < 2) ? null : (String)arrayOfObject[0];
  }

  public void setHitMaxTests(boolean paramBoolean)
  {
    this.GG = paramBoolean;
  }

  public boolean isHitMaxTests()
  {
    return this.GG;
  }

  protected void doOverlayMessages(Graphics paramGraphics)
  {
    this.LF.doOverlayMessages(paramGraphics);
  }

  protected void setFadeCopyrightInfo(boolean paramBoolean)
  {
    if ((this.HG == null) && (paramBoolean))
    {
      this.HG = new AppletTab.FadeCopyrightThread(this, this);
    }
    else if ((this.HG != null) && (!paramBoolean))
    {
      this.HG.stop();
      this.HG = null;
    }
  }

  public abstract void panelPaint(Graphics paramGraphics);

  protected void paintCopyrightInfo(Graphics paramGraphics, int paramInt1, int paramInt2)
  {
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int i = localFontMetrics.getHeight();
    long l1 = 8000L;
    long l2 = U.time();
    if (this.IG == 0L)
      this.IG = l2;
    long l3 = l2 - this.IG;
    if (l3 < l1)
    {
      int j = l3 < l1 / 2L ? U.max(128, 240 - (int)l3 / 8) : U.max(128, 240 - (int)(l1 - l3) / 8);
      paramGraphics.setColor(new Color(j, j, j));
      paramGraphics.drawString(this.LF.appNameVer(true), paramInt1, paramInt2);
      paramInt2 += i;
      paramGraphics.drawString(this.LF.copyrightStuff(), paramInt1, paramInt2);
      paramInt2 += i;
      String str1 = U.javaInfo(false);
      paramGraphics.drawString(str1, paramInt1, paramInt2);
      String str2 = iniGetString(TX("javaupdate"));
      if ((str2 != null) && (!TX("").equals(str2)) && (U.isNewerJavaAvailable()))
      {
        int k = localFontMetrics.stringWidth(str1);
        int m = localFontMetrics.stringWidth("x");
        int n = paramInt1 + k + m;
        paramGraphics.drawString(str2, n, paramInt2);
        int i1 = localFontMetrics.stringWidth(str2);
        paramGraphics.drawLine(n, paramInt2 + 1, n + i1, paramInt2 + 1);
        addHitRegion("updatejava", new Rectangle(n, paramInt2 - i + localFontMetrics.getDescent(), i1, i));
      }
      paramInt2 += i;
    }
  }

  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    panelPaint(paramGraphics);
    doOverlayMessages(paramGraphics);
    updateHitRegions();
  }

  protected Rectangle drawWrappedLines(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    return paramGraphics == null ? null : drawWrappedLines(paramGraphics, paramString, paramInt1, paramInt2, paramInt3, paramGraphics.getFont(), null);
  }

  protected Rectangle drawWrappedLines(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, Point paramPoint)
  {
    return paramGraphics == null ? null : drawWrappedLines(paramGraphics, paramString, paramInt1, paramInt2, paramInt3, paramGraphics.getFont(), paramPoint);
  }

  protected Rectangle drawWrappedLines(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, Font paramFont)
  {
    return drawWrappedLines(paramGraphics, paramString, paramInt1, paramInt2, paramInt3, paramFont, null);
  }

  protected Rectangle drawWrappedLines(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, Font paramFont, Point paramPoint)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    Font localFont = paramFont;
    Point localPoint = paramPoint == null ? new Point(0, 0) : paramPoint;
    Color localColor = paramGraphics == null ? null : paramGraphics.getColor();
    String str1 = null;
    FontMetrics localFontMetrics = getFontMetrics(paramGraphics, paramFont);
    int n = localFontMetrics.getHeight();
    int i1 = localFontMetrics.getAscent();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, TX("\n <>"), true);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str2 = localStringTokenizer.nextToken();
      if (str2.equals(TX("\n")))
      {
        j += n;
        i = 0;
      }
      else if (str2.equals(TX("<")))
      {
        Hashtable localHashtable = new Hashtable();
        String str4 = null;
        if (localStringTokenizer.hasMoreTokens())
          str4 = localStringTokenizer.nextToken();
        String str3;
        while ((localStringTokenizer.hasMoreTokens()) && (!(str3 = localStringTokenizer.nextToken()).equals(TX(">"))))
        {
          int i2 = str3.indexOf(TX("="));
          if ((i2 > 0) && (i2 < str3.length() - 1))
            localHashtable.put(str3.substring(0, i2), str3.substring(i2 + 1));
        }
        if ((str4 != null) && (str4.equals(TX("b"))))
        {
          paramFont = new Font(localFont.getName(), localFont.getStyle() | 0x1, localFont.getSize());
          if (paramGraphics != null)
            paramGraphics.setFont(paramFont);
        }
        else if ((str4 != null) && (str4.equals(TX("/b"))))
        {
          paramFont = new Font(localFont.getName(), localFont.getStyle() & 0xFFFFFFFE, localFont.getSize());
          if (paramGraphics != null)
            paramGraphics.setFont(paramFont);
        }
        else if ((str4 != null) && (str4.equals(TX("a"))))
        {
          if (paramGraphics != null)
            paramGraphics.setColor(Color.blue);
          str1 = (String)localHashtable.get("href");
        }
        else if ((str4 != null) && (str4.equals(TX("/a"))))
        {
          if (paramGraphics != null)
            paramGraphics.setColor(localColor);
          str1 = null;
        }
        else if ((str4 != null) && (str4.equals(TX("span"))))
        {
          try
          {
            paramGraphics.setColor(new Color(Integer.parseInt((String)localHashtable.get("bgcolor"), 16)));
          }
          catch (Exception localException)
          {
          }
        }
        else if ((str4 != null) && (str4.equals(TX("/span"))))
        {
          if (paramGraphics != null)
            paramGraphics.setColor(localColor);
        }
        else if ((str4 != null) && (str4.equals(TX("img"))))
        {
          String str5 = (String)localHashtable.get("src");
          if (str5 != null)
          {
            Image localImage = getImage(str5);
            int i3 = localImage == null ? -1 : localImage.getWidth(null);
            int i4 = localImage == null ? -1 : localImage.getHeight(null);
            if ((i3 > 0) && (i4 > 0))
            {
              if (i + k > paramInt3)
              {
                j += n;
                i = 0;
              }
              if (paramGraphics != null)
              {
                int i5 = paramInt2 + j + n / 2 - i4 / 2;
                paramGraphics.drawImage(localImage, paramInt1 + i, i5, null);
                if (str1 != null)
                  addHitRegion(str1, new Rectangle(paramInt1 + i + localPoint.x, i5 + localPoint.y, i3, i4));
              }
              i += i3;
            }
          }
        }
        localFontMetrics = getFontMetrics(paramGraphics, paramFont);
        i1 = localFontMetrics.getAscent();
        n = localFontMetrics.getHeight();
      }
      else
      {
        k = localFontMetrics.stringWidth(str2);
        if (i + k > paramInt3)
        {
          j += n;
          i = 0;
          if (str2.equals(TX(" ")))
          {
            str2 = "";
            k = 0;
          }
        }
        if (paramGraphics != null)
          paramGraphics.drawString(str2, paramInt1 + i, paramInt2 + j + i1);
        if ((paramGraphics != null) && (str1 != null) && (k > 0))
        {
          paramGraphics.drawLine(paramInt1 + i, paramInt2 + j + i1 + 1, paramInt1 + i + k, paramInt2 + j + i1 + 1);
          addHitRegion(str1, new Rectangle(paramInt1 + i + localPoint.x, paramInt2 + j + localPoint.y, k, n));
        }
        i += k;
        m = Math.max(i, m);
      }
    }
    if (i > 0)
      j += n;
    return new Rectangle(paramInt1, paramInt2, m, j);
  }

  protected static FontMetrics getFontMetrics(Graphics paramGraphics, Font paramFont)
  {
    return paramGraphics == null ? Toolkit.getDefaultToolkit().getFontMetrics(paramFont) : paramGraphics.getFontMetrics();
  }

  protected int drawBars(Graphics paramGraphics, FontMetrics paramFontMetrics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String[] paramArrayOfString, int[] paramArrayOfInt, float paramFloat, String paramString)
  {
    int i = paramFontMetrics.getHeight();
    int j = i + 13;
    int k = i + 6;
    int m = 1;
    int n = paramInt4 < 0 ? 1 : 0;
    for (int i1 = 0; i1 < paramArrayOfInt.length; i1++)
    {
      m = Math.max(m, paramArrayOfInt[i1]);
      if (n != 0)
        paramInt4 = Math.max(paramInt4, paramFontMetrics.stringWidth(paramArrayOfString[i1]));
    }
    i1 = paramInt3 - 3 - paramInt4 - 2;
    int i2 = paramInt2;
    if (i1 > 0)
      for (int i3 = 0; i3 < paramArrayOfInt.length; i3++)
      {
        if (paramGraphics != null)
        {
          paramGraphics.setColor(new Color(6710886));
          paramGraphics.drawString(paramArrayOfString[i3], paramInt1, i2 + j / 2 - paramFontMetrics.getDescent() + i / 2);
          int i4 = paramArrayOfInt[i3] < 0 ? 1 : 0;
          int i5 = Math.max(10, i4 != 0 ? i1 / 10 : i1 * paramArrayOfInt[i3] / m);
          Rectangle localRectangle = new Rectangle(paramInt1 + paramInt4 + 2, i2 + j / 2 - k / 2, i5, k);
          paramGraphics.setColor(new Color(i4 != 0 ? 10485760 : 40960));
          paramGraphics.fillRect(localRectangle.x + 4, localRectangle.y + localRectangle.height - k + 2, localRectangle.width - 9, k - 4);
          paramGraphics.setColor(new Color(i4 != 0 ? 12582912 : 49152));
          paramGraphics.drawRect(localRectangle.x + 3, localRectangle.y + localRectangle.height - k + 1, localRectangle.width - 8, k - 3);
          paramGraphics.setColor(new Color(i4 != 0 ? 8388608 : 32768));
          paramGraphics.drawRect(localRectangle.x + 3, localRectangle.y + localRectangle.height - k, localRectangle.width - 7, k - 2);
          String str = paramString;
          if (i4 == 0)
            str = paramArrayOfInt[i3];
          int i6 = paramFontMetrics.stringWidth(str) < localRectangle.width - 10 ? 1 : 0;
          int i7 = i6 != 0 ? localRectangle.x + localRectangle.width - 6 - paramFontMetrics.stringWidth(str) : localRectangle.x + localRectangle.width + 3;
          paramGraphics.setColor(i6 != 0 ? Color.white : new Color(6710886));
          paramGraphics.drawString(str, i7, i2 + j / 2 - paramFontMetrics.getDescent() + i / 2 - 1);
        }
        i2 += j;
      }
    return i2 - paramInt2;
  }

  public void setCursor(Cursor paramCursor)
  {
    super.setCursor(paramCursor);
    this.LF.getGlassPane().setCursor(paramCursor);
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    String str = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if (str != null)
      fireActionEvent(new ActionEvent(this, 1001, str));
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY()) == null ? 0 : 12));
  }

  public void addActionListener(ActionListener paramActionListener)
  {
    this.AG.add(ActionListener.class, paramActionListener);
  }

  public void removeActionListener(ActionListener paramActionListener)
  {
    this.AG.remove(ActionListener.class, paramActionListener);
  }

  protected void fireActionEvent(ActionEvent paramActionEvent)
  {
    ActionListener[] arrayOfActionListener = (ActionListener[])this.AG.getListeners(ActionListener.class);
    for (int i = 0; (arrayOfActionListener != null) && (i < arrayOfActionListener.length); i++)
      arrayOfActionListener[i].actionPerformed(paramActionEvent);
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
  
  class FadeCopyrightThread
  implements Runnable
{
  private AppletTab EH;
  private Thread HG;
  private boolean FH;

  public FadeCopyrightThread(AppletTab paramAppletTab1, AppletTab paramAppletTab2)
  {
    this.EH = paramAppletTab2;
    this.FH = true;
    this.HG = new Thread(this, "MCS-CopyrightFader");
    this.HG.start();
  }

  public void stop()
  {
    this.FH = false;
    try
    {
      this.HG.interrupt();
    }
    catch (Exception localException1)
    {
    }
    try
    {
      this.HG.join();
    }
    catch (Exception localException2)
    {
    }
  }

  public void run()
  {
    try
    {
      long l = System.currentTimeMillis();
      do
      {
        this.EH.repaint();
        try
        {
          Thread.sleep(200L);
        }
        catch (Exception localException)
        {
        }
        if (System.currentTimeMillis() >= l + 10000L)
          break;
      }
      while (this.FH);
    }
    finally
    {
      this.HG = null;
    }
  }
}
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.AppletTab
 * JD-Core Version:    0.6.2
 */