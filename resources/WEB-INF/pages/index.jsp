<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
    <form action="/view" method="POST">
        Photo id: <input type="text" name="photo_id">
        <input type="submit"/>
    </form>
    <form action="/add_photo" enctype="multipart/form-data" method="POST">
        Photos: <input type="file" name="phots" multiple="true" />
        <input type="submit"/>
    </form>
    <form action="/view_list" >
        <input type="submit"  value="View All Photos"/>
    </form>
</div>
</body>
</html>
