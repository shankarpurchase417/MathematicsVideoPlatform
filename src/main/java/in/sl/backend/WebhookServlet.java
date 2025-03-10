package in.sl.backend;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/razorpay-webhook")
public class WebhookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read JSON data from Razorpay webhook
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());

        // Extract payment details
        String paymentStatus = json.getJSONObject("payload").getJSONObject("payment").getJSONObject("entity").getString("status");
        String email = json.getJSONObject("payload").getJSONObject("payment").getJSONObject("entity").getString("email");

        if (paymentStatus.equals("captured")) {
            // Database update karna
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/math_videos", "root", "Shankar@1992");
                String query = "UPDATE users SET has_paid = TRUE WHERE email = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.executeUpdate();
                con.close();
                response.sendRedirect("dashboard.jsp?msg=Payment Successful");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.setStatus(200);
    }
}

