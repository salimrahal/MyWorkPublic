/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EtdUserPssdDP;

import etdUserPssdBP.EtdUP_cl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author salim
 */
public class EtdUP {

    public EtdUP() {
    }
    
    public static EtdUP_cl AvoirEtdPssd(String username ){
         String query="  ";
         EtdUP_cl e = null;
         Connection connection = null;
    if(username !=null){

            try {
                // query+="select username,password,nom,prenom from Etudiant,Users where username=\""+username+"\"";
                  //  query+=" and Nodossier=username;" ;
                   query+="select username,password,nom,prenom from Etudiant,Users where Users.no=Etudiant.NoEtud ";
                   query+=" and Nodossier=\""+username+"\";" ;

                connection = getJdbcDb_isae().getConnection();
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                if(rs.first()) {
                 e=new EtdUP_cl(rs.getString("username"),rs.getString("password"),rs.getString("nom"),rs.getString("prenom"));
                }
                else{
                   e=new EtdUP_cl("n'existe pas ","n'existe pas "," n'existe pas"," n'existe pas");
                }
            }
catch (SQLException ex) {
                Logger.getLogger(EtdUP.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (NamingException ex) {
                Logger.getLogger(EtdUP.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    return e;
  }

    private static DataSource getJdbcDb_isae() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/jdbc/db_isae");
    }
    }
