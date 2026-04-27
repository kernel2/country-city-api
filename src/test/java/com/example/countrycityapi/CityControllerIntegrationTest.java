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

    @Test
    void shouldCreateCity() throws Exception {
        String payload = "{\"name\":\"Porto\",\"countryId\":1,\"population\":237591,\"zipCode\":\"4000-001\",\"description\":\"Coastal city\"}";
        HttpResponse<String> response = sendPost("/cities", payload);
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(201, response.statusCode());
        assertEquals("Porto", body.get("name").asText());
        assertEquals(1L, body.get("countryId").asLong());
        assertEquals(237591L, body.get("population").asLong());
    }

    @Test
    void shouldReturnBadRequestWhenCreateCityPayloadIsInvalid() throws Exception {
        String payload = "{\"name\":\"\",\"countryId\":1,\"population\":-1,\"zipCode\":\"\",\"description\":\"\"}";
        HttpResponse<String> response = sendPost("/cities", payload);
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(400, response.statusCode());
        assertEquals(400, body.get("status").asInt());
        assertEquals("Bad Request", body.get("error").asText());
        assertEquals("Invalid request parameters", body.get("message").asText());
    }

    @Test
    void shouldReturnNotFoundWhenCreateCityWithUnknownCountry() throws Exception {
        String payload = "{\"name\":\"Ghost City\",\"countryId\":99999,\"population\":10,\"zipCode\":\"00000\",\"description\":\"Unknown country\"}";
        HttpResponse<String> response = sendPost("/cities", payload);
        JsonNode body = objectMapper.readTree(response.body());

        assertEquals(404, response.statusCode());
        assertEquals(404, body.get("status").asInt());
        assertEquals("Not Found", body.get("error").asText());
        assertEquals("Country not found with id: 99999", body.get("message").asText());
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
