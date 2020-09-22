import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class KMP {

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
	    * Initialize variables to be used during the comparisons
	    * lps[] will be the pre-processed array used to determine which index in pattern to start comparing from during a mismatch
	    * */
	   int m = pattern.length; 
       int n = text.length; 
       int lps[] = new int[m]; 
       int j = 0; // pattern index
       int i = 0; // text index
       boolean noPattern = true;
       
       if (m == 0) {
    	   System.out.println("Please enter a valid pattern.");
       } else {
    	   /*
            * Call pre-processing method
            * */
           lps = preProcessing(pattern, m, lps);
           
           /*
            * loop until last possible position of pattern in the text
            * */
           while (i < n) {
        	   
        	   /*
        	    * Increment index of both pattern and text until a mismatch occurs
        	    * */
               if (pattern[j] == text[i]) { 
                   j++;
                   i++;
               }
               
               
               /*
                * Process mismatch
                * If index j is same as length of pattern, means that a pattern is found in the text.
                * */
               if (j == m) {
                   System.out.println("Found pattern at index " + (i - j));
                   if (noPattern) noPattern = false;
                   j = lps[j - 1]; 
               } else if (i < n && pattern[j] != text[i]) {  
            	   /*
            	    * Event where there is a mismatch,
            	    * If index j is not 0, we do not shift i but instead use the pre-processed array to determine which index
            	    * in the pattern do we start at for the next set of comparisons
            	    * If index j is 0, simply shift index i by 1 to start new comparisons
            	    * */
            	   if (j != 0) {
            		   j = lps[j-1];
            	   } else {
            		   i = i + 1;
            	   }
               }
            } // end of comparison loops
       }

       if (noPattern) System.out.println("No occurrence found.");
       System.out.println("====End of Program====");
	} // end of main method
	
	/*
	 * Pre-processing method
	 * prepare an array of int which will be used to indicate which index in pattern to start comparison from to skip
	 * having to start from first character
	 * */
	static int[] preProcessing(char[] pattern, int m, int lps[]) {
		/*
		 * Initialize some variables for later comparisons
		 * lps[0] wll always start with 0 because no comparison done yet
		 * the leftIndex and rightIndex variables will be used to visualized the pre-processing concept
		 * */
        int leftIndex = 0; 
        int rightIndex = 1;
        lps[0] = 0;
   
        /*
         * Loop until the end of pattern
         * */
        while (rightIndex < m) {
        	/*
        	 * when both characters are the same, they are both shifted and value at rightCharacter
        	 * */
            if (pattern[rightIndex] == pattern[leftIndex]) { 
            	leftIndex++; 
                lps[rightIndex] = leftIndex; 
                rightIndex++; 
            } else {
                if (leftIndex != 0) { 
                	leftIndex = lps[leftIndex - 1]; 
                } else { 
                    lps[rightIndex] = leftIndex; 
                    rightIndex++; 
                }
            }
        } // end of comparison loop
        return lps;
    } // end of preProcessing method
}
