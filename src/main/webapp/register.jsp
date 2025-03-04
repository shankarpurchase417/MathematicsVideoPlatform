<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Register</h2>
    <form action="RegisterServlet" method="post">
        <label>Username:</label> <input type="text" name="username" required><br>
        <label>Email:</label> <input type="email" name="email" required><br>
        <label>Password:</label> <input type="password" name="password" required><br>
        <input type="submit" value="Register">
    </form>
    <a href="login.jsp">Already have an account? Login</a>
</body>
</