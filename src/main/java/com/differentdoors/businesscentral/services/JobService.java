package com.differentdoors.businesscentral.services;

import com.differentdoors.businesscentral.models.Job;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class JobService {
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    @Autowired
    @Qualifier("BusinessCentral")
    private WebClient webClient;

    public Job getJob(String id) throws Exception {
        return objectMapper.readValue(webClient.get()
                .uri("HUB_Projects('P" + id + "')")
                .retrieve()
                .bodyToMono(String.class)
                .block(), new TypeReference<>() {
        });


//        Map<String, String> urlParams = new HashMap<>();
//        urlParams.put("path", "HUB_Projects('P" + id + "')");
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);
//
//        return restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), Job.class);
    }

    public String createJob(Job job) throws Exception {
        return webClient.post().uri("HUB_Projects")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(objectMapper.writeValueAsString(job)), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(Mono::error)
                .block();


//
//        Map<String, String> urlParams = new HashMap<>();
//        urlParams.put("path", "HUB_Projects");
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<Object> entity = new HttpEntity<>(objectMapper.writeValueAsString(job), headers);
//        restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), entity, String.class);
    }

    public String updateJob(String id, String etag, Job job) throws Exception {
        return webClient.patch().uri("HUB_Projects('P" + id + "')")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("If-Match", etag)
                .body(Mono.just(objectMapper.writeValueAsString(job)), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();



//        Map<String, String> urlParams = new HashMap<>();
//        urlParams.put("path", "HUB_Projects('P" + id + "')");
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
//        headers.set("If-Match", etag);
//        HttpEntity<Object> entity = new HttpEntity<>(objectMapper.writeValueAsString(job), headers);
//        restTemplate.patchForObject(builder.buildAndExpand(urlParams).toUri(), entity, String.class);
    }
}
