package mcsaplugins.route;

import java.util.StringTokenizer;

public class PortInfo
{
  public boolean bConnected;
  public boolean bRefused;
  public String desc;
  public String protocol;
  public int port;

  public static PortInfo fromString(String paramString)
  {
    try
    {
      PortInfo localPortInfo = new PortInfo();
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      localPortInfo.protocol = localStringTokenizer.nextToken();
      localPortInfo.port = Integer.parseInt(localStringTokenizer.nextToken());
      String str = localStringTokenizer.nextToken();
      if ("connected".equals(str))
        localPortInfo.bConnected = true;
      else if ("refused".equals(str))
        localPortInfo.bRefused = true;
      if (localStringTokenizer.hasMoreTokens())
        localPortInfo.desc = localStringTokenizer.nextToken();
      return localPortInfo;
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.PortInfo
 * JD-Core Version:    0.6.2
 */