import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {
	
	public static final String EMPTY = "L";
	public static final String OCC = "#";
	public static final String FLOOR = ".";
		
	public static void main(String[] args) {
		esercizio11_2();
	}
	
	@SuppressWarnings("unchecked")
	public static void esercizio11_2(){	
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es11.txt";
		List<List<String>> input = Utility.readInput(path).stream().map(s -> new ArrayList<String>(Arrays.asList(s.split("")))).collect(Collectors.toList());
				
		int changes = 0;
		int occ = 0;
		
		do {
			List<Object> play =  playGame2(input);			
			changes = (int) play.get(0);
			occ = (int) play.get(1);
			
			input = (List<List<String>>) play.get(2);
			
		} while (changes != 0);
		
		System.out.println(occ);		
	}
	
	public static List<Object> playGame2(List<List<String>> input){
		int maxI = input.size();
		int maxJ = input.get(0).size();
		
		int changes = 0;
		int occup = 0;
		
		List<Object> res = new ArrayList<Object>();
		List<List<String>> result = new ArrayList<List<String>>();
		
		for (int i = 0; i<maxI; i++){
			result.add(i, new ArrayList<String>());
			
			for (int j = 0; j<maxJ; j++) {
				
				switch (input.get(i).get(j)) {
					case FLOOR:
						result.get(i).add(j, FLOOR);
						break;
					case EMPTY:
						if (checkEmptyNeigh2(input, i, j, maxI, maxJ)) {
							result.get(i).add(j, OCC);
							changes++;
							occup++;
						} else {
							result.get(i).add(j, EMPTY);
						}
						break;
					case OCC:
						if (checkOccNeigh2(input, i, j, maxI, maxJ)) {
							result.get(i).add(j, EMPTY);
							changes++;
						} else {
							result.get(i).add(j, OCC);
							occup++;
						}
						break;
				}
			}
		}
		res.add(changes);
		res.add(occup);
		res.add(result);
		return res;
	}
	
	/** il primo posto in ciascuna delle direzioni deve essere libero */
	public static boolean checkEmptyNeigh2(List<List<String>> input, int i, int j, int maxI, int maxJ){
		return	
			emptySeatDir("N", input, i, j, maxI, maxJ) &&
			emptySeatDir("NE", input, i, j, maxI, maxJ) &&
			emptySeatDir("NW", input, i, j, maxI, maxJ) &&
			emptySeatDir("S", input, i, j, maxI, maxJ) &&
			emptySeatDir("SE", input, i, j, maxI, maxJ) &&
			emptySeatDir("SW", input, i, j, maxI, maxJ) &&
			emptySeatDir("E", input, i, j, maxI, maxJ) &&
			emptySeatDir("W", input, i, j, maxI, maxJ);
	}
	
	public static boolean emptySeatDir(String direction, List<List<String>> input, int i, int j, int maxI, int maxJ) {
		
		switch (direction) {
			case "N":
				for (int k = i-1; k>=0; k--) {
					if (EMPTY.equals(input.get(k).get(j))) return true;
					if (OCC.equals(input.get(k).get(j))) return false;
					//se floor continua
				}
				return true;
			case "NE":
				for (int k = i-1, h = j+1 ; k>=0 && h<maxJ; k--, h++) {
					if (EMPTY.equals(input.get(k).get(h))) return true;
					if (OCC.equals(input.get(k).get(h))) return false;
					//se floor continua
				}
				return true;
			case "NW":
				for (int k = i-1, h = j-1 ; k>=0 && h>=0; k--, h--) {
					if (EMPTY.equals(input.get(k).get(h))) return true;
					if (OCC.equals(input.get(k).get(h))) return false;
					//se floor continua
				}
				return true;
			case "S":
				for (int k = i+1; k<maxI; k++) {
					if (EMPTY.equals(input.get(k).get(j))) return true;
					if (OCC.equals(input.get(k).get(j))) return false;
					//se floor continua
				}
				return true;
			case "SE":
				for (int k = i+1, h = j+1 ; k<maxI && h<maxJ; k++, h++) {
					if (EMPTY.equals(input.get(k).get(h))) return true;
					if (OCC.equals(input.get(k).get(h))) return false;
					//se floor continua
				}
				return true;
			case "SW":
				for (int k = i+1, h = j-1 ; k<maxI && h>=0; k++, h--) {
					if (EMPTY.equals(input.get(k).get(h))) return true;
					if (OCC.equals(input.get(k).get(h))) return false;
					//se floor continua
				}
				return true;
			case "E":
				for (int k = j+1; k<maxJ; k++) {
					if (EMPTY.equals(input.get(i).get(k))) return true;
					if (OCC.equals(input.get(i).get(k))) return false;
					//se floor continua
				}
				return true;
			case "W":
				for (int k = j-1; k>=0; k--) {
					if (EMPTY.equals(input.get(i).get(k))) return true;
					if (OCC.equals(input.get(i).get(k))) return false;
					//se floor continua
				}
				return true;
		}
		return true;
	}	
	
	public static boolean checkOccNeigh2(List<List<String>> input, int i, int j, int maxI, int maxJ){
		return	
			((emptySeatDir("N", input, i, j, maxI, maxJ) ? 0 : 1) +
			 (emptySeatDir("NE", input, i, j, maxI, maxJ) ? 0 : 1) +
			 (emptySeatDir("NW", input, i, j, maxI, maxJ) ? 0 : 1) +
			 (emptySeatDir("S", input, i, j, maxI, maxJ) ? 0 : 1) +
			 (emptySeatDir("SE", input, i, j, maxI, maxJ) ? 0 : 1) +
			 (emptySeatDir("SW", input, i, j, maxI, maxJ) ? 0 : 1) +
			 (emptySeatDir("E", input, i, j, maxI, maxJ) ? 0 : 1) +
			 (emptySeatDir("W", input, i, j, maxI, maxJ) ? 0 : 1))>=5;
	}
	
	/** Sort of game of life 
	 * the chaos stabilizes and further applications of these rules cause no seats to change state! 
	 * Simulate your seating area by applying the seating rules repeatedly until no seats change state. How many seats end up occupied?*/
	@SuppressWarnings("unchecked")
	public static void esercizio11_1(){	
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es11.txt";
		List<List<String>> input = Utility.readInput(path).stream().map(s -> new ArrayList<String>(Arrays.asList(s.split("")))).collect(Collectors.toList());
	
		int changes = 0;
		int occ = 0;
		
		 do {
			
			List<Object> play =  playGame(input);
			
			changes = (int) play.get(0);
			occ = (int) play.get(1);
			input = (List<List<String>>) play.get(2);
			
		} while (changes!= 0);
		 
		 System.out.println(occ);
	}
	
	/**All decisions are based on the number of occupied seats adjacent to a given seat 
	 * (one of the eight positions immediately up, down, left, right, or diagonal from the seat). The following rules are applied to every seat simultaneously:
	* If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
	* If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
	* Otherwise, the seat's state does not change.
	* Floor (.) never changes; seats don't move, and nobody sits on the floor.*/ 
	public static List<Object> playGame(List<List<String>> input){
		int maxI = input.size();
		int maxJ = input.get(0).size();
		
		int changes = 0;
		int occup = 0;
		
		List<Object> res = new ArrayList<Object>();
		List<List<String>> result = new ArrayList<List<String>>();
		
		for (int i = 0; i<maxI; i++){
			result.add(i, new ArrayList<String>());
			
			for (int j = 0; j<maxJ; j++) {
				
				switch (input.get(i).get(j)) {
					case FLOOR:
						result.get(i).add(j, FLOOR);
						break;
					case EMPTY:
						if (checkEmptyNeigh(input, i, j, maxI, maxJ)) {
							result.get(i).add(j, OCC);
							changes++;
							occup++;
						} else {
							result.get(i).add(j, EMPTY);
						}
						break;
					case OCC:
						if (checkOccNeigh(input, i, j, maxI, maxJ)) {
							result.get(i).add(j, EMPTY);
							changes++;
						} else {
							result.get(i).add(j, OCC);
							occup++;
						}
						break;
				}
			}
		}
		res.add(changes);
		res.add(occup);
		res.add(result);
		return res;
	}
	
	public static boolean checkEmptyNeigh(List<List<String>> input, int i, int j, int maxI, int maxJ){
		ArrayList<String> cases = new ArrayList<String>(); 
		cases.add(EMPTY); cases.add(FLOOR);
		
		return 
			cases.contains(input.get(Integer.max(0,i-1)).get(Integer.max(0,j-1))) &&
			cases.contains(input.get(Integer.max(0,i-1)).get(j)) && 
			cases.contains(input.get(Integer.max(0,i-1)).get(Integer.min(j+1, maxJ-1))) && 
			cases.contains(input.get(i).get(Integer.max(0,j-1))) && 
			cases.contains(input.get(i).get(Integer.min(j+1, maxJ-1))) && 
			cases.contains(input.get(Integer.min(maxI-1,i+1)).get(Integer.max(j-1, 0))) && 
			cases.contains(input.get(Integer.min(maxI-1,i+1)).get(j)) && 
			cases.contains(input.get(Integer.min(maxI-1,i+1)).get(Integer.min(j+1, maxJ-1)));	
	}
	
	public static boolean checkOccNeigh(List<List<String>> input, int i, int j, int maxI, int maxJ){
		
		if (i==0) { // prima riga
			// angoli false 
			if (j == 0 || j == maxJ-1) return false;
			else return ((OCC.equals(input.get(i).get(j-1)) ? 1 : 0) +
					(OCC.equals(input.get(i).get(j+1)) ? 1 : 0) +
					(OCC.equals(input.get(i+1).get(j-1)) ? 1 : 0) +
					(OCC.equals(input.get(i+1).get(j)) ? 1 : 0) +
					(OCC.equals(input.get(i+1).get(j+1)) ? 1 : 0)) >= 4;
		} else if (i == maxI-1) { //ultima riga
			// angoli false 
			if (j == 0 || j == maxJ-1) return false;
			else return ((OCC.equals(input.get(i-1).get(j-1)) ? 1 : 0) +
					(OCC.equals(input.get(i-1).get(j)) ? 1 : 0) +
					(OCC.equals(input.get(i-1).get(j+1)) ? 1 : 0) +
					(OCC.equals(input.get(i).get(j-1)) ? 1 : 0) +
					(OCC.equals(input.get(i).get(j+1)) ? 1 : 0))>=4;
		} else if (j == 0) { //prima colonna angoli esclusi
			return ((OCC.equals(input.get(i-1).get(j)) ? 1 : 0) +
					(OCC.equals(input.get(i-1).get(j+1)) ? 1 : 0) +
					(OCC.equals(input.get(i).get(j+1)) ? 1 : 0) +
					(OCC.equals(input.get(i+1).get(j)) ? 1 : 0) +
					(OCC.equals(input.get(i+1).get(j+1)) ? 1 : 0))>=4;
		} else if (j == maxJ-1){ //ultima colonna angoli esclusi
			return ((OCC.equals(input.get(i-1).get(j-1)) ? 1 : 0) +
					(OCC.equals(input.get(i-1).get(j)) ? 1 : 0) +
					(OCC.equals(input.get(i).get(j-1)) ? 1 : 0) +
					(OCC.equals(input.get(i+1).get(j-1)) ? 1 : 0) +
					(OCC.equals(input.get(i+1).get(j)) ? 1 : 0))>=4;
		} else {
			return 
				((OCC.equals(input.get(Integer.max(0,i-1)).get(Integer.max(0,j-1))) ? 1 : 0) +
				(OCC.equals(input.get(Integer.max(0,i-1)).get(Integer.max(0,j))) ? 1 : 0) +
				(OCC.equals(input.get(Integer.max(0,i-1)).get(Integer.min(j+1, maxJ-1))) ? 1 : 0) +
				(OCC.equals(input.get(i).get(Integer.max(0,j-1))) ? 1 : 0) +
				(OCC.equals(input.get(i).get(Integer.min(j+1, maxJ-1))) ? 1 : 0) +
				(OCC.equals(input.get(Integer.min(maxI-1,i+1)).get(Integer.max(j-1, 0))) ? 1 : 0) +
				(OCC.equals(input.get(Integer.min(maxI-1,i+1)).get(Integer.max(j, 0))) ? 1 : 0) +
				(OCC.equals(input.get(Integer.min(maxI-1,i+1)).get(Integer.min(j+1, maxJ-1))) ? 1 : 0)) >= 4;
		}
	}

}
