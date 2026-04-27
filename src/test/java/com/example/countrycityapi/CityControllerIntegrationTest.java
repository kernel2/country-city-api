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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CityControllerIntegrationTest {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Test
    void shouldGetCityById() throws Exception {
        HttpResponse<String> response = sendGet("/cities/7");
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(200, response.statusCode());
        assertEquals(7L, body.get("id").asLong());
        assertEquals("Berlin", body.get("name").asText());
        assertEquals(2L, body.get("countryId").asLong());
        assertEquals(3645000L, body.get("population").asLong());
        assertEquals("10115", body.get("zipCode").asText());
    }

    @Test
    void shouldReturnNotFoundForInvalidCityId() throws Exception {
        HttpResponse<String> response = sendGet("/cities/999");
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(404, response.statusCode());
        assertEquals(404, body.get("status").asInt());
        assertEquals("Not Found", body.get("error").asText());
        assertEquals("City not found with id: 999", body.get("message").asText());
        assertEquals("/cities/999", body.get("path").asText());
    }

    private HttpResponse<String> sendGet(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + path))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}
