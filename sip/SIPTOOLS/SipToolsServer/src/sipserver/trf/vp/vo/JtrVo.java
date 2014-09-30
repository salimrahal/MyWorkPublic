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

    long peak = -1;
    long avg = -1;

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
