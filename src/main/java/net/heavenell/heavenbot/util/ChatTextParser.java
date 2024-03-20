package net.heavenell.heavenbot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChatTextParser {
    public static String parseText(String messageString) {
        JsonElement jsonElement = JsonParser.parseString(messageString);

        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("with") && jsonObject.get("with").isJsonArray()) {
                JsonArray withArray = jsonObject.getAsJsonArray("with");

                if (!withArray.isEmpty()) {
                    JsonElement lastElement = withArray.get(withArray.size() - 1);
                    if (lastElement.isJsonObject() && lastElement.getAsJsonObject().has("extra")
                            && lastElement.getAsJsonObject().get("extra").isJsonArray()) {
                        JsonArray extraArray = lastElement.getAsJsonObject().getAsJsonArray("extra");

                        StringBuilder textBuilder = new StringBuilder(); // Initialize a StringBuilder to concatenate the "text" values

                        for (JsonElement extraElement : extraArray) {
                            if (extraElement.isJsonObject() && extraElement.getAsJsonObject().has("text")) {
                                String textValue = extraElement.getAsJsonObject().get("text").getAsString();
                                textBuilder.append(textValue); // Append the "text" value to the StringBuilder
                            }
                        }

                        return textBuilder.toString(); // Convert the StringBuilder to a String and return
                    }
                }
            }
        }
        return "";
    }
}
