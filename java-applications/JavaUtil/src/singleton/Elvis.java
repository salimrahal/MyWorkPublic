package singleton;

//Singleton with static factory
public class Elvis {
	//the final indicates this field always contains the same object reference
	private static final Elvis INSTANCE = new Elvis();

	private Elvis() {
	}

	/**All calls to Elvis.getInstance return the same object reference, and no other
	Elvis instance will ever be created (with the same caveat mentioned above).
	**/
	public static Elvis getInstance() {
		return INSTANCE;
	}

	public void leaveTheBuilding() {
	}
}