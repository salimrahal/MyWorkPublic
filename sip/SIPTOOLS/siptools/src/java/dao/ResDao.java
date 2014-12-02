/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.ResVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salim
 */
public class ResDao {

    /*
     get all results as list, except the start / end time equal to null
     */
    public List<ResVo> retrieveResList() throws Exception {
        PreparedStatement preparedStatement = null;
        List resList = new ArrayList();
        ResVo res = null;
        ResultSet rs;
        String query = "select customerName, publicIp, codec, testLength, startTime, endTime, "
                + "uploadPacketLost, uploadLatencyPeak, uploadLatencyAvg, uploadJitterPeak, uploadJitterAvg, "
                + " downloadPacketLost, downloadLatencyPeak, downloadLatencyAvg, downloadJitterPeak, downloadJitterAvg "
                + " from test_result";
        try (Connection connect = Conn.gettrf().getConnection()) {
            // preparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement(query);

            //i is the number of row updated, if 2 row is updated then returns: 2
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                res = new ResVo(rs.getString("customerName"), rs.getString("publicIp"), rs.getString("codec"), rs.getInt("testLength"));
                if (rs.getTimestamp("startTime") != null) {
                    res.setsDate(new java.util.Date(rs.getTimestamp("startTime").getTime()));//Mon Sep 29 20:49:41 EEST 2014
                }
                if (rs.getTimestamp("endTime") != null) {
                    res.seteDate(new java.util.Date(rs.getTimestamp("endTime").getTime()));//Mon Sep 29 20:49:41 EEST 2014
                }
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
                resList.add(res);
            }
        }
        return resList;
    }
}
