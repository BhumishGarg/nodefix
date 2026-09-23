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
import java.time.Duration;

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

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();

        this.objectMapper = new ObjectMapper();

        this.credential = new DefaultAzureCredentialBuilder()
                .build();

        this.projectEndpoint = removeTrailingSlash(projectEndpoint);
        this.agentName = agentName;
    }

    /**
     * Sends a student's question to the configured Microsoft Foundry agent.
     *
     * The agent itself must already be configured in Foundry with:
     * - Chitkara-only scope
     * - NodeFix knowledge source
     * - English/Hindi/Punjabi support
     * - No guessing when information is unavailable
     */
    public ChatResult processMessage(
            String message,
            String language) {

        if (message == null || message.isBlank()) {

            return new ChatResult(
                    "Please enter a question.",
                    "NodeFix",
                    false,
                    true,
                    false
            );
        }

        String selectedLanguage =
                normalizeLanguage(language);

        try {

            AccessToken token =
                    credential.getToken(
                            new TokenRequestContext()
                                    .addScopes(
                                            "https://ai.azure.com/.default"
                                    )
                    ).block();

            if (token == null ||
                token.getToken() == null ||
                token.getToken().isBlank()) {

                return new ChatResult(
                        "Could not authenticate with Microsoft Azure. "
                                + "Please check your Azure login and permissions.",
                        "Azure Authentication",
                        false,
                        false,
                        true
                );
            }

            String url =
                    projectEndpoint
                            + "/agents/"
                            + agentName
                            + "/endpoint/protocols/openai/responses"
                            + "?api-version=v1";

            ObjectNode body =
                    objectMapper.createObjectNode();

            ArrayNode input =
                    body.putArray("input");

            ObjectNode userMessage =
                    input.addObject();

            userMessage.put(
                    "role",
                    "user"
            );

            /*
             * The agent's Foundry instructions remain the main
             * source of policy. We additionally tell it which
             * language the student selected.
             */
            String prompt =
                    "Requested response language: "
                            + selectedLanguage
                            + "\n\n"
                            + "Student question:\n"
                            + message;

            userMessage.put(
                    "content",
                    prompt
            );

            String requestBody =
                    objectMapper.writeValueAsString(body);

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .timeout(Duration.ofSeconds(60))
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .header(
                                    "Accept",
                                    "application/json"
                            )
                            .header(
                                    "Authorization",
                                    "Bearer "
                                            + token.getToken()
                            )
                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(requestBody)
                            )
                            .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "Foundry Agent HTTP Status: "
                            + response.statusCode()
            );

            if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {

                System.err.println(
                        "Foundry Agent Error: "
                                + response.body()
                );

                return new ChatResult(
                        "The NodeFix AI service returned an error "
                                + "(HTTP "
                                + response.statusCode()
                                + "). Please try again.",
                        "Microsoft Foundry",
                        false,
                        false,
                        true
                );
            }

            String answer =
                    extractAnswer(
                            response.body()
                    );

            if (answer == null ||
                answer.isBlank()) {

                return new ChatResult(
                        "I could not generate a response right now. "
                                + "Please try again.",
                        "Microsoft Foundry",
                        false,
                        false,
                        true
                );
            }

            boolean unresolved =
                    isUnresolvedResponse(answer);

            return new ChatResult(
                    answer,
                    "Microsoft Foundry - nodefix1",
                    unresolved,
                    unresolved,
                    false
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return new ChatResult(
                    "The AI request was interrupted. Please try again.",
                    "Microsoft Foundry",
                    false,
                    false,
                    true
            );

        } catch (Exception e) {

            System.err.println(
                    "Foundry connection error: "
                            + e.getMessage()
            );

            return new ChatResult(
                    "Could not connect to the NodeFix AI service. "
                            + "Please try again later.",
                    "Microsoft Foundry",
                    false,
                    false,
                    true
            );
        }
    }


    /**
     * Extract text from common Microsoft Responses API shapes.
     */
    private String extractAnswer(
            String responseBody) {

        try {

            JsonNode root =
                    objectMapper.readTree(
                            responseBody
                    );

            /*
             * Preferred Responses API field.
             */
            JsonNode outputText =
                    root.path("output_text");

            if (outputText.isTextual() &&
                !outputText.asText().isBlank()) {

                return outputText.asText().trim();
            }


            /*
             * Fallback: output -> content -> text
             */
            JsonNode output =
                    root.path("output");

            if (output.isArray()) {

                StringBuilder answer =
                        new StringBuilder();

                for (JsonNode item : output) {

                    JsonNode content =
                            item.path("content");

                    if (!content.isArray()) {
                        continue;
                    }

                    for (JsonNode contentItem : content) {

                        JsonNode text =
                                contentItem.path("text");

                        if (text.isTextual()) {

                            String value =
                                    text.asText().trim();

                            if (value.isBlank()) {
                                continue;
                            }

                            if (answer.length() > 0) {
                                answer.append("\n");
                            }

                            answer.append(value);
                        }
                    }
                }

                if (answer.length() > 0) {
                    return answer.toString().trim();
                }
            }


            /*
             * Some responses may contain a plain text field.
             */
            JsonNode text =
                    root.path("text");

            if (text.isTextual() &&
                !text.asText().isBlank()) {

                return text.asText().trim();
            }


            /*
             * Last-resort fallback.
             */
            return responseBody;

        } catch (Exception e) {

            System.err.println(
                    "Could not parse Foundry response: "
                            + e.getMessage()
            );

            return responseBody;
        }
    }


    /**
     * This must match the exact fallback wording configured
     * in the NodeFix Foundry agent instructions.
     *
     * The agent should say:
     *
     * "I could not find a verified answer to this question
     *  in the NodeFix knowledge base."
     */
    private boolean isUnresolvedResponse(
            String answer) {

        if (answer == null) {
            return false;
        }

        String normalized =
                answer
                        .trim()
                        .toLowerCase();

        return normalized.contains(
                "i could not find a verified answer"
        )
        ||
        normalized.contains(
                "i couldn't find a verified answer"
        )
        ||
        normalized.contains(
                "could not find a verified answer"
        )
        ||
        normalized.contains(
                "couldn't find a verified answer"
        );
    }


    private String normalizeLanguage(
            String language) {

        if (language == null ||
            language.isBlank()) {

            return "English";
        }

        String value =
                language.trim();

        if (value.equalsIgnoreCase("Hindi")) {
            return "Hindi";
        }

        if (value.equalsIgnoreCase("Punjabi")) {
            return "Punjabi";
        }

        return "English";
    }


    private String removeTrailingSlash(
            String value) {

        if (value == null) {
            return "";
        }

        return value.replaceAll(
                "/+$",
                ""
        );
    }
}