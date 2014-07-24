package myspeedserver.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AdvancedTab extends AppletTab
  implements Runnable, KeyListener, AdjustmentListener
{
  private JFrame ZH = new JFrame();
  private JTextArea AH = new JTextArea(18, 80);
  private JScrollBar BH = new JScrollBar(1);
  private myspeed LF;

  public AdvancedTab(Applet paramApplet)
  {
    super(paramApplet, null, ((myspeed)paramApplet).getImageFromJar("tabadvanced.gif"), "advanced");
    this.LF = ((myspeed)paramApplet);
  }

  public void doFirstTimeInit()
  {
    this.AH.setEditable(false);
    this.AH.setFont(new Font(TX("Courier"), 0, 12));
    this.AH.setBackground(new Color(16777215));
    this.AH.setForeground(new Color(0));
    this.ZH.setTitle(this.LF.appNameVer(true) + TX(" Statistics"));
    this.ZH.add(TX("Center"), new JScrollPane(this.AH));
    this.ZH.setLocation(0, 0);
    this.ZH.pack();
    this.ZH.addWindowListener(this.LF);
    setLayout(new BorderLayout());
    add(TX("East"), this.BH);
    this.BH.setVisible(false);
    this.BH.addAdjustmentListener(this);
    addKeyListener(this);
  }

  public void reset()
  {
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    int k = 0;
    if (this.BH.isVisible())
    {
      k = -this.BH.getValue();
      paramGraphics.translate(0, k);
      i -= this.BH.getWidth();
    }
    paramGraphics.setColor(new Color(238, 242, 248));
    paramGraphics.fillRect(0, 0, i - 1, j - 1);
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int m = localFontMetrics.getHeight();
    int n = localFontMetrics.getAscent();
    int i1 = iniGetInteger("advancedpaddingx", 100);
    int i2 = iniGetInteger("advancedcols", 1);
    i -= i1;
    paramGraphics.translate(i1 / 2, 0);
    i2 = Math.max(1, Math.min(i / 200, i2));
    paramGraphics.setFont(new Font(TX("Helvetica"), 1, 13));
    paramGraphics.setColor(Color.black);
    int i3 = 10;
    int i4 = i / i2;
    int i5 = 0;
    i3 += drawWrappedLines(paramGraphics, RC(TX("advancedstats")), 65, i3, i - 70, new Point(0, k)).height;
    i3 += 20;
    paramGraphics.setFont(new Font(TX("Helvetica"), 0, 12));
    StringTokenizer localStringTokenizer = new StringTokenizer(getShowStatsText(), TX(","));
    while (localStringTokenizer.hasMoreTokens())
    {
      str1 = localStringTokenizer.nextToken().trim();
      String str2 = null;
      String str3 = null;
      String str4 = null;
      String[] arrayOfString = getAdvancedDataItem(str1);
      if (arrayOfString != null)
      {
        str2 = arrayOfString[0];
        str3 = arrayOfString[1];
      }
      else if (str1.indexOf(TX("header/")) >= 0)
      {
        str4 = str1.substring(str1.indexOf(TX("header/")) + 7);
      }
      if ((str2 != null) && (str3 != null))
      {
        if (i5 > 0)
        {
          i3 -= m;
          paramGraphics.drawLine(i4 * i5, i3 - n, i4 * i5, i3 - n + m);
        }
        paramGraphics.setColor(Color.black);
        paramGraphics.drawString(str2, 10 + i4 * i5, i3);
        paramGraphics.drawString(str3, i4 * (i5 + 1) - localFontMetrics.stringWidth(str3) - 10, i3);
        i3 += m;
        i5 = (i5 + 1) % i2;
      }
      else if (str4 != null)
      {
        i3 += 5;
        Font localFont = paramGraphics.getFont();
        paramGraphics.setFont(new Font(localFont.getName(), 1, localFont.getSize()));
        localFontMetrics = paramGraphics.getFontMetrics();
        paramGraphics.drawString(str4, 30, i3);
        Util.horzGradientFill(paramGraphics, 10, i3 + localFontMetrics.getDescent() + 3, i - 100, 2, new Color(66, 77, 248), new Color(238, 242, 248));
        i3 += localFontMetrics.getHeight() + localFontMetrics.getDescent() + 3 + 2;
        paramGraphics.setFont(localFont);
        localFontMetrics = paramGraphics.getFontMetrics();
        i5 = 0;
      }
    }
    String str1 = RC(TX("showtextwindow"));
    if ((!"".equals(str1)) && (!TX("?viewastext?").equals(str1)))
    {
      int i6 = localFontMetrics.stringWidth(str1);
      paramGraphics.translate(-i1 / 2, 0);
      int i7 = j - localFontMetrics.getHeight() - 5;
      if (i7 < i3)
      {
        i7 = i3;
        i3 += localFontMetrics.getHeight();
      }
      drawWrappedLines(paramGraphics, TX("<a href=viewastext>") + str1 + TX("</a>"), i - i6 - 5, i7, 9999, new Point(0, k));
      paramGraphics.translate(i1 / 2, 0);
    }
    paramGraphics.translate(-i1 / 2, 0);
    paramGraphics.translate(0, -k);
    i3 += 15;
    if (i3 > j != this.BH.isVisible())
    {
      this.BH.setVisible(i3 > j);
      repaint();
    }
    if (i3 > j)
    {
      this.BH.setBlockIncrement(30);
      this.BH.setMaximum(i3);
      this.BH.setMinimum(0);
      this.BH.getModel().setExtent(j);
    }
  }

  private String getShowStatsText()
  {
    String str1 = crlfToCommaTrim(RC(TX("showstats")));
    AppletPlugin[] arrayOfAppletPlugin = this.LF.getPlugins();
    for (int i = 0; (arrayOfAppletPlugin != null) && (i < arrayOfAppletPlugin.length); i++)
    {
      String str2 = arrayOfAppletPlugin[i].getName();
      String str3 = RC(str2 + TX(".showstats"));
      StringTokenizer localStringTokenizer = new StringTokenizer(str3 == null ? "" : str3, "\r\n");
      while (localStringTokenizer.hasMoreTokens())
        str1 = str1 + TX(",") + str2 + "." + localStringTokenizer.nextToken().trim();
    }
    return str1;
  }

  public boolean hasData()
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(getShowStatsText(), ",");
    while (localStringTokenizer.hasMoreTokens())
      if (getAdvancedDataItem(localStringTokenizer.nextToken().trim()) != null)
        return true;
    return false;
  }

  private String crlfToCommaTrim(String paramString)
  {
    if (paramString == null)
      return null;
    return paramString.replace('\n', ',').replace('\r', ',').replace(' ', ',');
  }

  private void copyAdvancedText()
  {
    if (this.LF.isProfessional())
    {
      this.AH.setText(getAdvancedText(getShowStatsText(), true));
      this.ZH.setVisible(true);
      new Thread(this).start();
    }
  }

  public void run()
  {
    try
    {
      Thread.sleep(500L);
    }
    catch (Exception localException)
    {
    }
    this.ZH.toFront();
    this.ZH.setVisible(true);
  }

  private String getAdvancedText(String paramString, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, TX(","));
    String str1;
    Object localObject1;
    Object localObject2;
    while (localStringTokenizer.hasMoreTokens())
    {
      str1 = localStringTokenizer.nextToken().trim();
      localObject1 = getAdvancedDataItem(str1);
      if (localObject1 != null)
      {
        localStringBuffer.append(localObject1[0]).append(": ").append(localObject1[1]).append("\r\n");
      }
      else if (str1.indexOf(TX("header/")) >= 0)
      {
        localObject2 = TX("---------------------------------------------------------------------------------------------------");
        localStringBuffer.append("\r\n");
        String str2 = str1.substring(str1.indexOf(TX("header/")) + 7);
        localStringBuffer.append(str2).append("\r\n");
        localStringBuffer.append(((String)localObject2).substring(0, Math.min(((String)localObject2).length() - 1, str2.length()))).append("\r\n");
      }
    }
    if (paramBoolean)
    {
      str1 = iniGetString("mss.clientip");
      localObject1 = this.LF.getTestTime();
      localObject2 = this.LF.getCodeBase();
      localStringBuffer.append("\r\n\r\nGeneral information\r\n-------------------\r\n");
      localStringBuffer.append("IP address: " + (str1 == null ? "<unknown>" : str1) + "\r\n");
      localStringBuffer.append("Local time: " + (localObject1 == null ? "<unknown>" : ((Date)localObject1).toLocaleString()) + "\r\n");
      localStringBuffer.append("Test server: " + (localObject2 == null ? "<unknown>" : ((URL)localObject2).toString()) + "\r\n");
    }
    return localStringBuffer.toString();
  }

  private String[] getAdvancedDataItem(String paramString)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    String str = null;
    AppletPlugin[] arrayOfAppletPlugin = this.LF.getPlugins();
    int i = paramString.indexOf(".");
    if (i > 0)
    {
      str = paramString.substring(0, i);
      paramString = paramString.substring(i + 1);
    }
    if (paramString.startsWith("adv_"))
      paramString = paramString.substring(4);
    for (int j = 0; (arrayOfAppletPlugin != null) && (j < arrayOfAppletPlugin.length); j++)
      if ((str == null) || (arrayOfAppletPlugin[j].getName().equals(str)))
      {
        AppletTest localAppletTest = arrayOfAppletPlugin[j].getTest();
        String[] arrayOfString = localAppletTest == null ? null : localAppletTest.getAdvancedDataItem(paramString);
        if (arrayOfString != null)
        {
          localObject1 = arrayOfString[0];
          localObject2 = arrayOfString[1];
          break;
        }
      }
    return new String[] { localObject1, (localObject1 == null) || (localObject2 == null) ? null : localObject2 };
  }

  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyChar() == 'a')
      this.ZH.setVisible(true);
  }

  public void keyReleased(KeyEvent paramKeyEvent)
  {
  }

  public void keyTyped(KeyEvent paramKeyEvent)
  {
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    String str = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if (TX("viewastext").equals(str))
      copyAdvancedText();
    super.mousePressed(paramMouseEvent);
  }

  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
  {
    repaint();
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.AdvancedTab
 * JD-Core Version:    0.6.2
 */