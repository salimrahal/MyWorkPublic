package fileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintWriterDemo {

	/**
	 * 
	 * Although using System.out to write to the console is still permissible
	 * under Java, its use is recommended mostly for debugging purposes or for
	 * sample programs, such as those found in this book. For real-world
	 * programs, the recommended method of writing to the console when using
	 * Java is through a PrintWriter stream. PrintWriter is one of the
	 * character-based classes. Using a character-based class for console output
	 * makes it easier to internationalize your program.
	 * 
	 * PrintWriter defines several constructors. The one we will use is shown
	 * here:
	 * 
	 * PrintWriter(OutputStream outputStream, boolean flushOnNewline)
	 * 
	 * Here, outputStream is an object of type OutputStream, and flushOnNewline
	 * controls whether Java flushes the output stream every time a newline
	 * ('\\n') character is output. If flushOnNewline is true, flushing
	 * automatically takes place. If false, flushing is not automatic.
	 * 
	 * PrintWriter supports the print( ) and println( ) methods for all types
	 * including Object. Thus, you can use these methods in the same way as they
	 * have been used with System.out. If an argument is not a simple type, the
	 * PrintWriter methods call the object's toString( ) method and then print
	 * the result.
	 * 
	 * To write to the console by using a PrintWriter, specify System.out for
	 * the output stream and flush the stream after each newline. For example,
	 * this line of code creates a PrintWriter that is connected to console
	 * output:
	 * 
	 * PrintWriter pw = new PrintWriter(System.out, true);
	 * 
	 * The following application illustrates using a PrintWriter to handle
	 * console output:
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//File f = createFile();
		//PrintWriter pw = printWriterDemoUsingFile(f);
               PrintWriter pw = printWriterAppendtest();
               pw.println("ehhhehhehh333333333333333\n");
               pw.flush();
               pw.close();
//		deleteFile(f);
//		createDir("tmpDir1");
		//printWriterDemoUsingFile();
	}

	public static File createFile() throws Exception {
		File f = new File("./src/fileIO/myNewFile.txt");//just create file obj
		f.createNewFile();
		return f;
	}
	public static void createDir(String tmpDir) throws Exception {
		File f = new File("./src/fileIO/"+tmpDir);
		System.out.println(f.mkdir());
	}
	public void printWriterDemo() {
		boolean autoFlush = true;
		PrintWriter pw = new PrintWriter(System.out, autoFlush);
		pw.println("from printwriter");
		pw.println("This is a string");
		//pw.format()
		int i = -7;
		pw.println(i);
		double d = 4.5e-7;
		pw.println(d);
		pw.flush();
		pw.close();
	}
	public static PrintWriter printWriterDemoUsingFile(File f) throws Exception {
		PrintWriter pw = new PrintWriter(f);//creating file on the hard disk
		String s = "text from the PrintWriter.Write()\n";
		pw.write(s);	
		pw.print("using println");
		//pw.flush(); //using flush is mandatory
		//pw.close();
                return pw;
	}
        public static PrintWriter printWriterAppendtest() throws Exception {
		 PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./src/fileIO/fileTmp4.txt", true)));
                 out.print("tmp4 is created by PrintWriter NB:file.createNewfile was not invoked");
                 return out;
	}
	/**
	 * creating file using Printwriter
	 * @throws Exception
	 */
	public static void printWriterDemoUsingFile() throws Exception {
		File file = new File ("./src/fileIO/fileTmp3.txt");
		PrintWriter pw = new PrintWriter(file);//creating file on the hard disk
		String s = "text from the PrintWriter.Write()\n";
		pw.write(s);	
		pw.print("tmp3 is created by PrintWriter NB:file.createNewfile was not invoked");
		pw.flush(); //using flush is mandatory
		pw.close();
	}
	public static void deleteFile(File f)
	{
	f.delete();
	}
}
