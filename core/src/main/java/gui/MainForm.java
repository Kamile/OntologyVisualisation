package gui;

import i18n.Translations;
import lang.ConceptDiagram;
import reader.ConceptDiagramsReader;
import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.reader.ReadingException;
import speedith.core.lang.reader.SpiderDiagramsReader;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.rules.util.ReasoningUtils;
import speedith.ui.ProofPanel;
import speedith.ui.SpeedithMainForm;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainForm extends JFrame {

    private DiagramType activeDiagram;

    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;

    private JMenu drawMenu;
    private JMenuItem textInputMenu;
    private JMenuItem useCdExample1MenuItem;
    private JMenuItem useCdExample2MenuItem;
    private JMenuItem useCdExample3MenuItem;

    private JFileChooser goalFileChooser;
    private JMenu openMenu;
    private JMenu saveMenu;

    private ProofPanel proofPanel;
    private JMenuBar menuBar;

    public MainForm() {
        initUI();
    }

    private void initUI() {
        GridBagConstraints gridBagConstraints;
        JSplitPane mainSplitPane = new JSplitPane();

        proofPanel = new ProofPanel();

        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        openMenu = new JMenu();
        saveMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        openMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();

        drawMenu = new JMenu();
        textInputMenu = new JMenuItem();
        useCdExample1MenuItem = new JMenuItem();
        useCdExample2MenuItem = new JMenuItem();
        useCdExample3MenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ontology Visualiser");

        proofPanel.setMinimumSize(new Dimension(500, 300));
        proofPanel.setPreferredSize(new Dimension(750, 300));
        mainSplitPane.setLeftComponent(proofPanel);

        initMenuBar();

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainSplitPane, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(mainSplitPane, GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );

        goalFileChooser = new JFileChooser();
        goalFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Speedith diagram files", "sdt"));
        goalFileChooser.setMultiSelectionEnabled(false);

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
        openMenuItem.setText("Load Goal");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onOpen();
            }
        });
        openMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveMenuItem.setMnemonic('S');
        saveMenuItem.setText("Save selected Subgoal");
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


        useCdExample1MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
        useCdExample1MenuItem.setMnemonic(Translations.i18n("MAIN_FORM_USE_EXAMPLE1_MNEMONIC").charAt(0));
        useCdExample1MenuItem.setText(Translations.i18n("MAIN_FORM_USE_EXAMPLE1")); // NOI18N
        useCdExample1MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample1(evt);
            }
        });
        drawMenu.add(useCdExample1MenuItem);

        useCdExample2MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        useCdExample2MenuItem.setMnemonic(Translations.i18n("MAIN_FORM_USE_EXAMPLE2_MNEMONIC").charAt(0));
        useCdExample2MenuItem.setText(Translations.i18n("MAIN_FORM_USE_EXAMPLE2")); // NOI18N
        useCdExample2MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExample2(evt);
            }
        });
        drawMenu.add(useCdExample2MenuItem);

        useCdExample3MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_MASK));
        useCdExample3MenuItem.setMnemonic(Translations.i18n("MAIN_FORM_USE_EXAMPLE3_MNEMONIC").charAt(0));
        useCdExample3MenuItem.setText(Translations.i18n("MAIN_FORM_USE_EXAMPLE3")); // NOI18N
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
        int returnVal = goalFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = goalFileChooser.getSelectedFile();
            try {
                // TODO: parse as CD
                SpiderDiagram input = SpiderDiagramsReader.readSpiderDiagram(file);
                if (!input.isValid()) {
                    throw new ReadingException("The spider diagram contained in the file is not valid.");
                }
                proofPanel.newProof(Goals.createGoalsFrom(ReasoningUtils.normalize(input)));
                this.setTitle("Speedith"+": " + file.getName());

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

    private void onExample1(ActionEvent evt) {
        proofPanel.newProof(Goals.createGoalsFrom(getExampleA()));
        setTitle("Speedith" + ": " + "Example 1");
    }

    private void onExample2(ActionEvent evt) {
        proofPanel.newProof(Goals.createGoalsFrom(getExampleB()));
        setTitle("Speedith"+": "+"Example 2");
    }

    private void onExample3(ActionEvent evt) {
        proofPanel.newProof(Goals.createGoalsFrom(getExampleC()));
        setTitle("Speedith"+": "+"Example 3");
    }

    private void onTextInputClicked(java.awt.event.ActionEvent evt) {
        CDInputDialog dialog = new CDInputDialog();
        if (proofPanel.getLastGoals() != null && !proofPanel.getLastGoals().isEmpty()) {
//            dialog.setConceptDiagramText(proofPanel.getLastGoals().getGoalAt(0));
        } else {
            dialog.setConceptDiagramText(getExampleA());
        }
        dialog.setVisible(true);
        if (!dialog.isCancelled() && dialog.getConceptDiagrma() != null) {
            proofPanel.newProof(Goals.createGoalsFrom(ReasoningUtils.normalize(dialog.getConceptDiagrma())));
            setTitle("Speedith");
        }
    }

    public static ConceptDiagram getExampleA() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("BinarySD {arg1 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s\", [([\"A\", \"B\"], [])]), (\"s'\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, arg2 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s'\", [([\"A\", \"B\"], [])]), (\"s\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, operator = \"op &\" }");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static ConceptDiagram getExampleB() {
        try {
            return ConceptDiagramsReader.readConceptDiagram("BinarySD {arg1 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s\", [([\"A\", \"B\"], [])]), (\"s'\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, arg2 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s'\", [([\"A\", \"B\"], [])]), (\"s\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, operator = \"op &\" }");
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
