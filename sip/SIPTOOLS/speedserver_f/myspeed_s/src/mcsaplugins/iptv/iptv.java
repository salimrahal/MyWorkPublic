package mcsaplugins.iptv;

import java.applet.Applet;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class iptv extends AppletPlugin
{
  private IptvTest m_test;
  private IptvTab m_tab;

  public iptv(Applet paramApplet)
  {
    super(paramApplet, TX("iptv"));
  }

  public void doFirstTimeInit()
  {
    this.m_tab = new IptvTab(getApplet(), this);
    this.m_tab.doFirstTimeInit();
    addTabToApplet(this.m_tab, true);
  }

  public void reset()
  {
    this.m_test = null;
    setMSSID(null);
  }

  public String doMySpeedVariableSubstitution(String paramString, boolean paramBoolean)
  {
    int i = this.m_test != null ? 1 : 0;
    int j = (this.m_test != null) && (this.m_test.isTest2()) ? 1 : 0;
    int k = (this.m_test != null) && (this.m_test.isTest3()) ? 1 : 0;
    paramString = U.subst(paramString, TX("$IPTV.JITTER1$"), i == 0 ? -1.0D : toDP(this.m_test.getJit1(), 2));
    paramString = U.subst(paramString, TX("$IPTV.LOSS1$"), i == 0 ? -1.0D : toDP(this.m_test.getPacketLoss1(), 2));
    paramString = U.subst(paramString, TX("$IPTV.ORDER1$"), i == 0 ? -1.0D : toDP(this.m_test.getOrd1(), 2));
    paramString = U.subst(paramString, TX("$IPTV.JITTER2$"), j == 0 ? -1.0D : toDP(this.m_test.getJit2(), 2));
    paramString = U.subst(paramString, TX("$IPTV.LOSS2$"), j == 0 ? -1.0D : toDP(this.m_test.getPacketLoss2(), 2));
    paramString = U.subst(paramString, TX("$IPTV.ORDER2$"), j == 0 ? -1.0D : toDP(this.m_test.getOrd2(), 2));
    paramString = U.subst(paramString, TX("$IPTV.JITTER3$"), k == 0 ? -1.0D : toDP(this.m_test.getJit3(), 2));
    paramString = U.subst(paramString, TX("$IPTV.LOSS3$"), k == 0 ? -1.0D : toDP(this.m_test.getPacketLoss3(), 2));
    paramString = U.subst(paramString, TX("$IPTV.ORDER3$"), k == 0 ? -1.0D : toDP(this.m_test.getOrd3(), 2));
    paramString = U.subst(paramString, TX("$JITTER1$"), i == 0 ? -1.0D : toDP(this.m_test.getJit1(), 2));
    paramString = U.subst(paramString, TX("$LOSS1$"), i == 0 ? -1.0D : toDP(this.m_test.getPacketLoss1(), 2));
    paramString = U.subst(paramString, TX("$ORDER1$"), i == 0 ? -1.0D : toDP(this.m_test.getOrd1(), 2));
    paramString = U.subst(paramString, TX("$JITTER2$"), j == 0 ? -1.0D : toDP(this.m_test.getJit2(), 2));
    paramString = U.subst(paramString, TX("$LOSS2$"), j == 0 ? -1.0D : toDP(this.m_test.getPacketLoss2(), 2));
    paramString = U.subst(paramString, TX("$ORDER2$"), j == 0 ? -1.0D : toDP(this.m_test.getOrd2(), 2));
    paramString = U.subst(paramString, TX("$JITTER3$"), k == 0 ? -1.0D : toDP(this.m_test.getJit3(), 2));
    paramString = U.subst(paramString, TX("$LOSS3$"), k == 0 ? -1.0D : toDP(this.m_test.getPacketLoss3(), 2));
    paramString = U.subst(paramString, TX("$ORDER3$"), k == 0 ? -1.0D : toDP(this.m_test.getOrd3(), 2));
    return paramString;
  }

  private static double toDP(double paramDouble, int paramInt)
  {
    if (paramInt == 0)
      paramInt = 1;
    double d = Math.pow(10.0D, paramInt);
    paramDouble *= d;
    int i = (int)paramDouble;
    paramDouble = i / d;
    return paramDouble;
  }

  public String getDetailResults()
  {
    StringBuffer localStringBuffer = new StringBuffer(65536);
    if (this.m_test != null)
    {
      String[] arrayOfString1 = toJitterLoss(this.m_test.getJitter1Array(), this.m_test.getBytes1Array(), this.m_test.getSequence1Array(), this.m_test.getLoss1Array());
      if (arrayOfString1 != null)
      {
        localStringBuffer.append("&detail.jtr1=").append(arrayOfString1[0]);
        localStringBuffer.append("&detail.byt1=").append(arrayOfString1[1]);
        localStringBuffer.append("&detail.seq1=").append(arrayOfString1[2]);
        localStringBuffer.append("&detail.los1=").append(arrayOfString1[3]);
      }
      String[] arrayOfString2;
      if (this.m_test.isTest2())
      {
        arrayOfString2 = toJitterLoss(this.m_test.getJitter2Array(), this.m_test.getBytes2Array(), this.m_test.getSequence2Array(), this.m_test.getLoss2Array());
        if (arrayOfString2 != null)
        {
          localStringBuffer.append("&detail.jtr2=").append(arrayOfString2[0]);
          localStringBuffer.append("&detail.byt2=").append(arrayOfString2[1]);
          localStringBuffer.append("&detail.seq2=").append(arrayOfString2[2]);
          localStringBuffer.append("&detail.los2=").append(arrayOfString2[3]);
        }
      }
      if (this.m_test.isTest3())
      {
        arrayOfString2 = toJitterLoss(this.m_test.getJitter3Array(), this.m_test.getBytes3Array(), this.m_test.getSequence3Array(), this.m_test.getLoss3Array());
        if (arrayOfString2 != null)
        {
          localStringBuffer.append("&detail.jtr3=").append(arrayOfString2[0]);
          localStringBuffer.append("&detail.byt3=").append(arrayOfString2[1]);
          localStringBuffer.append("&detail.seq3=").append(arrayOfString2[2]);
          localStringBuffer.append("&detail.los3=").append(arrayOfString2[3]);
        }
      }
    }
    return localStringBuffer.toString();
  }

  public String[] toJitterLoss(double[] paramArrayOfDouble, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    if ((paramArrayOfDouble == null) || (paramArrayOfInt1 == null) || (paramArrayOfInt2 == null) || (paramArrayOfInt3 == null))
      return null;
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    StringBuffer localStringBuffer3 = new StringBuffer();
    StringBuffer localStringBuffer4 = new StringBuffer();
    for (int i = 0; i < paramArrayOfDouble.length; i++)
      localStringBuffer1.append((i > 0 ? "," : "") + paramArrayOfDouble[i]);
    for (i = 0; i < paramArrayOfInt1.length; i++)
      localStringBuffer2.append((i > 0 ? "," : "") + paramArrayOfInt1[i]);
    for (i = 0; i < paramArrayOfInt2.length; i++)
      localStringBuffer3.append((i > 0 ? "," : "") + (paramArrayOfInt2[i] > 0 ? "y" : "n"));
    for (i = 0; i < paramArrayOfInt3.length; i++)
      localStringBuffer4.append((i > 0 ? "," : "") + (paramArrayOfInt3[i] > 0 ? "y" : "n"));
    return new String[] { localStringBuffer1.toString(), localStringBuffer2.toString(), localStringBuffer3.toString(), localStringBuffer4.toString() };
  }

  public String getReportMSS()
  {
    if (this.m_test == null)
      return "";
    return TX("&iptv.jitter1=") + Util.oneDP(this.m_test.getJit1()) + TX("&iptv.loss1=") + Util.oneDP(this.m_test.getPacketLoss1()) + TX("&iptv.order1=") + Util.oneDP(this.m_test.getOrd1()) + TX("&iptv.std1=") + Util.oneDP(this.m_test.getStd1()) + (this.m_test.isTest2() ? TX("&iptv.jitter2=") + Util.oneDP(this.m_test.getJit2()) + TX("&iptv.loss2=") + Util.oneDP(this.m_test.getPacketLoss2()) + TX("&iptv.order2=") + Util.oneDP(this.m_test.getOrd2()) + TX("&iptv.std2=") + Util.oneDP(this.m_test.getStd2()) : "") + (this.m_test.isTest3() ? TX("&iptv.jitter3=") + Util.oneDP(this.m_test.getJit3()) + TX("&iptv.loss3=") + Util.oneDP(this.m_test.getPacketLoss3()) + TX("&iptv.order3=") + Util.oneDP(this.m_test.getOrd3()) + TX("&iptv.std3=") + Util.oneDP(this.m_test.getStd3()) : "");
  }

  public AppletTest getTest()
  {
    return this.m_test;
  }

  public void notifyTestBegin()
  {
    this.m_tab.stopAnimation();
    this.m_tab.setStartButtonVisible(false);
  }

  public void runTest()
  {
    doAppletDelaySelectTab(this.m_tab);
    int i = iniGetInteger(TX("iptv.port1"), -1);
    int j = iniGetInteger(TX("iptv.port2"), -1);
    int k = iniGetInteger(TX("iptv.port3"), -1);
    int m = iniGetInteger(TX("iptv_bytesperpacket1"), 1400);
    int n = iniGetInteger(TX("iptv_framesize1"), 41472);
    int i1 = iniGetInteger(TX("iptv_framespersecond1"), 24);
    int i2 = iniGetInteger(TX("iptv_frames1"), 240);
    int i3 = iniGetInteger(TX("iptv_framesomit1"), 5);
    int i4 = iniGetInteger(TX("iptv_bytesperpacket2"), -1);
    int i5 = iniGetInteger(TX("iptv_framesize2"), -1);
    int i6 = iniGetInteger(TX("iptv_framespersecond2"), -1);
    int i7 = iniGetInteger(TX("iptv_frames2"), -1);
    int i8 = iniGetInteger(TX("iptv_framesomit2"), 5);
    int i9 = iniGetInteger(TX("iptv_bytesperpacket3"), -1);
    int i10 = iniGetInteger(TX("iptv_framesize3"), -1);
    int i11 = iniGetInteger(TX("iptv_framespersecond3"), -1);
    int i12 = iniGetInteger(TX("iptv_frames3"), -1);
    int i13 = iniGetInteger(TX("iptv_framesomit3"), 5);
    if (i != -1)
    {
      this.m_test = new IptvTest(this.m_tab, this, base().getHost(), i, j, k, m, n, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13);
      this.m_test.runTest();
    }
    else
    {
      OUT(TX("Iptv Port1=-1; cannot reach server"));
    }
  }

  public Image getAppletImage(String paramString)
  {
    byte[] arrayOfByte = getAppletData(paramString);
    if ((this.m_tab != null) && (arrayOfByte != null))
      return this.m_tab.createAppletImage(arrayOfByte);
    return null;
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
      System.out.println("IPTV getappletdatafromjar: exception: " + localException1);
      localException1.printStackTrace();
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

  public void setMSSID(String paramString)
  {
    super.setMSSID(paramString);
    this.m_tab.repaint();
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.iptv.iptv
 * JD-Core Version:    0.6.2
 */