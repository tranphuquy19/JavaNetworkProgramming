package tk.doraneko.test.robo;

import com.sun.imageio.plugins.common.ImageUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author tranphuquy19@gmail.com
 * @since 31/10/2019
 */
public class TakeScreenShot {
    public static void main(String[] args) throws AWTException, IOException {
//        long startTime = System.currentTimeMillis();
//        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ImageIO.write(image,"jpg", byteArrayOutputStream);
//        byte[] imagrArr = byteArrayOutputStream.toByteArray();
//        long time = System.currentTimeMillis() - startTime;
//        System.out.println("asd");
        Rectangle rect = new Rectangle( Toolkit.getDefaultToolkit().getScreenSize() );
        try {
            Robot robot = new Robot();
            int count = 1;
            long beforeTime = System.currentTimeMillis();
            while ( count < 15 ) {
                BufferedImage buffImage = robot.createScreenCapture( rect );
                ByteArrayOutputStream bios = new ByteArrayOutputStream();
                ImageIO.write(buffImage, "jpg", bios);
                byte[] imgArr = bios.toByteArray();
                System.out.println(imgArr.length);
                count++;
            }
            double time = System.currentTimeMillis() - beforeTime;
            System.out.println( "Seconds it took for 15 screen captures: " + time / 1000 );
        } catch ( Exception e ) { }

    }
}
