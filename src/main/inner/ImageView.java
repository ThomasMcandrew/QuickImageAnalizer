package main.inner;

import main.utils.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageView extends JPanel {

    public BufferedImage image;
    private static ImageView imageView;
    public static ImageView getImageView(){
        if(imageView == null){
            imageView = new ImageView();
        }
        return imageView;
    }
    private ImageView(){
        setPreferredSize(new Dimension(500,500));
        Utils.initVideoCapture(new File("C:\\Users\\thoma\\Desktop\\Recording #8.mp4"));
        nextImage();
    }
    public void setVideoFile(File file){
        Utils.initVideoCapture(file);
    }
    public void nextImage(){
        image = Utils.nextImage();
    }
    public void previousImage(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null){
            g.drawImage(image,0,0,getWidth(),getHeight(),null);
        }
    }
}
