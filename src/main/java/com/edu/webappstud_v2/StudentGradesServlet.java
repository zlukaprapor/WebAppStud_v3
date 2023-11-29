package com.edu.webappstud_v2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



@WebServlet(name = "StudentGradesServlet", value = "/student-grades")
public class StudentGradesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int studentId = Integer.parseInt(request.getParameter("studentId"));


        List<StudentGrade> grades = getStudentGradesFromDatabase(studentId);


        request.setAttribute("grades", grades);


        request.getRequestDispatcher("/grades.jsp").forward(request, response);
    }

    private List<StudentGrade> getStudentGradesFromDatabase(int studentId) {
        List<StudentGrade> grades = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "026323");


            String selectQuery = "SELECT id, subject, grade, ects, discipline_id FROM grades WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, studentId);


            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String subject = resultSet.getString("subject");
                int grade = resultSet.getInt("grade");
                String ects = resultSet.getString("ects");
                int disciplineId = resultSet.getInt("discipline_id"); // Додано disciplineId


                StudentGrade studentGrade = new StudentGrade(id, studentId, subject, grade, ects, disciplineId);
                grades.add(studentGrade);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return grades;
    }
}
