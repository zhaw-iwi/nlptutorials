import openai
openai.api_key = "Your https://platform.openai.com/ API key here"

class Chatbot:
    _default_role = "You are a kind, respectful, and sympathetic coach."
    _default_starter = "Create a short prompt to greet the user."

    def __init__(self, role=_default_role):
        self._messages = []
        self._append_system(role)

    def _append_system(self, content):
        self._messages.append({"role": "system", "content": content})

    def _append_assistant(self, content):
        self._messages.append({"role": "assistant", "content": content})

    def _append_user(self, content):
        self._messages.append({"role": "user", "content": content})

    def _openai(self):
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=self._messages,
            max_tokens=256
        )
        return response.choices[0].message.content
    
    def session_context(self, session_context):
        self._append_system(session_context)

    def starter(self):
        self._append_system(Chatbot._default_starter)
        response = self._openai()
        self._append_assistant(response)
        return response
    
    def reponse_for(self, user_says):
        if user_says is None:
            raise RuntimeError("user_says must not be None")
        self._append_user(user_says)
        assistant_says = self._openai()
        self._append_assistant(assistant_says)
        return assistant_says