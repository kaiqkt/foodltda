package com.zed.course.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Course{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The field 'title' is mandatory")
    @Column(nullable = false)
    private String tittle;

    public Course() {
    }

    public Course(@NotNull(message = "The field 'title' is mandatory") String tittle) {
        this.tittle = tittle;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", tittle='" + tittle + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
