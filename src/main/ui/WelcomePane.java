package ui;

import model.Event;
import model.EventLog;
import model.TravelJournal;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//The following resources were used to help make the overall Welcome Pane:
//https://stackoverflow.com/questions/28889667/create-a-form-with-a-background-image-jlayeredpane
//https://docs.oracle.com/javase/tutorial/uiswing/examples/components/
//TopLevelDemoProject/src/components/TopLevelDemo.java
//https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html


//Creates the starting point for the GUI: A welcome screen that allows you to enter your name.
public class WelcomePane extends JPanel implements ActionListener {
    private static Color babyBlue = new Color(204, 247, 255);
    private static Color lavender = new Color(213, 165, 230);
    private static final String newline = "\n";
    private String name;

    protected JFormattedTextField nameField;
    protected  JTextArea textArea = new JTextArea(1, 50);

    private static ImageIcon airplane = new ImageIcon("./data/PaperAirplane.png");

    private JLabel welcomeMessage;

    //EFFECTS: Creates a welcome message pane
    //With help from: https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
    public WelcomePane() {
        setLayout(new BorderLayout());
        BackgroundPane backgroundPane = new BackgroundPane();
        backgroundPane.setLayout(new BorderLayout());
        add(backgroundPane);

        designWelcomeMessage();
        backgroundPane.add(welcomeMessage, BorderLayout.CENTER);
        nameField = makeNameTextField();
        backgroundPane.add(nameField, BorderLayout.PAGE_END);

        try {
            backgroundPane.setBackgroundImage(ImageIO.read(new File("./data/RainbowClouds.png")));
        } catch (IOException exception) {
            System.out.println("Unexpected image error!");
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets the visual attributes of the welcome message
    private void designWelcomeMessage() {
        Font welcomeFont = new Font("Serif", Font.ITALIC + Font.BOLD, 50);
        welcomeMessage = new JLabel("Welcome to Souvenir!", airplane, JLabel.CENTER);
        welcomeMessage.setFont(welcomeFont);
        welcomeMessage.setVerticalTextPosition(JLabel.TOP);
        welcomeMessage.setHorizontalTextPosition(JLabel.CENTER);
        welcomeMessage.setOpaque(false);
        welcomeMessage.setForeground(Color.BLACK);
    }

    //MODIFIES: this
    //EFFECTS: Makes the text field where the user enters his/her/their name
    //With help from: https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
    private JFormattedTextField makeNameTextField() {
        JFormattedTextField nameField = new JFormattedTextField("Type your name here :)");
        nameField.setBounds(20, 5, 100, 10);
        Font nameFont = new Font("Serif", Font.ITALIC, 25);
        nameField.setFont(nameFont);
        nameField.setBackground(babyBlue);
        nameField.setBorder(new LineBorder(lavender, 5));
        nameField.setVisible(true);
        nameField.addActionListener(this);
        //referred to https://stackoverflow.com/questions/15507639/how-do-i-center-a-jtextfield
        //for help with the alignment of the text
        nameField.setHorizontalAlignment(JTextField.CENTER);
        return nameField;
    }

    //MODIFIES: this
    //EFFECTS: Detects when the user enters his/her/their name and opens up the journal frame
    //Taken from: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextDemoProject/src/components/TextDemo.java
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = nameField.getText();
        textArea.append(text + newline);
        nameField.selectAll();
        setName(text);
        createAndShowJournalScreen(text);
    }

    //getter
    public String getName() {
        return name;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    //EFFECTS: Makes the welcome frame with the welcome pane and displays the window
    private static void createAndShowWelcomeScreen() {
        //Create and set up the window.
        JFrame welcomeFrame = new JFrame("SOUVENIR");
        welcomeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        welcomeFrame.setPreferredSize(new Dimension(800, 425));
        WelcomePane mainWelcome = new WelcomePane();
        welcomeFrame.add(mainWelcome);

        //Help with the window listener was taken from the following resource:
        //https://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe/1235994
        welcomeFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    for (Event next : EventLog.getInstance()) {
                        System.out.println(next);
                    }
                //THEN you can exit the program
                System.exit(0);
            }
        });

        //Display the window.
        welcomeFrame.pack();
        welcomeFrame.setVisible(true);
    }

    //EFFECTS: Makes the journal frame with the journal pane and menu bar and displays it
    private static void createAndShowJournalScreen(String name) {
        JFrame journalFrame = new JFrame(name + "'s Souvenir Journal");
        TravelJournal travelJournal = new TravelJournal();
        travelJournal.setName(name);
        JournalWithEntriesScreen journal = new JournalWithEntriesScreen(travelJournal);
        journalFrame.add(journal);
        journalFrame.setPreferredSize(new Dimension(800,400));

        journalFrame.setJMenuBar(journal.createMenuBar());

        //Display the window.
        journalFrame.pack();
        journalFrame.setVisible(true);
    }

    //EFFECTS: runs the starting point of the app (the welcome screen)
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowWelcomeScreen();

            }
        });
    }


}




