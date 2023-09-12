package net.heavenell.heavenbot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChatTextParser2 {
    public static String parseText(String messageString) {
        JsonElement jsonElement = JsonParser.parseString(messageString);

        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("with") && jsonObject.get("with").isJsonArray()) {
                JsonArray withArray = jsonObject.getAsJsonArray("with");
                if (!withArray.isEmpty()) {
                    JsonElement lastElement = withArray.get(withArray.size() - 1);
                    if (lastElement.isJsonObject() && lastElement.getAsJsonObject().has("text")) {
                        String textValue = lastElement.getAsJsonObject().get("text").getAsString();
                        return textValue;
                    }
                }
            }
        }
        return "";
    }
}