package wavelet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageManipulation {
    private ImageIcon mImage; // eredeti kép
    private BufferedImage mBufferedImage; // feldolgozás alatt álló kép
    private int imgDim;
    private int level;

    public ImageManipulation()
    {
        imgDim = 0; level = 0;
    }

    public BufferedImage createBufferedImage(ImageIcon img)
    {
        // buffer íráshoz
        BufferedImage bufferedImage = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_BYTE_GRAY);
    
        // alap graphic rajzoláshoz - privát példányt rajzol
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img.getImage(), 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }

    public ImageIcon createImageIcon(BufferedImage image) {
        return new ImageIcon(image);
    }
    
    public boolean LoadImage()
    {
        // Fájl kiválasztása local CP-ről
        JFileChooser fileChooser = new JFileChooser();

        // Kezdő mappa
        fileChooser.setCurrentDirectory(new File("/Users/czimbermark/IdeaProjects/Wavelet"));

        // Felhasználói választás 
        int returnVal = fileChooser.showOpenDialog(null);

        // Volt e választott fájl
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            // Kiválasztott fájl
            File file = fileChooser.getSelectedFile();

            try 
            {
                // olvashasható e a kép?
                mImage = new ImageIcon(ImageIO.read(file));
                mBufferedImage = createBufferedImage(mImage);

                int w = mBufferedImage.getWidth();
                int h = mBufferedImage.getHeight();
                int i;

                if (w != h || w < 16 || w > 16384 || mBufferedImage.getType() != BufferedImage.TYPE_BYTE_GRAY) 
                {
                    System.out.println("Hibás képparaméterek");
                    return false;
                }

                for (i = 16, level=4; i < 16384; i *= 2, level++) 
                {
                    if (i == w) { imgDim = w; break; }
                }
                if (imgDim == 0) { System.out.println("Kép mérete nem 2 hatvány"); return false; }

                return true; // sikeres betöltés

            } 
            catch (IOException ex) 
            {
                // Ha bemenet operáció hibás IOException kezelés
                ex.printStackTrace();
            } 
        }

        System.out.println("Hibás betöltés");
        return false; // sikertelen betöltés
    }

    public boolean SaveImage() {
        if (mImage != null) {
            // Create a file chooser
            JFileChooser fileChooser = new JFileChooser();

            // Show save dialog
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try {
                    // Save the image as a jpg file
                    ImageIO.write(mBufferedImage, "jpg", fileToSave);
                    return true;
                } catch (IOException ex) {
                    // Handle IO exception
                    ex.printStackTrace();
                }
            } else if (userSelection == JFileChooser.CANCEL_OPTION) {
                // User canceled the operation
                System.out.println("Save operation canceled by the user.");
            }
        }

        System.out.println("Error in saving");
        return false;
    }

    public void show(MenuFrame menu)
    {

        ImageIcon icon = createImageIcon(mBufferedImage);

        // Create a JLabel with the ImageIcon
        JLabel label = new JLabel(icon);

        // Create a JScrollPane and add the label to it
        JScrollPane scrollPane = new JScrollPane(label);

        // Set the preferred size of the scroll pane to fit the image
        scrollPane.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

        // felület törlése
        menu.getFrame().getContentPane().removeAll();
        System.out.println("Frame refreshed");

        // Add the scroll pane to the frame
        menu.getFrame().add(scrollPane, BorderLayout.CENTER);
        // menü frissítése

        menu.getFrame().revalidate();
        System.out.println("Image shown");
    }

    public ImageIcon getImage()
    {
        return mImage;
    }

    public BufferedImage getBuffImage()
    {
        return mBufferedImage;
    }

    public void setBufferedImage(BufferedImage img)
    {
        mBufferedImage = img;
    }

    public int getImgDim() { return imgDim; }

    public int getLevel() { return level; }
}
