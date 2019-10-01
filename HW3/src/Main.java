public class Main {

    public static void main(String[] args) { // Driver Pogram

        try{
            ComponentFinder test = new ComponentFinder(args[0]);
            test.output();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }
}
