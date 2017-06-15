package edu.iastate.cs228.hw3;

/**
 *  
 * @author Caleb Utesch
 *
 */

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class PrimeFactorization implements Iterable<PrimeFactor> {
	private static final long OVERFLOW = -1;
	private long value; // the factored integer
						// it is set to OVERFLOW when the number is greater than
						// 2^63-1,
						// the largest number representable by the type long.

	/**
	 * Reference to dummy node at the head.
	 */
	private Node head;

	/**
	 * Reference to dummy node at the tail.
	 */
	private Node tail;

	private int size; // number of distinct prime factors

	// ------------
	// Constructors
	// ------------

	/**
	 * Default constructor constructs an empty list to represent the number 1.
	 * 
	 * Combined with the add() method, it can be used to create a prime
	 * factorization.
	 */
	public PrimeFactorization() {
		this(1);
	}

	/**
	 * Obtains the prime factorization of n and creates a doubly linked list to
	 * store the result. Follows the algorithm in Section 1.2 of the project
	 * description.
	 * 
	 * @param n
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public PrimeFactorization(long n) throws IllegalArgumentException {
		if (n < 1) {
			throw new IllegalArgumentException();
		}

		value = n;
		long m = n;
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;

		for (int d = 2; d * d <= m; ++d) { // Performs the Direct Search
											// Factorization
			if (isPrime(d))
				if (m % d == 0) {
					m = m / d;
					int pri = d;
					int multi = 1;
					while (m % d == 0) {
						m = m / d;
						multi += 1;
					}
					link(tail, new Node(new PrimeFactor(pri, multi))); // Creates
																		// a new
																		// node
																		// and
																		// adds
																		// it to
																		// the
																		// doubly
																		// linked
																		// list
																		// at
																		// the
																		// end
					size++;
				}
		}
		if (m > 1) {
			int mInt = (int) m;
			link(tail, new Node(new PrimeFactor(mInt, 1)));
			size++;
		}
	}

	/**
	 * Copy constructor. It is unnecessary to verify the primality of the
	 * numbers in the list.
	 * 
	 * @param pf
	 */
	public PrimeFactorization(PrimeFactorization pf) {
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		size = pf.size;
		PrimeFactorizationIterator iter = pf.iterator();
		while (iter.hasNext()) {
			link(tail, new Node(iter.next()));
		}
	}

	/**
	 * Constructs a factorization from an array of prime factors. Useful when
	 * the number is too large to be represented even as a long integer.
	 * 
	 * @param pflist
	 */
	public PrimeFactorization(PrimeFactor[] pfList) {
		ArrayList<Node> nodArray = new ArrayList<Node>();

		for (int i = 0; i < pfList.length; ++i) {
			nodArray.add(new Node(pfList[i].prime, pfList[i].multiplicity));
		}

		head = new Node();
		tail = new Node();
		size = pfList.length;

		head.next = nodArray.get(0);
		nodArray.get(0).previous = head;
		for (int i = 0; i < nodArray.size() - 1; ++i) {
			nodArray.get(i).next = nodArray.get(i + 1);
			nodArray.get(i + 1).previous = nodArray.get(i);
		}
		nodArray.get(size - 1).next = tail;
		tail.previous = nodArray.get(size - 1);
		updateValue();
	}

	// --------------
	// Primality Test
	// --------------

	/**
	 * Test if a number is a prime or not. Check iteratively from 2 to the
	 * largest integer not exceeding the square root of n to see if it divides
	 * n.
	 * 
	 * @param n
	 * @return true if n is a prime false otherwise
	 */
	public static boolean isPrime(long n) {
		if (n == 1 || n == 0)
			return false;
		for (int d = 2; d * d <= n; ++d) {
			if (n % d == 0) {
				return false;
			}
		}
		return true;
	}

	// ---------------------------
	// Multiplication and Division
	// ---------------------------

	/**
	 * Multiplies this.value with another number n. You can do this in one loop:
	 * Factor n and traverse the doubly linked list in the same time. For
	 * details refer to Section 3.1 in the project description. Store the prime
	 * factorization of the product. Update value and size.
	 * 
	 * @param n
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public void multiply(long n) throws IllegalArgumentException {
		if (n < 1) {
			throw new IllegalArgumentException();
		}

		PrimeFactorizationIterator iter = this.iterator();
		long m = n;

		if (iter.cursor == tail) { // Special case for if the list is empty
			for (int d = 2; d * d <= m; ++d) {
				if (m % d == 0) {
					m = m / d;
					int pri = d;
					int multi = 1;
					while (m % d == 0) {
						m = m / d;
						multi += 1;
					}
					link(tail, new Node(pri, multi));
					size++;
				}
			}
			if (m > 1) {
				int mInt = (int) m;
				link(tail, new Node(new PrimeFactor(mInt, 1)));
				size++;
			}
			updateValue();
		}

		else {
			for (int d = 2; d * d <= m; ++d) { // Regular case
				if (m % d == 0) {
					m = m / d;
					int pri = d;
					int multi = 1;
					while (m % d == 0) {
						m = m / d;
						multi += 1;
					}
					Node nd = new Node(pri, multi);

					while (iter.cursor.pFactor.prime < nd.pFactor.prime) {
						iter.next();
					}

					if (iter.cursor.pFactor.prime == nd.pFactor.prime) {
						iter.cursor.pFactor.multiplicity += nd.pFactor.multiplicity;
					}

					else if (iter.cursor.pFactor.prime > nd.pFactor.prime) {
						iter.add(nd.pFactor);
					}

				}
			}

			if (m > 1) { // adding the last node if it has a multiplicity of 1
				int mInt = (int) m;
				Node last = new Node(mInt, 1);
				iter = this.iterator();

				while (iter.hasNext()
						&& iter.cursor.pFactor.prime <= last.pFactor.prime) {
					iter.next();
				}

				if (iter.pending == null
						&& iter.cursor.pFactor.prime == last.pFactor.prime) {
					iter.cursor.pFactor.multiplicity += last.pFactor.multiplicity;
				}

				else if (iter.pending.pFactor.prime == last.pFactor.prime) {
					iter.pending.pFactor.multiplicity += last.pFactor.multiplicity;
					iter = this.iterator();
				}

				else if (iter.cursor.previous.pFactor.prime > last.pFactor.prime) {
					iter.add(last.pFactor);
					iter = this.iterator();
				}

				else {
					iter.add(last.pFactor);
				}
			}
			updateValue();
		}
	}

	/**
	 * Multiplies this.value with another number in the factorization form.
	 * Traverse both linked lists and store the result in this list object. See
	 * Section 3.1 in the project description for details of algorithm.
	 * 
	 * @param pf
	 */
	public void multiply(PrimeFactorization pf) {
		PrimeFactorizationIterator iter1 = this.iterator();
		PrimeFactorizationIterator iter2 = pf.iterator();

		while (iter2.hasNext() && iter1.hasNext()) {
			while (iter1.hasNext()
					&& iter1.cursor.pFactor.prime < iter2.cursor.pFactor.prime) {
				iter1.next();
			}

			if (iter1.hasNext()) {

				if (iter1.cursor.pFactor.prime == iter2.cursor.pFactor.prime) {
					iter1.cursor.pFactor.multiplicity += iter2.cursor.pFactor.multiplicity;
					iter2.next();
				}

				else if (iter1.cursor.pFactor.prime > iter2.cursor.pFactor.prime) {
					iter1.add(iter2.cursor.pFactor);
					iter2.next();
				}
			}
		}
		if (iter2.hasNext()) { // If you get to the end of this and pf still has
								// elements, add them to the end
								// or if you start with an empty list
			while (iter2.hasNext()) {
				iter1.add(iter2.cursor.pFactor);
				iter2.next();
			}
		}
		updateValue();
	}

	/**
	 * Divides this.value by n. Make updates to the list, value, size if
	 * divisible. No update otherwise. Refer to Section 3.2 in the project
	 * description for details.
	 * 
	 * @param n
	 * @return true if divisible false if not divisible
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 */
	public boolean dividedBy(long n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		else if (this.value != -1 && this.value < n) {
			return false;
		}

		if (this.value == n) {
			this.clearList();
			return true;
		}

		else {
			PrimeFactorization Pf = new PrimeFactorization(n);
			this.dividedBy(Pf);
			updateValue();
			return true;
		}
	}

	/**
	 * Division where the divisor is represented in the factorization form.
	 * Update the linked list of this object accordingly by removing those nodes
	 * housing prime factors that disappear after the division. No update if
	 * this number is not divisible by pf. Algorithm details are given in
	 * Section 3.2.
	 * 
	 * @param pf
	 * @return true if divisible by pf false otherwise
	 */
	public boolean dividedBy(PrimeFactorization pf) {
		if (this.value != -1 && pf.value != -1 && this.value < pf.value) {
			return false;
		}

		else if (this.value != -1 && pf.value == -1) {
			return false;
		}

		if (this.value == pf.value) {
			this.clearList();
			return true;
		}

		else {
			PrimeFactorization copy = new PrimeFactorization(this);
			PrimeFactorizationIterator iterCopy = copy.iterator();
			PrimeFactorizationIterator iterPf = pf.iterator();

			while (iterPf.cursor != pf.tail) {
				while (iterCopy.cursor.pFactor.prime < iterPf.cursor.pFactor.prime) {
					if (!iterCopy.hasNext() && iterPf.hasNext()) {
						return false;
					}

					else {
						iterCopy.next();
					}
				}
				if (iterCopy.cursor.pFactor.prime > iterPf.cursor.pFactor.prime) {
					return false;
				}

				else if (iterCopy.cursor.pFactor.prime == iterPf.cursor.pFactor.prime
						&& iterCopy.cursor.pFactor.multiplicity < iterPf.cursor.pFactor.multiplicity) {
					return false;
				}

				else {
					if (iterCopy.cursor.pFactor.multiplicity
							- iterPf.cursor.pFactor.multiplicity != 0) {
						iterCopy.cursor.pFactor.multiplicity -= iterPf.cursor.pFactor.multiplicity;
						iterCopy.next();
						iterPf.next();
					}

					else {
						iterCopy.next();
						iterCopy.remove();
						iterPf.next();
					}

				}
			}
			this.head.next = copy.head.next;
			this.tail.previous = copy.tail.previous;
			this.updateValue();
			this.size = copy.size;
			return true;
		}
	}

	// -------------------------------------------------
	// Greatest Common Divisor and Least Common Multiple
	// -------------------------------------------------

	/**
	 * Computes the greatest common divisor (gcd) of this.value and an integer
	 * n, and return the result as a PrimeFactors object. Calls the method
	 * Euclidean() if this.value != OVERFLOW.
	 * 
	 * It is more efficient to factorize the gcd than n, which is often much
	 * greater.
	 * 
	 * @param n
	 * @return prime factorization of gcd
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public PrimeFactorization gcd(long n) throws IllegalArgumentException {
		if (n < 1) {
			throw new IllegalArgumentException();
		}

		if (this.value == OVERFLOW) {
			return this.gcd(new PrimeFactorization(n));
		}

		long Gcd = Euclidean(this.value, n);
		PrimeFactorization pf = new PrimeFactorization(Gcd);
		return pf;
	}

	/**
	 * Implements the Euclidean algorithm to compute the gcd of two natural
	 * numbers m and n. The algorithm is described in Section 4.1 of the project
	 * description.
	 * 
	 * @param m
	 * @param n
	 * @return gcd of m and n.
	 * @throws IllegalArgumentException
	 *             if m < 1 or n < 1
	 */
	public static long Euclidean(long m, long n)
			throws IllegalArgumentException {
		if (m < 1 || n < 1) {
			throw new IllegalArgumentException();
		}

		long remainder = 1;
		while (remainder != 0) {
			remainder = m % n;
			m = n;
			n = remainder;
		}
		return m;
	}

	/**
	 * Computes the gcd of this.value and pf.value by traversing the two lists.
	 * No direct computation involving value and pf.value. Refer to Section 4.2
	 * in the project description on how to proceed.
	 * 
	 * @param pf
	 * @return prime factorization of the gcd
	 */
	public PrimeFactorization gcd(PrimeFactorization pf) {
		PrimeFactorizationIterator iter1 = this.iterator();
		PrimeFactorizationIterator iter2 = pf.iterator();
		PrimeFactorization gcd = new PrimeFactorization();

		while (iter2.hasNext()) {
			PrimeFactor thisCurrent = iter1.next();
			PrimeFactor pfCurrent = iter2.next();

			while (thisCurrent.prime != pfCurrent.prime && iter1.hasNext()) {
				thisCurrent = iter1.next();
			}
			if (thisCurrent.prime == pfCurrent.prime) {
				gcd.add(thisCurrent.prime, Math.min(thisCurrent.multiplicity,
						pfCurrent.multiplicity));
			}
			iter1 = this.iterator();
		}

		return gcd;
	}

	/**
	 * Computes the least common multiple (lcm) of this.value and the number
	 * represented by pf. The list-based algorithm is given in Section 4.3 in
	 * the project description.
	 * 
	 * @param pf
	 * @return factored least common multiple
	 */
	public PrimeFactorization lcm(PrimeFactorization pf) {
		PrimeFactorizationIterator iter1 = this.iterator();
		PrimeFactorizationIterator iter2 = pf.iterator();
		PrimeFactorization lcm = new PrimeFactorization();

		if (this.value == OVERFLOW || pf.value == OVERFLOW) {
			lcm.value = OVERFLOW;
		}

		while (iter1.hasNext() && iter2.hasNext()) {
			if (iter1.cursor.pFactor.prime != iter2.cursor.pFactor.prime) {
				if (iter1.cursor.pFactor.prime < iter2.cursor.pFactor.prime) {
					PrimeFactor toAdd = new PrimeFactor(
							iter1.cursor.pFactor.prime,
							iter1.cursor.pFactor.multiplicity);
					lcm.add(toAdd.prime, toAdd.multiplicity);
					iter1.next();
				} else {
					PrimeFactor toAdd = new PrimeFactor(
							iter2.cursor.pFactor.prime,
							iter2.cursor.pFactor.multiplicity);
					lcm.add(toAdd.prime, toAdd.multiplicity);
					iter2.next();
				}
			}

			else {
				Node toAdd = new Node(new PrimeFactor(
						iter1.cursor.pFactor.prime, Math.max(
								iter1.cursor.pFactor.multiplicity,
								iter2.cursor.pFactor.multiplicity)));
				lcm.add(toAdd.pFactor.prime, toAdd.pFactor.multiplicity);
				iter1.next();
				iter2.next();
			}
		}

		if (iter1.hasNext()) {
			while (iter1.hasNext()) {
				lcm.add(iter1.cursor.pFactor.prime,
						iter1.cursor.pFactor.multiplicity);
				iter1.next();
			}
		}

		else {
			while (iter2.hasNext()) {
				lcm.add(iter2.cursor.pFactor.prime,
						iter2.cursor.pFactor.multiplicity);
				iter2.next();
			}
		}

		lcm.updateValue();
		return lcm;
	}

	/**
	 * Computes the least common multiple of this.value and an integer n.
	 * Construct a PrimeFactors object using n and then call the lcm() method
	 * above. Calls the first lcm() method.
	 * 
	 * @param n
	 * @return factored least common multiple
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public PrimeFactorization lcm(long n) throws IllegalArgumentException {
		if (n < 1) {
			throw new IllegalArgumentException();
		}

		PrimeFactorization pf = new PrimeFactorization(n);
		PrimeFactorization lcm = this.lcm(pf);
		return lcm;
	}

	// ------------
	// List Methods
	// ------------

	/**
	 * Traverses the list to determine if p is a prime factor.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p
	 * @return true if p is a prime factor of the number represented by this
	 *         linked list false otherwise
	 * @throws IllegalArgumentException
	 *             if p is not a prime
	 */
	public boolean containsPrimeFactor(int p) throws IllegalArgumentException {
		if (isPrime(p) == false) {
			throw new IllegalArgumentException();
		}

		PrimeFactorizationIterator iter = new PrimeFactorizationIterator();

		while (iter.hasNext()) {
			if (iter.next().prime == p) {
				return true;
			} else {
			}
		}
		return false;
	}

	// The next two methods ought to be private but are made public for testing
	// purpose.

	/**
	 * Adds a prime factor p of multiplicity m. Search for p in the linked list.
	 * If p is found at a node N, add m to N.multiplicity. Otherwise, create a
	 * new node to store p and m.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p
	 *            prime
	 * @param m
	 *            multiplicity
	 * @return true if m >= 1 false if m < 1
	 */
	public boolean add(int p, int m) {
		if (m < 1) {
			return false;
		}
		PrimeFactorizationIterator iter = this.iterator();

		while (iter.hasNext()) {
			PrimeFactor pCheck = iter.next();

			if (pCheck.prime > p) {
				iter.previous();
				if (iter.cursor.previous.pFactor == null) {
					iter.cursor.previous = this.head;
					this.link(iter.cursor, new Node(p, m));
					size++;
					updateValue();
					return true;
				}
				iter.add(new PrimeFactor(p, m));
				return true;
			}

			else if (pCheck.prime == p) {
				pCheck.multiplicity += m;
				updateValue();
				return true;
			}
		}
		link(iter.cursor, new Node(p, m));
		size++;
		updateValue();
		return true;
	}

	/**
	 * Removes a prime p of multiplicity m from the list. It starts by searching
	 * for p in the linked list. Return if p is not found. Otherwise, let N be
	 * the node that stores p. If N.multiplicity > m, substract m from
	 * N.multiplicity. Otherwise, remove the node N.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p
	 * @param m
	 * @return true on success false when p is either not found or found at a
	 *         node N but m > N.multiplicity
	 * @throws IllegalArgumentException
	 *             if m < 1
	 */
	public boolean remove(int p, int m) throws IllegalArgumentException {

		if (m < 1) {
			throw new IllegalArgumentException();
		}

		PrimeFactorizationIterator iter = this.iterator();

		while (iter.hasNext()) {
			PrimeFactor pCheck = iter.next();

			if (pCheck.prime == p && pCheck.multiplicity == m
					|| (pCheck.prime == p && pCheck.multiplicity < m)) {
				iter.remove();
				updateValue();
				return true;
			}

			else if (pCheck.prime == p && pCheck.multiplicity > m) {
				pCheck.multiplicity -= m;
				updateValue();
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @return size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Writes out the list as a factorization in the form of a product.
	 * Represents exponentiation by a caret. For example, if the number is 5814,
	 * the returned string would be printed out as "2 * 3^2 * 17 * 19".
	 */
	@Override
	public String toString() {
		String fact = "";
		if (value == 1) {
			fact += 1;
			return fact;
		}

		PrimeFactorizationIterator iter = this.iterator();
		fact += iter.next().toString();
		while (iter.hasNext()) {
			fact += " * ";
			fact += iter.next().toString();
		}
		return fact;
	}

	// The next three methods are for testing, but you may use them as you like.

	/**
	 * @return true if this PrimeFactorization is representing a value that is
	 *         too large to be within long's range. e.g. 999^999. false
	 *         otherwise.
	 */
	public boolean valueOverflow() {
		return value == OVERFLOW;
	}

	/**
	 * @return value represented by this PrimeFactorization, or -1 if
	 *         valueOverflow()
	 */
	public long value() {
		return value;
	}

	public PrimeFactor[] toArray() {
		PrimeFactor[] arr = new PrimeFactor[size];
		int i = 0;
		for (PrimeFactor pf : this)
			arr[i++] = pf;
		return arr;
	}

	@Override
	public PrimeFactorizationIterator iterator() {
		return new PrimeFactorizationIterator();
	}

	/**
	 * Doubly-linked node type for this class.
	 */
	private class Node {
		public PrimeFactor pFactor; // prime factor
		public Node next;
		public Node previous;

		/**
		 * Default constructor for creating a dummy node.
		 */
		public Node() {
		}

		/**
		 * Precondition: p is a prime
		 * 
		 * @param p
		 *            prime number
		 * @param m
		 *            multiplicity
		 * @throws IllegalArgumentException
		 *             if m < 1
		 */
		public Node(int p, int m) throws IllegalArgumentException {
			if (m < 1) {
				throw new IllegalArgumentException();
			}
			this.pFactor = new PrimeFactor(p, m);
		}

		/**
		 * Constructs a node over a provided PrimeFactor object.
		 * 
		 * @param pf
		 * @throws IllegalArgumentException
		 */
		public Node(PrimeFactor pf) {
			this.pFactor = new PrimeFactor(pf.prime, pf.multiplicity);
		}

		/**
		 * Printed out in the form: prime + "^" + multiplicity. For instance
		 * "2^3".
		 */
		@Override
		public String toString() {
			return pFactor.toString();
		}

	}

	private class PrimeFactorizationIterator implements
			ListIterator<PrimeFactor> {
		// Class invariants:
		// 1) logical cursor position is always between cursor.previous and
		// cursor
		// 2) after a call to next(), cursor.previous refers to the node just
		// returned
		// 3) after a call to previous() cursor refers to the node just returned
		// 4) index is always the logical index of node pointed to by cursor

		private Node cursor = head;
		private Node pending = null; // node pending for removal
		private int index = 0;

		// other instance variables ...

		/**
		 * Default constructor positions the cursor before the smallest prime
		 * factor.
		 */
		public PrimeFactorizationIterator() {
			cursor = head.next;
		}

		@Override
		public boolean hasNext() {
			return cursor.next != null;
		}

		@Override
		public boolean hasPrevious() {

			return cursor.previous != null;
		}

		@Override
		public PrimeFactor next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			index++;
			pending = cursor;
			cursor = cursor.next;
			cursor.previous = pending;
			return pending.pFactor;
		}

		@Override
		public PrimeFactor previous() {

			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}

			index--;
			Node temp = cursor;
			pending = cursor.previous;
			cursor = cursor.previous;
			cursor.next = temp;
			cursor.previous = temp.previous.previous;
			return pending.pFactor;
		}

		/**
		 * Removes the prime factor returned by next() or previous()
		 * 
		 * @throws IllegalStateException
		 *             if pending == null
		 */
		@Override
		public void remove() throws IllegalStateException {

			if (pending == null) {
				throw new IllegalStateException();
			}

			if (pending == cursor) {
				cursor = cursor.next;
				unlink(pending);
				pending = null;
				updateValue();
				size--;
			}

			else {
				unlink(pending);
				pending = null;
				updateValue();
				index--;
				size--;
			}
		}

		/**
		 * Adds a prime factor at the cursor position. The cursor is at a wrong
		 * position in either of the two situations below:
		 * 
		 * a) pf.prime < cursor.previous.pFactor.prime if cursor.previous !=
		 * null. b) pf.prime > cursor.pFactor.prime if cursor != null.
		 * 
		 * Take into account the possibility that pf.prime ==
		 * cursor.pFactor.prime.
		 * 
		 * Precondition: pf.prime is a prime.
		 * 
		 * @param pf
		 * @throws IllegalArgumentException
		 *             if the cursor is at a wrong position.
		 */
		@Override
		public void add(PrimeFactor pf) throws IllegalArgumentException {
			if (cursor.previous != head
					&& cursor.previous.pFactor.prime >= pf.prime)
				throw new IllegalArgumentException(
						"Illegal add: previous prime >= given prime");
			if (cursor != tail && cursor.pFactor.prime <= pf.prime)
				throw new IllegalArgumentException(
						"Illegal add: next prime <= given prime");
			if (cursor.pFactor != null && pf.prime == cursor.pFactor.prime) {
				throw new IllegalArgumentException();
			}

			link(cursor, new Node(pf));
			updateValue();
			size++;
			index++;
		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Deprecated
		@Override
		public void set(PrimeFactor pf) {
			throw new UnsupportedOperationException(getClass().getSimpleName()
					+ " does not support set method");
		}

		// Other methods you may want to add or override that could possibly
		// facilitate
		// other operations, for instance, addition, access to the previous
		// element, etc.
		//
		// ...
		//
	}

	// --------------
	// Helper methods
	// --------------

	/**
	 * Inserts toAdd into the list after current without updating size.
	 * 
	 * Precondition: current != null, toAdd != null
	 */
	private void link(Node current, Node toAdd) {
		toAdd.previous = current.previous;
		toAdd.next = current;
		current.previous.next = toAdd;
		current.previous = toAdd;
	}

	/**
	 * Removes toRemove from the list without updating size.
	 */
	private void unlink(Node toRemove) {
		toRemove.previous.next = toRemove.next;
		toRemove.next.previous = toRemove.previous;
	}

	/**
	 * Remove all the nodes in the linked list except the two dummy nodes.
	 * 
	 * Made public for testing purpose. Ought to be private otherwise.
	 */
	public void clearList() {
		head.next = tail;
		tail.previous = head;
		size = 0;
		value = 1;
	}

	/**
	 * Multiply the prime factors (with multiplicities) out to obtain the
	 * represented integer. Use Math.multiply(). If an exception is throw,
	 * assign OVERFLOW to the instance variable value. Otherwise, assign the
	 * multiplication result to the variable.
	 * 
	 */
	private void updateValue() {
		try {
			PrimeFactorizationIterator iter = this.iterator();
			long sum = 1;
			while (iter.hasNext()) {
				if (iter.cursor.pFactor.multiplicity > 1) {
					for (int i = 0; i < iter.cursor.pFactor.multiplicity; ++i) {
						sum = Math
								.multiplyExact(sum, iter.cursor.pFactor.prime);
					}
				} else {
					sum = Math.multiplyExact(sum, iter.cursor.pFactor.prime);
				}
				iter.next();
			}
			this.value = sum;
		}

		catch (ArithmeticException e) {
			value = OVERFLOW;
		}

	}
}
