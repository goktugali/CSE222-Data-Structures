import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main  {

    public static void main(String[] args) {

        try {
            BufferedImage image = ImageIO.read(new File(args[0]));
            MainApp app = new MainApp(image,args[0]);
            app.start();
        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }
    }
}
