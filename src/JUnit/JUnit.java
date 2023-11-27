package JUnit;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import wavelet.ImageManipulation;
import wavelet.Matrix;
import wavelet.WaveletTransform;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class JUnit {
    private Matrix matrix;
    private WaveletTransform waveletTransform;
    private ImageManipulation imageManipulation;

    // private Cut cutting;

    @Before
    public void setUp() {
        matrix = new Matrix();
        waveletTransform = new WaveletTransform();
        imageManipulation = new ImageManipulation();
        imageManipulation.setBufferedImage(new BufferedImage(2, 2, BufferedImage.TYPE_BYTE_GRAY));
        WaveletTransform.Size = 16; // Set a size for testing
    }

    @Test
    public void testMatrixFromImage() {
        // Create a simple 4x4 grayscale image
        BufferedImage testImage = new BufferedImage(4, 4, BufferedImage.TYPE_BYTE_GRAY);
        matrix.matrixFromImage(testImage);

        // Check if the result matrix is not null and has the expected size
        assertNotNull(matrix.getMatrix());
        assertEquals(16, matrix.getMatrix().length);
    }

    @Test
    public void testSaveMatrixToFile() {
        // Create a sample matrix
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        matrix.setmMatrix(testMatrix);
        matrix.saveMatrixToFile("testMatrix.txt");
    }

    @Test
    public void testProcessAndReverse()
    {
        int i, j;

        // Create a sample matrix
        float[] testMatrix = new float[256];

        for(i = 0; i<16; i++)
        {
            for(j = 0; j<16; j++)
            {
                testMatrix[i*16+j] = i*3+j;
            }
        }

        float[] copyofTestMatrix = testMatrix.clone();

        // Perform wavelet transformation
        waveletTransform.process(3, testMatrix, 16, 4);

        // Perform inverse wavelet transformation
        waveletTransform.reverse(3, testMatrix, 16, 4);

        // Test equality
        assertArrayEquals(copyofTestMatrix, testMatrix, 0.001f);
    }

    @Test
    public void testLoadAndSaveImage() {
        // Load a sample image
        assertTrue(imageManipulation.LoadImage());

        // Get the loaded image
        ImageIcon loadedImage = imageManipulation.getImage();
        assertNotNull(loadedImage);

        // Save the loaded image
        assertTrue(imageManipulation.SaveImage());
    }

    @Test
    public void testWaveletTransform() {
        // Create a sample matrix
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        float[] copyofTestMatrix = testMatrix.clone();

        // Perform wavelet transformation
        waveletTransform.waveletTransform(testMatrix, 0, 2, 2, 1);

        // Perform inverse wavelet transformation
        waveletTransform.inverseWaveletTransform(testMatrix, 0, 2, 2, 1);

        // Test equality
        assertArrayEquals(copyofTestMatrix, testMatrix, 0.001f);
    }

    @Test
    public void testCalcbufInitialization() {
        // Ensure calcbuf is initialized to the correct size
        assertNull(waveletTransform.getCalcbuf()); // Before initialization

        // Trigger wavelet transformation to initialize calcbuf
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        waveletTransform.waveletTransform(testMatrix, 0, 2, 2, 1);

        // Check if calcbuf is initialized
        assertNotNull(waveletTransform.getCalcbuf());
        assertEquals(16, waveletTransform.getCalcbuf().length);
    }

    @Test
    public void testImageCreation() {
        // Create a sample image
        BufferedImage testImage = new BufferedImage(4, 4, BufferedImage.TYPE_BYTE_GRAY);
        ImageIcon testIcon = imageManipulation.createImageIcon(testImage);

        // Check if the created image icon is not null
        assertNotNull(testIcon);

        // Check if the image icon has the expected dimensions
        assertEquals(4, testIcon.getIconWidth());
        assertEquals(4, testIcon.getIconHeight());
    }

    @Test
    public void testSetBufferedImage() {
        // Create a sample image
        BufferedImage testImage = new BufferedImage(4, 4, BufferedImage.TYPE_BYTE_GRAY);

        // Set the buffered image in ImageManipulation
        imageManipulation.setBufferedImage(testImage);

        // Check if the buffered image is set correctly
        assertEquals(testImage, imageManipulation.getBuffImage());
    }

    @Test
    public void testGetImageFromMatrix() {
        // Create a sample matrix
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        matrix.setmMatrix(testMatrix);
        matrix.setmSize(4);
        imageManipulation.setBufferedImage(matrix.getImageFromMatrix());

        // Check if the result image is not null and has the expected dimensions
        assertNotNull(imageManipulation.getBuffImage());
        assertEquals(4, imageManipulation.getBuffImage().getWidth());
        assertEquals(4, imageManipulation.getBuffImage().getHeight());
    }

    @Test
    public void testGetandSetMatrix()
    {
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        matrix.setmMatrix(testMatrix);

        // Call the getter method
        float[] resultMatrix = matrix.getMatrix();

        // Check if the result is not null
        assertNotNull(resultMatrix);

        // Check if the result is the same object as the one in the matrix
        assertSame(testMatrix, resultMatrix);

        // Check if the contents of the result are the same as the original matrix
        assertArrayEquals(testMatrix, resultMatrix, 0.001f);
    }

    @Test
    public void CutGrayscale1()
    {
        // Csak Grayscale1.jpg file-ra jó teszt, mivel minden képnél más lesz a teszt.
        imageManipulation.LoadImage();
        matrix.setmSize(imageManipulation.getImgDim());
        WaveletTransform.Size = imageManipulation.getImgDim();
        matrix.matrixFromImage(imageManipulation.getBuffImage());

        // Perform wavelet transformation
        waveletTransform.process(5, matrix.getMatrix(), matrix.getMatrixSize(), imageManipulation.getLevel());
        matrix.imageFromMatrix(imageManipulation.getBuffImage());

        int zeros = matrix.Cut(4);

        assertEquals(zeros, 527015);
    }
}
