package nl.han.ica.datastructures.stack;

public class ReverseStackImpl<T> implements ReverseStack<T> {
    @Override
    public IHANStack<T> reverse(IHANStack<T> stack) {
        if (!stack.isEmpty()) {
            T temp = stack.peek();
            stack.pop();
            reverse(stack);
            insertAtBottom(stack, temp);
        }
        return stack;
    }

    private void insertAtBottom(IHANStack<T> stack, T item) {
        if (stack.isEmpty()) stack.push(item);
        else {
            T temp = stack.peek();
            stack.pop();
            insertAtBottom(stack, item);
            stack.push(temp);
        }
    }
}
