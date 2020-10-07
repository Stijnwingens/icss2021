package nl.han.ica.datastructures.queue;

import nl.han.ica.datastructures.linkedList.HANLinkedListImpl;
import nl.han.ica.datastructures.linkedList.IHANLinkedList;

public class HANQueueImpl<T> implements IHANQueue<T> {
    IHANLinkedList<T> list;

    public HANQueueImpl() {
        this.list = new HANLinkedListImpl<>();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.getSize() == 0;
    }

    @Override
    public void enqueue(T value) {
        list.addFirst(value);
    }

    @Override
    public T dequeue() {
        T temp = list.getFirst();
        list.delete(0);
        return temp;
    }

    @Override
    public T peek() {
        return list.getFirst();
    }

    @Override
    public int getSize() {
        return list.getSize();
    }
}
