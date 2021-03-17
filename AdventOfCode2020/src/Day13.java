import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {
	
	public static void main(String[] args) {
		esercizio13_2();
	}
	
	public static void esercizio13_2(){	
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es13.txt";
		List<String> input = Utility.readInput(path);
		
		// The second line lists the bus IDs that are in service according to the shuttle company; 
		//entries that show x must be out of service, so you decide to ignore them.
		List<BigDecimal> ids = Arrays.asList(input.get(1).split(",")).stream()
				.filter(c -> !"x".equals(c))
				.map(c-> new BigDecimal(c))
				.collect(Collectors.toList());
		Collections.sort(ids);
		
		List<String> inp = Arrays.asList(input.get(1).split(",")).stream().collect(Collectors.toList());
		
		boolean found = false;
		
		// parto da qui dopo aver provato con il teorema cinese del resto altrimenti ci impiega giorni
		// il calendar suggerisce di partire da 100000000000000
		BigDecimal t = new BigDecimal("672000000000000");
		
		//BigDecimal t = BigDecimal.ZERO;
		
		BigDecimal max = ids.get(ids.size()-1);
		BigDecimal maxPos = new BigDecimal(String.valueOf(inp.indexOf(max.toString())));
		
		BigDecimal mult = t.divide(max, RoundingMode.CEILING);
		t = max.multiply(mult);
				
		while (!found) {
			
			boolean ok = true;
			for (BigDecimal b : ids) {
				int index = inp.indexOf(b.toString());
					
				if (((t.subtract(maxPos).add(new BigDecimal(String.valueOf(index))))
						.divide(b,3,RoundingMode.CEILING)
						.remainder(BigDecimal.ONE)
						.compareTo(BigDecimal.ZERO))!=0) {
					ok = false;
					break;
				}
			}
			
			if (ok) {
				found = true;
			} else {
				t = t.add(max);
			}			
		}
		
		System.out.println(t.subtract(maxPos));		
	}
	
	
	/**Each bus has an ID number that also indicates how often the bus leaves for the airport.
	 * Bus schedules are defined based on a timestamp that measures the number of minutes since some fixed reference point in the past. 
	 * At timestamp 0, every bus simultaneously departed from the sea port. After that, each bus travels to the airport, 
	 * then various other locations, and finally returns to the sea port to repeat its journey forever.
	 * The time this loop takes a particular bus is also its ID number
	 * 
	 * What is the ID of the earliest bus you can take to the airport multiplied by the number of minutes you'll need to wait for that bus? */
	public static void esercizio13_1(){	
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es13.txt";
		List<String> input = Utility.readInput(path);
		
		//estimate of the earliest timestamp you could depart on a bus
		int est = Integer.parseInt(input.get(0));
		
		// The second line lists the bus IDs that are in service according to the shuttle company; 
		//entries that show x must be out of service, so you decide to ignore them.
		List<Integer> ids = Arrays.asList(input.get(1).split(",")).stream()
				.filter(c -> !"x".equals(c))
				.map(c->Integer.parseInt(c))
				.collect(Collectors.toList());
		
		int minVal = Integer.MAX_VALUE;
		int id = 0;
		
		boolean found = false;
		
		for (int i = 0; !found; i++) {
			for (int j = 0; j<ids.size(); j++) {
				
				int val = ids.get(j) * i;
				
				if (val>=est && val<minVal) {
					minVal = val;
					id = ids.get(j);
					found = true;
				}
			}
		}
		
		System.out.println(id*(minVal-est));		
	}

}
