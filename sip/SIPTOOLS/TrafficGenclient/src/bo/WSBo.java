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
import com.safirasoft.PrtMiscVo;
import com.safirasoft.PrtStsVo;
import com.safirasoft.PrtStstVoList;
import com.safirasoft.SAXException_Exception;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author salim
 */
public class WSBo {

    private static final String PRT_STS_FR = "f";
    private static final String PRT_STS_BU = "b";



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

    public static PrtMiscVo getMiscPorts() throws SAXException_Exception, IOException_Exception, ParserConfigurationException_Exception {
        com.safirasoft.Pivot pivotPort = null;
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        pivotPort = service.getPivotPort();
        return pivotPort.getMiscPorts();
    }
}
