/* Rahenkamp 2025
 * Some references made to the textbook and ChatGPT
 * imports all necessary libraries
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
public class HeapUp_HeapDown {

	
//declares all necessary values
	public static int numKeys;
	public static ArrayList<Integer> readInput = new ArrayList<Integer>();
	public static String inputFile = "input.txt";
	
	public static final String DELIM = " ";
	public static final int FIELD_AMT = 1;
	
//main method: reads the file, puts it into an array, times and heaps in both directions, writes it out
	public static void main(String[] args) {
		readInput = fileReader(inputFile);
		
		int[] unheaped = new int[numKeys];
		for(int i = 0; i < readInput.size(); i++)
		{
			unheaped[i] = readInput.get(i);
		}
		
		long startMaxHeap = System.nanoTime();
		int[] maxHeap = buildMaxHeap(unheaped);
		long endMaxHeap = System.nanoTime();
		
		
		
		long startMinHeap = System.nanoTime();
		int[] minHeap = buildMinHeap(unheaped);
		long endMinHeap = System.nanoTime();
		
		writeOut(maxHeap, minHeap); 
		
		long durationMerge = endMaxHeap - startMaxHeap;
		System.out.println("Max Heap Time (milliseconds): " + (durationMerge / 1_000_000.0));
		
		long durationQuick = endMinHeap - startMinHeap;
		System.out.println("Min Heap Time (milliseconds): " + (durationQuick / 1_000_000.0));

	}
	
//is the baseline for building the heap and returns the final array
	public static int[] buildMaxHeap(int[] arr) {
		int[] sortArray = arr.clone();
	    for (int i =  numKeys / 2 - 1; i >= 0; i--) {
	        maxHeapify(sortArray, numKeys, i);
	    }
	    return sortArray;
	}

//does the actual process of sorting the heap out, placing the lower values on their respective lower branches
	public static void maxHeapify(int[] arr, int n, int i) {
	    int largest = i;
	    int left = 2 * i + 1;
	    int right = 2 * i + 2;
	    
	    if (left < n && arr[left] > arr[largest])
	        largest = left;
	    
	    if (right < n && arr[right] > arr[largest])
	        largest = right;
	    
	    if (largest != i) {
	        int temp = arr[i];
	        arr[i] = arr[largest];
	        arr[largest] = temp;
	        
	        maxHeapify(arr, n, largest);
	    }
	}
	
	
//baseline for the minimum heap
	public static int[] buildMinHeap(int[] arr) {
		int[] sortArray = arr.clone();
	    for (int i = numKeys / 2 - 1; i >= 0; i--) {
	        minHeapify(sortArray, numKeys, i);
	    }
	    return sortArray;
	}

//places higher values on the respective lower branches
	public static void minHeapify(int[] arr, int n, int i) {
	    int smallest = i;
	    int left = 2 * i + 1;
	    int right = 2 * i + 2;
	    
	    if (left < n && arr[left] < arr[smallest])
	        smallest = left;
	    
	    if (right < n && arr[right] < arr[smallest])
	        smallest = right;
	    
	    if (smallest != i) {
	        int temp = arr[i];
	        arr[i] = arr[smallest];
	        arr[smallest] = temp;
	        
	        minHeapify(arr, n, smallest);
	    }
	}
	
//reads the file
	public static ArrayList<Integer> fileReader(String fileName) {
		
		ArrayList<Integer> fileInput = new ArrayList<Integer>();
		
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(fileName));
			
			String numWord = fileScanner.next();
			numKeys = Integer.parseInt(numWord);
			fileScanner.nextLine();
			
			while(fileScanner.hasNext())
			{
				String fileLine = fileScanner.next();
				String[] splitLines = fileLine.split(DELIM);
				if(splitLines.length != FIELD_AMT)
					continue;

				int value = Integer.parseInt(splitLines[0]);
				fileInput.add(value);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		return fileInput;
	}
	
//writes to the new file. 
	public static void writeOut(int[] maxHeap, int[] minHeap)
	{
		try {
            FileWriter writer = new FileWriter("output.txt");
            
            writer.write("MaxHeap\n");
            for (int i = 0; i < maxHeap.length; i++) {
                writer.write(maxHeap[i] + " ");
            }
            
            writer.write("\nMinHeap\n");
            for (int i = 0; i < minHeap.length; i++) {
                writer.write(minHeap[i] + " ");
            }
            
            writer.flush();
            writer.close(); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
