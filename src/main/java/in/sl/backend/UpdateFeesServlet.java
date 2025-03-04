package in.sl.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateFeesServlet")
public class UpdateFeesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String className = request.getParameter("class_name");
        String newFeeStr = request.getParameter("new_fee");

        if (className == null || newFeeStr == null || newFeeStr.isEmpty()) {
            response.sendRedirect("class_fees.jsp?msg=Invalid Input");
            return;
        }

        try {
            double newFee = Double.parseDouble(newFeeStr);
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE class_fees SET amount=? WHERE class_name=?");
            ps.setDouble(1, newFee);
            ps.setString(2, className);
            int updated = ps.executeUpdate();
            ps.close();
            conn.close();

            if (updated > 0) {
                response.sendRedirect("class_fees.jsp?msg=Fee updated successfully");
            } else {
                response.sendRedirect("class_fees.jsp?msg=Update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("class_fees.jsp?msg=Error occurred");
        }
    }
}

