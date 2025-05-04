import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class AgentStack<E> {
    private static class Node<E> {
        E data;
        Node<E> next;
        Node(E d){
            data = d;
        }
    }
    private Node<E> top;
    private int size;
    private int maxDepth;

    public void push(E item) {
        Node<E> n = new Node<>(item);
        n.next = top;
        top = n;
        size++;
        if (size > maxDepth) maxDepth = size;
    }

    public E pop() {
        if (top == null)
            return null;
        E data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public E peek() {
        return top != null ? top.data : null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    //En dipten en üste doğru liste olarak döner
    public List<E> toList() {
        List<E> list = new ArrayList<>();
        Node<E> cur = top;
        while (cur != null) {
            list.add(cur.data);
            cur = cur.next;
        }
        Collections.reverse(list);
        return list;
    }

    // Yığının o ana kadar ulaştığı maksimum derinlik
    public int getMaxDepth() {
        return maxDepth;
    }
}
