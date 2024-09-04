/*
1) Program Description: Driver
2) Authors: Rodolfo Lopez, Andrew Kirrane, Julia Paley
3) Last Modified: 10/15/20201 
*/

import java.io.*;
import java.util.*;

public class proj1 extends Object {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(true) { //allows program to be run multiple times
            System.out.println("Welcome to my first neural network – A Perceptron Net!");
            program(sc);
            System.out.println();
            System.out.println("Do you want to run the program again (y for yes and n for no)?:");
            if (!sc.next().equals("y")) {
                break;
            }
        }
        sc.close();
    }

    public static void program(Scanner sc){
        //program attributes
        int training_type; 
        int init_weights;
        int max_epochs;
        String training_input_file_name;
        String training_output_file_name;
        String testing_input_file_name;
        String testing_output_file_name;
        double learning_rate;
        double threshold;
        double threshold_weight;
        String trained_weights_filename;

        //either train weights for NN or test with NN trained weights
        System.out.println("Enter 1 to train using a training data file, enter 2 to use a trained weight settings data file:");
        training_type = Integer.parseInt(sc.next());

        if(training_type ==1) { //for training
            System.out.println("Enter the training data file name:");
            training_input_file_name = sc.next(); //training sample

            ReadX data = new ReadX(training_input_file_name);

            System.out.println("Enter 0 to initialize weights to 0, enter 1 to initialize weights to random values between -0.5 and 0.5:");
            init_weights = Integer.parseInt(sc.next()); //zero or random

            System.out.println("Enter the maximum number of training epochs:");
            max_epochs = Integer.parseInt(sc.next()); //convergence limit

            System.out.println("Enter a file name to save the trained weight settings:");
            training_output_file_name = sc.next(); //trained weights

            System.out.println("Enter the learning rate alpha from 0 to 1 but not including 0:");
            learning_rate = Double.parseDouble(sc.next()); //learning rate

            System.out.println("Enter the threshold theta:");
            threshold = Double.parseDouble(sc.next()); //threshold for activation function

            System.out.println("Enter the threshold to be used for measuring weight changes:");
            threshold_weight = Double.parseDouble(sc.next()); //threshold for weight change

            Perceptron net = new Perceptron(); //create NN
            net.initWeights(init_weights, data.dim_inputs_x, data.num_pairs, data.dim_outputs_t); //init NN weights
            net.train(data.dim_outputs_t, data.inputs_x, data.outputs_t, data.symbols, max_epochs, learning_rate, threshold, threshold_weight, training_output_file_name); //train NN
            
            System.out.println("Enter 1 to test/deploy using a testing/deploying data file, enter 2 to quit:");
            int test_choice = sc.nextInt(); //test or quit
            if(test_choice == 2) {
                System.exit(0); //quit
            }
            else if(test_choice == 1) { //test
                System.out.println("Enter the testing/deploying data file name:");
                testing_input_file_name= sc.next(); //different noise levels for input
                ReadX data2 = new ReadX(testing_input_file_name); //parses input
                System.out.println("Enter a filename to save the testing/deploying results:");
                testing_output_file_name= sc.next(); //sample results
                net.test(data2.inputs_x, data2.outputs_t, data2.num_pairs, data2.symbols, testing_output_file_name); //test NN
            }
        }       
        else if(training_type ==2) { //for testing
            System.out.println("Enter the trained weights settings input data file name:");
            trained_weights_filename = sc.next(); //sampleWeights

            Perceptron net = new Perceptron(); //create NN
            net.setWeights(trained_weights_filename); //set weights for NN
           
            System.out.println("Enter 1 to test/deploy using a testing/deploying data file, enter 2 to quit:");
            int test_choice = sc.nextInt(); //test or quit
            if(test_choice == 2) {
                System.exit(0); //quit
            }
            else if(test_choice == 1) { //test
                System.out.println("Enter the testing/deploying data file name:");
                testing_input_file_name= sc.next(); //different noise levels for input
                ReadX data = new ReadX(testing_input_file_name); //parse input
                System.out.println("Enter a filename to save the testing/deploying results:");
                testing_output_file_name = sc.next(); //sample results
                net.test(data.inputs_x, data.outputs_t, data.num_pairs, data.symbols, testing_output_file_name); //test NN
            }
        }
    }     
}