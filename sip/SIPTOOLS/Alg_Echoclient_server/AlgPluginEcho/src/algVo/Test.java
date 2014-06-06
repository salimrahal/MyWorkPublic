/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algVo;

/**
 *
 * @author salim
 */
public class Test {

    /**
     *
     */
    public  Integer seqNumber; //1, 2, 3, 4
    Integer portscr;
    Integer portDest;
    String transport;

    public Test(Integer seqNumber, Integer portscr, Integer portDest, String transport) {
        this.seqNumber = seqNumber;
        this.portscr = portscr;
        this.portDest = portDest;
        this.transport = transport;
    }

    public Test() {
    }

    public Integer getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
    }
    
    
    public Integer getPortscr() {
        return portscr;
    }

    public void setPortscr(Integer portscr) {
        this.portscr = portscr;
    }

    public Integer getPortDest() {
        return portDest;
    }

    public void setPortDest(Integer portDest) {
        this.portDest = portDest;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    @Override
    public String toString() {
        return "Test{" + "seqNumber=" + seqNumber + ", portscr=" + portscr + ", portDest=" + portDest + ", transport=" + transport + '}';
    }
    
}
