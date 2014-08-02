package mcsaplugins.iptv;

import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class IptvTest extends AppletTest
{
  public static final int S_PRE = 1;
  public static final int S_RUNNING = 2;
  public static final int S_COMPLETE = 3;
  private IptvTab m_tab;
  private String m_ip;
  private String m_error;
  private int m_port1;
  private int m_port2;
  private int m_port3;
  private int m_Bytes1;
  private int m_FrameSize1;
  private int m_FramesPerSecond1;
  private int m_Frames1;
  private int m_FramesOmit1;
  private int m_Bytes2;
  private int m_FrameSize2;
  private int m_FramesPerSecond2;
  private int m_Frames2;
  private int m_FramesOmit2;
  private int m_Bytes3;
  private int m_FrameSize3;
  private int m_FramesPerSecond3;
  private int m_Frames3;
  private int m_FramesOmit3;
  private int m_packetsperframe1;
  private int m_packetsperframe2;
  private int m_packetsperframe3;
  private int m_nPercentDone;
  private double m_jit1;
  private double m_los1;
  private double m_avg1;
  private double m_std1;
  private double m_ord1;
  private double m_jit2;
  private double m_los2;
  private double m_avg2;
  private double m_std2;
  private double m_ord2;
  private double m_jit3;
  private double m_los3;
  private double m_avg3;
  private double m_std3;
  private double m_ord3;
  private long[][] m_DeltaArray1;
  private int[][] m_BytesArray1;
  private int[][] m_Sequence1;
  private long[][] m_DeltaArray2;
  private int[][] m_BytesArray2;
  private int[][] m_Sequence2;
  private long[][] m_DeltaArray3;
  private int[][] m_BytesArray3;
  private int[][] m_Sequence3;
  private int m_nState = 1;

  public IptvTest(IptvTab paramIptvTab, iptv paramiptv, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, int paramInt18)
  {
    super(paramiptv, paramIptvTab);
    this.m_tab = paramIptvTab;
    this.m_ip = paramString;
    this.m_port1 = paramInt1;
    this.m_port2 = paramInt2;
    this.m_port3 = paramInt3;
    this.m_Bytes1 = paramInt4;
    this.m_FrameSize1 = paramInt5;
    this.m_FramesPerSecond1 = paramInt6;
    this.m_Frames1 = paramInt7;
    this.m_FramesOmit1 = paramInt8;
    this.m_Bytes2 = paramInt9;
    this.m_FrameSize2 = paramInt10;
    this.m_FramesPerSecond2 = paramInt11;
    this.m_Frames2 = paramInt12;
    this.m_FramesOmit2 = paramInt13;
    this.m_Bytes3 = paramInt14;
    this.m_FrameSize3 = paramInt15;
    this.m_FramesPerSecond3 = paramInt16;
    this.m_Frames3 = paramInt17;
    this.m_FramesOmit3 = paramInt18;
  }

  private double calcAvg(long[][] paramArrayOfLong, int[][] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    double d = 0.0D;
    int i = 0;
    for (int j = paramInt3; j < paramInt2; j++)
      for (int k = 0; k < paramInt1; k++)
        if (paramArrayOfInt[j][k] != -1)
        {
          d += paramArrayOfLong[j][k];
          i++;
        }
    return i > 0 ? d / i : 0.0D;
  }

  private double calcJitter(double paramDouble, long[][] paramArrayOfLong, int[][] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    double d = 0.0D;
    for (int j = paramInt3; j < paramInt2; j++)
      for (int k = 0; k < paramInt1; k++)
        if (paramArrayOfInt[j][k] != -1)
        {
          d += Math.abs(paramArrayOfLong[j][k] - paramDouble);
          i++;
        }
    return i > 0 ? d / i : -1.0D;
  }

  private double calcLoss(int[][] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    for (int j = paramInt3; j < paramInt2; j++)
      for (int k = 0; k < paramInt1; k++)
        if (paramArrayOfInt[j][k] == -1)
          i++;
    j = paramInt2 * paramInt1;
    return j > 0 ? i * 100.0D / j : 0.0D;
  }

  private double calcStd(long[][] paramArrayOfLong, int[][] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    double d1 = -1.0D;
    double d2 = 0.0D;
    double d3 = 0.0D;
    double d4 = 0.0D;
    int i = (paramInt2 - paramInt3) * paramInt1;
    for (int j = paramInt3; j < paramInt2; j++)
      for (int k = 0; k < paramInt1; k++)
        if (paramArrayOfInt[j][k] != -1)
          d4 += paramArrayOfLong[j][k];
    double d5 = d4 / i;
    for (int m = paramInt3; m < paramInt2; m++)
      for (int n = 0; n < paramInt1; n++)
        if (paramArrayOfInt[m][n] != -1)
        {
          d2 = paramArrayOfLong[m][n] - d5;
          d2 = Math.pow(d2, 2.0D);
          d3 += d2;
        }
    if (d3 > 0.0D)
    {
      d3 /= i;
      d1 = Math.sqrt(d3);
    }
    return d1;
  }

  public double calcSequence(int[][] paramArrayOfInt1, int[][] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
  {
    long l1 = 0L;
    long l2 = (paramInt2 - paramInt3) * paramInt1;
    for (int i = paramInt3; i < paramInt2; i++)
      for (int j = 0; j < paramInt1; j++)
        if (paramArrayOfInt2[i][j] != -1)
          l1 += paramArrayOfInt1[i][j];
    return l1 / l2 * 100.0D;
  }

  public String[] getAdvancedDataItem(String paramString)
  {
    String str1 = null;
    String str2 = null;
    if (TX("vjitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_jit1 >= 0.0D ? U.d2s(this.m_jit1) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("lajitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_jit2 >= 0.0D ? U.d2s(this.m_jit2) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("rajitter").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_jit3 >= 0.0D ? U.d2s(this.m_jit3) : TX("--")) + TX(" ") + this.m_plugin.RC(TX("ms"));
    }
    else if (TX("vloss").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_los1 >= 0.0D ? U.d2s(this.m_los1) : TX("--")) + " %";
    }
    else if (TX("laloss").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_los2 >= 0.0D ? U.d2s(this.m_los2) : TX("--")) + " %";
    }
    else if (TX("raloss").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_los3 >= 0.0D ? U.d2s(this.m_los3) : TX("--")) + " %";
    }
    else if (TX("vorder").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_ord1 >= 0.0D ? U.d2s(this.m_ord1) : TX("--")) + " %";
    }
    else if (TX("laorder").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_ord2 >= 0.0D ? U.d2s(this.m_ord2) : TX("--")) + " %";
    }
    else if (TX("raorder").equals(paramString))
    {
      str1 = this.m_plugin.RC(TX("iptv.adv_") + paramString);
      str2 = (this.m_ord3 >= 0.0D ? U.d2s(this.m_ord3) : TX("--")) + " %";
    }
    return new String[] { str1, (str1 == null) || (str2 == null) ? null : str2 };
  }

  public String getError()
  {
    return this.m_error;
  }

  public double getJit1()
  {
    return this.m_jit1;
  }

  public double getOrd1()
  {
    return this.m_ord1;
  }

  public double[] getJitter1Array()
  {
    return getJitterArray(this.m_DeltaArray1, this.m_FramesOmit1, this.m_packetsperframe1, this.m_avg1);
  }

  public double[] getJitter2Array()
  {
    return getJitterArray(this.m_DeltaArray2, this.m_FramesOmit2, this.m_packetsperframe2, this.m_avg2);
  }

  public double[] getJitter3Array()
  {
    return getJitterArray(this.m_DeltaArray3, this.m_FramesOmit3, this.m_packetsperframe3, this.m_avg3);
  }

  private static double[] getJitterArray(long[][] paramArrayOfLong, int paramInt1, int paramInt2, double paramDouble)
  {
    if ((paramArrayOfLong == null) || (paramArrayOfLong.length < 1))
      return null;
    double[] arrayOfDouble = new double[(paramArrayOfLong.length - paramInt1) * paramInt2];
    for (int i = paramInt1; i < paramArrayOfLong.length; i++)
      for (int j = 0; j < paramArrayOfLong[i].length; j++)
        arrayOfDouble[((i - paramInt1) * paramInt2 + j)] = (paramArrayOfLong[i][j] == -1L ? 0.0D : paramArrayOfLong[i][j] - paramDouble);
    return arrayOfDouble;
  }

  public int[] getSequence1Array()
  {
    return getSequenceArray(this.m_Sequence1, this.m_FramesOmit1, this.m_packetsperframe1);
  }

  public int[] getSequence2Array()
  {
    return getSequenceArray(this.m_Sequence2, this.m_FramesOmit2, this.m_packetsperframe2);
  }

  public int[] getSequence3Array()
  {
    return getSequenceArray(this.m_Sequence3, this.m_FramesOmit3, this.m_packetsperframe3);
  }

  private static int[] getSequenceArray(int[][] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length < 1))
      return null;
    int[] arrayOfInt = new int[(paramArrayOfInt.length - paramInt1) * paramInt2];
    for (int i = paramInt1; i < paramArrayOfInt.length; i++)
      for (int j = 0; j < paramArrayOfInt[i].length; j++)
        arrayOfInt[((i - paramInt1) * paramInt2 + j)] = (paramArrayOfInt[i][j] == 1 ? 1 : 0);
    return arrayOfInt;
  }

  public int[] getBytes1Array()
  {
    return getBytesArray(this.m_BytesArray1, this.m_FramesOmit1, this.m_packetsperframe1);
  }

  public int[] getBytes2Array()
  {
    return getBytesArray(this.m_BytesArray2, this.m_FramesOmit2, this.m_packetsperframe2);
  }

  public int[] getBytes3Array()
  {
    return getBytesArray(this.m_BytesArray3, this.m_FramesOmit3, this.m_packetsperframe3);
  }

  public static int[] getBytesArray(int[][] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length < 1))
      return null;
    int[] arrayOfInt = new int[(paramArrayOfInt.length - paramInt1) * paramInt2];
    for (int i = paramInt1; i < paramArrayOfInt.length; i++)
      for (int j = 0; j < paramArrayOfInt[i].length; j++)
        arrayOfInt[((i - paramInt1) * paramInt2 + j)] += (paramArrayOfInt[i][j] < 0 ? 0 : paramArrayOfInt[i][j]);
    return arrayOfInt;
  }

  public int[] getLoss1Array()
  {
    return getLossArray(this.m_BytesArray1, this.m_FramesOmit1, this.m_packetsperframe1);
  }

  public int[] getLoss2Array()
  {
    return getLossArray(this.m_BytesArray2, this.m_FramesOmit2, this.m_packetsperframe2);
  }

  public int[] getLoss3Array()
  {
    return getLossArray(this.m_BytesArray3, this.m_FramesOmit3, this.m_packetsperframe3);
  }

  private static int[] getLossArray(int[][] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length < 1))
      return null;
    int[] arrayOfInt = new int[(paramArrayOfInt.length - paramInt1) * paramInt2];
    for (int i = paramInt1; i < paramArrayOfInt.length; i++)
      for (int j = 0; j < paramArrayOfInt[i].length; j++)
        arrayOfInt[((i - paramInt1) * paramInt2 + j)] = (paramArrayOfInt[i][j] < 0 ? 1 : 0);
    return arrayOfInt;
  }

  public double getJit2()
  {
    return isTest2() ? this.m_jit2 : -1.0D;
  }

  public double getOrd2()
  {
    return isTest2() ? this.m_ord2 : -1.0D;
  }

  public double getJit3()
  {
    return isTest3() ? this.m_jit3 : -1.0D;
  }

  public double getOrd3()
  {
    return isTest3() ? this.m_ord3 : -1.0D;
  }

  public double[] getJitters()
  {
    return new double[] { getJit1(), getJit2(), getJit3() };
  }

  public double getPacketLoss1()
  {
    return this.m_los1;
  }

  public double getPacketLoss2()
  {
    return isTest2() ? this.m_los2 : -1.0D;
  }

  public double getPacketLoss3()
  {
    return isTest3() ? this.m_los3 : -1.0D;
  }

  public double[] getPacketLosses()
  {
    return new double[] { getPacketLoss1(), getPacketLoss2(), getPacketLoss3() };
  }

  public int getPercentDone()
  {
    return this.m_nPercentDone;
  }

  public int getState()
  {
    return this.m_nState;
  }

  public double getStd1()
  {
    return this.m_std1;
  }

  public double getStd2()
  {
    return this.m_std2;
  }

  public double getStd3()
  {
    return this.m_std3;
  }

  public Object[] getSummaryItem(String paramString)
  {
    String str;
    int i;
    if (TX("vjitter").equals(paramString))
    {
      str = this.m_plugin.RC(TX("iptv.vjitternone"));
      i = -1;
      if (this.m_jit1 > this.m_plugin.iniGetInteger(TX("iptv.vjitterbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("iptv.vjitterbad"), Util.oneDP(this.m_jit1));
        i = 2;
      }
      else if (this.m_jit1 > this.m_plugin.iniGetInteger(TX("iptv.vjitterwarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("iptv.vjitterwarn"), Util.oneDP(this.m_jit1));
        i = 1;
      }
      else if (this.m_jit1 >= 0.0D)
      {
        str = this.m_plugin.RC(TX("iptv.vjittergood"), Util.oneDP(this.m_jit1));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("lajitter").equals(paramString))
    {
      str = this.m_plugin.RC(TX("iptv.lajitternone"));
      i = -1;
      if (this.m_jit2 > this.m_plugin.iniGetInteger(TX("iptv.lajitterbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("iptv.lajitterbad"), Util.oneDP(this.m_jit2));
        i = 2;
      }
      else if (this.m_jit2 > this.m_plugin.iniGetInteger(TX("iptv.lajitterwarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("iptv.lajitterwarn"), Util.oneDP(this.m_jit2));
        i = 1;
      }
      else if (this.m_jit2 >= 0.0D)
      {
        str = this.m_plugin.RC(TX("iptv.lajittergood"), Util.oneDP(this.m_jit2));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    if (TX("rajitter").equals(paramString))
    {
      str = this.m_plugin.RC(TX("iptv.rajitternone"));
      i = -1;
      if (this.m_jit3 > this.m_plugin.iniGetInteger(TX("iptv.rajitterbadnum"), 30))
      {
        str = this.m_plugin.RC(TX("iptv.rajitterbad"), Util.oneDP(this.m_jit3));
        i = 2;
      }
      else if (this.m_jit3 > this.m_plugin.iniGetInteger(TX("iptv.rajitterwarnnum"), 8))
      {
        str = this.m_plugin.RC(TX("iptv.rajitterwarn"), Util.oneDP(this.m_jit3));
        i = 1;
      }
      else if (this.m_jit3 >= 0.0D)
      {
        str = this.m_plugin.RC(TX("iptv.rajittergood"), Util.oneDP(this.m_jit3));
        i = 0;
      }
      return new Object[] { new Integer(i), str };
    }
    return null;
  }

  public boolean isTest2()
  {
    return this.m_Frames2 > 0;
  }

  public boolean isTest3()
  {
    return this.m_Frames3 > 0;
  }

  public void out(String paramString)
  {
    this.m_plugin.OUT(paramString);
  }

  private void recalculateStats(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if ((this.m_DeltaArray1 != null) && (this.m_BytesArray1 != null) && (paramInt1 > 0) && (this.m_DeltaArray1.length > 0))
    {
      this.m_avg1 = calcAvg(this.m_DeltaArray1, this.m_BytesArray1, this.m_packetsperframe1, paramInt2, this.m_FramesOmit1);
      this.m_std1 = calcStd(this.m_DeltaArray1, this.m_BytesArray1, this.m_packetsperframe1, paramInt2, this.m_FramesOmit1);
      this.m_jit1 = calcJitter(this.m_avg1, this.m_DeltaArray1, this.m_BytesArray1, this.m_packetsperframe1, paramInt2, this.m_FramesOmit1);
      this.m_los1 = calcLoss(this.m_BytesArray1, this.m_packetsperframe1, paramInt2, this.m_FramesOmit1);
      this.m_ord1 = calcSequence(this.m_Sequence1, this.m_BytesArray1, this.m_packetsperframe1, paramInt2, this.m_FramesOmit1);
    }
    if ((this.m_DeltaArray2 != null) && (this.m_BytesArray2 != null) && (paramInt3 > 0) && (this.m_DeltaArray2.length > 0))
    {
      this.m_avg2 = calcAvg(this.m_DeltaArray2, this.m_BytesArray2, this.m_packetsperframe2, paramInt4, this.m_FramesOmit2);
      this.m_std2 = calcStd(this.m_DeltaArray2, this.m_BytesArray2, this.m_packetsperframe2, paramInt4, this.m_FramesOmit2);
      this.m_jit2 = calcJitter(this.m_avg2, this.m_DeltaArray2, this.m_BytesArray2, this.m_packetsperframe2, paramInt4, this.m_FramesOmit2);
      this.m_los2 = calcLoss(this.m_BytesArray2, this.m_packetsperframe2, paramInt4, this.m_FramesOmit2);
      this.m_ord2 = calcSequence(this.m_Sequence2, this.m_BytesArray2, this.m_packetsperframe2, paramInt4, this.m_FramesOmit2);
    }
    if ((this.m_DeltaArray3 != null) && (this.m_BytesArray3 != null) && (paramInt5 > 0) && (this.m_DeltaArray3.length > 0))
    {
      this.m_avg3 = calcAvg(this.m_DeltaArray3, this.m_BytesArray3, this.m_packetsperframe3, paramInt6, this.m_FramesOmit3);
      this.m_std3 = calcStd(this.m_DeltaArray3, this.m_BytesArray3, this.m_packetsperframe3, paramInt6, this.m_FramesOmit3);
      this.m_jit3 = calcJitter(this.m_avg3, this.m_DeltaArray3, this.m_BytesArray3, this.m_packetsperframe3, paramInt6, this.m_FramesOmit3);
      this.m_los3 = calcLoss(this.m_BytesArray3, this.m_packetsperframe3, paramInt6, this.m_FramesOmit3);
      this.m_ord3 = calcSequence(this.m_Sequence3, this.m_BytesArray3, this.m_packetsperframe3, paramInt6, this.m_FramesOmit3);
    }
  }

  public void runTest()
  {
    this.m_nState = 2;
    try
    {
      IptvClient localIptvClient1 = null;
      IptvClient localIptvClient2 = null;
      IptvClient localIptvClient3 = null;
      Thread localThread1 = null;
      Thread localThread2 = null;
      Thread localThread3 = null;
      this.m_packetsperframe1 = (this.m_FrameSize1 / this.m_Bytes1);
      long l1 = System.currentTimeMillis() % 503L;
      localIptvClient1 = new IptvClient(this, l1, this.m_ip, this.m_port1, this.m_Bytes1, this.m_FrameSize1, this.m_FramesPerSecond1, this.m_Frames1);
      localThread1 = new Thread(localIptvClient1, "Stream1");
      localThread1.start();
      if (this.m_Frames2 > 0)
      {
        this.m_packetsperframe2 = (this.m_FrameSize2 / this.m_Bytes2);
        localIptvClient2 = new IptvClient(this, l1 + 31L, this.m_ip, this.m_port2, this.m_Bytes2, this.m_FrameSize2, this.m_FramesPerSecond2, this.m_Frames2);
        localThread2 = new Thread(localIptvClient2, "Stream2");
        localThread2.start();
      }
      if (this.m_Frames3 > 0)
      {
        this.m_packetsperframe3 = (this.m_FrameSize3 / this.m_Bytes3);
        localIptvClient3 = new IptvClient(this, l1 + 73L, this.m_ip, this.m_port3, this.m_Bytes3, this.m_FrameSize3, this.m_FramesPerSecond3, this.m_Frames3);
        localThread3 = new Thread(localIptvClient3, "Stream3");
        localThread3.start();
      }
      int i = 0;
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      int i1 = 0;
      long l2 = System.currentTimeMillis();
      while ((localThread1.isAlive()) || ((this.m_Frames2 > 0) && (localThread2.isAlive())) || ((this.m_Frames3 > 0) && (localThread3.isAlive())))
        try
        {
          try
          {
            Thread.sleep(200L);
          }
          catch (Exception localException2)
          {
          }
          i = 0;
          j = 0;
          k = 0;
          m = 0;
          n = 0;
          i1 = 0;
          this.m_DeltaArray1 = localIptvClient1.getDelta();
          this.m_BytesArray1 = localIptvClient1.getBytes();
          this.m_Sequence1 = localIptvClient1.getSequence();
          int i3;
          for (int i2 = this.m_FramesOmit1; i2 < this.m_Frames1; i2++)
            for (i3 = 0; i3 < this.m_packetsperframe1; i3++)
              if (this.m_DeltaArray1[i2][i3] != -1L)
              {
                m = i2;
                i++;
              }
          if (this.m_Frames2 > 0)
          {
            this.m_DeltaArray2 = localIptvClient2.getDelta();
            this.m_BytesArray2 = localIptvClient2.getBytes();
            this.m_Sequence2 = localIptvClient2.getSequence();
            for (i2 = this.m_FramesOmit2; i2 < this.m_Frames2; i2++)
              for (i3 = 0; i3 < this.m_packetsperframe2; i3++)
                if (this.m_DeltaArray2[i2][i3] != -1L)
                {
                  n = i2;
                  j++;
                }
          }
          if (this.m_Frames3 > 0)
          {
            this.m_DeltaArray3 = localIptvClient3.getDelta();
            this.m_BytesArray3 = localIptvClient3.getBytes();
            this.m_Sequence3 = localIptvClient3.getSequence();
            for (i2 = this.m_FramesOmit3; i2 < this.m_Frames3; i2++)
              for (i3 = 0; i3 < this.m_packetsperframe3; i3++)
                if (this.m_DeltaArray3[i2][i3] != -1L)
                {
                  i1 = i2;
                  k++;
                }
          }
          String str;
          if ((str = localIptvClient1.getError()) != null)
          {
            this.m_error = str;
            this.m_nPercentDone = 100;
          }
          else if ((this.m_Frames2 > 0) && ((str = localIptvClient2.getError()) != null))
          {
            this.m_error = str;
            this.m_nPercentDone = 100;
          }
          else if ((this.m_Frames3 > 0) && ((str = localIptvClient3.getError()) != null))
          {
            this.m_error = str;
            this.m_nPercentDone = 100;
          }
          else
          {
            long l3 = System.currentTimeMillis();
            long l4 = l3 - l2 > 2000L ? l3 - 2000L - l2 : 0L;
            this.m_nPercentDone = Math.min(99, Math.max((int)(l4 * 100L / (this.m_Frames1 * 1000 / this.m_FramesPerSecond1)), i * 100 / (this.m_Frames1 * this.m_packetsperframe1)));
          }
          recalculateStats(i, m, j, n, k, i1);
          this.m_tab.repaint();
        }
        catch (Exception localException3)
        {
          localException3.printStackTrace();
          this.m_error = localException3.toString();
        }
      recalculateStats(this.m_DeltaArray1 == null ? 0 : this.m_DeltaArray1.length, this.m_DeltaArray1 == null ? 0 : this.m_DeltaArray1.length, this.m_DeltaArray2 == null ? 0 : this.m_DeltaArray2.length, this.m_DeltaArray2 == null ? 0 : this.m_DeltaArray2.length, this.m_DeltaArray3 == null ? 0 : this.m_DeltaArray3.length, this.m_DeltaArray3 == null ? 0 : this.m_DeltaArray3.length);
      out("session=" + localIptvClient1.getSession() + " jit=" + Util.oneDP(this.m_jit1) + " los=" + Util.oneDP(this.m_los1) + " order%=" + Util.oneDP(this.m_ord1) + " omit=" + this.m_FramesOmit1 + " ppf=" + this.m_packetsperframe1 + " recd=" + i);
      if (this.m_Frames2 > 0)
        out("session=" + localIptvClient2.getSession() + " jit=" + Util.oneDP(this.m_jit2) + " los=" + Util.oneDP(this.m_los2) + " order%=" + Util.oneDP(this.m_ord2) + " omit=" + this.m_FramesOmit2 + " ppf=" + this.m_packetsperframe2 + " recd=" + j);
      if (this.m_Frames3 > 0)
        out("session=" + localIptvClient3.getSession() + " jit=" + Util.oneDP(this.m_jit3) + " los=" + Util.oneDP(this.m_los3) + " order%=" + Util.oneDP(this.m_ord3) + " omit=" + this.m_FramesOmit3 + " ppf=" + this.m_packetsperframe3 + " recd=" + k);
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("iptv.graphjitter1")), null, null, toFloats(getJitter1Array()), toBooleans(getLoss1Array()));
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("iptv.graphjitter2")), null, null, toFloats(getJitter2Array()), toBooleans(getLoss2Array()));
      this.m_plugin.addGraphResults(this.m_plugin.RC(TX("iptv.graphjitter3")), null, null, toFloats(getJitter3Array()), toBooleans(getLoss3Array()));
      out("Tick resolution=" + U.getTickResolution());
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      this.m_nPercentDone = 100;
      this.m_nState = 3;
      this.m_tab.repaint();
    }
  }

  private static float[] toFloats(double[] paramArrayOfDouble)
  {
    float[] arrayOfFloat = paramArrayOfDouble == null ? null : new float[paramArrayOfDouble.length];
    for (int i = 0; (arrayOfFloat != null) && (i < arrayOfFloat.length); i++)
      arrayOfFloat[i] = ((float)paramArrayOfDouble[i]);
    return arrayOfFloat;
  }

  private static boolean[] toBooleans(int[] paramArrayOfInt)
  {
    boolean[] arrayOfBoolean = paramArrayOfInt == null ? null : new boolean[paramArrayOfInt.length];
    for (int i = 0; (arrayOfBoolean != null) && (i < arrayOfBoolean.length); i++)
      arrayOfBoolean[i] = (paramArrayOfInt[i] > 0 ? 1 : false);
    return arrayOfBoolean;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.iptv.IptvTest
 * JD-Core Version:    0.6.2
 */