package mcsaplugins.voip;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PushbackInputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class SipTester
{
  private PushbackInputStream m_in;
  private OutputStream m_out;
  private String m_address;
  private int m_nPort;
  private String m_lastMethod;
  private String m_lastUri;
  private Vector m_hLast;
  private Vector m_hOut;
  private int m_nCSeq;
  private String m_sipUser;
  private String m_password;
  private String m_callid;
  private String m_returnLine;
  private int m_nReturnStatus;
  private Hashtable m_returnHeader;
  private byte[] m_returnContent;
  private String m_error;

  public SipTester(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4)
  {
    Socket localSocket = new Socket(paramString1, paramInt);
    localSocket.setSoTimeout(6000);
    localSocket.setSoLinger(true, 2);
    this.m_in = new PushbackInputStream(localSocket.getInputStream());
    this.m_out = localSocket.getOutputStream();
    this.m_address = paramString1;
    this.m_nPort = paramInt;
    this.m_sipUser = paramString2;
    this.m_callid = paramString3;
    this.m_password = paramString4;
    this.m_hOut = new Vector();
  }

  public void addHeaderItem(String paramString1, String paramString2)
  {
    this.m_hOut.addElement(paramString1 + ": " + paramString2);
  }

  private void addProxyAuthorizationHeader(String paramString1, String paramString2, String paramString3)
  {
    String str1 = paramString3.trim();
    int i = str1.indexOf(' ');
    Object localObject1 = i > 0 ? str1.substring(0, i).trim().toLowerCase() : null;
    Object localObject2 = null;
    String str2 = "md5";
    Object localObject3 = null;
    Object localObject4 = null;
    int j = i;
    String str5;
    while ((j < str1.length()) && (j > 0))
    {
      int k = str1.indexOf('=', j);
      if (k < 0)
        break;
      String str3 = str1.substring(j, k).trim().toLowerCase();
      str5 = null;
      int m;
      if (str1.substring(k + 1).trim().startsWith("\""))
      {
        m = str1.indexOf('"', k + 1);
        int n = str1.indexOf('"', m + 1);
        if (n < 0)
          break;
        str5 = str1.substring(m + 1, n);
        j = str1.indexOf(',', n + 1) + 1;
      }
      else
      {
        m = str1.indexOf(',', k + 1);
        str5 = m > 0 ? str1.substring(k + 1, m) : str1.substring(k + 1);
        j = m + 1;
      }
      if (str3.equals("nonce"))
        localObject2 = str5;
      else if (str3.equals("algorithm"))
        str2 = str5.toLowerCase();
      else if (str3.equals("qop"))
        localObject3 = str5;
      else if (str3.equals("realm"))
        localObject4 = str5;
    }
    if ((localObject1.equals("digest")) && (str2.equals("md5")))
    {
      MessageDigest localMessageDigest = null;
      try
      {
        localMessageDigest = MessageDigest.getInstance("MD5");
      }
      catch (Exception localException)
      {
      }
      if (localMessageDigest == null)
      {
        System.out.println("MD5 algorithm not available");
        return;
      }
      String str4 = this.m_sipUser + ":" + localObject4 + ":" + this.m_password;
      str5 = paramString1 + ":" + paramString2;
      if (localObject3 == null)
      {
        localMessageDigest.update(md5(str4));
        localMessageDigest.update(new byte[] { 58 });
        localMessageDigest.update(localObject2.getBytes());
        localMessageDigest.update(new byte[] { 58 });
        localMessageDigest.update(md5(str5));
        String str6 = toMD5String(localMessageDigest.digest());
        System.out.println("auth compat digest / " + str6);
        addHeaderItem("Proxy-Authorization", "Digest username=\"" + this.m_sipUser + "\",uri=\"" + paramString2 + "\",cnonce=\"" + localObject2 + "\",realm=\"" + localObject4 + "\",response=\"" + str6 + "\"");
      }
      else if (!localObject3.equals("auth"))
      {
        System.out.println("Qop NOT RECOGNISED (" + str1 + ")");
      }
    }
    else
    {
      System.out.println("Method/algorithm NOT RECOGNISED (" + str1 + ")");
    }
  }

  public int getReturnStatusCode()
  {
    return this.m_nReturnStatus;
  }

  public String getReturnStatusLine()
  {
    return this.m_returnLine;
  }

  public String getReturnHeader(String paramString)
  {
    return this.m_returnHeader == null ? null : (String)this.m_returnHeader.get(paramString.toLowerCase());
  }

  public Hashtable getReturnHeaders()
  {
    return this.m_returnHeader;
  }

  public byte[] getReturnContent()
  {
    return this.m_returnContent;
  }

  public String getError()
  {
    return this.m_error;
  }

  public int sendHeader(String paramString1, String paramString2, int paramInt)
  {
    String str = generateSipId();
    addHeaderItem("To", "sip:" + this.m_sipUser + "@" + this.m_address + ":" + this.m_nPort);
    addHeaderItem("From", "sip:" + this.m_sipUser + "@" + this.m_address + ":" + this.m_nPort);
    addHeaderItem("Call-id", this.m_callid);
    addHeaderItem("CSeq", ++this.m_nCSeq + " " + paramString1);
    addHeaderItem("Max-Forwards", "20");
    addHeaderItem("Via", "SIP/2.0/UDP 192.168.100.12:3266;branch=z9hG4bK-" + str + ";rport");
    return sendBasicHeader(paramString1, paramString2);
  }

  private int sendBasicHeader(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    localStringBuffer.append(paramString1 + " " + paramString2 + " SIP/2.0\r\n");
    Enumeration localEnumeration = this.m_hOut.elements();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      System.out.println(" --> " + str);
      localStringBuffer.append(str + "\r\n");
    }
    i += writeLine(localStringBuffer.toString());
    System.out.println();
    flush();
    this.m_lastMethod = paramString1;
    this.m_lastUri = paramString2;
    this.m_hLast = ((Vector)this.m_hOut.clone());
    this.m_hOut = new Vector();
    return i;
  }

  public static String generateSipId()
  {
    char[] arrayOfChar = new char[32];
    for (int i = 0; i < arrayOfChar.length; i++)
      arrayOfChar[i] = ((char)(65 + (int)(Math.random() * 26.0D)));
    return new String(arrayOfChar);
  }

  public void readHeaderBody()
  {
    doReadHeaderBody();
    printHeaders(this);
    if (this.m_nReturnStatus == 407)
    {
      System.out.println("\n---- Requires authentication, retrying...");
      this.m_hOut = ((Vector)this.m_hLast.clone());
      addProxyAuthorizationHeader(this.m_lastMethod, this.m_lastUri, getReturnHeader("proxy-authenticate"));
      sendBasicHeader(this.m_lastMethod, this.m_lastUri);
      doReadHeaderBody();
      printHeaders(this);
    }
  }

  public void doReadHeaderBody()
  {
    this.m_returnHeader = new Hashtable();
    this.m_returnLine = readLine();
    if (this.m_returnLine == null)
      throw new IOException("The server gave an empty response");
    StringTokenizer localStringTokenizer = new StringTokenizer(this.m_returnLine, " ");
    if (localStringTokenizer.countTokens() < 2)
      throw new IOException("The server did not give the expected response (invalid 1st line)");
    localStringTokenizer.nextToken();
    try
    {
      this.m_nReturnStatus = Integer.parseInt(localStringTokenizer.nextToken());
    }
    catch (Exception localException1)
    {
      throw new IOException("The server did not give the expected response (invalid status code)");
    }
    String str;
    while ((str = readLine()) != null)
    {
      if ("".equals(localException1))
        break;
      i = localException1.indexOf(':');
      if (i > 0)
        this.m_returnHeader.put(localException1.substring(0, i).trim().toLowerCase(), localException1.substring(i + 1).trim());
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

  public void close()
  {
    if (this.m_out != null)
      this.m_out.close();
  }

  private void resetWatchdogKillTime()
  {
  }

  private static byte[] md5(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      return localMessageDigest.digest();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private static String toMD5String(byte[] paramArrayOfByte)
  {
    try
    {
      if (paramArrayOfByte == null)
        return null;
      char[] arrayOfChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; (paramArrayOfByte != null) && (i < paramArrayOfByte.length); i++)
      {
        localStringBuffer.append(arrayOfChar[(paramArrayOfByte[i] >> 4 & 0xF)]);
        localStringBuffer.append(arrayOfChar[(paramArrayOfByte[i] & 0xF)]);
      }
      return localStringBuffer.toString();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private static void printHeaders(SipTester paramSipTester)
  {
    Hashtable localHashtable = paramSipTester.getReturnHeaders();
    Enumeration localEnumeration = localHashtable.keys();
    System.out.println("=== " + paramSipTester.getReturnStatusLine());
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      System.out.println(" Header: " + str + ": " + localHashtable.get(str));
    }
    System.out.println();
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.voip.SipTester
 * JD-Core Version:    0.6.2
 */