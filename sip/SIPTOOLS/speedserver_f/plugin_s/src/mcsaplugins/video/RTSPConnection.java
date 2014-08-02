package mcsaplugins.video;

import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class RTSPConnection
{
  private PushbackInputStream m_in;
  private OutputStream m_out;
  private String m_method;
  private String m_url;
  private int m_nCSeq;
  private String m_sessionId;
  private byte[] m_data;
  private int m_nReturnStatus;
  private Hashtable m_returnHeader;
  private byte[] m_returnContent;
  private String m_error;

  public RTSPConnection(Socket paramSocket, String paramString1, String paramString2, int paramInt, String paramString3)
  {
    try
    {
      this.m_in = new PushbackInputStream(paramSocket.getInputStream());
      this.m_out = paramSocket.getOutputStream();
    }
    catch (Exception localException)
    {
    }
    this.m_method = paramString1;
    this.m_url = paramString2;
    this.m_nCSeq = paramInt;
    this.m_sessionId = paramString3;
  }

  public void setMethod(String paramString)
  {
    this.m_method = paramString;
  }

  public void addHeaderItem(Vector paramVector, String paramString1, String paramString2)
  {
    paramVector.addElement(paramString1 + ": " + paramString2);
  }

  public void addData(byte[] paramArrayOfByte)
  {
    this.m_data = paramArrayOfByte;
  }

  public int getReturnStatus()
  {
    return this.m_nReturnStatus;
  }

  public String getReturnHeader(String paramString)
  {
    return this.m_returnHeader == null ? null : (String)this.m_returnHeader.get(paramString.toLowerCase());
  }

  public byte[] getReturnContent()
  {
    return this.m_returnContent;
  }

  public String getError()
  {
    return this.m_error;
  }

  public void go()
  {
    try
    {
      Vector localVector = new Vector();
      sendHeaderBody(localVector);
      this.m_returnHeader = new Hashtable();
      StringTokenizer localStringTokenizer = new StringTokenizer(readLine(), " ");
      localStringTokenizer.nextToken();
      this.m_nReturnStatus = Integer.parseInt(localStringTokenizer.nextToken());
      Object localObject;
      while ((localObject = readLine()) != null)
      {
        if ("".equals(localObject))
          break;
        i = ((String)localObject).indexOf(':');
        if (i > 0)
          this.m_returnHeader.put(((String)localObject).substring(0, i).trim().toLowerCase(), ((String)localObject).substring(i + 1).trim());
      }
      int i = 0;
      try
      {
        i = Integer.parseInt(getReturnHeader("content-length"));
      }
      catch (Exception localException2)
      {
      }
      if (i > 0)
        this.m_returnContent = read(i);
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      this.m_error = localException1.toString();
    }
  }

  private int sendHeader(int paramInt, Vector paramVector)
  {
    int i = 0;
    int j = this.m_nCSeq++;
    if (paramInt > 0)
      addHeaderItem(paramVector, "Content-Length", paramInt);
    addHeaderItem(paramVector, "CSeq", j);
    if (this.m_sessionId != null)
      addHeaderItem(paramVector, "Session", this.m_sessionId);
    i += writeLine(this.m_method + " " + this.m_url + " RTSP/1.0");
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
      i += writeLine((String)localEnumeration.nextElement());
    i += writeLine("");
    flush();
    return i;
  }

  private int sendHeaderBody(Vector paramVector)
  {
    int i = sendHeader(this.m_data == null ? 0 : this.m_data.length, paramVector);
    if (this.m_data != null)
      i += write(this.m_data);
    flush();
    return i;
  }

  private byte[] read(int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    this.m_in.read(arrayOfByte);
    return arrayOfByte;
  }

  private String readLine()
  {
    char[] arrayOfChar = new char[65536];
    resetWatchdogKillTime();
    int j = 0;
    for (int i = 0; i < arrayOfChar.length; i++)
    {
      int k = this.m_in.read();
      if (k == -1)
      {
        j = 1;
        break;
      }
      if (k == 13)
      {
        int m = this.m_in.read();
        if (m == 10)
          break;
        this.m_in.unread(m);
        break;
      }
      arrayOfChar[i] = ((char)k);
    }
    resetWatchdogKillTime();
    return (j != 0) && (i == 0) ? null : new String(arrayOfChar, 0, i);
  }

  private int write(byte[] paramArrayOfByte)
  {
    resetWatchdogKillTime();
    this.m_out.write(paramArrayOfByte);
    resetWatchdogKillTime();
    return paramArrayOfByte.length;
  }

  private int writeLine(String paramString)
  {
    byte[] arrayOfByte = (paramString + "\r\n").getBytes();
    write(arrayOfByte);
    return arrayOfByte.length;
  }

  private void flush()
  {
    resetWatchdogKillTime();
    this.m_out.flush();
    resetWatchdogKillTime();
  }

  private void resetWatchdogKillTime()
  {
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.video.RTSPConnection
 * JD-Core Version:    0.6.2
 */