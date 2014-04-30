package multiThreading.synchronizingCode;

public class SyncTest implements Runnable {

	/**
	 * the syn block is more flexible the sync_method, but both has the same
	 * effect
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SyncTest sync = new SyncTest();
		Thread t1 = new Thread(sync);
		Thread t2 = new Thread(sync);
		t1.start();
		t2.start();
	}

	public void doStuff_mix() {
		System.out.println("not synchronized block, thread "
				+ Thread.currentThread().getName());
		synchronized (this) {
			System.out.println("synchronized");
		}
	}

	// both methods are equivalent
	public synchronized void doStuff_sync() {
		System.out.println("synchronized");
	}
	//invalid method return 
//	public void synchronized invalid_return() {
//		System.out.println("dsfgsdf");
//	}
	public void doStuff_syncBlovk() {
		synchronized (this) {
			System.out.println("synchronized");
		}
	}

/*
 * Synchronizing Static methods:
 * The static method locks on a Class
   instance while the non-static method locks on the this instance—these
   actions do not interfere with each other at all.
 */
	public static void doStuff_syncBlovk_static() {
		synchronized (SyncTest.class) {
			System.out.println("synchronized");
		}
	}
	public static void classMethod() {
		Class cl = null;
		try {
			cl = Class.forName("SyncTest");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (cl) {
			// do stuff
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		doStuff_mix();
	}
}
