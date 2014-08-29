/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.soap;

import com.safirasoft.IOException_Exception;
import com.safirasoft.ParserConfigurationException_Exception;
import com.safirasoft.PrtStsVo;
import com.safirasoft.PrtStstVoList;
import com.safirasoft.SAXException_Exception;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salim
 */
public class TestClient {

    private static final String PRT_STS_FR = "f";
    private static final String PRT_STS_BU = "b";

    public static void main(String[] args) {
        try {
            System.out.println("ws client call :get free port:"+getFrPort());
        } catch (ParserConfigurationException_Exception ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException_Exception ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException_Exception ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getFrPort() throws ParserConfigurationException_Exception, IOException_Exception, SAXException_Exception {
        String frPrt = null;
        PrtStstVoList pL = getPrtSts();
        List<PrtStsVo> pvL = pL.getPrtstsL();
        for (PrtStsVo p : pvL) {
            if (p.getSts().equalsIgnoreCase(PRT_STS_FR)) {
                frPrt = p.getPrtNum();
            }
        }
        return frPrt;

    }

    private static PrtStstVoList getPrtSts() throws ParserConfigurationException_Exception, IOException_Exception, SAXException_Exception {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.getPrtSts();
    }

}
