package nl.han.ica.datastructures.stack;

import nl.han.ica.datastructures.linkedList.HANLinkedListImpl;

public class HANStackImpl<T> implements IHANStack<T> {

    private final HANLinkedListImpl<T> helper;

    public HANStackImpl() {
        helper = new HANLinkedListImpl<>();
    }

    @Override
    public void push(T value) {
        helper.addFirst(value);
    }

    @Override
    public T pop() {
        T data = helper.getFirst();
        helper.removeFirst();
        return data;
    }

    @Override
    public T peek() {
        return helper.getFirst();
    }

    public boolean isEmpty() {
        return helper.getSize() <= 0;
    }

    public int getSize() {
        return helper.getSize();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < helper.getSize(); i++) {
            s.append("Node ").append(i).append("| Value: ").append(helper.get(i));
        }
        return s.toString();
    }
}