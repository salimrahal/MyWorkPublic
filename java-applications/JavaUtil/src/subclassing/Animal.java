package subclassing;

/*
 * Animal is an abstract class, this means its not necessary for
 * Animal class to implement all the method of the interface
 * 
 */
public abstract class Animal implements AnimalIntf{
	String s[][] = new String[0][0];
	
        @Override
	public int getAnimalId(){
		int id = 1000; 
		return id;
	}
	public int testmethod()
	{
		return 0;
		
		
	}

}
