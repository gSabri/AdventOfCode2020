import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utility {
	
	public static List<String> readInput(String filename) {
    	// leggo input
    	List<String> input = new ArrayList<String>();
    	
    	try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));
			
			input = br.lines().collect(Collectors.toList());
			
			br.close();		
			
		} catch (IOException e) {
			System.out.println("Error");
			return null;
		}
    	return input;    	
    }
}
