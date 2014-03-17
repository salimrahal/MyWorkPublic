package fileIO.serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeCat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ObjectInputStream
		/**
		 * pseudo code: 
		 * 1- serialize Cat instance using ObjectOutputStream.write() 
		 * 2-de-serialize Cat instance using bjectInoutStream.read()
		 */
		Cat c = new Cat(); // 2
		try {
			//create a FileOutputStream to write the object to
			FileOutputStream fs = new FileOutputStream("testSer.ser");
			//wrapping the FileOutputStream in a ObjectOutputStream, so we ca use the WriteObj()
			ObjectOutputStream os = new ObjectOutputStream(fs);
			//WriteObjetc(c):serialize Cat Obj then it writes the serialized obj to the file
			os.writeObject(c);
			// 3
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileInputStream fis = new FileInputStream("testSer.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			c = (Cat) ois.readObject(); // 4
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
