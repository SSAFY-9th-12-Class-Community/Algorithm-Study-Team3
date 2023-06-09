package me.khs.datastructure.stack;

public interface MyStack<E> {
    boolean push(E e);
    E pop();
    boolean isEmpty();
    E top();
}
