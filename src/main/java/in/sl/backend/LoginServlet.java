package in.sl.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String hashedPassword = DigestUtils.sha256Hex(request.getParameter("password")); // Hash password
        
        System.out.println("DEBUG: Email = " + email);
        System.out.println("DEBUG: Hashed Password = " + hashedPassword);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement("SELECT id, username, has_paid, paid_class FROM users WHERE email=? AND password=?");
            ps.setString(1, email);
            ps.setString(2, hashedPassword);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Store session attributes for user
                HttpSession session = request.getSession();
                session.setAttribute("user_id", rs.getInt("id"));
                session.setAttribute("username", rs.getString("username"));
                session.setAttribute("has_paid", rs.getBoolean("has_paid"));
                session.setAttribute("class_name", rs.getString("paid_class")); // Store paid class name
                session.setAttribute("userEmail", email);
                System.out.println("DEBUG: Login successful for user " + rs.getString("username"));
                response.sendRedirect("dashboard.jsp");
            } else {
                System.out.println("DEBUG: Login failed - Invalid credentials");
                response.sendRedirect("login.jsp?msg=Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?msg=Login failed due to an error");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}


