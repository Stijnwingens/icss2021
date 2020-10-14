package nl.han.ica.datastructures.linkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HANLinkedListImpl<T> implements IHANLinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public HANLinkedListImpl() {
        this.size = 0;
    }

    @Override
    public void addFirst(T value) {
        Node<T> e = new Node<>(value);
        if (size == 0) {
            first = last = e;
        } else {
            e.next = first;
            first = e;
        }
        size++;
    }

    @Override
    public void clear() {
        if (size > 0) {
            first = last = null;
            size = 0;
        }
    }

    @Override
    public void insert(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = new Node<>(value);
        Node<T> previous = getNode(--index);

        if (previous != last) {
            current.next = previous.next.next;
        }
        previous.next = current;
    }

    @Override
    public void delete(int pos) {
        if (size == 0) {
            first = last = null;
        } else {
            size--;
            Node<T> node = getNode(pos);
            if (node == first) {
                first = node.next;
            } else if (node == last) {
                last = getNode(--pos);
            } else {
                getNode(--pos).next = node.next;
            }
        }
    }

    private Node<T> getNode(int pos) {
        Node<T> node = null;
        if (pos < size) {
            node = first;
            while (pos-- > 0) {
                node = node.next;
            }
        }
        return node;
    }

    public void addLast(T e) {
        Node<T> node = new Node<>(e);
        if (size == 0) {
            first = last = node;
        } else {
            last.next = node;
            last = node;
        }
        size++;
    }

    @Override
    public T get(int pos) {
        Node<T> node = null;
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException();
        }
        if (pos < size) {
            node = first;
            while (pos-- > 0) {
                node = node.next;
            }
        }
        return node.data;
    }

    @Override
    public void removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        size--;
        if (first.next != null) {
            first = first.next;
        } else {
            first = last = null;
        }
    }

    @Override
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return first.data;
    }

    Node<T> getFirstNode() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return first;
    }

    @Override
    public Iterator<T> iterator() {
        return new HANLinkedListIterator<>(this);
    }

    @Override
    public int getSize() {
        return size;
    }

    /**
     * Struct to hold the data
     *
     * @param <T>
     */
    static final class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }
}