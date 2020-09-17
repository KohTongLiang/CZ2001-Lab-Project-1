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
		
		System.out.println("The text is : " + textStr);
		System.out.println("The pattern is : " + patternStr);
		
		compare(text, pattern);
	} // end of Main method
	
	static int compare (char[] text, char[] pattern) {
		if (pattern.length == 0) {
			return 0;
		}
		
        int charTable[] = makeCharTable(pattern);
        int offsetTable[] = makeOffsetTable(pattern);
        int i = pattern.length - 1;
        int j;
        
        while (i < text.length) {
            for (j = pattern.length - 1; pattern[j] == text[i]; i--, j--)  {
            	if (j == 0) {
        			System.out.println("Found at poisition " + i);
        			break;
            	}
            } // end of comparison at position i.
            
            // compare which rule (bad character heuristics or good suffix) gives a greater jump
            i += Math.max(offsetTable[pattern.length - 1 - j], charTable[text[i]]);
        } // end of while loop
        
        return -1;
	} // end of compare method
	
	
	static int[] makeCharTable(char[] pattern) {
       int[] table = new int[256];
       
       for (int i = 0; i < table.length; i++) {
    	   table[i] = pattern.length;
       }
       
       for (int i = 0; i < pattern.length - 1; i++) {
    	   table[pattern[i]] = pattern.length - 1 - i;
       }
       
       return table;
   } // 
	 
   /** Makes the jump table based on the scan offset which mismatch occurs. **/
   private static int[] makeOffsetTable(char[] pattern) {
       int[] table = new int[pattern.length];
       int lastPrefixPosition = pattern.length;
       
       for (int i = pattern.length - 1; i >= 0; i--) {
           if (isPrefix(pattern, i + 1)) { 
        	   lastPrefixPosition = i + 1;
           }
           
           table[pattern.length - 1 - i] = lastPrefixPosition - i + pattern.length - 1;
       }
       
       for (int i = 0; i < pattern.length - 1; i++) {
             int slen = suffixLength(pattern, i);
             table[slen] = pattern.length - 1 - i + slen;
       }
       
       return table;
   }
     
   /** function to check if needle[p:end] a prefix of pattern **/
   private static boolean isPrefix(char[] pattern, int p) {
       for (int i = p, j = 0; i < pattern.length; i++, j++) {
    	   if (pattern[i] != pattern[j]) {
                 return false;
    	   }
       }
       
       return true;
   }
   
   /** function to returns the maximum length of the substring ends at p and is a suffix **/
   private static int suffixLength(char[] pattern, int p) {
       int len = 0;
       for (int i = p, j = pattern.length - 1; i >= 0 && pattern[i] == pattern[j]; i--, j--) {
    	   len += 1;
       }
       return len;
   }

}
