package ch.zhaw.nlptutorials.openaijava;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LMOpenAI {
    private static final Logger LOGGER = LoggerFactory.getLogger(LMOpenAI.class);

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static String openai(List<Utterance> messages) {
        return LMOpenAI.openai(messages, 1, 1);
    }

    public static String openai(List<Utterance> message, float temperature, float topP) {
        try {

            JsonObject payload = new JsonObject();
            payload.addProperty("model", OpenAIProperties.instance().getModel());
            payload.addProperty("temperature", temperature);
            payload.addProperty("top_p", topP);
            payload.add("messages", LMOpenAI.GSON.toJsonTree(message));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(OpenAIProperties.instance().getUrl()))
                    .header("Authorization", "Bearer " + OpenAIProperties.instance().getKey())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(LMOpenAI.GSON.toJson(payload)))
                    .build();
            HttpResponse<String> response = LMOpenAI.HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            // @todo: possibly do some more extensive testing here?
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException(
                        "unable to use openai api - http request returned status code: " + response.statusCode());
            }

            JsonObject jsonResponse = LMOpenAI.GSON.fromJson(response.body(), JsonObject.class);
            String result = LMOpenAI.testAndObtainContent(jsonResponse);
            LMOpenAI.LOGGER.warn("LMOpenAI.openai() returns " + result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("unable to request openai :-(", e);
        }
    }

    private static String testAndObtainContent(JsonObject jsonResponse) {
        if (!jsonResponse.has("choices")) {
            throw new RuntimeException(
                    "unable to use openai api - json response has no choices: " + jsonResponse);
        }

        JsonArray jsonChoices = jsonResponse.getAsJsonArray("choices");

        if (jsonChoices.size() == 0) {
            throw new RuntimeException(
                    "unable to use openai api - json choices is empty: " + jsonResponse);
        }

        JsonObject jsonChoice = jsonChoices.get(0).getAsJsonObject();

        if (!jsonChoice.has("message")) {
            throw new RuntimeException(
                    "unable to use openai api - json choices is empty: " + jsonResponse);
        }

        JsonObject jsonMessage = jsonChoice.get("message").getAsJsonObject();

        if (!jsonMessage.has("content")) {
            throw new RuntimeException(
                    "unable to use openai api - json message has no content: " + jsonResponse);
        }

        return jsonMessage.get("content").getAsString();
    }
}