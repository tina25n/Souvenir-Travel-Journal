package model;

import java.util.LinkedList;

public abstract class Categories {
    private int nextIndexNum;
    private String actionItem;
    private String place;
    private LinkedList<String> sentenceList;
    private LinkedList<SentenceRequirements> info = new LinkedList<>();
    private int indexNum;

    // initializes the categories by setting the index numbers and the list for sentences
    public void initialize() {
        sentenceList = new LinkedList<>();
        nextIndexNum = 1;
        indexNum = 0;
    }

    //MODIFIES: this, actionItem, place, info and indexNum
    //EFFECTS: Forms and adds the string that will be placed into the category
    //         Changes actionItem and place to the inputted strings
    //         Assigns a unique entry number to the event
    //         Adds actionItem and place to info
    public void addSentenceToList(String actionItem, String place) {
        int index = setIndexNum();
        sentenceList.add(index + ": I had " + actionItem + " at " + place + ".");
        this.actionItem = actionItem;
        this.place = place;
        SentenceRequirements newInfo = new SentenceRequirements(actionItem, place);
        info.add(newInfo);
        EventLog.getInstance().logEvent(new Event("Added eatery: (" + actionItem + "," + place + ")"));
    }

    //REQUIRES: index must be a positive integer
    //MODIFIES: this and info
    //EFFECTS: Finds the event with the index and removes it from the list
    //         Also removes the event information from the info list
    public boolean removeSentenceFromList(int index) {
        for (int i = 0; i < sentenceList.size(); i++) {
            if (sentenceList.get(i).contains(String.valueOf(index))) {
                EventLog.getInstance().logEvent(new Event("Removed: ("
                        + info.get(i).getAction() + "," + info.get(i).getPlace()  + ")"));
                sentenceList.remove(i);
                info.remove(i);
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: assigns the indexNum to be the nextIndexNum and returns it
    public int setIndexNum() {
        indexNum = nextIndexNum++;
        return indexNum;
    }

    //EFFECTS: Returns the size of the attractions/activities list
    public int getSize() {
        return sentenceList.size();
    }

    //REQUIRES: index is not null and a positive integer
    //EFFECTS: returns the sentence in the attractions and activities list at an index
    public String getSentenceInList(int index) {
        return sentenceList.get(index);
    }

    //getters
    public String getActionItem() {
        return actionItem;
    }

    public String getPlace() {
        return place;
    }

    public int getIndexNum() {
        return indexNum;
    }

    public LinkedList<SentenceRequirements> getInfo() {
        return info;
    }

    public LinkedList<String> getList() {
        return sentenceList;
    }

    //setters
    public void setPlace(String place) {
        this.place = place;
    }

    public void setActionItem(String actionItem) {
        this.actionItem = actionItem;
    }

}
