package nl.han.ica.datastructures.stack;

import nl.han.ica.datastructures.linkedList.HANLinkedListImpl;
import nl.han.ica.datastructures.linkedList.IHANLinkedList;

public class HANStackImpl<T> implements IHANStack<T> {
    IHANLinkedList<T> list;

    public HANStackImpl() {
        this.list = new HANLinkedListImpl<>();
    }

    @Override
    public void push(T value) {
        list.addFirst(value);
    }

    @Override
    public T pop() {
        int i = list.getSize();
        T temp = list.get(i - 1);
        list.delete(i - 1);
        return temp;
    }

    @Override
    public T peek() {
        return list.get(list.getSize() - 1);
    }
}