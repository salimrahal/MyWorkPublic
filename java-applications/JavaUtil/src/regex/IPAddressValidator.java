/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package regex;

/**http://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
 *
 * @author salim
 ^		#start of the line
 (		#  start of group #1
   [01]?\\d\\d? #    Can be one or two digits. If three digits appear, it must start either 0 or 1
		#    e.g ([0-9], [0-9][0-9],[0-1][0-9][0-9])
    |		#    ...or
   2[0-4]\\d	#    start with 2, follow by 0-4 and end with any digit (2[0-4][0-9]) 
    |           #    ...or
   25[0-5]      #    start with 2, follow by 5 and ends with 0-5 (25[0-5]) 
 )		#  end of group #2
  \.            #  follow by a dot "."
....            # repeat with 3 times (3x)
$		#end of the line

 */
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class IPAddressValidator{
 
    private Pattern pattern;
    private Matcher matcher;
 
    private static final String IPADDRESS_PATTERN = 
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
 
    public IPAddressValidator(){
	  pattern = Pattern.compile(IPADDRESS_PATTERN);
    }
 
   /**
    * Validate ip address with regular expression
    * @param ip ip address for validation
    * @return true valid ip address, false invalid ip address
    */
    public boolean validate(final String ip){		  
	  matcher = pattern.matcher(ip);
	  return matcher.matches();	    	    
    }
    
    public static void main(String[] args){
     IPAddressValidator validator = new IPAddressValidator();
        System.out.println(validator.validate("192.168.5.100"));
    }
}