import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.List;


public class BruteForceMain {

	public static void main(String[] args) {
	    System.out.println("====Start of Program====");
	    
		String text = "";
		String pattern = "";
		//TCAGTGGAGGAAGCG(this has only 1) or AAAATAAAT (this has multiple occurances)
		
		//read in file
        try {
        	text = new String ( Files.readAllBytes( Paths.get("genome.fna") ) );
        } catch (IOException e) {
            e.printStackTrace();
        }

		if(text.length() < 1) {
			System.out.println("An error occurred in reading file");
		} else {
		    System.out.println("Please enter the pattern you wish to search: ");
			Scanner patternIn = new Scanner(System.in);  // Create a Scanner object
			pattern = patternIn.nextLine();  // Read user input
			patternIn.close();
		
			List<Integer> seqsFound=new ArrayList<Integer>();
			int endIndex = pattern.length() - 1;
			int startIndex = 0;
			while((startIndex + pattern.length()) < text.length()) {
				String tempText = text.substring(startIndex, startIndex+pattern.length());
				if(tempText.equals(pattern)) {
					seqsFound.add(startIndex);
					startIndex=startIndex + pattern.length();
				} else {
					startIndex = startIndex + 1;
					endIndex = endIndex + 1;
				}
			} // end of while loop
	    
	    	if(seqsFound.size() > 0) {
	    		for (int i : seqsFound) {
	    			System.out.println("Found at Index: "+ i);
	    		}
	    	} else {
	    		System.out.println("No occurrence found.");
	    	}
		}

	    System.out.println("====End of Program====");
	}

}
