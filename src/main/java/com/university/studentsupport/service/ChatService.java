package com.university.studentsupport.service;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ChatService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final DefaultAzureCredential credential;

    private final String projectEndpoint;
    private final String agentName;

    public ChatService(
            @Value("${foundry.project-endpoint}") String projectEndpoint,
            @Value("${foundry.agent-name}") String agentName) {

        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.credential = new DefaultAzureCredentialBuilder().build();

        this.projectEndpoint = projectEndpoint;
        this.agentName = agentName;
    }

    public String processMessage(String message) {

        if (message == null || message.isBlank()) {
            return "Please enter a question.";
        }

        try {
            AccessToken token = credential.getToken(
                    new TokenRequestContext()
                            .addScopes("https://ai.azure.com/.default")
            ).block();

            if (token == null) {
                return "Could not get Azure authentication token.";
            }

            String url =
                    projectEndpoint
                            + "/agents/"
                            + agentName
                            + "/endpoint/protocols/openai/responses?api-version=v1";

            ObjectNode body = objectMapper.createObjectNode();

            ArrayNode input = body.putArray("input");

            ObjectNode userMessage = input.addObject();
            userMessage.put("role", "user");
            userMessage.put("content", message);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token.getToken())
                    .POST(
                            HttpRequest.BodyPublishers.ofString(
                                    objectMapper.writeValueAsString(body)
                            )
                    )
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "Foundry Agent HTTP Status: " + response.statusCode()
            );

            if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {

                System.out.println(
                        "Foundry Agent Response: " + response.body()
                );

                return "Foundry Agent request failed. Status: "
                        + response.statusCode();
            }

            return extractAnswer(response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return "Could not connect to the UniAssist Foundry agent.";
        }
    }

    private String extractAnswer(String responseBody) {

        try {
            JsonNode root = objectMapper.readTree(responseBody);

            JsonNode output = root.path("output");

            if (output.isArray()) {

                for (JsonNode item : output) {

                    JsonNode content = item.path("content");

                    if (content.isArray()) {

                        for (JsonNode contentItem : content) {

                            JsonNode text = contentItem.path("text");

                            if (text.isTextual()) {
                                return text.asText();
                            }
                        }
                    }
                }
            }

            return responseBody;

        } catch (Exception e) {
            return responseBody;
        }
    }
}