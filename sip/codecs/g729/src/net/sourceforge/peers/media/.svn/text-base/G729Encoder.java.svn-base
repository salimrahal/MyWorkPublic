package net.sourceforge.peers.media;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import net.sourceforge.peers.Logger;
import net.sourceforge.peers.media.g729.Bits;
import net.sourceforge.peers.media.g729.BufferUtils;
import net.sourceforge.peers.media.g729.CircularBuffer;
import net.sourceforge.peers.media.g729.CodLD8K;
//import net.sourceforge.peers.media.g729.Frame;
import net.sourceforge.peers.media.g729.LD8KConstants;
import net.sourceforge.peers.media.g729.PreProc;
import net.sourceforge.peers.media.g729.Util;

public class G729Encoder extends Encoder{

	CodLD8K encoder = new CodLD8K();
    PreProc preProc = new PreProc();
    int prm[] = new int[LD8KConstants.PRM_SIZE];
    short serial[] = new short[LD8KConstants.SERIAL_SIZE];
    
    CircularBuffer circularBuffer = new CircularBuffer(320);
	
	public G729Encoder(PipedInputStream rawData, PipedOutputStream encodedData,
            boolean mediaDebug, Logger logger, String peersHome) {
        super(rawData, encodedData, mediaDebug, logger, peersHome);
        preProc.init_pre_process();
        encoder.init_coder_ld8k();
    }
	
	@Override
	public byte[] process(byte[] data) {
//   public byte[] processOLd(byte[] data) {
		
		
		//System.out.println("data.len = "+data.length);
		//byte[] frameData = input.getData();
		
		circularBuffer.addData(data);
//		if(data.length == 64){
//                    System.out.println("data 64 = "+data.toString());
//                    return new byte[0];
//                }
		/*else
		{
			System.out.println("data = "+data.toString());
		}*/
		
		int frameSize = 2 * LD8KConstants.L_FRAME;
		byte[] speechWindow =  circularBuffer.getData(2*frameSize);
		//byte[] speechWindow = data;
		//byte[] speechWindow = inputBytes;
        byte[] resultingBytes = null;
        
        if (speechWindow == null) {
            resultingBytes = new byte[0]; // No data available right now, send
            System.out.println("Speech window null");
        // empty buffer
        } else {
        	System.out.println("speechWindow = "+speechWindow.toString());
            // Process two frames = 20ms
            byte[] one = new byte[frameSize];
            byte[] two = new byte[frameSize];
            for (int q = 0; q < frameSize; q++) {
                one[q] = speechWindow[q];
                two[q] = speechWindow[q + frameSize];
            }
            one = PrivateProcess(one);
            two = PrivateProcess(two);
            
            if (one.length != two.length) {
                throw new RuntimeException(
                        "The two frames are not equal in size!");
            }
           
            resultingBytes = new byte[one.length + two.length];
            //resultingBytes = new byte[one.length];
            for (int q = 0; q < one.length; q++) {
                resultingBytes[q] = one[q];
                resultingBytes[q + one.length] = two[q];
            }
        }
        return resultingBytes;
		
		
	}
	
//    @Override
//    public byte[] process(byte[] media) {
	private byte[] PrivateProcess(byte[] media){
    	
		float[] new_speech = new float[media.length];
        short[] shortMedia = Util.byteArrayToShortArray(media);
        //System.out.println("short media "+shortMedia.length);
        if(shortMedia.length == 32) return new byte[0];
        for (int i = 0; i < LD8KConstants.L_FRAME; i++) {
            new_speech[i] = (float) shortMedia[i];
        }
        preProc.pre_process(new_speech, LD8KConstants.L_FRAME);

        encoder.loadSpeech(new_speech);
        encoder.coder_ld8k(prm, 0);

        //byte[] a = new byte[10];
        Bits.prm2bits_ld8k(prm, serial);
        // return a;
        return Bits.toRealBits(serial);
	}
	
	
	/**
     * Perform G729 encoding
     * 
     * @param input
     *            media
     * @return compressed media.
     */
    private byte[] PrivateProcess2( float[] media ) {

        preProc.pre_process( media, LD8KConstants.L_FRAME );

        encoder.loadSpeech( media );
        encoder.coder_ld8k( prm, 0 );

        Bits.prm2bits_ld8k( prm, serial );
        return Bits.toRealBits( serial );
    }

	
}
