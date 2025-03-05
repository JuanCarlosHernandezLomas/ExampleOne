package com.example.exampleOne.bo;

public class DataBo {
    private Long id;
    private String name;
    private int edad;

    public DataBo(){

    }
    public DataBo(Long id, String name, int edad) {
        this.id = id;
        this.name = name;
        this.edad = edad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
