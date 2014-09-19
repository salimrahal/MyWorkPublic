/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sipserver.trf.TrfBo;
import sipserver.trf.bean.Param;

/**
 *
 * @author salim
 */
public class TrfDao {

    private String y  = "user";

    public synchronized Connection getC(String x) throws ClassNotFoundException, SQLException {
        Connection connect = null;
        // this will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
        String sb = "jdbc:mysql://localhost/" + TrfBo.A + "?" + TrfBo.B + "=" + x + "&" + TrfBo.C + "=" + x + "*";

        // setup the connection with the DB.
        connect = DriverManager
                .getConnection(sb);
        return connect;
    }

    /*
     this method should be access by only one thread
     */
    public static synchronized void saveClientInfoToDB(Param param) {

    }

    /* it updates only two ports 
     portNumArr[0]: traffic
     portNumArr[1]: lat
     change the port status to busy after the server receives client request
     */
    public synchronized boolean updatePortStatus(int[] portNumArr, String sts) throws Exception {
        boolean res = false;
        String x1 = "traffic";
        String x = gety(x1);
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
//        Connection connect = null;
//        connect = getC(x);
        StringBuilder sb = new StringBuilder("update Ports set status = \"");
        sb.append(sts).append("\"");
        sb.append("where portNum =").append(portNumArr[0]).append(" or portNum = ").append(portNumArr[1]).
                append(" or portNum = ").append(portNumArr[2]);
        String query = sb.toString();

       try(Connection connect = getC(x)){
           
            // statements allow to issue SQL queries to the database
            statement = connect.createStatement();

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if (i == 3) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: updatePortStatus:" + query + " returns zero, no row updated!");
            }
            System.out.println("update return=" + i);
       }
        return res;
    }

    public String gety(String f1){
        String sb = f1 + y;
        return sb;
    }
    public synchronized boolean updateOnePortStatus(int portNum, String sts) throws Exception {
         String x1 = "traffic";
        String x = gety(x1);
        boolean res = false;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
//        Connection connect = null;
//        connect = getC(x);
        String query = "update Ports set status = '"+sts+"' where portNum ="+portNum;
         try(Connection connect = getC(x)){

            // statements allow to issue SQL queries to the database
            statement = connect.createStatement();

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);
           
           //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if (i == 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: updatePortStatus:" + query + " returns zero, no row updated!");
            }
        } 
        return res;
    }
    /*
     it insert the log record for a specific client
     */

    public static synchronized void insertTest() {

    }

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        int[] ports = new int[2];
        ports[0] = 5095;
        ports[1] = 5096;
        TrfDao dao = new TrfDao();
        dao.updatePortStatus(ports, "b");
    }
}
