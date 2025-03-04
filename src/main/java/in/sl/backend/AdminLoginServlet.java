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
import org.apache.commons.codec.digest.DigestUtils; // Import for password hashing

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");
	    String password = DigestUtils.sha256Hex(request.getParameter("password")); // Hash input password

	    System.out.println("DEBUG: Admin Username = " + username);
	    System.out.println("DEBUG: Hashed Password = " + password);

	    try {
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?");
	        ps.setString(1, username);
	        ps.setString(2, password);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            HttpSession session = request.getSession();
	            session.setAttribute("admin_id", rs.getInt("id"));
	            session.setAttribute("admin_username", username);

	            System.out.println("DEBUG: Admin login successful!");
	            response.sendRedirect("admin_dashboard.jsp");
	        } else {
	            System.out.println("DEBUG: Admin login failed - Invalid credentials");
	            response.sendRedirect("admin_login.jsp?msg=Invalid credentials");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("admin_login.jsp?msg=Login failed");
	    }
	}
}



