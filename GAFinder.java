/**
 * Genetic Algorithm that tries to find a series of T
 * 
 * Author: Christophe VG
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import java.util.Random;

abstract public class GAFinder<T> {
  private int    popSize        = 2000;
  private int    maxIterations  = 10000;

  private double eliteRate      = 0.10;
  private int    eliteSize      = (int)(popSize * eliteRate);
  private double mutationRate   = 0.65;
  private int    mutationLimit  = (int)(Integer.MAX_VALUE * mutationRate);

  private final  Random random = new Random();

  private List<GA<T>> population     = new ArrayList<GA<T>>();
  private List<GA<T>> nextGeneration = new ArrayList<GA<T>>();

  private FitnessFunction<T> fitnessFunction;

  public GAFinder() {
    this.fitnessFunction = this.createFitnessFunction();
  }
  
  public void start() {
    this.initPopulation();
    this.breed();
  }

  protected int getPopSize() {
    return this.popSize;
  }

  protected GA<T> createGA() {
    return new GA<T>( this.fitnessFunction );
  }
  
  protected int getRandomInt() {
    return this.random.nextInt();
  }

  protected int getRandomInt(int max) {
    return this.random.nextInt(max);
  }
  
  protected void add(GA<T> ga) {
    this.population.add(ga);
  }

  abstract protected int getTargetLength();
  abstract protected FitnessFunction<T> createFitnessFunction();
  abstract protected void initPopulation();
  abstract protected void mutate(GA<T> ga);

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
      GA<T> mate1 = this.population.get(this.getRandomInt(half));
      GA<T> mate2 = this.population.get(this.getRandomInt(half));
      int split   = this.getRandomInt(this.getTargetLength());
      GA<T> offspring = this.createGA();
      offspring.add(mate1.getPart(0, split));
      offspring.add(mate2.getPart(split, this.getTargetLength()));
      if( this.getRandomInt() < this.mutationLimit ) {
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
  
}
