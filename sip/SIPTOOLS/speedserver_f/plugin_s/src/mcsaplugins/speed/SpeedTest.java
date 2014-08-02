package mcsaplugins.speed;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.G;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class SpeedTest extends AppletTest
  implements Runnable
{
  private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
  public static final int S_PRE = -1;
  public static final int S_WAIT = 0;
  public static final int S_RTT1 = 1;
  public static final int S_PRETEST = 2;
  public static final int S_DOWNLOAD = 3;
  public static final int S_RTT2 = 4;
  public static final int S_UPLOAD = 5;
  public static final int S_RTT3 = 6;
  public static final int S_STATUS = 7;
  public static final int S_DONE = 9;
  private RTT m_rtt;
  private char m_uploadType = '-';
  private char m_downloadType = '-';
  private boolean m_bRanRTT;
  private long m_lUBurst;
  private int m_nMaxPause;
  private int m_nAvgPause;
  private int m_nAvgRTT;
  private int m_nMaxRTT;
  private int m_nMinRTT;
  private int[] m_nRtts;
  private int m_nUQOS = -1;
  private int m_nQOS = -1;
  private long m_lUSpeed;
  private long m_lDSpeed;
  private long m_lBandwidth;
  private int[] m_bytes;
  private long[] m_times;
  private int[] m_ubytes;
  private long[] m_utimes;
  private int m_nDMeasurements = 0;
  private int m_nSockets = 0;
  private int m_nUSockets = 0;
  private Hashtable m_actMetrics;
  private ErrorCode m_err;
  private boolean m_bDidCoyoteWarning;
  private int m_nExceptions;
  private int m_nPercentDone;
  private int m_nState;
  private boolean m_bRun;
  private Thread m_tUpdatePercent;
  private boolean m_bRunUpdatePercent;

  public SpeedTest(AppletPlugin paramAppletPlugin, SpeedTab paramSpeedTab)
  {
    super(paramAppletPlugin, paramSpeedTab);
  }

  public char getDownloadType()
  {
    return this.m_downloadType;
  }

  public char getUploadType()
  {
    return this.m_uploadType;
  }

  public String getDownloadTypeText()
  {
    return getTestTypeText(this.m_downloadType);
  }

  public String getUploadTypeText()
  {
    return getTestTypeText(this.m_uploadType);
  }

  private String getTestTypeText(char paramChar)
  {
    switch (paramChar)
    {
    case 's':
      return this.m_plugin.RC(TX("socket"));
    case 'p':
      return TX("POST");
    case 'h':
      return TX("HTTP");
    }
    return TX("-");
  }

  public long getUploadBurst()
  {
    return this.m_lUBurst;
  }

  public int getDownloadMaxPause()
  {
    return this.m_nMaxPause;
  }

  public int getDownloadAvgPause()
  {
    return this.m_nAvgPause;
  }

  public int getRTT()
  {
    return this.m_nMinRTT;
  }

  public int[] getRTTs()
  {
    return this.m_nRtts;
  }

  public int getAvgRTT()
  {
    return this.m_nAvgRTT;
  }

  public int getMaxRTT()
  {
    return this.m_nMaxRTT;
  }

  public int getRTTConsistency()
  {
    int i = -1;
    int j = -1;
    for (int k = 0; (this.m_nRtts != null) && (k < this.m_nRtts.length); k++)
    {
      i = this.m_nRtts[k] > i ? this.m_nRtts[k] : i;
      j = ((this.m_nRtts[k] >= 0) && (this.m_nRtts[k] < j)) || (j < 0) ? this.m_nRtts[k] : j;
    }
    return i <= 0 ? -1 : 100 * j / i;
  }

  public long getBandwidth()
  {
    return Math.max(this.m_lBandwidth, this.m_lDSpeed);
  }

  public long getDownloadSpeed()
  {
    return this.m_lDSpeed;
  }

  public long getUploadSpeed()
  {
    return this.m_lUSpeed;
  }

  public int getDownloadQOS()
  {
    return this.m_nQOS;
  }

  public int getUploadQOS()
  {
    return this.m_nUQOS;
  }

  public float getDownloadRouteConcurrency()
  {
    return (this.m_lDSpeed > 0L) && (this.m_lBandwidth > 0L) ? Math.max(1.0F, (float)this.m_lBandwidth / (float)this.m_lDSpeed) : -1.0F;
  }

  public int getDownloadForcedIdle()
  {
    if ((this.m_nAvgRTT > 0) && (this.m_lBandwidth > 0L))
    {
      int i = (int)(65535000L / this.m_lBandwidth);
      return Math.max(0, (this.m_nAvgRTT - i) * 100 / this.m_nAvgRTT);
    }
    return -1;
  }

  public long getTCPMaxRouteSpeed()
  {
    int i = this.m_nMinRTT;
    if (i > 0)
    {
      long l = 65535000L / i;
      return l > this.m_lDSpeed ? l : 0L;
    }
    return 0L;
  }

  public int getDownloadSockets()
  {
    return this.m_nSockets;
  }

  public Hashtable getAccessCTMetrics()
  {
    return this.m_actMetrics;
  }

  public int getState()
  {
    return this.m_nState;
  }

  public int getPercentDone()
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

  public boolean isRunUpload()
  {
    return !"no".equals(iniGetString("speed_doupload"));
  }

  public boolean isRunDownload()
  {
    return !"no".equals(iniGetString("speed_dodownload"));
  }

  private boolean isRunPretest()
  {
    return this.m_nSockets == 0;
  }

  public boolean isRunRTT()
  {
    return this.m_bRanRTT;
  }

  public ErrorCode getErrorCode()
  {
    return this.m_err;
  }

  public String[] getAdvancedDataItem(String paramString)
  {
    String str1 = null;
    String str2 = null;
    if (TX("dspeed").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = (this.m_lDSpeed != 0L ? this.m_lDSpeed * 8L / 1000L : TX("--")) + TX(" ") + this.m_plugin.RC(TX("k")) + this.m_plugin.RC(TX("bitsps"));
    }
    else if (TX("uspeed").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = (this.m_lUSpeed != 0L ? this.m_lUSpeed * 8L / 1000L : TX("--")) + TX(" ") + this.m_plugin.RC(TX("k")) + this.m_plugin.RC(TX("bitsps"));
    }
    else if (TX("numlines").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      int i = "yes".equals(this.m_plugin.iniGetProfessionalString("separateupdown")) ? 1 : 2;
      int k = this.m_plugin.iniGetInteger("voip_bytesperpacket", "voipbytesperpacket", 8) * this.m_plugin.iniGetInteger("voip_packetspersecond", "voippacketspersecond", 75) * this.m_plugin.iniGetInteger("voip_lines", "voiplines", 1);
      str2 = (this.m_lUSpeed != 0L) && (this.m_lDSpeed != 0L) ? TX("") + (int)Math.floor(Math.min(this.m_lDSpeed, this.m_lUSpeed) / (i * k)) : TX("--");
    }
    else if (TX("qos").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = (this.m_nQOS >= 0 ? TX("") + this.m_nQOS : TX("--")) + TX(" %");
    }
    else if (TX("uqos").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = (this.m_nUQOS >= 0 ? TX("") + this.m_nUQOS : TX("--")) + TX(" %");
    }
    else if (TX("maxpause").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = (this.m_nMaxPause != 0 ? TX("") + this.m_nMaxPause : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("avgpause").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = (this.m_nAvgPause != 0 ? TX("") + this.m_nAvgPause : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("minrtt").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = ((this.m_bRanRTT) && (this.m_nMinRTT != 0) ? TX("") + this.m_nMinRTT : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("maxrtt").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = ((this.m_bRanRTT) && (this.m_nMaxRTT != 0) ? TX("") + this.m_nMaxRTT : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("avgrtt").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = ((this.m_bRanRTT) && (this.m_nAvgRTT != 0) ? TX("") + this.m_nAvgRTT : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("dtest").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = this.m_lDSpeed != 0L ? getDownloadTypeText() : TX("--");
    }
    else if (TX("utest").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
      str2 = this.m_lUSpeed != 0L ? getUploadTypeText() : TX("--");
    }
    else
    {
      long l;
      if (TX("bandwidth").equals(paramString))
      {
        str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
        l = getBandwidth();
        str2 = (l >= this.m_lDSpeed) && (l > 0L) ? l * 8L / 1000L + TX(" ") + this.m_plugin.RC(TX("k")) + this.m_plugin.RC(TX("bitsps")) : TX("--");
      }
      else if (TX("routespeed").equals(paramString))
      {
        str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
        l = getTCPMaxRouteSpeed();
        str2 = (l >= this.m_lDSpeed) && (l > 0L) ? l * 8L / 1000L + TX(" ") + this.m_plugin.RC(TX("k")) + this.m_plugin.RC(TX("bitsps")) : TX("--");
      }
      else if (TX("forcedidle").equals(paramString))
      {
        str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
        int j = getDownloadForcedIdle();
        str2 = j >= 0 ? j + TX(" %") : TX("--");
      }
      else if (TX("routeconc").equals(paramString))
      {
        str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
        float f = getDownloadRouteConcurrency();
        str2 = f > 0.0F ? f : TX("--");
      }
      else if ((paramString != null) && (paramString.startsWith(TX("act."))))
      {
        str1 = this.m_plugin.RC(TX("speed.adv_") + paramString);
        Hashtable localHashtable = getAccessCTMetrics();
        str2 = localHashtable == null ? null : (String)localHashtable.get(paramString.substring(4));
      }
    }
    return new String[] { str1, (str1 == null) || (str2 == null) ? null : str2 };
  }

  public Object[] getSummaryItem(String paramString)
  {
    String str;
    int i;
    if (TX("dspeed").equals(paramString))
    {
      str = this.m_plugin.RC(TX("dspeednone"));
      i = -1;
      if (this.m_lDSpeed * 8L > this.m_plugin.iniGetInteger(TX("dspeedwarnnum"), 75000))
      {
        str = this.m_plugin.RC(TX("dspeedgood"), U.bps2s(this.m_lDSpeed * 8L));
        i = 0;
      }
      else if (this.m_lDSpeed * 8L > this.m_plugin.iniGetInteger(TX("dspeedbadnum"), 55000))
      {
        str = this.m_plugin.RC(TX("dspeedwarn"), U.bps2s(this.m_lDSpeed * 8L));
        i = 1;
      }
      else if (this.m_lDSpeed * 8L > 0L)
      {
        str = this.m_plugin.RC(TX("dspeedbad"), U.bps2s(this.m_lDSpeed * 8L));
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("uspeed").equals(paramString))
    {
      str = this.m_plugin.RC(TX("uspeednone"));
      i = -1;
      if (this.m_lUSpeed * 8L > this.m_plugin.iniGetInteger(TX("uspeedwarnnum"), 75000))
      {
        str = this.m_plugin.RC(TX("uspeedgood"), U.bps2s(this.m_lUSpeed * 8L));
        i = 0;
      }
      else if (this.m_lUSpeed * 8L > this.m_plugin.iniGetInteger(TX("uspeedbadnum"), 55000))
      {
        str = this.m_plugin.RC(TX("uspeedwarn"), U.bps2s(this.m_lUSpeed * 8L));
        i = 1;
      }
      else if (this.m_lUSpeed * 8L > 0L)
      {
        str = this.m_plugin.RC(TX("uspeedbad"), U.bps2s(this.m_lUSpeed * 8L));
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("qos").equals(paramString))
    {
      str = this.m_plugin.RC(TX("qosnone"));
      i = -1;
      if (this.m_nQOS > this.m_plugin.iniGetInteger(TX("qoswarnnum"), 85))
      {
        str = this.m_plugin.RC(TX("qosgood"), this.m_nQOS);
        i = 0;
      }
      else if (this.m_nQOS > this.m_plugin.iniGetInteger(TX("qosbadnum"), 55))
      {
        str = TX("");
        str = this.m_plugin.RC(TX("qoswarn"), this.m_nQOS);
        i = 1;
      }
      else if (this.m_nQOS >= 0)
      {
        str = TX("");
        str = this.m_plugin.RC(TX("qosbad"), this.m_nQOS);
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    return null;
  }

  private boolean isOutputAudit()
  {
    return !"yes".equals(iniGetString("speed_suspendlogging"));
  }

  public void runTest()
  {
    this.m_bRun = true;
    this.m_rtt = null;
    this.m_err = null;
    this.m_nSockets = (TX("app").equals(iniGetString("speed_displayspeed")) ? 1 : Math.min(iniGetInteger("speed_numsockets", 4), 12));
    this.m_nUSockets = (TX("app").equals(iniGetString("speed_displayspeed")) ? 1 : Math.max(1, Math.min(iniGetInteger("speed_numusockets", 4), 12)));
    doRttTest(1);
    this.m_nState = 2;
    if (isRunPretest())
    {
      doPretest();
      U.sleep(250L);
    }
    this.m_nState = 3;
    if (isRunDownload())
    {
      this.m_nPercentDone = 0;
      doRepaint();
      doDownloadSpeedTest();
      this.m_nPercentDone = 100;
      doRepaint();
    }
    doRttTest(2);
    U.sleep(250L);
    if (isRunUpload())
    {
      this.m_nState = 5;
      this.m_nPercentDone = 0;
      doRepaint();
      doUploadSpeedTest();
      this.m_nPercentDone = 100;
      doRepaint();
    }
    doRttTest(3);
    this.m_nState = 7;
    long[] arrayOfLong1 = getDownloadTimesArray();
    int[] arrayOfInt1 = getDownloadBytesArray();
    if ((arrayOfLong1 != null) && (arrayOfInt1 != null))
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("speed.downloadgraph")), arrayOfLong1, arrayOfInt1, null, null);
    long[] arrayOfLong2 = getUploadTimesArray();
    int[] arrayOfInt2 = getUploadBytesArray();
    if ((arrayOfLong2 != null) && (arrayOfInt2 != null))
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("speed.uploadgraph")), arrayOfLong2, arrayOfInt2, null, null);
    String str1 = this.m_actMetrics == null ? null : (String)this.m_actMetrics.get("detail.xms");
    long[] arrayOfLong3 = str1 == null ? null : Util.toLongArray(str1, ", ");
    String str2 = this.m_actMetrics == null ? null : (String)this.m_actMetrics.get("detail.xby");
    int[] arrayOfInt3 = str2 == null ? null : Util.toIntArray(str2, ", ");
    if ((arrayOfLong3 != null) && (arrayOfInt3 != null))
      this.m_plugin.addGraphResults(TX("Download Retransmitted Bytes"), arrayOfLong3, arrayOfInt3, true, (arrayOfLong1 == null) || (arrayOfLong1.length < 2) ? 8000 : (int)(arrayOfLong1[(arrayOfLong1.length - 1)] - arrayOfLong1[0]));
    String str3 = this.m_actMetrics == null ? null : (String)this.m_actMetrics.get("detail.uxms");
    long[] arrayOfLong4 = str3 == null ? null : Util.toLongArray(str3, ", ");
    String str4 = this.m_actMetrics == null ? null : (String)this.m_actMetrics.get("detail.uxby");
    int[] arrayOfInt4 = str4 == null ? null : Util.toIntArray(str4, ", ");
    if ((arrayOfLong4 != null) && (arrayOfInt4 != null))
      this.m_plugin.addGraphResults(TX("Upload Retransmitted Bytes"), arrayOfLong4, arrayOfInt4, true, (arrayOfLong2 == null) || (arrayOfLong2.length < 2) ? 8000 : (int)(arrayOfLong2[(arrayOfLong2.length - 1)] - arrayOfLong2[0]));
    if ((arrayOfLong1 != null) && (arrayOfInt1 != null) && (arrayOfLong3 != null) && (arrayOfInt3 != null))
      this.m_plugin.addCombinedGraphResults(TX("Download speed w/retransmits overlay"), new String[] { this.m_plugin.RC(TX("speed.downloadgraph")), TX("Download Retransmitted Bytes") });
    if ((arrayOfLong2 != null) && (arrayOfInt2 != null) && (arrayOfLong4 != null) && (arrayOfInt4 != null))
      this.m_plugin.addCombinedGraphResults(TX("Upload speed w/retransmits overlay"), new String[] { this.m_plugin.RC(TX("speed.uploadgraph")), TX("Upload Retransmitted Bytes") });
    doRepaint();
    this.m_nState = 9;
  }

  private void doPretest()
  {
    doDownloadSpeedTest();
    this.m_nSockets = Math.min(8, Math.max(1, (int)Math.ceil(getDownloadRouteConcurrency())));
    OUT("PRETEST: Route conc=" + getDownloadRouteConcurrency() + ", test using " + this.m_nSockets + " sockets");
  }

  private void doRttTest(int paramInt)
  {
    U.sleep(250L);
    switch (paramInt)
    {
    case 1:
      this.m_nState = 1;
      break;
    case 2:
      this.m_nState = 4;
      break;
    default:
      this.m_nState = 6;
    }
    int i = iniGetInteger(TX("speed_rttnumtests") + (paramInt != 3 ? paramInt : ""), 5);
    OUT(TX("Stage ") + paramInt + TX(", performing ") + i + TX(" RTT tests"));
    if (i > 0)
    {
      U.sleep(250L);
      this.m_bRanRTT = true;
      if (this.m_rtt == null)
        this.m_rtt = new RTT();
      int j = -1;
      j = (j == -1) && (this.m_uploadType == 's') ? iniGetInteger(TX("mss.upload.port"), -1) : j;
      j = (j == -1) && (this.m_downloadType == 's') ? iniGetInteger(TX("mss.download.port"), -1) : j;
      j = j == -1 ? getTargetHTTPPort() : j;
      this.m_rtt.addTests(null, getTargetAddress(), j, i);
      this.m_nMinRTT = this.m_rtt.getMinRtt();
      this.m_nMaxRTT = this.m_rtt.getMaxRtt();
      this.m_nAvgRTT = this.m_rtt.getAvgRtt();
      this.m_nRtts = this.m_rtt.getRtts();
      OUT(TX("RTT: avg=") + this.m_nAvgRTT + TX(" ms, min=") + this.m_nMinRTT + TX("ms"));
      for (int k = 0; (this.m_nRtts != null) && (k < this.m_nRtts.length); k++)
        OUT(TX("   RTT test #") + k + TX("=") + this.m_nRtts[k] + TX(" ms"));
      doRepaint();
    }
  }

  private void doDownloadSpeedTest()
  {
    this.m_downloadType = 's';
    if (!doNewDownloadSpeedTest())
    {
      this.m_downloadType = 'h';
      doOldDownloadSpeedTest();
    }
  }

  private void doUploadSpeedTest()
  {
    if (isRunUpload())
    {
      String str = iniGetString(TX("speed_uptype"), TX("uptype"));
      str = str == null ? "" : str.toLowerCase();
      boolean bool = false;
      if ((!bool) && (!str.equals("post")) && (!str.equals("http")))
      {
        bool = doNewUploadSpeedTest(false);
        if (bool)
          this.m_uploadType = 's';
      }
      if ((!bool) && (!str.equals("http")))
      {
        bool = doNewUploadSpeedTest(true);
        if (bool)
          this.m_uploadType = 'p';
      }
      if (!bool)
      {
        doOldUploadSpeedTest();
        this.m_uploadType = 'h';
      }
    }
  }

  private boolean doNewDownloadSpeedTest()
  {
    boolean bool = false;
    try
    {
      String str1 = iniGetString(TX("speed_downtype"), TX("downtype"));
      String str2 = "";
      int i = 0;
      if ((!TX("HTTP").equals(str1)) && (!TX("http").equals(str1)))
      {
        str2 = TX("mss.download.port");
        i = iniGetInteger(TX("redirectdownloadport"), -1);
        i = i == -1 ? iniGetInteger(str2, -1) : i;
      }
      if (i > 0)
      {
        int j = this.m_nState == 2 ? 1 : this.m_nSockets;
        OUT(TX("DOWNLOADING via ") + str2 + TX("=") + i);
        Socket[] arrayOfSocket = new Socket[j];
        InputStream[] arrayOfInputStream = new InputStream[j];
        OutputStream[] arrayOfOutputStream = new OutputStream[j];
        for (int k = 0; k < j; k++)
        {
          Socket localSocket = new Socket(getTargetAddress(), i);
          localSocket.setSoTimeout(8000);
          int m = iniGetInteger("speed_recbuff", 65536);
          if (m > 0)
            U.setReceiveBufferSize(localSocket, m);
          if (U.getReceiveBufferSize(localSocket) > 0)
            OUT(TX(new StringBuilder(" #").append(k + 1).append(": socket buffers: snd=").toString()) + U.getSendBufferSize(localSocket) + TX(" rcv=") + U.getReceiveBufferSize(localSocket));
          arrayOfInputStream[k] = localSocket.getInputStream();
          arrayOfOutputStream[k] = localSocket.getOutputStream();
          arrayOfSocket[k] = localSocket;
        }
        bool = true;
        goDownloadSpeedTest(arrayOfInputStream, arrayOfOutputStream, mssXFER(), TX("MySpeedServlet"));
      }
      else if (i == 0)
      {
        OUT(str2 + TX("=disabled"));
      }
    }
    catch (Exception localException)
    {
      ERR(" " + localException);
    }
    return bool;
  }

  private long mssXFER()
  {
    return this.m_nState == 2 ? 256000L : iniGetLong(TX("mss.xfer"), 10485760L);
  }

  private boolean doNewUploadSpeedTest(boolean paramBoolean)
  {
    Socket[] arrayOfSocket = null;
    DataInputStream[] arrayOfDataInputStream = null;
    OutputStream[] arrayOfOutputStream = null;
    int i16;
    try
    {
      String str1 = TX("mss.upload.port");
      int i = iniGetInteger(TX("redirectuploadport"), -1);
      i = i == -1 ? iniGetInteger(str1, -1) : i;
      if ((paramBoolean) || (i > 0))
      {
        OUT(TX("UPLOADING via ") + (paramBoolean ? TX("HTTP POST") : new StringBuilder(String.valueOf(str1)).append(TX("=")).append(i).toString()));
        int j = getMaxTimeTimeout();
        String str2 = null;
        URLConnection[] arrayOfURLConnection = new URLConnection[this.m_nUSockets];
        int k;
        if (paramBoolean)
        {
          OUT(TX("POST test gettting upload ID#"));
          str2 = readUrl("/myspeed/plugin/speed/createupload");
          if (str2.startsWith("resultset="))
            str2 = str2.substring(10);
          OUT(TX("POST test ID# =") + str2);
          if (str2 != null)
          {
            arrayOfOutputStream = new OutputStream[arrayOfURLConnection.length];
            for (k = 0; k < arrayOfURLConnection.length; k++)
            {
              arrayOfURLConnection[k] = new URL(this.m_plugin.base(), "/myspeed/llplugin/speed/uploadtest?uploadid=" + str2 + "&to=" + j).openConnection();
              setFixedLengthStreamingMode((HttpURLConnection)arrayOfURLConnection[k], 100000000);
              arrayOfURLConnection[k].setDoOutput(true);
              arrayOfOutputStream[k] = arrayOfURLConnection[k].getOutputStream();
            }
          }
        }
        else
        {
          arrayOfSocket = new Socket[this.m_nUSockets];
          arrayOfDataInputStream = new DataInputStream[this.m_nUSockets];
          arrayOfOutputStream = new OutputStream[this.m_nUSockets];
          k = iniGetInteger("speed_sendbuff", 65536);
          for (int m = 0; m < this.m_nUSockets; m++)
          {
            arrayOfSocket[m] = new Socket(getTargetAddress(), i);
            if (k > 0)
              U.setSendBufferSize(arrayOfSocket[m], k);
            if (U.getReceiveBufferSize(arrayOfSocket[m]) > 0)
              OUT(TX(" socket buffers: snd=") + U.getSendBufferSize(arrayOfSocket[m]) + TX(" rcv=") + U.getReceiveBufferSize(arrayOfSocket[m]));
            arrayOfDataInputStream[m] = new DataInputStream(arrayOfSocket[m].getInputStream());
            arrayOfOutputStream[m] = arrayOfSocket[m].getOutputStream();
          }
        }
        if ((arrayOfOutputStream == null) || ((!paramBoolean) && (arrayOfDataInputStream == null)))
        {
          int i12;
          return false;
        }
        if (paramBoolean)
        {
          this.m_tUpdatePercent = new Thread(this, "MSS-MySpeedProgressUpdater");
          this.m_bRunUpdatePercent = true;
          this.m_tUpdatePercent.start();
        }
        long l1 = U.time();
        long l2 = l1;
        long l3 = l2;
        long l4 = 0L;
        long l5 = 0L;
        long l6 = paramBoolean ? Math.min(20000000L, mssXFER()) : mssXFER();
        SocketSendThread[] arrayOfSocketSendThread = new SocketSendThread[this.m_nUSockets];
        for (int n = 0; n < this.m_nUSockets; n++)
          arrayOfSocketSendThread[n] = new SocketSendThread(this, arrayOfOutputStream[n], l6, paramBoolean);
        while ((this.m_bRun) && (l2 - l1 < j) && (l4 < l6))
        {
          n = l4 == 0L ? 1 : 0;
          l4 = 0L;
          for (int i1 = 0; i1 < arrayOfSocketSendThread.length; i1++)
          {
            l4 += arrayOfSocketSendThread[i1].getTotalWritten();
            long l8 = arrayOfSocketSendThread[i1].getLastButOneWriteTime();
            long l9 = arrayOfSocketSendThread[i1].getLastWriteTime();
            if ((l8 > 0L) && (l9 > l8) && (l2 > l9) && (l2 - l1 > 4000L))
            {
              int i4 = arrayOfSocketSendThread[i1].getLastWriteBytes();
              l4 -= Math.max(0L, i4 - (l2 - l9) * i4 / (l9 - l8));
            }
          }
          if ((n != 0) || ((paramBoolean) && (l2 - l1 < 2000L)))
            l5 = l4;
          if (!paramBoolean)
            this.m_lUSpeed = (l2 > l1 ? (l4 - l5) * 1000L / (l2 - l1) : 0L);
          long l7 = l2 - l1;
          if (!paramBoolean)
            this.m_nPercentDone = U.min(100, U.max((int)(l7 * 100L / j), (int)(l4 * 100L / l6)));
          if (l2 > l3)
          {
            l3 = l2 + 350L;
            doRepaint();
          }
          try
          {
            Thread.sleep(100L);
          }
          catch (Exception localException2)
          {
          }
          l2 = U.time();
        }
        for (n = 0; n < arrayOfSocketSendThread.length; n++)
          arrayOfSocketSendThread[n].stop();
        String str3 = null;
        int[] arrayOfInt1 = new int[this.m_nUSockets];
        int i2 = 0;
        if (this.m_bRun)
          for (int i3 = 0; (i3 < this.m_nUSockets) && (this.m_bRun); i3++)
          {
            if (paramBoolean)
            {
              arrayOfOutputStream[i3].flush();
              try
              {
                arrayOfURLConnection[i3].getContentLength();
              }
              catch (Exception localException3)
              {
              }
              try
              {
                arrayOfDataInputStream[i3].close();
              }
              catch (Exception localException4)
              {
              }
              try
              {
                arrayOfOutputStream[i3].close();
              }
              catch (Exception localException5)
              {
              }
              str3 = readUrl("/myspeed/plugin/speed/getupload?uploadid=" + str2);
            }
            else
            {
              arrayOfOutputStream[i3].write("**STOP**".getBytes());
              arrayOfOutputStream[i3].flush();
              arrayOfInt1[i3] = arrayOfDataInputStream[i3].readInt();
              i2 += arrayOfInt1[i3];
            }
            this.m_bRunUpdatePercent = false;
            this.m_nPercentDone = 100;
            try
            {
              this.m_tUpdatePercent.join();
            }
            catch (Exception localException6)
            {
            }
            doRepaint();
          }
        if (this.m_bRun)
        {
          l4 = 0L;
          l1 = U.time();
          long[] arrayOfLong = new long[i2];
          int[] arrayOfInt2 = new int[i2];
          int i6;
          int i5;
          int i7;
          if ((paramBoolean) && (str3 != null))
          {
            localObject1 = new StringTokenizer(str3, "\r\n");
            String str4 = ((StringTokenizer)localObject1).hasMoreTokens() ? ((StringTokenizer)localObject1).nextToken() : null;
            if ((str4 != null) && (str4.startsWith("len=")))
              str4 = str4.substring(4);
            i6 = Util.parseInt(str4, 0);
            OUT("len=" + i6 + ", sLen=" + str4);
            arrayOfLong = new long[i6];
            arrayOfInt2 = new int[i6];
            long l11 = 0L;
            int i8 = 0;
            for (int i9 = 0; (this.m_bRun) && (i9 < arrayOfLong.length) && (((StringTokenizer)localObject1).hasMoreTokens()); i9++)
            {
              String str6 = ((StringTokenizer)localObject1).nextToken();
              int i10 = str6.indexOf(',');
              if (i10 > 0)
              {
                long l12 = Util.parseLong(str6.substring(0, i10), -1L);
                int i11 = Util.parseInt(str6.substring(i10 + 1), -1);
                if ((l12 >= 0L) && (i11 >= 0) && (l12 >= l11) && (i8 < i6))
                {
                  arrayOfLong[i8] = l12;
                  arrayOfInt2[i8] = i11;
                  l4 += i11;
                  l11 = l12;
                  i8++;
                }
              }
            }
            if (i8 < i6)
            {
              arrayOfLong = U.resize(arrayOfLong, i8);
              arrayOfInt2 = U.resize(arrayOfInt2, i8);
            }
            while (((StringTokenizer)localObject1).hasMoreTokens())
            {
              String str5 = ((StringTokenizer)localObject1).nextToken();
              if (str5.startsWith("hbss="))
                this.m_actMetrics = U.stringToHash(str5.substring(4), "&\r\n", this.m_actMetrics);
            }
          }
          else
          {
            localObject1 = new long[this.m_nUSockets];
            i5 = 0;
            for (i6 = 0; (this.m_bRun) && (i6 < this.m_nUSockets); i6++)
            {
              for (i7 = 0; (this.m_bRun) && (i7 < arrayOfInt1[i6]); i7++)
              {
                arrayOfLong[i5] = arrayOfDataInputStream[i6].readInt();
                arrayOfInt2[i5] = arrayOfDataInputStream[i6].readInt();
                localObject1[i6] += arrayOfInt2[i5];
                i5++;
              }
              l4 += localObject1[i6];
            }
            U.sort(arrayOfLong, arrayOfInt2);
            i6 = U.removeDuplicates(arrayOfLong, arrayOfInt2);
            if (i6 != arrayOfLong.length)
            {
              arrayOfLong = U.resize(arrayOfLong, i6);
              arrayOfInt2 = U.resize(arrayOfInt2, i6);
            }
            if (isOutputAudit())
            {
              OUT(TX("Sockets used:"));
              for (i7 = 0; i7 < this.m_nUSockets; i7++)
                if (l4 == 0L)
                  OUT(i7 + 1 + TX(": no data"));
                else
                  OUT(i7 + 1 + TX(": ") + localObject1[i7] * 1000L / l4 / 10.0D + TX(" %"));
              OUT("");
            }
          }
          Object localObject1 = new StringBuffer();
          if (isOutputAudit())
          {
            for (i5 = 0; (this.m_bRun) && (i5 < arrayOfLong.length); i5++)
              ((StringBuffer)localObject1).append(TX(" ") + arrayOfLong[i5] + " " + arrayOfInt2[i5] + "\n");
            OUT(((StringBuffer)localObject1).toString());
          }
          else
          {
            OUT(TX("audit logging disabled"));
          }
          if ((isNetQCheck()) && (!paramBoolean) && (arrayOfDataInputStream != null))
          {
            Object localObject2;
            for (i5 = 0; i5 < this.m_nUSockets; i5++)
            {
              localObject2 = new StringBuffer();
              while ((i7 = arrayOfDataInputStream[i5].read()) != -1)
                ((StringBuffer)localObject2).append((char)i7);
              if (this.m_nUSockets >= 2)
                OUT(TX("AccessCM metrics [socket ") + (i5 + 1) + "]=" + ((StringBuffer)localObject2).toString());
              this.m_actMetrics = combineNetQCheckHash(this.m_actMetrics, U.stringToHash(((StringBuffer)localObject2).toString(), "&\r\n"));
            }
            OUT(TX("AccessCM metrics:"));
            Enumeration localEnumeration = this.m_actMetrics == null ? null : this.m_actMetrics.keys();
            while ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
            {
              localObject2 = (String)localEnumeration.nextElement();
              OUT(TX("  ") + (String)localObject2 + TX(" = ") + this.m_actMetrics.get(localObject2));
            }
          }
          if (arrayOfLong.length > 0)
          {
            long l10 = arrayOfLong[(arrayOfLong.length - 1)] - arrayOfLong[0];
            this.m_lUSpeed = (l10 > 0L ? l4 * 1000L / l10 : 0L);
            this.m_ubytes = arrayOfInt2;
            this.m_utimes = arrayOfLong;
            OUT(TX("UPLOADED ") + U.addCommas(l4) + TX(" bytes in ") + l10 + TX(" ms (") + U.addCommas(this.m_lUSpeed * 8L) + TX(" bps)"));
            this.m_nUQOS = calculateQOS(l10, this.m_utimes[(this.m_utimes.length - 1)], this.m_utimes, this.m_ubytes);
          }
        }
        int i13;
        return true;
      }
      if (i == 0)
        OUT(str1 + TX("=disabled"));
    }
    catch (Exception localException1)
    {
      ERR(" " + localException1);
      localException1.printStackTrace();
      this.m_bRunUpdatePercent = false;
      try
      {
        this.m_tUpdatePercent.interrupt();
      }
      catch (Exception localException11)
      {
      }
      for (int i14 = 0; (arrayOfSocket != null) && (i14 < arrayOfSocket.length); i14++)
      {
        try
        {
          arrayOfSocket[i14].close();
        }
        catch (Exception localException23)
        {
        }
        try
        {
          arrayOfDataInputStream[i14].close();
        }
        catch (Exception localException24)
        {
        }
        try
        {
          arrayOfOutputStream[i14].close();
        }
        catch (Exception localException25)
        {
        }
      }
      try
      {
        this.m_tUpdatePercent.join();
      }
      catch (Exception localException12)
      {
      }
    }
    finally
    {
      this.m_bRunUpdatePercent = false;
      try
      {
        this.m_tUpdatePercent.interrupt();
      }
      catch (Exception localException13)
      {
      }
      for (int i15 = 0; (arrayOfSocket != null) && (i15 < arrayOfSocket.length); i15++)
      {
        try
        {
          arrayOfSocket[i15].close();
        }
        catch (Exception localException26)
        {
        }
        try
        {
          arrayOfDataInputStream[i15].close();
        }
        catch (Exception localException27)
        {
        }
        try
        {
          arrayOfOutputStream[i15].close();
        }
        catch (Exception localException28)
        {
        }
      }
      try
      {
        this.m_tUpdatePercent.join();
      }
      catch (Exception localException14)
      {
      }
    }
    return false;
  }

  private static Hashtable combineNetQCheckHash(Hashtable paramHashtable1, Hashtable paramHashtable2)
  {
    if (paramHashtable1 == null)
      paramHashtable1 = new Hashtable();
    if (paramHashtable2 != null)
    {
      Vector localVector = new Vector();
      String str1 = (String)paramHashtable1.get("dontsum");
      str1 = str1 == null ? (String)paramHashtable2.get("dontsum") : str1;
      StringTokenizer localStringTokenizer = new StringTokenizer(str1 == null ? "tcpmtu,netupsec" : str1, ",");
      while (localStringTokenizer.hasMoreTokens())
        localVector.add(localStringTokenizer.nextToken());
      Enumeration localEnumeration = paramHashtable2.keys();
      while ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
      {
        String str2 = (String)localEnumeration.nextElement();
        Number localNumber1 = parseNum((String)paramHashtable1.get(str2));
        Number localNumber2 = parseNum((String)paramHashtable2.get(str2));
        if ((localNumber1 == null) && (localNumber2 == null))
        {
          if (paramHashtable1.get(str2) == null)
            paramHashtable1.put(str2, paramHashtable2.get(str2));
        }
        else if ((localNumber1 == null) || (localNumber2 == null))
          paramHashtable1.put(str2, localNumber1 == null ? localNumber2.toString() : localNumber1.toString());
        else if ((!localVector.contains(str2)) && (!str2.startsWith("detail.")))
          if (((localNumber1 instanceof Double)) || ((localNumber2 instanceof Double)))
            paramHashtable1.put(str2, localNumber1.doubleValue() + localNumber2.doubleValue());
          else
            paramHashtable1.put(str2, localNumber1.longValue() + localNumber2.longValue());
      }
    }
    return paramHashtable1;
  }

  private static Number parseNum(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      try
      {
        localObject = Long.valueOf(paramString);
      }
      catch (Exception localException1)
      {
      }
      if (localObject == null)
        try
        {
          localObject = Double.valueOf(paramString);
        }
        catch (Exception localException2)
        {
        }
    }
    return localObject;
  }

  public static boolean isNetQCheck()
  {
    return (G.g_server != null) && (G.g_server.toLowerCase().indexOf("netqcheck") >= 0);
  }

  private void setFixedLengthStreamingMode(HttpURLConnection paramHttpURLConnection, int paramInt)
  {
    try
    {
      Class localClass = Class.forName("java.net.HttpURLConnection");
      Method localMethod = localClass.getMethod("setFixedLengthStreamingMode", new Class[] { Integer.TYPE });
      localMethod.invoke(paramHttpURLConnection, new Object[] { new Integer(paramInt) });
      OUT("Set fixed length streaming mode, size=" + paramInt);
    }
    catch (Exception localException)
    {
      OUT("ERROR: Unable to set chunked streaming mode: " + localException);
      localException.printStackTrace();
    }
  }

  private void doOldDownloadSpeedTest()
  {
    InputStream[] arrayOfInputStream = new InputStream[this.m_nState == 2 ? 1 : this.m_nSockets];
    String str1 = iniGetString(TX("data.bin"));
    String str2 = str1 != null ? str1 : TX("data.bin");
    if (!str2.startsWith("/"))
      str2 = TX("/myspeed/") + str2;
    int m;
    try
    {
      String str3 = null;
      if (isNetQCheck())
      {
        str4 = readUrl(getTargetProtocol() + "://" + getTargetAddress() + ":" + getTargetHTTPPort() + "/myspeed/plugin/speed/createdownload?t=" + System.currentTimeMillis());
        if (str4.startsWith("resultset="))
          str3 = "&downloadid=" + str4.substring(10);
        OUT("NetQCheck ticket #" + str3);
      }
      String str4 = str2 + TX("?t=") + U.time() + (str3 == null ? "" : str3);
      URL localURL = new URL(getTargetProtocol() + "://" + getTargetAddress() + ":" + getTargetHTTPPort() + str4);
      URLConnection[] arrayOfURLConnection = new URLConnection[arrayOfInputStream.length];
      int i = -1;
      for (int j = 0; j < arrayOfInputStream.length; j++)
      {
        arrayOfURLConnection[j] = U.setNoCaching(localURL.openConnection());
        i = arrayOfURLConnection[j].getContentLength();
        if (i <= 0)
          throw new RuntimeException(localURL + TX(" missing Content-Length header field"));
        arrayOfInputStream[j] = arrayOfURLConnection[j].getInputStream();
      }
      OUT(TX("DOWNLOADING via HTTP file ") + str2 + TX(" (") + arrayOfInputStream.length + TX(" sockets)"));
      goDownloadSpeedTest(arrayOfInputStream, null, i, str2);
      if ((isNetQCheck()) && (str3 != null))
      {
        String str5 = readUrl(getTargetProtocol() + "://" + getTargetAddress() + ":" + getTargetHTTPPort() + "/myspeed/plugin/speed/getdownload?t=" + System.currentTimeMillis() + str3);
        StringTokenizer localStringTokenizer = new StringTokenizer(str5, "\r\n");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str6 = localStringTokenizer.nextToken();
          if ((str6.startsWith("hbss=")) || (str6.startsWith("&hbss=")))
          {
            int k = str6.charAt(0) == '&' ? 6 : 5;
            this.m_actMetrics = U.stringToHash(str6.substring(k), "&\r\n", this.m_actMetrics);
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      logException(str2, localThrowable);
    }
    finally
    {
      for (m = 0; m < arrayOfInputStream.length; m++)
        try
        {
          arrayOfInputStream[m].close();
        }
        catch (Exception localException2)
        {
        }
      this.m_nPercentDone = 100;
    }
  }

  private void doOldUploadSpeedTest()
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 99999;
    int n = 5;
    long l1 = getMaxTimeTimeout();
    long l2 = U.time();
    long l3 = l2;
    long l4 = l3;
    long l5 = U.max(0L, this.m_lDSpeed / 15L);
    int[] arrayOfInt1 = null;
    OUT(TX("UPLOADING via HTTP file ") + TX("/myspeed/upload.bin"));
    if (this.m_lDSpeed > 8192L)
    {
      U.sleep(1000L);
      arrayOfInt1 = up(8192);
      if (arrayOfInt1 != null)
        OUT(TX(" UP ") + U.addCommas(arrayOfInt1[0]) + TX(" bytes in ") + arrayOfInt1[1] + TX(" ms"));
    }
    long l6;
    for (int i1 = 0; (i1 < n) && (this.m_bRun) && (l3 - l2 < l1); i1++)
    {
      int[] arrayOfInt2 = up(0);
      l6 = U.max(4L, l1 / 3L);
      int[] arrayOfInt3 = up(U.max(8192, (int)(l5 * l6 / 1000L)));
      if ((arrayOfInt2 != null) && (arrayOfInt3 != null))
      {
        OUT(TX(" up ") + U.addCommas(arrayOfInt3[0]) + TX(" bytes in ") + arrayOfInt3[1] + TX("-") + arrayOfInt2[1] + TX(" ms"));
        m = U.min(m, U.min(arrayOfInt2[1], arrayOfInt3[1]));
        i += arrayOfInt3[0];
        j += arrayOfInt3[1];
        k++;
        int i3 = j - k * m;
        l5 = i3 > 0 ? (int)(i * 1000L / i3) : 1250000;
        if (i3 > 10)
          this.m_lUSpeed = l5;
      }
      l3 = U.time();
      long l7 = l3 - l2;
      this.m_nPercentDone = U.min(100, U.max((int)(l7 * 100L / l1), i1 * 100 / n));
      if (l3 > l4)
      {
        l4 = l3 + 25L;
        doRepaint();
      }
    }
    OUT(TX("UPLOADED ") + U.addCommas(i) + TX(" bytes in ") + (j - k * m) + TX(" ms (") + U.addCommas(this.m_lUSpeed * 8L) + TX(" bps)"));
    if ((arrayOfInt1 != null) && (m < 1000))
    {
      i1 = arrayOfInt1[0];
      int i2 = arrayOfInt1[1] - m;
      l6 = i2 > 0 ? i1 * 1000L / i2 : 0L;
      if ((this.m_lUSpeed > 0L) && (l6 > this.m_lUSpeed * 3L / 2L))
      {
        this.m_lUBurst = l6;
        OUT(TX("UPLOAD BURST ") + U.addCommas(i1) + TX(" bytes in ") + i2 + TX(" ms (") + U.addCommas(this.m_lUBurst * 8L) + TX(" bps)"));
      }
    }
  }

  private int getMaximumExtraHeaderSize()
  {
    String str = getServerSoftwareName();
    int i = 8192;
    int j = iniGetInteger(TX("speed_maxheadersize"), TX("maxheadersize"), -1);
    if (j > 0)
    {
      i = j;
    }
    else if ((!isHttpProxyPresent()) && (str != null))
    {
      str = str.toLowerCase();
      if (str.indexOf(TX("apache")) >= 0)
        i = 131072;
      else if (str.indexOf(TX("visualware")) >= 0)
        i = 262144;
      else if (str.indexOf(TX("iis/6.")) >= 0)
        i = 16384;
    }
    if (this.m_nExceptions > 0)
      i = U.min(i, 8192);
    return U.max(256, i - 768);
  }

  private int getMaximumLineLength(int paramInt)
  {
    String str = getServerSoftwareName();
    if (str != null)
    {
      str = str.toLowerCase();
      if (((str.indexOf(TX("apache")) >= 0) || (str.indexOf("visualware") >= 0)) && (paramInt > 65536))
        return 4000;
    }
    return 1000;
  }

  private int[] up(int paramInt)
  {
    InputStream localInputStream = null;
    String str1 = TX("/myspeed/upload.bin");
    try
    {
      paramInt = U.max(0, U.min(paramInt, getMaximumExtraHeaderSize()));
      String str2 = str1 + TX("?n=") + paramInt + TX("&t=") + U.time();
      URL localURL = new URL(getTargetProtocol() + "://" + getTargetAddress() + ":" + getTargetHTTPPort() + str2);
      URLConnection localURLConnection = U.setNoCaching(localURL.openConnection());
      String str3 = localURL.getHost();
      int j = localURL.getPort();
      localURLConnection.setRequestProperty(TX("Host"), str3 + ((j != -1) && (j != 80) ? ":" + j : ""));
      if (paramInt > 0)
      {
        int i = getMaximumLineLength(paramInt);
        j = 1 + paramInt / i;
        int k = paramInt;
        for (int m = j; m > 0; m--)
        {
          str4 = TX("X-Fill-") + m;
          int n = k / m;
          String str5 = makeS(n - str4.length() - 2 - 2);
          localURLConnection.setRequestProperty(str4, str5);
          k -= n;
        }
      }
      long l1 = U.syncTime();
      while (localInputStream.read() != -1);
      long l2 = U.endTime();
      String str4 = localURLConnection.getHeaderField(0);
      if ((str4 != null) && (str4.indexOf(TX("200")) < 0))
        throw new RuntimeException(str4);
      int[] arrayOfInt = { paramInt, (int)(l2 - l1) };
      return arrayOfInt;
    }
    catch (Throwable localThrowable)
    {
      logException(str1, localThrowable);
      doCoyoteWarning();
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
    return null;
  }

  private void goDownloadSpeedTest(InputStream[] paramArrayOfInputStream, OutputStream[] paramArrayOfOutputStream, long paramLong, String paramString)
  {
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;
    long l4 = 0L;
    int i = 0;
    int j = 0;
    long[] arrayOfLong = new long[paramArrayOfInputStream == null ? 0 : paramArrayOfInputStream.length];
    int i8;
    label1024: 
    try
    {
      doRepaint();
      byte[] arrayOfByte = new byte[65536];
      long l5 = paramArrayOfInputStream.length >= 2 ? paramLong / 20L : 0L;
      this.m_times = new long[(int)(paramLong / 256L + 100L)];
      this.m_bytes = new int[(int)(paramLong / 256L + 100L)];
      this.m_nDMeasurements = 0;
      long l6 = U.time();
      for (int i2 = 0; i2 < paramArrayOfInputStream.length; i2++)
      {
        long l8 = l6;
        while ((U.time() - l8 < 1000L) && (i < l5))
          i += U.max(0, doReadSomethingFully(paramArrayOfInputStream[i2], arrayOfByte));
        l6 = U.time();
        long l10;
        while ((l10 = U.time()) == l6)
          j += U.max(0, doReadNoWait(paramArrayOfInputStream[i2], arrayOfByte));
        l6 = l10;
      }
      long l7 = l6;
      long l9 = l6 + 1L;
      long l11 = l6;
      l2 = 0L;
      int i3 = getMaxTimeTimeout();
      int i4;
      long l12;
      while ((this.m_bRun) && (l6 - l7 < i3) && (l2 + i + j < paramLong))
      {
        i4 = 0;
        int i5 = -1;
        while ((this.m_bRun) && (l6 - l7 < i3))
        {
          int i6 = paramArrayOfInputStream.length == 1 ? 0 : paramArrayOfInputStream[i4].available();
          if ((i6 > 0) || (paramArrayOfInputStream.length == 1))
          {
            i5 = doReadSomethingFully(paramArrayOfInputStream[i4], arrayOfByte);
            break;
          }
          i4 = (i4 + 1) % paramArrayOfInputStream.length;
          if (i4 == 0)
          {
            try
            {
              Thread.sleep(1L);
            }
            catch (Exception localException1)
            {
            }
            l6 = U.time();
            this.m_nPercentDone = U.min(100, U.max((int)((l6 - l7) * 100L / i3), paramArrayOfInputStream.length > 0 ? 1 : (int)(l2 * 100L / (paramLong * paramArrayOfInputStream.length))));
            if (l6 > l11)
            {
              l11 = l6 + 250L;
              doRepaint();
            }
          }
        }
        l6 = U.time();
        if (i5 == -1)
          break;
        if ((i5 > 0) && (l6 - l7 > 3L))
        {
          l9 = l6;
          l2 += i5;
          arrayOfLong[i4] += i5;
          l12 = l6 - l7;
          if (this.m_nDMeasurements < this.m_times.length)
          {
            this.m_times[this.m_nDMeasurements] = (l12 - 3L);
            this.m_bytes[this.m_nDMeasurements] = i5;
            this.m_nDMeasurements += 1;
          }
          this.m_nPercentDone = U.min(100, U.max((int)(l12 * 100L / i3), paramArrayOfInputStream.length > 0 ? 1 : (int)(l2 * 100L / (paramLong * paramArrayOfInputStream.length))));
          if (l12 > 500L)
            this.m_lDSpeed = (l12 > 0L ? l2 * 1000L / l12 : 0L);
        }
        if (l6 > l11)
        {
          l11 = l6 + 250L;
          doRepaint();
        }
      }
      l3 = paramArrayOfInputStream.length > 1 ? l9 : U.endTime();
      l1 = l3 - l7;
      l4 = paramLong - i - j - l2;
      if (paramArrayOfOutputStream != null)
      {
        if (paramArrayOfOutputStream.length != paramArrayOfInputStream.length)
          break label1024;
        if (isNetQCheck())
        {
          for (i4 = 0; i4 < paramArrayOfOutputStream.length; i4++)
          {
            paramArrayOfOutputStream[i4].write("**STOP**".getBytes());
            paramArrayOfOutputStream[i4].flush();
          }
          for (i4 = 0; i4 < paramArrayOfInputStream.length; i4++)
            if (readTo(paramArrayOfInputStream[i4], new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 }, 20000000))
            {
              StringBuffer localStringBuffer2 = new StringBuffer();
              int i7;
              while ((i7 = paramArrayOfInputStream[i4].read()) != -1)
              {
                localStringBuffer2.append((char)l12);
                if ((l12 == 115) && (localStringBuffer2.toString().endsWith("&eof=yes")))
                  break;
              }
              this.m_actMetrics = U.stringToHash(localStringBuffer2.toString(), "&\r\n", this.m_actMetrics);
            }
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      logException(paramString, localThrowable1);
    }
    finally
    {
      for (i8 = 0; i8 < paramArrayOfInputStream.length; i8++)
        try
        {
          paramArrayOfInputStream[i8].close();
        }
        catch (Exception localException4)
        {
        }
      i8 = 0;
      do
      {
        try
        {
          paramArrayOfOutputStream[i8].close();
        }
        catch (Exception localException5)
        {
        }
        i8++;
        if (paramArrayOfOutputStream == null)
          break;
      }
      while (i8 < paramArrayOfOutputStream.length);
    }
    try
    {
      this.m_times = U.resize(this.m_times, Math.max(0, this.m_nDMeasurements));
      this.m_bytes = U.resize(this.m_bytes, Math.max(0, this.m_nDMeasurements));
      if (this.m_bRun)
      {
        StringBuffer localStringBuffer1 = new StringBuffer();
        int k = 0;
        int m = 0;
        int i1;
        for (int n = 0; n < this.m_times.length; n = i1)
        {
          for (i1 = n + 1; (i1 < this.m_times.length) && (this.m_times[n] == this.m_times[i1]) && (this.m_bytes[n] == this.m_bytes[i1]); i1++);
          if (isOutputAudit())
            localStringBuffer1.append(TX(" ") + this.m_times[n] + TX(" ") + this.m_bytes[n] + TX(" ") + (i1 - n) + TX("\n"));
          this.m_nMaxPause = (n >= 0 ? (int)U.max(this.m_nMaxPause, this.m_times[n] - (n == 0 ? 0L : this.m_times[(n - 1)])) : 0);
          k = (int)(k + (n >= 0 ? this.m_times[n] - (n == 0 ? 0L : this.m_times[(n - 1)]) : 0L));
          m += (n >= 0 ? 1 : 0);
        }
        this.m_nAvgPause = (m > 0 ? Math.max(k / m, 1) : 0);
        if (isOutputAudit())
        {
          n = 65536;
          String str1 = localStringBuffer1.toString();
          if (str1.length() > n)
          {
            String str2 = str1.substring(0, n / 2);
            String str3 = str1.substring(str1.length() - n / 2);
            str1 = str2.substring(0, str2.lastIndexOf('\n') + 1) + TX(" ....") + str3.substring(str3.indexOf('\n'));
          }
          OUT(str1);
        }
        else
        {
          OUT(TX("audit log disabled"));
        }
        OUT(TX(" max pause=") + this.m_nMaxPause + TX(" ms"));
        this.m_lDSpeed = (l1 > 0L ? l2 * 1000L / l1 : 0L);
        OUT(TX("DOWNLOADED ") + U.addCommas(l2) + TX(" bytes in ") + l1 + TX(" ms (") + U.addCommas(this.m_lDSpeed * 8L) + TX(" bps) SZ=") + paramLong + TX(" F1=") + i + TX(" F2=") + j + TX(" EX=") + l4);
        OUT(TX("Sockets used:"));
        for (n = 0; n < paramArrayOfInputStream.length; n++)
          OUT(n + 1 + TX(": ") + arrayOfLong[n] * 1000L / l2 / 10.0D + TX(" %"));
      }
      if (this.m_bRun)
        calculateDownloadStats(l1, l3);
      doRepaint();
    }
    catch (Throwable localThrowable2)
    {
      logException(paramString, localThrowable2);
    }
    finally
    {
      this.m_nPercentDone = 100;
    }
    U.sleep(3000L);
  }

  private static boolean readTo(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    int j = 0;
    while (i < paramArrayOfByte.length)
    {
      int k = paramInputStream.read();
      if ((k == -1) || (j++ > paramInt))
        return false;
      if (k != paramArrayOfByte[i])
        i = 0;
      if (k == paramArrayOfByte[i])
        i++;
    }
    return true;
  }

  private int calculateQOS(long paramLong1, long paramLong2, long[] paramArrayOfLong, int[] paramArrayOfInt)
  {
    int i = paramLong1 > 1000L ? 5 : 3;
    long l1 = 999999999L;
    long l2 = 0L;
    for (int j = 0; j < i; j++)
    {
      long l3 = paramLong1 * j / i;
      long l4 = paramLong1 * (j + 1) / i;
      long l5 = sum(paramArrayOfLong, paramArrayOfInt, l3, l4, paramLong2);
      long l6 = l4 - l3;
      long l7 = l6 > 0L ? l5 * 8L * 1000L / l6 : 0L;
      l1 = U.min(l5, l1);
      l2 = U.max(l5, l2);
      OUT(TX(" [") + l3 + TX(",") + l4 + TX(") = ") + U.addCommas(l5) + TX(" bytes") + (l7 > 0L ? TX(" (") + U.addCommas(l7) + " bps)" : ""));
    }
    if (l2 > 0L)
    {
      j = (int)(l1 * 100L / l2);
      OUT(TX("QOS=") + j + TX("% (") + l1 + TX("/") + l2 + TX(")"));
      return j;
    }
    return 0;
  }

  private void calculateDownloadStats(long paramLong1, long paramLong2)
  {
    if (this.m_bRun)
      this.m_nQOS = calculateQOS(paramLong1, paramLong2, this.m_times, this.m_bytes);
    if (this.m_bRun)
    {
      int[][] arrayOfInt = getPauseTable(this.m_times, this.m_bytes);
      int[] arrayOfInt1 = (arrayOfInt == null) || (arrayOfInt.length < 3) ? null : arrayOfInt[0];
      int[] arrayOfInt2 = (arrayOfInt == null) || (arrayOfInt.length < 3) ? null : arrayOfInt[1];
      int[] arrayOfInt3 = (arrayOfInt == null) || (arrayOfInt.length < 3) ? null : arrayOfInt[2];
      if ((arrayOfInt1 != null) && (arrayOfInt2 != null) && (arrayOfInt3 != null) && (arrayOfInt1.length == arrayOfInt2.length) && (arrayOfInt3.length == arrayOfInt2.length))
      {
        int i = 8;
        int j = 0;
        int k = 0;
        int i1 = 0;
        int i3;
        for (int i2 = 0; i2 < arrayOfInt1.length; i2++)
        {
          i3 = arrayOfInt3[i2] * 1000;
          int i4 = arrayOfInt1[i2];
          if (i4 < 5)
            i4 = 5;
          i3 /= i4;
          if (i3 > j)
          {
            j = i3;
            k = i2;
          }
          i1 += arrayOfInt3[i2];
        }
        OUT("Weighted modal pause is " + arrayOfInt1[k] + " ms, count " + arrayOfInt3[k]);
        if (arrayOfInt3[k] < i)
        {
          j = 0;
          for (i2 = 0; i2 < arrayOfInt1.length; i2++)
            if (arrayOfInt3[i2] > j)
            {
              j = arrayOfInt3[i2];
              k = i2;
            }
          OUT("Unweighted modal pause is " + arrayOfInt1[k] + " ms, count " + arrayOfInt3[k]);
        }
        OUT("Pause frequencies\n");
        for (i2 = 0; i2 < arrayOfInt1.length; i2++)
        {
          i3 = 20 * arrayOfInt3[i2] / i1;
          for (String str = ""; i3-- != 0; str = str + "#");
          OUT(align(new StringBuilder().append(arrayOfInt1[i2]).toString(), 5) + " ms | " + align(new StringBuilder().append(arrayOfInt3[i2]).toString(), 4) + " | " + align(new StringBuilder().append(arrayOfInt2[i2]).toString(), 7) + " bytes | " + str);
        }
        if (arrayOfInt3[k] >= i)
        {
          long l = 0L;
          int i5 = 0;
          int i6 = 0;
          int m = arrayOfInt1[k] - (1 + arrayOfInt1[k] / 4);
          int n = arrayOfInt1[k] + (1 + arrayOfInt1[k] / 4);
          OUT("Most likely pause is " + arrayOfInt1[k] + " ms");
          OUT("Considering pauses between " + m + " ms and " + n + " ms...");
          for (int i7 = 0; i7 < arrayOfInt1.length; i7++)
            if ((arrayOfInt1[i7] >= m) && (arrayOfInt1[i7] <= n))
            {
              l += arrayOfInt2[i7];
              i5 += arrayOfInt1[i7] * arrayOfInt3[i7];
              i6 += arrayOfInt3[i7];
            }
          if (i6 > i1 / 8)
          {
            this.m_lBandwidth = (l * 1000L / i5);
            OUT("MaxLineSpeedKbps = " + this.m_lBandwidth * 8L / 1000L + " (Data=" + l + ", Time=" + i5 + " (" + i6 + " of " + i1 + " samples considered))");
          }
          else
          {
            OUT("Insufficient valid samples (" + i6 + " of " + i1 + ")");
          }
        }
        else
        {
          OUT("Could not establish most likely pause");
        }
        if ((this.m_lBandwidth > 0L) && (this.m_lBandwidth < this.m_lDSpeed))
          this.m_lBandwidth = this.m_lDSpeed;
      }
    }
  }

  private static int[][] getPauseTable(long[] paramArrayOfLong, int[] paramArrayOfInt)
  {
    int i = 100;
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = new int[i];
    int[] arrayOfInt3 = new int[i];
    int j = 0;
    int k = 1;
    while ((paramArrayOfLong != null) && (paramArrayOfInt != null) && (k < Math.min(paramArrayOfLong.length, paramArrayOfInt.length)))
    {
      int m = k;
      int n = 0;
      while ((k < Math.min(paramArrayOfLong.length, paramArrayOfInt.length)) && (paramArrayOfLong[k] == paramArrayOfLong[m]))
      {
        n += paramArrayOfInt[k];
        k++;
      }
      int i1 = Math.max(1, (int)(paramArrayOfLong[m] - paramArrayOfLong[(m - 1)]));
      for (int i2 = 0; i2 < j; i2++)
        if ((arrayOfInt1[i2] >= i1) || ((i2 > 0) && (arrayOfInt1[i2] == 0)))
          break;
      if (arrayOfInt1[i2] != i1)
      {
        if (j + 1 >= arrayOfInt1.length)
        {
          arrayOfInt1 = resizeArray(arrayOfInt1, j + i);
          arrayOfInt2 = resizeArray(arrayOfInt2, j + i);
          arrayOfInt3 = resizeArray(arrayOfInt3, j + i);
        }
        arrayOfInt1 = shiftDown(arrayOfInt1, i2);
        arrayOfInt2 = shiftDown(arrayOfInt2, i2);
        arrayOfInt3 = shiftDown(arrayOfInt3, i2);
        arrayOfInt1[i2] = i1;
        j++;
      }
      else if ((i2 == 0) && (arrayOfInt2[0] == 0))
      {
        arrayOfInt1[i2] = i1;
        j++;
      }
      arrayOfInt2[i2] += n;
      arrayOfInt3[i2] += 1;
    }
    return new int[][] { resizeArray(arrayOfInt1, j), resizeArray(arrayOfInt2, j), resizeArray(arrayOfInt3, j) };
  }

  private static String align(String paramString, int paramInt)
  {
    if (paramString.length() > paramInt)
      return paramString;
    String str = "                             " + paramString;
    return str.substring(str.length() - paramInt);
  }

  private static int[] resizeArray(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, Math.min(paramInt, paramArrayOfInt.length));
    return arrayOfInt;
  }

  private static int[] shiftDown(int[] paramArrayOfInt, int paramInt)
  {
    for (int i = paramArrayOfInt.length - 1; i > paramInt; i--)
      paramArrayOfInt[i] = paramArrayOfInt[(i - 1)];
    return paramArrayOfInt;
  }

  private int doReadSomethingFully(InputStream paramInputStream, byte[] paramArrayOfByte)
  {
    int i = paramInputStream.read(paramArrayOfByte, 0, 256);
    if (i > 0)
    {
      int j = paramInputStream.available();
      if (j > 0)
        i += U.max(0, paramInputStream.read(paramArrayOfByte, i, U.min(paramArrayOfByte.length - i, j)));
    }
    return i;
  }

  private int doReadNoWait(InputStream paramInputStream, byte[] paramArrayOfByte)
  {
    int i = paramInputStream.available();
    return i > 0 ? paramInputStream.read(paramArrayOfByte, 0, U.min(paramArrayOfByte.length, i)) : 0;
  }

  private void doCoyoteWarning()
  {
    String str1 = getServerSoftwareName();
    if ((str1 != null) && (str1.toLowerCase().indexOf(TX("coyote")) > 0) && (!this.m_bDidCoyoteWarning))
    {
      this.m_bDidCoyoteWarning = true;
      String str2 = TX("ERROR: The Jakarta/Tomcat Coyote HTTP connector maxHttpHeaderSize is too small.");
      String str3 = TX("SOLUTION: In the <Connector> (server.xml), set 'maxHttpHeaderSize=\"131072\"'");
      String[] arrayOfString = { str2, str3 };
      for (int i = 0; i < arrayOfString.length; i++)
        ERR(arrayOfString[i]);
    }
  }

  private int getMaxTimeTimeout()
  {
    return this.m_nState == 2 ? 2000 : U.max(2000, U.min(90000, iniGetInteger(TX("speed_testtime"), TX("timeout"), 8000)));
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

  private static String makeS(int paramInt)
  {
    char[] arrayOfChar = new char[paramInt];
    for (int i = 0; i < arrayOfChar.length; i++)
      arrayOfChar[i] = BASE62[U.rand(BASE62.length)];
    return new String(arrayOfChar);
  }

  private String readUrl(String paramString)
  {
    URL localURL = new URL(this.m_plugin.base(), paramString);
    byte[] arrayOfByte = new byte[131072];
    InputStream localInputStream = null;
    int i = 0;
    int j = 0;
    try
    {
      URLConnection localURLConnection = localURL.openConnection();
      localURLConnection.setDoOutput(true);
      localURLConnection.setDoInput(true);
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
    return (i == -1) && (j == 0) ? null : new String(arrayOfByte, 0, j);
  }

  public void run()
  {
    if (Thread.currentThread() == this.m_tUpdatePercent)
      runUpdatePercentThread();
  }

  public void runUpdatePercentThread()
  {
    label118: 
    try
    {
      long l1 = System.currentTimeMillis();
      int i = getMaxTimeTimeout();
      int j = i + 10000;
      long l2 = 0L;
      do
      {
        long l3 = System.currentTimeMillis();
        this.m_nPercentDone = Math.min(99, (int)(100.0D * log2(1.0D + (l3 - l1) / j)));
        if (l3 > l2)
        {
          doRepaint();
          l2 = l3 + 200L;
        }
        try
        {
          Thread.sleep(100L);
        }
        catch (InterruptedException localInterruptedException)
        {
          break label118;
        }
        if (!this.m_bRunUpdatePercent)
          break;
      }
      while (this.m_nPercentDone < 100);
    }
    finally
    {
      this.m_tUpdatePercent = null;
    }
  }

  private static double log2(double paramDouble)
  {
    return Math.log(paramDouble) / Math.log(2.0D);
  }

  public void doRepaint()
  {
    ((speed)this.m_plugin).doRepaint();
  }

  private void logException(String paramString, Throwable paramThrowable)
  {
    this.m_nExceptions += 1;
    this.m_plugin.logException(paramString, paramThrowable);
  }

  private String getServerSoftwareName()
  {
    return G.g_server;
  }

  public void OUT(String paramString)
  {
    this.m_plugin.OUT(paramString);
  }

  private void ERR(String paramString)
  {
    this.m_plugin.ERR(paramString);
  }

  private String iniGetString(String paramString)
  {
    return this.m_plugin.iniGetString(paramString);
  }

  private String iniGetString(String paramString1, String paramString2)
  {
    return this.m_plugin.iniGetString(paramString1, paramString2);
  }

  private int iniGetInteger(String paramString, int paramInt)
  {
    return this.m_plugin.iniGetInteger(paramString, paramInt);
  }

  private int iniGetInteger(String paramString1, String paramString2, int paramInt)
  {
    return this.m_plugin.iniGetInteger(paramString1, paramString2, paramInt);
  }

  private long iniGetLong(String paramString, long paramLong)
  {
    return this.m_plugin.iniGetLong(paramString, paramLong);
  }

  private String getTargetProtocol()
  {
    String str = this.m_plugin.base().getProtocol();
    int i = str != null ? str.indexOf("://") : -1;
    str = i > 0 ? str.substring(0, i) : str;
    System.out.println("Target protocol is " + str + " (base is " + this.m_plugin.base() + ")");
    return str == null ? "http" : str;
  }

  private String getTargetAddress()
  {
    return this.m_plugin.base().getHost();
  }

  private int getTargetHTTPPort()
  {
    String str = this.m_plugin.base().getProtocol();
    int i = str != null ? str.indexOf("://") : -1;
    str = i > 0 ? str.substring(0, i) : str;
    str = str == null ? "http" : str;
    int j = str.toLowerCase().equals("https") ? 443 : 80;
    int k = this.m_plugin.base().getPort();
    k = k <= 0 ? j : k;
    System.out.println("Target port is " + k + " (base is " + this.m_plugin.base() + ")");
    return k;
  }

  private boolean isHttpProxyPresent()
  {
    return G.g_bServerCache;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.speed.SpeedTest
 * JD-Core Version:    0.6.2
 */