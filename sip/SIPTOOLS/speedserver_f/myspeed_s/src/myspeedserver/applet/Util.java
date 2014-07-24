package myspeedserver.applet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;

public class Util
{
  public static final int CF = 0;
  public static final int DF = 1;
  public static final int EF = 2;
  private static int[] AF = { 0, 2, 3, 1 };
  private static int[] BF = { 10, 18, 11, 12, 17, 10, 13, 16, 15, 14 };

  static
  {
    Base64.encode(new byte[0]);
  }

  public static Font getFont(String[] paramArrayOfString, int paramInt1, int paramInt2)
  {
    String[] arrayOfString = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String str = null;
    for (int i = 0; (str == null) && (i < paramArrayOfString.length); i++)
      for (int j = 0; (arrayOfString != null) && (j < arrayOfString.length); j++)
        if (paramArrayOfString[i].toLowerCase().equals(arrayOfString[j].toLowerCase()))
        {
          str = arrayOfString[j];
          break;
        }
    return new Font(str == null ? "Dialog" : str, paramInt1, paramInt2);
  }

  public static void gradientFill(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor1, Color paramColor2)
  {
    for (int i = 0; i < paramInt4; i++)
    {
      float f = i / paramInt4;
      paramGraphics.setColor(new Color(mid(paramColor1.getRed(), paramColor2.getRed(), f), mid(paramColor1.getGreen(), paramColor2.getGreen(), f), mid(paramColor1.getBlue(), paramColor2.getBlue(), f)));
      paramGraphics.drawLine(paramInt1, i + paramInt2, paramInt1 + paramInt3 - 1, i + paramInt2);
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

  public static void setAntialias(Graphics paramGraphics, boolean paramBoolean)
  {
    ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, paramBoolean ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
  }

  private static int mid(int paramInt1, int paramInt2, float paramFloat)
  {
    return (int)(paramInt1 + paramInt2 * paramFloat - paramInt1 * paramFloat);
  }

  public static String oneDP(double paramDouble)
  {
    return (int)(paramDouble * 10.0D) / 10.0F;
  }

  public static float getMOSScore(double paramDouble1, double paramDouble2)
  {
    if ((paramDouble1 < 0.0D) || (paramDouble2 == -1.0D))
      return 0.0F;
    double d1 = 1.0D;
    if (paramDouble1 <= 100.0D)
      d1 = -0.028D * paramDouble1 + 4.3D;
    else if (paramDouble1 <= 200.0D)
      d1 = -0.005D * paramDouble1 + 2.0D;
    double d2 = 1.0D;
    if (paramDouble2 <= 1.0D)
      d2 = -1.3D * paramDouble2 + 4.3D;
    else if (paramDouble2 < 5.0D)
      d2 = -0.5D * paramDouble2 + 3.5D;
    double d3 = Math.min(d1, d2);
    return (int)(10.0D * d3) / 10.0F;
  }

  public static void gbAdd(Container paramContainer, Component paramComponent, int paramInt1, int paramInt2, String paramString)
  {
    GridBagConstraints localGridBagConstraints = makeGBC(paramInt1, paramInt2, paramString);
    paramContainer.add(paramComponent);
    try
    {
      ((GridBagLayout)paramContainer.getLayout()).setConstraints(paramComponent, localGridBagConstraints);
    }
    catch (Exception localException)
    {
      System.out.println("gbAdd setConstraints failed: " + localException);
    }
  }

  private static GridBagConstraints makeGBC(int paramInt1, int paramInt2, String paramString)
  {
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = paramInt1;
    localGridBagConstraints.gridy = paramInt2;
    localGridBagConstraints.anchor = 17;
    if (paramString != null)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "whafxypiltrb", true);
      while (localStringTokenizer.hasMoreTokens())
        try
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
            localGridBagConstraints.anchor = BF[i];
          }
          else if (str.equals("f"))
          {
            localGridBagConstraints.fill = AF[i];
          }
          else if (str.equals("x"))
          {
            localGridBagConstraints.weightx = i;
          }
          else if (str.equals("y"))
          {
            localGridBagConstraints.weighty = i;
          }
          else if (str.equals("p"))
          {
            localGridBagConstraints.ipadx = i;
            localGridBagConstraints.ipady = i;
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
          else
          {
            System.out.println("?GBC:" + str);
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          break;
        }
    }
    return localGridBagConstraints;
  }

  public static String httpDecode(String paramString)
  {
    if (paramString == null)
      return null;
    String str = paramString.replace('+', ' ');
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    int j;
    while ((j = str.indexOf('%', i)) >= 0)
    {
      localStringBuffer.append(str.substring(i, j));
      localStringBuffer.append((char)Integer.parseInt(str.substring(j + 1, j + 3), 16));
      i = j + 3;
    }
    localStringBuffer.append(i == 0 ? str : str.substring(i, str.length()));
    return localStringBuffer.toString();
  }

  public static void drawShadow(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[][] arrayOfInt = { { 244, 224, 201, 182, 173 }, { 246, 232, 213, 198, 192 }, { 250, 241, 230, 221, 217 }, { 253, 249, 244, 240, 238 } };
    int i = 5;
    int j = 170;
    int k = 249;
    int n;
    for (int m = 1; m <= i; m++)
    {
      n = k + (i - m) * (j - k) / (i - 1);
      paramGraphics.setColor(new Color(n, n, n));
      paramGraphics.drawLine(paramInt1, paramInt2 + paramInt4 + m, paramInt1 + paramInt3, paramInt2 + paramInt4 + m);
    }
    paramGraphics.setColor(new Color(14474460));
    paramGraphics.drawLine(paramInt1 - 1, paramInt2 + 1, paramInt1 - 1, paramInt2 + paramInt4 + 1);
    paramGraphics.drawLine(paramInt1 + paramInt3 + 1, paramInt2 + 1, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1);
    paramGraphics.setColor(new Color(15921906));
    paramGraphics.drawLine(paramInt1 - 2, paramInt2 + 3, paramInt1 - 2, paramInt2 + paramInt4 + 1);
    paramGraphics.drawLine(paramInt1 + paramInt3 + 2, paramInt2 + 3, paramInt1 + paramInt3 + 2, paramInt2 + paramInt4 + 1);
    for (m = 0; m < arrayOfInt.length; m++)
      for (n = 0; n < arrayOfInt[0].length; n++)
      {
        paramGraphics.setColor(new Color(arrayOfInt[m][n], arrayOfInt[m][n], arrayOfInt[m][n]));
        paramGraphics.drawLine(paramInt1 + n - 2, paramInt2 + paramInt4 + m + 1, paramInt1 + n - 2, paramInt2 + paramInt4 + m + 1);
        paramGraphics.drawLine(paramInt1 + paramInt3 - n + 2, paramInt2 + paramInt4 + m + 1, paramInt1 + paramInt3 - n + 2, paramInt2 + paramInt4 + m + 1);
      }
  }

  public static void drawToolTipRect(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Color localColor = paramGraphics.getColor();
    gradientFill(paramGraphics, paramInt1 + 1, paramInt2 + 1, paramInt3 - 2, paramInt4 - 1, new Color(16514043), new Color(15856113));
    gradientFill(paramGraphics, paramInt1 + 2, paramInt2 + 2, paramInt3 - 4, paramInt4 - 3, new Color(16382457), new Color(15263976));
    paramGraphics.setColor(new Color(14342874));
    paramGraphics.drawRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, 5, 5);
    paramGraphics.setColor(localColor);
  }

  public static void drawOrangeButton(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Color localColor = paramGraphics.getColor();
    if (paramInt5 != 0)
    {
      int i;
      if (paramInt5 == 1)
      {
        paramGraphics.setColor(new Color(14405273));
        i = paramInt5 == 1 ? paramInt4 / 2 : 5;
        paramGraphics.drawLine(paramInt1 + 1, paramInt2, paramInt1 + paramInt3 - 1, paramInt2);
        gradientFill(paramGraphics, paramInt1, paramInt2 + 1, paramInt3, i - 1, new Color(14405273), new Color(12165236));
        gradientFill(paramGraphics, paramInt1 + 1, paramInt2 + 1, paramInt3 - 2, i - 1, new Color(16777211), new Color(16775651));
        gradientFill(paramGraphics, paramInt1 + 2, paramInt2 + 2, paramInt3 - 4, i - 2, new Color(16776684), new Color(16772537));
        gradientFill(paramGraphics, paramInt1, paramInt2 + i, paramInt3, paramInt4 - i - 1, new Color(12099443), new Color(12631211));
        gradientFill(paramGraphics, paramInt1 + 1, paramInt2 + i, paramInt3 - 2, paramInt4 - i - 1, new Color(16773833), new Color(16777210));
        gradientFill(paramGraphics, paramInt1 + 2, paramInt2 + i, paramInt3 - 4, paramInt4 - i - 2, new Color(16767069), new Color(16775585));
        paramGraphics.setColor(new Color(12631211));
        paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt4 - 1, paramInt1 + paramInt3 - 1, paramInt2 + paramInt4 - 1);
      }
      else if (paramInt5 == 2)
      {
        i = paramInt4 / 2;
        gradientFill(paramGraphics, paramInt1 + 1, paramInt2 + 1, paramInt3 - 2, 3, new Color(11699548), new Color(15904886));
        gradientFill(paramGraphics, paramInt1 + 1, paramInt2 + 4, paramInt3 - 2, i - 3, new Color(15839346), new Color(15832635));
        gradientFill(paramGraphics, paramInt1 + 1, paramInt2 + i, paramInt3 - 2, paramInt4 - i, new Color(15562756), new Color(16756227));
        paramGraphics.setColor(new Color(8087109));
        paramGraphics.drawLine(paramInt1 + 1, paramInt2, paramInt1 + paramInt3 - 1, paramInt2);
        paramGraphics.drawLine(paramInt1, paramInt2 + 1, paramInt1, paramInt2 + paramInt4 - 1);
        paramGraphics.drawLine(paramInt1 + paramInt3, paramInt2 + 1, paramInt1 + paramInt3, paramInt2 + paramInt4 - 1);
        paramGraphics.drawLine(paramInt1 + 1, paramInt2 + 1, paramInt1 + 1, paramInt2 + 1);
        paramGraphics.drawLine(paramInt1 + paramInt3 - 1, paramInt2 + 1, paramInt1 + paramInt3 - 1, paramInt2 + 1);
        paramGraphics.setColor(new Color(12631211));
        paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt4, paramInt1 + paramInt3 - 1, paramInt2 + paramInt4);
      }
    }
    paramGraphics.setColor(localColor);
  }

  public static double parseDouble(String paramString, double paramDouble)
  {
    try
    {
      return Double.valueOf(paramString).doubleValue();
    }
    catch (Exception localException)
    {
    }
    return paramDouble;
  }

  public static int parseInt(String paramString, int paramInt)
  {
    try
    {
      return Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
    }
    return paramInt;
  }

  public static long parseLong(String paramString, long paramLong)
  {
    try
    {
      return Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
    }
    return paramLong;
  }

  public static String parseString(Object paramObject, String paramString)
  {
    return ((paramObject instanceof String)) && (paramObject != null) ? (String)paramObject : paramString;
  }

  public static int constrainInt(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt1 > paramInt3 ? paramInt3 : paramInt1 < paramInt2 ? paramInt2 : paramInt1;
  }

  public static long constrainLong(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 > paramLong3 ? paramLong3 : paramLong1 < paramLong2 ? paramLong2 : paramLong1;
  }

  public static int[] toIntArray(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return null;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    int[] arrayOfInt = new int[localStringTokenizer.countTokens()];
    for (int i = 0; i < arrayOfInt.length; i++)
      try
      {
        arrayOfInt[i] = Integer.parseInt(localStringTokenizer.nextToken());
      }
      catch (Exception localException)
      {
      }
    return arrayOfInt;
  }

  public static long[] toLongArray(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return null;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    long[] arrayOfLong = new long[localStringTokenizer.countTokens()];
    for (int i = 0; i < arrayOfLong.length; i++)
      try
      {
        arrayOfLong[i] = Long.parseLong(localStringTokenizer.nextToken());
      }
      catch (Exception localException)
      {
      }
    return arrayOfLong;
  }

  public static float[] toFloatArray(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return null;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    float[] arrayOfFloat = new float[localStringTokenizer.countTokens()];
    for (int i = 0; i < arrayOfFloat.length; i++)
      try
      {
        arrayOfFloat[i] = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      }
      catch (Exception localException)
      {
      }
    return arrayOfFloat;
  }

  public static boolean[] toBooleanArray(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return null;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    boolean[] arrayOfBoolean = new boolean[localStringTokenizer.countTokens()];
    for (int i = 0; i < arrayOfBoolean.length; i++)
      arrayOfBoolean[i] = "y".equals(localStringTokenizer.nextToken());
    return arrayOfBoolean;
  }

  public static String intArrayToString(int[] paramArrayOfInt, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; (paramArrayOfInt != null) && (i < paramArrayOfInt.length); i++)
    {
      if (localStringBuffer.length() > 0)
        localStringBuffer.append(paramString);
      localStringBuffer.append(paramArrayOfInt[i]);
    }
    return localStringBuffer.toString();
  }

  public static String longArrayToString(long[] paramArrayOfLong, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; (paramArrayOfLong != null) && (i < paramArrayOfLong.length); i++)
    {
      if (localStringBuffer.length() > 0)
        localStringBuffer.append(paramString);
      localStringBuffer.append(paramArrayOfLong[i]);
    }
    return localStringBuffer.toString();
  }

  public static String floatArrayToString(float[] paramArrayOfFloat, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; (paramArrayOfFloat != null) && (i < paramArrayOfFloat.length); i++)
    {
      if (localStringBuffer.length() > 0)
        localStringBuffer.append(paramString);
      localStringBuffer.append(paramArrayOfFloat[i]);
    }
    return localStringBuffer.toString();
  }

  public static String booleanArrayToString(boolean[] paramArrayOfBoolean, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; (paramArrayOfBoolean != null) && (i < paramArrayOfBoolean.length); i++)
    {
      if (localStringBuffer.length() > 0)
        localStringBuffer.append(paramString);
      localStringBuffer.append(paramArrayOfBoolean[i] != 0 ? "y" : "n");
    }
    return localStringBuffer.toString();
  }

  public static Object renderVector(Vector paramVector, Class paramClass)
  {
    int i = paramVector.size();
    Object localObject = Array.newInstance(paramClass, i);
    for (int j = 0; j < i; j++)
      Array.set(localObject, j, paramVector.elementAt(j));
    return localObject;
  }

  public static String replace(String paramString1, String paramString2, String paramString3)
  {
    int i = paramString1.indexOf(paramString2);
    if (i < 0)
      return paramString1;
    paramString1 = paramString1.substring(0, i) + paramString3 + paramString1.substring(i + paramString2.length());
    return replace(paramString1, paramString2, paramString3);
  }

  public static String readUrl(URL paramURL)
  {
    DataInputStream localDataInputStream = null;
    try
    {
      localDataInputStream = new DataInputStream(paramURL.openStream());
      StringBuffer localStringBuffer = new StringBuffer();
      Object localObject1;
      while ((localObject1 = localDataInputStream.readLine()) != null)
        localStringBuffer.append(localObject1 + "\r\n");
      String str = localStringBuffer.toString();
      return str;
    }
    catch (Exception localException1)
    {
      System.out.println("Error reading " + paramURL + ": " + localException1);
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
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.Util
 * JD-Core Version:    0.6.2
 */