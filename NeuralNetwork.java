/**
 * A Neural Network
 * 
 * Author: Christophe VG
 */
 
import java.util.List;
import java.util.ArrayList;

class NeuralNetwork {
  // we store all layers of NeuralNodes in a 2 dimension-list of Nodes
  private List<List<NeuralNode>> nodes = new ArrayList<List<NeuralNode>>();

  // create a neural network consisting of an input layer, zero or more hidden
  // layers and one output leayer. 
  // we add the layers in reverse order, because we need to be able to connect
  // cells to the next layer. so we push each new layer on top, not the end.
  public NeuralNetwork( int input, int[] hidden, int output ) {
    this.nodes.add(0, this.createLayer(output));
    for( int l=hidden.length-1; l>=0; l-- ) {
      this.nodes.add(0, this.createLayer(hidden[l]));
    }
    this.nodes.add(0, this.createLayer(input));
  }

  // creates a layer of the given number of nodes
  private List<NeuralNode> createLayer(int size) {
    List<NeuralNode> layer = new ArrayList<NeuralNode>();
    for( int n=0; n<size; n++ ) {
      layer.add(this.createNeuralNode());
    }
    return layer;
  }

  // creates a node and connects it to the currently top-level layer
  private NeuralNode createNeuralNode() {
    NeuralNode node = new NeuralNode();
    if( this.nodes.size() > 0 ) {
      for( NeuralNode next : this.nodes.get(0) ) {
        new NeuralConnection(node, next);
      }
    }
    return node;
  }
  
  // apply a list of weights on the NeuralNetwork, by applying them
  // sequentially to each connection in the network
  public NeuralNetwork applyWeights( double[] weights ) {
    int w=0;
    for( List<NeuralNode> layer : this.nodes ) {
      for( NeuralNode node : layer ) {
        for( NeuralConnection connection : node.getOutgoing() ) {
          connection.applyWeight(weights[w++]);
        }
      }
    }
    return this;
  }
  
  // give a list of input values to the input-level Nodes
  public NeuralNetwork giveInput( double[] inputs ) {
    int i=0;
    for( NeuralNode node : this.nodes.get(0) ) {
      node.setValue(inputs[i++]);
    }
    return this;
  }

  // get the values from the output nodes in the network
  public double[] getOutput() {
    double[] outputs = new double[this.nodes.get(this.nodes.size()-1).size()];
    int o=0;
    for( NeuralNode node : this.nodes.get(this.nodes.size()-1) ) {
      outputs[o++] = node.getValue();
    }
    return outputs;
  }
  
}
