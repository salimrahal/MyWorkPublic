//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import org.apache.log4j.Logger;
//
//public class DateTester {
//	private static Logger log = Logger.getLogger(DateTester.class);
//	
//	public static void main(String[] args) {
//		String mdate = "12/26/2011 22:57:46 PDT";
//		String mdate2 = "12/26/2011 11:57:46 pm PST";
//		//TimeZone.setDefault(TimeZone.getTimeZone("PST"));
//		//System.out.println(TimeZone.getAvailableIDs());
//		System.out.println(mdate2);
//		System.out.println(getDateTimeAs12H(mdate2));
//		
//		System.out.println(TimeZone.getDefault().getDisplayName());
//		for(String l : TimeZone.getAvailableIDs()) {
//			//System.out.println(l);
//		}
//	}
//	
//	/**
//	 * this method checks whether a date string is in 12H format or 24H format.
//	 * it assumes if one of the following conditions are met then it is in 12H format:
//	 * 		- contains [space][AM|PM|am|pm][space]
//	 * 		- ends with [space][space][AM|PM|am|pm]
//	 * @param dateStr
//	 * @return true if it is in 12H format , false otherwise
//	 */
//	private static boolean isItAlready12H(String dateStr) {
//		boolean already12hFormat = false;
//		
//		if(dateStr.contains(" AM ") || dateStr.contains(" PM ") || 
//				dateStr.endsWith(" AM") || dateStr.endsWith(" PM") ||
//				dateStr.contains(" am ") || dateStr.contains(" pm ") || 
//				dateStr.endsWith(" am") || dateStr.endsWith(" pm") ) {
//			already12hFormat = true;
//		}
//		return already12hFormat;
//	}
//	
//	/**
//	 * this method takes as input a date string, checks whether it is in 12H format or not. If yes it returns it as is
//	 * otherwise it converts it to 12h format. 
//	 * Note that if any Time Zone found in the date string it is returned as is.
//	 * @param dateStr : A date string
//	 * @return String containing the date formatted as 12H format
//	 */
//	public static String getDateTimeAs12H(String dateStr) {
//        String formattedDate = null;
//        if(dateStr != null && ! dateStr.equals("")) {
//        	if(isItAlready12H(dateStr)) {
//        		// return it as it is.
//        		if(log.isDebugEnabled()) log.debug("input date [" + dateStr + "] is already in AM/PM format no need for further transformation");
//        		formattedDate = dateStr;
//        	} else {
//        		// check if time zone was set in the input date
//        		String tz = extractTimeZone(dateStr);
//        		String inputDateFormat = "MM/dd/yyyy HH:mm:ss";
//        		String outputDateFormat = "MM/dd/yyyy hh:mm:ss a";
//        		SimpleDateFormat parseFormat = new SimpleDateFormat(inputDateFormat);
//        		try {
//        			Date inDate = parseFormat.parse(dateStr);
//        			SimpleDateFormat displayFormat = new SimpleDateFormat(outputDateFormat,Locale.US);
//        			formattedDate = displayFormat.format(inDate);
//        			//if we have a time zone then concatenate it to the formatted string
//        			formattedDate += " " + tz;
//        		} catch (ParseException e) {
//        			log.warn("[Converting from 24 to 12H format: " +
//        					"Input date [" + dateStr + "] does not match expected format [" + inputDateFormat + "]");
//        			//in case of error return the input date as it is
//        			formattedDate = dateStr;
//        		}
//        	}
//        } else { 
//              if(log.isDebugEnabled()) log.debug("found a null-date while converting from a date 24H format to 12H format");
//        }
//        return formattedDate;
//	  }
//
//	  /**
//	   * Extracts the time zone as string from the full date string.
//	   * it assumes the input date string is in 24h format
//	   * @param dateStr : the date string possible containing the time zone
//	   * @return String containing the time zone
//	   */
//	  private static String extractTimeZone(String dateStr) {
//		  String timeZone = "";
//		  int lastTimeSeparator = dateStr.lastIndexOf(":");
//		  // extract time zone only when we have : inside the date and there's at least 2 chars after it (11:12:04 PDT)
//		  if(lastTimeSeparator > 0 && dateStr.length() > lastTimeSeparator + 4) {
//			  timeZone = dateStr.substring(lastTimeSeparator+4);
//		  }
//		  return timeZone;
//	}
//
////	public static String getDateTimeAs12H(String dateStr) {
////	        String formattedDate = null;
////	        String inputDateFormat = "MM/dd/yyyy HH:mm:ss";
////	        String outputDateFormat = "MM/dd/yyyy hh:mm:ss a";
////	        if(dateStr != null && ! dateStr.equals("")) {
////	              SimpleDateFormat parseFormat = new SimpleDateFormat(inputDateFormat,Locale.US);
////	              System.out.println(parseFormat.getTimeZone().getDisplayName());
////	              try {
////	                  Date inDate = parseFormat.parse(dateStr);
////	                  SimpleDateFormat displayFormat = new SimpleDateFormat(outputDateFormat,Locale.US);
////	                  formattedDate = displayFormat.format(inDate);
////	            } catch (ParseException e) {
////	                  log.error("Input date [" + dateStr + "] does not match expected format [" + inputDateFormat + "]");
////	                  //in case of error return the input date as it is
////	                  formattedDate = dateStr;
////	            }
////	        } else { 
////	              if(log.isDebugEnabled()) log.debug("found a null-date while converting from a date 24H format to 12H format");
////	        }
////	        return formattedDate;
////	  }
////
//}
