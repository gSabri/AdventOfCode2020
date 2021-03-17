import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {
	
	public static final String OCC = "#";
		
	public static void main(String[] args) {
		esercizio17_2();
	}
	
	// NB: sistemare i range di scorrimento degli indici in base al fatto che la dimensione iniziale dell'input sia pari o dispari
	// questa va bene solo nel caso pari :(
	
	/** Same rules in four dimensions */
	public static void esercizio17_2(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es17.txt";
		List<List<String>> input = Utility.readInput(path).stream().map(s -> new ArrayList<String>(Arrays.asList(s.split("")))).collect(Collectors.toList());
		
		int dim = input.size(); //dimensioni del cubo
		
		List<String> activePos = new ArrayList<String>();
		
		// inizializzo array posizioni occupate da mio dato iniziale
		int a = 0, b = 0;
		for (int x = (-dim/2)+1; x<=dim/2; x++){
			b = 0;
			for (int y = (-dim/2)+1; y<=dim/2; y++) {
				try {
					if (OCC.equals(input.get(a).get(b))) {
						activePos.add(x+","+y+","+0+","+0);
					}
				} catch (IndexOutOfBoundsException e) {}
				b++;
			}
			a++;
		}
		
		List<String> result = new ArrayList<String>(); 
		
		for (int it = 0; it<6; it++) {			
			dim+=2;
			
			for (int x = (-dim/2)+1; x<=dim/2; x++){			
				for (int y = (-dim/2)+1; y<=dim/2; y++){
					for (int z = (-dim/2)+1; z<=dim/2; z++) {
						for (int w = (-dim/2)+1; w<=dim/2; w++) {
						
							String check = x+","+y+","+z+","+w;
							System.out.println(check);
							
							if (activePos.contains(check)) { //active
								int count = countActiveNeigh(activePos, x, y, z, w);
								if (count==2 || count==3) {
									result.add(check);
								} 
							} else { //inactive
								if (countActiveNeigh(activePos, x, y, z, w) == 3) {
									result.add(check);
								}
							}
						}
					}
				}
			}
			activePos = new ArrayList<String>(result);
			result.clear(); // nuova iterazione
		}
		System.out.print(activePos.size());
	}
	
	/* invece di provarli tutti e 80 stavolta ottimizzo */
	public static int countActiveNeigh(List<String> activePos, int i, int j, int z, int w){
		int count = 0;
		
		for (int x = i-1; x<=i+1; x++) {
			for (int y = j-1; y<=j+1; y++) {
				for (int zz = z-1; zz<=z+1; zz++) {
					for (int ww = w-1; ww<=w+1; ww++) {
						if (x==i && y==j && zz==z && ww==w) continue;
						if(activePos.contains(x+","+y+","+zz+","+ww)) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}
	
	/** - If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
		- If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.*/
	public static void esercizio17_1(){
		
		String path = "C:\\Users\\sabgio\\Desktop\\AdventOfCode2020\\AdventOfCode2020\\input_es17.txt";
		List<List<String>> input = Utility.readInput(path).stream().map(s -> new ArrayList<String>(Arrays.asList(s.split("")))).collect(Collectors.toList());
		
		int dim = input.size(); //dimensioni del cubo
		
		List<String> activePos = new ArrayList<String>();
		
		// inizializzo array posizioni occupate da mio dato iniziale
		int a = 0, b = 0;
		for (int x = (-dim/2)+1; x<=dim/2; x++){
			b = 0;
			for (int y = (-dim/2)+1; y<=dim/2; y++) {
				try {
					if (OCC.equals(input.get(a).get(b))) {
						activePos.add(x+","+y+","+0);
					}
				} catch (IndexOutOfBoundsException e) {}
				b++;
			}
			a++;
		}
		
		List<String> result = new ArrayList<String>(); 
		
		for (int it = 0; it<6; it++) {			
			dim+=2;
			
			for (int z = (-dim/2)+1; z<=dim/2; z++){			
				for (int i = (-dim/2)+1; i<=dim/2; i++){
					for (int j = (-dim/2)+1; j<=dim/2; j++) {
						
						String check = i+","+j+","+z;
						
						if (activePos.contains(check)) { //active
							int count = countActiveNeigh(activePos, i, j, z);
							if (count==2 || count==3) {
								result.add(check);
							} 
						} else { //inactive
							if (countActiveNeigh(activePos, i, j, z) == 3) {
								result.add(check);
							}
						}
					}
				}
			}
			activePos = new ArrayList<String>(result);
			result.clear(); // nuova iterazione
		}
		System.out.print(activePos.size());
	}
	
	public static int countActiveNeigh(List<String> activePos, int i, int j, int z){
		return (activePos.contains((i-1)+","+(j-1)+","+(z-1)) ? 1 : 0) +
			(activePos.contains((i-1)+","+j+","+(z-1)) ? 1 : 0) +
			(activePos.contains((i-1)+","+(j+1)+","+(z-1)) ? 1 : 0) +
			(activePos.contains(i+","+(j-1)+","+(z-1)) ? 1 : 0) +
			(activePos.contains(i+","+j+","+(z-1)) ? 1 : 0) +
			(activePos.contains(i+","+(j+1)+","+(z-1)) ? 1 : 0) +
			(activePos.contains((i+1)+","+(j-1)+","+(z-1)) ? 1 : 0) +
			(activePos.contains((i+1)+","+j+","+(z-1)) ? 1 : 0) +
			(activePos.contains((i+1)+","+(j+1)+","+(z-1)) ? 1 : 0) +
			(activePos.contains((i-1)+","+(j-1)+","+z) ? 1 : 0) +
			(activePos.contains((i-1)+","+j+","+z) ? 1 : 0) +
			(activePos.contains((i-1)+","+(j+1)+","+z) ? 1 : 0) +
			(activePos.contains(i+","+(j-1)+","+z) ? 1 : 0) +
			(activePos.contains(i+","+(j+1)+","+z) ? 1 : 0) +
			(activePos.contains((i+1)+","+(j-1)+","+z) ? 1 : 0) +
			(activePos.contains((i+1)+","+j+","+z) ? 1 : 0) +
			(activePos.contains((i+1)+","+(j+1)+","+z) ? 1 : 0) +
			(activePos.contains((i-1)+","+(j-1)+","+(z+1)) ? 1 : 0) +
			(activePos.contains((i-1)+","+j+","+(z+1)) ? 1 : 0) +
			(activePos.contains((i-1)+","+(j+1)+","+(z+1)) ? 1 : 0) +
			(activePos.contains(i+","+(j-1)+","+(z+1)) ? 1 : 0) +
			(activePos.contains(i+","+j+","+(z+1)) ? 1 : 0) +
			(activePos.contains(i+","+(j+1)+","+(z+1)) ? 1 : 0) +
			(activePos.contains((i+1)+","+(j-1)+","+(z+1)) ? 1 : 0) +
			(activePos.contains((i+1)+","+j+","+(z+1)) ? 1 : 0) +
			(activePos.contains((i+1)+","+(j+1)+","+(z+1)) ? 1 : 0);
	}
}