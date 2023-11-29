package com.edu.webappstud_v2;

public class StudentGrade {
    private int id;
    private int studentId;
    private String subject;
    private int grade;
    private String ects;
    private int disciplineId;  // Додано поле для disciplineId

    public StudentGrade() {
        // Конструктор за замовчуванням
    }

    public StudentGrade(int id, int studentId, String subject, int grade, String ects, int disciplineId) {
        this.id = id;
        this.studentId = studentId;
        this.subject = subject;
        this.grade = grade;
        this.ects = ects;
        this.disciplineId = disciplineId;
    }

    // Додайте геттери та сеттери для всіх полів
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getEcts() {
        return ects;
    }

    public void setEcts(String ects) {
        this.ects = ects;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(int disciplineId) {
        this.disciplineId = disciplineId;
    }
}