package math;

public class EcartTypeTest {

	/**Propriétés de l'écart-type

    On n'utilise l'écart-type que pour mesurer la dispersion autour de la moyenne d'un ensemble de données.
    L'écart-type n'est jamais négatif.
    L'écart-type est sensible aux valeurs aberrantes. Une seule valeur aberrante peut accroître l'écart-type et, par le fait même, déformer le portrait de la dispersion.
    Dans le cas des données ayant approximativement la même moyenne, plus la dispersion est grande, plus l'écart-type est grand.
    L'écart-type est zéro si toutes les valeurs d'un ensemble de données sont les mêmes (parce que chaque valeur est égale à la moyenne).

    Quand on analyse des données normalement distribuées, on peut utiliser l'écart-type parallèlement à la moyenne pour calculer des intervalles de données.

    Si x barre = moyenne, S = écart-type et x = une valeur incluse dans l'ensemble de données, alors

    environ 68 % des données se situent à l'intérieur de l'intervalle :
    x barre - S < x < x barre+ S.
    environ 95 % des données se situent à l'intérieur de l'intervalle :
    x barre - 2S < x < x barre + 2S.
    environ 99 % des données se situent à l'intérieur de l'intervalle :
    x barre - 3S < x < x barre + 3S.

	 * @param args
	 */
	public static void main(String[] args) {
		//60 g, 56 g, 6l g, 68 g, 51 g, 53 g, 69 g, 54 g.
		double[] obsArr ={60, 56, 61, 68, 51, 53, 69, 54};
		double moyenne = calMoyenne(obsArr);
		System.out.println("Ecart Type="+calEcartType(obsArr, moyenne));
	}
	/**
	 * 
	 * @param observation
	 * @return moyenne
	 */
	public static double calMoyenne(double[] observation)
	{
		double sommeObs = 0;
		for(double obs : observation){			
			sommeObs = sommeObs + obs;			
		}	
		return sommeObs/observation.length;	
	}
	/**
	 *
	Les Etapes pour calculer l'ecart type:
    Calculez la moyenne.
    Soustrayez de chaque observation la moyenne.
    Calculez le carré de chacune des autres observations.
    Additionnez ces résultats au carré.
    Divisez ce total par le nombre d'observations (la variance, S2).
    Utilisez la racine carrée positive (écart-type, S).

	 * @param observation
	 * @param moyenne
	 * @return ecart type
	 */
	public static double calEcartType(double[] observation, double moyenne)
	{
		double diff = 0;
		double sommeCarre = 0;
		double variance = 0;
		double ecart = 0;
		
		for(double obs : observation)
		{
			diff = 	obs - moyenne;	
			sommeCarre = sommeCarre + Math.pow(diff, 2);
		}
		variance = sommeCarre/observation.length;
		ecart = Math.sqrt(variance);
		return ecart;
	}
}
