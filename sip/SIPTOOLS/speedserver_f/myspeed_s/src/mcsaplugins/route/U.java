package mcsaplugins.route;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import myspeedserver.applet.Base64;

public class U
{
  public static route g_vrApplet;
  private static Hashtable g_msToColour = new Hashtable();
  private static Hashtable g_places = new Hashtable();
  private static int[] aFill = { 0, 2, 3, 1 };
  private static int[] aAnchor = { 10, 18, 11, 12, 17, 10, 13, 16, 15, 14 };

  public static void gbAdd(Container paramContainer, Component paramComponent, int paramInt1, int paramInt2, String paramString)
  {
    GridBagConstraints localGridBagConstraints = getGridBagConstraints(paramInt1, paramInt2, paramString);
    paramContainer.add(paramComponent);
    ((GridBagLayout)paramContainer.getLayout()).setConstraints(paramComponent, localGridBagConstraints);
  }

  private static GridBagConstraints getGridBagConstraints(int paramInt1, int paramInt2, String paramString)
  {
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = paramInt1;
    localGridBagConstraints.gridy = paramInt2;
    localGridBagConstraints.anchor = 17;
    if (paramString != null)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "whafxypiltrb", true);
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        int i = Integer.parseInt(localStringTokenizer.nextToken());
        if (str.equals("w"))
        {
          localGridBagConstraints.gridwidth = i;
        }
        else if (str.equals("h"))
        {
          localGridBagConstraints.gridheight = i;
        }
        else if (str.equals("a"))
        {
          localGridBagConstraints.anchor = aAnchor[i];
        }
        else if (str.equals("f"))
        {
          localGridBagConstraints.fill = aFill[i];
        }
        else if (str.equals("x"))
        {
          localGridBagConstraints.weightx = i;
        }
        else if (str.equals("y"))
        {
          localGridBagConstraints.weighty = i;
        }
        else if (str.equals("i"))
        {
          localGridBagConstraints.insets = new Insets(i, i, i, i);
        }
        else if (str.equals("l"))
        {
          localGridBagConstraints.insets.left = i;
        }
        else if (str.equals("t"))
        {
          localGridBagConstraints.insets.top = i;
        }
        else if (str.equals("r"))
        {
          localGridBagConstraints.insets.right = i;
        }
        else if (str.equals("b"))
        {
          localGridBagConstraints.insets.bottom = i;
        }
        else if (str.equals("p"))
        {
          localGridBagConstraints.ipadx = i;
          localGridBagConstraints.ipady = i;
        }
      }
    }
    return localGridBagConstraints;
  }

  public static String readLineEnc(InputStream paramInputStream, long[] paramArrayOfLong)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    while (((i = read(paramInputStream, paramArrayOfLong)) != -1) && (i != 10))
      localStringBuffer.append((char)i);
    return (i == -1) && (localStringBuffer.length() == 0) ? null : localStringBuffer.toString();
  }

  public static String readUrlLine(URL paramURL)
  {
    DataInputStream localDataInputStream = null;
    try
    {
      localDataInputStream = new DataInputStream(paramURL.openStream());
      String str = localDataInputStream.readLine();
      return str;
    }
    catch (Exception localException1)
    {
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
    return null;
  }

  private static int read(InputStream paramInputStream, long[] paramArrayOfLong)
  {
    int i = paramInputStream.read();
    int j = paramInputStream.read();
    if ((i == -1) || (j == -1))
      return -1;
    int k = (char)(j << 8 | i);
    int m = (char)(int)(k ^ paramArrayOfLong[0] >>> 11);
    paramArrayOfLong[0] = (paramArrayOfLong[0] * paramArrayOfLong[1] + paramArrayOfLong[2] + (m << 8) ^ paramArrayOfLong[0] >>> 19);
    return m;
  }

  public static String urlEncode(String paramString)
  {
    String str = URLEncoder.encode(paramString);
    str = replace(str, ".", "%2E");
    return str;
  }

  public static String replace(String paramString1, String paramString2, String paramString3)
  {
    int i = paramString1.indexOf(paramString2);
    return i >= 0 ? replace(paramString1.substring(0, i) + paramString3 + paramString1.substring(i + paramString2.length()), paramString2, paramString3) : paramString1;
  }

  public static String pad(String paramString, int paramInt)
  {
    for (paramString = paramString == null ? "-" : paramString; paramString.length() < paramInt; paramString = paramString + " ");
    return paramString;
  }

  public static String hashTableToString(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3)
  {
    if (paramHashtable == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      String str = paramHashtable.get(localObject).toString();
      if (paramString3 != null)
      {
        str = replace(str, "\r\n", paramString3);
        str = replace(str, "\n", paramString3);
        str = replace(str, "\r", paramString3);
      }
      localStringBuffer.append(localObject).append(paramString2).append(str).append(paramString1);
    }
    return localStringBuffer.toString();
  }

  public static String trim(String paramString, int paramInt)
  {
    return (paramString == null) || (paramString.length() <= paramInt) ? paramString : paramString.substring(0, paramInt);
  }

  public static String base64Encode(String paramString)
  {
    return Base64.encode(paramString.getBytes());
  }

  public static String base64Decode(String paramString)
  {
    return new String(Base64.decode(paramString));
  }

  public static String urlDecode(String paramString)
  {
    int j;
    int i;
    for (paramString = paramString.replace('+', ' '); ((i = paramString.indexOf('%')) >= 0) && (i < paramString.length() - 2); paramString = paramString.substring(0, i) + (j > 0 ? (char)j : "") + paramString.substring(i + 3))
    {
      j = -1;
      try
      {
        j = Integer.parseInt(paramString.substring(i + 1, i + 3), 16);
      }
      catch (Exception localException)
      {
      }
    }
    return paramString;
  }

  public static String text(String paramString1, String paramString2)
  {
    return text(paramString1, new String[] { paramString2 });
  }

  public static String text(String paramString, String[] paramArrayOfString)
  {
    String str = text(paramString);
    for (int i = 0; i < paramArrayOfString.length; i++)
      str = replace(str, "$" + i, paramArrayOfString[i]);
    return str;
  }

  public static String text(String paramString)
  {
    String str1 = "?" + paramString + "?";
    if (g_vrApplet == null)
      return null;
    String str2 = g_vrApplet.getSetting(paramString, null);
    return str2 == null ? str1 : str2;
  }

  public static String dash(String paramString)
  {
    return paramString == null ? "-" : paramString;
  }

  public static void drawCopyright(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, Color paramColor)
  {
    long l1 = G.FADESTART + 2000L;
    long l2 = l1 + 3000L;
    long l3 = System.currentTimeMillis();
    if (l3 < l2)
    {
      int i = 3355443;
      int j = paramColor.getRGB();
      paramGraphics.setColor(l3 >= l1 ? mid(0, (int)(l2 - l1), (int)(l3 - l1), i, j) : new Color(i));
      Color localColor = paramGraphics.getColor();
      Font localFont = paramGraphics.getFont();
      paramGraphics.setFont(new Font("Helvetica", 0, 11));
      FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
      int k = localFontMetrics.stringWidth("Copyright (c) 2011 Visualware, Inc.");
      paramGraphics.drawString("Copyright (c) 2011 Visualware, Inc.", paramInt1 + paramInt3 / 2 - k / 2, paramInt2);
      paramGraphics.setFont(localFont);
      paramGraphics.setColor(localColor);
    }
  }

  public static Image getImage(String paramString)
  {
    return g_vrApplet == null ? null : g_vrApplet.getAppletImage(paramString);
  }

  public static void gradientFill(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor1, Color paramColor2)
  {
    for (int i = 0; i < paramInt4; i++)
    {
      float f = i / paramInt4;
      paramGraphics.setColor(new Color(mid(paramColor1.getRed(), paramColor2.getRed(), f), mid(paramColor1.getGreen(), paramColor2.getGreen(), f), mid(paramColor1.getBlue(), paramColor2.getBlue(), f)));
      paramGraphics.drawLine(paramInt1, i + paramInt2, paramInt1 + paramInt3, i + paramInt2);
    }
  }

  public static void horzGradientFill(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor1, Color paramColor2)
  {
    for (int i = 0; i < paramInt3; i++)
    {
      float f = i / paramInt3;
      paramGraphics.setColor(new Color(mid(paramColor1.getRed(), paramColor2.getRed(), f), mid(paramColor1.getGreen(), paramColor2.getGreen(), f), mid(paramColor1.getBlue(), paramColor2.getBlue(), f)));
      paramGraphics.drawLine(i + paramInt1, paramInt2, i + paramInt1, paramInt2 + paramInt4);
    }
  }

  private static int mid(int paramInt1, int paramInt2, float paramFloat)
  {
    return (int)(paramInt1 + paramInt2 * paramFloat - paramInt1 * paramFloat);
  }

  public static Color lighter(Color paramColor)
  {
    double d1 = paramColor.getRed() / 0.95D;
    double d2 = paramColor.getGreen() / 0.95D;
    double d3 = paramColor.getBlue() / 0.95D;
    return new Color(Math.min((int)d1, 255), Math.min((int)d2, 255), Math.min((int)d3, 255));
  }

  public static Color darker(Color paramColor)
  {
    double d1 = paramColor.getRed() * 0.95D;
    double d2 = paramColor.getGreen() * 0.95D;
    double d3 = paramColor.getBlue() * 0.95D;
    return new Color((int)d1, (int)d2, (int)d3);
  }

  public static Color latencyColour(float paramFloat, String paramString)
  {
    int[] arrayOfInt = (int[])g_msToColour.get(paramString);
    if (arrayOfInt == null)
    {
      arrayOfInt = G.getMsColourTable(paramString);
      g_msToColour.put(paramString, arrayOfInt);
    }
    int i = arrayOfInt.length / 2;
    if (paramFloat > arrayOfInt[(i * 2 - 2)])
      return new Color(arrayOfInt[(i * 2 - 1)]);
    for (int j = 0; j < i - 1; j++)
    {
      int k = arrayOfInt[(j * 2 + 0)];
      int m = arrayOfInt[(j * 2 + 2)];
      if ((paramFloat >= k) && (paramFloat <= m))
      {
        int n = arrayOfInt[(j * 2 + 1)];
        int i1 = arrayOfInt[(j * 2 + 3)];
        return mid(k, m, paramFloat, n, i1);
      }
    }
    return Color.white;
  }

  private static Color mid(int paramInt1, int paramInt2, float paramFloat, int paramInt3, int paramInt4)
  {
    double d = 1.0D * (paramFloat - paramInt1) / (paramInt2 - paramInt1);
    int i = paramInt3 >> 16 & 0xFF;
    int j = paramInt3 >> 8 & 0xFF;
    int k = paramInt3 >> 0 & 0xFF;
    int m = paramInt4 >> 16 & 0xFF;
    int n = paramInt4 >> 8 & 0xFF;
    int i1 = paramInt4 >> 0 & 0xFF;
    int i2 = i + (int)(d * (m - i));
    int i3 = j + (int)(d * (n - j));
    int i4 = k + (int)(d * (i1 - k));
    return new Color(i2, i3, i4);
  }

  public static Place getPlace(String paramString)
  {
    return paramString == null ? null : (Place)g_places.get(paramString);
  }

  public static void sleep(long paramLong)
  {
    try
    {
      Thread.sleep(paramLong);
    }
    catch (Exception localException)
    {
    }
  }

  public static String stringArrayToString(String[] paramArrayOfString)
  {
    return stringArrayToString(paramArrayOfString, ",");
  }

  public static String stringArrayToString(String[] paramArrayOfString, String paramString)
  {
    if (paramArrayOfString == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (i != 0)
        localStringBuffer.append(paramString);
      localStringBuffer.append(paramArrayOfString[i]);
    }
    return localStringBuffer.toString();
  }

  public static String[] stringToStringArray(String paramString1, String paramString2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    String[] arrayOfString = new String[localStringTokenizer.countTokens()];
    int i = 0;
    while (localStringTokenizer.hasMoreTokens())
      arrayOfString[(i++)] = localStringTokenizer.nextToken();
    return arrayOfString;
  }

  public static int[] stringToIntArray(String paramString1, String paramString2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    int[] arrayOfInt = new int[localStringTokenizer.countTokens()];
    for (int i = 0; localStringTokenizer.hasMoreTokens(); i++)
    {
      String str = localStringTokenizer.nextToken();
      try
      {
        arrayOfInt[i] = Integer.parseInt(str);
      }
      catch (Exception localException)
      {
      }
    }
    return arrayOfInt;
  }

  public static String intArrayToString(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      if (i != 0)
        localStringBuffer.append(",");
      localStringBuffer.append(paramArrayOfInt[i]);
    }
    return localStringBuffer.toString();
  }

  public static void setPlaces(String paramString)
  {
    if (paramString == null)
      return;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "\t");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      Place localPlace = Place.fromAppletString(str);
      if ((localPlace != null) && (g_places.get(localPlace.key) == null))
        g_places.put(localPlace.key, localPlace);
    }
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.U
 * JD-Core Version:    0.6.2
 */