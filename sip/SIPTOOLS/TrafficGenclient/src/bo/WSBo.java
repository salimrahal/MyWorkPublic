/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import com.safirasoft.CodecVo;
import com.safirasoft.CodecVoList;
import com.safirasoft.IOException_Exception;
import com.safirasoft.ParserConfigurationException_Exception;
import com.safirasoft.PrtStsVo;
import com.safirasoft.PrtStstVoList;
import com.safirasoft.SAXException_Exception;
import java.util.List;

/**
 *
 * @author salim
 */
public class WSBo {

    private static final String PRT_STS_FR = "f";
    private static final String PRT_STS_BU = "b";
    private static String serverip;

    public static String getFrPort() throws ParserConfigurationException_Exception, IOException_Exception, SAXException_Exception {
        String frPrt = null;
        PrtStstVoList pL = getPrtSts();
        serverip = pL.getServerIp();
        List<PrtStsVo> pvL = pL.getPrtstsL();
        for (PrtStsVo p : pvL) {
            if (p.getSts().equalsIgnoreCase(PRT_STS_FR)) {
                frPrt = p.getPrtNum();
            }
        }
        return frPrt;

    }

    public static String getServerip() {
        return serverip;
    }

    public static void setServerip(String serverip) {
        WSBo.serverip = serverip;
    }

    private static PrtStstVoList getPrtSts() throws ParserConfigurationException_Exception, IOException_Exception, SAXException_Exception {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.getPrtSts();
    }

    /*
     getting list of {codec:g711; enable: true|false}
     */
    public static List<CodecVo> getCodecRemoteList() throws IOException_Exception, ParserConfigurationException_Exception, SAXException_Exception {
        CodecVoList codecVoList = getcodecs();
        return codecVoList.getCodecList();

    }

    private static CodecVoList getcodecs() throws IOException_Exception, ParserConfigurationException_Exception, SAXException_Exception {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.getcodecs();
    }

}
