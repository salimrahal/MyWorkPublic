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
    
    public PrtMiscVo retrievePorts(String status) throws NamingException, SQLException{ 
        PrtMiscVo prtvo = new PrtMiscVo();
        Connection conn = getUdptrafficDBRef().getConnection("root", "root");
        String query = "Select portNum from Ports where status = '"+status+"' limit 2";
        PreparedStatement pr = conn.prepareStatement(query);
        ResultSet rs = pr.executeQuery();
        
        boolean first = true;
        
        while(rs.next()){
            if(first){
                prtvo.setPrtTrfNum(rs.getString(1));
            first = false;
            }else{
                prtvo.setPrtLatNum(rs.getString(1)); 
            }      
        }
        return prtvo;
    }

    private DataSource getUdptrafficDBRef() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/udptrafficDBRef");
    }
}
