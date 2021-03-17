import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 {
	
	public static void main(String[] args) {
		long target = esercizio9_1();
		esercizio9_2(target);
	}
	
	/** You must find a contiguous set of at least two numbers in your list which sum to the invalid number from step 1.
	To find the encryption weakness, add together the smallest and largest number in this contiguous range */
	public static void esercizio9_2(long target){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es9.txt";
		List<Long> input = Utility.readInput(path).stream().map(s-> Long.parseLong(s)).collect(Collectors.toList());
		
		// tolgo da input il mio target in modo da non trovare insieme contenente solo lui
		// EDIT non serve e potrei creare un insieme contiguo valido non esistente
		// input.remove(target);
		
		HashSet<Long> result = new HashSet<Long>();
		boolean found = false;
		long min = 0;
		long max = 0;
		
		for (int i = 0; i<input.size(); i++) {
			
			long val = input.get(i);
			
			result.clear();
			min = val;
			max = val;
			
			long sum = val;			
			result.add(val);
			
			for (int j=i+1; j<input.size(); j++) {
				long val2 = input.get(j);
				
				result.add(val2);
				sum += val2;
				
				if (val2>max) {
					max = val2;
				}
				if (val2<min) {
					min = val2;
				}
				
				if (sum == target) {
					found = true;
				}
				if (sum >= target){
					break;
				}
			}
			
			if (found) break;
		}
		
		System.out.println(min+max);
	}
	
	
	/** XMAS starts by transmitting a preamble of 25 numbers. 
	 * After that, each number you receive should be the sum of any two of the 25 immediately previous numbers. 
	 * The two numbers will have DIFFERENT values, and there might be more than one such pair.
	 * 
	 * Find the first number in the list (after the preamble) which is not the sum of two of the 25 numbers BEFORE IT. 
	 * What is the first number that does not have this property? */
	public static long esercizio9_1(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es9.txt";
		List<Long> input = Utility.readInput(path).stream().map(s-> Long.parseLong(s)).collect(Collectors.toList());
		
		long notSum = 0;
		boolean found = true;
		List<Long> preamble = input.subList(0, 25); 
		List<Long> checkList = input.subList(25, input.size()); 
		
		for (int i = 0; i<checkList.size() && found; i++) {
			found = false;
			long check = checkList.get(i);
			preamble = input.subList(i, Integer.min(i+25, checkList.size())); 
			
			for (Long p : preamble) {
				if (p>check) {
					continue;
				} else {
					long diff = check-p;
					if (diff!=p && preamble.contains(diff)) {
						found =  true;
						break;
					}
				}
			}
			notSum = check;
		}	
		
		System.out.println(notSum);
		return notSum;
	}

}
