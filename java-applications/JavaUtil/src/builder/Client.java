package builder;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/******Effective Java 
		 * The builders setter methods return the builder itself so
		that invocations can be chained
		The Builder pattern simulates named optional parameters

In summary, the Builder pattern is a good choice when designing classes
whose constructors or static factories would have more than a handful of
parameters, especially if most of those parameters are optional. Client code is
much easier to read and write with builders than with the traditional telescoping
constructor pattern, and builders are much safer than JavaBeans.
		*/
		NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).
		calories(100).sodium(35).carbohydrate(27).build();
		System.out.println(cocaCola);
	}

}
