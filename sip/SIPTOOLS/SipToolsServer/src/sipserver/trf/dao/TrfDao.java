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

    public synchronized Connection getConnection(String x) throws ClassNotFoundException, SQLException {
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
        String x = "trafficuser";
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connect = null;
        connect = getConnection(x);
        StringBuilder sb = new StringBuilder("update Ports set status = \"");
        sb.append(sts).append("\"");
        sb.append("where portNum =").append(portNumArr[0]).append(" or portNum = ").append(portNumArr[1]);
        String query = sb.toString();

        try {

            // statements allow to issue SQL queries to the database
            statement = connect.createStatement();

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement(query);
           
            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if(i==2){
                res = true;
            }else{
                res = false;
                System.out.println("Error: updatePortStatus:"+query+" returns zero, no row updated!");
            }
            System.out.println("update return="+i);
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }

            if (connect != null) {
                connect.close();
            }
        }
        return res;
    }

    public synchronized void updateOnePortStatus(int portNum, String sts) throws Exception {
        String x = "trafficuser";
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connect = null;
        connect = getConnection(x);

        try {

            // statements allow to issue SQL queries to the database
            statement = connect.createStatement();

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("update Ports set status = ? where portNum = ?");
            // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
            // parameters start with 1
            preparedStatement.setString(1, sts);
            preparedStatement.setInt(2, portNum);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }

            if (connect != null) {
                connect.close();
            }
        }
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
