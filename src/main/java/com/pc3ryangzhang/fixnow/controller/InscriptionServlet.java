package com.pc3ryangzhang.fixnow.controller;

import com.pc3ryangzhang.fixnow.entity.User;
import com.pc3ryangzhang.fixnow.entity.ValueObject.MessageModel;
import com.pc3ryangzhang.fixnow.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
    private UserService userService = new UserService();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("dopost");
        // Extract form parameters
        String userName = request.getParameter("username");
        String userType = request.getParameter("usertype");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Create a User object
        User user = new User();
        user.setUserName(userName);
        user.setUserType(userType);
        user.setEmail(email);
        user.setPassword(password);

        // Call the UserService to register the new user
        MessageModel messageModel = userService.userInscription(user);

        // Check the registration result and forward to the appropriate page
        if (messageModel.getCode() == 1) {
            response.sendRedirect("connection.jsp");
        } else {
            request.setAttribute("messageModel", messageModel);
            request.getRequestDispatcher("inscription.jsp").forward(request, response);
        }
    }
}
