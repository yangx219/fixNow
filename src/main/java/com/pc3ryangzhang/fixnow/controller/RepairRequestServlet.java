package com.pc3ryangzhang.fixnow.controller;

import com.google.gson.Gson;
import com.pc3ryangzhang.fixnow.entity.RepairRequest;
import com.pc3ryangzhang.fixnow.mapper.RepairRequestMapper;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/repairRequest")
public class RepairRequestServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet called");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();

        String userIdParam = request.getParameter("userId");

        try (SqlSession session = GetSqlSession.createSqlSession()) {
            RepairRequestMapper mapper = session.getMapper(RepairRequestMapper.class);

            if (userIdParam != null) {
                // Fetch repair requests by userId if provided
                int userId = Integer.parseInt(userIdParam);
                System.out.println("Received userId: " + userId);
                List<RepairRequest> repairRequests = mapper.getRepairRequestsByUserId(userId);
                System.out.println("Fetched repairRequests: " + repairRequests);
                result.put("repairRequests", repairRequests);
            } else {
                // Fetch all repair requests if no userId is provided
                List<RepairRequest> repairRequests = mapper.getAllRepairRequests();
                System.out.println("Fetched all repairRequests: " + repairRequests);
                result.put("repairRequests", repairRequests);
            }

            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("success", false);
            result.put("message", "Error fetching repair requests: " + e.getMessage());
        }

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(result));
        out.flush();
        out.close();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost called");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String requestBody = sb.toString();
            System.out.println("Received request body: " + requestBody);

            RepairRequest repairRequest = gson.fromJson(requestBody, RepairRequest.class);
            System.out.println("Received RepairRequest: " + repairRequest);

            // 手动解析日期
            Date preferredTime = null;
            try {
                preferredTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(repairRequest.getPreferredTime());
            } catch (ParseException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("success", false);
                errorResult.put("message", "Invalid date format for preferredTime.");
                sendJsonResponse(response, errorResult);
                return;
            }

            Map<String, Object> result = new HashMap<>();
            try (SqlSession session = GetSqlSession.createSqlSession()) {
                RepairRequestMapper mapper = session.getMapper(RepairRequestMapper.class);
                mapper.insertRepairRequest(repairRequest);  // 假设数据库中日期字段也是字符串
                session.commit();
                result.put("success", true);
                result.put("message", "Repair request created successfully");
                System.out.println("Repair request created successfully");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                result.put("success", false);
                result.put("message", "Error creating repair request: " + e.getMessage());
            }

            sendJsonResponse(response, result);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", "Error reading request: " + e.getMessage());
            sendJsonResponse(response, errorResult);
        }
    }



    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doDelete called");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        String requestBody = sb.toString();
        System.out.println("Received request body: " + requestBody);

        int requestId;
        try {
            Map<String, Object> requestMap = gson.fromJson(requestBody, Map.class);
            requestId = ((Double) requestMap.get("requestId")).intValue();  // JSON 序列化时数字默认为 Double
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "Invalid request body");
            sendJsonResponse(response, result);
            return;
        }

        System.out.println("Received requestId: " + requestId);

        Map<String, Object> result = new HashMap<>();
        try (SqlSession session = GetSqlSession.createSqlSession()) {
            RepairRequestMapper mapper = session.getMapper(RepairRequestMapper.class);

            if (mapper.getRepairRequestById(requestId) == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                result.put("success", false);
                result.put("message", "Repair request not found for ID: " + requestId);
                System.out.println("Repair request not found for ID: " + requestId);
            } else {
                mapper.deleteRepairRequest(requestId);
                session.commit();
                result.put("success", true);
                result.put("message", "Repair request deleted successfully");
                System.out.println("Repair request deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("success", false);
            result.put("message", "Error deleting repair request: " + e.getMessage());
        }

        sendJsonResponse(response, result);
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
