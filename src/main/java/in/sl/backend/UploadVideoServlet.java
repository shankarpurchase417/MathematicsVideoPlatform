package in.sl.backend;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadVideoServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 100) // 100MB limit
public class UploadVideoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String classLevel = request.getParameter("class_level"); // Fixing class level values
        String videoType = request.getParameter("video_type");
        Part videoPart = request.getPart("videoFile");
        InputStream videoStream = videoPart.getInputStream();

        // Fix: Convert "Class 10" → "10"
        if (classLevel.equals("Class 10")) classLevel = "10";
        if (classLevel.equals("Class 11")) classLevel = "11";
        if (classLevel.equals("Class 12")) classLevel = "12";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO videos (title, class_level, video_type, video_data) VALUES (?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, classLevel);
            ps.setString(3, videoType);
            ps.setBlob(4, videoStream);
            ps.executeUpdate();
            response.sendRedirect("admin_dashboard.jsp?msg=Video uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("upload_video.jsp?msg=Upload failed");
        }
    }
}



