<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <title>JSP Page</title>
    <style>
        .button-link {
            display: block;
            margin: 0 auto;

            text-decoration: none;
            color: #0056b3;



            cursor: pointer;
            transition: background-color 0.3s ease;
        }


    </style>
</head>
<body>
<div id="page">
    <h1>Student Form</h1>
    <form method="post" action="${pageContext.request.contextPath}/student-registration-db
">
        <table>
            <tbody>
            <tr>
                <td><label for="name">Name</label></td>
                <td><input id="name" type="text" name="name"></td>
            </tr>
            <tr>
                <td><label for="surname">Surname</label></td>
                <td><input id="surname" type="text" name="surname"></td>
            </tr>
            <tr>
                <td><label for="email">Email</label></td>
                <td><input id="email" type="email" name="email"></td>
            </tr>
            <tr>
                <td><label for="group">Group</label></td>
                <td><input id="group" type="text" name="group"></td>
            </tr>
            <tr>
                <td><label for="faculty">Faculty</label></td>
                <td><input id="faculty" type="text" name="faculty"></td>
            </tr>
            </tbody>
        </table>
        <input type="submit" name="send" value="Send">
        <a class="button-link" href="${pageContext.request.contextPath}/view-student-list">View Student List</a>
    </form>
    <c:if test="${not empty requestScope.error}">
        <div class="error" style="color: #ff0000">
              ${requestScope.error}
        </div>
    </c:if>
    <h1>Student List</h1>
    <table class="list">
        <tr>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th>Group</th>
            <th>Faculty</th>
            <th>Grades</th>
            <th>Delete</th>
        </tr>

        <c:forEach items="${students}" var="student" varStatus="loop">
            <tr>
                <td>${student.name}</td>
                <td>${student.surname}</td>
                <td>${student.email}</td>
                <td>${student.group}</td>
                <td>${student.faculty}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/student-grades?studentId=${loop.index + 1}" class="button-link">View Grades</a>
                </td>

                <td>
                    <form style=";color: #fff;
    border: none;
    margin: 1px 1px 1px 1px;
    padding: 5px 5px;
    width: 20px;
    height: 20px;
    border-radius: 5px;" action="${pageContext.request.contextPath}/delete-student" method="post">
                        <input type="hidden" name="name" value="${student.name}">
                        <input type="hidden" name="surname" value="${student.surname}">
                        <button style="background-color: red;

    border: none;
    width: 20px;
    height: 20px;
    padding: 5px 5px;
    cursor: pointer;
    border-radius: 5px;color: #000000"  type="submit">X</button>
                    </form>
                </td>

            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>