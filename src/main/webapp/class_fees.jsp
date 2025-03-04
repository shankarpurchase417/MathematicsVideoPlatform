<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, in.sl.backend.DBConnection" %>

<html>
<head>
    <title>Manage Class Fees</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Class Fees Management</h2>
    <table border="1">
        <tr>
            <th>Class Name</th>
            <th>Current Fee</th>
            <th>Update Fee</th>
        </tr>
        <%
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT class_name, amount FROM class_fees");
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    String className = rs.getString("class_name");
                    double feeAmount = rs.getDouble("amount");
        %>
        <tr>
            <td><%= className %></td>
            <td>₹<%= feeAmount %></td>
            <td>
                <form action="UpdateFeesServlet" method="post">
                    <input type="hidden" name="class_name" value="<%= className %>">
                    <input type="number" name="new_fee" required>
                    <button type="submit">Update</button>
                </form>
            </td>
        </tr>
        <%
                }
                rs.close();
                ps.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
        %>
            <tr><td colspan="3">Error fetching data</td></tr>
        <% } %>
    </table>
</body>
</html>

