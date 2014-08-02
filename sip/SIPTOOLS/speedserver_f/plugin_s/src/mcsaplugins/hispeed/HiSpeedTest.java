package mcsaplugins.hispeed;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.U;

public class HiSpeedTest extends AppletTest
  implements Runnable
{
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
  private int m_nDSockets = 0;
  private int m_nPercentDone;
  private boolean m_bRunUpdateThread;
  private int m_nState;
  private boolean m_bRun;

  public HiSpeedTest(AppletPlugin paramAppletPlugin, HiSpeedTab paramHiSpeedTab)
  {
    super(paramAppletPlugin, paramHiSpeedTab);
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

  public int getAvgRTT()
  {
    return this.m_nAvgRTT;
  }

  public int getMaxRTT()
  {
    return this.m_nMaxRTT;
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
    return this.m_nDSockets;
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
    return !"no".equals(iniGetString("hispeed_doupload"));
  }

  public boolean isRunDownload()
  {
    return !"no".equals(iniGetString("hispeed_dodownload"));
  }

  public boolean isRunRTT()
  {
    return this.m_bRanRTT;
  }

  public String[] getAdvancedDataItem(String paramString)
  {
    String str1 = null;
    String str2 = null;
    if (TX("dspeed").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      str2 = (this.m_lDSpeed != 0L ? this.m_lDSpeed * 8L : TX("--")) + TX(" ") + this.m_plugin.RC(TX("bitsps"));
    }
    else if (TX("uspeed").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      str2 = (this.m_lUSpeed != 0L ? this.m_lUSpeed * 8L : TX("--")) + TX(" ") + this.m_plugin.RC(TX("bitsps"));
    }
    else if (TX("numlines").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      int i = "yes".equals(this.m_plugin.iniGetProfessionalString("hispeed.separateupdown")) ? 1 : 2;
      int k = this.m_plugin.iniGetInteger("voip_bytesperpacket", "voipbytesperpacket", 8) * this.m_plugin.iniGetInteger("voip_packetspersecond", "voippacketspersecond", 75) * this.m_plugin.iniGetInteger("voip_lines", "voiplines", 1);
      str2 = (this.m_lUSpeed != 0L) && (this.m_lDSpeed != 0L) ? TX("") + (int)Math.floor(Math.min(this.m_lDSpeed, this.m_lUSpeed) / (i * k)) : TX("--");
    }
    else if (TX("qos").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      str2 = (this.m_nQOS >= 0 ? TX("") + this.m_nQOS : TX("--")) + TX(" %");
    }
    else if (TX("uqos").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      str2 = (this.m_nUQOS >= 0 ? TX("") + this.m_nUQOS : TX("--")) + TX(" %");
    }
    else if (TX("maxpause").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      str2 = (this.m_nMaxPause != 0 ? TX("") + this.m_nMaxPause : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("avgpause").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      str2 = (this.m_nAvgPause != 0 ? TX("") + this.m_nAvgPause : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("minrtt").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      str2 = ((this.m_bRanRTT) && (this.m_nMinRTT != 0) ? TX("") + this.m_nMinRTT : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("avgrtt").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
      str2 = ((this.m_bRanRTT) && (this.m_nAvgRTT != 0) ? TX("") + this.m_nAvgRTT : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else
    {
      long l;
      if (TX("bandwidth").equals(paramString))
      {
        str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
        l = getBandwidth();
        str2 = (l >= this.m_lDSpeed) && (l > 0L) ? l * 8L + this.m_plugin.RC(TX("bitsps")) : TX("--");
      }
      else if (TX("routespeed").equals(paramString))
      {
        str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
        l = getTCPMaxRouteSpeed();
        str2 = (l >= this.m_lDSpeed) && (l > 0L) ? l * 8L + this.m_plugin.RC(TX("bitsps")) : TX("--");
      }
      else if (TX("forcedidle").equals(paramString))
      {
        str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
        int j = getDownloadForcedIdle();
        str2 = j >= 0 ? j + TX(" %") : TX("--");
      }
      else if (TX("routeconc").equals(paramString))
      {
        str1 = this.m_plugin.RC(TX("hispeed.adv_") + paramString);
        float f = getDownloadRouteConcurrency();
        str2 = f > 0.0F ? f : TX("--");
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
      str = this.m_plugin.RC(TX("hispeed.dspeednone"));
      i = -1;
      if (this.m_lDSpeed * 8L > this.m_plugin.iniGetInteger(TX("hispeed.dspeedwarnnum"), 75000))
      {
        str = this.m_plugin.RC(TX("hispeed.dspeedgood"), U.bps2s(this.m_lDSpeed * 8L));
        i = 0;
      }
      else if (this.m_lDSpeed * 8L > this.m_plugin.iniGetInteger(TX("hispeed.dspeedbadnum"), 55000))
      {
        str = this.m_plugin.RC(TX("hispeed.dspeedwarn"), U.bps2s(this.m_lDSpeed * 8L));
        i = 1;
      }
      else if (this.m_lDSpeed * 8L > 0L)
      {
        str = this.m_plugin.RC(TX("hispeed.dspeedbad"), U.bps2s(this.m_lDSpeed * 8L));
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("uspeed").equals(paramString))
    {
      str = this.m_plugin.RC(TX("hispeed.uspeednone"));
      i = -1;
      if (this.m_lUSpeed * 8L > this.m_plugin.iniGetInteger(TX("hispeed.uspeedwarnnum"), 75000))
      {
        str = this.m_plugin.RC(TX("hispeed.uspeedgood"), U.bps2s(this.m_lUSpeed * 8L));
        i = 0;
      }
      else if (this.m_lUSpeed * 8L > this.m_plugin.iniGetInteger(TX("hispeed.uspeedbadnum"), 55000))
      {
        str = this.m_plugin.RC(TX("hispeed.uspeedwarn"), U.bps2s(this.m_lUSpeed * 8L));
        i = 1;
      }
      else if (this.m_lUSpeed * 8L > 0L)
      {
        str = this.m_plugin.RC(TX("hispeed.uspeedbad"), U.bps2s(this.m_lUSpeed * 8L));
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("qos").equals(paramString))
    {
      str = this.m_plugin.RC(TX("hispeed.qosnone"));
      i = -1;
      if (this.m_nQOS > this.m_plugin.iniGetInteger(TX("hispeed.qoswarnnum"), 85))
      {
        str = this.m_plugin.RC(TX("hispeed.qosgood"), this.m_nQOS);
        i = 0;
      }
      else if (this.m_nQOS > this.m_plugin.iniGetInteger(TX("hispeed.qosbadnum"), 55))
      {
        str = TX("");
        str = this.m_plugin.RC(TX("hispeed.qoswarn"), this.m_nQOS);
        i = 1;
      }
      else if (this.m_nQOS >= 0)
      {
        str = TX("");
        str = this.m_plugin.RC(TX("hispeed.qosbad"), this.m_nQOS);
        i = 2;
      }
      return new Object[] { new Integer(i), str };
    }
    return null;
  }

  public void runTest()
  {
    this.m_bRun = true;
    this.m_rtt = null;
    doRttTest(1);
    this.m_nState = 3;
    if (isRunDownload())
    {
      this.m_nPercentDone = 0;
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
    long[] arrayOfLong2 = getUploadTimesArray();
    int[] arrayOfInt2 = getUploadBytesArray();
    if ((arrayOfLong1 != null) && (arrayOfInt1 != null))
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("hispeed.downloadgraph")), arrayOfLong1, arrayOfInt1, null, null);
    if ((arrayOfLong2 != null) && (arrayOfInt2 != null))
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("hispeed.uploadgraph")), arrayOfLong2, arrayOfInt2, null, null);
    doRepaint();
    this.m_nState = 9;
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
    doNewDownloadSpeedTest();
  }

  private void doUploadSpeedTest()
  {
    if (isRunUpload())
    {
      this.m_uploadType = 's';
      doNewUploadSpeedTest();
    }
  }

  private boolean doNewDownloadSpeedTest()
  {
    boolean bool1 = false;
    try
    {
      String str = "";
      int i = 0;
      str = TX("mss.download.port");
      i = iniGetInteger(TX("hispeed.redirectdownloadport"), -1);
      i = i == -1 ? iniGetInteger(str, -1) : i;
      if (i > 0)
      {
        this.m_nDSockets = Math.min(iniGetInteger("hispeed_numsockets", 4), 128);
        int j = this.m_nDSockets;
        OUT(TX("DOWNLOADING via ") + str + TX("=") + i + TX(" (") + j + TX(" sockets)"));
        Socket[] arrayOfSocket = new Socket[j];
        InputStream[] arrayOfInputStream = new InputStream[j];
        for (int k = 0; k < j; k++)
        {
          localObject = new Socket(getTargetAddress(), i);
          U.setReceiveBufferSize((Socket)localObject, 65536);
          if (U.getReceiveBufferSize((Socket)localObject) > 0)
            OUT(TX(new StringBuilder(" #").append(k + 1).append(": socket buffers: snd=").toString()) + U.getSendBufferSize((Socket)localObject) + TX(" rcv=") + U.getReceiveBufferSize((Socket)localObject));
          arrayOfInputStream[k] = ((Socket)localObject).getInputStream();
          arrayOfSocket[k] = localObject;
        }
        k = Math.max(1, Math.min(j, iniGetInteger(TX("hispeed_numthreads"), 4)));
        Object localObject = new BufferedSocketInputStream[k];
        double d = j / k;
        boolean bool2 = TX("yes").equals(iniGetString("hispeed_threadhighprio"));
        for (int m = 0; m < k; m++)
        {
          int n = (int)(m * d);
          int i1 = (int)((m + 1) * d);
          int i2 = Math.min(arrayOfInputStream.length - n, i1 - n);
          localObject[m] = new BufferedSocketInputStream(arrayOfInputStream, n, i2, bool2 ? 10 : 5);
          OUT(TX("Thread #") + m + TX(", ") + i2 + TX(" sockets (#") + n + TX(" - #") + (i1 - 1) + TX(")"));
        }
        bool1 = true;
        goDownloadSpeedTest((InputStream[])localObject, mssXFER(), TX("MySpeedServlet"));
      }
      else if (i <= 0)
      {
        OUT(str + TX("=disabled"));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      ERR(" " + localException);
    }
    return bool1;
  }

  private long mssXFER()
  {
    return iniGetLong(TX("hispeed.xfer"), 10485760L);
  }

  private boolean doNewUploadSpeedTest()
  {
    Socket[] arrayOfSocket = null;
    int i8;
    try
    {
      String str = TX("mss.upload.port");
      int i = iniGetInteger(TX("hispeed.redirectuploadport"), -1);
      i = i == -1 ? iniGetInteger(str, -1) : i;
      if (i > 0)
      {
        int j = Math.min(iniGetInteger("hispeed_numusockets", 4), 128);
        arrayOfSocket = new Socket[j];
        DataInputStream[] arrayOfDataInputStream = new DataInputStream[j];
        OutputStream[] arrayOfOutputStream = new OutputStream[j];
        OUT(TX("UPLOADING via ") + str + TX("=") + i + TX(" {") + j + TX(" sockets)"));
        int k = getMaxTimeTimeout();
        for (int m = 0; m < j; m++)
        {
          arrayOfSocket[m] = new Socket(getTargetAddress(), i);
          U.setSendBufferSize(arrayOfSocket[m], 65536);
          if (U.getReceiveBufferSize(arrayOfSocket[m]) > 0)
            OUT(TX(" socket buffers: snd=") + U.getSendBufferSize(arrayOfSocket[m]) + TX(" rcv=") + U.getReceiveBufferSize(arrayOfSocket[m]));
          arrayOfDataInputStream[m] = new DataInputStream(arrayOfSocket[m].getInputStream());
          arrayOfOutputStream[m] = arrayOfSocket[m].getOutputStream();
        }
        long l1 = U.time();
        long l2 = l1;
        long l3 = l2;
        long l4 = 0L;
        long l5 = 0L;
        long l6 = mssXFER();
        SocketSendThread[] arrayOfSocketSendThread = new SocketSendThread[j];
        for (int n = 0; n < j; n++)
          arrayOfSocketSendThread[n] = new SocketSendThread(arrayOfOutputStream[n], l6);
        while ((this.m_bRun) && (l2 - l1 < k) && (l4 < l6))
        {
          n = l4 == 0L ? 1 : 0;
          l4 = 0L;
          for (int i1 = 0; i1 < arrayOfSocketSendThread.length; i1++)
            l4 += arrayOfSocketSendThread[i1].getTotalWritten();
          if (n != 0)
            l5 = l4;
          this.m_lUSpeed = (l2 > l1 ? (l4 - l5) * 1000L / (l2 - l1) : 0L);
          long l7 = l2 - l1;
          this.m_nPercentDone = U.min(70, U.max((int)(l7 * 70L / k), (int)(l4 * 70L / l6)));
          if (l2 > l3)
          {
            l3 = l2 + 20L;
            doRepaint();
          }
          l2 = U.time();
        }
        for (n = 0; n < arrayOfSocketSendThread.length; n++)
          arrayOfSocketSendThread[n].stop();
        this.m_bRunUpdateThread = true;
        new Thread(this).start();
        System.out.println("test complete, sending stop requests");
        int[] arrayOfInt1 = new int[j];
        int i2 = 0;
        for (int i3 = 0; (i3 < j) && (this.m_bRun); i3++)
        {
          arrayOfOutputStream[i3].write("**STOP**".getBytes());
          System.out.println("   write " + i3 + " at " + System.currentTimeMillis());
          arrayOfOutputStream[i3].flush();
          System.out.println("   flush " + i3 + " at " + System.currentTimeMillis());
          arrayOfInt1[i3] = arrayOfDataInputStream[i3].readInt();
          System.out.println("   read " + i3 + " at " + System.currentTimeMillis());
          i2 += arrayOfInt1[i3];
          System.out.println("stop " + i3 + " at " + System.currentTimeMillis());
        }
        System.out.println("all stop at " + System.currentTimeMillis());
        long[] arrayOfLong1 = new long[i2];
        int[] arrayOfInt2 = new int[i2];
        l4 = 0L;
        l1 = U.time();
        long[] arrayOfLong2 = new long[j];
        int i4 = 0;
        for (int i5 = 0; (this.m_bRun) && (i5 < j); i5++)
        {
          for (int i6 = 0; (this.m_bRun) && (i6 < arrayOfInt1[i5]); i6++)
          {
            arrayOfLong1[i4] = arrayOfDataInputStream[i5].readInt();
            arrayOfInt2[i4] = arrayOfDataInputStream[i5].readInt();
            arrayOfLong2[i5] += arrayOfInt2[i4];
            i4++;
          }
          l4 += arrayOfLong2[i5];
        }
        System.out.println("all read at " + System.currentTimeMillis());
        U.sort(arrayOfLong1, arrayOfInt2);
        i5 = U.removeDuplicates(arrayOfLong1, arrayOfInt2);
        if (i5 != arrayOfLong1.length)
        {
          arrayOfLong1 = U.resize(arrayOfLong1, i5);
          arrayOfInt2 = U.resize(arrayOfInt2, i5);
        }
        System.out.println("sort at " + System.currentTimeMillis());
        this.m_bRunUpdateThread = false;
        this.m_nPercentDone = 100;
        doRepaint();
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i7 = 0; i7 < arrayOfLong1.length; i7++)
          localStringBuffer.append(TX(" ") + arrayOfLong1[i7] + " " + arrayOfInt2[i7] + "\n");
        OUT(localStringBuffer.toString());
        OUT(TX("Sockets used:"));
        for (i7 = 0; i7 < j; i7++)
          if (l4 == 0L)
            OUT(i7 + 1 + TX(": no data"));
          else
            OUT(i7 + 1 + TX(": ") + arrayOfLong2[i7] * 1000L / l4 / 10.0D + TX(" %"));
        if (arrayOfLong1.length > 0)
        {
          long l8 = arrayOfLong1[(arrayOfLong1.length - 1)] - arrayOfLong1[0];
          this.m_lUSpeed = (l8 > 0L ? l4 * 1000L / l8 : 0L);
          this.m_ubytes = arrayOfInt2;
          this.m_utimes = arrayOfLong1;
          OUT(TX("UPLOADED ") + U.addCommas(l4) + TX(" bytes in ") + l8 + TX(" ms (") + U.addCommas(this.m_lUSpeed * 8L) + TX(" bps)"));
          this.m_nUQOS = calculateQOS(l8, this.m_utimes[(this.m_utimes.length - 1)], this.m_utimes, this.m_ubytes);
        }
        return true;
      }
      if (i == 0)
        OUT(str + TX("=disabled"));
    }
    catch (Exception localException1)
    {
      ERR(" " + localException1);
      localException1.printStackTrace();
    }
    finally
    {
      this.m_bRunUpdateThread = false;
      i8 = 0;
      do
      {
        try
        {
          arrayOfSocket[i8].close();
        }
        catch (Exception localException4)
        {
        }
        i8++;
        if (arrayOfSocket == null)
          break;
      }
      while (i8 < arrayOfSocket.length);
    }
    return false;
  }

  public void run()
  {
    long l = System.currentTimeMillis();
    while ((this.m_bRunUpdateThread) && (System.currentTimeMillis() < l + 5000L))
    {
      this.m_nPercentDone = Math.min(100, 70 + (int)(30L * (System.currentTimeMillis() - l) / 5000L));
      doRepaint();
      try
      {
        Thread.sleep(20L);
      }
      catch (Exception localException)
      {
      }
    }
    this.m_nPercentDone = 100;
    doRepaint();
  }

  private void goDownloadSpeedTest(InputStream[] paramArrayOfInputStream, long paramLong, String paramString)
  {
    int i6;
    try
    {
      doRepaint();
      byte[] arrayOfByte = new byte[65536];
      this.m_times = new long[(int)(paramLong / 256L + 100L)];
      this.m_bytes = new int[(int)(paramLong / 256L + 100L)];
      this.m_nDMeasurements = 0;
      int i = 0;
      int j = 0;
      long l1 = U.time();
      long l2 = l1;
      long l3 = l1 + 1L;
      long l4 = l1;
      long l5 = 0L;
      long[] arrayOfLong = new long[paramArrayOfInputStream.length];
      int k = getMaxTimeTimeout();
      while ((this.m_bRun) && (l1 - l2 < k))
      {
        int m = 0;
        int n = -1;
        while ((this.m_bRun) && (l1 - l2 < k))
        {
          int i1 = paramArrayOfInputStream.length == 1 ? 0 : paramArrayOfInputStream[m].available();
          if ((i1 > 0) || (paramArrayOfInputStream.length == 1))
          {
            n = doReadSomethingFully(paramArrayOfInputStream[m], arrayOfByte);
            break;
          }
          m = (m + 1) % paramArrayOfInputStream.length;
          if (m == 0)
          {
            try
            {
              Thread.sleep(1L);
            }
            catch (Exception localException1)
            {
            }
            l1 = U.time();
            this.m_nPercentDone = U.min(100, U.max((int)((l1 - l2) * 100L / k), paramArrayOfInputStream.length > 0 ? 1 : (int)(l5 * 100L / (paramLong * paramArrayOfInputStream.length))));
            if (l1 > l4)
            {
              l4 = l1 + 20L;
              doRepaint();
            }
          }
        }
        l1 = U.time();
        if (n == -1)
          break;
        if ((n > 0) && (l1 - l2 > 3L))
        {
          l3 = l1;
          l5 += n;
          arrayOfLong[m] += n;
          l7 = l1 - l2;
          if (this.m_nDMeasurements < this.m_times.length)
          {
            this.m_times[this.m_nDMeasurements] = (l7 - 3L);
            this.m_bytes[this.m_nDMeasurements] = n;
            this.m_nDMeasurements += 1;
          }
          this.m_nPercentDone = U.min(100, U.max((int)(l7 * 100L / k), paramArrayOfInputStream.length > 0 ? 1 : (int)(l5 * 100L / (paramLong * paramArrayOfInputStream.length))));
          if (l7 > 500L)
            this.m_lDSpeed = (l7 > 0L ? l5 * 1000L / l7 : 0L);
        }
        if (l1 > l4)
        {
          l4 = l1 + 250L;
          doRepaint();
        }
      }
      long l6 = paramArrayOfInputStream.length > 1 ? l3 : U.endTime();
      long l7 = l6 - l2;
      long l8 = paramLong - i - j - l5;
      this.m_times = U.resize(this.m_times, this.m_nDMeasurements);
      this.m_bytes = U.resize(this.m_bytes, this.m_nDMeasurements);
      if (this.m_bRun)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        int i2 = 0;
        int i3 = 0;
        int i5;
        for (int i4 = 0; i4 < this.m_times.length; i4 = i5)
        {
          for (i5 = i4 + 1; (i5 < this.m_times.length) && (this.m_times[i4] == this.m_times[i5]) && (this.m_bytes[i4] == this.m_bytes[i5]); i5++);
          localStringBuffer.append(TX(" ") + this.m_times[i4] + TX(" ") + this.m_bytes[i4] + TX(" ") + (i5 - i4) + TX("\n"));
          this.m_nMaxPause = (i4 > 0 ? (int)U.max(this.m_nMaxPause, this.m_times[i4] - this.m_times[(i4 - 1)]) : 0);
          i2 = (int)(i2 + (i4 > 0 ? this.m_times[i4] - this.m_times[(i4 - 1)] : 0L));
          i3 += (i4 > 0 ? 1 : 0);
        }
        this.m_nAvgPause = (i3 > 0 ? Math.max(i2 / i3, 1) : 0);
        i4 = 65536;
        String str1 = localStringBuffer.toString();
        if (str1.length() > i4)
        {
          String str2 = str1.substring(0, i4 / 2);
          String str3 = str1.substring(str1.length() - i4 / 2);
          str1 = str2.substring(0, str2.lastIndexOf('\n') + 1) + TX(" ....") + str3.substring(str3.indexOf('\n'));
        }
        OUT(str1);
        OUT(TX(" max pause=") + this.m_nMaxPause + TX(" ms"));
        this.m_lDSpeed = (l7 > 0L ? l5 * 1000L / l7 : 0L);
        OUT(TX("DOWNLOADED ") + U.addCommas(l5) + TX(" bytes in ") + l7 + TX(" ms (") + U.addCommas(this.m_lDSpeed * 8L) + TX(" bps) SZ=") + paramLong + TX(" F1=") + i + TX(" F2=") + j + TX(" EX=") + l8);
        OUT(TX("Threads used:"));
        for (i4 = 0; i4 < paramArrayOfInputStream.length; i4++)
          OUT(i4 + 1 + TX(": ") + arrayOfLong[i4] * 1000L / l5 / 10.0D + TX(" %"));
      }
      if (this.m_bRun)
        calculateDownloadStats(l7, l6);
      doRepaint();
    }
    catch (Throwable localThrowable)
    {
      logException(paramString, localThrowable);
    }
    finally
    {
      for (i6 = 0; i6 < paramArrayOfInputStream.length; i6++)
        try
        {
          paramArrayOfInputStream[i6].close();
        }
        catch (Exception localException3)
        {
        }
      this.m_nPercentDone = 100;
    }
    U.sleep(3000L);
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
      int i = getMode(getModeArray(this.m_bytes));
      int[][] arrayOfInt = getModeArray(getPauseArray());
      drawModeGraph(TX("Pause frequencies"), arrayOfInt, 35);
      int j = getMode(arrayOfInt);
      OUT(TX("Preliminary guess at modal pause = ") + j + TX(" ms"));
      int k = 0;
      int[] arrayOfInt1 = new int[10];
      int[] arrayOfInt2 = new int[10];
      int m = -1;
      int n = 0;
      for (int i1 = 0; i1 < this.m_times.length - 1; i1++)
      {
        i2 = (int)(this.m_times[(i1 + 1)] - this.m_times[i1]);
        if (i2 > this.m_nAvgPause + 2)
          m = i1;
        if ((m < 0) || (i1 - m > 4))
        {
          i3 = 0;
          for (i4 = 0; i4 < k; i4++)
            if (arrayOfInt1[i4] == i2)
            {
              arrayOfInt2[i4] += 1;
              i3 = 1;
              n++;
              break;
            }
          if (i3 == 0)
          {
            if (k + 1 >= arrayOfInt1.length)
            {
              arrayOfInt1 = resizeArray(arrayOfInt1, arrayOfInt1.length + 32);
              arrayOfInt2 = resizeArray(arrayOfInt2, arrayOfInt2.length + 32);
            }
            arrayOfInt1[k] = i2;
            arrayOfInt2[k] += 1;
            k++;
            n++;
          }
        }
      }
      i1 = 0;
      for (int i2 = 0; (n > 0) && (i2 < k); i2++)
        i1 = Math.max(i1, arrayOfInt2[i2] * 100 / n);
      i2 = 0;
      int i3 = 0;
      for (int i4 = 0; (i1 > 10) && (i4 < k); i4++)
      {
        int i5 = arrayOfInt2[i4] * 100 / n;
        if ((i5 >= i1 / 2) || (Math.abs(arrayOfInt1[i4] - j) <= 1))
        {
          i3 += arrayOfInt1[i4] * arrayOfInt2[i4];
          i2 += arrayOfInt2[i4];
          OUT(TX("Considering ") + arrayOfInt2[i4] + TX(" pauses of ") + arrayOfInt1[i4] + TX(" ms"));
        }
      }
      double d = i2 > 0 ? i3 / i2 : -1.0D;
      OUT(TX("Modal pause = ") + i3 + TX(" / ") + i2 + TX(" = ") + (int)(d * 10.0D) / 10.0D);
      long l = d > 0.0D ? ()(i * 1000 / d) : 0L;
      this.m_lBandwidth = U.toSF(l, 2);
      OUT(TX("Bandwidth = ") + l + TX(" --> ") + this.m_lBandwidth);
    }
  }

  private int[] getPauseArray()
  {
    if ((this.m_times == null) || (this.m_times.length == 0))
      return new int[0];
    int[] arrayOfInt = new int[this.m_times.length - 1];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = ((int)(this.m_times[(i + 1)] - this.m_times[i]));
    return arrayOfInt;
  }

  private static int[][] getModeArray(int[] paramArrayOfInt)
  {
    int[] arrayOfInt1 = new int[32];
    int[] arrayOfInt2 = new int[32];
    int i = 0;
    for (int j = 0; j < paramArrayOfInt.length; j++)
    {
      int k = 0;
      for (int m = 0; m < i; m++)
        if (arrayOfInt1[m] == paramArrayOfInt[j])
        {
          arrayOfInt2[m] += 1;
          k = 1;
          break;
        }
      if (k == 0)
      {
        if (i + 1 >= arrayOfInt1.length)
        {
          arrayOfInt1 = resizeArray(arrayOfInt1, arrayOfInt1.length + 32);
          arrayOfInt2 = resizeArray(arrayOfInt2, arrayOfInt2.length + 32);
        }
        arrayOfInt1[i] = paramArrayOfInt[j];
        arrayOfInt2[i] += 1;
        i++;
      }
    }
    int[] arrayOfInt3 = new int[i];
    int[] arrayOfInt4 = new int[i];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt3, 0, i);
    System.arraycopy(arrayOfInt2, 0, arrayOfInt4, 0, i);
    return new int[][] { arrayOfInt3, arrayOfInt4 };
  }

  private static int getMode(int[][] paramArrayOfInt)
  {
    int i = 0;
    int j = 0;
    for (int k = 0; k < paramArrayOfInt[0].length; k++)
      if (paramArrayOfInt[1][k] > i)
      {
        j = paramArrayOfInt[0][k];
        i = paramArrayOfInt[1][k];
      }
    return j;
  }

  private void drawModeGraph(String paramString, int[][] paramArrayOfInt, int paramInt)
  {
    int i = paramArrayOfInt[0].length;
    int j = 0;
    for (int k = 0; k < i; k++)
      j += paramArrayOfInt[1][k];
    OUT(TX(""));
    OUT(paramString);
    for (k = 0; k < i; k++)
      OUT(align(new StringBuilder().append(paramArrayOfInt[0][k]).toString(), 5) + "ms |" + align(new StringBuilder().append(paramArrayOfInt[1][k]).toString(), 5) + TX(" | ") + buildString('#', paramInt * paramArrayOfInt[1][k] / j));
    OUT(TX(""));
  }

  private static String align(String paramString, int paramInt)
  {
    String str = "                             " + paramString;
    return str.substring(str.length() - paramInt);
  }

  private static String buildString(char paramChar, int paramInt)
  {
    for (String str = ""; str.length() < paramInt; str = str + paramChar);
    return str;
  }

  private static int[] resizeArray(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, Math.min(paramInt, paramArrayOfInt.length));
    return arrayOfInt;
  }

  public static int doReadSomethingFully(InputStream paramInputStream, byte[] paramArrayOfByte)
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

  private int getMaxTimeTimeout()
  {
    return U.max(2000, U.min(90000, iniGetInteger(TX("hispeed_testtime"), 8000)));
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

  private void logException(String paramString, Throwable paramThrowable)
  {
    this.m_plugin.logException(paramString, paramThrowable);
  }

  private void OUT(String paramString)
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

  private int iniGetInteger(String paramString, int paramInt)
  {
    return this.m_plugin.iniGetInteger(paramString, paramInt);
  }

  private long iniGetLong(String paramString, long paramLong)
  {
    return this.m_plugin.iniGetLong(paramString, paramLong);
  }

  private String getTargetAddress()
  {
    return this.m_plugin.base().getHost();
  }

  private int getTargetHTTPPort()
  {
    int i = this.m_plugin.base().getPort();
    return i <= 0 ? 80 : i;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.hispeed.HiSpeedTest
 * JD-Core Version:    0.6.2
 */