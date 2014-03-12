package fileIO.serializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeDog {

	/** SCJP-6
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Collar collar = new Collar(3);
		Dog dog = new Dog(collar, 12);
		System.out.println("before: collar size is "+ dog.getCollar().getCollarSize());
		FileOutputStream fs = new FileOutputStream("./src/fileIO/serializable/outSer.ser");
		ObjectOutputStream os = new ObjectOutputStream(fs);
		os.writeObject(dog);
		os.close();

		
		//desrialize
		FileInputStream fi = new FileInputStream("./src/fileIO/serializable/outSer.ser");
		ObjectInputStream ois = new ObjectInputStream(fi);
		try {
			Dog dogIn = (Dog) ois.readObject();
			System.out.println("after: collar size is "+ dogIn.getCollar().getCollarSize());
			ois.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
