package com.pc3ryangzhang.fixnow.controller;

import com.pc3ryangzhang.fixnow.entity.Profile;
import com.pc3ryangzhang.fixnow.mapper.ProfileMapper;
import com.pc3ryangzhang.fixnow.util.GetSqlSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();
        String userIdParam = request.getParameter("userId");

        try (SqlSession session = GetSqlSession.createSqlSession()) {
            ProfileMapper mapper = session.getMapper(ProfileMapper.class);

            if (userIdParam != null) {
                try {
                    Integer userId = Integer.parseInt(userIdParam);
                    Profile profile = mapper.getProfileByUserId(userId);
                    if (profile != null) {
                        result.put("success", true);
                        result.put("profile", profile);
                    } else {
                        result.put("success", false);
                        result.put("message", "Profile not found for user ID: " + userId);
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    result.put("success", false);
                    result.put("message", "Invalid user ID");
                }
            } else {
                // 如果没有userId参数，获取所有修理师
                List<Profile> profiles = mapper.getAllProfiles();
                result.put("success", true);
                result.put("profiles", profiles);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("success", false);
            result.put("message", "Error retrieving profiles: " + e.getMessage());
        }

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(result));
        out.flush();
        out.close();
    }





    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        BufferedReader reader = request.getReader();
        Profile profile = gson.fromJson(reader, Profile.class);

        Map<String, Object> result = new HashMap<>();
        try (SqlSession session = GetSqlSession.createSqlSession()) {
            ProfileMapper mapper = session.getMapper(ProfileMapper.class);
            mapper.insertProfile(profile);
            session.commit();
            result.put("success", true);
            result.put("message", "Profile created successfully");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("success", false);
            result.put("message", "Error creating profile: " + e.getMessage());
        }

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(result));
        out.flush();
        out.close();
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = request.getReader();
        Profile updatedProfile = gson.fromJson(reader, Profile.class);
        Map<String, Object> result = new HashMap<>();

        try (SqlSession session = GetSqlSession.createSqlSession()) {
            ProfileMapper mapper = session.getMapper(ProfileMapper.class);
            Profile existingProfile = mapper.getProfileByUserId(updatedProfile.getUserId());
            if (existingProfile == null) {
                result.put("success", false);
                result.put("message", "Profile not found.");
            } else {
                // Update the existing profile with the new details
                existingProfile.updateFrom(updatedProfile);
                mapper.updateProfile(existingProfile);
                session.commit();
                result.put("success", true);
                result.put("message", "Profile updated successfully");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("success", false);
            result.put("message", "Error updating profile: " + e.getMessage());
        }

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(result));
        out.flush();
        out.close();
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Integer profileId = Integer.parseInt(request.getParameter("profileId"));
        Map<String, Object> result = new HashMap<>();

        try (SqlSession session = GetSqlSession.createSqlSession()) {
            ProfileMapper mapper = session.getMapper(ProfileMapper.class);
            if (mapper.deleteProfile(profileId) > 0) {
                session.commit();
                result.put("success", true);
                result.put("message", "Profile deleted successfully");
            } else {
                result.put("success", false);
                result.put("message", "No profile found to delete");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("success", false);
            result.put("message", "Error deleting profile: " + e.getMessage());
        }

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(result));
        out.flush();
        out.close();
    }


    private void sendJsonResponse(HttpServletResponse response, Map<String, Object> result) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(result));
        out.flush();
        out.close();
    }

}

