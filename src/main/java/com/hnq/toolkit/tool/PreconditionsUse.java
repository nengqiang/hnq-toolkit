package com.hnq.toolkit.tool;

import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author henengqiang
 * @date 2019/06/10
 */
@Slf4j
public class PreconditionsUse {

    public static void main(String[] args) {
        personPreconditionsTest();
    }

    private static void personPreconditionsTest() {
        Person person = new Person("alice", 25);
        try {
            checkPersonByPrecondition(person);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            checkPersonByPrecondition(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        person = new Person(null, 25);
        try {
            checkPersonByPrecondition(person);
        } catch (Exception e) {
            e.printStackTrace();
        }

        person = new Person("", 25);
        try {
            checkPersonByPrecondition(person);
        } catch (Exception e) {
            e.printStackTrace();
        }

        person = new Person("bob", -1);
        try {
            checkPersonByPrecondition(person);
        } catch (Exception e) {
            e.printStackTrace();
        }

        person = new Person("candy", 900);
        try {
            checkPersonByPrecondition(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkPersonByPrecondition(Person person) {
        Preconditions.checkNotNull(person, "person is null");
        String name = person.getName();
        Preconditions.checkNotNull(name, "name is null - " + person.toString());
        Preconditions.checkArgument(name.length() > 0, "name is '' - " + person.toString());
        Integer age = person.getAge();
        Preconditions.checkArgument(age > 0 && age < 100, "age must be an integer that grater than 0 and smaller than 100 - " + person.toString());
        log.debug(person.toString());
    }

    @Data
    private static class Person {

        private String name;

        private Integer age;

        Person() {}

        Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }

}
