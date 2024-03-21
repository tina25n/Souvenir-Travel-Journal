//Note: the conversion of index from an int to a string was realized from help with this source:
// https://www.geeksforgeeks.org/different-ways-for-integer-to-string-conversions-in-java/

package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.LinkedList;


//This class contains a list of sentences in the format "I "ACTIVITY" at PLACE" in past tense
// which will be displayed in the Entry of the Journal
public class AttractionsAndActivitiesCategory extends Categories implements Writeable {

    //MODIFIES: this, indexNum, nextIndexNum
    //EFFECTS: sets up a linked list for all the activities done at attractions sentences
    //         assigns initial values for indexNum and nextIndexNum
    public AttractionsAndActivitiesCategory() {
        initialize();
    }

    //MODIFIES: this, activity, attraction, and indexNum
    //EFFECTS: Forms and adds the string that will be placed into the Attractions/Activities category
    //         Changes activity and attraction to the inputted strings
    //         Assigns a unique entry number to the event
    //         Adds activity and attraction to attractAndActInfo
    @Override
    public void addSentenceToList(String activity, String attraction) {
        int index = setIndexNum();
        super.getList().add(index + ": I " + activity + " at " + attraction + ".");
        super.setActionItem(activity);
        super.setPlace(attraction);
        SentenceRequirements newInfo = new SentenceRequirements(activity, attraction);
        super.getInfo().add(newInfo);
        EventLog.getInstance().logEvent(new Event("Added attraction: (" + activity + "," + attraction + ")"));
    }

    //EFFECTS: converts attractAndAct info list to a jsonObject (that contains jsonArray)
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("attracts and acts info", super.getInfo());
        return json;
    }

}
