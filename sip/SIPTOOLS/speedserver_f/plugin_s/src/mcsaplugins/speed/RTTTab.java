package mcsaplugins.speed;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.StringTokenizer;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.Util;

public class RTTTab extends AppletTab
{
  private speed m_plugin;

  public RTTTab(Applet paramApplet, speed paramspeed)
  {
    super(paramApplet, paramspeed, paramspeed.getImage("tabrtt.gif"), "rtt");
    this.m_plugin = paramspeed;
  }

  public void doFirstTimeInit()
  {
  }

  public void panelPaint(Graphics paramGraphics)
  {
    Object localObject1 = paramGraphics.getFontMetrics();
    int i = getSize().height;
    int j = getSize().width;
    paramGraphics.setColor(new Color(16053492));
    paramGraphics.fillRect(0, 0, j, i);
    Util.setAntialias(paramGraphics, true);
    SpeedTest localSpeedTest = this.m_plugin.getSpeedTest();
    int[] arrayOfInt1 = localSpeedTest == null ? null : localSpeedTest.getRTTs();
    int m;
    if (arrayOfInt1 == null)
    {
      String str1 = RC(TX("speed.rttnotests"));
      m = ((FontMetrics)localObject1).stringWidth(str1);
      paramGraphics.setColor(Color.lightGray);
      paramGraphics.drawString(str1, j / 2 - m / 2, i / 2);
    }
    else
    {
      int k = -1;
      m = -1;
      for (int n = 0; n < arrayOfInt1.length; n++)
      {
        k = arrayOfInt1[n] > k ? arrayOfInt1[n] : k;
        m = (arrayOfInt1[n] < m) || (m < 0) ? arrayOfInt1[n] : m;
      }
      String str2 = this.m_plugin.RC(TX("speed.rttgraphtitle"));
      Font localFont = new Font("Dialog", 1, 16);
      int i1 = 0;
      if (str2.trim().length() > 0)
      {
        localObject2 = paramGraphics.getFont();
        Object localObject3 = localObject1;
        paramGraphics.setFont(localFont);
        localObject1 = paramGraphics.getFontMetrics();
        i1 = ((FontMetrics)localObject1).getHeight();
        i3 = ((FontMetrics)localObject1).stringWidth(str2);
        paramGraphics.setColor(new Color(6710886));
        paramGraphics.drawString(str2, j / 2 - i3 / 2, ((FontMetrics)localObject1).getAscent());
        paramGraphics.setFont((Font)localObject2);
        localObject1 = localObject3;
      }
      Object localObject2 = this.m_plugin.RC(TX("speed.rttxaxis")).trim();
      int i2 = ((FontMetrics)localObject1).stringWidth(TX("XXXXX ") + this.m_plugin.RC(TX("ms"))) + 5;
      int i3 = 15 + (((String)localObject2).length() > 0 ? ((FontMetrics)localObject1).getHeight() : 0);
      Rectangle localRectangle1 = new Rectangle(0, 5 + i1, j - 20, i - 5 - i1);
      Rectangle localRectangle2 = new Rectangle(localRectangle1.x + i2, localRectangle1.y, localRectangle1.width - i2, localRectangle1.height - i3);
      paramGraphics.setColor(new Color(16777215));
      paramGraphics.fillRect(localRectangle2.x, localRectangle2.y, localRectangle2.width, localRectangle2.height);
      int i4 = localRectangle2.width / arrayOfInt1.length;
      int i5 = Math.max(1, i4 / 10);
      i4 -= i5;
      int i6 = Math.max(k + 1, Math.max((int)(k * 1.2D), 2));
      int[] arrayOfInt2 = { 1, 5, 10, 50, 100, 500, 1000, 5000, 10000 };
      int i7 = 10000;
      int i8 = 10000;
      for (int i9 = 0; i9 < arrayOfInt2.length; i9++)
        if (i6 / arrayOfInt2[i9] < 6)
        {
          i7 = arrayOfInt2[i9];
          i8 = i9 == 0 ? i7 : arrayOfInt2[(i9 - 1)];
          break;
        }
      String str3 = this.m_plugin.RC(TX("speed.rttyaxis"));
      StringTokenizer localStringTokenizer = new StringTokenizer(str3, " ");
      paramGraphics.setColor(new Color(6710886));
      int i10 = -1;
      int i12;
      int i14;
      String str5;
      int i16;
      if (localStringTokenizer.countTokens() > 0)
      {
        i11 = 1;
        i12 = localRectangle1.y;
        i14 = ((FontMetrics)localObject1).stringWidth(" ");
        while (localStringTokenizer.hasMoreTokens())
        {
          str5 = localStringTokenizer.nextToken();
          i16 = ((FontMetrics)localObject1).stringWidth(str5);
          if ((i11 > 1) && (i11 + i16 > localRectangle2.x))
          {
            i12 += ((FontMetrics)localObject1).getHeight();
            i11 = 1;
          }
          paramGraphics.drawString(str5, i11, i12);
          i11 += i14 + i16;
        }
        i10 = i12 + ((FontMetrics)localObject1).getDescent() + ((FontMetrics)localObject1).getHeight() / 2;
      }
      if (((String)localObject2).length() > 0)
      {
        paramGraphics.setColor(new Color(6710886));
        i11 = ((FontMetrics)localObject1).stringWidth((String)localObject2);
        paramGraphics.drawString((String)localObject2, localRectangle2.x + localRectangle2.width / 2 - i11 / 2, localRectangle2.y + localRectangle2.height + ((FontMetrics)localObject1).getAscent() + 2);
      }
      int i11 = 0;
      while (i11 <= i6)
      {
        i12 = localRectangle2.y + localRectangle2.height - localRectangle2.height * i11 / i6;
        i14 = i11 % i7 == 0 ? 1 : 0;
        paramGraphics.setColor(new Color(i14 != 0 ? 12698049 : 15658734));
        if (i14 != 0)
        {
          paramGraphics.drawLine(localRectangle2.x - 3, i12, localRectangle2.x + localRectangle2.width, i12);
          str5 = i11 + TX(" ") + this.m_plugin.RC(TX("ms"));
          if ((i12 > i10) || (i10 < 0))
            paramGraphics.drawString(str5, localRectangle2.x - ((FontMetrics)localObject1).stringWidth(str5) - 5, i12 - ((FontMetrics)localObject1).getDescent() + ((FontMetrics)localObject1).getHeight() / 2);
        }
        else
        {
          paramGraphics.drawLine(localRectangle2.x, i12, localRectangle2.x + localRectangle2.width, i12);
        }
        i11 += i8;
      }
      paramGraphics.setColor(new Color(4671303));
      paramGraphics.drawLine(localRectangle2.x, localRectangle2.y, localRectangle2.x, localRectangle2.y + localRectangle2.height + 1);
      paramGraphics.drawLine(localRectangle2.x, localRectangle2.y + localRectangle2.height + 1, localRectangle2.x + localRectangle2.width + 3, localRectangle2.y + localRectangle2.height + 1);
      int i15;
      for (i11 = 0; i11 < arrayOfInt1.length; i11++)
      {
        i12 = arrayOfInt1[i11] < 0 ? 1 : 0;
        i14 = localRectangle2.x + i5 + (int)(i11 * localRectangle2.width / arrayOfInt1.length);
        i15 = (int)(localRectangle2.x + (i11 + 1) * localRectangle2.width / arrayOfInt1.length) - i14;
        i16 = i12 != 0 ? Math.min(50, localRectangle2.height) : localRectangle2.height * arrayOfInt1[i11] / i6;
        paintBar(paramGraphics, i14, localRectangle2.y + localRectangle2.height - i16, i15, i16, i12);
      }
      i11 = localSpeedTest.getAvgRTT();
      if (i11 > 0)
      {
        int i13 = localRectangle2.y + localRectangle2.height - localRectangle2.height * i11 / i6;
        paramGraphics.setColor(new Color(52224));
        for (i14 = -1; i14 <= 1; i14++)
          paramGraphics.drawLine(localRectangle2.x - 3, i13 + i14, localRectangle2.x + localRectangle2.width, i13 + i14);
        String str4 = this.m_plugin.RC(TX("speed.rttaverage"), i11);
        i15 = ((FontMetrics)localObject1).stringWidth(str4);
        paramGraphics.drawString(str4, localRectangle2.x + localRectangle2.width - i15 - 5, i13 - ((FontMetrics)localObject1).getDescent() - 2);
      }
    }
  }

  private void paintBar(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    paramGraphics.setColor(new Color(g(6131888, paramBoolean)));
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2);
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + paramInt4);
    paramGraphics.setColor(new Color(g(802655, paramBoolean)));
    paramGraphics.drawLine(paramInt1 + paramInt3, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    paramGraphics.setColor(new Color(g(2780310, paramBoolean)));
    paramGraphics.drawLine(paramInt1 + paramInt3 - 1, paramInt2 + 2, paramInt1 + paramInt3 - 1, paramInt2 + paramInt4);
    paramGraphics.setColor(new Color(g(6467038, paramBoolean)));
    paramGraphics.drawLine(paramInt1 + 1, paramInt2 + 1, paramInt1 + paramInt3 - 1, paramInt2 + 1);
    paramGraphics.setColor(new Color(g(2383748, paramBoolean)));
    paramGraphics.drawLine(paramInt1, paramInt2 + paramInt4, paramInt1 + paramInt3 - 1, paramInt2 + paramInt4);
    paramGraphics.setColor(new Color(g(2847648, paramBoolean)));
    paramGraphics.drawLine(paramInt1, paramInt2 + paramInt4 - 1, paramInt1 + paramInt3 - 1, paramInt2 + paramInt4 - 1);
    Util.gradientFill(paramGraphics, paramInt1 + 1, paramInt2 + 1, 1, paramInt4 - 1, new Color(g(6467038, paramBoolean)), new Color(g(6134464, paramBoolean)));
    Util.gradientFill(paramGraphics, paramInt1 + 2, paramInt2 + 2, paramInt3 - 2, paramInt4 - 3, new Color(g(3380692, paramBoolean)), new Color(g(2980523, paramBoolean)));
    if (paramBoolean)
    {
      paramGraphics.setColor(Color.white);
      String str = this.m_plugin.RC(TX("speed.rttnoresponse"));
      paramGraphics.drawString(str, paramInt1 + paramInt3 / 2 - paramGraphics.getFontMetrics().stringWidth(str) / 2, paramInt2 + paramInt4 / 2);
    }
  }

  private static int g(int paramInt, boolean paramBoolean)
  {
    int i = ((paramInt & 0xFF) + (paramInt >> 8 & 0xFF) + (paramInt >> 16 & 0xFF)) / 3;
    return paramBoolean ? i << 16 & 0xFF0000 | i << 8 & 0xFF00 | i & 0xFF : paramInt;
  }

  public void reset()
  {
    repaint();
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.speed.RTTTab
 * JD-Core Version:    0.6.2
 */