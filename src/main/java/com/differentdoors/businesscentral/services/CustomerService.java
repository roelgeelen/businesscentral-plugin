package com.differentdoors.businesscentral.services;

import com.differentdoors.businesscentral.models.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class CustomerService {
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    @Autowired
    @Qualifier("BusinessCentral")
    private WebClient webClient;

    public Customer getCustomer(String id) throws Exception {
        return objectMapper.readValue(webClient.get()
                .uri("HUB_Customers('" + id + "')")
                .retrieve()
                .bodyToMono(String.class)
                .block(), new TypeReference<>() {
        });
    }

    public String createCustomer(Customer customer) throws Exception {
        return webClient.post().uri("HUB_Customers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(objectMapper.writeValueAsString(customer)), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(Mono::error)
                .block();


//        Map<String, String> urlParams = new HashMap<>();
//        urlParams.put("path", "HUB_Customers");
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<Object> entity = new HttpEntity<>(objectMapper.writeValueAsString(customer), headers);
//        restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), entity, String.class);
    }

    public String updateCustomer(String id, String etag, Customer customer) throws Exception {
        return webClient.patch().uri("HUB_Customers('" + id + "')")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("If-Match", etag)
                .body(Mono.just(objectMapper.writeValueAsString(customer)), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
//        Map<String, String> urlParams = new HashMap<>();
//        urlParams.put("path", "HUB_Customers('" + id + "')");
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
//        headers.set("If-Match", etag);
//        HttpEntity<Object> entity = new HttpEntity<>(objectMapper.writeValueAsString(customer), headers);
//        restTemplate.patchForObject(builder.buildAndExpand(urlParams).toUri(), entity, String.class);
    }

}
