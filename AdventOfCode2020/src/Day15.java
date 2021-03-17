import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day15 {
	
	public static void main(String[] args) {
		esercizio15_2_opt();
	}
	
	/** What will be the 30000000th number spoken? */
	public static void esercizio15_2_opt(){
		
		int[] start = {14,3,1,0,9,5};
		
		HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		
		for (int i = 1; i<=start.length; i++) {
			map.put(start[i-1], i);
		}
		
		int prev = start[start.length-1];
		int prevPos = map.get(prev);
		
		for (int j = start.length; j<30000000; j++) {			
			
			int val = j - prevPos;
			
			if (map.get(val)!=null) {
				prevPos = map.get(val);
			} else prevPos = j+1;
			
			map.put(val,  j+1);		
			
			System.out.println((j+1)+" "+val);
		}
	}
	
	/*public static void esercizio15_2(){
		
		Integer[] start = {14,3,1,0,9,5};
		ArrayList<Integer> input = new ArrayList<Integer>(Arrays.asList(start));
		
		while (input.size()<30000000) {
			
			int check = input.get(input.size()-1);
			
			List<Integer> sublist = input.subList(0, input.size()-1);
			
			if (!sublist.contains(check)) {
				input.add(0);
			} else {				
				int lastIndex = input.lastIndexOf(check)+1;
				
				int prevIndex = 0;
				if(sublist!=null && !sublist.isEmpty()) {
					prevIndex = sublist.lastIndexOf(check)+1;
				}
				
				input.add(lastIndex-prevIndex);				
			}
			System.out.println(input.get(input.size()-1));
		}
		
		System.out.println(input.get(input.size()-1));
	}*/
	
	
	/** They begin by taking turns reading from a list of starting numbers (your puzzle input). 
	 * Then, each turn consists of considering the most recently spoken number:
	 * If that was the first time the number has been spoken, the current player says 0.
	 * Otherwise, the number had been spoken before; the current player announces how many turns apart the number is from when it was previously spoken.
	 * What will be the 2020th number spoken? */	
	public static void esercizio15_1(){
		
		Integer[] start = {14,3,1,0,9,5};
		ArrayList<Integer> input = new ArrayList<Integer>(Arrays.asList(start));
		
		while (input.size()<2020) {
			
			int check = input.get(input.size()-1);
			
			List<Integer> sublist = input.subList(0, input.size()-1);
			
			if (!sublist.contains(check)) {
				input.add(0);
			} else {				
				int lastIndex = input.lastIndexOf(check)+1;
				
				int prevIndex = 0;
				if(sublist!=null && !sublist.isEmpty()) {
					prevIndex = sublist.lastIndexOf(check)+1;
				}
				
				input.add(lastIndex-prevIndex);				
			}			
		}
		
		System.out.println(input.get(input.size()-1));		
	}

}
