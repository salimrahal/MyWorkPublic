package subclassing;

public class Parent {

	int i;
	// static Factory methods
	public static Parent newInstance() {
		return new Parent();
	}

	public static GoodChild getGoodChild() {
		return new GoodChild();
	}

	public static void getInnerClass() {
		abstract class InnerClass extends Parent{
			
		}
	}

}
