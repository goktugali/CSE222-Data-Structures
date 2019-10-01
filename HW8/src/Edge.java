public class Edge{

    private int source;
    private int dest;

    public Edge(int source, int dest)
    {
        this.dest = dest;
        this.source = source;
    }

    public int getDest() {
        return dest;
    }

    public int getSource() {
        return source;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("(" + source + ',' + dest + ")");
        return str.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        Edge edg;
        if(o instanceof Edge)
            edg = (Edge)o;
        else
            throw new ClassCastException();
        return (edg.getSource() == this.getSource() && edg.getDest() == this.getDest());
    }
}