/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bo.ClTcp;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salim
 */
public class Cc {

    ClTcp cltcp;

    public Cc() {
       cltcp = new ClTcp();
    }

    public void sendParamToServer(String port, String codec, String timelength, String custname, String svip, String tstid) throws IOException {
        try {
            cltcp.sendParamToServer(port, codec, timelength, custname, svip, tstid);
        } catch (Exception ex) {
            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
