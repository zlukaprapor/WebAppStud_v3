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

@WebServlet(name = "StudentRegistrationDBServlet", value = "/student-registration-db")
public class StudentRegistrationDBServlet extends HttpServlet {

    // Перевірка наявності студента з такими самими даними в базі даних
    private boolean isStudentDuplicate(Connection connection, String name, String surname, String email, String gp, String faculty) throws SQLException {
        String selectQuery = "SELECT COUNT(*) FROM students WHERE name = ? AND surname = ? AND email = ? AND gp = ? AND faculty = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, name);
        selectStatement.setString(2, surname);
        selectStatement.setString(3, email);
        selectStatement.setString(4, gp);
        selectStatement.setString(5, faculty);
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }

        return false;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");




        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String gp = request.getParameter("group");
        String faculty = request.getParameter("faculty");

        if (name != null && !name.trim().isEmpty() && surname != null && !surname.trim().isEmpty()) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                Class.forName("org.postgresql.Driver"); // Завантажуємо драйвер PostgreSQL
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "026323");
                // Перевірка наявності дублікату
                if (isStudentDuplicate(connection, name, surname, email, gp, faculty)) {
                    request.setAttribute("error", "Студент з такими даними вже існує.");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    return; // Вихід з методу, якщо існує дублікат
                }

                String insertQuery = "INSERT INTO students (name, surname, email, gp, faculty) VALUES (?, ?, ?, ?, ?)";

                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, gp);
                preparedStatement.setString(5, faculty);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Отримуємо дані з бази даних та зберігаємо їх у списку студентів
                    List<Student> students = getStudentsFromDatabase(connection);
                    // Зберігаємо список студентів у об'єкті request
                    request.setAttribute("students", students);

                    // Дані були успішно збережені в базі даних
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Помилка при збереженні даних.");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            }
            catch (ClassNotFoundException | SQLException e) {
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
            request.setAttribute("error", "Некоректне ім'я або прізвище.");
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
