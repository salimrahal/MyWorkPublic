/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf.vp.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import sipserver.trf.vp.vo.JtrVo;
import sipserver.trf.vp.vo.LatVo;
import sipserver.trf.vp.vo.PktVo;

/**
 *
 * @author salim
 */
public class VpMethds {

    private static final long M_X_L = 1500;

    /**
     *
     * @param pktL: packet received
     * @param cdcvo
     * @param testlength: in second
     * @return
     */
    public synchronized static void cvLat(LatVo latvo) {
        if (latvo != null) {
            long lpk = VpMethds.convertNTM(latvo.getPeak());
            latvo.setPeak(lpk);

            long av = VpMethds.convertNTM(latvo.getAvg());
            latvo.setAvg(av);

            JtrVo jtO = latvo.getJitterObj();
            long jpk = VpMethds.convertNTM(jtO.getPeak());
            jtO.setPeak(jpk);

            long jav = VpMethds.convertNTM(jtO.getAvg());
            jtO.setAvg(jav);

            latvo.setJitterObj(jtO);
        } else {
            System.out.println("Error: VpMethds.cvLat(): latency Obj is null !");
        }
    }

    public static synchronized float computePktLossByCodec(int receivedPkt, int pps, int testlength) {
        int pktLoss = -1;
        float pktLossPerc = -1;
        
        int expectedPktNum = pps * testlength;// 50 pps* 15 sec = 750 pkt should be received
        int effectivePktNum = receivedPkt;
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
        // System.out.println("VpMethds:roundLat:latparam"+latparam);
        long mx_nn = convertMTN(M_X_L);
        if (latparam > mx_nn) {
            // System.out.println("VpMethds:roundLat:latparam"+latparam+"ns>"+M_X_L+" ns");
            latparam = mx_nn;
        } else {
            //System.out.println("VpMethds:roundLat:latparam no need to round.");
        }
        return latparam;
    }

    public static long convertMTN(long ms) {
        long ns = ms * 1000000;
        return ns;
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

    /*
     remove pkt that has rtt = 0, or the packet that they are not received
     */
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
        System.out.println("Pkt loss %=" + computePktLossByCodec(700, 50,
                15) + "%");

    }
}
