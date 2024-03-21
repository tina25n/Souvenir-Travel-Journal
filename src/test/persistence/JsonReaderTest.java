package persistence;

import model.TravelJournal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//This class closely models the JsonReaderTest class in the JsonSterilization Demo.
//Link to the demo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TravelJournal testJournal = reader.read();
            fail("IOException was expected.");
        } catch (IOException error) {
            //pass
        }
    }

    @Test
    public void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            TravelJournal testJournal = reader.read();
            assertEquals(0, testJournal.getSize());

        } catch (IOException error) {
            fail("IOException was not expected.");
        }

    }

    @Test
    public void testReaderJournal() {
        JsonReader reader = new JsonReader("./data/testReaderJournal.json");
        try {
            TravelJournal testJournal = reader.read();
            assertEquals(testJournal.getName(), "Jack");
            assertEquals(testJournal.getSize(), 3);

        } catch (IOException error) {
            fail("IOException was not expected.");
        }

    }

}
