import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class Word_Map implements Map, Iterable<Word_Map.Entry>
{
    private final static int INITCAP=101;  //initial capacity
    private final static float LOADFACT = 0.75f;
    private Entry [] table;
    private int keyNum=0; // number of keys
    private int deleteNum=0; // number of deleted entries
    private final Entry DELETED = new Entry(null,null);

    /* For word processing, control(helper) data fields */
    private String currentFile; // current file working on
    private int currentPos; // current position(occurence) added
    private Entry lastInserted; // last inserted word
    private Entry firstInserted; // first inserted word
    private boolean rehashed; // flag for rehashing

    private static class File_Map implements Map
    {
        /*
        For this hashmap, you will use arraylists which will provide easy but costly implementation.
        Your should provide and explain the complexities for each method in your report.
        * */
        private ArrayList<String> fnames = new ArrayList<>();
        private ArrayList<List<Integer>> occurences = new ArrayList<>();

        @Override
        public int size()
        {
            return fnames.size();
        }

        @Override
        public boolean isEmpty()
        {
            return fnames.isEmpty();
        }

        @Override
        public boolean containsKey(Object key)
        {
            return fnames.contains(key);
        }

        @Override
        public boolean containsValue(Object value)
        {
            return occurences.contains(value);
        }

        @Override
        public Object get(Object key)
        {
            if(this.containsKey(key))
                return occurences.get(fnames.indexOf(key));
            return null;
        }

        public String getFilename(int index)
        {
            return fnames.get(index);
        }

        @SuppressWarnings("unchecked")
        @Override
        /*Each put operation will extend the occurance list*/
        public Object put(Object key, Object value)
        {
            if(!this.containsKey(key)){ // if it is a new key
                fnames.add((String)key);
                occurences.add((List<Integer>)value);
                return value;
            }
            else{ // if it is not a new key
                List<Integer> ll = (List<Integer>)value;
                int pos = ll.get(0); // get the occurence(position) value
                if(!occurences.get(fnames.indexOf(key)).contains(pos)) // if it is new position
                    occurences.get(fnames.indexOf(key)).add(pos); // add to occurence list (position list)
            }
            return null;
        }

        @Override
        public void clear()
        {
            fnames.clear();
            occurences.clear();
        }

        @Override
        public Set keySet()
        {
            return new HashSet<>(fnames);
        }

        @Override
        public Collection values()
        {
            return new ArrayList<>(occurences);
        }
        @Override
        public String toString()
        {
            StringBuilder str = new StringBuilder();
            str.append("  Filename :           Positions:\n");
            for (int i=0;i<fnames.size();++i)
                str.append("  "+fnames.get(i) + "      " + occurences.get(i) + "\n");
            return str.toString();
        }

        @Override
        public Object remove(Object key)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map m) {
            throw new UnsupportedOperationException();
        }
        @Override
        public Set<Entry> entrySet()
        {
            throw new UnsupportedOperationException();
        }
    }

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
        Entry [] temp = table;
        table = new Entry[newSize+GROWFACTOR];
        keyNum=0;
        deleteNum=0;
        for(Entry item : temp)
            if(item!=null && item!=DELETED)
                this.put(item.getKey(),item.getValue());
        rehashed=false;
            
    }
    /**
    * Returns the index which has key value which desribed in key param.
    * @param key Key whose index will be returned.
    * @return index of key in de hash table.
    */ // PRIVATE HELPER METHOD
    private int findEntry(Object key)
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

    /*   
    * 
    * PUBLIC INTERFACE 
    *
    */
    public Word_Map() 
    {
        this.table = new Entry[INITCAP];
    }
    /**
    * Sets the current filename.
    */
    public void setFile(String file)
    {
        currentFile=file;
    }
    /**
    * Add word into the word map by giving position in file.
    * User can add word easily by giving word name and position in the file.
    * This method uses put() method internally.
    * @param word Word which will be added into the map.
    * @param pos Position(occrence) value in the file.
    */
    public void add(String word,int pos)
    {   
        currentPos=pos; // update the currentPos
        if(!this.containsKey(word))
            this.put(word,new File_Map()); // if it is a new word, create new File_Map
        else
            this.put(word,null); // if it s not a new word, just add to occurency list.  
    }
    @Override
    public Iterator<Entry> iterator() 
    {
        return new WMIterator();
    }

    @Override
    public int size() 
    {
        return keyNum;
    }

    @Override
    public boolean isEmpty() 
    {
        return keyNum==0;
    }

    @Override
    /*Use linked structure instead of table index
    to perform search operation effectively
     * */
    public boolean containsKey(Object key) 
    {
        for(Entry item : this)
            if(item.getKey().equals(key))
                return true;
        return false;
    }

    @Override
    /*Use linked structure instead of table index
    to perform search operation effectively
     * */
    public boolean containsValue(Object value) 
    {
        for(Entry item : this)
            if(item.equals(value))
                return true;
        return false;
    }

    @Override
    public Object get(Object key) 
    {
        int index = findEntry(key);
        if(table[index]!=null)
        	return table[index].getValue();
        return null;
    }

    @Override
    /*
    Use linear probing in case of collision
    * */
    public Object put(Object key, Object value) 
    {
    	int index = findEntry(key);
    	if(table[index]==null){ // If key is a new word

    		table[index] = new Entry((String)key,(File_Map)value);
    		keyNum++;
    		
            if(keyNum>1)
                lastInserted.nextEntry=table[index];
            else if(keyNum==1)
                firstInserted=table[index];    
            lastInserted=table[index];
    	}
    	if(!rehashed){
                File_Map fmp = table[index].getValue();
                List<Integer> occ = new ArrayList<>();
                occ.add(currentPos); // occurences list
                fmp.put(currentFile,occ); // put into File_Map
        }
        double load = (double)((keyNum + deleteNum) / table.length);
        if(load>LOADFACT){
                rehashed = true;
                this.reHash();      
        }
        if(table[index]!=null)
            return table[index].getKey();
        return null;
    }

    @Override
    public void clear() 
    {
        for (int i=0;i<table.length;++i)
            table[i]=null;
        keyNum=0;
        deleteNum=0;
        firstInserted=null;
        lastInserted=null;
        currentFile=null;
        currentPos=0;     
    }

    @Override
    /*Use linked structure instead of table index
    for efficiency
     * */
    public Set keySet() 
    {
        Set<String> set = new HashSet<>();
        for(Entry item : this)
            set.add(item.getKey());
        return set;
    }

    @Override
    /*Use linked structure instead of table index
    for efficiency
     * */
    public Collection values() 
    {
        Iterator<Entry> iter = this.iterator();
        ArrayList<String> list = new ArrayList<>();
        while(iter.hasNext())
            list.add(iter.next().getKey());  
        return list;
    }

    /**
    * Finds the number of different files that contains specific word.Used in TFIDF calculation.
    * @param word word that which will be used in the file count.
    * @return number of files contains specific word.
    */
    public int numOfFilesContainsWord(String word)
    {
        File_Map fmp = (File_Map)this.get(word);
        if(fmp!=null)
            return fmp.size();
        return 0;
    }

    /**
    * Finds the number of different files working on.Used in TFIDF calculation.
    * @rreturn the number of different files working on.
    */
    public int numOfFilesWorkingOn()
    {
        GtuArrayList<String> files = new GtuArrayList<>();
        for(Entry item : this)
        {
            File_Map fmp = item.getValue();
            for (int i=0;i<fmp.size();++i)   
                if(!files.contains(fmp.getFilename(i))) 
                    files.add(fmp.getFilename(i));                                   
        }
        return files.size(); 
    }
    @SuppressWarnings("unchecked")
    public float TF(String word,String fileName)
    {
        File_Map fmp = (File_Map) this.get(word);
        List<Integer> occ = (List<Integer>)fmp.get(fileName);
        float x = (float) occ.size();
        float y = 0.0f;
        for(Entry item : this)
        {
            List<Integer> occ2 = (List<Integer>) item.getValue().get(fileName);
            if(occ2!=null)
            y += (float) occ2.size();
        }
        return x / y;
    }
    /**
    * Returns the bigrams in the word map.
    * @param word Word that bigrams founded with it.
    * @return bigrams in the word map with given parameter word. 
    */
    @SuppressWarnings("unchecked")
    public List<String> bigrams(String word)
    {
        /*
        * Main word : several years. Several years is bigram, main word is Several.
        */
        Iterator<Entry> iter = this.iterator();
        File_Map mainWord = (File_Map)this.get(word); // mainword's file map
        ArrayList<String> bigramList = new ArrayList<>();
        if(mainWord==null)
            return bigramList;

        StringBuilder str;
        while(iter.hasNext()) // iterate all over the word map for finding bigrams
        {                     //try all words
            Entry ent = iter.next();
            File_Map search = ent.getValue();
            if(!search.equals(mainWord)){
                for (int i=0;i<mainWord.size();++i) { // iterate all over the main word's filemap
                    
                    String file = mainWord.getFilename(i);
                    List<Integer> occ = (List<Integer>)mainWord.get(file); // main word
                    List<Integer> occ2 = (List<Integer>)search.get(file); // target word
                    if(occ!=null && occ2!=null){
                        //searching bigram
                        for (int pos : occ) {
                            for(int pos2 : occ2) {
                                if(pos+1==pos2){ // if bigram found, add it to list
                                    str = new StringBuilder();
                                    str.append(word + " ");
                                    str.append(ent.getKey());
                                    if(!bigramList.contains(str.toString()))
                                        bigramList.add(str.toString());
                                }
                            }
                        }
                    }       
                }
            }
        }
        return bigramList;   
    }

    @Override
    public String toString()
    {   
        StringBuilder str = new StringBuilder();
        for(Entry item : table)
            str.append(item + "\n");
        return str.toString();               
    } 

    /**
    * Inner static class that describes (key,value) pair.
    */
    public static class Entry {
        private String key;
        private File_Map value;
        private Entry nextEntry;

        public Entry(String key, File_Map value)
        {
            this.key = key;
            this.value = value;
        } 

        public String getKey(){
            return key;
        }
        public File_Map getValue(){
            return value;
        }
        @Override
        public String toString()
        {
            return new String("Word : " + key + "\n" + value);
        }
    }

    private class WMIterator implements Iterator<Entry>{

        private Entry currentItem; // next Entry pointed.
        private int iterated; // current iteration counter.

        public WMIterator()
        {
            currentItem=firstInserted;
            iterated=0;
        }

        @Override
        public Entry next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            Entry returned = currentItem;
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

    @Override
    /*You do not need to implement remove function
    * */
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map m) {
        throw new UnsupportedOperationException();
    }

    @Override
    /*You do not need to implement entrySet function
     * */
    public Set<Entry> entrySet() {
        return null;
    }
}
