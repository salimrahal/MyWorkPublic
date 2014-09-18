/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import vo.PrtMiscVo;

/**
 *
 * @author salim
 */
public class PrtDao {

    private String y = "user";
    private String k1 = "*";

    public PrtMiscVo retrievePorts(String status) throws NamingException, SQLException {
        String x1 = "traffic";
        String x = gety(x1);
        String k = getz(x, k1);
        PrtMiscVo prtvo = new PrtMiscVo();
        String[] portArr ;
        String res = null;
        try (Connection conn = getUdptrafficDBRef().getConnection(x, k)) {
            String query = "select group_concat(portNum) as ports "
                    + "from (select * from Ports where status = '" + status + "' limit 3) as portsTable";// 
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                res = rs.getString("ports");//res = 5108,5095,5096
            }
            if (res != null) {
                portArr = res.split(",");
                if (portArr.length == 3) {
                    prtvo.setPrtTrfNumUp(portArr[0]);
                    prtvo.setPrtTrfNumDown(portArr[1]);
                    prtvo.setPrtLatNum(portArr[2]);
                } else {
                    //return an empty results because 3 ports are mandatories
                    System.out.println("retrievePorts: insufficient free ports");
                }
            }
        }
        return prtvo;
    }

    private DataSource getUdptrafficDBRef() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/udptrafficDBRef");
    }

    public String gety(String f1) {
        String sb = f1 + y;
        return sb;
    }

    public String getz(String k, String l) {
        String sb = k + l;
        return sb;
    }

    public static void main(String[] args) {
        String x = "5108,5095,5096";
        String[] arr = x.split(",");
        System.out.println("arr=" + arr.length + "/" + arr);
    }
}
