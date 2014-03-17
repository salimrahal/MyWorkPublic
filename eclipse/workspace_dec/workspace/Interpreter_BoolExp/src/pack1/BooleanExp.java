/**
 * 
 */
package pack1;

/**
 * @author salim
 *
 */
public interface BooleanExp {

	public abstract boolean evaluate(Context context);
	public abstract BooleanExp replace(String var, BooleanExp boolExp);
	public abstract boolean copy();
	
}
