/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import com.safirasoft.ResVo;

/**
 *
 * @author salim
 */
public class WsRes {

    boolean valueSet = false;
    ResVo res = null;

    public synchronized ResVo getRes() {
        if (!valueSet) {
            try {
                System.out.println("getRes.. wait()");
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        if(res != null){
            System.out.println("Got: clientip=" + res.getPuip()+"/getDopkloss="+res.getDopkloss());
        }else{
              System.out.println("Got: warning result is null!");
        }
        valueSet = false;
        notify();
        return res;
    }

    public synchronized void putRes(ResVo res) {
        if (valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        this.res = res;
        valueSet = true;
        notify();
        if(res != null){
             System.out.println("Put: clientip=" + res.getPuip()+"/getDopkloss="+res.getDopkloss());
        }else{
             System.out.println("Put: warning: putting  a Null value! ");
        }
    }

    public synchronized ResVo retreiveResbyWS(String ti) {
        System.out.println("retreiveResbyWS ti=" + ti);
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.getrs(ti);
    }

    public static void main(String[] args) {
        // callws("0681d609-fd24-4f4b-b4e3-38079869afcc");          
    }
}
