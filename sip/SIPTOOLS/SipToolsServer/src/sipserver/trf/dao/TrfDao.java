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
import java.text.SimpleDateFormat;
import java.util.Date;
import sipserver.trf.TrfBo;
import sipserver.trf.bean.Param;
import sipserver.trf.vp.vo.JtrVo;
import sipserver.trf.vp.vo.LatVo;
import vo.ResVo;

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
        String query = "update Ports set status = \"";
        query += sts + "\" where (";
        boolean first = true;
        for (int i = 0; i < portNumArr.length; i++) {
            if (first) {
                query += "portNum =" + portNumArr[i];
                first = false;
            }
            else{
                query += " or  portNum =" + portNumArr[i];
            }
        }
        query+=")";
        //System.out.println("TrfDao:updatePortStatus query="+query);
        try (Connection connect = getC(x)) {

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
             if (i >= 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: updatePortStatus:" + query + " returns zero, no row updated!");
            }
            System.out.println("updatePortStatus:update return=" + i);
        }
        return res;
    }

    public synchronized boolean updateALLPortStatus(String sts) throws Exception {
        boolean res = false;
        String x1 = "traffic";
        String x = gety(x1);
        PreparedStatement preparedStatement = null;
        String query = "update Ports set status = \"" + sts + "\"";
        try (Connection connect = getC(x)) {

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement(query);
            //i is the number of row updated, if 2 row is updated then returns: 2
            int i = preparedStatement.executeUpdate();
             if (i >= 1) {
                res = true;
            } else {
                res = false;
                System.out.println("Error: updatePortStatus:" + query + " returns zero, no row updated!");
            }
            System.out.println("updatePortStatus:update return=" + i);
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
            if (i >= 1) {
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

    /*
     unsused in the server
     for test only
     */
    public ResVo getRes(String testId) throws Exception {
        String x1 = "traffic";
        String x = gety(x1);
        PreparedStatement preparedStatement = null;
        ResVo res = null;
        ResultSet rs;
        String query = "select customerName, publicIp, codec, testLength, startTime, endTime, "
                + "uploadPacketLost, uploadLatencyPeak, uploadLatencyAvg, uploadJitterPeak, uploadJitterAvg, "
                + " downloadPacketLost, downloadLatencyPeak, downloadLatencyAvg, downloadJitterPeak, downloadJitterAvg "
                + " from test_result"
                + " where uid = \"" + testId + "\"";
        try (Connection connect = getC(x)) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            rs = preparedStatement.executeQuery();
            if (rs.first()) {
                res = new ResVo(rs.getString("customerName"), rs.getString("publicIp"), rs.getString("codec"), rs.getInt("testLength"));
                res.setsDate(new java.util.Date(rs.getTimestamp("startTime").getTime()));//Mon Sep 29 20:49:41 EEST 2014
                res.seteDate(new java.util.Date(rs.getTimestamp("endTime").getTime()));//Mon Sep 29 20:49:41 EEST 2014
                res.setUppkloss(rs.getFloat("uploadPacketLost"));
                res.setUplatpeak(rs.getInt("uploadLatencyPeak"));
                res.setUplatav(rs.getInt("uploadLatencyAvg"));
                res.setUpjtpeak(rs.getInt("uploadJitterPeak"));
                res.setUpjtav(rs.getInt("uploadJitterAvg"));
                res.setDopkloss(rs.getFloat("downloadPacketLost"));
                res.setDolatpeak(rs.getInt("downloadLatencyPeak"));
                res.setDolatav(rs.getInt("downloadLatencyAvg"));
                res.setDojtpeak(rs.getInt("downloadJitterPeak"));
                res.setDojtav(rs.getInt("downloadJitterAvg"));
            } else {
                System.out.println("Error: getRes:: Query:" + query + ", test_id has no record!");
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        int[] ports = new int[2];
        ports[0] = 5095;
        ports[1] = 5096;
        TrfDao dao = new TrfDao();
        //dao.updatePortStatus(ports, "b");
       // System.out.println(dao.getRes("7034cc4f-0f59-471b-a198-f803fe7bf062"));
        //ResVo{cnme=custnamefromAppletclass, puip=93.185.225.150, cdc=SILK, tlth=120,
        //stime=Thu Jan 01 20:49:41 EET 1970, sDate=Mon Sep 29 00:00:00 EEST 2014, etime=2014-09-29, eDate=null, uppkloss=54.38, uplatpeak=921, uplatav=894, 
        //upjtpeak=643, upjtav=25, dopkloss=81.28, dolatpeak=403, dolatav=272, dojtpeak=30, dojtav=9}
        dao.updatePortStatus(new int[]{5098,5098},"f");
    }

}
