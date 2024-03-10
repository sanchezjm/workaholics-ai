package com.izertis.ai.workaholics;

import dev.langchain4j.service.UserMessage;

public interface WorkaholicsAuthorsAgent {

    @UserMessage("Is the following text a question about how to publish or write? Text: {{it}}")
    boolean isQuestionAboutMyDomain(String text);

    @UserMessage("Answer the following question providing information to the authors. Question: {{it}}")
    String chat(String userMessage);

}