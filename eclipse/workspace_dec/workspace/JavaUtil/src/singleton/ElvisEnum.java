package singleton;

//Enum singleton - the preferred approach
public enum ElvisEnum {
	INSTANCE;
	public void leaveTheBuilding() {
		System.out.println("building left");
	}
}
