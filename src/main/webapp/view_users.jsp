<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, in.sl.backend.DBConnection" %>
<html>
<head>
    <title>View Users</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Registered Users</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Payment Status</th>
            <th>Class</th>
        </tr>
        <%
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getInt("id") %></td>
            <td><%= rs.getString("username") %></td>
            <td><%= rs.getString("email") %></td>
            <td><%= rs.getBoolean("has_paid") ? "Paid" : "Unpaid" %></td>
            <td><%= rs.getString("paid_class") != null ? rs.getString("paid_class") : "N/A" %></td>
        </tr>
        <%
                }
                rs.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </table>
</body>
</html>

