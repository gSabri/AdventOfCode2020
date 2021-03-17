import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day24 {	

	public static void main(String[] args) {
		ArrayList<Tile> result = esercizio24_1();
		esercizio24_2(result);
	}
	
	/** Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
		Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
		
		Here, tiles immediately adjacent means the six tiles directly touching the tile in question.
		
		The rules are applied simultaneously to every tile; put another way, it is first determined which tiles need to be flipped, 
		then they are all flipped at the same time
		
		How many tiles will be black after 100 days? */
	
	public static void esercizio24_2(ArrayList<Tile> tiles){
		
		// prendo solo i neri
		tiles = (ArrayList<Tile>) tiles.stream().filter(t->t.color==1).collect(Collectors.toList());
		
		for (int day = 1; day<=100; day++){
			
			ArrayList<Tile> result = new ArrayList<Tile>(tiles);
			
			for (Tile t : tiles) {
				checkBlack(tiles, result, t, true);
			}
			tiles = result;
			System.out.println(day+" -"+tiles.size());
    	}
	}
	
	public static int checkBlack(ArrayList<Tile> tiles, ArrayList<Tile> result, Tile t, boolean blk) {
		int black = 0;
		int e = t.e;
		int n = t.n;		
		Tile et = new Tile(e+2,n);
		Tile net = new Tile(e+1,n+1);
		Tile set = new Tile(e+1,n-1);
		Tile wt = new Tile(e-2,n);
		Tile nwt = new Tile(e-1,n+1);
		Tile swt = new Tile(e-1,n-1);
		
		ArrayList<Tile> toCheck = new ArrayList<Tile>();
		
		// se non c'e' e' bianco
		if (tiles.contains(et)) black++;
		else toCheck.add(et); 
		
		if (tiles.contains(net)) black++;
		else toCheck.add(net);
		
		if (tiles.contains(set)) black++;
		else toCheck.add(set);
		
		if (tiles.contains(wt)) black++;
		else toCheck.add(wt);
		
		if (tiles.contains(nwt)) black++;
		else toCheck.add(nwt);
		
		if (tiles.contains(swt)) black++;
		else toCheck.add(swt);
		
		if (blk) {
			if (black == 0 || black>2) {
				result.remove(t);
			}
		
			for (Tile tl : toCheck) {
				if (checkBlack(tiles, result, tl, false) == 2 && !result.contains(tl)) {				
					result.add(tl);
				} 
			}
		}	
				
		return black;
	}

	
	/** The tiles are all hexagonal; they need to be arranged in a hex grid with a very specific color pattern.
	 * 
	 * The tiles are all white on one side and black on the other. They start with the white side facing up. 
	 *  The lobby is large enough to fit whatever pattern might need to appear there.
	 *  
	 * List of the tiles that need to be flipped over
	 * Each line in the list identifies a single tile that needs to be flipped by giving a series of steps 
	 * starting from a reference tile in the very center of the room. 
	 * 
	 * Because the tiles are hexagonal, every tile has six neighbors: east, southeast, southwest, west, northwest, and northeast. 
	 * These directions are given in your list, respectively, as e, se, sw, w, nw, and ne.
	 * 
	 * Each time a tile is identified, it flips from white to black or from black to white. Tiles might be flipped more than once.
	 * 
	 * Go through the renovation crew's list and determine which tiles they need to flip. 
	 * After all of the instructions have been followed, how many tiles are left with the black side up? */
	public static ArrayList<Tile> esercizio24_1(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es24.txt";
    	List<String> input = Utility.readInput(path);
    	
    	ArrayList<Tile> tiles = new ArrayList<Tile>();
    	Tile start =  new Tile(0,0); // white
    	tiles.add(start);    	
    	int black = 0;
    	
    	for (String s : input) {
    		
    		int e = 0;
    		int n = 0;    		
    		while (s.length()>0) {
    		
	    		int dirLength = 1;
	    		String dir = s.substring(0,dirLength);
	    		if (!dir.equals("e") && !dir.equals("w")) {
	    			dir = s.substring(0,2);
	    			dirLength = 2;
	    		}
	    		
	    		s = s.substring(dirLength);    		
	    			
	    		switch (dir) {
		    		case "e":
		    			e+=2;
		    			break;
		    		case "ne":
		    			n++; e++;
		    			break;
		    		case "se":
		    			n--; e++;
		    			break;
		    		case "w":
		    			e-=2;
		    			break;
		    		case "nw":
		    			n++; e--;
		    			break;
		    		case "sw":
		    			n--; e--;
		    			break;
		    	}  			
    		}
    		
    		Tile t = new Tile(e,n);
    		if (tiles.contains(t)) {
    			t = tiles.get(tiles.indexOf(t));
    		} else tiles.add(t);
    		
    		if (t.color == 0) {
    			t.color = 1;
    			black++;
    		} else {
    			t.color = 0;
    			black--;
    		}
    	}
		System.out.println(black);
		
		return tiles;
	}
	
    public static class Tile implements Cloneable {

		public int color; //0 white, 1 black
		
		public int e;
		public int n;
		
		public Tile (int e, int n){
			this.e=e;
			this.n=n;
		}
		
		public Tile (int e, int n, int cl){
			this.e=e;
			this.n=n;
			this.color=cl;
		}
			
		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(o instanceof Tile)) return false;
			Tile obj = (Tile) o;
			return (this.e == obj.e && this.n == obj.n);
		}
		
		@Override
		public String toString(){
			return e+" - "+n+" - "+color;
		}
		
		@Override
		public Tile clone(){
			Tile clone = new Tile(e,n);
			clone.color = this.color;
			return clone;			
		}
	}

}