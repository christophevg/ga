/**
 * Connection between two NeuralNodes in a NeuralNetwork
 * 
 * Author: Christophe VG
 */

public class NeuralConnection {
  NeuralNode from, to;
  double weight = 0;

  // create a connection from a NeuralNode to another NeuralNode
  public NeuralConnection(NeuralNode from, NeuralNode to) {
    this.from = from;
    this.from.addOutgoing(this);
    this.to   = to;
    this.to.addIncoming(this);
  }

  // apply the supplied weight on upstream values
  public NeuralConnection applyWeight(double weight) {
    this.weight = weight;
    return this;
  }

  // gets the value of the upstream node, applies the weight and passes it on
  public double getValue() {
    return this.from.getValue() * this.weight;
  }
}
