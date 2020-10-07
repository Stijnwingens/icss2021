package nl.han.ica.datastructures.linkedList;

import java.util.Iterator;

public class HANLinkedListImpl<T> implements IHANLinkedList<T> {
    private final HANLinkedListNode<T> header;
    private int size;

    public HANLinkedListImpl() {
        header = new HANLinkedListNode<>();
        size = 0;
    }

    @Override
    public void addFirst(T data) {
        header.next = new HANLinkedListNode<>(data, header.next);
        size++;
    }

    @Override
    public void clear() {
        header.next = null;
        size = 0;
    }

    @Override
    public void insert(int index, T value) {
        HANLinkedListNode<T> n = findNode(index - 1);
        n.next = new HANLinkedListNode<>(value, n.next);
        size++;
    }

    @Override
    public void delete(int pos) {
        if (pos + 1 > size || pos < 0) throw new IndexOutOfBoundsException();
        HANLinkedListNode<T> n = findNode(pos - 1);
        n.next = n.next.next;
        size--;
    }

    @Override
    public T get(int pos) {
        if (pos + 1 > size || pos < 0) throw new IndexOutOfBoundsException();
        else return findNode(pos).data;
    }

    @Override
    public void removeFirst() {
        if (size < 1) throw new IndexOutOfBoundsException();
        header.next = header.next.next;
    }

    @Override
    public T getFirst() {
        if (size < 1) throw new IndexOutOfBoundsException();
        return header.next.data;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        throw new RuntimeException("Iterator is not implemented.");
    }

    private HANLinkedListNode<T> findNode(int index) {
        if (index > size) throw new IndexOutOfBoundsException();
        HANLinkedListNode<T> n = header;
        for (int i = 0; i < index + 1; i++) {
            n = n.next;
        }

        return n;
    }
}