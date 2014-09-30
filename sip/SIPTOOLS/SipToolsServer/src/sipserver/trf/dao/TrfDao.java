/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import sipserver.trf.TrfBo;
import sipserver.trf.bean.Param;
import sipserver.trf.vp.vo.JtrVo;
import sipserver.trf.vp.vo.LatVo;

/**
 *
 * @author salim
 */
public class TrfDao {

    private String y = "user";

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

    /* it updates only two ports 
     portNumArr[0]: traffic
     portNumArr[1]: lat
     change the port status to busy after the server receives client request
     */
    public synchronized boolean updatePortStatus(int[] portNumArr, String sts) throws Exception {
        boolean res = false;
        String x1 = "traffic";
        String x = gety(x1);
        PreparedStatement preparedStatement = null;
        StringBuilder sb = new StringBuilder("update Ports set status = \"");
        sb.append(sts).append("\"");
        sb.append("where portNum =").append(portNumArr[0]).append(" or portNum = ").append(portNumArr[1]).
                append(" or portNum = ").append(portNumArr[2]);
        String query = sb.toString();

        try (Connection connect = getC(x)) {

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

    public String gety(String f1) {
        String sb = f1 + y;
        return sb;
    }

    public synchronized boolean updateOnePortStatus(int portNum, String sts) throws Exception {
        String x1 = "traffic";
        String x = gety(x1);
        boolean res = false;
        PreparedStatement preparedStatement = null;
//        Connection connect = null;
//        connect = getC(x);
        String query = "update Ports set status = '" + sts + "' where portNum =" + portNum;
        try (Connection connect = getC(x)) {

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if (i == 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: Query:" + query + " returns zero, no row updated!");
            }
        }
        return res;
    }

    public synchronized boolean createNewTest(String testId, String clientname, String clientIp, String codec, String testlength) throws Exception {
        String x1 = "traffic";
        String x = gety(x1);
        boolean res = false;
        PreparedStatement preparedStatement = null;
        String query = "insert into test_result(uid, customerName, publicIp, codec, testLength, startTime)"
                + " values(\"" + testId + "\",\"" + clientname + "\",\"" + clientIp + "\",\"" + codec + "\",\"" + testlength + "\", NOW())";
        try (Connection connect = getC(x)) {

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if (i == 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: Query:" + query + " returns zero, no row added!");
            }
        }
        return res;
    }
    /*
     it insert the log record for a specific client
     */

    public synchronized boolean updateTestPacketLostUp(String testId, float packetLossUp) throws Exception {
        String x1 = "traffic";
        String x = gety(x1);
        boolean res = false;
        PreparedStatement preparedStatement = null;
        String query = "update test_result set uploadPacketLost = " + packetLossUp + " where uid = \"" + testId + "\"";
        try (Connection connect = getC(x)) {

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if (i == 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: Query:" + query + " returns zero, no row added!");
            }
        }
        return res;
    }

    public synchronized boolean updateTestPacketLostDown(String testId, float packetLossDown) throws Exception {
        String x1 = "traffic";
        String x = gety(x1);
        boolean res = false;
        PreparedStatement preparedStatement = null;
        String query = "update test_result set downloadPacketLost = " + packetLossDown + " where uid = \"" + testId + "\"";
        try (Connection connect = getC(x)) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if (i == 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: Query:" + query + " returns zero, no row added!");
            }
        }
        return res;
    }

    public synchronized boolean updateLatJitUp(String testId, LatVo latUp, JtrVo jitterUp) throws Exception {
        String x1 = "traffic";
        String x = gety(x1);
        boolean res = false;
        PreparedStatement preparedStatement = null;
        //  preparedStatement.setDate(4, new java.sql.Date(fiche.getDateValidationFiche().getTime()));
        String query = "update test_result set uploadLatencyPeak = " + latUp.getPeak() + ",uploadLatencyAvg = " + latUp.getAvg()
                + ", uploadJitterPeak = " + jitterUp.getPeak() + ", uploadJitterAvg = " + jitterUp.getAvg()
                + " where uid = \"" + testId + "\"";
        try (Connection connect = getC(x)) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if (i == 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: Query:" + query + " returns zero, no row added!");
            }
        }
        return res;
    }
 public synchronized boolean updateTestEndTime(String testId) throws Exception {
        String x1 = "traffic";
        String x = gety(x1);
        boolean res = false;
        PreparedStatement preparedStatement = null;
        String query = "update test_result set endTime = NOW() where uid = \"" + testId + "\"";
        try (Connection connect = getC(x)) {

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
            if (i == 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: Query:" + query + " returns zero, no row added!");
            }
        }
        return res;
    }
    public static void main(String[] args) throws Exception {
        int[] ports = new int[2];
        ports[0] = 5095;
        ports[1] = 5096;
        TrfDao dao = new TrfDao();
        dao.updatePortStatus(ports, "b");
    }
}
