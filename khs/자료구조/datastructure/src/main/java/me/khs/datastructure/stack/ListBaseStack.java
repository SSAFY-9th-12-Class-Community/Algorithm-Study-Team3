package me.khs.datastructure.stack;

public class ListBaseStack<E> implements MyStack<E>{

    private Node<E> top;
    private Node<E> bottom;

    @Override
    public boolean push(E e) {
        if(this.top == null) {
            this.top = new Node<>(e);
            this.bottom = this.top;
        } else {
            Node<E> node = new Node<>(e);
            node.prev = this.top;
            this.top = node;
        }

        return true;
    }

    @Override
    public E pop() {
        if(this.top == null) throw new RuntimeException();
        E result = this.top.value;
        this.top = this.top.prev;

        if(this.top == null) {
            this.bottom = null;
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.top == null;
    }

    @Override
    public E top() {
        if(this.top == null) throw new RuntimeException();
        return this.top.value;
    }

    private static class Node<E> {
        private E value;
        private Node<E> prev;

        private Node(E e) {
            this.value = e;
            this.prev = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Node cur = this.top;
        while (cur!=null) {
            if(sb.length() != 0) sb.append(",");
            sb.append(cur.value);
            cur = cur.prev;
        }

        return sb.toString();
    }
}
