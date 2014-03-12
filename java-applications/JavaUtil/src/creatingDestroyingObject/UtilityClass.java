package creatingDestroyingObject;

/** Enforce Noninstantiability with Private constructor---
 * a class can be
made noninstantiable by including a private constructor:
 */


//Noninstantiable utility class
public class UtilityClass {
	// Suppress default constructor for noninstantiability
	private UtilityClass() {
		throw new AssertionError();
	}
	// Remainder omitted
	
	public static void invokeConstructByMistake()
	{
		new UtilityClass();
	}
}
