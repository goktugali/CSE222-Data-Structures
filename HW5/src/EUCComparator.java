import java.util.Comparator;

public class EUCComparator implements Comparator<GtuArrayList<Integer>>{
    @Override
    public int compare(GtuArrayList<Integer> v1, GtuArrayList<Integer> v2)
    {
        if(L2Norm(v1) == L2Norm(v2))
            return 0;
        if(L2Norm(v1) > L2Norm(v2))
            return 1;
        return -1;
    }
    private double L2Norm(GtuArrayList<Integer> v)
    {
        double sum=0.0;
        for (int i = 0; i < v.size() ; i++)
            sum += v.get(i)*v.get(i);
        return Math.sqrt(sum);
    }
}