//Note: the conversion of index from an int to a string was realized from help with this source:
// https://www.geeksforgeeks.org/different-ways-for-integer-to-string-conversions-in-java/

package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.LinkedList;
import java.util.List;

//Potential to add an abstract class "Category" and have AAA and Eateries extend it
//This class contains a list of sentences in the format "I HAD FOOD at PLACE" in past tense
// which will be displayed in the Entry of the Journal
public class EateriesCategory extends Categories implements Writeable {

    //MODIFIES: this, indexNum, nextIndexNum
    //EFFECTS: sets up a linked list for all the eatery sentences
    //         assigns initial values for indexNum and nextIndexNum
    public EateriesCategory() {
        initialize();
    }

    //EFFECTS: converts eatery info list to a jsonObject (that contains jsonArray)
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("eateries info", super.getInfo());
        return json;
    }

}
