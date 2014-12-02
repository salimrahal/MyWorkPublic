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
    long timeSent;
    long timeArrival;
    long rtt;// rtt ~= timeArrival - timeSent

    public PktVo(int id) {
        this.id = id;
    }
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(long timeSent) {
        this.timeSent = timeSent;
    }

    public long getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(long timeArrival) {
        this.timeArrival = timeArrival;
    }

    public long getRtt() {
        return rtt;
    }

    public void setRtt(long rtt) {
        this.rtt = rtt;
    }
   
    @Override
    public String toString() {
        return "PktVo{" + "id=" + id + ", timeSent=" + timeSent + ", timeArrival=" + timeArrival + ", rtt=" + rtt + '}';
    }

  

}
