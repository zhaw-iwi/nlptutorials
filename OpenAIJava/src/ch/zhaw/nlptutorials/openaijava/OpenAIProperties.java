package ch.zhaw.nlptutorials.openaijava;

public class OpenAIProperties {

    private final static String URL = "https://api.openai.com/v1/chat/completions";
    private final static String MODEL = "gpt-4";
    private final static String KEY = "Your OpenAI API Key here";

    private static OpenAIProperties INSTANCE;

    public static OpenAIProperties instance() {
        if (OpenAIProperties.INSTANCE == null) {
            OpenAIProperties.INSTANCE = new OpenAIProperties(OpenAIProperties.URL, OpenAIProperties.MODEL,
                    OpenAIProperties.KEY);
        }
        return OpenAIProperties.INSTANCE;
    }

    private String url;
    private String model;
    private String key;

    private OpenAIProperties(String url, String model, String key) {
        this.url = url;
        this.model = model;
        this.key = key;
    }

    public String getUrl() {
        return this.url;
    }

    public String getModel() {
        return this.model;
    }

    public String getKey() {
        return this.key;
    }

}
