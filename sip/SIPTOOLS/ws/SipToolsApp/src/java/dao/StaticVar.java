/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author salim
 */
public class StaticVar {

    private static String y = "user";
    protected static String k1 = "*";
    public static final int TEST_ID_SIZE = 36;//see generateUUID() function in client side: UUID.randomUUID();

    protected static DataSource getUdptrafficDBRef() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/udptrafficDBRef");
    }

    public static String gety(String f1) {
        String sb = f1 + y;
        return sb;
    }

    public static String getz(String k, String l) {
        String sb = k + l;
        return sb;
    }
}
