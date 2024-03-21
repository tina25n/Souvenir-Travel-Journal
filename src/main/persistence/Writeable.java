package persistence;

import org.json.JSONObject;

//This was taken from the JsonSerializationDemo
//Link to source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
