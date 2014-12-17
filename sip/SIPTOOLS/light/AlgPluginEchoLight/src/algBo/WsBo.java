/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo;

/**
 *
 * @author salim
 */
public class WsBo {

    public static int vam(String tid, String cus, String pip, String trans, int prs, int prdes) {
        int res = 0;
        try {
            res = vAlgpam(tid, cus, pip, trans, prs, prdes);
        } catch (Exception e) {
        }
        return res;
    }

    public static int vae(String tid, boolean isalgd, boolean isfwd) {
        int res = 0;
        try {
            res = vAlgEnd(tid, isalgd, isfwd);
        } catch (Exception e) {
        }
        return res;
    }
     public static int dae(String tid) {
        int res = 0;
        try {
            res = dtid(tid);
        } catch (Exception e) {
        }
        return res;
    }
    private static Integer vAlgEnd(java.lang.String tid, boolean isalgdetected, boolean isfwdetected) {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.vAlgEnd(tid, isalgdetected, isfwdetected);
    }

    private static Integer vAlgpam(java.lang.String tid, java.lang.String cus, java.lang.String pip, java.lang.String trs, int prs, int prdes) {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.vAlgpam(tid, cus, pip, trs, prs, prdes);
    }

    private static Integer dtid(java.lang.String tid) {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.dtid(tid);
    }

}
