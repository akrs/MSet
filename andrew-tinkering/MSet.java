import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;

/**
    An MSet is an unordered collection that allows duplicates. This class should be 
    a direct, custom, array-based implementation of the Collection interface.
*/

public class MSet extends Object implements Collection {

    private Object[] items;

    private long[] numberOfItems;

    private int numberOfUniqueItems;

    /** Constructs an MSet with no elements. */
    public MSet () {
        this.items = new Object[1024];          // Inital capacty 1024 items
        this.numberOfItems = new long[1024];
        this.numberOfUniqueItems = 0;
        Arrays.fill(this.numberOfItems, 0);
    }

    /** Constructs an MSet from the given collection. */
    public MSet ( Collection c ) {
        this.items = new Object[1024];          // Inital capacty 1024 items
        this.numberOfItems = new long[1024];
        this.numberOfUniqueItems = 0;
        Arrays.fill(this.numberOfItems, 0);

        this.addAll(c);
    }

    /** Grows the current MSet to the specified size */
    private void embiggen (int newSize) {
        this.items = Arrays.copyOf(this.items, newSize);
        this.numberOfItems = Arrays.copyOf(this.numberOfItems, newSize);

        System.gc();        // Embiggen will orphan large arrays, want them cleaned.
    }

    /** Ensures that this collection contains the specified element. 
        Returns true if this collection changed as a result of the call. (Returns 
        false if this collection does not permit duplicates and already contains the specified 
        element.) */
    public boolean add ( Object o ) {
        if (this.containsAndAdd(o)) {                                   // If the item is in the MSet, it will be added by this method
            return true;
        }

        if (numberOfUniqueItems == this.items.length + 1) {             // Better make sure we have space.
            if (this.numberOfUniqueItems * 2 < Integer.MAX_VALUE) { 
                this.embiggen(this.numberOfUniqueItems * 2);
            } else {
                return false;                                           // Return false if we can't add it. TODO: Should we throw an exception?
            }
        }

        for (int i = 0; i < this.items.length; i++) {                   // At this point, we know the MSet does not contain the item, it needs to be added.
            if (this.items[i] == null) {
                this.items[i] = o;
                this.numberOfItems[i] += 1;
                return true;
            }
        }

        return false;                                                   // This should never happen, but the complier complains if it is removed.
    }

    /** Checks to see if the MSet contains the specified item. If it does,
        this will return true and increment the number of the item by 1. If this
        MSet does not conten the object, returns false*/
    private boolean containsAndAdd ( Object o ) {
        for (int i = 0; i < this.items.length; i++) {
            if (o == this.items[i]) {
                this.numberOfItems[i]++;
                return true;
            }
        }

        return false;
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
        this.items = new Object[1024];          // Just make a new MSet
        this.numberOfItems = new long[1024];
        this.numberOfUniqueItems = 0;
        Arrays.fill(this.numberOfItems, 0);
        System.gc();                            // Hopefully clean up all the orphaned items.
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
    public int hashCode () {            // Needs moar comments
        long combinedHash = 0;
        
        for (int i = 0; i < numberOfUniqueItems; i++) {
            String tempHash = this.items[i].hashCode() + this.numberOfItems[i] + "";
            combinedHash += tempHash.hashCode();
        }

        String hash = combinedHash + "";

        return hash.hashCode();
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