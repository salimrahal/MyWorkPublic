package mcsaplugins.firewall;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JPanel;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.Util;

public class FirewallTab extends AppletTab
  implements Runnable, ActionListener, KeyListener
{
  private static final int BOXWIDTH = 180;
  private firewall m_plugin;
  private Image m_background;
  private Font m_fHead;
  private Font m_fSubHead;
  private Font m_fBody;
  private int m_scrollPx = 0;
  private int m_nDrawWidth;
  private BufferedImage m_boxShadow;
  private Object m_tScrollLock;
  private Thread m_tScroll;
  private int m_nScrollToPx = -1;
  private int m_nScrollFromPx = -1;
  private long m_lScrollStart;
  private boolean m_bUserHasScrolled;
  private JPanel m_startPanel;
  private JButton m_start;

  public FirewallTab(Applet paramApplet, firewall paramfirewall)
  {
    super(paramApplet, paramfirewall, paramfirewall.getImage("tabfirewall.gif"), "firewall");
    this.m_plugin = paramfirewall;
    this.m_tScrollLock = new Object();
    setFocusable(true);
    addKeyListener(this);
  }

  public void doFirstTimeInit()
  {
    this.m_background = this.m_plugin.getAppletImage(TX("fwbg2.png"));
    MediaTracker localMediaTracker = new MediaTracker(this);
    localMediaTracker.addImage(this.m_background, 0);
    try
    {
      localMediaTracker.waitForAll();
    }
    catch (Exception localException)
    {
    }
    this.m_fHead = Util.getFont(new String[] { "Segoe UI Semibold", "Segoe UI", "Verdana" }, 1, 19);
    this.m_fSubHead = Util.getFont(new String[] { "Segoe UI Semibold", "Segoe UI", "Verdana" }, 1, 16);
    this.m_fBody = Util.getFont(new String[] { "Segoe UI", "Verdana" }, 0, 12);
    this.m_bUserHasScrolled = false;
    setLayout(new GridLayout(1, 1));
    this.m_startPanel = new FirewallTab.1(this);
    this.m_start = new JButton("Start Test");
    this.m_start.setSize(this.m_start.getPreferredSize());
    this.m_start.addActionListener(this);
    this.m_startPanel.setOpaque(false);
    this.m_startPanel.setBounds(getBounds());
    this.m_startPanel.setLayout(new GridBagLayout());
    Util.gbAdd(this.m_startPanel, this.m_start, 0, 0, "x1y1a5");
    add(this.m_startPanel);
    validate();
    setFadeCopyrightInfo(true);
  }

  public void reset()
  {
    setStartButtonVisible(true);
    this.m_bUserHasScrolled = false;
  }

  public void setStartButtonVisible(boolean paramBoolean)
  {
    this.m_startPanel.setVisible(paramBoolean);
  }

  public void updateCurrentTest()
  {
    if (!this.m_bUserHasScrolled)
    {
      FirewallTest localFirewallTest = (FirewallTest)this.m_plugin.getTest();
      if (localFirewallTest != null)
        scrollToPx(localFirewallTest.getCurrentTestNum() * 180 + 90 - getSize().width / 2);
    }
  }

  private void scrollToPx(int paramInt)
  {
    int i = getSize().width;
    if (this.m_nDrawWidth > i)
      if (paramInt != (this.m_nScrollToPx == -1 ? this.m_scrollPx : this.m_nScrollToPx))
      {
        int j = Math.max(0, Math.min(this.m_nDrawWidth - i, paramInt));
        if (j != this.m_scrollPx)
          synchronized (this.m_tScrollLock)
          {
            this.m_nScrollToPx = j;
            this.m_nScrollFromPx = this.m_scrollPx;
            this.m_lScrollStart = System.currentTimeMillis();
            if (this.m_tScroll == null)
            {
              this.m_tScroll = new Thread(this);
              this.m_tScroll.start();
            }
          }
      }
  }

  public void run()
  {
    int i = 500;
    while (this.m_scrollPx != this.m_nScrollToPx)
    {
      long l = this.m_lScrollStart + i;
      double d = Math.min(1.0D, (i - l + System.currentTimeMillis() + 50L) / i);
      this.m_scrollPx = ((int)((this.m_nScrollToPx - this.m_nScrollFromPx) * d) + this.m_nScrollFromPx);
      repaint();
      try
      {
        Thread.sleep(50L);
      }
      catch (Exception localException)
      {
      }
    }
    synchronized (this.m_tScrollLock)
    {
      this.m_tScroll = null;
      this.m_nScrollToPx = -1;
      this.m_nScrollFromPx = -1;
      this.m_lScrollStart = 0L;
    }
    repaint();
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().height;
    int j = getSize().width;
    paramGraphics.setColor(Color.white);
    paramGraphics.fillRect(0, 0, j, i);
    FirewallTest localFirewallTest = (FirewallTest)this.m_plugin.getTest();
    FirewallTestItem[] arrayOfFirewallTestItem = localFirewallTest == null ? null : localFirewallTest.getTests();
    if (localFirewallTest == null)
      arrayOfFirewallTestItem = FirewallTest.parseTestSpec(this.m_plugin.iniGetString("firewall_testports"), null, null);
    int k = arrayOfFirewallTestItem == null ? 0 : arrayOfFirewallTestItem.length;
    this.m_nDrawWidth = (k * 180 + 20);
    int m = this.m_nDrawWidth > j ? -this.m_scrollPx : j / 2 - this.m_nDrawWidth / 2;
    paramGraphics.translate(m, 0);
    int n = this.m_nDrawWidth > j ? i - 10 : i;
    Rectangle localRectangle1;
    for (int i1 = 0; i1 < k; i1++)
    {
      localRectangle1 = new Rectangle(180 * i1, 0, 200, n);
      if (localRectangle1.intersects(new Rectangle(this.m_scrollPx, 0, j, i)))
        drawBox(paramGraphics, localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height, localFirewallTest, arrayOfFirewallTestItem[i1]);
    }
    paramGraphics.translate(-m, 0);
    if (this.m_nDrawWidth > j)
    {
      localObject1 = new Rectangle(150, 0, 14, 16);
      localRectangle1 = new Rectangle(150, 16, 14, 16);
      Rectangle localRectangle2 = new Rectangle(150, 32, 14, 16);
      Rectangle localRectangle3 = new Rectangle(150, 48, 14, 16);
      paramGraphics.setColor(Color.blue);
      int i2 = this.m_nScrollToPx < this.m_nScrollFromPx ? 1 : 0;
      int i3 = this.m_nScrollToPx > this.m_nScrollFromPx ? 1 : 0;
      Object localObject2 = i2 != 0 ? localRectangle2 : localObject1;
      Rectangle localRectangle4 = i3 != 0 ? localRectangle3 : localRectangle1;
      int i4 = i - 10 - localRectangle4.height / 2;
      addHitRegion("scrollright", new Rectangle(j - 5 - localObject2.width, i4, localRectangle4.width, localRectangle4.height));
      drawImagePartAt(paramGraphics, this.m_background, localRectangle4, j - 5 - localObject2.width, i4);
      int i5 = i - 10 - localObject2.height / 2;
      addHitRegion("scrollleft", new Rectangle(5, i5, localObject2.width, localObject2.height));
      drawImagePartAt(paramGraphics, this.m_background, localObject2, 5, i4);
    }
    paramGraphics.setFont(this.m_fBody);
    Object localObject1 = paramGraphics.getFontMetrics();
    paintCopyrightInfo(paramGraphics, 10, i - 3 * ((FontMetrics)localObject1).getHeight());
    doOverlayMessages(paramGraphics);
  }

  private void drawBox(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, FirewallTest paramFirewallTest, FirewallTestItem paramFirewallTestItem)
  {
    paramGraphics.translate(paramInt1, paramInt2);
    Rectangle localRectangle1 = new Rectangle(10, 10, paramInt3 - 27, paramInt4 - 27);
    paramGraphics.setColor(new Color(16580094));
    paramGraphics.fillRect(localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height);
    drawBoxShadow(paramGraphics, 0, 0, paramInt3, paramInt4);
    Util.setAntialias(paramGraphics, true);
    paramGraphics.setFont(this.m_fBody);
    int i = Math.max(50, paramGraphics.getFontMetrics().stringWidth("Response time") + 10);
    Rectangle localRectangle2 = new Rectangle(localRectangle1.x, localRectangle1.y + 17, localRectangle1.width, 48);
    localRectangle2 = drawTitle(paramGraphics, localRectangle2.x, localRectangle2.y, localRectangle2.width, paramFirewallTestItem);
    int j = Math.max(i, localRectangle1.height * 2 / 8);
    int k = Math.max(50, localRectangle1.height - 57 - j - localRectangle2.height);
    int m = localRectangle2.y + localRectangle2.height + 10;
    Rectangle localRectangle3 = new Rectangle(localRectangle1.x, m, localRectangle1.width, k);
    drawStatus(paramGraphics, localRectangle3.x, localRectangle3.y, localRectangle3.width, localRectangle3.height, paramFirewallTest, paramFirewallTestItem);
    Rectangle localRectangle4 = new Rectangle(localRectangle1.x + 10, localRectangle3.y + localRectangle3.height + 20, localRectangle1.width - 20, j);
    drawGraph(paramGraphics, localRectangle4.x, localRectangle4.y, localRectangle4.width, localRectangle4.height, paramFirewallTest, paramFirewallTestItem);
    paramGraphics.translate(-paramInt1, -paramInt2);
  }

  private void drawStatus(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, FirewallTest paramFirewallTest, FirewallTestItem paramFirewallTestItem)
  {
    int[][] arrayOfInt = paramFirewallTestItem.getResponseTimes();
    int i = arrayOfInt == null ? 0 : arrayOfInt.length * ((arrayOfInt.length > 0) && (arrayOfInt[0] != null) ? arrayOfInt[0].length : 0);
    int j = paramFirewallTestItem.getNumTestsPerformed();
    int k = (paramFirewallTest != null) && (paramFirewallTest.getPercentComplete() >= 100) ? 100 : paramFirewallTestItem.getPercentComplete();
    Object localObject1 = paramFirewallTestItem != null ? paramFirewallTestItem.getErrorCode() : null;
    Object localObject2;
    if (localObject1 != null)
    {
      paramGraphics.setFont(this.m_fBody);
      FontMetrics localFontMetrics1 = paramGraphics.getFontMetrics();
      localObject2 = "Error: " + localObject1.desc;
      Rectangle localRectangle1 = drawCentredText(null, (String)localObject2, paramInt1 + 5, 0, paramInt3 - 10, localFontMetrics1);
      drawCentredText(paramGraphics, (String)localObject2, paramInt1 + 5, paramInt2 + paramInt4 / 2 - localRectangle1.height / 2, paramInt3 - 10, localFontMetrics1);
    }
    else
    {
      boolean bool;
      Object localObject3;
      if ((k < 100) || (arrayOfInt == null) || (arrayOfInt.length == 0) || (arrayOfInt[0] == null) || (arrayOfInt[0].length == 0))
      {
        bool = paramFirewallTest == null ? false : paramFirewallTest.isRunning();
        if (bool)
        {
          paramGraphics.setFont(this.m_fBody);
          localObject2 = paramGraphics.getFontMetrics();
          if (paramFirewallTestItem.isRunning())
          {
            int i1 = ((FontMetrics)localObject2).getHeight() + 10 + 10;
            localObject3 = "Progress";
            paramGraphics.drawString((String)localObject3, paramInt1 + paramInt3 / 2 - ((FontMetrics)localObject2).stringWidth((String)localObject3) / 2, paramInt2 + paramInt4 / 2 - i1 / 2 + ((FontMetrics)localObject2).getAscent());
            paramGraphics.drawRect(paramInt1 + paramInt3 / 2 - 50, paramInt2 + paramInt4 / 2 + i1 / 2 - 10, 101, 10);
            paramGraphics.setColor(new Color(170));
            paramGraphics.fillRect(paramInt1 + paramInt3 / 2 - 50 + 1, paramInt2 + paramInt4 / 2 + i1 / 2 - 10 + 1, k, 9);
          }
        }
      }
      else
      {
        bool = false;
        int m;
        for (int n = 0; n < i; n++)
          bool += (arrayOfInt[(n / arrayOfInt[0].length)][(n % arrayOfInt[0].length)] >= 0);
        n = j == 0 ? 0 : m * 100 / j;
        paramGraphics.setFont(this.m_fSubHead);
        FontMetrics localFontMetrics2 = paramGraphics.getFontMetrics();
        localObject3 = new Rectangle(0, 106, 75, 75);
        Rectangle localRectangle2 = new Rectangle(75, 106, 75, 75);
        Rectangle localRectangle3 = new Rectangle(0, 181, 75, 75);
        String str = "Connection Blocked";
        Object localObject4 = localRectangle2;
        Color localColor = new Color(9437184);
        if (n > 95)
        {
          str = "Connection Successful";
          localObject4 = localObject3;
          localColor = new Color(36864);
        }
        else if (n > 10)
        {
          str = "Connection Unreliable";
          localObject4 = localRectangle3;
          localColor = new Color(14911764);
        }
        Rectangle localRectangle4 = drawCentredText(null, str, paramInt1 + 20, 0, paramInt3 - 40, localFontMetrics2);
        int i2 = Math.min(((Rectangle)localObject4).height, paramInt4 - localRectangle4.height - 10);
        if (i2 < 10)
          i2 = 0;
        int i3 = localRectangle4.height + i2;
        int i4 = Math.max(0, (paramInt4 - i3) / 4);
        i3 += i4;
        drawImagePartAt(paramGraphics, this.m_background, (Rectangle)localObject4, paramInt1 + paramInt3 / 2 - ((Rectangle)localObject4).width / 2, paramInt2 + paramInt4 / 2 - i3 / 2, i2 / ((Rectangle)localObject4).height);
        paramGraphics.setColor(localColor);
        drawCentredText(paramGraphics, str, paramInt1 + 20, paramInt2 + paramInt4 / 2 - i3 / 2 + i2 + i4 + localFontMetrics2.getAscent(), paramInt3 - 40, localFontMetrics2);
      }
    }
    paramGraphics.setColor(new Color(13884389));
    paramGraphics.drawLine(paramInt1 + 20, paramInt2 + paramInt4, paramInt1 + paramInt3 - 20, paramInt2 + paramInt4);
  }

  private Rectangle drawTitle(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, FirewallTestItem paramFirewallTestItem)
  {
    Font localFont = paramGraphics.getFont();
    paramGraphics.setFont(this.m_fBody);
    FontMetrics localFontMetrics1 = paramGraphics.getFontMetrics();
    paramGraphics.setFont(this.m_fHead);
    FontMetrics localFontMetrics2 = paramGraphics.getFontMetrics();
    Rectangle localRectangle = new Rectangle(0, 101, 164, 5);
    String str1 = paramFirewallTestItem.getAlias();
    int i = drawCentredText(null, str1, paramInt1 + 5, 0, paramInt3 - 10, localFontMetrics2).height;
    i += 15;
    int j = paramFirewallTestItem.getLowPort();
    int k = paramFirewallTestItem.getHighPort();
    if (j > k)
    {
      int m = j;
      j = k;
      k = m;
    }
    String str2 = "Port " + j + (j != k ? "-" + k : "") + " (" + paramFirewallTestItem.getProtocol().toUpperCase() + ")";
    str2 = (str2 == null) || (str2.trim().length() == 0) ? null : str2.trim();
    if (str2 != null)
      i += localFontMetrics1.getHeight();
    paramGraphics.setColor(Color.white);
    paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, i);
    if (str2 != null)
    {
      paramGraphics.setColor(new Color(6710886));
      paramGraphics.setFont(this.m_fBody);
      paramGraphics.drawString(str2, paramInt1 + paramInt3 - 20 - localFontMetrics1.stringWidth(str2), paramInt2 + i - 10 - localFontMetrics1.getDescent());
    }
    paramGraphics.setColor(new Color(3355443));
    paramGraphics.setFont(this.m_fHead);
    drawCentredText(paramGraphics, str1, paramInt1 + 5, paramInt2 + localFontMetrics2.getAscent(), paramInt3 - 10, localFontMetrics2);
    drawImagePartAt(paramGraphics, this.m_background, localRectangle, paramInt1, paramInt2 + i - localRectangle.height);
    paramGraphics.setFont(localFont);
    return new Rectangle(paramInt1, paramInt2, paramInt3, i);
  }

  private void drawGraph(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, FirewallTest paramFirewallTest, FirewallTestItem paramFirewallTestItem)
  {
    paramGraphics.setFont(this.m_fBody);
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int i = localFontMetrics.getHeight();
    int[][] arrayOfInt = paramFirewallTestItem.getResponseTimes();
    int j = arrayOfInt == null ? 0 : arrayOfInt.length * ((arrayOfInt.length > 0) && (arrayOfInt[0] != null) ? arrayOfInt[0].length : 0);
    int k = paramFirewallTestItem.getNumTestsPerformed();
    Rectangle localRectangle1 = new Rectangle(paramInt1 + i + 3, paramInt2, paramInt3 - i - 3, paramInt4);
    int m = Math.min(j, localRectangle1.width / (i + 5));
    int n = arrayOfInt == null ? 0 : Math.max(0, k - m);
    int i1 = 0;
    for (int i2 = 0; i2 < k; i2++)
      i1 = Math.max(i1, arrayOfInt[(i2 / arrayOfInt[0].length)][(i2 % arrayOfInt[0].length)]);
    double d = localRectangle1.width / m;
    for (int i3 = n; i3 < n + m; i3++)
    {
      paramGraphics.setColor(new Color(i3 % 2 == 0 ? 16316664 : 15790320));
      Rectangle localRectangle2 = new Rectangle(localRectangle1.x + (int)((i3 - n) * d), localRectangle1.y, (int)((i3 + 1) * d) - (int)(i3 * d), localRectangle1.height);
      paramGraphics.fillRect(localRectangle2.x, localRectangle2.y, localRectangle2.width, localRectangle2.height);
      if ((arrayOfInt != null) && (k > i3))
      {
        int i5 = arrayOfInt[(i3 / arrayOfInt[0].length)][(i3 % arrayOfInt[0].length)];
        int i6 = (i1 <= 0) || (i5 <= 0) ? localRectangle2.height * 1 / 5 : localRectangle2.height * 3 / 4 * i5 / i1;
        paramGraphics.setColor(new Color(i5 < 0 ? 9437184 : 40960));
        paramGraphics.fillRect(localRectangle2.x + 5, localRectangle2.y + localRectangle2.height - i6 + 2, localRectangle2.width - 9, i6 - 4);
        paramGraphics.setColor(new Color(i5 < 0 ? 11534336 : 45056));
        paramGraphics.drawRect(localRectangle2.x + 4, localRectangle2.y + localRectangle2.height - i6 + 1, localRectangle2.width - 8, i6 - 3);
        paramGraphics.setColor(new Color(i5 < 0 ? 7340032 : 32768));
        paramGraphics.drawRect(localRectangle2.x + 3, localRectangle2.y + localRectangle2.height - i6, localRectangle2.width - 7, i6 - 2);
        paramGraphics.setColor(Color.white);
        String str = i5 >= 0 ? i5 + " ms" : "BLOCKED";
        int i7 = localFontMetrics.stringWidth(str);
        int i8 = localRectangle1.y + localRectangle1.height - i7 / 2 - 5;
        if (i7 > i6 - 8)
        {
          i8 -= i6;
          paramGraphics.setColor(new Color(6710886));
        }
        drawRotatedText(paramGraphics, str, localRectangle2.x + localRectangle2.width / 2 + localFontMetrics.getDescent() + 2, i8);
      }
    }
    paramGraphics.setColor(new Color(13421772));
    paramGraphics.drawRect(localRectangle1.x, localRectangle1.y, localRectangle1.width - 1, localRectangle1.height - 1);
    paramGraphics.setColor(new Color(11184810));
    i3 = localRectangle1.x - localFontMetrics.getDescent();
    int i4 = localRectangle1.y + localRectangle1.height / 2;
    drawRotatedText(paramGraphics, "Response time", i3, i4);
  }

  private void drawBoxShadow(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.m_boxShadow == null) || (this.m_boxShadow.getWidth(null) != paramInt3) || (this.m_boxShadow.getHeight(null) != paramInt4))
    {
      BufferedImage localBufferedImage = ((Graphics2D)paramGraphics).getDeviceConfiguration().createCompatibleImage(paramInt3, paramInt4, 3);
      Graphics2D localGraphics2D = localBufferedImage.createGraphics();
      Rectangle localRectangle1 = new Rectangle(0, 0, 29, 32);
      Rectangle localRectangle2 = new Rectangle(79, 0, 37, 32);
      Rectangle localRectangle3 = new Rectangle(0, 64, 26, 32);
      Rectangle localRectangle4 = new Rectangle(79, 64, 37, 32);
      drawImagePartAt(localGraphics2D, this.m_background, localRectangle1, 0, 0);
      drawImagePartAt(localGraphics2D, this.m_background, localRectangle2, paramInt3 - localRectangle2.width, 0);
      drawImagePartAt(localGraphics2D, this.m_background, localRectangle3, 0, paramInt4 - localRectangle3.height);
      drawImagePartAt(localGraphics2D, this.m_background, localRectangle4, paramInt3 - localRectangle4.width, paramInt4 - localRectangle4.height);
      Rectangle localRectangle5 = new Rectangle(localRectangle1.x + localRectangle1.width, localRectangle1.y, localRectangle2.x - (localRectangle1.x + localRectangle1.width), Math.max(localRectangle1.height, localRectangle2.height));
      localGraphics2D.setClip(localRectangle1.width, 0, paramInt3 - localRectangle1.width - localRectangle2.width, paramInt4);
      int i = localRectangle1.width;
      while (i < paramInt3 - localRectangle2.width)
      {
        drawImagePartAt(localGraphics2D, this.m_background, localRectangle5, i, 0);
        localGraphics2D.setColor(Color.black);
        i += localRectangle5.width;
      }
      Rectangle localRectangle6 = new Rectangle(localRectangle3.x + localRectangle3.width, localRectangle3.y, localRectangle4.x - (localRectangle3.x + localRectangle3.width), Math.max(localRectangle3.height, localRectangle4.height));
      localGraphics2D.setClip(localRectangle3.width, 0, paramInt3 - localRectangle3.width - localRectangle4.width, paramInt4);
      int j = localRectangle3.width;
      while (j < paramInt3 - localRectangle4.width)
      {
        drawImagePartAt(localGraphics2D, this.m_background, localRectangle6, j, paramInt4 - localRectangle6.height);
        localGraphics2D.setColor(Color.black);
        j += localRectangle6.width;
      }
      Rectangle localRectangle7 = new Rectangle(localRectangle1.x, localRectangle1.y + localRectangle1.height, Math.max(localRectangle1.width, localRectangle3.width), localRectangle3.y - (localRectangle1.y + localRectangle1.height));
      localGraphics2D.setClip(0, localRectangle1.height, localRectangle7.width, paramInt4 - localRectangle3.height - localRectangle1.height);
      int k = localRectangle1.height;
      while (k < paramInt4 - localRectangle3.height)
      {
        drawImagePartAt(localGraphics2D, this.m_background, localRectangle7, 0, k);
        localGraphics2D.setColor(Color.black);
        k += localRectangle7.height;
      }
      Rectangle localRectangle8 = new Rectangle(localRectangle2.x, localRectangle2.y + localRectangle2.height, Math.max(localRectangle2.width, localRectangle4.width), localRectangle4.y - (localRectangle2.y + localRectangle2.height));
      localGraphics2D.setClip(paramInt3 - localRectangle8.width, localRectangle2.height, localRectangle8.width, paramInt4 - localRectangle4.height - localRectangle2.height);
      int m = localRectangle2.height;
      while (m < paramInt4 - localRectangle4.height)
      {
        drawImagePartAt(localGraphics2D, this.m_background, localRectangle8, paramInt3 - localRectangle8.width, m);
        localGraphics2D.setColor(Color.black);
        m += localRectangle8.height;
      }
      localGraphics2D.dispose();
      this.m_boxShadow = localBufferedImage;
    }
    paramGraphics.drawImage(this.m_boxShadow, paramInt1, paramInt2, null);
  }

  private static void drawImagePartAt(Graphics paramGraphics, Image paramImage, Rectangle paramRectangle, int paramInt1, int paramInt2)
  {
    drawImagePartAt(paramGraphics, paramImage, paramRectangle, paramInt1, paramInt2, 1.0D);
  }

  private static void drawImagePartAt(Graphics paramGraphics, Image paramImage, Rectangle paramRectangle, int paramInt1, int paramInt2, double paramDouble)
  {
    Shape localShape = paramGraphics.getClip();
    Rectangle localRectangle = new Rectangle(paramInt1, paramInt2, (int)(paramRectangle.width * paramDouble), (int)(paramRectangle.height * paramDouble));
    paramGraphics.setClip(localShape == null ? localRectangle : localRectangle.intersection(localShape.getBounds()));
    if (paramDouble != 1.0D)
      paramGraphics.drawImage(paramImage, paramInt1 - (int)(paramRectangle.x * paramDouble), paramInt2 - (int)(paramRectangle.y * paramDouble), (int)(paramImage.getWidth(null) * paramDouble), (int)(paramImage.getHeight(null) * paramDouble), null);
    else
      paramGraphics.drawImage(paramImage, paramInt1 - paramRectangle.x, paramInt2 - paramRectangle.y, null);
    paramGraphics.setClip(localShape);
  }

  private static void drawRotatedText(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2)
  {
    AffineTransform localAffineTransform1 = ((Graphics2D)paramGraphics).getTransform();
    AffineTransform localAffineTransform2 = new AffineTransform(localAffineTransform1);
    localAffineTransform2.rotate(-1.570796326794897D, paramInt1, paramInt2);
    ((Graphics2D)paramGraphics).setTransform(localAffineTransform2);
    paramGraphics.drawString(paramString, paramInt1 - paramGraphics.getFontMetrics().stringWidth(paramString) / 2, paramInt2);
    ((Graphics2D)paramGraphics).setTransform(localAffineTransform1);
  }

  private static Rectangle drawCentredText(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, FontMetrics paramFontMetrics)
  {
    if ((paramFontMetrics == null) && (paramGraphics != null))
      paramFontMetrics = paramGraphics.getFontMetrics();
    int i = paramFontMetrics.getHeight();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, " ");
    Object localObject = "";
    int j = paramInt2;
    int k = 0;
    while ((((String)localObject).length() > 0) || (localStringTokenizer.hasMoreTokens()))
    {
      String str1 = localStringTokenizer.hasMoreTokens() ? localStringTokenizer.nextToken() : null;
      String str2 = localObject + (((String)localObject).length() == 0 ? "" : " ") + str1;
      if ((str2 != null) && (paramFontMetrics.stringWidth(str2) > paramInt3))
      {
        if (paramGraphics != null)
          paramGraphics.drawString((String)localObject, paramInt1 + paramInt3 / 2 - paramFontMetrics.stringWidth((String)localObject) / 2, j);
        k++;
        localObject = str1;
        j += i;
      }
      else if (str1 == null)
      {
        if (paramGraphics != null)
          paramGraphics.drawString((String)localObject, paramInt1 + paramInt3 / 2 - paramFontMetrics.stringWidth((String)localObject) / 2, j);
        k++;
        localObject = "";
      }
      else
      {
        localObject = str2;
      }
    }
    return new Rectangle(paramInt1, paramInt2, paramInt3, k * i);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_start)
    {
      setStartButtonVisible(false);
      fireActionEvent(new ActionEvent(this, 1001, "start"));
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    String str = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if ("scrollleft".equals(str))
    {
      this.m_bUserHasScrolled = true;
      scrollToPx(this.m_nScrollToPx < 0 ? this.m_scrollPx - 180 : this.m_nScrollToPx - 180);
    }
    else if ("scrollright".equals(str))
    {
      this.m_bUserHasScrolled = true;
      scrollToPx(this.m_nScrollToPx < 0 ? this.m_scrollPx + 180 : this.m_nScrollToPx + 180);
    }
    super.mousePressed(paramMouseEvent);
  }

  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyCode() == 39)
    {
      this.m_bUserHasScrolled = true;
      scrollToPx(this.m_nScrollToPx < 0 ? this.m_scrollPx + 180 : this.m_nScrollToPx + 180);
    }
    else if (paramKeyEvent.getKeyCode() == 37)
    {
      this.m_bUserHasScrolled = true;
      scrollToPx(this.m_nScrollToPx < 0 ? this.m_scrollPx - 180 : this.m_nScrollToPx - 180);
    }
  }

  public void keyReleased(KeyEvent paramKeyEvent)
  {
  }

  public void keyTyped(KeyEvent paramKeyEvent)
  {
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.firewall.FirewallTab
 * JD-Core Version:    0.6.2
 */