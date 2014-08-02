package mcsaplugins.hispeed;

import java.applet.Applet;
import java.net.URLEncoder;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.U;

public class hispeed extends AppletPlugin
{
  private HiSpeedTest m_test;
  private HiSpeedTab m_tab;

  public hispeed(Applet paramApplet)
  {
    super(paramApplet, TX("hispeed"));
  }

  public HiSpeedTest getHiSpeedTest()
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

  public void doFirstTimeInit()
  {
    this.m_tab = new HiSpeedTab(getApplet(), this);
    this.m_tab.doFirstTimeInit();
    addTabToApplet(this.m_tab, true);
  }

  public void reset()
  {
    this.m_test = null;
  }

  public void runTest()
  {
    doAppletDelaySelectTab(this.m_tab);
    this.m_test = new HiSpeedTest(this, this.m_tab);
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
    paramString = U.subst(paramString, TX("$HISPEED.DSPEED$"), Long.toString(this.m_test.getDownloadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$HISPEED.CDSPEED$"), str + U.addCommas(this.m_test.getDownloadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$HISPEED.USPEED$"), Long.toString(this.m_test.getUploadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$HISPEED.CUSPEED$"), str + U.addCommas(this.m_test.getUploadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$HISPEED.BANDWIDTH$"), Long.toString(this.m_test.getBandwidth() * 8L));
    paramString = U.subst(paramString, TX("$HISPEED.QOS$"), Integer.toString(this.m_test.getDownloadQOS()));
    paramString = U.subst(paramString, TX("$HISPEED.UQOS$"), Integer.toString(this.m_test.getUploadQOS()));
    paramString = U.subst(paramString, TX("$HISPEED.RTT$"), Integer.toString(this.m_test.getRTT()));
    paramString = U.subst(paramString, TX("$HISPEED.AVGRTT$"), Integer.toString(this.m_test.getAvgRTT()));
    paramString = U.subst(paramString, TX("$HISPEED.MAXPAUSE$"), Integer.toString(this.m_test.getDownloadMaxPause()));
    paramString = U.subst(paramString, TX("$HISPEED.AVGPAUSE$"), Integer.toString(this.m_test.getDownloadAvgPause()));
    paramString = U.subst(paramString, TX("$HISPEED.ROUTESPEED$"), Long.toString(this.m_test.getTCPMaxRouteSpeed() * 8L));
    paramString = U.subst(paramString, TX("$HISPEED.FORCEDIDLE$"), Long.toString(this.m_test.getDownloadForcedIdle()));
    paramString = U.subst(paramString, TX("$HISPEED.ROUTECONC$"), this.m_test.getDownloadRouteConcurrency());
    return paramString;
  }

  public String getReportMSS()
  {
    return TX("&hispeed.dspeed=") + this.m_test.getDownloadSpeed() * 8L + TX("&hispeed.uspeed=") + this.m_test.getUploadSpeed() * 8L + TX("&hispeed.qos=") + this.m_test.getDownloadQOS() + TX("&hispeed.uqos=") + this.m_test.getUploadQOS() + TX("&hispeed.bandwidth=") + this.m_test.getBandwidth() * 8L + TX("&hispeed.avgpause=") + this.m_test.getDownloadAvgPause() + TX("&hispeed.routespeed=") + this.m_test.getTCPMaxRouteSpeed() * 8L + TX("&hispeed.forcedidle=") + this.m_test.getDownloadForcedIdle() + TX("&hispeed.routeconc=") + this.m_test.getDownloadRouteConcurrency() + (this.m_test.isRunRTT() ? TX("&hispeed.rtt=") + this.m_test.getRTT() : "") + TX("&hispeed.maxpause=") + this.m_test.getDownloadMaxPause();
  }

  public String getDetailResults()
  {
    int[][] arrayOfInt1 = compress(this.m_test.getDownloadTimesArray(), this.m_test.getDownloadBytesArray());
    int[][] arrayOfInt2 = compress(this.m_test.getUploadTimesArray(), this.m_test.getUploadBytesArray());
    String str = "";
    if (arrayOfInt1 != null)
      str = str + TX("&detail.dms=") + URLEncoder.encode(toString(arrayOfInt1[0])) + TX("&detail.dby=") + URLEncoder.encode(toString(arrayOfInt1[1]));
    if (arrayOfInt2 != null)
      str = str + TX("&detail.ums=") + URLEncoder.encode(toString(arrayOfInt2[0])) + TX("&detail.uby=") + URLEncoder.encode(toString(arrayOfInt2[1]));
    return str;
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
 * Qualified Name:     mcsaplugins.hispeed.hispeed
 * JD-Core Version:    0.6.2
 */