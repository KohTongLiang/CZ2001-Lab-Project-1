import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		String textStr = "";
		// example search: TCAGTGGAGGAAGCG
		 
        try {
        	textStr = new String ( Files.readAllBytes( Paths.get("genome.fna") ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Please enter the pattern you wish to search: ");
        Scanner sc = new Scanner(System.in);
        String patternStr = sc.nextLine();
		
		char[] text = textStr.toCharArray();
		char[] pattern = patternStr.toCharArray();

		if (pattern.length == 0) {
			System.out.println("Pattern not found.");
		} else {
			System.out.println("The text is : " + textStr);
			System.out.println("The pattern is : " + patternStr);
		
			// bad character heuristic pre-processing
			int charTable[] = preProcessing(pattern);
        
			int i = pattern.length - 1; // index for text
			int j; // index for pattern
        	int patternsFound = 0;
        
        	while (i < text.length) {
        		for (j = pattern.length - 1; pattern[j] == text[i]; i--, j--)  {
        			if (j == 0) {
        				System.out.println("Found at poisition " + i);
        				patternsFound++;
        				break;
        			}
        		} // end of comparison at position i.
            
        		i += Math.max(1, charTable[text[i]]);
        	} // end of while loop
        	
        	if (patternsFound == 0) {
        		System.out.println("Pattern not found");
        	} else {
        		System.out.println("Total patterns found in genome: " + patternsFound);
        	}
		}
	} // end of Main method
	
	// bad character heuristic pre-processing
	static int[] preProcessing(char[] pattern) {
       int[] table = new int[256];
       
       for (int i = 0; i < table.length; i++) {
    	   table[i] = pattern.length;
       }
       
       for (int i = 0; i < pattern.length - 1; i++) {
    	   table[pattern[i]] = pattern.length - 1 - i;
       }
       
       return table;
   } // end of preProcessing

}
