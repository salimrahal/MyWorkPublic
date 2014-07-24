package myspeedserver.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;

public class U
{
  private static Random TG = new Random(System.currentTimeMillis());
  private static Method VG;
  private static long XG;
  private static long WG;
  private static myspeed LF;
  private static int UG = 0;

  static
  {
    timeSetup();
  }

  public static void setMySpeed(Applet paramApplet)
  {
    LF = (myspeed)paramApplet;
  }

  public static long endTime()
  {
    long l1 = time();
    long l2;
    for (int i = 0; (l2 = time()) == l1; i++);
    long l3;
    for (int j = 0; (l3 = time()) == l2; j++);
    int k = (int)(l2 - l1);
    int m = (int)(l3 - l2);
    if ((k > 0) && (m > 0))
    {
      int n = k * j / m;
      if (n > 0)
        return l2 - Math.min(k, i * k / n);
    }
    return l1;
  }

  private static byte[] gen()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    boolean[][] arrayOfBoolean = new boolean[256][256];
    int i = 0;
    for (int j = 0; j < 196608; j++)
    {
      int k = TG.nextInt() & 0xFF;
      for (int m = 0; m < 7; m++)
      {
        int n = k + m & 0xFF;
        if (arrayOfBoolean[i][n] == 0)
        {
          localByteArrayOutputStream.write((char)n);
          arrayOfBoolean[i][n] = 1;
          i = n;
        }
      }
    }
    return localByteArrayOutputStream.toByteArray();
  }

  public static int getReceiveBufferSize(Socket paramSocket)
  {
    try
    {
      return ((Integer)paramSocket.getClass().getMethod(TX("getReceiveBufferSize"), new Class[0]).invoke(paramSocket, new Object[0])).intValue();
    }
    catch (Throwable localThrowable)
    {
    }
    return 0;
  }

  public static int getSendBufferSize(Socket paramSocket)
  {
    try
    {
      return ((Integer)paramSocket.getClass().getMethod(TX("getSendBufferSize"), new Class[0]).invoke(paramSocket, new Object[0])).intValue();
    }
    catch (Throwable localThrowable)
    {
    }
    return 0;
  }

  public static int getTickResolution()
  {
    if (UG <= 0)
    {
      int i = 100;
      long l1 = syncTime();
      for (int j = 0; j < 10; j++)
      {
        long l2 = syncTime();
        i = Math.min(i, (int)(l2 - l1));
        l1 = l2;
      }
      UG = Math.max(1, i);
    }
    return UG;
  }

  public static byte[] makeRandom(int paramInt)
  {
    byte[] arrayOfByte1 = new byte[paramInt];
    int i = 0;
    while (i < arrayOfByte1.length)
    {
      byte[] arrayOfByte2 = gen();
      int j = Math.min(arrayOfByte1.length - i, arrayOfByte2.length);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, i, j);
      i += j;
    }
    return arrayOfByte1;
  }

  public static double log(double paramDouble)
  {
    return Math.log(paramDouble);
  }

  public static double log10(double paramDouble)
  {
    return log(paramDouble) / log(10.0D);
  }

  public static int max(int paramInt1, int paramInt2)
  {
    return paramInt1 > paramInt2 ? paramInt1 : paramInt2;
  }

  public static long max(long paramLong1, long paramLong2)
  {
    return paramLong1 > paramLong2 ? paramLong1 : paramLong2;
  }

  public static int min(int paramInt1, int paramInt2)
  {
    return paramInt1 < paramInt2 ? paramInt1 : paramInt2;
  }

  public static long min(long paramLong1, long paramLong2)
  {
    return paramLong1 < paramLong2 ? paramLong1 : paramLong2;
  }

  public static double pow(double paramDouble1, double paramDouble2)
  {
    return Math.pow(paramDouble1, paramDouble2);
  }

  public static int abs(int paramInt)
  {
    return paramInt < 0 ? -paramInt : paramInt;
  }

  public static long abs(long paramLong)
  {
    return paramLong < 0L ? -paramLong : paramLong;
  }

  public static int rand(int paramInt)
  {
    return (int)(Math.random() * paramInt) % paramInt;
  }

  public static int[] resize(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, min(paramArrayOfInt.length, paramInt));
    return arrayOfInt;
  }

  public static byte[] resize(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, min(paramArrayOfByte.length, paramInt));
    return arrayOfByte;
  }

  public static long[] resize(long[] paramArrayOfLong, int paramInt)
  {
    long[] arrayOfLong = new long[paramInt];
    System.arraycopy(paramArrayOfLong, 0, arrayOfLong, 0, min(paramArrayOfLong.length, paramInt));
    return arrayOfLong;
  }

  public static String bps2s(long paramLong)
  {
    return bps2s(paramLong, true);
  }

  public static String bps2s(long paramLong, boolean paramBoolean)
  {
    boolean bool = G.g_bBytes;
    long l = bool ? paramLong / 8L : paramLong;
    int i = 100;
    l *= i;
    l /= 1000L;
    int j = l / i > 1000L ? 1 : 0;
    String str1 = j != 0 ? RC(TX("m")) : RC(TX("k"));
    if (j != 0)
      l /= 1000L;
    int k = (int)(l / 100L);
    String str2 = TX("") + k;
    if (k < 100)
    {
      str2 = str2 + TX(".") + l % 100L / 10L;
      if ((k < 10) && (paramBoolean))
        str2 = str2 + l % 10L;
    }
    return str2 + TX(" ") + str1 + (bool ? RC(TX("bytesps")) : RC(TX("bitsps")));
  }

  public static long toSF(long paramLong, int paramInt)
  {
    long l = ()Math.pow(10.0D, Math.floor(Math.log(paramLong) / Math.log(10.0D)) - paramInt);
    return l > 0L ? (paramLong + 5L * l) / (l * 10L) * l * 10L : paramLong;
  }

  public static String addCommas(long paramLong)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramLong < 0L ? 1 : 0;
    paramLong = abs(paramLong);
    for (int j = 0; (j == 0) || (paramLong != 0L); j++)
    {
      if ((j > 0) && (j % 3 == 0))
        localStringBuffer.append(TX(","));
      localStringBuffer.append((char)(int)(48L + paramLong % 10L));
      paramLong /= 10L;
    }
    if (i != 0)
      localStringBuffer.append(TX("-"));
    return localStringBuffer.reverse().toString();
  }

  public static String d2s(double paramDouble)
  {
    return paramDouble != -1.0D ? Util.oneDP(paramDouble) : "-";
  }

  public static String mapValueToLabel(String paramString, int paramInt)
  {
    if (paramString != null)
    {
      StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, ",");
      while (localStringTokenizer1.hasMoreTokens())
        try
        {
          StringTokenizer localStringTokenizer2 = new StringTokenizer(localStringTokenizer1.nextToken(), "<>=", true);
          String str1 = localStringTokenizer2.nextToken();
          int i = 0;
          int j = 0;
          if (!localStringTokenizer2.hasMoreTokens())
            return str1;
          while (localStringTokenizer2.hasMoreTokens())
          {
            String str2 = localStringTokenizer2.nextToken();
            if ("<".equals(str2))
            {
              i = -1;
            }
            else if (">".equals(str2))
            {
              i = 1;
            }
            else if ("=".equals(str2))
            {
              j = 1;
            }
            else
            {
              int k = Integer.parseInt(str2);
              if (((i < 0) && (paramInt < k)) || ((i > 0) && (paramInt > k)) || ((j != 0) && (paramInt == k)))
                return str1;
            }
          }
        }
        catch (Exception localException)
        {
        }
    }
    return "";
  }

  private static String RC(String paramString)
  {
    return LF.RC(paramString);
  }

  public static Color valueToColour(double paramDouble, long[] paramArrayOfLong)
  {
    int i = paramArrayOfLong.length / 2;
    if ((i > 0) && (paramDouble > paramArrayOfLong[(paramArrayOfLong.length - 2)]))
      return new Color((int)paramArrayOfLong[(paramArrayOfLong.length - 1)]);
    for (int j = 0; j < i - 1; j++)
    {
      long l1 = paramArrayOfLong[(j * 2 + 0)];
      long l2 = paramArrayOfLong[(j * 2 + 2)];
      if ((paramDouble >= l1) && (paramDouble <= l2))
      {
        int k = (int)paramArrayOfLong[(j * 2 + 1)];
        int m = (int)paramArrayOfLong[(j * 2 + 3)];
        return xxx(l1, l2, paramDouble, k, m);
      }
    }
    return Color.white;
  }

  private static Color xxx(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2)
  {
    double d = 1.0D * (paramDouble3 - paramDouble1) / (paramDouble2 - paramDouble1);
    int i = paramInt1 >> 16 & 0xFF;
    int j = paramInt1 >> 8 & 0xFF;
    int k = paramInt1 >> 0 & 0xFF;
    int m = paramInt2 >> 16 & 0xFF;
    int n = paramInt2 >> 8 & 0xFF;
    int i1 = paramInt2 >> 0 & 0xFF;
    int i2 = i + (int)(d * (m - i));
    int i3 = j + (int)(d * (n - j));
    int i4 = k + (int)(d * (i1 - k));
    return new Color(i2, i3, i4);
  }

  public static long[] parseColourTable(String paramString)
  {
    try
    {
      if (paramString != null)
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(paramString, TX(",\r\n"));
        int i = localStringTokenizer.countTokens();
        long[] arrayOfLong = new long[i * 2];
        for (int j = 0; j < i; j++)
        {
          String str = localStringTokenizer.nextToken();
          int k = str.indexOf('=');
          long l = Long.parseLong(str.substring(0, k).trim());
          int m = Integer.parseInt(str.substring(k + 1).trim(), 16);
          arrayOfLong[(j * 2 + 0)] = l;
          arrayOfLong[(j * 2 + 1)] = m;
        }
        return arrayOfLong;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static void drawDot(Graphics paramGraphics, int paramInt1, int paramInt2, Color paramColor)
  {
    Color localColor = paramGraphics.getColor();
    paramGraphics.setColor(Color.black);
    paramGraphics.fillRect(paramInt1 - 3, paramInt2 - 1, 7, 3);
    paramGraphics.fillRect(paramInt1 - 2, paramInt2 - 2, 5, 5);
    paramGraphics.fillRect(paramInt1 - 1, paramInt2 - 3, 3, 7);
    paramGraphics.setColor(paramColor);
    paramGraphics.fillRect(paramInt1 - 2, paramInt2 - 1, 5, 3);
    paramGraphics.fillRect(paramInt1 - 1, paramInt2 - 2, 3, 5);
    paramGraphics.setColor(localColor);
  }

  public static boolean isNewerJavaAvailable()
  {
    try
    {
      StringTokenizer localStringTokenizer1 = new StringTokenizer("1.6", ".");
      StringTokenizer localStringTokenizer2 = new StringTokenizer(System.getProperty("java.version"), ".");
      while (localStringTokenizer1.hasMoreTokens())
      {
        int i = localStringTokenizer1.nextToken().charAt(0);
        int j = localStringTokenizer2.hasMoreTokens() ? localStringTokenizer2.nextToken().charAt(0) : 48;
        if (i > j)
          return true;
        if (i < j)
          break;
      }
    }
    catch (Throwable localThrowable)
    {
    }
    return false;
  }

  public static String javaInfo(boolean paramBoolean)
  {
    String str1 = getSystemProperty(TX("java.vendor"), "");
    String str2 = str1.toLowerCase();
    str1 = str2.indexOf(TX("sun")) >= 0 ? TX("Sun") : str2.indexOf(TX("microsoft")) >= 0 ? TX("MS") : paramBoolean ? str1 : "";
    return str1 + TX(" Java ") + getSystemProperty(TX("java.version"), TX("?"));
  }

  public static String getSystemProperty(String paramString1, String paramString2)
  {
    return System.getProperty(paramString1, paramString2);
  }

  private static long nanoTime()
  {
    if (VG != null)
      try
      {
        return ((Long)VG.invoke(null, new Object[0])).longValue();
      }
      catch (Throwable localThrowable)
      {
      }
    return 0L;
  }

  public static void sleep(long paramLong)
  {
    try
    {
      if (paramLong > 0L)
        Thread.sleep(paramLong);
    }
    catch (Exception localException)
    {
    }
  }

  public static URLConnection setNoCaching(URLConnection paramURLConnection)
  {
    paramURLConnection.setUseCaches(false);
    paramURLConnection.setRequestProperty(TX("Cache-Control"), TX("no-cache"));
    return paramURLConnection;
  }

  public static int getReceiveBufferSize(DatagramSocket paramDatagramSocket)
  {
    try
    {
      return ((Integer)paramDatagramSocket.getClass().getMethod(TX("getReceiveBufferSize"), new Class[0]).invoke(paramDatagramSocket, new Object[0])).intValue();
    }
    catch (Throwable localThrowable)
    {
    }
    return 0;
  }

  public static int getSendBufferSize(DatagramSocket paramDatagramSocket)
  {
    try
    {
      return ((Integer)paramDatagramSocket.getClass().getMethod(TX("getSendBufferSize"), new Class[0]).invoke(paramDatagramSocket, new Object[0])).intValue();
    }
    catch (Throwable localThrowable)
    {
    }
    return 0;
  }

  public static void setReceiveBufferSize(DatagramSocket paramDatagramSocket, int paramInt)
  {
    if (paramInt > getReceiveBufferSize(paramDatagramSocket))
      try
      {
        paramDatagramSocket.getClass().getMethod(TX("setReceiveBufferSize"), new Class[] { Integer.TYPE }).invoke(paramDatagramSocket, new Object[] { new Integer(paramInt) });
      }
      catch (Throwable localThrowable)
      {
      }
  }

  public static void setSendBufferSize(DatagramSocket paramDatagramSocket, int paramInt)
  {
    if (paramInt > getSendBufferSize(paramDatagramSocket))
      try
      {
        paramDatagramSocket.getClass().getMethod(TX("setSendBufferSize"), new Class[] { Integer.TYPE }).invoke(paramDatagramSocket, new Object[] { new Integer(paramInt) });
      }
      catch (Throwable localThrowable)
      {
      }
  }

  public static void setReceiveBufferSize(Socket paramSocket, int paramInt)
  {
    if (paramInt > getReceiveBufferSize(paramSocket))
      try
      {
        paramSocket.getClass().getMethod(TX("setReceiveBufferSize"), new Class[] { Integer.TYPE }).invoke(paramSocket, new Object[] { new Integer(paramInt) });
      }
      catch (Throwable localThrowable)
      {
      }
  }

  public static void setSendBufferSize(Socket paramSocket, int paramInt)
  {
    if (paramInt > getSendBufferSize(paramSocket))
      try
      {
        paramSocket.getClass().getMethod(TX("setSendBufferSize"), new Class[] { Integer.TYPE }).invoke(paramSocket, new Object[] { new Integer(paramInt) });
      }
      catch (Throwable localThrowable)
      {
      }
  }

  public static byte[] stob(String paramString)
  {
    if (paramString != null)
    {
      byte[] arrayOfByte = new byte[paramString.length()];
      paramString.getBytes(0, arrayOfByte.length, arrayOfByte, 0);
      return arrayOfByte;
    }
    return null;
  }

  public static String subst(String paramString1, String paramString2, String paramString3)
  {
    int i;
    for (int j = 0; (j < 10) && ((i = paramString1.indexOf(paramString2)) >= 0); j++)
      paramString1 = paramString1.substring(0, i) + paramString3 + paramString1.substring(i + paramString2.length());
    return paramString1;
  }

  public static Hashtable stringToHash(String paramString1, String paramString2)
  {
    return stringToHash(paramString1, paramString2, null);
  }

  public static Hashtable stringToHash(String paramString1, String paramString2, Hashtable paramHashtable)
  {
    if (paramHashtable == null)
      paramHashtable = new Hashtable();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      int i = str.indexOf("=");
      if (i > 0)
        paramHashtable.put(str.substring(0, i), str.substring(i + 1));
    }
    return paramHashtable;
  }

  public static void sort(long[] paramArrayOfLong, int[] paramArrayOfInt)
  {
    int i = 0;
    int[] arrayOfInt1 = new int[300];
    int[] arrayOfInt2 = new int[300];
    arrayOfInt1[0] = 0;
    arrayOfInt2[0] = paramArrayOfLong.length;
    while (i >= 0)
    {
      int j = arrayOfInt1[i];
      int k = arrayOfInt2[i] - 1;
      if (j < k)
      {
        long l = paramArrayOfLong[j];
        int n = paramArrayOfInt[j];
        while (j < k)
        {
          while ((paramArrayOfLong[k] >= l) && (j < k))
            k--;
          if (j < k)
          {
            paramArrayOfLong[j] = paramArrayOfLong[k];
            paramArrayOfInt[j] = paramArrayOfInt[k];
            j++;
          }
          while ((paramArrayOfLong[j] <= l) && (j < k))
            j++;
          if (j < k)
          {
            paramArrayOfLong[k] = paramArrayOfLong[j];
            paramArrayOfInt[k] = paramArrayOfInt[j];
            k--;
          }
        }
        paramArrayOfLong[j] = l;
        paramArrayOfInt[j] = n;
        arrayOfInt1[(i + 1)] = (j + 1);
        arrayOfInt2[(i + 1)] = arrayOfInt2[i];
        arrayOfInt2[(i++)] = j;
        if (arrayOfInt2[i] - arrayOfInt1[i] > arrayOfInt2[(i - 1)] - arrayOfInt1[(i - 1)])
        {
          int m = arrayOfInt1[i];
          arrayOfInt1[i] = arrayOfInt1[(i - 1)];
          arrayOfInt1[(i - 1)] = m;
          m = arrayOfInt2[i];
          arrayOfInt2[i] = arrayOfInt2[(i - 1)];
          arrayOfInt2[(i - 1)] = m;
        }
      }
      else
      {
        i--;
      }
    }
  }

  public static int removeDuplicates(long[] paramArrayOfLong, int[] paramArrayOfInt)
  {
    int i = paramArrayOfLong.length;
    for (int j = 0; j < i - 1; j++)
      if (paramArrayOfLong[j] == paramArrayOfLong[(j + 1)])
      {
        paramArrayOfInt[j] += paramArrayOfInt[(j + 1)];
        for (int k = j + 1; k < i - 1; k++)
        {
          paramArrayOfLong[k] = paramArrayOfLong[(k + 1)];
          paramArrayOfInt[k] = paramArrayOfInt[(k + 1)];
        }
        i--;
        j--;
      }
    return i;
  }

  public static long syncTime()
  {
    long l1 = time();
    long l2;
    while ((l2 = time()) == l1);
    return l2;
  }

  public static long time()
  {
    return WG != 0L ? XG + (nanoTime() - WG) / 1000000L : System.currentTimeMillis();
  }

  private static void timeSetup()
  {
    try
    {
      VG = System.class.getMethod(TX("nanoTime"), new Class[0]);
    }
    catch (Throwable localThrowable)
    {
    }
    long l1 = time();
    long l2;
    while ((l2 = time()) == l1);
    WG = nanoTime();
    XG = l2;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.U
 * JD-Core Version:    0.6.2
 */