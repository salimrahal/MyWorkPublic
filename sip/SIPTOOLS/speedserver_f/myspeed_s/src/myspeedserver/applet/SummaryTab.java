package myspeedserver.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.StringTokenizer;

public class SummaryTab extends AppletTab
{
  private myspeed LF;
  private Image OG;
  private Image PG;
  private Image QG;
  private Image RG;
  private Image SG;
  private boolean NG = true;

  public SummaryTab(Applet paramApplet)
  {
    super(paramApplet, null, ((myspeed)paramApplet).getImageFromJar("tabsummary.gif"), "summ");
    this.LF = ((myspeed)paramApplet);
  }

  public void doFirstTimeInit()
  {
    this.OG = this.LF.getImageFromJar(TX("sumgood.gif"));
    this.PG = this.LF.getImageFromJar(TX("sumwarning.gif"));
    this.QG = this.LF.getImageFromJar(TX("sumbad.gif"));
    this.RG = this.LF.getImageFromJar(TX("sumnone.gif"));
  }

  public void reset()
  {
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    paramGraphics.setColor(new Color(238, 242, 248));
    paramGraphics.fillRect(0, 0, i - 1, j - 1);
    int k = 0;
    if ((this.NG) && (this.SG == null))
    {
      this.NG = false;
      this.SG = getBackgroundOverlay();
    }
    if (this.SG != null)
      paramGraphics.drawImage(this.SG, 0, 0, null);
    paramGraphics.setFont(new Font(TX("Helvetica"), 1, 13));
    paramGraphics.setColor(Color.black);
    k = 10;
    k += drawWrappedLines(paramGraphics, this.LF.RC(TX("connectionsummary")), 65, k, i - 70).height;
    k += 10;
    paramGraphics.setFont(new Font("Helvetica", 0, 12));
    StringTokenizer localStringTokenizer = new StringTokenizer(getConclusionsText(), TX(", "));
    while (localStringTokenizer.hasMoreTokens())
    {
      String str1 = localStringTokenizer.nextToken();
      Object[] arrayOfObject = getSummaryDataItem(str1);
      String str2;
      Object localObject;
      if (arrayOfObject != null)
      {
        int m = ((Integer)arrayOfObject[0]).intValue();
        str2 = (String)arrayOfObject[1];
        Rectangle localRectangle = null;
        if (m >= -1)
          localRectangle = drawColourBox(paramGraphics, 10, k, 36, 36, m);
        localObject = drawWrappedLines(null, str2, 65, k, i - (m >= -1 ? 70 : 20), paramGraphics.getFont());
        int i1 = 0;
        if ((localRectangle != null) && (((Rectangle)localObject).height < localRectangle.height))
          i1 += (localRectangle.height - ((Rectangle)localObject).height) / 2;
        localObject = drawWrappedLines(paramGraphics, str2, 65, k + i1, i - (m >= -1 ? 70 : 20));
        k += Math.max(20, Math.max(localRectangle == null ? 0 : localRectangle.height + 2, ((Rectangle)localObject).height));
      }
      else if ((this.LF.isProfessional()) && (TX("auditreport").equals(str1)) && ("yes".equals(iniGetString("mssidlink"))))
      {
        k += Math.max(20, drawWrappedLines(paramGraphics, this.LF.RC(TX("auditreport")), 10, k, i - 20).height);
      }
      else if ((this.LF.isProfessional()) && (TX("analysis").equals(str1)))
      {
        AppletPlugin[] arrayOfAppletPlugin = this.LF.getPlugins();
        str2 = "";
        for (int n = 0; n < arrayOfAppletPlugin.length; n++)
        {
          if (str2.length() > 0)
            str2 = str2 + " | ";
          localObject = arrayOfAppletPlugin[n].getName();
          str2 = str2 + TX("<a href=analysisportal_") + (String)localObject + TX(">") + this.LF.RC(TX("analysislinkjoin"), (String)localObject) + TX("</a>");
        }
        k += Math.max(20, drawWrappedLines(paramGraphics, this.LF.RC(TX("analysislink"), str2), 10, k, i - 20).height);
      }
    }
  }

  private String getConclusionsText()
  {
    String str1 = "";
    if ("yes".equals(RC(TX("conclusionsauditreport"))))
      str1 = str1 + TX(",auditreport");
    if ("yes".equals(RC(TX("conclusionsanalysisportal"))))
      str1 = str1 + TX(",analysis");
    AppletPlugin[] arrayOfAppletPlugin = this.LF.getPlugins();
    for (int i = 0; (arrayOfAppletPlugin != null) && (i < arrayOfAppletPlugin.length); i++)
    {
      String str2 = arrayOfAppletPlugin[i].getName();
      String str3 = RC(str2 + TX(".conclusions"));
      StringTokenizer localStringTokenizer = new StringTokenizer(str3 == null ? "" : str3, "\r\n");
      while (localStringTokenizer.hasMoreTokens())
        str1 = str1 + TX(",") + str2 + "." + localStringTokenizer.nextToken();
    }
    return str1;
  }

  public boolean hasData()
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(getConclusionsText(), ",");
    while (localStringTokenizer.hasMoreTokens())
      if (getSummaryDataItem(localStringTokenizer.nextToken()) != null)
        return true;
    return false;
  }

  private Rectangle drawColourBox(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Color[] arrayOfColor = { new Color(65408), new Color(16777152), new Color(16744576) };
    Image[] arrayOfImage = { this.OG, this.PG, this.QG };
    Color localColor1 = paramGraphics.getColor();
    Color localColor2 = paramInt5 == -1 ? Color.white : arrayOfColor[paramInt5];
    Image localImage = paramInt5 == -1 ? this.RG : arrayOfImage[paramInt5];
    int i = localImage.getHeight(null);
    Rectangle localRectangle;
    if ((localImage == null) || (i < 0))
    {
      Util.gradientFill(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, localColor2, localColor2.darker());
      paramGraphics.setColor(Color.darkGray);
      paramGraphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
      localRectangle = new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    else
    {
      paramGraphics.drawImage(localImage, paramInt1, paramInt2, null);
      localRectangle = new Rectangle(paramInt1, paramInt2, localImage.getWidth(null), localImage.getHeight(null));
    }
    paramGraphics.setColor(localColor1);
    return localRectangle;
  }

  private Object[] getSummaryDataItem(String paramString)
  {
    String str = null;
    AppletPlugin[] arrayOfAppletPlugin = this.LF.getPlugins();
    int i = paramString.indexOf(".");
    if (i > 0)
    {
      str = paramString.substring(0, i);
      paramString = paramString.substring(i + 1);
    }
    if (paramString.startsWith("sum_"))
      paramString = paramString.substring(4);
    for (int j = 0; (arrayOfAppletPlugin != null) && (j < arrayOfAppletPlugin.length); j++)
      if ((str == null) || (arrayOfAppletPlugin[j].getName().equals(str)))
      {
        AppletTest localAppletTest = arrayOfAppletPlugin[j].getTest();
        Object[] arrayOfObject = localAppletTest == null ? null : localAppletTest.getSummaryItem(paramString);
        if (arrayOfObject != null)
          return arrayOfObject;
      }
    return null;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.SummaryTab
 * JD-Core Version:    0.6.2
 */