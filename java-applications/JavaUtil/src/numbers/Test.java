package numbers;

import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {
	
           int localtimelength = 120 + 120/3;
           System.out.println("localtimelength "+localtimelength);
        //formatNumberFl((float) 5.1234);//
         formatNumberFl((float) 0.12999999523162842);
       /*
         System.out.println("racine=" + calRacine(0.67));
        System.out.println("using sqrt=" + Math.sqrt(0.67));
        System.out.println("using sqrt=" + Math.pow(4, 2));
        */
		//mathTest();
        //System.out.println(11^2);
        // compileTest();
        // declaringNumb();
        // BoxingInteger();
    }

    /*
    format number: x.xxxxxx -> x.xx
    */
    public static double formatNumberDb(double r) {
        //double r = 5.1234;
        System.out.println(r); // r is 5.1234

        int decimalPlaces = 2;
        BigDecimal bd = new BigDecimal(r);

// setScale is immutable
        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        r = bd.doubleValue();

        System.out.println(r); // r is 5.12
        return r;
    }
        public static double formatNumberFl(float r) {
        //double r = 5.1234;
        System.out.println(r); // r is 5.1234

        int decimalPlaces = 2;
        BigDecimal bd = new BigDecimal(r);

// setScale is immutable
        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        r = bd.floatValue();

        System.out.println(r); // r is 5.12
        return r;
    }

    /**
     * It calculate the racine of the base a: the base ; b the exponent
     *
     * @param base
     * @return
     */
    public static double calRacine(double num) {
        return Math.pow(num, 0.5);
    }

    public static double calRacineV1(double num) {
        return Math.sqrt(num);
    }

    public static void meth1() {
        int i;
        i = 3;
        System.out.println((int) i * 2.5 / 3.0 + "\n");// 2.5
    }

    public static void meth2() {
        int i;
        i = 3;
        double ff = i * 2.5 / 3.0;
        System.out.println(i * 2.5 / 3.0);// 2.5

        int x = (int) 3.5;
        System.out.println(x);// 3
        System.out.println((int) 4.666);// 4
        System.out.println((int) 4.666 / 3.9);// 1.0256410256410258
    }

    public static void mathTest() {
        if (-2.1 > -2.0) {
            System.out.println("yes");
        } else {
            System.out.println("false");
        }

        System.out.println("floor -2.1=" + Math.floor(-2.1));
        System.out.println(Math.ceil(-2.1));
        System.out.println("floor 2.1=" + Math.floor(2.1));
        System.out.println(Math.ceil(2.1));
    }

    public static void compileTest() {
        char c = 'c';
        int i = 50;
        double d = 80;
        long l = 200;
        String s = "Goodbye";

        s += i;
        //s = s + i;
        s += c;
        s += d;
        s += l;

        /*
         * wont compile 
         * i+=s; 
         * c+=s; 
         * d+=s; 
         * l+=s; 
         * c=c+i;
         */
        // it compile
        c += i;
        System.out.println(c);
        // casting between numbers
        i += l;
        i += d;
        d += i;
    }

    public static void declaringNumb() {
        /**
         * ** DOUBLE ***
         */
        double dou = 10;
        double dd = 10d;
        double ddd = 10.01f;
        System.out.println("dd=" + dd + "/ddd=" + ddd);

        /**
         * * FLOAT ****
         */
		// by default floating points are double, so when declaring a float
        // we should add a f.
        float fff = (float) 11.01;
        float ff1 = 11.02f;
        float ff = 11;
        System.out.println("float ff=" + ff);// out: 11.0
        short sh = 12;
        // compile error: short sh1=12.99;
        short sh1 = (short) 12.99;
        int ii = (int) 9.0;
        long ll = (long) 200.00;
        long l1 = 200l;
        long l2 = 200;
        // compilation error: passShort(ii);
        passShort((short) ii);
		// compilation error:passFloat(dd)
        // passFloat(10.01)
        passFloat(10.01f);
        passDouble(ddd);

		// short descendent from int
        // compilation error: passInteger(ff)
        passInteger((int) ff);
        // we can pass short without casting
        passInteger(sh1);
    }

    public static void passShort(short ss) {
        System.out.println(ss);
    }

    public static void passFloat(float ff) {
        System.out.println(ff);
    }

    public static void passDouble(double dd) {
        System.out.println(dd);
    }

    public static void passInteger(int ii) {
        System.out.println(ii);
    }

    public static void BoxingInteger() {
        int i = new Integer(2);
        System.out.println(i);
        Integer iObj = 1;
        int[] iarr = {1, 2, 3};
        int[] iarr1 = {1, 2, 3};
        Integer[] iObjarr = {2, 4, 4};
        // error compilation: iarr = iObjarr; int[] != Integer[]
        iarr = iarr1;// no error: int[] = int []
    }
}
