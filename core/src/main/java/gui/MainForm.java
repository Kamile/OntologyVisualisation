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
    private JMenuItem useCdExample4MenuItem;

    private JFileChooser descriptionFileChooser;
    private JMenu openMenu;
    private JMenu saveMenu;

    private BoundaryPanel boundaryPanel;
    private ArrowPanel glassPanel;
    private JMenuBar menuBar;

    public MainForm() {
        initUI();
    }

    private void initUI() {
        boundaryPanel = new BoundaryPanel();
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
        useCdExample4MenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ontology Visualiser");

        initMenuBar();
        setContentPane(boundaryPanel);
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
        useCdExample1MenuItem.setText("Subclass example"); // NOI18N
        useCdExample1MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample1(evt);
            }
        });
        drawMenu.add(useCdExample1MenuItem);

        useCdExample2MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        useCdExample2MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE2_MNEMONIC").charAt(0));
        useCdExample2MenuItem.setText("ObjectSomeValuesFrom(op CE) Example");
        useCdExample2MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample2(evt);
            }
        });
        drawMenu.add(useCdExample2MenuItem);

        useCdExample3MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_MASK));
        useCdExample3MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE3_MNEMONIC").charAt(0));
        useCdExample3MenuItem.setText("Assertion Axiom Example"); // NOI18N
        useCdExample3MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample3(evt);
            }
        });
        drawMenu.add(useCdExample3MenuItem);

        useCdExample4MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.CTRL_MASK));
        useCdExample4MenuItem.setMnemonic('4');
        useCdExample4MenuItem.setText("Datatype Definition Example"); // NOI18N
        useCdExample4MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample4(evt);
            }
        });
        drawMenu.add(useCdExample4MenuItem);
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
                boundaryPanel.setDiagram(input);
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
                glassPanel = boundaryPanel.getArrowGlassPanel();
                setGlassPane(glassPanel);
                getGlassPane().setVisible(true);
                pack();
            }
        });
        validate();
        repaint();
    }

    private void onExample1(ActionEvent evt) {
        boundaryPanel.setDiagram(getExampleA());
        setArrowPanel();
        setTitle("OntologyVisualiser" + ": " + "Subclass");
    }

    private void onExample2(ActionEvent evt) {
        boundaryPanel.setDiagram(getExampleB());
        setArrowPanel();
        setTitle("OntologyVisualiser" + ": ObjectSomeValuesFrom(op CE)");
    }

    private void onExample3(ActionEvent evt) {
        boundaryPanel.setDiagram(getExampleC());
        setArrowPanel();
        setTitle("OntologyVisualiser: " + ": OWL Assertion Axiom ");
    }

    private void onExample4(ActionEvent evt) {
        boundaryPanel.setDiagram(getExampleD());
        setArrowPanel();
        setTitle("OntologyVisualiser: " + ": DatatypeDefinition(DT DataUnionOf(DR1, DR2))");
    }

    private void onTextInputClicked(java.awt.event.ActionEvent evt) {
        CDInputDialog dialog = new CDInputDialog();
        dialog.setVisible(true);
        if (!dialog.isCancelled() && dialog.getConceptDiagram() != null) {
            boundaryPanel.setDiagram(dialog.getConceptDiagram());
            setArrowPanel();
            setTitle("OntologyVisualiser");
            pack();
        }
    }

    public static ConceptDiagram getExampleA() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("ConceptDiagram {\n" +
                    "    spider_diagrams = [\n" +
                    "        (PrimarySD {\n" +
                    "            spiders = [\"c\"],\n" +
                    "            habitats = [(\"c\", [([\"C4\"],[])])],\n" +
                    "            sh_zones = [],\n" +
                    "            present_zones = [([], [\"C4\"]), ([\"C4\"], [])]},\n" +
                    "        PrimarySD {\n" +
                    "            spiders = [\"_1\", \"_2\"],\n" +
                    "            habitats = [(\"_1\", [([\"C5\", \"_anon\"],[])]), (\"_2\", [([\"C5\", \"_anon\"],[])])],\n" +
                    "            sh_zones = [([\"_anon\"], [\"C5\"])],\n" +
                    "            present_zones = [([\"_anon\", \"C5\"], []), ([\"C5\"], [\"_anon\"]), ([], [\"_anon\", \"C5\"])]}\n" +
                    "    )],\n" +
                    "\tarrows = [(\"op\", \"c\", \"_anon\")]\n" +
                    "}");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static ConceptDiagram getExampleB() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("ConceptDiagram {\n" +
                    "    spider_diagrams = [\n" +
                    "        (PrimarySD {\n" +
                    "            spiders = [],\n" +
                    "            habitats = [],\n" +
                    "            sh_zones = [],\n" +
                    "            present_zones = [([], [\"CE\"]), ([\"CE\"], [])]}),\n" +
                    "        (PrimarySD {\n" +
                    "            spiders = [],\n" +
                    "            habitats = [],\n" +
                    "            sh_zones = [([\"_anon\"], [])],\n" +
                    "            present_zones = [([], [\"_anon\"])]})\n" +
                    "    ],\n" +
                    "\tarrows = [(\"op-\", \"CE\", \"_anon\")]\n" +
                    "}\n");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static ConceptDiagram getExampleC() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("ConceptDiagram {\n" +
                    "    spider_diagrams = [\n" +
                    "        (PrimarySD {\n" +
                    "            spiders = [\"a\"],\n" +
                    "            sh_zones = [],\n" +
                    "            habitats = [(\"a\", [([\"C1\"], [\"C2\"])])],\n" +
                    "            present_zones=[([\"C2\"],[\"C1\"]), ([\"C1\"],[\"C2\"]), ([],[\"C1\", \"C2\"]), ([\"C1\", \"C2\"],[])]})\n" +
                    "    ],\n" +
                    "\tarrows = []\n" +
                    "}");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static ConceptDiagram getExampleD() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("ConceptDiagram {\n" +
                    "    spider_diagrams = [\n" +
                    "        (PrimarySD {\n" +
                    "            spiders = [],\n" +
                    "            sh_zones = [\n" +
                    "                ([\"DT\"], [\"DR1\", \"DR2\"]),\n" +
                    "                ([\"DR1\", \"DR2\"], [\"DT\"]),\n" +
                    "                ([\"DR2\"], [\"DT\", \"DR1\"]),\n" +
                    "                ([\"DR1\"],[\"DT\", \"DR2\"])],\n" +
                    "            habitats = [],\n" +
                    "            present_zones=[\n" +
                    "                ([\"DR2\", \"DT\"],[\"DR1\"]),\n" +
                    "                ([\"DR1\", \"DT\"],[\"DR2\"]),\n" +
                    "                ([],[\"DT\", \"DR1\", \"DR2\"]),\n" +
                    "                ([\"DT\", \"DR1\", \"DR2\"],[])]\n" +
                    "        })\n" +
                    "    ],\n" +
                    "\tarrows = []\n" +
                    "}\n");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        new MainForm().setVisible(true);
    }
}
