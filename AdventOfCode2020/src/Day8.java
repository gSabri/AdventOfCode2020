import java.util.HashSet;
import java.util.List;

public class Day8 {

	public static void main(String[] args) {
		esercizio8_1();
		esercizio8_2();
	}
	
	/** Corruption:  either a jmp is supposed to be a nop, or a nop is supposed to be a jmp. 
	 * (No acc instructions were harmed in the corruption of this boot code.)*/
	public static void esercizio8_2(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es8.txt";
		List<String> input = Utility.readInput(path);
		
		int[] result = new int[2];
		
		// cambio un nop in jump alla volta e vedo se finisce, partendo dal primo loop trovato prima
		for (int i = 0; i<input.size(); i++) {			
			String c = input.get(i).split("\\s+")[0].trim();			
			switch (c) {
				case "acc":
					continue;
				case "jmp":
					input.set(i, input.get(i).replace("jmp", "nop"));
					break;
				case "nop":
					input.set(i, input.get(i).replace("nop", "jmp"));
					break;
			}
			result = runGame(input);
			
			if (result[0] != input.size()-1) {
				//input = Utility.readInput(path);				
				// se non ho finito riscambio jmp e nop senza rileggere tutto
				String cc = input.get(i).split("\\s+")[0].trim();
				switch (cc) {
					case "jmp":
						input.set(i, input.get(i).replace("jmp", "nop"));
						break;
					case "nop":
						input.set(i, input.get(i).replace("nop", "jmp"));
						break;
				}
			} else break;
		}
		
		System.out.println(result[0]);
		System.out.println(result[1]);
	}
	
	/**The boot code is represented as a text file with one instruction per line of text. 
	 * Each instruction consists of an operation (acc, jmp, or nop) and an argument (a signed number like +4 or -20).
	 * acc increases the accumulator of the given value
	 * jmp jumps a number of instructions equals to the given value (jmp+1 continues, jmp+2 skips one)
	 * nop does nothing */
	public static void esercizio8_1(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es8.txt";
		List<String> input = Utility.readInput(path);
		
		int[] result = runGame(input);
		
		System.out.println(result[1]);
	}
	
	public static int[] runGame(List<String> input){
		
		int accumulator = 0;
		HashSet<Integer> posSet = new HashSet<Integer>(); // set of visited positions
		
		int lastPos = 0;
		int[] result = new int[2]; 
		
		// Immediately before any instruction is executed a second time, what value is in the accumulator?
		for (int i=0; i<input.size();){
			String[] in = input.get(i).split("\\s+");
			
			String command = in[0].trim();
			int value = Integer.parseInt(in[1].trim());
			
			if (!posSet.add(i)){ //if position already visited, exit the loop
				break;
			}
			
			lastPos = i;
			result[0] = lastPos;
						
			switch (command) {
				case "acc":
					accumulator+=value;
					i++;
					break;
				case "jmp":
					i+=value;
					break;
				case "nop":
					i++;
					break;
			}
			result[1] = accumulator;
		}
		return result;
	}
}
