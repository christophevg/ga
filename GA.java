/**
* Genetic Algorithm
* 
* Generic implementation of a genetic algorithm, based on a list of cells,
* of which the type can be specified through the generic parameter.
* 
* Author: Christophe VG
*/

import java.util.List;
import java.util.ArrayList;


public class GA<T> {

  private ArrayList<T> cells;
  private FitnessFunction<T> ff;
  private int fitness = -1;

  public GA(FitnessFunction<T> ff) {
    this.ff = ff;
    this.cells = new ArrayList<T>();
  }

  public void add(T cell) {
    this.cells.add(cell);
  }

  public void add(List<T> cells) {
    this.cells.addAll(cells);
  }
  
  public T getCell(int i) {
    return this.cells.get(i);
  }

  public int getLength() {
    return this.cells.size();
  }
  
  public List<T> getPart(int start, int end) {
    List<T> copy = new ArrayList<T>(this.cells);
    return copy.subList(start, end);
  }

  public T setCell(int i, T cell) {
    this.resetFitness();
    return this.cells.set(i, cell);
  }
  
  public List<T> getCells() {
    return this.cells;
  }

  public int getFitness() {
    // we're caching the fitness, because this method is call _many_ times
    // by the sortPopulation/sort/compare functionality
    if( ! this.hasFitness() ) {
      this.fitness = this.ff.calculate(this);
    }
    return this.fitness;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    String delim = "";
    for( T c: this.cells ) { 
      sb.append(delim).append(c);
      delim = ", ";
    }
    return sb + " : " + this.getFitness();
  }
  
  private void resetFitness() {
    this.fitness = -1;
  }

  private Boolean hasFitness() {
    return this.fitness != -1;
  }
}
