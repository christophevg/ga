/**
 * Implementation of the Generic Finder
 * Finds a list of numbers.
 * 
 * Author: Christophe VG
 */

public class NumberFinder {

  public static void main(String[] args) {

    GAFinder<Integer> finder = new GAFinder<Integer>() {
      // a "long" list of numbers (Int is good enough, the fitness function
      // can e.g. divide it by 1000 to get decimal numbers
      protected Integer[] getTarget() {
        return new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
                              11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
      }

      // the length of our string
      protected int getTargetLength() {
        return this.getTarget().length;
      }

      // create a population of random numbers
      protected void initPopulation() {
        for( int i=0; i<this.getPopSize(); i++ ) {
          GA<Integer> ga = this.createGA();
          for(int c=0; c<this.getTargetLength(); c++ ) {
            ga.add(this.getRandomNumber());
          }
          this.add(ga);
        }
      }
      
      private int getRandomNumber() {
        return this.getRandomInt(10000);
      }
      
      // change one random number in the GA
      protected void mutate(GA<Integer> ga) {
        int pos = this.getRandomInt(ga.getLength());
        ga.setCell(pos, this.getRandomNumber());
      }

      // fitness
      protected FitnessFunction<Integer> createFitnessFunction() {
        FitnessFunction<Integer> fitness = new FitnessFunction<Integer>(){
          private Integer[] target;
          public void setTarget(Object target) {
            this.target = (Integer[])target;
          }
          public int calculate(GA<Integer> ga) {
            int fitness = 0;
            for( int c=0; c < ga.getLength(); c++ ) {
              fitness += ga.getCell(c) == this.target[c] ? 0 : 1;
            }
            return fitness;
          }
        };
        fitness.setTarget(this.getTarget());
        return fitness;
      }
    };

    finder.start();
  }
}