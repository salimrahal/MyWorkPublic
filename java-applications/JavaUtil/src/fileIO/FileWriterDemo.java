package fileIO;

import java.io.*;

public class FileWriterDemo {

	/**
	 * http://www.java-samples.com/showtutorial.php?tutorialid=392 FileWriter
	 * 
	 * FileWriter creates a Writer that you can use to write to a file. Its most
	 * commonly used constructors are shown here:
	 * 
	 * FileWriter(String filePath) FileWriter(String filePath, boolean append)
	 * 
	 * FileWriter(File fileObj)
	 * 
	 * They can throw an IOException or a SecurityException. Here, filePath is
	 * the full path name of a file, and fileObj is a File object that describes
	 * the file. If append is true, then output is appended to the end of the
	 * file.
	 * 
	 * Creation of a FileWriter is not dependent on the file already existing.
	 * FileWriter will create the file before opening it for output when you
	 * create the object. In the case where you attempt to open a read-only
	 * file, an IOException will be thrown.
	 * 
	 * The following example is a character stream version of an example shown
	 * earlier when FileOutputStream was discussed. This version creates a
	 * sample buffer of characters by first making a String and then using the
	 * getChars( ) method to extract the character array equivalent. It then
	 * creates three files. The first, file1.txt, will contain every other
	 * character from the sample. The second, file2.txt, will contain the entire
	 * set of characters. Finally, the third, file3.txt, will contain only the
	 * last quarter.
	 */
	public static void main(String args[]) throws Exception {
		//fileWriterDemo();
		fileWriterDemoUsingBufferedWriter();
	}

	public static void fileWriterDemo() throws Exception {
		String source = "Now is the time for all good men\n"
				+ " to come to the aid of their country\n"
				+ " and pay their due taxes.";
		char buffer[] = new char[source.length()];
		// getChars(int srcBegin,int srcEnd,char[] dst,int dstBegin)
		source.getChars(0, source.length(), buffer, 0);
		FileWriter f0 = new FileWriter("./src/fileIO/file1.txt", true);
//		for (int i = 0; i < buffer.length; i += 2) {
//			f0.write(buffer[i]);
//		}
		f0.write(source);
		f0.flush();// flush before closing
		f0.close();// close file when done
		
		FileWriter f1 = new FileWriter("./src/fileIO/file2.txt");
		f1.write(buffer);
		f1.flush();// flush before closing
		f1.close();// close file when done
		
		FileWriter f2 = new FileWriter("./src/fileIO/file3.txt");
		/*
		 * f2.write(arg1,arg2,arg3) buffer - Buffer of characters off - Offset
		 * from which to start writing characters len - Number of characters to
		 * write
		 */
		f2.write(buffer, buffer.length / 4, buffer.length / 4);
		f2.flush();// flush before closing
		f2.close();// close file when done
	}

	public static void fileWriterDemoUsingBufferedWriter() throws IOException {
		String source = "Now is the time for all good men\\n"
				+ " to come to the aid of their country\\n"
				+ " and pay their due taxes.";
		char buffer[] = new char[source.length()];
		// getChars(int srcBegin,int srcEnd,char[] destination_Array,int
		// dstBegin)
		source.getChars(0, source.length(), buffer, 0);
		FileWriter fw = new FileWriter("./src/fileIO/fileUsingBuffered.txt",
				true);
		BufferedWriter bw = new BufferedWriter(fw, 50);
		bw.write(buffer);
		bw.newLine();// insert new line
		bw.write("Neww line");// insert new line
		bw.close();
		fw.close();
	}

}
