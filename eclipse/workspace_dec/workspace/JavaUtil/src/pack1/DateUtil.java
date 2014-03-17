package pack1;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Compressus</p>
 * @author ramon
 * @version $Revision: 1.1 $
 */

public class DateUtil 
{
    public static Date dateTimeToDate( int date, double time )
  {
    return dateTimeToDate( Calendar.getInstance(), date, time );
  }
  
  public static Date dateTimeToDate( Calendar cal, int date, double time )
  {
    int year = date / 10000;
    int remainder = date - ( year * 10000 );
    int month = remainder / 100;
    int day = remainder - ( month * 100 );
    int hour = (int) (time / 3600);
    remainder = (int) ( time - ( hour * 3600 ) );
    int minute = remainder / 60;
    int second = remainder - ( minute * 60 );
    // we need to add ms at some point
    cal.set( year, month-1, day, hour, minute, second ); // month is zero based
    return cal.getTime();
  }
  
  
  
  public static void elseTest(boolean val)
  {
     
     if(val){System.out.println("True");}
     else
          System.out.println("1-----else");
          System.out.println("2-----else");
  }
}
