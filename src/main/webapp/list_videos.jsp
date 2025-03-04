%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, in.sl.backend.DBConnection" %>
<html>
<head>
    <title>Manage Videos</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>Uploaded Videos</h2>
        <table>
            <tr>
                <th>Title</th>
                <th>Class</th>
                <th>Demo</th>
                <th>Action</th>
            </tr>
            <%
                try (Connection con = DBConnection.getConnection()) {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM videos");
                    while (rs.next()) {
            %>
            <tr>
                <td><%= rs.getString("title") %></td>
                <td><%= rs.getString("class_level") %></td>
                <td><%= rs.getBoolean("is_demo") ? "Yes" : "No" %></td>
                <td><a href="DeleteVideoServlet?id=<%= rs.getInt("id") %>">Delete</a></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</body>
</html>