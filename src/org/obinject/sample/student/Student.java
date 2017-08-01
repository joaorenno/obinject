package org.obinject.sample.student;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Sort;
import org.obinject.annotation.Unique;

@Persistent
public class Student
{
    @Unique
    private int registration;
    @Edition
    @Sort
    private String name = "";
    private String address = "";
    private String course = "";
    private float coefficient;
    private int age;

    public int getRegistration() {
        return registration;
    }

    public void setRegistration(int registration) {
        this.registration = registration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

 
}
