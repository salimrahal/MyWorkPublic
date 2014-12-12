/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.ResAlgVo;
import bean.ResTrfVo;
import bo.SipToolsBO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author salim
 */
public class ResDao {

    /*retrieveTrfResList
     uploadPacketLost, downloadPacketLost : as in %
     others in ms
     get all results as list, except the start / end time equal to null
     */
    public List<ResTrfVo> retrieveTrfResList() throws Exception {
        PreparedStatement preparedStatement = null;
        List resList = new ArrayList();
        ResTrfVo res = null;
        ResultSet rs;
        Date sDate;
        Date eDate;
        String query = "select customerName, publicIp, codec, testLength, startTime, endTime, "
                + "uploadPacketLost, uploadLatencyPeak, uploadLatencyAvg, uploadJitterPeak, uploadJitterAvg, "
                + " downloadPacketLost, downloadLatencyPeak, downloadLatencyAvg, downloadJitterPeak, downloadJitterAvg "
                + " from test_result "
                + " where startTime is not null and endTime is not null"
                + " order by startTime Desc";
        try (Connection connect = Conn.gettrf().getConnection()) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                sDate = new java.util.Date(rs.getTimestamp("startTime").getTime());
                eDate = new java.util.Date(rs.getTimestamp("endTime").getTime());
                res = new ResTrfVo(rs.getString("customerName"), rs.getString("publicIp"), rs.getString("codec"), rs.getInt("testLength"));
                res.setsDate(sDate);//Mon Sep 29 20:49:41 EEST 2014
                res.seteDate(eDate);//Mon Sep 29 20:49:41 EEST 2014
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
                res.setsDateview(SipToolsBO.formatDate(sDate));
                res.seteDateView(SipToolsBO.formatDate(eDate));
                resList.add(res);
            }
        }
        return resList;
    }
    /*
     get all results as list, except the start / end time equal to null
     */

    public List<ResAlgVo> retrieveAlgResList() throws Exception {
        PreparedStatement preparedStatement = null;
        List resList = new ArrayList();
        ResAlgVo res = null;
        Date sDate;
        Date eDate;
        ResultSet rs;
        String query = "select customerName, publicIp, startTime, endTime, transport "
                + ",portsrc, portdest, isAlgDetected, isFirewallDetected"
                + " from test_result_alg"
                + " where startTime is not null and endTime is not null"
                + " order by startTime Desc";
        try (Connection connect = Conn.gettrf().getConnection()) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                sDate = new java.util.Date(rs.getTimestamp("startTime").getTime());
                eDate = new java.util.Date(rs.getTimestamp("endTime").getTime());
                res = new ResAlgVo(rs.getString("customerName"), rs.getString("publicIp"),
                        sDate, eDate, rs.getString("transport"), rs.getInt("portsrc"), rs.getInt("portdest"),
                        rs.getBoolean("isAlgDetected"), rs.getBoolean("isFirewallDetected"));
                res.setsDateview(SipToolsBO.formatDate(sDate));
                res.seteDateView(SipToolsBO.formatDate(eDate));
                resList.add(res);
            }
        }
        return resList;
    }
}
