/*
This defines the perceptron NN. Weights for the net can be initialized at random or at zero. 
If the NN is trained, then the optimal weights for the NN are determined after a certain amount of epochs. 
After training, the NN can be tested using a variety of data samples with any number of dimensions. 
*/


import java.io.*;
import java.util.*;
import java.util.concurrent.*; //used to init random weights

public class Perceptron extends Object {
    //perceptron attributes
    int dim_inputs_x;
    int dim_outputs_t;
    int[][] inputs_x;
    int[][] outputs_t;

    int num_pairs;
    int init_weights;
    double[][] weights;
    double[] weights_bias;
    double threshold;
   
    String[] symbols;

    public Perceptron() {} //default constructor 

    public void initWeights(int init_weights, int dim_inputs_x, int num_pairs, int dim_outputs_t) { //init NN weights
        this.init_weights = init_weights;
        this.dim_inputs_x = dim_inputs_x;
        this.num_pairs = num_pairs;
        this.dim_outputs_t = dim_outputs_t;
       
        weights = new double[this.num_pairs][this.dim_inputs_x]; //init weights array
        weights_bias = new double[this.dim_outputs_t]; //init weights bias array

        if(this.init_weights == 0){ //init weights to zero
            Arrays.fill(this.weights_bias,1); //bias always one
            for(int i = 0; i < this.num_pairs; i++){ //sample pairs from 0-22
                for(int j=0; j < this.dim_inputs_x; j++) { //inputs from 0-63
                    this.weights[i][j] = 0; //init weight
                }
            }
        } 
        else if(this.init_weights == 1){ //init weights to random 
            double min = -0.5; //lowerbound
            double max = 0.5; //upperbound
            Arrays.fill(this.weights_bias,1); //bias always one
            for(int i = 0; i < this.num_pairs; i++){ //i in sample pairs 
                for(int j=0; j < this.dim_inputs_x; j++) { //j in inputs x
                    this.weights[i][j] = ThreadLocalRandom.current().nextDouble(min, max); //init weight
                }
            }
        }
    }

    public void train(int dim_outputs_t, int[][] inputs_x, int[][] outputs_t, String[] symbols, int max_epochs, double learning_rate, double threshold, double threshold_weight, String training_output_file_name) { //train NN
        boolean converged = false; //NN not trained
        int epoch_count = 0;
        double y_in;
        int[] y = new int[dim_outputs_t]; //init output array

        while(!converged && epoch_count <= max_epochs) { //train unitl weights are optimized and convergence limit is not reached
            double max_dW = 0;
            for(int i=0; i < num_pairs; i++) { //i in sample pairs 
                for(int j=0; j < dim_outputs_t; j++ ) { //j in outputs t
                    y_in  = weights_bias[j]; //access bias
                    for(int k=0; k < dim_inputs_x; k++) {
                        y_in += inputs_x[i][k] * weights[j][k]; //compute y sum of inputs times weights plus bias
                    }
                    
                    //activation function with y sum as input
                    if(y_in > threshold) { 
                        y[j] = 1;
                    }
                    else if(y_in < threshold) {
                        y[j] = -1;
                    }
                    else {
                        y[j] = 0;
                    }
                }
                
                //update weights 
                for(int j = 0; j < dim_outputs_t; j++){ //j in outputs t
                    if(y[j] != outputs_t[i][j]) { //y does not equal target
                        weights_bias[j] = weights_bias[j] + learning_rate * outputs_t[i][j]; //update bias
                        for (int k=0; k < dim_inputs_x; k++) { //k in inputs x
                            double dW = learning_rate * inputs_x[i][k] * outputs_t[i][j]; //calc change weight
                            weights[j][k] = weights[j][k] + dW; //update weight
                            if(Math.abs(dW) > max_dW){ //find max weight change
                                max_dW = dW;
                            }
                        }
                    }
                }
            }
            if(Math.abs(max_dW) < threshold_weight){ //weight change cannot exceed the weight's max threshold
                converged = true; //NN trained
            }
            epoch_count ++; //one epoch of training
        }
        if(converged = true){ //trained
            System.out.println("Training converged after " + epoch_count + " epochs");
            WriteW trained_weights_file = new WriteW(training_output_file_name, symbols, weights, weights_bias, threshold); //write sample weights file
            
        }
        
    }
    
    public void setWeights(String trained_weights_filename) { //reads from sample weights and sets NN weights
        try {
            File infile = new File(trained_weights_filename);
            Scanner sca = new Scanner(infile);

            this.dim_inputs_x = sca.nextInt(); //read dim inputs x
            this.dim_outputs_t = sca.nextInt(); //read dim outputs t
            this.threshold = sca.nextDouble(); //read threshold
            this.weights_bias = new double[dim_outputs_t]; 
            this.weights = new double[dim_outputs_t][dim_inputs_x];

            sca.nextLine();

            for(int i=0; i < this.dim_outputs_t; i++) { //for i in outputs t
                this.weights_bias[i] = sca.nextDouble(); //set bias
            }

            sca.nextLine();

            for(int i = 0; i < this.dim_outputs_t; i++){ //for i in outputs t
                for(int j = 0; j < this.dim_inputs_x; j++){ //for j in inputs x
                    this.weights[i][j] = sca.nextDouble(); //set weight
                }
                sca.nextLine();
                sca.next();
                sca.nextLine();
            }    
            sca.close();
        }
        catch(FileNotFoundException e) {
            e.getMessage();
        }
    }

    public void test(int[][] inputs_x, int[][] outputs_t, int num_pairs, String[] symbols, String testing_output_filename) { //test NN
        double y_in; 
        int[][] y = new int[num_pairs][outputs_t.length]; 
        for(int i=0; i < num_pairs; i++) { //for i in sample pairs
            for(int j=0; j < outputs_t[i].length; j++ ) { //for j in weights
                y_in  = weights_bias[j];  //access bias
                for(int k=0; k < dim_inputs_x; k++) { //for k in inputs x
                    y_in += inputs_x[i][k] * weights[j][k]; //compute y sum of inputs times weights plus bias
                }

                //activation function
                if(y_in > this.threshold) {
                    y[i][j] = 1;
                }
                else if(y_in < this.threshold) {
                    y[i][j] = -1;
                }
                else {
                    y[i][j] = 0;
                }
            }
        } 
        WriteResult testing_output = new WriteResult(testing_output_filename, outputs_t, y, symbols); //write NN results
    }
}