package oDesk;
/**What could be the replacement of “//ABC” in the following code 
 * public void apple(String s, int i){} --ok
public int apple(int i, String s){} --No , dup method
public void apple(int i, String mystring){} --No , dup method
public void Apple(int i, String s) {} --Ok
 * */

public class MethodSignature {
	public static void main(String [] args){
	MethodSignature o = new MethodSignature();
//	o.
	}
	
	public void apple(int i, String s){}	
	//ABC
	public void apple(String myString ,int i ) {}
	//public String apple(int i, String ss){return "s";}
	public String apple(int i, String s, int x){return "s";}
	public void Apple(int i, String s) {}
}
