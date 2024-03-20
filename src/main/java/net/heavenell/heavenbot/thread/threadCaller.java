package net.heavenell.heavenbot.thread;

import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.heavenell.heavenbot.util.ChatTextParser;
import net.heavenell.heavenbot.util.ChatTextParser2;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

public class threadCaller implements ClientReceiveMessageEvents.Chat{

    private static boolean respond = false;
    private static boolean found = false;
    private Instant lastThreadCall = Instant.MIN;
    private static String chatContent;
    private static String threadName;
    private static String threadNumber = "0";
    private static String vtBoardLink = "https://boards.4channel.org/vt/thread/";
    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serialization.toJsonString(message).toLowerCase();
//        System.out.println(messageString);
        chatContent = ChatTextParser.parseText(messageString);
//        System.out.println(chatContent);

        if (chatContent.contains(".thread")) {
            String[] parts = chatContent.split("\\s+|\\.");
            Duration cooldownDuration = Duration.ofSeconds(6);
            Instant currentTime = Instant.now();
            Duration timeSinceLastThreadCall = Duration.between(lastThreadCall, currentTime);

            if (timeSinceLastThreadCall.compareTo(cooldownDuration) >= 0) {
                lastThreadCall = currentTime;
                if (parts.length >= 2) {
                    threadName = parts[parts.length - 2];
                    if (threadName.length() <= 200) {
                        // Start a new thread to make the HTTP request
                        Thread thread = new Thread(() -> {
                            link();
                            respond = true;
                            sendchat();
                        });
                        thread.start();
                    }
                }
            }
        }
    }

    public static void sendchat() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (respond && found) {
                client.player.networkHandler.sendChatMessage(vtBoardLink+threadNumber);
                respond = false;
                found = false;
            } else if (respond && !found) {
                client.player.networkHandler.sendChatMessage("Cannot find " + "/" + threadName + "/");
                respond = false;
            }
        });
    }

    public static void link() {
        try {
            // Define the URL you want to request
            String url = "https://a.4cdn.org/vt/catalog.json";

            // Create a URL object
            URL apiUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create a BufferedReader to read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Close the BufferedReader
                in.close();

                // Print the response
                ThreadNoParser(response.toString());
//                System.out.println("Response Body:\n" + response.toString());
            } else {
                System.err.println("Request failed with HTTP error code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void ThreadNoParser(String response) {
        String jsonData = response.toLowerCase(); // Your JSON data here
        try {
            // Initialize Gson
            Gson gson = new Gson();

            // Parse the JSON data into a JsonArray
            JsonArray pages = gson.fromJson(jsonData, JsonArray.class);

            // Iterate through the pages
            for (JsonElement pageElement : pages) {
                JsonObject pageObject = pageElement.getAsJsonObject();

                JsonArray threads = pageObject.getAsJsonArray("threads");

                for (JsonElement threadElement : threads) {
                    JsonObject threadObject = threadElement.getAsJsonObject();

                    // Check if "sub" field exists
                    if (threadObject.has("sub")) {
                        String sub = threadObject.get("sub").getAsString();
                        String no = threadObject.get("no").getAsString();

                        if (sub.contains("/"+threadName+"/")) {
                            System.out.println("Thread Number: " + no);
                            threadNumber = no;
                            found = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}