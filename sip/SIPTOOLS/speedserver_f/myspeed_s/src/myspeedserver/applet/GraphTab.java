package myspeedserver.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Properties;
import javax.swing.JComboBox;

public class GraphTab extends AppletTab
  implements ActionListener
{
  private JComboBox PH;
  private Hashtable OH = new Hashtable();
  private Point JG = null;
  private int KG = 1;
  private int LG = 1;
  private int MH = 0;
  private int NH = 0;
  private Point QH;
  private int RH;
  private int SH;
  private int TH;
  private int UH;

  public GraphTab(Applet paramApplet)
  {
    super(paramApplet, null, ((myspeed)paramApplet).getImageFromJar("tabgraph.gif"), "graph");
  }

  public void doFirstTimeInit()
  {
    setLayout(null);
    this.PH = new JComboBox();
    this.PH.addActionListener(this);
    this.PH.removeAllItems();
    add(this.PH);
  }

  public void reset()
  {
  }

  public boolean canDisplayGraph()
  {
    return this.PH.getItemCount() > 0;
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    Dimension localDimension = new Dimension(i, j);
    boolean bool = this.PH.getItemCount() > 1;
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    String str1 = RC(TX("graph_clickdetail"));
    int k = (TX("yes").equals(iniGetString("mssidlink"))) && (str1.length() > 0) ? 1 : 0;
    int m = (k != 0) || (bool) ? Math.max(this.PH.getSize().height, localFontMetrics.getHeight()) + 5 : 0;
    String str2 = (String)this.PH.getSelectedItem();
    Object[] arrayOfObject = str2 == null ? null : (Object[])this.OH.get(str2);
    int n;
    if ((arrayOfObject instanceof String[]))
      for (n = 0; n < ((String[])arrayOfObject).length; n++)
        drawGraph(paramGraphics, new Point(), ((String[])arrayOfObject)[n], 10, 10, localDimension.width - 20, localDimension.height - 20 - m, n > 0);
    else
      drawGraph(paramGraphics, new Point(), (String)this.PH.getSelectedItem(), 10, 10, localDimension.width - 20, localDimension.height - 20 - m, false);
    paramGraphics.setColor(Color.blue);
    if (k != 0)
    {
      n = localFontMetrics.stringWidth(str1);
      int i1 = localDimension.height - localFontMetrics.getDescent() - 5 - 3;
      paramGraphics.drawString(str1, localDimension.width - 10 - n, i1);
      paramGraphics.drawLine(localDimension.width - 10 - n, i1 + 2, localDimension.width - 10, i1 + 2);
      addHitRegion("auditreport", new Rectangle(localDimension.width - 10 - n, i1 - localFontMetrics.getAscent(), n, localFontMetrics.getHeight()));
    }
    paramGraphics.setColor(Color.black);
    this.PH.setVisible(bool);
    if (bool)
    {
      String str3 = RC(TX("graph_plot"));
      paramGraphics.drawString(str3, 10, localDimension.height - localFontMetrics.getDescent() - 5 - 3);
      this.PH.setSize(this.PH.getPreferredSize());
      this.PH.setLocation(10 + localFontMetrics.stringWidth(str3) + 10, localDimension.height - this.PH.getSize().height - 5);
    }
  }

  public void addCombinedGraphResults(String paramString, String[] paramArrayOfString)
  {
    this.OH.put(paramString, paramArrayOfString);
    this.PH.addItem(paramString);
    repaint();
  }

  public void addGraphResults(String paramString, long[] paramArrayOfLong, int[] paramArrayOfInt, float[] paramArrayOfFloat, boolean[] paramArrayOfBoolean)
  {
    int i = (paramArrayOfLong == null) || (paramArrayOfLong.length <= 0) ? 8000 : (int)(paramArrayOfLong[(paramArrayOfLong.length - 1)] - paramArrayOfLong[0]);
    int j = paramArrayOfFloat == null ? 750 : paramArrayOfFloat.length;
    Properties localProperties = new Properties();
    localProperties.put("totalms", i);
    localProperties.put("totalpackets", j);
    localProperties.put("delaygraph", (paramArrayOfLong != null) && (paramArrayOfInt != null) ? "yes" : "no");
    localProperties.put("multfactor", "8");
    this.OH.put(paramString, new Object[] { paramArrayOfLong, paramArrayOfInt, paramArrayOfFloat, paramArrayOfBoolean, localProperties });
    this.PH.addItem(paramString);
    repaint();
  }

  public void addGraphResults(String paramString, long[][] paramArrayOfLong, int[][] paramArrayOfInt, float[] paramArrayOfFloat, boolean[] paramArrayOfBoolean)
  {
    int i = (paramArrayOfLong == null) || (paramArrayOfLong.length <= 0) || (paramArrayOfLong[0] == null) || (paramArrayOfLong[0].length < 0) ? 8000 : (int)(paramArrayOfLong[0][(paramArrayOfLong.length - 1)] - paramArrayOfLong[0][0]);
    int j = paramArrayOfFloat == null ? 750 : paramArrayOfFloat.length;
    Properties localProperties = new Properties();
    localProperties.put("totalms", i);
    localProperties.put("totalpackets", j);
    localProperties.put("delaygraph", (paramArrayOfLong != null) && (paramArrayOfInt != null) ? "yes" : "no");
    localProperties.put("multfactor", "8");
    Object[] arrayOfObject = new Object[5 + 2 * (paramArrayOfLong == null ? 0 : paramArrayOfLong.length - 1)];
    arrayOfObject[0] = ((paramArrayOfLong == null) || (paramArrayOfLong.length == 0) ? null : paramArrayOfLong[0]);
    arrayOfObject[1] = ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0) ? null : paramArrayOfInt[0]);
    arrayOfObject[2] = paramArrayOfFloat;
    arrayOfObject[3] = paramArrayOfBoolean;
    arrayOfObject[4] = localProperties;
    for (int k = 1; (paramArrayOfLong != null) && (k < paramArrayOfLong.length); k++)
    {
      arrayOfObject[(k * 2 + 3)] = paramArrayOfLong[k];
      arrayOfObject[(k * 2 + 4)] = paramArrayOfInt[k];
    }
    this.OH.put(paramString, arrayOfObject);
    this.PH.addItem(paramString);
    repaint();
  }

  public void addGraphResults(String paramString, long[] paramArrayOfLong, int[] paramArrayOfInt, boolean paramBoolean, int paramInt)
  {
    Properties localProperties = new Properties();
    localProperties.put("bars", paramBoolean ? "yes" : "no");
    localProperties.put("totalms", paramInt);
    localProperties.put("absolutetimes", "yes");
    localProperties.put("yaxislabel", "Bytes ReTx");
    this.OH.put(paramString, new Object[] { paramArrayOfLong, paramArrayOfInt, 0, 0, localProperties });
    this.PH.addItem(paramString);
    repaint();
  }

  private Object[] summarize(long[] paramArrayOfLong, int[] paramArrayOfInt, boolean paramBoolean)
  {
    long[] arrayOfLong = paramArrayOfLong == null ? null : new long[paramArrayOfLong.length];
    int[] arrayOfInt = paramArrayOfLong == null ? null : new int[paramArrayOfLong.length];
    int i = 0;
    int k;
    for (int j = 0; (arrayOfLong != null) && (arrayOfInt != null) && (j < arrayOfLong.length); j = k)
    {
      paramArrayOfLong[j] -= (paramBoolean ? 0L : paramArrayOfLong[0]);
      for (k = j; (k < arrayOfLong.length) && (k < paramArrayOfLong.length) && (paramArrayOfLong[j] == paramArrayOfLong[k]); k++)
        arrayOfInt[i] += paramArrayOfInt[k];
      i++;
    }
    arrayOfLong = arrayOfLong == null ? null : U.resize(arrayOfLong, i);
    arrayOfInt = arrayOfInt == null ? null : U.resize(arrayOfInt, i);
    return new Object[] { arrayOfLong, (arrayOfLong == null) || (arrayOfInt == null) ? null : arrayOfInt };
  }

  private int[] getMinMax(boolean paramBoolean1, float[] paramArrayOfFloat, long[] paramArrayOfLong, int[] paramArrayOfInt, int paramInt1, int paramInt2, boolean paramBoolean2)
  {
    int i = 1;
    int j = 0;
    int k = 0;
    do
    {
      do
      {
        if (paramBoolean2)
          i = Math.max(i, (int)(1.1D * paramInt2 * paramArrayOfInt[k]));
        else
          i = Math.max(i, (int)(paramBoolean1 ? Math.ceil(paramArrayOfFloat[k]) : paramInt2 * paramArrayOfInt[k] / ((k > 0) && (k < paramArrayOfLong.length) ? (paramArrayOfLong[(k + 1)] - paramArrayOfLong[(k - 1)]) / 2L : paramInt1)));
        j = Math.min(j, (int)(paramBoolean1 ? Math.floor(paramArrayOfFloat[k]) : paramArrayOfInt[k]));
        k++;
      }
      while ((paramBoolean1) && (paramArrayOfFloat != null) && (k < paramArrayOfFloat.length));
      if ((paramBoolean1) || (paramArrayOfLong == null))
        break;
    }
    while (k < paramArrayOfLong.length - (paramBoolean2 ? 0 : 1));
    return new int[] { j, i };
  }

  private void drawGraph(Graphics paramGraphics, Point paramPoint, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    Point localPoint1 = new Point(paramPoint);
    localPoint1.x += 3;
    paramInt3 -= 6;
    if (!paramBoolean)
    {
      paramGraphics.setColor(Color.white);
      paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
      paramGraphics.setColor(Color.gray);
      paramGraphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
      paramGraphics.setColor(Color.lightGray);
      paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt4 + 1, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1);
      paramGraphics.drawLine(paramInt1 + paramInt3 + 1, paramInt2 + 1, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1);
    }
    Object[] arrayOfObject1 = (Object[])this.OH.get(paramString);
    long[] arrayOfLong1 = arrayOfObject1 == null ? null : (long[])arrayOfObject1[0];
    int[] arrayOfInt1 = arrayOfObject1 == null ? null : (int[])arrayOfObject1[1];
    float[] arrayOfFloat = arrayOfObject1 == null ? null : (float[])arrayOfObject1[2];
    boolean[] arrayOfBoolean = arrayOfObject1 == null ? null : (boolean[])arrayOfObject1[3];
    Properties localProperties = (arrayOfObject1 == null) || (arrayOfObject1.length <= 4) ? null : (Properties)arrayOfObject1[4];
    localProperties = localProperties == null ? new Properties() : localProperties;
    long[] arrayOfLong2 = (arrayOfObject1 == null) || (arrayOfObject1.length <= 6) ? null : (long[])arrayOfObject1[5];
    int[] arrayOfInt2 = (arrayOfObject1 == null) || (arrayOfObject1.length <= 6) ? null : (int[])arrayOfObject1[6];
    boolean bool1 = (arrayOfFloat != null) && (arrayOfBoolean != null);
    boolean bool2 = "yes".equals(localProperties.get("absolutetimes"));
    Object[] arrayOfObject2 = summarize(arrayOfLong1, arrayOfInt1, bool2);
    long[] arrayOfLong3 = (arrayOfObject2 == null) || (arrayOfObject2.length < 2) ? null : (long[])arrayOfObject2[0];
    int[] arrayOfInt3 = (arrayOfObject2 == null) || (arrayOfObject2.length < 2) ? null : (int[])arrayOfObject2[1];
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int i = localFontMetrics.getHeight();
    int j = localFontMetrics.stringWidth("XXXXXX.X");
    Rectangle localRectangle1 = new Rectangle(paramInt1 + j, paramInt2 + i * 2, paramInt3 - j * 2, paramInt4 - i * 5);
    Point localPoint2 = this.JG == null ? null : new Point(this.JG.x, this.JG.y);
    if (localPoint2 != null)
      localPoint2.translate(-localPoint1.x, -localPoint1.y);
    if ((arrayOfFloat == null) || (arrayOfBoolean == null) || (arrayOfFloat.length == 0) || (arrayOfBoolean.length == 0))
    {
      arrayOfFloat = null;
      arrayOfBoolean = null;
    }
    boolean bool3 = "yes".equals(localProperties.get("delaygraph"));
    int k = Util.parseInt((String)localProperties.get("multfactor"), 1);
    boolean bool4 = "yes".equals(localProperties.get("bars"));
    int m = 1;
    Color localColor1 = new Color(8355839);
    Color localColor2 = new Color(16744319);
    int n = Util.parseInt((String)localProperties.get("totalpackets"), arrayOfFloat == null ? 750 : arrayOfFloat.length);
    int i1 = Util.parseInt((String)localProperties.get("totalms"), (arrayOfLong3 == null) || (arrayOfLong3.length < 2) ? 8000 : Math.max(1000, (int)(arrayOfLong3[(arrayOfLong3.length - 1)] - arrayOfLong3[0])));
    if (!paramBoolean)
    {
      paramGraphics.setColor(Color.white);
      paramGraphics.fillRect(localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height);
      paramGraphics.setColor(Color.gray);
      paramGraphics.drawRect(localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height);
    }
    int i2 = 20;
    double d1 = bool1 ? localRectangle1.width / n : localRectangle1.width / (i1 / i2);
    int i3 = 1;
    double d2 = bool1 ? 4 : 4;
    if ((bool1) && (d1 < d2))
    {
      i3 = (int)Math.ceil(d2 / d1);
      d1 = d2;
    }
    int i4 = bool1 ? this.NH : this.MH;
    int i5 = -1;
    int i6 = -1;
    paramGraphics.clipRect(localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height);
    paramGraphics.translate(localRectangle1.x, localRectangle1.y);
    int[] arrayOfInt4 = getMinMax(bool1, arrayOfFloat, arrayOfLong3, arrayOfInt3, i2, k, bool4);
    int i7 = arrayOfInt4[0];
    int i8 = arrayOfInt4[1];
    int i9 = i7 * localRectangle1.height / (i8 - i7);
    Point localPoint3 = null;
    paramGraphics.setColor(localColor2);
    String str1 = 0;
    int i10;
    while ((bool1) && (arrayOfFloat != null) && (str1 < n))
    {
      i10 = 0;
      for (int i11 = str1; i11 < Math.min(str1 + i3, n); i11++)
        if (arrayOfBoolean[i11] != 0)
          i10++;
      i10 = i10 * 100 / i3;
      i11 = i4 + (int)(str1 * d1 / i3);
      paramGraphics.fillRect(i11 - (int)(d1 / 2.0D), localRectangle1.height * (100 - i10) / 100, Math.max(1, (int)d1 - 1), localRectangle1.height * i10 / 100);
      str1 += i3;
    }
    paramGraphics.setColor(localColor1);
    int i14;
    int i15;
    int i18;
    int i19;
    for (str1 = 0; ((str1 < 2) && (!bool1)) || (str1 < 1); str1++)
    {
      i10 = str1 == 1 ? 1 : 0;
      long[] arrayOfLong4 = arrayOfLong3;
      int[] arrayOfInt5 = arrayOfInt3;
      if (i10 != 0)
      {
        if ((arrayOfLong2 == null) || (arrayOfInt2 == null))
          break;
        Object[] arrayOfObject3 = summarize(arrayOfLong2, arrayOfInt2, bool2);
        arrayOfLong4 = (arrayOfObject3 == null) || (arrayOfObject3.length < 2) ? null : (long[])arrayOfObject3[0];
        arrayOfInt5 = (arrayOfObject3 == null) || (arrayOfObject3.length < 2) ? null : (int[])arrayOfObject3[1];
        if ((arrayOfLong4 == null) || (arrayOfInt5 == null))
          break;
      }
      if ((i10 != 0) || (paramBoolean))
        paramGraphics.setColor(new Color(39168));
      i14 = 0;
      i15 = 0;
      do
      {
        do
        {
          double d3 = 0.0D;
          i18 = 0;
          i19 = bool1 ? i15 + i3 : timeTo(arrayOfLong4, i14, i15 + i2);
          if ((arrayOfLong4 != null) && (i19 == arrayOfLong4.length - 1) && (i19 == i14))
            i19++;
          int i20 = i14;
          do
          {
            if (bool4)
              d3 += arrayOfInt5[i20];
            else
              d3 += (bool1 ? arrayOfFloat[i20] : arrayOfBoolean[i20] != 0 ? 0.0F : (float)(k * arrayOfInt5[i20] / ((i20 > 0) && (i20 < arrayOfLong4.length) ? (arrayOfLong4[(i20 + 1)] - arrayOfLong4[(i20 - 1)]) / 2L : i2)));
            i18 += (bool1 ? 1 : arrayOfBoolean[i20] != 0 ? 0 : 1);
            i20++;
            if (i19 < 0)
              break;
          }
          while (i20 < Math.min(bool1 ? n : arrayOfLong4.length, i19));
          if (i18 > 0)
          {
            if (!bool4)
              d3 /= Math.max(i18, 1);
            i20 = i4 + (int)((bool1 ? i14 : (int)(arrayOfLong4[i14] - (bool2 ? 0L : arrayOfLong4[0]))) * d1 / (bool1 ? i3 : i2));
            int i21 = i9 + localRectangle1.height - (int)(localRectangle1.height * d3 / (i8 - i7));
            if (bool4)
              paramGraphics.fillRect(i20 - (int)(d1 / 2.0D), i21, (int)d1, localRectangle1.height - i21 - i9);
            else if (localPoint3 != null)
              paramGraphics.drawLine(localPoint3.x, localPoint3.y, i20, i21);
            if ((!paramBoolean) && (i10 != 0) && (localPoint2 != null) && (localRectangle1.contains(localPoint2)) && ((i6 < 0) || (i6 > Math.abs(localPoint2.x - localRectangle1.x - i20))))
            {
              i6 = Math.abs(localPoint2.x - localRectangle1.x - i20);
              i5 = i14;
            }
            localPoint3 = new Point(i20, i21);
          }
          i14 = i19 == -1 ? i14 : i19;
          i15 += (bool1 ? i3 : i2);
        }
        while ((bool1) && (arrayOfFloat != null) && (i14 < arrayOfFloat.length - 1));
        if ((bool1) || (arrayOfLong4 == null))
          break;
      }
      while (i14 < arrayOfLong4.length - (bool2 ? 0 : 1));
    }
    if (!paramBoolean)
    {
      paramGraphics.setColor(localColor2);
      str1 = 50;
      if ((!bool1) && (bool3) && (arrayOfLong3 != null))
      {
        for (i10 = 0; i10 < arrayOfLong3.length - 1; i10++)
          str1 = Math.max(str1, (int)(arrayOfLong3[(i10 + 1)] - arrayOfLong3[i10]));
        localPoint3 = null;
        for (i12 = 0; (i12 >= 0) && (str1 > 0) && (i12 < arrayOfLong3.length - 1); i12 = i10)
        {
          i10 = timeTo(arrayOfLong3, i12, arrayOfLong3[i12] + i2);
          i13 = 1;
          for (i14 = i12; (i14 < arrayOfLong3.length - 1) && (i14 < i10); i14++)
            i13 = Math.max(i13, (int)(arrayOfLong3[(i14 + 1)] - arrayOfLong3[i14]));
          i14 = i4 + (int)((arrayOfLong3[i12] - arrayOfLong3[0]) * d1 / i2);
          i15 = localRectangle1.height - localRectangle1.height * i13 / str1;
          if (localPoint3 != null)
          {
            paramGraphics.drawLine(localPoint3.x, localPoint3.y, i14, localPoint3.y);
            paramGraphics.drawLine(i14, localPoint3.y, i14, i15);
          }
          if ((localPoint2 != null) && (localRectangle1.contains(localPoint2)) && ((i6 < 0) || (i6 > Math.abs(localPoint2.x - localRectangle1.x - i14))))
          {
            i6 = Math.abs(localPoint2.x - localRectangle1.x - i14);
            i5 = i12;
          }
          localPoint3 = new Point(i14, i15);
        }
      }
      paramGraphics.setClip(-localRectangle1.x + paramInt1, -localRectangle1.y + paramInt2, paramInt3, paramInt4);
      paramGraphics.setColor(Color.gray);
      paramGraphics.drawLine(0, i9 + localRectangle1.height, localRectangle1.width, i9 + localRectangle1.height);
      i10 = (int)(-i4 * (bool1 ? i3 : i2) / d1);
      int i12 = (int)(localRectangle1.width * (bool1 ? i3 : i2) / d1);
      int i13 = i10 + i12;
      int[] arrayOfInt6 = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000 };
      i15 = localFontMetrics.stringWidth("XXXX");
      int i16 = i12 / (localRectangle1.width / i15);
      for (int i17 = 0; i17 < arrayOfInt6.length; i17++)
        if (arrayOfInt6[i17] > i16)
        {
          i16 = arrayOfInt6[i17];
          break;
        }
      i17 = Math.max(0, i16 * (int)Math.ceil(i10 / i16));
      while (i17 <= i13)
      {
        i18 = i4 + (int)(i17 * d1 / (bool1 ? i3 : i2));
        i19 = localFontMetrics.stringWidth(i17);
        paramGraphics.drawLine(i18, localRectangle1.height, i18, localRectangle1.height + 2);
        paramGraphics.drawString(i17, i18 - i19 / 2, localRectangle1.height + localFontMetrics.getAscent() + 2);
        i17 += i16;
      }
      String str2 = RC(bool1 ? TX("graph_voipx") : TX("graph_speedx"));
      i18 = localFontMetrics.stringWidth(str2);
      paramGraphics.drawString(str2, localRectangle1.width / 2 - i18 / 2, localRectangle1.height + localFontMetrics.getAscent() + 2 + localFontMetrics.getHeight());
      Rectangle localRectangle2 = new Rectangle(paramInt1 + localPoint1.x, localRectangle1.y + localRectangle1.height + localPoint1.y, paramInt3, paramInt4 - localRectangle1.y - localRectangle1.height + paramInt2);
      if (bool1)
        addHitRegion("voipaxis", localRectangle2);
      else
        addHitRegion("speedaxis", localRectangle2);
      paramGraphics.setColor(localColor1);
      String str3 = Util.parseString(localProperties.get("yaxislabel"), RC(bool1 ? TX("graph_voipy1") : TX("graph_speedy1")));
      paramGraphics.drawString(str3, -localRectangle1.x + 12, -localFontMetrics.getHeight() + localFontMetrics.getAscent() / 2);
      double[] arrayOfDouble = { 0.1D, 0.2D, 0.5D, 1.0D, 2.0D, 5.0D, 10.0D, 20.0D, 50.0D, 100.0D, 200.0D, 500.0D, 1000.0D, 2000.0D, 5000.0D, 10000.0D, 20000.0D, 50000.0D, 100000.0D, 200000.0D, 500000.0D, 1000000.0D, 2000000.0D, 5000000.0D, 10000000.0D };
      double d4 = (i8 - i7) / (localRectangle1.height / (i + 10));
      for (int i22 = 0; i22 < arrayOfDouble.length; i22++)
        if (arrayOfDouble[i22] > d4)
        {
          d4 = arrayOfDouble[i22];
          break;
        }
      String str5;
      for (double d5 = i7 + Math.abs(i7 % d4); d5 <= i8; d5 += d4)
      {
        str5 = (int)(100.0D * d5) / 100.0D;
        int i26 = i9 + localRectangle1.height - (int)(localRectangle1.height * d5 / (i8 - i7));
        paramGraphics.drawString(str5, -3 - localFontMetrics.stringWidth(str5), i26 + localFontMetrics.getAscent() / 2);
        paramGraphics.drawLine(-2, i26, 0, i26);
      }
      int i28;
      int i29;
      int i30;
      if ((bool1) || ((!bool1) && (bool3) && (str1 > 0)))
      {
        paramGraphics.setColor(localColor2);
        String str4 = RC(bool1 ? TX("graph_voipy2") : TX("graph_speedy2"));
        int i24 = localFontMetrics.stringWidth(str4);
        paramGraphics.drawString(str4, Math.min(localRectangle1.x + paramInt3 - i24, localRectangle1.width - i24 / 2), -localFontMetrics.getHeight() + localFontMetrics.getAscent() / 2);
        str5 = bool1 ? 100 : str1;
        int[] arrayOfInt7 = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000 };
        i28 = str5 / (localRectangle1.height / (i + 10));
        for (i29 = 0; i29 < arrayOfInt7.length; i29++)
          if (arrayOfInt7[i29] > i28)
          {
            i28 = arrayOfInt7[i29];
            break;
          }
        i29 = 0;
        while (i29 <= str5)
        {
          i30 = localRectangle1.height - localRectangle1.height * i29 / str5;
          paramGraphics.drawString(i29, localRectangle1.width + 3, i30 + localFontMetrics.getAscent() / 2);
          paramGraphics.drawLine(localRectangle1.width, i30, localRectangle1.width + 2, i30);
          i29 += i28;
        }
      }
      if (i5 >= 0)
      {
        int i23 = i4 + (int)((bool1 ? i5 : arrayOfLong3[i5] - (bool2 ? 0L : arrayOfLong3[0])) * d1 / (bool1 ? i3 : i2));
        paramGraphics.setColor(Color.red);
        paramGraphics.drawLine(i23 - 1, 0, i23 - 1, paramInt4);
        paramGraphics.drawLine(i23, 0, i23, paramInt4);
        paramGraphics.drawLine(i23 + 1, 0, i23 + 1, paramInt4);
        String[] arrayOfString;
        if (bool1)
        {
          i25 = 0;
          float f = 0.0F;
          i28 = 0;
          i29 = i5;
          do
          {
            i25 += (arrayOfBoolean[i29] != 0 ? 1 : 0);
            f += (arrayOfBoolean[i29] != 0 ? 0.0F : arrayOfFloat[i29]);
            i28++;
            i29++;
            if (i29 >= n)
              break;
          }
          while (i29 < i5 + (bool1 ? i3 : i2));
          arrayOfString = new String[] { RC(TX("jitter")) + TX(": ") + (i28 - i25 > 0 ? (int)(10.0F * f / (i28 - i25)) / 10.0D : "") + TX(" ") + RC(TX("ms")), RC(TX("packetloss")) + TX(": ") + i25 * 100 / i28 + " %", RC(TX("graph_samples")) + TX(": ") + i28 };
        }
        else
        {
          arrayOfString = new String[1 + (m != 0 ? 1 : 0) + (bool3 ? 1 : 0)];
          arrayOfString[0] = (RC(TX("graph_time")) + TX(": ") + (arrayOfLong3[i5] - (bool2 ? 0L : arrayOfLong3[0])) + TX(" ") + RC(TX("ms")));
          i25 = 1;
          if (m != 0)
            arrayOfString[(i25++)] = (Util.parseString(localProperties.get("yaxislabel"), RC(TX("graph_downloaded"))) + TX(": ") + arrayOfInt3[i5] + TX(" ") + RC(TX("bytes")));
          if (bool3)
            arrayOfString[(i25++)] = (RC(TX("graph_pause")) + TX(": ") + (i5 + 1 < arrayOfLong3.length ? arrayOfLong3[(i5 + 1)] - arrayOfLong3[i5] : "--") + TX(" ") + RC(TX("ms")));
        }
        int i25 = i * arrayOfString.length;
        int i27 = 0;
        for (i28 = 0; i28 < arrayOfString.length; i28++)
          i27 = Math.max(i27, localFontMetrics.stringWidth(arrayOfString[i28]));
        i27 += 6;
        i25 += 6;
        i28 = Math.max(0, (paramInt3 - i27) / 2);
        i29 = Math.max(0, (paramInt4 - i25) / 2);
        paramGraphics.setColor(Color.white);
        paramGraphics.fillRect(i28, i29, i27, i25);
        paramGraphics.setColor(Color.black);
        paramGraphics.drawRect(i28, i29, i27, i25);
        for (i30 = 0; i30 < arrayOfString.length; i30++)
          paramGraphics.drawString(arrayOfString[i30], i28 + 3, i29 + i * i30 + localFontMetrics.getAscent() + 3);
      }
    }
    paramGraphics.translate(-localRectangle1.x, -localRectangle1.y);
    paramGraphics.setClip(0, 0, getSize().width, getSize().height);
    localRectangle1.translate(localPoint1.x, localPoint1.y);
    if (!paramBoolean)
      if (bool1)
        addHitRegion("voipgraph", localRectangle1);
      else
        addHitRegion("speedgraph", localRectangle1);
  }

  private int timeTo(long[] paramArrayOfLong, int paramInt, long paramLong)
  {
    for (int i = paramInt; (paramArrayOfLong != null) && (i < paramArrayOfLong.length); i++)
      if (paramArrayOfLong[i] > paramLong)
        return i == paramInt ? -1 : i;
    return paramArrayOfLong.length - 1;
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if ((paramActionEvent.getSource() == this.PH) && (this.PH.getItemCount() > 0))
      repaint();
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.QH = new Point(paramMouseEvent.getX(), paramMouseEvent.getY());
    this.RH = this.KG;
    this.SH = this.MH;
    this.TH = this.LG;
    this.UH = this.NH;
    this.JG = null;
    super.mousePressed(paramMouseEvent);
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    String str = getHitRegion(localPoint.x, localPoint.y);
    if (("speedgraph".equals(str)) || ("voipgraph".equals(str)))
    {
      this.JG = new Point(localPoint.x, localPoint.y);
      repaint();
    }
    else if (this.JG != null)
    {
      this.JG = null;
      repaint();
    }
    setCursor(new Cursor(str != null ? 12 : ("speedaxis".equals(str)) || ("voipaxis".equals(str)) ? 11 : 0));
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    if (this.QH != null)
    {
      String str1 = getHitRegion(localPoint.x, localPoint.y);
      String str2 = getHitRegion(this.QH.x, this.QH.y);
      int i;
      int j;
      if ("speedaxis".equals(str1))
      {
        i = (localPoint.x - this.QH.x) / 40;
        j = Math.max(1, this.RH + i);
        if (this.KG != j)
        {
          this.KG = j;
          repaint();
        }
      }
      else if ("voipaxis".equals(str1))
      {
        i = (localPoint.x - this.QH.x) / 40;
        j = Math.max(1, this.TH + i);
        if (this.LG != j)
        {
          this.LG = j;
          repaint();
        }
      }
      else if ("speedgraph".equals(str2))
      {
        this.MH = (this.SH - this.QH.x + localPoint.x);
        repaint();
      }
      else if ("voipgraph".equals(str2))
      {
        this.NH = (this.UH - this.QH.x + localPoint.x);
        repaint();
      }
    }
    super.mouseDragged(paramMouseEvent);
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.GraphTab
 * JD-Core Version:    0.6.2
 */