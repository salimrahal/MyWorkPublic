package mcsaplugins.route;

import java.util.StringTokenizer;

public class TraceRouteHop
{
  private static final String SEP_CHAR = ";";
  public String hop;
  public int loss;
  public String ip;
  public String dns;
  public int ms;
  public int minMs;
  public int maxMs;
  public int sentTTL;
  public int nAffinity = -1;
  public String network;
  public String location;
  public String locCode;
  public boolean bAuthoritativeLocation;
  public boolean bTerminal = false;
  public boolean bInitial = false;

  public String getDisplayDNS()
  {
    if (this.dns == null)
      return "-";
    if ("*".equals(this.dns))
      return "-";
    return this.dns;
  }

  public static TraceRouteHop fromAppletString(String paramString)
  {
    try
    {
      TraceRouteHop localTraceRouteHop = new TraceRouteHop();
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
      localTraceRouteHop.hop = localStringTokenizer.nextToken();
      localTraceRouteHop.sentTTL = Integer.parseInt(localTraceRouteHop.hop);
      localTraceRouteHop.ip = localStringTokenizer.nextToken();
      localTraceRouteHop.ms = Integer.parseInt(localStringTokenizer.nextToken());
      localTraceRouteHop.minMs = Integer.parseInt(localStringTokenizer.nextToken());
      localTraceRouteHop.maxMs = Integer.parseInt(localStringTokenizer.nextToken());
      localTraceRouteHop.nAffinity = Integer.parseInt(localStringTokenizer.nextToken());
      localTraceRouteHop.loss = Integer.parseInt(localStringTokenizer.nextToken());
      localTraceRouteHop.dns = localStringTokenizer.nextToken();
      localTraceRouteHop.network = localStringTokenizer.nextToken();
      String str = localStringTokenizer.nextToken();
      localTraceRouteHop.bAuthoritativeLocation = (!str.endsWith("?"));
      localTraceRouteHop.location = (localTraceRouteHop.bAuthoritativeLocation ? str : str.substring(0, str.length() - 1));
      return localTraceRouteHop;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.TraceRouteHop
 * JD-Core Version:    0.6.2
 */