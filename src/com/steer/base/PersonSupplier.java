package com.steer.base;

import java.util.Random;
import java.util.function.Supplier;

public class PersonSupplier implements Supplier<Person> {
    private int no = 0;
    private Random random = new Random();

    @Override
    public Person get() {
        Person person = new Person("name"+no,random.nextInt());
        no++;
        return person;
    }
}
