/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import com.safirasoft.IOException_Exception;
import com.safirasoft.ParserConfigurationException_Exception;
import com.safirasoft.PrtMiscVo;
import com.safirasoft.ResVo;
import com.safirasoft.SAXException_Exception;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salim
 */
public class WsBoRes {

    public synchronized static ResVo getRs(String ti) {
        ResVo res = null;
        System.out.println("WsBoRes calling ws.."+ti);
        res = getrs(ti);
        System.out.println("ResVo="+res.toString());
        return res;
    }

    private static ResVo getrs(java.lang.String ti) {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        System.out.println("port.getrs(ti)="+port.getrs(ti));
        return port.getrs(ti);
    }
    
    public static void main(String[] args){
            getRs("0681d609-fd24-4f4b-b4e3-38079869afcc");          
    }
}
