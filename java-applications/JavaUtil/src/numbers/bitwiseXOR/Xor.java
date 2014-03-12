package numbers.bitwiseXOR;

public class Xor {

	/**
	 * @param args
	 * The bitwise XOR "^" operator  produces 1 if both of the bits in its operands are different. 
	 * However, if both of the bits are same then this operator produces 0.
	 *  Moreover if both of the bits are 1 i.e. 1^1 then also it produces 1.

In the program code given below, 
the corresponding bits of both operands are 1 and 0, hence we get 1 as output because the bits are different. 
	1 ^ 0 = 1
	0 ^ 0 = 0
	1 ^ 1 = 0
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.out.println(" ^ XOR operator");
		  int x = 1 ^ 0;
		  System.out.println("1 ^ 0 = " + x);
		  int y = 0 ^ 0;
		  System.out.println("0 ^ 0 = "+y);
		  int z = 1 ^ 1;
		  System.out.println("1 ^ 1 = "+z);
		  int w = 11 ^ 2;
		  System.out.println("11 ^ 2 = "+w);
		  int a = 2 ^ 11;
		  System.out.println("2 ^ 11 = "+a);
		  }
	}
