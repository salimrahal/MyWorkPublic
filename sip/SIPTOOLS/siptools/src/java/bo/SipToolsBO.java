/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import bean.ResTrfVo;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author salim
 */
public class SipToolsBO {

    public static final String FINAL_RESULT_PASSED = "Passed";
    public static final String FINAL_RESULT_FAILED = "Failed";
    public static final String REASON_FAILED_FW = "Firewall detected";
    public static final String REASON_FAILED_ALG = "ALG detected";
    public static final String REASON_PASSED_ALG = "No ALG detected";
    public static final String REASON_FAILED_TRF_TIMEOUT = "Time out";
    public static final String REASON_PASSED_TRF = "No Firewall";

    public static final int E_VAL = -1;

    public static String formatDate(Date date) {
        String formattedDate = null;
        if (date != null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MMM-dd-HH:mm:ss");//yyyy-MM-dd HH:mm:ss
            formattedDate = sf.format(date);
        }
        return formattedDate;
    }

    //any -1 value indicate the presence of a time out, the app interprete it as firewall
    public static boolean isES(ResTrfVo resvo) {
        boolean res = false;//
        if (resvo.getDopkloss() == E_VAL || resvo.getDolatpeak() == E_VAL || resvo.getDolatav() == E_VAL || resvo.getDojtav() == E_VAL || resvo.getDojtpeak() == E_VAL
                || resvo.getUppkloss() == E_VAL || resvo.getUplatpeak() == E_VAL || resvo.getUplatav() == E_VAL || resvo.getUpjtpeak() == E_VAL || resvo.getUpjtav() == E_VAL) {
            res = true;
        }
        return res;
    }
}
