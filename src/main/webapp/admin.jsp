<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Upload Video</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Admin Panel - Upload Video</h2>
    <form action="UploadVideoServlet" method="post">
        <label>Title:</label> <input type="text" name="title" required><br>
        <label>Video URL:</label> <input type="text" name="video_url" required><br>
        <label>Class Level:</label> 
        <select name="class_level">
            <option value="10th">10th</option>
            <option value="11th">11th</option>
            <option value="12th">12th</option>
        </select><br>
        <label>Is Demo Video?</label> <input type="checkbox" name="is_demo"><br>
        <input type="submit" value="Upload Video">
    </form>
</body>
</html>