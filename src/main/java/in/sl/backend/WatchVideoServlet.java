package in.sl.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WatchVideoServlet")
public class WatchVideoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Validate ID parameter
        String videoIdParam = request.getParameter("id");
        if (videoIdParam == null || !videoIdParam.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid video ID.");
            return;
        }

        int videoId = Integer.parseInt(videoIdParam);

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT video_data FROM videos WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, videoId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] videoBytes = rs.getBytes("video_data");
                response.setContentType("video/mp4");
                
                try (OutputStream out = response.getOutputStream()) {
                    out.write(videoBytes);
                    out.flush();
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Video not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching video.");
        }
    }
}


