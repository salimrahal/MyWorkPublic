/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf.vp.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sipserver.trf.vp.vo.JtrVo;
import sipserver.trf.vp.vo.LatVo;
import sipserver.trf.vp.vo.PktVo;

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
        System.out.println("computePktLoss1::expected-Rcv-Pkt-Num=" + expectedPktNum + "/received-pkt-num=" + effectivePktNum);

        if (effectivePktNum < expectedPktNum) {
            pktLoss = expectedPktNum - effectivePktNum;
            //int res = 100 * 100 / 3;
            //double res2 = 40/50f;
            System.out.println("pkloss1 = " + pktLoss + " pkt");
            pktLossPerc = (float) 100 * pktLoss / expectedPktNum;
            pktLossPerc = formatNumberFl(pktLossPerc);
        }
        return pktLossPerc;
    }

    /*
     format a number from x.xxxxx --> x.xx
     */
    public static float formatNumberFl(float r) {
        int decimalPlaces = 2;
        BigDecimal bd = new BigDecimal(r);
// setScale is immutable
        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        r = bd.floatValue();
        return r;
    }

    /**
     * it computes peak and avg of a given pkt list it takes 3 packets and
     * compute their latency, based on the RTT: latency = round trip time / 2
     *
     *
     * @param pktL: received packets for latency test
     * @param pktSize = 3
     * @return
     */
    public static synchronized LatVo computeLatJitV2(List<PktVo> pktL) {
        LatVo latObj = null;
        int pktLSize = pktL.size();
        System.out.println("computeLatV2:pktsize=" + pktLSize);
        float peakLat = -1;
        float avgLat = -1;
        float sumLat = 0;
        float latInst;
        //create array to store the latencies for every packet echoed
        float[] latArray = new float[pktLSize];
        int i = 0;

        //loop thru packet and retreive latencies
        for (PktVo pktObj : pktL) {
            latInst = pktObj.getRtt() / 2;
            latArray[i] = latInst;
            System.out.println("computLat::latency[" + i + "]=" + latArray[i]);
            i++;
        }
        //computes lat peak/avg
        for (int j = 0; j < latArray.length; j++) {
            if (latArray[j] > peakLat) {
                peakLat = latArray[j];
            }
            sumLat = sumLat + latArray[j];
        }
        //calculate Avg
        avgLat = sumLat / latArray.length;
            //casting results to int
//            int peakLatInt = (int) peakLat;
//            int avgLatInt = (int) avgLat;
        //create the LatObject results
        latObj = new LatVo(formatNumberFl(peakLat), formatNumberFl(avgLat));
        latObj.setLatArr(latArray);
        //computing jitter
        JtrVo jtrObj = computeJtr(latObj);
        latObj.setJitterObj(jtrObj);
        return latObj;
    }

    /**
     * it computes peak and avg of a given pkt list it takes 3 packets and
     * compute their latency timearrival latency = timesent - timearrival -
     * timeApplication processing
     *
     * @param pktL: received packets for latency test
     * @param pktSize = 3
     * @return
     *
     * public static synchronized LatVo computeLatV1(List<PktVo> pktL, int
     * pktLSize) { LatVo latObj = null; if (pktL.size() == pktLSize) {
     * System.out.println("computeLat:pktsize=" + pktLSize); long peak = -1;
     * long avg = -1; long sum = 0; long latInst; //create array to store the
     * latencies for every packet echoed long[] latArray = new long[pktLSize];
     * int i = 0;
     *
     * //loop thru packet and retreive latencies for (PktVo pktObj : pktL) {
     * latInst = pktObj.getTimeArrival().getTime() -
     * pktObj.getTimeSent().getTime(); latArray[i] = latInst;
     * System.out.println("computLat::latency[" + i + "]=" + latArray[i]); i++;
     * } //computes peak/avg for (int j = 0; j < latArray.length; j++) { if
     * (latArray[j] > peak) { peak = latArray[j]; } sum = sum + latArray[j]; }
     * //calculate Avg avg = sum / latArray.length; //casting results to int int
     * peakLat = (int) peak; int avgLat = (int) avg; //create the LatObject
     * results latObj = new LatVo(peakLat, avgLat); latObj.setLatArr(latArray);
     * } return latObj; }
     */
    /**
     *
     * @param latObj
     * @return
     */
    public static synchronized JtrVo computeJtr(LatVo latObj) {
        JtrVo jtrObj = null;
        float[] latArr = latObj.getLatArr();
        float peak = -1;
        float avg = -1;
        float sum = 0;
        //create diff array
        float[] diffArr = new float[latArr.length - 1];
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
//        int peakInt = (int) peak;
//        int avgInt = (int) avg;
        jtrObj = new JtrVo(formatNumberFl(peak), formatNumberFl(avg));
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
        PktVo pk1 = new PktVo(1);
        pk1.setRtt(10);

        Date d3 = new Date();
        Thread.currentThread().sleep(5);
        Date d4 = new Date();
        PktVo pk2 = new PktVo(2);
        pk2.setRtt(20);

        Date d5 = new Date();
        Thread.currentThread().sleep(3);
        Date d6 = new Date();
        PktVo pk3 = new PktVo(3);
        pk3.setRtt(30);

        Thread.currentThread().sleep(1);
        PktVo pk4 = new PktVo(4);
        pk4.setRtt(40);
        PktVo pk5 = new PktVo(5);
        pk4.setRtt(50);
        PktVo pk6 = new PktVo(6);
        pk4.setRtt(60);
        PktVo pk7 = new PktVo(7);
        pk4.setRtt(70);
        PktVo pk8 = new PktVo(8);
        pk4.setRtt(80);

        List<PktVo> l = new ArrayList<>();
        List<PktVo> lrecv = new ArrayList<>();
        l.add(pk1);
        l.add(pk2);
        l.add(pk3);
        l.add(pk4);
         l.add(pk5);
        l.add(pk6);
        l.add(pk7);
        l.add(pk8);

        /**
         * *************Latendy jitter test****************
         */
        LatVo lat = computeLatJitV2(l);

        System.out.println(lat.toString());
        JtrVo jt = lat.getJitterObj();
        System.out.println(jt.toString());
        System.out.println("test ends..." + new Date());

        /**
         *************Packet lost test************************
         * System.out.println("packet lost test..........."); int testlength =
         * 15; int pps = CdcVo.returnPPSbyCodec("g711"); List<PktVo> l2 = new
         * ArrayList<>(); for (int i = 0; i < 50; i++) { l2.add(pk1); } int
         * pktLsize = l2.size(); System.out.println("");
         *
         * System.out.println("Pkt loss %=" + computePktLossByCodec(749, pps,
         * testlength) + "%");
         *
         */
    }
}
