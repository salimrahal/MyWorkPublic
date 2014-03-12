/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import vo.PresenceVO;
import java.security.MessageDigest;
import javax.sip.address.URI;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.ProxyAuthenticateHeader;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import javax.sip.header.ProxyAuthorizationHeader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


/**
 *
 * @author salim
 */
public class SipClientBO {

    //the limit to retry the Authntication request, unused yet  
    public static final int RETRY_AUTH_LIMIT = 20;
    //default expires 10 sec
    public static final int Expires_default = 30;
    public static final String ALGORITHM_MD5 = "MD5";
    //UTF encoding
    public static final String UTF_8_ENCODING = StandardCharsets.UTF_8.toString();
    //private String username;
    //private String password;
    private String realm;
    private String nonce;
    private String opaque;
    private URI requestUri;
    private String digest;
    private String method; //REGISTER, OPTIONS, etc
    private String algo; //e.g. md5
    private String profileUri;
    //this is the static list where we would store the presences objects
    public static List<PresenceVO> presenceList;
    
   
    public static List<PresenceVO> getPresenceList() {
        return presenceList;
    }

   
    public static void initializePresList() {
        presenceList = null;
    }

    @Deprecated
    public static List<PresenceVO> createPresenceList() {
        presenceList = new ArrayList<>();
        return presenceList;
    }

    //Unused hash<string,PresenceVO>==>List<PresenceVO>
    public static List hashtoList(Hashtable ht) {
        List list = null;
        if (!ht.isEmpty()) {
            list = new ArrayList();
            Enumeration enume = ht.keys();
            while (enume.hasMoreElements()) {
                String key = (String) enume.nextElement();
                PresenceVO value = (PresenceVO) ht.get(key);
                //PresenceVO o = new PresenceVO(key, value);
                list.add(value);
                System.out.println("HashtoList { " + value + " }");
            }
        } else {
            System.out.println("HashtoList { le hash est vide ! }");
        }
        return list;
    }

  

    /*
     * The sip server don't support subscribe to resource list pidf
     *   <?xml version="1.0" encoding="UTF-8"?>
     <resource-lists xmlns="urn:ietf:params:xml:ns:resource-lists"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
     <list>
     <entry uri="sip:bill@example.com" />
     <entry uri="sip:joe@example.org" />
     <entry uri="sip:ted@example.net" />
     </list>
     </resource-lists>
     *-- received XML from Fortivoice-----------
     * <?xml version="1.0" encoding="UTF-8"?>
     <presence>
     <presentity uri="sip:299@10.0.254.125:5060;method=SUBSCRIBE"/>
     <atom id="1">
     <address uri="sip:299@10.0.254.125:5060">
     <status status="open"/>
     <msnstatus substatus="online"/>
     </address>
     </atom>
     </presence>

     */
    public String getResourceXMLasString() {
        String res = " <?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<resource-lists xmlns=\"urn:ietf:params:xml:ns:resource-lists\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + " <list>"
                // + "  <entry uri=\"sip:112@173.231.103.38\" />"
                // + "   <entry uri=\"sip:299@173.231.103.38\" />"
                + "  <entry uri=\"sip:140@173.231.103.38\" />"
                //  + "  <entry uri=\"sip:298@173.231.103.38\" />"
                + " </list>"
                + "</resource-lists>";
        return res;
    }
    /*
     * The sip server don't support subscribe to resource list xpidf
     */

    public String getResourceXMLAdhocasString() {
        String res = " <adhoclist uri=\"sip:298@173.231.103.38:5060\""
                + " name=\"sip:298@173.231.103.38\">"
                + "<create>"
                + " <resource uri=\"sip:112@173.231.103.38:5060\"/>"
                + " <resource uri=\"sip:299@173.231.103.38:5060\"/>"
                + "<resource uri=\"sip:125@173.231.103.38:5060\"/>"
                + "<resource uri=\"sip:140@173.231.103.38:5060\"/>"
                + "</create>"
                + "</adhoclist>";
        return res;
    }

    public static String getLocalIp() {
        return null;
    }

    public static String getLocalPort() {
        return null;

    }
    //http://vkslabs.com/digest-authorization-in-sip-with-md5-challenge/
      /*
     *  implement this method , add Proxy-Authorization header
     * UAS: Proxy-Authenticate: Digest realm="TALKSWITCH", nonce="acde00513dadc084f3b39155f9c835f1", opaque="185a87188c63a2c7f69105023454e0b2", stale=TRUE, algorithm=MD5
     * UAC: Proxy-Authorization: Digest username="Alice", realm="atlanta.com", nonce="c60f3082ee1212b402a21831ae", response="245f23415f11432b3434341c022"
     * UAC authorization example: Authorization: Digest realm="TALKSWITCH",nonce="bf3dfc09660fc32dc2b6805cf7d18f04",username="299",uri="sip:173.231.103.38",algorithm=MD5,response="6d86fa024b4fd1e929ee197057376190",opaque="185a87188c63a2c7f69105023454e0b2"
     ah_r=Authorization: Digest realm="TALKSWITCH",nonce="befafce7d66356d1572e506df86c38c2",username="user299",uri="sip:299@173.231.103.38:5060",algorithm=MD5,response="85dc2a9a5a819ca6d6550a17828343d8",opaque="185a87188c63a2c7f69105023454e0b2"
     */

    public AuthorizationHeader makeAuthHeader(HeaderFactory headerFactory, Response response,
            Request request, String username, String password) {
        AuthorizationHeader authoHeader = null;
        /*: high level
         1- get the header "Proxy-Authenticate" value from the response
         2- read the parameters, if STALE is TRUE means the client should not use a new NONCE value
         3- build the Proxy-Authenticate header
         4- add it to the headerFactory
         5- re-send the request
         */
        ProxyAuthenticateHeader proxyAuthenHeader = (ProxyAuthenticateHeader) response.getHeader(ProxyAuthenticateHeader.NAME);
        // WWWAuthenticateHeader proxyAuthenHeader = (WWWAuthenticateHeader) (WWWAuthenticateHeader) response.getHeader(ProxyAuthenticateHeader.NAME);
        //retrieve the param values from the server response
        if (proxyAuthenHeader != null) {
            realm = proxyAuthenHeader.getRealm();
            nonce = proxyAuthenHeader.getNonce();
            opaque = proxyAuthenHeader.getOpaque();
            algo = proxyAuthenHeader.getAlgorithm();
            method = request.getMethod();
            //get the req URI e.g.: sip:299@173.231.103.38:5060
            requestUri = request.getRequestURI();
            //System.out.println("rm=" + realm + "-n=" + nonce + "-o=" + opaque + "-meth=" + method + "-requri=" + requestUri);

            authoHeader = generateProxyAuthorizationHeader(headerFactory, proxyAuthenHeader, nonce, realm, password, algo, method,
                    requestUri, opaque, username);
        } else {
            System.out.println("makeAuthHeader: ProxyAuthenticateHeader is missed from the response.");
        }

        return authoHeader;
    }
    /*
     * * Proxy-Authorization: Digest username="Alice", realm="atlanta.com",

     * nonce="c60f3082ee1212b402a21831ae", response="245f23415f11432b3434341c022"
     */

    public static ProxyAuthorizationHeader generateProxyAuthorizationHeader(HeaderFactory headerFactory, ProxyAuthenticateHeader ah_c, String nonce, String realm,
            String password, String algrm, String request_method, URI request_uri, String opaque, String username) {
        try {
            MessageDigest mdigest = MessageDigest.getInstance(algrm);

            ProxyAuthorizationHeader ah_r = headerFactory.createProxyAuthorizationHeader(ah_c
                    .getScheme());
            //http://tools.ietf.org/html/rfc2617#section-3.2.2.2
            // Approved PATTERN : A1 = unq(username-value) ":" unq(realm-value) ":" passwd
            String a1 = username + ":" + realm + ":" + password;
            String ha1 = toHexString(mdigest.digest(a1.getBytes()));

            //System.out.println(ha1);
            //If the "qop" directive's value is "auth" or is unspecified => A2  = Method ":" digest-uri-value
            String a2 = request_method.toUpperCase() + ":" + request_uri;
            String ha2 = toHexString(mdigest.digest(a2.getBytes()));
            //System.out.println(ha2);

            String finalStr = ha1 + ":" + nonce + ":" + ha2;

            String response = toHexString(mdigest.digest(finalStr.getBytes()));
            //System.out.println(response);

            ah_r.setRealm(realm);
            ah_r.setUsername(username);

            //TOTO: nonce ??
            ah_r.setNonce(nonce);
            ah_r.setURI(request_uri);
            ah_r.setAlgorithm(algrm);
            ah_r.setResponse(response);
            if (opaque != null) {
                ah_r.setOpaque(opaque);
            }

            //ah_r=Authorization: Digest realm="TALKSWITCH",nonce="befafce7d66356d1572e506df86c38c2",username="user299",uri="sip:299@173.231.103.38:5060",algorithm=MD5,response="85dc2a9a5a819ca6d6550a17828343d8",opaque="185a87188c63a2c7f69105023454e0b2"
            //System.out.println("ah_r=" + ah_r.toString());
            return ah_r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //qop is empty or undefined
    public static AuthorizationHeader generateAuthHeader(HeaderFactory headerFactory, WWWAuthenticateHeader ah_c, String nonce, String realm,
            String password, String algrm, String request_method, URI request_uri, String opaque, String username) {
        try {
            System.out.println("generateAuthHeader:case of qop is null|0");
            MessageDigest mdigest = MessageDigest.getInstance(algrm);
            AuthorizationHeader ah_r = headerFactory.createAuthorizationHeader(ah_c
                    .getScheme());

            String a1 = username + ":" + realm + ":" + password;
            String ha1 = toHexString(mdigest.digest(a1.getBytes()));

            System.out.println(ha1);

            String a2 = request_method.toUpperCase() + ":" + request_uri;
            String ha2 = toHexString(mdigest.digest(a2.getBytes()));
            System.out.println(ha2);

            String finalStr = ha1 + ":" + nonce + ha2;

            String response = toHexString(mdigest.digest(finalStr.getBytes()));
            System.out.println(response);

            ah_r.setRealm(realm);
            ah_r.setNonce(nonce);
            ah_r.setUsername(username);
            ah_r.setURI(request_uri);
            ah_r.setAlgorithm(algrm);
            ah_r.setResponse(response);
            ah_r.setAlgorithm(algrm);
            if (opaque != null) {
                ah_r.setOpaque(opaque);
            }
            //ah_r=Authorization: Digest realm="TALKSWITCH",nonce="befafce7d66356d1572e506df86c38c2",username="user299",uri="sip:299@173.231.103.38:5060",algorithm=MD5,response="85dc2a9a5a819ca6d6550a17828343d8",opaque="185a87188c63a2c7f69105023454e0b2"
            System.out.println("ah_r=" + ah_r.toString());
            return ah_r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //qop present
    public static AuthorizationHeader generateAuthHeader(HeaderFactory headerFactory, WWWAuthenticateHeader ah_c, String nonce, String realm,
            String password, String algrm, String request_method, URI request_uri, String cNonce,
            String nonceCount, String qop, String opaque, String username) {
        try {
            MessageDigest mdigest = MessageDigest.getInstance(algrm);
            AuthorizationHeader ah_r = headerFactory.createAuthorizationHeader(ah_c
                    .getScheme());

            String a1 = username + ":" + realm + ":" + password;
            String ha1 = toHexString(mdigest.digest(a1.getBytes()));

            System.out.println(ha1);

            String a2 = request_method.toUpperCase() + ":" + request_uri;
            String ha2 = toHexString(mdigest.digest(a2.getBytes()));
            System.out.println(ha2);

            String finalStr = ha1 + ":" + nonce + ":" + nonceCount + ":" + cNonce + ":" + qop + ":" + ha2;

            String response = toHexString(mdigest.digest(finalStr.getBytes()));
            System.out.println(response);

            ah_r.setRealm(realm);
            ah_r.setNonce(nonce);
            ah_r.setUsername(username);
            ah_r.setURI(request_uri);
            ah_r.setAlgorithm(algrm);
            ah_r.setResponse(response);
            ah_r.setCNonce(cNonce);
            ah_r.setAlgorithm(algrm);
            ah_r.setNonceCount(Integer.parseInt(nonceCount));
            if (opaque != null) {
                ah_r.setOpaque(opaque);
            }
            ah_r.setQop(qop);
            return ah_r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static final char[] toHex = {'0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * From Nist/JAIN examples: convert an array of bytes to an hexadecimal
     * string
     *
     * @return a string (length = 2 * b.length)
     * @param b bytes array to convert to a hexadecimal string
     */
    static String toHexString(byte b[]) {
        int pos = 0;
        char[] c = new char[b.length * 2];
        for (int i = 0; i < b.length; i++) {
            c[pos++] = toHex[(b[i] >> 4) & 0x0F];
            c[pos++] = toHex[b[i] & 0x0f];
        }
        return new String(c);
    }
}
