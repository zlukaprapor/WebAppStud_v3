package com.edu.webappstud_v2;


import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "RegistrationFormServlet", value = "/registration-form")

public class RegistrationFormServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }


}
