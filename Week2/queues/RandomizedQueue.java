/* *****************************************************************************
 *  Name: JWang925
 *  Date: 2020Jul11
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // Goal constant time removal, enqueue and dequeue(pick by random)
    // construct an empty randomized queue


    private Object[] data; // reference to array
    private int size;
    private int arraySize;


    public RandomizedQueue() {
        size = 0;
        arraySize = 1;

        // regarding creation of array of generic, the following is used as reference
        // stack overflow answer: https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
        data = new Object[arraySize];

    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("argument is null");
        if (size == arraySize)
            resizeUp();
        if (size < arraySize / 4)
            resizeDown();

        data[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }

        Item retValue;
        if (size == 1) {
            retValue = (Item) data[0];
            data[0] = null;
        }
        else {
            int index = StdRandom.uniform(0, size - 1);
            retValue = (Item) data[index];
            data[index] = null;
            data[index] = data[size - 1];
            data[size - 1] = null;
        }
        size--;

        return retValue;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        Item retValue;
        if (size == 1) {
            retValue = (Item) data[0];
        }
        else {
            int index = StdRandom.uniform(0, size - 1);
            retValue = (Item) data[index];
        }
        return retValue;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator<Item>();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> testRandomQueue = new RandomizedQueue<>();
        testRandomQueue.enqueue(1);
        testRandomQueue.dequeue();
        testRandomQueue.enqueue(2);
        testRandomQueue.enqueue(3);
        testRandomQueue.enqueue(4);
        testRandomQueue.enqueue(5);
        testRandomQueue.enqueue(6);
        testRandomQueue.enqueue(7);
        testRandomQueue.enqueue(8);
        testRandomQueue.print();
        Iterator<Integer> itr = testRandomQueue.iterator();
        Iterator<Integer> itr2 = testRandomQueue.iterator();
        System.out.print(itr.next());
        System.out.print(itr.next());
        System.out.print(itr.next());
        System.out.print(itr.next());
        System.out.print(itr.next());
        System.out.print(itr.next());
        System.out.print(itr.next());
        System.out.print(itr2.next());
        System.out.print(itr2.next());
        System.out.print(itr2.next());
        System.out.print(itr2.next());
        System.out.print(itr2.next());
        System.out.print(itr2.next());
        System.out.print(itr2.next());


    }

    private class RandomQueueIterator<T> implements Iterator<T> {
        private int sizeCopy = 0;
        private int index;
        private T currentItem;
        private Object[] dataCopy;

        public RandomQueueIterator() {
            if (size == 0) {
                this.currentItem = null;
                return;
            }

            dataCopy = new Object[arraySize];
            sizeCopy = size;
            for (int i = 0; i < sizeCopy; i++) {
                dataCopy[i] = data[i];
            }

            if (sizeCopy != 1) {
                index = StdRandom.uniform(0, sizeCopy - 1);
                this.currentItem = (T) dataCopy[index];
            }
            else {
                index = 0;
                this.currentItem = (T) dataCopy[index];
            }


        }

        public boolean hasNext() {
            return sizeCopy != 0;
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements");

            if (sizeCopy == 1)
                index = 0;
            else
                index = StdRandom.uniform(0, sizeCopy - 1);

            this.currentItem = (T) dataCopy[index];

            dataCopy[index] = null;
            dataCopy[index] = dataCopy[sizeCopy - 1];
            dataCopy[sizeCopy - 1] = null;
            sizeCopy--;

            return currentItem;
        }
    }


    private void resizeUp() {
        arraySize = arraySize * 2;
        Object[] temp = new Object[arraySize];

        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }

        data = temp;
    }

    private void resizeDown() {
        arraySize = arraySize / 2;
        Object[] temp = new Object[arraySize];

        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }

        data = temp;
    }

    private void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(" " + data[i]);
        }
        System.out.println();
    }

}
