<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Video</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Upload Video</h2>
    <form action="UploadVideoServlet" method="post" enctype="multipart/form-data">
        <label>Video Title:</label>
        <input type="text" name="title" required>
        
        <label>Class Level:</label>
        <select name="class_level">
            <option value="10">Class 10</option>
            <option value="11">Class 11</option>
            <option value="12">Class 12</option>
        </select>

        <label>Video Type:</label>
        <select name="video_type">
            <option value="demo">Demo</option>
            <option value="full">Full</option>
        </select>

        <label>Upload Video:</label>
        <input type="file" name="videoFile" required>
        <button type="submit">Upload</button>
    </form>
</body>
</html>


