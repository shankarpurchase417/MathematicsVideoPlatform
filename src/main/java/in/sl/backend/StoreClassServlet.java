package in.sl.backend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StoreClassServlet")
public class StoreClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // ✅ Get selected class & amount
        String selectedClass = request.getParameter("selectedClass");
        String amount = request.getParameter("amount");

        // ✅ Get session and store values
        HttpSession session = request.getSession();
        session.setAttribute("selectedClass", selectedClass);
        session.setAttribute("amount", amount);

        System.out.println("Stored in Session: " + selectedClass + " - " + amount);

        // ✅ No Redirect (Fetch API से Call हो रहा है)
        response.getWriter().write("Session Updated");
    }
}


