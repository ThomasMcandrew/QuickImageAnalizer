package main.utils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Utils {

    private static VideoCapture capture;
    public static boolean captureReady(){
        return capture != null;
    }
    public static void initVideoCapture(File file){
        capture = new VideoCapture(file.getAbsolutePath());
    }
    public static BufferedImage nextImage(){
        BufferedImage image = null;
        Mat mat = new Mat();

        capture.read(mat);
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, mob);
        try {
            image = ImageIO.read(new ByteArrayInputStream(mob.toArray()));
        } catch (IOException e) {
            System.exit(0);
            e.printStackTrace();
        }
        return image;
    }

}
