import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day6 {	

	public static void main(String[] args) {    	
		esercizio6_2();
	}
	
	/* Dire quante lettere sono comuni alle stringhe dei vari gruppi e sommare tali numeri*/
	public static void esercizio6_2(){
	
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es6.txt";
		List<String> input = Utility.readInput(path);
		// devo aggiungere un a capo che c'e' sul file ma che non viene letto 
		// e quindi non fa funzionare il mio ragionamento
		input.add("");
		
		int tot = 0;
		HashSet<Character> letters = new HashSet<Character>();
			
		for (int i = 0 ; i < input.size(); i++) {
			String s = input.get(i);
			
			//fine di un gruppo
			if (StringUtils.isBlank(s)) {
				tot+=letters.size();
				letters.clear();
				continue;
			}
			
			List<Character> chars = getStringChars(s);
			
			if (i==0 || StringUtils.isBlank(input.get(i-1))) { // aggiungo il primo di ogni lista
				letters.addAll(chars);
			} else {			
				letters.retainAll(chars); //fa l'intersezione tra insiemi
			}
		}
		
		System.out.println(tot);   	
	}
    
	/* Dire quante diverse lettere ci sono tra le stringhe dei vari gruppi e sommare tali numeri*/
	public static void esercizio6_1(){
    	
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es6.txt";
		List<String> input = Utility.readInput(path);
		// devo aggiungere un a capo che c'e' sul file ma che non viene letto 
		// e quindi non fa funzionare il mio ragionamento
		input.add("");
	
		int tot = 0;
		HashSet<Character> letters = new HashSet<Character>();
			
		for (String s : input) {
			
			//fine di un gruppo
			if (StringUtils.isBlank(s)) {
				tot+=letters.size();
				letters.clear();
				continue;
			}
		
			List<Character> chars = getStringChars(s);    			
			letters.addAll(chars);
		}
	
		System.out.println(tot);   	
	}
	
	public static List<Character> getStringChars(String s) {
		return s.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
	}
}
