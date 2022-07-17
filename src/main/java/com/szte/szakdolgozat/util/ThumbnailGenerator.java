package com.szte.szakdolgozat.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public class ThumbnailGenerator {

    private static final String IMG_PATH = "C:/Users/András/Desktop/Egyetem/Szakdolgozat/BackEnd/szakdolgozat/src/main/resources/imgs/";
    private static final String THUMBNAIL_PATH = "C:/Users/András/Desktop/Egyetem/Szakdolgozat/BackEnd/szakdolgozat/src/main/resources/thumbnails/";
    
    public void generateThumbnail(String name,Float percentage) throws IOException {
        BufferedImage image = ImageIO.read(new File(IMG_PATH+name));
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage compressedImageFile = resizeImage(image,type,200,200);
        ImageIO.write(compressedImageFile, "jpg", new File(THUMBNAIL_PATH+name)); //change path where you want it saved
//        OutputStream os = new FileOutputStream(compressedImageFile);
//
//        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
//        ImageWriter writer = writers.next();
//
//        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
//        writer.setOutput(ios);
//
//        ImageWriteParam param = writer.getDefaultWriteParam();
//
//        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//        param.setCompressionQuality(percentage);  // Change the quality value you prefer
//        writer.write(null, new IIOImage(image, null, null), param);
//
//        os.close();
//        ios.close();
//        writer.dispose();
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
}
