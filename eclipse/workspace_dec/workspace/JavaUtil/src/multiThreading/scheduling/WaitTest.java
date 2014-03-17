package multiThreading.scheduling;
/*
 * It output: 1 2
 * and wait for infinity
 */
public class WaitTest {
	public static void main(String[] args) {
		System.out.print("1 ");
		synchronized (args) {
			System.out.print("2 ");
			try {
				args.wait();//wait till infinity
				//args.wait(2000);//wait with timeout 2000 ms, then it continue executing
			} catch (InterruptedException e) {
			}
		}
		System.out.print("3 ");
	}
}
