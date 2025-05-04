public class CustomQueue<E> {
    private static class Node<E> {
        E data;
        Node<E> next;
        Node(E d){
            data = d;
        }
    }
    private Node<E> head, tail;
    private int size;

    //kuyruk oluştur
    public void enqueue(E item) {
        Node<E> n = new Node<>(item);
        if (tail != null) tail.next = n;
        tail = n;
        if (head == null) head = tail;
        size++;
    }

    //kuyruk sil
    public E dequeue() {
        if (head == null) return null;
        E data = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return data;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
