package persistence;

import model.TravelJournal;
import model.SouvenirEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//This class closely follows the JsonReader java class in the JsonSterilization Demo.
//Link to the demo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//Represents a reader that reads Saved Journal from JSOND data stored in file
public class JsonReader {
    private String fileSource;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        fileSource = source;
    }

    // EFFECTS: reads travel journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TravelJournal read() throws IOException {
        String jsonData = readFile(fileSource);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTravelJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TravelJournal from JSON object and returns it
    private TravelJournal parseTravelJournal(JSONObject jsonObject) {
        String travellerName = jsonObject.getString("name");
        TravelJournal travelJournal = new TravelJournal();
        travelJournal.setName(travellerName);
        addEntries(travelJournal, jsonObject);
        return travelJournal;
    }

    // MODIFIES: travelJournal
    // EFFECTS: parses Entries from JSON object and adds it to the Travel Journal
    private void addEntries(TravelJournal travelJournal, JSONObject jsonObject) {
        JSONArray jsonJournal = jsonObject.getJSONArray("journal entries");
        for (Object jsonEntry : jsonJournal) {
            JSONObject nextEntry = (JSONObject) jsonEntry;
            addEntry(travelJournal, nextEntry);

        }
    }

    // MODIFIES: travelJournal
    // EFFECTS: parses Entry Data from JSON object and adds it to Journal Entry
    private void addEntry(TravelJournal travelJournal, JSONObject jsonObject) {
        String startDate = jsonObject.getString("start date");
        String endDate = jsonObject.getString("end date");
        String location = jsonObject.getString("location");
        SouvenirEntry journalEntry = new SouvenirEntry(location, startDate, endDate);
        travelJournal.addJournalEntry(journalEntry);

        addEateriesCategory(journalEntry, jsonObject);
        addActsAndAttractsCategory(journalEntry, jsonObject);
    }

    // MODIFIES: journalEntry
    // EFFECTS: parses Eateries Category from JSON object and adds it to Journal Entry
    private void addEateriesCategory(SouvenirEntry journalEntry, JSONObject jsonObject) {
        JSONObject jsonEateriesList = jsonObject.getJSONObject("eateries list");
        addEaterySentences(journalEntry, jsonEateriesList);
    }


    // MODIFIES: journalEntry
    // EFFECTS: parses Activity/Attraction Category from JSON object
    private void addActsAndAttractsCategory(SouvenirEntry journalEntry, JSONObject jsonObject) {
        JSONObject jsonActsAndAttractsList = jsonObject.getJSONObject("attracts and acts list");
        addActsAndAttractsSentences(journalEntry, jsonActsAndAttractsList);
    }


    // MODIFIES: journalEntry
    // EFFECTS: parses list of eatery sentences from JSON object
    private void addEaterySentences(SouvenirEntry journalEntry, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("eateries info");
        for (Object jsonEateriesInfo : jsonArray) {
            JSONObject nextInfo = (JSONObject) jsonEateriesInfo;
            addEaterySentence(journalEntry, nextInfo);
        }
    }

    //MODIFIES: journalEntry
    // EFFECTS: parses list of eatery info from JSON object and adds it to the eatery category as sentences in entry
    private void addEaterySentence(SouvenirEntry journalEntry, JSONObject jsonObject) {
        String actionItem = jsonObject.getString("action");
        String place = jsonObject.getString("place");
        journalEntry.getEateriesCategory().addSentenceToList(actionItem, place);
    }

    //MODIFIES: journalEntry
    // EFFECTS: parses list of attractions/activities sentences from JSON object
    private void addActsAndAttractsSentences(SouvenirEntry journalEntry, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("attracts and acts info");
        for (Object jsonActsAndAttractInfo : jsonArray) {
            JSONObject nextInfo = (JSONObject) jsonActsAndAttractInfo;
            addActsAndAttractsSentence(journalEntry, nextInfo);
        }
    }

    //MODIFIES: journalEntry
    // EFFECTS: parses list of activities/attractions info from JSON object and adds it to the
    // activities/attractions category as sentences in entry
    private void addActsAndAttractsSentence(SouvenirEntry journalEntry, JSONObject jsonObject) {
        String actionItem = jsonObject.getString("action");
        String place = jsonObject.getString("place");
        journalEntry.getAttractsActsCategory().addSentenceToList(actionItem, place);
    }

}


