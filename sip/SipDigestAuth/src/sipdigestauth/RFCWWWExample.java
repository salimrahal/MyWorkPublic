/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sipdigestauth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author salim
 *
 * http://tools.ietf.org/html/rfc2617#section-3.2.2 Example: The following
 * example assumes that an access-protected document is being requested from the
 * server via a GET request. The URI of the document is
 * "http://www.nowhere.org/dir/index.html". Both client and server know that the
 * username for this document is "Mufasa", and the password is "Circle Of Life"
 * (with one space between each of the three words).
 *
 * The first time the client requests the document, no Authorization header is
 * sent, so the server responds with:
 *
 * HTTP/1.1 401 Unauthorized WWW-Authenticate: Digest
 * realm="testrealm@host.com", qop="auth,auth-int",
 * nonce="dcd98b7102dd2f0e8b11d0f600bfb0c093",
 * opaque="5ccc069c403ebaf9f0171e9517f40e41"
 *
 * The client may prompt the user for the username and password, after which it
 * will respond with a new request, including the following
 *
 * Authorization header: Authorization: Digest username="Mufasa",
 * realm="testrealm@host.com", nonce="dcd98b7102dd2f0e8b11d0f600bfb0c093",
 * uri="/dir/index.html", qop=auth, nc=00000001, cnonce="0a4f113b",
 * response="6629fae49393a05397450978507c4ef1",
 * opaque="5ccc069c403ebaf9f0171e9517f40e41"
 */
public class RFCWWWExample {

    public static void main(String args[]) throws NoSuchAlgorithmException {
        String username = "Mufasa";
        String password = "Circle Of Life";
        String realm = "testrealm@host.com";
        String request_method = "GET";
        String request_uri = "http://www.nowhere.org/dir/index.html";
        String qop = "auth,auth-int";
        String nonce = "dcd98b7102dd2f0e8b11d0f600bfb0c093";
        String opaque = "5ccc069c403ebaf9f0171e9517f40e41";
        String cNonce = "0a4f113b";
        String nonceCount = "00000001";

        MessageDigest mdigest = MessageDigest.getInstance("MD5");

        String a1 = username + ":" + realm + ":" + password;
        System.out.println("a1="+mdigest.digest(a1.getBytes()));//a1 = [B@4c4a0d
        String ha1 = toHexString(mdigest.digest(a1.getBytes()));

        System.out.println("ha1="+ha1);//ha1 = 939e7578ed9e3c518a452acee763bce9

        String a2 = request_method.toUpperCase() + ":" + request_uri;
        String ha2 = toHexString(mdigest.digest(a2.getBytes()));
        System.out.println(ha2);

        String finalStr = ha1 + ":" + nonce + ":" + nonceCount + ":" + cNonce + ":" + qop + ":" + ha2;

        // String finalStr = ha1 + ":" + nonce + ha2;
        String response = toHexString(mdigest.digest(finalStr.getBytes()));
        System.out.println("response=" + response);
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
