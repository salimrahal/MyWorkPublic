package oDesk;

import java.io.DataInputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
/**
 * The code will fail to compile
11 12
11 11
12 12
 * @author salim
 *
 */
public class IncrementTest {

	static int x = 10;

	public IncrementTest() {
		Bar b = new Bar();
		Bar b1 = new Bar();
		update(b);
		update(b1);
	}

	private void update(Bar bar) {
		//bar.x = x++;
		bar.x = ++x;
		System.out.println(bar.x);
	}

	public static void main(String args[]) {
		IncrementTest it = new IncrementTest();
}

	private class Bar {
		public int x = 10;
	}
	

	}
	
