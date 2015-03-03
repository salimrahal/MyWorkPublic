package subclassing;

/*
 * Cat is a concret class class, this means that it should
 * implement all interface's methods
 */
public class Cat extends Animal{

	public static void main(String[] args)
	{	
	}
	
	public void createCate(){
		Cat cat = new Cat ();
		if(cat instanceof Animal)
		{
			System.out.println(getAnimalId());
		}
	}

	@Override
	public int moveAnimal() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//below method is only declared in the class not in intf.
	public int testmethod1()
	{
		return 0;
		
	}
	public void instanceofTest(Cat cat){
		//valid operator
		if(cat instanceof Animal){
			System.out.println("valid operation");
		}
		//Not valid:: incompatible condition operator
//		if(cat instanceof String){
//			System.out.println("yess");
//		}
		Short s = 1;
		String str="ss";
		if(s instanceof Number){			
		}
		//invalid operation
//		if(str instanceof Number){			
//		}
	}

    @Override
    public int moveAnimalV2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
