/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.IOException;

/**
 *
 * @author salim
 */
public class TrfGenBo {

    public static final String PRT_B = "No PORT is available for test!!!";
    public static final String CODEC_G711 = "g711";
    public static final String CODEC_G722 = "g722";
    public static final String CODEC_G729 = "g729";
    public static final String CODEC_ILBC = "ILBC";
    public static final String CODEC_SILK = "SILK";

    public static String srIp;
    public enum Codec {

        G711, G722, G729, ILBC, SILK
    }

    public static String getSrIp() {
        return srIp;
    }

    public static void setSrIp(String srIp) {
        TrfGenBo.srIp = srIp;
    }

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
}
