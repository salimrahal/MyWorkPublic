package fileIO.serializable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Dog implements Serializable {
	// use transient Collar to avoid serializing the Collar (by using implement
	// Seriablizable)
	private transient Collar theCollar;// we can't serialize this
	private int dogSize;

	public Dog(Collar collar, int size) {
		theCollar = collar;
		dogSize = size;
	}

	public Collar getCollar() {
		return theCollar;
	}
/** SCJP-6*********Essential for transient *******************
By implementing these two
methods you're saying to the compiler: "If anyone invokes writeObject() or
readObject() concerning a Dog object, use this code as part of the read and write."
**********/
	//SCJP: final step Overriding the below method, and customizing the code
	private void writeObject(ObjectOutputStream os) {
		// throws IOException {
		try {
			os.defaultWriteObject();
			os.writeInt(theCollar.getCollarSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readObject(ObjectInputStream is) {
		try {//keep the same order as writeObject when reading the object then the Collar
			is.defaultReadObject();
			theCollar = new Collar(is.readInt());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
