package wavelet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Matrix {
    
    public float[] matrixFromImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        float[] matrix = new float[height*width];
    
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grayValue = image.getRaster().getSample(j, i, 0); // greyscale values
                matrix[i*width + j] = (float) grayValue;
            }
        }
    
        return matrix;
    }
    
    public BufferedImage imageFromMatrix(float[] matrix, int size) {
        int width = size;
        int height = size;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grayValue = Math.round(matrix[i*width+j]);
                if(grayValue > 255) grayValue = 255;
                if(grayValue < 0) grayValue = 0;
                image.getRaster().setSample(j, i, 0, grayValue);
            }
        }
    
        return image;
    }
    
    public void saveMatrixToFile(float[] matrix, int size, String filePath) 
    {
        File file = new File(filePath);

        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    writer.print(matrix[i*size + j] + " ");
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
