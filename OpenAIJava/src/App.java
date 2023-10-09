import java.util.List;

import ch.zhaw.nlptutorials.openaijava.LMOpenAI;
import ch.zhaw.nlptutorials.openaijava.Utterance;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, OpenAI!");

        Utterance role = new Utterance(Utterance.ROLE_SYSTEM, "You are a grumpy assistant.");
        Utterance starter = new Utterance(Utterance.ROLE_SYSTEM, "Greet the user and ask what they want.");
        List<Utterance> utterances = List.of(role, starter);

        String response = LMOpenAI.openai(utterances);
        System.out.println(response);
    }
}
