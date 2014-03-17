package pack1;

public class VariableExp implements BooleanExp {
    String name= null;
	public VariableExp(String name) {
		super();
		this.name = name;
	}

	@Override
	public boolean evaluate(Context context) {
		// TODO Auto-generated method stub
		return context.lookup(this);
	}

	@Override
	public BooleanExp replace(String var, BooleanExp boolExp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean copy() {
		// TODO Auto-generated method stub
		return false;
	}

}
