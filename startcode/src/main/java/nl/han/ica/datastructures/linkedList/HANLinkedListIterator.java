package nl.han.ica.datastructures.linkedList;

import java.util.Iterator;

public class HANLinkedListIterator<T> implements Iterator<T> {
    private HANLinkedListImpl.Node<T> current;

    public HANLinkedListIterator(HANLinkedListImpl<T> list) {
        current = list.getFirstNode();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        T data = current.data;
        current = current.next;
        return data;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
