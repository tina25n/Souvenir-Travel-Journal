/*
 * This file closely models the Account class in the AccountNotRobust project (TellerApp)
 * The link to the source is as follows: https://github.students.cs.ubc.ca/CPSC210/TellerApp
 */

package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

//Represents a journal entry with the owner name, date of trip, and location visited.
//This class includes the two main categories of eateries and activity/attraction to display
//It also keeps track of the journal entries
public class SouvenirEntry implements Writeable {
    private static int nextJournalEntryNum = 1;     // Tracks next Entry Number
    private int journalEntryNum;                    //Current Entry number


    private String locationTravelled;               //Saves the location of the trip
    private String startDate;                       //Trip start date
    private String endDate;                         //Trip end date


    //LinkedList for separate categories in journal entry
    private EateriesCategory eateriesList;
    private AttractionsAndActivitiesCategory attractsAndActsList;

    //REQUIRES: endDate must be after startDate
    //          endDate and startDate must be valid and in format of MM/DD/YYYY
    //EFFECTS: Constructs a souvenir entry with the date and location
    //          Journal Entry Number is set to a unique, positive integer value
    public SouvenirEntry(String location, String startDate, String endDate) {
        journalEntryNum = nextJournalEntryNum++;
        eateriesList = new EateriesCategory();
        attractsAndActsList = new AttractionsAndActivitiesCategory();
        locationTravelled = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //EFFECTS: Returns the title of the journal entry
    public String titleOfEntry() {
        return (journalEntryNum + " My trip to " + locationTravelled + " (" + startDate + "-" + endDate + ")");
    }

    //setter
    public void setNextJournalEntryNum(int value) {
        nextJournalEntryNum = value;
    }

    //getters
    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return locationTravelled;
    }

    public int getJournalEntryNum() {
        return journalEntryNum;
    }

    public EateriesCategory getEateriesCategory() {
        return eateriesList;
    }

    public AttractionsAndActivitiesCategory getAttractsActsCategory() {
        return attractsAndActsList;
    }

    //EFFECTS: converts journal entry info (string: start date, end date, location;
    //         jsonArray: eateries list, attracts/acts list) to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("start date", startDate);
        json.put("end date", endDate);
        json.put("location", locationTravelled);
        json.put("eateries list", eateriesList.toJson());
        json.put("attracts and acts list", attractsAndActsList.toJson());
        return json;
    }


}
