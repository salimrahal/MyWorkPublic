/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vp.vo;

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
    public static final String CODEC_ILBC = "ILBC";
    public static final String CODEC_SILK = "SILK";

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

}
