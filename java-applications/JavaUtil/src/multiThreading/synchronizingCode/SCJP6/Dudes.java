package multiThreading.synchronizingCode.SCJP6;
/**Objectives: Synchronization test and throw a null pointer exception in 
 * case a non static object var is used without assigned to an instance object.
 * And given these two fragments:
I. synchronized void chat(long id) {
II. void chat(long id) {
When fragment I or fragment II is inserted at line 5, which are true? (Choose all that apply.)
A. An exception is thrown at runtime
B. With fragment I, compilation fails
C. With fragment II, compilation fails
D. With fragment I, the output could be yo dude dude yo
E. With fragment I, the output could be dude dude yo yo
F. With fragment II, the output could be yo dude dude yo

Answer:
✓ F is correct. With fragment I, the chat method is synchronized, so the two threads can't

swap back and forth. With either fragment, the first output must be yo.

 A, B, C, D, and E are incorrect based on the above. (Objective 4.3)
 * @author salim
 *
 */
	class Dudes {
		static long flag = 0;

		// insert code here
		//synchronized void chat(long id) {
			void chat(long id) {
		if(flag == 0) flag = id;

		for(int x = 1; x < 3; x++) {

		if(flag == id) System.out.print("yo ");

		else System.out.print("dude ");

		}

		}
 }
