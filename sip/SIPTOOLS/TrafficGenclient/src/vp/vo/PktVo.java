/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vp.vo;

import java.util.Date;

/**
 *
 * @author salim
 */
public class PktVo {
   int id;
   Date timeArrival;

    public PktVo(int id, Date timeArrival) {
        this.id = id;
        this.timeArrival = timeArrival;
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
   
   
}
