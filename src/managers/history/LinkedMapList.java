package managers.history;


import java.util.HashMap;
import java.util.LinkedList;

public class LinkedMapList<T> extends LinkedList {

    private class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;
        public int id;

        public Node(Node<E> next,E data, Node<E> prev, int id) {
            this.next = next;
            this.prev = prev;
            this.data = data;
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private HashMap<Integer, Node<T>> internalHashMap = new HashMap<>();
    private int size = 0;

    public void linkLast(T data, int id) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(null, data, last, id);
        internalHashMap.put(id, newNode);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    public void linkFirst(T data, int id) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(first, data, null, id);
        internalHashMap.put(id, newNode);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    @Override
    public T get(int id) {
        return internalHashMap.get(id).data;
    }

    public T getFirst() {
        return internalHashMap.get(head.getId()).data;
    }

    public T getLast() {
        return internalHashMap.get(tail.getId()).data;
    }

    private T unlink(Node<T> o, int id) {
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

    public boolean removeFirstElement() {
        if (head == null) {
            return false;
        }
        internalHashMap.remove(head.getId());
        unlinkFirst(head);
        return true;
    }

    public boolean removeLastElement() {
        if (tail == null) {
            return false;
        }
        internalHashMap.remove(tail.getId());
        unlinkLast(tail);
        return true;
    }

    private T unlinkFirst(Node<T> o) {
        final T data = o.data;
        final Node<T> next = o.next;
        o.next = null;
        o.data = null;
        head = next;
        if (next == null) {
            tail = null;
        } else {
            next.prev = null;
        }
        size--;
        return data;
    }

    private T unlinkLast(Node<T> o) {
        final T data = o.data;
        final Node<T> last = o.prev;
        o.prev = null;
        o.data = null;
        tail = last;
        if (last == null) {
            head = null;
        } else {
            last.next = null;
        }
        size--;
        return data;
    }

    public boolean removeElement(int id) {
        if (!internalHashMap.containsKey(id)) {
            return false;
        }

        unlink(internalHashMap.get(id), id);
        return true;

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
        Node<T> current = head;
        while (current != null) {
            returnmentString += ("{" + current.data.toString()) + "\n";
            current = current.next;
        }
        return returnmentString + "}";
    }


}
