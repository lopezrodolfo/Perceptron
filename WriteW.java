/*
This writes the sample weights file
*/

import java.io.*;

public class WriteW {

    public WriteW(String fileName, String[] symbols, double[][] weights, double[] weights_bias, double threshold) {
        try {
            FileWriter writer = new FileWriter(fileName); 

            writer.write(weights[0].length + "\n"); //write dim inputs x
            writer.write(weights_bias.length + "\n"); //write dim outputs t
            writer.write(String.valueOf(threshold) + "\n\n"); //write threshold
            
            for(int i=0; i < weights_bias.length; i++) {
                writer.write(String.valueOf(weights_bias[i]) + " "); //write bias
            }
            writer.write("\n\n");
            for(int i=0; i < weights_bias.length; i++) {
                for(int j=0; j < weights[i].length; j++) {
                    writer.write(String.valueOf(weights[i][j]) + " "); //write weights
                }
                writer.write("\n\n");
                writer.write(symbols[i].substring(0, 1)); //write symbols
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
