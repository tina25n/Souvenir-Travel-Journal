package ui;

import model.SouvenirEntry;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

// Designs the category frame as 2 panes: 1 for the activites and 1 for the eateries.
// Panes contain a list and a button pane
// Many of the components for the lists were taken from the JournalWithEntriesScreen, but this was used for the tabs:
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/
// uiswing/examples/components/TabbedPaneDemoProject/src/components/TabbedPaneDemo.java
public class CategoryScreen extends JPanel implements ListSelectionListener {
    private static final Color babyBlue = new Color(204, 247, 255);
    private static final Color lavender = new Color(213, 165, 230);
    private static final Color pinky = new Color(242, 177, 232);

    private JList eatsList;
    private JList actsList;
    private DefaultListModel eatsListModel;
    private DefaultListModel actsListModel;

    private JFormattedTextField food;
    private JFormattedTextField eatery;
    private JFormattedTextField activity;
    private JFormattedTextField attraction;

    private JButton deleteActsButton;
    private JButton deleteEatsButton;
    private JButton addActsButton;
    private JButton addEatsButton;

    private SouvenirEntry entry;

    //MODIFIES: this
    //EFFECTS: Creates the overall category screen: creates the tabbed pane and initializes all fields
    public CategoryScreen(SouvenirEntry journalEntry) {
        super(new GridLayout(1, 1));
        setBackground(lavender);

        entry = journalEntry;

        activity = designFields();
        attraction = designFields();
        food = designFields();
        eatery = designFields();

        JTabbedPane tabbedPane = new JTabbedPane();
        //designs the panes
        designEatsPane(tabbedPane);
        designActsPane(tabbedPane);

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setBackground(babyBlue);
        add(tabbedPane, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS:  designs the activity pane
    private void designActsPane(JTabbedPane tabbedPane) {
        //icons found from iconfinder.com
        ImageIcon actsAndAttracts = new ImageIcon("./data/activity_icon.png");
        JPanel actsPanel = new JPanel();
        actsPanel.setLayout(new BorderLayout());
        actsListModel = new DefaultListModel();
        actsList = new JList(actsListModel);
        initializeList(actsListModel, actsList);
        JScrollPane actsScrollPane = new JScrollPane(actsList);
        actsList.addListSelectionListener(this);
        actsPanel.add(actsScrollPane, BorderLayout.CENTER);

        for (int i = 0; i < entry.getAttractsActsCategory().getSize(); i++) {
            actsListModel.addElement(entry.getAttractsActsCategory().getSentenceInList(i));
        }

        deleteActsButton = designButtons("Delete");
        deleteActsButton.setActionCommand("DeleteAct");
        deleteActsButton.addActionListener(new ActsDeleteListener());

        addActsButton = designButtons("Add");
        addActsButton.setActionCommand("AddAct");
        addActsButton.addActionListener(new ActsAddListener(addActsButton));

        JPanel actsAction = addAndDeletePane("I", activity, attraction, deleteActsButton, addActsButton);
        actsPanel.add(actsAction,BorderLayout.PAGE_END);
        tabbedPane.addTab("Activities and Attractions", actsAndAttracts, actsPanel,
                "Shows eatery sentences");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
    }

    //MODIFIES: this
    //EFFECTS: designs the eateries pane
    private void designEatsPane(JTabbedPane tabbedPane) {
        //icons found from iconfinder.com
        ImageIcon eateries = new ImageIcon("./data/eatery_icon.png");
        JPanel eatsPanel = new JPanel();
        eatsPanel.setLayout(new BorderLayout());
        eatsListModel = new DefaultListModel();
        eatsList = new JList(eatsListModel);
        initializeList(eatsListModel, eatsList);
        JScrollPane eatsScrollPane = new JScrollPane(eatsList);
        eatsList.addListSelectionListener(this);
        eatsPanel.add(eatsScrollPane, BorderLayout.CENTER);

        for (int i = 0; i < entry.getEateriesCategory().getSize(); i++) {
            eatsListModel.addElement(entry.getEateriesCategory().getSentenceInList(i));
        }

        deleteEatsButton = designButtons("Delete");
        deleteEatsButton.setActionCommand("DeleteEat");
        deleteEatsButton.addActionListener(new EatsDeleteListener());

        addEatsButton = designButtons("Add");
        addEatsButton.setActionCommand("AddEat");
        addEatsButton.addActionListener(new EatsAddListener(addEatsButton));

        JPanel eatsAction = addAndDeletePane("I had", food, eatery, deleteEatsButton, addEatsButton);
        eatsPanel.add(eatsAction,BorderLayout.PAGE_END);
        tabbedPane.addTab("Food and Eateries", eateries, eatsPanel,
                "Shows activity sentences");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
    }

    //MODIFIES: this
    //EFFECTS: initializes the list and sets the visual aspect of it
    private void initializeList(DefaultListModel listModel, JList list) {
        list.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 16));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setPreferredSize(new Dimension(500, 200));
        list.setSelectedIndex(0);
        list.setVisibleRowCount(10);
    }

    //MODIFIES: this
    //EFFECTS: designs the bottom pane where user can add or delete events
    private JPanel addAndDeletePane(String first, JFormattedTextField actionItem, JFormattedTextField place,
                                    JButton delButton, JButton addButton) {
        JPanel addAndDel = new JPanel();
        addAndDel.setLayout(new FlowLayout());
        addAndDel.setBackground(pinky);
        JLabel sentencePart1 = designLabels(first);
        JLabel sentencePart2 = designLabels("at");
        JLabel period = designLabels(".");

        addAndDel.add(delButton);
        addAndDel.add(new JSeparator(SwingConstants.VERTICAL));
        addAndDel.add(sentencePart1);
        addAndDel.add(actionItem);
        addAndDel.add(sentencePart2);
        addAndDel.add(place);
        addAndDel.add(period);
        addAndDel.add(new JSeparator(SwingConstants.VERTICAL));
        addAndDel.add(addButton);

        return addAndDel;
    }

    //EFFECTS: creates, designs, then returns a button
    private JButton designButtons(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(80, 40));
        button.setBorder(new EmptyBorder(5, 5, 5, 5));
        button.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 14));
        return button;
    }

    //EFFECTS: creates, designs, then returns a text field
    private JFormattedTextField designFields() {
        JFormattedTextField textField = new JFormattedTextField();
        textField.setPreferredSize(new Dimension(200, 50));
        textField.setBounds(20, 5, 100, 50);
        Font nameFont = new Font("Serif", Font.ITALIC, 30);
        textField.setFont(nameFont);
        textField.setBackground(babyBlue);
        textField.setBorder(new LineBorder(lavender, 8));
        textField.setVisible(true);
        return textField;
    }

    //MODIFIES: this
    //EFFECTS: creates, designs, then returns a label
    private JLabel designLabels(String text) {
        JLabel label = new JLabel(text);
        Font labelFont = new Font("Serif", Font.ITALIC, 18);
        label.setFont(labelFont);
        label.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        return label;
    }

    //creates a listener to handle the activity delete button
    class ActsDeleteListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: If the delete button is clicked, delete the entry from the list
        @Override
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection so go ahead and remove what is selected.
            int index = actsList.getSelectedIndex();

            int size = actsListModel.getSize();
            //No item in list, disable delete button
            if (size == 0) {
                deleteActsButton.setEnabled(false);
            } else { //Select an index.
                if (index == actsListModel.getSize()) {
                    //removed item in last position
                    index--;
                }
                actsList.setSelectedIndex(index);
                actsList.ensureIndexIsVisible(index);
            }
            playSound();
            actsListModel.remove(index);
            entry.getAttractsActsCategory().removeSentenceFromList(index);
        }
    }

    //creates a listener to handle the eatery delete button
    class EatsDeleteListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: If the delete button is clicked, delete the entry from the list
        @Override
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection so go ahead and remove what is selected.
            int index = eatsList.getSelectedIndex();

            int size = eatsListModel.getSize();
            //No item in list, disable delete button
            if (size == 0) {
                deleteEatsButton.setEnabled(false);
            } else { //Select an index.
                if (index == eatsListModel.getSize()) {
                    //removed item in last position
                    index--;
                }
                eatsList.setSelectedIndex(index);
                eatsList.ensureIndexIsVisible(index);
            }
            playSound();
            eatsListModel.remove(index);
            entry.getEateriesCategory().removeSentenceFromList(index + 1);
        }
    }

    // if the add button is clicked, only add the entry to the
    // list if all three text fields are filled then reset the fields
    //This listener is shared by the text fields and the add button.
    class ActsAddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        //MODIFIES: this
        //EFFECTS: creates a listener for the add button
        public ActsAddListener(JButton button) {
            this.button = button;
        }

        //MODIFIES: this
        //EFFECTS: adds an entry to journal if all fields are filled and the add button is pressed.
        //Required by ActionListener.
        @Override
        public void actionPerformed(ActionEvent e) {
            String act = activity.getText();
            String attract = attraction.getText();

            //User didn't type in one of the fields
            if (act.equals("") || attract.equals("")) {
                activity.requestFocusInWindow();
                attraction.requestFocusInWindow();
                activity.selectAll();
                attraction.selectAll();
                return;
            }

            int index = findIndex();

            entry.getAttractsActsCategory().addSentenceToList(act,attract);
            playSound();
            actsListModel.insertElementAt(entry.getAttractsActsCategory().getSentenceInList(
                    entry.getAttractsActsCategory().getSize() - 1), index);

            resetTextFields();

            //Select the new item and make it visible.
            actsList.setSelectedIndex(index);
            actsList.ensureIndexIsVisible(index);
        }

        //EFFECTS: returns the index of where to add a new entry
        private int findIndex() {
            int index = actsList.getSelectedIndex(); //get selected index
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
            activity.requestFocusInWindow();
            activity.setText("");
            attraction.requestFocusInWindow();
            attraction.setText("");
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

    //if the add button is clicked, only add the entry to the
    // list if all three text fields are filled then reset the fields
    //This listener is shared by the text fields and the add button.
    class EatsAddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        //MODIFIES: this
        //EFFECTS: creates a listener for the add button
        public EatsAddListener(JButton button) {
            this.button = button;
        }

        //MODIFIES: this
        //EFFECTS: adds an entry to journal if all fields are filled and the add button is pressed.
        //Required by ActionListener.
        @Override
        public void actionPerformed(ActionEvent e) {
            String eaten = food.getText();
            String place = eatery.getText();

            //User didn't type in one of the fields
            if (eaten.equals("") || place.equals("")) {
                food.requestFocusInWindow();
                eatery.requestFocusInWindow();
                food.selectAll();
                eatery.selectAll();
                return;
            }

            int index = findIndex();

            entry.getEateriesCategory().addSentenceToList(eaten,place);
            playSound();
            eatsListModel.insertElementAt(entry.getEateriesCategory().getSentenceInList(
                    entry.getEateriesCategory().getSize() - 1), index);

            resetTextFields();

            //Select the new item and make it visible.
            eatsList.setSelectedIndex(index);
            eatsList.ensureIndexIsVisible(index);
        }

        //EFFECTS: returns the index of where to add a new entry
        private int findIndex() {
            int index = eatsList.getSelectedIndex(); //get selected index
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
            food.requestFocusInWindow();
            food.setText("");
            eatery.requestFocusInWindow();
            eatery.setText("");
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

    //This method is required by ListSelectionListener.
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            deleteEatsButton.setEnabled(eatsList.getSelectedIndex() != -1);
            deleteActsButton.setEnabled(actsList.getSelectedIndex() != -1);
        }
    }
}
