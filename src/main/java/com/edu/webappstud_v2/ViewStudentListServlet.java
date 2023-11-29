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

@WebServlet(name = "ViewStudentListServlet", value = "/view-student-list")
public class ViewStudentListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            // Отримати з'єднання з базою даних
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "026323");

            // Отримати список студентів з бази даних
            List<Student> students = getStudentsFromDatabase(connection);

            // Зберегти список студентів у об'єкті request для використання у JSP
            request.setAttribute("students", students);

            // Перенаправити на JSP-сторінку для відображення списку студентів
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Обробити помилку (наприклад, відправити користувачу сторінку з помилкою)
            response.getWriter().println("Помилка при отриманні даних студентів: " + e.getMessage());
        } finally {
            // Закрити з'єднання
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Student> getStudentsFromDatabase(Connection connection) throws SQLException {
        List<Student> students = new ArrayList<>();
        String selectQuery = "SELECT * FROM students";
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = selectStatement.executeQuery();

        while (resultSet.next()) {
            Student student = new Student();
            student.setName(resultSet.getString("name"));
            student.setSurname(resultSet.getString("surname"));
            student.setEmail(resultSet.getString("email"));
            student.setGroup(resultSet.getString("gp"));
            student.setFaculty(resultSet.getString("faculty"));
            students.add(student);
        }

        resultSet.close();
        selectStatement.close();

        return students;
    }
}

