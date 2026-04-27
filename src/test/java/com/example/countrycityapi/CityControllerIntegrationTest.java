package com.example.countrycityapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetCityById() throws Exception {
        mockMvc.perform(get("/cities/{cityId}", 7L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Berlin"))
                .andExpect(jsonPath("$.countryId").value(2))
                .andExpect(jsonPath("$.population").value(3645000))
                .andExpect(jsonPath("$.zipCode").value("10115"));
    }

    @Test
    void shouldReturnNotFoundForInvalidCityId() throws Exception {
        mockMvc.perform(get("/cities/{cityId}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("City not found with id: 999"))
                .andExpect(jsonPath("$.path").value("/cities/999"));
    }
}
