import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
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

		if (pattern.length == 0) {
			/*
			 * * Condition when user enter a pattern not found in the genome.
			 * */
			System.out.println("Pattern not found.");
		} else {
			//System.out.println("The text is : " + textStr);
			System.out.println("The pattern is : " + patternStr);
		
			/*
			 * Calls the preProcessing method to setup the bad character table which
			 * will be used to determine how much characters to jump mismatch is
			 * detected. 
			 * */
			int charTable[] = preProcessing(pattern);
        
			/*
			 * Initialize some basic variable to be used to loop through the text and
			 * pattern character arrays
			 * */
			int i = 0;;
			int j;
        	int patternsFound = 0;
        
        	/*
        	 * Loop while index i has not reach the position of the last possible occurrence
        	 * */
        	while (i <= (text.length - pattern.length)) {
        		/*
        		 * Loop through the pattern and text backwards from the last element of pattern
        		 * to determine whether characters are matching.
        		 * */
        		j = pattern.length - 1;
        		
        		while (j >=0 && pattern[j] == text[i+j]) {
        			j--;
        		}
        		
        		/*
        		* When a match is found, print the position it starts at and continue
        		* to search for more matches in the text
        		* */
        		if (j < 0) {
        			System.out.println("Found at position " + i);
        			patternsFound++;
        			i += (i + pattern.length < text.length) ? pattern.length - charTable[text[i]] : 1;
        		} else {
                	/*
                	 * Determine how much characters to jump.
                	 * Ensure that a shift always occurs by at least 1 character
                	 * */
                	i += Math.max(1, j - charTable[text[i+j]]);
        		}
        	} // end of while loop
        	
        	
        	if (patternsFound == 0) {
        		System.out.println("Pattern not found");
        	} else {
        		System.out.println("Total patterns found in genome: " + patternsFound);
        	}
		}
	} // end of Main method
	
	/*
	 * Bad Character Heuristic Pre-processing
	 * */
	static int[] preProcessing(char[] pattern) {
       int[] table = new int[256];
       
       for (int i = 0; i < table.length; i++) {
    	   table[i] = -1;
       }
       
       for (int i = 0; i < pattern.length - 1; i++) {
    	   table[(int) pattern[i]] = i;
       }
       
       return table;
   } // end of preProcessing

}
