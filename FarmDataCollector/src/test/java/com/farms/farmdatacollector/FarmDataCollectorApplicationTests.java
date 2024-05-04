package com.farms.farmdatacollector;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FarmDataCollectorApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddPlantedData() throws Exception {
        String json = "{\n" +
                "    \"farmName\": \"MyFarm\",\n" +
                "    \"season\": \"Summer\",\n" +
                "    \"cropType\": \"corn\",\n" +
                "    \"plantedArea\": 10.5,\n" +
                "    \"expectedProduct\": 15.2\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/farmers/planted")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testAddHarvestedData() throws Exception {
        String json = "{\n" +
                "    \"farmName\": \"MyFarm\",\n" +
                "    \"cropType\": \"corn\",\n" +
                "    \"season\" : \"Summer\",\n" +
                "    \"actualHarvest\": 12.5\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/farmers/harvested")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
