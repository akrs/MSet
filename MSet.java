import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;

/**
    An MSet is an unordered collection that allows duplicates. This class should be 
    a direct, custom, array-based implementation of the Collection interface.
*/

public class MSet extends Object implements Collection {

    private Object[][] items;

    private long[][] numberOfItems;

    private int numberOfUniqueItems;

    private int[] firstEmpty;

    public static void main(String[] args) {
        MSet y = new MSet();

        MSet x = new MSet();

        for (long i = 0; i < 100; i++) {
            y.add(Long.valueOf(i));
        }

        for (long i = 0; i < 1000; i++) {
            y.add(Long.valueOf(i));
        }

        System.out.println(y);

        for (long i = 0; i < 100; i++) {
            x.add(Long.valueOf(i));
        }

        for (long i = 0; i < 1000; i++) {
            x.add(Long.valueOf(i));
        }

        System.out.println(x.equals(y));


    }

    /** Constructs an MSet with no elements. */
    public MSet () {
        this.items = new Object[Integer.MAX_VALUE/1024][];
        this.items[0] = new Object[1024];
        this.numberOfItems = new long[Integer.MAX_VALUE/1024][];
        this.numberOfItems[0] = new long[1024];
        this.numberOfUniqueItems = 0;
        Arrays.fill(this.numberOfItems[0], 0);
        this.firstEmpty = new int[2];
        Arrays.fill(this.firstEmpty, 0);
    }

    /** Constructs an MSet from the given collection. */
    public MSet ( Collection c ) {
        this.items = new Object[Integer.MAX_VALUE/1024][];
        this.items[0] = new Object[1024];
        this.numberOfItems = new long[Integer.MAX_VALUE/1024][];
        this.numberOfItems[0] = new long[1024];
        this.numberOfUniqueItems = 0;
        Arrays.fill(this.numberOfItems[0], 0);
        this.firstEmpty = new int[2];
        Arrays.fill(this.firstEmpty, -1);

        this.addAll(c);
    }

    /** Private custom methods */
    private void updateEmpty (int column, int row) {
        if (this.firstEmpty[0] == -1) {
            this.firstEmpty[0] = column;
            this.firstEmpty[1] = row;
        } else if (column < this.firstEmpty[0] || (this.firstEmpty[0] == column && row < this.firstEmpty[0])) {
            this.firstEmpty[0] = column;
            this.firstEmpty[1] = row;
        }
    }

    private boolean containsAndMaybeAdds (Object o, boolean add) {
        boolean updatedEmpty = false;
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (updatedEmpty == false && this.items[column][row] == null) {
                    updateEmpty(column, row);
                    updatedEmpty = true;
                }

                if (o.equals(this.items[column][row])) {
                    if (add) {
                        this.numberOfItems[column][row]++;
                    }
                    return true;
                }
            }
        }

        return false;
    }


    /** Ensures that this collection contains the specified element. 
        Returns true if this collection changed as a result of the call. (Returns 
        false if this collection does not permit duplicates and already contains the specified 
        element.) */
    public boolean add ( Object o ) {
        if (this.containsAndMaybeAdds(o, true)) {
            return true;
        }

        if (this.firstEmpty[0] != -1) {
            this.items[this.firstEmpty[0]][this.firstEmpty[1]] = o;
            this.numberOfItems[this.firstEmpty[0]][this.firstEmpty[1]] += 1;
            Arrays.fill(this.firstEmpty, -1);
            return true;
        }

        int column = 0;
        while (column < this.items.length && this.items[column] != null) {
            for (int row = 0; row < 1024; row++) {
                if (this.items[column][row] == null) {
                    this.items[column][row] = o;
                    this.numberOfItems[column][row]++;
                    numberOfUniqueItems++;
                    return true;
                }
            }
            column++;
        }

        try {
            this.items[column] = new Object[1024];
            this.items[column][0] = o;
            numberOfUniqueItems++;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new UnsupportedOperationException("Collection too large. Go fuck yourself.");
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
        this.items = new Object[Integer.MAX_VALUE/1024][];
        this.items[0] = new Object[1024];
        this.numberOfItems = new long[Integer.MAX_VALUE/1024][];
        this.numberOfItems[0] = new long[1024];
        this.numberOfUniqueItems = 0;
        Arrays.fill(this.numberOfItems[0], 0);
        this.firstEmpty = new int[2];
        Arrays.fill(this.firstEmpty, 0);

        System.gc();
    }


    /** Returns true if this collection contains the specified element. */
    public boolean contains ( Object o ) {
        return containsAndMaybeAdds(o, false);
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
        throw new UnsupportedOperationException();
    }

    /** Compares the specified object with this collection for equality. Overrides Object.equals(). */
    public boolean equals ( Object o ) {
        throw new UnsupportedOperationException();
    }

    /** Returns a hash code value for this collection. May override Object.hashCode(). */
    public int hashCode () {            // Needs moar comments
        boolean updatedEmpty = false;
        long combinedHash = 0;
        
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (updatedEmpty == false && this.items[column][row] == null) {
                    updateEmpty(column, row);
                    updatedEmpty = true;
                } else if (this.items[column][row] != null) {
                    String tempHash = this.items[column][row].hashCode() + this.numberOfItems[column][row] + "";
                    combinedHash += Long.parseLong(tempHash);
                }
            }
        }

        while (combinedHash >= Integer.MAX_VALUE) {
            combinedHash /= 25964951;
        }

        return (int)combinedHash;
    }

    /** Returns true if this collection contains no elements. */
    public boolean isEmpty () {
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (this.items[column][row] != null) {
                    return false;
                }
            }
        }

        return true;
    }

    /** Returns an iterator over the elements in this collection. 
        There are no guarantees concerning the order in which the elements are returned. */
    public Iterator iterator () {
        throw new UnsupportedOperationException();
    }

    /** Removes a single instance of the specified element from this collection, if it is present. */
    public boolean remove ( Object o ) {
        boolean updatedEmpty = false;
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (updatedEmpty == false && this.items[column][row] == null) {
                    updateEmpty(column, row);
                    updatedEmpty = true;
                }

                if (o.equals(this.items[column][row])) {
                    this.items[column][row] = null;
                    this.numberOfItems[column][row] = 0;
                    numberOfUniqueItems--;
                    return true;
                }
            }
        }

        return false;
    }


    /** Decrements the number of copies of o in this MSet. Returns true iff this MSet changed
    as a result of the operation. */
    public boolean reduce ( Object o ) {
        boolean updatedEmpty = false;
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (updatedEmpty == false && this.items[column][row] == null) {
                    updateEmpty(column, row);
                    updatedEmpty = true;
                }

                if (o.equals(this.items[column][row])) {
                    this.numberOfItems[column][row]--;
                    if (this.numberOfItems[column][row] == 0) {
                        this.items[column][row] = null;
                        numberOfUniqueItems--;
                    }
                    
                    return true;
                }
            }
        }

        return false;
    }

    /** Removes all of this collection's elements that are also contained in the specified collection. 
        After this call returns, this collection will contain no elements in common with the specified collection. */
    public boolean removeAll ( Collection c ) {
        boolean changed = false;
        for (Object o : c) {
            if (this.remove(o)) {
                changed = true;
            }
        }

        return changed;
    }

    /** Retains only the elements in this collection that are contained in the specified collection. 
         In other words, removes from this collection all of its elements that are not contained in the 
         specified collection. */
    public boolean retainAll ( Collection c ) {
        boolean updatedEmpty = false;
        boolean changed = false;
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (updatedEmpty == false && this.items[column][row] == null) {
                    updateEmpty(column, row);
                    updatedEmpty = true;
                }

                if (!c.contains(this.items[column][row])) {
                    this.items[column][row] = null;
                    this.numberOfItems[column][row] = 0;
                    numberOfUniqueItems--;
                    changed = true;
                }
            }
        }

        return changed;
    }
    
    /** Returns the number of elements in this collection, including duplicates. 
        If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE. */
    public int size () {
        boolean updatedEmpty = false;
        long totalItems = 0;
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (updatedEmpty == false && this.items[column][row] == null) {
                    updateEmpty(column, row);
                    updatedEmpty = true;
                }

                totalItems += this.numberOfItems[column][row];
            }
        }

        if (totalItems > Integer.MAX_VALUE) {
            totalItems = Integer.MAX_VALUE;
        }

        return (int)totalItems;
    }

    /** Returns the number of UNIQUE elements in this collection (i.e., not including duplicates). 
    If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE. */
    public int unique () {
        return numberOfUniqueItems;
    }

    /** Returns an array containing all of the UNIQUE elements in this collection. */
    public Object[] toArray () {
        Object[] x = new Object[numberOfUniqueItems];
        int i = 0;

        boolean updatedEmpty = false;
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (updatedEmpty == false && this.items[column][row] == null) {
                    updateEmpty(column, row);
                    updatedEmpty = true;
                }

                if (this.items[column][row] != null) {
                    x[i] = this.items[column][row];
                    i++;
                }
            }
        }        

        return x;
    }

    /** Throws an UnsupportedOperationException. */
    public Object[] toArray ( Object[] a ) {
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
        String result = "";

        boolean updatedEmpty = false;
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < 1024; row++) {
                if (updatedEmpty == false && this.items[column][row] == null) {
                    updateEmpty(column, row);
                    updatedEmpty = true;
                }

                if (this.items[column][row] != null) {
                    result += "{" + this.items[column][row].toString() + ", " + this.numberOfItems[column][row] + "}";
                }
            }
        }

        return result;
    }
    
}