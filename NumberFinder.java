/**
 * Implementation of the Generic Finder
 * Finds a list of numbers.
 * 
 * Author: Christophe VG
 */

abstract public class NumberFinder extends GAFinder<Integer> {
  // fitness-function-builder is only required method
  abstract protected FitnessFunction<Integer> createFitnessFunction();

  private int lower, upper, size;

  // create a NumberFinder for size numbers between lower and upper
  public NumberFinder( int lower, int upper, int size ) {
    this.lower = lower;
    this.upper = upper;
    this.size  = size;
  }
  
  // create an initial population of random numbers
  protected void initPopulation() {
    for( int i=0; i<this.getPopSize(); i++ ) {
      GA<Integer> ga = this.createGA();
      for(int c=0; c<this.size; c++ ) {
        ga.add(this.getRandomNumber());
      }
      this.add(ga);
    }
  }
      
  // change one random number in the GA
  protected void mutate(GA<Integer> ga) {
    int pos = this.getRandomInt(ga.getLength());
    ga.setCell(pos, this.getRandomNumber());
  }

  // return a random number between de given boundaries
  private int getRandomNumber() {
    return this.lower + this.getRandomInt(this.upper-this.lower);
  }
}
