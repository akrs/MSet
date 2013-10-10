import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

public class MSet extends ArrayList {

	/** Constructs an MSet with no elements. */
	public MSet () {
		
	}

	/** Constructs an MSet from the given collection. */
	public MSet ( Collection c ) {
		super.addAll(c);
	}

	/** Returns the number of copies of Object o in this MSet. */
	public int count ( Object o ) {
		int result = 0;

		for (int i = 0; i < this.size(); i++) {
			if (o.equals(this.get(i))) {
				result += 1;
			}
		}

		return result;
	}

	/** Compares the specified object with this collection for equality. Overrides Object.equals(). */
	public boolean equals ( Object o ) {
		if (o == null ||
			!(o instanceof MSet) ||
			this.unique() != ((MSet)o).unique() ||
			this.size() != ((MSet)o).size()) {
			return false;
		}

		MSet m = (MSet)o;

		for (int i = 0; i < this.size(); i++) {
			Object currentObject = this.get(i);
			if (this.count(currentObject) != m.count(currentObject)) {
				return false;
			}
		}

		return true;
	}

	/** Returns a hash code value for this collection. May override Object.hashCode(). */
	public int hashCode () {
		long result = 0;
		for (int i = 0; i < this.size(); i++) {
			result += this.get(i).hashCode();
		}
		
		if (result > Integer.MAX_VALUE) {
			result /= 25964951;
		}

		return (int)result;
	}

	/** [REVISED] Removes all instances of the specified element from this collection. */
	public boolean remove ( Object o ) {
		boolean result = false;
		int x = super.indexOf(o);
		while (x >= 0) {
			super.remove(x);
			x = super.indexOf(o);

			result = true;
		} 
		return result;
	}

	/** Removes all of this collection's elements that are also contained in the specified collection. 
		After this call returns, this collection will contain no elements in common with the specified collection. */
	public boolean removeAll ( Collection c ) {
		boolean result = false;
		for (Object o : c) {
			if (this.remove(o)) {
				result = true;
			}
		}
		return result;
	}

	/** Retains only the elements in this collection that are contained in the specified collection. 
		 In other words, removes from this collection all of its elements that are not contained in the 
		 specified collection. */
	public boolean retainAll ( Collection c ) {
		boolean result = false; 

		for (int i = 0; i < this.size(); i++) {
			if (!c.contains(this.get(i))) {
				this.remove(i);
				result = true;
			}
		}

		return result;
	}

	/** Returns the number of UNIQUE elements in this collection (i.e., not including duplicates). */
	public int unique () {
		ArrayList uniques = new ArrayList();

		for (int i = 0; i < this.size(); i++) {
			if (!uniques.contains(this.get(i))) {
				uniques.add(this.get(i));
			}
		}

		return uniques.size();
	}

	/** Returns an array containing all of the UNIQUE elements in this collection. */
	public Object[] toArray () {
		ArrayList uniques = new ArrayList();

		for (int i = 0; i < this.size(); i++) {
			if (!uniques.contains(this.get(i))) {
				uniques.add(this.get(i));
			}
		}

		return uniques.toArray();
	}

	/** Throws an UnsupportedOperationException. */
	public Object[] toArray ( Object[] a  ) {
		throw new UnsupportedOperationException();
	}


	/** [ADDED] Decrements the number of copies of o in this MSet. Returns true iff this MSet changed
		as a result of the operation. */
	public boolean reduce ( Object o ) {
		return super.remove(o);
	}

	public Iterator iterator () {
		return new ObjIterator(this);
	}

	public class ObjIterator implements Iterator {
		private MSet m;
		private int position;
		private ArrayList uniques;

		public ObjIterator (MSet n) {
			this.m = n;
			this.position = -1;
			this.uniques = new ArrayList();
		}

		public Object next () {
			this.position++;
			if (this.position < m.size() && !uniques.contains(m.get(this.position))) {
				uniques.add(m.get(this.position));
				return m.get(this.position);
			} else {
				if (this.hasNext()) {
					return this.next();
				} else {
					return null;
				}
			}
		}

		public boolean hasNext () {
			if (m.size() == 0) {
				return false;
			} else if ((this.position + 1) < m.size() && !uniques.contains(m.get(this.position + 1))) {
				return true;
			} else if ((this.position + 2) < m.size()) {
				this.position++;
				return this.hasNext();
			} else {
				return false;
			}
		}

		public void remove () {
			m.remove(m.get(this.position));
		}
	}
	
}