/*
This parses the sample training file
*/

import java.io.*;
import java.util.*;

public class ReadX {
    int dim_inputs_x; //reads dim inputs
    int dim_outputs_t; //reads dim outputs
    int num_pairs; //reads sample pairs
    int[][] inputs_x; //reads inputs x array
    int[][] outputs_t; //reads outputs t array
    String[] symbols; //reads symbols
        
    public ReadX(String inputFileName) {
        try{
            File infile = new File(inputFileName);
            Scanner sca = new Scanner(infile);

            this.dim_inputs_x = sca.nextInt();
            this.dim_outputs_t = sca.nextInt();
            this.num_pairs = sca.nextInt();

            this.inputs_x = new int[num_pairs][dim_inputs_x];
            this.outputs_t = new int[num_pairs][dim_outputs_t];
            this.symbols = new String[num_pairs];

            for (int i=0; i < this.num_pairs; i++){
                for (int j=0; j < this.dim_inputs_x; j++){
                    this.inputs_x[i][j] = sca.nextInt();
                }
                for (int k=0; k < this.dim_outputs_t; k++){
                    this.outputs_t[i][k] = sca.nextInt();
                }
                
                sca.nextLine();
                this.symbols[i] = sca.nextLine();
            }
            sca.close();
        }
        catch (FileNotFoundException e){
            e.getMessage();
        }

    }
}