package string;

import pack1.*;

public class StringMethods {

    public static void main(String[] args) {
        //a- build update list:get the list of Sip ID to watch
        StringBuilder strbuilder = new StringBuilder("299, 112, 140");
        String sipIds = strbuilder.toString();
        // removes all whitespaces and non visible characters such as tab, \n
        sipIds = sipIds.replaceAll("\\s+", "");
        String[] sipArr = sipIds.split(",");
        for (int i = 0; i < sipArr.length; i++) {
            System.out.println("[" + sipArr[i] + "]");
        }

    }

    public static void replaceTest() {
        String s  = "ader\"><div clas=\"entry-contenta";
        StringBuilder strbuilder = new StringBuilder("<div class=\"wrap\"><div id=\"title-area\"><p id=\"title\"><a href=\"http://www.mysqltutorial.org/\" title=\"MySQL Tutorial\">MySQL Tutorial</a></p><p id=\"description\">A comprehensive MySQL");
        String sipIds = strbuilder.toString();
        // removes all whitespaces and non visible characters such as tab, \n
        sipIds = sipIds.replaceAll("\\s+", "");
    }

    public static void stringVSStringBuilder() {
        String s = "salim";
        //creating a new String object, but it is lost , no assignement.
        s.concat("rahal");
        System.out.println(s);//res = salim
        //creating new String
        String ss = s.concat("rahal");// salim rahal
        System.out.println(ss);
        StringBuilder sb = new StringBuilder("salim");
        sb.append(" rahal");
        System.out.println(sb);//res= salim rahal
    }

    public static String concat1(String s1, String s2, String s3, String s4, String s5, String s6) {
        String result = "";
        result += s1;
        result += s2;
        result += s3;
        result += s4;
        result += s5;
        result += s6;
        return result;
    }

    /**
     * String is immutable, so the compiled code will create many intermediate
     * String objects, which can strain the garbage collector. A common remedy
     * is to introduce StringBuffer, causing it to look like this:
     *
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @param s5
     * @param s6
     * @return
     */
    public static String concat2(String s1, String s2, String s3, String s4, String s5, String s6) {
        StringBuffer result = new StringBuffer();
        result.append(s1);
        result.append(s2);
        result.append(s3);
        result.append(s4);
        result.append(s5);
        result.append(s6);
        return result.toString();
    }

    public static String getFirstLetter(String chaine) {
        String[] tab = null;
        String res = "";
        chaine = chaine.replace("MD", "");
        if (chaine.contains(" ")) {
            System.out.println("space");
            tab = chaine.split(" ");

        } else if (chaine.contains("^")) {
            System.out.println("cont ^");
            tab = chaine.split("\\^");
        }

        for (int i = 0; i < tab.length; i++) {
            res += tab[i].substring(0, 1);
        }
        return res;
    }

    public static String makeInitials(String chaine) {
        String[] tab = null;
        String firstLetters = "";

        if (chaine != null) {
            chaine = chaine.replace("MD", "");

            if (chaine.contains(" ")) {
                tab = chaine.split(" ");
            } else {
                tab = chaine.split("\\^");
            }
            for (int i = 0; i < tab.length; i++) { // this condition already implemented in library.vm in makeinitial
                // macro
                if (tab[i].length() > 0) {
                    if (!tab[i].substring(0, 1).equalsIgnoreCase("<")) {
                        firstLetters += tab[i].substring(0, 1);
                    }
                }
            }
        }
        return firstLetters;
    }

    public static String getExtAccession(String accessNo) {
        String[] tab;
        String accessNoTmp = "";
        accessNoTmp = accessNo;
        if (accessNoTmp != null) {
            if (accessNoTmp.contains("__")) {
                tab = accessNoTmp.split("__");
                //if(tab.length>0){
                accessNoTmp = tab[1];
                //}
            }
        }
        return accessNoTmp;
    }
}
