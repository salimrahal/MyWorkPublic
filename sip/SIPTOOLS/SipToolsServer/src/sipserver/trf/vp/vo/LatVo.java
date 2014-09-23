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
    float peak;
    float avg;
    float[] latArr;
    JtrVo jitterObj;

    public LatVo(float peak, float avg) {
        this.peak = peak;
        this.avg = avg;
    }

    
    public float getPeak() {
        return peak;
    }

    public void setPeak(float peak) {
        this.peak = peak;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public float[] getLatArr() {
        return latArr;
    }

    public void setLatArr(float[] latArr) {
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
