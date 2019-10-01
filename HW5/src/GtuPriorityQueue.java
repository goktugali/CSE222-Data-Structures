import java.util.Comparator;

public class GtuPriorityQueue<E> extends GtuAbstractQueue<E> implements GtuQueue<E> {

    private GtuArrayList<E> array;
    private Comparator<E> comparator = null;
    private int compare(E e1, E e2)throws ClassCastException
    {
        if(comparator!=null)
            return comparator.compare(e1,e2);
        else
            return ((Comparable<E>) e1).compareTo(e2); // this may be cause class cast exception
    }
    private void swap(int val1,int val2)
    {
        E temp = array.get(val1);
        array.set(val1,array.get(val2));
        array.set(val2,temp);
    }
    public GtuPriorityQueue(int initSize)
    {
        array = new GtuArrayList<>(initSize);
    }
    public GtuPriorityQueue()
    {
        array = new GtuArrayList<>();
    }
    public GtuPriorityQueue(Comparator<E> comp)
    {
        array = new GtuArrayList<>();
        comparator=comp;
    }
    public GtuPriorityQueue(Comparator<E> comp, int initSize)
    {
        this(initSize);
        comparator=comp;
    }
    @Override
    public boolean offer(E item)
    {
        array.add(item);
        int child = array.size()-1;
        int start = (child-1)/2;
        while(start>=0 && compare(array.get(start),array.get(child))<0) // start shifting to the true position
        {
            swap(start,child);
            child=start;
            start=(child-1)/2;
        }
        return true;
    }
    @Override
    public E poll()
    {
        if(isEmpty())
            return null;
        E result = array.get(0);
        if(array.size()==1) {
            array.remove(result);
            return result;
        }

        array.set(0,array.remove(array.size()-1));
        int start = 0;
        while(true) // start shifting to the true position
        {
            int leftCh = 2*start + 1;
            if(leftCh>=array.size())
                break;
            int rightCh = leftCh + 1;
            int minCh = leftCh;
            if(rightCh < array.size() && compare(array.get(leftCh),array.get(rightCh))<0)
                minCh = rightCh;
            if(compare(array.get(start), array.get(minCh)) < 0 ) {
                swap(start, minCh);
                start = minCh;
            }
            else
                break;
        }
        return result;
    }
    @Override
    public E peek()
    {
        return array.get(0);
    }
    @Override
    public boolean remove(E elem)
    {
        if(array.indexOf(elem)!=0)
            return false;
        array.remove(0);
        return true;
    }
    @Override
    public int size()
    {
        return array.size();
    }
    @Override
    public void clear()
    {
        array.clear();
    }
    @Override
    public boolean isEmpty()
    {
        return array.isEmpty();
    }
    @Override
    public boolean contains(E elem)
    {
        return array.contains(elem);
    }
    @Override
    public GtuIterator<E> iterator()
    {
        return array.iterator();
    }
    @Override
    public String toString()
    {
        return array.toString();
    }
}