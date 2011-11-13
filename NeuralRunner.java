/**
 * A demo for the NeuralNetwork class
 * 
 * Author: Christophe VG
 */

import java.util.Arrays;

public class NeuralRunner {
  public static void main(String[] args) {
    // create a neural network
    // 2 input cells, 2 hidden layers of each 4 cells, 1 output cell
    NeuralNetwork nn = new NeuralNetwork( 2, new int[] { 4, 4 }, 1 );
    // this network requires 2 * 4 (8) weights between the input cells and
    // the first hidden layer, 4 * 4 (16) between the hidden layers and
    // 4 * 1 (4) between the second hidden layer, total 28 weights.
    nn.applyWeights( new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                                    1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                                    1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                                    1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 } );
    // let's see what comes out
    nn.giveInput( new double[] { 1.0, 1.0 } );
    
    System.out.println( Arrays.toString(nn.getOutput()) );
  }
}
