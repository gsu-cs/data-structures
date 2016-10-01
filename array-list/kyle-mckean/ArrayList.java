public class ArrayList<E> {
    private int capacity;
    private int pointer;
    private E[] elements;
    public ArrayList() { this(10); }
    public ArrayList(int initialCap) {
        this.capacity = initialCap;
        this.pointer  = 0;
        this.elements = (E[]) new Object[initialCap];
    }
    public void add(E element) { this.add(this.pointer, element); }
    public void add(int index, E element) {
        if (this.pointer == this.capacity) {
            this.capacity *= 2;
            E[] grown = (E[]) new Object[this.capacity];
            for(int i = 0; i < index; i++) {
                grown[i] = this.elements[i];
            }
            grown[index] = element;
            for(int i = index + 1; i <= this.pointer; i++) {
                grown[i] = this.elements[i - 1];
            }
            this.elements = grown;
        } else {
            for(int i = this.pointer - 1 ; i >= index; i--) {
                this.elements[i + 1] = this.elements[i];
            }
            this.elements[index] = element;
        }
        pointer++;
    }
    public E get(int index) { return this.elements[index]; }
    public int size() { return this.pointer; }
    public boolean isEmpty() { return this.pointer == 0; }
    public boolean isIn(E element) { return this.find(element) != -1; }
    public int find(E element) {
        for(int i = 0; i < this.pointer; i++) {
            if (this.elements[i].equals(element)) return i;
        }
        return -1;
    }
    public void remove(int index) {
        for(int i = index + 1; i < this.pointer; i++) {
            this.elements[i-1] = this.elements[i];
        }
        pointer--;
    }
    public String toString() {
        String str = "[";
        for(int i = 0; i < this.pointer; i++) {
            str += " " + this.elements[i];
        }
        return str + " ]";
    }
}
