package myspeedserver.applet;

/*s.r.
http://www.gladir.com/TECHNOLOGIE/INFORMATIQUE/base64.htm
*/
public class Base64
{
  private static final char[] CH = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
  private static final byte[] DH = new byte[256];

  static
  {//fill the value table with it's correcpondent value in number: 'A' coded to '0'
    for (int i = 0; i < CH.length; i++)
      DH[CH[i]] = ((byte)i);
  }

  public static String addCRLF(String paramString)
  {
    int i = paramString.length();
    int j = i + (i - 1) / 76 * 2;
    char[] arrayOfChar = new char[j];
    int k = 0;
    int m = 0;
    while (k < i)
    {
      int n = Math.min(76, i - k);
      paramString.getChars(k, k + n, arrayOfChar, m);
      k += n;
      m += n;
      if (k < i)
      {
        arrayOfChar[(m++)] = '\r';
        arrayOfChar[(m++)] = '\n';
      }
    }
    return new String(arrayOfChar);
  }

  public static byte[] decode(String paramString)
  {
    int i = paramString.endsWith("=") ? 1 : paramString.endsWith("==") ? 2 : 0;
    char[] arrayOfChar = paramString.toCharArray();
    int j = arrayOfChar.length / 4;
    byte[] arrayOfByte = new byte[Math.max(0, j * 3 - i)];
    int k = arrayOfByte.length;
    int m = 0;
    int n = 0;
    for (int i1 = 0; i1 < j; i1++)
    {
      int i2 = (((DH[(arrayOfChar[(n + 0)] & 0xFF)] << 6) + DH[(arrayOfChar[(n + 1)] & 0xFF)] << 6) + DH[(arrayOfChar[(n + 2)] & 0xFF)] << 6) + DH[(arrayOfChar[(n + 3)] & 0xFF)];
      if (m < k)
        arrayOfByte[(m++)] = ((byte)(i2 >> 16));
      if (m < k)
        arrayOfByte[(m++)] = ((byte)(i2 >> 8));
      if (m < k)
        arrayOfByte[(m++)] = ((byte)i2);
      n += 4;
    }
    return arrayOfByte;
  }

  public static String encode(byte[] paramArrayOfByte)
  {
    int i = (paramArrayOfByte.length + 2) / 3 * 4;
    char[] arrayOfChar = new char[i];
    int j = 0;
    int k = 0;
    int m = paramArrayOfByte.length / 3 * 3;
    int i1;
    int i2;
    while (k < m)
    {
      // uncommented by SR: n = paramArrayOfByte[(k++)];
      byte n = paramArrayOfByte[(k++)];
      i1 = paramArrayOfByte[(k++)];
      i2 = paramArrayOfByte[(k++)];
      arrayOfChar[(j++)] = CH[(n >>> 2 & 0x3F)];
      arrayOfChar[(j++)] = CH[((n << 4 & 0x30) + (i1 >>> 4 & 0xF))];
      arrayOfChar[(j++)] = CH[((i1 << 2 & 0x3C) + (i2 >>> 6 & 0x3))];
      arrayOfChar[(j++)] = CH[(i2 & 0x3F)];
    }
    int n = paramArrayOfByte.length - m;
    if (n > 0)
    {
      i1 = paramArrayOfByte[(k++)];
      i2 = n == 2 ? paramArrayOfByte[(k++)] : 0;
      int i3 = 0;
      arrayOfChar[(j++)] = CH[(i1 >>> 2 & 0x3F)];
      arrayOfChar[(j++)] = CH[((i1 << 4 & 0x30) + (i2 >>> 4 & 0xF))];
      arrayOfChar[(j++)] = (n == 2 ? CH[((i2 << 2 & 0x3C) + (i3 >>> 6 & 0x3))] : '=');
      arrayOfChar[(j++)] = '=';
    }
    if ((j != arrayOfChar.length) || (k != paramArrayOfByte.length))
      throw new RuntimeException("Base64 encode error on len=" + paramArrayOfByte.length);
    return new String(arrayOfChar);
  }

  public static String trim(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if ((c != ' ') && (c != '\r') && (c != '\n'))
        localStringBuffer.append(c);
    }
    return localStringBuffer.toString();
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.Base64
 * JD-Core Version:    0.6.2
 */