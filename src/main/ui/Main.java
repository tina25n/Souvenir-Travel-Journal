package ui;

import java.io.FileNotFoundException;

//Main class used to run the journal UI
public class Main {
    public static void main(String[] args) {
        try {
            new SouvenirJournal();
        } catch (FileNotFoundException fileError) {
            System.out.println("File Not Found! Unable to run Application :(");
        }
    }
}


