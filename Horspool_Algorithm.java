/* Rahenkamp 2025
 * Some references made to the textbook and ChatGPT
 * input requisite libraries
 */

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

/**
 * 
 */

//class declaration
public class Horspool_Algorithm {

//required values
	public static String pattern;
	public static String readInput = null;
	public static String inputFile = "input.txt";
	
	public static final String DELIM = " ";
	public static final int FIELD_AMT = 1;
	
	
//main method: reads file, sets table, commits and times the search, writes it out
	public static void main(String[] args) {
		readInput = fileReader(inputFile);
		
		int[] table = buildShiftTable(pattern);
		
		long startHorspoolSearch = System.nanoTime();
		int searchNum = horspoolSearch(pattern, readInput, table);
		long endHorspoolSearch = System.nanoTime();

		
		writeOut(searchNum); 
		
		long durationMerge = endHorspoolSearch - startHorspoolSearch;
		System.out.println("Horspool Search Time (milliseconds): " + (durationMerge / 1_000_000.0));
		
	}
	
//performs the search itself. 
	public static int horspoolSearch(String pattern, String text, int[] shiftTable) {
	    int m = pattern.length();
	    int n = text.length();
	    
	    int i = m - 1; 
	    
	    while (i < n) {
	        int k = 0;
	        while (k < m && pattern.charAt(m - 1 - k) == text.charAt(i - k)) {
	            k++;
	        }
	        if (k == m) {
	            return i - m + 1; 
	        } else {

	            i += shiftTable[(int) text.charAt(i)];
	        }
	    }
	    return -1; 
	}
	
	
	
//builds the shift table
	public static int[] buildShiftTable(String pattern) {
	    int m = pattern.length();
	    int[] shiftTable = new int[256];  
	    
	    for (int i = 0; i < shiftTable.length; i++) {
	        shiftTable[i] = m;
	    }
	    
	    for (int j = 0; j < m - 1; j++) {
	        shiftTable[(int) pattern.charAt(j)] = m - 1 - j;
	    }
	    
	    return shiftTable;
	}
	
	
//reads the file
	public static String fileReader(String fileName) {
		
		String searchStream = null;
		
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(fileName));
			
			pattern = fileScanner.next();
			
			fileScanner.nextLine();
			
			searchStream = fileScanner.nextLine();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return searchStream;
	}
	
//writes to the new file
	public static void writeOut(int index)
	{
		try {
            FileWriter writer = new FileWriter("output.txt");
            
            writer.write("Horspool Search Result Index:\n");
            writer.write(Integer.toString(index));
            
            writer.flush();
            writer.close(); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
}
