package com.pc3ryangzhang.fixnow.controller;

import com.pc3ryangzhang.fixnow.entity.User;
import com.pc3ryangzhang.fixnow.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet("/userCenter")
@MultipartConfig
public class UserCenterServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {

            request.getSession().invalidate();
            response.sendRedirect("connection.jsp");
        } else {

            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {

                response.sendRedirect("connection.jsp");
                return;
            }

            request.getRequestDispatcher("userCenter.jsp").forward(request, response);
        }
    }




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle avatar upload
        Part filePart = request.getPart("avatar"); // Retrieves <input type="file" name="avatar">
        String fileName = filePart.getSubmittedFileName();

        // Get the absolute path of the webapp directory
        String contextPath = getServletContext().getRealPath("");
        String directoryPath = "img"; // The directory where the image will be saved
        File directory = new File(contextPath + directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        String filePath = directoryPath + File.separator + fileName;
        String absolutePath = contextPath + filePath;

        // Save the file to the server file system
        filePart.write(absolutePath);

        // Get the user from session and update the avatar URL
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            String avatarUrl = request.getContextPath() + "/" + filePath; // This is relative to the context root
            user.setAvatar(avatarUrl);

            // Save the new avatar URL in the database using your UserService
            userService.updateUserAvatar(user.getUserId(), avatarUrl);
        }

        // Redirect to the User Center page with updated avatar
        response.sendRedirect("userCenter.jsp");
    }

}
