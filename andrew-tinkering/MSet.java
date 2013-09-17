import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;

/**
    An MSet is an unordered collection that allows duplicates. This class should be 
    a direct, custom, array-based implementation of the Collection interface.
*/

public class MSet extends Object implements Collection {

    private int length;

    private Object[] storage;

    private long[] numberOfObjects;

    private boolean[] full;

    /** Constructs an MSet with no elements. */
    public MSet () {
        this.length = 10;                                   // Sets size of collection storage arrays
        this.storage = new Object[this.length];             // Sets up storage array
        this.numberOfObjects = new long[this.length];       // Sets up numberOfObjects array
        Arrays.fill(this.numberOfObjects, 0);               // Fills numberOfObjects, as there's nothing there yet
        this.full = new boolean[this.length];               // Sets up "vacency" array
        Arrays.fill(this.full, false);
    }

    private void MSet (int size) {
        this.length = size;
        this.storage = new Object[this.length];
        this.numberOfObjects = new long[this.length];
        Arrays.fill(this.numberOfObjects, 0);
        this.full = new boolean[this.length];
        Arrays.fill(this.full, false);
    }

    /** Constructs an MSet from the given collection. */
    public MSet ( Collection c ) {
        this.length = c.size();
        this.storage = new Object[this.length];
        this.numberOfObjects = new long[this.length];
        Arrays.fill(this.numberOfObjects, 0);
        this.full = new boolean[this.length];
        Arrays.fill(this.full, false);

        this.addAll(c);
    }

    /** Grows the current MSet to the specified size */
    private void embiggen (int newSize) {
        this.length = newSize;
        this.storage = Arrays.copyOf(this.storage, newSize);
        this.numberOfObjects = Arrays.copyOf(this.numberOfObjects, newSize);
        this.full = Arrays.copyOf(this.full, newSize);
    }

    /** Ensures that this collection contains the specified element. 
        Returns true if this collection changed as a result of the call. (Returns 
        false if this collection does not permit duplicates and already contains the specified 
        element.) */
    public boolean add ( Object o ) {
        throw new UnsupportedOperationException();
    }

    /** Adds all of the elements in the specified collection to this collection. */
    public boolean addAll ( Collection c ) {
        throw new UnsupportedOperationException();
    }

    /** Removes all of the elements from this collection. */
    public void clear () {
        throw new UnsupportedOperationException();
    }

    /** Returns true if this collection contains the specified element. */
    public boolean contains ( Object o ) {
        throw new UnsupportedOperationException();
    }


    /** Returns true if this collection contains all of the elements in the specified collection. */
    public boolean containsAll ( Collection c ) {
        throw new UnsupportedOperationException();
    }

    /** Compares the specified object with this collection for equality. Overrides Object.equals(). */
    public boolean equals ( Object o ) {
        throw new UnsupportedOperationException();
    }

    /** Returns a hash code value for this collection. May override Object.hashCode(). */
    public int hashCode () {
        throw new UnsupportedOperationException();
    }

    /** Returns true if this collection contains no elements. */
    public boolean isEmpty () {
        throw new UnsupportedOperationException();
    }

    /** Returns an iterator over the elements in this collection. 
        There are no guarantees concerning the order in which the elements are returned. */
    public Iterator iterator () {
        throw new UnsupportedOperationException();
    }

    /** Removes a single instance of the specified element from this collection, if it is present.
        Returns true if the collection changes as a result of this operation.*/
    public boolean remove ( Object o ) {
        throw new UnsupportedOperationException();
    }

    /** Removes all of this collection's elements that are also contained in the specified collection. 
        After this call returns, this collection will contain no elements in common with the specified collection. */
    public boolean removeAll ( Collection c ) {
        throw new UnsupportedOperationException();
    }

    /** Retains only the elements in this collection that are contained in the specified collection. 
         In other words, removes from this collection all of its elements that are not contained in the 
         specified collection. */
    public boolean retainAll ( Collection c ) {
        throw new UnsupportedOperationException();
    }
    
    /** Returns the number of elements in this collection, including duplicates. 
        If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE. */
    public int size () {
        throw new UnsupportedOperationException();
    }

    /** Returns the number of UNIQUE elements in this collection (i.e., not including duplicates). 
    If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE. */
    public int unique () {
        throw new UnsupportedOperationException();
    }

    /** Returns an array containing all of the UNIQUE elements in this collection. */
    public Object[] toArray () {
        throw new UnsupportedOperationException();
    }

    /** Throws an UnsupportedOperationException. */
    public Object[] toArray ( Object[] a  ) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
    
}