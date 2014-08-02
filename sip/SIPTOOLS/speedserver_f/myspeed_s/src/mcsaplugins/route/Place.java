package mcsaplugins.route;

import java.io.PrintStream;
import java.util.StringTokenizer;

public class Place
{
  public String key;
  public String mapname;
  public String fullname;
  public double lat;
  public double lon;
  public boolean bHaveLatLon;

  public String toAppletString()
  {
    return this.key + ";" + (this.bHaveLatLon ? this.lat + ";" + this.lon + ";" : "-;-;") + this.fullname + ";" + this.mapname;
  }

  public static Place fromAppletString(String paramString)
  {
    try
    {
      Place localPlace = new Place();
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
      localPlace.key = localStringTokenizer.nextToken();
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      localPlace.bHaveLatLon = ((!"-".equals(str1)) && (!"-".equals(str2)));
      if (localPlace.bHaveLatLon)
      {
        localPlace.lat = Double.valueOf(str1).doubleValue();
        localPlace.lon = Double.valueOf(str2).doubleValue();
      }
      localPlace.fullname = localStringTokenizer.nextToken();
      localPlace.mapname = localStringTokenizer.nextToken();
      return localPlace;
    }
    catch (Exception localException)
    {
      System.out.println("bad data: " + localException + " on " + paramString);
    }
    return null;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.Place
 * JD-Core Version:    0.6.2
 */