import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day16 {
	
	public static void main(String[] args) {
		List<List<Integer>> valid = esercizio16_1();
		esercizio16_2(valid);
		// esercizio16_2_perm(valid); le prova tutte
	}
		
	public static void esercizio16_2(List<List<Integer>> valid){
		
		int[] my = {61,151,137,191,59,163,89,83,71,179,67,149,197,167,181,173,53,139,193,157};
		
		String pathSett = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es16_settings.txt";
		List<String> settings = Utility.readInput(pathSett);
		
		HashMap<String,HashSet<Integer>> sets = new HashMap<String,HashSet<Integer>>();
		
		// costruisco gli insiemi dei valori validi
		for (String s : settings){
			HashSet<Integer> values = new HashSet<Integer>();			
			String type = s.split(":")[0].trim();
			String intervals = s.split(":")[1];
			
			String first = intervals.split("or")[0].trim();
			String sec = intervals.split("or")[1].trim();
			
			int min1 = Integer.parseInt(first.split("-")[0]);
			int max1 = Integer.parseInt(first.split("-")[1]);
			int min2 = Integer.parseInt(sec.split("-")[0]);
			int max2 = Integer.parseInt(sec.split("-")[1]);
			
			for (int j=min1; j<=max1; j++) {
				values.add(j);
			}
			for (int k=min2; k<=max2; k++) {
				values.add(k);
			}
			sets.put(type,values);
		}
		
		ArrayList<Integer> checkedVal =  new ArrayList<Integer>();
		ArrayList<String> checkedKey =  new ArrayList<String>();
		long result = 1;
		
		while (checkedVal.size()<my.length) { // finche' non le ho trovate tutte univocamente
			
			for (String key : sets.keySet()) {
				if (checkedKey.contains(key)) continue; //se posizione gia assegnata skippo
				HashSet<Integer> values = sets.get(key);
				int countColOk = 0;
				int myOkVal = 0;
				
				for (int i = 0; i<my.length; i++) {					
					int myVal = my[i];
					if (checkedVal.contains(myVal)) continue; //se valore gia assegnato skippo
					
					if (checkColumn(values,key,valid,my,i)) {
						countColOk++;
						myOkVal = myVal;
					}
				}
				
				if (countColOk == 1) { // se la colonna matcha una sola serie di valori e' lei 
					checkedVal.add(myOkVal); // aggiungo il mio valore da quelli da skippare
					checkedKey.add(key); // aggiungo la colonna tra quelle da skippare
									
					if (key.startsWith("departure")) {
						result *= myOkVal;
					}
				}
			}
		}
		
		System.out.println(result);			
	}
	
	public static boolean checkColumn(HashSet<Integer> set, String key, List<List<Integer>> valid, int[] my, int i) {
		
		int myVal = my[i];
		if (!set.contains(myVal)) return false; //se mio non va bene
		else { // se si controllo altri ticket
			for (List<Integer> l : valid) {
				if (!set.contains(l.get(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*public static void esercizio16_2_perm(List<List<Integer>> valid){
		
		int[] my = {61,151,137,191,59,163,89,83,71,179,67,149,197,167,181,173,53,139,193,157};
		
		String pathSett = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es16_settings.txt";
		List<String> settings = Utility.readInput(pathSett);
		
		HashMap<String,HashSet<Integer>> sets = new HashMap<String,HashSet<Integer>>();
		
		// costruisco gli insiemi dei valori validi
		for (String s : settings){
			HashSet<Integer> values = new HashSet<Integer>();			
			String type = s.split(":")[0].trim();
			String intervals = s.split(":")[1];
			
			String first = intervals.split("or")[0].trim();
			String sec = intervals.split("or")[1].trim();
			
			int min1 = Integer.parseInt(first.split("-")[0]);
			int max1 = Integer.parseInt(first.split("-")[1]);
			int min2 = Integer.parseInt(sec.split("-")[0]);
			int max2 = Integer.parseInt(sec.split("-")[1]);
			
			for (int j=min1; j<=max1; j++) {
				values.add(j);
			}
			for (int k=min2; k<=max2; k++) {
				values.add(k);
			}
			sets.put(type,values);
		}
		
		int n = settings.size();
		int[] indexes = new int[n];
		for (int i = 0; i < settings.size(); i++) {
		    indexes[i] = 0;
		}
		
		String[] keys = new String[n];
	    sets.keySet().toArray(keys);
		
		boolean found = checkMatch(sets, keys, valid, my);		
		
		int i = 0;
		while (i < n && !found) {
		    if (indexes[i] < i) {
		        swap(keys, i % 2 == 0 ?  0: indexes[i], i);
		        found = checkMatch(sets, keys, valid, my);
		        indexes[i]++;
		        i = 0;
		    } else {
		        indexes[i] = 0;
		        i++;
		    }
		}
		for(int j = 0; j<keys.length; j++){
			System.out.println(keys[j]);
		}
	}
	
	public static boolean checkMyMatch(HashMap<String,HashSet<Integer>> sets, String[] keys, int[] my){
		
		for (int k = 0; k<keys.length; k++) {
			HashSet<Integer> values = sets.get(keys[k]);
			
			for (int i = 0; i<my.length; i++) {
				if (!values.contains(my[i])) return false;
			}
		}
		
		return true;
	}
	
	public static boolean checkMatch(HashMap<String,HashSet<Integer>> sets, String[] keys, List<List<Integer>> valid, int[] my){
		
		if (checkMyMatch(sets, keys, my)) {
		
			for (int k = 0; k<keys.length; k++) {
				HashSet<Integer> values = sets.get(keys[k]);
				
				for (List<Integer> l : valid) {
					if (!values.contains(l.get(k))) return false;
				}
			}
		
			return true;
		} 
		return false;
	}
	
	private static void swap(String[] input, int a, int b) {
		String tmp = input[a];
	    input[a] = input[b];
	    input[b] = tmp;
	}*/
		
	/** It doesn't matter which position corresponds to which field; 
	 * you can identify invalid nearby tickets by considering only whether tickets contain values that are not valid for any field. 
	 * Adding together all of the invalid values produces your ticket scanning error rate.
	 * 
	 * Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate? */
	public static List<List<Integer>> esercizio16_1(){
		
		//int[] my = {61,151,137,191,59,163,89,83,71,179,67,149,197,167,181,173,53,139,193,157};
		
		String pathSett = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es16_settings.txt";
		List<String> settings = Utility.readInput(pathSett);
		
		//potrei appiattire la lista per ciclare meno dopo ma nin ci riesco e probabilmente mi serve input cosi' per dopo
		String pathNearby = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es16_nearby.txt";
		List<List<Integer>> nearby = (List<List<Integer>>) Utility.readInput(pathNearby).stream()
				.map(s-> Arrays.asList(s.split(",")).stream()
						.map(String::trim)
						.map(Integer::parseInt)
						.collect(Collectors.toList()))
				.collect(Collectors.toList());
		
		HashSet<Integer> values = new HashSet<Integer>();
		int result = 0;
		
		// per pt2
		List<List<Integer>> valid = new ArrayList<List<Integer>>();
		
		// costruisco l'insieme dei valori validi
		for (String s : settings){
			String intervals = s.split(":")[1];
			
			String first = intervals.split("or")[0].trim();
			String sec = intervals.split("or")[1].trim();
			
			int min1 = Integer.parseInt(first.split("-")[0]);
			int max1 = Integer.parseInt(first.split("-")[1]);
			int min2 = Integer.parseInt(sec.split("-")[0]);
			int max2 = Integer.parseInt(sec.split("-")[1]);
			
			for (int j=min1; j<=max1; j++) {
				values.add(j);
			}
			for (int k=min2; k<=max2; k++) {
				values.add(k);
			}
		}
		
		for (List<Integer> l : nearby) {
			boolean validNearby = true; // per pt2
			for (int in : l) {
				if (!values.contains(in)) {
					result+=in;
					validNearby = false; // per pt2
				}
			}
			// per pt2
			if (validNearby) valid.add(l);
		}
				
		System.out.println(result);
		
		return valid; // per pt2
	}
}
