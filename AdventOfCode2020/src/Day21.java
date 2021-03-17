import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day21 {
		
	public static void main(String[] args) {
		 esercizio21_1_2();
	}
	
	/**Each allergen is found in exactly one ingredient. Each ingredient contains zero or one allergen. 
	 * Allergens aren't always marked; when they're listed (as in (contains nuts, shellfish) after an ingredients list), 
	 * the ingredient that contains each listed allergen will be somewhere in the corresponding ingredients list.
	 * 
	 * However, even if an allergen isn't listed, the ingredient that contains that allergen could still be present.
	 * 
	 * Determine which ingredients cannot possibly contain any of the allergens in your list. 
	 * How many times do any of those ingredients appear?
	 * 
	 * Arrange the ingredients alphabetically by their allergen and separate them by commas to produce your canonical dangerous ingredient list.*/
	public static void esercizio21_1_2(){
		
		// leggo l'input
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es21.txt";
		List<String> input = Utility.readInput(path);	
		
		HashMap<String,List<List<String>>> map = new HashMap<String,List<List<String>>>();
		HashSet<String> allIng = new HashSet<String>();
		
		// salvo una mappa allergeni -> liste di ingredineti che lo contengono
		for (String s : input) {
			String ingredients = (s.split("\\("))[0];
			List<String> ingrArr = Arrays.asList(ingredients.split("\\s+"));
			String[] allergens = (s.split("\\("))[1].replace("contains","").replace(")","").split(",");
			
			for (String alg : allergens) {
				if (map.get(alg.trim())==null) {
					map.put(alg.trim(), new ArrayList<List<String>>());
				} 
				map.get(alg.trim()).add(ingrArr);				
			}
			
			allIng.addAll(ingrArr);
		}
		
		HashMap<String, String> decodification = new HashMap<String, String>();
		HashSet<String> matched = new HashSet<String>();
		int allSize = map.keySet().size();
		
		HashSet<String> possible = new HashSet<String>();
		
		while (decodification.size() < allSize) {
			
			for (String a : map.keySet()) {
				int countOk = 0;
				String dec = "";
				if (decodification.keySet().contains(a)) continue;
								
				List<String> l0 = map.get(a).get(0);
				
				for (String st : l0) {
					if (matched.contains(st)) continue;
					boolean found = true;
					for (int i = 1; i<map.get(a).size(); i++) {						
						if (!map.get(a).get(i).contains(st)) {
							found = false;
							break;							
						}
					}
					if (found) {
						countOk++;
						dec = st;
						possible.add(st);
					}
				}
				if (countOk == 1) {
					decodification.put(a, dec);
					matched.add(dec);
				}
			}			
		}
		
		allIng.removeAll(possible);
		int count =0;
		for (String s : input) {
			String ingredients = (s.split("\\("))[0];
			List<String> ingrArr = Arrays.asList(ingredients.split("\\s+"));
			
			for (String nm : allIng) {
				if (ingrArr.contains(nm)) count++;
			}			
		}
		
		System.out.println(count);	
		
		List<String> keys = new ArrayList<String>(decodification.keySet());
		Collections.sort(keys);
		
		String result = "";
		for(String key : keys){
			result+=decodification.get(key)+",";
		}
		
		System.out.println(result);			
	}
}
		
	