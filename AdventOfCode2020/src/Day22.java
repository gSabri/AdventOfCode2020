import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day22 {	
		
	public static void main(String[] args) {
		esercizio22_2();
	}
	
	/** The game consists of a series of rounds with a few changes:

		Before either player deals a card, if there was a previous round in this game that had exactly the same cards in the same order in the same players' decks, 
		the game instantly ends in a win for player 1. Previous rounds from other games are not considered. 
		(This prevents infinite games of Recursive Combat, which everyone agrees is a bad idea.)
		
		Otherwise, this round's cards must be in a new configuration; the players begin the round by each drawing the top card of their deck as normal.
		
		If both players have at least as many cards remaining in their deck as the value of the card they just drew, 
		the winner of the round is determined by playing a new game of Recursive Combat (see below).
		Otherwise, at least one player must not have enough cards left in their deck to recurse; the winner of the round is the player with the higher-value card.
		
		As in regular Combat, the winner of the round (even if they won the round by winning a sub-game) takes the two cards dealt at the beginning of the round 
		and places them on the bottom of their own deck (again so that the winner's card is above the other card) */	
	public static void esercizio22_2(){
		
		String path1 = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es22_p1.txt";
		List<Integer> p1 = Utility.readInput(path1).stream().map(s-> Integer.parseInt(s)).collect(Collectors.toList());
		
		String path2 = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es22_p2.txt";
		List<Integer> p2 = Utility.readInput(path2).stream().map(s-> Integer.parseInt(s)).collect(Collectors.toList());
		
		List<List<Integer>> prevRounds1 = new ArrayList<List<Integer>>();
		List<List<Integer>> prevRounds2 = new ArrayList<List<Integer>>();
		
		HashMap<Integer,List<Integer>> winner = playGame(p1,p2,prevRounds1,prevRounds2);
		
		int winId = (winner.get(1) == null) ? 2 : 1;
		List<Integer> board = winner.get(winId);		
		
		int result = 0;
		for (int i = 0; i<board.size(); i++){
			result += board.get(i)*(board.size()-i);
		}
		
		System.out.println(result);
	}
	
	/**To play a sub-game of Recursive Combat, each player creates a new deck by making a copy of the next cards in their deck 
	 * (the quantity of cards copied is equal to the number on the card they drew to trigger the sub-game). 
	 * During this sub-game, the game that triggered it is on hold and completely unaffected; no cards are removed from players' decks to form the sub-game.*/
	public static HashMap<Integer,List<Integer>> playGame(List<Integer> p1, List<Integer> p2, List<List<Integer>> prevRounds1, List<List<Integer>> prevRounds2){
		HashMap<Integer,List<Integer>> winner = new HashMap<Integer,List<Integer>>();
		
		while (p1.size()!=0 && p2.size()!=0) {			
			/* If there was a previous round in this game that had exactly the same cards in the same order in the same players' decks, 
			 * the game instantly ends in a win for player 1 */
			if (prevRounds1.contains(p1) || prevRounds2.contains(p2)) {
				winner.remove(2);
				winner.put(1, p1);
				break;
			}			
			prevRounds1.add(new ArrayList<Integer>(p1));
			prevRounds2.add(new ArrayList<Integer>(p2));
			
			/* If both players have at least as many cards remaining in their deck 
			as the value of the card they just drew, the winner of the round is determined by playing a new game of Recursive Combat*/			
			int n1 = p1.get(0);
			int n2 = p2.get(0);
			
			p1.remove(0);
			p2.remove(0);			
			
			if (p1.size()>=n1 && p2.size()>=n2) { 
				
				ArrayList<Integer> p1s = new ArrayList<Integer>(p1.subList(0, n1));
				ArrayList<Integer> p2s = new ArrayList<Integer>(p2.subList(0, n2));
				
				List<List<Integer>> prevRounds1s = new ArrayList<List<Integer>>();
				List<List<Integer>> prevRounds2s = new ArrayList<List<Integer>>();			
				
				winner = playGame(p1s, p2s, prevRounds1s, prevRounds2s);
				
				int winId = winner.get(1) == null ? 2 : 1;
				
				if (winId == 1) {
					p1.add(n1);
					p1.add(n2);
					winner.remove(2);
					winner.put(1, p1);
				} else {
					p2.add(n2);
					p2.add(n1);
					winner.remove(1);
					winner.put(2, p2);
				}
				
			} else {
				/*Otherwise, at least one player must not have enough cards left in their deck to recurse
				 * the winner of the round is the player with the higher-value card. */
				if (n1>n2) {
					p1.add(n1);
					p1.add(n2);
					winner.remove(2);
					winner.put(1, p1);
				} else {
					p2.add(n2);
					p2.add(n1);
					winner.remove(1);
					winner.put(2, p2);
				}
			}
		}		
		return winner;
	}
	
	/** Then, the game consists of a series of rounds: both players draw their top card, and the player with the higher-valued card wins the round. 
	 * The winner keeps both cards, placing them on the bottom of their own deck so that the winner's card is above the other card. 
	 * If this causes a player to have all of the cards, they win, and the game ends.
	 * 
	 * Once the game ends, you can calculate the winning player's score. 
	 * The bottom card in their deck is worth the value of the card multiplied by 1, 
	 * the second-from-the-bottom card is worth the value of the card multiplied by 2, and so on. 
	 * With 10 cards, the top card is worth the value on the card multiplied by 10.*/
	public static void esercizio22_1(){
		
		String path1 = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es22_p1.txt";
		List<Integer> p1 = Utility.readInput(path1).stream().map(s-> Integer.parseInt(s)).collect(Collectors.toList());
		
		String path2 = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es22_p2.txt";
		List<Integer> p2 = Utility.readInput(path2).stream().map(s-> Integer.parseInt(s)).collect(Collectors.toList());
		
		while (p1.size()!=0 && p2.size()!=0) {
			int n1= p1.get(0);
			int n2 = p2.get(0);
			
			p1.remove(0);
			p2.remove(0);
			
			if (n1>n2) {
				p1.add(n1);
				p1.add(n2);
			} else {
				p2.add(n2);
				p2.add(n1);
			}
		}
		
		List<Integer> winner = (p1!=null && !p1.isEmpty()) ? p1 : p2;
		
		int result = 0;
		for (int i = 0; i<winner.size(); i++){
			result += winner.get(i)*(winner.size()-i);
		}
		
		System.out.println(result);
	}

}
