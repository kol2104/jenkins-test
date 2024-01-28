package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void handleGetPersons_ReturnOkAnswer() throws Exception {
        List<Person> list = List.of(
                new Person(1L, "name"),
                new Person(2L, "name2")
        );
        doReturn(list).when(personService).getPersons();

        MvcResult mvcResult = mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        List<Person> resultList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {}
        );
        verify(personService).getPersons();
        assertEquals(list.size(), resultList.size());
        assertEquals(list, resultList);
    }

    @Test
    void handleSavePerson_ReturnSavedPerson() throws Exception {
        Person person = new Person(1L, "name");
        doReturn(person).when(personService).savePerson(person);

        MvcResult mvcResult = mockMvc.perform(post("/person")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        Person savedPerson =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Person.class);
        verify(personService).savePerson(person);
        assertEquals(person, savedPerson);
    }
}
