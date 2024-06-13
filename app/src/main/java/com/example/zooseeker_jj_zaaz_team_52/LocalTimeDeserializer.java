package com.example.zooseeker_jj_zaaz_team_52;

import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializationContext;

import java.lang.reflect.Type;
import java.time.LocalTime;

/**
 * This class is used to deserialize the LocalTime object which is intended to be stored in the backend database.
 */
public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {
    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            int hour = json.getAsJsonObject().get("hour").getAsInt();
            int minute = json.getAsJsonObject().get("minute").getAsInt();
            return LocalTime.of(hour, minute);
        } else {
            throw new JsonParseException("Expected a JSON object for LocalTime");
        }
    }
}
