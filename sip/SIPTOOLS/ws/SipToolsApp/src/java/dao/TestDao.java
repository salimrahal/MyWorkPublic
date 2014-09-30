/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.StaticVar.getUdptrafficDBRef;
import static dao.StaticVar.gety;
import static dao.StaticVar.getz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import vo.JtrVo;
import vo.LatVo;
import vo.ResVo;

/**
 *
 * @author salim
 */
public class TestDao {

    String x1 = "traffic";

    public boolean updateTestPacketLostDown(String testId, float packetLossDown) throws Exception {
        String x = gety(x1);
        String k = getz(x, StaticVar.k1);
        boolean res;
        PreparedStatement preparedStatement = null;
        String query = "update test_result set downloadPacketLost = " + packetLossDown + " where uid = \"" + testId + "\"";
        try (Connection conn = getUdptrafficDBRef().getConnection(x, k)) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = conn.prepareStatement(query);

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
     */

    public boolean updateLatJitDown(String testId, LatVo latDown, JtrVo jitterDown) throws Exception {
        String x = gety(x1);
        String k = getz(x, StaticVar.k1);
        boolean res = false;
        PreparedStatement preparedStatement = null;
        String query = "update test_result set downloadLatencyPeak = " + latDown.getPeak() + ",downloadLatencyAvg = " + latDown.getAvg()
                + ", downloadJitterPeak = " + jitterDown.getPeak() + ", downloadJitterAvg = " + jitterDown.getAvg()
                + " where uid = \"" + testId + "\"";
        try (Connection conn = getUdptrafficDBRef().getConnection(x, k)) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = conn.prepareStatement(query);

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
//http://stackoverflow.com/questions/7875196/mysql-datetime-not-returning-time
    public ResVo getRes(String testId) throws Exception {
        String x = gety(x1);
        String k = getz(x, StaticVar.k1);
        ResVo res = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs;
        String query = "select customerName, publicIp, codec, testLength, startTime, endTime, "
                + "uploadPacketLost, uploadLatencyPeak, uploadLatencyAvg, uploadJitterPeak, uploadJitterAvg, "
                + " downloadPacketLost, downloadLatencyPeak, downloadLatencyAvg, downloadJitterPeak, downloadJitterAvg "
                + " from test_result"
                + " where uid = \"" + testId + "\"";
        try (Connection conn = getUdptrafficDBRef().getConnection(x, k)) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = conn.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            rs = preparedStatement.executeQuery();
            if (rs.first()) {
                res = new ResVo(rs.getString("customerName"), rs.getString("publicIp"), rs.getString("codec"), rs.getInt("testLength"));
                res.setStime(new java.util.Date(rs.getTime("startTime").getTime()));
                res.setEtime(rs.getDate("endTime"));
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
            }else{
                   System.out.println("Error: getRes:: Query:" + query + ", test_id has no record!");
            }
        }
        return res;
    }
}
