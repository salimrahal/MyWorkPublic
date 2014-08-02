package mcsaplugins.voip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class VoIPTest extends AppletTest
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
  private static final short TYPE_NEWSESSION = 1;
  private static final short TYPE_NEWSESSIONOK = 2;
  private static final short TYPE_CONTSESSION = 3;
  private static final short TYPE_GETRESULTS = 4;
  private static final short TYPE_RESULTSPAGE = 5;
  private static final int BYTESPERPACKET = 8;
  private static final int NUMSECONDS = 10;
  private static final int PACKETSPERSECOND = 75;
  private DatagramSocket m_sock;
  private DatagramSocket m_downloadSock;
  private long m_lSessionID;
  private int m_packetSize = 50;
  private int[] m_uploadSentTimes;
  private int[] m_uploadRecdTimes;
  private int[] m_downloadRecdTimes;
  private int[] m_downloadSentTimes;
  private long m_lStartDownload = 0L;
  private long m_lStart;
  private InetAddress m_addr;
  private int m_port;
  private int m_dport;
  private int m_sipPort;
  private SipTester m_sipTester;
  private int m_nState;
  private int m_nRegister;
  private int m_nInvite;
  private int m_nBye;
  private double m_uorder;
  private double m_dorder;
  private double m_uloss;
  private double m_dloss;
  private double m_djitter;
  private double m_ujitter;
  private double m_udiscards;
  private double m_ddiscards;
  private double m_percentComplete;
  private ErrorCode m_error;
  private int m_nBytesPerPacket = 8;
  private int m_nSeconds = 10;
  private int m_nPacketsPerSecond = 75;
  private int m_nDiscardMs;
  private int m_nOmitPackets;
  private int m_dscp;
  private String m_sipTests;
  private VoIPTab m_panel;
  private voip m_plugin;
  private Thread m_tReceiveThread;
  private boolean m_bRun;
  private boolean m_bRunReceive;

  public VoIPTest(VoIPTab paramVoIPTab, voip paramvoip, String paramString1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, String paramString2, int paramInt10, int paramInt11, int paramInt12, int paramInt13)
  {
    super(paramvoip, paramVoIPTab);
    this.m_panel = paramVoIPTab;
    this.m_plugin = paramvoip;
    this.m_nBytesPerPacket = paramInt4;
    this.m_nSeconds = paramInt5;
    this.m_nPacketsPerSecond = paramInt6;
    this.m_nDiscardMs = paramInt7;
    this.m_nOmitPackets = paramInt8;
    this.m_dscp = paramInt9;
    this.m_sipTests = paramString2;
    this.m_uploadSentTimes = (this.m_nOmitPackets > this.m_nPacketsPerSecond * this.m_nSeconds ? new int[0] : new int[this.m_nSeconds * this.m_nPacketsPerSecond - this.m_nOmitPackets]);
    try
    {
      this.m_addr = InetAddress.getByName(paramString1);
    }
    catch (Exception localException)
    {
    }
    this.m_port = paramInt1;
    this.m_dport = paramInt2;
    this.m_sipPort = paramInt3;
    if (this.m_addr == null)
      setError("The voice-over-IP test server is currently unavailable, or your firewall is preventing access to it. Please try later.", 1);
  }

  public void runTest()
  {
    if (this.m_addr != null)
    {
      this.m_nRegister = -1;
      this.m_nInvite = -1;
      this.m_nBye = -1;
      this.m_uorder = 0.0D;
      this.m_uloss = -1.0D;
      this.m_djitter = -1.0D;
      this.m_ujitter = -1.0D;
      this.m_percentComplete = 0.0D;
      this.m_error = null;
      this.m_sipTester = null;
      this.m_bRun = true;
      if ((this.m_sipTests != null) && (this.m_sipTests.length() > 0))
      {
        try
        {
          this.m_sipTester = new SipTester(this.m_addr.getHostAddress(), this.m_sipPort, "test", SipTester.generateSipId(), "password");
        }
        catch (Exception localException1)
        {
          detailPrint("ERROR performing SIP simulation: " + localException1);
        }
        detailPrint("SIP Times:");
        if (this.m_sipTests.toUpperCase().indexOf('R') >= 0)
        {
          this.m_nRegister = measureSipRequest("REGISTER");
          detailPrint("REGISTER: " + this.m_nRegister + " ms");
        }
        if (this.m_sipTests.toUpperCase().indexOf('I') >= 0)
        {
          this.m_nInvite = measureSipRequest("INVITE");
          detailPrint("INVITE:   " + this.m_nInvite + " ms");
        }
        if (this.m_sipTests.toUpperCase().indexOf('B') >= 0)
        {
          this.m_nBye = measureSipRequest("BYE");
          detailPrint("BYE:    " + this.m_nBye + " ms");
        }
        try
        {
          this.m_sipTester.close();
        }
        catch (Exception localException2)
        {
          detailPrint("ERROR performing SIP close: " + localException2);
        }
      }
      doSendThread();
      this.m_percentComplete = 100.0D;
    }
  }

  public int getState()
  {
    return this.m_nState;
  }

  public double getPercentComplete()
  {
    return this.m_percentComplete;
  }

  public int getRegisterTime()
  {
    return this.m_nRegister;
  }

  public int getInviteTime()
  {
    return this.m_nInvite;
  }

  public int getByeTime()
  {
    return this.m_nBye;
  }

  public double getUploadOrder()
  {
    return this.m_uorder;
  }

  public double getDownloadOrder()
  {
    return this.m_dorder;
  }

  public double getUploadLoss()
  {
    return this.m_uloss;
  }

  public double getDownloadLoss()
  {
    return this.m_dloss;
  }

  public double getUploadDiscards()
  {
    return this.m_udiscards;
  }

  public double getDownloadDiscards()
  {
    return this.m_ddiscards;
  }

  public double getUploadJitter()
  {
    return this.m_ujitter;
  }

  public double getDownloadJitter()
  {
    return this.m_djitter;
  }

  public ErrorCode getError()
  {
    return this.m_error;
  }

  public int getBytesPerSecond()
  {
    int i = 41;
    return (this.m_nBytesPerPacket + i) * this.m_nPacketsPerSecond;
  }

  public int[] getUploadSentArray()
  {
    return this.m_uploadSentTimes;
  }

  public int[] getUploadRecdArray()
  {
    return this.m_uploadRecdTimes;
  }

  public int[] getDownloadSentArray()
  {
    return this.m_downloadSentTimes;
  }

  public int[] getDownloadRecdArray()
  {
    return this.m_downloadRecdTimes;
  }

  public String getSIPTests()
  {
    return this.m_sipTests;
  }

  public float[] getUploadJitterArray()
  {
    return getJitterArray(this.m_ujitter, this.m_uploadSentTimes, this.m_uploadRecdTimes);
  }

  public float[] getDownloadJitterArray()
  {
    return getJitterArray(this.m_djitter, this.m_downloadSentTimes, this.m_downloadRecdTimes);
  }

  private static float[] getJitterArray(double paramDouble, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null))
      return null;
    float[] arrayOfFloat = new float[paramArrayOfInt1.length];
    double d1 = 0.0D;
    int i = 0;
    for (int j = 0; j < arrayOfFloat.length; j++)
      if (paramArrayOfInt2.length > j)
      {
        arrayOfFloat[j] = ((float)(paramDouble + paramArrayOfInt2[j] - paramArrayOfInt1[j]));
        if (paramArrayOfInt2[j] >= 0)
        {
          d1 += arrayOfFloat[j];
          i++;
        }
      }
    double d2 = d1 / Math.max(1, i);
    for (int k = 0; tmp108_104 < arrayOfFloat.length; tmp108_104++)
    {
      int tmp108_106 = k;
      float[] tmp108_104 = arrayOfFloat;
      tmp108_104[tmp108_106] = ((float)(tmp108_104[tmp108_106] - d2));
    }
    return arrayOfFloat;
  }

  public boolean[] getUploadLossArray()
  {
    return getLossArray(this.m_uploadSentTimes, this.m_uploadRecdTimes);
  }

  public boolean[] getDownloadLossArray()
  {
    return getLossArray(this.m_downloadSentTimes, this.m_downloadRecdTimes);
  }

  private static boolean[] getLossArray(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null))
      return null;
    boolean[] arrayOfBoolean = new boolean[paramArrayOfInt1.length];
    for (int i = 0; i < arrayOfBoolean.length; i++)
      arrayOfBoolean[i] = ((paramArrayOfInt2.length > i) && (paramArrayOfInt2[i] == -1) ? 1 : false);
    return arrayOfBoolean;
  }

  public float getMOS()
  {
    int i = this.m_plugin.iniGetInteger("voip.uploadport", -1) != -1 ? 1 : 0;
    float f = Util.getMOSScore(getUploadJitter(), getUploadLoss());
    double d1 = getDownloadJitter();
    double d2 = getDownloadLoss();
    if ((i != 0) && (d1 >= 0.0D) && (d2 >= 0.0D))
      f = Math.min(f, Util.getMOSScore(d1, d2));
    return f;
  }

  public String[] getAdvancedDataItem(String paramString)
  {
    String str1 = null;
    String str2 = null;
    int i = this.m_plugin.iniGetInteger("voip.uploadport", -1) != -1 ? 1 : 0;
    if (TX("ujitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("voip.adv_") + paramString);
      str2 = (this.m_ujitter >= 0.0D ? U.d2s(this.m_ujitter) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("djitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("voip.adv_") + paramString);
      str2 = i != 0 ? (this.m_djitter >= 0.0D ? U.d2s(this.m_djitter) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms")) : TX("off");
    }
    else if (TX("uloss").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("voip.adv_") + paramString);
      str2 = (this.m_uloss >= 0.0D ? U.d2s(this.m_uloss) : TX("--")) + " %";
    }
    else if (TX("dloss").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("voip.adv_") + paramString);
      str2 = i != 0 ? (this.m_dloss >= 0.0D ? U.d2s(this.m_dloss) : TX("--")) + " %" : TX("off");
    }
    else if (TX("udiscards").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("voip.adv_") + paramString);
      str2 = (this.m_udiscards >= 0.0D ? U.d2s(this.m_udiscards) : TX("--")) + " %";
    }
    else if (TX("ddiscards").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("voip.adv_") + paramString);
      str2 = (this.m_ddiscards >= 0.0D ? U.d2s(this.m_ddiscards) : TX("--")) + " %";
    }
    else if (TX("order").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("voip.adv_") + paramString);
      str2 = (this.m_uorder >= 0.0D ? U.d2s(100.0D - this.m_uorder) : TX("--")) + " %";
    }
    else if (TX("mos").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("voip.adv_") + paramString);
      float f = getMOS();
      str2 = f != 0.0F ? TX("") + f : TX("--");
    }
    return new String[] { str1, (str1 == null) || (str2 == null) ? null : str2 };
  }

  public Object[] getSummaryItem(String paramString)
  {
    String str1;
    int i;
    if (TX("jitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("jitternone"));
      i = -1;
      if (this.m_ujitter > this.m_plugin.iniGetInteger(TX("jitterbadnum"), 30))
      {
        str1 = this.m_plugin.RC(TX("jitterbad"), Util.oneDP(this.m_ujitter));
        i = 2;
      }
      else if (this.m_ujitter > this.m_plugin.iniGetInteger(TX("jitterwarnnum"), 8))
      {
        str1 = this.m_plugin.RC(TX("jitterwarn"), Util.oneDP(this.m_ujitter));
        i = 1;
      }
      else if (this.m_ujitter >= 0.0D)
      {
        str1 = this.m_plugin.RC(TX("jittergood"), Util.oneDP(this.m_ujitter));
        i = 0;
      }
      return new Object[] { new Integer(i), str1 };
    }
    if (TX("loss").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("lossnone"));
      i = -1;
      if (this.m_uloss > this.m_plugin.iniGetInteger(TX("lossbadnum"), 5))
      {
        str1 = this.m_plugin.RC(TX("lossbad"), Util.oneDP(this.m_uloss));
        i = 2;
      }
      else if (this.m_uloss > this.m_plugin.iniGetInteger(TX("losswarnnum"), 1))
      {
        str1 = this.m_plugin.RC(TX("losswarn"), Util.oneDP(this.m_uloss));
        i = 1;
      }
      else if (this.m_uloss >= 0.0D)
      {
        str1 = this.m_plugin.RC(TX("lossgood"), Util.oneDP(this.m_uloss));
        i = 0;
      }
      return new Object[] { new Integer(i), str1 };
    }
    if (TX("mos").equals(paramString))
    {
      float f = getMOS();
      String str2 = this.m_plugin.RC(TX("mosnone"));
      if (f >= 1.0F)
        str2 = this.m_plugin.RC(TX("mosscore"), f);
      return new Object[] { new Integer(-2), str2 };
    }
    return null;
  }

  private int measureSipRequest(String paramString)
  {
    if (this.m_sipTester != null)
      try
      {
        long l = U.time();
        this.m_sipTester.sendHeader(paramString, "sip:" + this.m_addr.getHostAddress() + ":" + this.m_sipPort, 0);
        this.m_sipTester.readHeaderBody();
        if ("INVITE".equals(paramString))
        {
          this.m_sipTester.readHeaderBody();
          this.m_sipTester.readHeaderBody();
        }
        return (int)(U.time() - l);
      }
      catch (Exception localException)
      {
        detailPrint("ERROR: SIP Tester could not send " + paramString + " request: " + localException);
        localException.printStackTrace();
        return -1;
      }
    return -1;
  }

  private boolean isDoDownloadTest()
  {
    return this.m_dport != -1;
  }

  private void setError(String paramString, int paramInt)
  {
    this.m_percentComplete = 100.0D;
    this.m_error = new ErrorCode(0x10000 | paramInt & 0xFFFF, paramString);
    detailPrint(paramString);
    updateResultsPanel();
  }

  private boolean createSession()
  {
    this.m_sock = new DatagramSocket();
    this.m_sock.setSoTimeout(5000);
    this.m_lSessionID = (()(System.currentTimeMillis() * Math.random()));
    int i = 0;
    while (i < 3)
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(this.m_packetSize);
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(1);
      localDataOutputStream.writeLong(this.m_lSessionID);
      localDataOutputStream.writeLong(this.m_nPacketsPerSecond * this.m_nSeconds);
      localDataOutputStream.writeInt(this.m_nSeconds);
      localDataOutputStream.writeInt(this.m_nBytesPerPacket);
      localDataOutputStream.writeInt(this.m_dscp);
      sendPacket(localByteArrayOutputStream);
      byte[] arrayOfByte = receivePacket(this.m_sock);
      if (arrayOfByte == null)
      {
        i++;
      }
      else
      {
        DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(arrayOfByte));
        boolean bool = (localDataInputStream.readShort() == 2) && (localDataInputStream.readLong() == this.m_lSessionID);
        localDataInputStream.close();
        return bool;
      }
    }
    return false;
  }

  private void sendPacket(long paramLong1, long paramLong2)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(this.m_packetSize);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    int i = 0;
    localDataOutputStream.writeShort(3);
    i += 2;
    localDataOutputStream.writeLong(this.m_lSessionID);
    i += 8;
    localDataOutputStream.writeLong(paramLong1);
    i += 8;
    while (i < this.m_nBytesPerPacket)
    {
      localDataOutputStream.writeByte((int)(Math.random() * 127.0D));
      i++;
    }
    while (System.currentTimeMillis() + 10L < paramLong2)
      if (paramLong2 - System.currentTimeMillis() > 100L)
        try
        {
          Thread.sleep(50L);
        }
        catch (Exception localException)
        {
        }
    if ((int)paramLong1 >= this.m_nOmitPackets)
      this.m_uploadSentTimes[((int)paramLong1 - this.m_nOmitPackets)] = ((int)(System.currentTimeMillis() - this.m_lStart));
    sendPacket(localByteArrayOutputStream);
  }

  private void sendPacket(ByteArrayOutputStream paramByteArrayOutputStream)
  {
    byte[] arrayOfByte = paramByteArrayOutputStream.toByteArray();
    DatagramPacket localDatagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, this.m_addr, this.m_port);
    if (this.m_dscp > 0)
      try
      {
        this.m_sock.setTrafficClass(this.m_dscp << 2 | this.m_sock.getTrafficClass() & 0x3);
      }
      catch (Exception localException)
      {
        detailPrint("Error setting traffic class: " + localException);
      }
    this.m_sock.send(localDatagramPacket);
    paramByteArrayOutputStream.close();
  }

  private byte[] getReport()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    byte[] arrayOfByte;
    while (((arrayOfByte = getReportPage(i++)) != null) && (arrayOfByte.length != 0))
      localByteArrayOutputStream.write(arrayOfByte);
    return localByteArrayOutputStream.toByteArray();
  }

  private byte[] getReportPage(int paramInt)
  {
    int i = 0;
    while (i < 3)
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(this.m_packetSize);
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(4);
      localDataOutputStream.writeLong(this.m_lSessionID);
      localDataOutputStream.writeInt(paramInt);
      sendPacket(localByteArrayOutputStream);
      byte[] arrayOfByte1 = receivePacket(this.m_sock);
      if (arrayOfByte1 == null)
      {
        i++;
      }
      else
      {
        DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(arrayOfByte1));
        if ((localDataInputStream.readShort() == 5) && (localDataInputStream.readLong() == this.m_lSessionID) && (localDataInputStream.readInt() == paramInt))
        {
          byte[] arrayOfByte2 = new byte[arrayOfByte1.length - 14];
          System.arraycopy(arrayOfByte1, 14, arrayOfByte2, 0, arrayOfByte2.length);
          localDataInputStream.close();
          return arrayOfByte2;
        }
      }
    }
    if (i == 3)
      setError("Cannot obtain test results from server - the server could be down. Please try later.", 2);
    return null;
  }

  private byte[] receivePacket(DatagramSocket paramDatagramSocket)
  {
    DatagramPacket localDatagramPacket = new DatagramPacket(new byte[8000], 8000);
    try
    {
      paramDatagramSocket.receive(localDatagramPacket);
    }
    catch (InterruptedIOException localInterruptedIOException)
    {
      return null;
    }
    byte[] arrayOfByte = new byte[localDatagramPacket.getLength()];
    System.arraycopy(localDatagramPacket.getData(), 0, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  private void parseReport(byte[] paramArrayOfByte)
  {
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    int j = 0;
    int[] arrayOfInt1 = this.m_nOmitPackets > this.m_nPacketsPerSecond * this.m_nSeconds ? new int[0] : new int[this.m_nPacketsPerSecond * this.m_nSeconds - this.m_nOmitPackets];
    try
    {
      int i;
      while ((i = localDataInputStream.readInt()) != -2)
      {
        if (j >= this.m_nOmitPackets)
        {
          if (j - this.m_nOmitPackets >= arrayOfInt1.length)
            break;
          arrayOfInt1[(j - this.m_nOmitPackets)] = i;
          detailPrint("  " + j + " " + i + " " + this.m_uploadSentTimes[(j - this.m_nOmitPackets)]);
        }
        j++;
      }
    }
    catch (EOFException localEOFException)
    {
    }
    this.m_uploadRecdTimes = arrayOfInt1;
    int[] arrayOfInt2 = removeNoResponse(arrayOfInt1);
    int k = getOrderMetric(arrayOfInt2, arrayOfInt2.length);
    long l = j * j - j;
    this.m_uorder = (this.m_uploadRecdTimes != null ? Math.min(100.0D, 1000L * (l - k) / (10.0D * Math.max(1L, l))) : j == 0 ? 0.0D : -1.0D);
    this.m_uloss = (10000 * (arrayOfInt1.length - arrayOfInt2.length) / arrayOfInt1.length / 100.0D);
    double[] arrayOfDouble = getJitter(arrayOfInt1, this.m_uploadSentTimes);
    double d = max(arrayOfDouble);
    this.m_ujitter = avg(arrayOfDouble);
    this.m_udiscards = getDiscards(arrayOfDouble, this.m_ujitter);
    detailPrint("Upstream Loss: " + this.m_uloss + "% (=" + (arrayOfInt1.length - arrayOfInt2.length) + "/" + arrayOfInt1.length + ")");
    detailPrint("Upstream Order: " + this.m_uorder + "% (=" + (l - k) + "/" + Math.max(1L, l) + ")");
    detailPrint("Upstream Jitter: ave=" + twoDP(this.m_ujitter) + "ms, max=" + d + "ms");
  }

  private double twoDP(double paramDouble)
  {
    return (int)(100.0D * paramDouble) / 100.0D;
  }

  private int[] removeNoResponse(int[] paramArrayOfInt)
  {
    int[] arrayOfInt1 = new int[paramArrayOfInt.length];
    int i = 0;
    for (int j = 0; j < paramArrayOfInt.length; j++)
      if (paramArrayOfInt[j] != -1)
        arrayOfInt1[(i++)] = paramArrayOfInt[j];
    int[] arrayOfInt2 = new int[i];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, i);
    return arrayOfInt2;
  }

  private static double[] getJitter(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i = 0;
    int j = 0;
    int k = paramArrayOfInt2 != null ? 1 : 0;
    for (int m = 0; m < paramArrayOfInt1.length; m++)
      if (paramArrayOfInt1[m] != -1)
      {
        i += Math.abs(paramArrayOfInt1[m] - (k != 0 ? paramArrayOfInt2[m] : 0));
        j++;
      }
    m = 0;
    double[] arrayOfDouble = new double[j];
    double d = j == 0 ? 0.0D : i / j;
    for (int n = 0; n < paramArrayOfInt1.length; n++)
      if (paramArrayOfInt1[n] != -1)
        arrayOfDouble[(m++)] = Math.abs(Math.abs(paramArrayOfInt1[n] - (k != 0 ? paramArrayOfInt2[n] : 0)) - d);
    return arrayOfDouble;
  }

  private double getDiscards(double[] paramArrayOfDouble, double paramDouble)
  {
    if ((paramArrayOfDouble == null) || (paramDouble == -1.0D))
      return -1.0D;
    int i = 0;
    int j = 0;
    for (int k = 0; k < paramArrayOfDouble.length; k++)
      if (paramArrayOfDouble[k] != -1.0D)
      {
        j++;
        if (Math.abs(paramArrayOfDouble[k] - paramDouble) > this.m_nDiscardMs)
          i++;
      }
    return j != 0 ? i * 100.0D / j : 0.0D;
  }

  private double max(double[] paramArrayOfDouble)
  {
    double d = -2147483648.0D;
    for (int i = 0; i < paramArrayOfDouble.length; i++)
      if (d < paramArrayOfDouble[i])
        d = paramArrayOfDouble[i];
    return d;
  }

  private double avg(double[] paramArrayOfDouble)
  {
    long l = 0L;
    for (int i = 0; i < paramArrayOfDouble.length; i++)
      l = ()(l + paramArrayOfDouble[i]);
    return paramArrayOfDouble.length == 0 ? 0.0D : 10000L * l / paramArrayOfDouble.length / 10000.0D;
  }

  private int getOrderMetric(int[] paramArrayOfInt, int paramInt)
  {
    if (paramArrayOfInt != null)
    {
      int[] arrayOfInt = new int[paramArrayOfInt.length];
      System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
      int i = 0;
      for (int j = 0; j < paramInt - 1; j++)
        for (int k = 0; k < paramInt - 1 - j; k++)
          if (arrayOfInt[k] > arrayOfInt[(k + 1)])
          {
            i++;
            int m = arrayOfInt[k];
            arrayOfInt[k] = arrayOfInt[(k + 1)];
            arrayOfInt[(k + 1)] = m;
          }
      return i;
    }
    return 0;
  }

  public void OUT(String paramString)
  {
    this.m_plugin.OUT(paramString);
  }

  public void run()
  {
    doReceiveThread();
  }

  private void doSendThread()
  {
    if ((this.m_addr != null) && (this.m_port >= 0))
      try
      {
        updateResultsPanel();
        detailPrint("Beginning test, ip=" + this.m_addr.getHostAddress() + ", port=UDP-" + this.m_port + "...");
        boolean bool = createSession();
        if ((bool) && (this.m_bRun))
        {
          if (isDoDownloadTest())
            startReceiveThread();
          detailPrint("Found server, sending data...");
          long l1 = 250L;
          this.m_lStart = System.currentTimeMillis();
          for (long l2 = 0L; (l2 < this.m_nSeconds * this.m_nPacketsPerSecond) && (this.m_bRun); l2 += 1L)
          {
            sendPacket(l2, this.m_lStart + l2 * 1000L / this.m_nPacketsPerSecond);
            this.m_percentComplete = (5.0D + l2 * 10000L / (this.m_nSeconds * this.m_nPacketsPerSecond) / 100.0D * 0.9D);
            long l4 = System.currentTimeMillis();
            if (l4 > l1)
            {
              l1 = l4 + 250L;
              updateResultsPanel();
            }
          }
          try
          {
            Thread.sleep(250L);
          }
          catch (Exception localException2)
          {
          }
          int i = 1;
          long l3 = System.currentTimeMillis();
          while ((isDoDownloadTest()) && (System.currentTimeMillis() - l3 < 3000L) && (i != 0))
          {
            i = 0;
            for (j = 0; j < this.m_downloadRecdTimes.length; j++)
              if (this.m_downloadRecdTimes[j] == -1)
                i = 1;
          }
          stopReceiveThread();
          int j = this.m_downloadRecdTimes == null ? 0 : this.m_downloadRecdTimes.length;
          int k = this.m_downloadRecdTimes != null ? getOrderMetric(this.m_downloadRecdTimes, j) : -1;
          long l5 = j * j - j;
          this.m_dorder = (this.m_downloadRecdTimes != null ? Math.min(100.0D, 1000L * (l5 - k) / (10.0D * Math.max(1L, l5))) : j == 0 ? 0.0D : -1.0D);
          double[] arrayOfDouble = this.m_downloadRecdTimes != null ? getJitter(this.m_downloadRecdTimes, this.m_downloadSentTimes) : null;
          this.m_djitter = (arrayOfDouble != null ? avg(arrayOfDouble) : -1.0D);
          this.m_ddiscards = (arrayOfDouble != null ? getDiscards(arrayOfDouble, this.m_djitter) : -1.0D);
          int m = 0;
          if (this.m_downloadRecdTimes != null)
            for (int n = 0; n < this.m_downloadRecdTimes.length; n++)
              if (this.m_downloadRecdTimes[n] == -1)
                m++;
          this.m_dloss = (this.m_downloadRecdTimes != null ? 10000 * m / this.m_downloadRecdTimes.length / 100.0D : -1.0D);
          if (this.m_bRun)
          {
            try
            {
              Thread.sleep(500L);
            }
            catch (Exception localException3)
            {
            }
            detailPrint("Finished test, obtaining results...");
            byte[] arrayOfByte = getReport();
            parseReport(arrayOfByte);
            this.m_percentComplete = 95.0D;
            updateResultsPanel();
            if (arrayOfDouble != null)
            {
              detailPrint("Downstream Loss: " + this.m_dloss + "% (=" + m + "/" + (this.m_downloadRecdTimes == null ? 0 : this.m_downloadRecdTimes.length) + ")");
              detailPrint("Downstream Order: " + this.m_dorder + "% (=" + (l5 - k) + "/" + Math.max(1L, l5) + ")");
              detailPrint("Downstream Jitter: ave=" + twoDP(this.m_djitter) + "ms, max=" + max(arrayOfDouble) + "ms");
            }
            float[] arrayOfFloat1 = getUploadJitterArray();
            boolean[] arrayOfBoolean1 = getUploadLossArray();
            float[] arrayOfFloat2 = getDownloadJitterArray();
            boolean[] arrayOfBoolean2 = getDownloadLossArray();
            if ((arrayOfFloat1 != null) && (arrayOfBoolean1 != null))
              this.m_plugin.addGraphResults(this.m_plugin.RC("voip.graphupstream"), null, null, arrayOfFloat1, arrayOfBoolean1);
            if ((arrayOfFloat2 != null) && (arrayOfBoolean2 != null))
              this.m_plugin.addGraphResults(this.m_plugin.RC("voip.graphdownstream"), null, null, arrayOfFloat2, arrayOfBoolean2);
          }
        }
        else if (!bool)
        {
          setError("Did not receive response from server", 3);
          updateResultsPanel();
          detailPrint("Server is not responding - is your Internet connection up?");
        }
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
  }

  private void doReceiveThread()
  {
    this.m_downloadRecdTimes = (this.m_nOmitPackets > this.m_nPacketsPerSecond * this.m_nSeconds ? new int[0] : new int[this.m_nPacketsPerSecond * this.m_nSeconds - this.m_nOmitPackets]);
    this.m_downloadSentTimes = (this.m_nOmitPackets > this.m_nPacketsPerSecond * this.m_nSeconds ? new int[0] : new int[this.m_nPacketsPerSecond * this.m_nSeconds - this.m_nOmitPackets]);
    for (int i = 0; i < this.m_downloadRecdTimes.length; i++)
    {
      this.m_downloadRecdTimes[i] = -1;
      this.m_downloadSentTimes[i] = -1;
    }
    long l = System.currentTimeMillis();
    try
    {
      this.m_downloadSock = new DatagramSocket();
      this.m_downloadSock.setSoTimeout(5000);
      if (this.m_dscp > 0)
        try
        {
          this.m_downloadSock.setTrafficClass(this.m_dscp << 2 | this.m_downloadSock.getTrafficClass() & 0x3);
        }
        catch (Exception localException1)
        {
          detailPrint("Error setting traffic class: " + localException1);
        }
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(4097);
      localDataOutputStream.writeLong(this.m_lSessionID);
      localDataOutputStream.writeInt(this.m_nBytesPerPacket);
      localDataOutputStream.writeInt(this.m_nSeconds);
      localDataOutputStream.writeInt(this.m_nPacketsPerSecond);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      DatagramPacket localDatagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, this.m_addr, this.m_dport);
      localDataOutputStream.close();
      int j = 0;
      int k = 0;
      while (true)
      {
        this.m_downloadSock.send(localDatagramPacket);
        try
        {
          DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(receivePacket(this.m_downloadSock)));
          if ((localDataInputStream.readShort() == 4098) && (localDataInputStream.readLong() == this.m_lSessionID))
            k = 1;
          localDataInputStream.close();
        }
        catch (Exception localException3)
        {
          localException3.printStackTrace();
        }
        j++;
        if ((this.m_bRunReceive) && (j < 3))
          if (k != 0)
            break;
      }
      do
      {
        arrayOfByte = receivePacket(this.m_downloadSock);
        receiveDownloadPacket(arrayOfByte, (int)(System.currentTimeMillis() - l));
        if (k == 0)
          break;
      }
      while (this.m_bRunReceive);
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
    }
  }

  private boolean receiveDownloadPacket(byte[] paramArrayOfByte, int paramInt)
  {
    try
    {
      DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
      int i = localDataInputStream.readShort();
      if (i == 3)
      {
        long l1 = localDataInputStream.readLong();
        if (l1 == this.m_lSessionID)
        {
          long l2 = localDataInputStream.readLong();
          int j = localDataInputStream.readInt();
          if (this.m_lStartDownload == 0L)
            this.m_lStartDownload = l2;
          if ((j >= this.m_nOmitPackets) && (j - this.m_nOmitPackets < this.m_downloadRecdTimes.length))
          {
            this.m_downloadRecdTimes[(j - this.m_nOmitPackets)] = paramInt;
            this.m_downloadSentTimes[(j - this.m_nOmitPackets)] = ((int)(l2 - this.m_lStartDownload));
          }
          return true;
        }
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private void startReceiveThread()
  {
    stopReceiveThread();
    this.m_tReceiveThread = new Thread(this);
    this.m_bRunReceive = true;
    this.m_tReceiveThread.start();
  }

  private void stopReceiveThread()
  {
    this.m_bRunReceive = false;
    long l = System.currentTimeMillis();
    while ((this.m_tReceiveThread != null) && (System.currentTimeMillis() < l + 1000L))
      try
      {
        Thread.sleep(200L);
      }
      catch (Exception localException1)
      {
      }
    if (this.m_tReceiveThread != null)
      try
      {
        this.m_tReceiveThread.stop();
      }
      catch (Exception localException2)
      {
      }
  }

  private void updateResultsPanel()
  {
    if (this.m_panel != null)
      this.m_panel.repaint();
  }

  public void doStop()
  {
    this.m_bRun = false;
    this.m_bRunReceive = false;
    long l = System.currentTimeMillis();
    while ((System.currentTimeMillis() < l + 1000L) && (this.m_tReceiveThread != null))
      try
      {
        Thread.sleep(100L);
      }
      catch (Exception localException)
      {
      }
  }

  private void detailPrint(String paramString)
  {
    if (this.m_plugin != null)
      this.m_plugin.OUT(paramString);
    else
      System.out.println(paramString);
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.voip.VoIPTest
 * JD-Core Version:    0.6.2
 */