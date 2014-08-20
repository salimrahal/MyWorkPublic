/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sipserver.trf;

import org.omg.CORBA.TRANSACTION_MODE;
import sipserver.trf.bean.Param;

/**
 *
 * @author salim
 *
 */
public class TrfBo {
    String initialLoc ;
    /*
      a- it splits the params
      b- save them to param singleton bean
    */
    public Param savingParamsTobean(String paramquery){
       Param param = new Param();
       //tstid=66d2b2fc-6f54-49cf-a495-e158ebb814c7;codec=g711;timelength=15;custname=custnamefromAppletclass
       String[] paramsArr = paramquery.split(";");
       String[] keyval;
       for(String s: paramsArr){
           keyval = s.split("=");
           switch(keyval[0]){
               case "tstid":
                   param.setTstid(keyval[1]);
                   break;
               case "codec":
                   param.setCodec(keyval[1]);
                   break;
               case "timelength":
                   param.setTimelength(keyval[1]);
                   break;
               case "custname":
                   param.setCustname(keyval[1]);
                   break;   
                //todo add the client public Ip to the bean
           }
       }//end for
        System.out.println(param.toString());
        return param;
    }
    
    /*
    this method should be access by only one thread
    */
    public static synchronized void saveClientInfoToXmlLog(Param param, String clientIp){
       //retreive the location of log file
        String confloc = getConfLoc();
       //create it if not exists
        //save the client info: create xml nodes under the root: 
    }
    
    /*
    change the port status to busy after the server receives client request
    */
    public static synchronized void changeConfigPortStatus(String sts){
        
    }
    /*
    it insert the log record for a specific client
    */
    public static synchronized void insertLogXmlNode(){
        
    }
    
    public static void main(String[] args){
       TrfBo tb = new TrfBo();
        tb.savingParamsTobean("tstid=66d2b2fc-6f54-49cf-a495-e158ebb814c7;codec=g711;timelength=15;custname=custnamefromAppletclass");
    }

    private static String getConfLoc() {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.getConfLoc();
    }
    
    public int computeLatency(){
        return 0;
        
    }
}
