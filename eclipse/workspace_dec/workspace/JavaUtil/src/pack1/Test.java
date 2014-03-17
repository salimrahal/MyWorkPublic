package pack1;

import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// UtilMethods.formatDateTime12("12/20/2011 10:05:36 PDT");
		// System.out.print(UtilMethods.getDateTimeAs12H("12/26/2011 10:57:46 PM GMT"));
		// System.out.print(UtilMethods.getDateTimeAs12H("06/09/2009 17:00:00"));
		// System.out.print(UtilMethods.getDateTimeAs12HByFormat("06/09/2009 01:31:00","MM/dd/yyyy HH:mm:ss","MM/dd/yyyy hh:mm:ss a"));
		// System.out.print(UtilMethods.getDate("01/10/2012 13:45:22"));

		// String mdate = "12/26/2011 22:57:46 PDT";
		// String mdate2 = "12/26/2011 13:57:46 GMT";
		// //TimeZone.setDefault(TimeZone.getTimeZone("PST"));
		// //System.out.println(TimeZone.getAvailableIDs());
		// System.out.println(mdate2+"\n");
		// System.out.println(UtilMethods.getDateTimeAs12H(mdate2));
		//
		// //System.out.println(TimeZone.getDefault().getDisplayName());
		// for(String l : TimeZone.getAvailableIDs()) {
		// System.out.println(l);
		// UtilMethods.longTest();
		// UtilMethods.IntegerObjectEqualityTest();
		// UtilMethods.testNull(null);
		// System.out.println(UtilMethods.m3FactIt(100));
		// System.out.println(StringMethods.getFirstLetter("salim^MDantoine^rahal^"));
		// System.out.println(StringMethods.makeInitials("salim"));
		// System.out.println(StringMethods.makeInitials("salim rahal MD ZZ"));
		// System.out.println(StringMethods.makeInitials("salim^rahal^MD^^ZZ"));
		// System.out.println(StringMethods.makeInitials(""));
		//
		// String[] x = "salimooooo".split("\\^");
		// System.out.println(x[0]);
		//
		/************************ THREAD TEST *******/
		// Thread th = new Thread(new ThreadRunnable());
		// th.start();
		// Thread th = new Thread(new ThreadRunnable());
		// th.start();
		//
		// Thread th1 = new Thread(new ThreadRunnable());
		// th1.start();
		/**************** some test *******************/
		// System.out.println(StringMethods.getExtAccession("Inte__ext"));
		// System.out.println(StringMethods.getExtAccession("ext1"));
		// System.out.println("ext-- "+ StringMethods.getExtAccession("__"));
		//

		/******** difference between ++i i++ *************/
		// UtilMethods.incrementBeforeAfterUsage();

		System.out.println("10" + 1);// string concat
		System.out.println(1 + "10");// string concat
		StringMethods.stringVSStringBuilder();
	}

}
