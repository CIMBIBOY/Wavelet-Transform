package wavelet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class ImageManipulation {
    private ImageIcon mImage;
    private JLabel mLabel;
    private BufferedImage mBufferedImage;
    public int imgDim;
    public int level;

    public ImageManipulation()
    {
        imgDim = 0;
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
        fileChooser.setCurrentDirectory(new File("D:\\Egyetem\\Prog\\Prog3\\Nagyhazi"));

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
                
                // címke a kép megjelenítéséhez
                mLabel = new JLabel(mImage);

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

    public boolean SaveImage()
    {
        // volt kép betöltés
        if (mImage != null) {
            // mentés helye
            File file = new File("D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\savedImage.jpg");
    
            try {
                // jpg-ként mentés
                ImageIO.write(mBufferedImage, "jpg", file);

                return true;
            } catch (IOException ex) {
                // IO kivétel kezelés
                ex.printStackTrace();
            }
        }

        System.out.println("Hibás mentés");
        return false;
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

    public JLabel getLabel()
    {
        return mLabel;
    }   
}
