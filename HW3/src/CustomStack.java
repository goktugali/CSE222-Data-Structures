public class CustomStack<E>{

    private CustomList<E> list;

    public CustomStack()
    {
        list = new CustomList<E>();
    }

    public void push(E e)
    {
        list.addHead(e);
    }
    public E pop()
    {
        return list.removeHead();
    }

    public E peek()
    {
        return list.look();
    }

    public boolean empty()
    {
        return list.size()==0;
    }
}