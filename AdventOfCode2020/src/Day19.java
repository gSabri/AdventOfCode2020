import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day19 {
	
	public static void main(String[] args) {
		esercizio19_2();
	}
	
	public static void esercizio19_2(){		
		String pathRules = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es19_rules.txt";
		HashMap<Integer,String> rules = (HashMap<Integer, String>) Utility.readInput(pathRules).stream()
				.map(s -> s.split(":"))
				.collect(Collectors.toMap(l -> Integer.parseInt(l[0].trim()), l -> l[1].trim()));
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es19.txt";
		List<String> input = Utility.readInput(path);
		
		String regex42 = decode(rules, 42);
		String regex31 = decode(rules, 31);
		//String regex = "\\b("+regex42+"){2,}("+regex31+")+\\b";
		
		int result = 0;
		int count = 0;
		int count_31 = 1;
		do {
		
			String regex = "\\b("+regex42+")+("+regex42+"){"+count_31+"}("+regex31+"){"+count_31+"}\\b";
			
			count = 0;
			for (String mess : input) {
				if (mess.trim().matches(regex)) count++;
			}
			
			result+=count;
			count_31++;
		} while (count>0);
		
		System.out.println(result);
	}
			
	public static void esercizio19_1(){		
		String pathRules = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es19_rules.txt";
		HashMap<Integer,String> rules = (HashMap<Integer, String>) Utility.readInput(pathRules).stream()
				.map(s -> s.split(":"))
				.collect(Collectors.toMap(l -> Integer.parseInt(l[0].trim()), l -> l[1].trim()));
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es19.txt";
		List<String> input = Utility.readInput(path);
		
		String regex = decode(rules, 0);
		
		int count = 0;
		for (String mess : input) {
			if (mess.matches(regex)) count++;
		}
		
		System.out.println(count);
	}

	
	public static String decode(HashMap<Integer,String> rules, int rule) {
		String exp = rules.get(rule);
		if (exp.equals("\"a\"")) {
			return "a";
		}
		if (exp.equals("\"b\"")) {
			return "b";
		}
		
		if (exp.contains("|")) {			
			String first = (exp.split("\\|"))[0].trim();
			String sec = (exp.split("\\|"))[1].trim();
			
			String[] firstArr = first.split("\\s+");
			String[] secArr = sec.split("\\s+");
			
			String newExp = "";
			for (int k = 0; k<firstArr.length; k++) {
				newExp += "("+ (firstArr[k].contains("a") || firstArr[0].contains("b") ? firstArr[k].trim() : decode(rules, Integer.parseInt(firstArr[k]))) + ")";
			}
			newExp+="|";
			for (int k = 0; k<secArr.length; k++) {
				newExp += "("+ (secArr[k].contains("a") || secArr[0].contains("b") ? secArr[k].trim() : decode(rules, Integer.parseInt(secArr[k]))) + ")";
			}
			return newExp;
			
		} else {
			
			String[] arr = exp.trim().split("\\s+");
			
			String newExp = "";
			for (int k = 0; k<arr.length; k++) {
				newExp += "("+ (arr[k].contains("a") || arr[0].contains("b") ? arr[k].trim() : decode(rules, Integer.parseInt(arr[k]))) + ")";
			}
			return newExp;
		}
	}
}
