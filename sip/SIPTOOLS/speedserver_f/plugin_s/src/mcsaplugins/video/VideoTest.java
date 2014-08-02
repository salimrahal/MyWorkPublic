package mcsaplugins.video;

import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class VideoTest extends AppletTest
{
  public static final int S_PRE = 1;
  public static final int S_RUNNING = 2;
  public static final int S_COMPLETE = 3;
  private VideoTab m_tab;
  private video m_video;
  private String m_ip;
  private int m_port;
  private int m_nVideoBpp;
  private int m_nVideoPps;
  private int m_nVideoCdt;
  private int m_nVideoSdt;
  private int m_nAudioBpp;
  private int m_nAudioPps;
  private int m_nAudioCdt;
  private int m_nAudioSdt;
  private int m_nOmitMs;
  private int m_nMs;
  private long m_vBaseLag;
  private long m_aBaseLag;
  private int m_nVideoLastGood = 0;
  private int m_nAudioLastGood = 0;
  private long m_lStart;
  private Hashtable m_actMetrics;
  private boolean m_bnetqcheck = false;
  private int m_nPercentDone;
  private int m_nCurrentMs;
  private int m_nSetupTime;
  private int m_nDescribeTime;
  private int m_nPlayTime;
  private int m_nRTT;
  private int m_nMaxDelay;
  private double m_acjitter;
  private double m_acavg;
  private double m_asjitter;
  private double m_asavg;
  private int m_aloss;
  private double m_adiscards;
  private double m_vcjitter;
  private double m_vcavg;
  private double m_vsjitter;
  private double m_vsavg;
  private int m_vloss;
  private double m_vdiscards;
  private int[] m_aDeltaArray;
  private int[] m_aBytesArray;
  private int[] m_aTimesArray;
  private int[] m_aServerDeltaArray;
  private long[] m_aServerSendArray;
  private long[] m_aNowArray;
  private long[] m_aUpperArray;
  private int[] m_aDiscardArray;
  private int[] m_aBytesBad;
  private int[] m_aBytesGood;
  private int[] m_aBytesDiscard;
  private int[] m_aBytesLost;
  private boolean[] m_aDecide;
  private int[] m_vDeltaArray;
  private int[] m_vBytesArray;
  private int[] m_vTimesArray;
  private int[] m_vServerDeltaArray;
  private long[] m_vServerSendArray;
  private long[] m_vNowArray;
  private long[] m_vUpperArray;
  private int[] m_vDiscardArray;
  private int[] m_vBytesBad;
  private int[] m_vBytesGood;
  private int[] m_vBytesDiscard;
  private int[] m_vBytesLost;
  private boolean[] m_vDecide;
  private int m_nState = 1;
  private Socket m_socket;
  private PushbackInputStream m_in;
  private OutputStream m_out;
  private boolean m_bRun;
  private boolean m_bShowRequestTimes;
  private ErrorCode m_error;
  private int m_nCSeq = 0;
  private int m_nStreamId = 0;
  private int m_nLength = 0;
  private int m_nPacketId = 0;
  private int m_nMagic = 0;
  private int m_nPacketTime = 0;
  private int m_nServerDelta = 0;

  public VideoTest(VideoTab paramVideoTab, video paramvideo, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, boolean paramBoolean)
  {
    super(paramvideo, paramVideoTab);
    this.m_tab = paramVideoTab;
    this.m_video = paramvideo;
    this.m_ip = paramString;
    this.m_port = paramInt1;
    this.m_nVideoBpp = paramInt2;
    this.m_nVideoPps = paramInt3;
    this.m_nVideoCdt = paramInt4;
    this.m_nVideoSdt = paramInt5;
    this.m_nAudioBpp = paramInt6;
    this.m_nAudioPps = paramInt7;
    this.m_nAudioCdt = paramInt8;
    this.m_nAudioSdt = paramInt9;
    this.m_nMs = paramInt10;
    this.m_nOmitMs = paramInt11;
    this.m_bShowRequestTimes = paramBoolean;
  }

  public boolean showRequestTimes()
  {
    return this.m_bShowRequestTimes;
  }

  public int getAudioBytesPerPacket()
  {
    return this.m_nAudioBpp;
  }

  public int getAudioPacketsPerSecond()
  {
    return this.m_nAudioPps;
  }

  public int getVideoBytesPerPacket()
  {
    return this.m_nVideoBpp;
  }

  public int getVideoPacketsPerSecond()
  {
    return this.m_nVideoPps;
  }

  public int getDuration()
  {
    return this.m_nMs;
  }

  public int getSetupTime()
  {
    return this.m_nSetupTime;
  }

  public int getDescribeTime()
  {
    return this.m_nDescribeTime;
  }

  public int getPlayTime()
  {
    return this.m_nPlayTime;
  }

  public int getRTT()
  {
    return this.m_nRTT;
  }

  public int getMaxDelay()
  {
    return this.m_nMaxDelay;
  }

  public double getClientAudioJitter()
  {
    return this.m_acjitter;
  }

  public double getServerAudioJitter()
  {
    return this.m_asjitter;
  }

  public int getAudioPacketLoss()
  {
    return this.m_aloss;
  }

  public double getAudioDiscards()
  {
    return this.m_adiscards;
  }

  public double getClientVideoJitter()
  {
    return this.m_vcjitter;
  }

  public double getServerVideoJitter()
  {
    return this.m_vsjitter;
  }

  public int getVideoPacketLoss()
  {
    return this.m_vloss;
  }

  public int[] getVideoBadArray()
  {
    return this.m_vBytesBad;
  }

  public int[] getAudioBadArray()
  {
    return this.m_aBytesBad;
  }

  public int[] getVideoGoodArray()
  {
    return this.m_vBytesGood;
  }

  public int[] getAudioGoodArray()
  {
    return this.m_aBytesGood;
  }

  public int[] getVideoDiscardArray()
  {
    return this.m_vBytesDiscard;
  }

  public int[] getAudioDiscardArray()
  {
    return this.m_aBytesDiscard;
  }

  public int[] getVideoLostArray()
  {
    return this.m_vBytesLost;
  }

  public int[] getAudioLostArray()
  {
    return this.m_aBytesLost;
  }

  public double getVideoDiscards()
  {
    return this.m_vdiscards;
  }

  public int[] getAudioDeltaArray()
  {
    return this.m_aDeltaArray;
  }

  public int[] getAudioBytesArray()
  {
    return this.m_aBytesArray;
  }

  public int[] getAudioTimesArray()
  {
    return this.m_aTimesArray;
  }

  public int[] getVideoDeltaArray()
  {
    return this.m_vDeltaArray;
  }

  public int[] getVideoBytesArray()
  {
    return this.m_vBytesArray;
  }

  public int[] getVideoTimesArray()
  {
    return this.m_vTimesArray;
  }

  public int getState()
  {
    return this.m_nState;
  }

  public boolean isTestAudio()
  {
    return this.m_nAudioPps > 0;
  }

  public boolean isTestVideo()
  {
    return this.m_nVideoPps > 0;
  }

  public int getPercentComplete()
  {
    return this.m_nPercentDone;
  }

  public int getCurrentMs()
  {
    return this.m_nCurrentMs;
  }

  public ErrorCode getErrorCode()
  {
    return this.m_error;
  }

  public Object[] getSummaryItem(String paramString)
  {
    if (this.m_error != null)
      return null;
    String str = null;
    int i = -1;
    if (TX("aloss").equals(paramString))
    {
      str = this.m_plugin.RC(TX("video.alossnone"));
      if (this.m_aloss > this.m_plugin.iniGetInteger(TX("video.alossbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("video.alossbad"), Util.oneDP(this.m_aloss));
        i = 2;
      }
      else if (this.m_aloss > this.m_plugin.iniGetInteger(TX("video.alosswarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("video.alosswarn"), Util.oneDP(this.m_aloss));
        i = 1;
      }
      else if (this.m_aloss >= 0)
      {
        str = this.m_plugin.RC(TX("video.alossgood"), Util.oneDP(this.m_aloss));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("vloss").equals(paramString))
    {
      str = this.m_plugin.RC(TX("video.vlossnone"));
      if (this.m_vloss > this.m_plugin.iniGetInteger(TX("video.vlossbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("video.vlossbad"), Util.oneDP(this.m_vloss));
        i = 2;
      }
      else if (this.m_vloss > this.m_plugin.iniGetInteger(TX("video.vlosswarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("video.vlosswarn"), Util.oneDP(this.m_vloss));
        i = 1;
      }
      else if (this.m_vloss >= 0)
      {
        str = this.m_plugin.RC(TX("video.vlossgood"), Util.oneDP(this.m_vloss));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("adiscards").equals(paramString))
    {
      str = this.m_plugin.RC(TX("video.adiscardsnone"));
      if (this.m_adiscards > this.m_plugin.iniGetInteger(TX("video.adiscardsbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("video.adiscardsbad"), Util.oneDP(this.m_adiscards));
        i = 2;
      }
      else if (this.m_adiscards > this.m_plugin.iniGetInteger(TX("video.adiscardswarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("video.adiscardswarn"), Util.oneDP(this.m_adiscards));
        i = 1;
      }
      else if (this.m_adiscards >= 0.0D)
      {
        str = this.m_plugin.RC(TX("video.adiscardsgood"), Util.oneDP(this.m_adiscards));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("vdiscards").equals(paramString))
    {
      str = this.m_plugin.RC(TX("video.vdiscardsnone"));
      if (this.m_vdiscards > this.m_plugin.iniGetInteger(TX("video.vdiscardsbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("video.vdiscardsbad"), Util.oneDP(this.m_vdiscards));
        i = 2;
      }
      else if (this.m_vdiscards > this.m_plugin.iniGetInteger(TX("video.vdiscardswarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("video.vdiscardswarn"), Util.oneDP(this.m_vdiscards));
        i = 1;
      }
      else if (this.m_vdiscards >= 0.0D)
      {
        str = this.m_plugin.RC(TX("video.vdiscardsgood"), Util.oneDP(this.m_vdiscards));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("ajitter").equals(paramString))
    {
      str = this.m_plugin.RC(TX("video.ajitternone"));
      if (this.m_acjitter > this.m_plugin.iniGetInteger(TX("video.ajitterbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("video.ajitterbad"), Util.oneDP(this.m_acjitter));
        i = 2;
      }
      else if (this.m_acjitter > this.m_plugin.iniGetInteger(TX("video.ajitterwarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("video.ajitterwarn"), Util.oneDP(this.m_acjitter));
        i = 1;
      }
      else if (this.m_acjitter >= 0.0D)
      {
        str = this.m_plugin.RC(TX("video.ajittergood"), Util.oneDP(this.m_acjitter));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("vjitter").equals(paramString))
    {
      str = this.m_plugin.RC(TX("video.vjitternone"));
      if (this.m_vcjitter > this.m_plugin.iniGetInteger(TX("video.vjitterbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("video.vjitterbad"), Util.oneDP(this.m_vcjitter));
        i = 2;
      }
      else if (this.m_vcjitter > this.m_plugin.iniGetInteger(TX("video.vjitterwarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("video.vjitterwarn"), Util.oneDP(this.m_vcjitter));
        i = 1;
      }
      else if (this.m_vcjitter >= 0.0D)
      {
        str = this.m_plugin.RC(TX("video.vjittergood"), Util.oneDP(this.m_vcjitter));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("asjitter").equals(paramString))
    {
      str = this.m_plugin.RC(TX("video.asjitternone"));
      if (this.m_asjitter > this.m_plugin.iniGetInteger(TX("video.asjitterbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("video.asjitterbad"), Util.oneDP(this.m_asjitter));
        i = 2;
      }
      else if (this.m_asjitter > this.m_plugin.iniGetInteger(TX("video.asjitterwarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("video.asjitterwarn"), Util.oneDP(this.m_asjitter));
        i = 1;
      }
      else if (this.m_asjitter >= 0.0D)
      {
        str = this.m_plugin.RC(TX("video.asjittergood"), Util.oneDP(this.m_asjitter));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("vsjitter").equals(paramString))
    {
      str = this.m_plugin.RC(TX("video.vsjitternone"));
      if (this.m_vsjitter > this.m_plugin.iniGetInteger(TX("video.vsjitterbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("video.vsjitterbad"), Util.oneDP(this.m_vsjitter));
        i = 2;
      }
      else if (this.m_vsjitter > this.m_plugin.iniGetInteger(TX("video.vsjitterwarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("video.vsjitterwarn"), Util.oneDP(this.m_vsjitter));
        i = 1;
      }
      else if (this.m_asjitter >= 0.0D)
      {
        str = this.m_plugin.RC(TX("video.vsjittergood"), Util.oneDP(this.m_vsjitter));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    return null;
  }

  public String[] getAdvancedDataItem(String paramString)
  {
    if (this.m_error != null)
      return null;
    String str1 = null;
    String str2 = null;
    if (TX("ajitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("video.adv_") + paramString);
      str2 = (this.m_acjitter >= 0.0D ? U.d2s(this.m_acjitter) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("vjitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("video.adv_") + paramString);
      str2 = (this.m_vcjitter >= 0.0D ? U.d2s(this.m_vcjitter) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("asjitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("video.adv_") + paramString);
      str2 = (this.m_asjitter >= 0.0D ? U.d2s(this.m_asjitter) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("vsjitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("video.adv_") + paramString);
      str2 = (this.m_vsjitter >= 0.0D ? U.d2s(this.m_vsjitter) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("aloss").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("video.adv_") + paramString);
      str2 = (this.m_aloss >= 0 ? U.d2s(this.m_aloss) : TX("--")) + " %";
    }
    else if (TX("vloss").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("video.adv_") + paramString);
      str2 = (this.m_vloss >= 0 ? U.d2s(this.m_vloss) : TX("--")) + " %";
    }
    else if (TX("adiscards").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("video.adv_") + paramString);
      str2 = (this.m_adiscards >= 0.0D ? U.d2s(this.m_adiscards) : TX("--")) + " %";
    }
    else if (TX("vdiscards").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("video.adv_") + paramString);
      str2 = (this.m_vdiscards >= 0.0D ? U.d2s(this.m_vdiscards) : TX("--")) + " %";
    }
    return new String[] { str1, (str1 == null) || (str2 == null) ? null : str2 };
  }

  private String makeUrl()
  {
    return "rtsp://v." + this.m_nVideoPps + "." + this.m_nVideoBpp + "." + this.m_nVideoSdt + ".a." + this.m_nAudioPps + "." + this.m_nAudioBpp + "." + this.m_nAudioSdt + ".t." + this.m_nMs;
  }

  public void runTest()
  {
    this.m_bRun = true;
    this.m_nState = 2;
    this.m_error = null;
    String str1 = makeUrl();
    String str2 = null;
    byte[] arrayOfByte2;
    Object localObject4;
    String str5;
    int i19;
    RTSPConnection localRTSPConnection4;
    try
    {
      long l1 = U.time();
      this.m_socket = new Socket(this.m_ip, this.m_port);
      U.setSendBufferSize(this.m_socket, 65536);
      this.m_nRTT = ((int)(U.time() - l1));
      OUT("Socket connection established (" + this.m_ip + ":" + this.m_port + ")");
      this.m_in = new PushbackInputStream(this.m_socket.getInputStream());
      this.m_out = this.m_socket.getOutputStream();
      int i = 1;
      RTSPConnection localRTSPConnection1 = null;
      this.m_tab.repaint();
      if ((this.m_bRun) && (i != 0))
      {
        OUT("Performing SETUP (url=" + str1 + ", cseq=" + this.m_nCSeq + ")");
        l1 = U.time();
        localRTSPConnection1 = new RTSPConnection(this.m_socket, "SETUP", str1, this.m_nCSeq++, null);
        localRTSPConnection1.go();
        this.m_nSetupTime = ((int)(U.time() - l1));
        str2 = localRTSPConnection1.getReturnHeader("Session");
        j = localRTSPConnection1.getReturnStatus();
        OUT("sessionid=" + str2 + ", status=" + j);
        i = (str2 != null) && (j == 200) ? 1 : 0;
        if (i == 0)
          setError((short)2, "Unable to set up the RTSP connection. Is a firewall or proxy server interfering with this connection?", null);
      }
      int j = -1;
      int k = -1;
      int i2;
      Object localObject1;
      Object localObject2;
      if ((this.m_bRun) && (i != 0))
      {
        OUT("Performing DESCRIBE (url=" + str1 + ", cseq=" + this.m_nCSeq + ")");
        l1 = U.time();
        localRTSPConnection1 = new RTSPConnection(this.m_socket, "DESCRIBE", str1, this.m_nCSeq++, str2);
        localRTSPConnection1.go();
        this.m_nDescribeTime = ((int)(U.time() - l1));
        byte[] arrayOfByte1 = localRTSPConnection1.getReturnContent();
        StringTokenizer localStringTokenizer1 = new StringTokenizer(new String(arrayOfByte1), "\r\n");
        while ((this.m_bRun) && (localStringTokenizer1.hasMoreTokens()))
        {
          String str3 = localStringTokenizer1.nextToken();
          i2 = str3.indexOf('=');
          if (i2 > 0)
          {
            localObject1 = str3.substring(0, i2).trim();
            localObject2 = str3.substring(i2 + 1).trim();
            if ("m".equals(localObject1))
            {
              StringTokenizer localStringTokenizer2 = new StringTokenizer((String)localObject2, " ");
              String str4 = localStringTokenizer2.nextToken().toLowerCase();
              if ("audio".equals(str4))
                j = Integer.parseInt(localStringTokenizer2.nextToken());
              else if ("video".equals(str4))
                k = Integer.parseInt(localStringTokenizer2.nextToken());
            }
          }
          OUT("DESCRIBE info: " + str3);
        }
        OUT("audiochannel=" + j + ", videochannel=" + k);
        i = (j == -1) && (k == -1) ? 0 : 1;
        if (i == 0)
          setError((short)3, "Unable to obtain an audio or video channel. Is a firewall or proxy server interfering with this connection?", null);
      }
      if ((this.m_bRun) && (i != 0))
      {
        l1 = U.time();
        localRTSPConnection1 = new RTSPConnection(this.m_socket, "PLAY", str1, this.m_nCSeq++, str2);
        localRTSPConnection1.go();
        this.m_nPlayTime = ((int)(U.time() - l1));
        i = localRTSPConnection1.getReturnStatus() == 200 ? 1 : 0;
        if (i == 0)
          setError((short)4, "Unable to action the PLAY request. Is a firewall or proxy server interfering with this connection?", null);
      }
      int m;
      int n;
      if ((this.m_bRun) && (i != 0))
      {
        m = j == -1 ? 1 : 0;
        n = k == -1 ? 1 : 0;
        int i1 = this.m_nVideoPps * this.m_nMs / 1000;
        i2 = this.m_nAudioPps * this.m_nMs / 1000;
        this.m_vTimesArray = new int[i1];
        this.m_vBytesArray = new int[i1];
        this.m_vNowArray = new long[i1];
        this.m_vUpperArray = new long[i1];
        this.m_vBytesBad = new int[i1];
        this.m_vBytesGood = new int[i1];
        this.m_vBytesDiscard = new int[i1];
        this.m_vBytesLost = new int[i1];
        this.m_vDiscardArray = new int[i1];
        this.m_vDecide = new boolean[i1];
        this.m_vServerDeltaArray = new int[i1];
        this.m_vServerSendArray = new long[i1];
        this.m_aTimesArray = new int[i2];
        this.m_aBytesArray = new int[i2];
        this.m_aNowArray = new long[i2];
        this.m_aUpperArray = new long[i2];
        this.m_aBytesBad = new int[i2];
        this.m_aBytesGood = new int[i2];
        this.m_aBytesDiscard = new int[i2];
        this.m_aBytesLost = new int[i2];
        this.m_aDiscardArray = new int[i2];
        this.m_aDecide = new boolean[i2];
        this.m_aServerDeltaArray = new int[i2];
        this.m_aServerSendArray = new long[i2];
        localObject1 = new int[i1];
        localObject2 = new int[i2];
        for (int i5 = 0; i5 < i1; i5++)
        {
          this.m_vBytesArray[i5] = -1;
          this.m_vNowArray[i5] = -1L;
        }
        for (i5 = 0; i5 < i2; i5++)
        {
          this.m_aBytesArray[i5] = -1;
          this.m_aNowArray[i5] = -1L;
        }
        long l2 = 0L;
        long l3 = 0L;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        this.m_lStart = U.time();
        while (((m == 0) || (n == 0)) && (U.time() < this.m_lStart + this.m_nMs + 3000L))
          try
          {
            i9 = readInterleavedPacket();
            l3 = U.time();
            i8 = (int)(l3 - this.m_lStart);
            this.m_nCurrentMs = i8;
            if (i9 != -1)
              if (this.m_nPacketId < 0)
              {
                if (this.m_nStreamId == j)
                  m = 1;
                if (this.m_nStreamId == k)
                  n = 1;
              }
              else if ((this.m_nStreamId == j) && (this.m_nPacketId < this.m_aBytesArray.length))
              {
                this.m_aTimesArray[this.m_nPacketId] = i8;
                this.m_aNowArray[this.m_nPacketId] = l3;
                localObject2[this.m_nPacketId] = (i8 - this.m_nPacketTime);
                this.m_aServerSendArray[this.m_nPacketId] = this.m_nPacketTime;
                this.m_aBytesArray[this.m_nPacketId] = this.m_nLength;
                this.m_aServerDeltaArray[this.m_nPacketId] = this.m_nServerDelta;
                i7 = Math.max(this.m_nPacketId, i7);
              }
              else if ((this.m_nStreamId == k) && (this.m_nPacketId < this.m_vBytesArray.length))
              {
                this.m_vTimesArray[this.m_nPacketId] = i8;
                this.m_vNowArray[this.m_nPacketId] = l3;
                localObject1[this.m_nPacketId] = (i8 - this.m_nPacketTime);
                this.m_vServerSendArray[this.m_nPacketId] = this.m_nPacketTime;
                this.m_vBytesArray[this.m_nPacketId] = this.m_nLength;
                this.m_vServerDeltaArray[this.m_nPacketId] = this.m_nServerDelta;
                i6 = Math.max(this.m_nPacketId, i6);
              }
            if (l3 > l2)
            {
              this.m_nPercentDone = Math.min(100, Math.min(i1 > 0 ? i6 * 100 / i1 : 100, i2 > 0 ? i7 * 100 / i2 : 100));
              recalculateStats((int[])localObject1, this.m_vServerDeltaArray, this.m_vBytesArray, i6, this.m_vNowArray, this.m_vDiscardArray, this.m_vDecide, (int[])localObject2, this.m_aServerDeltaArray, this.m_aBytesArray, i7, this.m_aNowArray, this.m_aDiscardArray, this.m_aDecide);
              this.m_tab.repaint();
              l2 = l3 + 250L;
            }
          }
          catch (Exception localException3)
          {
            localException3.printStackTrace();
          }
        this.m_nPercentDone = 100;
        int i10 = 0;
        int i11 = 0;
        int i12 = -1;
        int i13 = -1;
        for (int i14 = 0; i14 < this.m_vNowArray.length; i14++)
        {
          i13 = -1;
          if (this.m_vBytesArray[i14] > 0)
            i13 = this.m_vTimesArray[i14];
          if (i13 != -1)
          {
            if (i12 >= 0)
              i10 = Math.max(i13 - i12, i10);
            i12 = i13;
          }
        }
        for (i14 = 0; i14 < this.m_aNowArray.length; i14++)
        {
          i13 = -1;
          if (this.m_aBytesArray[i14] > 0)
            i13 = this.m_aTimesArray[i14];
          if (i13 != -1)
          {
            if (i12 >= 0)
              i11 = Math.max(i13 - i12, i11);
            i12 = i13;
          }
        }
        this.m_nMaxDelay = Math.max(i11, i10);
        recalculateStats((int[])localObject1, this.m_vServerDeltaArray, this.m_vBytesArray, localObject1.length, this.m_vNowArray, this.m_vDiscardArray, this.m_vDecide, (int[])localObject2, this.m_aServerDeltaArray, this.m_aBytesArray, localObject2.length, this.m_aNowArray, this.m_aDiscardArray, this.m_aDecide);
        OUT("Timings:");
        OUT("SETUP:    " + this.m_nSetupTime + " ms");
        OUT("DESCRIBE: " + this.m_nDescribeTime + " ms");
        OUT("PLAY:     " + this.m_nPlayTime + " ms");
        OUT("RTT:      " + this.m_nRTT + " ms");
        OUT("");
        long l4 = Math.max(getDuration() / 40, 20);
        long l5 = ()(l4 / (1000.0D / this.m_nVideoPps));
        long l6 = ()(l4 / (1000.0D / this.m_nAudioPps));
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        OUT("Bucket ms:\t" + l4);
        OUT("Vppb:\t" + l5);
        OUT("Vbpb:\t" + this.m_nVideoBpp);
        OUT("Vpps:\t" + this.m_nVideoPps);
        OUT("Vca:\t" + (int)Math.abs(this.m_vcavg) + " ms");
        OUT("Vcj:\t" + (int)this.m_vcjitter + " ms");
        OUT("Vsa:\t" + (int)Math.abs(this.m_vsavg) + " ms");
        OUT("Vsj:\t" + (int)this.m_vsjitter + " ms");
        OUT("Vd:\t" + this.m_nVideoCdt + " ms");
        OUT("Vbl:\t" + this.m_vBaseLag + " ms");
        OUT("Packet times: (note 'sent' is based on server clock; 'received' on client)");
        OUT("#\tE\tS\tR\tD\tB\tA\tU");
        for (int i18 = 0; i18 < i1; i18++)
        {
          i17 = (int)(i18 * (1000.0D / this.m_nVideoPps));
          i15 = (int)(i18 / ((float)l4 / (1000.0D / this.m_nVideoPps)));
          i16 = (int)((float)(this.m_vTimesArray[i18] - this.m_vBaseLag) / (float)l4);
          OUT("  " + i18 + "\t" + i17 + "\t" + (this.m_vNowArray[i18] == -1L ? "-" : new StringBuilder().append(this.m_vServerSendArray[i18]).toString()) + "\t" + (this.m_vNowArray[i18] == -1L ? "-" : new StringBuilder().append(this.m_vTimesArray[i18]).toString()) + "\t" + (this.m_vNowArray[i18] == -1L ? "-" : new StringBuilder().append(localObject1[i18] - (i18 > 0 ? this.m_vBaseLag : 0L)).toString()) + "\t" + i15 + "\t" + (this.m_vNowArray[i18] == -1L ? "-" : new StringBuilder().append(i16).toString()) + "\t" + (this.m_vNowArray[i18] == -1L ? "-" : new StringBuilder().append(this.m_vUpperArray[i18]).toString()));
        }
        OUT("Appb:\t" + l6);
        OUT("Abpp:\t" + this.m_nAudioBpp);
        OUT("Apps:\t" + this.m_nAudioPps);
        OUT("Aca:\t" + (int)Math.abs(this.m_acavg) + " ms");
        OUT("Acj:\t" + (int)this.m_acjitter + " ms");
        OUT("Asa:\t" + (int)Math.abs(this.m_asavg) + " ms");
        OUT("Asj:\t" + (int)this.m_asjitter + " ms");
        OUT("Ad:\t" + this.m_nAudioCdt + " ms");
        OUT("Abl:\t" + this.m_aBaseLag + " ms");
        for (i18 = 0; i18 < i2; i18++)
        {
          i17 = (int)(i18 * (1000.0D / this.m_nAudioPps));
          i15 = (int)(i18 / ((float)l4 / (1000.0D / this.m_nAudioPps)));
          i16 = (int)((float)(this.m_aTimesArray[i18] - this.m_aBaseLag) / (float)l4);
          OUT("  " + i18 + "\t" + i17 + "\t" + (this.m_aNowArray[i18] == -1L ? "-" : new StringBuilder().append(this.m_aServerSendArray[i18]).toString()) + "\t" + (this.m_aNowArray[i18] == -1L ? "-" : new StringBuilder().append(this.m_aTimesArray[i18]).toString()) + "\t" + (this.m_aNowArray[i18] == -1L ? "-" : new StringBuilder().append(localObject2[i18] - (i18 > 0 ? this.m_aBaseLag : 0L)).toString()) + "\t" + i15 + "\t" + (this.m_aNowArray[i18] == -1L ? "-" : new StringBuilder().append(i16).toString()) + "\t" + (this.m_aNowArray[i18] == -1L ? "-" : new StringBuilder().append(this.m_aUpperArray[i18]).toString()));
        }
      }
      try
      {
        m = 0;
        if (this.m_nOmitMs > 0)
        {
          for (n = 0; n < this.m_vNowArray.length; n++)
          {
            if (this.m_vNowArray[n] > this.m_lStart + this.m_nOmitMs)
              break;
            m++;
          }
          OUT(" video omit ms=" + this.m_nOmitMs + " omit count=" + m);
        }
        double d = avg(this.m_vDeltaArray, this.m_vBytesArray, this.m_vBytesArray.length, m);
        boolean[] arrayOfBoolean1 = bytesArrayToLossArray(this.m_vBytesArray);
        this.m_video.addGraphResults(this.m_plugin.RC(TX("video.graphvideo")), null, null, intArrayToFloatArray(this.m_vDeltaArray, -1.0D * d), arrayOfBoolean1);
        int i3 = 0;
        if (this.m_nOmitMs > 0)
        {
          for (int i4 = 0; i4 < this.m_aNowArray.length; i4++)
          {
            if (this.m_aNowArray[i4] > this.m_lStart + this.m_nOmitMs)
              break;
            i3++;
          }
          OUT(" audio omit ms=" + this.m_nOmitMs + " omit count=" + i3);
        }
        d = avg(this.m_aDeltaArray, this.m_aBytesArray, this.m_aBytesArray.length, i3);
        boolean[] arrayOfBoolean2 = bytesArrayToLossArray(this.m_aBytesArray);
        this.m_video.addGraphResults(this.m_plugin.RC(TX("video.graphaudio")), null, null, intArrayToFloatArray(this.m_aDeltaArray, -1.0D * d), arrayOfBoolean2);
      }
      catch (Exception localException2)
      {
      }
      this.m_tab.repaint();
    }
    catch (Exception localException1)
    {
      if (this.m_port > 0)
        setError((short)1, "The test could not be completed - is a firewall blocking port " + this.m_port + "?", "error=" + localException1);
      else
        setError((short)5, "Video testing is currently disabled by the server", null);
      localException1.printStackTrace();
      this.m_tab.repaint();
    }
    finally
    {
      try
      {
        RTSPConnection localRTSPConnection2;
        if (str2 != null)
        {
          RTSPConnection localRTSPConnection3 = new RTSPConnection(this.m_socket, "TEARDOWN", str1, this.m_nCSeq++, str2);
          localRTSPConnection3.go();
          arrayOfByte2 = localRTSPConnection3.getReturnContent();
          if (arrayOfByte2 != null)
          {
            localObject4 = new StringTokenizer(new String(arrayOfByte2), "\r\n");
            while ((this.m_bRun) && (((StringTokenizer)localObject4).hasMoreTokens()))
            {
              str5 = ((StringTokenizer)localObject4).nextToken();
              if ((str5.startsWith("hbss=")) || (str5.startsWith("&hbss=")))
              {
                i19 = str5.charAt(0) == '&' ? 6 : 5;
                this.m_bnetqcheck = true;
                this.m_actMetrics = U.stringToHash(str5.substring(i19), "&\r\n", this.m_actMetrics);
              }
            }
          }
          if (this.m_bnetqcheck)
          {
            OUT(TX("AccessCM metrics (" + (this.m_actMetrics == null ? "zero" : new StringBuilder().append(this.m_actMetrics.size()).toString()) + "):"));
            localObject4 = this.m_actMetrics == null ? null : this.m_actMetrics.keys();
            do
            {
              str5 = (String)((Enumeration)localObject4).nextElement();
              OUT(TX("  ") + str5 + TX(" = ") + this.m_actMetrics.get(str5));
              if (localObject4 == null)
                break;
            }
            while (((Enumeration)localObject4).hasMoreElements());
          }
        }
      }
      catch (Exception localException8)
      {
        OUT("TEARDOWN exception " + localException8.getMessage());
      }
      try
      {
        this.m_in.close();
      }
      catch (Exception localException9)
      {
      }
      try
      {
        this.m_out.close();
      }
      catch (Exception localException10)
      {
      }
      try
      {
        this.m_socket.close();
      }
      catch (Exception localException11)
      {
      }
      this.m_nPercentDone = 100;
      this.m_nState = 3;
      this.m_tab.repaint();
    }
  }

  private int readInterleavedPacket()
  {
    byte[] arrayOfByte1 = new byte[4];
    read(arrayOfByte1);
    if (arrayOfByte1[0] == 36)
    {
      this.m_nStreamId = arrayOfByte1[1];
      this.m_nLength = Math.max(8, readShort(arrayOfByte1, 2));
      int i = this.m_nLength;
      if (i > 0)
      {
        byte[] arrayOfByte2 = new byte[i];
        read(arrayOfByte2);
        if (i >= 4)
          this.m_nPacketId = readInt(arrayOfByte2, 0);
        i -= 4;
        if (i >= 4)
          this.m_nPacketTime = readInt(arrayOfByte2, 4);
        i -= 4;
        if (i >= 4)
          this.m_nMagic = readInt(arrayOfByte2, 8);
        i -= 4;
        if (i >= 2)
          this.m_nServerDelta = (this.m_nMagic == 305419896 ? readShort(arrayOfByte2, 12) : -1);
        else
          this.m_nServerDelta = -1;
        i -= 4;
      }
      return this.m_nLength;
    }
    return -1;
  }

  public long getLateBytes()
  {
    int i = 0;
    int j = 0;
    float f = Math.max(this.m_nMs / 40.0F, 20.0F);
    int[] arrayOfInt = new int[(int)(this.m_nMs / f + 0.5D)];
    long l = 0L;
    for (int k = 0; (this.m_aTimesArray != null) && (k < this.m_aTimesArray.length); k++)
    {
      i = (int)(k / (f / (1000.0D / this.m_nAudioPps)));
      j = (int)(this.m_aTimesArray[k] / f);
      if ((i >= 0) && (arrayOfInt.length > i) && (j >= arrayOfInt.length))
        l += this.m_aBytesArray[k];
    }
    for (k = 0; (this.m_vTimesArray != null) && (k < this.m_vTimesArray.length); k++)
    {
      i = (int)(k / (f / (1000.0D / this.m_nVideoPps)));
      j = (int)(this.m_vTimesArray[k] / f);
      if ((i >= 0) && (arrayOfInt.length > i) && (j >= arrayOfInt.length))
        l += this.m_vBytesArray[k];
    }
    return l;
  }

  private void recalculateStats(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt1, long[] paramArrayOfLong1, int[] paramArrayOfInt4, boolean[] paramArrayOfBoolean1, int[] paramArrayOfInt5, int[] paramArrayOfInt6, int[] paramArrayOfInt7, int paramInt2, long[] paramArrayOfLong2, int[] paramArrayOfInt8, boolean[] paramArrayOfBoolean2)
  {
    int i;
    int j;
    int k;
    if ((paramArrayOfInt1 != null) && (paramArrayOfInt3 != null) && (paramInt1 > 0) && (paramArrayOfInt1.length > 0))
    {
      i = 0;
      j = Math.min(paramArrayOfInt1.length, paramInt1);
      for (k = 0; (this.m_nOmitMs > 0) && (k < j); k++)
      {
        if (paramArrayOfLong1[k] > this.m_lStart + this.m_nOmitMs)
          break;
        i++;
      }
      this.m_vcavg = avg(paramArrayOfInt1, paramArrayOfInt3, j, i);
      this.m_vcjitter = getJitter(this.m_vcavg, paramArrayOfInt1, paramArrayOfInt3, j, i);
      this.m_vsavg = (this.m_nMagic == 305419896 ? avg(paramArrayOfInt2, paramArrayOfInt3, j, i) : -1.0D);
      this.m_vsjitter = (this.m_nMagic == 305419896 ? getJitter(this.m_vsavg, paramArrayOfInt2, paramArrayOfInt3, j, i) : -1.0D);
      this.m_vBaseLag = getBaseLagg(j, i, this.m_vTimesArray, this.m_vBytesArray);
      this.m_vdiscards = getDiscards(this.m_vcavg, paramArrayOfInt1, paramArrayOfInt3, j, this.m_nVideoCdt, i, paramArrayOfInt4, this.m_vTimesArray, this.m_vBaseLag);
      this.m_vloss = getLoss(paramArrayOfInt3, j, i);
      this.m_nVideoLastGood = getOnTime(this.m_nVideoLastGood, j, i, this.m_nVideoPps, this.m_nVideoBpp, paramArrayOfLong1, this.m_vDiscardArray, this.m_vBytesBad, this.m_vBytesGood, this.m_vBytesDiscard, this.m_vBytesLost, paramArrayOfBoolean1, this.m_vTimesArray, this.m_vBytesArray, this.m_vBaseLag, this.m_vUpperArray);
      if (paramInt1 == paramArrayOfInt1.length)
      {
        this.m_vDeltaArray = paramArrayOfInt1;
        this.m_vBytesArray = paramArrayOfInt3;
      }
    }
    if ((paramArrayOfInt5 != null) && (paramArrayOfInt7 != null) && (paramInt2 > 0) && (paramArrayOfInt5.length > 0))
    {
      i = 0;
      j = Math.min(paramArrayOfInt5.length, paramInt2);
      for (k = 0; (this.m_nOmitMs > 0) && (k < j); k++)
      {
        if (paramArrayOfLong2[k] > this.m_lStart + this.m_nOmitMs)
          break;
        i++;
      }
      this.m_acavg = avg(paramArrayOfInt5, paramArrayOfInt7, j, i);
      this.m_acjitter = getJitter(this.m_acavg, paramArrayOfInt5, paramArrayOfInt7, j, i);
      this.m_asavg = (this.m_nMagic == 305419896 ? avg(paramArrayOfInt6, paramArrayOfInt7, j, i) : -1.0D);
      this.m_asjitter = (this.m_nMagic == 305419896 ? getJitter(this.m_asavg, paramArrayOfInt6, paramArrayOfInt7, j, i) : -1.0D);
      this.m_aBaseLag = getBaseLagg(j, i, this.m_aTimesArray, this.m_aBytesArray);
      this.m_adiscards = getDiscards(this.m_acavg, paramArrayOfInt5, paramArrayOfInt7, j, this.m_nAudioCdt, i, paramArrayOfInt8, this.m_aTimesArray, this.m_aBaseLag);
      this.m_aloss = getLoss(paramArrayOfInt7, j, i);
      this.m_nAudioLastGood = getOnTime(this.m_nAudioLastGood, j, i, this.m_nAudioPps, this.m_nAudioBpp, paramArrayOfLong2, this.m_aDiscardArray, this.m_aBytesBad, this.m_aBytesGood, this.m_aBytesDiscard, this.m_aBytesLost, paramArrayOfBoolean2, this.m_aTimesArray, this.m_aBytesArray, this.m_aBaseLag, this.m_aUpperArray);
      if (paramInt2 == paramArrayOfInt5.length)
      {
        this.m_aDeltaArray = paramArrayOfInt5;
        this.m_aBytesArray = paramArrayOfInt7;
      }
    }
  }

  private long getBaseLagg(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    long l = 0L;
    for (int i = paramInt2; i < paramInt1; i++)
      if (paramArrayOfInt2[i] > 0)
        return paramArrayOfInt1[i];
    return l;
  }

  private int getOnTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long[] paramArrayOfLong1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, boolean[] paramArrayOfBoolean, int[] paramArrayOfInt6, int[] paramArrayOfInt7, long paramLong, long[] paramArrayOfLong2)
  {
    long l = Math.max(getDuration() / 40, 20);
    int i = 0;
    int j = 0;
    for (int k = paramInt3; k < paramInt2; k++)
      if (paramArrayOfBoolean[k] == 0)
      {
        i = (int)(k * (1000.0D / paramInt4));
        paramArrayOfLong2[k] = ((i / l + 1L) * l + paramLong);
        if (paramArrayOfLong1[k] == -1L)
        {
          if ((this.m_nPercentDone == 100) || (k < paramInt1))
          {
            paramArrayOfInt5[k] += paramInt5;
            paramArrayOfBoolean[k] = true;
          }
        }
        else if (paramArrayOfInt1[k] > 0)
        {
          paramArrayOfInt4[k] += paramInt5;
          paramArrayOfBoolean[k] = true;
          j = k;
        }
        else if (paramArrayOfLong1[k] > this.m_lStart + paramArrayOfLong2[k])
        {
          paramArrayOfInt2[k] += paramInt5;
          paramArrayOfBoolean[k] = true;
          j = k;
        }
        else
        {
          paramArrayOfInt3[k] += paramInt5;
          paramArrayOfBoolean[k] = true;
          j = k;
        }
      }
    return j;
  }

  private double getJitter(double paramDouble, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = 0;
    for (int k = paramInt2; k < paramInt1; k++)
      if (paramArrayOfInt2[k] > 0)
      {
        i = (int)(i + Math.abs(paramArrayOfInt1[k] - paramDouble));
        j++;
      }
    return j > 0 ? i / j : -1.0D;
  }

  private double getDiscards(double paramDouble, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt3, int[] paramArrayOfInt4, long paramLong)
  {
    if ((paramArrayOfInt1 == null) || (paramDouble == -1.0D))
      return -1.0D;
    int i = 0;
    int j = 0;
    for (int k = paramInt3; k < paramInt1; k++)
      if (paramArrayOfInt2[k] > 0)
      {
        j++;
        if (paramArrayOfInt1[k] - paramLong > paramInt2)
        {
          i++;
          paramArrayOfInt3[k] += 1;
        }
        else
        {
          paramArrayOfInt3[k] = 0;
        }
      }
    return j != 0 ? i * 100.0D / j : 0.0D;
  }

  private int getLoss(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = 0;
    for (int j = paramInt2; j < paramInt1; j++)
      if (paramArrayOfInt[j] <= 0)
        i++;
    return paramInt1 > 0 ? i * 100 / paramInt1 : 0;
  }

  private static boolean[] bytesArrayToLossArray(int[] paramArrayOfInt)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayOfInt.length];
    for (int i = 0; i < paramArrayOfInt.length; i++)
      arrayOfBoolean[i] = (paramArrayOfInt[i] <= 0 ? 1 : false);
    return arrayOfBoolean;
  }

  private double avg(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2)
  {
    double d = 0.0D;
    int i = 0;
    for (int j = paramInt2; j < paramInt1; j++)
      if (paramArrayOfInt2[j] > 0)
      {
        d += paramArrayOfInt1[j];
        i++;
      }
    return i > 0 ? d / i : 0.0D;
  }

  private void read(byte[] paramArrayOfByte)
  {
    int i = 0;
    while (i < paramArrayOfByte.length)
      i += this.m_in.read(paramArrayOfByte, i, paramArrayOfByte.length - i);
  }

  private short readShort(byte[] paramArrayOfByte, int paramInt)
  {
    return (short)(paramArrayOfByte[paramInt] << 8 & 0xFF00 | paramArrayOfByte[(paramInt + 1)] & 0xFF);
  }

  private int readInt(byte[] paramArrayOfByte, int paramInt)
  {
    return paramArrayOfByte[paramInt] << 24 & 0xFF000000 | paramArrayOfByte[(paramInt + 1)] << 16 & 0xFF0000 | paramArrayOfByte[(paramInt + 2)] << 8 & 0xFF00 | paramArrayOfByte[(paramInt + 3)] & 0xFF;
  }

  private void OUT(String paramString)
  {
    this.m_video.OUT(paramString);
  }

  public Hashtable getAccessCTMetrics()
  {
    return this.m_actMetrics;
  }

  public boolean isNetQCheck()
  {
    return this.m_bnetqcheck;
  }

  private static float[] intArrayToFloatArray(int[] paramArrayOfInt, double paramDouble)
  {
    if (paramArrayOfInt == null)
      return null;
    if (paramArrayOfInt.length == 0)
      return new float[0];
    float[] arrayOfFloat = new float[paramArrayOfInt.length];
    for (int i = 0; i < paramArrayOfInt.length; i++)
      arrayOfFloat[i] = (paramArrayOfInt[i] + (float)paramDouble);
    return arrayOfFloat;
  }

  private void setError(short paramShort, String paramString1, String paramString2)
  {
    this.m_error = new ErrorCode(0x70000 | paramShort & 0xFFFF, paramString1);
    this.m_error.detail = paramString2;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.video.VideoTest
 * JD-Core Version:    0.6.2
 */