package project3;

import java.util.Iterator;

/**
 * This file is implementing the MDeque data structure.
 * Where all of the methods and iterators listed on the Javadocs are
 * implemented, plus a reverse method.
 * 
 * @author Henry Yuan
 *
 */
public class MDeque<E> implements Iterable<E> {
    private Node front; // front pointer
    private Node middle; // middle pointer
    private Node back; // back pointer
    private int counter; // size of MDeque
    private boolean midBool = false; // peakMiddle() tracker

    public MDeque() { //create a new MDeque
        front = null;
        middle = null;
        back = null;
        counter = 0;
    }

    /* (non-Javadoc)
     * Returns an iterator over the elements in this mdeque in proper sequence.
     *
     * @return java.util.Iterator<E>
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return new ForwardIterator(front);
    }

    private class ForwardIterator implements Iterator<E> {
        Node current;

        ForwardIterator(Node head) { // set the starting point for the foward iterator
            this.current = head;
        }

        /*
         * (non-Javadoc)
         * Returns {@code true} if the iteration has more elements.
         * 
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /*
         * (non-Javadoc)
         * 
         * @return the next element in the iteration
         * 
         * @throws NoSuchElementException if the iteration has no more elements
         * 
         * @see java.util.Iterator#next()
         */
        @Override
        public E next() {
            E data = current.getElement();
            current = current.getNext();
            return data;
        }

        /*
         * (non-Javadoc)
         * throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         * 
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns an iterator over the elements in this mdeque in reverse sequential
     * order.
     * 
     * @return java.util.Iterator<E>
     */
    public Iterator<E> reverseIterator() { // set the starting point for the reverse iterator
        return new ReverseIterator(back);
    }

    private class ReverseIterator implements Iterator<E> {
        Node current;

        /**
         * @param tail starting Node for the reverse iterator.
         */
        ReverseIterator(Node tail) {
            this.current = tail;
        }

        /*
         * (non-Javadoc)
         * Returns {@code true} if the iteration has more elements.
         * 
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /*
         * (non-Javadoc)
         * Returns the next element in the iteration.
         * 
         * @see java.util.Iterator#next()
         */
        @Override
        public E next() {
            E data = current.getElement();
            current = current.getPrev();
            return data;
        }

        /*
         * (non-Javadoc)
         * throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         * 
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    class Node {
        E element;
        public Node next;
        public Node prev;

        /**
         * @param element creates a new node with element E as its data.
         * 
         */
        public Node(E element) {
            this.element = element;
            next = null;
            prev = null;
        }

        /**
         * @return this.element
         */
        public E getElement() {
            return element;
        }

        /**
         * @return the next Node
         */
        public Node getNext() {
            return next;
        }

        /**
         * @param next set the next node to this.next
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * @return the previous Node
         */
        public Node getPrev() {
            return prev;
        }

        /**
         * @param next set the previous node to this.prev
         */
        public void setPrev(Node prev) {
            this.prev = prev;
        }

    }

    /**
     * type void
     * Reverses the MDeque pointers from prev to next and vice versa.
     * Also switches the front and back pointers while updating the middle pointer.
     */
    public void reverse() {
        Node prev = null;
        Node current = front;
        Node next = null;
        Node frontHold = front;

        while (current != null) { // traverse through the whole MDeque until all of the Nodes are reversed.
            next = current.next;
            current.next = prev;
            current.prev = next;
            prev = current;
            current = next;

        }

        front = back;
        front.setPrev(null);

        back = frontHold;
        back.setNext(null);
        if (size() % 2 == 0 && size() > 0) { // update the middle pointer.
            middle = middle.getNext();
        }
    }

    /**
     * @return Retrieves the back element of this mdeque.
     */
    public E peekBack() {
        if (size() == 0) { // check if the MDeque is empty
            return null;
        }
        return back.getElement();
    }

    /**
     * @return Retrieves the first element of this mdeque.
     */
    public E peekFront() {
        if (size() == 0) { // check if the MDeque is empty
            return null;
        }
        return front.getElement();
    }

    /**
     * @return Retrieves the middle element of this mdeque.
     */
    public E peekMiddle() {
        if (size() == 0) { // check if the MDeque is empty
            return null;
        }
        if (midBool == true && size() > 2) { // if the MDeque was added to using pushMiddle at anytime when the size of
                                             // the MDeque was odd,
            return middle.getNext().getElement(); // then the middle.getNext() node must be used to retrieve the correct
                                                  // node.
        }
        return middle.getElement();

    }

    /**
     * @return Retrieves and removes the back element of this mdeque.
     */
    public E popBack() {
        if (this.size() == 0) { // check if the MDeque is empty
            return null;
        }
        Node hold = back;
        back = back.getPrev(); // assign back to the previous node

        if (back == null) { // check if back is null as if it is then the MDeque is now empty.
            front = null;
            middle = null;
        }
        if (back != null) { // if back is not null, then it now points to null making it the back pointer.
            back.next = null;
        }

        counter--; // size decreases by one

        if (counter % 2 == 0 && counter > 0) { // update the middle pointer.
            if (middle.getPrev() != null) {
                middle = middle.getPrev();
            }
        }

        return hold.getElement(); // returns initial back node element.
    }

    /**
     * @return Retrieves and removes the first element of this mdeque.
     */
    public E popFront() {
        if (this.size() == 0) { // check if the MDeque is empty
            return null;
        }
        Node hold = front;
        front = front.getNext(); // assign front to the next node

        if (front != null) { // check if front is null as if it is not then the new front is the front
                             // pointer.
            front.prev = null;
        }

        counter--; // size decreases by one

        if (size() % 2 == 1 && size() != 1) { // update the middle pointer
            middle = middle.getNext();
        }

        return hold.getElement(); // returns initial front node element.
    }

    /**
     * @return Retrieves and removes the middle element of this mdeque.
     */
    public E popMiddle() {
        if (this.size() == 0) { // check if the MDeque is empty
            return null;
        }
        Node hold = middle;
        hold.setNext(middle.getNext()); // assign hold to the next node
        Node out = middle; // output node

        if (middle == null) {
            front = null;
            back = null;
        }

        if (middle.getPrev() == null) { // check if middle is the most front node
            if (middle.getNext() != null) { // check if middle is the most back node
                middle = middle.getNext(); // if it is then the next node is the only possible node left in the MDeque.
                middle.setPrev(null);
                front = middle;
                back = middle;
                counter--;

                return out.getElement();
            }
            front = null; // if middle.getPrev() and middle.getNext() are both null,
            back = null; // then the MDeque is now empty.
        }

        middle = middle.getPrev(); // middle is now the previous as the previous is not null.

        if (hold.getNext() != null) { // check if there was more than 2 inital nodes.
            hold = hold.getNext(); // if yes, then remove middle node.
            middle.setNext(hold);
            middle = middle.getNext();
            hold = hold.getPrev().getPrev();
            middle.setPrev(hold);
            middle = middle.getPrev();
        }

        counter--; // size decreases by one

        if (size() % 2 == 0) {// update the middle pointer
            middle = middle.getNext();
        }
        return out.getElement(); // returns initial middle node element.
    }

    /**
     * @param insert element
     *               Inserts the specified item at the back of this mdeque.
     */
    public void pushBack(E insert) {

        if (insert == null) { // check if insert is null, if true then throw IllegalArgumentException().
            throw new IllegalArgumentException();
        }
        Node newNode = new Node(insert);
        Node prevHold = back;
        if (front == null) { // check if the MDeque is empty, if it is then all pointers point to the new
                             // node.
            front = newNode;
            middle = front;
            back = front;

        } else { // if the Mdeque is not empty, then add the node to the back of the MDeque and
                 // update the back pointer.
            back.setNext(newNode);
            back = back.getNext();
            back.setPrev(prevHold);
        }
        counter++; // size increases by one

        if (counter % 2 == 0) { // update the middle pointer
            middle = middle.getNext();
        }

    }

    /**
     * @param insert element
     *               Inserts the specified item at the front of this mdeque.
     */
    public void pushFront(E insert) {

        if (insert == null) { // check if insert is null, if true then throw IllegalArgumentException().
            throw new IllegalArgumentException();
        }
        Node newNode = new Node(insert);
        Node nextHold = front;

        if (front == null) { // check if the MDeque is empty, if it is then all pointers point to the new
                             // node.
            front = newNode;
            middle = front;
            back = front;

        } else { // if the Mdeque is not empty, then add the node to the front of the MDeque and
                 // update the front pointer.
            front.setPrev(newNode);
            front = front.getPrev();
            front.setNext(nextHold);
        }

        counter++; // size increases by one

        if (size() % 2 == 1 && size() != 1) { // update the middle pointer
            middle = middle.getPrev();
        }
    }

    /**
     * @param insert element
     *               Inserts the specified item in the middle of this mdeque.
     */
    public void pushMiddle(E insert) {
        if (insert == null) { // check if insert is null, if true then throw IllegalArgumentException().
            throw new IllegalArgumentException();
        }
        Node newNode = new Node(insert);
        Node midHold = middle;

        if (front == null) { // check if the MDeque is empty, if it is then all pointers point to the new
                             // node.
            front = newNode;
            middle = front;
            back = front;
        } else if (size() % 2 == 0) { // if the size of the MDeque is even, insert the middle element to the left of
                                      // the middle pointer.
            Node nextMiddle = middle.next; // keep track of middle.next
            middle.setNext(newNode);
            middle = middle.getNext();
            middle.setPrev(midHold);
            middle.setNext(nextMiddle);
            if (middle.next != null) { // check if nextMiddle is equal to null, if not then middle is pointing at
                                       // nextMiddle.
                middle = middle.getNext();
                middle.setPrev(newNode);
            }
            middle = middle.getPrev(); // update middle pointer after adding a new node
            midBool = false;
        } else if (size() == 1) { // check if size is one as the odd cases are differnt from this case.
            back = newNode; // set back to the new node
            middle.setNext(back); // have middle point to back
            back.setPrev(middle); // back points back to middle

        } else if (size() % 2 == 1) { // check for if the size of the MDqeue is odd, if true,
                                      // then insert the middle ement to the right of the midle pointer.

            Node nextMiddle = middle.next;
            middle.setNext(newNode);

            middle = middle.getNext();
            middle.setPrev(midHold);
            middle.setNext(nextMiddle);
            if (middle.next != null) {
                middle = middle.getNext();
                middle.setPrev(newNode);
            }
            middle = middle.getPrev().getPrev();
            midBool = true; // for peakMiddle(), middle.getNext().getElement() must be used to obtain the
                            // correct node/element.
        }

        counter++; // size increases by one

    }

    /**
     * @return Returns the number of elements in this mdeque.
     */
    public int size() {
        return counter;
    }

    /*
     * (non-Javadoc)
     * toString override
     * Takes a MDeque and puts it in brackets and separates each element with a
     * comma.
     * @return Return a string of all of elements in the MDeque separated by commas and surrounded by brackets.
     * @see java.lang.Object#toString()
     * 
     */
    public String toString() {
        String out = toStringHelper(front);
        if (out.length() > 0 && out.charAt(0) == ',') { // remove the first common plus space
            out = out.substring(2);
        }
        return "[" + out + "]"; // add brackets around the whole MDeque
    }

    /**
     * @param front Node
     * @return Return a string of the MDeque starting with Node front,
     *         recursively.
     */
    private String toStringHelper(Node front) {
        String out = "";
        if (front == null) { // base case is when front is null
            return out;
        } else {
            out += ", " + String.valueOf(front.element); // concatenate a comma plus space with all of the elements
            front = front.getNext();
            return out + toStringHelper(front); // recursive call
        }

    }

}
