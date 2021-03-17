public class Day25 {
	
	public static void main(String[] args) {
		esercizio25_1();
	}
	
	/** The handshake used by the card and the door involves an operation that transforms a subject number.
	 *  To transform a subject number, start with the value 1. 
	 *  
	 *  Then, a number of times called the loop size, perform the following steps:
	 *  Set the value to itself multiplied by the subject number.
	 *	Set the value to the remainder after dividing the value by 20201227.
	 *
	 * The card always uses a specific, secret loop size when it transforms a subject number. 
	 * The door always uses a different, secret loop size.
	 * 
	 * The card transforms the subject number of 7 according to the card's secret loop size. The result is called the card's public key.
	 * The door transforms the subject number of 7 according to the door's secret loop size. The result is called the door's public key.
	 * 
	 * The card transforms the subject number of the door's public key according to the card's loop size. The result is the encryption key.
	 * The door transforms the subject number of the card's public key according to the door's loop size. The result is the same encryption key as the card calculated.*/		
	public static void esercizio25_1(){
		
		long kpc = 8987316;
		long kpd = 14681524;
		
		long cSub = 7;
		long dSub = 7;
		long cLoop = 1;
		long dLoop = 1;
		
		long kcs = 7;
		long kds = 7;
		
		while (kcs!=kpc || kds!=kpd) {
			if (kcs!=kpc) {
				kcs *= cSub;
				kcs = kcs%20201227;
				cLoop++;				
			}
			
			if (kds!=kpd) {			
				kds *= dSub;
				kds = kds%20201227;
				dLoop++;
			}		
		}
		
		long ekc = kpc;
		long ekd = kpd;		
		long cl = 1;
		long dl = 1;
		
		while (dl!=dLoop || cl!=cLoop) {
			if (dl!=dLoop) {
				ekc *= kpc;
				ekc = ekc%20201227;
				dl++;				
			}
			
			if (cl!=cLoop) {			
				ekd *= kpd;
				ekd = ekd%20201227;
				cl++;
			}		
		}
		
		System.out.println(ekd);
		System.out.println(ekc);
	}
	
}
