<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Uploading Files Example with Spring Boot, Freemarker</title>
</head>

<body>
<form name="uploadingForm" enctype="multipart/form-data" action="/upload" method="POST">
    <p>
        <label>Class Name</label><br/>
        <input type="text" name="className" value="class" /><br/><br/>

        <label>File</label><br/>
        <input id="fileInput" type="file" name="uploadingFile" />
    </p>
    <p>
        <input type="submit" value="Kuy">
    </p>
</form>
<textarea rows="20" cols="150" readonly>${message!}</textarea>
</body>
</html>