# Perceptron Neural Network

This project implements a simple perceptron neural network for character recognition. The perceptron is trained on a set of input patterns representing letters and can then classify new input patterns.

## Authors

- Rodolfo Lopez
- Andrew Kirrane
- Julia Paley

## Date

10/15/2021

## Files

- `proj1.java`: Main driver program
- `Perceptron.java`: Perceptron neural network implementation
- `ReadX.java`: Utility for reading input data files
- `WriteW.java`: Utility for writing trained weights to file
- `WriteResult.java`: Utility for writing test results to file

## Usage

1. Compile all Java files:

   ```
   javac *.java
   ```

2. Run the main program:

   ```
   java proj1
   ```

3. Follow the prompts to:
   - Choose training or testing mode
   - Specify input/output filenames
   - Set training parameters (if in training mode)

## Input File Format

The input files (for both training and testing) should follow this format:

```
<number of inputs>
<number of outputs>
<number of sample pairs>
<input pattern 1>
<output pattern 1>
<symbol 1>
<input pattern 2>
<output pattern 2>
<symbol 2>
...
```

## Output Files

- Training mode generates a weights file with the trained network parameters.
- Testing mode generates a results file showing the actual vs. classified outputs.

## Parameters

- Learning rate
- Maximum epochs
- Threshold
- Weight threshold

## Notes

- The perceptron uses a simple step activation function.
- Weights can be initialized to zero or random values.
- The network trains until convergence or the maximum number of epochs is reached.
