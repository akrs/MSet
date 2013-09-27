public class MSetTesting {                                  // Basic tests
    public static void main (String[] args) {
        MSet m = new MSet();

        m.add("hamster");
        m.add("cat");
        m.add("String");
        m.add(Integer(3));

        System.out.println("toString: " + m.toString());
    }
}
