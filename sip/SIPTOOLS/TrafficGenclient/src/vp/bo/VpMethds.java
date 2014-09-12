/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vp.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import vp.vo.CdcVo;
import vp.vo.JtrVo;
import vp.vo.LatVo;
import vp.vo.PktVo;

/**
 *
 * @author salim
 */
public class VpMethds {

    /**
     *
     * @param pktL: packet received
     * @param cdcvo
     * @param testlength: in second
     * @return
     */
    public static synchronized float computePktLossByCodec(int receivedPkt, int pps, int testlength) {
        int pktLoss = -1;
        float pktLossPerc = 0;
        //total pkt received per time interval or test length
        int expectedPktNum = pps * testlength;// 50 pps* 15 sec = 750 pkt should be received
        int effectivePktNum = receivedPkt;
        System.out.println("computePktLoss::expected-Rcv-Pkt-Num=" + expectedPktNum + "/received-pkt-num=" + effectivePktNum);

        if (effectivePktNum < expectedPktNum) {
            pktLoss = expectedPktNum - effectivePktNum;
            //int res = 100 * 100 / 3;
            //double res2 = 40/50f;
            System.out.println("pkloss = " + pktLoss +" pkt");
            pktLossPerc = (float) 100 * pktLoss / expectedPktNum;
        }
        return pktLossPerc;
    }

    
     public static synchronized int computePktLossByCodec(int pktCount, CdcVo cdcvo, int testlength) {
        int pktLoss = -1;
        int pktLossPerc;
        int pps = cdcvo.getPps();
        //total pkt received per time interval or test length
        int expectedPktNum = pps * testlength;// 50 pps* 15 sec = 750 pkt should be received
        System.out.println("computePktLoss::expected-Rcv-Pkt-Num" + expectedPktNum);
        int effectivePktNum = pktCount;
        System.out.println("computePktLoss::effective-Rcv-Pkt-Num" + effectivePktNum);
        pktLoss = expectedPktNum - effectivePktNum;
        //int res = 100 * 100 / 3;
        //double res2 = 40/50f;
        System.out.println("pkloss(pkt) = " + pktLoss);
        float pktLossPercDouble =(float) 100 * pktLoss / expectedPktNum ;
        pktLossPerc = (int) pktLossPercDouble;
        return pktLossPerc;
    }

    /**
     * it computes peak and avg of a given pkt list it takes 3 packets and
     * compute their latency timearrival latency = timesent - timearrival -
     * timeApplication processing
     *
     * @param pktL: received packets for latency test
     * @param pktSize = 3
     * @return
     */
    public static synchronized LatVo computeLat(List<PktVo> pktL, int pktLSize) {
        LatVo latObj = null;
        if (pktL.size() == pktLSize) {
            System.out.println("computeLat:pktsize=" + pktLSize);
            long peak = -1;
            long avg = -1;
            long sum = 0;
            long latInst;
            //create array to store the latencies for every packet echoed
            long[] latArray = new long[pktLSize];
            int i = 0;

            //loop thru packet and retreive latencies
            for (PktVo pktObj : pktL) {
                latInst = pktObj.getTimeArrival().getTime() - pktObj.getTimeSent().getTime();
                latArray[i] = latInst;
                System.out.println("computLat::latency[" + i + "]=" + latArray[i]);
                i++;
            }
            //computes peak/avg
            for (int j = 0; j < latArray.length; j++) {
                if (latArray[j] > peak) {
                    peak = latArray[j];
                }
                sum = sum + latArray[j];
            }
            //calculate Avg
            avg = sum / latArray.length;
            //casting results to int
            int peakInt = (int) peak;
            int avgInt = (int) avg;
            //create the LatObject results
            latObj = new LatVo(peakInt, avgInt);
            latObj.setLatArr(latArray);
        }//end of size clause
        return latObj;
    }

    /**
     *
     * @param latObj
     * @return
     */
    public static synchronized JtrVo computeJtr(LatVo latObj) {
        JtrVo jtrObj = null;
        long[] latArr = latObj.getLatArr();
        long peak = -1;
        long avg = -1;
        long sum = 0;
        //create diff array
        long[] diffArr = new long[latArr.length - 1];
        for (int i = 0; i < latArr.length - 1; i++) {
            diffArr[i] = Math.abs(latArr[i] - latArr[i + 1]);
            System.out.println("computeJtr::lat1-lat2=jitter" + latArr[i] + "-" + latArr[i + 1] + "=" + diffArr[i]);
        }

        //computes peak/avg
        for (int j = 0; j < diffArr.length; j++) {
            if (diffArr[j] > peak) {
                peak = diffArr[j];
            }
            sum = sum + diffArr[j];
        }
        //calculate Avg
        avg = sum / diffArr.length;
        //casting results to int
        int peakInt = (int) peak;
        int avgInt = (int) avg;
        jtrObj = new JtrVo(peakInt, avgInt);
        return jtrObj;
    }

    /**
     * test begins...Wed Sep 03 16:28:21 EEST 2014 computeLat:pktsize=3
     * computLat::latency[0]=10000 computLat::latency[1]=5000
     * computLat::latency[2]=3000 LatVo{peak=10000, avg=6000}
     * computeJtr::lat1-lat2=jitter10000-5000=5000
     * computeJtr::lat1-lat2=jitter5000-3000=2000 JtrVo{peak=5000, avg=3500}
     * test ends...Wed Sep 03 16:28:40 EEST 2014
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("test begins..." + new Date());
        Date d1 = new Date();//sent
        Thread.currentThread().sleep(10);
        Date d2 = new Date();//received
        PktVo pk1 = new PktVo(1, d1, d2);

        Date d3 = new Date();
        Thread.currentThread().sleep(5);
        Date d4 = new Date();
        PktVo pk2 = new PktVo(2, d3, d4);

        Date d5 = new Date();
        Thread.currentThread().sleep(3);
        Date d6 = new Date();
        PktVo pk3 = new PktVo(3, d5, d6);

        Date d7 = new Date();
        Thread.currentThread().sleep(1);
        Date d8 = new Date();
        PktVo pk4 = new PktVo(4, d7, d8);

        List<PktVo> l = new ArrayList<>();
        List<PktVo> lrecv = new ArrayList<>();
        l.add(pk1);
        l.add(pk2);
        l.add(pk3);
        //l.add(pk4);

        /**
         * *************Latendy jitter test****************
         */
        int pktListSize = 3;
        LatVo lat = computeLat(l, pktListSize);
         
        System.out.println(lat.toString());
        JtrVo jt = computeJtr(lat);
        System.out.println(jt.toString());
        System.out.println("test ends..." + new Date());

//***************Packet lost test****************************/
        System.out.println("packet lost test...........");
        int testlength = 10;
        int pps = CdcVo.returnPPSbyCodec("g729");
        List<PktVo> l2 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            l2.add(pk1);
        }
        int pktLsize = l2.size();
        System.out.println("");

        System.out.println("Pkt loss %=" + computePktLossByCodec(pktLsize, pps, testlength) + "%");
    }
}
