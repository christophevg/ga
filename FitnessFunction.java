/**
 * FitnessFunction 
 * 
 * an interface to define a fitnessfunction that can be provided to a GA
 * to allow it to calculate its own fitness function.
 * 
 * Author: Christophe VG
 */

public interface FitnessFunction<T> {
  public int calculate(GA<T> ga);
}
