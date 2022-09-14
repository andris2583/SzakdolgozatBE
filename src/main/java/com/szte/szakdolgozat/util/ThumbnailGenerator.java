package com.szte.szakdolgozat.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.szte.szakdolgozat.util.Constants.IMAGE_PATH;
import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;

public class ThumbnailGenerator {
    public static BufferedImage generateThumbnail(String name,Float percentage) throws IOException {
        BufferedImage image = ImageIO.read(new File(IMAGE_PATH + name));
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        return resizeImage(image, type, Math.round(image.getWidth() * percentage), Math.round(image.getHeight() * percentage));
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
}
