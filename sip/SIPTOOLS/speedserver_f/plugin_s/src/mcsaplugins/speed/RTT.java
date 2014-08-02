package mcsaplugins.speed;

import java.net.InetAddress;
import java.net.Socket;
import myspeedserver.applet.U;

public class RTT
  implements Runnable
{
  private boolean m_bRunning;
  private speed m_plugin;
  private String m_targetAddress;
  private int m_port;
  private int m_nTests;
  private long m_tick;
  private int[] m_nRtts;

  private int doRTT(InetAddress paramInetAddress, int paramInt)
  {
    Socket localSocket = null;
    try
    {
      this.m_tick = U.time();
      long l1 = U.syncTime();
      localSocket = new Socket(paramInetAddress, paramInt);
      long l2 = U.endTime();
      this.m_tick = U.time();
      int i = Math.max(1, (int)(l2 - l1));
      return i;
    }
    catch (Throwable localThrowable1)
    {
    }
    finally
    {
      try
      {
        localSocket.close();
      }
      catch (Throwable localThrowable4)
      {
      }
    }
    return -1;
  }

  public void addTests(speed paramspeed, String paramString, int paramInt1, int paramInt2)
  {
    this.m_bRunning = true;
    this.m_plugin = paramspeed;
    this.m_targetAddress = paramString;
    this.m_port = paramInt1;
    this.m_nTests = paramInt2;
    Thread localThread = new Thread(this, TX("MySpeed-RTT"));
    localThread.start();
    this.m_tick = U.time();
    while ((localThread.isAlive()) && (U.time() - this.m_tick < 8000L))
      try
      {
        localThread.join(1000L);
      }
      catch (Throwable localThrowable)
      {
      }
    this.m_bRunning = false;
  }

  public int[] getRtts()
  {
    return this.m_nRtts;
  }

  public int getMinRtt()
  {
    int i = -1;
    for (int j = 0; (this.m_nRtts != null) && (j < this.m_nRtts.length); j++)
      i = (this.m_nRtts[j] < i) || (i == -1) ? this.m_nRtts[j] : i;
    return i;
  }

  public int getMaxRtt()
  {
    int i = -1;
    for (int j = 0; (this.m_nRtts != null) && (j < this.m_nRtts.length); j++)
      i = Math.max(this.m_nRtts[j], i);
    return i;
  }

  public int getAvgRtt()
  {
    int i = 0;
    int j = 0;
    for (int k = 0; (this.m_nRtts != null) && (k < this.m_nRtts.length); k++)
      if (this.m_nRtts[k] >= 0)
      {
        i += this.m_nRtts[k];
        j++;
      }
    return j > 0 ? i / j : -1;
  }

  private void OUT(String paramString)
  {
    if ((this.m_bRunning) && (this.m_plugin != null))
      this.m_plugin.OUT(paramString);
  }

  public void run()
  {
    this.m_tick = U.time();
    InetAddress localInetAddress = null;
    try
    {
      localInetAddress = InetAddress.getByName(InetAddress.getByName(this.m_targetAddress).getHostAddress());
    }
    catch (Throwable localThrowable)
    {
    }
    if (localInetAddress != null)
    {
      this.m_tick = U.time();
      int i = this.m_port;
      i = i <= 0 ? 80 : i;
      doRTT(localInetAddress, i);
      Object localObject = this.m_nTests > 0 ? new int[this.m_nTests] : null;
      for (int j = 0; (this.m_bRunning) && (j < this.m_nTests); j++)
      {
        localObject[j] = doRTT(localInetAddress, i);
        OUT(TX("RTT #") + j + TX(": ") + localObject[j] + TX(" ms"));
      }
      int[] arrayOfInt = new int[localObject.length + (this.m_nRtts == null ? 0 : this.m_nRtts.length)];
      if (this.m_nRtts != null)
        System.arraycopy(this.m_nRtts, 0, arrayOfInt, 0, this.m_nRtts.length);
      System.arraycopy(localObject, 0, arrayOfInt, arrayOfInt.length - localObject.length, localObject.length);
      this.m_nRtts = arrayOfInt;
      OUT(TX("RTT(min)=") + getMinRtt() + TX(", RTT(avg)=") + getAvgRtt() + TX(" (") + localInetAddress + TX(":") + i + TX(")"));
    }
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.speed.RTT
 * JD-Core Version:    0.6.2
 */