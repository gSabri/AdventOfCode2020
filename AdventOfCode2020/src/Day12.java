import java.util.HashMap;
import java.util.List;

public class Day12 {
	
	public static void main(String[] args) {
		esercizio12_2();
	}
	
	/** Almost all of the actions indicate how to move a waypoint which is relative to the ship's position. 
	 	Action N means to move the waypoint north by the given value.
		Action S means to move the waypoint south by the given value.
		Action E means to move the waypoint east by the given value.
		Action W means to move the waypoint west by the given value.
		Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
		Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
		Action F means to move forward to the waypoint a number of times equal to the given value.*/
	public static void esercizio12_2(){	
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es12.txt";
		//List<List<String>> input = Utility.readInput(path).stream().map(s -> new ArrayList<String>(Arrays.asList(s.split("")))).collect(Collectors.toList());
		List<String> input = Utility.readInput(path);
		
		HashMap<String, Integer> pos = new HashMap<String, Integer>();
		pos.put("N", 0);
		pos.put("E", 0);
		
		HashMap<String, Integer> posW = new HashMap<String, Integer>();
		posW.put("N", 1);
		posW.put("E", 10);
		
		// il waypoint da la direzione e la velocita'
		for (String s : input) {
			
			String command = s.substring(0,1);
			int value = Integer.parseInt(s.substring(1,s.length()));
			
			int n = posW.get("N"), e = posW.get("E");
			
			switch (command) {				
				case "N":
					posW.put("N", posW.get("N")+value);
					break;
				case "S":
					posW.put("N", posW.get("N")-value);
					break;
				case "E":
					posW.put("E", posW.get("E")+value);
					break;
				case "W":
					posW.put("E", posW.get("E")-value);
					break;
				case "L":					
					switch (value) {						
						case 90:
							posW.put("N", e);
							posW.put("E", -n);
							break;
						case 180:
							posW.put("N", -n);
							posW.put("E", -e);
							break;
						case 270:
							posW.put("N", -e);
							posW.put("E", n);
							break;
					}
					break;
				case "R":					
					switch (value) {						
						case 90:
							posW.put("N", -e);
							posW.put("E", n);
							break;
						case 180:
							posW.put("N", -n);
							posW.put("E", -e);
							break;
						case 270:
							posW.put("N", e);
							posW.put("E", -n);
							break;
					}
					break;
				case "F":
					int valE = posW.get("E")*value; 
					pos.put("E", pos.get("E")+valE);
					int valN = posW.get("N")*value; 
					pos.put("N", pos.get("N")+valN);
					break;
			}
		}
		
		System.out.println(Math.abs(pos.get("E"))+Math.abs(pos.get("N")));		
	}
	
	/** Action N means to move north by the given value.
		Action S means to move south by the given value.
		Action E means to move east by the given value.
		Action W means to move west by the given value.
		Action L means to turn left the given number of degrees.
		Action R means to turn right the given number of degrees.
		Action F means to move forward by the given value in the direction the ship is currently facing.
		
		Manhattan distance (sum of the absolute values of its east/west position and its north/south position) from its starting position is 17 + 8 = 25. */
	public static void esercizio12_1(){	
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es12.txt";
		//List<List<String>> input = Utility.readInput(path).stream().map(s -> new ArrayList<String>(Arrays.asList(s.split("")))).collect(Collectors.toList());
		List<String> input = Utility.readInput(path);
		
		String dir = "E";
		HashMap<String, Integer> pos = new HashMap<String, Integer>();
		pos.put("N", 0);
		pos.put("E", 0);
		
		for (String s : input) {
			
			String command = s.substring(0,1);
			int value = Integer.parseInt(s.substring(1,s.length()));
			
			switch (command) {
				case "N":
					pos.put("N", pos.get("N")+value);
					break;
				case "S":
					pos.put("N", pos.get("N")-value);
					break;
				case "E":
					pos.put("E", pos.get("E")+value);
					break;
				case "W":
					pos.put("E", pos.get("E")-value);
					break;
				case "L":
					switch (value) {
						case 90:
							if ("N".equals(dir)) dir = "W";
							else if ("S".equals(dir)) dir = "E"; 
							else if ("E".equals(dir)) dir = "N";
							else if ("W".equals(dir)) dir = "S";
							break;
						case 180:
							if ("N".equals(dir)) dir = "S";
							else if ("S".equals(dir)) dir = "N"; 
							else if ("E".equals(dir)) dir = "W";
							else if ("W".equals(dir)) dir = "E";
							break;
						case 270:
							if ("N".equals(dir)) dir = "E";
							else if ("S".equals(dir)) dir = "W"; 
							else if ("E".equals(dir)) dir = "S";
							else if ("W".equals(dir)) dir = "N";
							break;
					}
					break;
				case "R":
					switch (value) {
						case 90:
							if ("N".equals(dir)) dir = "E";
							else if ("S".equals(dir)) dir = "W"; 
							else if ("E".equals(dir)) dir = "S";
							else if ("W".equals(dir)) dir = "N";
							break;
						case 180:
							if ("N".equals(dir)) dir = "S";
							else if ("S".equals(dir)) dir = "N"; 
							else if ("E".equals(dir)) dir = "W";
							else if ("W".equals(dir)) dir = "E";
							break;
						case 270:
							if ("N".equals(dir)) dir = "W";
							else if ("S".equals(dir)) dir = "E"; 
							else if ("E".equals(dir)) dir = "N";
							else if ("W".equals(dir)) dir = "S";
							break;
					}
					break;
				case "F":
					switch (dir) {
						case "E":
							pos.put("E", pos.get("E")+value);
							break;
						case "W":
							pos.put("E", pos.get("E")-value);
							break;
						case "N":
							pos.put("N", pos.get("N")+value);
							break;
						case "S":
							pos.put("N", pos.get("N")-value);
							break;
					}
					break;
			}
		}
		
		System.out.println(Math.abs(pos.get("E"))+Math.abs(pos.get("N")));		
	}

}
