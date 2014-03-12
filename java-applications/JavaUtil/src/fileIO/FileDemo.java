package fileIO;

import java.io.File;

public class FileDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			boolean newFile = false;
			File file = new File("./src/fileIO/fileWrite1.txt");
			System.out.println(file.exists());//1st exec-false; 2nd exec-true
			newFile = file.createNewFile();
			System.out.println(newFile);//1st exec-true; 2nd exec-false
			System.out.println(file.exists());//1st exec-true; 2nd exec-true
		} catch (Exception e) {
		}
	}
	public static void createDir() throws Exception {
		//????????????
	}
}
