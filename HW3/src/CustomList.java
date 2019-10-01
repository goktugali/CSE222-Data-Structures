/**
 * CustomList class.
 * @author Goktug Akin,161044018
 * @version HW3
 * @since 2019
 */
public class CustomList<E>{

    private Node<E> head;
    private int size=0;

    private static class Node<E>{

        private E data;
        private Node<E> next=null;

        private Node(E e)
        {
            data = e;
        }
    }
    public CustomList()
    {}
    public E look()
    {
        return head.data;
    }

    public E removeHead()
    {
        E data=head.data;
        head=head.next;
        --size;
        return data;
    }
    public void addHead(E e)
    {
        Node<E> newNode = new Node<E>(e);
        newNode.next = head;
        head = newNode;
        ++size;
    }

    public int size()
    {
        return size;
    }

    @Override
    public String toString()
    {
        return String.format("# CustomList | Size : " + this.size());
    }

}
