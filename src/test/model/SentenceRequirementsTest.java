package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentenceRequirementsTest {
    private SentenceRequirements requirements;
    private String actionItem;
    private String place;

    @BeforeEach
    public void initialize() {
        actionItem = "food";
        place ="park";
        requirements= new SentenceRequirements(actionItem, place);
    }

    @Test
    public void testInitialization() {
        assertEquals(requirements.getAction(), actionItem);
        assertEquals(requirements.getPlace(), place);
    }

    @Test
    public void testToJson() {
        JSONObject jsonReq =requirements.toJson();

        assertEquals(jsonReq.getString("action"), actionItem);
        assertEquals(jsonReq.getString("place"), place);
    }

}
