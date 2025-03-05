package com.example.exampleOne.eo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "data_table")
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters long.")
    private String name;

    @Min(value = 1, message = "The minimum age allowed is 1")
    @Max(value = 120, message = "The maximum age allowed is 120")
    private int edad;

    public DataEntity() {}
    public DataEntity(Long id, String name, int edad) {
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
