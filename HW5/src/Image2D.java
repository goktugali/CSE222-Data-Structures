import java.awt.image.BufferedImage;

/**
 * Image2D class that represents 2D image as an 2D array of ArrayList which stores
 * R-G-B color  values.
 */

public class Image2D{

    private GtuArrayList<Integer>[][] convertedImage;
    private String filename;
    private int height;
    private int width;

    @SuppressWarnings("unchecked")
    public Image2D(BufferedImage image,String file)
    {
        height = image.getHeight();
        width = image.getWidth();
        convertedImage = new GtuArrayList[height][width];
        filename=file;
        int red,green,blue;
        int pixel;
        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width; j++) {
                pixel = image.getRGB(j,i);

                red = (pixel >> 16) & 0xff;
                green = (pixel >> 8) & 0xff;
                blue = (pixel) & 0xff;
                convertedImage[i][j] = new GtuArrayList<>();
                convertedImage[i][j].add(red);
                convertedImage[i][j].add(green);
                convertedImage[i][j].add(blue);
            }
        }
    }

    public GtuArrayList<Integer> getPixel(int i, int j)
    {
        return convertedImage[i][j];
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public String getFileName(){return filename;}
    public int getSize(){return height*width;}

}