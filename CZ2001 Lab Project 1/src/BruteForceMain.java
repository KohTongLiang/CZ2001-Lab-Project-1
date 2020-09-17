import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.List;


public class BruteForceMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String text = "";
		String pattern = "";//TCAGTGGAGGAAGCG(this has only 1) or AAAATAAAT (this has multiple occurances)
		
		//read in file
        try {
        	text = new String ( Files.readAllBytes( Paths.get("genome.fna") ) );
        } catch (IOException e) {
            e.printStackTrace();
        }

		if(text.length() < 1)
			System.out.println("An error occurred in reading file");
		else {
			
			
		Scanner patternIn = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Enter pattern");
	    pattern = patternIn.nextLine();  // Read user input
	    System.out.println("Pattern is: " + pattern);  // Output user input
		patternIn.close();
		
		List<Integer> seqsFound=new ArrayList<Integer>();
	    int endIndex = pattern.length()-1;
	    int startIndex = 0;
	    while((startIndex+pattern.length())<text.length())
	    {
	      String tempText = text.substring(startIndex, startIndex+pattern.length());

	      if(tempText.equals(pattern))
	      {
	    	//System.out.println("found : " + tempText);//used for debugging
	        seqsFound.add(startIndex);
	        startIndex=startIndex+pattern.length();
	      }
	      else
	      {
	    	//System.out.println("not found : " + tempText + " vs " + pattern);//used for debugging
	        startIndex = startIndex+1;
	        endIndex = endIndex+1;
	      }

	    }
	    if(seqsFound.size() > 0) {
	    System.out.println("Found results for pattern match");
	    	for (int i : seqsFound) 
	            {
	    	 System.out.println("Found at Index: "+ i+ " Position: "+(i+1));
	            }}
	    else
	    	 System.out.println("No results found");
		}
		System.out.println();
	    System.out.println("end of searching Genome");
		
	}

}
