package model;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedMapList<T> extends LinkedList {

    private class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> next,E data, Node<E> prev) {
            this.next = next;
            this.prev = prev;
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private HashMap<Integer, Node<T>> internalHashMap = new HashMap<>();
    private int size = 0;

    public void linkLast(T data, int id) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(null, data, last);
        internalHashMap.put(id, newNode);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public T get(int id) {
        return internalHashMap.get(id).data;
    }

    public boolean removeElement(int id) {
        if (!internalHashMap.containsKey(id)) {
            return false;
        }

        unlink(internalHashMap.get(id));
        return true;

    }

    private T unlink(Node<T> o) {
        final T element = o.data;
        final Node<T> next = o.next;
        final Node<T> prev = o.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            o.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            o.next = null;
        }

        o.data = null;
        size--;
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(int id) {
        return internalHashMap.containsKey(id);
    }

    @Override
    public String toString() {
        String returnmentString = "";
        for (Node<T> nodeData : internalHashMap.values()) {
            returnmentString += ("{" + nodeData.data.toString()) + "\n";
        }
        return returnmentString + "}";
    }

}
