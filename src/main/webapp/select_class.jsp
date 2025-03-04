<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.* ,in.sl.backend.DBConnection" %>
<html>
<head>
    <title>Select Class & Pay</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Select Your Class to Pay & Get Full Access</h2>
    
    <form action="PaymentServlet" method="post">
        <label>Select Class:</label>
        <select name="class_name">
            <%
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM class_fees");
                while (rs.next()) {
            %>
                <option value="<%= rs.getString("class_name") %>">
                    <%= rs.getString("class_name") %> - ₹<%= rs.getDouble("amount") %>
                </option>
            <% } %>
        </select>
        <br><br>
        <input type="submit" value="Proceed to Pay">
    </form>
</body>
</html>
