
import java.util.Collection;
import java.util.Iterator;

/**
	An MSet is an unordered collection that allows duplicates. This class should be 
	a direct, custom, array-based implementation of the Collection interface, created from
	"first principles," i.e., you may not use any other material (e.g., ArrayList) from the 
	Java Collections Framework. Note that there have been several revisions to the original 
	specifications, e.g., indexOf() has been removed.
*/

public class MSet extends Object implements Collection {

	private Element[][] items;
    private int numberOfUniqueItems;
    private int[] objectLocation;
    private boolean currentArrayFull;
    private int rowLength = 1024;
    
///// CONCERNS /////
    // 1) searching this.contains(null) might return a column position that is null? Possibly not, it's late

    public static void main(String[] args) {
    	MSet m = new MSet();
    	m.add("cat");
    	m.add("rabbit");
    	m.add("platapus");
    	m.add("cat");
    	System.out.println(m.toString());

    	m.remove("cat");
    	System.out.println(m.toString());
    }
///// PRIVATE METHODS /////
    private class Element {
    	
    	Object object;
    	long count;

    	private Element (Object o) {
    		this.object = o;
    		this.count = 1;
    	}

    	public String toString () {
    		return "{" + this.object.toString() + ", " + this.count + "}";
    	}

    	private boolean equals ( Element e ) {
    		return this.count == e.count && this.object.equals(e.object);
    	}
    }

    private class ObjIterator implements Iterator {
    	private MSet m;
    	private int i;

    	public ObjIterator (MSet m) {
    		this.m = m;
    		this.i = -1;
    	}

    	public Object next () {
    		i++;
    		return m.items[i / m.rowLength][i % m.rowLength].object;
    	}

    	public boolean hasNext () {
    		return i <  m.numberOfUniqueItems;
    	}

    }

    private void setObjectLocation (int column, int row) {
    	this.objectLocation[0] = column;
    	this.objectLocation[1] = row;
    }

    private void moveLastIntoEmpty () {
    	this.items[objectLocation[0]][objectLocation[1]] = this.items[numberOfUniqueItems / rowLength][numberOfUniqueItems % 1024];
    	this.items[numberOfUniqueItems / rowLength][numberOfUniqueItems % 1024] = null;
    }

	private int indexOf ( Object o ) {
		for (int i = 0; i < numberOfUniqueItems; i++) {
			if (o.equals(this.items[i / 1024][i % 1024].object)) {
				this.setObjectLocation(i / 1024, i % 1024);
				return i;
			}
		}
		return -1;
	}

    // Only ever move the first empty row element by 1. This will determine how the column
    // element reacts
    /*
    private void updateFirstEmpty (int moveBy) {
    	int newRow = this.firstEmpty[1] + moveBy;
    	this.currentArrayFull = false;

    	// If current row array is full, set it up so that the first empty will be ready after 
    	// new array is created
    	if (newRow == 1024) {
    		this.firstEmpty[0] = this.firstEmpty[0] + 1;
    		this.firstEmpty[1] = 0;
    		this.currentArrayFull = true;
    	} else if (newRow < 0) {
    		// If firstEmpty row goes less than 0 check to see if column == 0
    		// If yes: set first empty to (0, 0), else set column -= 1, row = 1023
    		if (this.firstEmpty[0] == 0) {
    			this.firstEmpty[0] = 0;
    			this.firstEmpty[1] = 0;
    		} else {
    			this.firstEmpty[0] -= 1;
    			this.firstEmpty[1] = 1023;
    		}
    	} else {
    		this.firstEmpty[1] = row;
    	}
    } **/

///// PUBLIC METHODS /////
	/** Constructs an MSet with no elements. */
	public MSet () {
		this.items = new Element[Integer.MAX_VALUE / rowLength][];
		this.items[0] = new Element[rowLength];
		this.objectLocation = new int[2];
		this.numberOfUniqueItems = 0;
		this.currentArrayFull = false;
	}

	/** Constructs an MSet from the given collection. */
	public MSet ( Collection c ) {
		this.items = new Element[Integer.MAX_VALUE / rowLength][];
		this.items[0] = new Element[rowLength];
		this.objectLocation = new int[2];
		this.numberOfUniqueItems = 0;
		this.currentArrayFull = false; 

		this.addAll(c);
	}

	/** Ensures that this collection contains the specified element. 
	    Returns true if this collection changed IN ANY WAY as a result of the call. */
	public boolean add ( Object o ) {
		// If the MSet contains the specified object then add one to the Element's count
		// Don't update the first empty
		// Contains will update the object position, this will use those coordinates to add 1 to the object's count
		if (this.contains(o)) {
			this.items[this.objectLocation[0]][this.objectLocation[1]].count += 1;
			return true;
		} else {
			try {
				// Create a new Element from the object
				// Place in first empty
				// Update number of unique items, by doing this, this updates first empty
				Element element = new Element(o);
				if (this.items[numberOfUniqueItems / rowLength] == null) {
					this.items[numberOfUniqueItems / rowLength] = new Element[rowLength];
				}
				this.items[numberOfUniqueItems / rowLength][numberOfUniqueItems % rowLength] = element;
				this.numberOfUniqueItems += 1;
				
				return true;
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new UnsupportedOperationException("Collection too large. Go fuck yourself.");
			}
		}
	}

	/** Adds all of the elements in the specified collection to this collection. */
	public boolean addAll ( Collection c ) {
		for (Object o : c) {
			this.add(o);
		}
		return true;
	}

	/** Removes all of the elements from this collection. */
	public void clear () {
		throw new UnsupportedOperationException();
	}

	/** Returns true if this collection contains the specified element. */
	// This method also sets where the object is located in a global variable, so 
	// it find's the specified object's position
	public boolean contains ( Object o ) {
		return this.indexOf(o) >= 0;
	}

	/** Returns true if this collection contains all of the elements in the specified collection. */
	public boolean containsAll ( Collection c ) {
		for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
	}

	/** Returns the number of copies of Object o in this MSet. */
	public int count ( Object o ) {
		if (this.contains(o)) {
			return (int)this.items[this.objectLocation[0]][this.objectLocation[1]].count;
		}
		throw new UnsupportedOperationException();
	}

	/** Compares the specified object with this collection for equality. Overrides Object.equals(). */
	public boolean equals ( Object o ) {
		if (o == null ||
			!(o instanceof MSet) ||
            this.numberOfUniqueItems != ((MSet)o).numberOfUniqueItems) {
            return false;
        }
        MSet m = (MSet)o;

        for (int i = 0; i < numberOfUniqueItems; i++) {
        	for (int j = 0; j < numberOfUniqueItems; j++) {
        		if (this.items[i / rowLength][i % rowLength].equals(m.items[j / rowLength][j % rowLength])) {
        			
        		}
        	}
        	return false;
        }

        return true;
	}

 	/** Returns a hash code value for this collection. May override Object.hashCode(). */
	public int hashCode () {
		throw new UnsupportedOperationException();
	}

	/** Returns true if this collection contains no elements. */
	public boolean isEmpty () {
		throw new UnsupportedOperationException();
	}

	/** Returns an iterator over the UNIQUE elements in this collection. There are no 
		guarantees concerning the order in which the elements are returned. */
	public Iterator iterator () {
		throw new UnsupportedOperationException();
	}

	/** [REVISED] Removes all instances of the specified element from this collection. */
	public boolean remove ( Object o ) {
		if (this.contains(o)) {
			this.remove();
			return true;
		} else {
			return false;
		}
	}
	/** Called when we want to remove the object at objectLocation */
	private void remove () {
		this.items[this.objectLocation[0]][this.objectLocation[1]] = null;
		this.numberOfUniqueItems--;
		this.moveLastIntoEmpty();
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
		for (int i = 0; i < numberOfUniqueItems; i++) {
			if (!c.contains(this.items[i / rowLength][i % rowLength].object)) {
				this.setObjectLocation(i / rowLength, i % rowLength);
				this.remove();
				result = true;
			}
		}

		return result;
	}
	
	/** Returns the number of elements in this collection, including duplicates.  */
	public int size () {
		long result = 0;

		for (int i = 0; i < numberOfUniqueItems; i++) {
			result += this.items[i / 1024][i % 1024].count;
		}

		return (int)result;
	}

	/** Returns the number of UNIQUE elements in this collection (i.e., not including duplicates). */
	public int unique () {
		return this.numberOfUniqueItems;
	}

	/** Returns an array containing all of the UNIQUE elements in this collection. */
	public Object[] toArray () {
		Object[] result = new Object[numberOfUniqueItems];
		for (int i = 0; i < numberOfUniqueItems; i++) {
			result[i] = this.items[i / 1024][i % 1024].object;
		}
		return result;
	}

	/** Throws an UnsupportedOperationException. */
	public Object[] toArray ( Object[] a  ) {
		throw new UnsupportedOperationException();
	}


	/** [ADDED] Decrements the number of copies of o in this MSet. Returns true iff this MSet changed
		as a result of the operation. */
	public boolean reduce ( Object o ) {
		if (this.contains(o)) {
			this.items[objectLocation[0]][objectLocation[1]].count -= 1;
			if (this.items[objectLocation[0]][objectLocation[1]].count == 0) {
				this.remove();
			}
			return true;
		} else {
			return false;
		}
	}

	
/* The following override methods inherited from Object: */

	/** Throws an UnsupportedOperationException. Overrides Object.clone(). */
	protected Object clone() {
		throw new UnsupportedOperationException();
	}
	
	/** Throws an UnsupportedOperationException. Overrides Object.finalize(). */
	protected void finalize () {
		throw new UnsupportedOperationException();
	}
	
	/** Returns a stringy representation of this MSet. Overrides Object.toString(). */
	public String toString () {
		String result = "";
		for (int i = 0; i < numberOfUniqueItems; i++) {
			result += this.items[i / 1024][i % 1024].toString();
		}
		return result;
	}
	
}