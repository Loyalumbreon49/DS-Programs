/* Rahenkamp 2025
 * Some references made to the textbook and ChatGPT
 * Imports all necessary libraries
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
public class MergeSort_Quicksort {

//declare necessary variables
	public static ArrayList<Double> readInput = new ArrayList<Double>();
	public static String inputFile = "input.txt";
	
//honestly, I'm only going to mention these two here. I used the same base read function
//that I had previously written in the past. These are mostly artifacts left from that
//which I didn't see the need to remove. 
	public static final String DELIM = " ";
	public static final int FIELD_AMT = 1;
	
//main method: calls the file reader, calls mergesort and quicksort, then writes and times each
	public static void main(String[] args) {
		readInput = fileReader(inputFile);
		double[] unsorted = new double[readInput.size()];
		for(int i = 0; i < readInput.size(); i++)
		{
			unsorted[i] = readInput.get(i);
		}
		
		long startMerge = System.nanoTime();
		double[] mergeSorted = mergeSort(unsorted);
		
		long endMerge = System.nanoTime();
		
		
		long startQuick = System.nanoTime();
		double[] quickSorted = quickSort(unsorted);

		long endQuick = System.nanoTime();
		
		writeOut(mergeSorted, quickSorted); 
		
		long durationMerge = endMerge - startMerge;
		System.out.println("Merge Sort Time (milliseconds): " + (durationMerge / 1_000_000.0));
		
		long durationQuick = endQuick - startQuick;
		System.out.println("Quick Sort Time (milliseconds): " + (durationQuick / 1_000_000.0));
	}
	
	
//mergesort function
	public static double[] mergeSort(double[] preSort)
	{
		double[] postSort = new double[preSort.length];

		
		if (preSort.length > 1)
		{
			int midpoint = preSort.length/2;
			double[] left = new double[midpoint];
			for(int i = 0; i < midpoint; i++)
			{
				left[i] = preSort[i];
			}
			double[] right = new double[preSort.length - midpoint];
			for(int i = 0; i < right.length; i++)
			{
				right[i] = preSort[midpoint + i];
			}
			left = mergeSort(left);
			right = mergeSort(right);
			int i = 0;
			int j = 0;
			while(i < left.length || j < right.length)
			{
				if (i == left.length)
				{
					postSort[i+j] = right[j];
					j++;
				}
				else if (j == right.length)
				{
					postSort[i+j] = left[i];
					i++;
				}
				else if (left[i] > right[j])
				{
					postSort[i+j] = right[j];
					j++;
				}
				else
				{
					postSort[i+j] = left[i];
					i++;
				}
				
			}
		}
		else
		{
			postSort[0] = preSort[0];
		}
		
		
		return postSort;
	}
	
//quicksort function
	public static double[] quickSort(double[] preSort)
	{
		double[] postSort = new double[preSort.length];
		if (preSort.length > 1)
		{
			double pivot = preSort[0];
			int left = 0;
			int right = 0;
			for(int i = 1; i < preSort.length; i++)
			{
				if (preSort[0] > preSort[i])
				{
					left++;
				}
				else
				{
					right++;
				}
					
			}
			double[] leftGroup = new double[left];
			double[] rightGroup = new double[right];
			left = 0;
			right = 0;
			for(int i = 1; i < preSort.length; i++)
			{
				if (preSort[0] > preSort[i])
				{
					leftGroup[left] = preSort[i];
					left++;
				}
				else
				{
					rightGroup[right] = preSort[i];
					right++;
				}
					
			}
			leftGroup = quickSort(leftGroup);
			rightGroup = quickSort(rightGroup);
			for(int i = 0; i < preSort.length; i*=1)
			{
				if(i < leftGroup.length) {
					for(int j = 0; j < leftGroup.length; j++)
					{
						postSort[i] = leftGroup[j];
						i++;
					}
				}
				else if(i == leftGroup.length) {
					postSort[i] = pivot;
					i++;
				}
				else
				{
					for(int j = 0; j < rightGroup.length; j++)
					{
						postSort[i] = rightGroup[j];
						i++;
					}
				}
				
					
			}
			
		}
		else if(preSort.length == 1)
		{
			postSort[0] = preSort[0];
		}
		
		return postSort;
	}

//reads the file
	public static ArrayList<Double> fileReader(String fileName) {
		
		ArrayList<Double> fileInput = new ArrayList<Double>();
		
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(fileName));
			
			while(fileScanner.hasNext())
			{
				String fileLine = fileScanner.next();
				String[] splitLines = fileLine.split(DELIM);
				if(splitLines.length != FIELD_AMT)
					continue;

				double value = Double.parseDouble(splitLines[0]);
				fileInput.add(value);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		return fileInput;
	}
	
//writes the file to output.txt
	public static void writeOut(double[] sortNum, double[] quickNum)
	{
		try {
            FileWriter writer = new FileWriter("output.txt");
            
            writer.write("mergeSorted\n");
            for (int i = 0; i < sortNum.length; i++) {
                writer.write(sortNum[i] + " ");
            }
            
            writer.write("\nquickSorted\n");
            for (int i = 0; i < quickNum.length; i++) {
                writer.write(quickNum[i] + " ");
            }
            
            writer.flush();
            writer.close(); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}


}



