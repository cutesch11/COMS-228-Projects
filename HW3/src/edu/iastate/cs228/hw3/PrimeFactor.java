package edu.iastate.cs228.hw3;

/**
 * @author Caleb Utesch
 */

public class PrimeFactor 
{
	public int prime; 		 // prime factor
	public int multiplicity; // number of times the prime factor appears in a factorization

	/**
	 * Precondition: p is a prime number.  
	 * 
	 * @param p	 prime
	 * @param m  multiplicity
	 * @throws IllegalArgumentException if m < 1 
	 */
	public PrimeFactor(int p, int m) throws IllegalArgumentException
	{
		if(m < 1) throw new IllegalArgumentException();
		
		else{
		prime = p;
		multiplicity = m;
		}
	}

	@Override
	public PrimeFactor clone() 
	{
		return new PrimeFactor(prime, multiplicity);
	}

	/**
	 * Prints out, for instance "2^3" if prime == 2 and multiplicity == 3, or "5" if 
	 * prime == 5 and multiplicity == 1.
	 */
	@Override
	public String toString() 
	{
		String pFactor = "";
		
		if(multiplicity == 1){
			pFactor += prime;
		}
		
		else{
		pFactor += prime + "^" + multiplicity;
		}
		
		return pFactor; 
	}
	
	 @Override
	 public boolean equals(Object o) {
	     if (o == null || !(o instanceof PrimeFactor))
	          return false;
	     PrimeFactor pf = (PrimeFactor) o;
	     return prime == pf.prime && multiplicity == pf.multiplicity;
	 }
}
