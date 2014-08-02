package mcsaplugins.iptv;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.U;

public class IptvTab extends AppletTab
  implements Runnable, ActionListener
{
  private static final Color cG2 = new Color(226, 234, 243);
  private static final int BARTHICKNESS = 70;
  private BufferedImage m_iptvBack;
  private Vector m_iptvlabels = new Vector();
  private long[] m_lossc = { 0, 65408L, 1L, 12648384L, 2L, 16777152L, 10L, 16761024L, 100L, 16744576L };
  private long[] m_jitterc = { 0, 65408L, 3L, 12648384L, 10L, 16777152L, 40L, 16761024L, 100L, 16744576L };
  private static final int MINJITTER = 0;
  private int MAXJITTER = 100;
  private float m_loss;
  private float m_jitter;
  private Point m_pLAudioJitterSrc;
  private Point m_pRAudioJitterSrc;
  private Point m_pVideoJitterSrc;
  private int m_nLAudioBottom = -1;
  private int m_nRAudioBottom = -1;
  private int m_nVideoBottom = -1;
  private int m_aniLMult = -1;
  private int m_aniJMult = 1;
  private Thread m_ani;
  private boolean m_bRunAnimation = true;
  private iptv m_plugin;
  private JButton m_start = new JButton();
  private boolean m_bShowStartButton = true;

  public IptvTab(Applet paramApplet, iptv paramiptv)
  {
    super(paramApplet, paramiptv, paramiptv.getImage("tabiptv.gif"), "iptv");
    this.m_plugin = paramiptv;
    this.m_start.setText(RC(TX("start")));
    this.m_start.addActionListener(this);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_start)
      fireActionEvent(new ActionEvent(this, 1001, "start"));
  }

  public void doFirstTimeInit()
  {
    setLayout(null);
    add(this.m_start);
    initHiJitter();
    initJitterColourTable();
    initLossColourTable();
    String str = iniGetString("iptvlabel");
    StringTokenizer localStringTokenizer = new StringTokenizer(str == null ? "" : str, TX("\r\n"));
    while (localStringTokenizer.hasMoreTokens())
      this.m_iptvlabels.addElement(localStringTokenizer.nextToken());
    this.m_jitter = ((0 + this.MAXJITTER) / 2);
    this.m_loss = 0.0F;
    this.m_ani = new Thread(this, TX("MCS-IPTV-Animation"));
    this.m_ani.start();
  }

  public void reset()
  {
    this.m_plugin.reset();
    initHiJitter();
  }

  private void drawGraphLabels(Graphics paramGraphics)
  {
    int i = getSize().height;
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    paramGraphics.setColor(Color.gray);
    for (int j = 0; j < this.m_iptvlabels.size(); j++)
    {
      String str1 = (String)this.m_iptvlabels.elementAt(j);
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, TX(","));
      try
      {
        float f1 = -1.0F;
        float f2 = -1.0F;
        String str2 = localStringTokenizer.nextToken().trim().toLowerCase();
        float f3 = Float.valueOf(localStringTokenizer.nextToken().trim()).floatValue();
        localStringTokenizer.nextToken();
        String str3 = localStringTokenizer.nextToken();
        if (TX("loss").equals(str2))
          f1 = f3;
        else if (TX("jitter").equals(str2))
          f2 = f3;
        if ((!str3.equals(TX("-"))) && (((f1 >= 0.0F) && (f1 <= 100.0F)) || ((f2 >= 0.0F) && (f2 <= 100.0F))))
        {
          int k = f1 >= 0.0F ? lossToX(f1) : jitterToX(f2);
          int m = localFontMetrics.stringWidth(str3);
          paramGraphics.drawString(str3, k - m / 2, i / 2);
          paramGraphics.drawLine(k - m / 2, 0, k - m / 2, i);
          paramGraphics.drawLine(k - m / 2, 0, k - m / 2, i);
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  private void drawIPTVBackground(Graphics paramGraphics, Dimension paramDimension)
  {
    int i = paramDimension.width;
    int j = paramDimension.height;
    if (paramGraphics != null)
    {
      Color localColor = getBackground();
      paramGraphics.setColor(localColor);
      paramGraphics.fillRect(0, 0, i, j);
      String str1 = getUnregisteredMessage();
      int m;
      if (str1 != null)
      {
        localFontMetrics = paramGraphics.getFontMetrics();
        int k = localFontMetrics.getHeight();
        m = k / 2;
        String str2 = str1;
        int i1 = localFontMetrics.stringWidth(str2 + TX(" "));
        int i3 = 0;
        paramGraphics.setColor(new Color(14211288));
        int i5 = k;
        while (i5 < paramDimension.height)
        {
          int i6 = 0;
          while (i6 < paramDimension.width)
          {
            paramGraphics.drawString(str2, i3 + i6, i5);
            i6 += i1 + m;
          }
          i3 = i3 == 0 ? -(i1 + m) / 2 : 0;
          i5 += k + m;
        }
      }
      paramGraphics.setColor(cG2.darker());
      drawJitterBar(paramGraphics, 0, 0, i, 70);
      drawLossBar(paramGraphics, 0, j - 70, i, 70);
      drawGraphLabels(paramGraphics);
      paramGraphics.setColor(new Color(6908265));
      paramGraphics.setFont(new Font("Dialog", 1, 12));
      FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
      Image localImage1 = this.m_plugin.getAppletImage("laudio.gif");
      int n;
      if (localImage1 != null)
      {
        m = localImage1.getWidth(null);
        n = localImage1.getHeight(null);
        if ((m > 0) && (n > 0))
          paramGraphics.drawImage(localImage1, i / 6 - m / 2, j / 2 - n / 2, null);
        String str3 = RC("iptv.leftaudio");
        paramGraphics.drawString(str3, i / 6 - localFontMetrics.stringWidth(str3) / 2, j / 2 - n / 2 - localFontMetrics.getDescent() - 5);
        this.m_pLAudioJitterSrc = new Point(i / 6, j / 2 - n / 2 - localFontMetrics.getHeight() - 5);
        this.m_nLAudioBottom = (j / 2 + n / 2);
      }
      Image localImage2 = this.m_plugin.getAppletImage("video.gif");
      int i2;
      if (localImage2 != null)
      {
        n = localImage2.getWidth(null);
        i2 = localImage2.getHeight(null);
        if ((n > 0) && (i2 > 0))
          paramGraphics.drawImage(localImage2, i / 2 - n / 2, j / 2 - i2 / 2, null);
        String str4 = RC("iptv.video");
        paramGraphics.drawString(str4, i / 2 - localFontMetrics.stringWidth(str4) / 2, j / 2 - i2 / 2 - localFontMetrics.getDescent() - 5);
        this.m_pVideoJitterSrc = new Point(i / 2, j / 2 - i2 / 2 - localFontMetrics.getHeight() - 5);
        this.m_nVideoBottom = (j / 2 + i2 / 2);
      }
      Image localImage3 = this.m_plugin.getAppletImage("raudio.gif");
      if (localImage3 != null)
      {
        i2 = localImage3.getWidth(null);
        int i4 = localImage3.getHeight(null);
        if ((i2 > 0) && (i4 > 0))
          paramGraphics.drawImage(localImage3, 5 * i / 6 - i2 / 2, j / 2 - i4 / 2, null);
        String str5 = RC("iptv.rightaudio");
        paramGraphics.drawString(str5, 5 * i / 6 - localFontMetrics.stringWidth(str5) / 2, j / 2 - i4 / 2 - localFontMetrics.getDescent() - 5);
        this.m_pRAudioJitterSrc = new Point(5 * i / 6, j / 2 - i4 / 2 - localFontMetrics.getHeight() - 5);
        this.m_nRAudioBottom = (j / 2 + i4 / 2);
      }
      paramGraphics.translate(0, -30);
      Image localImage4 = getBackgroundOverlay();
      if (localImage4 != null)
        paramGraphics.drawImage(localImage4, 0, 0, null);
      paramGraphics.dispose();
    }
  }

  private void drawJitterBar(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = paramInt1; i < paramInt1 + paramInt3; i++)
    {
      float f = xToJitter(i);
      paramGraphics.setColor(jitterToColour(f));
      paramGraphics.drawLine(i, paramInt2, i, paramInt2 + paramInt4);
    }
    paramGraphics.setColor(Color.gray);
    Font localFont = paramGraphics.getFont();
    paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() | 0x1, localFont.getSize()));
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    paramGraphics.drawString(RC(TX("iptv.jitter")), paramInt1 + 3, paramInt2 + localFontMetrics.getAscent() + 3);
    paramGraphics.drawLine(paramInt1, paramInt2 + paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
    paramGraphics.setColor(Color.black);
    paramGraphics.setFont(localFont);
    localFontMetrics = paramGraphics.getFontMetrics();
    for (int j = 0; j < this.m_iptvlabels.size(); j++)
      try
      {
        String str1 = (String)this.m_iptvlabels.elementAt(j);
        StringTokenizer localStringTokenizer = new StringTokenizer(str1, TX(","));
        String str2 = localStringTokenizer.nextToken().trim().toLowerCase();
        if (TX("jitter").equals(str2))
        {
          int k = jitterToX(Float.valueOf(localStringTokenizer.nextToken().trim()).floatValue());
          String str3 = localStringTokenizer.nextToken().trim();
          if (!TX("-").equals(str3))
          {
            U.drawDot(paramGraphics, k, paramInt2 + paramInt4, Color.lightGray);
            paramGraphics.setColor(Color.black);
            int m = localFontMetrics.stringWidth(str3);
            if (k + m / 2 > paramInt3)
              k = paramInt3 - m / 2;
            paramGraphics.drawString(str3, Math.max(0, k - m / 2), paramInt2 + paramInt4 - 5);
          }
        }
      }
      catch (Exception localException)
      {
      }
  }

  private void drawLossBar(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = paramInt1; i < paramInt1 + paramInt3; i++)
    {
      float f = xToLoss(i);
      paramGraphics.setColor(lossToColour(f));
      paramGraphics.drawLine(i, paramInt2 + 1, i, paramInt2 + paramInt4);
    }
    paramGraphics.setColor(Color.gray);
    Font localFont = paramGraphics.getFont();
    paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() | 0x1, localFont.getSize()));
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    String str1 = RC(TX("iptv.packetloss"));
    paramGraphics.drawString(str1, paramInt1 + 3, paramInt2 + paramInt4 - localFontMetrics.getDescent() - 3);
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2);
    paramGraphics.setColor(Color.black);
    paramGraphics.setFont(localFont);
    localFontMetrics = paramGraphics.getFontMetrics();
    for (int j = 0; j < this.m_iptvlabels.size(); j++)
      try
      {
        String str2 = (String)this.m_iptvlabels.elementAt(j);
        StringTokenizer localStringTokenizer = new StringTokenizer(str2, TX(","));
        String str3 = localStringTokenizer.nextToken().trim().toLowerCase();
        if (TX("loss").equals(str3))
        {
          int k = lossToX(Float.valueOf(localStringTokenizer.nextToken().trim()).floatValue());
          String str4 = localStringTokenizer.nextToken().trim();
          if (!TX("-").equals(str4))
          {
            U.drawDot(paramGraphics, k, paramInt2, Color.lightGray);
            paramGraphics.setColor(Color.black);
            int m = localFontMetrics.stringWidth(str4);
            if (k + m / 2 > paramInt3)
              k = paramInt3 - m / 2;
            paramGraphics.drawString(str4, Math.max(0, k - m / 2), paramInt2 + localFontMetrics.getHeight() + 5);
          }
        }
      }
      catch (Exception localException)
      {
      }
  }

  private void initHiJitter()
  {
    int i = iniGetInteger(TX("iptv.hijitter"), -1);
    this.MAXJITTER = (i > 0 ? i : 100);
  }

  private void initJitterColourTable()
  {
    long[] arrayOfLong = U.parseColourTable(iniGetString(TX("iptv.jitter2c")));
    this.m_jitterc = (arrayOfLong == null ? this.m_jitterc : arrayOfLong);
  }

  private void initLossColourTable()
  {
    long[] arrayOfLong = U.parseColourTable(iniGetString(TX("iptv.loss2c")));
    this.m_lossc = (arrayOfLong == null ? this.m_lossc : arrayOfLong);
  }

  private Color jitterToColour(float paramFloat)
  {
    return U.valueToColour(paramFloat, this.m_jitterc);
  }

  private int jitterToX(double paramDouble)
  {
    return (int)(getSize().width * (U.log10(paramDouble + 1.0D) / U.log10(this.MAXJITTER + 1)));
  }

  private Color lossToColour(float paramFloat)
  {
    return U.valueToColour(paramFloat, this.m_lossc);
  }

  private int lossToX(double paramDouble)
  {
    return (int)(getSize().width * (U.log10(24.0D * paramDouble + 1.0D) / U.log10(2401.0D)));
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    IptvTest localIptvTest = (IptvTest)this.m_plugin.getTest();
    double[] arrayOfDouble1 = localIptvTest == null ? new double[] { this.m_ani == null ? -1.0F : this.m_jitter } : localIptvTest.getJitters();
    double[] arrayOfDouble2 = localIptvTest == null ? new double[] { this.m_ani == null ? -1.0F : this.m_loss } : localIptvTest.getPacketLosses();
    double d1 = (arrayOfDouble1 == null) || (arrayOfDouble1.length < 1) ? -1.0D : arrayOfDouble1[0];
    double d2 = (arrayOfDouble2 == null) || (arrayOfDouble2.length < 1) ? -1.0D : arrayOfDouble2[0];
    double d3 = (arrayOfDouble1 == null) || (arrayOfDouble1.length < 2) ? -1.0D : arrayOfDouble1[1];
    double d4 = (arrayOfDouble2 == null) || (arrayOfDouble2.length < 2) ? -1.0D : arrayOfDouble2[1];
    double d5 = (arrayOfDouble1 == null) || (arrayOfDouble1.length < 3) ? -1.0D : arrayOfDouble1[2];
    double d6 = (arrayOfDouble2 == null) || (arrayOfDouble2.length < 3) ? -1.0D : arrayOfDouble2[2];
    int k = (int)Math.max(Math.max(d1, d3), d5);
    if (k > this.MAXJITTER)
    {
      this.MAXJITTER = k;
      this.m_iptvBack = null;
    }
    if (this.m_iptvBack == null)
    {
      this.m_iptvBack = ((Graphics2D)paramGraphics).getDeviceConfiguration().createCompatibleImage(i, j, 3);
      Graphics2D localGraphics2D = this.m_iptvBack.createGraphics();
      ((Graphics2D)localGraphics2D).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      ((Graphics2D)localGraphics2D).setFont(getFont());
      drawIPTVBackground(localGraphics2D, getSize());
    }
    if (this.m_iptvBack != null)
      paramGraphics.drawImage(this.m_iptvBack, 0, 0, this);
    int m = d1 == -1.0D ? -1 : jitterToX(d1);
    int n = d2 == -1.0D ? -1 : lossToX(d2);
    int i1 = d3 == -1.0D ? -1 : jitterToX(d3);
    int i2 = d4 == -1.0D ? -1 : lossToX(d4);
    int i3 = d5 == -1.0D ? -1 : jitterToX(d5);
    int i4 = d6 == -1.0D ? -1 : lossToX(d6);
    int i5 = localIptvTest == null ? -1 : localIptvTest.getPercentDone();
    int i6 = (localIptvTest != null) && (i5 < 100) ? 1 : 0;
    int i7 = (localIptvTest != null) && (localIptvTest.getError() != null) ? 1 : 0;
    String str1 = null;
    int i8 = 1;
    if ((i7 != 0) && (localIptvTest != null) && (localIptvTest.getError().equals("timeout")))
    {
      str1 = RC(TX("iptvtimeout"));
    }
    else if (i7 != 0)
    {
      str1 = RC(TX("iptverror"));
      i8 = 0;
    }
    else if (isHitMaxTests())
    {
      str1 = RC(TX("hitmaxtests"));
      i8 = 0;
    }
    else if (i6 != 0)
    {
      str1 = RC(TX("iptvworking")) + TX(" ");
    }
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    String str2 = (d3 >= 0.0D) && (d4 >= 0.0D) ? RC(TX("iptv.jitterloss"), new String[] { (int)(d3 * 10.0D) / 10.0D, (int)(d4 * 10.0D) / 10.0D }) : null;
    String str3 = (d5 >= 0.0D) && (d6 >= 0.0D) ? RC(TX("iptv.jitterloss"), new String[] { (int)(d5 * 10.0D) / 10.0D, (int)(d6 * 10.0D) / 10.0D }) : null;
    String str4 = (d1 >= 0.0D) && (d2 >= 0.0D) ? RC(TX("iptv.jitterloss"), new String[] { (int)(d1 * 10.0D) / 10.0D, (int)(d2 * 10.0D) / 10.0D }) : null;
    if (str1 != null)
    {
      i9 = (i6 != 0) && (i7 == 0) ? 1 : 0;
      drawStatusBox(paramGraphics, str1, i / 2, 4, i9 != 0 ? 100 : 0);
      if (i9 != 0)
      {
        int i10 = localFontMetrics.stringWidth(str1);
        paramGraphics.drawRect(i / 2 + i10 / 2 - 50, 6, 100, localFontMetrics.getHeight() - 2);
        paramGraphics.setColor(new Color(190, 190, 145));
        paramGraphics.fillRect(i / 2 + i10 / 2 - 49, 7, i5, localFontMetrics.getHeight() - 3);
      }
    }
    if (this.m_bShowStartButton)
    {
      this.m_start.setVisible(true);
      this.m_start.setSize(this.m_start.getPreferredSize());
      this.m_start.setLocation(i / 2 - this.m_start.getSize().width / 2, 4 + localFontMetrics.getHeight() + 2 + 4);
    }
    else
    {
      this.m_start.setVisible(false);
    }
    int i9 = localFontMetrics.getHeight();
    if ((i8 != 0) && (d3 >= 0.0D) && (d4 >= 0.0D))
    {
      localObject1 = this.m_pLAudioJitterSrc == null ? new Point(i / 6, j / 2) : this.m_pLAudioJitterSrc;
      localObject2 = drawStatusBox(paramGraphics, str2, i / 6, this.m_nLAudioBottom + 5, 0);
      paramGraphics.setColor(Color.blue);
      paramGraphics.drawLine(i1, 70, ((Point)localObject1).x, ((Point)localObject1).y);
      paramGraphics.drawLine(i2, j - 70, ((Rectangle)localObject2).x + ((Rectangle)localObject2).width / 2, ((Rectangle)localObject2).y + ((Rectangle)localObject2).height);
      U.drawDot(paramGraphics, i1, 70, Color.yellow);
      U.drawDot(paramGraphics, i2, j - 70, Color.yellow);
    }
    if ((i8 != 0) && (d1 >= 0.0D) && (d2 >= 0.0D))
    {
      localObject1 = this.m_pVideoJitterSrc == null ? new Point(i / 2, j / 2) : this.m_pVideoJitterSrc;
      localObject2 = drawStatusBox(paramGraphics, str4, i / 2, this.m_nVideoBottom + 5, 0);
      paramGraphics.setColor(Color.blue);
      paramGraphics.drawLine(m, 70, ((Point)localObject1).x, ((Point)localObject1).y);
      paramGraphics.drawLine(n, j - 70, ((Rectangle)localObject2).x + ((Rectangle)localObject2).width / 2, ((Rectangle)localObject2).y + ((Rectangle)localObject2).height);
      U.drawDot(paramGraphics, m, 70, Color.yellow);
      U.drawDot(paramGraphics, n, j - 70, Color.yellow);
    }
    if ((i8 != 0) && (d5 >= 0.0D) && (d6 >= 0.0D))
    {
      localObject1 = this.m_pRAudioJitterSrc == null ? new Point(5 * i / 6, j / 2) : this.m_pRAudioJitterSrc;
      localObject2 = drawStatusBox(paramGraphics, str3, 5 * i / 6, this.m_nRAudioBottom + 5, 0);
      paramGraphics.setColor(Color.blue);
      paramGraphics.drawLine(i3, 70, ((Point)localObject1).x, ((Point)localObject1).y);
      paramGraphics.drawLine(i4, j - 70, ((Rectangle)localObject2).x + ((Rectangle)localObject2).width / 2, ((Rectangle)localObject2).y + ((Rectangle)localObject2).height);
      U.drawDot(paramGraphics, i3, 70, Color.yellow);
      U.drawDot(paramGraphics, i4, j - 70, Color.yellow);
    }
    if (i6 == 0)
      paintCopyrightInfo(paramGraphics, 20, j - 70 - i9 * 3 - 10);
    Object localObject1 = this.m_plugin.getMSSID();
    Object localObject2 = RC(TX("iptv.mssid"));
    if ((localObject1 != null) && (localObject2 != null) && (!TX("").equals(((String)localObject2).trim())))
    {
      boolean bool = "yes".equals(iniGetString("mssidlink"));
      int i11 = localFontMetrics.stringWidth((String)localObject1);
      int i12 = i - localFontMetrics.stringWidth(localObject2 + " ") - i11 - 7;
      int i13 = j - localFontMetrics.getDescent() - 5;
      paramGraphics.setColor(Color.white);
      paramGraphics.fillRect(i12 - 4, i13 - localFontMetrics.getAscent() - 1, i - i12 - 7 + 8, localFontMetrics.getHeight() + 2);
      paramGraphics.setColor(new Color(8421504));
      paramGraphics.drawRect(i12 - 4, i13 - localFontMetrics.getAscent() - 1, i - i12 - 7 + 8, localFontMetrics.getHeight() + 2);
      paramGraphics.drawString((String)localObject2, i12, i13);
      i12 = i - i11 - 9;
      if (bool)
        paramGraphics.setColor(new Color(8421631));
      paramGraphics.drawString((String)localObject1, i12, i13);
      if (bool)
      {
        paramGraphics.drawLine(i12, i13 + 1, i12 + i11, i13 + 1);
        addHitRegion("mssid", new Rectangle(i12, i13 - localFontMetrics.getAscent(), i11, localFontMetrics.getHeight()));
      }
    }
  }

  private Rectangle drawStatusBox(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int i = localFontMetrics.stringWidth(paramString) + paramInt3;
    Rectangle localRectangle = new Rectangle(paramInt1 - i / 2 - 2, paramInt2, i + 4, localFontMetrics.getHeight() + 2);
    paramGraphics.setColor(Color.white);
    paramGraphics.fillRect(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawRect(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
    paramGraphics.drawString(paramString, paramInt1 - i / 2 + 1, localFontMetrics.getHeight() + paramInt2);
    paramGraphics.setColor(new Color(190, 190, 145).darker());
    return localRectangle;
  }

  public void run()
  {
    try
    {
      U.sleep(200L);
      int i = "no".equals(RC(TX("loadanimation"))) ? 0 : 1;
      while (this.m_bRunAnimation)
      {
        IptvTest localIptvTest = (IptvTest)this.m_plugin.getTest();
        if (localIptvTest != null)
          break;
        this.m_jitter = xToJitter(jitterToX(this.m_jitter) + this.m_aniJMult * 5);
        if ((this.m_jitter < 0.0F) || (this.m_jitter > this.MAXJITTER))
          this.m_aniJMult *= -1;
        this.m_jitter = (i != 0 ? Math.max(0.0F, Math.min(this.MAXJITTER, this.m_jitter)) : -1.0F);
        this.m_loss = xToLoss(lossToX(this.m_loss) + this.m_aniLMult * 5);
        if ((this.m_loss < 0.0F) || (this.m_loss > 100.0F))
          this.m_aniLMult *= -1;
        this.m_loss = (i != 0 ? Math.max(0.0F, Math.min(100.0F, this.m_loss)) : -1.0F);
        repaint();
        U.sleep(50L);
      }
    }
    finally
    {
      this.m_ani = null;
    }
  }

  public void setStartButtonVisible(boolean paramBoolean)
  {
    this.m_bShowStartButton = paramBoolean;
    repaint();
  }

  public void stopAnimation()
  {
    if (this.m_ani != null)
      this.m_bRunAnimation = false;
    long l = System.currentTimeMillis() + 500L;
    while ((this.m_ani != null) && (System.currentTimeMillis() < l))
      U.sleep(50L);
    if (this.m_ani != null)
      this.m_ani = null;
  }

  private float xToJitter(int paramInt)
  {
    int i = Math.max(1, getSize().width);
    return (float)(Math.pow(this.MAXJITTER + 1, paramInt / i) - 1.0D);
  }

  private float xToLoss(int paramInt)
  {
    int i = Math.max(1, getSize().width);
    return (float)(0.04166666666666666D * Math.pow(2401.0D, paramInt / i) - 0.04166666666666666D);
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

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.iptv.IptvTab
 * JD-Core Version:    0.6.2
 */