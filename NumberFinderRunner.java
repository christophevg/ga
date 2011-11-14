/**
 * Runner for the NumberFinder
 * 
 * Find 100 numbers in range
 * 
 * Author: Christophe VG
 */
 
public class NumberFinderRunner {
  public static void main( String[] args ) {

    new NumberFinder( 0, 10000, 100 ) {

      protected FitnessFunction<Integer> createFitnessFunction() {
        return new FitnessFunction<Integer>() {
          public int calculate(GA<Integer> ga) {
            int fitness = 0;
            for( int c=0; c < ga.getLength(); c++ ) {
              fitness += ga.getCell(c) == c ? 0 : 1;
            }
            return fitness;
          }
        };
      }

    }.start();

  }
}