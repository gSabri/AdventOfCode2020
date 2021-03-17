import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day14 {
	
	public static void main(String[] args) {
		esercizio14_2();
	}
	
	/** A version 2 decoder chip doesn't modify the values being written at all. 
		Instead, it acts as a memory address decoder. Immediately before a value is written to memory, 
	  	each bit in the bitmask modifies the corresponding bit of the destination memory address in the following way:
	  
	  	If the bitmask bit is 0, the corresponding memory address bit is unchanged.
		If the bitmask bit is 1, the corresponding memory address bit is overwritten with 1.
		If the bitmask bit is X, the corresponding memory address bit is floating.
		
		The floating bits will take on all possible values, 
		potentially causing many memory addresses to be written all at once!*/
	
	public static void esercizio14_2(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es14.txt";
		List<String> input = Utility.readInput(path);
		
		HashMap<BigInteger, Integer> mem = new HashMap<BigInteger, Integer>();
		String mask = "";
		
		for (String inp : input) {
			
			if (inp.startsWith("mask")) {
				mask = inp.substring(7,inp.length()).trim();
			} else {
				int pos = Integer.parseInt(StringUtils.substringBetween(inp, "[", "]"));
				int value = Integer.parseInt(inp.split("=")[1].trim());				
				String binary = StringUtils.leftPad(Integer.toBinaryString(pos), 36, "0");
				
				ArrayList<Integer> floatPos = new ArrayList<Integer>();
				
				for (int i = 0; i<mask.length(); i++) {
					if (mask.charAt(i) != '0') {
						binary = replaceCharUsingCharArray(binary, mask.charAt(i), i);
						if (mask.charAt(i) == 'X') {
							floatPos.add(35-i);
						}
					}
				}
				
				Collections.sort(floatPos);
				String binaryZero = binary.replace('X', '0');
				BigInteger minPos = new BigInteger(binaryZero, 2);
				mem.put(minPos, value);
				
				ArrayList<BigInteger> toSum = new ArrayList<BigInteger>();
				for (int j = 0 ; j<floatPos.size(); j++) {
					
					BigInteger x = new BigInteger("2").pow(floatPos.get(j));
					
					ArrayList<BigInteger> toAdd = new ArrayList<BigInteger>();
					for (BigInteger i : toSum){
						toAdd.add(i.add(x));
					}
					toSum.addAll(toAdd);
					toSum.add(x);
				}
				
				for (BigInteger d : toSum){
					mem.put(minPos.add(d), value);
				}
			}			
		}
		
		long result =0;
		for (long l : mem.values()) { 
			result+=l;
		}
		
		System.out.println(result);		
	}
	
	public static void esercizio14_1(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es14.txt";
		List<String> input = Utility.readInput(path);
		
		HashMap<Integer, Long> mem = new HashMap<Integer, Long>();
		String mask = "";
		
		for (String inp : input) {
			
			if (inp.startsWith("mask")) {
				// recupero la maschera
				mask = inp.substring(7,inp.length()).trim();
			} else {
				// recupero posizione e valore
				int pos = Integer.parseInt(StringUtils.substringBetween(inp, "[", "]"));
				int value = Integer.parseInt(inp.split("=")[1].trim());
				
				//applico la maschera
				String binary = StringUtils.leftPad(Integer.toBinaryString(value), 36, "0");
				for (int i = 0; i<mask.length(); i++) {
					if (mask.charAt(i) != 'X') {
						binary = replaceCharUsingCharArray(binary, mask.charAt(i), i);
					}
				}				
				
				mem.put(pos, Long.parseLong(binary, 2));
			}			
		}
		
		long result = 0;
		for (long l : mem.values()) { 
			result += l; 
		}
		
		System.out.println(result);		
		
	}
	
	public static String replaceCharUsingCharArray(String str, char ch, int index) {
	    char[] chars = str.toCharArray();
	    chars[index] = ch;
	    return String.valueOf(chars);
	}

}
