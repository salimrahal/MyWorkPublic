package pack1;

public interface Context {

	boolean lookup(VariableExp var);
	void assign(VariableExp varExp, boolean val );	
}
