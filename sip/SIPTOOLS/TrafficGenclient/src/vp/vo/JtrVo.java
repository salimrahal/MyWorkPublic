/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vp.vo;

/**
 *
 * @author salim
 */
public class JtrVo {

    long peak;
    long avg;

    public JtrVo(long peak, long avg) {
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

   
    @Override
    public String toString() {
        return "JtrVo{" + "peak=" + peak + ", avg=" + avg + '}';
    }

}
