package mcsaplugins.capacity;

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
import javax.swing.JComboBox;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class LossProfileTab extends AppletTab
  implements ActionListener
{
  private int m_nMouseOverTest = -1;
  private Point m_mousePos;
  private boolean m_bShowLegend;
  private int m_nToggleLegendState;
  private capacity m_plugin;
  private JComboBox m_updown = new JComboBox();
  private boolean m_bShowUpDown = false;

  public LossProfileTab(Applet paramApplet, capacity paramcapacity)
  {
    super(paramApplet, paramcapacity, paramcapacity.getImage("tablosses.gif"), "losses");
    this.m_plugin = paramcapacity;
    this.m_bShowLegend = TX("yes").equals(TX(this.m_plugin.RC("capacity.showlosslegend")));
    setFindClosestHitRegion(true);
    this.m_updown.setVisible(false);
    setLayout(null);
    add(this.m_updown);
    this.m_updown.addActionListener(this);
  }

  public void reset()
  {
  }

  public void doFirstTimeInit()
  {
  }

  public void updateUpDownBox()
  {
    int i = this.m_updown.getSelectedIndex();
    this.m_updown.removeAllItems();
    long[] arrayOfLong1 = new long[0];
    long[] arrayOfLong2 = new long[0];
    if (arrayOfLong1 != null)
      this.m_updown.addItem(this.m_plugin.RC(TX("capacity.losstabdownload")));
    if (arrayOfLong2 != null)
      this.m_updown.addItem(this.m_plugin.RC(TX("capacity.losstabupload")));
    if ((arrayOfLong1 != null) && (arrayOfLong2 != null))
    {
      this.m_updown.setSize(this.m_updown.getPreferredSize());
      this.m_updown.setSelectedIndex(i >= 0 ? i : 0);
      this.m_updown.setVisible(true);
      this.m_bShowUpDown = true;
    }
    else
    {
      if (this.m_updown.getItemCount() > 0)
        this.m_updown.setSelectedIndex(0);
      this.m_updown.setVisible(false);
      this.m_bShowUpDown = false;
    }
    repaint();
  }

  public void panelPaint(Graphics paramGraphics)
  {
    CapacityTest localCapacityTest = (CapacityTest)this.m_plugin.getTest();
    String str1 = (String)this.m_updown.getSelectedItem();
    int i = (str1 != null) && (!str1.equals(this.m_plugin.RC(TX("capacity.losstabdownload")))) ? 0 : 1;
    long[] arrayOfLong1 = i != 0 ? localCapacityTest.getDownloadTargetBandwidths() : localCapacityTest == null ? null : localCapacityTest.getUploadTargetBandwidths();
    long[] arrayOfLong2 = i != 0 ? localCapacityTest.getDownloadAchievedBandwidths() : localCapacityTest == null ? null : localCapacityTest.getUploadAchievedBandwidths();
    float[] arrayOfFloat = i != 0 ? localCapacityTest.getDownloadLosses() : localCapacityTest == null ? null : localCapacityTest.getUploadLosses();
    int j = getSize().width;
    int k = getSize().height;
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int m = localFontMetrics.getHeight();
    Color localColor1 = new Color(153);
    Color localColor2 = new Color(34816);
    paramGraphics.setColor(Color.white);
    paramGraphics.fillRect(0, 0, j, k);
    paramGraphics.setColor(Color.black);
    long l1 = 0L;
    long l2 = 0L;
    for (int n = 0; (arrayOfLong1 != null) && (n < arrayOfLong1.length); n++)
    {
      l1 = Math.max(arrayOfLong1[n] * 8L, l1);
      l2 = arrayOfLong1[n] == 0L ? l2 : Math.min(arrayOfLong1[n] * 8L, l2 == 0L ? 9223372036854775807L : l2);
    }
    for (n = 0; (arrayOfLong2 != null) && (n < arrayOfLong2.length); n++)
    {
      l1 = Math.max(arrayOfLong2[n] * 8L, l1);
      l2 = arrayOfLong2[n] == 0L ? l2 : Math.min(arrayOfLong2[n] * 8L, l2 == 0L ? 9223372036854775807L : l2);
    }
    float f1 = 0.0F;
    for (int i1 = 0; (arrayOfFloat != null) && (i1 < arrayOfFloat.length); i1++)
      f1 = Math.max(arrayOfFloat[i1], f1);
    if (f1 == 0.0F)
      f1 = 100.0F;
    if ((l1 > 0L) && (arrayOfLong1 != null) && (arrayOfLong2 != null) && (arrayOfFloat != null))
    {
      i1 = Math.min(Math.min(arrayOfLong1.length, arrayOfLong2.length), arrayOfFloat.length);
      l2 = ()(0.9D * l2);
      l1 = ()(1.1D * l1);
      f1 = Math.min(100.0F, 1.1F * f1);
      String str2 = this.m_plugin.RC(TX("capacity.rateattempted"));
      String str3 = this.m_plugin.RC(TX("capacity.rateachieved"));
      int i2 = Math.max(localFontMetrics.stringWidth(str2), localFontMetrics.stringWidth(str3)) + 20 + 10 + 6;
      String str4 = this.m_plugin.RC(TX("capacity.losstabinstruction"));
      int i3 = drawWrappedLines(null, str4, 0, 0, i2 - 10, paramGraphics.getFont()).height;
      Object localObject1 = this.m_bShowLegend ? new Rectangle(j - i2 - 10, 10, i2, m * 3 + 5 + 7 + i3 + 8) : null;
      Rectangle localRectangle1 = new Rectangle(0, 0, 0, 0);
      if (this.m_bShowUpDown)
      {
        localObject2 = this.m_plugin.RC(TX("capacity.losstabplot"));
        int i4 = 2;
        i5 = this.m_updown.getSize().height;
        paramGraphics.drawString((String)localObject2, i4, k - 2 - i5 / 2 - localFontMetrics.getDescent() + m / 2);
        i4 += 5 + localFontMetrics.stringWidth((String)localObject2);
        this.m_updown.setLocation(i4, k - 2 - i5);
        i4 += this.m_updown.getSize().width;
        i6 = Math.min(k - 2 - i5 / 2 - m / 2, k - 2 - i5);
        localRectangle1 = new Rectangle(2, i6, i4 - 2, k - 2 - i6);
      }
      paramGraphics.drawString(this.m_plugin.RC(TX("capacity.losstabyaxis")), 2, localFontMetrics.getAscent() + 2);
      Object localObject2 = { 10.0F, 5.0F, 1.0F, 0.1F };
      float f2 = 0.0F;
      for (int i5 = 0; i5 < localObject2.length; i5++)
        if (localObject2[i5] * 1.5D < f1)
        {
          f2 = localObject2[i5];
          break;
        }
      i5 = localFontMetrics.stringWidth("XXXX") + 5;
      int i6 = m * 2 + 10 + localRectangle1.height;
      int i7 = m + 2 + 2;
      Rectangle localRectangle2 = new Rectangle(i5, i7, (localObject1 == null ? j : localObject1.x) - i5 - 10, k - i7 - i6);
      paramGraphics.drawLine(localRectangle2.x, localRectangle2.y, localRectangle2.x, localRectangle2.y + localRectangle2.height);
      if (f2 > 0.0F)
      {
        float f3 = 0.0F;
        i8 = k;
        while (f3 < f1)
        {
          int i9 = getY(f3, localRectangle2, f1);
          String str6 = (int)f3;
          int i10 = localFontMetrics.stringWidth(str6);
          paramGraphics.drawLine(localRectangle2.x - 3, i9, localRectangle2.x, i9);
          if (i9 - m < i8)
          {
            paramGraphics.drawString(str6, localRectangle2.x - i10 - 3, i9 + m / 2 - localFontMetrics.getDescent());
            paramGraphics.setColor(new Color(15658734));
            paramGraphics.drawLine(localRectangle2.x + 1, i9, localRectangle2.x + localRectangle2.width, i9);
            paramGraphics.setColor(Color.black);
            i8 = i9 + m / 2;
          }
          f3 += f2;
        }
      }
      String str5 = this.m_plugin.RC(TX("capacity.losstabxaxis"));
      int i8 = localRectangle2.x + localRectangle2.width / 2 - localFontMetrics.stringWidth(str5) / 2;
      paramGraphics.drawString(str5, i8, localRectangle2.y + localRectangle2.height + m + localFontMetrics.getAscent() + 4);
      paramGraphics.drawLine(localRectangle2.x, localRectangle2.y + localRectangle2.height, localRectangle2.x + localRectangle2.width, localRectangle2.y + localRectangle2.height);
      long[] arrayOfLong3 = { 1000000000L, 100000000L, 10000000L, 1000000L, 100000L, 10000L, 1000L };
      long l3 = 0L;
      for (int i11 = 0; i11 < arrayOfLong3.length; i11++)
        if (arrayOfLong3[i11] * 1.5D < l1 - l2)
        {
          l3 = arrayOfLong3[i11];
          break;
        }
      int i16;
      int i18;
      if (l3 > 0L)
      {
        long l4 = l2 - l2 % l3;
        int i14 = 0;
        while (l4 < l1)
        {
          if (l4 >= l2)
          {
            i16 = getX(l4, localRectangle2, l2, l1);
            String str8 = l4;
            i18 = localFontMetrics.stringWidth(str8);
            paramGraphics.drawLine(i16, localRectangle2.y + localRectangle2.height, i16, localRectangle2.y + localRectangle2.height + 3);
            if (i16 - i18 / 2 > i14)
            {
              paramGraphics.drawString(str8, i16 - i18 / 2, localRectangle2.y + localRectangle2.height + localFontMetrics.getAscent() + 4);
              i14 = i16 + i18 / 2 + 10;
            }
          }
          l4 += l3;
        }
      }
      String str7;
      int i13;
      Rectangle localRectangle3;
      if (localObject1 == null)
      {
        str7 = this.m_plugin.RC(TX("capacity.losstablegend"));
        i13 = localFontMetrics.stringWidth(str7);
        localRectangle3 = new Rectangle(j - i13 - 3 - 8, k - 2 - m - 4, i13 + 8, m + 4);
        Util.drawOrangeButton(paramGraphics, localRectangle3.x, localRectangle3.y, localRectangle3.width, localRectangle3.height, this.m_nToggleLegendState);
        i16 = this.m_nToggleLegendState == 2 ? 2 : 0;
        paramGraphics.drawString(str7, localRectangle3.x + 4, localRectangle3.y + localRectangle3.height - localFontMetrics.getDescent() - 2 + i16);
        addHitRegion("togglelegend", localRectangle3);
      }
      else
      {
        Util.gradientFill(paramGraphics, localObject1.x, localObject1.y, localObject1.width, localObject1.height, new Color(14872063), new Color(10929919));
        paramGraphics.setColor(new Color(5930683));
        paramGraphics.drawRect(localObject1.x, localObject1.y, localObject1.width, localObject1.height);
        Util.drawShadow(paramGraphics, localObject1.x, localObject1.y, localObject1.width, localObject1.height);
        str7 = this.m_plugin.RC(TX("capacity.losstablegend"));
        i13 = localObject1.y + localFontMetrics.getAscent() + 2;
        paramGraphics.setColor(Color.black);
        paramGraphics.drawString(str7, localObject1.x + 10, i13);
        paramGraphics.drawLine(localObject1.x + 10, i13 + 1, localObject1.x + 10 + localFontMetrics.stringWidth(str7), i13 + 1);
        localRectangle3 = new Rectangle(localObject1.x + localObject1.width - 17, localObject1.y + 2, 15, 15);
        Util.drawOrangeButton(paramGraphics, localRectangle3.x, localRectangle3.y, localRectangle3.width, localRectangle3.height, this.m_nToggleLegendState);
        i16 = this.m_nToggleLegendState == 2 ? 2 : 0;
        drawAchievedBlock(paramGraphics, localRectangle3.x + localRectangle3.width / 2, localRectangle3.y + localRectangle3.height / 2 + i16, 2, 3);
        addHitRegion("togglelegend", localRectangle3);
        i13 += m + 2;
        paramGraphics.setColor(localColor1);
        drawTargetBlock(paramGraphics, localObject1.x + 10, i13 + localFontMetrics.getDescent() - m / 2, 3, 3);
        paramGraphics.setColor(Color.black);
        paramGraphics.drawString(str2, localObject1.x + 10 + 10 + 3, i13);
        i13 += m + 2;
        paramGraphics.setColor(localColor2);
        drawAchievedBlock(paramGraphics, localObject1.x + 10, i13 + localFontMetrics.getDescent() - m / 2, 3, 3);
        paramGraphics.setColor(Color.black);
        paramGraphics.drawString(str3, localObject1.x + 10 + 10 + 3, i13);
        i13 += m + 2;
        paramGraphics.setColor(new Color(5930683));
        paramGraphics.drawLine(localObject1.x + 5, i13 - m / 2 - 3, localObject1.x + localObject1.width - 5, i13 - m / 2 - 3);
        paramGraphics.setColor(Color.black);
        drawWrappedLines(paramGraphics, str4, localObject1.x + 5, i13 - m + 7, localObject1.width - 10);
      }
      for (int i12 = 0; i12 <= i1; i12++)
      {
        i13 = i12 < i1 ? i12 : this.m_nMouseOverTest;
        if (i13 >= 0)
        {
          int i15 = getX(arrayOfLong1[i13] * 8L, localRectangle2, l2, l1);
          i16 = getX(arrayOfLong2[i13] * 8L, localRectangle2, l2, l1);
          int i17 = getY(arrayOfFloat[i13], localRectangle2, f1);
          i18 = i13 == this.m_nMouseOverTest ? 1 : 0;
          int i19 = arrayOfLong2[i13] > 0L ? 1 : 0;
          if ((i18 == 0) || (i12 >= i1))
          {
            paramGraphics.setColor(i18 != 0 ? Color.red : Color.black);
            int i20 = 3;
            if (i19 != 0)
            {
              paramGraphics.setColor(new Color(11184810));
              drawHorzLine(paramGraphics, i15 + (i15 > i16 ? -i20 : i20), i16, i17, i18 != 0 ? 3 : 1);
            }
            paramGraphics.setColor(localColor1);
            drawTargetBlock(paramGraphics, i15, i17, i20, i18 != 0 ? 3 : 2);
            paramGraphics.setColor(localColor2);
            if (i19 != 0)
              drawAchievedBlock(paramGraphics, i16, i17, i20, i18 != 0 ? 3 : 1);
            if (i19 != 0)
            {
              addHitRegion("rect" + i13, new Rectangle(Math.min(i15, i16) - i20 * 2, i17 - 6, Math.abs(i15 - i16) + 4 * i20, 12));
              addHitRegion("rect" + i13, new Rectangle(i16 - i20, i17 - i20, 2 * i20, 2 * i20));
            }
            addHitRegion("rect" + i13, new Rectangle(i15 - i20, i17 - i20, 2 * i20, 2 * i20));
            if (i18 != 0)
            {
              String str9 = this.m_plugin.RC(TX("capacity.losstabtooltip1"), new String[] { U.bps2s(arrayOfLong1[i13] * 8L), (int)(arrayOfFloat[i13] * 10.0F) / 10.0F });
              String str10 = this.m_plugin.RC(TX("capacity.losstabtooltip2"), U.bps2s(arrayOfLong2[i13] * 8L));
              int i21 = Math.max(localFontMetrics.stringWidth(str9), localFontMetrics.stringWidth(str10));
              int i22 = i21 + 10;
              int i23 = m * 2 + 6;
              int i24 = Math.max(0, this.m_mousePos.x + i22 / 2 > j ? j - i22 - 1 : this.m_mousePos.x - i22 / 2);
              int i25 = Math.max(0, (this.m_mousePos.y + 9 + i23 > k) || ((localRectangle1.height > 0) && (this.m_mousePos.y + 9 + i23 > localRectangle1.y)) ? this.m_mousePos.y - i23 - 10 : this.m_mousePos.y + 9);
              Util.drawToolTipRect(paramGraphics, i24, i25, i22, i23);
              paramGraphics.setColor(Color.black);
              paramGraphics.drawString(str9, i24 + 5, i25 + i23 - 3 - localFontMetrics.getDescent() - m);
              paramGraphics.drawString(str10, i24 + 5, i25 + i23 - 3 - localFontMetrics.getDescent());
            }
          }
        }
      }
    }
  }

  private static void drawTargetBlock(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = 1; i <= paramInt4; i++)
    {
      int j = paramInt3 + (i % 2 == 0 ? -1 : 1) * i / 2;
      paramGraphics.drawRect(paramInt1 - j, paramInt2 - j, 2 * j, 2 * j);
    }
  }

  private static void drawAchievedBlock(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = 1; i <= paramInt4; i++)
    {
      int j = (i % 2 == 0 ? -1 : 1) * i / 2;
      paramGraphics.drawLine(paramInt1 - paramInt3 + j, paramInt2 - paramInt3, paramInt1 + paramInt3 + j, paramInt2 + paramInt3);
      paramGraphics.drawLine(paramInt1 - paramInt3 + j, paramInt2 + paramInt3, paramInt1 + paramInt3 + j, paramInt2 - paramInt3);
    }
  }

  private static void drawHorzLine(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = 1; i <= paramInt4; i++)
    {
      int j = (i % 2 == 0 ? -1 : 1) * i / 2;
      paramGraphics.drawLine(paramInt1, paramInt3 + j, paramInt2, paramInt3 + j);
    }
  }

  private static int getX(long paramLong1, Rectangle paramRectangle, long paramLong2, long paramLong3)
  {
    double d = (paramLong1 - paramLong2) / (paramLong3 - paramLong2);
    return (int)(paramRectangle.width * d) + paramRectangle.x;
  }

  private static int getY(float paramFloat1, Rectangle paramRectangle, float paramFloat2)
  {
    double d = paramFloat1 / paramFloat2;
    return paramRectangle.height - (int)(paramRectangle.height * d) + paramRectangle.y;
  }

  private void setMouseOverTest(int paramInt)
  {
    if (this.m_nMouseOverTest != paramInt)
    {
      this.m_nMouseOverTest = paramInt;
      repaint();
    }
  }

  private void setToggleLegendState(int paramInt)
  {
    if (paramInt != this.m_nToggleLegendState)
    {
      this.m_nToggleLegendState = paramInt;
      repaint();
    }
  }

  private void setShowLegend(boolean paramBoolean)
  {
    if (this.m_bShowLegend != paramBoolean)
    {
      this.m_bShowLegend = paramBoolean;
      repaint();
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    String str = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if ((str != null) && (str.startsWith("togglelegend")))
      setToggleLegendState(2);
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    String str = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if ((str != null) && (str.startsWith("togglelegend")))
    {
      setToggleLegendState(0);
      setShowLegend(!this.m_bShowLegend);
    }
    else
    {
      setToggleLegendState(0);
    }
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    this.m_mousePos = localPoint;
    String str = getHitRegion(localPoint.x, localPoint.y);
    if ((str != null) && (str.startsWith("rect")))
    {
      setMouseOverTest(Integer.parseInt(str.substring(4)));
    }
    else if ((str != null) && (str.startsWith("togglelegend")) && (this.m_nToggleLegendState != 2))
    {
      setToggleLegendState(1);
    }
    else
    {
      setMouseOverTest(-1);
      setToggleLegendState(0);
    }
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(0));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_updown)
      repaint();
  }

  public String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.capacity.LossProfileTab
 * JD-Core Version:    0.6.2
 */