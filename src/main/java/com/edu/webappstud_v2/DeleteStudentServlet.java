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

@WebServlet(name = "DeleteStudentServlet", value = "/delete-student")
public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        if (name != null && surname != null) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "026323");
                String deleteQuery = "DELETE FROM students WHERE name = ? AND surname = ?";
                preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Студент був успішно видалений
                    List<Student> students = getStudentsFromDatabase(connection); // Отримуємо список студентів з бази даних
                    request.setAttribute("students", students); // Зберігаємо список студентів в об'єкті request
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } else {
                    // Неможливо знайти студента для видалення
                    request.setAttribute("error", "Не вдалося знайти студента для видалення.");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Помилка при роботі з базою даних: " + e.getMessage());
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } finally {
                // Закриваємо з'єднання та підготовлену заяву в блоку finally
                try {
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
        } else {
            request.setAttribute("error", "Помилка: відсутні ім'я та/або прізвище для видалення.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    // Отримати дані студентів з бази даних та зберегти їх у списку
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
