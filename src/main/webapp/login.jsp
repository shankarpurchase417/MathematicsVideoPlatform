<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
        <label>Email:</label> <input type="email" name="email" required><br>
        <label>Password:</label> <input type="password" name="password" required><br>
        <input type="submit" value="Login">
    </form>
    <a href="register.jsp">Don't have an account? Register</a>
</body>
</html>