package ch.zhaw.nlptutorials.openaijava;

public class Utterance {

    public static final String ROLE_SYSTEM = "system";
    public static final String ROLE_ASSISTANT = "assistant";
    public static final String ROLE_USER = "user";

    private String role;
    private String content;

    public Utterance(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return this.role;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "{role=\"" + role + "\", content=\"" + content + "\"}";
    }
}
