package mcsaplugins.route;

import java.io.DataInputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class HopServerQuery
  implements Runnable
{
  public static route g_applet;
  private static Vector g_queries = new Vector();
  private static Hashtable g_ipToNetDesc = new Hashtable();
  private static Hashtable g_ipToLocCode = new Hashtable();
  private static Hashtable g_ipToPlacekey = new Hashtable();
  private static Hashtable g_ipToName = new Hashtable();
  private String m_ip = null;
  private String m_nname = null;

  private HopServerQuery(String paramString1, String paramString2)
  {
    this.m_ip = paramString1;
    this.m_nname = paramString2;
    Thread localThread = new Thread(this, "MyRoute-ServerLookup-" + this.m_ip + "-" + hashCode());
    localThread.start();
  }

  private static synchronized void query(String paramString1, String paramString2)
  {
    if (!ipValid(paramString1))
      return;
    if (currentlyQuerying(paramString1))
      return;
    g_queries.add(paramString1);
    new HopServerQuery(paramString1, paramString2);
  }

  private static boolean ipValid(String paramString)
  {
    return (!"?".equals(paramString)) && (!"...".equals(paramString)) && (paramString != null) && (!"-".equals(paramString));
  }

  public static String getNameString(String paramString1, String paramString2)
  {
    if (!ipValid(paramString1))
      return null;
    String str = (String)g_ipToName.get(paramString1);
    if (str == null)
    {
      query(paramString1, paramString2);
      return null;
    }
    return str;
  }

  public static String getGeoString(String paramString1, String paramString2)
  {
    if (!ipValid(paramString1))
      return null;
    String str = (String)g_ipToLocCode.get(paramString1);
    if (str == null)
    {
      query(paramString1, paramString2);
      return null;
    }
    return str;
  }

  public static String getLocation(String paramString1, String paramString2)
  {
    if (!ipValid(paramString1))
      return "-";
    String str = (String)g_ipToPlacekey.get(paramString1);
    if (str == null)
    {
      query(paramString1, paramString2);
      return null;
    }
    return str;
  }

  public static String getNetworkDesc(String paramString1, String paramString2)
  {
    if (!ipValid(paramString1))
      return "-";
    String str = (String)g_ipToNetDesc.get(paramString1);
    if (str == null)
    {
      query(paramString1, paramString2);
      return null;
    }
    return str;
  }

  public static boolean currentlyQuerying(String paramString)
  {
    return g_queries.contains(paramString);
  }

  private void handleReQueryData(String paramString)
  {
    int i = paramString.indexOf(",");
    if (i == -1)
      return;
    paramString = paramString.substring(i + 1);
    if (paramString.startsWith("nwho="))
    {
      paramString = paramString.substring(5);
      setHopData(this.m_ip, paramString, "nwho");
    }
    else if (paramString.startsWith("geo="))
    {
      paramString = paramString.substring(4);
      setHopData(this.m_ip, paramString, "geo");
    }
    else if (paramString.startsWith("name="))
    {
      paramString = paramString.substring(5);
      setHopData(this.m_ip, paramString, "name");
    }
  }

  private void setHopData(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1 == null)
      return;
    if ("nwho".equals(paramString3))
    {
      g_ipToNetDesc.put(paramString1, paramString2);
    }
    else if ("geo".equals(paramString3))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString2, ";");
      String str = localStringTokenizer.nextToken();
      g_ipToPlacekey.put(paramString1, str);
      g_ipToLocCode.put(paramString1, paramString2);
      if (!"-".equals(str))
        U.setPlaces(paramString2);
    }
    else if ("name".equals(paramString3))
    {
      g_ipToName.put(paramString1, paramString2);
    }
  }

  public void run()
  {
    DataInputStream localDataInputStream = null;
    try
    {
      String str = "&detail.nname=" + U.urlEncode(this.m_nname) + "&detail.ips=" + U.urlEncode(this.m_ip);
      URL localURL = new URL(g_applet.getAppletCodeBase(), "plugin/route/applet/relookup?" + str);
      localDataInputStream = new DataInputStream(localURL.openStream());
      Object localObject1 = null;
      while ((localObject1 = localDataInputStream.readLine()) != null)
      {
        if ("EOF".equals(localObject1))
          break;
        handleReQueryData((String)localObject1);
      }
      if (getLocation(this.m_ip, this.m_nname) == null)
        g_ipToPlacekey.put(this.m_ip, "-");
      g_queries.remove(this.m_ip);
      return;
    }
    catch (Exception localException1)
    {
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException4)
      {
      }
    }
    g_ipToNetDesc.put(this.m_ip, "-");
    g_ipToPlacekey.put(this.m_ip, "-");
    g_queries.remove(this.m_ip);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.HopServerQuery
 * JD-Core Version:    0.6.2
 */