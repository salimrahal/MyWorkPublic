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
    float timeSent;
    float timeArrival;
    float rtt;// rtt ~= timeArrival - timeSent

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

    public float getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(float timeSent) {
        this.timeSent = timeSent;
    }

    public float getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(float timeArrival) {
        this.timeArrival = timeArrival;
    }

   
    @Override
    public String toString() {
        return "PktVo{" + "id=" + id + ", timeSent=" + timeSent + ", timeArrival=" + timeArrival + ", rtt=" + rtt + '}';
    }

  

}
