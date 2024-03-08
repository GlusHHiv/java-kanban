package model;


import java.util.HashMap;
import java.util.LinkedList;

public class LinkedMapList<T> extends LinkedList {

    class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> next, Node<E> prev, E data) {
            this.next = next;
            this.prev = prev;
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private HashMap<Integer, Node<T>> innternalHashMap = new HashMap<>();
    private int size = 0;

    public void linkLast(T data, int id) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(null, last, data);
        innternalHashMap.put(id, newNode);
        tail = newNode;
        if(last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public T get(int id) {
        return innternalHashMap.get(id).data;
    }

    public boolean removeElement(int id) {
        if (!innternalHashMap.containsKey(id)) {
            return false;
        }

        unlink(innternalHashMap.get(id));
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
        return innternalHashMap.containsKey(id);
    }

    @Override
    public String toString() {
        String returnmentString = "";
        for(Node<T> nodeData : innternalHashMap.values()) {
            returnmentString += ("{" + nodeData.data.toString()) + "\n";
        }
        return returnmentString + "}";
    }

}
