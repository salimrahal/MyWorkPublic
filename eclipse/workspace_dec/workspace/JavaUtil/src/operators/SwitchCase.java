package operators;

public class SwitchCase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		switchTest(2);
	}
	
	public static void switchTest(int i){
		final int j=2;
		switch (i) {
        case j:
        	System.out.println("case 2 - final variable");
			break;
		default:
			break;
		case 1+2:
			System.out.println(" case 3");
			break;
		}
	}

}
