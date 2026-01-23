package com.differentdoors.businesscentral.services;

import com.differentdoors.businesscentral.models.JobPlanningLine;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class JobPlanningLineService {
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    @Autowired
    @Qualifier("BusinessCentral")
    private WebClient webClient;

    public String createJobPlanningLine(JobPlanningLine line) throws Exception {
        return webClient.post().uri("line_items")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(objectMapper.writeValueAsString(line)), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(Mono::error)
                .block();
    }
}
