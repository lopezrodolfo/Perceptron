/*
This writes the sample results file 
*/

import java.io.*;

public class WriteResult {

    public WriteResult(String fileName, int[][] outputs_t, int[][] y, String[] symbols) {
        try {
            FileWriter writer = new FileWriter(fileName);
            
            for(int i = 0; i < y.length; i++){
                writer.write("Actual output:\n"  + symbols[i].substring(0, 1) + "\n");
                for(int j = 0; j < outputs_t[i].length; j++){
                    writer.write(outputs_t[i%7][j] + " ");  //writes actual output
                }
                writer.write("\nClassified output:\n"  + symbols[i].substring(0, 1) + "\n"); 
                for(int j = 0; j < outputs_t[i].length; j++){
                    writer.write(y[i][j] + " ");  //writes NN classified output
                }
                writer.write("\n\n");
            }

            
            writer.flush();
            writer.close();
            
        }
        catch(FileNotFoundException e) {
            e.getMessage();
        }
        catch(IOException e) {
            e.getMessage();
        }
    }
}
