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

/**
 *
 * @author salim
 */
public class AlgDao {
    String x1 = "traffic";
    /*
    -------------------+-------------+------+-----+---------+----------------+
| Field             | Type        | Null | Key | Default | Extra          |
+-------------------+-------------+------+-----+---------+----------------+
| idtest_result_alg | int(11)     | NO   | PRI | NULL    | auto_increment |
| uid               | varchar(36) | YES  | UNI | NULL    |                |
| customerName      | varchar(45) | YES  |     | NULL    |                |
| publicIp          | varchar(16) | YES  |     | NULL    |                |
| transport         | varchar(3)  | YES  |     | NULL    |                |
| portsrc           | int(11)     | YES  |     | NULL    |                |
| portdest          | int(11)     | YES  |     | NULL    |                |
| startTime         | datetime    | YES  |     | NULL    |                |
| endTime           | datetime    | YES  |     | NULL    |                |
| isAlgDetected     | tinyint(1)  | YES  |     | NULL    |
    */
     public boolean insertAlGPart1(String testId, float packetLossDown) throws Exception {
        String x = gety(x1);
        String k = getz(x, StaticVar.k1);
        boolean res;
        PreparedStatement preparedStatement = null;
        String query = "insert into test_result_alg(uid,customerName,publicIp,transport,portsrc,portdest,startTime)";
        query+="values()";
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
}
