// package multisets;

import java.util.Collection;
import java.util.Iterator;

/**
    Test program for class MSet.
*/

public class MSetTester {

	/** Test program for some of the material in this class. */
	public static void main ( String[] args ) {

        toStringTests();
        constructorTests();
        addTests();
        addAllTests();
        clearTests();
		
        isEmptyTests();
        sizeTests();
        uniqueTests();
        countTests();
        equalsTests();
		
        containsTests();
        containsAllTests();
        iteratorTests();
        toArrayTests();
		reduceTests();
		
        removeTests();
        removeAllTests();
        retainAllTests();
        
		// hashCodeTests();

    }
    

    static void toStringTests() {
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add(new Integer(-1));
            m.add("dog");
            m.add("cat");
            m.add(new Object());
            m.add("mouse");
            m.add("dog");
			System.out.println ( "\nStrings are like: \t" + m.toString() );
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from toStringTests()");
        }
    }
 
 
    static void constructorTests() { // for both kinds of constructors
        System.out.println ("\nBegin constructorTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add(new Integer(-1));
            m.add("dog");
            m.add("cat");
            m.add(new Object());
            m.add("mouse");
            m.add("dog");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from (null) constructorTests()");
        }
        
        try {
            java.util.ArrayList a = new java.util.ArrayList();
            a.add("cat");
            a.add(new Integer(-1));
            a.add("dog");
            a.add("cat");
            a.add(new Object());
            a.add("mouse");
            a.add("dog");
            MSet m = new MSet(a);
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from (Collection) constructorTests()");
        }
    }
   
    
    static void addTests() {
        System.out.println ("\nBegin addTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add(new Integer(-1));
            m.add("dog");
            m.add("cat");
            m.add(new Object());
            m.add("mouse");
            m.add("dog");
            assert m.size() == 7 && m.unique() == 5;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from (null) constructorTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from (null) constructorTests()");
        }
            
    }

    static void addAllTests() {
        System.out.println ("\nBegin addAllTests()");
        
        try {
            java.util.ArrayList a = new java.util.ArrayList();
            a.add("cat");
            a.add(new Integer(-1));
            a.add("dog");
            a.add("cat");
            a.add(new Object());
            a.add("mouse");
            a.add("dog");
            MSet m = new MSet(a);
            assert m.size() == 7 && m.unique() == 5;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from (Collection) constructorTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from (Collection) constructorTests()");
        }    
    }

    static void clearTests() {
        System.out.println ("\nBegin clearTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
            m.clear();
            assert m.size() == 0;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from clearTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from clearTests()");
        }
    }

    static void containsTests() {
        System.out.println ("\nBegin containsTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
            assert m.contains("cat") && m.contains("dog") && m.contains("mouse") && ! m.contains("moose");
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from containsTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from containsTests()");
        }     
    }

    static void containsAllTests() {
        System.out.println ("\nBegin containsAllTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
            java.util.ArrayList a = new java.util.ArrayList();
            a.add("cat");
            a.add("dog");
            a.add("mouse");
            assert m.containsAll(a);
            a.add("moose");
            assert ! m.containsAll(a);
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from containsTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from containsTests()");
        }        
    }

    static void countTests() {
        System.out.println ("\nBegin countTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
            assert m.count("cat") == 2 && m.count("dog") == 2 && m.count("mouse") == 1 && m.count("moose") == 0;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from countTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from countTests()");
        }    
    }

    static void equalsTests() {
        System.out.println ("\nBegin equalsTests()");
        
        try {
            MSet m1 = new MSet();
            m1.add("cat");
            m1.add("dog");
            m1.add("cat");
            m1.add("mouse");
            m1.add("dog");
            
            MSet m2 = new MSet();
            m2.add("mouse");
            m2.add("dog");
            m2.add("cat");
            m2.add("dog");
            m2.add("cat");
            assert m1.equals(m2);
            
            m2.add("dog");
            assert ! m1.equals(m2);
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from equalsTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from equalsTests()");
        }
    }

    static void hashCodeTests() {
        System.out.println ("\nBegin hashCodeTests()");
        try {
            throw new UnsupportedOperationException();
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from hashCodeTests()");
        } 
    }

    static void isEmptyTests() {
        System.out.println ("\nBegin isEmptyTests()");
        try {
            MSet m = new MSet();
            assert m.isEmpty();
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from isEmptyTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from isEmptyTests()");
        }    
    }

    static void iteratorTests() {
      System.out.println ("\nBegin iteratorTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
            int count = 0;
            for ( Object o : m ) {
                count++;
            }
            assert count == 3;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from iteratorTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from iteratorTests()");
        } 
    }


	static void reduceTests() {
        System.out.println ("\nBegin reduceTests()");
        try {
            MSet m1 = new MSet();
            m1.add("cat");
            m1.add("dog");
            m1.add("cat");
            m1.add("mouse");
            m1.add("dog");
			m1.reduce("cat");
			assert m1.size() == 4;
			m1.reduce("dog");
			assert m1.size() == 3;
			m1.reduce("mouse");
			assert m1.size() == 2;
			m1.reduce("cat");
			assert m1.size() == 1 && ! m1.reduce("cat");
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from reduceTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from reduceTests()");
        }	
	}
	
	
    static void removeTests() {
		System.out.println ("\nBegin removeTests()");
        try {
            MSet m1 = new MSet();
            m1.add("cat");
            m1.add("dog");
            m1.add("cat");
            m1.add("mouse");
            m1.add("dog");
			m1.remove("cat");
			assert m1.count("cat") == 0;
			assert ( ! m1.remove("cat") ) && ( ! m1.remove("moose") );
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from removeTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from removeTests()");
        }	   
    }

    static void removeAllTests() {
		System.out.println ("\nBegin removeAllTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
			m.remove("cat");
			
            java.util.ArrayList a = new java.util.ArrayList();
            a.add("cat");
            a.add("moose");
			
            m.removeAll(a);
			assert m.unique() == 2;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from removeAllTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from removeAllTests()");
        }    
    }

    static void retainAllTests() {
		System.out.println ("\nBegin retainAllTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
			
            java.util.ArrayList a = new java.util.ArrayList();
            a.add("cat");
            a.add("moose");
            a.add("mouse");
			
            m.retainAll(a);
			assert m.unique() == 2 && m.size() == 3 && ! m.contains("dog");
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from retainAllTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from retainAllTests()");
        } 
    }
    
    static void sizeTests() {
        System.out.println ("\nBegin sizeTests()");
        
        try {
            MSet m = new MSet();
            assert m.size() == 0;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from (empty) sizeTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from (empty) sizeTests()");
        }
        
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
            assert m.size() == 5;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from (non-empty) sizeTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from (non-empty) sizeTests()");
        }
    }

    static void toArrayTests() {
      System.out.println ("\nBegin toArrayTests()");
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
            
            Object[] objects = m.toArray();
            assert objects.length == 3;
            
            boolean mouseFoundInArray = false;
            for ( int i = 0; i < objects.length; i++ ) {
                if ( "mouse".equals(objects[i].toString() ) ) { mouseFoundInArray = true; }
            }
            assert mouseFoundInArray;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from toArrayTests()");
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from toArrayTests()");
        }
    
    }



    static void uniqueTests() {
        System.out.println ("\nBegin uniqueTests()");

        try {
            MSet m = new MSet();
            m.add("cat");
            m.add("dog");
            m.add("cat");
            m.add("mouse");
            m.add("dog");
            assert m.unique() == 3;
        }
        catch ( AssertionError e ) {
            System.out.println ("Assertion error from (strings-only) uniqueTests()");
        
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from (strings-only) uniqueTests()");
        }
        
        try {
            MSet m = new MSet();
            m.add("cat");
            m.add(new Integer(-1));
            m.add("dog");
            m.add("cat");
            m.add (new Object() );
            m.add("mouse");
            m.add("dog");
            assert m.unique() == 5;
        }        catch ( AssertionError e ) {
            System.out.println ("Assertion error from (strings-only) uniqueTests()");
        
        }
        catch ( Exception e ) {
            System.out.println ("Abnormal termination from (strings-only) uniqueTests()");
        }

    }

}