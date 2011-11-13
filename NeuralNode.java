/**
 * A Node in a NeuralNetwork
 * 
 * Author: Christophe VG
 */

import java.util.List;
import java.util.ArrayList;

public class NeuralNode {
  double localValue = 0;

  // we keep track of our incoming and outgoing connections
  List<NeuralConnection> incoming = new ArrayList<NeuralConnection>();
  List<NeuralConnection> outgoing = new ArrayList<NeuralConnection>();

  public NeuralNode() {}

  // we can give a value to a Node, this should normally only be used on input
  // nodes, but we're not going to enforce it here (not yet)
  public NeuralNode setValue(double value) {
    this.localValue = value;
    return this;
  }

  // adds an incoming connection. through these connections we can fetch the
  // value of our upstream connected nodes
  public NeuralNode addIncoming(NeuralConnection connection) {
    this.incoming.add(connection);
    return this;
  }

  // adds an outgoing connection. this is mostly used for administrative 
  // reasons, because weights are applied on these outgoing connections
  public NeuralNode addOutgoing(NeuralConnection connection) {
    this.outgoing.add(connection);
    return this;
  }

  // we give access to the list of outgoing connections, to allow application
  // of weights
  public List<NeuralConnection> getOutgoing() {
    return this.outgoing;
  }

  // we get the value of all of our upstream connected nodes through the
  // connections, sum all of them and add that to our localValue.
  public double getValue() {
    double value = this.localValue;
    for( NeuralConnection connection : this.incoming ) {
      value += connection.getValue();
    }
    return value;
  }
}
