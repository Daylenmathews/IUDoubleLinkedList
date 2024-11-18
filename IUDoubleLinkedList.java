
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Double linled node base implmentation of IndexedUnsortedList that supports
 * basic iterators or list iterators
 *
 * @auhtor Daylen Mathews
 *
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int versionNumber;

    /**
     * Iniatialize new empty list
     *
     */
    public IUDoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
        versionNumber = 0;
    }

    @Override
    public void addToFront(T element) {
        Node<T> newNode = new Node<T>(element);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } //Run if NOT empty 
        else {
            newNode.setNextNode(head);
            head.setPreviousNode(newNode);
        }

        head = newNode;
        size++;
        versionNumber++;
    }

    @Override
    public void addToRear(T element) {
        //Create new node
        Node<T> newNode = new Node<T>(element);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            Node<T> targetNode = tail;
            targetNode.setNextNode(newNode);
            newNode.setPreviousNode(targetNode);

            tail = newNode;
        }

        size++;
        versionNumber++;
    }

    @Override
    public void add(T element) {
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        Node<T> targetNode = head;

        while (targetNode != null && !targetNode.getElement().equals(target)) {
            targetNode = (Node<T>) targetNode.getNextNode();
        }

        if (targetNode == null) {
            throw new NoSuchElementException();
        }

        Node<T> newNode = new Node<T>(element);

        newNode.setNextNode(targetNode.getNextNode());
        newNode.setPreviousNode(targetNode);
        targetNode.setNextNode(newNode);

        if (newNode.getNextNode() != null) {
            newNode.getNextNode().setPreviousNode(newNode);
        } else {
            tail = newNode;
        }

        size++;
        versionNumber++;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> newNode = new Node<T>(element);

        if (index == 0) {
            addToFront(element);
        } else if (index == size()) {
            addToRear(element);
        } else {
            Node<T> targetNode = head;
            for (int i = 0; i < index - 1; i++) {
                targetNode = (Node<T>) targetNode.getNextNode();
            }
            Node<T> afterIndexNode = (Node<T>) targetNode.getNextNode();
            targetNode.setNextNode(newNode);
            newNode.setPreviousNode(targetNode);
            newNode.setNextNode(afterIndexNode);
            afterIndexNode.setPreviousNode(newNode);

            size++;
            versionNumber++;
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T returnValue = head.getElement();
        if (size() == 1) {
            head = null;
            tail = null;
        } else {
            head = (Node<T>) head.getNextNode();
        }

        size--;
        versionNumber++;
        return returnValue;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T returnValue = tail.getElement();

        // For one element list
        if (size() == 1) {
            head = null;
            tail = null;
        } else {
            tail = (Node<T>) tail.getPreviousNode();

            // Update the new null position
            tail.setNextNode(null);
        }

        size--;
        versionNumber++;
        return returnValue;
    }

    @Override
    public T remove(T element) {
        Node<T> targetNode = head;

        while (targetNode != null && !targetNode.getElement().equals(element)) {
            targetNode = (Node<T>) targetNode.getNextNode();
        }

        if (targetNode == null) {
            throw new NoSuchElementException();
        }
        if (targetNode != head) {
            targetNode.getPreviousNode().setNextNode(targetNode.getNextNode());
        } else {
            head = (Node<T>) targetNode.getNextNode();
        }

        if (targetNode != tail) {
            targetNode.getNextNode().setPreviousNode(targetNode.getPreviousNode());
        } else {
            tail = (Node<T>) targetNode.getPreviousNode();
        }

        size--;
        versionNumber++;

        return targetNode.getElement();
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        // Always start at the beginning
        Node<T> targetNode = head;

        for (int i = 0; i < index; i++) {
            targetNode = (Node<T>) targetNode.getNextNode();
        }

        if (index == 0) {
            head = (Node<T>) targetNode.getNextNode();
        } else {
            targetNode.getPreviousNode().setNextNode(targetNode.getNextNode());
        }
        if (targetNode == tail) {
            tail = (Node<T>) targetNode.getPreviousNode();
        } else {
            targetNode.getNextNode().setPreviousNode(targetNode.getPreviousNode());
        }

        size--;
        versionNumber++;
        return targetNode.getElement();
    }

    @Override
    public void set(int index, T element) {
        // Check if index is in bounds
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> targetNode = head;

        if (index == 0) {
            targetNode.setElement(element);
        } else {
            for (int i = 0; i < index; i++) {
                targetNode = (Node<T>) targetNode.getNextNode();
            }
            targetNode.setElement(element);
        }

        versionNumber++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        T returnValue;

        // In the case of index is 0, the head
        if (index == 0) {
            returnValue = head.getElement();
        } else {
            Node<T> targetNode = head;

            for (int i = 0; i < index; i++) {
                targetNode = (Node<T>) targetNode.getNextNode();
            }
            returnValue = targetNode.getElement();
        }
        return returnValue;
    }

    @Override
    public int indexOf(T element) {
        Node<T> targetNode = head;
        int currentIndex = 0;

        while (targetNode != null && !targetNode.getElement().equals(element)) {
            targetNode = (Node<T>) targetNode.getNextNode();

            currentIndex++;
        }
        if (targetNode == null) {

            currentIndex = -1;
        }
        return currentIndex;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.getElement();
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        return indexOf(target) > -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (T element : this) {
            stringBuilder.append(element.toString());
            stringBuilder.append(", ");
        }

        if (size() > 0) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        return new DLLIterator(startingIndex);
    }

    /**
     * ListIterator for use with a Double Linked List, and also inherits a basic
     * iterator
     */
    private class DLLIterator implements ListIterator<T> {

        private Node<T> nextNode;
        private int nextIndex;
        private int iterVersionNumber;
        private Node<T> lastReturnedNode;

        /**
         * Iterator that starts at the beginning of the list
         */
        public DLLIterator() {
            nextNode = head;
            nextIndex = 0;
            iterVersionNumber = versionNumber;
            lastReturnedNode = null;
        }

        /**
         * Iterator that can start at a desired index
         *
         * @param startIndex takes in a given index value to start
         */
        public DLLIterator(int startIndex) {
            if (startIndex < 0 || startIndex > size()) {
                throw new IndexOutOfBoundsException();
            }

            nextNode = head;
            for (int i = 0; i < startIndex; i++) {
                nextNode = (Node<T>) nextNode.getNextNode();
            }

            nextIndex = startIndex;
            iterVersionNumber = versionNumber;
            lastReturnedNode = null;
        }

        @Override
        public boolean hasNext() {
            if (iterVersionNumber != versionNumber) {
                throw new ConcurrentModificationException();
            }

            return nextNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T returnValue = nextNode.getElement();
            lastReturnedNode = nextNode;
            nextNode = (Node<T>) nextNode.getNextNode();

            nextIndex++;
            return returnValue;
        }

        @Override
        public boolean hasPrevious() {
            if (iterVersionNumber != versionNumber) {
                throw new ConcurrentModificationException();
            }

            return nextNode != head;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            if (nextNode == null) {
                nextNode = tail;
            } else {
                nextNode = (Node<T>) nextNode.getPreviousNode();
            }

            lastReturnedNode = nextNode;
            nextIndex--;
            return nextNode.getElement();
        }

        @Override
        public int nextIndex() {
            if (iterVersionNumber != versionNumber) {
                throw new ConcurrentModificationException();
            }

            return nextIndex;
        }

        @Override
        public int previousIndex() {
            if (iterVersionNumber != versionNumber) {
                throw new ConcurrentModificationException();
            }

            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (iterVersionNumber != versionNumber) {
                throw new ConcurrentModificationException();
            }

            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }

            if (lastReturnedNode != head) {
                lastReturnedNode.getPreviousNode().setNextNode(lastReturnedNode.getNextNode());
            } else {
                head = (Node<T>) head.getNextNode();
            }

            if (lastReturnedNode != tail) {
                lastReturnedNode.getNextNode().setPreviousNode(lastReturnedNode.getPreviousNode());
            } else {
                tail = (Node<T>) tail.getPreviousNode();
            }
            if (lastReturnedNode != nextNode) {
                nextIndex--;
            } else {
                nextNode = (Node<T>) nextNode.getNextNode();
            }

            lastReturnedNode = null;
            size--;
            versionNumber++;
            iterVersionNumber++;
        }

        @Override
        public void set(T e) {
            if (iterVersionNumber != versionNumber) {
                throw new ConcurrentModificationException();
            }

            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }
            lastReturnedNode.setElement(e);

            versionNumber++;
            iterVersionNumber++;
        }

        @Override
        public void add(T e) {
            if (iterVersionNumber != versionNumber) {
                throw new ConcurrentModificationException();
            }
            if (head == null) {
                Node<T> newNode = new Node<T>(e);
                head = newNode;
                tail = newNode;
                size++;
                versionNumber++;
            } else if (nextNode == head) {
                addToFront(e);
            } else if (nextNode == null) {
                addToRear(e);
            } else {
                Node<T> newNode = new Node<T>(e);
                Node<T> prevNode = (Node<T>) nextNode.getPreviousNode();
                prevNode.setNextNode(newNode);
                newNode.setNextNode(nextNode);
                nextNode.setPreviousNode(newNode);
                newNode.setPreviousNode(prevNode);

                size++;
                versionNumber++;
            }

            nextIndex++;
            iterVersionNumber++;
        }
    }

}
