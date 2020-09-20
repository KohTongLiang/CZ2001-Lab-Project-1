import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class KMP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		
		int M = pattern.length; 
        int N = text.length; 
  
        // create lps[] that will hold the longest 
        // prefix suffix values for pattern 
        int lps[] = new int[M]; 
        int j = 0; // index for pat[] 
  
        // Preprocess the pattern (calculate lps[] 
        // array) 
        computeLPSArray(pattern, M, lps); 
  
        int i = 0; // index for txt[] 
        while (i < N) { 
            if (pattern[j] == text[i]) { 
                j++; 
                i++; 
            } 
            if (j == M) { 
                System.out.println("Found pattern "
                                   + "at index " + (i - j)); 
                j = lps[j - 1]; 
            } 
  
            // mismatch after j matches 
            else if (i < N && pattern[j] != text[i]) { 
                // Do not match lps[0..lps[j-1]] characters, 
                // they will match anyway 
                if (j != 0) 
                    j = lps[j - 1]; 
                else
                    i = i + 1; 
            } 
        }
	} // end of main method
	
	static void computeLPSArray(char[] pat, int M, int lps[]) 
    { 
        // length of the previous longest prefix suffix 
        int len = 0; 
        int i = 1; 
        lps[0] = 0; // lps[0] is always 0 
  
        // the loop calculates lps[i] for i = 1 to M-1 
        while (i < M) { 
            if (pat[i] == pat[len]) { 
                len++; 
                lps[i] = len; 
                i++; 
            } 
            else // (pat[i] != pat[len]) 
            { 
                // This is tricky. Consider the example. 
                // AAACAAAA and i = 7. The idea is similar 
                // to search step. 
                if (len != 0) { 
                    len = lps[len - 1]; 
  
                    // Also, note that we do not increment 
                    // i here 
                } 
                else // if (len == 0) 
                { 
                    lps[i] = len; 
                    i++; 
                } 
            } 
        } 
    } // end of computeLPSArray
}
