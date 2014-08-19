/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vo;

import java.util.List;

/**
 *
 * @author salim
 */
public class PrtStstVoList {
    List<PrtStsVo> prtstsL;
    String serverIp;
    
    public PrtStstVoList(List<PrtStsVo> prtstsL) {
        this.prtstsL = prtstsL;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    
    public List<PrtStsVo> getPrtstsL() {
        return prtstsL;
    }

    public void setPrtstsL(List<PrtStsVo> prtstsL) {
        this.prtstsL = prtstsL;
    }
    
    
}
