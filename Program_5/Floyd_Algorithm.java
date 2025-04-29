/* Rahenkamp 2025
 * Some references made to the textbook and ChatGPT
 * imports requisite libraries
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

/**
 * 
 */

//class declaration
public class Floyd_Algorithm {

	
//requisite values
	public static double[][] readInput;
	public static String inputFile = "input.txt";
	
	public static final String DELIM = " ";
	public static final int FIELD_AMT = 1;
	
	
//main method: reads file, solves the matrix, writes it out
	public static void main(String[] args) {
		readInput = fileReader(inputFile);
		
		double[][] thisMatrix = floydWarshall(readInput);
		
		writeOut(thisMatrix);
	}
	
	
//solves the matrix and returns the new matrix. 
	public static double[][] floydWarshall(double[][] duplex) {
	    int n = duplex.length; 
	    double[][] matrix = duplex.clone();
	    
	    for (int k = 0; k < n; k++) {
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                if (matrix[i][k] + matrix[k][j] < matrix[i][j]) {
	                    matrix[i][j] = matrix[i][k] + matrix[k][j];
	                }
	            }
	        }
	    }
	    return matrix;
	}
	
//reads the file
	public static double[][] fileReader(String fileName) {
		
		ArrayList<Double> fileInput = new ArrayList<Double>();
		double[][] filedub = null;
		
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(fileName));
			
			while(fileScanner.hasNextLine())
			{
				while(fileScanner.hasNext())
				{
					
					String fileLine = fileScanner.next();
					String[] splitLines = fileLine.split(DELIM);
					if(splitLines.length != FIELD_AMT)
						continue;
	
					double value = Double.parseDouble(splitLines[0]);
					fileInput.add(value);
				}
			}

			
			int sizeInt = (int) Math.sqrt(fileInput.size());
			filedub = new double[sizeInt][sizeInt];
			for(int i = 0; i < sizeInt; i++)
			{
				for(int j = 0; j < sizeInt; j++)
				{
					filedub[i][j] = fileInput.get(sizeInt*i+j);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		return filedub;
	}
	
	
//writes to a new file.
	public static void writeOut(double[][] duplex)
	{
		try {
            FileWriter writer = new FileWriter("output.txt");
            
            writer.write("Final Distances\n");
            for(int i = 0; i < duplex.length; i++)
    		{
    			for(int j = 0; j < duplex[0].length; j++)
    			{
    				writer.write(duplex[i][j] + "  ");
    			}
    			writer.write(" \n");
    		}
            
            
            writer.flush();
            writer.close(); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}



}
