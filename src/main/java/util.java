import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class util {
    public static byte[] BImageToByte(BufferedImage b) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(b, "jpg", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

    public static BufferedImage readImage(String filePath) throws IOException {
        File img = new File(filePath);
        return ImageIO.read(img);
    }
}
