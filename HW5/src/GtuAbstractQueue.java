public abstract class GtuAbstractQueue<E> implements GtuQueue<E>{


    @Override
    public boolean add(E elem)
    {
        return this.offer(elem);
    }
    @Override
    public E element()
    {
        return this.peek();
    }

}