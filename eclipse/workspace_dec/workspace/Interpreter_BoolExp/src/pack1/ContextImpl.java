package pack1;

import java.util.HashMap;

public class ContextImpl implements Context{

	HashMap<VariableExp, Boolean> hashTable;
	
	public ContextImpl() {
		super();
		this.hashTable = new HashMap<VariableExp, Boolean>();
	}
	
	public boolean lookup(VariableExp var)
	{
		return (boolean)hashTable.get(var);
	}
	
	public void assign(VariableExp varExp, boolean val )
	{
		hashTable.put( varExp, val);
	}
}
