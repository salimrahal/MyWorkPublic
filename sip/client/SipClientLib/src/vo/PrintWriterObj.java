/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 * @author salim
 */
public class PrintWriterObj {
    //print writers

    static PrintWriter sipLogsPW = null;
    static PrintWriter errorLogsPW = null;
//the final indicates this field always contains the same object reference
    private static final PrintWriterObj INSTANCE = new PrintWriterObj();

    private PrintWriterObj(){
    }

    /**
     * All calls to PrintWriterObj.getInstance return the same object
     * reference, and no other PrintWriterObj instance will ever be created (with the
     * same caveat mentioned above).
	*
     */
    public static PrintWriterObj getInstance() {
        return INSTANCE;
    }

    public PrintWriter getSipLogsPW() {
        return sipLogsPW;
    }

    public void setSipLogsPW(PrintWriter sipLogsPW) {
        PrintWriterObj.sipLogsPW = sipLogsPW;
    }

    public PrintWriter getErrorLogsPW() {
        return errorLogsPW;
    }

    public void setErrorLogsPW(PrintWriter errorLogsPW) {
        PrintWriterObj.errorLogsPW = errorLogsPW;
    }
   
    public static void writePrintWriter(PrintWriter pw, String log) throws Exception {		
            if(pw != null){
                  pw.print(log+"\n");		
		pw.flush(); //using flush is mandatory: it writes from the writer to the file
		//pw.close();//close the ressource
            }
	}
      public static PrintWriter createSipLogPrintWriter() throws Exception {
		 //sipLogsPW = new PrintWriter(new BufferedWriter(new FileWriter("./logs/SIPDialogs"+new Date()+".txt", true)));
           sipLogsPW = new PrintWriter(new BufferedWriter(new FileWriter("SIPDialogs"+1+".txt", true)));
                 sipLogsPW.print("============Logs: "+new Date()+"=============\n");
                 return sipLogsPW;
	}
          public static PrintWriter createErrorPrintWriter() throws Exception {
		// errorLogsPW = new PrintWriter(new BufferedWriter(new FileWriter("./logs/errors"+new Date()+".txt", true)));
             errorLogsPW = new PrintWriter(new BufferedWriter(new FileWriter("errors"+1+".txt", true)));
                 errorLogsPW.print("============Logs: "+new Date()+"=============\n");
                 return errorLogsPW;
	}
          
      public static void closePrintWriter(PrintWriter pw){
          pw.close();
      }
      
       public static void closePrintWriters(){
          errorLogsPW.close();
          errorLogsPW.close();
      }
}
