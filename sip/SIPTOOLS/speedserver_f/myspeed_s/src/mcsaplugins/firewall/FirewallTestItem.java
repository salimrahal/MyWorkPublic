package mcsaplugins.firewall;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.U;

public class FirewallTestItem
{
  private FirewallTest m_firewall;
  private String m_ip;
  private String m_alias;
  private int m_lowPort;
  private int m_highPort;
  private boolean m_bTCP;
  private int m_nTests;
  private int[][] m_nResponses;
  private int m_nTestsPerformed;
  private ErrorCode m_error;
  private boolean m_bRun;
  private int m_nPercentDone;
  private long m_threadtimeout;

  public FirewallTestItem(FirewallTest paramFirewallTest, String paramString1, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, String paramString2, long paramLong)
  {
    this.m_firewall = paramFirewallTest;
    this.m_ip = paramString1;
    this.m_lowPort = paramInt1;
    this.m_highPort = paramInt2;
    this.m_bTCP = paramBoolean;
    this.m_nTests = paramInt3;
    this.m_alias = paramString2;
    this.m_threadtimeout = paramLong;
  }

  public Object[] getSummaryItem(String paramString)
  {
    return null;
  }

  public String[] getAdvancedDataItem(String paramString)
  {
    return null;
  }

  public int[][] getResponseTimes()
  {
    return this.m_nResponses;
  }

  public int getNumTestsPerformed()
  {
    return this.m_nTestsPerformed;
  }

  public String getAlias()
  {
    return this.m_alias;
  }

  public int getHighPort()
  {
    return this.m_highPort;
  }

  public int getLowPort()
  {
    return this.m_lowPort;
  }

  public String getProtocol()
  {
    return this.m_bTCP ? TX("TCP") : TX("UDP");
  }

  public float getAvgResponseTime()
  {
    int i = 0;
    int j = 0;
    for (int k = 0; (this.m_nResponses != null) && (k < this.m_nResponses.length); k++)
      for (int m = 0; (this.m_nResponses[k] != null) && (m < this.m_nResponses[k].length); m++)
        if (this.m_nResponses[k][m] >= 0)
        {
          i += this.m_nResponses[k][m];
          j++;
        }
    return j > 0 ? i * 10 / j / 10.0F : -1.0F;
  }

  public int getMaxResponseTime()
  {
    int i = -1;
    for (int j = 0; (this.m_nResponses != null) && (j < this.m_nResponses.length); j++)
      for (int k = 0; (this.m_nResponses[j] != null) && (k < this.m_nResponses[j].length); k++)
        i = Math.max(this.m_nResponses[j][k], i);
    return i;
  }

  public int getMinResponseTime()
  {
    int i = -1;
    for (int j = 0; (this.m_nResponses != null) && (j < this.m_nResponses.length); j++)
      for (int k = 0; (this.m_nResponses[j] != null) && (k < this.m_nResponses[j].length); k++)
        i = (i == -1) || ((this.m_nResponses[j][k] >= 0) && (this.m_nResponses[j][k] < i)) ? this.m_nResponses[j][k] : i;
    return i;
  }

  public boolean isRunning()
  {
    return this.m_bRun;
  }

  public void runTest()
  {
    this.m_bRun = true;
    int i = 0;
    int j = this.m_nTests * (Math.abs(this.m_highPort - this.m_lowPort) + 1);
    try
    {
      for (i = this.m_lowPort; i <= this.m_highPort; i++)
      {
        OUT(TX("Firewall (") + this.m_ip + TX(":") + i + TX(" ") + (this.m_bTCP ? TX("TCP") : TX("UDP")) + TX("): opening port..."));
        if (doOpenPort(getBase(), i, this.m_threadtimeout))
        {
          InetAddress localInetAddress = InetAddress.getByName(this.m_ip);
          if (this.m_nResponses == null)
            this.m_nResponses = new int[this.m_highPort - this.m_lowPort + 1][this.m_nTests];
          this.m_nResponses[(i - this.m_lowPort)] = (this.m_nTests > 0 ? new int[this.m_nTests] : new int[0]);
          for (int k = 0; (this.m_bRun) && (k < this.m_nTests); k++)
          {
            int m;
            if (this.m_bTCP)
              m = doTCPTest(localInetAddress, i);
            else
              m = doUDPTest(localInetAddress, i);
            OUT(TX("Firewall (") + this.m_ip + TX(":") + i + TX(" ") + TX(this.m_bTCP ? "TCP" : "UDP") + TX("): #") + (k + 1) + TX(" = ") + m + TX(" ms"));
            this.m_nResponses[(i - this.m_lowPort)][(this.m_nTestsPerformed++ % this.m_nTests)] = m;
            this.m_firewall.updateGUI();
            this.m_nPercentDone = (this.m_nTestsPerformed * 100 / j);
          }
        }
      }
      this.m_nPercentDone = 100;
    }
    catch (Exception localException)
    {
      OUT(TX("Error running firewall test: ") + this.m_ip + TX(":") + i + TX(": ") + localException);
      localException.printStackTrace();
      this.m_firewall.updateGUI();
      this.m_nPercentDone = 100;
    }
    finally
    {
      this.m_nPercentDone = 100;
      this.m_firewall.updateGUI();
    }
  }

  private boolean doOpenPort(URL paramURL, int paramInt, long paramLong)
  {
    BufferedReader localBufferedReader = null;
    try
    {
      String str1 = this.m_ip != null ? TX("&ip=") + this.m_ip : "";
      URL localURL = new URL(paramURL, TX("/myspeed/plugin/firewall/openport?port=") + paramInt + TX("&threadtimeout=") + paramLong + TX("&prot=") + TX(this.m_bTCP ? "tcp" : "udp") + str1);
      URLConnection localURLConnection = localURL.openConnection();
      localURLConnection.setDoInput(true);
      localBufferedReader = new BufferedReader(new InputStreamReader(localURL.openStream()));
      String str2 = null;
      String str3 = null;
      String str4;
      while ((str4 = localBufferedReader.readLine()) != null)
        if (str4.startsWith("status="))
          str2 = str4.substring(7);
        else if (str4.startsWith("error="))
          str3 = str4.substring(6);
      if ((str2 != null) && (str2.toLowerCase().equals("ok")))
        return true;
      str3 = str3 == null ? "no detail" : str3;
      setError(1, "The server socket could not be opened to check the port (" + str3 + ")");
      return false;
    }
    catch (Exception localException1)
    {
      OUT(TX("Error opening port: ") + localException1);
      localException1.printStackTrace();
      setError(1, "The server socket could not be opened to check the port (" + localException1 + ")");
      return false;
    }
    finally
    {
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException5)
      {
      }
    }
  }

  private void setError(int paramInt, String paramString)
  {
    this.m_error = new ErrorCode(0xC0000 | paramInt & 0xFFFF, paramString);
    this.m_error.addDetail("ip", this.m_ip);
    this.m_error.addDetail("lowport", this.m_lowPort);
    this.m_error.addDetail("highport", this.m_highPort);
    this.m_error.addDetail("prot", this.m_bTCP ? "tcp" : "udp");
    this.m_firewall.updateGUI();
    OUT("ERROR: " + paramString);
  }

  private int doTCPTest(InetAddress paramInetAddress, int paramInt)
  {
    Socket localSocket = null;
    InputStream localInputStream = null;
    OutputStream localOutputStream = null;
    try
    {
      long l = U.time();
      localSocket = new Socket(paramInetAddress, paramInt);
      localSocket.setSoTimeout(3000);
      localSocket.setSoLinger(true, 2);
      int i = (int)(U.time() - l);
      localInputStream = localSocket.getInputStream();
      localOutputStream = localSocket.getOutputStream();
      int j = i;
      return j;
    }
    catch (Exception localException1)
    {
      OUT(TX("Error performing firewall test (") + paramInetAddress.getHostAddress() + TX(":") + paramInt + TX(" TCP): ") + localException1);
      localException1.printStackTrace();
      return -1;
    }
    finally
    {
      try
      {
        localInputStream.close();
      }
      catch (Exception localException8)
      {
      }
      try
      {
        localOutputStream.close();
      }
      catch (Exception localException9)
      {
      }
      try
      {
        localSocket.close();
      }
      catch (Exception localException10)
      {
      }
    }
  }

  private int doUDPTest(InetAddress paramInetAddress, int paramInt)
  {
    DatagramSocket localDatagramSocket = null;
    try
    {
      localDatagramSocket = new DatagramSocket();
      localDatagramSocket.setSoTimeout(8000);
      DatagramPacket localDatagramPacket = new DatagramPacket("test".getBytes(), 4, paramInetAddress, paramInt);
      long l = U.time();
      localDatagramSocket.send(localDatagramPacket);
      localDatagramPacket = new DatagramPacket(new byte[65536], 65536);
      localDatagramSocket.receive(localDatagramPacket);
      int i = (int)(U.time() - l);
      return i;
    }
    catch (Exception localException1)
    {
      OUT("Error performing firewall test (" + paramInetAddress.getHostAddress() + ":" + paramInt + " UDP): " + localException1);
      localException1.printStackTrace();
      return -1;
    }
    finally
    {
      try
      {
        localDatagramSocket.close();
      }
      catch (Exception localException4)
      {
      }
    }
  }

  public int getPercentComplete()
  {
    return this.m_nPercentDone;
  }

  public URL getBase()
  {
    return this.m_firewall.getBase();
  }

  private void OUT(String paramString)
  {
    this.m_firewall.OUT(paramString);
  }

  public ErrorCode getErrorCode()
  {
    return this.m_error;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.firewall.FirewallTestItem
 * JD-Core Version:    0.6.2
 */