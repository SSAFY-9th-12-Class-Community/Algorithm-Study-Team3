package me.khs.datastructure.queue;

public interface MyQueue<E> {
    boolean enqueue(E e);
    boolean dequeue();
    boolean isEmpty();
    E front();
}
