package ui;

import model.SouvenirEntry;
import model.TravelJournal;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

// The skeletons of this class came from the following resource:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// Many classes are taken from this example

//Creates a journal entry screen with a list on the left and a pane for actions to act on the list on the right
public class JournalWithEntriesScreen extends JPanel implements ListSelectionListener {
    private static final Color babyBlue = new Color(204, 247, 255);
    private static final Color lavender = new Color(213, 165, 230);
    private static final Color pinky = new Color(242, 177, 232);

    private static final String JSON_STORE = "./data/travelJournal.json";

    //icons found from iconfinder.com
    private static final ImageIcon saveIcon = new ImageIcon("./data/save_icon.png");
    private static final ImageIcon loadIcon = new ImageIcon("./data/load_icon.png");

    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    private TravelJournal travelJournal;

    private JList entriesList;
    private DefaultListModel listModel;
    private static final String addEntry = "Add Journal Entry";
    private static final String deleteEntry = "Delete Journal Entry";
    private static final String viewEntry = "View Journal Entry";
    private static String save = "Save Journal";
    private static String load = "Load Previous Journal";

    private JButton addEntryButton;
    private JButton deleteEntryButton;
    private JButton viewEntryButton;

    private JLabel locationLabel;
    private JTextField location;
    private JLabel startDateLabel;
    private JTextField startDate;
    private JLabel endDateLabel;
    private JTextField endDate;

    //EFFECTS: Creates the journal entry screen: sets up all the components that make up the frame
    public JournalWithEntriesScreen(TravelJournal travelJournal) {
        super(new BorderLayout());
        this.travelJournal = travelJournal;

        initializeList();
        JScrollPane listScrollPane = new JScrollPane(entriesList);

        //add an EntryButton
        addEntryButton = designButtons(addEntry);
        AddListener addListener = new AddListener(addEntryButton);
        addEntryButton.setActionCommand(addEntry);
        addEntryButton.addActionListener(addListener);
        addEntryButton.setEnabled(false);

        fieldsSetup(addListener);

        //delete a journal entry button
        deleteEntryButton = designButtons(deleteEntry);
        deleteEntryButton.setActionCommand(deleteEntry);
        deleteEntryButton.addActionListener(new DeleteListener());
        deleteEntryButton.setEnabled(false);

        //view a journal entry
        viewEntryButton = designButtons(viewEntry);
        viewEntryButton.setActionCommand(viewEntry);
        viewEntryButton.addActionListener(new ViewListener());
        viewEntryButton.setEnabled(false);

        makeLabels();

        JPanel buttonPane = createButtonPane();
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.LINE_END);
    }

    //MODIFIES: this
    //EFFECTS: initializes the list of journal entries and sets the visual aspect of it
    private void initializeList() {
        listModel = new DefaultListModel();
        entriesList = new JList(listModel);
        entriesList.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 16));
        entriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //listModel.addElement("delete test");
        entriesList.setSelectedIndex(0);
        entriesList.addListSelectionListener(this);
        entriesList.setVisibleRowCount(10);
    }

    //MODIFIES: this
    //EFFECTS: sets up the label with its desired text
    private void makeLabels() {
        locationLabel = designLabels("Where did you go?");
        startDateLabel = designLabels("When is your trip's start date? (MM/DD/YY)");
        endDateLabel = designLabels("When is your trip's end date? (MM/DD/YY)");
    }

    //MODIFIES: this
    //EFFECTS: Sets up the text fields: creates them, adds listeners
    private void fieldsSetup(AddListener addListener) {
        location = designFields();
        location.addActionListener(addListener);
        location.getDocument().addDocumentListener(addListener);

        startDate = designFields();
        startDate.addActionListener(addListener);
        startDate.getDocument().addDocumentListener(addListener);

        endDate = designFields();
        endDate.addActionListener(addListener);
        endDate.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES: this
    //EFFECTS: creates and designs the button pane
    //Referred to: https://docs.oracle.com/javase/tutorial/uiswing/layout/flow.html
    private JPanel createButtonPane() {
        //Create a panel that uses Flowlayout
        JPanel buttonPane = new JPanel();
        buttonPane.setPreferredSize(new Dimension(375, 100));
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
        JPanel deleteViewPane = new JPanel();
        deleteViewPane.setBackground(pinky);
        deleteViewPane.setLayout(new FlowLayout());
        deleteViewPane.add(deleteEntryButton);
        deleteViewPane.add(viewEntryButton);

        buttonPane.add(deleteViewPane);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.setBackground(pinky);

        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(locationLabel);
        buttonPane.add(location);
        buttonPane.add(startDateLabel);
        buttonPane.add(startDate);
        buttonPane.add(endDateLabel);
        buttonPane.add(endDate);
        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(addEntryButton);
        return buttonPane;
    }

    //EFFECTS: creates, designs, then returns a label
    private JLabel designLabels(String text) {
        JLabel label = new JLabel(text);
        Font labelFont = new Font("Serif", Font.ITALIC, 18);
        label.setFont(labelFont);
        label.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        return label;
    }

    //EFFECTS: creates, designs, then returns a text field
    private JFormattedTextField designFields() {
        JFormattedTextField textField = new JFormattedTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBounds(20, 5, 100, 20);
        Font nameFont = new Font("Serif", Font.ITALIC, 25);
        textField.setFont(nameFont);
        textField.setBackground(babyBlue);
        textField.setBorder(new LineBorder(lavender, 8));
        textField.setVisible(true);
        return textField;
    }


    //EFFECTS: creates, designs, then returns a button
    private JButton designButtons(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 30));
        button.setBorder(new EmptyBorder(5, 5, 5, 5));
        button.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 14));
        return button;
    }

    //Effects: Creates, designs and returns the menu bar: adds menu items and make it look nice
    //Referred to: https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html#custom
    public JMenuBar createMenuBar() {
        //Creates the menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(new EmptyBorder(2, 2, 2, 2));
        JMenuItem menuItem;
        Font cursive = new Font("Serif", Font.ITALIC + Font.BOLD, 16);

        //Builds the menu
        JMenu menu = new JMenu("More Options");
        menu.setFont(cursive);
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("Menu that allows saving and loading.");
        menuBar.add(menu);
        menuBar.setOpaque(true);
        menuBar.setBackground(babyBlue);
        menuBar.setPreferredSize(new Dimension(200, 30));

        menuItem = new JMenuItem(save, saveIcon);
        menuItem.setFont(cursive);
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.addActionListener(new SaveLoadMenuListener());
        menu.add(menuItem);

        menuItem = new JMenuItem(load, loadIcon);
        menuItem.setFont(cursive);
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.addActionListener(new SaveLoadMenuListener());
        menu.add(menuItem);

        return menuBar;
    }

    //Creates a listener to handle the delete button
    class DeleteListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: If the delete button is clicked, delete the entry from the list
        @Override
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection so go ahead and remove what is selected.
            int index = entriesList.getSelectedIndex();

            int size = listModel.getSize();
            //No item in list, disable delete button
            if (size == 0) {
                deleteEntryButton.setEnabled(false);
            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }
                entriesList.setSelectedIndex(index);
                entriesList.ensureIndexIsVisible(index);
            }

            playSound();
            listModel.remove(index);
            travelJournal.deleteJournalEntry(index + 1);
        }
    }


    // Makes the listener for the viewEntry button
    class ViewListener implements ActionListener {

        //If the view button is pressed, launch a categories frame of that trip
        @Override
        public void actionPerformed(ActionEvent e) {
            playSound();
            int index = entriesList.getSelectedIndex();
            int size = listModel.getSize();
            if (size == 0) {
                viewEntryButton.setEnabled(false);
            }
            SouvenirEntry entryToView = travelJournal.getJournalEntry(index);
            createAndShowCategoriesScreen(entryToView);
        }
    }

    // If the add button is clicked, only add the entry to the
    // list if all three text fields are filled then reset the fields
    //This listener is shared by the text fields and the add button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        //MODIFIES: this
        //EFFECTS: creates a listener for the add button
        public AddListener(JButton button) {
            this.button = button;
        }

        //MODIFIES: this
        //EFFECTS: adds an entry to journal if all fields are filled and the add button is pressed.
        //Required by ActionListener.
        @Override
        public void actionPerformed(ActionEvent e) {
            String place = location.getText();
            String start = startDate.getText();
            String end = endDate.getText();

            //User didn't type in one of the fields
            if (place.equals("") || start.equals("") || end.equals("")) {
                location.requestFocusInWindow();
                startDate.requestFocusInWindow();
                endDate.requestFocusInWindow();
                location.selectAll();
                startDate.selectAll();
                endDate.selectAll();
                return;
            }

            int index = findIndex();

            SouvenirEntry entry = new SouvenirEntry(place, start, end);
            travelJournal.addJournalEntry(entry);
            playSound();
            listModel.insertElementAt(entry.titleOfEntry(), index);

            resetTextFields();

            //Select the new item and make it visible.
            entriesList.setSelectedIndex(index);
            entriesList.ensureIndexIsVisible(index);
        }

        //EFFECTS: returns the index of where to add a new entry
        private int findIndex() {
            int index = entriesList.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }
            return index;
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //MODIFIES: this
        //EFFECTS: Reset the text field.
        private void resetTextFields() {
            location.requestFocusInWindow();
            location.setText("");
            startDate.requestFocusInWindow();
            startDate.setText("");
            endDate.requestFocusInWindow();
            endDate.setText("");
        }

        //MODIFIES: this
        //EFFECTS: if button is not enabled, enable it
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        //MODIFIES: this
        //EFFECTS: checks to see if the text field is empty. If so, disable button
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //creates a listener to handle the menu bar items
    //Referred to: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/
    //MenuSelectionManagerDemoProject/src/components/MenuSelectionManagerDemo.java
    class SaveLoadMenuListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: If the menu item save is selected, save the journal
        // else if load is selected, load the previous journal.
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem source = (JMenuItem) (e.getSource());
            if (source.getText().equals(save)) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(travelJournal);
                    jsonWriter.close();
                } catch (FileNotFoundException fileError) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
            } else {
                try {
                    travelJournal = jsonReader.read();
                    listModel.clear();
                    for (int i = 0; i < travelJournal.getSize(); i++) {
                        listModel.addElement(travelJournal.getJournalEntry(i).titleOfEntry());
                    }
                } catch (IOException errorIO) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }
            }
        }
    }

    //EFFECTS: plays a delete/add sound
    //Referred to: https://www.codegrepper.com/code-examples/whatever/java+play+sound+file
    private void playSound() {
        //sound taken from freesound.org
        File soundFile = new File("./data/sparkle.wav");
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
            unsupportedAudioFileException.printStackTrace();
        } catch (LineUnavailableException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    //EFFECTS: creates and displays a categories frame for the selected journal entry
    private static void createAndShowCategoriesScreen(SouvenirEntry entry) {
        JFrame categoriesFrame = new JFrame(entry.titleOfEntry());
        CategoryScreen categories = new CategoryScreen(entry);
        categoriesFrame.add(categories);
        categoriesFrame.setPreferredSize(new Dimension(800, 400));

        //Display the window.
        categoriesFrame.pack();
        categoriesFrame.setVisible(true);
    }

    //This method is required by ListSelectionListener.
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (entriesList.getSelectedIndex() == -1) {
                //No selection, disable delete and view buttons.
                deleteEntryButton.setEnabled(false);
                viewEntryButton.setEnabled(false);
            } else {
                //Selection, enable delete and view buttons.
                deleteEntryButton.setEnabled(true);
                viewEntryButton.setEnabled(true);
            }
        }
    }
}
