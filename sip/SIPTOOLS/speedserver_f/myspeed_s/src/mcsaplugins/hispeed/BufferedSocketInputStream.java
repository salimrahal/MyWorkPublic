package mcsaplugins.hispeed;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class BufferedSocketInputStream extends InputStream
  implements Runnable
{
  private Thread m_thread;
  private int m_nAvailable;
  private InputStream[] m_ins;
  private int m_nInsOff;
  private int m_nInsLen;
  private int m_nPrio;
  private boolean m_bRun;

  public BufferedSocketInputStream(InputStream[] paramArrayOfInputStream, int paramInt1, int paramInt2, int paramInt3)
  {
    this.m_ins = paramArrayOfInputStream;
    this.m_nInsOff = paramInt1;
    this.m_nInsLen = paramInt2;
    this.m_nPrio = paramInt3;
    start();
  }

  private void start()
  {
    if (this.m_thread == null)
    {
      this.m_bRun = true;
      this.m_thread = new Thread(this, "BufferedSocketInputStream");
      this.m_thread.setPriority(this.m_nPrio);
      this.m_thread.start();
    }
  }

  public int available()
  {
    return this.m_nAvailable;
  }

  public int read()
  {
    this.m_nAvailable -= 1;
    return 0;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    for (int j = 0; (this.m_nAvailable > 0) && (j < paramInt2); j++)
    {
      paramArrayOfByte[(paramInt1 + j)] = 0;
      this.m_nAvailable -= 1;
      i++;
    }
    return i;
  }

  public void close()
  {
    this.m_bRun = false;
    try
    {
      this.m_thread.interrupt();
    }
    catch (Exception localException1)
    {
    }
    Object localObject = null;
    for (int i = this.m_nInsOff; i < this.m_nInsOff + this.m_nInsLen; i++)
      try
      {
        this.m_ins[i].close();
      }
      catch (Exception localException2)
      {
        localObject = localException2;
      }
    if ((localObject != null) && ((localObject instanceof IOException)))
      throw ((IOException)localObject);
  }

  public int getNumSockets()
  {
    return this.m_nInsLen;
  }

  public void run()
  {
    System.out.println("Beginning thread " + hashCode() + " (prio=" + this.m_thread.getPriority() + ")");
    byte[] arrayOfByte = new byte[131072];
    try
    {
      while (this.m_bRun)
      {
        int i = 0;
        while (this.m_bRun)
        {
          int j = this.m_ins.length == 1 ? 0 : this.m_ins[(i + this.m_nInsOff)].available();
          if ((j > 0) || (this.m_ins.length == 1))
          {
            int k = HiSpeedTest.doReadSomethingFully(this.m_ins[(i + this.m_nInsOff)], arrayOfByte);
            this.m_nAvailable += k;
            break;
          }
          i = (i + 1) % this.m_nInsLen;
          if (i == 0)
            try
            {
              Thread.sleep(1L);
            }
            catch (Exception localException2)
            {
            }
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      this.m_thread = null;
      System.out.println("Thread " + hashCode() + " finished");
    }
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.hispeed.BufferedSocketInputStream
 * JD-Core Version:    0.6.2
 */