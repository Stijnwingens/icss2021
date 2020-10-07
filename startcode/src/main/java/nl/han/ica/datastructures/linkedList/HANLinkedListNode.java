package nl.han.ica.datastructures.linkedList;

class HANLinkedListNode<T> {
    T data;
    HANLinkedListNode<T> next;

    // Used only for headers
    public HANLinkedListNode() {
    }

    public HANLinkedListNode(T data, HANLinkedListNode<T> next) {
        this.data = data;
        this.next = next;
    }
}
