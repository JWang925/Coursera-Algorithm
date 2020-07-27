/* *****************************************************************************
 *  Name: JWang925
 *  Date: 2020Jul11
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // to achieve constant time first/last removal, we need double linked list
    // as the underlying representation for this deque, also keep track of size
    // and where first and last item lives
    private Node<Item> first; // pointer to first
    private Node<Item> last; // pointer to last
    private int size;


    // private Item[] deque; // pointer to the deque

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("addFirst argument is null");

        if (isEmpty()) {
            first = new Node<Item>(item, null, null);
            last = first;
        }
        else {
            first.prev = new Node<Item>(item, null, first);
            first = first.prev;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("addLast argument is null");

        if (isEmpty()) {
            first = new Node<Item>(item, null, null);
            last = first;
        }
        else {
            last.next = new Node<Item>(item, last, null);
            last = last.next;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        Node<Item> temp = first;
        if (size == 1) {
            first = null;
            last = null;
            size--;
        }
        else {
            first = first.next;
            first.prev = null;
            size--;
            return temp.item;
        }
        return temp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }

        Node<Item> temp = last;
        if (size == 1) {
            first = null;
            last = null;
            size--;
        }
        else {
            last = last.prev;
            size--;
            last.next = null;
        }
        return temp.item;
    }

    // return an iterator over items in order from front to back
    // need to implement
    public Iterator<Item> iterator() {
        if (isEmpty()) {
            return new NodeIterator<Item>(null);
        }
        else {
            return new NodeIterator<Item>(first);
        }
    }

    private void print() {
        NodeIterator<Item> itr = new NodeIterator<>(first);
        if (isEmpty()) {
            return;
        }
        while (itr.hasNext()) {
            System.out.print(" " + itr.next());
        }
        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Instantiate a deque

        /*
        Deque<Integer> intDeque = new Deque<Integer>();
        System.out.println("is empty?" + intDeque.isEmpty());
        intDeque.addFirst(3);
        intDeque.print();
        intDeque.addFirst(3);
        intDeque.addFirst(3);
        intDeque.print();
        intDeque.removeLast();
        intDeque.print();

         */
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.removeFirst();
        /*
        intDeque.addFirst(2);
        intDeque.print();
        intDeque.addFirst(1);
        intDeque.print();
        intDeque.addLast(4);
        intDeque.print();
        intDeque.addLast(5);
        intDeque.print();
        intDeque.removeFirst();
        intDeque.print();
        intDeque.removeFirst();
        intDeque.print();
        intDeque.removeLast();
        intDeque.print();
        intDeque.removeLast();
        intDeque.print();
        intDeque.removeLast();
        intDeque.print();
        */

    }

    private class Node<T> {
        public T item;
        public Node<T> prev;
        public Node<T> next;

        // constructor
        Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private class NodeIterator<T> implements Iterator<T> {

        private Node<T> currentNode = null;

        public NodeIterator(Node<T> node) {
            this.currentNode = node;
        }

        public boolean hasNext() {
            return currentNode != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("operation not allowed");

        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements");
            T current = this.currentNode.item;
            this.currentNode = this.currentNode.next;
            return current;
        }


    }
}
