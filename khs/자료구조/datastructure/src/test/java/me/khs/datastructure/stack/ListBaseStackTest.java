package me.khs.datastructure.stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("링크드 리스트 기반 스택 구현체 테스트")
class ListBaseStackTest {

    @Test
    @DisplayName("노드 추가 테스트")
    void pushTest() {
        // Arrange
        MyStack<Integer> stack = new ListBaseStack<>();

        // Act
        stack.push(1);
        stack.push(3);
        stack.push(5);
        stack.push(7);
        stack.push(9);

        // Assert
        assertEquals("9,7,5,3,1", stack.toString());
    }

    @Test
    @DisplayName("노드 삭제 테스트")
    void popTest() {
        // Arrange
        MyStack<Integer> stack = new ListBaseStack<>();

        // Act
        stack.push(1);
        stack.push(3);
        stack.push(5);

        // Assert
        assertAll(()-> assertEquals("5,3,1", stack.toString()),
          ()->{
            stack.pop();
            assertEquals("3,1", stack.toString());
        },()->{
            stack.pop();
            assertEquals("1", stack.toString());
        }, ()->{
            stack.pop();
            assertTrue(stack.isEmpty());
        });
    }

    @Test
    @DisplayName("노드 삭제 예외 테스트")
    void emptyStackPopTest() {
        // Arrange, Act
        MyStack<Integer> stack = new ListBaseStack<>();

        // Assert
        assertThrows(RuntimeException.class, stack::pop);
    }

    @Test
    @DisplayName("Not empty 테스트")
    void notEmptyTest() {
        // Arrange
        MyStack<Integer> stack = new ListBaseStack<>();

        // Act
        stack.push(2);

        // Assert
        assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("top 테스트")
    void stackTopTest() {
        // Arrange
        MyStack<Integer> stack = new ListBaseStack<>();

        // Act
        stack.push(1);
        stack.push(3);
        stack.push(5);

        // Assert
        assertEquals(5, stack.top());
    }

    @Test
    @DisplayName("top exception 테스트")
    void emptyStackTopExceptionTest() {
        // Arrange, Act
        MyStack<Integer> stack = new ListBaseStack<>();

        // Assert
        assertThrows(RuntimeException.class, stack::top);
    }
}