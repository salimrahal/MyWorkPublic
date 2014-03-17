package multiThreading;

public class Test {

	/** SCJP-6
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/************************ THREAD TEST 
		 *  SCJP-6: Thread is the "worker," and the Runnable is the "job" to be done
		 *  by passing the Runnable as argument to the thread we are telling him to execute our Runnable.run() instead of the thread run() method
		 * *******/
		Thread th = new Thread(new MyRunnable());// Pass your Runnable to the Thread
		th.setName("salim");
		th.start();//The thread moves from the new state to the runnable state.
		Thread th1 = new Thread(new MyRunnable());// Pass your Runnable to the Thread
		th1.start();
		System.out.println("Thread of main is: "+Thread.currentThread().getName());
		//goodSynch();
		
		/**The following code does not start a new thread of execution, because u r invoking run() directly*/
		//Thread t = new Thread();
		//t.run(); // Legal, but does not start a new thread
		
	}

}
