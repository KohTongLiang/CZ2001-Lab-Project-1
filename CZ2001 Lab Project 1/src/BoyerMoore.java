import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class BoyerMoore {

	public static void main(String[] args) {
	    System.out.println("====Start of Program====");
        /*
         * Initialize text and pattern variables.
         * */
		String textStr = "";
		String patternStr = "";
		// example search: AGCATATCTCAGCG
		 
		/*
		 * Read the genome file located in the project folder.
		 * */
		try {
	       	textStr = new String ( Files.readAllBytes( Paths.get("genome.fna") ) );
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	        
	    /*
	    * Capture user input. The input will be the pattern that the user wish to find in the
	    * genome sequence.
	    * */
	    System.out.println("Please enter the pattern you wish to search: ");
	    Scanner sc = new Scanner(System.in);
	    patternStr = sc.nextLine();
       
        
        /*
         * Convert text and pattern String to character array for future comparisons.
         * */
		char[] text = textStr.toCharArray();
		char[] pattern = patternStr.toCharArray();
		/*
		 * Initialize some basic variable to be used to loop through the text and
		 * pattern character arrays
		 * */
		int i = 0;
		int j;
    	int patternsFound = 0;
    	int m = pattern.length;
    	int n = text.length;

		if (m == 0) {
			/*
			 * * Condition when user enter a pattern not found in the genome.
			 * */
	    	System.out.println("Please enter a valid pattern.");
		} else {
			/*
			 * Calls the preProcessing method to setup the bad character table which
			 * will be used to determine how much characters to jump mismatch is
			 * detected. 
			 * */
			int charTable[] = preProcessing(pattern);
        
        
        	/*
        	 * Loop while index i has not reach the position of the last possible occurrence
        	 * */
        	while (i <= (n - m)) {
        		/*
        		 * Start comparison from the last character in the pattern
        		 * */
        		j = m - 1;
        		
        		/*
        		 * Decrement j as comparison is done from back to front unlike the other
        		 * 2 algorithms.
        		 * */
        		while (j >= 0 && pattern[j] == text[i+j]) {
        			j--;
        		}
        		
        		/*
        		* When a match is found, print the position it starts at and continue
        		* to search for more matches in the text
        		* */
        		if (j < 0) {
        			System.out.println("Found at position " + i);
        			patternsFound++;
        			
        			/*
        			 * Ensure that shift stay within boundary of text. Shift pattern by 1 if out of bound will occur.
        			 * */
        			if ((i + m) < n) {
        				i += (m - charTable[text[i]]);
        			} else {
        				i++;
        			}
        		} else {
                	/*
                	 * Align last occurrence of bad character or shift by 1 if the
                	 * last occurrence is to the right of current character.
                	 * In the event last occurrence is to the right of the current character,
                	 * negative shift occurs. By comparing using Max() function with 1 we ensure
                	 * that the pattern does not move left.
                	 * */
                	i += Math.max(1, j - charTable[text[i+j]]);
        		}
        	} // end of while loop
		}
		
        System.out.println("Patterns found: " + patternsFound);
        System.out.println("====End of Program====");
	} // end of Main method
	
	/*
	 * Bad Character Heuristic Pre-processing
	 * We prepare a character that will be filled up with the last occurrence of a particular
	 * character in the pattern given by the user
	 * */
	static int[] preProcessing(char[] pattern) {
		/*
		 * 127 length to accomodate all ASCII characters from [NULL] to [DEL]
		 * Importantly includes A-Z, a-z
		 * We are assuming in this project that there are only lower and/or uppercase alphabets
		 * in the genome sequence, thus any number more than 122 (which is z) would realistically
		 * be fine.
		 * */
		int[] table = new int[127];
       
		/*
		 * initializing the last occurrence of a particular character from the pattern
		 * The last occurrence will be stored in the position of where it would usually occupy
		 * in an ASCII table 
		 * */
        for (int i = 0; i < pattern.length; i++) {
    	    table[(int) pattern[i]] = i;
        }
       
        return table;
   } // end of preProcessing
}
