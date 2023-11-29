package com.edu.webappstud_v2;



public class Student {
    private String name;
    private String surname;
    private String email;
    private String group;
    private String faculty;

    private String error;

    public Student() {
        error = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
            error = ""; // Скидаємо помилку
        } else {
            error = "Ім'я не може бути пустим або null";
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname != null && !surname.trim().isEmpty()) {
            this.surname = surname;
            error = ""; // Скидаємо помилку
        } else {
            error = "Прізвище не може бути пустим або null";
        }
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getError() {
        return error;
    }
}