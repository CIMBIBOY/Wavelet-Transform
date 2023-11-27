package wavelet;

import javax.swing.*;

public class Main {

    private static ImageManipulation image = new ImageManipulation();
    private static MenuFrame menuFrame = new MenuFrame();
    private static Matrix matrix = new Matrix();
    private static WaveletTransform waveletT = new WaveletTransform();
    private static int param;

    public static void main(String[] args)
    {
        // image.setDir(menuFrame.getDirString());
        // if(!image.LoadTestImage())  JOptionPane.showMessageDialog(menuFrame.getFrame(), "The test image 'Grayscale1.jpg' couldn't be loaded.", "Image Loading Failed", JOptionPane.ERROR_MESSAGE);;

        menuFrame.getLoadMenuItem().addActionListener(e ->
        {
            if(!image.LoadImage()) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép betöltése sikertelen!\nMérete vagy típusa nem megfelelő!\nA kép követelményei:\nszélesség és magasság = 2^n\n-min 16*16 pixel\n-max 16384*16384 pixel\nSzürkeárnyalatos kép", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            matrix.setmSize(image.getImgDim());
            WaveletTransform.Size = image.getImgDim();

            image.show(menuFrame);

            // alapkép mátrixa
            matrix.matrixFromImage(image.getBuffImage());
            matrix.saveMatrixToFile("/Users/czimbermark/IdeaProjects/Wavelet/orig.txt");
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
                    if (paramNum instanceof Integer)
                    {
                        // kapott paraméter
                        param = (Integer) paramNum;
                        // 10 paraméter közötti switchcase
                        waveletT.process(param, matrix.getMatrix(), image.getImgDim(), image.getLevel()); // wavelet trafó a mátrixon
                        matrix.imageFromMatrix(image.getBuffImage());
                        image.show(menuFrame);
                        JOptionPane.showMessageDialog(menuFrame.getFrame(), param + ". paraméter használatával", "Sikeres Transzformáció", JOptionPane.PLAIN_MESSAGE);
                    }
                    matrix.saveMatrixToFile("/Users/czimbermark/IdeaProjects/Wavelet/wavelet.txt");
                }
            }
        });

        menuFrame.getCutTable().getSelectionModel().addListSelectionListener(e -> {
            int zeros;

            if (image.getImage() == null) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem elérhető, nem lett betöltve!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (param == 0) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem volt transzformálva, nem vágható!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // kiválasztás folyamatban van e?
            if (!e.getValueIsAdjusting())
            {
                // kiválasztott sor indexe a táblázatban
                    int selectedRow = menuFrame.getCutTable().getSelectedRow();

                // valóban történt e választás
                if (selectedRow >= 0) {
                    // sorérték
                    Object paramNum = menuFrame.getCutTable().getValueAt(selectedRow, 0);

                    // Nem 0 és int
                    if (paramNum instanceof Integer) {
                        // kapott paraméter
                        int parameter = (Integer) paramNum;

                        zeros = matrix.Cut(parameter);
                        matrix.imageFromMatrix(image.getBuffImage());
                        image.show(menuFrame);
                        JOptionPane.showMessageDialog(menuFrame.getFrame(), "Vágás " + parameter + " paraméterrel, Nulla pixelek száma: " + zeros, "Sikeres vágás", JOptionPane.PLAIN_MESSAGE);

                    }
                    matrix.saveMatrixToFile("/Users/czimbermark/IdeaProjects/Wavelet/cut.txt");
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

            waveletT.reverse(param, matrix.getMatrix(), image.getImgDim(), image.getLevel());
            matrix.imageFromMatrix(image.getBuffImage());
            image.show(menuFrame);
            JOptionPane.showMessageDialog(menuFrame.getFrame(), param + ". paraméter alapján", "Sikeres vissza transzformáció", JOptionPane.PLAIN_MESSAGE);
            matrix.saveMatrixToFile("/Users/czimbermark/IdeaProjects/Wavelet/reverse.txt");

            param = 0; // újra transzformálás engedélyezése
        });

        menuFrame.getSaveMenuItem().addActionListener(e ->
        {
            if(image.getImage() == null) {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép nem elérhető, nem lett betöltve!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!image.SaveImage())
            {
                JOptionPane.showMessageDialog(menuFrame.getFrame(), "A kép mentése sikertelen!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
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

