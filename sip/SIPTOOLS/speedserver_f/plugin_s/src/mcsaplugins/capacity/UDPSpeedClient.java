package mcsaplugins.capacity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

public class UDPSpeedClient
  implements Runnable
{
  public static final int TYPE_DOWNSTREAM = 1;
  public static final int TYPE_UPSTREAM_BASIC = 2;
  public static final int TYPE_UPSTREAM_FULL = 3;
  private Thread m_thread;
  private boolean m_bRunThread;
  private DatagramSocket m_socket;
  private String m_ip;
  private InetAddress m_ipAddr;
  private int m_nPort;
  private boolean m_bUseUDP;
  private URL m_baseURL;
  private long m_lSID;
  private byte[] m_token;
  private long m_lUpstreamToken;
  private long m_lMaxBandwidth;
  private long m_lMaxTransferBytes;
  private int m_nMaxPacketsPerSec;
  private int m_nPacketSize;
  private int m_nPacketsPerSec;
  private int m_nPackets;
  private int m_nType;
  private long[] m_lTimes;
  private int[] m_nBytes;
  private float m_fLoss;
  private long m_lStartTime;
  private long m_lUpstreamElapsed;
  private long m_lUpstreamBytes;
  private DatagramPacket m_inPacket;
  private int m_nTimePos;
  private int m_nServerErr = -1;
  private CapacityTest m_captst;
  private long m_lWaitUntilMs = 0L;
  private long m_lOverRunMs = 0L;

  public UDPSpeedClient(String paramString, int paramInt, URL paramURL, CapacityTest paramCapacityTest)
  {
    this.m_ip = paramString;
    this.m_nPort = paramInt;
    this.m_baseURL = paramURL;
    this.m_lSID = generateSID();
    this.m_captst = paramCapacityTest;
  }

  public UDPSpeedClient(String paramString, int paramInt1, URL paramURL, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong, CapacityTest paramCapacityTest)
  {
    this(paramString, paramInt1, paramURL, paramCapacityTest);
    setParameters(paramInt2, paramInt3, paramInt4, paramInt5, paramLong);
  }

  public void setParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
  {
    this.m_nPacketSize = paramInt1;
    this.m_nPacketsPerSec = paramInt2;
    this.m_nPackets = paramInt3;
    this.m_nType = paramInt4;
    this.m_lOverRunMs = paramLong;
  }

  public long getMaxMeasurableBandwidth()
  {
    return this.m_lMaxBandwidth;
  }

  public long getMaxTransferBytes()
  {
    return this.m_lMaxTransferBytes;
  }

  public int getMaxPacketsPerSec()
  {
    return this.m_nMaxPacketsPerSec;
  }

  public long getWaitUntil()
  {
    return this.m_lWaitUntilMs;
  }

  private static long generateSID()
  {
    int i = (int)(System.currentTimeMillis() % 3600000L);
    long l = 0L;
    for (int j = 0; j < 8; j++)
    {
      l <<= 8;
      l |= (int)(Math.random() * 255.0D) ^ i & 0xFF;
      i >>= 4;
    }
    return l;
  }

  public boolean connect()
  {
    DataOutputStream localDataOutputStream = null;
    Object localObject1 = null;
    DatagramSocket localDatagramSocket = null;
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(516);
      localDataOutputStream.writeLong(this.m_lSID);
      this.m_ipAddr = InetAddress.getByName(this.m_ip);
      DatagramPacket localDatagramPacket1 = new DatagramPacket(localByteArrayOutputStream.toByteArray(), localByteArrayOutputStream.size(), this.m_ipAddr, this.m_nPort);
      localDatagramSocket = new DatagramSocket();
      DatagramPacket localDatagramPacket2 = new DatagramPacket(new byte[1024], 1024);
      for (int i = 0; i < 3; i++)
        try
        {
          localDatagramSocket.setSoTimeout((i + 1) * 1000);
          localDatagramSocket.send(localDatagramPacket1);
          localDatagramSocket.receive(localDatagramPacket2);
          localObject1 = localDatagramPacket2.getLength() >= 50 ? new DataInputStream(new ByteArrayInputStream(localDatagramPacket2.getData())) : null;
          if ((localObject1 != null) && (localObject1.readShort() == 517) && (localObject1.readLong() == this.m_lSID))
            break;
          try
          {
            localObject1.close();
          }
          catch (Exception localException2)
          {
          }
          localObject1 = null;
        }
        catch (Exception localException3)
        {
        }
      if (localObject1 != null)
      {
        byte[] arrayOfByte = new byte[20];
        localObject1.read(arrayOfByte, 0, 20);
        this.m_lMaxTransferBytes = localObject1.readLong();
        this.m_lMaxBandwidth = localObject1.readLong();
        this.m_nMaxPacketsPerSec = localObject1.readInt();
        this.m_token = arrayOfByte;
        return true;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localDatagramSocket.close();
      }
      catch (Exception localException10)
      {
      }
      try
      {
        localDataOutputStream.close();
      }
      catch (Exception localException11)
      {
      }
      try
      {
        localObject1.close();
      }
      catch (Exception localException12)
      {
      }
    }
    try
    {
      localDatagramSocket.close();
    }
    catch (Exception localException13)
    {
    }
    try
    {
      localDataOutputStream.close();
    }
    catch (Exception localException14)
    {
    }
    try
    {
      localObject1.close();
    }
    catch (Exception localException15)
    {
    }
    return false;
  }

  public boolean isConnected()
  {
    return this.m_token != null;
  }

  public void start()
  {
    stop();
    this.m_lTimes = null;
    this.m_nBytes = null;
    this.m_fLoss = 0.0F;
    this.m_lStartTime = 0L;
    this.m_nTimePos = 0;
    this.m_nServerErr = -1;
    this.m_lWaitUntilMs = (System.currentTimeMillis() + this.m_lOverRunMs);
    this.m_bRunThread = true;
    this.m_thread = new Thread(this);
    this.m_thread.setPriority(10);
    this.m_thread.start();
  }

  public void stop()
  {
    this.m_bRunThread = false;
    try
    {
      this.m_socket.close();
    }
    catch (Exception localException1)
    {
    }
    try
    {
      this.m_thread.interrupt();
    }
    catch (Exception localException2)
    {
    }
    try
    {
      this.m_thread.join();
    }
    catch (Exception localException3)
    {
    }
  }

  public long[] getTimes()
  {
    if ((this.m_lTimes == null) || (this.m_lTimes.length == this.m_nTimePos + 1))
      return this.m_lTimes;
    long[] arrayOfLong = new long[Math.min(this.m_lTimes.length, this.m_nTimePos + 1)];
    System.arraycopy(this.m_lTimes, 0, arrayOfLong, 0, arrayOfLong.length);
    if (!isRunning())
      this.m_lTimes = arrayOfLong;
    return arrayOfLong;
  }

  public int[] getBytes()
  {
    if ((this.m_nBytes == null) || (this.m_nBytes.length == this.m_nTimePos + 1))
      return this.m_nBytes;
    int[] arrayOfInt = new int[Math.min(this.m_lTimes.length, this.m_nTimePos + 1)];
    System.arraycopy(this.m_nBytes, 0, arrayOfInt, 0, arrayOfInt.length);
    if (!isRunning())
      this.m_nBytes = arrayOfInt;
    return arrayOfInt;
  }

  public float getLoss()
  {
    return this.m_fLoss;
  }

  public long getUpstreamElapsed()
  {
    return this.m_lUpstreamElapsed;
  }

  public long getUpstreamBytes()
  {
    return this.m_lUpstreamBytes;
  }

  public int getServerError()
  {
    return this.m_nServerErr;
  }

  public boolean isRunning()
  {
    return this.m_thread != null;
  }

  public void run()
  {
    if (this.m_token != null)
      try
      {
        this.m_socket = new DatagramSocket();
        this.m_socket.setReceiveBufferSize(131072);
        this.m_socket.setSendBufferSize(131072);
        if (this.m_nType == 1)
          runDownstreamTest();
        else
          runUpstreamTest();
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
      finally
      {
        try
        {
          this.m_socket.close();
        }
        catch (Exception localException3)
        {
        }
        this.m_thread = null;
      }
  }

  private void runUpstreamTest()
  {
    this.m_fLoss = 100.0F;
    this.m_lWaitUntilMs = (System.currentTimeMillis() + this.m_lOverRunMs);
    if (sendBeginUpstreamPacket())
    {
      this.m_lWaitUntilMs = (System.currentTimeMillis() + this.m_lOverRunMs);
      sendUpstreamPackets();
      this.m_lWaitUntilMs = this.m_lOverRunMs;
      downloadUpstreamResults();
    }
  }

  private boolean sendBeginUpstreamPacket()
  {
    DataOutputStream localDataOutputStream = null;
    byte[] arrayOfByte = null;
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(518);
      localDataOutputStream.writeLong(this.m_lSID);
      localDataOutputStream.write(this.m_token);
      localDataOutputStream.writeInt(this.m_nPacketSize);
      localDataOutputStream.writeShort(this.m_nPacketsPerSec);
      localDataOutputStream.writeInt(this.m_nPackets);
      localDataOutputStream.writeByte(this.m_nType == 2 ? 0 : 1);
      arrayOfByte = localByteArrayOutputStream.toByteArray();
    }
    finally
    {
      try
      {
        localDataOutputStream.close();
      }
      catch (Exception localException1)
      {
      }
    }
    for (int i = 0; (arrayOfByte != null) && (i < 3); i++)
    {
      DatagramPacket localDatagramPacket1 = new DatagramPacket(arrayOfByte, arrayOfByte.length, this.m_ipAddr, this.m_nPort);
      this.m_socket.send(localDatagramPacket1);
      this.m_socket.setSoTimeout(4000);
      DatagramPacket localDatagramPacket2 = new DatagramPacket(new byte[1024], 1024);
      DataInputStream localDataInputStream = null;
      try
      {
        int j;
        do
        {
          this.m_socket.receive(localDatagramPacket2);
          this.m_inPacket = localDatagramPacket2;
          localDataInputStream = new DataInputStream(new ByteArrayInputStream(localDatagramPacket2.getData()));
          j = localDataInputStream.readShort();
          localDataInputStream.readLong();
          if (j == 515)
          {
            this.m_nServerErr = localDataInputStream.readInt();
            OUT("server error: " + this.m_nServerErr);
            return false;
          }
        }
        while (j != 519);
        this.m_lUpstreamToken = localDataInputStream.readLong();
        return true;
      }
      catch (Exception localException3)
      {
        OUT("ERROR Sending 'begin upstream' packet");
        localException3.printStackTrace();
      }
      finally
      {
        try
        {
          localDataInputStream.close();
        }
        catch (Exception localException7)
        {
        }
      }
    }
    return false;
  }

  private void sendUpstreamPackets()
  {
    DataOutputStream localDataOutputStream = null;
    byte[] arrayOfByte1 = new byte[131072];
    int i = 0;
    long l1 = System.currentTimeMillis();
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(520);
      localDataOutputStream.writeLong(this.m_lSID);
      localDataOutputStream.write(this.m_token);
      localDataOutputStream.writeLong(this.m_lUpstreamToken);
      int j = 38;
      int k = this.m_nPacketSize - j;
      if ((k > 0) && (arrayOfByte1.length - i - 1 < k))
      {
        int m = arrayOfByte1.length - i - 1;
        localDataOutputStream.write(arrayOfByte1, i, m);
        localDataOutputStream.write(arrayOfByte1, 0, k - m);
      }
      else if (k >= 0)
      {
        localDataOutputStream.write(arrayOfByte1, i, k);
      }
      i = (i + k) % arrayOfByte1.length;
      byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
      int n = 0;
      long l2 = 0L;
      long l3 = System.nanoTime();
      long l4 = 1000000000 / this.m_nPacketsPerSec;
      while (n < this.m_nPackets)
      {
        l2 = n * l4 - (System.nanoTime() - l3);
        if (l2 > 0L)
          try
          {
            sleepNanos(l2);
          }
          catch (Exception localException1)
          {
          }
        sendUpstreamPacket(arrayOfByte2);
        n++;
      }
      OUT("finished sending " + n + " of " + this.m_nPackets + " upstream packets in " + (System.currentTimeMillis() - l1) + " ms" + (this.m_bRunThread ? "" : " thread terminate"));
    }
    finally
    {
      try
      {
        localDataOutputStream.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }

  private static void sleepNanos(long paramLong)
  {
    long l = System.nanoTime() + paramLong;
    int i = (int)(l / 10L);
    do
      try
      {
        Thread.sleep(0L, i);
      }
      catch (Exception localException)
      {
      }
    while (System.nanoTime() < l);
  }

  private boolean downloadUpstreamResults()
  {
    if (this.m_bUseUDP)
      return udpDownloadUpstreamResults();
    long[] arrayOfLong = null;
    int[] arrayOfInt = null;
    long l1 = 0L;
    long l2 = 0L;
    int i = 0;
    DataInputStream localDataInputStream = null;
    try
    {
      URL localURL = new URL(this.m_baseURL, "/myspeed/plugin/capacity/getupstreamresult?key=" + this.m_lUpstreamToken);
      URLConnection localURLConnection = localURL.openConnection();
      localDataInputStream = new DataInputStream(localURL.openStream());
      l1 = localDataInputStream.readLong();
      l2 = localDataInputStream.readLong();
      i = localDataInputStream.readShort();
      localDataInputStream.readByte();
      localDataInputStream.readByte();
      int j = localDataInputStream.readInt();
      int k = localDataInputStream.readInt();
      int m = localURLConnection.getContentLength() - 34;
      if ((arrayOfLong == null) && (j > 0))
      {
        arrayOfLong = new long[j];
        arrayOfInt = new int[j];
      }
      for (int n = 0; (arrayOfLong != null) && (k < arrayOfLong.length) && (n < m); n += 8)
      {
        arrayOfLong[k] = localDataInputStream.readInt();
        arrayOfInt[k] = localDataInputStream.readInt();
        k++;
      }
      this.m_lUpstreamElapsed = l1;
      this.m_lUpstreamBytes = l2;
      this.m_lTimes = arrayOfLong;
      this.m_nBytes = arrayOfInt;
      this.m_nTimePos = (this.m_lTimes == null ? 0 : this.m_lTimes.length - 1);
      this.m_fLoss = (i / 100.0F);
      return true;
    }
    catch (Exception localException1)
    {
      if (!(localException1 instanceof InterruptedException))
        localException1.printStackTrace();
      this.m_bUseUDP = true;
      boolean bool = udpDownloadUpstreamResults();
      return bool;
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
  }

  private boolean udpDownloadUpstreamResults()
  {
    int i = 1;
    long[] arrayOfLong = null;
    int[] arrayOfInt = null;
    long l1 = 0L;
    long l2 = 0L;
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      this.m_lWaitUntilMs = (System.currentTimeMillis() + this.m_lOverRunMs);
      DataOutputStream localDataOutputStream = null;
      byte[] arrayOfByte = null;
      try
      {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
        localDataOutputStream.writeShort(521);
        localDataOutputStream.writeLong(this.m_lSID);
        localDataOutputStream.write(this.m_token);
        localDataOutputStream.writeLong(this.m_lUpstreamToken);
        localDataOutputStream.writeByte(k);
        arrayOfByte = localByteArrayOutputStream.toByteArray();
      }
      finally
      {
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException1)
        {
        }
      }
      int m = 0;
      break label459;
      DatagramPacket localDatagramPacket1 = new DatagramPacket(arrayOfByte, arrayOfByte.length, this.m_ipAddr, this.m_nPort);
      this.m_socket.send(localDatagramPacket1);
      this.m_socket.setSoTimeout(4000);
      DatagramPacket localDatagramPacket2 = new DatagramPacket(new byte[16384], 16384);
      DataInputStream localDataInputStream = null;
      label459: 
      try
      {
        int n;
        do
        {
          this.m_socket.receive(localDatagramPacket2);
          localDataInputStream = new DataInputStream(new ByteArrayInputStream(localDatagramPacket2.getData()));
          n = localDataInputStream.readShort();
          localDataInputStream.readLong();
          if (n == 515)
          {
            this.m_nServerErr = localDataInputStream.readInt();
            return false;
          }
        }
        while (n != 522);
        l1 = localDataInputStream.readLong();
        l2 = localDataInputStream.readLong();
        j = localDataInputStream.readShort();
        localDataInputStream.readByte();
        i = localDataInputStream.readByte();
        int i1 = localDataInputStream.readInt();
        int i2 = localDataInputStream.readInt();
        int i3 = localDatagramPacket2.getLength() - 34;
        if ((arrayOfLong == null) && (i1 > 0))
        {
          arrayOfLong = new long[i1];
          arrayOfInt = new int[i1];
        }
        for (int i4 = 0; (arrayOfLong != null) && (i2 < arrayOfLong.length) && (i4 < i3); i4 += 8)
        {
          arrayOfLong[i2] = localDataInputStream.readInt();
          arrayOfInt[i2] = localDataInputStream.readInt();
          i2++;
        }
        try
        {
          localDataInputStream.close();
        }
        catch (Exception localException5)
        {
        }
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
        try
        {
          localDataInputStream.close();
        }
        catch (Exception localException6)
        {
        }
      }
      finally
      {
        try
        {
          localDataInputStream.close();
        }
        catch (Exception localException7)
        {
        }
      }
    }
    this.m_lUpstreamElapsed = l1;
    this.m_lUpstreamBytes = l2;
    this.m_lTimes = arrayOfLong;
    this.m_nBytes = arrayOfInt;
    this.m_nTimePos = (this.m_lTimes == null ? 0 : this.m_lTimes.length - 1);
    this.m_fLoss = (j / 100.0F);
    return true;
  }

  private void sendUpstreamPacket(byte[] paramArrayOfByte)
  {
    if (this.m_inPacket != null)
    {
      DatagramPacket localDatagramPacket = new DatagramPacket(paramArrayOfByte, paramArrayOfByte.length, this.m_inPacket.getAddress(), this.m_inPacket.getPort());
      try
      {
        this.m_socket.send(localDatagramPacket);
      }
      catch (Exception localException)
      {
      }
    }
  }

  private void runDownstreamTest()
  {
    int i = 0;
    try
    {
      if ((sendRequestPacket()) && (this.m_bRunThread))
      {
        byte[] arrayOfByte = new byte[65535];
        DatagramPacket localDatagramPacket = new DatagramPacket(arrayOfByte, 65535);
        this.m_lTimes = new long[this.m_nPackets];
        this.m_nBytes = new int[this.m_nPackets];
        do
        {
          this.m_socket.receive(localDatagramPacket);
          if (receiveDownstreamPacket(System.currentTimeMillis(), localDatagramPacket))
            i++;
          if (!this.m_bRunThread)
            break;
        }
        while (i < this.m_nPackets);
      }
    }
    finally
    {
      this.m_fLoss = ((this.m_nPackets - i) * 100.0F / this.m_nPackets);
    }
  }

  private boolean receiveDownstreamPacket(long paramLong, DatagramPacket paramDatagramPacket)
  {
    DataInputStream localDataInputStream = null;
    try
    {
      localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramDatagramPacket.getData()));
      int i = localDataInputStream.readShort();
      long l = localDataInputStream.readLong();
      if ((l == this.m_lSID) && (i == 515))
      {
        this.m_nServerErr = localDataInputStream.readInt();
        throw new IOException();
      }
      if ((l == this.m_lSID) && (i == 514))
      {
        if (this.m_lStartTime == 0L)
          this.m_lStartTime = paramLong;
        int j = (int)(paramLong - this.m_lStartTime);
        if (this.m_lTimes[this.m_nTimePos] != j)
          this.m_nTimePos += 1;
        if (this.m_nTimePos < this.m_lTimes.length)
        {
          this.m_lTimes[this.m_nTimePos] = j;
          this.m_nBytes[this.m_nTimePos] += paramDatagramPacket.getLength();
        }
        return true;
      }
      return false;
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException3)
      {
      }
    }
  }

  private boolean sendRequestPacket()
  {
    ByteArrayOutputStream localByteArrayOutputStream = null;
    try
    {
      localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(513);
      localDataOutputStream.writeLong(this.m_lSID);
      localDataOutputStream.write(this.m_token);
      localDataOutputStream.writeInt(this.m_nPacketSize);
      localDataOutputStream.writeShort(this.m_nPacketsPerSec);
      localDataOutputStream.writeInt(this.m_nPackets);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      DatagramPacket localDatagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, this.m_ipAddr, this.m_nPort);
      this.m_socket.send(localDatagramPacket);
      return true;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      return false;
    }
    finally
    {
      try
      {
        localByteArrayOutputStream.close();
      }
      catch (Exception localException4)
      {
      }
    }
  }

  private void OUT(String paramString)
  {
    this.m_captst.OUT(paramString);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.capacity.UDPSpeedClient
 * JD-Core Version:    0.6.2
 */