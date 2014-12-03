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


    public static synchronized float computePktLossByCodec(int receivedPkt, int pps, int testlength) {
        int pktLoss = -1;
        float pktLossPerc = -1;
        //total pkt received per time interval or test length
        int expectedPktNum = pps * testlength;// 50 pps* 15 sec = 750 pkt should be received
        int effectivePktNum = receivedPkt;
        System.out.println("computePktLoss1::expected-Rcv-Pkt-Num=" + expectedPktNum + "/received-pkt-num=" + effectivePktNum);
        
        if (effectivePktNum > expectedPktNum) {
            effectivePktNum = expectedPktNum;
        }
        pktLoss = expectedPktNum - effectivePktNum;
         
        pktLossPerc = (float) 100 * pktLoss / expectedPktNum;
        if (pktLossPerc != 0) {
            pktLossPerc = formatNumberFl(pktLossPerc);
        }

        return pktLossPerc;
    }

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
        
        int expectedPktNum = pps * testlength;
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

    public static void main(String[] args) throws InterruptedException {
    }
}
