package multiThreading.scheduling;
/**
 * 
 * @author salim
 *
 */
public class MyRunnable implements Runnable {

	@Override
	public void run() {
		for (int x = 1; x <= 100; x++) {
			//Output a String every ten numbers
			if(x % 10 == 0)
			{
				System.out.println("Run by " + Thread.currentThread().getName()
						+ ", x is " + x);
			}
			try {
				/**
				 * ----------Using SLEEP-------------------- using sleep() is
				 * the best way to help all threads get a chance to run! using
				 * sleep() to try to force the threads to alternate rather than
				 * letting one thread dominate for any period of time.
				 *  Output::
				 *  Run by daisy, x is 10
					Run by salim, x is 10
					Run by jane, x is 10
					....................
					Run by daisy, x is 100
					Run by jane, x is 100
					Run by salim, x is 100

				 */
				Thread.sleep(1000);// it puts the current running thread to
									// sleep. We are pausing 1 sec between each number
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end for loop
	}
}
