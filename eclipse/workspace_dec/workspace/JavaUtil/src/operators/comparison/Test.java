package operators.comparison;

public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//compareStrings();
		compareObject();
	}
	public static void compareStrings()
	{
		String a, b, c, d;
		a = "Hello1234";
		b = "Hello" + String.valueOf(1234);
		c = "Hello" + "1234";
		
		d = new String(
				new char[] { 'H', 'e', 'l', 'l', 'o', '1', '2', '3', '4' });

		System.out.print(a == b);
		System.out.print(" ");
		System.out.print(a.equals(b));
		System.out.print(" ");
		System.out.print(a == c);
		System.out.print(" ");
		System.out.print(a.equals(c));
		System.out.print(" ");
		System.out.print(a == d);
		System.out.print(" ");
		System.out.print(a.equals(d));
		System.out.print(" ");
	}
	
	/**
	 * Equals
	 * resultat:
	 * false because are two diff instance
	 * true
	 * true
	 */
	public static void compareObject(){
		class ObjT1{
			int i;
			public ObjT1(int i) {
				// TODO Auto-generated constructor stub
				this.i=i;
			}
		}
		class ObjT2{
			int i;
			public ObjT2(int i) {
				// TODO Auto-generated constructor stub
				this.i=i;
			}
		}
		ObjT1 o1= new ObjT1(1);
		ObjT1 o2 = new ObjT1(1);
		System.out.println(o1+"/"+o2);
		System.out.println("equal?"+o1.equals(o2));
		ObjT1 o3 = o1;
		System.out.println("equal?"+o1.equals(o3));
		System.out.println(o1+"/"+o3);
		o3.i = 4;
		System.out.println("equal?"+o1.equals(o3));
	    System.out.println("o1.i="+o1.i+"/o3.i="+o3.i);
	}
	
}
