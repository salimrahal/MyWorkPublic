package mcsaplugins.speed;

import java.applet.Applet;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.U;

public class speed extends AppletPlugin
{
  private SpeedTest m_test;
  private SpeedTab m_tab;
  private RTTTab m_rttTab;

  public speed(Applet paramApplet)
  {
    super(paramApplet, TX("speed"));
  }

  public SpeedTest getSpeedTest()
  {
    return this.m_test;
  }

  public AppletTest getTest()
  {
    return this.m_test;
  }

  public void notifyTestBegin()
  {
    this.m_tab.stopAnimation();
    this.m_tab.repaint();
  }

  public synchronized void doRepaint()
  {
    if (this.m_tab != null)
      this.m_tab.repaint();
    if ((this.m_rttTab == null) && (this.m_test != null) && (this.m_test.getState() >= 6) && (TX("yes").equals(iniGetString(TX("speed.rttshowtab")))))
    {
      this.m_rttTab = new RTTTab(getApplet(), this);
      addTabToApplet(this.m_rttTab, false);
    }
    if (this.m_rttTab != null)
      this.m_rttTab.repaint();
  }

  public void doFirstTimeInit()
  {
    this.m_tab = new SpeedTab(getApplet(), this);
    this.m_tab.doFirstTimeInit();
    addTabToApplet(this.m_tab, true);
  }

  public void reset()
  {
    this.m_test = null;
    this.m_rttTab = null;
  }

  public void runTest()
  {
    doAppletDelaySelectTab(this.m_tab);
    this.m_test = new SpeedTest(this, this.m_tab);
    this.m_test.runTest();
  }

  public void setMSSID(String paramString)
  {
    super.setMSSID(paramString);
    if (this.m_tab != null)
      this.m_tab.repaint();
  }

  public String doMySpeedVariableSubstitution(String paramString, boolean paramBoolean)
  {
    if (this.m_test == null)
      return paramString;
    String str = paramBoolean ? "\"" : "";
    paramString = U.subst(paramString, TX("$SPEED.DSPEED$"), Long.toString(this.m_test.getDownloadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$SPEED.CDSPEED$"), str + U.addCommas(this.m_test.getDownloadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$SPEED.USPEED$"), Long.toString(this.m_test.getUploadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$SPEED.CUSPEED$"), str + U.addCommas(this.m_test.getUploadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$SPEED.BANDWIDTH$"), Long.toString(this.m_test.getBandwidth() * 8L));
    paramString = U.subst(paramString, TX("$SPEED.QOS$"), Integer.toString(this.m_test.getDownloadQOS()));
    paramString = U.subst(paramString, TX("$SPEED.UQOS$"), Integer.toString(this.m_test.getUploadQOS()));
    paramString = U.subst(paramString, TX("$SPEED.RTT$"), Integer.toString(this.m_test.getRTT()));
    paramString = U.subst(paramString, TX("$SPEED.AVGRTT$"), Integer.toString(this.m_test.getAvgRTT()));
    paramString = U.subst(paramString, TX("$SPEED.RTTCONSISTENCY$"), Integer.toString(this.m_test.getRTTConsistency()));
    paramString = U.subst(paramString, TX("$SPEED.MAXPAUSE$"), Integer.toString(this.m_test.getDownloadMaxPause()));
    paramString = U.subst(paramString, TX("$SPEED.AVGPAUSE$"), Integer.toString(this.m_test.getDownloadAvgPause()));
    paramString = U.subst(paramString, TX("$SPEED.ROUTESPEED$"), Long.toString(this.m_test.getTCPMaxRouteSpeed() * 8L));
    paramString = U.subst(paramString, TX("$SPEED.FORCEDIDLE$"), Long.toString(this.m_test.getDownloadForcedIdle()));
    paramString = U.subst(paramString, TX("$SPEED.TIMERACCURACY$"), Integer.toString(U.getTickResolution()));
    paramString = U.subst(paramString, TX("$SPEED.ROUTECONC$"), this.m_test.getDownloadRouteConcurrency());
    paramString = U.subst(paramString, TX("$SPEED.DTESTTYPE$"), str + this.m_test.getDownloadTypeText() + str);
    paramString = U.subst(paramString, TX("$SPEED.UTESTTYPE$"), str + this.m_test.getUploadTypeText() + str);
    paramString = U.subst(paramString, TX("$DSPEED$"), Long.toString(this.m_test.getDownloadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$CDSPEED$"), str + U.addCommas(this.m_test.getDownloadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$USPEED$"), Long.toString(this.m_test.getUploadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$CUSPEED$"), str + U.addCommas(this.m_test.getUploadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$BANDWIDTH$"), Long.toString(this.m_test.getBandwidth() * 8L));
    paramString = U.subst(paramString, TX("$QOS$"), Integer.toString(this.m_test.getDownloadQOS()));
    paramString = U.subst(paramString, TX("$AVGRTT$"), Integer.toString(this.m_test.getAvgRTT()));
    paramString = U.subst(paramString, TX("$RTT$"), Integer.toString(this.m_test.getRTT()));
    paramString = U.subst(paramString, TX("$MAXPAUSE$"), Integer.toString(this.m_test.getDownloadMaxPause()));
    paramString = U.subst(paramString, TX("$AVGPAUSE$"), Integer.toString(this.m_test.getDownloadAvgPause()));
    paramString = U.subst(paramString, TX("$ROUTESPEED$"), Long.toString(this.m_test.getTCPMaxRouteSpeed() * 8L));
    paramString = U.subst(paramString, TX("$FORCEDIDLE$"), Long.toString(this.m_test.getDownloadForcedIdle()));
    paramString = U.subst(paramString, TX("$ROUTECONC$"), this.m_test.getDownloadRouteConcurrency());
    paramString = U.subst(paramString, TX("$DTESTTYPE$"), str + this.m_test.getDownloadTypeText() + str);
    paramString = U.subst(paramString, TX("$UTESTTYPE$"), str + this.m_test.getUploadTypeText() + str);
    return paramString;
  }

  public String getReportMSS()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Hashtable localHashtable = this.m_test.getAccessCTMetrics();
    int i = localHashtable != null ? 1 : 0;
    String str1 = "";
    String str2 = TX("speed");
    if (i != 0)
    {
      str2 = TX("act");
      str1 = "&plugin=act";
    }
    Enumeration localEnumeration = localHashtable == null ? null : localHashtable.keys();
    while ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
    {
      str3 = (String)localEnumeration.nextElement();
      if (!str3.startsWith("detail."))
        localStringBuffer.append(TX("&") + str2 + TX(".") + str3 + TX("=") + (String)localHashtable.get(str3));
    }
    String str3 = i != 0 ? TX("maxlinespeed") : TX("bandwidth");
    long l1 = i != 0 ? 1000 : 1;
    String str4 = i != 0 ? TX("maxroutespeed") : TX("routespeed");
    long l2 = i != 0 ? 1000 : 1;
    String str5 = i != 0 ? TX("tcpforcedidle") : TX("forcedidle");
    String str6 = i != 0 ? TX("concurrenttcp") : TX("routeconc");
    boolean bool = this.m_test.isRunRTT();
    return str1 + TX("&") + str2 + TX(".dspeed=") + this.m_test.getDownloadSpeed() * 8L + TX("&") + str2 + TX(".uspeed=") + this.m_test.getUploadSpeed() * 8L + TX("&") + str2 + TX(".qos=") + this.m_test.getDownloadQOS() + TX("&") + str2 + TX(".uqos=") + this.m_test.getUploadQOS() + TX("&") + str2 + TX(".dtesttype=") + this.m_test.getDownloadType() + TX("&") + str2 + TX(".utesttype=") + this.m_test.getUploadType() + TX("&") + str2 + TX(".") + str3 + TX("=") + this.m_test.getBandwidth() * 8L / l1 + TX("&") + str2 + TX(".avgpause=") + this.m_test.getDownloadAvgPause() + TX("&") + str2 + TX(".") + str4 + TX("=") + this.m_test.getTCPMaxRouteSpeed() * 8L / l2 + TX("&") + str2 + TX(".") + str5 + TX("=") + this.m_test.getDownloadForcedIdle() + TX("&") + str2 + TX(".") + str6 + TX("=") + this.m_test.getDownloadRouteConcurrency() + (bool ? TX("&") + str2 + TX(".rtt=") + this.m_test.getRTT() : "") + (bool ? TX("&") + str2 + TX(".rttmax=") + this.m_test.getMaxRTT() : "") + (bool ? TX("&") + str2 + TX(".rttavg=") + this.m_test.getAvgRTT() : "") + TX("&") + str2 + TX(".rttconsistency=") + this.m_test.getRTTConsistency() + TX("&") + str2 + TX(".maxpause=") + this.m_test.getDownloadMaxPause() + localStringBuffer;
  }

  public String getDetailResults()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Hashtable localHashtable = this.m_test.getAccessCTMetrics();
    Enumeration localEnumeration = localHashtable == null ? null : localHashtable.keys();
    while ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
    {
      localObject = (String)localEnumeration.nextElement();
      if (((String)localObject).startsWith("detail."))
        localStringBuffer.append(TX("&") + (String)localObject + TX("=") + (String)localHashtable.get(localObject));
    }
    Object localObject = compress(this.m_test.getDownloadTimesArray(), this.m_test.getDownloadBytesArray());
    int[][] arrayOfInt = compress(this.m_test.getUploadTimesArray(), this.m_test.getUploadBytesArray());
    if (localObject != null)
    {
      localStringBuffer.append(TX("&detail.dms=") + URLEncoder.encode(toString(localObject[0])));
      localStringBuffer.append(TX("&detail.dby=") + URLEncoder.encode(toString(localObject[1])));
    }
    if (arrayOfInt != null)
    {
      localStringBuffer.append(TX("&detail.ums=") + URLEncoder.encode(toString(arrayOfInt[0])));
      localStringBuffer.append(TX("&detail.uby=") + URLEncoder.encode(toString(arrayOfInt[1])));
    }
    return localStringBuffer.toString();
  }

  private static int[][] compress(long[] paramArrayOfLong, int[] paramArrayOfInt)
  {
    if ((paramArrayOfLong == null) || (paramArrayOfInt == null))
      return null;
    int[] arrayOfInt1 = new int[paramArrayOfLong.length];
    int[] arrayOfInt2 = new int[paramArrayOfInt.length];
    int i = 0;
    int j = 0;
    while (j < arrayOfInt1.length)
    {
      int k = 0;
      int m = 0;
      for (k = 0; k + j < arrayOfInt1.length; k++)
      {
        if (paramArrayOfLong[(k + j)] != paramArrayOfLong[j])
          break;
        m += paramArrayOfInt[(k + j)];
      }
      arrayOfInt1[i] = ((int)paramArrayOfLong[j]);
      arrayOfInt2[i] = m;
      i++;
      j += k;
    }
    int[][] arrayOfInt = new int[2][i];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt[0], 0, i);
    System.arraycopy(arrayOfInt2, 0, arrayOfInt[1], 0, i);
    return arrayOfInt;
  }

  private static String toString(int[] paramArrayOfInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; (paramArrayOfInt != null) && (i < paramArrayOfInt.length); i++)
    {
      if (localStringBuffer.length() > 0)
        localStringBuffer.append(',');
      localStringBuffer.append(paramArrayOfInt[i]);
    }
    return localStringBuffer.toString();
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.speed.speed
 * JD-Core Version:    0.6.2
 */