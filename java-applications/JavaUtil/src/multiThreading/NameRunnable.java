package multiThreading;

import java.lang.management.ManagementFactory;

public class NameRunnable implements Runnable {

	@Override
	public void run() {
		for (int x = 1; x <= 4; x++) {
			System.out.println("Run by " + Thread.currentThread().getName()
					+ ", x is " + x);
			
			/**----------Using SLEEP--------------------
			 * using sleep() is the best way to help all threads get a chance to run!
			 * using sleep() to try to force the threads to alternate rather 
			 * than letting one thread dominate for any period of time.
			Here the output:
			jane thread Stat:NEW
			Run by salim, x is 1
			Run by daisy, x is 1
			Run by jane, x is 1
			Run by salim, x is 2
			Run by daisy, x is 2
			Run by jane, x is 2
			Run by salim, x is 3
			Run by jane, x is 3
			Run by daisy, x is 3
			Run by salim, x is 4
			Run by jane, x is 4
			Run by daisy, x is 4
			*/
			try {
				Thread.sleep(1000);//it puts the current running thread to sleep.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end for loop
	}
        
        public static void main(String[] args) throws InterruptedException {
		
		NameRunnable n = new NameRunnable();
                n.run();
	}

}
