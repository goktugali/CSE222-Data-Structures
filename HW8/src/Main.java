import java.io.*;

public class Main {

    private static ListGraph lg;

    public static void main(String[] args) throws IOException {

        FileInputStream file = new FileInputStream("input.txt");
        lg = new ListGraph(file);
        System.out.println("Number of people who are considered \nas popular by everyone :");
        System.out.println(lg.getNumOfOrderedRelations());
    }
}
