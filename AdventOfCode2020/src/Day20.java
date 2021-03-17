import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day20 {
		
	public static void main(String[] args) {
		 esercizio20_2();
	}
	
	/** imbroglio: tolgo i bordi, calcolo i # e provo a togliere x mostri alla volta*/
	public static void esercizio20_2(){
		
		// leggo l'input
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es20.txt";
		List<String> input = Utility.readInput(path);	
		
		HashMap<Integer,List<List<String>>> map = new HashMap<Integer,List<List<String>>>();
		int tile = 0;
		List<List<String>> list = new ArrayList<List<String>>();		
		
		for (String s : input) {
			if (StringUtils.isBlank(s)) {
				map.put(tile, new ArrayList<List<String>>(list));
				tile = 0;
				list = new ArrayList<List<String>>();
				continue;
			}
			if (s.startsWith("Tile")) {
				tile = Integer.parseInt(s.substring(5,s.length()-1));
				list = new ArrayList<List<String>>();
				continue;
			}
			
			List<String> row =  new ArrayList<String>();
			row = Arrays.asList(s.split(""));
			list.add(row);			
		}
						
		long count = 0;	
				
		for (Integer i : map.keySet()) {
			List<List<String>> tl = map.get(i);
			int size = tl.size();
			
			for (int x = 1; x<size-1; x++) {
				List<String> l = tl.get(x);
				for (int y = 1; y<size-1; y++) {
					if (l.get(y).equals("#"))
						count++;
				}				
			}
		}
		System.out.println(0+"-"+count);
		
		int monsterSize = 15;		
		for (int mult = 1; mult<=40; mult++) {
			count-=monsterSize;
			System.out.println(mult+"-"+count);
		}		
	}
		
	/**The camera array consists of many cameras: they produce many smaller square image tiles with a random unique ID number 
	 * that need to be reassembled back into a single image.
	 * Worse yet, each image tile has been rotated and flipped to a random orientation.
	 *  Your first task is to reassemble the original image by orienting the tiles so they fit together.
	 *  
	 *  To check that you've assembled the image correctly, multiply the IDs of the four corner tiles together.*/
	public static void esercizio20_1(){
		
		// leggo l'input
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es20.txt";
		List<String> input = Utility.readInput(path);	
		
		HashMap<Integer,List<List<String>>> map = new HashMap<Integer,List<List<String>>>();
		int tile = 0;
		List<List<String>> list = new ArrayList<List<String>>();		
		
		for (String s : input) {
			if (StringUtils.isBlank(s)) {
				map.put(tile, new ArrayList<List<String>>(list));
				tile = 0;
				list = new ArrayList<List<String>>();
				continue;
			}
			if (s.startsWith("Tile")) {
				tile = Integer.parseInt(s.substring(5,s.length()-1));
				list = new ArrayList<List<String>>();
				continue;
			}
			
			List<String> row =  new ArrayList<String>();
			row = Arrays.asList(s.split(""));
			list.add(row);			
		}
		
		HashMap<Integer,List<Integer>> matches = new HashMap<Integer,List<Integer>>();
		
		long result = 1;
		for (Integer i : map.keySet()) {
			ArrayList<Integer> m = getMatches(map, i);
			
			matches.put(i, m);
			
			// spero che con due match ci siano sono i 4 angoli
			// e non altri che combaciano per due lati opposti
			if (m.size()==2) { 
				result*=i;
			}
			System.out.println(i+"-"+matches.get(i));
		}
		
		System.out.println(result);
		
	}
	
	// Ricerca greedy dei match delle 2 versioni (dritta e reverse) di ogni lato 
	// con i 4 lati degli altri tile (basta in una sola direzione)
	// -> senza contare che se un lato si rovescia deve farlo anche il suo opposto
	// ma considero che eventualmente posso girare l'altro pezzo visto che il puzzle si completa
	public static ArrayList<Integer> getMatches(HashMap<Integer,List<List<String>>> map, int num){
		
		ArrayList<Integer> mt = new ArrayList<Integer>();
		List<List<String>> tile = map.get(num);
		int size = tile.size();		
			
		ArrayList<String> firstRow = new ArrayList<String>(tile.get(0));
		Collections.reverse(tile.get(0));
		ArrayList<String> firstRowRev = new ArrayList<String>(tile.get(0));
		Collections.reverse(tile.get(0));// lo radrizzo
		
		ArrayList<String> lastRow = new ArrayList<String>(tile.get(size-1));
		Collections.reverse(tile.get(size-1));
		ArrayList<String> lastRowRev = new ArrayList<String>(tile.get(size-1));
		Collections.reverse(tile.get(size-1));// lo radrizzo
		
		ArrayList<String> firstCol = (ArrayList<String>) tile.stream().map(l -> l.get(0)).collect(Collectors.toList());
		Collections.reverse(firstCol);
		ArrayList<String> firstColRev = new ArrayList<String>(firstCol);
		Collections.reverse(firstCol);// lo radrizzo
		
		ArrayList<String> lastCol = (ArrayList<String>) tile.stream().map(l -> l.get(size-1)).collect(Collectors.toList());
		Collections.reverse(lastCol);
		ArrayList<String> lastColRev = new ArrayList<String>(lastCol);
		Collections.reverse(lastCol);// lo radrizzo
		
		for (Integer it : map.keySet()) {
			if (it==num) continue;
				
			List<List<String>> tileOther = map.get(it);
				
			ArrayList<String> firstRowOther = new ArrayList<String>(tileOther.get(0));				
			if (firstRowOther.equals(firstRow) || firstRowOther.equals(firstRowRev) || 
				firstRowOther.equals(lastRow) || firstRowOther.equals(lastRowRev) ||
				firstRowOther.equals(firstCol) || firstRowOther.equals(firstColRev) ||
				firstRowOther.equals(lastCol) || firstRowOther.equals(lastColRev)){
				mt.add(it);
				continue;
			}
			
			ArrayList<String> lastRowOther = new ArrayList<String>(tileOther.get(size-1));				
			if (lastRowOther.equals(firstRow) || lastRowOther.equals(firstRowRev) || 
				lastRowOther.equals(lastRow) || lastRowOther.equals(lastRowRev) ||
				lastRowOther.equals(firstCol) || lastRowOther.equals(firstColRev) ||
				lastRowOther.equals(lastCol) || lastRowOther.equals(lastColRev)){
				mt.add(it);
				continue;
			}
			
			ArrayList<String> firstColOther = (ArrayList<String>) tileOther.stream().map(l -> l.get(0)).collect(Collectors.toList());
			if (firstColOther.equals(firstRow) || firstColOther.equals(firstRowRev) || 
				firstColOther.equals(lastRow) || firstColOther.equals(lastRowRev) ||
				firstColOther.equals(firstCol) || firstColOther.equals(firstColRev) ||
				firstColOther.equals(lastCol) || firstColOther.equals(lastColRev)){
				mt.add(it);
				continue;
			}
			
			ArrayList<String> lastColOther = (ArrayList<String>) tileOther.stream().map(l -> l.get(size-1)).collect(Collectors.toList());
			if (lastColOther.equals(firstRow) || lastColOther.equals(firstRowRev) || 
				lastColOther.equals(lastRow) || lastColOther.equals(lastRowRev) ||
				lastColOther.equals(firstCol) || lastColOther.equals(firstColRev) ||
				lastColOther.equals(lastCol) || lastColOther.equals(lastColRev)){
				mt.add(it);
				continue;
			}
			
		}
		return mt;
		
	}

}
