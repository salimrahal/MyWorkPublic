package mcsaplugins.speed;

import java.io.OutputStream;
import java.io.PrintStream;
import myspeedserver.applet.U;

public class SocketSendThread
  implements Runnable
{
  private Thread m_thread;
  private boolean m_bRun;
  private boolean m_bHttpPost;
  private OutputStream m_out;
  private long m_lMaxLength;
  private long m_lTotalWritten;
  private long m_lLastWriteTime;
  private int m_nLastWriteBytes;
  private long m_lLastButOneWriteTime;
  private SpeedTest m_parent;

  public SocketSendThread(SpeedTest paramSpeedTest, OutputStream paramOutputStream, long paramLong, boolean paramBoolean)
  {
    this.m_parent = paramSpeedTest;
    this.m_out = paramOutputStream;
    this.m_lMaxLength = paramLong;
    this.m_bHttpPost = paramBoolean;
    this.m_bRun = true;
    this.m_thread = new Thread(this, "SocketSendThread");
    this.m_thread.start();
  }

  public long getTotalWritten()
  {
    return this.m_lTotalWritten;
  }

  public long getLastWriteTime()
  {
    return this.m_lLastWriteTime;
  }

  public long getLastButOneWriteTime()
  {
    return this.m_lLastButOneWriteTime;
  }

  public int getLastWriteBytes()
  {
    return this.m_nLastWriteBytes;
  }

  public boolean isFinished()
  {
    return this.m_thread == null;
  }

  public void stop()
  {
    this.m_bRun = false;
  }

  public void run()
  {
    label189: 
    try
    {
      byte[] arrayOfByte = U.makeRandom(65536);
      do
      {
        try
        {
          this.m_out.write(arrayOfByte, 0, arrayOfByte.length);
          if (this.m_bHttpPost)
            this.m_out.flush();
          this.m_nLastWriteBytes = arrayOfByte.length;
          this.m_lTotalWritten += arrayOfByte.length;
          this.m_lLastButOneWriteTime = this.m_lLastWriteTime;
          this.m_lLastWriteTime = U.time();
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          this.m_parent.OUT("Caught out-of-memory condition (totalwritten=" + this.m_lTotalWritten + "): ");
          System.out.println("Caught out-of-memory condition (totalwritten=" + this.m_lTotalWritten + "): ");
          localOutOfMemoryError.printStackTrace();
          this.m_out.flush();
          break label189;
        }
        if (!this.m_bRun)
          break;
      }
      while (this.m_lTotalWritten < this.m_lMaxLength);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      this.m_thread = null;
    }
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.speed.SocketSendThread
 * JD-Core Version:    0.6.2
 */