package com.example.demo.repository;

import com.example.demo.model.Person;
import com.example.demo.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @Test
    public void handleGetPersons_ReturnValidList() {
        List<Person> list = List.of(
                new Person(1L, "name"),
                new Person(2L, "name2")
        );
        doReturn(list).when(personRepository).findAll();

        List<Person> resultList = personServiceImpl.getPersons();

        assertEquals(list.size(), resultList.size());
        assertEquals(list, resultList);
    }
}
