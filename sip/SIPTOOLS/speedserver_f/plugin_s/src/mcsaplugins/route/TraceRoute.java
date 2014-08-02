package mcsaplugins.route;

import java.util.StringTokenizer;
import myspeedserver.applet.ErrorCode;

public class TraceRoute
  implements Cloneable
{
  private static final String ERR_LOCAL = "ERRLOC";
  private static final String ERR_SECURE = "ERRSEC";
  private static final String ERR_DNS = "ERRDNS";
  private static final String ERR_RANGE = "ERRRAN";
  private static final int ERR_CODE_LOCAL = 1;
  private static final int ERR_CODE_DNS = 2;
  private static final int ERR_CODE_SECURE = 3;
  private static final int ERR_CODE_RANGE = 4;
  public String ip;
  public String ipFrom;
  public String nameFrom;
  public String nname;
  public String uhl;
  public String error;
  public long time = 0L;
  public TraceRouteHop[] hops;
  public ErrorCode errorResult = null;

  public TraceRoute doClone()
  {
    try
    {
      return (TraceRoute)super.clone();
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static TraceRoute fromAppletString(String paramString1, String paramString2)
  {
    try
    {
      TraceRoute localTraceRoute = new TraceRoute();
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",");
      localTraceRoute.ip = localStringTokenizer.nextToken();
      localTraceRoute.nname = localStringTokenizer.nextToken();
      localTraceRoute.ipFrom = localStringTokenizer.nextToken();
      localTraceRoute.uhl = paramString2;
      int i = Integer.parseInt(localStringTokenizer.nextToken());
      localTraceRoute.hops = new TraceRouteHop[i];
      for (int j = 0; j < i; j++)
      {
        String str = localStringTokenizer.nextToken();
        localTraceRoute.hops[j] = TraceRouteHop.fromAppletString(str);
        if (localTraceRoute.hops[j] == null)
          return null;
        localTraceRoute.hops[j].bTerminal = localTraceRoute.ip.equals(localTraceRoute.hops[j].ip);
      }
      return localTraceRoute;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static int mapOldStringToNewInt(String paramString)
  {
    if ("ERRSEC".equals(paramString))
      return 3;
    if ("ERRRAN".equals(paramString))
      return 4;
    if ("ERRLOC".equals(paramString))
      return 1;
    if ("ERRDNS".equals(paramString))
      return 2;
    return -1;
  }

  public static String mapToOldStringError(int paramInt)
  {
    if (paramInt == 3)
      return "ERRSEC";
    if (paramInt == 4)
      return "ERRRAN";
    if (paramInt == 1)
      return "ERRLOC";
    if (paramInt == 2)
      return "ERRDNS";
    return "";
  }

  public static String mapErrorDesc(int paramInt, String paramString)
  {
    if (paramInt == 3)
      return "Trace IP witin secure IP list '" + paramString + "'";
    if (paramInt == 4)
      return "Trace IP outside allowed range '" + paramString + "'";
    if (paramInt == 1)
      return "Trace IP was local '" + paramString + "'";
    if (paramInt == 2)
      return "DNS Lookup failed '" + paramString + "'";
    return "";
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.TraceRoute
 * JD-Core Version:    0.6.2
 */