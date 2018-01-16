package gui;

import lang.ConceptDiagram;
import reader.ConceptDiagramsReader;
import speedith.core.lang.reader.ReadingException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import static speedith.i18n.Translations.i18n;

public class MainForm extends JFrame {
    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;

    private JMenu drawMenu;
    private JMenuItem textInputMenuItem;
    private JMenuItem useCdExample1MenuItem;
    private JMenuItem useCdExample2MenuItem;
    private JMenuItem useCdExample3MenuItem;

    private JFileChooser descriptionFileChooser;
    private JMenu openMenu;
    private JMenu saveMenu;

    private DiagramPanel diagramPanel;
    private ArrowPanel glassPanel;
    private JMenuBar menuBar;

    public MainForm() {
        initUI();
    }

    private void initUI() {
        diagramPanel = new DiagramPanel();
        glassPanel = new ArrowPanel();

        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        openMenu = new JMenu();
        saveMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        openMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();

        drawMenu = new JMenu();
        textInputMenuItem = new JMenuItem();
        useCdExample1MenuItem = new JMenuItem();
        useCdExample2MenuItem = new JMenuItem();
        useCdExample3MenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ontology Visualiser");

        initMenuBar();
        setContentPane(diagramPanel);
        setGlassPane(glassPanel);
        getGlassPane().setVisible(true);

        descriptionFileChooser = new JFileChooser();
        descriptionFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("OntologyVisualiser diagram files", "sdt"));
        descriptionFileChooser.setMultiSelectionEnabled(false);
        pack();
    }

    private void initMenuBar() {
        fileMenu.setMnemonic('F');
        fileMenu.setText("File");

        openMenu.setText("Load");
        fileMenu.add(openMenu);
        saveMenu.setText("Save");
        fileMenu.add(saveMenu);

        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        openMenuItem.setMnemonic('L');
        openMenuItem.setText("Load Description");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onOpen();
            }
        });
        openMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveMenuItem.setMnemonic('S');
        saveMenuItem.setText("Save selected description");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onSave();
            }
        });
        saveMenu.add(saveMenuItem);

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        drawMenu.setMnemonic('D');
        drawMenu.setText("Draw");

        textInputMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        textInputMenuItem.setMnemonic(ResourceBundle.getBundle("speedith/i18n/strings").getString("MAIN_FORM_TEXT_INPUT_MNEMONIC").charAt(0));
        ResourceBundle bundle = ResourceBundle.getBundle("speedith/i18n/strings"); // NOI18N
        textInputMenuItem.setText(bundle.getString("MAIN_FORM_TEXT_INPUT")); // NOI18N
        textInputMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onTextInputClicked(evt);
            }
        });
        drawMenu.add(textInputMenuItem);

        useCdExample1MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
        useCdExample1MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE1_MNEMONIC").charAt(0));
        useCdExample1MenuItem.setText(i18n("MAIN_FORM_USE_EXAMPLE1")); // NOI18N
        useCdExample1MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample1(evt);
            }
        });
        drawMenu.add(useCdExample1MenuItem);

        useCdExample2MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        useCdExample2MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE2_MNEMONIC").charAt(0));
        useCdExample2MenuItem.setText(i18n("MAIN_FORM_USE_EXAMPLE2")); // NOI18N
        useCdExample2MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample2(evt);
            }
        });
        drawMenu.add(useCdExample2MenuItem);

        useCdExample3MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_MASK));
        useCdExample3MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE3_MNEMONIC").charAt(0));
        useCdExample3MenuItem.setText(i18n("MAIN_FORM_USE_EXAMPLE3")); // NOI18N
        useCdExample3MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample3(evt);
            }
        });
        drawMenu.add(useCdExample3MenuItem);
        menuBar.add(drawMenu);
        setJMenuBar(menuBar);
    }

    private void onOpen() {
        int returnVal = descriptionFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = descriptionFileChooser.getSelectedFile();
            try {
                ConceptDiagram input = ConceptDiagramsReader.readConceptDiagram(file);
                if (!input.isValid()) {
                    throw new ReadingException("The spider diagram contained in the file is not valid.");
                }
                diagramPanel.setDiagram(input);
                setArrowPanel();
                this.setTitle("Ontology Visualisation" + ": " + file.getName());
                pack();
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(this, "An error occurred while accessing the file:\n" + ioe.getLocalizedMessage());
            } catch (ReadingException re) {
                JOptionPane.showMessageDialog(this, "An error occurred while reading the contents of the file:\n" + re.getLocalizedMessage());
            }
        }
    }

    private void onSave() {
    }

    private void exitMenuItemActionPerformed(ActionEvent evt) {
        this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void setArrowPanel() {
        this.remove(glassPanel);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                glassPanel = diagramPanel.getArrowGlassPanel();
                setGlassPane(glassPanel);
                getGlassPane().setVisible(true);
            }
        });
        validate();
        repaint();
    }

    private void onExample1(ActionEvent evt) {
        diagramPanel.setDiagram(getExampleA());
        setArrowPanel();
        setTitle("OntologyVisualiser" + ": " + "Example 1");
        pack();
    }

    private void onExample2(ActionEvent evt) {
        diagramPanel.setDiagram(getExampleB());
        setArrowPanel();
        setTitle("OntologyVisualiser" + ": " + "Example 2");
        pack();
    }

    private void onExample3(ActionEvent evt) {
        diagramPanel.setDiagram(getExampleC());
        setArrowPanel();
        setTitle("OntologyVisualiser: " + ": " + "Example 3");
        pack();
    }

    private void onTextInputClicked(java.awt.event.ActionEvent evt) {
        CDInputDialog dialog = new CDInputDialog();
        dialog.setVisible(true);
        if (!dialog.isCancelled() && dialog.getConceptDiagram() != null) {
            diagramPanel.setDiagram(dialog.getConceptDiagram());
            setArrowPanel();
            setTitle("OntologyVisualiser");
            pack();
        }
    }

    public static ConceptDiagram getExampleA() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("BasicCD {\n" +
                    "    spider_diagrams = [\n" +
                    "        PrimarySD {\n" +
                    "            spiders = [\"c\"],\n" +
                    "            habitats = [(\"c\", [([\"C4\"],[])])],\n" +
                    "            sh_zones = [],\n" +
                    "            present_zones = [([], [\"C4\"]), ([\"C4\"], [])]},\n" +
                    "        PrimarySD {\n" +
                    "            spiders = [\" \", \"\"],\n" +
                    "            habitats = [(\" \", [([\"C5\", \"_anon\"],[])]), (\"\", [([\"C5\", \"_anon\"],[])])],\n" +
                    "            sh_zones = [([\"_anon\"], [\"C5\"])],\n" +
                    "            present_zones = [([\"_anon\", \"C5\"], []), ([\"C5\"], [\"_anon\"]), ([], [\"_anon\", \"C5\"])]}\n" +
                    "    ],\n" +
                    "\tarrows = [(\"op\", \"c\", \"_anon\")]\n" +
                    "}");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static ConceptDiagram getExampleB() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("BasicCD {spider_diagrams = [PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s\", [([\"A\", \"B\"], [])]), (\"s'\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s'\", [([\"A\", \"B\"], [])]), (\"s\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}]}");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static ConceptDiagram getExampleC() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("BinarySD {arg1 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s\", [([\"A\", \"B\"], [])]), (\"s'\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, arg2 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s'\", [([\"A\", \"B\"], [])]), (\"s\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, operator = \"op -->\" }");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        new MainForm().setVisible(true);
    }
}
