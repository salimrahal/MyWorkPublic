package mcsaplugins.speed;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Hashtable;
import java.util.StringTokenizer;
import myspeedserver.applet.U;

class SpeedTab$DialMetrics
{
  public Rectangle MAINDIALNEEDLE;
  public Rectangle MINIDIALNEEDLE;
  public Point MAINDIALNEEDLEPOS;
  public Point MINIDIALNEEDLEPOS;
  public Point MINIDIALCOR;
  public Point MAINDIALCOR;
  public Rectangle MAINDIALCENTRE;
  public Rectangle MINIDIALCENTRE;
  public Point UPLOADREADOUT;
  public Point UPLOADREADOUTUNITS;
  public int UPLOADREADOUTALIGN;
  public boolean SHOWUPLOADREADOUTDURING;
  public Rectangle[] UPLOADREADOUTDIGITS;
  public Rectangle[] UPLOADREADOUTSI;
  public Point DOWNLOADREADOUT;
  public Point DOWNLOADREADOUTUNITS;
  public int DOWNLOADREADOUTALIGN;
  public boolean SHOWDOWNLOADREADOUTDURING;
  public Rectangle[] DOWNLOADREADOUTDIGITS;
  public Rectangle[] DOWNLOADREADOUTSI;
  public Point CURRENTSPEEDPOS;
  public Point CURRENTSPEEDUNITSPOS;
  public Point CURRENTUPSPEEDUNITSPOS;
  public int CURRENTSPEEDALIGN;
  public Rectangle CUSTOMTRACKDIAL;
  public Point CUSTOMTRACKDIALPOS;
  public Rectangle[] CURRENTSPEEDDIGITS;
  public Rectangle[] CURRENTSPEEDSI;
  public Rectangle[] LCDDIGITS;
  public Rectangle[] MAINDIALDIGITS;
  public Rectangle[] MINIDIALDIGITS;
  public int[] MAINDIALPEGDEGS;
  public Point[] MAINDIALPEGS;
  public int[] MAINDIALPEGALIGN;
  public boolean MAINDIALSI;
  public int[] MINIDIALPEGDEGS;
  public Point[] MINIDIALPEGS;
  public int[] MINIDIALPEGALIGN;
  public boolean MINIDIALSI;
  public Point[] LCDROWS;
  public int[] LCDROWALIGN;
  public Rectangle[] QOSDIGITS;
  public Rectangle QOSPERCENT;
  public Rectangle QOSDISPLAY;
  public Rectangle[] QOSBARS;
  public Point QOSBARTOPLEFT;
  public boolean CUSTOMSTART;
  public boolean SHOWRESTART;
  public Point STARTBUTTON;
  public Rectangle STARTBUTTONDEFAULT;
  public Rectangle STARTBUTTONMO;
  public Rectangle STARTBUTTONMD;
  public Rectangle STARTACTIVEAREA;
  public Rectangle RESTARTBUTTONDEFAULT;
  public Rectangle RESTARTBUTTONMO;
  public Rectangle RESTARTBUTTONMD;
  public boolean CUSTOMPROGRESS;
  public Point PROGRESSBARPOS;
  public Rectangle PROGRESSBAR;
  public Rectangle PROGRESSBARENDCAP;
  public boolean KEEPCOMPLETEDPROGRESS;
  public boolean PROGRESSREVERSEONUPLOAD;
  public boolean PROGRESSREVERSEONDOWNLOAD;
  public Point UPRTTPOS;
  public Point DOWNRTTPOS;
  public Rectangle INFOBUTTONDEFAULT;
  public Rectangle INFOBUTTONMO;
  public Rectangle INFOBUTTONMD;
  public Point INFOBUTTONPOS;
  public Rectangle UPLOADMESSAGE;
  public Point UPLOADMESSAGEPOS;
  public Rectangle DOWNLOADOVERLAY;
  public Rectangle UPLOADOVERLAY;
  public Rectangle DONEOVERLAY;

  public static DialMetrics fromSpecString(String paramString)
  {
    return paramString == null ? null : new DialMetrics(paramString);
  }

  private SpeedTab$DialMetrics(String paramString)
  {
    Hashtable localHashtable = paramString == null ? new Hashtable() : U.stringToHash(paramString, "\r\n");
    this.MAINDIALNEEDLE = parseRectangle((String)localHashtable.get("maindialneedle"));
    this.MINIDIALNEEDLE = parseRectangle((String)localHashtable.get("minidialneedle"));
    this.MAINDIALNEEDLEPOS = parsePoint((String)localHashtable.get("maindialneedlepos"));
    this.MINIDIALNEEDLEPOS = parsePoint((String)localHashtable.get("minidialneedlepos"));
    this.MINIDIALCOR = parsePoint((String)localHashtable.get("minidialcor"));
    this.MAINDIALCOR = parsePoint((String)localHashtable.get("maindialcor"));
    this.MAINDIALCENTRE = parseRectangle((String)localHashtable.get("maindialcentre"));
    this.MINIDIALCENTRE = parseRectangle((String)localHashtable.get("minidialcentre"));
    this.UPLOADREADOUT = parsePoint((String)localHashtable.get("uploadreadout"));
    this.UPLOADREADOUTUNITS = parsePoint((String)localHashtable.get("uploadreadoutunits"));
    this.UPLOADREADOUTALIGN = parseAlign((String)localHashtable.get("uploadreadoutalign"));
    this.SHOWUPLOADREADOUTDURING = (!"no".equals((String)localHashtable.get("showuploadreadoutduring")));
    this.UPLOADREADOUTDIGITS = new Rectangle[11];
    for (int i = 0; i < 11; i++)
      this.UPLOADREADOUTDIGITS[i] = parseRectangle((String)localHashtable.get("uploadreadoutdigits" + (i <= 9 ? i : ".")));
    this.UPLOADREADOUTSI = new Rectangle[3];
    this.UPLOADREADOUTSI[0] = parseRectangle((String)localHashtable.get("uploadreadoutkbps"));
    this.UPLOADREADOUTSI[1] = parseRectangle((String)localHashtable.get("uploadreadoutmbps"));
    this.UPLOADREADOUTSI[2] = parseRectangle((String)localHashtable.get("uploadreadoutgbps"));
    this.DOWNLOADREADOUT = parsePoint((String)localHashtable.get("downloadreadout"));
    this.DOWNLOADREADOUTUNITS = parsePoint((String)localHashtable.get("downloadreadoutunits"));
    this.DOWNLOADREADOUTALIGN = parseAlign((String)localHashtable.get("downloadreadoutalign"));
    this.SHOWDOWNLOADREADOUTDURING = (!"no".equals((String)localHashtable.get("showdownloadreadoutduring")));
    this.DOWNLOADREADOUTDIGITS = new Rectangle[11];
    for (i = 0; i < 11; i++)
      this.DOWNLOADREADOUTDIGITS[i] = parseRectangle((String)localHashtable.get("downloadreadoutdigits" + (i <= 9 ? i : ".")));
    this.DOWNLOADREADOUTSI = new Rectangle[3];
    this.DOWNLOADREADOUTSI[0] = parseRectangle((String)localHashtable.get("downloadreadoutkbps"));
    this.DOWNLOADREADOUTSI[1] = parseRectangle((String)localHashtable.get("downloadreadoutmbps"));
    this.DOWNLOADREADOUTSI[2] = parseRectangle((String)localHashtable.get("downloadreadoutgbps"));
    this.CURRENTSPEEDPOS = parsePoint((String)localHashtable.get("currentspeedpos"));
    this.CURRENTSPEEDUNITSPOS = parsePoint((String)localHashtable.get("currentspeedunitspos"));
    this.CURRENTUPSPEEDUNITSPOS = parsePoint((String)localHashtable.get("currentupspeedunitspos"));
    this.CURRENTSPEEDALIGN = parseAlign((String)localHashtable.get("currentspeedalign"));
    this.CURRENTSPEEDDIGITS = new Rectangle[11];
    for (i = 0; i < 11; i++)
      this.CURRENTSPEEDDIGITS[i] = parseRectangle((String)localHashtable.get("currentspeeddigits" + (i <= 9 ? i : ".")));
    this.CURRENTSPEEDSI = new Rectangle[3];
    this.CURRENTSPEEDSI[0] = parseRectangle((String)localHashtable.get("currentspeedkbps"));
    this.CURRENTSPEEDSI[1] = parseRectangle((String)localHashtable.get("currentspeedmbps"));
    this.CURRENTSPEEDSI[2] = parseRectangle((String)localHashtable.get("currentspeedgbps"));
    this.CUSTOMTRACKDIAL = parseRectangle((String)localHashtable.get("customtrackdial"));
    this.CUSTOMTRACKDIALPOS = parsePoint((String)localHashtable.get("customtrackdialpos"));
    this.LCDDIGITS = new Rectangle[11];
    this.LCDDIGITS[0] = parseRectangle((String)localHashtable.get("lcddigits0"));
    this.LCDDIGITS[1] = parseRectangle((String)localHashtable.get("lcddigits1"));
    this.LCDDIGITS[2] = parseRectangle((String)localHashtable.get("lcddigits2"));
    this.LCDDIGITS[3] = parseRectangle((String)localHashtable.get("lcddigits3"));
    this.LCDDIGITS[4] = parseRectangle((String)localHashtable.get("lcddigits4"));
    this.LCDDIGITS[5] = parseRectangle((String)localHashtable.get("lcddigits5"));
    this.LCDDIGITS[6] = parseRectangle((String)localHashtable.get("lcddigits6"));
    this.LCDDIGITS[7] = parseRectangle((String)localHashtable.get("lcddigits7"));
    this.LCDDIGITS[8] = parseRectangle((String)localHashtable.get("lcddigits8"));
    this.LCDDIGITS[9] = parseRectangle((String)localHashtable.get("lcddigits9"));
    this.LCDDIGITS[10] = parseRectangle((String)localHashtable.get("lcddigits."));
    this.LCDROWS = new Point[4];
    this.LCDROWALIGN = new int[4];
    for (i = 0; i < 4; i++)
    {
      this.LCDROWS[i] = parsePoint((String)localHashtable.get("lcdrows" + i));
      this.LCDROWALIGN[i] = parseAlign((String)localHashtable.get("lcdrowalign" + i));
    }
    i = SpeedTab.access$0((String)localHashtable.get("minidialpegs"));
    this.MINIDIALPEGDEGS = new int[i];
    this.MINIDIALPEGS = new Point[i];
    this.MINIDIALPEGALIGN = new int[i];
    for (int j = 0; j < i; j++)
    {
      this.MINIDIALPEGDEGS[j] = SpeedTab.access$0((String)localHashtable.get("minidialpegdegs" + j));
      this.MINIDIALPEGS[j] = parsePoint((String)localHashtable.get("minidialpegs" + j));
      this.MINIDIALPEGALIGN[j] = parseAlign((String)localHashtable.get("minidialpegalign" + j));
    }
    j = SpeedTab.access$0((String)localHashtable.get("maindialpegs"));
    this.MAINDIALPEGDEGS = new int[j];
    this.MAINDIALPEGS = new Point[j];
    this.MAINDIALPEGALIGN = new int[j];
    for (int k = 0; k < j; k++)
    {
      this.MAINDIALPEGDEGS[k] = SpeedTab.access$0((String)localHashtable.get("maindialpegdegs" + k));
      this.MAINDIALPEGS[k] = parsePoint((String)localHashtable.get("maindialpegs" + k));
      this.MAINDIALPEGALIGN[k] = parseAlign((String)localHashtable.get("maindialpegalign" + k));
    }
    this.MAINDIALDIGITS = new Rectangle[15];
    this.MAINDIALDIGITS[0] = parseRectangle((String)localHashtable.get("maindialdigits0"));
    this.MAINDIALDIGITS[1] = parseRectangle((String)localHashtable.get("maindialdigits1"));
    this.MAINDIALDIGITS[2] = parseRectangle((String)localHashtable.get("maindialdigits2"));
    this.MAINDIALDIGITS[3] = parseRectangle((String)localHashtable.get("maindialdigits3"));
    this.MAINDIALDIGITS[4] = parseRectangle((String)localHashtable.get("maindialdigits4"));
    this.MAINDIALDIGITS[5] = parseRectangle((String)localHashtable.get("maindialdigits5"));
    this.MAINDIALDIGITS[6] = parseRectangle((String)localHashtable.get("maindialdigits6"));
    this.MAINDIALDIGITS[7] = parseRectangle((String)localHashtable.get("maindialdigits7"));
    this.MAINDIALDIGITS[8] = parseRectangle((String)localHashtable.get("maindialdigits8"));
    this.MAINDIALDIGITS[9] = parseRectangle((String)localHashtable.get("maindialdigits9"));
    this.MAINDIALDIGITS[10] = parseRectangle((String)localHashtable.get("maindialdigits."));
    this.MAINDIALDIGITS[11] = parseRectangle((String)localHashtable.get("maindialdigitsk"));
    this.MAINDIALDIGITS[12] = parseRectangle((String)localHashtable.get("maindialdigitsm"));
    this.MAINDIALDIGITS[13] = parseRectangle((String)localHashtable.get("maindialdigitsg"));
    this.MAINDIALDIGITS[14] = parseRectangle((String)localHashtable.get("maindialdigitst"));
    this.MAINDIALSI = "yes".equals((String)localHashtable.get("maindialsi"));
    this.MINIDIALDIGITS = new Rectangle[15];
    this.MINIDIALDIGITS[0] = parseRectangle((String)localHashtable.get("minidialdigits0"));
    this.MINIDIALDIGITS[1] = parseRectangle((String)localHashtable.get("minidialdigits1"));
    this.MINIDIALDIGITS[2] = parseRectangle((String)localHashtable.get("minidialdigits2"));
    this.MINIDIALDIGITS[3] = parseRectangle((String)localHashtable.get("minidialdigits3"));
    this.MINIDIALDIGITS[4] = parseRectangle((String)localHashtable.get("minidialdigits4"));
    this.MINIDIALDIGITS[5] = parseRectangle((String)localHashtable.get("minidialdigits5"));
    this.MINIDIALDIGITS[6] = parseRectangle((String)localHashtable.get("minidialdigits6"));
    this.MINIDIALDIGITS[7] = parseRectangle((String)localHashtable.get("minidialdigits7"));
    this.MINIDIALDIGITS[8] = parseRectangle((String)localHashtable.get("minidialdigits8"));
    this.MINIDIALDIGITS[9] = parseRectangle((String)localHashtable.get("minidialdigits9"));
    this.MINIDIALDIGITS[10] = parseRectangle((String)localHashtable.get("minidialdigits."));
    this.MINIDIALDIGITS[11] = parseRectangle((String)localHashtable.get("minidialdigitsk"));
    this.MINIDIALDIGITS[12] = parseRectangle((String)localHashtable.get("minidialdigitsm"));
    this.MINIDIALDIGITS[13] = parseRectangle((String)localHashtable.get("minidialdigitsg"));
    this.MINIDIALDIGITS[14] = parseRectangle((String)localHashtable.get("minidialdigitst"));
    this.MINIDIALSI = "yes".equals((String)localHashtable.get("minidialsi"));
    this.QOSDIGITS = new Rectangle[10];
    this.QOSDIGITS[0] = parseRectangle((String)localHashtable.get("qosdigits0"));
    this.QOSDIGITS[1] = parseRectangle((String)localHashtable.get("qosdigits1"));
    this.QOSDIGITS[2] = parseRectangle((String)localHashtable.get("qosdigits2"));
    this.QOSDIGITS[3] = parseRectangle((String)localHashtable.get("qosdigits3"));
    this.QOSDIGITS[4] = parseRectangle((String)localHashtable.get("qosdigits4"));
    this.QOSDIGITS[5] = parseRectangle((String)localHashtable.get("qosdigits5"));
    this.QOSDIGITS[6] = parseRectangle((String)localHashtable.get("qosdigits6"));
    this.QOSDIGITS[7] = parseRectangle((String)localHashtable.get("qosdigits7"));
    this.QOSDIGITS[8] = parseRectangle((String)localHashtable.get("qosdigits8"));
    this.QOSDIGITS[9] = parseRectangle((String)localHashtable.get("qosdigits9"));
    this.QOSPERCENT = parseRectangle((String)localHashtable.get("qospercent"));
    this.QOSDISPLAY = parseRectangle((String)localHashtable.get("qosdisplay"));
    this.QOSBARS = new Rectangle[10];
    this.QOSBARS[0] = parseRectangle((String)localHashtable.get("qosbars0"));
    this.QOSBARS[1] = parseRectangle((String)localHashtable.get("qosbars1"));
    this.QOSBARS[2] = parseRectangle((String)localHashtable.get("qosbars2"));
    this.QOSBARS[3] = parseRectangle((String)localHashtable.get("qosbars3"));
    this.QOSBARS[4] = parseRectangle((String)localHashtable.get("qosbars4"));
    this.QOSBARS[5] = parseRectangle((String)localHashtable.get("qosbars5"));
    this.QOSBARS[6] = parseRectangle((String)localHashtable.get("qosbars6"));
    this.QOSBARS[7] = parseRectangle((String)localHashtable.get("qosbars7"));
    this.QOSBARS[8] = parseRectangle((String)localHashtable.get("qosbars8"));
    this.QOSBARS[9] = parseRectangle((String)localHashtable.get("qosbars9"));
    this.QOSBARTOPLEFT = parsePoint((String)localHashtable.get("qosbartopleft"));
    this.CUSTOMSTART = "yes".equals((String)localHashtable.get("customstart"));
    this.SHOWRESTART = "yes".equals((String)localHashtable.get("showrestart"));
    this.STARTBUTTON = parsePoint((String)localHashtable.get("startbutton"));
    this.STARTACTIVEAREA = parseRectangle((String)localHashtable.get("startactivearea"));
    this.STARTBUTTONDEFAULT = parseRectangle((String)localHashtable.get("startbuttondefault"));
    this.STARTBUTTONMO = parseRectangle((String)localHashtable.get("startbuttonmo"));
    this.STARTBUTTONMD = parseRectangle((String)localHashtable.get("startbuttonmd"));
    this.RESTARTBUTTONDEFAULT = parseRectangle((String)localHashtable.get("restartbuttondefault"));
    this.RESTARTBUTTONMO = parseRectangle((String)localHashtable.get("restartbuttonmo"));
    this.RESTARTBUTTONMD = parseRectangle((String)localHashtable.get("restartbuttonmd"));
    this.INFOBUTTONDEFAULT = parseRectangle((String)localHashtable.get("infobuttondefault"));
    this.INFOBUTTONMO = parseRectangle((String)localHashtable.get("infobuttonmo"));
    this.INFOBUTTONMD = parseRectangle((String)localHashtable.get("infobuttonmd"));
    this.INFOBUTTONPOS = parsePoint((String)localHashtable.get("infobuttonpos"));
    this.CUSTOMPROGRESS = "yes".equals((String)localHashtable.get("customprogress"));
    this.PROGRESSBARPOS = parsePoint((String)localHashtable.get("progressbarpos"));
    this.PROGRESSBAR = parseRectangle((String)localHashtable.get("progressbar"));
    this.PROGRESSBARENDCAP = parseRectangle((String)localHashtable.get("progressbarendcap"));
    this.KEEPCOMPLETEDPROGRESS = "yes".equals((String)localHashtable.get("keepcompletedprogress"));
    this.PROGRESSREVERSEONUPLOAD = "yes".equals((String)localHashtable.get("progressreverseonupload"));
    this.PROGRESSREVERSEONDOWNLOAD = "yes".equals((String)localHashtable.get("progressreverseondownload"));
    this.UPRTTPOS = parsePoint((String)localHashtable.get("uprtt"));
    this.DOWNRTTPOS = parsePoint((String)localHashtable.get("downrtt"));
    this.UPLOADMESSAGE = parseRectangle((String)localHashtable.get("uploadmessage"));
    this.UPLOADMESSAGEPOS = parsePoint((String)localHashtable.get("uploadmessagepos"));
    this.DOWNLOADOVERLAY = parseRectangle((String)localHashtable.get("downloadoverlay"));
    this.UPLOADOVERLAY = parseRectangle((String)localHashtable.get("uploadoverlay"));
    this.DONEOVERLAY = parseRectangle((String)localHashtable.get("doneoverlay"));
  }

  private static Rectangle parseRectangle(String paramString)
  {
    try
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      return new Rectangle(Integer.parseInt(localStringTokenizer.nextToken()), Integer.parseInt(localStringTokenizer.nextToken()), Integer.parseInt(localStringTokenizer.nextToken()), Integer.parseInt(localStringTokenizer.nextToken()));
    }
    catch (Exception localException)
    {
    }
    return new Rectangle();
  }

  private static Point parsePoint(String paramString)
  {
    try
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      return new Point(Integer.parseInt(localStringTokenizer.nextToken()), Integer.parseInt(localStringTokenizer.nextToken()));
    }
    catch (Exception localException)
    {
    }
    return new Point();
  }

  private static int parseAlign(String paramString)
  {
    int i = 0;
    if (paramString != null)
    {
      if (paramString.indexOf("top") >= 0)
        i |= 1;
      else if (paramString.indexOf("middle") >= 0)
        i |= 2;
      else if (paramString.indexOf("bottom") >= 0)
        i |= 4;
      if (paramString.indexOf("left") >= 0)
        i |= 16;
      else if (paramString.indexOf("centre") >= 0)
        i |= 32;
      else if (paramString.indexOf("right") >= 0)
        i |= 64;
    }
    return i;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.speed.SpeedTab.DialMetrics
 * JD-Core Version:    0.6.2
 */