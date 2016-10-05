/**
 * The <tt> testarray </tt> class tests every part of the
 * <tt> ArrayList </tt> class
 **/
public class testarray {
    public static void main(String[] args) {
        ArrayList<Integer> defaultInts = new ArrayList<Integer>();
        System.out.println("Default construtor initialized");
        ArrayList<Integer> ints = new ArrayList<Integer>(2);
        System.out.println("With capacity construtor initialized (capacity = 2)");
        for (int i = 0; i < 10; i++) {
            ints.add(i);
        }
        System.out.println("Added ten integers (0..9)");
        ints.add(3, 20);
        System.out.println("Added 20 to index 3");
        System.out.println("The collection looks like: " + ints);
        System.out.println("The 6th element is: " + ints.get(5));
        System.out.println("The size of this collection is: " + ints.size());
        if (ints.isEmpty()) {
            System.out.println("The collection is empty");
        } else {
            System.out.println("The collection is not empty");
        }
        if (ints.isIn(10)) {
            System.out.println("The element 10 is in the collection");
        } else {
            System.out.println("The element 10 is not in the collection");
        }
        System.out.println("The element '6' is at index: " + ints.find(6));
        ints.remove(2);
        System.out.println("Removed element at index 2");
        System.out.println("The collection looks like: " + ints);
    }
}
