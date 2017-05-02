package mitsai.etl.transform.feature.jFeatureHandler;

import ij.process.ColorProcessor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by junior on 11/14/15.
 */
class ImageLoader {
    public static ColorProcessor open(String imagePath) throws IOException {
        return new ColorProcessor(createImageBuffer(imagePath));
    }

    private static BufferedImage createImageBuffer(String absolutePath) throws IOException {
        return ImageIO.read(new File(absolutePath));
    }

}
