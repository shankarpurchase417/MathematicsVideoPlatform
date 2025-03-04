<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, in.sl.backend.DBConnection" %>
<html>
<head>
    <title>Manage Videos</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Uploaded Videos</h2>
    <table border="1">
        <tr>
            <th>Video ID</th>
            <th>Title</th>
            <th>Class</th>
            <th>Type</th>
            <th>Action</th>
        </tr>
        <%
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM videos");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getInt("id") %></td>
            <td><%= rs.getString("title") %></td>
            <td><%= rs.getString("class_level") %></td>
            <td><%= rs.getString("video_type") %></td>
            <td>
                <a href="DeleteVideoServlet?id=<%= rs.getInt("id") %>">Delete</a>
            </td>
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
