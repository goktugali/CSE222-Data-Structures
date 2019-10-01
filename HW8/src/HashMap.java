import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMap<K,V> implements Iterable<HashMap.Entry<K,V>>
{
    private final static int INITCAP=11;  //initial capacity
    private final static float LOADFACT = 0.75f;
    private Entry<K,V> [] table;
    private int keyNum=0; // number of keys
    private int deleteNum=0; // number of deleted entries
    private final Entry DELETED = new Entry<K,V>(null,null);

    private Entry<K,V> lastInserted; // last inserted
    private Entry<K,V> firstInserted; // first inserted

    /**
     * Returns the next prime number bigger than given number param.
     * @param number Value for finding the prime number after it.
     * @return Biggest number prime after param number.
     */ // PRIVATE HELPER METHOD
    private int nextPrime(int number)
    {
        int primeNumber = number;
        class prime{ // anonymous inner class for method isPrime
            private boolean isPrime(int num)
            {
                for(int i=2;i<=num/2;++i)
                    if(num%i==0)
                        return false;
                return true;
            }
        }
        prime primeFounder = new prime();
        while(!primeFounder.isPrime(primeNumber))
            primeNumber++;

        return primeNumber;
    }
    /**
     * Reallocates the has table.
     */ // PRIVATE HELPER METHOD
    private void reHash()
    {
        int GROWFACTOR = 1;
        int newSize = nextPrime(table.length + GROWFACTOR);
        Entry<K,V> [] temp = table;
        table = new Entry[newSize+GROWFACTOR];
        keyNum=0;
        deleteNum=0;
        for (int i=0;i<temp.length;++i)
            if(temp[i]!=null && temp[i]!=DELETED)
                this.put(temp[i].getKey(),temp[i].getValue());

    }
    /**
     * Returns the index which has key value which desribed in key param.
     * @param key Key whose index will be returned.
     * @return index of key in de hash table.
     */ // PRIVATE HELPER METHOD
    private int findEntry(K key)
    {
        int index = key.hashCode() % table.length;
        if(index<0)
            index += table.length;

        while(table[index]!=null && !table[index].key.equals(key))
        {
            index++;
            if(index>=table.length)
                index=0;
        }
        return index;
    }

    public HashMap()
    {
        this.table = new Entry[INITCAP];
    }

    @Override
    public Iterator<Entry<K,V>> iterator()
    {
        return new WMIterator();
    }

    public GtuArrayList<K> keyList()
    {
        GtuArrayList<K> list = new GtuArrayList<>();
        for(Entry<K,V> e : this)
            list.add(e.getKey());
        return list;
    }

    public int size()
    {
        return keyNum;
    }

    public boolean isEmpty()
    {
        return keyNum==0;
    }

    public boolean containsKey(K key)
    {
        return table[findEntry(key)]!=null;
    }

    public boolean containsValue(V value)
    {
        for(Entry<K,V> ent : this)
            if(ent.getValue().equals(value))
                return true;
        return false;
    }

    public V get(K key)
    {
        int index = findEntry(key);
        if(table[index]!=null)
            return table[index].getValue();
        return null;
    }

    public boolean put(K key, V value)
    {
        int index = findEntry(key);
        if(table[index]==null){

            table[index] = new Entry<>(key,value);
            keyNum++;

            if(keyNum>1)
                lastInserted.nextEntry=table[index];
            else if(keyNum==1)
                firstInserted=table[index];
            lastInserted=table[index];
            double load = (double)((keyNum + deleteNum) / table.length);

            if(load>LOADFACT)
                this.reHash();
            return true;
        }
        return false;
    }

    public void clear()
    {
        for (int i=0;i<table.length;++i)
            table[i]=null;
        keyNum=0;
        deleteNum=0;
        firstInserted=null;
        lastInserted=null;
    }

    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for (int i=0;i<table.length;++i)
            str.append(table[i] + "\n");
        return str.toString();
    }

    /**
     * Inner static class that describes (key,value) pair.
     */
    public static class Entry<K,V> {
        private K key;
        private V value;
        private Entry<K,V> nextEntry;

        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public K getKey(){
            return key;
        }
        public V getValue(){
            return value;
        }

        @Override
        public String toString()
        {
            return new String(key + "\n" + value);
        }
    }

    private class WMIterator implements Iterator<Entry<K,V>>{

        private Entry<K,V> currentItem; // next Entry pointed.
        private int iterated; // current iteration counter.

        public WMIterator()
        {
            currentItem=firstInserted;
            iterated=0;
        }

        @Override
        public Entry<K,V> next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            Entry<K,V> returned = currentItem;
            currentItem=currentItem.nextEntry;
            ++iterated;
            return returned;

        }
        @Override
        public boolean hasNext()
        {
            return iterated<keyNum;
        }
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
