package multiThreading.synchronizingCode.boTest;

import multiThreading.synchronizingCode.*;

/**
 *
 * @author salim is this example we see concurrent access on the same Account
 * instance!!
 *
 */
public class AccountDanger implements Runnable {

    private Account acc;
    AccountBo accbo;

    public AccountDanger(Account acc, AccountBo accbo) {
        this.acc = acc;
        this.accbo = accbo;
    }

    public static void main(String[] args) {
        Account acc = new Account();
        AccountBo accbo = new AccountBo(acc);
        AccountDanger r = new AccountDanger(acc, accbo);
        AccountDanger r2 = new AccountDanger(acc, accbo);
        Thread one = new Thread(r);
        Thread two = new Thread(r2);
        one.setName("Fred");
        two.setName("Lucy");
        one.start();
        two.start();
    }

    public void run() {
        for (int x = 0; x < 5; x++) {
            accbo.makeWithdrawal(10);
            if (acc.getBalance() < 0) {
                System.out.println("account is overdrawn!");
            }
        }
    }

    /**
     * locked or synchronized method:: so that only one thread at a time can be
     * executing that code NB: Only methods (or blocks) can be synchronized, not
     * variables or classes
     *
     * @param amt
     */

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
