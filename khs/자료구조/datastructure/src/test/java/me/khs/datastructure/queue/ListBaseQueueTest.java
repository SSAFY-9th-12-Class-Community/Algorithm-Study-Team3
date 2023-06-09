package me.khs.datastructure.queue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("링크드 리스트 기반 큐 구현체 테스트")
class ListBaseQueueTest {

    @Test
    @DisplayName("노드 추가 테스트")
    void enqueueTest() {
        // Arrange
        ListBaseQueue<Integer> queue = new ListBaseQueue<>();

        // Act
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(5);
        queue.enqueue(8);

        // Assert
        assertEquals("1,2,3,5,8", queue.toString());
    }

    @Test
    @DisplayName("노드 삭제 테스트")
    void dequeueTest() {
        // Arrange
        ListBaseQueue<Integer> queue = new ListBaseQueue<>();

        // Act
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(5);
        queue.enqueue(8);

        // Assert
        assertAll(()->{
            queue.dequeue();
            assertEquals("2,3,5,8", queue.toString());
        }, ()->{
            queue.dequeue();
            assertEquals("3,5,8", queue.toString());
        }, ()->{
            queue.dequeue();
            assertEquals("5,8", queue.toString());
        }, ()->{
            queue.dequeue();
            assertEquals("8", queue.toString());
        }, ()->{
            queue.dequeue();
            assertTrue(queue.isEmpty());
        });
    }

    @Test
    @DisplayName("Not empty 테스트")
    void notEmptyTest() {
        // Arrange
        ListBaseQueue<Integer> queue = new ListBaseQueue<>();

        // Act
        queue.enqueue(123);

        // Assert
        assertFalse(queue.isEmpty());
    }

    @Test
    @DisplayName("front 테스트")
    void frontTest() {
        // Arrange
        ListBaseQueue<String> queue = new ListBaseQueue<>();

        // Act
        queue.enqueue("front1");
        queue.enqueue("front2");
        queue.enqueue("front3");

        // Assert
        assertAll(()->{
            assertEquals("front1", queue.front());
        }, ()->{
            queue.dequeue();
            assertEquals("front2", queue.front());
        }, ()->{
            queue.dequeue();
            assertEquals("front3", queue.front());
        }, ()->{
            queue.dequeue();
            assertEquals(null, queue.front());
        });
    }
}