package multiThreading.synchronizingCode;

/**
 * 
 * @author salim
 * is this example we see concurrent access on the same Account instance!!
 * 
 */
public class AccountDanger implements Runnable {
	private Account acct = new Account();

	public static void main(String[] args) {
		AccountDanger r = new AccountDanger();
		Thread one = new Thread(r);
		Thread two = new Thread(r);
		one.setName("Fred");
		two.setName("Lucy");
		one.start();
		two.start();
	}

	public void run() {
		for (int x = 0; x < 5; x++) {
			makeWithdrawal(10);
			if (acct.getBalance() < 0) {
				System.out.println("account is overdrawn!");
			}
		}
	}

/**locked or synchronized method:: so that only one thread at a time can be executing that code
 * NB: Only methods (or blocks) can be synchronized, not variables or classes
 * @param amt
 */
	private synchronized void makeWithdrawal(int amt) {
		if (acct.getBalance() >= amt) {
			System.out.println(Thread.currentThread().getName()
					+ " is going to withdraw");
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
			}
			acct.withdraw(amt);
			System.out.println(Thread.currentThread().getName()
					+ " completes the withdrawal");
		} else {
			System.out.println("Not enough in account for "
					+ Thread.currentThread().getName() + " to withdraw "
					+ acct.getBalance());
		}
	}
	/* output:
	 * Fred is going to withdraw
Fred completes the withdrawal
Fred is going to withdraw
Fred completes the withdrawal
Fred is going to withdraw
Fred completes the withdrawal
Fred is going to withdraw
Fred completes the withdrawal
Fred is going to withdraw
Fred completes the withdrawal
Not enough in account for Lucy to withdraw 0
Not enough in account for Lucy to withdraw 0
Not enough in account for Lucy to withdraw 0
Not enough in account for Lucy to withdraw 0
Not enough in account for Lucy to withdraw 0

	 */
}
