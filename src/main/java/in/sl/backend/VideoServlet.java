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

@WebServlet("/VideoServlet")
public class VideoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int videoId = Integer.parseInt(request.getParameter("id"));

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT video_data FROM videos WHERE id = ?");
            ps.setInt(1, videoId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] videoData = rs.getBytes("video_data");
                response.setContentType("video/mp4");
                OutputStream os = response.getOutputStream();
                os.write(videoData);
                os.flush();
                os.close();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

