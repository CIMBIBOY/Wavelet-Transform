package UnitTests;

import wavelet.*;

import static org.junit.assertEquals;

import wavelet.ImageManipulation;
import wavelet.Matrix;
import wavelet.WaveletTransform;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class JUnit {
    private Matrix matrix;
    private WaveletTransform waveletTransform;
    private ImageManipulation imageManipulation;

    @Before
    public void setUp() {
        matrix = new Matrix();
        waveletTransform = new WaveletTransform();
        imageManipulation = new ImageManipulation();
        WaveletTransform.Size = 4; // Set a size for testing
    }

    @Test
    public void testMatrixFromImage() {
        // Create a simple 4x4 grayscale image
        BufferedImage testImage = new BufferedImage(4, 4, BufferedImage.TYPE_BYTE_GRAY);
        float[] resultMatrix = matrix.matrixFromImage(testImage);

        // Check if the result matrix is not null and has the expected size
        assertNotNull(resultMatrix);
        assertEquals(16, resultMatrix.length);

        // Add more specific assertions as needed
    }

    @Test
    public void testImageFromMatrix() {
        // Create a sample matrix
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        BufferedImage resultImage = matrix.imageFromMatrix(testMatrix, 4);

        // Check if the result image is not null and has the expected dimensions
        assertNotNull(resultImage);
        assertEquals(4, resultImage.getWidth());
        assertEquals(4, resultImage.getHeight());

        // Add more specific assertions as needed
    }

    @Test
    public void testSaveMatrixToFile() {
        // Create a sample matrix
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        matrix.saveMatrixToFile(testMatrix, 4, "testMatrix.txt");

        // Add assertions to check if the file was created and contains the expected content
        // You may want to read the file and compare its content with the expected content
    }

    @Test
    public void testProcessAndReverse() {
        // Create a sample matrix
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        // Perform wavelet transformation
        waveletTransform.process(2, testMatrix, 4, 2);

        // Add assertions to check the result after transformation

        // Perform inverse wavelet transformation
        waveletTransform.reverse(2, testMatrix, 4, 2);

        // Add assertions to check if the matrix is back to the original state
    }

    @Test
    public void testLoadImageAndSaveImage() {
        // Load a sample image
        assertTrue(imageManipulation.LoadImage());

        // Get the loaded image
        ImageIcon loadedImage = imageManipulation.getImage();
        assertNotNull(loadedImage);

        // Save the loaded image
        assertTrue(imageManipulation.SaveImage());

        // Add more specific assertions as needed
    }

    @Test
    public void testWaveletTransform() {
        // Create a sample matrix
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        // Perform wavelet transformation
        waveletTransform.waveletTransform(testMatrix, 0, 2, 2, 1);

        // Add assertions to check the result after transformation
        // Check specific values in the transformed matrix

        // Perform inverse wavelet transformation
        waveletTransform.inverseWaveletTransform(testMatrix, 0, 2, 2, 1);

        // Add assertions to check if the matrix is back to the original state
    }

    @Test
    public void testCalcbufInitialization() {
        // Ensure calcbuf is initialized to the correct size
        assertNull(waveletTransform.calcbuf); // Before initialization

        // Trigger wavelet transformation to initialize calcbuf
        float[] testMatrix = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        waveletTransform.waveletTransform(testMatrix, 0, 2, 2, 1);

        // Check if calcbuf is initialized
        assertNotNull(waveletTransform.calcbuf);
        assertEquals(16, waveletTransform.calcbuf.length);
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
}
