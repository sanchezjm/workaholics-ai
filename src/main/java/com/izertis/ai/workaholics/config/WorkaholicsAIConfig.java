package com.izertis.ai.workaholics.config;

import com.izertis.ai.workaholics.WorkaholicsAuthorsAgent;
import com.izertis.ai.workaholics.WorkaholicsTutorialsAgent;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class WorkaholicsAIConfig {

    @Bean
    ApplicationRunner interactiveChatRunner(WorkaholicsTutorialsAgent agent, WorkaholicsAuthorsAgent authorsAgent) {
        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("User: ");
                String userMessage = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userMessage)) {
                    break;
                }
                String agentMessage = "";
                if (authorsAgent.isQuestionAboutMyDomain(userMessage)){
                    agentMessage = authorsAgent.chat(userMessage);
                    System.out.print("[Authors] ");
                } else {
                    agentMessage = agent.chat(userMessage);
                    System.out.print("[Generic] ");
                }
                System.out.println("Agent: " + agentMessage);
            }

            scanner.close();
        };
    }

}
