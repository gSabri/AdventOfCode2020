import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day18 {
	
	public static void main(String[] args) {
		esercizio18_2();
	}
	
	public static void esercizio18_2(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es18.txt";
		List<ArrayList<String>> input = Utility.readInput(path).stream().map(s -> new ArrayList<String>(Arrays.asList(s.split("\\s+")))).collect(Collectors.toList());
				
		long result = 0;
		for (ArrayList<String> exp : input) {
			
			boolean changes = true;
			while (changes) {
				simplify(exp); // calcola le parentesi
				changes = refactorExp(exp);	//aggiunge parentesi alle somme 
			}
			result+=calculate(exp);
		}
		
		System.out.println(result);
	}
	
	public static void simplify(List<String> exp) {
		while (exp.lastIndexOf("(")!=-1) {
			int startExp = exp.lastIndexOf("(");			
			int endExp = startExp+1+exp.subList(startExp+1, exp.size()).indexOf(")");
			
			List<String> sub = exp.subList(startExp+1,endExp);
			
			boolean changes = true;
			while (changes) { // lavoro sulla prima parentesi
				changes = refactorExp(sub); // aggiunge parentesi attorno alle somme
				simplify(sub); // semplifica le eventuali nuove sottoparentesi
				endExp = startExp+1+exp.subList(startExp+1, exp.size()).indexOf(")");
			}
			
			// calcola e sostituisce valore singola parentesi, ora con solo * o solo +			
			long expResult = calculate(exp.subList(startExp+1,endExp));
			
			//tolgo espressione e la sostituisco con il valore risultante
			exp.subList(startExp,endExp+1).clear();
			exp.add(startExp, String.valueOf(expResult));
		}
	}
	
	// se aggiungo parentesi ritorno true
	public static boolean refactorExp(List<String> exp) {
		// rielaboro espressione per piazzare parentesi intorno alle addizioni
		boolean changes = false;
		for (int i = 0; i<exp.size();) {
			if ("+".equals(exp.get(i))) {
				
				ArrayList<String> replace = new ArrayList<String>();				
				replace.add("(");
				
				int endExp = 0;				
				if (exp.subList(i+1,exp.size()).indexOf("*")!=-1) {
					endExp = i+exp.subList(i,exp.size()).indexOf("*");						
				} else {
					endExp = exp.size();
				}
				
				replace.addAll(exp.subList(i-1, Integer.min(exp.size(), endExp)));
				replace.add(")");
				
				// se repalce e lungo come espressione iniziale e parentesi
				// sono solo somme, non ho cambiato nulla e posso procedere
				if (replace.size()==exp.size()+2) {
					changes = false;
					break;
				}
							
				exp.subList(i-1, Integer.min(exp.size(), endExp)).clear();
				exp.addAll(i-1, replace);
				
				i += replace.size()-1;
				changes = true;
			} else i++;
		}
		return changes;
	}
	
	public static void esercizio18_1(){
	
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es18.txt";
		List<ArrayList<String>> input = Utility.readInput(path).stream().map(s -> new ArrayList<String>(Arrays.asList(s.split("\\s+")))).collect(Collectors.toList());
		
		
		long result = 0;
		for (ArrayList<String> exp : input) {
			while (exp.lastIndexOf("(")!=-1) {
				int startExp = exp.lastIndexOf("(");			
				int endExp = startExp+1+exp.subList(startExp+1, exp.size()).indexOf(")");
				
				long expResult = calculate(exp.subList(startExp+1,endExp));
				
				//tolgo espressione e la sostituisco con il valore risultante
				exp.subList(startExp,endExp+1).clear();
				exp.add(startExp, String.valueOf(expResult));
			}
			result+=calculate(exp);
		}
		
		System.out.println(result);
	}
	
	public static long calculate(List<String> exp) {
		long res = Long.parseLong(exp.get(0));
		for (int i=1; i<exp.size(); i+=2) {
			String op = exp.get(i);
			long val = Long.parseLong(exp.get(i+1));
			switch (op) {
				case "+":
					res+=val;
					break;
				case "*":
					res*=val;
					break;
			}
		}
		return res;
	}
}
