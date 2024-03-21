package model;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

//This is the overall journal class. It contains and manages all the entries.
public class TravelJournal implements Writeable {
    private LinkedList<SouvenirEntry> journal;
    private String name;

    //EFFECTS: Creates an empty journal Linked List to store the entries and sets name to 1
    public TravelJournal() {
        journal = new LinkedList<>();
    }

    //EFFECTS: Forms and Returns the string that welcomes the user
    public String makeWelcomeString(String name) {
        this.name = name;
        return ("Welcome to " + name + "'s Travel Journal!");
    }

    //REQUIRES: SouvenirEntry is not null
    //MODIFIES: this
    //EFFECTS: Adds the souvenir entry to the journal
    public void addJournalEntry(SouvenirEntry entry) {
        journal.add(entry);
        EventLog.getInstance().logEvent(new Event("Added journal entry for: "
                + entry.titleOfEntry()));
    }

    //REQUIRES: entryNumToRemove must be a positive integer
    //MODIFIES: this
    //EFFECTS: finds and deletes the journal entry with the ID entryNumToRemove from the journal list
    //         returns true if the entry is found and removed and returns false otherwise
    public boolean deleteJournalEntry(int entryNumToRemove) {
        for (int i = 0; i < journal.size(); i++) {
            if (journal.get(i).getJournalEntryNum() == entryNumToRemove) {
                EventLog.getInstance().logEvent(new Event("Deleted journal entry for: "
                        + journal.get(i).titleOfEntry()));
                journal.remove(i);
                return true;
            }
        }
        return false;
    }

    //REQUIRES: journalEntryNum must be a positive integer
    //MODIFIES: this
    //EFFECTS: looks for and returns the index with the matching journal ID number if found
    //         otherwise, returns -1
    public int findJournalEntry(int journalEntryNum) {
        for (int i = 0; i < journal.size(); i++) {
            if (journal.get(i).getJournalEntryNum() == journalEntryNum) {
                return i;
            }
        }

        return -1;
    }

    //getters

    //REQUIRES: journalEntryNum must be a positive integer
    //EFFECTS: returns the entry at an index journalEntryNum
    public SouvenirEntry getJournalEntry(int journalEntryNum) {
        return journal.get(journalEntryNum);
    }

    //EFFECTS: returns the size of the journal list
    public int getSize() {
        return journal.size();
    }

    public String getName() {
        return name;
    }

    //setter
    public void setName(String newName) {
        name = newName;
    }

    //EFFECTS: converts journal to Json Object (string: name; JsonArray: journal entries)
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("journal entries", entriesToJson());
        return json;
    }

    //EFFECTS:
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SouvenirEntry journalEntry : journal) {
            jsonArray.put(journalEntry.toJson());
        }

        return jsonArray;
    }
}
