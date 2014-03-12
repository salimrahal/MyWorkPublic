package fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderDemo {

	/**
	 * @param args
	 * @throws IOException
	 *             FileReader
	 * 
	 *             The FileReader class creates a Reader that you can use to
	 *             read the contents of a file. Its two most commonly used
	 *             constructors are shown here:
	 * 
	 *             FileReader(String filePath) FileReader(File fileObj)
	 * 
	 *             Either can throw a FileNotFoundException. Here, filePath is
	 *             the full path name of a file, and fileObj is a File object
	 *             that describes the file.
	 * 
	 *             The following example shows how to read lines from a file and
	 *             print these to the standard output stream. It reads its own
	 *             source file, which must be in the current directory.
	 */
	// Demonstrate FileReader.
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File f = new File("./src/fileIO/FileReaderDemo.java");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr, 30);// 30 buffer size
		String s;
		while ((s = br.readLine()) != null) {
			System.out.println(s);
		}
		fr.close();// always close
		br.close();
	}

}
