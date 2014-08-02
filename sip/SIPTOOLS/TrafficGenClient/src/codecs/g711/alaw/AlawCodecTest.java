/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package codecs.g711.alaw;

import codecs.Codec;
import codecs.memory.Frame;
import codecs.memory.Memory;

/**
 *
 * @author Oleg Kulikov
 */
public class AlawCodecTest {

    private Frame buffer = Memory.allocate(512);//initializing an array of byte, giving the array size 512, values are zero
//sr: 
    private Frame bufferDefault711 = Memory.allocate(320);//initializing an array of byte, giving the array size 320, values are zero

    //256 value
    private static short aLawDecompressTable[] = new short[]{
        -5504, -5248, -6016, -5760, -4480, -4224, -4992, -4736,
        -7552, -7296, -8064, -7808, -6528, -6272, -7040, -6784,
        -2752, -2624, -3008, -2880, -2240, -2112, -2496, -2368,
        -3776, -3648, -4032, -3904, -3264, -3136, -3520, -3392,
        -22016, -20992, -24064, -23040, -17920, -16896, -19968, -18944,
        -30208, -29184, -32256, -31232, -26112, -25088, -28160, -27136,
        -11008, -10496, -12032, -11520, -8960, -8448, -9984, -9472,
        -15104, -14592, -16128, -15616, -13056, -12544, -14080, -13568,
        -344, -328, -376, -360, -280, -264, -312, -296,
        -472, -456, -504, -488, -408, -392, -440, -424,
        -88, -72, -120, -104, -24, -8, -56, -40,
        -216, -200, -248, -232, -152, -136, -184, -168,
        -1376, -1312, -1504, -1440, -1120, -1056, -1248, -1184,
        -1888, -1824, -2016, -1952, -1632, -1568, -1760, -1696,
        -688, -656, -752, -720, -560, -528, -624, -592,
        -944, -912, -1008, -976, -816, -784, -880, -848,
        5504, 5248, 6016, 5760, 4480, 4224, 4992, 4736,
        7552, 7296, 8064, 7808, 6528, 6272, 7040, 6784,
        2752, 2624, 3008, 2880, 2240, 2112, 2496, 2368,
        3776, 3648, 4032, 3904, 3264, 3136, 3520, 3392,
        22016, 20992, 24064, 23040, 17920, 16896, 19968, 18944,
        30208, 29184, 32256, 31232, 26112, 25088, 28160, 27136,
        11008, 10496, 12032, 11520, 8960, 8448, 9984, 9472,
        15104, 14592, 16128, 15616, 13056, 12544, 14080, 13568,
        344, 328, 376, 360, 280, 264, 312, 296,
        472, 456, 504, 488, 408, 392, 440, 424,
        88, 72, 120, 104, 24, 8, 56, 40,
        216, 200, 248, 232, 152, 136, 184, 168,
        1376, 1312, 1504, 1440, 1120, 1056, 1248, 1184,
        1888, 1824, 2016, 1952, 1632, 1568, 1760, 1696,
        688, 656, 752, 720, 560, 528, 624, 592,
        944, 912, 1008, 976, 816, 784, 880, 848
    };

    //sr: put 160 value: default size of a voice payload g711
    private static short aLawDecompressTableDefault[] = new short[]{
        -5504, -5248, -6016, -5760, -4480, -4224, -4992, -4736,
        -7552, -7296, -8064, -7808, -6528, -6272, -7040, -6784,
        -2752, -2624, -3008, -2880, -2240, -2112, -2496, -2368,
        -3776, -3648, -4032, -3904, -3264, -3136, -3520, -3392,
        -22016, -20992, -24064, -23040, -17920, -16896, -19968, -18944,
        -30208, -29184, -32256, -31232, -26112, -25088, -28160, -27136,
        -11008, -10496, -12032, -11520, -8960, -8448, -9984, -9472,
        -15104, -14592, -16128, -15616, -13056, -12544, -14080, -13568,
        -344, -328, -376, -360, -280, -264, -312, -296,
        -472, -456, -504, -488, -408, -392, -440, -424,
        -88, -72, -120, -104, -24, -8, -56, -40,
        -216, -200, -248, -232, -152, -136, -184, -168,
        -1376, -1312, -1504, -1440, -1120, -1056, -1248, -1184,
        -1888, -1824, -2016, -1952, -1632, -1568, -1760, -1696,
        -688, -656, -752, -720, -560, -528, -624, -592,
        -944, -912, -1008, -976, -816, -784, -880, -848,
        5504, 5248, 6016, 5760, 4480, 4224, 4992, 4736,
        7552, 7296, 8064, 7808, 6528, 6272, 7040, 6784,
        2752, 2624, 3008, 2880, 2240, 2112, 2496, 2368,
        944, 912, 1008, 976, 816, 784, 880, 848
    };

    private byte[] src = new byte[512];
    private byte[] srcDefault711 = new byte[320];

    public AlawCodecTest() {
    }
    /*
     original method
     */

    public void setUp() throws Exception {
//          System.out.println("before set up array");
//        printoutBuffer(buffer.getData());
        //sr: print the length of the compressed table that contains digital compressed value
        System.out.println("aLawDecompressTable length=" + aLawDecompressTable.length);//length = 256
        int k = 0;
        int l = 0;
        //filling the buffer with the alawdecompresstable values
        byte[] src1 = buffer.getData();
        for (int i = 0; i < 256; i++) {
            short s = aLawDecompressTable[i];//retreiving the digital value from the aLawDecompressTable table
            src[k++] = (byte) (s);
            src[k++] = (byte) (s >> 8);//Bitwise and Bit Shift Operators: >> Signed right shift

            src1[l++] = (byte) (s);
            src1[l++] = (byte) (s >> 8);
        }
//        System.out.println("after set up array");
//        printoutBuffer(buffer.getData());
        buffer.setLength(512);
        System.out.println("buffer length=" + buffer.getLength());
    }
    /*
     sr
     320 byte buffer size 
     */

    public void setUpDefaultG711() throws Exception {
//          System.out.println("before set up array");
//        printoutBuffer(buffer.getData());
        //sr: print the length of the compressed table that contains digital compressed value
        System.out.println("aLawDecompressTable length=" + aLawDecompressTableDefault.length);//length = 160
        int k = 0;
        int l = 0;
        //filling the buffer with the alawdecompresstable values
        byte[] src1 = bufferDefault711.getData();
        for (int i = 0; i < 160; i++) {
            short s = aLawDecompressTableDefault[i];//retreiving the digital value from the aLawDecompressTable table
            srcDefault711[k++] = (byte) (s);
            srcDefault711[k++] = (byte) (s >> 8);//Bitwise and Bit Shift Operators: >> Signed right shift

            src1[l++] = (byte) (s);
            src1[l++] = (byte) (s >> 8);
        }
//        System.out.println("after set up array");
//        printoutBuffer(buffer.getData());
        bufferDefault711.setLength(320);
        System.out.println("bufferDefault711 length=" + bufferDefault711.getLength());//buffer filled with values
    }

    /**
     * Test of process method, of class Decoder. 512 byte are compressed into
     * --> 256 byte I should give 320 byte to the input compressor to get the
     * default payload of 160 byte (320/2)
     */
    public void testCodec() throws Exception {
        setUp();
        /**
         * ***********Encoding**************
         */
        Codec compressor = new Encoder();
        System.out.println("buffer before compression=" + buffer.getData().length);//512
        printoutBuffer(buffer.getData());
        long s = System.nanoTime();
        Frame frameCompressed = compressor.process(buffer);
        System.out.println("Frame compressed=" + frameCompressed.getData().length + ":");//Frame compressed=256:
        byte[] compressedByte = frameCompressed.getData();
        //sr
        //System.out.println("buffer after compression="+buffer.getData().length);//512
        printoutBuffer(buffer.getData());
        long f = System.nanoTime();
        long elapsedtime = (f - s);
        System.out.println("Duration in nanosecond=" + elapsedtime);// Duration=45126573
        double seconds = (double) elapsedtime / 1000000000.0;
        System.out.println("Duration in second=" + seconds);//Duration in second=0.048414073

        /**
         * ***********Decoding**************
         */
        Codec decompressor = new Decoder();
        decompressor.process(buffer);

        byte[] res = buffer.getData();
        for (int i = 0; i < src.length; i++) {
            if (src[i] != res[i]) {
                System.out.println("mismatch found at " + i);
            }
        }
    }

    /*sr
     G711
     1- compression/ encoding:
     pass a digital input of size 320 value or digital number and get an outpout compressed of 160 bytes
     2- decompression/decoding
     pass a compressed 160 bytes and get 320 decompressed
     */
    public void testCodecDefaultpayload() throws Exception {
        //set up the buffer by 320 byte : digital values
        setUpDefaultG711();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++Encoding+++++++++++++++++++++++++++++++++++++++++++++++=");
        /**
         * ***********Encoding**************
         */
        Codec compressor = new Encoder();
        System.out.println("buffer before compression=" + bufferDefault711.getData().length);//320
        printoutBuffer(bufferDefault711.getData());
        long s = System.nanoTime();
        Frame frameCompressed = compressor.process(bufferDefault711);
        byte[] compressedByte = frameCompressed.getData();
        System.out.println("Frame compressed size in byte=" + compressedByte.length + "compressed array[---");//Frame compressed=160:

        //sr
        printoutBuffer(compressedByte);
        System.out.println("--]compressed array end");
        long f = System.nanoTime();
        long elapsedtime = (f - s);
        System.out.println("Duration in nanosecond=" + elapsedtime);// Duration=45126573
        double seconds = (double) elapsedtime / 1000000000.0;
        System.out.println("Duration in second=" + seconds);//Duration in second=0.048414073

        /**
         * ***********Decoding**************
         */
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++Decoding+++++++++++++++++++++++++++++++++++++++++++++++=");
        System.out.println(" decompression..");//320

        Codec decompressor = new Decoder();
        Frame frameDecompressed = decompressor.process(frameCompressed);
        System.out.println("frame decompressed =" + frameDecompressed.getData().length);//320
        printoutBuffer(frameDecompressed.getData());
        byte[] res = frameDecompressed.getData();
        for (int i = 0; i < srcDefault711.length; i++) {
            if (srcDefault711[i] != res[i]) {
                System.out.println("mismatch found at " + i);
            }
        }
    }

    /*
     Desc: it returns a G711 encoded bytes sample with fixed length of 160 byte
    
     */
    public byte[] getEncodedG711Defaultpayload(Frame bufferDefault711) throws Exception {
        //set up the buffer by 320 byte : digital values
        setUpDefaultG711();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++Encoding+++++++++++++++++++++++++++++++++++++++++++++++=");
        /**
         * ***********Encoding**************
         */
        Codec compressor = new Encoder();
        System.out.println("buffer before compression=" + bufferDefault711.getData().length);//320
        printoutBuffer(bufferDefault711.getData());
        long s = System.nanoTime();
        Frame frameEncoded = compressor.process(bufferDefault711);
        byte[] encodedByte = frameEncoded.getData();
        System.out.println("Frame compressed size in byte=" + encodedByte.length + "compressed array[---");//Frame compressed=160:
        //sr
        printoutBuffer(encodedByte);
        System.out.println("--]compressed array end");
        return encodedByte;

    }

    public void printoutBuffer(byte[] buff) {
        System.out.println("print out buffer:");
        for (int i = 0; i < buff.length; i++) {
            System.out.println("buff[" + i + "]=" + buff[i]);
        }
        System.out.println("End of print out buffer.");
    }

    public static void main(String[] args) throws Exception {
        AlawCodecTest test = new AlawCodecTest();
        //System.out.println("++++++++++++++++++++++++original test: buffer of 512 byte+++++++++++++++++++++++++++++++++++++++++++");
        //test.testCodec();

        System.out.println("++++++++++++++++++++++++ test using buffer of 320 byte and getting as output compressed:160 byte+++++++++++++++++++++++++++++++++++++++++++");
//test encoding and decoding g711        
//test.testCodecDefaultpayload();
        // encode a sample message to G711 codec , size is 160 byte
        Frame bufferDefault711 = Memory.allocate(320);//initializing an array of byte, giving the array size 320, values are zero
        byte[] encodedG711Defpayload = test.getEncodedG711Defaultpayload(bufferDefault711);
        /*TODO: send those byte thru Rtp/Udp session with speed 50 pps
        bandwidth per call = (1744 bits voice packet size) * 50 pps = 87.2 Kb/s
         1 packet per 20 ms

        */
    }
}
