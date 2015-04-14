package pack1;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

public class UtilMethods
{
//   static Log log = LogFactory.getLog(UtilMethods.class);

	public static void main(String[] args)
	{
		incrementBeforeAfterUsage();
	}
   public static void formatDateTime24()
   {
      // format 12 hours time to 24 hours time
      SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
      SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
      Date date = null;
      try
      {
         date = parseFormat.parse("10:30 PM");
      }
      catch(ParseException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));

   }

   public static void formatDateTime12(String dateStr)
   {
      // format 12 hours time to 24 hours time
      SimpleDateFormat parseFormat = new SimpleDateFormat("dd/mm/yy HH:mm:ss zz");
      SimpleDateFormat displayFormat = new SimpleDateFormat("dd/mm/yy hh:mm:ss a zz");
      // time zone
      String[] tz = dateStr.split(" ");
      TimeZone firstTime = TimeZone.getTimeZone(tz[tz.length - 1]);
      parseFormat.setTimeZone(firstTime);
      displayFormat.setTimeZone(firstTime);
      Date date = null;
      try
      {
         date = parseFormat.parse(dateStr);
      }
      catch(ParseException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));

   }

   public static String getDateTimeAs12H0(String dateStr)
   {
      // format 12 hours time to 24 hours time
      String timeZone = "";

      if(dateStr != null)
      { // if we have 24 hour format
         if(!dateStr.toLowerCase().contains("am") && !dateStr.toLowerCase().contains("pm"))
         {
            SimpleDateFormat parseFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss zz");
            SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            // time zone
            String[] tz = dateStr.split(" ");
            // to avoid the repetition and getting the wrong timezone value
            if(tz.length - 2 != tz.length - 1)
            {
               timeZone = tz[tz.length - 1];
            }
            Date date = null;

            try
            {
               date = parseFormat.parse(dateStr);
            }
            catch(ParseException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }

            dateStr = displayFormat.format(date).toString() + " " + timeZone;
         }
      }
      return dateStr;
   }
   /**
    * 
    * @param dateStr
    * @param inputDateFormat
    * @param outputDateFormat
    * @return
    * @throws ParseException
    */
   private static String dateParser(String dateStr, String inputDateFormat, String outputDateFormat) throws ParseException
   {
      String formattedDate = null;
      SimpleDateFormat parseFormat = null;
      parseFormat = new SimpleDateFormat(inputDateFormat);
      Date inDate = parseFormat.parse(dateStr);
      SimpleDateFormat displayFormat = new SimpleDateFormat(outputDateFormat);
      formattedDate = displayFormat.format(inDate);
      return formattedDate;
   }

   public static String getDateTimeAs12HByFormat(String dateStr, String inputDateFormat, String outputDateFormat)
   {
      String formattedDate = null;
      if(dateStr != null && !dateStr.equals(""))
      {
         try
         {
            formattedDate = dateParser(dateStr, inputDateFormat, outputDateFormat);
         }
         catch(ParseException e)
         {
//            log.error("Input date [" + dateStr + "] does not match expected format [" + inputDateFormat + "]");
            // in case of error return the input date as it is
            formattedDate = dateStr;
         }
      }
      else
      {
//         if(log.isWarnEnabled()) log.warn("found a null-date while converting from a date 24H format to 12H format");
      }
      return formattedDate;
   }

   /**
    * convert date to 12 if we have a GMT else, return the same Date
    * 
    * @param dateStr
    * @return
    */
   public static String getDateTimeAs12HV0(String dateStr)
   {
      String formattedDate = null;
      String inputDateFormat = "MM/dd/yyyy HH:mm:ss z";
      String outputDateFormat = "MM/dd/yyyy hh:mm:ss a z";
      if(dateStr != null && !dateStr.equals(""))
      {
         SimpleDateFormat parseFormat = new SimpleDateFormat(inputDateFormat);
         try
         {
            Date inDate = parseFormat.parse(dateStr);
            SimpleDateFormat displayFormat = new SimpleDateFormat(outputDateFormat);
            formattedDate = displayFormat.format(inDate);
         }
         catch(ParseException e)
         {
//            log.error("Input date [" + dateStr + "] does not match expected format [" + inputDateFormat + "]");
            // in case of error return the input date as it is
            formattedDate = dateStr;
         }
      }
      else
      {
//         if(log.isWarnEnabled()) log.warn("found a null-date while converting from a date 24H format to 12H format");
      }
      return formattedDate;
   }

   public static Date getDate(String milliseconds)
   {
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(Long.parseLong(milliseconds));
      return c.getTime();
   }

   /*
    * By Firas
    */
   /**
    * this method checks whether a date string is in 12H format or 24H format.
    * it assumes if one of the following conditions are met then it is in 12H
    * format: - contains [space][AM|PM|am|pm][space] - ends with
    * [space][space][AM|PM|am|pm]
    * 
    * @param dateStr
    * @return true if it is in 12H format , false otherwise
    */
   private static boolean isItAlready12H(String dateStr)
   {
      boolean already12hFormat = false;

      if(dateStr.contains(" AM ") || dateStr.contains(" PM ") || dateStr.endsWith(" AM") || dateStr.endsWith(" PM") || dateStr.contains(" am ") || dateStr.contains(" pm ") || dateStr
         .endsWith(" am") || dateStr.endsWith(" pm"))
      {
         already12hFormat = true;
      }
      return already12hFormat;
   }

   /**
    * this method takes as input a date string, checks whether it is in 12H
    * format or not. If yes it returns it as is otherwise it converts it to 12h
    * format. Note that if any Time Zone found in the date string it is returned
    * as is.
    * 
    * @param dateStr
    *           : A date string
    * @return String containing the date formatted as 12H format
    */
   public static String getDateTimeAs12H(String dateStr)
   {
      String formattedDate = null;
      if(dateStr != null && !dateStr.equals(""))
      {
         if(isItAlready12H(dateStr))
         {
            // return it as it is.
//            if(log.isDebugEnabled()) log.debug("input date [" + dateStr + "] is already in AM/PM format no need for further transformation");
            formattedDate = dateStr;
         }
         else
         {
            // check if time zone was set in the input date
            String tz = extractTimeZone(dateStr);
            String inputDateFormat = "MM/dd/yyyy HH:mm:ss";
            String outputDateFormat = "MM/dd/yyyy hh:mm:ss a";
            SimpleDateFormat parseFormat = new SimpleDateFormat(inputDateFormat);
            try
            {
               Date inDate = parseFormat.parse(dateStr);
               SimpleDateFormat displayFormat = new SimpleDateFormat(outputDateFormat, Locale.US);
               formattedDate = displayFormat.format(inDate);
               // if we have a time zone then concatenate it to the formatted
               // string
               formattedDate += " " + tz;
            }
            catch(ParseException e)
            {
//               log.warn("[Converting from 24 to 12H format: " + "Input date [" + dateStr + "] does not match expected format [" + inputDateFormat + "]");
               // in case of error return the input date as it is
               formattedDate = dateStr;
            }
         }
      }
      else
      {
//         if(log.isDebugEnabled()) log.debug("found a null-date while converting from a date 24H format to 12H format");
      }
      return formattedDate;
   }

   /**
    * Extracts the time zone as string from the full date string. it assumes the
    * input date string is in 24h format
    * 
    * @param dateStr
    *           : the date string possible containing the time zone
    * @return String containing the time zone
    */
   private static String extractTimeZone(String dateStr)
   {
      String timeZone = "";
      int lastTimeSeparator = dateStr.lastIndexOf(":");
      // extract time zone only when we have : inside the date and there's at
      // least 2 chars after it (11:12:04 PDT)
      if(lastTimeSeparator > 0 && dateStr.length() > lastTimeSeparator + 4)
      {
         timeZone = dateStr.substring(lastTimeSeparator + 4);
      }
      return timeZone;
   }

   public static void longTest()
   {
      Long a1 = new Long(1);
      Long a2 = new Long(1);
      Long a3 = new Long(11);
      System.out.println((a1.compareTo(a2)) + "");
      System.out.println((a1.compareTo(a3)) + "");
      System.out.println((a1 == a2) + "");
      System.out.println("equals " + a1.compareTo(a2) + "\n");
      System.out.println("equals " + a1.equals(a3) + "");
   }

   public static void IntegerObjectEqualityTest()
   {
      // assigning a value between [-128,127] use the IntegerCach Class
      // else it create a new Integer() Object
      Integer i1 = 1000;
      Integer i2 = 1000;
      System.out.println(i1 + "/" + i2);
      if(i1 != i2) System.out.println("different objects");
      if(Integer.valueOf(i1).equals(Integer.valueOf(i2))) System.out.println("Value Of same objects");
      // if(i1 == i2) System.out.println("same object");
      Integer i3 = 10;
      Integer i4 = 10;
      System.out.println(i3 + "/" + i4);
      if(i3 == i4) System.out.println("same object");

      Integer i5 = 127;
      Integer i6 = 127;
      System.out.println(i5 + "/" + i6);
      if(i5 == i6) System.out.println("127 range same object");

      Integer i7 = 128;
      Integer i8 = 128;
      // System.out.println(i5+"/"+i6);
      if(i7 == i8) System.out.println(" 128 same object");

      Integer i9 = 90;
      Integer i10 = 90;
      // System.out.println(i5+"/"+i6);
      if(i9 == i10) System.out.println("-- same object");
   }

   //suspicious test
   public static void testNull(String pName)
   {
      if(pName == null || pName.length() == 0)

      {
         System.out.println("ok");
      }
   }
   
   public static int m3FactIt(int var)
   {
      if(var == 1)
         return 1;
      else
      {
         System.out.println("recursif call");
        
         return (var * (m3FactIt(var - 1)));
      }
   }
   
//   public static BigInteger m3FactIt(BigInteger var)
//   {
//      if(var == 1)
//         return 1;
//      else
//      {
//         System.out.println("recursif call");
//        
//         return (var * (m3FactIt(var - 1)));
//      }
//   }
  
   public static void incrementBeforeAfterUsage()
   {
	   int i;
	   int j;
	    
	   i=1;
	   /*
	    * j=i;
          i=i+1;
	    */
	   j=i++;
	   System.out.println("i++: "+j);
	    
	   i=1;
	   /*
	    * i=i+1;
          j=i;
	    */
	   j=++i;
	   System.out.println("++i: "+j);
   }
 
   public static void numbersMethod()
   {
	   //  int a1 = 5; double a2 = (float)a1;
	   //  int a1 = 5; float a2 = (double)a1;//compile error 
	   int a = 9/0;//arithmetic exception
	   System.out.println(a);
   }

   // Operator : ?:
  public static void shortcutOperator()
  {
	  int a=1,b=2, max=0;
	  //single condition
	  if (a > b) {
		  max = a;
		}
		else {
		  max = b;
		}
	  
	  //or
	  max = (a>b)?a:b;
  }  
}
