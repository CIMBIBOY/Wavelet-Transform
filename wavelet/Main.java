package wavelet;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main {

    private static ImageManipulation image = new ImageManipulation();
    private static MenuFrame menuFrame;
    private static Matrix mMatrix = new Matrix();
    private static WaveletTransform waveletT = new WaveletTransform();
    private static int param;

    // alapkép mátrixa 
    public static float[] matrix;

    public static void show(BufferedImage buffI)
    {
        // transzformált kép megjelenítése - mátrix visszatrafó
        ImageIcon icon = image.createImageIcon(buffI);
        // felület törlése
        menuFrame.getFrame().getContentPane().removeAll();
        System.out.println("Frame refreshed");
        // címke kitűzése a menübe a képpel
        menuFrame.getFrame().add(new JLabel(icon), BorderLayout.CENTER);
        // menü frissítése
        menuFrame.getFrame().revalidate();
        System.out.println("Image shown");
    }

    public static void main(String[] args) 
    {
        // Gui instance
        menuFrame = new MenuFrame();
        
        menuFrame.getLoadMenuItem().addActionListener(e -> 
        {
            if(image.LoadImage() == false) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép betöltése sikertelen!\nMérete vagy típusa nem megfelelő!\nA kép követelményei:\nszélesség és magasság = 2^n\n-min 16*16 pixel\n-max 16384*16384 pixel\nSzürkeárnyalatos kép", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            WaveletTransform.Size = image.imgDim;
            // felület törlése
            menuFrame.getFrame().getContentPane().removeAll();

            // címke kitűzése a menübe a képpel
            menuFrame.getFrame().add(image.getLabel(), BorderLayout.CENTER);

            // menü frissítése
            menuFrame.getFrame().revalidate();

            // alapkép mátrixa 
            matrix = mMatrix.matrixFromImage(image.getBuffImage());
            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\matrix.txt");
        
            param = 0;
        });

        // wavelet paraméter választás
        menuFrame.getDaubTable().getSelectionModel().addListSelectionListener(e -> 
        {
            if(image.getImage() == null) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem elérhető, nem lett betöltve!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(param != 0) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "Többszörös transzformációnak nincs értelme!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // kiválasztás folyamatban van e?
            if (!e.getValueIsAdjusting()) 
            {
                // kiválasztott sor indexe a táblázatban
                int selectedRow = menuFrame.getDaubTable().getSelectedRow();
                
                // valóban történt e választás
                if (selectedRow >= 0) 
                {
                    // sorérték
                    Object paramNum = menuFrame.getDaubTable().getValueAt(selectedRow, 0);
                    // Nem 0 és int
                    if (paramNum != null && paramNum instanceof Integer) 
                    {
                        // kapott paraméter
                        param = (Integer) paramNum;
                        // 10 paraméter közötti switchcase
                        switch (param) 
                        {
                        case 1:
                            waveletT.process(1, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "1-es paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 2:
                            waveletT.process(2, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "2-es paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 3:
                            waveletT.process(3, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "3-as paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 4:
                            waveletT.process(4, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "4-es paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 5:
                            waveletT.process(5, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "5-ös paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 6:
                            waveletT.process(6, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "6-os paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 7:
                            waveletT.process(7, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "7-es paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 8:
                            waveletT.process(8, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "8-as paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 9:
                            waveletT.process(9, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "9-es paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        case 10:
                            waveletT.process(10, matrix, image.imgDim, image.level); // wavelet trafó a mátrixon
                            mMatrix.saveMatrixToFile(matrix, image.imgDim, "D:\\Egyetem\\Prog\\Prog3\\Nagyhazi\\transformedMatrix.txt");
                            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
                            JOptionPane.showMessageDialog(menuFrame.getFrame(), "10-es paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                            break;
                        }
                    }
                }
            }
        });

        menuFrame.getCutTable().getSelectionModel().addListSelectionListener(e -> 
        {
            if(image.getImage() == null) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem elérhető, nem lett betöltve!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(param == 0) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem volt transzformálva, nem vágható!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // kiválasztás folyamatban van e?
            if (!e.getValueIsAdjusting()) 
            {
                // kiválasztott sor indexe a táblázatban
                int selectedRow = menuFrame.getDaubTable().getSelectedRow();
                
                // valóban történt e választás
                if (selectedRow >= 0) 
                {
                    // sorérték
                    Object paramNum = menuFrame.getDaubTable().getValueAt(selectedRow, 0);
                    // Nem 0 és int
                    if (paramNum != null && paramNum instanceof Integer) 
                    {
                        // kapott paraméter
                        int parameter = (Integer) paramNum;
                        // 10 paraméter közötti switchcase
                        switch (parameter) 
                        {
                        case 1: 
                        break;
                        case 2:
                        break;
                        case 3:
                        break;
                        case 4:
                        break;
                        }
                    }
                }
            }
        });

        menuFrame.getReverseMenuItem().addActionListener(e -> 
        {
            if(image.getImage() == null) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem elérhető, nem lett betöltve!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(param == 0) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem volt transzformálva, ezért vissza sem lehet!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            waveletT.reverse(param, matrix, image.imgDim, image.level);
            show(mMatrix.imageFromMatrix(matrix, image.imgDim));
            JOptionPane.showMessageDialog(menuFrame.getFrame(), param + ". paraméter alapján", "Sikeres vissza transzformáció", JOptionPane.PLAIN_MESSAGE);
        
            param = 0; // újra transzformálás engedélyezése
        });
        
        menuFrame.getSaveMenuItem().addActionListener(e -> 
        {
            if(image.getImage() == null) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem elérhető, nem lett betöltve!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(image.SaveImage()==false)
            {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép mentése sikertelen!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            };
        });

        // menü exit opció implementálása - lambda function call 
        menuFrame.getExitMenuItem().addActionListener(e -> 
        {
            int option = JOptionPane.showConfirmDialog(null, "Valóban ki szeretnél lépni?", "Kilépés", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) 
            {
                System.exit(0); // kilépés 
            }
        });
    }
}

