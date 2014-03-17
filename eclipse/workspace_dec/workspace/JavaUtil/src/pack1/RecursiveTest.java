package pack1;

public class RecursiveTest {
	
	  
	
	public static void main(String[] args)
	   {
		 MyClass.m.myMethod(1000);
	   }
	
	public static class MyClass
	{
		public static MyClass m=new MyClass();
	
		public MyClass(){
			System.out.println("23");
		}
		int i=0;
		
		public void myMethod(int i)
		{
			System.out.println("i="+i);
			System.out.println("this.i="+this.i);
//			if(i==6)
//			{
//				say("42");
//			}
//			else
//			{
//				say("10"+i);
//				myMethod(i);
//				this.i+=1;
//			}
			
		}
		
		private void say(String s)
		{
		System.out.println(s);
		}
	}
}
