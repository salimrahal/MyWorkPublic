package multiThreading.scheduling;

/**SCJP-6
 * You can pass a single Runnable instance to multiple Thread objects, so that
 * the same Runnable becomes the target of multiple threads, as follows:
 * 
 * @author salim
 * Thread state: new -> runnable -> Running -> blocked -> terminated
 * Schedule methods:
 * 1- Static Thread.Yield():: grant the execution from the current thread to another with same priority
 *           running ---> runnable
 * 2- thread.Join()         
 * 3- Static Thread.sleep
 */
public class TestThreads {
	public static void main(String[] args) {
		// Make one Runnable
		MyRunnable r = new MyRunnable();
		Thread foo = new Thread(r);
		Thread bar = new Thread(r);
		Thread bat = new Thread(r);
		System.out.println("jane thread Stat:" + bat.getState().toString());
		foo.setName("salim");
		bar.setName("daisy");
		bat.setName("jane");
		//Start the threads, call run() indirectly
		foo.setPriority(8);
		foo.start();
		bar.start();
		bat.start();
		
		/**JOIN(): "Join me (the current thread) to the end of bat, so that bat
		must finish before I (the current thread) can run again." 
		*/
		
			try {
				bat.join();
                                System.out.println("Class Main after bat(name jane) has finished:"+Thread.currentThread().getName());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/***** YIELD: Static method *****/
			Thread.yield();
	}
}