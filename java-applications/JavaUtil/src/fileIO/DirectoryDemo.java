package fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DirectoryDemo {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/******
		 * Practice IO:: 1- create new directory and file then fille them 2-
		 * read from the existing file and directory 3- delete/rename existing
		 * file/dir
		 * **************/
		// createNewDirectoryandFile();
		// readExistingDirectoryandFile();
		//deleteRenameExistingFileDir();
		searchDirFile();
	}

	public static void createNewDirectoryandFile() throws IOException {
		File myDir = new File("./src/fileIO/myDir");
		System.out.println(myDir.mkdir());// exception if mkdir omitted upon
											// creating the file
		// adding file to the directory
		File f = new File(myDir, "child1.txt");
		f.createNewFile();
		PrintWriter pw = new PrintWriter(f);
		pw.println("Dear Mr XXX");
		pw.println("Following your request");
		pw.flush();
		pw.close();
	}

	public static void readExistingDirectoryandFile() throws IOException {
		// check files existance
		File existingDir = new File("./src/fileIO/myDir");
		System.out.println(existingDir.isDirectory());
		File exitingFile = new File(existingDir, "child1.txt");
		System.out.println(exitingFile.isFile());

		// read file contents
		FileReader rdr = new FileReader(exitingFile);
		BufferedReader br = new BufferedReader(rdr, 50);
		// read line by line
		String s;
		while ((s = br.readLine()) != null) {
			System.out.println(s);
		}
		br.close();
	}

	public static void deleteRenameExistingFileDir() throws IOException {
		// make new dir
		File delDir = new File("./src/fileIO/delDir");
		delDir.mkdir();
		// make new files in the dir
		File delFile1 = new File(delDir, "delFile1");
		delFile1.createNewFile();
		File delFile2 = new File(delDir, "delFile2");
		delFile2.createNewFile();
		// delete the file1
		delFile1.delete();
		// attemping to delete the Directory
		System.out.println("delDir is " + delDir.delete());// false , cannot
															// delete a DIR not
															// empty

		// renaming
		File newFile = new File(delDir, "newFile.txt"); // a new object newFile
		delFile2.renameTo(newFile);
		File newDir = new File("./src/fileIO/newDir"); // rename directory
		delDir.renameTo(newDir);
	}

	public static void searchDirFile() throws IOException {
		// make new dir
		File searchThis = new File("./src/fileIO/searchThis");
		searchThis.mkdir();
		// make new files in the dir
		File file1 = new File(searchThis, "file1.txt");
		file1.createNewFile();
		File file2 = new File(searchThis, "file2.txt");
		file2.createNewFile();
		File subDir = new File(searchThis,"subDir");
		subDir.mkdir();
		
		//extract all files and DIR
		String[] files = new String[100];
		files = searchThis.list();
		for(String s: files)
		{
			System.out.println("founbd "+s);			
		}
	}
}
