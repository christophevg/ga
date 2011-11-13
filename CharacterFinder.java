/**
 * Implementation of the Generic Finder
 * 
 * Author: Christophe VG
 */

public class CharacterFinder {

  public static void main(String[] args) {

    GAFinder<Character> finder = new GAFinder<Character>() {
      // a long string
      protected String getTarget() {
        return 
        "A long string, to test how long a string can " +
        "become, before it really takes too many iterations to find an " +
        "optimal solution. So far, it feels acceptable, but how far can " + 
        "I push this? The string/bytecode for a VM that can drive a robot " +
        " will probably be longer?";
      }

      // the length of our string
      protected int getTargetLength() {
        return this.getTarget().length();
      }

      // create a population of random strings
      protected void initPopulation() {
        for( int i=0; i<this.getPopSize(); i++ ) {
          GA<Character> ga = this.createGA();
          for(int c=0; c<this.getTargetLength(); c++ ) {
            int rand = this.getRandomInt(90);
            ga.add((char)(rand+32));
          }
          this.add(ga);
        }
      }
      
      // change one random character in the GA
      protected void mutate(GA<Character> ga) {
        int pos    = this.getRandomInt(ga.getLength());
        int change = this.getRandomInt(90) + 32;
        char newChar = (char)((((int)ga.getCell(pos)) + change) % 122);
        ga.setCell(pos, newChar);
      }

      // fitness = count of wrong characters (zero = optimum)
      protected FitnessFunction<Character> createFitnessFunction() {
        FitnessFunction<Character> fitness = new FitnessFunction<Character>(){
          private String target;
          public void setTarget(Object target) {
            this.target = (String)target;
          }
          public int calculate(GA<Character> ga) {
            int fitness = 0;
            for( int c=0; c < ga.getLength(); c++ ) {
              fitness += (ga.getCell(c) == this.target.charAt(c)) ? 0 : 1;
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