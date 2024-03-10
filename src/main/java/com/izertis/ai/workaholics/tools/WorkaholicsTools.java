package com.izertis.ai.workaholics.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.document.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Component
public class WorkaholicsTools {

    @Autowired
    private Set<Metadata> tutorialsStatsStorage;

    @Tool
    public long getTotalNumberOfTutorials() {
        return tutorialsStatsStorage.size();
    }

    @Tool
    public Map<String, String> getMostVisitedTutorial() {
        return tutorialsStatsStorage.stream().max(Comparator.comparingInt( m -> Integer.valueOf(m.get("views")))).orElseThrow().asMap();
    }

    @Tool
    public Map<String, String> getMostRecentTutorial() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("es"));

        return tutorialsStatsStorage.stream()
                .max(Comparator.comparing(m -> LocalDate.parse(m.get("date"), formatter) ))
                .orElseThrow()
                .asMap();
    }

}