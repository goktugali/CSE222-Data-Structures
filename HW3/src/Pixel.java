/**
 * Pixel class that represents 2d matrix location.
 */
public class Pixel{

    private int x;
    private int y;

    public Pixel(int xval,int yval)
    {
        x=xval;
        y=yval;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
}