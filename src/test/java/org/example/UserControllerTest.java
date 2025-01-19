package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUsers() throws Exception {
        String userJson = "{\"name\":\"Jan Kowalski\",\"email\":\"jan@kowalski.pl\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isArray());
    }

    @Test
    void testGetUserById() throws Exception {
        String userJson = "{\"name\":\"Jan Kowalski\",\"email\":\"jan@kowalski.pl\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/users/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jan Kowalski"));

        mockMvc.perform(get("/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        String userJson = "{\"name\":\"Anna Nowak\",\"email\":\"anna@nowak.pl\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Anna Nowak"));

        String invalidJson = "{\"email\":\"invalid@nowak.pl\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser() throws Exception {
        String userJson = "{\"name\":\"Anna Nowak\",\"email\":\"anna@nowak.pl\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated());

        String updatedUserJson = "{\"name\":\"Anna Nowakowska\",\"email\":\"anna@nowakowska.pl\"}";
        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anna Nowakowska"));

        mockMvc.perform(put("/users/999").contentType(MediaType.APPLICATION_JSON).content(updatedUserJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        String userJson = "{\"name\":\"Jan Kowalski\",\"email\":\"jan@kowalski.pl\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }
}