package wavelet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Matrix
{
    private static float[] mMatrix; //matrix

    private int mSize; //képméret

    public void Create(int size)
    {
        mMatrix = new float[size*size];
        mSize = size;
    }

    public void matrixFromImage(BufferedImage image) {
        int width = image.getWidth();
        Create(width);
    
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                int grayValue = image.getRaster().getSample(j, i, 0); // greyscale values
                mMatrix[i*width + j] = (float) grayValue;
            }
        }
    }
    
    public void imageFromMatrix(BufferedImage image)
    {
        for (int i = 0; i < mSize; i++) {
            for (int j = 0; j < mSize; j++) {
                int grayValue = Math.round(mMatrix[i*mSize+j]);
                if(grayValue > 255) grayValue = 255;
                if(grayValue < 0) grayValue = 0;
                image.getRaster().setSample(j, i, 0, grayValue);
            }
        }
    }

    public BufferedImage getImageFromMatrix()
    {
        BufferedImage image = new BufferedImage(mSize, mSize, BufferedImage.TYPE_BYTE_GRAY);
        imageFromMatrix(image);
        return image;
    }
    
    public void saveMatrixToFile(String filePath)
    {
        File file = new File(filePath);

        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < mSize; i++) {
                for (int j = 0; j < mSize; j++) {
                    writer.print(mMatrix[i*mSize + j] + " ");
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int Cut(int param)
    {
        int i, j, a, zeros=0;
        for(i=0; i<mSize; i++)
        {
            for(j=0; j<mSize; j++)
            {
                a = (int) (mMatrix[i*mSize +j] / param); // vágás
                if(a == 0) zeros++; // 0-ák száma
                mMatrix[i*mSize +j] = a*param; // visszaalakítás
            }
        }
        return zeros;
    }

    public float[] getMatrix()
    {
        return mMatrix;
    }

    public void setmMatrix(float[] matrix) { mMatrix = matrix; }

    public int getMatrixSize()
    {
        return mSize;
    }

    public void setmSize(int size) { mSize = size; }
}
