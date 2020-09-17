
public class BoyerMoore {

	public BoyerMoore() {
		// TODO Auto-generated constructor stub
	}
	
	public void GoodSuffixRule() {
		System.out.println("GoodSuffixRule");
	}
	
	public int BadCharacterRule(char[] pattern, char c, int position) {
		int shift = 0;
		
		for (int i = position; i >= 0; i--) {
			if (pattern[i] == c) {
				return i; // returns next occurence of bad character
			} else {
				shift++;
			}
		} // end of for loop
		
		return shift; //shifts all the way to start of pattern
	} // end of bad character rule

}
