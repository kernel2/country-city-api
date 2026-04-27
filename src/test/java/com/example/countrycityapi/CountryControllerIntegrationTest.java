package com.example.countrycityapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CountryControllerIntegrationTest {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Test
    void shouldGetAllCountries() throws Exception {
        HttpResponse<String> response = sendGet("/countries");
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(200, response.statusCode());
        assertEquals(4, body.size());
        assertEquals(1L, body.get(0).get("id").asLong());
        assertEquals("France", body.get(0).get("name").asText());
    }

    @Test
    void shouldGetCitiesByCountryWithPagination() throws Exception {
        HttpResponse<String> response = sendGet("/countries/1/cities?page=0&size=2");
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(200, response.statusCode());
        assertEquals(0, body.get("page").asInt());
        assertEquals(2, body.get("size").asInt());
        assertEquals(6, body.get("totalElements").asLong());
        assertEquals(3, body.get("totalPages").asInt());
        assertFalse(body.get("last").asBoolean());
        assertFalse(body.get("content").isEmpty());
    }

    @Test
    void shouldCreateCountry() throws Exception {
        String payload = "{\"name\":\"Portugal\"}";
        HttpResponse<String> response = sendPost("/countries", payload);
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(201, response.statusCode());
        assertEquals("Portugal", body.get("name").asText());
    }

    @Test
    void shouldReturnBadRequestWhenCreateCountryPayloadIsInvalid() throws Exception {
        String payload = "{\"name\":\"\"}";
        HttpResponse<String> response = sendPost("/countries", payload);
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(400, response.statusCode());
        assertEquals(400, body.get("status").asInt());
        assertEquals("Bad Request", body.get("error").asText());
        assertEquals("Invalid request parameters", body.get("message").asText());
    }

    private HttpResponse<String> sendGet(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + path))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> sendPost(String path, String jsonPayload) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}
