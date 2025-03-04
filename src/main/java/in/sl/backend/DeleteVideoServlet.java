package in.sl.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteVideoServlet")
public class DeleteVideoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int videoId = Integer.parseInt(request.getParameter("id"));

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM videos WHERE id=?");
            ps.setInt(1, videoId);
            ps.executeUpdate();
            response.sendRedirect("manage_video.jsp?msg=Video Deleted");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manage_video.jsp?msg=Error Deleting Video");
        }
    }
}


