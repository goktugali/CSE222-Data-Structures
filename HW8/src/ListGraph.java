import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ListGraph extends AbstractGraph{

    private HashMap<Integer,GtuArrayList<Edge>> hMap;

    public ListGraph(int numVertex, boolean directed)
    {
        super(numVertex,directed); // calling AbstractGraph constructor.
        hMap = new HashMap<>();
    }

    /**
     * Constructor that constructs the graph reading the input txt file.
     * @param file file object that points the input txt file
     * @throws IOException If file is not found.
     */
    public ListGraph(FileInputStream file)throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(file));
        String s = in.readLine();
        String[] tmp = s.split(" ");
        numVertex = Integer.parseInt(tmp[0]);
        numRelation = Integer.parseInt(tmp[1]);
        hMap = new HashMap<>();
        while ((s = in.readLine()) != null)
        {
            if(!s.isEmpty()) {
                String[] tmp2 = s.split(" "); //Split space
                this.insert(new Edge(Integer.parseInt(tmp2[0]), Integer.parseInt(tmp2[1])));
            }
        }
    }
    /**
     * Insert an edge into graph
     * @param edge edge which will be inserted into the graph.
     */
    public void insert(Edge edge)
    {
        if(!hMap.containsKey(edge.getSource())) { // new element
            GtuArrayList<Edge> nList = new GtuArrayList<>();
            nList.add(edge);
            hMap.put(edge.getSource(),nList);
        }
        else { // update the current element
            GtuArrayList<Edge> list = hMap.get(edge.getSource());
            list.add(edge);
        }
        if(!hMap.containsKey(edge.getDest())){ // if destination is a new, add it to the graph.
            GtuArrayList<Edge> nList = new GtuArrayList<>();
            hMap.put(edge.getDest(),nList);
        }
    }
    /**
     * Checks whether given source and destination is edge or not.
     * @param source source of an edge. (starting point)
     * @param dest destination of an edge. (finish point)
     * @return true if given destination and source values are edge.
     */
    public boolean isEdge(int source, int dest)
    {
        return false;
    }
    /**
     * Returns the edge if exist.
     * @param source source of an edge. (starting point)
     * @param dest destination of an edge. (finish point)
     * @return Edge if it exist.
     */
    public Edge getEdge(int source, int dest)
    {
        return null;
    }
    /**
     * Returns an iterator over the edge.
     * @param source source value of an edge.
     * @return an iterator over the edge with given source value.
     */
    public Iterator<Edge> edgeIterator(int source)
    {
        return hMap.get(source).iterator();
    }

    /**
     * Returns true if given vertex is related (popular) by everyone.
     * @param source Vertex number which will be checked.
     * @return true if the given vertex is related (popular) by everyone.
     */
    public boolean isRelatedWithEveryone(int source)
    {
        GtuArrayList<Integer> list = hMap.keyList();
        int pop_ctr = 0;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i) != source)
                if (isTransitiveEdge(list.get(i), source))
                    ++pop_ctr;

        return pop_ctr==(list.size()-1);
    }

    /**
     * Returns number of vertex which are considered as popular by everyone.
     * @return number of vertex which are considered as popular by everyone.
     */
    public int getNumOfOrderedRelations()
    {
        GtuArrayList<Integer> list = hMap.keyList();
        int numOfRelations = 0;
        for (int i = 0; i < list.size(); i++)
            if(isRelatedWithEveryone(list.get(i)))
                ++numOfRelations;
        return numOfRelations;
    }

    /**
     * Returns true if given edge values are transitive edge (un-directly edge).
     * @param source source value of an edge.
     * @param dest destination value of an edge.
     * @return true if given edge values are transitive edge (un-directly edge).
     */
    public boolean isTransitiveEdge(int source, int dest)
    {
        GtuArrayList<Edge> list = hMap.get(source);
        if(list==null || list.isEmpty())
            return false;
        if(isEdge(source,dest))
            return true;

        // HashSet used for speed up, Adding O(1) and check contains in O(1)
        GtuHashSet<Integer> checked = new GtuHashSet<>(); // checked node list
        return traverse(list,checked,dest);
    }

    /**
     * Private helper method for search in the graph recursively.
     * @param list Edge list.
     * @param checked Current checked elements set. Avoids infinite recursion.
     * @param target Target value to be reached.
     * @return true if there is a path to target.
     */
    private boolean traverse(GtuArrayList<Edge> list,GtuHashSet<Integer> checked, int target)
    {
        if(list==null) // base case
            return false;

        for(Edge e : list) // traverse over the all edges
        {
            if(e.getDest()==target) // reached the target
                return true;
            else if(!checked.contains(e.getDest())) { // to avoid infinite recursion.
                checked.add(e.getDest()); // mark as checked
                if (traverse(hMap.get(e.getDest()),checked,target))
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return hMap.toString();
    }
}