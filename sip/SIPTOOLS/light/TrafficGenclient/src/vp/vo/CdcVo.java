/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vp.vo;

import com.safirasoft.CodecVo;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author salim
 */
public class CdcVo {

    String codec;
    int pps;

    public static final String CODEC_G711 = "g711";
    public static final String CODEC_G722 = "g722";
    public static final String CODEC_G729 = "g729";
    public static final String CODEC_ILBC = "ilbc";
    public static final String CODEC_SILK = "silk";

    public static final String CODEC_G711_VIEW = "G.711 (87.2 Kbps)";
    public static final String CODEC_G722_VIEW = "G.722 (80 kbps)";
    public static final String CODEC_G729_VIEW = "G.729 (31.2 Kbps)";
    public static final String CODEC_ILBC_VIEW = "ILBC (27.7 kbps)";
    public static final String CODEC_SILK_VIEW = "SILK (178,5 kbps)";

    @Deprecated
    public static String returnSelectedCodec(int index) {
        String codec = null;
        switch (index) {
            case 0:
                codec = CODEC_G711;
                break;
            case 1:
                codec = CODEC_G722;
                break;
            case 2:
                codec = CODEC_G729;
                break;
            case 3:
                codec = CODEC_ILBC;
                break;
            case 4:
                codec = CODEC_SILK;
                break;

        }
        return codec;
    }
    
        public static String returnSelectedCodec(String codecSelected) {
        String codec = null;
        switch (codecSelected) {
            case CODEC_G711_VIEW:
                codec = CODEC_G711;
                break;
            case CODEC_G722_VIEW:
                codec = CODEC_G722;
                break;
            case CODEC_G729_VIEW:
                codec = CODEC_G729;
                break;
            case CODEC_ILBC_VIEW:
                codec = CODEC_ILBC;
                break;
            case CODEC_SILK_VIEW:
                codec = CODEC_SILK;
                break;

        }
        return codec;
    }

    public static String[] buildListViewCodec(List<CodecVo> cdcLEnabled) {
        int cdcSize = cdcLEnabled.size();
        //int cdcSize = 1;
        String[] strCodec = new String[cdcSize];
        int i = 0;
        //String[] tmp = new String[]{"g711"};
        for (CodecVo c : cdcLEnabled) {
            switch (c.getCodec().toLowerCase()) {
                case CODEC_G711:
                    strCodec[i] = CODEC_G711_VIEW;
                    i++;
                    break;
                case CODEC_G722:
                    strCodec[i] = CODEC_G722_VIEW;
                    i++;
                    break;
                case CODEC_G729:
                    strCodec[i] = CODEC_G729_VIEW;
                    i++;
                    break;
                case CODEC_ILBC:
                    strCodec[i] = CODEC_ILBC_VIEW;
                    i++;
                    break;
                case CODEC_SILK:
                    strCodec[i] = CODEC_SILK_VIEW;
                    i++;
                    break;
            }
        }
        //System.out.println("buildListViewCodec res="+Arrays.toString(strCodec));
        return strCodec;
    }
    /*
     get byte array by codec
     */

    public static byte[] returnPayloadybyCodec(String codec) {
        byte[] payload = null;
        switch (codec) {
            case CODEC_G711:
                payload = new byte[160];
                break;
            case CODEC_G722:
                payload = new byte[160];
                break;
            case CODEC_G729:
                payload = new byte[20];
                break;
            case CODEC_ILBC:
                payload = new byte[50];
                break;
            case CODEC_SILK:
                payload = new byte[500];
                break;
        }

        return payload;
    }

    public static int computePeriodBetweenPkt(int pps) {
        int period = 0;
        //80pps --> 12.5 ms
        //50pps --> 20 ms
        period = 1000 / pps;
        return period;
    }
    /*
     pps table
     */

    public static int returnPPSbyCodec(String codec) {
        int pps = -1;
        switch (codec) {
            case CODEC_G711:
                pps = 50;
                break;
            case CODEC_G722:
                pps = 50;
                break;
            case CODEC_G729:
                pps = 50;
                break;
            case CODEC_ILBC:
                pps = 33;
                break;
            case CODEC_SILK:
                pps = 80;
                break;
        }

        return pps;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public int getPps() {
        return pps;
    }

    public void setPps(int pps) {
        this.pps = pps;
    }

    public static void main(String[] args) {
       // System.out.println("computePeriodBetweenPkt(160)=" + computePeriodBetweenPkt(80)); //returns 6ms
        buildListViewCodec(null);
    }
}
