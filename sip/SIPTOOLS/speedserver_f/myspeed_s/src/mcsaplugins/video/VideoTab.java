package mcsaplugins.video;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.JButton;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class VideoTab extends AppletTab
  implements ActionListener, MouseListener
{
  private JButton m_start = new JButton();
  private Font m_fBody;
  private video m_plugin;
  private String m_explain;
  private Point m_explainLoc;
  private int m_nStartState;
  private static final int STATE_NORMAL = 0;
  private static final int STATE_MOUSEOVER = 1;
  private static final int STATE_MOUSEDOWN = 2;

  public VideoTab(Applet paramApplet, video paramvideo)
  {
    super(paramApplet, paramvideo, paramvideo.getImage("tabvideo.gif"), "video");
    this.m_plugin = paramvideo;
    setLayout(null);
    add(this.m_start);
    this.m_start.setText(RC(TX("start")));
    this.m_start.addActionListener(this);
    this.m_nStartState = 0;
  }

  public void setShowStartButton(boolean paramBoolean)
  {
    this.m_start.setVisible(false);
  }

  public void doFirstTimeInit()
  {
    this.m_fBody = Util.getFont(new String[] { "Segoe UI", "Verdana" }, 0, 12);
    setFadeCopyrightInfo(true);
  }

  public void reset()
  {
    this.m_plugin.reset();
    repaint();
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().height;
    int j = getSize().width;
    paramGraphics.setFont(this.m_fBody);
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    paramGraphics.setColor(Color.white);
    paramGraphics.fillRect(0, 0, j, i);
    VideoTest localVideoTest = (VideoTest)this.m_plugin.getTest();
    drawLeftStatus(paramGraphics, 0, 0, 150, i, localVideoTest);
    drawGraph(paramGraphics, 160, 0, j - 160, i, localVideoTest);
    drawNCQ(paramGraphics, 200, 0, 200, i, localVideoTest);
    paramGraphics.setFont(this.m_fBody);
    paintCopyrightInfo(paramGraphics, 2, i - 3 * localFontMetrics.getHeight());
    if ((this.m_explain != null) && (this.m_explainLoc != null))
    {
      String str = RC(TX("speed.explain.") + this.m_explain);
      Rectangle localRectangle = drawWrappedLines(null, str, this.m_explainLoc.x, this.m_explainLoc.y, j / 2, paramGraphics.getFont());
      localRectangle.width += 6;
      localRectangle.height += 6;
      localRectangle.x -= 3;
      localRectangle.y -= 3;
      int k = Math.max(0, Math.min(j - localRectangle.width - 1, this.m_explainLoc.x - localRectangle.width / 2));
      int m = Math.max(0, Math.min(i - localRectangle.height, this.m_explainLoc.y - localRectangle.height - 5));
      paramGraphics.setColor(new Color(15663086));
      paramGraphics.fillRect(k, m, localRectangle.width, localRectangle.height);
      paramGraphics.setColor(Color.black);
      paramGraphics.drawRect(k, m, localRectangle.width, localRectangle.height);
      drawWrappedLines(paramGraphics, str, k + 3, m + 3, j / 2, paramGraphics.getFont());
    }
    doOverlayMessages(paramGraphics);
  }

  private void drawNCQ(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, VideoTest paramVideoTest)
  {
    if ((paramVideoTest != null) && (paramVideoTest.isNetQCheck()))
    {
      String str1 = iniGetString("speed.nqcmetrics");
      StringTokenizer localStringTokenizer = new StringTokenizer(str1 == null ? "" : str1, "\r\n, ");
      Hashtable localHashtable = paramVideoTest.getAccessCTMetrics();
      StringBuffer localStringBuffer = new StringBuffer();
      String str2 = RC(TX("video.nqcmetricstitle"));
      if ((str2 != null) && (str2.trim().length() > 0))
        localStringBuffer.append("<b>" + str2 + "</b>\r\n");
      if ((localStringTokenizer != null) && (localHashtable != null))
      {
        Object localObject1;
        Object localObject2;
        while (localStringTokenizer.hasMoreTokens())
        {
          localObject1 = localStringTokenizer.nextToken();
          if ("analysislink".equals(localObject1))
          {
            localStringBuffer.append("<a href=analysisportal_video>" + RC(TX("video.nqcmetricslink")) + "</a>\r\n");
          }
          else
          {
            localObject2 = localHashtable == null ? null : (String)localHashtable.get(localObject1);
            if (localObject2 != null)
            {
              String str3 = Util.parseInt((String)localObject2, -1) > 0 ? "<b><span bgcolor=cc0000>" : "";
              String str4 = str3.length() > 0 ? "</span></b>" : "";
              localStringBuffer.append(str3 + RC(new StringBuilder("speed.").append((String)localObject1).toString(), new String[] { localObject2 }) + str4 + "<a href=info:" + (String)localObject1 + "><img src=information.png></a>\r\n");
            }
          }
        }
        if (localStringBuffer.length() > 0)
        {
          localObject1 = drawWrappedLines(null, localStringBuffer.toString(), paramInt1 + 2, paramInt2, paramInt3 - 5, paramGraphics.getFont());
          localObject2 = paramGraphics.getFontMetrics();
          int i = ((FontMetrics)localObject2).getHeight();
          int j = paramInt4 - i - 30 - ((Rectangle)localObject1).height;
          paramGraphics.setColor(Color.white);
          paramGraphics.fillRect(paramInt1, j - 3, ((Rectangle)localObject1).width + 6, ((Rectangle)localObject1).height + 6);
          paramGraphics.setColor(Color.black);
          paramGraphics.drawRect(paramInt1, j - 3, ((Rectangle)localObject1).width + 6, ((Rectangle)localObject1).height + 6);
          drawWrappedLines(paramGraphics, localStringBuffer.toString(), paramInt1 + 2, j, paramInt3 - 5, paramGraphics.getFont());
        }
      }
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    String str = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if ("start".equals(str))
      setStartButtonState(2);
    super.mousePressed(paramMouseEvent);
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    String str = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    setStartButtonState("start".equals(str) ? 1 : 0);
    super.mouseReleased(paramMouseEvent);
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    Object[] arrayOfObject = getHitRegionDetail(paramMouseEvent.getX(), paramMouseEvent.getY());
    int i = 0;
    String str1 = null;
    Point localPoint = null;
    if ((arrayOfObject != null) && (arrayOfObject.length >= 2) && (arrayOfObject[0] != null))
    {
      String str2 = (String)arrayOfObject[0];
      if (str2.startsWith("info:"))
      {
        Rectangle localRectangle = (Rectangle)arrayOfObject[1];
        str1 = ((String)arrayOfObject[0]).substring(5);
        localPoint = new Point(localRectangle.x + localRectangle.width / 2, localRectangle.y + localRectangle.height / 2);
      }
      else if (str2.equals("start"))
      {
        i = this.m_nStartState == 2 ? 2 : 1;
      }
    }
    if ((str1 != this.m_explain) || ((str1 != null) && (!str1.equals(this.m_explain))) || (localPoint != this.m_explainLoc) || ((localPoint != null) && (!localPoint.equals(this.m_explainLoc))))
    {
      this.m_explain = str1;
      this.m_explainLoc = localPoint;
      repaint();
    }
    setStartButtonState(i);
    setCursor(Cursor.getPredefinedCursor(arrayOfObject == null ? 0 : 12));
  }

  private void setStartButtonState(int paramInt)
  {
    if (paramInt != this.m_nStartState)
    {
      this.m_nStartState = paramInt;
      repaint();
    }
  }

  private void drawLeftStatus(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, VideoTest paramVideoTest)
  {
    paramGraphics.setFont(this.m_fBody);
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int i = paramVideoTest == null ? 0 : paramVideoTest.getPercentComplete();
    ErrorCode localErrorCode = paramVideoTest == null ? null : paramVideoTest.getErrorCode();
    Util.horzGradientFill(paramGraphics, paramInt1 + paramInt3 - paramInt3 / 4, 0, paramInt3 / 4, paramInt4, new Color(16777215), new Color(15790320));
    Util.horzGradientFill(paramGraphics, paramInt1 + paramInt3 - 4, 0, 4, paramInt4, new Color(15592941), new Color(13816530));
    if (paramVideoTest == null)
    {
      this.m_start.setSize(this.m_start.getPreferredSize());
      this.m_start.setLocation(paramInt3 / 2 - this.m_start.getWidth() / 2, 20);
    }
    else
    {
      String str1;
      if (localErrorCode != null)
      {
        paramGraphics.setColor(new Color(6710886));
        str1 = "ERROR";
        paramGraphics.drawString(str1, paramInt1 + paramInt3 / 2 - localFontMetrics.stringWidth(str1) / 2, 40);
        drawWrappedLines(paramGraphics, localErrorCode.desc, paramInt1 + 7, 60, paramInt3 - 14);
      }
      else if (i < 100)
      {
        paramGraphics.setColor(new Color(6710886));
        str1 = "PLEASE WAIT";
        paramGraphics.drawString(str1, paramInt1 + paramInt3 / 2 - localFontMetrics.stringWidth(str1) / 2, 40);
        String str2 = "Testing...";
        paramGraphics.drawString(str2, paramInt1 + paramInt3 / 2 - localFontMetrics.stringWidth(str2) / 2, 60);
        paramGraphics.setColor(new Color(3355630));
        paramGraphics.fillRect(paramInt1 + 10, 70, (paramInt3 - 20) * i / 100, 10);
        paramGraphics.setColor(new Color(6710886));
        paramGraphics.drawRect(paramInt1 + 10, 70, paramInt3 - 20, 10);
      }
      else
      {
        int j = paramVideoTest.getSetupTime();
        int k = paramVideoTest.getDescribeTime();
        int m = paramVideoTest.getPlayTime();
        int n = paramVideoTest.getRTT();
        double d1 = paramVideoTest.getClientAudioJitter();
        double d2 = paramVideoTest.getClientVideoJitter();
        double d3 = paramVideoTest.getServerAudioJitter();
        double d4 = paramVideoTest.getServerVideoJitter();
        double d5 = paramVideoTest.getAudioPacketLoss();
        double d6 = paramVideoTest.getVideoPacketLoss();
        double d7 = paramVideoTest.getAudioDiscards();
        double d8 = paramVideoTest.getVideoDiscards();
        long l = paramVideoTest.getLateBytes();
        int[] arrayOfInt1 = { (int)(d1 * 10.0D), (int)(d2 * 10.0D), (int)(d3 * 10.0D), (int)(d4 * 10.0D) };
        String[] arrayOfString1 = { "Client Audio", "Client Video", "Server Audio", "Server Video" };
        String[] arrayOfString2 = { "Audio", "Video" };
        int[] arrayOfInt2 = { (int)(d5 * 10.0D), (int)(d6 * 10.0D) };
        int[] arrayOfInt3 = { (int)d7, (int)d8 };
        Util.setAntialias(paramGraphics, true);
        int i1 = 20;
        paramGraphics.setFont(this.m_fBody);
        localFontMetrics = paramGraphics.getFontMetrics();
        int i2 = localFontMetrics.getHeight();
        int i3 = Math.max(getBarLabelLength(arrayOfString2, localFontMetrics), getBarLabelLength(arrayOfString1, localFontMetrics));
        if (paramVideoTest.showRequestTimes())
        {
          paramGraphics.setColor(new Color(1118481));
          paramGraphics.drawString("Setup Dsc Play RTT", 5, i1);
          i1 += i2;
          paramGraphics.drawString(j + "  " + k + "  " + m + "  " + n, 5, i1);
          i1 = (int)(i1 + i2 * 1.5D);
        }
        paramGraphics.setColor(new Color(1118481));
        paramGraphics.drawString("Jitter (ms)", 5, i1);
        i1 += drawBars(paramGraphics, localFontMetrics, paramInt1 + 5, i1, paramInt3 - 23, i3, arrayOfString1, arrayOfInt1, 10.0F, TX("-"));
        i1 = (int)(i1 + i2 * 1.5D);
        paramGraphics.setColor(new Color(1118481));
        paramGraphics.drawString("Loss (%)", 5, i1);
        i1 += drawBars(paramGraphics, localFontMetrics, paramInt1 + 5, i1, paramInt3 - 23, i3, arrayOfString2, arrayOfInt2, 10.0F, TX("-"));
        i1 = (int)(i1 + i2 * 1.5D);
        paramGraphics.setColor(new Color(1118481));
        paramGraphics.drawString("Discard (%)", 5, i1);
        i1 += drawBars(paramGraphics, localFontMetrics, paramInt1 + 5, i1, paramInt3 - 23, i3, arrayOfString2, arrayOfInt3, 1.0F, TX("-"));
        paramGraphics.setColor(new Color(6710886));
        i1 += i2;
        paramGraphics.drawString("Timer accuracy " + U.getTickResolution() + " ms", 5, i1);
        i1 += i2;
        paramGraphics.drawString("Bytes XWindow " + l, 5, i1);
      }
    }
  }

  private int getBarLabelLength(String[] paramArrayOfString, FontMetrics paramFontMetrics)
  {
    int i = 0;
    for (int j = 0; j < paramArrayOfString.length; j++)
      i = Math.max(i, paramFontMetrics.stringWidth(paramArrayOfString[j]));
    return i;
  }

  private void drawGraph(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, VideoTest paramVideoTest)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 10000;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    paramGraphics.setFont(this.m_fBody);
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int i5 = localFontMetrics.getHeight() + 5;
    int i6 = localFontMetrics.getHeight() * 2 + 5;
    Rectangle localRectangle = new Rectangle(paramInt1 + i5, paramInt2 + 3, paramInt3 - i5 - 5, paramInt4 - i6 - 3);
    paramGraphics.setColor(new Color(15658734));
    for (int i7 = 0; i7 <= i1; i7 += 500)
    {
      int i8 = localRectangle.x + i7 * localRectangle.width / i1;
      i9 = 500 * localRectangle.width / i1;
      paramGraphics.setColor(new Color(i7 / 500 % 2 == 0 ? 16448250 : 16777215));
      paramGraphics.fillRect(i8, localRectangle.y, i9, localRectangle.height);
      paramGraphics.setColor(new Color(15658734));
      paramGraphics.drawLine(i8, localRectangle.y, i8, localRectangle.y + localRectangle.height + 3);
    }
    paramGraphics.setColor(new Color(6710886));
    paramGraphics.drawLine(paramInt1 + i5 - 4, localRectangle.y + localRectangle.height / 2, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height / 2);
    paramGraphics.drawRect(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
    String str1 = "Bytes early / Bytes late";
    drawRotatedText(paramGraphics, str1, paramInt1 + i5 / 2, localRectangle.y + localRectangle.height / 2);
    String str2 = "Time (ms)";
    paramGraphics.drawString(str2, 470, localRectangle.height + 30);
    paramGraphics.setColor(Color.orange);
    paramGraphics.fillRect(180, localRectangle.height + 20, 6, 8);
    paramGraphics.setColor(new Color(6710886));
    paramGraphics.drawString("bad", 190, localRectangle.height + 30);
    paramGraphics.setColor(Color.red);
    paramGraphics.fillRect(240, localRectangle.height + 20, 6, 8);
    paramGraphics.setColor(new Color(6710886));
    paramGraphics.drawString("discard", 250, localRectangle.height + 30);
    paramGraphics.setColor(Color.green);
    paramGraphics.fillRect(300, localRectangle.height + 20, 6, 8);
    paramGraphics.setColor(new Color(6710886));
    paramGraphics.drawString("good", 310, localRectangle.height + 30);
    paramGraphics.setColor(Color.blue);
    paramGraphics.fillRect(360, localRectangle.height + 20, 6, 8);
    paramGraphics.setColor(new Color(6710886));
    paramGraphics.drawString("lost", 370, localRectangle.height + 30);
    paramGraphics.setColor(new Color(11184810));
    int i9 = -999;
    Object localObject;
    for (int i10 = 0; i10 < i1; i10 += 1000)
    {
      int i11 = localRectangle.x + i10 * localRectangle.width / i1;
      localObject = i10;
      int i12 = localFontMetrics.stringWidth((String)localObject);
      if (i11 - i12 / 2 >= i9)
      {
        paramGraphics.drawString((String)localObject, i11 - i12 / 2, localRectangle.y + localRectangle.height + localFontMetrics.getAscent());
        i9 = i11 + i12 / 2 + 10;
      }
    }
    if (paramVideoTest != null)
    {
      i = paramVideoTest.getPercentComplete();
      j = paramVideoTest.getVideoPacketsPerSecond();
      k = paramVideoTest.getAudioPacketsPerSecond();
      m = paramVideoTest.getVideoBytesPerPacket();
      n = paramVideoTest.getAudioBytesPerPacket();
      i1 = paramVideoTest.getDuration();
      i2 = i < 100 ? paramVideoTest.getCurrentMs() : i1;
      int[] arrayOfInt1 = paramVideoTest.getAudioTimesArray();
      int[] arrayOfInt2 = paramVideoTest.getAudioBytesArray();
      localObject = paramVideoTest.getVideoTimesArray();
      int[] arrayOfInt3 = paramVideoTest.getVideoBytesArray();
      int[] arrayOfInt4 = paramVideoTest.getAudioBadArray();
      int[] arrayOfInt5 = paramVideoTest.getAudioGoodArray();
      int[] arrayOfInt6 = paramVideoTest.getAudioDiscardArray();
      int[] arrayOfInt7 = paramVideoTest.getAudioLostArray();
      int[] arrayOfInt8 = paramVideoTest.getVideoBadArray();
      int[] arrayOfInt9 = paramVideoTest.getVideoGoodArray();
      int[] arrayOfInt10 = paramVideoTest.getVideoDiscardArray();
      int[] arrayOfInt11 = paramVideoTest.getVideoLostArray();
      float f1 = Math.max(i1 / 40.0F, 20.0F);
      float f2 = (float)((k * n + j * m) * i1 / 1000.0D * (f1 / i1)) * 2.0F;
      int[] arrayOfInt12 = new int[(int)(i1 / f1 + 0.5D)];
      long[] arrayOfLong1 = new long[arrayOfInt12.length];
      long[] arrayOfLong2 = new long[arrayOfInt12.length];
      long[] arrayOfLong3 = new long[arrayOfInt12.length];
      long[] arrayOfLong4 = new long[arrayOfInt12.length];
      for (int i13 = 0; (arrayOfInt1 != null) && (i13 < arrayOfInt1.length); i13++)
      {
        if ((i < 100) && (i13 + 1 < arrayOfInt1.length) && (arrayOfInt2[(i13 + 1)] > 0) && (arrayOfInt1[(i13 + 1)] > i2))
          break;
        i3 = (int)(i13 / (f1 / (1000.0D / k)));
        i4 = (int)(arrayOfInt1[i13] / f1);
        if ((i3 >= 0) && (arrayOfInt12.length > i3) && (arrayOfInt12.length > i4))
        {
          arrayOfInt12[i3] += arrayOfInt2[i13];
          if (arrayOfInt4[i13] > 0)
            arrayOfLong1[i4] += arrayOfInt4[i13];
          if (arrayOfInt5[i13] > 0)
            arrayOfLong2[i3] += arrayOfInt5[i13];
          if (arrayOfInt6[i13] > 0)
            arrayOfLong3[i4] += arrayOfInt6[i13];
          if (arrayOfInt7[i13] > 0)
            arrayOfLong4[i3] += arrayOfInt7[i13];
        }
      }
      for (i13 = 0; (localObject != null) && (i13 < localObject.length); i13++)
      {
        if ((i < 100) && (i13 + 1 < localObject.length) && (arrayOfInt3[(i13 + 1)] > 0) && (localObject[(i13 + 1)] > i2))
          break;
        i3 = (int)(i13 / (f1 / (1000.0D / j)));
        i4 = (int)(localObject[i13] / f1);
        if ((i3 >= 0) && (arrayOfInt12.length > i3) && (arrayOfInt12.length > i4))
        {
          arrayOfInt12[i3] += arrayOfInt3[i13];
          if (arrayOfInt8[i13] > 0)
            arrayOfLong1[i4] += arrayOfInt8[i13];
          if (arrayOfInt9[i13] > 0)
            arrayOfLong2[i3] += arrayOfInt9[i13];
          if (arrayOfInt10[i13] > 0)
            arrayOfLong3[i4] += arrayOfInt10[i13];
          if (arrayOfInt11[i13] > 0)
            arrayOfLong4[i3] += arrayOfInt11[i13];
        }
      }
      i13 = (int)(localRectangle.width * f1 / i1);
      int i14 = 0;
      int i15 = 0;
      int i16 = 0;
      int i17 = 0;
      int i18 = 0;
      int i19 = localRectangle.y + localRectangle.height / 2;
      for (int i20 = 0; i20 < arrayOfInt12.length; i20++)
      {
        if ((i < 100) && ((i20 + 1) * f1 > i2))
          break;
        i14 = localRectangle.x + (int)((i20 * f1 + f1 / 2.0F) * localRectangle.width / i1);
        i15 = 0;
        i16 = 0;
        i17 = 0;
        i18 = 0;
        if (arrayOfLong1[i20] > 0L)
        {
          paramGraphics.setColor(Color.orange);
          i15 = Math.max(3, (int)((localRectangle.height - 5) * Math.min(1.0F, (float)arrayOfLong1[i20] / f2)));
          paramGraphics.fillRect(i14 - i13 / 2, i19 - i15, i13, i15);
        }
        if (arrayOfLong3[i20] > 0L)
        {
          paramGraphics.setColor(Color.red);
          i16 = Math.max(3, (int)((localRectangle.height - 5) * Math.min(1.0F, (float)arrayOfLong3[i20] / f2)));
          paramGraphics.fillRect(i14 - i13 / 2, i19 - i15 - i16, i13, i16);
        }
        if (arrayOfLong2[i20] > 0L)
        {
          paramGraphics.setColor(Color.green);
          i17 = Math.max(3, (int)(localRectangle.height * ((float)arrayOfLong2[i20] / f2)));
          paramGraphics.fillRect(i14 - i13 / 2, i19, i13, i17);
        }
        if (arrayOfLong4[i20] > 0L)
        {
          paramGraphics.setColor(Color.blue);
          i18 = Math.max(3, (int)(localRectangle.height * ((float)arrayOfLong4[i20] / f2)));
          paramGraphics.fillRect(i14 - i13 / 2, i19 + i17, i13, i18);
        }
      }
    }
  }

  private static void drawRotatedText(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2)
  {
    AffineTransform localAffineTransform1 = ((Graphics2D)paramGraphics).getTransform();
    AffineTransform localAffineTransform2 = new AffineTransform(localAffineTransform1);
    localAffineTransform2.rotate(-1.570796326794897D, paramInt1, paramInt2);
    ((Graphics2D)paramGraphics).setTransform(localAffineTransform2);
    paramGraphics.drawString(paramString, paramInt1 - paramGraphics.getFontMetrics().stringWidth(paramString) / 2, paramInt2);
    ((Graphics2D)paramGraphics).setTransform(localAffineTransform1);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_start)
    {
      fireActionEvent(new ActionEvent(this, 1001, "start"));
      this.m_start.setVisible(false);
    }
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.video.VideoTab
 * JD-Core Version:    0.6.2
 */