package mcsaplugins.route;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.StringTokenizer;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.JS;
import myspeedserver.applet.U;
import myspeedserver.applet.myspeed;

public class route extends AppletPlugin
{
  private static route m_r = null;
  private DetachFrame m_fDetach = null;
  public static final String TYPE_BOOL_TRUE = "yes";
  public static final String TYPE_BOOL_FALSE = "no";
  private static final String RCTAG_TAB_NAME = "rcTabName";
  private static final String IMAGE_TAB = "tabroute.gif";
  private RouteTab m_rtTab = null;
  private Applet m_applet = null;
  private String m_go = null;
  private String[] m_traceList = null;
  private String m_traceFrom = null;
  private long m_lIdDatabase = 0L;
  private int m_nServerIdDatabase = 0;
  private boolean m_bIsSelfAutoGo = false;
  private boolean m_bIsAutoGo = false;
  private boolean m_bUserStart = false;
  private boolean m_bUserDriven = false;
  private boolean m_bDatabaseViewMode = false;

  public route(Applet paramApplet)
  {
    super(paramApplet, "route");
    this.m_applet = paramApplet;
    m_r = this;
  }

  public void doFirstTimeInit()
  {
    AppletPlugin[] arrayOfAppletPlugin = super.getPlugins();
    int i = arrayOfAppletPlugin.length <= 1 ? 1 : 0;
    this.m_bIsSelfAutoGo = (!iniGetTestBoolean("allowtraceany"));
    this.m_bIsAutoGo = "yes".equals(super.iniGetString("mss.autostart"));
    this.m_bDatabaseViewMode = iniGetTestBoolean("databaseViewMode");
    this.m_lIdDatabase = iniGetTestLong("lIdDatabase", 0L);
    this.m_nServerIdDatabase = iniGetTestInt("lServerIdDatabase", 0);
    this.m_traceFrom = iniGetTestString("tracefrom");
    if ((this.m_traceFrom != null) && (this.m_traceFrom.length() == 0))
      this.m_traceFrom = null;
    initTraceList();
    if ((isAutoGo()) || (i == 0) || (isSelfAutoGo()))
    {
      this.m_bUserDriven = false;
      if (isAutoGo())
        this.m_bUserStart = false;
      else
        this.m_bUserStart = true;
    }
    else
    {
      this.m_go = ((myspeed)this.m_applet).getUrlStringParameter("go");
      this.m_bUserDriven = true;
    }
    U.g_vrApplet = this;
    G.g_vrApplet = this;
    HopServerQuery.g_applet = this;
    this.m_rtTab = new RouteTab(this, RC(getName() + "." + "rcTabName"), getImage("tabroute.gif"));
    this.m_rtTab.doFirstTimeInit();
    addTabToApplet(this.m_rtTab, true);
    this.m_rtTab.validate();
    this.m_rtTab.initAppPanel();
  }

  public RouteTab getRouteTab()
  {
    return this.m_rtTab;
  }

  public String getGo()
  {
    return this.m_go;
  }

  public String getTraceFrom()
  {
    return this.m_traceFrom;
  }

  private void initTraceList()
  {
    String[] arrayOfString = null;
    Object localObject = iniGetTestString("defaultaddress");
    String str1 = iniGetTestString("tracelist");
    String str2 = getLocalHostAddress();
    if (getTraceFrom() != null)
      str2 = getAppletCodeBase().getHost();
    if ("CLIENT".equals(localObject))
      localObject = str2;
    if ((localObject == null) && (str1 == null))
    {
      arrayOfString = new String[1];
      arrayOfString[0] = str2;
      this.m_traceList = arrayOfString;
      return;
    }
    if ((isSelfAutoGo()) && (str1.length() != 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, "+");
      arrayOfString = new String[localStringTokenizer.countTokens()];
      for (int i = 0; localStringTokenizer.hasMoreTokens(); i++)
      {
        String str3 = localStringTokenizer.nextToken();
        if ("CLIENT".equals(str3))
          str3 = str2;
        arrayOfString[i] = str3;
      }
      this.m_traceList = arrayOfString;
      return;
    }
    if (((String)localObject).length() == 0)
    {
      arrayOfString = new String[1];
      arrayOfString[0] = str2;
      this.m_traceList = arrayOfString;
      return;
    }
    arrayOfString = new String[1];
    arrayOfString[0] = localObject;
    this.m_traceList = arrayOfString;
  }

  private String getLocalHostAddress()
  {
    String str1 = "";
    try
    {
      str1 = InetAddress.getLocalHost().getHostAddress();
    }
    catch (Exception localException)
    {
    }
    String str2 = super.iniGetString("mss.clientip");
    if (str2 == null)
      return str1;
    return str2;
  }

  public boolean isDatabaseViewMode()
  {
    return this.m_bDatabaseViewMode;
  }

  public long getDatabaseID()
  {
    return this.m_lIdDatabase;
  }

  public long getDatabaseServerID()
  {
    return this.m_nServerIdDatabase;
  }

  public boolean isUserStart()
  {
    return this.m_bUserStart;
  }

  public boolean isAutoGo()
  {
    return this.m_bIsAutoGo;
  }

  public boolean isSelfAutoGo()
  {
    return this.m_bIsSelfAutoGo;
  }

  public boolean isUserDriven()
  {
    return this.m_bUserDriven;
  }

  public String[] getTraceList()
  {
    return this.m_traceList;
  }

  public String doMySpeedVariableSubstitution(String paramString, boolean paramBoolean)
  {
    if (this.m_rtTab == null)
      return paramString;
    if (this.m_rtTab.getAppletPanel() == null)
      return paramString;
    return this.m_rtTab.getAppletPanel().doMySpeedVariableSubstitution(paramString, paramBoolean);
  }

  public String getName()
  {
    return "route";
  }

  public boolean getSetting(String paramString, boolean paramBoolean)
  {
    paramString = getName() + "." + paramString;
    String str = RC(paramString);
    if (str == null)
      return paramBoolean;
    return "yes".equals(str);
  }

  public int getSetting(String paramString, int paramInt)
  {
    return iniGetInteger(paramString, paramInt);
  }

  public int iniGetInteger(String paramString, int paramInt)
  {
    paramString = getName() + "." + paramString;
    return super.iniGetInteger(paramString, paramInt);
  }

  public String iniGetString(String paramString)
  {
    paramString = getName() + "." + paramString;
    return super.iniGetString(paramString);
  }

  public boolean iniGetTestBoolean(String paramString)
  {
    paramString = getName() + "_" + paramString;
    return "yes".equals(super.iniGetString(paramString));
  }

  public int iniGetTestInt(String paramString, int paramInt)
  {
    try
    {
      return Integer.parseInt(iniGetTestString(paramString));
    }
    catch (Exception localException)
    {
    }
    return paramInt;
  }

  public long iniGetTestLong(String paramString, long paramLong)
  {
    try
    {
      return Long.parseLong(iniGetTestString(paramString));
    }
    catch (Exception localException)
    {
    }
    return paramLong;
  }

  public String iniGetTestString(String paramString)
  {
    paramString = getName() + "_" + paramString;
    return super.iniGetString(paramString);
  }

  public String getSetting(String paramString1, String paramString2)
  {
    paramString1 = getName() + "." + paramString1;
    String str = RC(paramString1);
    return str == null ? paramString2 : str;
  }

  public Image getAppletImage(String paramString)
  {
    byte[] arrayOfByte = getAppletData(paramString);
    if ((this.m_rtTab != null) && (arrayOfByte != null))
      return this.m_rtTab.createAppletImage(arrayOfByte);
    return null;
  }

  public Image getRemoteImage(URL paramURL)
  {
    return this.m_applet.getImage(paramURL);
  }

  private byte[] getAppletData(String paramString)
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

  public void showDocument(URL paramURL, String paramString)
  {
    this.m_applet.getAppletContext().showDocument(paramURL, paramString);
  }

  public URL getAppletCodeBase()
  {
    return base();
  }

  public String getHost()
  {
    return this.m_applet.getDocumentBase().getHost();
  }

  public void js(String paramString)
  {
    JS.js(this.m_applet, paramString);
  }

  public void detach()
  {
    if (this.m_fDetach != null)
    {
      this.m_fDetach.requestFocus();
      this.m_fDetach.toFront();
      return;
    }
    this.m_rtTab.setDetached(true);
    this.m_fDetach = new DetachFrame(this.m_rtTab.getAppletPanel(), this);
  }

  public void attach()
  {
    this.m_rtTab.setDetached(false);
    this.m_fDetach.setVisible(false);
    this.m_fDetach = null;
  }

  public void doLogoff()
  {
  }

  public Applet getParentApplet()
  {
    return getApplet();
  }

  public ErrorCode getErrorCode()
  {
    return this.m_rtTab.getErrorCode();
  }

  public String getReportMSS()
  {
    String str = this.m_rtTab.getReportMSS();
    return str;
  }

  public void doReportResult()
  {
    doReportResults();
  }

  public String getDetailResults()
  {
    return this.m_rtTab.getDetailReport();
  }

  public void runTest()
  {
    doAppletDelaySelectTab(this.m_rtTab);
    this.m_rtTab.runTest();
  }

  public void notifyTestBegin()
  {
  }

  public static route getRoute()
  {
    return m_r;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.route
 * JD-Core Version:    0.6.2
 */