package mcsaplugins.capacity;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class CapacityTab extends AppletTab
  implements Runnable, ActionListener, AdjustmentListener
{
  private capacity m_plugin;
  private JButton m_start;
  private JScrollBar m_scroll;
  private boolean m_bHaveScrolled = false;
  private Thread m_tAnimation;
  private boolean m_bRunAnimation = false;
  private long m_lDemoDCapacity;
  private long m_lDemoUCapacity;

  public CapacityTab(Applet paramApplet, capacity paramcapacity)
  {
    super(paramApplet, paramcapacity, paramcapacity.getImage("tabcapacity.gif"), "capacity");
    this.m_plugin = paramcapacity;
  }

  public void doFirstTimeInit()
  {
    setLayout(null);
    this.m_start = new JButton(RC(TX("start")));
    this.m_scroll = new JScrollBar(1);
    add(this.m_start);
    add(this.m_scroll);
    this.m_start.setSize(this.m_start.getPreferredSize());
    this.m_start.addActionListener(this);
    this.m_scroll.addAdjustmentListener(this);
    if (!"no".equals(RC(TX("loadanimation"))))
    {
      this.m_bRunAnimation = true;
      this.m_tAnimation = new Thread(this, TX("MCS-CapacityAnimation"));
      this.m_tAnimation.start();
    }
  }

  public void reset()
  {
    this.m_plugin.reset();
  }

  public void stopAnimation()
  {
    this.m_bRunAnimation = false;
    try
    {
      this.m_tAnimation.join();
    }
    catch (Exception localException)
    {
    }
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    Font localFont1 = paramGraphics.getFont();
    Font localFont2 = new Font(localFont1.getFamily(), localFont1.getStyle() | 0x1, localFont1.getSize());
    paramGraphics.setFont(localFont2);
    FontMetrics localFontMetrics1 = paramGraphics.getFontMetrics();
    int k = localFontMetrics1.getHeight();
    int m = localFontMetrics1.getAscent();
    int n = localFontMetrics1.getDescent();
    paramGraphics.setColor(Color.white);
    paramGraphics.fillRect(0, 0, i, j);
    paramGraphics.setColor(Color.black);
    paramGraphics.setFont(localFont1);
    FontMetrics localFontMetrics2 = paramGraphics.getFontMetrics();
    int i1 = localFontMetrics2.getHeight();
    int i2 = localFontMetrics2.getAscent();
    int i3 = localFontMetrics2.getDescent();
    CapacityTest localCapacityTest = (CapacityTest)this.m_plugin.getTest();
    long[] arrayOfLong1 = localCapacityTest == null ? null : localCapacityTest.getDownloadAchievedBandwidths();
    long[] arrayOfLong2 = localCapacityTest == null ? null : localCapacityTest.getDownloadTargetBandwidths();
    float[] arrayOfFloat1 = localCapacityTest == null ? null : localCapacityTest.getDownloadLosses();
    int i4 = (arrayOfLong1 == null) || (arrayOfLong2 == null) || (arrayOfFloat1 == null) ? 0 : Math.min(arrayOfLong1.length, Math.min(arrayOfLong2.length, arrayOfFloat1.length));
    long[] arrayOfLong3 = localCapacityTest == null ? null : localCapacityTest.getUploadAchievedBandwidths();
    long[] arrayOfLong4 = localCapacityTest == null ? null : localCapacityTest.getUploadTargetBandwidths();
    float[] arrayOfFloat2 = localCapacityTest == null ? null : localCapacityTest.getUploadLosses();
    int i5 = (arrayOfLong3 == null) || (arrayOfLong4 == null) || (arrayOfFloat2 == null) ? 0 : Math.min(arrayOfLong3.length, Math.min(arrayOfLong4.length, arrayOfFloat2.length));
    long l1 = localCapacityTest == null ? this.m_lDemoDCapacity : localCapacityTest.getDownloadSpeed();
    long l2 = localCapacityTest == null ? this.m_lDemoUCapacity : localCapacityTest.getUploadSpeed();
    int i6 = localCapacityTest == null ? 1000 : localCapacityTest.getPacketSize();
    int i7 = localCapacityTest == null ? -1 : localCapacityTest.getState();
    long l3 = Math.min(l1, l2);
    long l4 = Math.max(l1, l2);
    for (int i8 = 0; i8 < i4; i8++)
    {
      l3 = arrayOfLong1[i8] == 0L ? l3 : Math.min(l3 == 0L ? arrayOfLong1[i8] : l3, arrayOfLong1[i8]);
      l4 = arrayOfLong1[i8] == 0L ? l4 : Math.max(l4, arrayOfLong1[i8]);
    }
    for (i8 = 0; i8 < i5; i8++)
    {
      l3 = arrayOfLong3[i8] == 0L ? l3 : Math.min(l3 == 0L ? arrayOfLong3[i8] : l3, arrayOfLong3[i8]);
      l4 = arrayOfLong3[i8] == 0L ? l4 : Math.max(l4, arrayOfLong3[i8]);
    }
    l4 = localCapacityTest == null ? 12500000L : Math.max(l4 + 12500L, ()(l4 * 1.1D));
    l3 = localCapacityTest == null ? 0L : Math.max(0L, Math.min(l3 - 12500L, l3 - ()(l4 * 0.1D)));
    i8 = 5;
    if (localCapacityTest == null)
    {
      this.m_start.setVisible(true);
      this.m_start.setLocation(5, i8);
      this.m_start.setSize(this.m_start.getPreferredSize());
      i8 += this.m_start.getSize().height + 5;
    }
    else
    {
      this.m_start.setVisible(false);
    }
    int i9 = localFontMetrics2.stringWidth("X" + U.bps2s(1234567890L));
    int i10 = localFontMetrics2.stringWidth(RC(TX("capacity.ppsterse"), "XXXXX"));
    int i11 = localFontMetrics1.stringWidth("X" + U.bps2s(1234567890L));
    int i12 = localFontMetrics2.stringWidth(RC(TX("capacity.tableloss"), TX("XXX.X")));
    int i13 = 5;
    int i14 = i13 + Math.max(i10, i9) + 5;
    int i15 = i14 + Math.max(i11 + i10, i12) + 5;
    Rectangle localRectangle = new Rectangle(i15 + 3, i8 + i1 + 6, i - i15 - 3 - (this.m_scroll.isVisible() ? this.m_scroll.getPreferredSize().width : 1), j - i8 - 3 - i1 * 2 - 6);
    int i16;
    int i18;
    int i20;
    if ((l1 > 0L) || (i7 == 2))
    {
      paramGraphics.setFont(localFont2);
      i16 = i7 == 2 ? localCapacityTest.getPercentComplete() : -1;
      i18 = drawFeaturedResult(paramGraphics, localFont2, localFont1, 5, i15, i8, l1, i6, i16, "Download Capacity") + 5;
      i20 = Math.min(i18, Math.max(i18 / 2, i1 + 10));
      drawGraphLine(paramGraphics, l1, localRectangle.x, localRectangle.x + localRectangle.width, i8 + i18 / 2 - i20 / 2, i20, l3, l4, new Color(3833035), new Color(2907545));
      i8 += i18;
      paramGraphics.setFont(localFont1);
    }
    if ((l2 > 0L) || (i7 == 3))
    {
      paramGraphics.setFont(localFont2);
      i16 = i7 == 3 ? localCapacityTest.getPercentComplete() : -1;
      i18 = drawFeaturedResult(paramGraphics, localFont2, localFont1, 5, i15, i8, l2, i6, i16, "Upload Capacity") + 5;
      i20 = Math.min(i18, Math.max(i18 / 2, i1 + 10));
      drawGraphLine(paramGraphics, l2, localRectangle.x, localRectangle.x + localRectangle.width, i8 + i18 / 2 - i20 / 2, i20, l3, l4, new Color(3833035), new Color(2907545));
      i8 += i18;
      paramGraphics.setFont(localFont1);
    }
    localRectangle = new Rectangle(i15 + 3, i8 + i1 + 6, i - i15 - 3 - (this.m_scroll.isVisible() ? this.m_scroll.getPreferredSize().width : 0), j - i8 - 3 - i1 * 2 - 6);
    Object localObject;
    long l6;
    if (l4 > l3)
    {
      paramGraphics.setColor(new Color(2907545));
      localObject = new long[] { 125000000L, 12500000L, 1250000L, 125000L, 12500L, 1250L, 125L };
      long l5 = 0L;
      for (int i22 = 0; i22 < localObject.length; i22++)
        if (localObject[i22] * 1.5D < l4 - l3)
        {
          l5 = localObject[i22];
          break;
        }
      if (l5 > 0L)
      {
        l6 = l3 - l3 % l5;
        String str2 = "Data Rate";
        paramGraphics.setFont(localFont2);
        paramGraphics.drawString(str2, localRectangle.x, localRectangle.y - n - 3);
        paramGraphics.setFont(localFont1);
        paramGraphics.drawLine(localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y);
        int i24 = localRectangle.x + localFontMetrics2.stringWidth(str2) + 6;
        while (l6 < l4)
        {
          if (l6 >= l3)
          {
            int i25 = getX(l6, localRectangle, l3, l4);
            String str4 = U.bps2s(l6 * 8L);
            int i27 = localFontMetrics2.stringWidth(str4);
            paramGraphics.drawLine(i25, localRectangle.y - 3, i25, localRectangle.y);
            if (i25 - i27 / 2 > i24)
            {
              paramGraphics.drawString(str4, i25 - i27 / 2, localRectangle.y - i3 - 3);
              i24 = i25 + i27 / 2 + 10;
            }
          }
          l6 += l5;
        }
      }
    }
    if (localCapacityTest != null)
    {
      paramGraphics.setColor(new Color(15186360));
      paramGraphics.setFont(localFont2);
      paramGraphics.drawString("Packet Loss", i15, localRectangle.y + localRectangle.height + m + 3);
      paramGraphics.setFont(localFont1);
      paramGraphics.drawLine(localRectangle.x, localRectangle.y + localRectangle.height, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height);
      localObject = "50%";
      paramGraphics.drawString((String)localObject, localRectangle.x + localRectangle.width / 2 - localFontMetrics2.stringWidth((String)localObject) / 2, localRectangle.y + localRectangle.height + i2 + 3);
      paramGraphics.drawLine(localRectangle.x + localRectangle.width / 2, localRectangle.y + localRectangle.height, localRectangle.x + localRectangle.width / 2, localRectangle.y + localRectangle.height + 3);
      String str1 = "100%";
      paramGraphics.drawString(str1, localRectangle.x + localRectangle.width - localFontMetrics2.stringWidth(str1), localRectangle.y + localRectangle.height + i2 + 3);
      paramGraphics.drawLine(localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height + 3);
    }
    if (localCapacityTest != null)
    {
      i8 += 3;
      Util.gradientFill(paramGraphics, i13, i8 - 3, i15 - i13, i1 + 6, new Color(16383229), new Color(13884393));
      paramGraphics.setColor(Color.black);
      paramGraphics.drawString("Target", i13 + 3, i8 + i2);
      paramGraphics.drawString("Achieved", i14 + 3, i8 + i2);
      int i17 = Math.min(localRectangle.y + localRectangle.height - 2, i8 + i1 + 2 + ((i4 + i5) * (i1 + k + 5) - 3) + (i4 > 0 ? k : 0) + (i5 > 0 ? k : 0));
      paramGraphics.setColor(new Color(6851759));
      paramGraphics.drawLine(i13, i8, i15, i8);
      paramGraphics.setColor(new Color(10401486));
      paramGraphics.drawLine(i13, i8 + i1 + 3, i15, i8 + i1 + 3);
      paramGraphics.drawLine(i13, i8, i13, i8 + i1 + 3);
      paramGraphics.drawLine(i14, i8, i14, i8 + i1 + 3);
      paramGraphics.drawLine(i15, i8, i15, i8 + i1 + 3);
      paramGraphics.setColor(new Color(13686757));
      paramGraphics.drawLine(i13, i8 + i1 + 3, i13, i17);
      paramGraphics.drawLine(i14, i8 + i1 + 3, i14, i17);
      paramGraphics.drawLine(i15, i8 + i1 + 3, i15, i17);
      paramGraphics.setColor(Color.black);
      i8 += i1 + 3;
    }
    paramGraphics.clipRect(0, localRectangle.y + 1, i, localRectangle.height - 2);
    Point localPoint = new Point(0, -this.m_scroll.getValue());
    paramGraphics.translate(localPoint.x, localPoint.y);
    for (int i19 = 0; i19 < i4 + i5; i19++)
    {
      paramGraphics.setFont(localFont2);
      if (((i19 == 0) && (i4 > 0)) || ((i19 == i4) && (i5 > 0)))
      {
        i20 = (i19 == 0) && (i4 > 0) ? 1 : 0;
        paramGraphics.setColor(Color.white);
        paramGraphics.fillRect(i13 + 1, i8 + 1 - (i19 > 0 ? 3 : 0), i15 - i13 - 1, k - 1 + (i19 > 0 ? 3 : 0));
        paramGraphics.setColor(new Color(6851759));
        paramGraphics.drawString(i20 != 0 ? "Downstream results" : "Upstream results", i13 + 3, i8 + m);
        i8 += k;
        paramGraphics.setColor(new Color(13686757));
        paramGraphics.drawLine(i13, i8, i15, i8);
      }
      paramGraphics.setFont(localFont1);
      float f = i19 >= i4 ? arrayOfFloat2[(i19 - i4)] : arrayOfFloat1[i19];
      l6 = i19 >= i4 ? arrayOfLong3[(i19 - i4)] : arrayOfLong1[i19];
      long l7 = i19 >= i4 ? arrayOfLong4[(i19 - i4)] : arrayOfLong2[i19];
      String str3 = RC(TX("capacity.tableloss"), (int)(f * 10.0F) / 10.0F);
      int i26 = localFontMetrics2.stringWidth(str3);
      String str5 = l6 > 0L ? U.bps2s(l6 * 8L) : null;
      int i28 = str5 == null ? 0 : localFontMetrics1.stringWidth(str5);
      String str6 = TX(" ") + RC(TX("capacity.ppsterse"), new StringBuilder().append(l6 / i6).toString());
      int i29 = str6 == null ? 0 : localFontMetrics2.stringWidth(str6);
      String str7 = U.bps2s(l7 * 8L);
      String str8 = TX(" ") + RC(TX("capacity.ppsterse"), new StringBuilder().append(l7 / i6).toString());
      paramGraphics.setColor(new Color(13421772));
      paramGraphics.drawString(str7, i13 + 3, i8 + i2 + (str8 == null ? i1 / 2 : 0));
      if (str8 != null)
        paramGraphics.drawString(str8, i13 + 3, i8 + i2 + i1);
      paramGraphics.setColor(new Color(15186360));
      paramGraphics.drawString(str3, i15 - i26 - 3, i8 + i2);
      paramGraphics.setFont(localFont2);
      paramGraphics.setColor(new Color(2907545));
      if (str5 != null)
        paramGraphics.drawString(str5, i15 - i28 - i29 - 3, i8 + m + i1);
      paramGraphics.setFont(localFont1);
      if (str6 != null)
        paramGraphics.drawString(str6, i15 - i29 - 3, i8 + m + i1);
      drawGraphLine(paramGraphics, ()(f * 10.0F), localRectangle.x, localRectangle.x + localRectangle.width, i8 + 6, i1 - 6, 0L, 1000L, new Color(16246503), new Color(15186360));
      drawGraphLine(paramGraphics, l6, localRectangle.x, localRectangle.x + localRectangle.width, i8 + i1, k, l3, l4, new Color(3833035), new Color(2907545));
      i8 += i1 + k + 5;
      paramGraphics.setColor(new Color(13686757));
      paramGraphics.drawLine(i13, i8 - 3, i15, i8 - 3);
      paramGraphics.setColor(Color.black);
    }
    Dimension localDimension = this.m_scroll.getPreferredSize();
    int i21 = this.m_scroll.getValue();
    this.m_scroll.setSize(localDimension.width, localRectangle.height - 1);
    this.m_scroll.setLocation(localRectangle.x + localRectangle.width, localRectangle.y + 1);
    int i23 = this.m_bHaveScrolled ? i21 : Math.max(0, i8 - localRectangle.y - 1 - localRectangle.height);
    this.m_scroll.setValues(i23, localRectangle.height, 0, i8 - localRectangle.y - 1);
    boolean bool = i8 >= localRectangle.y + localRectangle.height;
    if (bool != this.m_scroll.isVisible())
    {
      this.m_scroll.setVisible(bool);
      repaint();
    }
    if ((bool) && (i21 != i23))
      repaint();
    paramGraphics.translate(-localPoint.x, -localPoint.y);
    paramGraphics.setClip(null);
  }

  private void drawPercentDoneBar(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Color localColor = paramGraphics.getColor();
    paramGraphics.setColor(new Color(10526880));
    paramGraphics.fillRect(paramInt1 - 1, paramInt2 - 1, paramInt3 + 1 + 1 + 1, 3);
    paramGraphics.setColor(new Color(14737632));
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2);
    paramGraphics.setColor(new Color(4210943));
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3 * paramInt4 / 100, paramInt2);
    paramGraphics.setColor(localColor);
  }

  private static int getX(long paramLong1, Rectangle paramRectangle, long paramLong2, long paramLong3)
  {
    double d = (paramLong1 - paramLong2) / (paramLong3 - paramLong2);
    return (int)(paramRectangle.width * d) + paramRectangle.x;
  }

  private void drawGraphLine(Graphics paramGraphics, long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong2, long paramLong3, Color paramColor1, Color paramColor2)
  {
    Color localColor = paramGraphics.getColor();
    int i = paramLong3 > paramLong2 ? (int)((paramLong1 - paramLong2) * (paramInt2 - paramInt1) / (paramLong3 - paramLong2)) : (paramInt2 - paramInt1) / 2;
    if (i > 0)
    {
      paramGraphics.setColor(paramColor2);
      Util.horzGradientFill(paramGraphics, paramInt1, paramInt3, i + 1, paramInt4, paramColor1, paramColor2);
      Util.drawShadow(paramGraphics, paramInt1, paramInt3, i, paramInt4);
      paramGraphics.setColor(localColor);
    }
  }

  private int drawFeaturedResult(Graphics paramGraphics, Font paramFont1, Font paramFont2, int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4, int paramInt5, String paramString)
  {
    paramGraphics.setFont(paramFont2);
    FontMetrics localFontMetrics1 = paramGraphics.getFontMetrics();
    paramGraphics.setFont(paramFont1);
    FontMetrics localFontMetrics2 = paramGraphics.getFontMetrics();
    int i = localFontMetrics2.getHeight();
    Util.gradientFill(paramGraphics, paramInt1, paramInt3, paramInt2 - paramInt1, i * 2 + 10, new Color(14872063), new Color(10929919));
    paramGraphics.setColor(new Color(5930683));
    paramGraphics.drawRect(paramInt1, paramInt3, paramInt2 - paramInt1, i * 2 + 10);
    paramGraphics.setColor(Color.black);
    int j = paramInt3 + 5 + localFontMetrics2.getAscent();
    paramGraphics.drawString(paramString, paramInt1 + 5, j);
    if (paramInt5 >= 0)
    {
      int k = paramInt1 + 5 + 5 + localFontMetrics2.stringWidth(paramString);
      if (paramInt2 - k > 5)
        drawPercentDoneBar(paramGraphics, k, j - localFontMetrics2.getAscent() + localFontMetrics2.getHeight() / 2, paramInt2 - k - 5, paramInt5);
    }
    j += i;
    if (paramLong > 0L)
    {
      String str1 = U.bps2s(paramLong * 8L) + TX(" ");
      String str2 = paramInt4 == 0 ? null : RC(TX("capacity.ppsverbose"), paramLong / paramInt4);
      int m = str2 == null ? 0 : localFontMetrics1.stringWidth(str2);
      paramGraphics.drawString(str1, paramInt2 - localFontMetrics2.stringWidth(str1) - m - 5, j);
      paramGraphics.setFont(paramFont2);
      if (str2 != null)
        paramGraphics.drawString(str2, paramInt2 - m - 5, j);
    }
    j += i;
    return i * 2 + 10;
  }

  public void run()
  {
    try
    {
      long l1 = 4000L;
      long l2 = 12500000L;
      long l3 = 1250L;
      long l4 = System.currentTimeMillis();
      while (this.m_bRunAnimation)
      {
        long l5 = System.currentTimeMillis();
        this.m_lDemoDCapacity = Math.abs((l2 - l3) / l1 * ((l5 - l4) % (l1 * 2L)) - l2 + l3);
        this.m_lDemoUCapacity = Math.abs((l2 - l3) / l1 * ((l5 - l4 + l1 / 2L) % (l1 * 2L)) - l2 + l3);
        repaint();
        try
        {
          Thread.sleep(25L);
        }
        catch (Exception localException)
        {
        }
      }
    }
    finally
    {
      this.m_tAnimation = null;
      this.m_lDemoDCapacity = 0L;
      this.m_lDemoUCapacity = 0L;
    }
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_start)
      fireActionEvent(new ActionEvent(this, 1001, "start"));
  }

  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
  {
    this.m_bHaveScrolled = true;
    repaint();
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.capacity.CapacityTab
 * JD-Core Version:    0.6.2
 */