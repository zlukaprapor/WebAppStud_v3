<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Student Grades</title>
    <style>
        /* Додайте ваші стилі тут */
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        /* Додайте інші стилі за необхідності */
    </style>
</head>
<body>
<h1>Student Grades</h1>
<table border="1">
    <tr>
        <th>Subject</th>
        <th>Grade</th>
        <th>ECTS</th>
    </tr>
    <c:forEach items="${grades}" var="grade">
        <tr>
            <td>${grade.subject}</td>
            <td>${grade.grade}</td>
            <td>${grade.ects}</td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/view-student-list">View Student List</a>
</body>
</html>
