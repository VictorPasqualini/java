import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

// Create a Scanner object to read input from stdin.
// Read a full line of input from stdin and save it to our variable, inputString.
// Close the scanner object, because we've finished reading 
// all of the input from stdin needed for this challenge.
// Print a string literal saying "Hello, World." to stdout.

public class Solution {    
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in); 
		
		String inputString = scan.nextLine(); 

		scan.close(); 
      
		System.out.println("Hello, World.");
        System.out.print(inputString);
	}
}