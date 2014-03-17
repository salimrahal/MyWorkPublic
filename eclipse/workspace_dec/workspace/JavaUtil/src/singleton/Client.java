package singleton;

public class Client {

	public static void main(String[] args) {
	
    Elvis elvisSole = Elvis.getInstance();
	System.out.println(elvisSole);
	Elvis elvisSole1 = Elvis.getInstance();
	System.out.println(elvisSole1);
	
	
	ElvisEnum elvisEnumSole = ElvisEnum.INSTANCE;
	System.out.println(elvisEnumSole);
	
	/**output
	 * singleton.Elvis@19821f
       singleton.Elvis@19821f
	   INSTANCE
	 */
	}
}
