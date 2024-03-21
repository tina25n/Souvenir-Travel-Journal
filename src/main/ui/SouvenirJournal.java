package ui;

//This closely follows the TellerApp example (TellerApp.java) for help with the scanner
//Link to source: https://github.students.cs.ubc.ca/CPSC210/TellerApp

//For Assistance with Files, certain classes followed the WorkRoomApp.
//Link to source:

import model.SouvenirEntry;
import model.TravelJournal;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//Travel Journal Application
//This contains all the UI to run the app in the terminal!
public class SouvenirJournal {
    private static final String JSON_STORE = "./data/traveljournal.json";
    private TravelJournal tinaJournal;
    private String name;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: Runs the journal app
    public SouvenirJournal() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runJournal();
    }

    //MODIFIES: this
    //EFFECTS: main function that keeps the journal running
    //         it asks for user's name then displays welcome message before allowing user to view a menu
    //         to start journaling
    //         Ends if user selects q (quit) from the journal menu
    private void runJournal() {
        boolean keepGoing = true;
        String command;

        initializeJournal();
        name = askName();

        while (keepGoing) {
            displayWelcome(name);
            displayJournalMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processJournalCommand(command);
            }
        }
    }


    //EFFECTS: Asks the user for his/her name and returns it
    public String askName() {
        System.out.println("Hello! What is your name?");
        return input.next();
    }


    //EFFECTS: Displays and forms the welcome message to user
    public void displayWelcome(String name) {
        System.out.println("\n" + tinaJournal.makeWelcomeString(name));
    }

    //MODIFIES: this
    //EFFECTS: processes the users command from the journal menu
    private void processJournalCommand(String command) {
        switch (command) {
            case "v":
                displayEntriesList();
                selectSingleJournalEntry();
                break;
            case "a":
                addJournalEntry();
                break;
            case "d":
                deleteJournalEntry();
                break;
            case "s" :
                saveJournal();
                break;
            case "l":
                loadJournal();
                break;
            default:
                System.out.println("\nI'm sorry, I do not understand! Please try again.");
                break;
        }
    }


    //EFFECTS: prints a series of statements that form the journal menu
    private void displayJournalMenu() {
        System.out.println("\nPlease Select From the Following:");
        System.out.println("\tv -> View All Journal Entries");
        System.out.println("\ta -> Add a Journal Entry");
        System.out.println("\td -> Delete a Journal Entry");
        System.out.println("\ts -> Save Journal to file");
        System.out.println("\tl -> Load Journal from file");
        System.out.println("\tq -> Quit Journaling :(");
    }

    //MODIFIES: this
    //EFFECTS: processes the users command from the entry menu
    private void processEntryCommand(String command, int journalNumToView) {
        if (command.equals("b")) {
            return;
        } else if (command.equals("ae")) {
            addEatery(journalNumToView);
        } else if (command.equals("aa")) {
            addActivityAndAttraction(journalNumToView);
        } else if (command.equals("re")) {
            removeEatery(journalNumToView);
        } else if (command.equals("ra")) {
            removeActivityAndAttraction(journalNumToView);
        } else {
            System.out.println("I'm sorry, I do not understand! Please Try Again.");
            String newCommand = input.next();
            processEntryCommand(newCommand, journalNumToView);
        }
        System.out.println("\nHere's the updated journal entry :)");
        displayCategories(journalNumToView);
        displayEntriesMenu();
        String nextCommand = input.next();

        if (!nextCommand.equals("b")) {
            processEntryCommand(nextCommand, journalNumToView);
        }
    }

    //EFFECTS: prints a series of statements that form the entries menu
    private void displayEntriesMenu() {
        System.out.println("\nAnything you would like to do with this Entry?");
        System.out.println("\tae -> Add an Eatery");
        System.out.println("\tre-> Remove an Eatery");
        System.out.println("\taa -> Add an Activity at an Attraction");
        System.out.println("\tra -> Remove an Activity at an Attraction");
        System.out.println("\tb-> Go Back to Entries List");
    }

    //MODIFIES: this
    //EFFECTS: initializes the journal
    private void initializeJournal() {
        tinaJournal = new TravelJournal();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    //EFFECTS: prints a list of all the entries in the journal
    private void displayEntriesList() {
        for (int i = 0; i < tinaJournal.getSize(); i++) {
            System.out.println(tinaJournal.getJournalEntry(i).titleOfEntry());
        }
    }

    //MODIFIES: this
    //EFFECTS: asks the user if they would like to view a specific entry
    //         if yes, it will display the categories, then the entry menu, process the command and return true
    //         if no, it will return false
    //         if the answer is none of the above, it will repeat the question
    private boolean selectSingleJournalEntry() {
        System.out.println("\nWould you like to view an entry? Please type yes or no.");
        String confirm = input.next();

        if (confirm.equals("yes")) {
            System.out.println("\nWhat is the number of the entry you would like to view?");
            int journalEntryNum = input.nextInt();
            if (getIndex(journalEntryNum) != -1) {
                displayCategories(journalEntryNum);

                displayEntriesMenu();
                String entryCommand = input.next();
                processEntryCommand(entryCommand, journalEntryNum);

                return true;
            } else {
                System.out.println("That is an invalid response!");
                selectSingleJournalEntry();
            }
        } else if (confirm.equals("no")) {
            return false;
        } else {
            System.out.println("That is an invalid response!");
            selectSingleJournalEntry();
        }
        return false;
    }


    //MODIFIES: this
    //EFFECTS: Asks questions about location and trip dates to make a new journal entry
    private void addJournalEntry() {
        System.out.println("\nWhere did you go?");
        String location = input.next();
        System.out.println("\nWhen did you go? Please enter the date in the format MM/DD/YY.");
        String startDate = input.next();
        System.out.println("\nWhen did your trip end/When is it ending? Please enter the date in the format MM/DD/YY.");
        String endDate = input.next();

        SouvenirEntry newEntryToAdd = new SouvenirEntry(location, startDate, endDate);
        tinaJournal.addJournalEntry(newEntryToAdd);

        System.out.println("Your trip was successfully added!");
    }


    //MODIFIES: this
    //EFFECTS: Asks user for the number of the entry to delete and removes the entry from the journal
    private void deleteJournalEntry() {
        System.out.println("What is the number of the entry you would like to delete?");
        int numToDelete = input.nextInt();

        tinaJournal.deleteJournalEntry(numToDelete);
    }

    //REQUIRES: entryNum must be a positive integer
    //          it is the index of the list element so needs to be less than the size of the list
    //MODIFIES: this
    //EFFECTS: prints out the title of the entry, the title of the 2 categories and the respective sentences
    //         in the lists
    private void displayCategories(int entryNum) {
        int index = getIndex(entryNum);

        System.out.println(tinaJournal.getJournalEntry(index).titleOfEntry());

        System.out.println("\nEATERIES");
        int sizeOfEateriesList = tinaJournal.getJournalEntry(index).getEateriesCategory().getSize();
        for (int i = 0; i < sizeOfEateriesList; i++) {
            System.out.println(
                    tinaJournal.getJournalEntry(index).getEateriesCategory().getSentenceInList(i));
        }

        System.out.println("\nACTIVITIES AND ATTRACTIONS");
        int sizeOfAAndAList =
                tinaJournal.getJournalEntry(index).getAttractsActsCategory().getSize();
        for (int i = 0; i < sizeOfAAndAList; i++) {
            System.out.println(
                    tinaJournal.getJournalEntry(index).getAttractsActsCategory().getSentenceInList(i));
        }
    }

    //REQUIRES: journalNum must be a positive integer
    //          it is the index of the list element so needs to be less than the size of the list
    //MODIFIES: this
    //EFFECTS: asks the user for the food/drink and the eatery to form the sentence then add it
    //         to the eatery category in the journalNum entry
    private void addEatery(int journalNum) {
        int index = getIndex(journalNum);

        System.out.println("What did you eat/drink?");
        String food = input.next();
        System.out.println("Where did you eat/drink?");
        String eatery = input.next();

        tinaJournal.getJournalEntry(index).getEateriesCategory().addSentenceToList(food, eatery);
        System.out.println("Your event was successfully logged!");
    }

    //REQUIRES: journalNum must be a positive integer
    //          it is the index of the list element so needs to be less than the size of the list
    //MODIFIES: this
    //EFFECTS: asks the user for the activity and the attraction to form the sentence then add it
    //         to the activity/attraction category in the journalNum entry
    private void addActivityAndAttraction(int journalNum) {
        int index = getIndex(journalNum);

        System.out.println("What did you do?");
        String act = input.next();
        System.out.println("Where did you go?");
        String attract = input.next();

        tinaJournal.getJournalEntry(index).getAttractsActsCategory().addSentenceToList(act, attract);
        System.out.println("Your event was successfully logged!");

    }

    //REQUIRES: journalNum must be a positive integer
    //          it is the index of the list element so needs to be less than the size of the list
    //MODIFIES: this
    //EFFECTS: asks the user for the index of the event the user would like removed and removes it
    //        from the activity/attraction list
    private void removeActivityAndAttraction(int journalNum) {
        int index = getIndex(journalNum);
        System.out.println("Please enter the number (on the left) of the event you would like to remove.");
        int removeIndex = input.nextInt();

        boolean removed;
        removed = tinaJournal.getJournalEntry(index).getAttractsActsCategory().removeSentenceFromList(removeIndex);
        if (removed) {
            System.out.println("Your event was successfully removed!");
        }
    }

    //REQUIRES: journalNum must be a positive integer
    //          it is the index of the list element so needs to be less than the size of the list
    //MODIFIES: this
    //EFFECTS: asks the user for the index of the event the user would like removed and removes it
    //        from the eatery list
    private void removeEatery(int journalNum) {
        int index = getIndex(journalNum);

        System.out.println("Please enter the number (on the left) of the event you would like to remove.");
        int removeIndex = input.nextInt();

        boolean removed;
        removed = tinaJournal.getJournalEntry(index).getEateriesCategory().removeSentenceFromList(removeIndex);
        if (removed) {
            System.out.println("Your event was successfully removed!");
        }
    }

    //EFFECTS: Finds the index of the journal entry with a valid journal ID and returns the index
    //         If the ID is not valid, ask to re-enter another ID number
    private int getIndex(int entryNum) {
        int index = tinaJournal.findJournalEntry(entryNum);

        while (index == -1) {
            System.out.println("Invalid Input. Please try again.");
            index = input.nextInt();
        }
        return index;
    }

    //EFFECTS: Saves Journal to a File
    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(tinaJournal);
            jsonWriter.close();
            System.out.println("Successfully Saved to " + JSON_STORE);
        } catch (FileNotFoundException fileError) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    //EFFECTS: Loads Journal from a File
    private void loadJournal() {
        try {
            tinaJournal = jsonReader.read();
            System.out.println("Loaded " + name + "'s Journal from " + JSON_STORE);
        } catch (IOException errorIO) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}

