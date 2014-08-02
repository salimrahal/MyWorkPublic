package mcsaplugins.hispeed;

import java.io.OutputStream;
import myspeedserver.applet.U;

public class SocketSendThread
  implements Runnable
{
  private Thread m_thread;
  private boolean m_bRun;
  private OutputStream m_out;
  private long m_lMaxLength;
  private long m_lTotalWritten;

  public SocketSendThread(OutputStream paramOutputStream, long paramLong)
  {
    this.m_out = paramOutputStream;
    this.m_lMaxLength = paramLong;
    this.m_bRun = true;
    this.m_thread = new Thread(this, "SocketSendThread");
    this.m_thread.start();
  }

  public long getTotalWritten()
  {
    return this.m_lTotalWritten;
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
    try
    {
      byte[] arrayOfByte = U.makeRandom(65536);
      do
      {
        this.m_out.write(arrayOfByte, 0, arrayOfByte.length);
        this.m_lTotalWritten += arrayOfByte.length;
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
 * Qualified Name:     mcsaplugins.hispeed.SocketSendThread
 * JD-Core Version:    0.6.2
 */