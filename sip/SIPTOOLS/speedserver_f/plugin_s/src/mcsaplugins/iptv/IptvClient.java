package mcsaplugins.iptv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import myspeedserver.applet.U;

public class IptvClient
  implements Runnable
{
  IptvTest m_iptvtest;
  String m_ip;
  String m_error;
  int m_port;
  int m_size;
  int m_framesize;
  int m_fps;
  int m_frames;
  int m_packetstotal;
  int m_packetsperframe;
  long m_Session;
  long[][] m_delta;
  long[][] m_now;
  long[][] m_sendtime;
  int[][] m_bytes;
  int[][] m_sequence;
  int[][] m_seqraw;
  long[][] m_session;

  public IptvClient(IptvTest paramIptvTest, long paramLong, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.m_iptvtest = paramIptvTest;
    this.m_Session = paramLong;
    this.m_ip = paramString;
    this.m_port = (paramInt1 == 0 ? 554 : paramInt1);
    this.m_size = (paramInt2 == 0 ? 1400 : paramInt2);
    this.m_framesize = (paramInt3 == 0 ? 14000 : paramInt3);
    this.m_fps = (paramInt4 == 0 ? 24 : paramInt4);
    this.m_frames = Math.min(90 * this.m_fps, paramInt5 == 0 ? 100 : paramInt5);
    this.m_packetsperframe = (this.m_framesize / this.m_size);
    this.m_packetstotal = (this.m_packetsperframe * this.m_frames);
    this.m_delta = new long[this.m_frames][this.m_packetsperframe];
    this.m_now = new long[this.m_frames][this.m_packetsperframe];
    this.m_sendtime = new long[this.m_frames][this.m_packetsperframe];
    this.m_bytes = new int[this.m_frames][this.m_packetsperframe];
    this.m_sequence = new int[this.m_frames][this.m_packetsperframe];
    this.m_seqraw = new int[this.m_frames][this.m_packetsperframe];
    this.m_session = new long[this.m_frames][this.m_packetsperframe];
  }

  public int[][] getBytes()
  {
    return this.m_bytes;
  }

  public long[][] getDelta()
  {
    return this.m_delta;
  }

  public int[][] getSequence()
  {
    return this.m_sequence;
  }

  public int getPacketsTotal()
  {
    return this.m_packetstotal;
  }

  public long getSession()
  {
    return this.m_Session;
  }

  public String getError()
  {
    return this.m_error;
  }

  public void run()
  {
    DatagramSocket localDatagramSocket = null;
    DatagramPacket localDatagramPacket1 = null;
    DatagramPacket localDatagramPacket2 = null;
    long l1 = -1L;
    long l2 = 0L;
    long l3 = 0L;
    for (int j = 0; j < this.m_frames; j++)
      for (int k = 0; k < this.m_packetsperframe; k++)
      {
        this.m_delta[j][k] = -1L;
        this.m_now[j][k] = -1L;
        this.m_sendtime[j][k] = -1L;
        this.m_bytes[j][k] = -1;
        this.m_sequence[j][k] = 1;
        this.m_seqraw[j][k] = 0;
        this.m_session[j][k] = 0L;
      }
    try
    {
      localDatagramSocket = new DatagramSocket();
      U.setSendBufferSize(localDatagramSocket, 65536);
      U.setReceiveBufferSize(localDatagramSocket, 65536);
      this.m_iptvtest.out("socket buffers: snd=" + U.getSendBufferSize(localDatagramSocket) + " rcv=" + U.getReceiveBufferSize(localDatagramSocket));
      InetAddress localInetAddress = InetAddress.getByName(this.m_ip);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(1);
      localDataOutputStream.writeLong(this.m_Session);
      localDataOutputStream.writeInt(this.m_size);
      localDataOutputStream.writeInt(this.m_framesize);
      localDataOutputStream.writeInt(this.m_fps);
      localDataOutputStream.writeInt(this.m_frames);
      byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
      localDatagramPacket1 = new DatagramPacket(arrayOfByte1, arrayOfByte1.length, localInetAddress, this.m_port);
      localDatagramSocket.send(localDatagramPacket1);
      localDatagramSocket.setSoTimeout(5000);
      byte[] arrayOfByte2 = new byte[65535];
      localDatagramPacket2 = new DatagramPacket(arrayOfByte2, arrayOfByte2.length);
      localDatagramSocket.receive(localDatagramPacket2);
      byte[] arrayOfByte3 = localDatagramPacket2.getData();
      int i;
      if (arrayOfByte3.length > 0)
      {
        DataInputStream localDataInputStream1 = new DataInputStream(new ByteArrayInputStream(arrayOfByte3));
        i = localDataInputStream1.readShort();
        localDataInputStream1.close();
      }
      this.m_iptvtest.out("session=" + this.m_Session + " size=" + this.m_size + " framesize=" + this.m_framesize + " fps=" + this.m_fps + " frames=" + this.m_frames + " packetsperframe=" + this.m_packetsperframe + " buffersize=" + localDatagramSocket.getReceiveBufferSize());
      long l4 = U.time();
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      int i3 = 0;
      int i4 = 0;
      while (l3 <= this.m_packetstotal)
      {
        localDatagramSocket.receive(localDatagramPacket2);
        byte[] arrayOfByte4 = new byte[localDatagramPacket2.getLength()];
        System.arraycopy(localDatagramPacket2.getData(), 0, arrayOfByte4, 0, arrayOfByte4.length);
        l1 = -1L;
        if (arrayOfByte4.length > 0)
        {
          DataInputStream localDataInputStream2 = new DataInputStream(new ByteArrayInputStream(arrayOfByte4));
          i = localDataInputStream2.readShort();
          l1 = localDataInputStream2.readLong();
          if (i == 4)
          {
            int i5 = localDataInputStream2.readInt();
            this.m_iptvtest.out("session=" + this.m_Session + " finished, server sent=" + i5);
            localDataInputStream2.close();
            break;
          }
          if (i == 3)
          {
            i1 = localDataInputStream2.readInt();
            n = localDataInputStream2.readInt();
            i3 = localDataInputStream2.readInt();
            l2 = localDataInputStream2.readLong();
            try
            {
              m = i2 = 0;
              while (i2 != -1)
              {
                i2 = localDataInputStream2.read(arrayOfByte4, m, arrayOfByte4.length - m);
                if (i2 != -1)
                  m += i2;
              }
              this.m_now[i1][n] = U.time();
              this.m_sendtime[i1][n] = l2;
              this.m_bytes[i1][n] = localDatagramPacket2.getLength();
              this.m_seqraw[i1][n] = i3;
              this.m_session[i1][n] = l1;
            }
            catch (Exception localException2)
            {
              this.m_iptvtest.out("session=" + this.m_Session + "  inner " + (l1 != -1L ? "session=" + l1 : "") + " exception:" + localException2.getMessage());
            }
            if (i3 < i4)
              this.m_sequence[i1][n] = 0;
            i4 = i3;
          }
          localDataInputStream2.close();
        }
        else
        {
          this.m_iptvtest.out("session=" + this.m_Session + " zero length previous frame=" + i1 + " previous pack=" + n);
        }
        l3 += 1L;
      }
      this.m_iptvtest.out("session=" + this.m_Session + " starttime=" + l4 + " counted=" + l3 + " expected=" + this.m_packetstotal);
      for (m = 0; m < this.m_frames; m++)
        for (n = 0; n < this.m_packetsperframe; n++)
        {
          this.m_delta[m][n] = (this.m_now[m][n] - l4 - this.m_sendtime[m][n]);
          this.m_iptvtest.out("sn=" + this.m_session[m][n] + " f=" + m + " p=" + n + " sqr=" + this.m_seqraw[m][n] + " sq=" + this.m_sequence[m][n] + " b=" + this.m_bytes[m][n] + " d=" + this.m_delta[m][n] + " n=" + this.m_now[m][n] + " st=" + this.m_sendtime[m][n]);
        }
    }
    catch (Exception localException1)
    {
      this.m_iptvtest.out("session=" + this.m_Session + " outer " + (l1 != -1L ? "session=" + l1 : "") + " packets=" + l3 + " exception:" + localException1.toString());
      if ((localException1 instanceof SocketTimeoutException))
        this.m_error = "timeout";
      else
        this.m_error = localException1.toString();
    }
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.iptv.IptvClient
 * JD-Core Version:    0.6.2
 */