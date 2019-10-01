public class GtuHashSet<E> {

    private HashMap<E,Object> map; // HashMap used for delegation and makes easier implementation

    public GtuHashSet()
    {
        map = new HashMap<>();
    }

    public boolean isEmpty()
    {
        return this.size() == 0;
    }
    public int size()
    {
        return map.size();
    }

    public boolean add(E elem)
    {
        return map.put(elem,elem);
    }

    public boolean contains(E elem)
    {
        return map.containsKey(elem);
    }

    public void clear()
    {
        map.clear();
    }

    @Override
    public String toString()
    {
        return map.keyList().toString();
    }
}