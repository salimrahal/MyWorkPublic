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
public class JtrVo {

    float peak;
    float avg;

    public JtrVo(float peak, float avg) {
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

   
    @Override
    public String toString() {
        return "JtrVo{" + "peak=" + peak + ", avg=" + avg + '}';
    }

}
