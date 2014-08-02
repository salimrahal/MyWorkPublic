package mcsaplugins.capacity;

import java.net.URL;
import java.util.Hashtable;
import javax.swing.JOptionPane;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class CapacityTest extends AppletTest
{
  public static final int S_PRE = -1;
  public static final int S_WAIT = 0;
  public static final int S_VOIP = 1;
  public static final int S_DOWNLOAD = 2;
  public static final int S_UPLOAD = 3;
  public static final int S_RTT = 4;
  public static final int S_STATUS = 5;
  public static final int S_DONE = 9;
  private int m_nQOS;
  private int m_nPacketSize = 0;
  private long m_lUCapacity;
  private long m_lDCapacity;
  private int[] m_bytes;
  private long[] m_times;
  private int[] m_ubytes;
  private long[] m_utimes;
  private long[] m_lDTargetBandwidths;
  private long[] m_lDAchievedBandwidths;
  private long[] m_lUTargetBandwidths;
  private long[] m_lUAchievedBandwidths;
  private float[] m_fDLosses;
  private float[] m_fULosses;
  private boolean m_bDMoreThan = false;
  private boolean m_bUMoreThan = false;
  private boolean m_bOverrun = false;
  private int m_nPercentDone;
  private int m_nState;
  private boolean m_bRun;

  public CapacityTest(AppletPlugin paramAppletPlugin, CapacityTab paramCapacityTab)
  {
    super(paramAppletPlugin, paramCapacityTab);
  }

  public long getDownloadSpeed()
  {
    return this.m_lDCapacity;
  }

  public long getUploadSpeed()
  {
    return this.m_lUCapacity;
  }

  public int getDownloadPackets()
  {
    return this.m_nPacketSize > 0 ? (int)(this.m_lDCapacity / this.m_nPacketSize) : 0;
  }

  public int getUploadPackets()
  {
    return this.m_nPacketSize > 0 ? (int)(this.m_lUCapacity / this.m_nPacketSize) : 0;
  }

  public int getPacketSize()
  {
    return this.m_nPacketSize;
  }

  public boolean isDownloadSpeedMoreThan()
  {
    return this.m_bDMoreThan;
  }

  public boolean isUploadSpeedMoreThan()
  {
    return this.m_bUMoreThan;
  }

  public int getDownloadQOS()
  {
    return this.m_nQOS;
  }

  public int getState()
  {
    return this.m_nState;
  }

  public int getPercentComplete()
  {
    return this.m_nPercentDone;
  }

  public int[] getDownloadBytesArray()
  {
    return this.m_bytes;
  }

  public long[] getDownloadTimesArray()
  {
    return this.m_times;
  }

  public int[] getUploadBytesArray()
  {
    return this.m_ubytes;
  }

  public long[] getUploadTimesArray()
  {
    return this.m_utimes;
  }

  public long[] getDownloadTargetBandwidths()
  {
    return this.m_lDTargetBandwidths;
  }

  public long[] getUploadTargetBandwidths()
  {
    return this.m_lUTargetBandwidths;
  }

  public long[] getDownloadAchievedBandwidths()
  {
    return this.m_lDAchievedBandwidths;
  }

  public long[] getUploadAchievedBandwidths()
  {
    return this.m_lUAchievedBandwidths;
  }

  public float[] getDownloadLosses()
  {
    return this.m_fDLosses;
  }

  public float[] getUploadLosses()
  {
    return this.m_fULosses;
  }

  public boolean isRunUpload()
  {
    return !"no".equals(iniGetString("capacity_doupload"));
  }

  public boolean isRunDownload()
  {
    return !"no".equals(iniGetString("capacity_dodownload"));
  }

  public String[] getAdvancedDataItem(String paramString)
  {
    String str1 = null;
    String str2 = null;
    if (TX("dcapacity").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("capacity.adv_") + paramString);
      str2 = (this.m_lDCapacity != 0L ? this.m_lDCapacity * 8L : TX("--")) + TX(" ") + this.m_plugin.RC(TX("bitsps"));
    }
    else if (TX("ucapacity").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("capacity.adv_") + paramString);
      str2 = (this.m_lUCapacity != 0L ? this.m_lUCapacity * 8L : TX("--")) + TX(" ") + this.m_plugin.RC(TX("bitsps"));
    }
    else if (TX("qos").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("capacity.adv_") + paramString);
      str2 = (this.m_nQOS != 0 ? TX("") + this.m_nQOS : TX("--")) + TX(" %");
    }
    else if (TX("dpackets").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("capacity.adv_") + paramString);
      str2 = (this.m_lDCapacity != 0L) && (this.m_nPacketSize > 0) ? this.m_lDCapacity / this.m_nPacketSize : TX("--");
    }
    else if (TX("upackets").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("capacity.adv_") + paramString);
      str2 = (this.m_lUCapacity != 0L) && (this.m_nPacketSize > 0) ? this.m_lUCapacity / this.m_nPacketSize : TX("--");
    }
    else if (TX("packetsize").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("capacity.adv_") + paramString);
      str2 = (this.m_nPacketSize != 0 ? this.m_nPacketSize : TX("--")) + TX(" ") + this.m_plugin.RC(TX("bytes"));
    }
    return new String[] { str1, (str1 == null) || (str2 == null) ? null : str2 };
  }

  public Object[] getSummaryItem(String paramString)
  {
    String str;
    int i;
    if (TX("dcapacity").equals(paramString))
    {
      str = this.m_plugin.RC(TX("capacity.dcapacitynone"));
      i = -1;
      if (this.m_lDCapacity * 8L > this.m_plugin.iniGetInteger(TX("capacity.dcapacitywarnnum"), 75000))
      {
        str = this.m_plugin.RC(TX("capacity.dcapacitygood"), U.bps2s(this.m_lDCapacity * 8L));
        i = 0;
      }
      else if (this.m_lDCapacity * 8L > this.m_plugin.iniGetInteger(TX("capacity.dcapacitybadnum"), 55000))
      {
        str = this.m_plugin.RC(TX("capacity.dcapacitywarn"), U.bps2s(this.m_lDCapacity * 8L));
        i = 1;
      }
      else if (this.m_lDCapacity * 8L > 0L)
      {
        str = this.m_plugin.RC(TX("capacity.dcapacitybad"), U.bps2s(this.m_lDCapacity * 8L));
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("ucapacity").equals(paramString))
    {
      str = this.m_plugin.RC(TX("capacity.ucapacitynone"));
      i = -1;
      if (this.m_lUCapacity * 8L > this.m_plugin.iniGetInteger(TX("capacity.ucapacitywarnnum"), 75000))
      {
        str = this.m_plugin.RC(TX("capacity.ucapacitygood"), U.bps2s(this.m_lUCapacity * 8L));
        i = 0;
      }
      else if (this.m_lUCapacity * 8L > this.m_plugin.iniGetInteger(TX("capacity.ucapacitybadnum"), 55000))
      {
        str = this.m_plugin.RC(TX("capacity.ucapacitywarn"), U.bps2s(this.m_lUCapacity * 8L));
        i = 1;
      }
      else if (this.m_lUCapacity * 8L > 0L)
      {
        str = this.m_plugin.RC(TX("capacity.ucapacitybad"), U.bps2s(this.m_lUCapacity * 8L));
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("qos").equals(paramString))
    {
      str = this.m_plugin.RC(TX("capacity.qosnone"));
      i = -1;
      if (this.m_nQOS > this.m_plugin.iniGetInteger(TX("capacity.qoswarnnum"), 85))
      {
        str = this.m_plugin.RC(TX("capacity.qosgood"), this.m_nQOS);
        i = 0;
      }
      else if (this.m_nQOS > this.m_plugin.iniGetInteger(TX("capacity.qosbadnum"), 55))
      {
        str = TX("");
        str = this.m_plugin.RC(TX("capacity.qoswarn"), this.m_nQOS);
        i = 1;
      }
      else if (this.m_nQOS > 0)
      {
        str = TX("");
        str = this.m_plugin.RC(TX("capacity.qosbad"), this.m_nQOS);
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    return null;
  }

  public void runTest()
  {
    this.m_bRun = true;
    this.m_nState = 2;
    if (isRunDownload())
    {
      this.m_nPercentDone = 0;
      if ((!doUdpSpeedTest(true)) && (this.m_nPercentDone == 0))
      {
        OUT("Download connect failure, please ensure port " + iniGetInteger(TX("capacity.port"), -1) + " UDP is allowed on server and client firewalls");
        JOptionPane.showMessageDialog(null, "Download connect failure, please ensure port " + iniGetInteger(TX("capacity.port"), -1) + " UDP is allowed on server and client firewalls", "MyCapacity test", 1);
      }
      this.m_nPercentDone = 100;
      doRepaint();
    }
    if (isRunUpload())
    {
      this.m_nState = 3;
      this.m_nPercentDone = 0;
      doRepaint();
      int i = iniGetInteger(TX("capacity_uploadwait"), 5000);
      if (i > 0)
        U.sleep(i);
      if ((!doUdpSpeedTest(false)) && (this.m_nPercentDone == 0))
      {
        OUT("Upload connect failure, please ensure port " + iniGetInteger(TX("capacity.port"), -1) + " UDP is allowed on server and client firewalls");
        JOptionPane.showMessageDialog(null, "Upload connect failure, please ensure port " + iniGetInteger(TX("capacity.port"), -1) + " UDP is allowed on server and client firewalls", "MyCapacity test", 1);
      }
      this.m_nPercentDone = 100;
      doRepaint();
    }
    this.m_nState = 5;
    doAddGraphs();
    doRepaint();
    this.m_nState = 9;
  }

  public void doAddGraphs()
  {
    long[] arrayOfLong1 = getDownloadTimesArray();
    int[] arrayOfInt1 = getDownloadBytesArray();
    long[] arrayOfLong2 = getUploadTimesArray();
    int[] arrayOfInt2 = getUploadBytesArray();
    if ((arrayOfLong1 != null) && (arrayOfInt1 != null))
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("capacity.downloadgraph")), arrayOfLong1, arrayOfInt1, null, null);
    if ((arrayOfLong2 != null) && (arrayOfInt2 != null))
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("capacity.uploadgraph")), arrayOfLong2, arrayOfInt2, null, null);
  }

  public void doRepaint()
  {
    super.doRepaint();
    LossProfileTab localLossProfileTab = ((capacity)this.m_plugin).getLossProfileTab();
    if (localLossProfileTab != null)
      localLossProfileTab.repaint();
  }

  private boolean doUdpSpeedTest(boolean paramBoolean)
  {
    int i = iniGetInteger(TX("capacity.port"), -1);
    this.m_nPacketSize = iniGetInteger(TX("capacity_udppacketsize"), 1000);
    long l1 = iniGetInteger(TX("capacity_udpinitialbandwidth"), 1500) * 1000L;
    if (!paramBoolean)
      l1 = iniGetInteger(TX("capacity_udpinitialubandwidth"), 80) * 1000L;
    int j = iniGetInteger(TX("capacity_confirmations"), 2);
    int k = iniGetInteger(TX("capacity_significantloss"), 5);
    j = Math.max(1, Math.min(10, j));
    int m = 0;
    this.m_nPercentDone = 0;
    UDPSpeedClient localUDPSpeedClient = new UDPSpeedClient(getTargetAddress(), i, this.m_plugin.base(), this);
    if (!localUDPSpeedClient.connect())
      return false;
    long l2 = localUDPSpeedClient.getMaxMeasurableBandwidth();
    int n = localUDPSpeedClient.getMaxPacketsPerSec();
    long l3 = n * this.m_nPacketSize;
    long l4 = U.min(l2, l3);
    OUT("Max measurable bandwidth is min {" + l2 * 8L + ", " + l3 * 8L + "} = " + l4 * 8L + " bps");
    OUT("Max transfer is " + localUDPSpeedClient.getMaxTransferBytes() + " bytes");
    l1 = U.min(l1, l4);
    long l5 = 0L;
    int i1 = 0;
    long l6 = 0L;
    long l7 = 0L;
    int i2 = 0;
    long l10;
    for (int i3 = 0; (i3 < j) && (i1 == 0); i3++)
    {
      OUT("Beginning confirmation #" + (i3 + 1) + "...");
      long l8 = 0L;
      long l9 = 0L;
      l10 = 0L;
      long l11 = 0L;
      long l13 = l1;
      if (i3 > 0)
        if (!this.m_bOverrun)
        {
          if (l6 > 0L)
          {
            l13 = l6;
            OUT("Starting next confirmation at " + l13 * 8L + " bps (maximum rate found with no loss)");
          }
          else if (l7 > 0L)
          {
            l13 = l7;
            OUT("No rate has been found which produces zero loss");
            OUT("So, starting next confirmation at " + l13 * 8L + " bps (maximum rate found with acceptable loss)");
          }
        }
        else
          l10 = l6;
      while ((this.m_bRun) && (((l11 == 0L) && (l10 == 0L)) || (l8 == 0L)))
      {
        i2++;
        long[] arrayOfLong1 = doUDPSpeedPretest(localUDPSpeedClient, paramBoolean, l13, l10, l11, l8, i2, k);
        if ((arrayOfLong1 == null) || (arrayOfLong1.length < 4))
          break;
        l9 = l13;
        l13 = arrayOfLong1[0];
        l10 = arrayOfLong1[1];
        l11 = arrayOfLong1[2];
        l8 = arrayOfLong1[3];
        l7 = Math.max(l11, l7);
        l6 = Math.max(l10, l6);
        i1 = 0;
        if (this.m_bOverrun)
          if (l9 > l13)
          {
            this.m_bOverrun = false;
          }
          else
          {
            l1 = l9;
            OUT("Test " + i2 + " overrun, network buffer interference detected");
            break;
          }
        if (l13 >= l2)
        {
          OUT("Test bandwidth limited by server config (server permits max bandwidth of " + l2 + ")");
          l5 = l4;
          i1 = 1;
          break;
        }
        if (l13 >= l3)
        {
          OUT("Test bandwidth limited by server config (server permits max packets per second of " + n + ", for bandwidth limit of " + l3 + " [as packet size is " + this.m_nPacketSize + " bytes])");
          l5 = l4;
          i1 = 1;
          break;
        }
        if (l13 <= 0L)
          l10 = 1L;
        if (!this.m_bRun)
          break;
        OUT("Test bandwidth is between " + Math.max(l10, l11) * 8L + " bps and " + l8 * 8L + " bps (zero means 'unknown so far')");
        this.m_nPercentDone = U.min(50, this.m_nPercentDone + 5);
        doRepaint();
        try
        {
          Thread.sleep(2000L);
        }
        catch (Exception localException1)
        {
        }
        this.m_nPercentDone = U.min(50, this.m_nPercentDone + 5);
        doRepaint();
      }
      l5 = Math.max(l9, l5);
    }
    if (i1 != 0)
    {
      if (paramBoolean)
        this.m_bDMoreThan = true;
      else
        this.m_bUMoreThan = true;
      l5 = l4;
    }
    this.m_nPercentDone = 50;
    doRepaint();
    i3 = getMaxTimeTimeout();
    if (this.m_bRun)
    {
      int i4 = i3 * (int)(l5 / this.m_nPacketSize) / 1000;
      int i5 = Math.min(i4, (int)(localUDPSpeedClient.getMaxTransferBytes() / this.m_nPacketSize));
      String str = "";
      if (i4 > i5)
        str = " (would be " + i4 + " but limited due to server byte limit restriction)";
      int i6 = (int)(l5 / this.m_nPacketSize);
      OUT("\n========================================================");
      l10 = iniGetInteger(TX("capacity_overrun"), 25);
      float f1 = i5 / i6 * (float)(1.0D + (float)l10 / 100.0D);
      long l12 = ()(f1 * 1000.0F);
      OUT("Final test with bandwidth: " + l5 * 8L + " bps, pps=" + i6 + ", numpackets=" + i5 + str + " overrun limit=" + l12 + " ms ...");
      localUDPSpeedClient.setParameters(this.m_nPacketSize, i6, i5, paramBoolean ? 1 : 3, l12);
      localUDPSpeedClient.start();
      long l14 = System.currentTimeMillis();
      long l15 = l14 + 500L;
      long l16;
      while ((this.m_bRun) && (localUDPSpeedClient.isRunning()) && ((l16 = System.currentTimeMillis()) < localUDPSpeedClient.getWaitUntil()))
      {
        Object localObject;
        this.m_nPercentDone = U.min(99, 50 + (int)(localObject - l14) * 50 / i3);
        if (localObject > l15)
        {
          l15 = localObject + 500L;
          doRepaint();
        }
        try
        {
          Thread.sleep(100L);
        }
        catch (Exception localException2)
        {
        }
      }
      localUDPSpeedClient.stop();
      long[] arrayOfLong2 = localUDPSpeedClient.getTimes();
      int[] arrayOfInt = localUDPSpeedClient.getBytes();
      int i7 = arrayOfLong2 == null ? 0 : arrayOfLong2.length;
      long l17 = 0L;
      for (int i8 = 0; (arrayOfLong2 != null) && (i8 < arrayOfLong2.length); i8++)
        l17 += arrayOfInt[i8];
      long l18 = (arrayOfLong2 != null) && (arrayOfLong2.length > 0) ? arrayOfLong2[(arrayOfLong2.length - 1)] : 0L;
      long l19 = l18 > 0L ? l17 * 1000L / l18 : 0L;
      float f2 = localUDPSpeedClient.getLoss();
      if (!this.m_bRun)
        return false;
      int i9 = (l18 > 0L) && (this.m_nPacketSize > 0) ? (int)(l17 * 1000L / (l18 * this.m_nPacketSize)) : -1;
      OUT("Final reading: Achieved " + ((m != 0) && (f2 < k) ? "more than " : "") + l19 * 8L + " bps (=" + l17 / 1000L + " Kbytes in " + l18 + " ms = " + i9 + " pps) with loss=" + f2 + " %");
      this.m_nPercentDone = 100;
      doRepaint();
      if (paramBoolean)
      {
        this.m_bDMoreThan = ((m != 0) && (f2 < k));
        this.m_times = arrayOfLong2;
        this.m_bytes = arrayOfInt;
        this.m_lDCapacity = l19;
        if (i7 > 0)
        {
          long l20 = arrayOfLong2[(i7 - 1)] - arrayOfLong2[0];
          long l21 = arrayOfLong2[(i7 - 1)];
          int i10 = l20 > 1000L ? 5 : 3;
          long l22 = 999999999L;
          long l23 = 0L;
          for (int i11 = 0; i11 < i10; i11++)
          {
            long l24 = l20 * i11 / i10;
            long l25 = l20 * (i11 + 1) / i10;
            long l26 = sum(this.m_times, this.m_bytes, l24, l25, l21);
            long l27 = l25 - l24;
            long l28 = l27 > 0L ? l26 * 8L * 1000L / l27 : 0L;
            l22 = U.min(l26, l22);
            l23 = U.max(l26, l23);
            OUT(TX(" [") + l24 + TX(",") + l25 + TX(") = ") + U.addCommas(l26) + TX(" bytes") + (l28 > 0L ? TX(" (") + U.addCommas(l28) + " bps)" : ""));
          }
          if (l23 > 0L)
          {
            this.m_nQOS = ((int)(l22 * 100L / l23));
            OUT(TX("QOS=") + this.m_nQOS + TX("% (") + l22 + TX("/") + l23 + TX(")"));
          }
        }
      }
      else
      {
        this.m_bUMoreThan = ((m != 0) && (f2 < k));
        this.m_utimes = arrayOfLong2;
        this.m_ubytes = arrayOfInt;
        this.m_lUCapacity = l19;
      }
      return true;
    }
    return false;
  }

  private long[] doUDPSpeedPretest(UDPSpeedClient paramUDPSpeedClient, boolean paramBoolean, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, int paramInt2)
  {
    int i = (int)(paramLong1 / this.m_nPacketSize) * 2;
    int j = Math.min(i, (int)(paramUDPSpeedClient.getMaxTransferBytes() / this.m_nPacketSize));
    int k = (int)(paramLong1 / this.m_nPacketSize);
    double d = iniGetDouble(TX("capacity_noloss"), 0.1D);
    String str1 = "";
    if (i > j)
      str1 = " (would be " + i + " but limited due to server byte limit restriction)";
    OUT("\n--------------------------------------------------------");
    long l1 = iniGetInteger(TX("capacity_overrun"), 25);
    float f1 = j / k * (float)(1.0D + (float)l1 / 100.0D);
    long l2 = ()(f1 * 1000.0F);
    OUT("Test " + paramInt1 + ", attempting to stream bandwidth: " + paramLong1 * 8L + " bps, pps=" + k + ", numpackets=" + j + str1 + " overrun limit=" + l2 + " ms ...");
    paramUDPSpeedClient.setParameters(this.m_nPacketSize, k, j, paramBoolean ? 1 : 2, l2);
    paramUDPSpeedClient.start();
    this.m_bOverrun = false;
    long l3 = System.currentTimeMillis();
    long l4 = 0L;
    long l5 = l3 + 500L;
    while ((this.m_bRun) && (paramUDPSpeedClient.isRunning()) && ((l4 = System.currentTimeMillis()) < paramUDPSpeedClient.getWaitUntil()))
    {
      try
      {
        Thread.sleep(100L);
      }
      catch (Exception localException)
      {
      }
      if (l4 > l5)
      {
        l5 = l4 + 500L;
        doRepaint();
      }
    }
    paramUDPSpeedClient.stop();
    long l6 = 0L;
    long l7 = 0L;
    long l8 = 0L;
    float f2 = paramUDPSpeedClient.getLoss();
    String str2 = (int)(f2 * 10.0F) / 10.0D + " %";
    if (!this.m_bRun)
      return null;
    if (paramBoolean)
    {
      long[] arrayOfLong = paramUDPSpeedClient.getTimes();
      int[] arrayOfInt = paramUDPSpeedClient.getBytes();
      for (int n = 0; (arrayOfLong != null) && (n < arrayOfLong.length); n++)
        l7 += arrayOfInt[n];
      l8 = (arrayOfLong != null) && (arrayOfLong.length > 0) ? arrayOfLong[(arrayOfLong.length - 1)] : 0L;
      l6 = l8 > 0L ? l7 * 1000L / l8 : 0L;
      this.m_lDCapacity = l6;
      this.m_bDMoreThan = (f2 < paramInt2);
      this.m_lDTargetBandwidths = append(this.m_lDTargetBandwidths, paramLong1);
      this.m_lDAchievedBandwidths = append(this.m_lDAchievedBandwidths, l6);
      this.m_fDLosses = append(this.m_fDLosses, f2);
    }
    else
    {
      l8 = paramUDPSpeedClient.getUpstreamElapsed();
      l7 = paramUDPSpeedClient.getUpstreamBytes();
      l6 = l8 > 0L ? l7 * 1000L / l8 : 0L;
      this.m_lUCapacity = l6;
      this.m_bUMoreThan = (f2 < paramInt2);
      this.m_lUTargetBandwidths = append(this.m_lUTargetBandwidths, paramLong1);
      this.m_lUAchievedBandwidths = append(this.m_lUAchievedBandwidths, l6);
      this.m_fULosses = append(this.m_fULosses, f2);
    }
    int m = (l8 > 0L) && (this.m_nPacketSize > 0) ? (int)(l7 * 1000L / (l8 * this.m_nPacketSize)) : -1;
    OUT("Achieved " + l6 * 8L + " bps (=" + l7 / 1000L + " Kbytes in " + l8 + " ms = " + m + " pps) with loss=" + str2);
    ((capacity)this.m_plugin).addUDPResult(paramBoolean);
    if (!this.m_bRun)
      return null;
    if ((l8 > l2) && (f2 <= paramInt2))
      this.m_bOverrun = true;
    if (paramUDPSpeedClient.getServerError() > 0)
    {
      OUT("Cannot measure bandwidth - the server does not support testing this fast. Server settings may have been changed as this test was taking place.");
      return new long[] { 9223372036854775807L, paramLong2, paramLong3, paramLong4 };
    }
    if (f2 > 99.0F)
    {
      OUT("Significant packet loss detected (" + str2 + "). Halving bandwidth for next test.");
      if (paramBoolean)
        this.m_bDMoreThan = false;
      else
        this.m_bUMoreThan = false;
      doLossWait();
      return new long[] { paramLong1 / 2L, paramLong2, paramLong3, (paramLong4 > 0L) && (paramLong4 < paramLong1) ? paramLong4 : paramLong1 };
    }
    if (f2 > d)
    {
      OUT("Some packet loss detected (" + str2 + "). Using this to calculate bandwidth for next test.");
      if (paramBoolean)
        this.m_bDMoreThan = false;
      else
        this.m_bUMoreThan = false;
      long l9;
      if (paramLong2 <= 0L)
      {
        l9 = ()((100.0F - f2) * (float)paramLong1 / 100.0D);
      }
      else
      {
        l9 = paramLong1 * 12L / 10L;
        if (f2 > paramInt2)
          paramLong4 = (paramLong4 > 0L) && (paramLong4 < paramLong1) ? paramLong4 : paramLong1;
      }
      if (f2 > paramInt2)
        doLossWait();
      else
        paramLong3 = Math.max(paramLong1, paramLong3);
      return new long[] { l9, paramLong2, paramLong3, paramLong4 };
    }
    return new long[] { paramLong1 * 12L / 10L, Math.max(paramLong2, paramLong1), Math.max(paramLong3, paramLong1), paramLong4 };
  }

  private int sum(long[] paramArrayOfLong, int[] paramArrayOfInt, long paramLong1, long paramLong2, long paramLong3)
  {
    int i = 0;
    for (int j = 0; j < paramArrayOfLong.length; j++)
    {
      long l1 = j > 0 ? paramArrayOfLong[(j - 1)] : 0L;
      long l2 = U.min(paramLong3, U.max(l1 + U.getTickResolution(), paramArrayOfLong[j]));
      long l3 = U.min(paramLong2, l2) - U.max(paramLong1, l1);
      if (l3 > 0L)
        i = (int)(i + paramArrayOfInt[j] * l3 / (l2 - l1));
    }
    return i;
  }

  private void doLossWait()
  {
    int i = iniGetInteger(TX("capacity_losswait"), 1000);
    if (i > 0)
      try
      {
        Thread.sleep(i);
      }
      catch (Exception localException)
      {
      }
  }

  private static long[] append(long[] paramArrayOfLong, long paramLong)
  {
    long[] arrayOfLong = paramArrayOfLong == null ? new long[1] : new long[paramArrayOfLong.length + 1];
    if (paramArrayOfLong != null)
      System.arraycopy(paramArrayOfLong, 0, arrayOfLong, 0, paramArrayOfLong.length);
    arrayOfLong[(arrayOfLong.length - 1)] = paramLong;
    return arrayOfLong;
  }

  private static float[] append(float[] paramArrayOfFloat, float paramFloat)
  {
    float[] arrayOfFloat = paramArrayOfFloat == null ? new float[1] : new float[paramArrayOfFloat.length + 1];
    if (paramArrayOfFloat != null)
      System.arraycopy(paramArrayOfFloat, 0, arrayOfFloat, 0, paramArrayOfFloat.length);
    arrayOfFloat[(arrayOfFloat.length - 1)] = paramFloat;
    return arrayOfFloat;
  }

  private int getMaxTimeTimeout()
  {
    return U.max(2000, U.min(90000, iniGetInteger(TX("capacity_testtime"), 8000)));
  }

  public static CapacityTest createTest(AppletPlugin paramAppletPlugin, AppletTab paramAppletTab, Hashtable paramHashtable1, Hashtable paramHashtable2)
  {
    CapacityTest localCapacityTest = new CapacityTest(paramAppletPlugin, (CapacityTab)paramAppletTab);
    localCapacityTest.m_nQOS = Util.parseInt((String)paramHashtable1.get("capacity.qos"), 0);
    localCapacityTest.m_nPacketSize = Util.parseInt((String)paramHashtable1.get("capacity.packetsize"), 1000);
    localCapacityTest.m_lUCapacity = (Util.parseLong((String)paramHashtable1.get("capacity.ucapacity"), 0L) / 8L);
    localCapacityTest.m_lDCapacity = (Util.parseLong((String)paramHashtable1.get("capacity.dcapacity"), 0L) / 8L);
    localCapacityTest.m_bytes = Util.toIntArray((String)paramHashtable2.get("dby"), ",");
    localCapacityTest.m_times = Util.toLongArray((String)paramHashtable2.get("dms"), ",");
    localCapacityTest.m_ubytes = Util.toIntArray((String)paramHashtable2.get("uby"), ",");
    localCapacityTest.m_utimes = Util.toLongArray((String)paramHashtable2.get("ums"), ",");
    localCapacityTest.m_lDTargetBandwidths = Util.toLongArray((String)paramHashtable2.get("dtg"), ",");
    localCapacityTest.m_lDAchievedBandwidths = Util.toLongArray((String)paramHashtable2.get("dac"), ",");
    localCapacityTest.m_lUTargetBandwidths = Util.toLongArray((String)paramHashtable2.get("utg"), ",");
    localCapacityTest.m_lUAchievedBandwidths = Util.toLongArray((String)paramHashtable2.get("uac"), ",");
    localCapacityTest.m_fDLosses = Util.toFloatArray((String)paramHashtable2.get("dlo"), ",");
    localCapacityTest.m_fULosses = Util.toFloatArray((String)paramHashtable2.get("ulo"), ",");
    return localCapacityTest;
  }

  public void OUT(String paramString)
  {
    this.m_plugin.OUT(paramString);
  }

  private String iniGetString(String paramString)
  {
    return this.m_plugin.iniGetString(paramString);
  }

  private int iniGetInteger(String paramString, int paramInt)
  {
    return this.m_plugin.iniGetInteger(paramString, paramInt);
  }

  private double iniGetDouble(String paramString, double paramDouble)
  {
    return this.m_plugin.iniGetDouble(paramString, paramDouble);
  }

  private String getTargetAddress()
  {
    return this.m_plugin.base().getHost();
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.capacity.CapacityTest
 * JD-Core Version:    0.6.2
 */