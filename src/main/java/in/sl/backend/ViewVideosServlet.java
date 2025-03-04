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
import javax.servlet.http.HttpSession;

@WebServlet("/ViewVideosServlet")
public class ViewVideosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp?msg=Please login first");
            return;
        }

        String classLevel = request.getParameter("class");
        String videoType = request.getParameter("type");

        if (classLevel == null || videoType == null) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT title, video_data FROM videos WHERE class_level=? AND video_type=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, classLevel);
            ps.setString(2, videoType);
            ResultSet rs = ps.executeQuery();

            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h2>Videos for Class " + classLevel + " (" + videoType + ")</h2>");

            while (rs.next()) {
                String title = rs.getString("title");
                byte[] videoBytes = rs.getBytes("video_data");

                response.getWriter().println("<h3>" + title + "</h3>");
                response.getWriter().println("<video width='500' controls>");
                response.getWriter().println("<source src='data:video/mp4;base64," + java.util.Base64.getEncoder().encodeToString(videoBytes) + "' type='video/mp4'>");
                response.getWriter().println("Your browser does not support the video tag.");
                response.getWriter().println("</video><br>");
            }

            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




