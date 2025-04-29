/* Rahenkamp 2025
 * Some references made to the textbook and ChatGPT
 * 
 * import the libraries
 */

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

/**
 * 
 */

//class declaration
public class Maximum_Bipartite_Matching {
	
//declare and initiate requisite values. 
	public static int[][] readInput;
	public static String[] v = null;
	public static String[] u = null;
	public static String inputFile = "input.txt";
	
	public static final String DELIM = " ";
	public static final int FIELD_AMT = 1;
	
//main method: reads file, sorts through the MBM, writes it out. 
	public static void main(String[] args) {
		readInput = fileReader(inputFile);
		
		
		int[] maximum = maxBM(readInput, v.length, u.length);

		
		writeOut(u, v, maximum);
		
	}
	
	
	
	
//the function that carries out the actual progression and links connections 
    public static int[] maxBM(int[][] adjMatrix, int V, int U) {
        int vSize = V;
        int uSize = U;

        int[] matchU = new int[uSize];
        int[] matchV = new int[vSize];
        Arrays.fill(matchU, -1);
        Arrays.fill(matchV, -1); 

        boolean updated = true;

        while (updated) {
            updated = false;

            Queue<Integer> queue = new LinkedList<>();
            int[] labelV = new int[vSize];
            int[] labelU = new int[uSize];
            Arrays.fill(labelV, -1);
            Arrays.fill(labelU, -1);

            for (int v = 0; v < vSize; v++) {
                if (matchV[v] == -1) {
                    queue.offer(v);
                }
            }

            boolean foundAugmentingPath = false;

            while (!queue.isEmpty() && !foundAugmentingPath) {
                int v = queue.poll();

                for (int u = 0; u < uSize; u++) {
                    if (adjMatrix[v][u] == 1) { 
                        if (matchU[u] == -1) {
                            matchV[v] = u;
                            matchU[u] = v;

                            int currV = v;
                            while (labelV[currV] != -1) {
                                int prevU = labelV[currV];
                                int prevV = labelU[prevU];
                                matchV[prevV] = prevU;
                                matchU[prevU] = prevV;
                                currV = prevV;
                            }

                            foundAugmentingPath = true;
                            updated = true;
                            break;
                        } else if (labelU[u] == -1) {
                            labelU[u] = v;
                            int nextV = matchU[u];
                            labelV[nextV] = u;
                            queue.offer(nextV);
                        }
                    }
                }
            }
        }

        return matchU;
    }
	
	
	
	
//reads file
	public static int[][] fileReader(String fileName) {
		
		ArrayList<Integer> fileInput = new ArrayList<Integer>();
		int[][] filedub = null;
		
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(fileName));
			
			String line = fileScanner.nextLine();
			v = line.split(" ");


			line = fileScanner.nextLine();
			u = line.split(" ");
			
			
			while(fileScanner.hasNextLine())
			{
				while(fileScanner.hasNext())
				{
					
					String fileLine = fileScanner.next();
					String[] splitLines = fileLine.split(DELIM);
					if(splitLines.length != FIELD_AMT)
						continue;
	
					int value = Integer.parseInt(splitLines[0]);
					fileInput.add(value);
				}
			}

			
			int sizeInt = (int) Math.sqrt(fileInput.size());
			filedub = new int[sizeInt][sizeInt];
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
	
//writes to new file
	public static void writeOut(String[] uString, String[] vString, int[] matches)
	{
		try {
            FileWriter writer = new FileWriter("./output.txt");
            
            writer.write("Final Matches\n");
            for(int i = 0; i < vString.length; i++)
    		{
            	writer.write(vString[i] + " ");
    		}
			writer.write(" \n");
			for(int i = 0; i < uString.length; i++)
    		{
            	writer.write(uString[i] + " ");
    		}
			writer.write(" \n");
			for(int i = 0; i < matches.length; i++)
    		{
            	writer.write(matches[i] + " ");
    		}
            
            
            writer.flush();
            writer.close(); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
