package com.hnq.toolkit.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author henengqiang
 * @date 2020/3/26
 */
class ArrUtilsTest {

    @Test
    void testDistinctByKey1() {
        List<Person> persons = getPeople();

        List<Person> res = persons.stream().filter(ArrUtils.distinctByKey(Person::getName)).collect(Collectors.toList());
        List<Person> res2 = persons.stream().filter(ArrUtils.distinctByKey(Person::getAge)).collect(Collectors.toList());
        assertEquals(res, res2);
    }

    @Test
    void testDistinctByKey2() {
        List<Person> persons = getPeople();

        List<Person> res = ArrUtils.distinctByKey(persons, Person::getName);
        List<Person> res2 = ArrUtils.distinctByKey(persons, Person::getAge);
        assertEquals(res, res2);
    }

    private List<Person> getPeople() {
        List<Person> persons = new ArrayList<>(8);
        persons.add(new Person("alice", 20, ""));
        persons.add(new Person("alice", 20, ""));
        persons.add(new Person("bob", 30, ""));
        persons.add(new Person("candy", 40, ""));
        persons.add(new Person("candy", 40, ""));
        return persons;
    }

    @Data
    @AllArgsConstructor
    private static class Person {
        private String name;
        private Integer age;
        private String address;
    }

}