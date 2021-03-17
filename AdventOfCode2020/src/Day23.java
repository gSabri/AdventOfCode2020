import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Day23 {	
		
	public static void main(String[] args) {
		esercizio23_2_opt();
	}
		
	/**Your labeling is still correct for the first few cups; 
	 * after that, the remaining cups are just numbered in an increasing fashion starting from the number 
	 * after the highest number in your list and proceeding one by one until one million is reached.
	 * 
	 * The small crab isn't going to do merely 100 moves; the crab is going to do ten million (10000000) moves!
	 * 
	 * The crab is going to hide your stars - one each - under the two cups that will end up immediately clockwise of cup 1. 
	 * You can have them if you predict what the labels on those cups will be when the crab is finished.
	 * 
	 * What do you get if you multiply their labels together? */
	public static void esercizio23_2_opt(){
		
		int[] myOrig = {9,1,6,4,3,8,2,7,5};
		
		// salvo per ogni posizione il suo successivo da 0 a 1000000
		int[] myShift = {6,7,8,3,10,4,5,2,1}; 
		
		int[] my = new int[1000001];
		my[0] = 0;		
		for (int i = 1; i<=9; i++)
			my[i]=myShift[i-1];
		for (int i = 10; i<1000000; i++)
			my[i]=i+1;
		my[1000000] = myOrig[0];

		int size = my.length;		
		int nMoves = 10000000;
		
		int current = myOrig[0];
		
		for (int i = 1; i <=nMoves; i++) {
			
			/*The crab picks up the three cups that are immediately clockwise of the current cup. 
			 *They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.*/
			int p1 = my[current];
			int p2 = my[p1];
			int p3 = my[p2];
			
			/* The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. 
			 * 	If this would select one of the cups that was just picked up, the crab will keep subtracting one 
			 * 	until it finds a cup that wasn't just picked up. If at any point in this process the value goes below 
			 * the lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.*/
			int dest = current-1;
			
			if (dest<=0) 
				dest = size-1;
			while (dest==p1  || dest==p2 || dest==p3){
				dest--;
				if (dest<=0) 
					dest = size-1;
			}
			
			my[current]=my[p3];
			int succDest = my[dest];
			my[dest] = p1;
			my[p3] = succDest;
			current = my[current];	
		}
		
		/** Starting after the cup labeled 1, collect the other cups' labels clockwise into a single string with no extra characters*/
		long result = 1;
		result*=my[1];
		result*=my[my[1]];
				
		System.out.println(result);
	}
	
	// Ci mette una vita
	public static void esercizio23_2(){
		
		Integer[] myArr = {9,1,6,4,3,8,2,7,5};
		
		ArrayList<Integer> my = new ArrayList<Integer>(Arrays.asList(myArr));
		
		for (int i = 10; i<=1000000; i++)
			my.add(i);

		int size = myArr.length;		
		int nMoves = 10000000;		
		int current = my.get(0);
		
		for (int i = 0; i < nMoves; i++) {			
			
			/*The crab picks up the three cups that are immediately clockwise of the current cup. 
			 *They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.*/
			int currentIndex = my.indexOf(current);
			int pickInd = (currentIndex+1)%size;
			
			ArrayList<Integer> picked = new ArrayList<Integer>();
			while (picked.size()<3) {
				//if (pickInd>=size) 
					picked.add(my.get(pickInd%size));
				/*else 
					picked.add(my.get(pickInd));*/
				pickInd++;
			}
			
			my.removeAll(picked);
			
			/* The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. 
			 * 	If this would select one of the cups that was just picked up, the crab will keep subtracting one 
			 * 	until it finds a cup that wasn't just picked up. If at any point in this process the value goes below 
			 * the lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.*/
			int dest = current-1;
			
			ArrayList<Integer> clone = new ArrayList<Integer>(my);
			Collections.sort(clone); // ordino array ausiliario per trovare max e min
			
			int min = clone.get(0);
			int max = clone.get(clone.size()-1);
			while (my.indexOf(dest) == -1 && dest>=min){
				dest--;
			}			
			if (dest<min) dest = max; 
			
			/* The crab places the cups it just picked up so that they are immediately clockwise of the destination cup. 
			 * They keep the same order as when they were picked up. */
			my.addAll(my.indexOf(dest)+1, picked);
			
			current = my.get((my.indexOf(current)+1)%size);
			System.out.println(i);
		}
		
		/** Starting after the cup labeled 1, collect the other cups' labels clockwise into a single string with no extra characters*/
		long result = 1;
		result*=my.get((my.indexOf(1)+1)%size);
		result*=my.get((my.indexOf(1)+2)%size);
				
		System.out.println(result);
	}
	
	/** The crab is going to mix up some cups, and you have to predict where they'll end up.
	 * The cups will be arranged in a circle and labeled clockwise. 
	 * 
	 * Before the crab starts, it will designate the first cup in your list as the current cup. 
	 * The crab is then going to do 100 moves.
	 * 
	 * Each move, the crab does the following actions:
	 * - The crab picks up the three cups that are immediately clockwise of the current cup. 
	 *	They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.
	 * - The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. 
	 * 	If this would select one of the cups that was just picked up, the crab will keep subtracting one until it finds a cup that wasn't just picked up. 
	 * 	If at any point in this process the value goes below the lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.
	 * - The crab places the cups it just picked up so that they are immediately clockwise of the destination cup. They keep the same order as when they were picked up.
	 * - The crab selects a new current cup: the cup which is immediately clockwise of the current cup. 
	 * 
	 * Starting after the cup labeled 1, collect the other cups' labels clockwise into a single string with no extra characters; 
	 * each number except 1 should appear exactly once.*/
	public static void esercizio23_1(){
		
		Integer[] myArr = {9,1,6,4,3,8,2,7,5};
		
		ArrayList<Integer> my = new ArrayList<Integer>(Arrays.asList(myArr));

		int size = myArr.length;		
		int nMoves = 100;		
		int current = my.get(0);
		
		for (int i = 0; i < nMoves; i++) {			
			
			/*The crab picks up the three cups that are immediately clockwise of the current cup. 
			 *They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.*/
			int currentIndex = my.indexOf(current);
			int pickInd = (currentIndex+1)%size;
			
			ArrayList<Integer> picked = new ArrayList<Integer>();
			while (picked.size()<3) {
				//if (pickInd>=size) 
					picked.add(my.get(pickInd%size));
				/*else 
					picked.add(my.get(pickInd));*/
				pickInd++;
			}
			
			my.removeAll(picked);
			
			/* The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. 
			 * 	If this would select one of the cups that was just picked up, the crab will keep subtracting one 
			 * 	until it finds a cup that wasn't just picked up. If at any point in this process the value goes below 
			 * the lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.*/
			int dest = current-1;
			
			ArrayList<Integer> clone = new ArrayList<Integer>(my);
			Collections.sort(clone); // ordino array ausialiario per trovare max e min
			
			int min = clone.get(0);
			int max = clone.get(clone.size()-1);
			while (my.indexOf(dest) == -1 && dest>=min){
				dest--;
			}			
			if (dest<min) dest = max; 
			
			/* The crab places the cups it just picked up so that they are immediately clockwise of the destination cup. 
			 * They keep the same order as when they were picked up. */
			my.addAll(my.indexOf(dest)+1, picked);
			
			current = my.get((my.indexOf(current)+1)%size);
		}
		
		/** Starting after the cup labeled 1, collect the other cups' labels clockwise into a single string with no extra characters*/
		String result = "";
		for (int i=my.indexOf(1)+1; result.length()<size-1; i++) {
			result+=my.get(i%size);
		}
		
		System.out.println(result);
	}	
}