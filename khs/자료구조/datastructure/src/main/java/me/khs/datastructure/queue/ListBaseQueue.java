package me.khs.datastructure.queue;

public class ListBaseQueue<E> implements MyQueue<E> {

    private Node<E> front;
    private Node<E> rear;

    public ListBaseQueue() {
        this.front = this.rear = null;
    }

    @Override
    public boolean enqueue(E o) {
        if(this.rear == null) {
            this.rear = new Node<>(o);
            this.front = this.rear;
        } else {
            this.rear.next = new Node<>(o);
            this.rear = this.rear.next;
        }
        return true;
    }

    @Override
    public boolean dequeue() {
        if(this.front == null) return false;
        else {
            this.front = this.front.next;
            if(this.front == null) this.rear = null;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.front == null;
    }

    @Override
    public E front() {
        return this.front == null ? null : this.front.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> cur = this.front;
        while (cur!=null) {
            if(sb.length() != 0) sb.append(",");
            sb.append(cur.data);
            cur = cur.next;
        }

        return sb.toString();
    }

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E e) {
            this.data = e;
            this.next = null;
        }
    }
}
