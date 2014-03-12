package multiThreading;

/**SCJP-6
 * You can pass a single Runnable instance to multiple Thread objects, so that
 * the same Runnable becomes the target of multiple threads, as follows:
 * 
 * @author salim
 * Thread state: new -> runnable -> Running -> blocked -> terminated
 * 
 */
public class TestThreads {
	public static void main(String[] args) {
		/*****Test 1/2 Populating different thread name with sleep and reading thread states**********
		// Make one Runnable
		NameRunnable r = new NameRunnable();
		Thread foo = new Thread(r);
		Thread bar = new Thread(r);
		Thread bat = new Thread(r);
		System.out.println("jane thread Stat:" + bat.getState().toString());
		foo.setName("salim");
		bar.setName("daisy");
		bat.setName("jane");
		//Start the threads, call run() in parallel
		foo.start();
		bar.start();
		bat.start();
	
		/***/
		/*********Test 2/2---invoking RUN direclty test********/
		System.out.println("Test 2/2invoking RUN direclty");
		new NameRunnable().run();//no new thread is created and run() is runned by the current thread (main)
		//new NameRunnable().start();doesn't compile
		new Thread(new NameRunnable());//a new thread obj is created but not started
		new Thread(new NameRunnable()).run();//the new thread is not started,
		// and run() is executed by the current thread (main)
		new Thread(new NameRunnable()).start();//new thread started and the run() is invoked
		 
		/** Print out the state of Jane thread, break the loop when Jane is TERMINATED**/
//		while (true) {
//			System.out.println("jane thread Stat:" + bat.getState().toString());
//			if (!bat.isAlive()) {
//				System.out.println("Break--jane thread Stat:" + bat.getState().toString());
//				break;
//			}
//		}
	}
}