package com.example.demo.service;

import com.example.demo.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getPersons();
    Person savePerson(Person person);
}
