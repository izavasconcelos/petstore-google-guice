package com.izavasconcelos.cloud.tema02.model;

public class Pet {

    private String name;
    private String race;
    private int age;
    private int id;

    public Pet(String name, String race, int age) {
        this.name = name;
        this.race = race;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getRace() {
        return race;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  "\nName: " + name + ", Age: " + age +", Race: " + race + ", Pet Id: " + id;
    }
}
