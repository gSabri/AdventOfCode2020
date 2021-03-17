import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7 {
	
	private static final String MY_COLOR="shinygold";
	private static final String NO_OTHER="noother";
	
	public static void main(String[] args) {    	
		esercizio7_2();
	}
	
	/* Quante borse puo' contenere ricorsivamente una shiny gold */
	public static void esercizio7_2(){
	
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es7.txt";
		List<String> input = Utility.readInput(path);
		
		HashMap<String, List<Color>> map = (HashMap<String, List<Color>>) input.stream()
			.map(s -> s.replaceAll("bags", ""))
			.map(s -> s.replaceAll("bag", ""))
			.map(s -> s.replaceAll("\\s+", ""))
			.map(s -> s.split("contain"))
			.collect(Collectors.toMap(l -> l[0].trim(), 
				l -> Arrays.asList(l[1].replaceAll("\\.", "").split(",")).stream()
					.filter(st -> !NO_OTHER.equals(st))
					.map(st -> st.trim())
					.map(st -> new Color(st.substring(1,st.length()), Integer.parseInt(st.substring(0,1)))).collect(Collectors.toList())));
		
		long count = countChildren(map, MY_COLOR);	
		
		System.out.println(count);		
	}
	
	public static int countChildren(Map<String, List<Color>> m, String fatherName) {		
		int count = 0;
		List<Color> children = m.get(fatherName);
		if (children!=null && !children.isEmpty()) {
			for (Color c : children) {
				count+= c.num + c.num*(countChildren(m,c.name));
			}
		}
		return count;
	}
	
	public static class Color {		
		public String name;
		public int num;
				
		public Color(String name, int num) {
			this.name = name;
			this.num = num;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(o instanceof Color)) return false;
			Color obj = (Color) o;
			return this.name!=null && (this.name.equals(obj.name));
		}
	}
	
		
	/* Quanti colori di borse possono contenere la mia */
	public static void esercizio7_1(){
	
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es7.txt";
		List<String> input = Utility.readInput(path);
		
		HashMap<String, List<String>> map = (HashMap<String, List<String>>) 
				input.stream()
				.map(s -> s.replaceAll("bags", ""))
				.map(s -> s.replaceAll("bag", ""))
				.map(s -> s.replaceAll("[0-9]", ""))
				.map(s -> s.replaceAll("\\s+", ""))
				.map(s -> s.split("contain"))
				.collect(Collectors.toMap(l -> l[0].trim(), l -> Arrays.asList(l[1].replaceAll("\\.", "").split(","))));
		
		long count = 0;
		
		for (String key : map.keySet()) {
			if (shinyGoldRecursiveChild(map, key)) count++;
		}
		
		System.out.println(count);		
	}
		
	public static boolean shinyGoldRecursiveChild(Map<String, List<String>> m, String father) {		
		// se due figli possono contenere la shiny gold conto come 1
		List<String> children = m.get(father);
		if (children!=null && !children.isEmpty()) {
			// se c'e' come figlio non serve vedere se e' anche figlio di figlio
			if (children.contains(MY_COLOR)) {
				return true;
			}
			// se due figli possono conbtenere la shiny gold conto come 1
			for (String c : children) {
				c = c.trim();					
				if (shinyGoldRecursiveChild(m, c)) {
					return true;
				}
			}
		} 
		return false;
	}
	
	/*public static Nodo treeSearch(Nodo radix, Nodo item) {
		Nodo result = null;		
		if (radix!=null && radix.equals(item)) result = radix;
		else if (radix!=null && radix.children != null && !radix.children.isEmpty()) {
			for (Nodo n : radix.children) {
				result = treeSearch(n, item);
			}
		}
		return result;
	}
	
	public static int treeCount(Nodo radix, Nodo item, int count) {
		if (radix!=null && radix.equals(item)) count++;
		else if (radix!=null && radix.children != null && !radix.children.isEmpty()) {
			for (Nodo n : radix.children) {
				count+= treeCount(n, item, count);
			}
		}
		return count;
	}
	
	public class Nodo {
		public String parent;
		public String name;
		public int number;
		public ArrayList<Nodo> children;
		
		public Nodo(String parent, String name) {
			this.parent = parent;
			this.name = name;
		}
		
		public Nodo(String parent, String name, int number) {
			this.parent = parent;
			this.name = name;
			this.number = number;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(o instanceof Nodo)) return false;
			Nodo obj = (Nodo) o;
			return this.name!=null && (this.name.equals(obj.name));
		}
	}*/

}
