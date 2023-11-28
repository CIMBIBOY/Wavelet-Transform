package wavelet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class MenuFrame extends Component implements ActionListener
{
    private JFrame frame; // creating frame
    private JMenuBar menuBar; // creating menu bar 
    private JMenu fileMenu; // menu itself
    private JMenuItem loadMenuItem; // creating items 
    private JMenuItem saveMenuItem; 
    private JMenuItem exitMenuItem;

    private JMenu waveletParametersMenu;
    private JTable waveletParametersTable;

    private JMenu cutParametersMenu;
    private JTable cutParametersTable;

    private JMenuItem reverseMenuItem;

    public MenuFrame()
    {
        initialize();
    }

    private void initialize()
    {
        frame = new JFrame(); // instence
        frame.setTitle("Wavelet Transformer Menu"); // title 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // alap bezárási művelet
        frame.setSize(1400, 800); // alkalmazás nagyság
        frame.setLayout(new BorderLayout(10,10)); // alap háttér
        frame.setLocationRelativeTo(null); // alap elhelyezés

        Font f = new Font("SansSerif", Font.PLAIN, 18); // betütípus 
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        UIManager.put("CheckBoxMenuItem.font", f);
        UIManager.put("RadioButtonMenuItem.font", f);

        // maga a menu
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");

        // példányok
        loadMenuItem = new JMenuItem("Fájl Betöltés");
        loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        loadMenuItem.addActionListener(this);

        saveMenuItem = new JMenuItem("Fájl Kimentés");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(this);

        exitMenuItem = new JMenuItem("Kilépés");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exitMenuItem.addActionListener(this);

        waveletParametersMenu = new JMenu("Wavelet Paraméterek:");
        cutParametersMenu = new JMenu("Vágás Paraméterek:");

        reverseMenuItem = new JMenuItem("Vissza Transzformáció");

        // itemek hozzáadása
        fileMenu.add(loadMenuItem);

        // Daubechies JTable inicializálása a paraméterekkel
        Object[][] daubData = {
            {1, "Daubechies 1"},
            {2, "Daubechies 2"},
            {3, "Daubechies 3"},
            {4, "Daubechies 4"},
            {5, "Daubechies 5"},
            {6, "Daubechies 6"},
            {7, "Daubechies 7"},
            {8, "Daubechies 8"},
            {9, "Daubechies 9"},
            {10, "Daubechies 10"}
        };
        
         // Vágás JTable inicializálás vágási paraméterekkel
        Object[][] cutData = {
            {2, "2-es Vágás"},
            {4, "4-es Vágás"},
            {8, "8-as Vágás"},
            {16, "16-os Vágás"}
        };
        
        // Fejléc
        String[] columnNames = {"Paraméter szám", "Leírás"};
        
        // JTables létrehozása
        waveletParametersTable = new JTable(daubData, columnNames);
        cutParametersTable = new JTable(cutData, columnNames);

        // Set a custom editor that doesn't allow editing
        waveletParametersTable.setDefaultEditor(Object.class, null);
        cutParametersTable.setDefaultEditor(Object.class, null);

        waveletParametersMenu.add(waveletParametersTable);
        cutParametersMenu.add(cutParametersTable);

        fileMenu.add(waveletParametersMenu);
        fileMenu.add(cutParametersMenu);

        fileMenu.add(reverseMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        // bar hozzáadása
        menuBar.add(fileMenu);

        // bar beállítása
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    // Inside MenuFrame class


    @Override // akció végrehajtó
    public void actionPerformed(ActionEvent e) 
    {
        System.out.println("Végrehajtva: " + e.getActionCommand());
    }

    // frame getter
    public JFrame getFrame()
    { 
        return frame;
    }

    // item getterek main callhoz
    public JMenuItem getLoadMenuItem() 
    {
        return loadMenuItem;
    }

    public JTable getDaubTable()
    {
        return waveletParametersTable;
    }

    public JTable getCutTable()
    {
        return cutParametersTable;
    }

    public JMenuItem getReverseMenuItem() {
        return reverseMenuItem;
    }

    public JMenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }
}

