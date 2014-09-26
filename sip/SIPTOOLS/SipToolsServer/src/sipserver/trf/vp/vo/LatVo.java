/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sipserver.trf.vp.vo;

/**
 *
 * @author salim
 */
public class LatVo {
    long peak;
    long avg;
    long[] latArr;
    JtrVo jitterObj;

    public LatVo(long peak, long avg) {
        this.peak = peak;
        this.avg = avg;
    }

    public long getPeak() {
        return peak;
    }

    public void setPeak(long peak) {
        this.peak = peak;
    }

    public long getAvg() {
        return avg;
    }

    public void setAvg(long avg) {
        this.avg = avg;
    }

    public long[] getLatArr() {
        return latArr;
    }

    public void setLatArr(long[] latArr) {
        this.latArr = latArr;
    }

    public JtrVo getJitterObj() {
        return jitterObj;
    }

    public void setJitterObj(JtrVo jitterObj) {
        this.jitterObj = jitterObj;
    }


    @Override
    public String toString() {
        return "LatVo{" + "peak=" + peak + ", avg=" + avg + ", latArr=" + latArr + ", jitterObj=" + jitterObj + '}';
    }

  
    
    
   
}
