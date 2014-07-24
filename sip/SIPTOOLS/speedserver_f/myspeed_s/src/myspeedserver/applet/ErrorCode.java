package myspeedserver.applet;

public class ErrorCode
{
  public static final int ERRORCODE_SOURCE = -65536;
  public static final int ERRORCODE_SOURCE_RA = 16777216;
  public static final int ERRORCODE_SOURCE_APPLET = 33554432;
  public static final int ERRORCODE_RA_NORESULTS = 16777217;
  public static final int ERRORCODE_RA_TIMEOUT = 16777218;
  public static final int ERRORCODE_RA_FAILEDSTART = 16777219;
  public static final int ERRORCODE_RA_POSTFAILED = 16777220;
  public static final int ERRORCODE_RA_WRONGPASS = 16777221;
  public static final int ERRORCODE_RA_PLUGINDOWNLOAD = 16777222;
  public static final int ERRORCODE_RA_NOTLICENSED = 16777223;
  public static final int ERRORCODE_RA_UNKNOWN = 16777224;
  public static final int ERRORCODE_APPLET_UNKNOWN = 33554433;
  public static final int ERRORCODE_APPLET_HITMAXTESTS = 33554434;
  public static final int ERRORCODE_APPLET_REPORTFAIL = 33554435;
  public int nCode;
  public String desc;
  public String detail;

  public ErrorCode(int paramInt, String paramString)
  {
    this.nCode = paramInt;
    this.desc = paramString;
  }

  public void addDetail(String paramString1, String paramString2)
  {
    if (this.detail == null)
      this.detail = "";
    this.detail = (this.detail + "&detail." + paramString1 + "=" + paramString2);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.ErrorCode
 * JD-Core Version:    0.6.2
 */