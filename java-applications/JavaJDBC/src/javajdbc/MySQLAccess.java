/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javajdbc;

/**
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * @author salim
 */
import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;

  public void readDataBase() throws Exception {
    try {
      // this will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // setup the connection with the DB.
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/udptrafficDB?"
              + "user=trafficuser&password=trafficuser*");

      // statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // resultSet gets the result of the SQL query
      resultSet = statement
          .executeQuery("select * from Ports");
      writeResultSet(resultSet);

      // preparedStatements can use variables and are more efficient
      preparedStatement = connect
          .prepareStatement("update Ports set status = ? where portNum = ?");
      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
      // parameters start with 1
      preparedStatement.setString(1, "f");
      preparedStatement.setInt(2, 5096);
      preparedStatement.executeUpdate();
/*
      preparedStatement = connect
          .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
      resultSet = preparedStatement.executeQuery();
      writeResultSet(resultSet);

      */
      
      // remove again the insert comment
//      preparedStatement = connect
//      .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
//      preparedStatement.setString(1, "Test");
//      preparedStatement.executeUpdate();
//      
      resultSet = statement
          .executeQuery("select * from Ports");
      writeResultSet(resultSet);

    } catch (Exception e) {
      throw e;
    } finally {
     if(statement!=null){
          statement.close();
      }
      if(resultSet !=null){
          resultSet.close();
      }
      
       if(connect!=null){
        connect.close();
      }
    }

  }

  private void writeMetaData(ResultSet resultSet) throws SQLException {
    // now get some metadata from the database
    System.out.println("The columns in the table are: ");
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // resultSet is initialised before the first data set
    while (resultSet.next()) {
      // it is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g., resultSet.getSTring(2);
      int portnum = resultSet.getInt("portNum");
      String status = resultSet.getString("status");
      
      
      System.out.println("portnum: " + portnum);
      System.out.println("status: " + status);
      }
  }
} 
