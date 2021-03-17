import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
	
	public static void main(String[] args) {
		esercizio10_2();
	}
	
	/** You'll need to figure out how many different ways they can be arranged */
	public static void esercizio10_2(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es10.txt";
		List<Integer> input = Utility.readInput(path).stream().map(s-> Integer.parseInt(s)).collect(Collectors.toList());
		Collections.sort(input);
		
		int max = input.get(input.size()-1)+3;
		input.add(0,0);
		input.add(input.size(),max);
		
		long[] c = new long[input.size()];
		c[0] = 1;
		
		long count = 0;
		int jolts = 0;	
		
		for (int i = 1; i<input.size(); i++) {
			jolts = input.get(i);
			int j1 = jolts-1;
			int j2 = jolts-2;
			int j3 = jolts-3;		
			
			count = 0;
			if (input.contains(j1))	count+= c[input.indexOf(j1)];		
			if (input.contains(j2)) count+= c[input.indexOf(j2)];		
			if (input.contains(j3)) count+= c[input.indexOf(j3)];
			
			c[i] = count;			
		}
		
		System.out.println(count);		
	}
	
	
	/** Each of your joltage adapters is rated for a specific output joltage (your puzzle input). 
	 * Any given adapter can take an input 1, 2, or 3 jolts lower than its rating and still produce its rated output joltage.
	 * 
	 * In addition, your device has a built-in joltage adapter rated for 3 jolts higher than the highest-rated adapter in your bag. 
	 * (If your adapter list were 3, 9, and 6, your device's built-in adapter would be rated for 12 jolts.)
	 * 
	 * Treat the charging outlet near your seat as having an effective joltage rating of 0.
	 * 
	 * If you use every adapter in your bag at once, what is the distribution of joltage differences between the charging outlet, the adapters, and your device? */
	public static void esercizio10_1(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es10.txt";
		List<Integer> input = Utility.readInput(path).stream().map(s-> Integer.parseInt(s)).collect(Collectors.toList());
		Collections.sort(input);
		
		HashMap<Integer,Integer> distMap = new HashMap<Integer,Integer>();
		distMap.put(1, 0);
		distMap.put(2, 0);
		distMap.put(3, 0);
		
		int jolts = 0;
		
		for (int i = 0; i<input.size(); i++) {
			int diff = input.get(i) - jolts;
			
			distMap.put(diff, distMap.get(diff)+1);
			jolts = input.get(i);
		}
		/*Finally, your device's built-in adapter is always 3 higher than the highest adapter*/
		distMap.put(3, distMap.get(3)+1);
		
		System.out.println(distMap.get(1)*distMap.get(3));		
	}
}
