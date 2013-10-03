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

    private int rowLength = 1024;

    /** Constructs an MSet with no elements. */
    public MSet () {
        this.items = new Object[Integer.MAX_VALUE/rowLength][];
        this.items[0] = new Object[rowLength];
        this.numberOfItems = new long[Integer.MAX_VALUE/rowLength][];
        this.numberOfItems[0] = new long[rowLength];
        this.numberOfUniqueItems = 0;
        Arrays.fill(this.numberOfItems[0], 0);
        this.firstEmpty = new int[2];
        Arrays.fill(this.firstEmpty, 0);
    }

    /** Constructs an MSet from the given collection. */
    public MSet ( Collection c ) {
        this.items = new Object[Integer.MAX_VALUE/rowLength][];
        this.items[0] = new Object[rowLength];
        this.numberOfItems = new long[Integer.MAX_VALUE/rowLength][];
        this.numberOfItems[0] = new long[rowLength];
        this.numberOfUniqueItems = 0;
        Arrays.fill(this.numberOfItems[0], 0);
        this.firstEmpty = new int[2];
        Arrays.fill(this.firstEmpty, -1);

        this.addAll(c);
    }

    /** Private custom methods */
    private int[] indexOf (Object o) {
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < rowLength; row++) {
                if (this.items[column][row] != null && o.equals(this.items[column][row])) {
                    return new int[]{column, row};
                }
            }
        }

        return new int[]{-1, -1};
    }

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
        int[] x = this.indexOf(o);

        if (x[0] != -1) {
            if (add) {
                this.numberOfItems[x[0]][x[1]]++;
            }
            return true;
        }

        return false;
    }


    /** Ensures that this collection contains the specified element. 
        Returns true if this collection changed as a result of the call. (Returns 
        false if this collection does not permit duplicates and already contains the specified 
        element.) */
    public boolean add ( Object o ) {
        if (o == null) {
            throw new NullPointerException("This collection does not allow null objects.");
        }

        if (this.containsAndMaybeAdds(o, true)) {
            return true;
        }

        if (this.firstEmpty[0] != -1) {
            this.items[this.firstEmpty[0]][this.firstEmpty[1]] = o;
            this.numberOfItems[this.firstEmpty[0]][this.firstEmpty[1]] += 1;
            this.numberOfUniqueItems++;
            Arrays.fill(this.firstEmpty, -1);
            return true;
        }

        int column = 0;
        while (column < this.items.length && this.items[column] != null) {
            for (int row = 0; row < rowLength; row++) {
                if (this.items[column][row] == null) {
                    this.items[column][row] = o;
                    this.numberOfItems[column][row]++;
                    this.numberOfUniqueItems++;
                    return true;
                }
            }
            column++;
        }

        try {
            this.items[column] = new Object[rowLength];
            this.items[column][0] = o;
            this.numberOfItems[column] = new long[rowLength];
            Arrays.fill(this.numberOfItems[column], 0);
            this.numberOfItems[column][0] = 1;
            this.numberOfUniqueItems++;
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
        this.items = new Object[Integer.MAX_VALUE/rowLength][];
        this.items[0] = new Object[rowLength];
        this.numberOfItems = new long[Integer.MAX_VALUE/rowLength][];
        this.numberOfItems[0] = new long[rowLength];
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
        int[] x = this.indexOf(o);

        if (x[0] != -1) {
            return (int)this.numberOfItems[x[0]][x[1]];
        }

        return 0;
    }

    /** Compares the specified object with this collection for equality. Overrides Object.equals(). */
    public boolean equals ( Object o ) {
        if (!(o instanceof MSet) ||
            this.numberOfUniqueItems != ((MSet)o).numberOfUniqueItems) {
            return false;
        }

        for (int tColumn = 0; tColumn < this.items.length && this.items[tColumn] != null; tColumn++) {
            nextItem:
            for (int tRow = 0; tRow < this.rowLength; tRow++) {
                if (this.items[tColumn][tRow] != null) {
                    for (int oColumn = 0; oColumn < ((MSet)o).items.length && ((MSet)o).items[oColumn] != null; oColumn++) {
                        for (int oRow = 0; oRow < ((MSet)o).rowLength; oRow++) {
                            if (((MSet)o).items[oColumn][oRow] != null &&
                                this.items[tColumn][tRow].equals(((MSet)o).items[oColumn][oRow])) {
                                
                                if (this.numberOfItems[tColumn][tRow] == ((MSet)o).numberOfItems[oColumn][oRow]) {
                                    continue nextItem;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    /** Returns a hash code value for this collection. May override Object.hashCode(). */
    public int hashCode () {            // Needs moar comments
        long combinedHash = 0;
        
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < rowLength; row++) {
                if (this.items[column][row] != null) {
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
            for (int row = 0; row < rowLength; row++) {
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
        return new SpecIterator(this);
    }

    /** Removes a single instance of the specified element from this collection, if it is present. */
    public boolean remove ( Object o ) {
        int[] x = this.indexOf(o);
        if (x[0] != -1) {
            this.items[x[0]][x[1]] = null;
            this.numberOfItems[x[0]][x[1]] = 0;
            this.numberOfUniqueItems--;

            if ((this.firstEmpty[0] > x[0]) || (this.firstEmpty[0] == x[0] && this.firstEmpty[1] > x[1])) {
                this.firstEmpty = x;
            }
            return true;
        }

        return false;
    }


    /** Decrements the number of copies of o in this MSet. Returns true iff this MSet changed
    as a result of the operation. */
    public boolean reduce ( Object o ) {
        int[] x = this.indexOf(o);
        if (x[0] != -1) {
            this.numberOfItems[x[0]][x[1]]--;
            if (this.numberOfItems[x[0]][x[1]] == 0) {
                this.items[x[0]][x[1]] = null;
                this.numberOfUniqueItems--;
            }
            
            return true;
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
        boolean changed = false;
        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < rowLength; row++) {
                if (this.items[column][row] != null && !c.contains(this.items[column][row])) {
                    this.items[column][row] = null;
                    this.numberOfItems[column][row] = 0;
                    this.numberOfUniqueItems--;
                    changed = true;
                }
            }
        }

        return changed;
    }
    
    /** Returns the number of elements in this collection, including duplicates. 
        If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE. */
    public int size () {
        long totalItems = 0;
        for (int column = 0; column < this.numberOfItems.length && this.numberOfItems[column] != null; column++) {
            for (int row = 0; row < rowLength; row++) {
                totalItems += this.numberOfItems[column][row];
            }
        }

        return (int)totalItems;
    }

    /** Returns the number of UNIQUE elements in this collection (i.e., not including duplicates). 
    If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE. */
    public int unique () {
        return this.numberOfUniqueItems;
    }

    /** Returns an array containing all of the UNIQUE elements in this collection. */
    public Object[] toArray () {
        Object[] x = new Object[numberOfUniqueItems];
        int i = 0;

        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < rowLength; row++) {

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

        for (int column = 0; column < this.items.length && this.items[column] != null; column++) {
            for (int row = 0; row < rowLength; row++) {
                if (this.items[column][row] != null) {
                    result += "{" + this.items[column][row].toString() + ", " + this.numberOfItems[column][row] + "}";
                }
            }
        }

        return result;
    }
    
    private class SpecIterator implements Iterator {
        private MSet m;
        private int i;

        public SpecIterator (MSet m) {
            this.m = m;
            i = -1;
        }

        public Object next () {
            i++;
            while (m.items[i/rowLength][i%rowLength] == null) {
                i++;
            }

            return m.items[i/rowLength][i%rowLength];
        }

        public boolean hasNext() {
            int j = i + 1;
            while (m.items[j/rowLength] != null) {
                if (m.items[j/rowLength][j%rowLength] != null) {
                    return true;
                } else {
                    j++;
                }
            }

            return false;
        }

        public void remove () {
            m.remove(m.items[i/rowLength][i%rowLength]);
        }
    }
}