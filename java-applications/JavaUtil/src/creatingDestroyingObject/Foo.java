package creatingDestroyingObject;

//Finalizer Guardian idiom
public class Foo {
	// Sole purpose of this object is to finalize outer Foo object
	private final Object finalizerGuardian = new Object() {
		@Override
		protected void finalize() throws Throwable {
			// Finalize outer Foo object
		}
	};
	// Remainder omitted
	
//	// Manual finalizer chaining
//	@Override
//	protected void finalize() throws Throwable {
//		try {
//			// Finalize subclass state
//		} finally {
//			super.finalize();
//		}
//	}
}
