import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;
public class ComponentFinder{

    private int [][] img; // source array that represents binary digital image
    private int rowsize=0; // column size of image
    private int colsize=0; // row siize of image
    private String file; // file that contains binary digital image
    /**
     * Constructor that builds binary image by input txt file.
     * @param filename file name that contains binary digital image.
     */
    public ComponentFinder(String filename)
    {
        try {
            File file = new File(filename);
            String line;
            String[] res=null;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                res = line.split("\\s+");
                ++rowsize;
            }
            colsize=res.length;
            img = new int [rowsize][colsize];

            scanner = new Scanner(file);
            int k=0;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                res = line.split("\\s+");
                for(int x=0;x<colsize;++x)
                    img[k][x]=Integer.parseInt(res[x]);
                ++k;
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        file=filename;

    }
    /**
     * Prints the binary image and number of components to the screen.
     */
    public void output()
    {
        printImg();
        System.out.printf("Number of components in binary image : %d\n",getNumOfComponents());
    }
    /**
     * Prints the binary digital image.
     */
    public void printImg()
    {
        System.out.printf("Input image(%s) [%d]x[%d] :\n",file,rowsize,colsize);
        for (int p=0;p<rowsize;++p)
        {
            for (int o=0;o<colsize;++o)
                System.out.printf("%d ",img[p][o]);
            System.out.printf("\n");
        }
    }
    /**
     * Calculates the number of components in binary digital image.
     * @return Number of components in binary digital image.
     */
    public int getNumOfComponents()
    {
        boolean [][] checked = new boolean[rowsize][colsize]; // automaticall initalized as false.
        CustomStack<Pixel> stack = new CustomStack<Pixel>(); // custom stack for stroing the Pixels.

        int components = 0; // number of components counter
        int numOfIter= 0; // iteration counter // just for checking
        int cc;// current column
        int cr;// current row

        // Traversing 2D integer matrix
        for (int i=0;i<rowsize;++i) {
            for (int j=0;j<colsize;++j) {
                if(isValidPixel(img,i,j,checked)){// validty check
                    stack.push(new Pixel(j,i));
                    cc=j;
                    cr=i;
                    checked[cr][cc]=true; // mark as checked
                    while(!stack.empty())// starting search, simulation of recursion
                    {
                        if(isValidPixel(img,cr,cc+1,checked)){// check next right pixel
                            cc++;
                            checked[cr][cc]=true;// mark as checked
                            stack.push(new Pixel(cc,cr));
                        }
                        else if(isValidPixel(img,cr,cc-1,checked)){// check next left pixel
                            cc--;
                            checked[cr][cc]=true;
                            stack.push(new Pixel(cc,cr));
                        }
                        else if(isValidPixel(img,cr-1,cc,checked)){// check upper pixel
                            cr--;
                            checked[cr][cc]=true;
                            stack.push(new Pixel(cc,cr));
                        }
                        else if(isValidPixel(img,cr+1,cc,checked)){// check down pixel
                            cr++;
                            checked[cr][cc]=true;
                            stack.push(new Pixel(cc,cr));
                        }
                        else{// anything to valid, 'return' to back pixels
                            stack.pop();
                            if(!stack.empty()){
                                cc=stack.peek().getX();// theese peek invokes, can also be written in the top of the loop.
                                cr=stack.peek().getY();// but in this time, every execution of loop, we call a method.
                            }
                        }
                        ++numOfIter;
                    }// end of searching components // increment component counter
                    ++components;
                }
            }
        }// end of traversing whole image array.

        return components;
    }
    /**
     * Private helper method that checks given location's validty.
     * @param img source binary image array that will be checked.
     * @param row current row location that will be checked.
     * @param column current column location that will be checked.
     * @param checked boolean array that represents given location is checked or unchecked.
     * @return true if given location is valid.
     */
    private boolean isValidPixel(int img[][],int row,int column,boolean checked[][])
    {
        return ((row >= 0) && (row < img.length) && // index check for out of bounds
                (column >= 0) && (column< img[0].length) &&
                img[row][column]==1 && !checked[row][column]); // checks the location is white component and not visited yet.
    }
}