package exceptions;

/**
 * finally clause should a
 * 
 * @author salim
 * 
 */

public class Y {
	public void foo() {
		try {
			b();
		} catch (Exception e) {
		} finally {
			ba();
		}
	}

	public void b() throws X {
		throw new X();
	}

	public void ba() throws RuntimeException {
		throw new RuntimeException();
	}
}
