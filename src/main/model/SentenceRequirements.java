package model;

import org.json.JSONObject;
import persistence.Writeable;

//Keeps the 2 strings of information that form the 2 types of Sentences
//Required for parsing
public class SentenceRequirements implements Writeable {
    private String actionItem;
    private String place;


    //EFFECTS: Constructs a sentence requirement list that save the actionItem and place
    public SentenceRequirements(String actionItem, String place) {
        this.actionItem = actionItem;
        this.place = place;
    }

    public String getAction() {
        return actionItem;
    }

    public String getPlace() {
        return place;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("action", actionItem);
        json.put("place", place);
        return json;
    }
}
