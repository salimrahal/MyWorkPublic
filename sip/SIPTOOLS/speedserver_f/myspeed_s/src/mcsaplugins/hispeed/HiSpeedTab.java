package mcsaplugins.hispeed;

import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.G;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class HiSpeedTab extends AppletTab
  implements Runnable, ActionListener
{
  private static final int ALIGN_V_TOP = 1;
  private static final int ALIGN_V_MIDDLE = 2;
  private static final int ALIGN_V_BOTTOM = 4;
  private static final int ALIGN_H_LEFT = 16;
  private static final int ALIGN_H_CENTRE = 32;
  private static final int ALIGN_H_RIGHT = 64;
  private static final int UNITS_KBPS = 0;
  private static final int UNITS_MBPS = 1;
  private static final int UNITS_GBPS = 2;
  private static final int STATE_NORMAL = 0;
  private static final int STATE_MOUSEOVER = 1;
  private static final int STATE_MOUSEDOWN = 2;
  private boolean m_bHadPreviousTest = false;
  private BufferedImage m_back;
  private int m_top = 0;
  private long LOBPS = 10000L;
  private long[] m_bpsc;
  private long HIBPS = 1000000000L;
  private Vector m_labels = new Vector();
  private JButton m_start = new JButton();
  private boolean m_bClassic;
  private String m_explain;
  private Point m_explainLoc;
  private Image m_dialViewBack;
  private Image m_dialViewPieces;
  private double[] m_nMainPegs;
  private double[] m_nMiniPegs;
  private HiSpeedTab.DialMetrics m_dialMetrics;
  private Thread m_downloadDialView;
  private int m_nStartState;
  private int m_nInfoLinkState;
  private Thread m_ani;
  private boolean m_bRunAnimation = true;
  private long m_aniSpeed;
  private Applet m_applet;
  private hispeed m_plugin;
  private Point m_down;
  private static boolean g_bTestAni = false;

  public HiSpeedTab(Applet paramApplet, hispeed paramhispeed)
  {
    super(paramApplet, paramhispeed, paramhispeed.getImage("tabhispeed.gif"), "hispeed");
    this.m_applet = paramApplet;
    this.m_plugin = paramhispeed;
    setLayout(null);
    add(this.m_start);
    this.m_start.setText(RC(TX("start")));
    this.m_start.addActionListener(this);
    this.m_nStartState = 0;
    this.m_nInfoLinkState = 0;
  }

  public void doFirstTimeInit()
  {
    this.m_bpsc = new long[] { 10000L, 16744576L, 28000L, 16761024L, 128000L, 16777152L, 3000000L, 12648384L, 1000000000L, 65408L };
    initLoHiBps();
    initBpsColourTable();
    String str = iniGetString("hispeed.label");
    StringTokenizer localStringTokenizer = new StringTokenizer(str == null ? "" : str, TX("\r\n"));
    while (localStringTokenizer.hasMoreTokens())
      this.m_labels.addElement(localStringTokenizer.nextToken());
    this.m_bClassic = (!"yes".equals(iniGetString("hispeed.dialview")));
    this.m_downloadDialView = new Thread(this, TX("MCS-HiSpeed-DialViewBackgroundDownload"));
    this.m_downloadDialView.start();
    if (this.m_bClassic)
    {
      this.m_ani = new Thread(this, TX("MCS-HiSpeed-Animation"));
      this.m_ani.start();
    }
    else
    {
      setFadeCopyrightInfo(true);
    }
  }

  public void reset()
  {
    this.m_plugin.reset();
    this.m_top = 0;
    repaint();
  }

  private void initBpsColourTable()
  {
    long[] arrayOfLong = U.parseColourTable(iniGetString(TX("hispeed.bps2c")));
    this.m_bpsc = (arrayOfLong == null ? this.m_bpsc : arrayOfLong);
  }

  private void initLoHiBps()
  {
    long l1 = iniGetLong(TX("hispeed.lobps"), -1L);
    if (l1 > 0L)
      this.LOBPS = l1;
    long l2 = iniGetLong(TX("hispeed.hibps"), -1L);
    if (l2 > this.LOBPS * 2L)
      this.HIBPS = l2;
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    Graphics2D localGraphics2D1 = (Graphics2D)paramGraphics;
    Object localObject1;
    Object localObject2;
    int m;
    if ((!this.m_bClassic) && (this.m_downloadDialView != null))
    {
      localGraphics2D1.drawString("Loading...", i / 2, j / 2);
    }
    else
    {
      localObject1 = new Point(0, 0);
      localObject2 = this.m_back;
      HiSpeedTest localHiSpeedTest = this.m_plugin.getHiSpeedTest();
      m = localHiSpeedTest == null ? -1 : localHiSpeedTest.getState();
      if ((this.m_bClassic) || (this.m_dialMetrics == null) || (!this.m_dialMetrics.CUSTOMSTART))
        if (m > -1)
        {
          this.m_start.setVisible(false);
          this.m_top = 3;
        }
        else
        {
          this.m_start.setVisible(true);
          this.m_start.setSize(this.m_start.getPreferredSize());
          this.m_start.setLocation(3, 3);
          this.m_top = (this.m_start.getSize().height + 3);
        }
      if (m > -1)
        this.m_bHadPreviousTest = true;
      if ((localObject2 == null) || (i != ((BufferedImage)localObject2).getWidth(null)) || (j != ((BufferedImage)localObject2).getHeight(null)))
      {
        localObject2 = localGraphics2D1.getDeviceConfiguration().createCompatibleImage(i, j, 3);
        this.m_back = ((BufferedImage)localObject2);
        localObject3 = ((BufferedImage)localObject2).createGraphics();
        ((Graphics2D)localObject3).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D)localObject3).setFont(getFont());
        drawBackground((Graphics2D)localObject3, new Dimension(i, j));
        ((Graphics2D)localObject3).dispose();
      }
      localGraphics2D1.drawImage((Image)localObject2, 0, 0, null);
      Object localObject3 = localGraphics2D1.getFontMetrics();
      int i20;
      Object localObject6;
      Object localObject4;
      if (this.m_bClassic)
      {
        int n = TX("no").equals(iniGetString(TX("hispeed.allowbitsbytesclick"))) ? 0 : 1;
        int i1 = (this.m_ani != null) && (n != 0) ? 1 : 0;
        String str1 = (i1 != 0) && ((U.time() / 750L & 1L) == 0L) ? RC(TX("hispeed.click")) + TX(" ") : TX("");
        String str2 = str1 + (G.g_bBytes ? RC(TX("hispeed.kbyteps")) : RC(TX("hispeed.kbitps")));
        int i4 = ((FontMetrics)localObject3).stringWidth(str2);
        localGraphics2D1.setColor(Color.gray);
        int i6 = i - 1 - 2 - i4;
        int i8 = j - 1 - 2;
        localGraphics2D1.drawString(str2, i6, i8);
        if (n != 0)
          addHitRegion("units", new Rectangle(i6, i8 - ((FontMetrics)localObject3).getAscent(), i4, ((FontMetrics)localObject3).getHeight()));
        n = this.m_top;
        i1 = ((FontMetrics)localObject3).getHeight();
        int i2 = i1 + ((FontMetrics)localObject3).getDescent();
        int i3 = 5;
        long l3;
        int i17;
        if (((localHiSpeedTest != null) && (localHiSpeedTest.getState() > -1)) || (this.m_ani != null))
        {
          l3 = localHiSpeedTest == null ? this.m_aniSpeed : this.m_ani == null ? 0L : localHiSpeedTest.getDownloadSpeed();
          long l4 = localHiSpeedTest == null ? 0L : localHiSpeedTest.getBandwidth();
          String str5 = m > 3 ? RC(TX("???")) : l3 > 0L ? U.bps2s(l3 * 8L) : RC(TX("working"));
          String str6 = (l4 > 0L) && (l4 > l3) ? RC(TX("hispeed.bwstatus"), U.bps2s(l4 * 8L, false)) : TX("");
          if (this.m_ani != null)
            str5 = l3 > 0L ? RC(TX("hispeed.isdownloadspeed"), new String[] { str5 }) : "";
          else if (m > 3)
            str5 = RC(TX("hispeed.bwdownloadspeed"), new String[] { str5, str6 });
          int i15 = ((FontMetrics)localObject3).stringWidth(str5);
          if (str5.length() > 0)
          {
            localGraphics2D1.setColor(Color.white);
            localGraphics2D1.fillRect(i3, n + i3, i15 + 2 + 2, i2);
            localGraphics2D1.setColor(Color.black);
            localGraphics2D1.drawRect(i3, n + i3, i15 + 2 + 2, i2);
            localGraphics2D1.drawString(str5, i3 + 2, n + i3 + i1);
          }
          i17 = localHiSpeedTest == null ? -1 : localHiSpeedTest.getPercentDone();
          if ((m == 3) && (i17 >= 0) && (i17 <= 100))
            drawPercentDoneBar(localGraphics2D1, i3 + i15 + 3 * i3, n + i3 + i2 / 2, i17, false);
          if (this.m_ani != null)
            paintCopyrightInfo(localGraphics2D1, i3, n + i3 + i2 + i3 + i1);
          if (l3 > 0L)
          {
            localGraphics2D1.setColor(Color.blue);
            double d4 = bps2per(l3 * 8L);
            i20 = (int)(i * d4);
            int i21 = j - (int)(j * d4);
            localGraphics2D1.drawLine(i3 + i15 + 2 + 2, n + i3 + i2, i20, i21);
            drawLabel(localGraphics2D1, 0, l3 * 8L, null, null, null, Color.yellow);
          }
          if (l3 > 0L)
          {
            String[] arrayOfString = { U.bps2s(l3 * 8L), howfast(1, l3), howfast(5, l3), howfast(10, l3), howfast(50, l3), howfast(100, l3), howfast(500, l3), howfast(1000, l3) };
            String str9 = RC(TX("hispeed.downloadbox"), arrayOfString);
            doDrawString(localGraphics2D1, str9, i * 66 / 100, j * 80 / 100, Color.white);
          }
          n += i2 + i3;
        }
        String str4;
        if ((m >= 5) && (localHiSpeedTest.isRunUpload()))
        {
          l3 = localHiSpeedTest.getUploadSpeed();
          str4 = m > 5 ? RC(TX("???")) : l3 > 0L ? U.bps2s(l3 * 8L) : RC(TX("working"));
          if (m > 5)
            str4 = RC(TX("hispeed.uploadspeed"), new String[] { str4 });
          int i10 = ((FontMetrics)localObject3).stringWidth(str4);
          localGraphics2D1.setColor(Color.white);
          localGraphics2D1.fillRect(i3, n + i3, i10 + 2 + 2, i2);
          localGraphics2D1.setColor(Color.black);
          localGraphics2D1.drawRect(i3, n + i3, i10 + 2 + 2, i2);
          localGraphics2D1.drawString(str4, i3 + 2, n + ((FontMetrics)localObject3).getHeight() + i3);
          int i13 = localHiSpeedTest.getPercentDone();
          if ((m == 5) && (i13 >= 0) && (i13 <= 100))
            drawPercentDoneBar(localGraphics2D1, i3 + i10 + 3 * i3, n + i3 + i2 / 2, i13, false);
          if (l3 > 0L)
          {
            localGraphics2D1.setColor(Color.blue);
            double d1 = bps2per(l3 * 8L);
            i17 = (int)(i * d1);
            int i18 = j - (int)(j * d1);
            localGraphics2D1.drawLine(i3 + i10 + 2 + 2, n + i3 + i2, i17, i18);
            drawLabel(localGraphics2D1, 0, l3 * 8L, null, null, null, Color.yellow);
          }
          n += i2 + i3;
        }
        if (m >= 7)
        {
          n += i1;
          localGraphics2D1.setColor(new Color(128, 128, 128));
          String str3 = iniGetString(TX("hispeed.showextra81d"));
          str3 = str3 == null ? RC(TX("hispeed.showextra74")) : str3;
          StringTokenizer localStringTokenizer = new StringTokenizer(str3, TX(",\r\n "));
          while (localStringTokenizer.hasMoreTokens())
          {
            str4 = localStringTokenizer.nextToken();
            String str7;
            if (str4.equals(TX("upburst")))
            {
              long l5 = localHiSpeedTest.getUploadBurst();
              if (l5 > 0L)
              {
                n += i1;
                str7 = RC(TX("hispeed.") + str4, new String[] { U.bps2s(l5 * 8L) });
                localGraphics2D1.drawString(str7, i3, n);
              }
            }
            else
            {
              int i11;
              if (str4.equals(TX("qos")))
              {
                i11 = localHiSpeedTest.getDownloadQOS();
                if (i11 >= 0)
                {
                  n += i1;
                  localObject6 = U.mapValueToLabel(iniGetString(TX("hispeed.") + str4 + TX(".labels")), i11);
                  str7 = RC(TX("hispeed.") + str4, new String[] { i11, localObject6 });
                  localGraphics2D1.drawString(str7, i3, n);
                }
              }
              else if (str4.equals(TX("uqos")))
              {
                i11 = localHiSpeedTest.getUploadQOS();
                if (i11 >= 0)
                {
                  n += i1;
                  localObject6 = U.mapValueToLabel(iniGetString(TX("hispeed.") + str4 + TX(".labels")), i11);
                  str7 = RC(TX("hispeed.") + str4, new String[] { i11, localObject6 });
                  localGraphics2D1.drawString(str7, i3, n);
                }
              }
              else if (str4.equals(TX("sockets")))
              {
                i11 = localHiSpeedTest.getDownloadSockets();
                if (i11 > 0)
                {
                  n += i1;
                  localObject6 = RC(TX("hispeed.") + str4, i11);
                  localGraphics2D1.drawString((String)localObject6, i3, n);
                }
              }
              else if (str4.equals(TX("timerres")))
              {
                i11 = U.getTickResolution();
                if (i11 > 0)
                {
                  n += i1;
                  localObject6 = RC(TX("hispeed.") + str4, i11);
                  localGraphics2D1.drawString((String)localObject6, i3, n);
                }
              }
              else if (str4.equals(TX("rtt")))
              {
                i11 = localHiSpeedTest.getRTT();
                if (i11 > 0)
                {
                  n += i1;
                  localObject6 = U.mapValueToLabel(iniGetString(TX("hispeed.") + str4 + TX(".labels")), i11);
                  str7 = RC(TX("hispeed.") + str4, new String[] { i11, localObject6 });
                  localGraphics2D1.drawString(str7, i3, n);
                }
              }
              else if ((str4.equals(TX("maxattainable"))) || (str4.equals(TX("routespeed"))))
              {
                long l6 = localHiSpeedTest.getTCPMaxRouteSpeed() * 8L;
                if (l6 > 0L)
                {
                  n += i1;
                  str7 = RC(TX("hispeed.routespeed"), new String[] { l6 > 0L ? U.bps2s(l6) : RC(TX("???")) });
                  localGraphics2D1.drawString(str7, i3, n);
                }
              }
              else if (str4.equals(TX("routeconcurrency")))
              {
                float f = localHiSpeedTest.getDownloadRouteConcurrency();
                if (f > 1.0F)
                {
                  n += i1;
                  localObject6 = RC(TX("hispeed.") + str4, new String[] { (int)(f * 10.0F) / 10.0F });
                  localGraphics2D1.drawString((String)localObject6, i3, n);
                }
              }
              else
              {
                int i12;
                if (str4.equals(TX("forcedidle")))
                {
                  i12 = localHiSpeedTest.getDownloadForcedIdle();
                  if (i12 >= 0)
                  {
                    n += i1;
                    localObject6 = RC(TX("hispeed.") + str4, new String[] { i12 });
                    localGraphics2D1.drawString((String)localObject6, i3, n);
                  }
                }
                else if (str4.equals(TX("maxpause")))
                {
                  i12 = localHiSpeedTest.getDownloadMaxPause();
                  if (i12 > 0)
                  {
                    n += i1;
                    localObject6 = U.mapValueToLabel(iniGetString(TX("hispeed.") + str4 + TX(".labels")), i12);
                    str7 = RC(TX("hispeed.") + str4, new String[] { i12, localObject6 });
                    localGraphics2D1.drawString(str7, i3, n);
                  }
                }
                else if (str4.equals(TX("ip")))
                {
                  localObject4 = G.g_trueIp;
                  if (localObject4 != null)
                  {
                    n += i1;
                    localObject6 = RC(TX("hispeed.") + str4, new String[] { localObject4 });
                    localGraphics2D1.drawString((String)localObject6, i3, n);
                    addHitRegion("trueip", new Rectangle(i3 + ((Point)localObject1).x, n - i1 + ((Point)localObject1).y, ((FontMetrics)localObject3).stringWidth((String)localObject6), i1));
                  }
                }
                else if (str4.equals(TX("mssid")))
                {
                  localObject4 = this.m_plugin.getMSSID();
                  if (localObject4 != null)
                  {
                    localObject6 = localGraphics2D1.getColor();
                    boolean bool2 = "yes".equals(iniGetString("mssidlink"));
                    n += i1;
                    String str8 = RC(str4, new String[] { localObject4 });
                    if (bool2)
                      localGraphics2D1.setColor(new Color(8421631));
                    localGraphics2D1.drawString(str8, i3, n);
                    if (bool2)
                      localGraphics2D1.drawLine(i3, n + 2, i3 + ((FontMetrics)localObject3).stringWidth(str8), n + 2);
                    localGraphics2D1.setColor((Color)localObject6);
                    if (bool2)
                      addHitRegion("mssid", new Rectangle(i3 + ((Point)localObject1).x, n - i1 + ((Point)localObject1).y, ((FontMetrics)localObject3).stringWidth(str8), i1));
                  }
                }
                else if (str4.equals(TX("space")))
                {
                  n += i1 / 2;
                }
              }
            }
          }
          str4 = iniGetProfessionalString(TX("submit.text"));
          localObject4 = iniGetProfessionalString(TX("submit.url"));
          if ((str4 != null) && (localObject4 != null))
          {
            localObject6 = localGraphics2D1.getColor();
            Object localObject7 = localObject6;
            try
            {
              localObject7 = new Color(Integer.parseInt(iniGetProfessionalString(TX("submit.color")), 16));
            }
            catch (Exception localException)
            {
            }
            localGraphics2D1.setColor((Color)localObject7);
            n += i1;
            localGraphics2D1.drawString(str4, i3, n);
            int i16 = ((FontMetrics)localObject3).stringWidth(str4);
            addHitRegion("submit", new Rectangle(i3 + ((Point)localObject1).x, n - i1 + ((Point)localObject1).y, i16, i1 + 2));
            localGraphics2D1.drawLine(i3, n + 1, i3 + i16, n + 1);
            localGraphics2D1.setColor((Color)localObject6);
          }
        }
      }
      else
      {
        long l1 = localHiSpeedTest == null ? this.m_aniSpeed : localHiSpeedTest.getDownloadSpeed() * 8L;
        long l2 = localHiSpeedTest == null ? this.m_aniSpeed : localHiSpeedTest.getUploadSpeed() * 8L;
        int i5 = localHiSpeedTest == null ? 0 : localHiSpeedTest.getRTT();
        int i7 = localHiSpeedTest == null ? -1 : localHiSpeedTest.getPercentDone();
        if (this.m_dialMetrics != null)
        {
          if ((this.m_dialMetrics.DOWNLOADOVERLAY != null) && ((m == 2) || (m == 3) || (m == 4)))
            drawImageArea(localGraphics2D1, this.m_dialViewPieces, this.m_dialMetrics.DOWNLOADOVERLAY, this.m_dialViewBack.getWidth(null) / 2 - this.m_dialMetrics.DOWNLOADOVERLAY.width / 2, this.m_dialViewBack.getHeight(null) / 2 - this.m_dialMetrics.DOWNLOADOVERLAY.height / 2);
          else if ((this.m_dialMetrics.UPLOADOVERLAY != null) && ((m == 5) || (m == 6)))
            drawImageArea(localGraphics2D1, this.m_dialViewPieces, this.m_dialMetrics.UPLOADOVERLAY, this.m_dialViewBack.getWidth(null) / 2 - this.m_dialMetrics.UPLOADOVERLAY.width / 2, this.m_dialViewBack.getHeight(null) / 2 - this.m_dialMetrics.UPLOADOVERLAY.height / 2);
          else if ((this.m_dialMetrics.DONEOVERLAY != null) && ((m == 7) || (m == 9)))
            drawImageArea(localGraphics2D1, this.m_dialViewPieces, this.m_dialMetrics.DONEOVERLAY, this.m_dialViewBack.getWidth(null) / 2 - this.m_dialMetrics.DONEOVERLAY.width / 2, this.m_dialViewBack.getHeight(null) / 2 - this.m_dialMetrics.DONEOVERLAY.height / 2);
          if ((m > 5) || ((this.m_dialMetrics.SHOWUPLOADREADOUTDURING) && (l2 > 0L)))
          {
            drawNumber(localGraphics2D1, this.m_dialMetrics.LCDROWS[0], this.m_dialMetrics.LCDDIGITS, this.m_dialMetrics.LCDROWALIGN[0], (float)(l2 / 10000L) / 100.0F, false, 3);
            i9 = determineSI(l2);
            drawNumber(localGraphics2D1, this.m_dialMetrics.UPLOADREADOUT, this.m_dialMetrics.UPLOADREADOUTDIGITS, this.m_dialMetrics.UPLOADREADOUTALIGN, divideSI(l2, i9), false, 3);
            if ((i9 >= 0) && (this.m_dialMetrics.UPLOADREADOUTSI != null) && (i9 < this.m_dialMetrics.UPLOADREADOUTSI.length) && (this.m_dialMetrics.UPLOADREADOUTSI[i9] != null))
              drawImageArea(localGraphics2D1, this.m_dialViewPieces, this.m_dialMetrics.UPLOADREADOUTSI[i9], this.m_dialMetrics.UPLOADREADOUTUNITS.x, this.m_dialMetrics.UPLOADREADOUTUNITS.y);
          }
          if ((m > 3) || ((this.m_dialMetrics.SHOWDOWNLOADREADOUTDURING) && (l1 > 0L)))
          {
            drawNumber(localGraphics2D1, this.m_dialMetrics.LCDROWS[1], this.m_dialMetrics.LCDDIGITS, this.m_dialMetrics.LCDROWALIGN[1], (float)(l1 / 10000L) / 100.0F, false, 3);
            i9 = determineSI(l1);
            drawNumber(localGraphics2D1, this.m_dialMetrics.DOWNLOADREADOUT, this.m_dialMetrics.DOWNLOADREADOUTDIGITS, this.m_dialMetrics.DOWNLOADREADOUTALIGN, divideSI(l1, i9), false, 3);
            if ((i9 >= 0) && (this.m_dialMetrics.DOWNLOADREADOUTSI != null) && (i9 < this.m_dialMetrics.DOWNLOADREADOUTSI.length) && (this.m_dialMetrics.DOWNLOADREADOUTSI[i9] != null))
              drawImageArea(localGraphics2D1, this.m_dialViewPieces, this.m_dialMetrics.DOWNLOADREADOUTSI[i9], this.m_dialMetrics.DOWNLOADREADOUTUNITS.x, this.m_dialMetrics.DOWNLOADREADOUTUNITS.y);
          }
          if (m > 3)
            drawNumber(localGraphics2D1, this.m_dialMetrics.LCDROWS[2], this.m_dialMetrics.LCDDIGITS, this.m_dialMetrics.LCDROWALIGN[2], (float)(localHiSpeedTest.getBandwidth() * 8L / 10000L) / 100.0F, false, 3);
          if ((m >= 7) && (i5 >= 0))
            drawNumber(localGraphics2D1, this.m_dialMetrics.LCDROWS[3], this.m_dialMetrics.LCDDIGITS, this.m_dialMetrics.LCDROWALIGN[3], i5, false);
          if (!isShowingMessageOnGlass())
          {
            drawNeedle(localGraphics2D1, true, this.m_dialMetrics.MAINDIALNEEDLEPOS.x, this.m_dialMetrics.MAINDIALNEEDLEPOS.y, getNeedleRot(l1, getPegNumbers(true), this.m_dialMetrics.MAINDIALPEGDEGS));
            drawNeedle(localGraphics2D1, false, this.m_dialMetrics.MINIDIALNEEDLEPOS.x, this.m_dialMetrics.MINIDIALNEEDLEPOS.y, getNeedleRot(l2, getPegNumbers(false), this.m_dialMetrics.MINIDIALPEGDEGS));
          }
          int i9 = (localHiSpeedTest != null) && (m >= 7) ? localHiSpeedTest.getDownloadQOS() : -1;
          if (i9 >= 0)
            drawQOS(localGraphics2D1, i9);
          if (((m <= -1) || ((m >= 9) && (this.m_dialMetrics.SHOWRESTART))) && (this.m_dialMetrics.CUSTOMSTART))
          {
            localObject4 = this.m_nStartState == 1 ? this.m_dialMetrics.STARTBUTTONMO : this.m_nStartState == 2 ? this.m_dialMetrics.STARTBUTTONMD : this.m_dialMetrics.STARTBUTTONDEFAULT;
            if ((this.m_bHadPreviousTest) && (this.m_dialMetrics.SHOWRESTART))
            {
              localObject6 = this.m_nStartState == 1 ? this.m_dialMetrics.RESTARTBUTTONMO : this.m_nStartState == 2 ? this.m_dialMetrics.RESTARTBUTTONMD : this.m_dialMetrics.RESTARTBUTTONDEFAULT;
              if (localObject6 != null)
                localObject4 = localObject6;
            }
            if (localObject4 != null)
              drawImageArea(localGraphics2D1, this.m_dialViewPieces, (Rectangle)localObject4, this.m_dialMetrics.STARTBUTTON.x, this.m_dialMetrics.STARTBUTTON.y);
            localObject6 = (this.m_dialMetrics.STARTACTIVEAREA == null) || (this.m_dialMetrics.STARTACTIVEAREA.equals(new Rectangle(0, 0, 0, 0))) ? localObject4 : this.m_dialMetrics.STARTACTIVEAREA;
            if (localObject6 != null)
              addHitRegion("start", (Rectangle)localObject6);
          }
          if (((m == 5) || (m == 3)) && (i7 >= 0) && (i7 <= 100))
          {
            boolean bool1 = ((m == 5) && (this.m_dialMetrics.PROGRESSREVERSEONUPLOAD)) || ((m == 3) && (this.m_dialMetrics.PROGRESSREVERSEONDOWNLOAD));
            drawPercentDoneBar(localGraphics2D1, 3, 7, i7, bool1);
          }
          else if ((m >= 5) && (this.m_dialMetrics.KEEPCOMPLETEDPROGRESS))
          {
            drawPercentDoneBar(localGraphics2D1, 3, 7, 100, this.m_dialMetrics.PROGRESSREVERSEONUPLOAD);
          }
          if ((m >= 5) && (this.m_dialMetrics.UPLOADMESSAGE != null) && (this.m_dialMetrics.UPLOADMESSAGEPOS != null))
            drawImageArea(localGraphics2D1, this.m_dialViewPieces, this.m_dialMetrics.UPLOADMESSAGE, this.m_dialMetrics.UPLOADMESSAGEPOS.x, this.m_dialMetrics.UPLOADMESSAGEPOS.y);
          long l7;
          if ((this.m_dialMetrics.CURRENTSPEEDPOS != null) && (m >= 2) && (m <= 6))
          {
            l7 = m >= 5 ? l2 : l1;
            if (l7 > 0L)
            {
              int i14 = determineSI(l7);
              drawNumber(localGraphics2D1, this.m_dialMetrics.CURRENTSPEEDPOS, this.m_dialMetrics.CURRENTSPEEDDIGITS, this.m_dialMetrics.CURRENTSPEEDALIGN, divideSI(l7, i14), false, 3);
              if ((i14 >= 0) && (this.m_dialMetrics.CURRENTSPEEDSI != null) && (i14 < this.m_dialMetrics.CURRENTSPEEDSI.length) && (this.m_dialMetrics.CURRENTSPEEDSI[i14] != null))
              {
                Point localPoint = (m >= 5) && (this.m_dialMetrics.CURRENTUPSPEEDUNITSPOS != null) ? this.m_dialMetrics.CURRENTUPSPEEDUNITSPOS : this.m_dialMetrics.CURRENTSPEEDUNITSPOS;
                drawImageArea(localGraphics2D1, this.m_dialViewPieces, this.m_dialMetrics.CURRENTSPEEDSI[i14], localPoint.x, localPoint.y);
              }
            }
          }
          if ((this.m_dialMetrics.CUSTOMTRACKDIAL != null) && (this.m_dialMetrics.CUSTOMTRACKDIAL.width > 0) && (this.m_dialMetrics.CUSTOMTRACKDIAL.height > 0) && (((m == -1) && (g_bTestAni)) || (m == 3) || (m == 4) || (m == 5) || (m == 6)))
          {
            l7 = m >= 5 ? l2 : l1;
            if (m == -1)
              l7 = this.m_aniSpeed * 8L;
            double d2 = 3.864158963915445D;
            double d3 = -0.9054070027645784D;
            int i19 = 128;
            i20 = 113;
            for (double d5 = 3.864158963915445D + -4.769565966680024D * (log10(Math.max(1L, Math.min(l7, 1000000000L))) / log10(1000000000.0D)); d5 < 0.0D; d5 += 6.283185307179586D);
            while (d5 > 6.283185307179586D)
              d5 -= 6.283185307179586D;
            BufferedImage localBufferedImage1 = new BufferedImage(this.m_dialMetrics.CUSTOMTRACKDIAL.width, this.m_dialMetrics.CUSTOMTRACKDIAL.height, 2);
            Graphics2D localGraphics2D2 = (Graphics2D)localBufferedImage1.getGraphics();
            drawImageArea(localGraphics2D2, this.m_dialViewPieces, this.m_dialMetrics.CUSTOMTRACKDIAL, 0, 0);
            localGraphics2D2.setComposite(AlphaComposite.getInstance(1));
            localGraphics2D2.setColor(new Color(0, 0, 0, 0));
            Polygon localPolygon;
            if ((d5 > 0.0D) && (d5 < 1.570796326794897D))
            {
              localGraphics2D2.fillRect(128, 113, this.m_dialMetrics.CUSTOMTRACKDIAL.width - 128, this.m_dialMetrics.CUSTOMTRACKDIAL.height - 113);
              localPolygon = new Polygon(new int[] { this.m_dialMetrics.CUSTOMTRACKDIAL.width, this.m_dialMetrics.CUSTOMTRACKDIAL.width, 128 }, new int[] { -(int)((this.m_dialMetrics.CUSTOMTRACKDIAL.width - 128) * Math.tan(d5) - 113.0D), 113, 113 }, 3);
              localGraphics2D2.fillPolygon(localPolygon);
            }
            else if (d5 == 1.570796326794897D)
            {
              localGraphics2D2.fillRect(128, 0, this.m_dialMetrics.CUSTOMTRACKDIAL.width - 128, this.m_dialMetrics.CUSTOMTRACKDIAL.height);
            }
            else if ((d5 > 1.570796326794897D) && (d5 < 3.141592653589793D))
            {
              localGraphics2D2.fillRect(128, 0, this.m_dialMetrics.CUSTOMTRACKDIAL.width - 128, this.m_dialMetrics.CUSTOMTRACKDIAL.height);
              localPolygon = new Polygon(new int[] { 128, 128, (int)(128.0D - 113.0D * Math.tan(d5 - 1.570796326794897D)) }, new int[] { 113 }, 3);
              localGraphics2D2.fillPolygon(localPolygon);
            }
            else if ((d5 > 3.141592653589793D) && (d5 < 4.71238898038469D))
            {
              localGraphics2D2.fillRect(128, 0, this.m_dialMetrics.CUSTOMTRACKDIAL.width - 128, this.m_dialMetrics.CUSTOMTRACKDIAL.height);
              localGraphics2D2.fillRect(0, 0, 128, 113);
              localPolygon = new Polygon(new int[] { 128 }, new int[] { 113, 113, (int)(128.0D * Math.tan(d5)) + 113 }, 3);
              localGraphics2D2.fillPolygon(localPolygon);
            }
            else if ((d5 > 4.71238898038469D) && (d5 < 6.283185307179586D))
            {
              localPolygon = new Polygon(new int[] { 128, 128, (int)((113 - this.m_dialMetrics.CUSTOMTRACKDIAL.height) / Math.tan(d5) + 128.0D) }, new int[] { 113, this.m_dialMetrics.CUSTOMTRACKDIAL.height, this.m_dialMetrics.CUSTOMTRACKDIAL.height }, 3);
              localGraphics2D2.fillPolygon(localPolygon);
            }
            int i22 = 20;
            BufferedImage localBufferedImage2 = new BufferedImage(i22 + 2, i22, 2);
            Graphics2D localGraphics2D3 = (Graphics2D)localBufferedImage2.getGraphics();
            localGraphics2D3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            localGraphics2D3.setColor(new Color(-1, true));
            localGraphics2D3.drawLine(0, 0, 0, i22);
            localGraphics2D3.drawLine(i22 + 1, 0, i22 + 1, i22);
            localGraphics2D3.setColor(new Color(-1776153, true));
            localGraphics2D3.fillRect(1, 0, i22, i22);
            localGraphics2D3.setComposite(AlphaComposite.getInstance(1));
            localGraphics2D3.setColor(new Color(0, true));
            localGraphics2D3.fillOval(-1, -5, i22 + 2, i22 + 2);
            localGraphics2D3.fillRect(0, 0, i22 + 2, i22 / 2);
            localGraphics2D3.setComposite(AlphaComposite.getInstance(3));
            localGraphics2D3.setColor(new Color(-1, true));
            localGraphics2D3.fillOval(i22 / 2 - 4, 0, 8, 8);
            double d6 = 0.0D;
            int i23 = 0;
            double[] arrayOfDouble1 = { 0.1135446048726548D, 0.2600312895821321D, 0.3788313368029068D, 0.5000687432153129D, 0.5956555153657105D, 0.7266423406817256D, 0.9048270894157868D, 1.059973444837074D, 1.190289949682532D, 1.304544277643971D, 1.492525186273619D, 1.687905071361761D, 1.874256238234719D, 1.999825736839942D, 2.200990236339447D, 2.316215803069055D, 2.408777551803287D, 2.507350539458632D, 2.629203193279055D, 2.794351870693682D, 2.920063848973045D, 3.019397482898438D, 3.115736348230533D, 3.226136409102791D, 3.319188820873281D, 3.418753360923382D, 3.468551494225507D, 3.561472610795345D, 3.65264996548784D, 3.749681840965243D, 3.842447061474243D, 3.921404282926585D, 3.982489322232958D, 4.053385237666388D, 5.382281475751634D, 5.508897797679746D, 5.605934959934801D, 5.679662885509591D, 5.769039151267021D, 5.889022722909312D, 6.006024599845997D, 6.130535978784321D, 6.265945635985057D };
            double[] arrayOfDouble2 = { 115.23883387938018D, 113.29184367674819D, 111.86027241532469D, 110.53506231056279D, 109.72442227944924D, 108.37435120913067D, 105.82696288858914D, 104.31682510506155D, 104.31813133555558D, 102.61578825892242D, 102.3132444994293D, 103.20345661174214D, 103.24005976477939D, 103.86827366266692D, 105.70931517693668D, 106.15083607772479D, 107.62899237658968D, 108.00462953040486D, 109.64535850411491D, 111.16467659918243D, 113.28049041905207D, 114.85643212288984D, 116.03878661895772D, 117.4229707446997D, 118.86967653695369D, 121.10265337047937D, 121.43310915891102D, 122.65398485169571D, 124.17436570041843D, 124.77791436936813D, 126.60652849274993D, 127.57408897558773D, 128.47548783981961D, 129.01550294441361D, 128.84486796143648D, 128.28707711311466D, 126.97088012864695D, 125.59996003196804D, 125.03628501370073D, 123.38463955905577D, 121.60265337047937D, 119.37651794169315D, 117.01724009818541D };
            while ((i23 < arrayOfDouble1.length) && (arrayOfDouble1[i23] < d5))
              i23++;
            i23 = i23 >= arrayOfDouble1.length ? 0 : i23;
            double d7 = arrayOfDouble1[(i23 - 1)];
            double d8 = arrayOfDouble2[(i23 - 1)];
            d6 = (arrayOfDouble2[i23] - d8) * (d5 - d7) / (arrayOfDouble1[i23] - d7) + d8;
            int i24 = (int)(d6 * Math.cos(d5)) + 128;
            int i25 = (int)(d6 * -Math.sin(d5)) + 113;
            localGraphics2D2.setComposite(AlphaComposite.getInstance(3));
            localGraphics2D2.transform(AffineTransform.getRotateInstance(-d5, i24, i25));
            localGraphics2D2.drawImage(localBufferedImage2, i24 - i22 / 2, i25 - i22 + 1, null);
            localGraphics2D1.drawImage(localBufferedImage1, this.m_dialMetrics.CUSTOMTRACKDIALPOS.x, this.m_dialMetrics.CUSTOMTRACKDIALPOS.y, this);
          }
          Object localObject5;
          if (i5 > 0)
          {
            localGraphics2D1.setColor(new Color(11843513));
            localObject5 = localGraphics2D1.getFont();
            localGraphics2D1.setFont(new Font("Open Sans", 1, 12));
            if ((this.m_dialMetrics.UPRTTPOS != null) && ((m == 5) || (m == 6)))
              localGraphics2D1.drawString(i5 + " ms", this.m_dialMetrics.UPRTTPOS.x, this.m_dialMetrics.UPRTTPOS.y);
            else if ((this.m_dialMetrics.DOWNRTTPOS != null) && ((m == 2) || (m == 3) || (m == 4)))
              localGraphics2D1.drawString(i5 + " ms", this.m_dialMetrics.DOWNRTTPOS.x, this.m_dialMetrics.DOWNRTTPOS.y);
            localGraphics2D1.setFont((Font)localObject5);
          }
          if ((this.m_dialMetrics.INFOBUTTONDEFAULT != null) && (m >= 9))
          {
            localObject5 = this.m_nInfoLinkState == 1 ? this.m_dialMetrics.INFOBUTTONMO : this.m_nInfoLinkState == 2 ? this.m_dialMetrics.INFOBUTTONMD : this.m_dialMetrics.INFOBUTTONDEFAULT;
            if (localObject5 != null)
            {
              drawImageArea(localGraphics2D1, this.m_dialViewPieces, (Rectangle)localObject5, this.m_dialMetrics.INFOBUTTONPOS.x, this.m_dialMetrics.INFOBUTTONPOS.y);
              addHitRegion("diallink", new Rectangle(this.m_dialMetrics.INFOBUTTONPOS.x, this.m_dialMetrics.INFOBUTTONPOS.y, ((Rectangle)localObject5).width, ((Rectangle)localObject5).height));
            }
          }
        }
        if (m <= -1)
          paintCopyrightInfo(localGraphics2D1, 2, j - ((FontMetrics)localObject3).getHeight() * 3);
      }
    }
    if ((this.m_explain != null) && (this.m_explainLoc != null))
    {
      localObject1 = RC(TX("hispeed.explain.") + this.m_explain);
      localObject2 = drawWrappedLines(null, (String)localObject1, this.m_explainLoc.x, this.m_explainLoc.y, i / 2, localGraphics2D1.getFont());
      localObject2.width += 6;
      localObject2.height += 6;
      localObject2.x -= 3;
      localObject2.y -= 3;
      int k = Math.max(0, Math.min(i - ((Rectangle)localObject2).width - 1, this.m_explainLoc.x - ((Rectangle)localObject2).width / 2));
      m = Math.max(0, Math.min(j - ((Rectangle)localObject2).height, this.m_explainLoc.y - ((Rectangle)localObject2).height - 5));
      localGraphics2D1.setColor(new Color(15663086));
      localGraphics2D1.fillRect(k, m, ((Rectangle)localObject2).width, ((Rectangle)localObject2).height);
      localGraphics2D1.setColor(Color.black);
      localGraphics2D1.drawRect(k, m, ((Rectangle)localObject2).width, ((Rectangle)localObject2).height);
      drawWrappedLines(localGraphics2D1, (String)localObject1, k + 3, m + 3, i / 2, localGraphics2D1.getFont());
    }
    doOverlayMessages(localGraphics2D1);
  }

  private static int getNeedleRot(long paramLong, double[] paramArrayOfDouble, int[] paramArrayOfInt)
  {
    if (paramArrayOfDouble.length == 0)
      return 0;
    if (paramArrayOfDouble.length != paramArrayOfInt.length)
      System.out.println("ERROR: pegdegs=" + paramArrayOfInt.length + ", pegnums=" + paramArrayOfDouble.length);
    if (paramLong >= paramArrayOfDouble[(paramArrayOfDouble.length - 1)] * 1000000.0D)
      return paramArrayOfInt[(paramArrayOfDouble.length - 1)];
    for (int i = 0; (i + 1 < paramArrayOfDouble.length) && (paramLong > paramArrayOfDouble[(i + 1)] * 1000000.0D); i++);
    if (i >= paramArrayOfDouble.length - 1)
      return paramArrayOfInt[(paramArrayOfDouble.length - 1)];
    return (int)((paramLong - paramArrayOfDouble[i] * 1000000.0D) * (paramArrayOfInt[(i + 1)] - paramArrayOfInt[i]) / (paramArrayOfDouble[(i + 1)] * 1000000.0D - paramArrayOfDouble[i] * 1000000.0D)) + paramArrayOfInt[i];
  }

  private void drawNeedle(Graphics2D paramGraphics2D, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.m_dialMetrics != null)
    {
      Rectangle localRectangle1 = paramBoolean ? this.m_dialMetrics.MAINDIALNEEDLE : this.m_dialMetrics.MINIDIALNEEDLE;
      Point localPoint = paramBoolean ? this.m_dialMetrics.MAINDIALCOR : this.m_dialMetrics.MINIDIALCOR;
      Rectangle localRectangle2 = paramBoolean ? this.m_dialMetrics.MAINDIALCENTRE : this.m_dialMetrics.MINIDIALCENTRE;
      AffineTransform localAffineTransform = new AffineTransform();
      localAffineTransform.translate(paramInt1 - localPoint.x, paramInt2 - localPoint.y);
      localAffineTransform.rotate(Math.toRadians(paramInt3), localPoint.x, localPoint.y);
      paramGraphics2D.setClip(transform(localRectangle1, localAffineTransform));
      paramGraphics2D.drawImage(this.m_dialViewPieces, localAffineTransform, null);
      paramGraphics2D.setClip(null);
      drawImageArea(paramGraphics2D, this.m_dialViewPieces, localRectangle2, paramInt1 - localRectangle2.width / 2, paramInt2 - localRectangle2.height / 2);
    }
  }

  private static Shape transform(Rectangle paramRectangle, AffineTransform paramAffineTransform)
  {
    Point2D localPoint2D1 = paramAffineTransform.transform(new Point2D.Double(paramRectangle.x, paramRectangle.y), null);
    Point2D localPoint2D2 = paramAffineTransform.transform(new Point2D.Double(paramRectangle.x + paramRectangle.width, paramRectangle.y), null);
    Point2D localPoint2D3 = paramAffineTransform.transform(new Point2D.Double(paramRectangle.x + paramRectangle.width, paramRectangle.y + paramRectangle.height), null);
    Point2D localPoint2D4 = paramAffineTransform.transform(new Point2D.Double(paramRectangle.x, paramRectangle.y + paramRectangle.height), null);
    int[] arrayOfInt1 = { (int)localPoint2D1.getX(), (int)localPoint2D2.getX(), (int)localPoint2D3.getX(), (int)localPoint2D4.getX() };
    int[] arrayOfInt2 = { (int)localPoint2D1.getY(), (int)localPoint2D2.getY(), (int)localPoint2D3.getY(), (int)localPoint2D4.getY() };
    return new Polygon(arrayOfInt1, arrayOfInt2, 4);
  }

  private static void drawImageArea(Graphics2D paramGraphics2D, Image paramImage, Rectangle paramRectangle, int paramInt1, int paramInt2)
  {
    if ((paramRectangle.width > 0) && (paramRectangle.height > 0))
    {
      paramGraphics2D.setClip(new Rectangle(paramInt1, paramInt2, paramRectangle.width, paramRectangle.height));
      paramGraphics2D.drawImage(paramImage, paramInt1 - paramRectangle.x, paramInt2 - paramRectangle.y, null);
      paramGraphics2D.setClip(null);
    }
  }

  private void drawNumber(Graphics2D paramGraphics2D, Point paramPoint, Rectangle[] paramArrayOfRectangle, int paramInt, long paramLong, boolean paramBoolean)
  {
    drawNumber(paramGraphics2D, paramPoint, paramArrayOfRectangle, paramInt, paramLong, paramBoolean, 0);
  }

  private int determineSI(long paramLong)
  {
    if (paramLong >= 1000000000L)
      return 2;
    if (paramLong >= 1000000L)
      return 1;
    return 0;
  }

  private float divideSI(long paramLong, int paramInt)
  {
    if (paramInt == 2)
      return (float)(paramLong / 10000000L) / 100.0F;
    if (paramInt == 1)
      return (float)(paramLong / 10000L) / 100.0F;
    return (float)(paramLong / 10L) / 100.0F;
  }

  private static double log10(double paramDouble)
  {
    return Math.log(paramDouble == 0.0D ? 1.0D : paramDouble) / Math.log(10.0D);
  }

  private void drawNumber(Graphics2D paramGraphics2D, Point paramPoint, Rectangle[] paramArrayOfRectangle, int paramInt1, double paramDouble, boolean paramBoolean, int paramInt2)
  {
    int i = 1;
    int j = (paramBoolean) && (paramDouble > 0.0D) ? determineSI(()paramDouble) : -1;
    if (j >= 0)
      paramDouble = Math.abs(divideSI(()paramDouble, j));
    String str1 = (int)paramDouble;
    for (String str2 = ""; str1.length() + str2.length() < paramInt2; str2 = str2 + (int)(paramDouble % 10.0D))
      paramDouble *= 10.0D;
    char[] arrayOfChar = (str1 + (str2.length() > 0 ? "." : "") + str2).toCharArray();
    int k = 0;
    if (((paramInt1 & 0x20) != 0) || ((paramInt1 & 0x40) != 0))
    {
      for (m = 0; m < arrayOfChar.length; m++)
      {
        if (m > 0)
          k += i;
        n = arrayOfChar[m] - '0';
        n = (n < 0) || (n > 9) ? 10 : n;
        k += (n >= paramArrayOfRectangle.length ? 2 : paramArrayOfRectangle[n].width);
      }
      if ((j >= 0) && (j + 11 < paramArrayOfRectangle.length))
        k += paramArrayOfRectangle[(j + 11)].width;
    }
    int m = paramPoint.x;
    int n = paramPoint.y;
    if ((paramInt1 & 0x20) != 0)
      m -= k / 2;
    else if ((paramInt1 & 0x40) != 0)
      m -= k;
    if ((paramInt1 & 0x2) != 0)
      n -= paramArrayOfRectangle[0].height / 2;
    if ((paramInt1 & 0x4) != 0)
      n -= paramArrayOfRectangle[0].height;
    for (int i1 = 0; i1 < arrayOfChar.length; i1++)
    {
      int i2 = arrayOfChar[i1] - '0';
      i2 = (i2 < 0) || (i2 > 9) ? 10 : i2;
      Rectangle localRectangle = i2 >= paramArrayOfRectangle.length ? new Rectangle(0, 0, 2, 2) : paramArrayOfRectangle[i2];
      drawImageArea(paramGraphics2D, this.m_dialViewPieces, localRectangle, m, n);
      m += localRectangle.width;
      m += i;
    }
    if ((j >= 0) && (j + 11 < paramArrayOfRectangle.length))
      drawImageArea(paramGraphics2D, this.m_dialViewPieces, paramArrayOfRectangle[(j + 11)], m, n);
  }

  private void drawQOS(Graphics paramGraphics, int paramInt)
  {
    if (this.m_dialMetrics != null)
    {
      int i = this.m_dialMetrics.QOSPERCENT.width;
      char[] arrayOfChar = paramInt.toCharArray();
      for (int j = 0; j < arrayOfChar.length; j++)
      {
        i += 2;
        i += this.m_dialMetrics.QOSDIGITS[(arrayOfChar[j] - '0')].width;
      }
      j = this.m_dialMetrics.QOSDISPLAY.x + this.m_dialMetrics.QOSDISPLAY.width / 2 - i / 2;
      for (int k = 0; k < arrayOfChar.length + 1; k++)
      {
        localRectangle = k == arrayOfChar.length ? this.m_dialMetrics.QOSPERCENT : this.m_dialMetrics.QOSDIGITS[(arrayOfChar[k] - '0')];
        m = this.m_dialMetrics.QOSDISPLAY.y + this.m_dialMetrics.QOSDISPLAY.height / 2 - localRectangle.height / 2;
        paramGraphics.setClip(new Rectangle(j, m, localRectangle.width, localRectangle.height));
        paramGraphics.drawImage(this.m_dialViewPieces, j - localRectangle.x, m - localRectangle.y, null);
        j += 2 + localRectangle.width;
      }
      k = Math.min(Math.max(1, 1 + paramInt / 10), 10);
      Rectangle localRectangle = this.m_dialMetrics.QOSBARS[(k - 1)];
      int m = this.m_dialMetrics.QOSBARTOPLEFT.x;
      int n = this.m_dialMetrics.QOSBARTOPLEFT.y + localRectangle.y;
      paramGraphics.setClip(m, n, localRectangle.width, localRectangle.height);
      paramGraphics.drawImage(this.m_dialViewPieces, m - localRectangle.x, n - localRectangle.y, null);
      paramGraphics.setClip(null);
    }
  }

  private void drawBackground(Graphics2D paramGraphics2D, Dimension paramDimension)
  {
    Object localObject2;
    if (this.m_bClassic)
    {
      localObject1 = getUnregisteredMessage();
      int j;
      int i3;
      if (localObject1 != null)
      {
        FontMetrics localFontMetrics = paramGraphics2D.getFontMetrics();
        j = localFontMetrics.getHeight();
        int m = j / 2;
        i1 = localFontMetrics.stringWidth(localObject1 + TX(" "));
        i2 = 0;
        paramGraphics2D.setColor(new Color(14211288));
        i3 = j;
        while (i3 < paramDimension.height)
        {
          int i4 = 0;
          while (i4 < paramDimension.width)
          {
            paramGraphics2D.drawString((String)localObject1, i2 + i4, i3);
            i4 += i1 + m;
          }
          i2 = i2 == 0 ? -(i1 + m) / 2 : 0;
          i3 += j + m;
        }
      }
      for (int i = 0; i < paramDimension.width; i++)
      {
        j = paramDimension.height - i * paramDimension.height / paramDimension.width;
        long l2 = x2bps(i);
        paramGraphics2D.setColor(bps2c(l2));
        paramGraphics2D.drawLine(i, j, i, paramDimension.height);
      }
      for (long l1 = 10000L; l1 < 100000000000L; l1 *= 10L)
        if ((l1 > this.LOBPS) && (l1 <= this.HIBPS))
        {
          double d = bps2per(l1);
          i2 = (int)(paramDimension.width * d);
          i3 = paramDimension.height - (int)(paramDimension.height * d);
          paramGraphics2D.setColor(darker(bps2c(l1), 90));
          paramGraphics2D.drawLine(i2, i3, i2, paramDimension.height);
        }
      paramGraphics2D.setColor(Color.white);
      paramGraphics2D.drawLine(0, paramDimension.height - 1, paramDimension.width, -1);
      paramGraphics2D.setColor(Color.gray);
      paramGraphics2D.drawLine(0, paramDimension.height, paramDimension.width, 0);
      localObject2 = new Vector();
      Color localColor1 = Color.lightGray;
      Color localColor2 = new Color(11579647);
      int i1 = paramGraphics2D.getFontMetrics().getHeight();
      for (int i2 = 0; i2 < this.m_labels.size(); i2++)
      {
        String str1 = (String)this.m_labels.elementAt(i2);
        StringTokenizer localStringTokenizer = new StringTokenizer(str1, TX(","));
        if (localStringTokenizer.countTokens() == 5)
        {
          int i5 = parseInt(localStringTokenizer.nextToken());
          long l3 = parseLong(localStringTokenizer.nextToken());
          if ((l3 > this.LOBPS) && (l3 < this.HIBPS))
          {
            String str2 = mapDash(localStringTokenizer.nextToken());
            String str3 = mapDash(localStringTokenizer.nextToken());
            String str4 = mapDash(localStringTokenizer.nextToken());
            int i6 = (l3 != 100000L) && (l3 != 1000000L) && (l3 != 10000000L) && (l3 != 100000000L) && (l3 != 1000000000L) ? 0 : 1;
            if (!((Vector)localObject2).contains(new Long(l3)))
            {
              drawLabel(paramGraphics2D, i1 * i5 / 100, l3, str2, str3, str4, i6 != 0 ? localColor2 : localColor1);
              ((Vector)localObject2).addElement(new Long(l3));
            }
          }
        }
      }
    }
    else
    {
      if (this.m_dialViewBack != null)
        paramGraphics2D.drawImage(this.m_dialViewBack, 0, 0, null);
      if (this.m_dialMetrics != null)
      {
        localObject1 = getPegNumbers(true);
        localObject2 = getPegNumbers(false);
        int k = this.m_dialMetrics.MINIDIALSI ? 1000000 : 1;
        for (int n = 0; n < Math.min(localObject2.length, this.m_dialMetrics.MINIDIALPEGS.length); n++)
          drawNumber(paramGraphics2D, this.m_dialMetrics.MINIDIALPEGS[n], this.m_dialMetrics.MINIDIALDIGITS, this.m_dialMetrics.MINIDIALPEGALIGN[n], ()(localObject2[n] * k), this.m_dialMetrics.MINIDIALSI);
        k = this.m_dialMetrics.MAINDIALSI ? 1000000 : 1;
        for (n = 0; n < Math.min(localObject1.length, this.m_dialMetrics.MAINDIALPEGS.length); n++)
          drawNumber(paramGraphics2D, this.m_dialMetrics.MAINDIALPEGS[n], this.m_dialMetrics.MAINDIALDIGITS, this.m_dialMetrics.MAINDIALPEGALIGN[n], ()(localObject1[n] * k), this.m_dialMetrics.MAINDIALSI);
      }
    }
    Object localObject1 = getBackgroundOverlay();
    if (localObject1 != null)
      paramGraphics2D.drawImage((Image)localObject1, 0, 0, null);
  }

  private double[] getPegNumbers(boolean paramBoolean)
  {
    if (this.m_dialMetrics == null)
      return new double[0];
    if (((paramBoolean) && (this.m_nMainPegs == null)) || ((!paramBoolean) && (this.m_nMiniPegs == null)))
    {
      String str = iniGetString(paramBoolean ? TX("hispeed.maindialpegs") : TX("hispeed.minidialpegs"));
      int i = paramBoolean ? this.m_dialMetrics.MAINDIALPEGS.length : this.m_dialMetrics.MINIDIALPEGS.length;
      double[] arrayOfDouble = new double[i];
      StringTokenizer localStringTokenizer = new StringTokenizer(str == null ? "" : str, ", ");
      for (int j = 0; j < arrayOfDouble.length; j++)
      {
        double d = localStringTokenizer.hasMoreTokens() ? Util.parseDouble(localStringTokenizer.nextToken(), -1.0D) : -1.0D;
        if (d == -1.0D)
        {
          System.out.println("WARNING: Peg numbers for " + (paramBoolean ? "main" : "mini") + " dial (" + str + ") are invalid: Peg #" + j + " cannot be parsed");
          d = j > 0 ? arrayOfDouble[(j - 1)] + 1.0D : 0.0D;
        }
        if ((j > 0) && (d <= arrayOfDouble[(j - 1)]))
        {
          System.out.println("WARNING: Peg numbers for " + (paramBoolean ? "main" : "mini") + " dial (" + str + ") are invalid: Peg #" + j + "(=" + d + ") is less than or equal to peg #" + (j - 1) + "(=" + arrayOfDouble[(j - 1)] + ")");
          d = arrayOfDouble[(j - 1)] + 1.0D;
        }
        arrayOfDouble[j] = d;
      }
      if (paramBoolean)
        this.m_nMainPegs = arrayOfDouble;
      else
        this.m_nMiniPegs = arrayOfDouble;
    }
    return paramBoolean ? this.m_nMainPegs : this.m_nMiniPegs;
  }

  private void doDrawString(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, Color paramColor)
  {
    Font localFont = paramGraphics.getFont();
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int i = localFontMetrics.getHeight();
    int j = 0;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, TX("\n"));
    int k = localStringTokenizer.countTokens();
    if ((paramString.trim().length() > 0) && (k > 0))
    {
      for (int m = 0; m < k; m++)
        j = U.max(j, localFontMetrics.stringWidth(localStringTokenizer.nextToken()));
      paramInt2 -= (k - 1) * i / 2;
      if (paramColor != null)
      {
        paramGraphics.setColor(paramColor);
        paramGraphics.fillRect(paramInt1 - 3, paramInt2 - i, j + 3 + 3, i * k);
        paramGraphics.setColor(Color.black);
        paramGraphics.drawRect(paramInt1 - 3, paramInt2 - i, j + 3 + 3, i * k);
      }
      paramGraphics.setColor(Color.black);
      localStringTokenizer = new StringTokenizer(paramString, TX("\n"));
      for (m = 0; m < k; m++)
      {
        paramGraphics.drawString(localStringTokenizer.nextToken(), paramInt1, paramInt2 - localFontMetrics.getDescent());
        paramInt2 += i;
      }
    }
    paramGraphics.setFont(localFont);
  }

  private static String mapDash(String paramString)
  {
    return paramString.equals(TX("-")) ? null : paramString;
  }

  private Color bps2c(long paramLong)
  {
    return U.valueToColour(paramLong, this.m_bpsc);
  }

  private long x2bps(int paramInt)
  {
    int i = getSize().width;
    double d1 = U.log(this.LOBPS);
    double d2 = U.log(this.HIBPS);
    paramInt = U.min(i, U.max(0, paramInt));
    long l = ()U.pow(2.718281828459045D, d1 + (d2 - d1) * paramInt / i);
    return l;
  }

  private Color darker(Color paramColor, int paramInt)
  {
    return new Color(paramColor.getRed() * paramInt / 100, paramColor.getGreen() * paramInt / 100, paramColor.getBlue() * paramInt / 100);
  }

  private static int parseInt(String paramString)
  {
    try
    {
      return Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  private static long parseLong(String paramString)
  {
    try
    {
      return Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
    }
    return 0L;
  }

  private void drawLabel(Graphics paramGraphics, int paramInt, long paramLong, String paramString1, String paramString2, String paramString3, Color paramColor)
  {
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    Dimension localDimension = getSize();
    double d = bps2per(paramLong);
    int i = (int)(localDimension.width * d);
    int j = localDimension.height - (int)(localDimension.height * d);
    U.drawDot(paramGraphics, i, j, paramColor);
    String str1 = G.g_bBytes ? paramString3 : paramString2;
    if (str1 != null)
    {
      paramGraphics.setColor(Color.black);
      int k = (paramString1 != null) && (str1.startsWith("$")) ? 1 : 0;
      String str2 = (paramString1 != null ? paramString1 + TX(" - ") : TX("")) + str1;
      String str3 = k != 0 ? str1.substring(1) : null;
      int m = localFontMetrics.getHeight();
      int n = i + 2 + paramInt;
      int i1 = j + m + 1 - paramInt;
      paramGraphics.drawString(str2, n, i1);
      if (str3 != null)
        paramGraphics.drawString(str3, n, i1 + m);
    }
  }

  private void drawPercentDoneBar(Graphics2D paramGraphics2D, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if ((this.m_bClassic) || (this.m_dialMetrics == null) || (!this.m_dialMetrics.CUSTOMPROGRESS) || (this.m_dialMetrics.PROGRESSBAR == null))
    {
      int i = 100;
      paramGraphics2D.setColor(new Color(10526880));
      paramGraphics2D.fillRect(paramInt1 - 1, paramInt2 - 1, i + 1 + 1 + 1, 3);
      paramGraphics2D.setColor(new Color(14737632));
      paramGraphics2D.drawLine(paramInt1, paramInt2, paramInt1 + i, paramInt2);
      paramGraphics2D.setColor(new Color(4210943));
      paramGraphics2D.drawLine(paramInt1, paramInt2, paramInt1 + i * paramInt3 / 100, paramInt2);
    }
    else
    {
      Rectangle localRectangle = (Rectangle)this.m_dialMetrics.PROGRESSBAR.clone();
      localRectangle.width = (this.m_dialMetrics.PROGRESSBAR.width * paramInt3 / 100);
      drawImageArea(paramGraphics2D, this.m_dialViewPieces, localRectangle, this.m_dialMetrics.PROGRESSBARPOS.x, this.m_dialMetrics.PROGRESSBARPOS.y);
      if (this.m_dialMetrics.PROGRESSBARENDCAP != null)
      {
        int j = this.m_dialMetrics.PROGRESSBARPOS.x + localRectangle.width;
        if (paramBoolean)
          j = this.m_dialMetrics.PROGRESSBARPOS.x + this.m_dialMetrics.PROGRESSBAR.width - localRectangle.width;
        drawImageArea(paramGraphics2D, this.m_dialViewPieces, this.m_dialMetrics.PROGRESSBARENDCAP, j, this.m_dialMetrics.PROGRESSBARPOS.y);
      }
    }
  }

  private double bps2per(long paramLong)
  {
    long l1 = this.LOBPS;
    long l2 = this.HIBPS;
    paramLong = U.max(l1, U.min(l2, paramLong));
    return (U.log(paramLong) - U.log(l1)) / (U.log(l2) - U.log(l1));
  }

  private String howfast(int paramInt, long paramLong)
  {
    long l = 65536000 / paramInt;
    return U.bps2s(8L * Math.min(paramLong, l));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_start)
      fireActionEvent(new ActionEvent(this, 1001, "start"));
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.m_down = paramMouseEvent.getPoint();
    String str = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if ("units".equals(str))
    {
      G.g_bBytes = !G.g_bBytes;
      this.m_back = null;
      repaint();
    }
    else if ("start".equals(str))
    {
      setStartButtonState(2);
    }
    else if ("diallink".equals(str))
    {
      setInfoLinkButtonState(2);
    }
    super.mousePressed(paramMouseEvent);
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    String str1 = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    setStartButtonState("start".equals(str1) ? 1 : 0);
    if ("diallink".equals(str1))
    {
      setInfoLinkButtonState(1);
      String str2 = iniGetString("hispeed.diallink");
      if ((this.m_down != null) && ("diallink".equals(getHitRegion(this.m_down.x, this.m_down.y))))
        fireActionEvent(new ActionEvent(this, 1001, str2));
    }
    else
    {
      setInfoLinkButtonState(0);
    }
    this.m_down = null;
    super.mouseReleased(paramMouseEvent);
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    this.m_down = paramMouseEvent.getPoint();
    super.mouseDragged(paramMouseEvent);
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    Object[] arrayOfObject = getHitRegionDetail(paramMouseEvent.getX(), paramMouseEvent.getY());
    int i = 0;
    int j = 0;
    String str1 = null;
    Point localPoint = null;
    if ((arrayOfObject != null) && (arrayOfObject.length >= 2) && (arrayOfObject[0] != null))
    {
      String str2 = (String)arrayOfObject[0];
      if (str2.startsWith("info:"))
      {
        Rectangle localRectangle = (Rectangle)arrayOfObject[1];
        str1 = ((String)arrayOfObject[0]).substring(5);
        localPoint = new Point(localRectangle.x + localRectangle.width / 2, localRectangle.y + localRectangle.height / 2);
      }
      else if (str2.equals("start"))
      {
        i = this.m_nStartState == 2 ? 2 : 1;
      }
      else if (str2.equals("diallink"))
      {
        j = this.m_nInfoLinkState == 2 ? 2 : 1;
      }
    }
    if ((str1 != this.m_explain) || ((str1 != null) && (!str1.equals(this.m_explain))) || (localPoint != this.m_explainLoc) || ((localPoint != null) && (!localPoint.equals(this.m_explainLoc))))
    {
      this.m_explain = str1;
      this.m_explainLoc = localPoint;
      repaint();
    }
    setStartButtonState(i);
    setInfoLinkButtonState(j);
    setCursor(Cursor.getPredefinedCursor(arrayOfObject == null ? 0 : 12));
  }

  private void setStartButtonState(int paramInt)
  {
    if (paramInt != this.m_nStartState)
    {
      this.m_nStartState = paramInt;
      repaint();
    }
  }

  private void setInfoLinkButtonState(int paramInt)
  {
    if (paramInt != this.m_nInfoLinkState)
    {
      this.m_nInfoLinkState = paramInt;
      repaint();
    }
  }

  public void stopAnimation()
  {
    if (this.m_ani != null)
      this.m_bRunAnimation = false;
    long l = System.currentTimeMillis() + 500L;
    while ((this.m_ani != null) && (System.currentTimeMillis() < l))
      U.sleep(50L);
    if (this.m_ani != null)
      try
      {
        this.m_ani.stop();
      }
      catch (Exception localException)
      {
      }
  }

  public void run()
  {
    Thread localThread = Thread.currentThread();
    if (localThread == this.m_downloadDialView)
      runDialViewDownloadThread();
    else if (localThread == this.m_ani)
      runAniThread();
  }

  private void runDialViewDownloadThread()
  {
    try
    {
      String str = iniGetString("hispeed.customdial");
      str = "speeddials/" + str.trim() + "/";
      this.m_dialViewBack = this.m_applet.getImage(new URL(this.m_plugin.base(), "/myspeed/" + str + "speeddialview.jpg"));
      this.m_dialViewPieces = this.m_applet.getImage(new URL(this.m_plugin.base(), "/myspeed/" + str + "speeddialviewparts.png"));
      this.m_dialMetrics = HiSpeedTab.DialMetrics.fromSpecString(Util.readUrl(new URL(this.m_plugin.base(), "/myspeed/" + str + "dialmetrics.txt")));
      MediaTracker localMediaTracker = new MediaTracker(this);
      if (this.m_dialViewBack != null)
        localMediaTracker.addImage(this.m_dialViewBack, 0);
      if (this.m_dialViewPieces != null)
        localMediaTracker.addImage(this.m_dialViewPieces, 1);
      try
      {
        localMediaTracker.waitForAll();
      }
      catch (Exception localException2)
      {
      }
      repaint();
    }
    catch (Exception localException1)
    {
      System.out.println("Error downloading dial view: " + localException1);
      localException1.printStackTrace();
    }
    finally
    {
      this.m_downloadDialView = null;
    }
  }

  private void runAniThread()
  {
    try
    {
      int i = ("no".equals(RC(TX("loadanimation")))) && (!g_bTestAni) ? 0 : 1;
      long l1 = U.max(14400L, this.LOBPS);
      long l2 = U.min(this.HIBPS, U.max(this.LOBPS * 2L, 10000000000L));
      long l3 = l1;
      double d = 1.1D;
      U.sleep(200L);
      while (this.m_bRunAnimation)
      {
        if (!isShowingMessageOnGlass())
        {
          HiSpeedTest localHiSpeedTest = this.m_plugin.getHiSpeedTest();
          if ((localHiSpeedTest != null) && (localHiSpeedTest.getState() > -1))
            break;
          Point localPoint = this.m_down;
          long l4;
          if (localPoint != null)
          {
            l4 = x2bps(localPoint.x);
            l4 = (l4 + 500L) / 1000L * 1000L;
            this.m_aniSpeed = bits2bytes(l4);
          }
          else
          {
            l4 = ()(l3 * d);
            if ((l4 < l1) || (l4 > l2))
              d = d < 1.0D ? 1.1D : 0.9090909090909091D;
            l3 = ()(l3 * d);
            this.m_aniSpeed = (i != 0 ? bits2bytes(l3) : 0);
          }
          repaint();
        }
        U.sleep(50L);
      }
    }
    finally
    {
      this.m_ani = null;
    }
  }

  private int bits2bytes(long paramLong)
  {
    return (int)U.min(2147483647L, paramLong / 8L);
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.hispeed.HiSpeedTab
 * JD-Core Version:    0.6.2
 */