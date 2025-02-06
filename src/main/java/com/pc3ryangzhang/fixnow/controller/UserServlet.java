package com.pc3ryangzhang.fixnow.controller;

import com.pc3ryangzhang.fixnow.entity.ValueObject.MessageModel;
import com.pc3ryangzhang.fixnow.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/connection")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();


    @Override
    public void doPost ( HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{

        String email = request.getParameter("email");
        String pwd = request.getParameter("password");

        MessageModel messageModel = userService.userConnection(email,pwd);


        if(messageModel.getCode() == 1){
            request.getSession().setAttribute("user", messageModel.getObject());
            response.sendRedirect("userCenter.jsp");

        }else{
            request.setAttribute("messageModel", messageModel);
            request.getRequestDispatcher("connection.jsp").forward(request, response);

        }

    }
}
