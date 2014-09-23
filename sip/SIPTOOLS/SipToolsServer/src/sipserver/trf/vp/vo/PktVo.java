/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf.vp.vo;

import java.util.Date;

/**
 *
 * @author salim
 */
public class PktVo {

    int id;
    Date timeSent;//useful latency and jitter test
    Date timeArrival;//useful latency and jitter test
 float rtt;

    public PktVo(int id) {
        this.id = id;
    }

    public float getRtt() {
        return rtt;
    }

    public void setRtt(float rtt) {
        this.rtt = rtt;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(Date timeArrival) {
        this.timeArrival = timeArrival;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    @Override
    public String toString() {
        return "PktVo{" + "id=" + id + ", timeSent=" + timeSent + ", timeArrival=" + timeArrival + ", rtt=" + rtt + '}';
    }

  

}
