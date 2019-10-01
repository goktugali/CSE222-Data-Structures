public abstract class AbstractGraph implements Graph{

    protected int numVertex;
    protected int numRelation;
    protected boolean directed;

    public AbstractGraph()
    {}

    public AbstractGraph(int numVertex, boolean directed)
    {
        this.numVertex = numVertex;
        this.directed = directed;
        if(!directed)
            numRelation=numVertex;
    }

    public int getNumVertex()
    {
        return numVertex;
    }

    public int getNumRelation()
    {
        return numRelation;
    }

    public boolean isDirected()
    {
        return directed;
    }
}