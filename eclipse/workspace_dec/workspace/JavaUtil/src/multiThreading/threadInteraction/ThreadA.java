package multiThreading.threadInteraction;

/**
 * SCJP-6 This program contains two objects with threads: ThreadA contains the
 * main thread and ThreadB has a thread that calculates the sum of all numbers
 * from 0 through 99. As soon as line 19 calls the start() method, ThreadA will
 * continue with the next line of code in its own class, which means it could
 * get to line 11 before ThreadB has finished the calculation. To prevent this,
 * we use the wait() method in line 23. Notice in line 20 the code synchronizes
 * itself with the object b—this is because in order to call wait() on the
 * object, ThreadA must own a lock on b . For a thread to call wait() or
 * notify(), the thread has to be the owner of the lock for that object. When
 * the thread waits, it temporarily releases the lock for other threads to use,
 * but it will need it again to continue execution.
 */
class ThreadA {
	public static void main(String[] args) {
		ThreadB b = new ThreadB();
		b.start();
		/** this has the lock on object b */
		//synchronized (b) {
			try {
				System.out.println("Waiting for b to complete...");
				b.wait();//the thread releases the lock and waits
			} catch (InterruptedException e) {
			}
			System.out.println("Total is: " + b.total);
	//	}
	}
	/*couldnt synchronize maine, runtime exception
	 * public static synchronized void main(String[] args) throws
InterruptedException {
Thread t = new Thread();
t.start();
System.out.print("X");
t.wait(10000);
System.out.print("Y");
} */
	
}