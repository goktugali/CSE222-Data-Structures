import java.util.Comparator;

public class LEXComparator implements Comparator<GtuArrayList<Integer>>{

    @Override
    public int compare(GtuArrayList<Integer> v1,GtuArrayList<Integer> v2)
    {
        int eqCtr=0;
        if(v1.get(0) > v2.get(0))
            return 1;
        else if(v1.get(0).equals(v2.get(0)))
            eqCtr++;

        if(v1.get(1) > v2.get(1))
            return 1;
        else if(v1.get(1).equals(v2.get(1)))
            eqCtr++;

        if(v1.get(2) > v2.get(2))
            return 1;
        else if(v1.get(2).equals(v2.get(2)))
            eqCtr++;

        return (eqCtr==3) ? 0 : -1;
    }
}