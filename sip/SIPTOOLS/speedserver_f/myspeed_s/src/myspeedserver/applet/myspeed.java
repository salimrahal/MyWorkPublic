package myspeedserver.applet;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.html.parser.ParserDelegator;

public class myspeed extends JApplet
  implements Runnable, ActionListener, WindowListener, KeyListener, MouseListener, MouseMotionListener
{
  private static final int XF = 60;
  private static final int YF = -1;
  private static final int ZF = 4;
  public static final Color MD = new Color(247, 247, 247);
  public static final Color ND = new Color(226, 234, 243);
  private static Object OD = new Object();
  private static String QF;
  private static boolean PD = false;
  private static String QD = null;
  private String[] TD = { TX("MyConnection Server (tm) 9.7c"), TX("© 1999-2013 Visualware"), TX(""), TX("Unregistered Version"), TX("License Expired"), TX("*"), "<*<MSS8>*>", TX("8000"), TX("Unlicensed") };
  private String WF;
  private boolean UD = false;
  private URL VD = null;
  private SIDPanel XE;
  private WaitPanel YE;
  private GraphTab DE;
  private myspeed.MySpeedContentPane WE;
  private boolean VE;
  private Properties WD = new Properties();
  private Properties NF;
  private Properties HE;
  private String XD = null;
  private int YD = 0;
  private Image BE;
  private Image CE;
  private Image ZE;
  private Image AE;
  private boolean ZD = true;
  private boolean AD = false;
  private Hashtable TE;
  private Hashtable UE;
  private String QE;
  private int BD = -1;
  private Date CD = new Date();
  private String OE;
  private long DD = 0L;
  private String LE;
  private int MF;
  private boolean ED = false;
  private Rectangle FD = new Rectangle();
  private Rectangle GD = new Rectangle();
  private Rectangle[] HD = new Rectangle[0];
  private Rectangle RF;
  private Rectangle SF;
  private int JD = 0;
  //thread called by doStartMySpeed
  private Thread OF;
  private Thread PE;
  private int KD = -1;
  private int TF;
  private Thread UF;
  private boolean VF;
  private int JE;
  private Vector KE;
  private JFrame LD = new JFrame();
  private JTextArea ME = new JTextArea(18, 80);
  private String PF;
  private static char[] RD = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  private char IE;
  private String RE;
  private AppletPlugin[] SE;
  private Object NE = new Object();
  private AppletTab[] EE;
  private boolean[] FE;
  private int GE;

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    String str1 = paramActionEvent.getActionCommand();
    if ("start".equals(str1))
    {
      doStartMySpeed(true);
    }
    else if ("start_noFocusRequest".equals(str1))
    {
      doStartMySpeed(false);
    }
    else if ("updatejava".equals(str1))
    {
      doShowDocument(TX("http://www.java.com/download/"));
    }
    else
    {
      String str2;
      if ((("mssid".equals(str1)) || ("auditreport".equals(str1))) && (isMSSIDLinkOK()))
      {
        str2 = getMSSBase();
        if ((str2 != null) && (isProfessional()))
          doShowDocument(doGetReportDetailURL(this.OE));
      }
      else if ((str1 != null) && (str1.startsWith("analysisportal_")) && (isProfessional()))
      {
        str2 = getAnalysisPortalURL(str1.substring(15));
        if (str2 != null)
          doShowDocument(str2);
      }
      else if (TX("submit").equals(str1))
      {
        doSubmit();
      }
      else if ((str1 != null) && ((str1.startsWith(TX("http://"))) || (str1.startsWith(TX("https://")))))
      {
        doShowDocument(str1);
      }
      else if ((str1 != null) && (str1.startsWith(TX("tab_"))))
      {
        selectTab(str1.substring(4));
      }
      else if (TX("speedtest").equals(str1))
      {
        selectTab(TX("speed"));
      }
      else if (TX("voiptest").equals(str1))
      {
        selectTab(TX("voip"));
      }
    }//end else
  }//end action performed

  public String appNameVer(boolean paramBoolean)
  {
    return this.TD[0] + (paramBoolean ? editionText() : "");
  }

  private String editionText()
  {
    return "";
  }

  public String copyrightStuff()
  {
    return this.TD[1];
  }

  public URL base()
  {
    if (this.VD == null)
      this.VD = getFixedMySpeedURL();
    return this.VD;
  }

  private void doDelaySwitchThread()
  {
    long l = System.currentTimeMillis() + 3000L;
    while ((this.KD >= 0) && (System.currentTimeMillis() < l))
      try
      {
        Thread.sleep(50L);
      }
      catch (Exception localException)
      {
      }
    if ((this.KD >= 0) && (this.PE != null))
      selectTab(this.KD);
    this.PE = null;
  }

  private Rectangle doDrawString(Graphics paramGraphics, String paramString, int paramInt, Color paramColor)
  {
    Font localFont = paramGraphics.getFont();
    paramGraphics.setFont(new Font(TX("Helvetica"), 0, 16));
    int i = getSize().width;
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int j = localFontMetrics.getHeight();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, TX("\n"));
    int k = localStringTokenizer.countTokens();
    String[] arrayOfString = new String[k];
    paramInt -= (k - 1) * j / 2;
    int m = i / 2;
    int n = paramInt - localFontMetrics.getAscent();
    int i1 = i / 2;
    int i2 = paramInt + (k - 1) * j + localFontMetrics.getDescent();
    int i4;
    int i5;
    for (int i3 = 0; i3 < k; i3++)
    {
      paramString = localStringTokenizer.nextToken();
      i4 = localFontMetrics.stringWidth(paramString);
      i5 = (i - i4) / 2;
      m = Math.min(i5, m);
      i1 = Math.max(i5 + i4, i1);
      arrayOfString[i3] = paramString;
    }
    if (paramColor != null)
    {
      paramGraphics.setColor(paramColor);
      paramGraphics.fillRect(m - 3, n - 3, i1 - m + 3 + 3, i2 - n + 3);
      paramGraphics.setColor(Color.black);
      paramGraphics.drawRect(m - 3, n - 3, i1 - m + 3 + 3, i2 - n + 3);
    }
    paramGraphics.setColor(Color.black);
    for (i3 = 0; i3 < k; i3++)
    {
      i4 = localFontMetrics.stringWidth(arrayOfString[i3]);
      i5 = (i - i4) / 2;
      paramGraphics.drawString(arrayOfString[i3], i5, paramInt - localFontMetrics.getDescent());
      paramInt += j;
    }
    paramGraphics.setFont(localFont);
    return new Rectangle(m - 3, n - 3, i1 - m + 3 + 3, i2 - n + 3);
  }

  private boolean doDuckwareTamperingCheck(Graphics paramGraphics)
  {
    return false;
  }

  private AppletPlugin loadPlugin(String paramString)
  {
    try
    {
      Class localClass = Class.forName("mcsaplugins." + paramString + "." + paramString);
      Constructor localConstructor = localClass == null ? null : localClass.getConstructor(new Class[] { Applet.class });
      Object localObject = localConstructor == null ? null : localConstructor.newInstance(new Object[] { this });
      AppletPlugin localAppletPlugin = (localObject instanceof AppletPlugin) ? (AppletPlugin)localObject : null;
      return localAppletPlugin;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  /*
  sr load plugins: myspeed
  */
  private void loadPlugins()
  {
    String str = this.AD ? this.QE : this.RE;
    if ((!this.AD) && ((str == null) || (TX("all").equals(str))))
      str = iniGetString("mss.plugins");
    this.SE = new AppletPlugin[0];
    StringTokenizer localStringTokenizer = new StringTokenizer(str == null ? "" : str, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      AppletPlugin localAppletPlugin = loadPlugin(localStringTokenizer.nextToken());
      if (localAppletPlugin != null)
        addPlugin(localAppletPlugin);
    }
    for (int i = 0; i < this.SE.length; i++)
      try
      {
        this.SE[i].doFirstTimeInit();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    if ((this.AD) && (this.SE.length > 0) && (this.TE != null) && (this.UE != null))
    {
      AppletTest localAppletTest = this.SE[0].createTest(this.TE, this.UE);
      this.SE[0].setTest(localAppletTest);
      doFinalTabs();
    }
  }

  private void loadTestData()
  {
    long l = Util.parseLong(getParameter("recordid"), 0L);
    int i = Util.parseInt(getParameter("serverid"), 0);
    if (l > 0L)
    {
      this.AD = true;
      DataInputStream localDataInputStream = null;
      try
      {
        URL localURL = new URL(getFixedMySpeedURL(), TX("db/testdata?recordid=") + l + "&serverid=" + i);
        URLConnection localURLConnection = localURL.openConnection();
        Hashtable localHashtable1 = new Hashtable();
        Hashtable localHashtable2 = new Hashtable();
        localDataInputStream = new DataInputStream(localURLConnection.getInputStream());
        String str;
        while ((str = localDataInputStream.readLine()) != null)
        {
          int j = str.indexOf('=');
          Object localObject1 = j > 0 ? str.substring(0, j).trim() : null;
          Object localObject2 = j > 0 ? str.substring(j + 1) : null;
          if ((localObject1 != null) && (localObject2 != null))
            if (localObject1.equals("plugin"))
              this.QE = localObject2;
            else if (localObject1.startsWith(TX("detail.")))
              localHashtable2.put(localObject1.substring(7), localObject2);
            else
              localHashtable1.put(localObject1, localObject2);
        }
        this.TE = localHashtable1;
        this.UE = localHashtable2;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
        try
        {
          localDataInputStream.close();
        }
        catch (Exception localException2)
        {
        }
      }
      finally
      {
        try
        {
          localDataInputStream.close();
        }
        catch (Exception localException3)
        {
        }
      }
    }
  }

  private void addPlugin(AppletPlugin paramAppletPlugin)
  {
    AppletPlugin[] arrayOfAppletPlugin = new AppletPlugin[this.SE.length + 1];
    System.arraycopy(this.SE, 0, arrayOfAppletPlugin, 0, this.SE.length);
    arrayOfAppletPlugin[(arrayOfAppletPlugin.length - 1)] = paramAppletPlugin;
    this.SE = arrayOfAppletPlugin;
  }

  public int getTestSpecId()
  {
    try
    {
      return Integer.parseInt(getUrlStringParameter("testspecid"));
    }
    catch (Exception localException)
    {
    }
    return iniGetInteger("testspecid", 0);
  }

  private String getDB()
  {
    try
    {
      String str = JS.js(this, "document.location.href").toString();
      return str;
    }
    catch (Throwable localThrowable)
    {
    }
    return getDocumentBase().toString();
  }

  public String getUrlStringParameter(String paramString)
  {
    String str1 = getDB();
    int i = str1.indexOf('?');
    if (i > 0)
    {
      String str2 = "&" + str1.substring(i + 1);
      i = str2.indexOf('#');
      str2 = i > 0 ? str2.substring(0, i) : str2;
      String str3 = "&" + paramString + "=";
      i = str2.indexOf(str3);
      if (i >= 0)
      {
        String str4 = str2.substring(i + str3.length());
        i = str4.indexOf('&');
        return Util.httpDecode(i > 0 ? str4.substring(0, i) : str4);
      }
    }
    return null;
  }

  public void init()
  {
    if (!this.VE)
    {
      this.VE = true;
      try
      {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
      new ParserDelegator();
      setBackground(new Color(15790320));
      setForeground(new Color(0));
      this.WE = new myspeed.MySpeedContentPane(this, null);
      GridBagConstraints localGridBagConstraints = new GridBagConstraints();
      localGridBagConstraints.weightx = 1.0D;
      localGridBagConstraints.weighty = 1.0D;
      localGridBagConstraints.fill = 1;
      Container localContainer = getContentPane();
      GridBagLayout localGridBagLayout = new GridBagLayout();
      localGridBagLayout.setConstraints(this.WE, localGridBagConstraints);
      localContainer.setLayout(localGridBagLayout);
      localContainer.add(this.WE);
      this.WE.setLayout(null);
      U.setMySpeed(this);
      this.XE = new SIDPanel(this);
      this.YE = new WaitPanel();
      initStatusWindow();
      loadInternalSettings();
      loadMSS(getTestSpecId());
      loadINI();
      loadTestData();
      loadControls();
      loadPlugins();
      addKeyListener(this);
      addMouseListener(this);
      addMouseMotionListener(this);
      setFont(new Font(TX("Helvetica"), 0, iniGetInteger("fontsize", 11)));
      String str = un(null, this.TD);
      if ((!doDuckwareTamperingCheck(null)) && (!doINILoadedAndVersionCheck(null)) && ((str == "") || (str == this.TD[3])))
      {
        try
        {
          this.ZE = getImageFromJar(TX("tabselected.gif"));
          this.AE = getImageFromJar(TX("tabdeselected.gif"));
          this.BE = getImageFromJar(TX("uparrow.gif"));
          this.CE = getImageFromJar(TX("downarrow.gif"));
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        if ((isProfessional()) && (!this.AD) && ((TX("yes").equals(iniGetString(TX("autostart")))) || (TX("*").equals(iniGetString(TX("start"))))))
          doStartMySpeed(true);
        selectTab(0);
      }
      doRepaint();
    }
  }

  private void loadControls()
  {
    JPanel localJPanel = new JPanel();
    localJPanel.setOpaque(false);
    localJPanel.setLayout(null);
    setGlassPane(localJPanel);
    localJPanel.setVisible(true);
    localJPanel.add(this.XE);
    localJPanel.add(this.YE);
    int i = isPaintingTabs() ? 60 : 0;
    int j = 300;
    int k = 200;
    this.XE.setBounds(i + (getSize().width - i) / 2 - j / 2, getSize().height / 2 - k / 2, j, k);
    this.XE.setVisible(false);
    this.YE.setVisible(false);
    this.DE = new GraphTab(this);
    this.DE.doFirstTimeInit();
    invalidate();
    this.XE.invalidate();
    validate();
  }

  public AppletPlugin[] getPlugins()
  {
    return this.SE;
  }

  public AppletPlugin getPlugin(String paramString)
  {
    for (int i = 0; (this.SE != null) && (i < this.SE.length); i++)
      if ((this.SE[i] != null) && (paramString.equals(this.SE[i].getName())))
        return this.SE[i];
    return null;
  }

  public void addTab(AppletTab paramAppletTab, boolean paramBoolean)
  {
    synchronized (this.NE)
    {
      this.WE.add(paramAppletTab);
      paramAppletTab.addActionListener(this);
      int i = isPaintingTabs() ? 60 : 0;
      paramAppletTab.setBounds(i + 1, 1, getSize().width - i - 2, getSize().height - 3);
      paramAppletTab.setVisible(false);
      AppletTab[] arrayOfAppletTab = new AppletTab[this.EE == null ? 1 : this.EE.length + 1];
      boolean[] arrayOfBoolean = new boolean[arrayOfAppletTab.length];
      if (this.EE != null)
        System.arraycopy(this.EE, 0, arrayOfAppletTab, 0, this.EE.length);
      if (this.FE != null)
        System.arraycopy(this.FE, 0, arrayOfBoolean, 0, this.FE.length);
      arrayOfAppletTab[(arrayOfAppletTab.length - 1)] = paramAppletTab;
      arrayOfBoolean[(arrayOfBoolean.length - 1)] = paramBoolean;
      this.EE = arrayOfAppletTab;
      this.FE = arrayOfBoolean;
      repaint();
    }
  }

  private void removeTab(int paramInt)
  {
    synchronized (this.NE)
    {
      if ((this.EE != null) && (paramInt < this.EE.length))
      {
        this.WE.remove(this.EE[paramInt]);
        this.EE[paramInt].removeActionListener(this);
        AppletTab[] arrayOfAppletTab = new AppletTab[this.EE.length - 1];
        boolean[] arrayOfBoolean = new boolean[this.EE.length - 1];
        if (paramInt > 0)
        {
          System.arraycopy(this.EE, 0, arrayOfAppletTab, 0, paramInt);
          System.arraycopy(this.FE, 0, arrayOfBoolean, 0, paramInt);
        }
        if (paramInt + 1 < this.EE.length)
        {
          System.arraycopy(this.EE, paramInt + 1, arrayOfAppletTab, paramInt, this.EE.length - paramInt - 1);
          System.arraycopy(this.FE, paramInt + 1, arrayOfBoolean, paramInt, this.EE.length - paramInt - 1);
        }
        this.EE = arrayOfAppletTab;
        this.FE = arrayOfBoolean;
        if (this.GE >= this.EE.length)
          selectTab(this.EE.length - 1);
      }
    }
  }

  public void addCombinedGraphResults(String paramString, String[] paramArrayOfString)
  {
    this.DE.addCombinedGraphResults(paramString, paramArrayOfString);
  }

  public void addGraphResults(String paramString, long[] paramArrayOfLong, int[] paramArrayOfInt, float[] paramArrayOfFloat, boolean[] paramArrayOfBoolean)
  {
    this.DE.addGraphResults(paramString, paramArrayOfLong, paramArrayOfInt, paramArrayOfFloat, paramArrayOfBoolean);
  }

  public void addGraphResults(String paramString, long[] paramArrayOfLong, int[] paramArrayOfInt, boolean paramBoolean, int paramInt)
  {
    this.DE.addGraphResults(paramString, paramArrayOfLong, paramArrayOfInt, paramBoolean, paramInt);
  }

  public void addGraphResults(String paramString, long[][] paramArrayOfLong, int[][] paramArrayOfInt)
  {
    this.DE.addGraphResults(paramString, paramArrayOfLong, paramArrayOfInt, null, null);
  }

  public Image getImageFromJar(String paramString)
  {
    byte[] arrayOfByte = getDataFromJar(paramString);
    Image localImage = arrayOfByte == null ? null : getToolkit().createImage(arrayOfByte, 0, arrayOfByte.length);
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

  private byte[] getDataFromJar(String paramString)
  {
    DataInputStream localDataInputStream = null;
    Object localObject1 = new byte[65535];
    try
    {
      int i = 0;
      localDataInputStream = new DataInputStream(getClass().getResourceAsStream(paramString));
      while (localDataInputStream.available() > 0)
      {
        while (localObject1.length - i < localDataInputStream.available())
        {
          byte[] arrayOfByte = new byte[localObject1.length * 2];
          System.arraycopy(localObject1, 0, arrayOfByte, 0, localObject1.length);
          localObject1 = arrayOfByte;
        }
        int j = localDataInputStream.read((byte[])localObject1, i, localObject1.length - i);
        if (j == -1)
          break;
        i += j;
      }
      localObject1 = U.resize((byte[])localObject1, i);
      Object localObject3 = localObject1;
      return localObject3;
    }
    catch (Exception localException1)
    {
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException4)
      {
      }
    }
    return null;
  }

  private String getMSSBase()
  {
    return "MySpeedServer/";
  }

  private String getWebDir()
  {
    return this.HE.getProperty("webdir", "myspeed/");
  }

  public String doGetReportURL()
  {
    try
    {
      if (isProfessional())
        return new URL(base(), TX("search.html")).toString();
    }
    catch (Throwable localThrowable)
    {
    }
    return null;
  }

  private String doGetReportDetailURL(String paramString)
  {
    try
    {
      if (isProfessional())
      {
        String str = iniGetString(TX("mss.dbserver"));
        if (str != null)
        {
          if (str.indexOf("://") < 0)
            str = "http://" + str;
          if (str.substring(str.indexOf("://") + 3).indexOf('/') < 0)
            str = str + "/" + getWebDir();
          if (!str.endsWith("/"))
            str = str + "/";
          return str + TX("db/report?id=") + paramString;
        }
        return new URL(base(), TX("db/report?id=") + paramString).toString();
      }
    }
    catch (Throwable localThrowable)
    {
    }
    return null;
  }

  private String getAnalysisPortalURL(String paramString)
  {
    String str1 = "speedq".equals(paramString) ? "speed" : paramString;
    String str2 = "speedq".equals(paramString) ? "act" : paramString;
    AppletPlugin localAppletPlugin = getPlugin(str1);
    if (localAppletPlugin != null)
    {
      String str3 = localAppletPlugin.getReportMSS();
      StringBuffer localStringBuffer = new StringBuffer(TX("http://www.connectionanalyzer.com/index.php?testtype=") + paramString);
      StringTokenizer localStringTokenizer = new StringTokenizer(str3 == null ? "" : str3, "&?");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str4 = localStringTokenizer.nextToken();
        if (str4.startsWith(str2 + TX(".")))
          localStringBuffer.append(TX("&") + str1 + "_" + str4.substring(str2.length() + 1));
      }
      return localStringBuffer.toString();
    }
    return null;
  }

  private boolean doINILoadedAndVersionCheck(Graphics paramGraphics)
  {
    String str1 = appNameVer(false);
    String str2 = iniGetString(TX("mss.appletversion"));
    str2 = str2 == null ? iniGetString(TX("version")) : str2;
    String str3 = iniGetString(TX("EOF"));
    if ((str2 == null) || (str3 == null))
    {
      drawLines(paramGraphics, TX("Error accessing '") + getMySpeedBinFilename() + TX("' file on server"), Color.red);
      return true;
    }
    if (!this.TD[0].equals(str2))
    {
      drawLines(paramGraphics, RC(TX("refresh"), new String[] { str1, str2 }), Color.red);
      return true;
    }
    return false;
  }

  private boolean doPluginsCheck(Graphics paramGraphics)
  {
    if ((this.SE == null) || (this.SE.length == 0))
    {
      if (this.AD)
        doDrawString(paramGraphics, TX("The test specified could not be loaded"), (getSize().height + paramGraphics.getFontMetrics().getHeight()) / 2, new Color(16777152));
      else
        this.GD = doDrawString(paramGraphics, TX("No plugins have been loaded. Either no plugins have been specified,\nor the plugins file cannot be found. Click here for assistance."), (getSize().height + paramGraphics.getFontMetrics().getHeight()) / 2, new Color(16777152));
      return true;
    }
    return false;
  }

  private void doJavaScriptReportResults(AppletPlugin paramAppletPlugin)
  {
    String str = paramAppletPlugin == null ? iniGetString(TX("js")) : iniGetString(TX("js") + "-" + paramAppletPlugin.getName());
    if (str != null)
    {
      str = doMySpeedVariableSubstitution(str, true, null);
      jsEval(str);
    }
  }

  private String doMySpeedVariableSubstitution(String paramString1, boolean paramBoolean, String paramString2)
  {
    String str1 = this.OE;
    String str2 = getSID();
    String str3 = paramBoolean ? "\"" : "";
    paramString1 = U.subst(paramString1, TX("$URL$"), str3 + base().toString() + str3);
    paramString1 = U.subst(paramString1, TX("$DATETIME$"), str3 + this.CD.toString() + str3);
    for (int i = 0; i < this.SE.length; i++)
    {
      paramString1 = this.SE[i].doMySpeedVariableSubstitution(paramString1, paramBoolean);
      str5 = this.SE[i].getName();
      String str6 = null;
      try
      {
        str6 = getAnalysisPortalURL(str5);
      }
      catch (Exception localException)
      {
        OUT("Exception handled: " + localException);
        localException.printStackTrace();
      }
      paramString1 = U.subst(paramString1, TX("$") + str5.toUpperCase() + TX(".ANALYSISLINK$"), str3 + (str6 == null ? "" : str6) + str3);
    }
    paramString1 = U.subst(paramString1, TX("$IP$"), str3 + G.g_trueIp + str3);
    paramString1 = U.subst(paramString1, TX("$EBODY$"), paramString2 != null ? paramString2 : "");
    paramString1 = U.subst(paramString1, TX("$MSSID$"), str3 + (str1 != null ? str1 : "NA") + str3);
    paramString1 = U.subst(paramString1, TX("$DETAILLINK$"), str3 + doGetReportDetailURL(this.OE) + str3);
    paramString1 = U.subst(paramString1, TX("$SID$"), str3 + (str2 != null ? str2 : "NA") + str3);
    String str4 = jsSafe(System.getProperty("java.version"));
    String str5 = jsSafe(System.getProperty("java.vendor"));
    paramString1 = U.subst(paramString1, TX("$JAVAVERSION$"), str3 + str4 + str3);
    paramString1 = U.subst(paramString1, TX("$JAVAVENDOR$"), str3 + str5 + str3);
    return paramString1;
  }

  private static String jsSafe(String paramString)
  {
    return paramString == null ? "[unknown]" : paramString.replace('\'', ' ').replace('"', ' ');
  }

  public void doOverlayMessages(Graphics paramGraphics)
  {
    int i = 0;
    un(paramGraphics, this.TD);
    String str = null;
    if (this.IE == 'v')
    {
      str = appNameVer(true);
    }
    else if (this.IE == 'c')
    {
      str = this.TD[1];
    }
    else if (this.IE == 'p')
    {
      str = this.TD[2];
    }
    else if (this.IE == 'j')
    {
      str = U.javaInfo(true);
    }
    else if ((this.IE == 's') || (this.IE == 'b') || (this.IE == 'e'))
    {
      str = gr(this.TD, this.IE);
    }
    else if (this.IE == 'm')
    {
      Runtime localRuntime = Runtime.getRuntime();
      str = localRuntime.totalMemory() / 1024L + TX("k  ") + localRuntime.freeMemory() / 1024L + TX("k");
    }
    else if ((this.JE > 0) && (4 != this.BD))
    {
      i = 1;
      str = iniGetString(TX("errorslogged"));
    }
    if (str != null)
      doDrawString(paramGraphics, str, getSize().height - 10, new Color(i != 0 ? 16765136 : 16777152));
  }

  private void doRepaint()
  {
    repaint();
  }

  private void doReport(String paramString)
  {
    try
    {
      String str = iniGetString(TX("reporturl"));
      if ((str != null) && (str.trim().length() > 0) && (isProfessional()))
      {
        long l = System.currentTimeMillis();
        URL localURL = new URL(getDocumentBase(), str + paramString);
        URLConnection localURLConnection = U.setNoCaching(localURL.openConnection());
        localURLConnection.getInputStream().close();
        OUT(TX("report --> url: ") + (System.currentTimeMillis() - l) + TX(" ms"));
      }
    }
    catch (Throwable localThrowable)
    {
    }
  }

  private void doReportException(String paramString)
  {
    doReport(TX("?exception=") + URLEncoder.encode(paramString));
  }

  private void doReportMSS(AppletPlugin paramAppletPlugin, String paramString1, String paramString2, String paramString3)
  {
    long l = System.currentTimeMillis();
    try
    {
      String str1 = getMSSBase();
      if ((str1 != null) && (isProfessional()) && (!"yes".equals(iniGetString("dontpostresults"))))
      {
        URL localURL1 = base();
        URL localURL2 = new URL(localURL1, str1 + TX("report"));
        URLConnection localURLConnection = U.setNoCaching(localURL2.openConnection());
        localURLConnection.setDoOutput(true);
        localURLConnection.setUseCaches(false);
        OutputStream localOutputStream = localURLConnection.getOutputStream();
        String str2 = getSID();
        String str3 = paramString2;
        String str4 = paramString3;
        String str5 = (paramString2 == null) || (paramString2.indexOf(TX("&plugin=")) < 0) ? TX("&plugin=") + paramString1 : TX("");
        int i = localURL1.getPort();
        String str6 = i > 0 ? ":" + i : "";
        String str7 = (paramString2 == null) || (paramString2.indexOf(TX("&testto=")) < 0) ? TX("&testto=") + localURL1.getHost() + str6 : TX("");
        String str8 = TX("&src=applet") + str3 + str7 + str5 + TX("&testspecid=") + this.YD + (this.DD > 0L ? TX("&testid=") + this.DD : "") + (G.g_trueIp != null ? TX("&ip=") + G.g_trueIp : "") + (str2 != null ? TX("&sid=") + URLEncoder.encode(str2) : "") + (str4 == null ? "" : str4);
        localOutputStream.write(U.stob(str8));
        DataInputStream localDataInputStream = new DataInputStream(localURLConnection.getInputStream());
        String str9;
        while ((str9 = localDataInputStream.readLine()) != null)
        {
          int j = str9.indexOf('=');
          if (j > 0)
          {
            String str10 = str9.substring(0, j);
            String str11 = str9.substring(j + 1);
            if (TX("mssid").equals(str10))
            {
              this.OE = str11;
              paramAppletPlugin.setMSSID(this.OE);
              OUT(TX("MSSID=") + this.OE);
            }
            else if (TX("testid").equals(str10))
            {
              try
              {
                this.DD = Long.parseLong(str11);
              }
              catch (Exception localException2)
              {
              }
              OUT(TX("Test ID=") + this.DD);
            }
          }
        }
      }
    }
    catch (Exception localException1)
    {
      ERR(TX("doReportMSS: ") + localException1);
      addError(new ErrorCode(33554435, "Unable to report " + paramString1 + " results to server (" + localException1.toString() + ")"));
    }
    finally
    {
      OUT(TX("report --> server: ") + (System.currentTimeMillis() - l) + TX(" ms"));
    }
  }

  public void doReportResults(AppletPlugin paramAppletPlugin)
  {
    if (isRunning())
    {
      ErrorCode localErrorCode = paramAppletPlugin.getErrorCode();
      long l = System.currentTimeMillis();
      String str1 = getSID();
      String str2;
      String str3;
      if (localErrorCode != null)
      {
        str2 = "&error.source=" + paramAppletPlugin.getName() + "&error.code=" + localErrorCode.nCode + "&error.desc=" + localErrorCode.desc;
        doReport(str2 + (this.JE > 0 ? TX("&e=") + this.JE : "") + (str1 != null ? TX("&s=") + URLEncoder.encode(str1) : "") + TX("&t=") + U.time());
        str3 = localErrorCode.detail == null ? "" : localErrorCode.detail;
        if (str3.indexOf("detail.console=") < 0)
          str3 = str3 + "&detail.console=" + URLEncoder.encode(paramAppletPlugin.getLog());
        doReportMSS(paramAppletPlugin, "error", str2, str3);
      }
      else
      {
        str2 = paramAppletPlugin.getReportMSS();
        doReport(str2 + (this.JE > 0 ? TX("&e=") + this.JE : "") + (str1 != null ? TX("&s=") + URLEncoder.encode(str1) : "") + TX("&t=") + U.time());
        str3 = paramAppletPlugin.getDetailResults();
        if (str3 == null)
          str3 = "";
        str3 = str3 + "&detail.console=" + URLEncoder.encode(paramAppletPlugin.getLog());
        doReportMSS(paramAppletPlugin, paramAppletPlugin.getName(), str2, str3);
        doJavaScriptReportResults(paramAppletPlugin);
      }
      OUT(TX("report (total): ") + (System.currentTimeMillis() - l) + TX(" ms"));
    }
  }

  //sr called by speedtest thread
  private void doRunThread()
  {
    try
    {
      this.KE = new Vector();
      this.ED = true;
      this.OE = null;
      this.DD = 0L;
      for (int i = 0; (this.SE != null) && (i < this.SE.length); i++)
        this.SE[i].notifyTestBegin();
      //sr get ticket or check the server if busy or not
      doGetTicket();
      int i = (this.LE != null) && (this.LE.startsWith(TX("MAX"))) ? 1 : 0;//sr int i
      int j;
      Object localObject1;
      if ((this.MF > 0) || (i != 0))
      {
        this.YE.setWait(i != 0 ? 0 : this.MF);
        this.YE.setLabel(RC(i != 0 ? TX("hitmaxtests") : TX("serverbusy")));
        j = isPaintingTabs() ? 60 : 0;
        localObject1 = this.YE.getPreferredSize();
        this.YE.setBounds(j + (getSize().width - j) / 2 - ((Dimension)localObject1).width / 2, getSize().height / 2 - ((Dimension)localObject1).height / 2, ((Dimension)localObject1).width, ((Dimension)localObject1).height);
        this.YE.setVisible(true);
        if (i == 0)
        {
          this.YE.doWait();
          this.YE.setVisible(false);
        }
      }
      Thread.currentThread().setPriority(10);
      if (i == 0)
      {
        this.YE.setVisible(false);
        OUT(TX("SID=") + getSID());
        OUT(TX("tickres=") + U.getTickResolution() + TX(" ms"));
        for (j = 0; (this.SE != null) && (j < this.SE.length); j++)
        {
          this.NF = this.SE[j].getOverrideSettings();
          this.SE[j].resetLog();
          dumpSettings(this.SE[j]);
          this.SE[j].runTest();
          localObject1 = this.SE[j].getErrorCode();
          if (localObject1 != null)
            addError((ErrorCode)localObject1);
          doReportResults(this.SE[j]);
          U.sleep(250L);
        }
        doJavaScriptReportResults(null);
        doFinalTabs();
        U.sleep(this.JE > 0 ? 3000 : 0);
        this.BD = 4;
        String str = RC(TX("finishtab"));
        if ((str != null) && (!str.trim().equals("")))
          delaySelectTab(TX("summary").equals(str) ? TX("summ") : str);
        doRepaint();
      }//end if i=0
      else
      {
        addError(new ErrorCode(33554434, "The maximum number of tests for this interval has been reached. Please try later."));
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      addError(new ErrorCode(33554433, "An error occurred: " + localThrowable));
      logException("Thread", localThrowable);
    }
    finally
    {
      runEpitaph();
    }
  }//end of doRunThread

  private void runEpitaph()
  {
    String str1 = iniGetString("js-final");
    if (str1 != null)
    {
      String str2 = "null";
      String str3 = "null";
      if ((this.KE != null) && (this.KE.size() > 0))
      {
        StringBuffer localStringBuffer1 = new StringBuffer();
        StringBuffer localStringBuffer2 = new StringBuffer();
        Enumeration localEnumeration = this.KE.elements();
        while (localEnumeration.hasMoreElements())
        {
          ErrorCode localErrorCode = (ErrorCode)localEnumeration.nextElement();
          String str4 = localErrorCode.desc;
          localStringBuffer1.append((localStringBuffer1.length() == 0 ? "" : ",") + localErrorCode.nCode);
          localStringBuffer2.append((localStringBuffer2.length() == 0 ? "" : ",") + "\"" + (str4 == null ? "" : str4.replace('"', '\'')) + "\"");
        }
        str2 = "[" + localStringBuffer1.toString() + "]";
        str3 = "[" + localStringBuffer2.toString() + "]";
      }
      str1 = Util.replace(str1, "$ERRCODES$", str2);
      str1 = Util.replace(str1, "$ERRMSGS$", str3);
      jsEval(str1);
    }
  }

  private void dumpSettings(AppletPlugin paramAppletPlugin)
  {
    String str1 = null;
    if (str1 == null)
    {
      Socket localSocket = null;
      try
      {
        URL localURL = getDocumentBase();
        int i = localURL.getPort();
        if (i < 0)
        {
          String str3 = localURL.getProtocol();
          str3 = str3 == null ? "http" : str3.toLowerCase();
          str3 = str3.endsWith("://") ? str3.substring(0, str3.length() - 3) : str3;
          i = "https".equals(str3) ? 443 : 80;
        }
        localSocket = new Socket(localURL.getHost(), i);
        str1 = localSocket.getLocalAddress().getHostAddress();
      }
      catch (Exception localException2)
      {
        try
        {
          localSocket.close();
        }
        catch (Exception localException3)
        {
        }
      }
      finally
      {
        try
        {
          localSocket.close();
        }
        catch (Exception localException4)
        {
        }
      }
    }
    if ((str1 == null) || ("255.255.255.255".equals(str1)) || ("127.0.0.1".equals(str1)))
      try
      {
        str1 = InetAddress.getLocalHost().getHostAddress();
      }
      catch (Exception localException1)
      {
      }
    if (str1 == null)
      str1 = "[unknown]";
    String str2 = paramAppletPlugin.getName();
    paramAppletPlugin.OUT(TX("---- ENVIRONMENT ---------------------------------"));
    paramAppletPlugin.OUT(TX("Local time = ") + new Date().toLocaleString());
    paramAppletPlugin.OUT(TX("Test server = ") + getCodeBase());
    paramAppletPlugin.OUT(TX("Local IP = ") + str1);
    paramAppletPlugin.OUT(TX("OS Name = ") + safeProperty("os.name"));
    paramAppletPlugin.OUT(TX("OS Version = ") + safeProperty("os.version"));
    paramAppletPlugin.OUT(TX("Java VM = ") + safeProperty("java.vendor") + " " + safeProperty("java.version"));
    paramAppletPlugin.OUT(TX("Tick Resolution = ") + U.getTickResolution() + TX(" ms"));
    paramAppletPlugin.OUT(TX("User Name = ") + safeProperty("user.name"));
    paramAppletPlugin.OUT(TX("User Home Directory = ") + safeProperty("user.home"));
    paramAppletPlugin.OUT(TX("--------------------------------------------------"));
    paramAppletPlugin.OUT(TX(""));
    paramAppletPlugin.OUT(TX("---- TEST CONFIGURATION (") + str2 + TX(") ") + TX("-----------------------------------------").substring(0, 24 - str2.length() - 1));
    paramAppletPlugin.OUT(TX("Configuration fields not mentioned take default values"));
    paramAppletPlugin.OUT(TX("Basic settings:"));
    dumpSettings(paramAppletPlugin, this.WD);
    paramAppletPlugin.OUT(TX("Override settings:"));
    dumpSettings(paramAppletPlugin, this.NF);
    paramAppletPlugin.OUT(TX("--------------------------------------------------"));
    paramAppletPlugin.OUT(TX(""));
    paramAppletPlugin.OUT(TX("---- TEST AUDIT LOG ------------------------------"));
  }

  private static String safeProperty(String paramString)
  {
    String str = null;
    try
    {
      str = System.getProperty(paramString);
    }
    catch (Exception localException)
    {
    }
    if (str == null)
      return "[Access denied by applet security]";
    str = str.replace('\r', '/');
    str = str.replace('\n', '/');
    str = str.replace('=', '-');
    str = str.replace('|', '/');
    return str;
  }

  private void dumpSettings(AppletPlugin paramAppletPlugin, Properties paramProperties)
  {
    String str1 = paramAppletPlugin.getName();
    Enumeration localEnumeration = paramProperties == null ? null : paramProperties.keys();
    if (localEnumeration == null)
      paramAppletPlugin.OUT(TX("  (no settings)"));
    while ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
    {
      String str2 = (String)localEnumeration.nextElement();
      if (str2.startsWith(str1 + "_"))
        paramAppletPlugin.OUT(TX("  ") + str2.substring(str1.length() + 1) + TX(" = ") + iniGetString(str2));
    }
  }

  private void doFinalTabs()
  {
    if (("yes".equals(iniGetString(TX("showgraphtab")))) && (this.DE.canDisplayGraph()))
      addTab(this.DE, false);
    Object localObject;
    if ("yes".equals(iniGetString(TX("showsummarytab"))))
    {
      localObject = new SummaryTab(this);
      if (((SummaryTab)localObject).hasData())
      {
        ((SummaryTab)localObject).doFirstTimeInit();
        addTab((AppletTab)localObject, false);
      }
    }
    if ("yes".equals(iniGetString(TX("showadvancedtab"))))
    {
      localObject = new AdvancedTab(this);
      if (((AdvancedTab)localObject).hasData())
      {
        ((AdvancedTab)localObject).doFirstTimeInit();
        addTab((AppletTab)localObject, false);
      }
    }
  }

  private boolean doGetTicket()
  {
    int i = -1;
    DataInputStream localDataInputStream = null;
    int j = 0;
    try
    {
      URL localURL = new URL(getFixedMySpeedURL(), getMSSBase() + TX("ticket?mver=") + iniGetInteger("maxuses", 0) + "&per=" + iniGetInteger("maxusesdays", 0));
      URLConnection localURLConnection = localURL.openConnection();
      localURLConnection.setDoInput(true);
      localDataInputStream = new DataInputStream(localURLConnection.getInputStream());
      this.LE = localDataInputStream.readLine();
      int k = this.LE.indexOf(',');
      j = (this.LE != null) && (this.LE.startsWith(TX("MAX"))) ? 1 : 0;
      i = j != 0 ? -1 : Integer.parseInt(this.LE.substring(k + 1));
      System.out.println("Server busy, wait=" + i + " ms");
    }
    catch (Exception localException1)
    {
      System.out.println("Error getting ticket:");
      localException1.printStackTrace();
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException3)
      {
      }
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException4)
      {
      }
    }
    if (j != 0)
    {
      String str = iniGetString("hitmaxtestsurl");
      if (str != null)
        doShowDocument(str);
      doRepaint();
    }
    if (i == -1)
      return false;
    try
    {
      this.MF = i;
      return true;
    }
    catch (Exception localException2)
    {
    }
    return false;
  }

  private boolean doSetCursor(int paramInt1, int paramInt2)
  {
    boolean bool1 = htNotLicensed(paramInt1, paramInt2);
    boolean bool2 = htNoPlugins(paramInt1, paramInt2);
    int i = (!htScrollUp(paramInt1, paramInt2)) && (!htScrollDown(paramInt1, paramInt2)) ? 0 : 1;
    int j = htTab(paramInt1, paramInt2);
    int k = j >= 0 ? 12 : bool2 ? 12 : bool1 ? 12 : i != 0 ? 0 : 0;
    setCursor(new Cursor(k));
    return true;
  }

  private void doShowDocument(String paramString)
  {
    try
    {
      int i = "no".equals(RC(TX("popupwindows"))) ? 0 : 1;
      URL localURL = new URL(base(), paramString);
      OUT("URL: " + localURL);
      getAppletContext().showDocument(localURL, i != 0 ? "_blank" : "_self");
    }
    catch (Throwable localThrowable)
    {
      ERR(" " + localThrowable);
    }
  }

  public void doStartButton()
  {
    if (isProfessional())
      doStartMySpeed(true);
  }

  //sr: start myspeed test
  public void doStartMySpeed(boolean paramBoolean)
  {
    int i = this.OF == null ? 1 : 0;
    int j = (this.OF != null) && (!this.OF.isAlive()) ? 1 : 0;
    if ((i != 0) || (j != 0))
    {
      if (isRequireUserSID())
      {
        if (getSID() == null)
        {
          this.XE.setVisible(true);
          this.XE.focus();
          return;
        }
        this.XE.setVisible(false);
      }
      if (paramBoolean)
        requestFocus();
      if (this.PF == null)
        this.PF = this.ME.getText();
      else
        this.ME.setText(this.PF);
      if (this.ED)
        for (int k = 0; (this.EE != null) && (k < this.EE.length); k++)
          if (this.FE[k] != 0)
          {
            this.EE[k].reset();
          }
          else
          {
            removeTab(k);
            k--;
          }
      this.OF = new Thread(this, TX("MySpeed-Test"));
      this.OF.start();
    }
  }

  private void doSubmit()
  {
    String str1 = iniGetProfessionalString(TX("submit.url"));
    if (str1 != null)
    {
      String str2 = iniGetProfessionalString(TX("submit.ebody"));
      str2 = str2 != null ? encode(doMySpeedVariableSubstitution(str2, false, null)) : null;
      str1 = doMySpeedVariableSubstitution(str1, false, str2);
      String str3 = TX("javascript:");
      if (str1.startsWith(str3))
        jsEval(str1.substring(str3.length()));
      else
        doShowDocument(str1);
    }
  }

  public boolean isShowingMessageOnGlass()
  {
    return (this.XE.isVisible()) || (this.YE.isVisible());
  }

  private void drawLines(Graphics paramGraphics, String paramString, Color paramColor)
  {
    if (paramGraphics != null)
    {
      int i = paramGraphics.getFontMetrics().getHeight();
      paramGraphics.setColor(paramColor);
      int j = 1;
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, TX("\n"), true);
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        if (str.equals(TX("\n")))
          j++;
        else
          paramGraphics.drawString(str, 5, i * j);
      }
    }
  }

  private long dt(String paramString)
  {
    try
    {
      return new Date(paramString).getTime();
    }
    catch (Exception localException)
    {
    }
    return 0L;
  }

  private static String encode(String paramString)
  {
    int i = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(2 * i);
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')))
      {
        localStringBuffer.append(c);
      }
      else if (c == ' ')
      {
        localStringBuffer.append(' ');
      }
      else
      {
        localStringBuffer.append('%');
        localStringBuffer.append(RD[(c / '\020' & 0xF)]);
        localStringBuffer.append(RD[(c & 0xF)]);
      }
    }
    return localStringBuffer.toString();
  }

  public void ERR(String paramString)
  {
    OUT(paramString);
    System.err.println(paramString);
  }

  public Image getBackgroundOverlay(String paramString)
  {
    String str1 = TX("speed").equals(paramString) ? TX("") : paramString;
    String str2 = iniGetProfessionalString(str1 + TX("backgroundoverlay"));
    return (str2 != null) && (str2.trim().length() > 0) ? waitForImageLoad(getImage(base(), str2)) : null;
  }

  private String getMySpeedBinFilename()
  {
    String str1 = TX("default.bin");
    String str2 = getParameter(TX("config"));
    str2 = str2 == null ? getParameter(TX("myspeed.bin")) : str2;
    if ((str2 != null) && (!str2.endsWith(TX(".bin"))))
      str2 = str2 + TX(".bin");
    return str2 != null ? str2 : str1;
  }

  private String getSID()
  {
    String str = iniGetProfessionalString(TX("sid"));
    if ((str != null) && (str.startsWith("*")))
      return str.trim().substring(1);
    return this.XE == null ? null : this.XE.getSID();
  }

  private boolean isRequireUserSID()
  {
    String str = iniGetProfessionalString(TX("sid"));
    return (str != null) && (str.trim().length() > 0) && (!str.startsWith("*"));
  }

  private String getSystemProperty(String paramString1, String paramString2)
  {
    return U.getSystemProperty(paramString1, paramString2);
  }

  private byte[] getUrlAsByteArray(URL paramURL)
  {
    byte[] arrayOfByte = new byte[1024];
    InputStream localInputStream = null;
    int i = 0;
    int j = 0;
    try
    {
      URLConnection localURLConnection = paramURL.openConnection();
      localURLConnection.setDoOutput(true);
      localURLConnection.setDoInput(true);
      G.g_server = G.g_server == null ? localURLConnection.getHeaderField(TX("Server")) : G.g_server;
      localInputStream = localURLConnection.getInputStream();
      while ((i = localInputStream.read()) != -1)
      {
        if (j >= arrayOfByte.length)
          break;
        arrayOfByte[(j++)] = ((byte)i);
      }
    }
    catch (Exception localException1)
    {
      System.out.println("exception" + localException1);
      try
      {
        localInputStream.close();
      }
      catch (Exception localException2)
      {
      }
    }
    finally
    {
      try
      {
        localInputStream.close();
      }
      catch (Exception localException3)
      {
      }
    }
    return (i == -1) && (j == 0) ? null : arrayOfByte;
  }

  private String getValue(String paramString1, String paramString2)
  {
    int i = paramString1.indexOf(paramString2 + '=');
    if (i >= 0)
    {
      i += paramString2.length() + 1;
      int j = paramString1.indexOf('\n', i);
      if (j > i)
        return paramString1.substring(i, j).trim();
    }
    return null;
  }

  public Date getTestTime()
  {
    return this.CD;
  }

  private URL getFixedMySpeedURL()
  {
    URL localURL = getCodeBase();
    String str = getWebDir();
    if ((str == null) || ("".equals(str)))
      return localURL;
    try
    {
      return new URL(localURL.getProtocol(), localURL.getHost(), localURL.getPort(), "/" + str);
    }
    catch (Exception localException)
    {
    }
    return localURL;
  }

  public String getUnregisteredMessage()
  {
    return gr(this.TD, TX("s")) == null ? this.TD[8] : null;
  }

  private void printKeyInfo()
  {
    System.out.println("Key details:");
    System.out.println("  From server = " + PD);
    System.out.println("  Key = " + QF);
  }

  protected String gr(String[] paramArrayOfString, String paramString)
  {
    synchronized (OD)
    {
      if (QF == null)
      {
        PD = false;
        QF = "";
        URL localURL = null;
        try
        {
          localURL = new URL(getFixedMySpeedURL(), TX("speedkey.bin?t=") + System.currentTimeMillis());
          QF = new String(getUrlAsByteArray(localURL), 0).trim();
        }
        catch (Exception localException1)
        {
          System.out.println("Unable to obtain license key from server (URL=" + localURL + ", exception=" + localException1 + ")");
          localException1.printStackTrace();
        }
        PD = (QF != null) && (!"".equals(QF));
      }
    }
    if (paramString == null)
    {
      System.out.println("License key error: no key barrel letter for retrieval supplied");
      printKeyInfo();
      return null;
    }
    if ("".equals(QF))
    {
      System.out.println("License key error: no key, unable to reach server");
      printKeyInfo();
      return null;
    }
    int i = QF.lastIndexOf('\n');
    if (i < 0)
    {
      System.out.println("License key error: key appears corrupt, cannot be parsed into tokens");
      printKeyInfo();
      return null;
    }
    long l = 0L;
    String str1 = QF.substring(0, i + 1);
    int j = str1.length();
    int k = 0;
    int n;
    for (int m = 0; m < j; m++)
    {
      n = str1.charAt(m);
      l ^= (n >= 32 ? (n + k++) * 785443476184546L : 0L);
    }
    str1 = ov(QF.substring(i + 1));
    BigInteger localBigInteger1 = new BigInteger(TX("17"));
    BigInteger localBigInteger2 = new BigInteger(TX("26075329981586256014826778307316413081100342489241164941735916710218199683450613936762038119503692856896790771011925193789826325440573707685306901116631814001488039351834072992680732261029139415817229935433528958829846749174217209853673181693834991953620185801811865052234349056025019224324944482894496127739909597472623175805983948050685722970627382362231012464575023377410262307171969892392797181845787151655822980524904270415783048215479616520119864449684338169536402614325743226357661722873985179204978323571404092577418924775071592504018134974714129061956127138048043139968751457054255226821371323990534743707059"));
    try
    {
      if ((PD) || ((l | 1L).equals(new String(new BigInteger(str1, 16).modPow(localBigInteger1, localBigInteger2).toByteArray()))))
      {
        String str2 = getValue(QF, "a");
        n = (!isANV(str2, paramArrayOfString[6])) && (!isANV(str2, "<*<MSS9>*>")) ? 0 : 1;
        System.out.println("License ANV check " + (n != 0 ? "passed" : "failed"));
        if (n == 0)
          printKeyInfo();
        return n != 0 ? getValue(QF, paramString) : null;
      }
      System.out.println("License rejected as it appears to be corrupt");
      printKeyInfo();
    }
    catch (Exception localException2)
    {
      System.out.println("License ANV check generated exception " + localException2);
      localException2.printStackTrace();
      printKeyInfo();
    }
    return TX("-");
  }

  private int htTab(int paramInt1, int paramInt2)
  {
    if (this.HD == null)
      return -1;
    for (int i = 0; i < this.HD.length; i++)
      if ((this.HD[i] != null) && (this.EE != null) && (this.EE.length > i) && (this.HD[i].contains(paramInt1, paramInt2)))
        return i;
    return -1;
  }

  private boolean htNotLicensed(int paramInt1, int paramInt2)
  {
    return (this.FD != null) && (this.FD.contains(paramInt1, paramInt2));
  }

  private boolean htNoPlugins(int paramInt1, int paramInt2)
  {
    return (this.GD != null) && (this.GD.contains(paramInt1, paramInt2));
  }

  private boolean htScrollUp(int paramInt1, int paramInt2)
  {
    return (this.RF != null) && (this.RF.contains(paramInt1, paramInt2));
  }

  private boolean htScrollDown(int paramInt1, int paramInt2)
  {
    return (this.SF != null) && (this.SF.contains(paramInt1, paramInt2));
  }

  public double iniGetDouble(String paramString, double paramDouble)
  {
    try
    {
      return Double.valueOf(iniGetString(paramString, null)).doubleValue();
    }
    catch (Throwable localThrowable)
    {
    }
    return paramDouble;
  }

  private int iniGetInteger(String paramString, int paramInt)
  {
    return iniGetInteger(paramString, null, paramInt);
  }

  public int iniGetInteger(String paramString1, String paramString2, int paramInt)
  {
    try
    {
      return Integer.parseInt(iniGetString(paramString1, paramString2));
    }
    catch (Throwable localThrowable)
    {
    }
    return paramInt;
  }

  public long iniGetLong(String paramString1, String paramString2, long paramLong)
  {
    try
    {
      return Long.parseLong(iniGetString(paramString1, paramString2));
    }
    catch (Throwable localThrowable)
    {
    }
    return paramLong;
  }

  public String iniGetProfessionalString(String paramString)
  {
    return isProfessional() ? iniGetString(paramString) : null;
  }

  private String iniGetString(String paramString)
  {
    return iniGetString(paramString, null);
  }

  public String iniGetString(String paramString1, String paramString2)
  {
    if ((!paramString1.equals(TX("mss.appletversion"))) && (!paramString1.equals(TX("mss.plugins"))) && (paramString1.startsWith(TX("mss."))) && (!isProfessional()))
      return null;
    String str = this.NF == null ? null : this.NF.getProperty(paramString1);
    str = (str != null) || (this.NF == null) || (paramString2 == null) ? str : this.NF.getProperty(paramString2);
    str = str != null ? str : getParameter(paramString1);
    str = (str != null) || (paramString2 == null) ? str : getParameter(paramString2);
    str = str != null ? str : this.WD.getProperty(paramString1);
    str = (str != null) || (paramString2 == null) ? str : this.WD.getProperty(paramString2);
    return str;
  }

  public int paramGetInteger(String paramString, int paramInt)
  {
    try
    {
      return Integer.parseInt(paramGetString(paramString));
    }
    catch (Throwable localThrowable)
    {
    }
    return paramInt;
  }

  public long paramGetLong(String paramString, long paramLong)
  {
    try
    {
      return Long.parseLong(paramGetString(paramString));
    }
    catch (Throwable localThrowable)
    {
    }
    return paramLong;
  }

  public String paramGetString(String paramString)
  {
    if ((!paramString.equals(TX("mss.appletversion"))) && (!paramString.equals(TX("mss.plugins"))) && (paramString.startsWith(TX("mss."))) && (!isProfessional()))
      return null;
    return getParameter(paramString);
  }

  private void initStatusWindow()
  {
    this.ME.setEditable(false);
    this.ME.setFont(new Font(TX("Courier"), 0, 12));
    this.ME.setBackground(new Color(16777215));
    this.ME.setForeground(new Color(0));
    JScrollPane localJScrollPane = new JScrollPane(this.ME);
    JPanel localJPanel = new JPanel(new BorderLayout());
    localJPanel.add(TX("Center"), localJScrollPane);
    this.LD.setContentPane(localJPanel);
    this.LD.setTitle(appNameVer(true) + TX(" Information"));
    this.LD.setLocation(0, 0);
    this.LD.pack();
    this.LD.addWindowListener(this);
    OUT(appNameVer(true) + TX(" - http://www.myspeed.com - ") + this.TD[1]);
    OUT(TX("java=") + U.javaInfo(true));
    OUT(TX("client=") + osInfo(true));
  }

  private boolean isANV(String paramString1, String paramString2)
  {
    int i = paramString2.length();
    return paramString1.indexOf(paramString2.substring(3, i - 3)) >= 0;
  }

  private boolean isHostDomainMatch(String paramString1, String paramString2)
  {
    if (paramString2.length() == 0)
      return false;
    if ((paramString1.equals(paramString2)) || (paramString1.endsWith(TX(".") + paramString2)))
      return true;
    return -1 == paramString1.indexOf('.');
  }

  public synchronized boolean isProfessional()
  {
    if (QD == null)
      QD = gr(this.TD, TX("p"));
    return TX("yes").equals(iniGetString(TX("license.mss.pro")));
  }

  private static String isProxyOrCache(URLConnection paramURLConnection)
  {
    String[] arrayOfString = { TX("Via"), TX("Age"), TX("Proxy-Connection"), TX("X-Cache"), TX("NetAnts") };
    Object localObject = null;
    for (int i = 0; i < arrayOfString.length; i++)
    {
      String str1 = arrayOfString[i];
      String str2 = paramURLConnection.getHeaderField(str1);
      if (str2 != null)
        localObject = (localObject != null ? localObject + TX(", ") : "") + str1 + TX("=") + str2;
    }
    return localObject;
  }

  private boolean isRunning()
  {
    return isActive();
  }

  private void jsEval(String paramString)
  {
    OUT(TX("Asking JavaScript to eval '") + paramString + TX("'"));
    try
    {
      Class.forName(TX("myspeedserver.applet.JS")).getMethod(TX("js"), new Class[] { Applet.class, String.class }).invoke(null, new Object[] { this, paramString });
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Throwable localThrowable2 = localInvocationTargetException.getTargetException();
      String str = " " + localThrowable2;
      if (str.toLowerCase().indexOf(TX("mayscript")) > 0)
      {
        ERR(TX("ERROR: JavaScript failure because 'MAYSCRIPT' flag within the <applet> tag is missing"));
      }
      else
      {
        ERR(" " + localThrowable2);
        localThrowable2.printStackTrace();
      }
    }
    catch (Throwable localThrowable1)
    {
      ERR(" " + localThrowable1);
      localThrowable1.printStackTrace();
    }
  }

  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getSource() == this)
    {
      int i = paramKeyEvent.getKeyChar();
      if (i == 105)
        this.LD.setVisible(true);
      if (i == 114)
        doStartButton();
      else if ((i == 120) && (this.IE == 'x'))
        try
        {
          throw new RuntimeException(TX("testing"));
        }
        catch (Throwable localThrowable)
        {
          logException(TX("Testing"), localThrowable);
        }
      this.IE = ((char)(i == this.IE ? 0 : i));
      doRepaint();
    }
  }

  public void keyReleased(KeyEvent paramKeyEvent)
  {
  }

  public void keyTyped(KeyEvent paramKeyEvent)
  {
  }

  private void loadINI()
  {
    BufferedReader localBufferedReader = null;
    String str1 = getMySpeedBinFilename();
    try
    {
      URL localURL1 = base();
      int i = localURL1.getHost().length() > 0 ? 1 : 0;
      String str2 = str1 + (i != 0 ? TX("?t=") + U.time() : "");
      URL localURL2 = new URL(localURL1, str2);
      URLConnection localURLConnection = U.setNoCaching(localURL2.openConnection());
      localBufferedReader = new BufferedReader(new CharArrayReader(streamToUnicode(localURLConnection.getInputStream())));
      Object localObject1 = "";
      String str3;
      String str6;
      String str7;
      while ((str3 = localBufferedReader.readLine()) != null)
        if (!str3.startsWith(TX("#")))
        {
          if (str3.startsWith(TX(" +")))
            str3 = localObject1 + "\n" + str3.substring(2);
          else if (str3.startsWith(TX("+")))
            str3 = localObject1 + "\n" + str3.substring(1);
          localObject1 = str3;
          int j = str3.indexOf('=');
          if (j > 0)
          {
            String str4 = str3.substring(0, j);
            str6 = str3.substring(j + 1);
            if ((str4.equals(TX("label"))) || (str4.equals(TX("voiplabel"))) || (str4.endsWith("^")))
            {
              if (str4.endsWith("^"))
                str4 = str4.substring(0, j - 1);
              str7 = (String)this.WD.get(str4);
              if (str7 != null)
                str6 = str7 + "\n" + str6;
            }
            this.WD.put(str4, str6);
          }
        }
      Object localObject2 = new StringBuffer();
      for (int k = 0; k < 50; k++)
      {
        str6 = localURLConnection.getHeaderField(k);
        if (str6 != null)
        {
          str7 = localURLConnection.getHeaderFieldKey(k);
          if (((StringBuffer)localObject2).length() > 0)
            ((StringBuffer)localObject2).append(TX(" "));
          ((StringBuffer)localObject2).append(str7).append(TX("=")).append(str6);
        }
      }
      this.XD = ((StringBuffer)localObject2).toString();
      localObject2 = isProxyOrCache(localURLConnection);
      G.g_bServerCache = localObject2 != null;
      G.g_server = localURLConnection.getHeaderField(TX("Server"));
      String str5 = base().toString();
      int m = str5.indexOf('?');
      if (m > 0)
        str5 = str5.substring(0, m);
      OUT(TX("url=") + str5);
      OUT(TX("server=") + G.g_server + (localObject2 != null ? TX(" [PROXY/CACHE DETECTED]") : TX("")));
      if (localObject2 != null)
        OUT(TX("WARNING: ") + (String)localObject2);
    }
    catch (Throwable localThrowable)
    {
      logException(str1, localThrowable);
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException1)
      {
      }
    }
    finally
    {
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }

  private void loadInternalSettings()
  {
    if (this.HE == null)
    {
      this.HE = new Properties();
      byte[] arrayOfByte = getDataFromJar("isettings.ini");
      if (arrayOfByte != null)
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(new String(arrayOfByte), "\r\n");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str = localStringTokenizer.nextToken();
          int i = str.indexOf("=");
          if (i > 0)
            this.HE.put(str.substring(0, i).trim(), str.substring(i + 1).trim());
        }
      }
    }
  }

  private void loadMSS(int paramInt)
  {
    String str1 = getMSSBase();
    if (str1 != null)
    {
      BufferedReader localBufferedReader = null;
      try
      {
        URL localURL1 = base();
        String str2 = str1 + TX("mss?t=") + U.time() + "&testid=" + paramInt;
        URL localURL2 = new URL(localURL1, str2);
        URLConnection localURLConnection = U.setNoCaching(localURL2.openConnection());
        localBufferedReader = new BufferedReader(new InputStreamReader(localURLConnection.getInputStream()));
        String str3;
        while ((str3 = localBufferedReader.readLine()) != null)
        {
          int i = str3.indexOf('=');
          if (i > 0)
          {
            String str4 = str3.substring(0, i);
            String str5 = str3.substring(i + 1);
            if (TX("mss.testspec").equals(str4))
              loadTestSpec(str5);
            else
              this.WD.put(str4, str5);
          }
        }
        G.g_trueIp = this.WD.getProperty("mss.clientip");
      }
      catch (Exception localException1)
      {
        ERR("--loadMSS: " + localException1);
        try
        {
          localBufferedReader.close();
        }
        catch (Exception localException2)
        {
        }
      }
      finally
      {
        try
        {
          localBufferedReader.close();
        }
        catch (Exception localException3)
        {
        }
      }
    }
  }

  private void loadTestSpec(String paramString)
  {
    StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, "|");
    if (localStringTokenizer1.countTokens() >= 2)
    {
      String str1 = "";
      localStringTokenizer1.nextToken();
      this.YD = 0;
      try
      {
        this.YD = Integer.parseInt(localStringTokenizer1.nextToken());
      }
      catch (Exception localException)
      {
      }
      while (localStringTokenizer1.hasMoreTokens())
      {
        String str2 = localStringTokenizer1.nextToken();
        int i = str2.indexOf('=');
        if (i > 0)
        {
          String str3 = str2.substring(0, i);
          str1 = str1 + str3 + ",";
          StringTokenizer localStringTokenizer2 = new StringTokenizer(str2.substring(i + 1), ",");
          while (localStringTokenizer2.hasMoreTokens())
          {
            String str4 = localStringTokenizer2.nextToken();
            i = str4.indexOf('=');
            if (i > 0)
              this.WD.put(str3 + "_" + str4.substring(0, i), str4.substring(i + 1));
          }
        }
      }
      this.RE = str1;
    }
  }

  public void logException(String paramString, Throwable paramThrowable)
  {
    boolean bool = paramThrowable instanceof ThreadDeath;
    if (!bool)
    {
      this.JE += 1;
      String str1 = paramString + TX(": ") + paramThrowable;
      ERR(TX("*** EXCEPTION: ") + str1 + TX(" ***"));
      paramThrowable.printStackTrace();
      doReportException(str1);
      if (this.JE == 1)
      {
        String str2 = this.XD;
        if (str2 != null)
          doReport(TX("?rheaders=") + URLEncoder.encode(str2));
      }
    }
  }

  private void addError(ErrorCode paramErrorCode)
  {
    if (this.KE != null)
      this.KE = new Vector();
    this.KE.add(paramErrorCode);
  }

  private boolean isMSSIDLinkOK()
  {
    return "yes".equals(iniGetString("mssidlink"));
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    int i = -1;
    if (htNotLicensed(localPoint.x, localPoint.y))
      doShowDocument(TX("http://www.myconnectionserver.com/support/v9/notlicensed.html"));
    else if (htNoPlugins(localPoint.x, localPoint.y))
      doShowDocument(TX("http://www.myconnectionserver.com/support/v9/noplugins.html"));
    else if ((!htScrollUp(localPoint.x, localPoint.y)) && (!htScrollDown(localPoint.x, localPoint.y)) && ((i = htTab(localPoint.x, localPoint.y)) >= 0))
      selectTab(i);
    doSetCursor(localPoint.x, localPoint.y);
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
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

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    doSetCursor(paramMouseEvent.getX(), paramMouseEvent.getY());
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    if (htScrollUp(localPoint.x, localPoint.y))
      startScrollThread(-10);
    else if (htScrollDown(localPoint.x, localPoint.y))
      startScrollThread(10);
    else
      killScrollThread();
    doSetCursor(localPoint.x, localPoint.y);
  }

  public void startScrollThread(int paramInt)
  {
    this.TF = paramInt;
    if (this.UF == null)
    {
      this.VF = true;
      this.UF = new Thread(this);
      this.UF.start();
    }
  }

  public void killScrollThread()
  {
    this.VF = false;
    try
    {
      this.UF.interrupt();
    }
    catch (Exception localException)
    {
    }
  }

  public void runScrollThread()
  {
    try
    {
      try
      {
        Thread.sleep(100L);
      }
      catch (Exception localException1)
      {
      }
      while (this.VF)
      {
        this.JD = Math.max(0, this.JD + this.TF);
        repaint();
        try
        {
          Thread.sleep(50L);
        }
        catch (Exception localException2)
        {
        }
      }
    }
    finally
    {
      this.UF = null;
    }
  }

  private String osInfo(boolean paramBoolean)
  {
    String str1 = getSystemProperty(TX("os.name"), "");
    String str2 = getSystemProperty(TX("os.version"), "");
    String str3 = getSystemProperty(TX("os.arch"), "");
    return str1 + TX(" ") + str2 + TX(" ") + str3;
  }

  public void OUT(String paramString)
  {
    this.ME.append(paramString + "\n");
  }

  private static String ov(String paramString)
  {
    char[] arrayOfChar = paramString.toLowerCase().toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++)
      if (arrayOfChar[i] > 'f')
        arrayOfChar[i] = ((char)(arrayOfChar[i] - 'f' + 48 - 1));
    return new String(arrayOfChar);
  }

  private void setPaintingTabs(boolean paramBoolean)
  {
    if (this.ZD != paramBoolean)
    {
      this.ZD = paramBoolean;
      for (int i = 0; (this.EE != null) && (i < this.EE.length); i++)
      {
        int j = this.ZD ? 60 : 0;
        this.EE[i].setBounds(j + 1, 1, getSize().width - j - 2, getSize().height - 3);
        this.EE[i].invalidate();
      }
      validate();
    }
  }

  private boolean isPaintingTabs()
  {
    return this.ZD;
  }

  private void paintTabs(Graphics paramGraphics)
  {
    int i = 50;
    int j = 30;
    int k = 60;
    int m = getSize().height;
    int n = getSize().width;
    if ((this.ZE != null) && (this.AE != null))
    {
      int i1 = this.ZE.getWidth(null);
      int i2 = this.AE.getWidth(null);
      i = (i1 == -1) && (i2 == -1) ? i : Math.min(i1 == -1 ? i2 : i1, i2 == -1 ? i1 : i2);
      k = (i1 == -1) && (i2 == -1) ? k : i;
    }
    paramGraphics.setColor(Color.white);
    paramGraphics.fillRect(0, 0, 60, m);
    Color localColor1 = new Color(204, 213, 230);
    Color localColor2 = new Color(226, 231, 242);
    Color localColor3 = new Color(238, 242, 248);
    Color localColor4 = new Color(133, 150, 202);
    paramGraphics.setColor(localColor1);
    paramGraphics.drawLine(59, 0, 59, m);
    paramGraphics.setColor(localColor2);
    paramGraphics.drawLine(58, 0, 58, m);
    paramGraphics.setColor(localColor3);
    paramGraphics.drawLine(57, 0, 57, m);
    paramGraphics.setColor(localColor4);
    paramGraphics.drawRect(60, 0, n - 60 - 1, m - 2);
    Font localFont = paramGraphics.getFont();
    paramGraphics.setFont(new Font("Helvetica", 1, 12));
    int i3 = -1;
    int i4 = 0;
    int i5 = 0;
    int i6 = this.EE == null ? 0 : this.EE.length;
    int i7 = j + k * i6;
    int i8 = i7 > m ? 1 : 0;
    int i9 = i7 - m + 20;
    if (i8 != 0)
      this.JD = Math.min(i9, this.JD);
    else
      this.JD = 0;
    paramGraphics.translate(0, -this.JD);
    Rectangle[] arrayOfRectangle = new Rectangle[i6];
    Object localObject;
    for (int i10 = 0; (this.EE != null) && (i10 < i6); i10++)
    {
      localObject = this.EE[i10];
      int i11 = this.GE == i10 ? 1 : 0;
      arrayOfRectangle[i10] = new Rectangle(0, j + k * i10 - this.JD, 60 - (i11 != 0 ? 3 : 4), i - 1);
      Image localImage1 = i11 != 0 ? this.ZE : this.AE;
      if (localImage1 != null)
      {
        if (i11 != 0)
        {
          i3 = i10;
          i4 = 60 - localImage1.getWidth(null) + 1;
          i5 = j + k * i10;
        }
        paramGraphics.drawImage(localImage1, 60 - localImage1.getWidth(null) + (i11 != 0 ? 1 : 0), j + k * i10, null);
      }
      Image localImage2 = ((AppletTab)localObject).getImage();
      if (localImage2 != null)
        paramGraphics.drawImage(localImage2, 60 - localImage2.getWidth(null) + (i11 != 0 ? 3 : 1), j + k * i10, null);
    }
    if ((i3 != -1) && (this.EE != null))
    {
      AppletTab localAppletTab = this.EE[i3];
      paramGraphics.drawImage(this.ZE, i4, i5, null);
      localObject = localAppletTab.getImage();
      if (localObject != null)
        paramGraphics.drawImage((Image)localObject, i4, i5, null);
    }
    paramGraphics.translate(0, this.JD);
    if ((i8 != 0) && (this.JD > 0))
    {
      this.RF = new Rectangle(8, 0, 49, 15);
      paramGraphics.setColor(Color.white);
      paramGraphics.fillRect(this.RF.x, this.RF.y, this.RF.width, this.RF.height);
      paramGraphics.setColor(Color.gray);
      paramGraphics.drawRect(this.RF.x, this.RF.y, this.RF.width, this.RF.height);
      if (this.BE != null)
        paramGraphics.drawImage(this.BE, this.RF.x + this.RF.width / 2 - this.BE.getWidth(null) / 2, this.RF.y + this.RF.height / 2 - this.BE.getHeight(null) / 2, null);
    }
    else
    {
      this.RF = null;
    }
    if ((i8 != 0) && (this.JD < i9))
    {
      this.SF = new Rectangle(8, m - 16, 49, 15);
      paramGraphics.setColor(Color.white);
      paramGraphics.fillRect(this.SF.x, this.SF.y, this.SF.width, this.SF.height);
      paramGraphics.setColor(Color.gray);
      paramGraphics.drawRect(this.SF.x, this.SF.y, this.SF.width, this.SF.height);
      if (this.CE != null)
        paramGraphics.drawImage(this.CE, this.SF.x + this.SF.width / 2 - this.CE.getWidth(null) / 2, this.SF.y + this.SF.height / 2 - this.CE.getHeight(null) / 2, null);
    }
    else
    {
      this.SF = null;
    }
    paramGraphics.setFont(localFont);
    this.HD = arrayOfRectangle;
  }

  public String RC(String paramString)
  {
    String str = iniGetString(paramString);
    return TX("?") + paramString + TX("?");
  }

  public String RC(String paramString1, String paramString2)
  {
    return RC(paramString1, new String[] { paramString2 });
  }

  public String RC(String paramString, String[] paramArrayOfString)
  {
    String str = RC(paramString);
    for (int i = 0; i < paramArrayOfString.length; i++)
      str = SUB(str, TX("%") + i, paramArrayOfString[i]);
    return str;
  }

  public void run()
  {
    Thread localThread = Thread.currentThread();
    if (localThread == this.OF)
      doRunThread();
    else if (localThread == this.PE)
      doDelaySwitchThread();
    else if (localThread == this.UF)
      runScrollThread();
  }

  private int getTabIndex(Object paramObject)
  {
    for (int i = 0; (this.EE != null) && (i < this.EE.length); i++)
      if ((this.EE[i] == paramObject) || (this.EE[i].getName().equals(paramObject)))
        return i;
    return -1;
  }

  public void delaySelectTab(AppletTab paramAppletTab)
  {
    int i = getTabIndex(paramAppletTab);
    if (i >= 0)
      delaySelectTab(i);
  }

  public void delaySelectTab(String paramString)
  {
    int i = getTabIndex(paramString);
    if (i >= 0)
      delaySelectTab(i);
  }

  private void delaySelectTab(int paramInt)
  {
    if ((this.EE != null) && (this.EE.length > paramInt))
    {
      if (this.PE != null)
      {
        this.KD = -1;
        try
        {
          Thread.sleep(75L);
        }
        catch (Exception localException1)
        {
        }
        if (this.PE != null)
        {
          try
          {
            this.PE.stop();
          }
          catch (Exception localException2)
          {
          }
          this.PE = null;
        }
      }
      this.KD = paramInt;
      this.PE = new Thread(this);
      this.PE.start();
    }
  }

  private void selectTab(int paramInt)
  {
    if ((this.EE != null) && (this.EE.length > paramInt))
    {
      this.GE = paramInt;
      this.KD = -1;
      for (int i = 0; i < this.EE.length; i++)
        this.EE[i].setVisible(i == paramInt);
      repaint();
    }
  }

  private void selectTab(String paramString)
  {
    int i = getTabIndex(paramString);
    if (i >= 0)
      selectTab(i);
  }

  private void hideTabs()
  {
    for (int i = 0; (this.EE != null) && (i < this.EE.length); i++)
      this.EE[i].setVisible(false);
  }

  public void stop()
  {
    this.LD.setVisible(false);
  }

  private char[] streamToUnicode(InputStream paramInputStream)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = (paramInputStream.read() & 0xFF | (paramInputStream.read() & 0xFF) << 8) == 65279 ? 1 : 0;
    int j;
    int k;
    while (((j = paramInputStream.read()) != -1) && ((k = paramInputStream.read()) != -1))
      localStringBuffer.append(i != 0 ? (char)(j & 0xFF | (k & 0xFF) << 8) : (char)(k & 0xFF | (j & 0xFF) << 8));
    return localStringBuffer.toString().toCharArray();
  }

  private String SUB(String paramString1, String paramString2, String paramString3)
  {
    int i;
    while ((i = paramString1.indexOf(paramString2)) >= 0)
      paramString1 = paramString1.substring(0, i) + paramString3 + paramString1.substring(i + paramString2.length());
    return paramString1;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }

  private String getMSSSignedTo()
  {
    DataInputStream localDataInputStream = null;
    try
    {
      URL localURL = new URL(getFixedMySpeedURL(), TX("maysite?dh=") + getDocumentBase().getHost().toLowerCase());
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
      localHttpURLConnection.setInstanceFollowRedirects(false);
      localHttpURLConnection.setDoInput(true);
      localDataInputStream = new DataInputStream(localHttpURLConnection.getInputStream());
      int i = localHttpURLConnection.getResponseCode();
      if ((i == 403) || (i == 302))
      {
        str2 = TX("U");
        return str2;
      }
      String str1 = localDataInputStream.readLine();
      if (TX("-").equals(str1))
        return null;
      if (TX("*").equals(str1))
      {
        str2 = TX("*");
        return str2;
      }
      str2 = TX("X");
      return str2;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      str2 = TX("*");
      return str2;
    }
    catch (Exception localException1)
    {
      String str2 = TX("E");
      return str2;
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException8)
      {
      }
    }
  }

  private boolean isServedByMSS()
  {
    String str = G.g_server == null ? "" : G.g_server.toLowerCase();
    return (str.indexOf("visualware") >= 0) || (str.indexOf("netqcheck") >= 0);
  }

  protected String un(Graphics paramGraphics, String[] paramArrayOfString)
  {
    Object localObject1;
    if (this.WF == null)
    {
      this.WF = "";
      this.UD = false;
      long l = dt(gr(paramArrayOfString, TX("e")));
      localObject1 = isServedByMSS() ? getMSSSignedTo() : gr(paramArrayOfString, TX("s"));
      if ((l > 0L) && (System.currentTimeMillis() >= l))
        this.WF = paramArrayOfString[4];
      else if (l == 0L)
        this.WF = TX("Unable to obtain license");
      else if (localObject1 == null)
        this.WF = paramArrayOfString[3];
      else if (!((String)localObject1).equals(paramArrayOfString[5]))
        if (((String)localObject1).equals(TX("E")))
        {
          this.WF = TX("Error loading applet - try hitting Ctrl+Refresh");
        }
        else if (((String)localObject1).equals(TX("U")))
        {
          this.WF = TX("User not permitted\nPlease log in to perform a test");
        }
        else
        {
          String str1 = getDocumentBase().getHost().toLowerCase();
          if (str1.length() != 0)
            if ("X".equals(localObject1))
            {
              this.WF = (TX("NOT LICENSED for '") + str1 + TX("'\nClick here for assistance with this error"));
              this.UD = true;
            }
            else
            {
              String str2 = TX("null");
              try
              {
                str2 = InetAddress.getByName(str1).getHostAddress();
              }
              catch (Throwable localThrowable)
              {
              }
              int k = 0;
              StringTokenizer localStringTokenizer = new StringTokenizer(((String)localObject1).toLowerCase(), TX("+"));
              Object localObject2;
              while (localStringTokenizer.hasMoreTokens())
              {
                localObject2 = localStringTokenizer.nextToken();
                if ((isHostDomainMatch(str1, (String)localObject2)) || (str2.equals(localObject2)))
                {
                  k = 1;
                  break;
                }
              }
              try
              {
                localObject2 = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
                for (int m = 0; m < localObject2.length; m++)
                  if (str2.equals(localObject2[m].getHostAddress()))
                  {
                    k = 1;
                    break;
                  }
              }
              catch (Exception localException)
              {
              }
              if (k == 0)
              {
                this.WF = (TX("NOT LICENSED for '") + str1 + TX("'\nClick here for assistance with this error"));
                this.UD = true;
              }
            }
        }
    }
    if ((this.WF.length() > 0) && (paramGraphics != null))
    {
      int i = paramGraphics.getFontMetrics().getHeight();
      int j = (getSize().height + i) / 2;
      localObject1 = doDrawString(paramGraphics, this.WF, j, new Color(16777152));
      if (this.UD)
        this.FD = ((Rectangle)localObject1);
    }
    return this.WF;
  }

  private Image waitForImageLoad(Image paramImage)
  {
    try
    {
      MediaTracker localMediaTracker = new MediaTracker(this);
      localMediaTracker.addImage(paramImage, 0);
      localMediaTracker.waitForAll();
      if ((paramImage.getWidth(null) > 0) && (paramImage.getHeight(null) > 0))
        return paramImage;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public void windowActivated(WindowEvent paramWindowEvent)
  {
  }

  public void windowClosed(WindowEvent paramWindowEvent)
  {
  }

  public void windowClosing(WindowEvent paramWindowEvent)
  {
    paramWindowEvent.getWindow().setVisible(false);
  }

  public void windowDeactivated(WindowEvent paramWindowEvent)
  {
  }

  public void windowDeiconified(WindowEvent paramWindowEvent)
  {
  }

  public void windowIconified(WindowEvent paramWindowEvent)
  {
  }

  public void windowOpened(WindowEvent paramWindowEvent)
  {
  }
  class MySpeedContentPane extends JPanel
{
  private MySpeedContentPane(myspeed parammyspeed)
  {
  }

  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    String str = this.JF.un(paramGraphics, myspeed.access$0(this.JF));
    if ((str != "") && (str != myspeed.access$0(this.JF)[3]))
    {
      myspeed.access$1(this.JF);
      return;
    }
    if (myspeed.access$2(this.JF, paramGraphics))
    {
      myspeed.access$1(this.JF);
      return;
    }
    if (myspeed.access$3(this.JF, paramGraphics))
    {
      myspeed.access$1(this.JF);
      return;
    }
    if (myspeed.access$4(this.JF, paramGraphics))
    {
      myspeed.access$1(this.JF);
      return;
    }
    boolean bool = ((myspeed.access$5(this.JF) != null) && (myspeed.access$5(this.JF).length > 1)) || ("no".equals(this.JF.RC(myspeed.access$6("hideonetab"))));
    myspeed.access$7(this.JF, bool);
    if (myspeed.access$8(this.JF))
      myspeed.access$9(this.JF, paramGraphics);
  }
}
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.myspeed
 * JD-Core Version:    0.6.2
 */