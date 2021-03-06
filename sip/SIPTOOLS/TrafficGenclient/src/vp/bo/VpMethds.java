/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vp.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.Iterator;
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

    private static final long M_X_L = 1500;

    public static int safeLongToInt(long l) {
        int i = (int) l;
        if ((long) i != l) {
            throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
        }
        return i;
    }

    public static void cvLat(LatVo latvo) {
        if (latvo != null) {
            long lpk = vp.bo.VpMethds.convertNTM(latvo.getPeak());
            latvo.setPeak(lpk);

            long av = vp.bo.VpMethds.convertNTM(latvo.getAvg());
            latvo.setAvg(av);

            JtrVo jtO = latvo.getJitterObj();
            long jpk = vp.bo.VpMethds.convertNTM(jtO.getPeak());
            jtO.setPeak(jpk);

            long jav = vp.bo.VpMethds.convertNTM(jtO.getAvg());
            jtO.setAvg(jav);

            latvo.setJitterObj(jtO);
        } else {
            System.out.println("Error: VpMethds.cvLat(): latency Obj is null !");
        }
    }

    /**
     *
     * @param receivedPkt
     * @param testlength: in second
     * @return
     */
    public static synchronized float computePktLossByCodec(int receivedPkt, int pps, int testlength) {
        int pktLoss = -1;
        float pktLossPerc = -1;
        //total pkt received per time interval or test length
        int expectedPktNum = pps * testlength;// 50 pps* 15 sec = 750 pkt should be received
        int effectivePktNum = receivedPkt;
        System.out.println("computePktLoss1::expected-Rcv-Pkt-Num=" + expectedPktNum + "/received-pkt-num=" + effectivePktNum);
        /*in case the packet received > expected ,
         I should take the expected. this case occurred in G729/60 sec where I have received extrat two packet +2: expected:3000/received 3002
         */
        if (effectivePktNum > expectedPktNum) {
            effectivePktNum = expectedPktNum;
        }
        pktLoss = expectedPktNum - effectivePktNum;
            //int res = 100 * 100 / 3;
        //double res2 = 40/50f;
        //System.out.println("pkloss1 = " + pktLoss + " pkt");
        pktLossPerc = (float) 100 * pktLoss / expectedPktNum;
        if (pktLossPerc != 0) {
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

    @Deprecated
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
        float pktLossPercDouble = (float) 100 * pktLoss / expectedPktNum;
        pktLossPerc = (int) pktLossPercDouble;
        return pktLossPerc;
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
        long peakLat = -1;
        long avgLat = -1;
        long sumLat = 0;
        long latInst;
        if (pktLSize > 1) {
            //create array to store the latencies for every packet echoed
            long[] latArray = new long[pktLSize];
            int i = 0;

            //loop thru packet and retreive latencies
            for (PktVo pktObj : pktL) {
                latInst = pktObj.getRtt() / 2;
                latInst = roundLat(latInst);
                latArray[i] = (long) latInst;
                //System.out.println("computLat::latency[" + i + "]=" + pktObj.getRtt() + "/ 2=" + latArray[i]);
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
            latObj = new LatVo(peakLat, avgLat);
            latObj.setLatArr(latArray);
            //computing jitter
            JtrVo jtrObj = computeJtr(latObj);
            latObj.setJitterObj(jtrObj);
        } else {
            System.out.println("Error: VpMethds.computeLatJitV2(): pktL Array length is " + pktLSize);
        }
        return latObj;
    }

    /*
     if lat > 1500 ms --> round it on 1500 ms
     */
    public static long roundLat(long latparam) {
        //System.out.println("roundLat:latparam"+latparam);
        long mx_nn = convertMTN(M_X_L);
        if (latparam > mx_nn) {
            //System.out.println("roundLat:latparam"+latparam+"ns>"+M_X_L+" ns");
            latparam = mx_nn;
        }
        return latparam;
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
        int arrL = latArr.length;
        if (arrL > 1) {
            //create diff array
            long[] diffArr = new long[arrL - 1];
            for (int i = 0; i < arrL - 1; i++) {
                diffArr[i] = Math.abs(latArr[i] - latArr[i + 1]);
                //System.out.println("computeJtr::lat1-lat2=jitter" + latArr[i] + "-" + latArr[i + 1] + "=" + diffArr[i]);
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
            jtrObj = new JtrVo(peak, avg);
        } else {
            System.out.println("Error: VpMethds.computeJtr(): Latency Array length is " + arrL);
        }
        return jtrObj;
    }

    public static long convertNTM(long nv) {
        long ms = nv / 1000000;
        return ms;
    }

    public static long convertMTN(long ms) {
        long ns = ms * 1000000;
        return ns;
    }

    public static List<PktVo> cll(List<PktVo> pkl) {
        for (Iterator<PktVo> iterator = pkl.iterator(); iterator.hasNext();) {
            PktVo value = iterator.next();
            if (value.getRtt() == 0) {
                iterator.remove();
            }
        }
        return pkl;
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
        System.out.println(" safeLongToInt(405)=" + safeLongToInt(405));
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

        Date d7 = new Date();
        Thread.currentThread().sleep(1);
        Date d8 = new Date();
        PktVo pk4 = new PktVo(4);
        pk4.setRtt(40);

        List<PktVo> l = new ArrayList<>();
        List<PktVo> lrecv = new ArrayList<>();
        l.add(pk1);
        l.add(pk2);
        l.add(pk3);
        l.add(pk4);

        /**
         * *************Latendy jitter test****************
         */
        LatVo lat = computeLatJitV2(l);

        System.out.println(lat.toString());
        JtrVo jt = lat.getJitterObj();
        System.out.println(jt.toString());
        System.out.println("test ends..." + new Date());

        /**
         ************Packet lost test************************
         */
        System.out.println("packet lost test...........");
        int testlength = 15;
        int pps = CdcVo.returnPPSbyCodec("g729");

        System.out.println("Pkt loss %=" + computePktLossByCodec(3002, pps,
                testlength) + "%");

    }
}
