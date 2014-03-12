package fileIO;

import java.io.Console;
import java.io.RandomAccessFile;
/** How to execute the console programe
 * 1- move using the terminal to the head package that contains the fileIO package
 * 2- write the following command: $ java package.Class_NAME
 *  e.g.: $/workspace_dec/workspace/JavaUtil/bin$ java fileIO.ConsoleDemo
 *  3- program will be executed...
 */
public class ConsoleDemo {
	public static void main(String[] args) {
		String name;
		Console c = System.console();// #1: get a Console
		char[] pw;
		pw = c.readPassword("%s", "pw: ");// #2: return a char[]
		for (char ch : pw)
			c.format("%c ", ch);
		c.format("\n");// #3: format output
		MyUtility mu = new MyUtility();
		while (true) {
			name = c.readLine("%s", "input?: ");// #4: return a String

			c.format("output: %s \n", mu.doStuff(name));
		}
	}
}

class MyUtility {// #5: class to test
	String doStuff(String arg1) {
		// stub code
		return "result is " + arg1;
	}
}
