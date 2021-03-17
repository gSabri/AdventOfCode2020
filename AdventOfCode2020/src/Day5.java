import java.util.ArrayList;
import java.util.List;

public class Day5 {
     
    public static void main(String[] args) {    	
    	esercizio5_2();
    }
    
    /* Idea geniale di Marta:
     * FBFBFBB equivale ad un "codice binario contrario" con F=0, B=1 quindi al numero 1101010
     * LRR equivale ad un "codice binario contrario" con L=0, R=1 quindi al numero 110
     * 
     *  una riga di codice e si faceva tutto "-.- */
    
    /** Qual'e' il mio posto ? L'unico rimasto libero. 
     * Ne rimangono alcuni liberi anche alla riga 0 e 127 ma non sono i miei*/    
    public static void esercizio5_2(){
    	
    	String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\input_es5.txt";
    	List<String> input = Utility.readInput(path);
    	
    	ArrayList<Integer> result = new ArrayList<Integer>();

    	for (String s : input) {
			
    		int minR = 0;
    		int maxR = 127;    		   		
    		for (int c = 0 ; c<7 ; c++) {    			
    			if (minR == maxR) break;    			
    			int center = (int) Math.ceil((maxR-minR)/2.0);    			
    			switch (s.charAt(c)) {
    				case 'F':
    					maxR -= center;
    					break;
					case 'B':
						minR += center;
						break;
    			}
    		}    		
    		int row = maxR;
    		
    		int minC = 0;
    		int maxC = 7;    		
    		for (int ch = 7 ; ch<10 ; ch++) {    			
    			if (minC == maxC) break;    			
    			int center = (int) Math.ceil((maxC-minC)/2.0);    			
    			switch (s.charAt(ch)) {
    				case 'L':
    					maxC -= center;
    					break;
					case 'R':
						minC += center;
						break;
    			}
    		}
    		
    		int col = maxC;
    		
    		result.add(row*8+col);
		}
    	
    	// non e' sulla prima e ultima riga, quindi da 8 a 820 = 828(res prec)-8
    	// gli id sono univoci e sequenziali, non essendo nella prima o ultima riga non supero il massimo
    	// sono in mezzo
    	int id = 0;
    	for (int i = 8; i<=820; i++) {
    		
    		if (!result.contains(i) && result.contains(i+1) && result.contains(i-1)) {
    			id = i;
    			break;
    		}
    	}
    	
    	System.out.println(id);    
    }
    
    /** Le prime sette lettere B o F ti dicono in che meta' sei se continui a dividere le 128 righe
     * F la prima meta', B la seconda (delle rimanenti)
     * Con le ultime 3 il ragionamento e' lo stesso sulle 8 colonne
     * L a sinistra (prime),  R a destra (ultime)
     * 
     * Every seat also has a unique seat ID: multiply the row by 8, then add the column  
     * What is the highest seat ID on a boarding pass?*/    
    public static void esercizio5_1(){
    	
    	// leggo input
    	String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\input_es5.txt";
    	List<String> input = Utility.readInput(path);
    	
    	int max = 0;
    	
    	for (String s : input) {
			
    		int minR = 0;
    		int maxR = 127;
    		   		
    		for (int c = 0 ; c<7 ; c++) {
    			
    			if (minR == maxR) break;
    			
    			int center = (int) Math.ceil((maxR-minR)/2.0);
    			
    			switch (s.charAt(c)) {
    				case 'F':
    					maxR -= center;
    					break;
					case 'B':
						minR += center;
						break;
    			}
    		}
    		
    		int row = maxR;
    		
    		int minC = 0;
    		int maxC = 7;
    		
    		for (int ch = 7 ; ch<10 ; ch++) {
    			
    			if (minC == maxC) break;
    			
    			int center = (int) Math.ceil((maxC-minC)/2.0);
    			
    			switch (s.charAt(ch)) {
    				case 'L':
    					maxC -= center;
    					break;
					case 'R':
						minC += center;
						break;
    			}
    		}
    		
    		int col = maxC;
    		
    		int resId = row*8+col;
    		if (resId>max) max =  resId; 		
    		
		}    	
    	
    	System.out.println(max);    
    }
}