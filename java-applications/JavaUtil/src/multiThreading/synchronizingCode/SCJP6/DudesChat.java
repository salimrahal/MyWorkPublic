package multiThreading.synchronizingCode.SCJP6;

public class DudesChat implements Runnable {

	static Dudes d;
/**Dudes d; it will throw a runtime exception, at line 23, d is not assigned to an object
**/
	public static void main(String[] args) {

		new DudesChat().go();
		Thread.currentThread();
		Thread.yield();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	void go() {
		d = new Dudes();
	    new Thread(new DudesChat()).start();

		new Thread(new DudesChat()).start();

	}

	public void run() {

		d.chat(Thread.currentThread().getId());

	}
}
