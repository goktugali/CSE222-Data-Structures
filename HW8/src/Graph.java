import java.util.Iterator;

public interface Graph{

    /**
     * Insert an edge into graph
     * @param edge edge which will be inserted into the graph.
     */
    void insert(Edge edge);

    /**
     * Checks whether given source and destination is edge or not.
     * @param source source of an edge. (starting point)
     * @param dest destination of an edge. (finish point)
     * @return true if given destination and source values are edge.
     */
    boolean isEdge(int source, int dest);

    /**
     * Returns the edge if exist.
     * @param source source of an edge. (starting point)
     * @param dest destination of an edge. (finish point)
     * @return Edge if it exist.
     */
    Edge getEdge(int source, int dest);

    /**
     * Returns an iterator over the edge.
     * @param source source value of an edge.
     * @return an iterator over the edge with given source value.
     */
    Iterator<Edge> edgeIterator(int source);
}