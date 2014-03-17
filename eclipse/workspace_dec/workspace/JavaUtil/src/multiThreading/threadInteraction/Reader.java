package multiThreading.threadInteraction;

/**
 * SCJP-6 using wait and NotifyAll(): In this example, there is one class that
 * performs a calculation and many readers that are waiting to receive the
 * completed calculation. At any given moment many readers may be waiting. The
 * program starts three threads that are all waiting to receive the finished
 * calculation (lines 35-38), and then starts the calculator with its
 * calculation. Note that if the run() method at line 30 used notify() instead
 * of notifyAll(), only one reader would be notified instead of all the readers.
 * 
 * The methods wait() , notify(), and notifyAll() are methods of only
 * java.lang.Object, not of java.lang.Thread or java.lang.Runnable. Be sure you
 * know which methods are defined in Thread, which in Object, and which in
 * Runnable (just run(), so that’s an easy one). Of the key methods in Thread,
 * be sure you know which are static— sleep() and yield(), and which are not
 * static—join() and start(). Table 9-2 lists the key methods you’ll need to
 * know for the exam, with the static methods shown in italics.
 * 
 * @author salim
 * 
 */
class Reader extends Thread {
	Calculator c;

	public Reader(Calculator calc) {
		c = calc;
	}

	public void run() {
		synchronized (c) {
			try {
				System.out.println("Waiting for calculation...");
				c.wait();
			} catch (InterruptedException e) {
			}
			System.out.println("Total is: " + c.total);
		}
	}

	public static void main(String[] args) {
		Calculator calculator = new Calculator();
		System.out.println("before start "+calculator.getState());// state= NEW
		new Reader(calculator).start();
		new Reader(calculator).start();
		new Reader(calculator).start();
		calculator.start();
		System.out.println("after start "+calculator.getState());// state= RUNNABLE
	}
}
