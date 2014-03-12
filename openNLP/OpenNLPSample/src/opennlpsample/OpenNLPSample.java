/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opennlpsample;

import com.sun.media.sound.InvalidFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 *
 * @author salim
 */
public class OpenNLPSample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            sentenceDetect();
        } catch (InvalidFormatException ex) {
            Logger.getLogger(OpenNLPSample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpenNLPSample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    	public static void sentenceDetect() throws InvalidFormatException,
			IOException {
		String paragraph = "Hi. How are you? This is Mike.";
 
		// always start with a model, a model is learned from training data
		InputStream is = new FileInputStream("en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
 
		String sentences[] = sdetector.sentDetect(paragraph);
 
		System.out.println(sentences[0]);
		System.out.println(sentences[1]);
		is.close();
	}
}
