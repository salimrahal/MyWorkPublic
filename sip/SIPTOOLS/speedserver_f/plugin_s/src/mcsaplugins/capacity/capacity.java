package mcsaplugins.capacity;

import java.applet.Applet;
import java.net.URLEncoder;
import java.util.Hashtable;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class capacity extends AppletPlugin
{
  private CapacityTest m_test;
  private CapacityTab m_tab;
  private LossProfileTab m_udpTab;

  public capacity(Applet paramApplet)
  {
    super(paramApplet, TX("capacity"));
  }

  public AppletTest getTest()
  {
    return this.m_test;
  }

  public void setTest(AppletTest paramAppletTest)
  {
    if ((paramAppletTest instanceof CapacityTest))
    {
      this.m_test = ((CapacityTest)paramAppletTest);
      this.m_test.doAddGraphs();
      if ((this.m_test.getDownloadAchievedBandwidths() != null) && (this.m_test.getDownloadTargetBandwidths() != null) && (this.m_test.getDownloadLosses() != null))
        addUDPResult(true);
      if ((this.m_test.getUploadAchievedBandwidths() != null) && (this.m_test.getUploadTargetBandwidths() != null) && (this.m_test.getUploadLosses() != null))
        addUDPResult(false);
      if (this.m_tab != null)
        this.m_tab.repaint();
    }
  }

  public AppletTest createTest(Hashtable paramHashtable1, Hashtable paramHashtable2)
  {
    return CapacityTest.createTest(this, this.m_tab, paramHashtable1, paramHashtable2);
  }

  public void notifyTestBegin()
  {
    this.m_tab.stopAnimation();
    this.m_tab.repaint();
  }

  public void doFirstTimeInit()
  {
    this.m_tab = new CapacityTab(getApplet(), this);
    this.m_tab.doFirstTimeInit();
    addTabToApplet(this.m_tab, true);
  }

  public void reset()
  {
    this.m_test = null;
    this.m_udpTab = null;
  }

  public void runTest()
  {
    doAppletDelaySelectTab(this.m_tab);
    this.m_test = new CapacityTest(this, this.m_tab);
    this.m_test.runTest();
  }

  public void addUDPResult(boolean paramBoolean)
  {
    if (this.m_udpTab == null)
    {
      this.m_udpTab = new LossProfileTab(getApplet(), this);
      addTabToApplet(this.m_udpTab, false);
    }
    this.m_udpTab.updateUpDownBox();
  }

  public LossProfileTab getLossProfileTab()
  {
    return this.m_udpTab;
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
    paramString = U.subst(paramString, TX("$CAPACITY.DCAPACITY$"), Long.toString(this.m_test.getDownloadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$CAPACITY.CDCAPACITY$"), str + U.addCommas(this.m_test.getDownloadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$CAPACITY.UCAPACITY$"), Long.toString(this.m_test.getUploadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$CAPACITY.CUCAPACITY$"), str + U.addCommas(this.m_test.getUploadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$CAPACITY.QOS$"), Integer.toString(this.m_test.getDownloadQOS()));
    paramString = U.subst(paramString, TX("$CAPACITY.DPACKETS$"), Integer.toString(this.m_test.getDownloadPackets()));
    paramString = U.subst(paramString, TX("$CAPACITY.UPACKETS$"), Integer.toString(this.m_test.getUploadPackets()));
    paramString = U.subst(paramString, TX("$CAPACITY.PACKETSIZE$"), Integer.toString(this.m_test.getPacketSize()));
    paramString = U.subst(paramString, TX("$DCAPACITY$"), Long.toString(this.m_test.getDownloadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$CDCAPACITY$"), str + U.addCommas(this.m_test.getDownloadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$UCAPACITY$"), Long.toString(this.m_test.getUploadSpeed() * 8L));
    paramString = U.subst(paramString, TX("$CUCAPACITY$"), str + U.addCommas(this.m_test.getUploadSpeed() * 8L) + str);
    paramString = U.subst(paramString, TX("$QOS$"), Integer.toString(this.m_test.getDownloadQOS()));
    paramString = U.subst(paramString, TX("$DPACKETS$"), Integer.toString(this.m_test.getDownloadPackets()));
    paramString = U.subst(paramString, TX("$UPACKETS$"), Integer.toString(this.m_test.getUploadPackets()));
    paramString = U.subst(paramString, TX("$PACKETSIZE$"), Integer.toString(this.m_test.getPacketSize()));
    return paramString;
  }

  public String getReportMSS()
  {
    return TX("&capacity.dcapacity=") + this.m_test.getDownloadSpeed() * 8L + TX("&capacity.ucapacity=") + this.m_test.getUploadSpeed() * 8L + TX("&capacity.dpackets=") + this.m_test.getDownloadPackets() + TX("&capacity.upackets=") + this.m_test.getUploadPackets() + TX("&capacity.packetsize=") + this.m_test.getPacketSize() + TX("&capacity.qos=") + this.m_test.getDownloadQOS();
  }

  public String getDetailResults()
  {
    int[][] arrayOfInt1 = compress(this.m_test.getDownloadTimesArray(), this.m_test.getDownloadBytesArray());
    int[][] arrayOfInt2 = compress(this.m_test.getUploadTimesArray(), this.m_test.getUploadBytesArray());
    long[] arrayOfLong1 = this.m_test.getDownloadTargetBandwidths();
    long[] arrayOfLong2 = this.m_test.getDownloadAchievedBandwidths();
    float[] arrayOfFloat1 = this.m_test.getDownloadLosses();
    long[] arrayOfLong3 = this.m_test.getUploadTargetBandwidths();
    long[] arrayOfLong4 = this.m_test.getUploadAchievedBandwidths();
    float[] arrayOfFloat2 = this.m_test.getUploadLosses();
    String str = "";
    if (arrayOfInt1 != null)
      str = str + TX("&detail.dms=") + URLEncoder.encode(Util.intArrayToString(arrayOfInt1[0], ",")) + TX("&detail.dby=") + URLEncoder.encode(Util.intArrayToString(arrayOfInt1[1], ","));
    if (arrayOfInt2 != null)
      str = str + TX("&detail.ums=") + URLEncoder.encode(Util.intArrayToString(arrayOfInt2[0], ",")) + TX("&detail.uby=") + URLEncoder.encode(Util.intArrayToString(arrayOfInt2[1], ","));
    if ((arrayOfLong1 != null) && (arrayOfLong2 != null) && (arrayOfFloat1 != null))
      str = str + TX("&detail.dtg=") + URLEncoder.encode(Util.longArrayToString(arrayOfLong1, ",")) + TX("&detail.dac=") + URLEncoder.encode(Util.longArrayToString(arrayOfLong2, ",")) + TX("&detail.dlo=") + URLEncoder.encode(Util.floatArrayToString(arrayOfFloat1, ","));
    if ((arrayOfLong3 != null) && (arrayOfLong4 != null) && (arrayOfFloat2 != null))
      str = str + TX("&detail.utg=") + URLEncoder.encode(Util.longArrayToString(arrayOfLong3, ",")) + TX("&detail.uac=") + URLEncoder.encode(Util.longArrayToString(arrayOfLong4, ",")) + TX("&detail.ulo=") + URLEncoder.encode(Util.floatArrayToString(arrayOfFloat2, ","));
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

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.capacity.capacity
 * JD-Core Version:    0.6.2
 */