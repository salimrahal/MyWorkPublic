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
public class LatVo {
    int peak;
    int avg;

    public LatVo(int peak, int avg) {
        this.peak = peak;
        this.avg = avg;
    }

    public int getPeak() {
        return peak;
    }

    public void setPeak(int peak) {
        this.peak = peak;
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }
    
}
