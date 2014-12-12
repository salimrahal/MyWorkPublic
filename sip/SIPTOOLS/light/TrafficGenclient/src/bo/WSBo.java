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
import com.safirasoft.SAXException_Exception;
import java.util.ArrayList;
import java.util.List;

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

    public static List<CodecVo> returnFilterdList(List<CodecVo> lparam, boolean enabled) {
        List<CodecVo> l = new ArrayList<>();
        for (CodecVo c : lparam) {
            if (c.isEnabled()) {
                l.add(c);
            }
        }
        return l;
    }

    private static CodecVoList getcodecs() throws IOException_Exception, ParserConfigurationException_Exception, SAXException_Exception {
        CodecVoList codlist = null;
        try {
            com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
            com.safirasoft.Pivot port = service.getPivotPort();
            codlist = port.getcodecs();

        } catch (IOException_Exception | ParserConfigurationException_Exception | SAXException_Exception iOException_Exception) {
            System.out.println("wsbo:Exception:getcodecs" + iOException_Exception.getMessage());
        }
        return codlist;
    }

    public static PrtMiscVo getMiscPorts() throws SAXException_Exception, IOException_Exception, ParserConfigurationException_Exception {
        PrtMiscVo portVo = null;
        try {
            com.safirasoft.Pivot pivotPort = null;
            com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
            pivotPort = service.getPivotPort();
            portVo = pivotPort.getMiscPorts();
        } catch (IOException_Exception | ParserConfigurationException_Exception | SAXException_Exception iOException_Exception) {
            System.out.println("wsbo:Exception:getMiscPorts" + iOException_Exception.getMessage());
        }
        return portVo;
    }

    protected static String savePLD(java.lang.String tid, float pld) {
        String message = "";
        try {
            com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
            com.safirasoft.Pivot port = service.getPivotPort();
            int res = port.savePLD(tid, pld);
            if (res != -1) {
                message = TrfBo.SUCCESS_KEY;
            } else {
                message = TrfBo.FAIL_KEY;
            }
        } catch (Exception e) {
            System.out.println("wsbo:Exception:getMiscPorts" + e.getMessage());
            message = e.getMessage();
        }
        return message;
    }

    protected static Integer svLJD(java.lang.String ti, int latdwnpk, int latdwnav, int jitdwpk, int jitdwav) {
        int res = 0;
        try {
            com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
            com.safirasoft.Pivot port = service.getPivotPort();
            res = port.svLJD(ti, latdwnpk, latdwnav, jitdwpk, jitdwav);
        } catch (Exception e) {
            System.out.println("wsbo:Exception:svLJD" + e.getMessage());
        }
        return res;
    }
}
