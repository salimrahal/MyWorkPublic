package methodsCommonAllObjects;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer intObj =1000;
		Integer intObj1=1000;
		String res;
		if(intObj.equals(intObj1))
		{
			System.out.println("yess");
		}else
		{
			System.out.println("noo");
		}
		Object obj=1000;
		Object obj1=1000;
		if(obj.equals(obj1))
		{
			System.out.println("yess");
		}else
		{
			System.out.println("noo");
		}
	}

}
