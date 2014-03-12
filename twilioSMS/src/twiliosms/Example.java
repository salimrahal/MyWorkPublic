// Install the Java helper library from twilio.com/docs/java/install
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;
import com.twilio.sdk.resource.list.SmsList;
import java.util.HashMap;
import java.util.Map;

public class Example {
// Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC32a3c49700934481addd5ce1659f04d2";
    public static final String AUTH_TOKEN = "{{ auth_token }}";

    public static void main(String[] args) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
// Build a filter for the SmsList
        Map<String, String> params = new HashMap<String, String>();
        params.put("Body", "Jenny please?! I love you <3");
        params.put("To", "+14159352345");
        params.put("From", "+14158141829");
        SmsFactory messageFactory = client.getAccount().getSmsFactory();
        Sms message = messageFactory.create(params);
        System.out.println(message.getSid());
    }
}