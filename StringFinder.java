/**
 * Genetic Algorithm that tries to find a String
 * 
 * Author: Christophe VG
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import java.util.Random;

public class StringFinder {
  private int    popSize        = 2000;
  private int    maxIterations  = 10000;

  private double eliteRate      = 0.10;
  private int    eliteSize      = (int)(popSize * eliteRate);
  private double mutationRate   = 0.65;
  private int    mutationLimit  = (int)(Integer.MAX_VALUE * mutationRate);

  private String target         = "";
  private int    targetLength   = 0;

  private final  Random random = new Random();

  private List<GA<Character>> population     = new ArrayList<GA<Character>>();
  private List<GA<Character>> nextGeneration = new ArrayList<GA<Character>>();

  private FitnessFunction<Character> fitnessFunction;

  public StringFinder(String string) {
    this.target = string;
    this.targetLength = string.length();

    this.createFitnessFunction();
    this.initPopulation();
    this.breed();
  }
  
  private void createFitnessFunction() {
    this.fitnessFunction = new FitnessFunction<Character>() {
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
    this.fitnessFunction.setTarget(this.target);
  }
  
  private void initPopulation() {
    for( int i=0; i<this.popSize; i++ ) {
      GA<Character> ga = this.createGA();
      // populate with random string
      for(int c=0; c<this.targetLength; c++ ) {
        int rand = this.random.nextInt(90);
        ga.add((char)(rand+32));
      }
      this.population.add(ga);
    }
  }
  
  private GA<Character> createGA() {
    return new GA<Character>( this.fitnessFunction );
  }
  
  private void breed() {
    for( int i=0; i<this.maxIterations; i++ ) {
      this.sortPopulation();
      if( i % 100 == 0 ) {
        System.out.println( i + ". " + this.population.get(0) );
      }
      if( this.population.get(0).getFitness() == 0 ) {
        System.out.println( i + ". " + this.population.get(0) );
        break;
      }
      this.mate();
      this.acceptNextGeneration();
    }
  }

  private void sortPopulation() {
    Collections.sort(this.population, new Comparator<GA>(){
      public int compare(GA ga1, GA ga2) {
        return ( ga1.getFitness() < ga2.getFitness() ) ? 0 : 1;
      }
    } );
  }
  
  private void mate() {
    this.selectElite();
    for( int i=this.eliteSize; i<this.popSize; i++ ) {
      int half = this.popSize/2;
      GA<Character> mate1 = this.population.get(this.random.nextInt(half));
      GA<Character> mate2 = this.population.get(this.random.nextInt(half));
      int split = this.random.nextInt(this.targetLength);
      GA<Character> offspring = this.createGA();
      offspring.add(mate1.getPart(0, split));
      offspring.add(mate2.getPart(split, this.targetLength));
      if( this.random.nextInt() < this.mutationLimit ) {
        this.mutate(offspring);
      }
      this.nextGeneration.add(offspring);
    }
  }
  
  private void acceptNextGeneration() {
    this.population.clear();
    this.population.addAll(this.nextGeneration);
  }

  private void selectElite() {
    this.nextGeneration.clear();
    this.nextGeneration.addAll(this.population.subList(0, this.eliteSize));
  }

  private void mutate(GA<Character> ga) {
    int pos    = this.random.nextInt(ga.getLength());
    int change = this.random.nextInt(90) + 32;
    char newChar = (char)((((int)ga.getCell(pos)) + change) % 122);
    ga.setCell(pos, newChar);
  }
  
  public static void main(String[] args) {
    new StringFinder("A long string, to test how long a string can become, " +
                 "before it really takes too many iterations to find an " +
                 "optimal solution. So far, it feels acceptable, but how " +
                 "far can I push this? The string/bytecode for a VM that " +
                 "can drive a robot will probably be longer?" );
  }
}
