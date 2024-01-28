package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @Test
    void handleGetPersons_ReturnValidList() {
        List<Person> list = List.of(
                new Person(1L, "name"),
                new Person(2L, "name2")
        );
        doReturn(list).when(personRepository).findAll();

        List<Person> resultList = personServiceImpl.getPersons();

        verify(personRepository).findAll();
        assertEquals(list.size(), resultList.size());
        assertEquals(list, resultList);
    }

    @Test
    void handleSavePerson_ReturnSavedPerson() {
        Person person = new Person(1L, "name");
        doReturn(person).when(personRepository).save(person);

        Person savedPerson = personServiceImpl.savePerson(person);

        verify(personRepository).save(person);
        assertEquals(person, savedPerson);
    }
}
