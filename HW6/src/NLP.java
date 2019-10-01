import java.util.List;
import java.util.*;
import java.io.File;

public class NLP
{
    public Word_Map wmap;

    public NLP(Word_Map wmap){
        this.wmap = wmap;
    }

    public Word_Map getWmap()
    {
        return wmap;
    }

    /*You should not use File_Map class in this file since only word hash map is aware of it.
    In fact, you can define the File_Map class as a nested class in Word_Map,
     but for easy evaluation we defined it separately.
     If you need to access the File_Map instances, write wrapper methods in Word_Map class.
    * */

    /*Reads the dataset from the given dir and adds words into the word map */
    public void readDataset(String dir)
    {
        Scanner sc = null;
        wmap.setFile(dir); // set working file
        try {
            sc = new Scanner(new File(dir));
        } catch (Exception e) {
            e.printStackTrace();  
        }
        int i=0;
        while (sc.hasNextLine()) 
        {
            Scanner s2 = new Scanner(sc.nextLine());
            while (s2.hasNext()) 
            {
                String s = s2.next();
                s = s.trim().replaceAll("\\p{Punct}", "");
                wmap.add(s,i); // add word into wordmap
                ++i;
            }
        }
    }
    /*Print the WordMap by using its iterator*/
    public void printWordMap()
    {   
        for (Word_Map.Entry x : wmap)  // for-each by Iterable interface
            System.out.println(x);
        
        //second way..
        /*Iterator iter = wmap.iterator();
        while(iter.hasNext())
            System.out.println(iter.next());
        */
    }

    /*Finds all the bigrams starting with the given word*/
    public List<String> bigrams(String word){

        return wmap.bigrams(word);
    }

    /*Calculates the tfIDF value of the given word for the given file */
    public float tfIDF(String word, String fileName)
    {   
        return this.TF(word,fileName) * this.IDF(word);
    }

    private float IDF(String word)
    {
        return (float)Math.log(((float)wmap.numOfFilesWorkingOn()) / ((float)wmap.numOfFilesContainsWord(word)));
    }

    private float TF(String word, String fileName)
    {
        return wmap.TF(word,fileName);
    }
}
