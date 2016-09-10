<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
    <h1>Number of photos: ${photo_numb}</h1>
    <input type="submit" value="Upload New" onclick="window.location='/';"/>

    <form action="/deleteChecked" method="POST">
        <input type="submit" value="Delete checked photos"/>
        <br/><br/>
        <table>
            <tr>
                <td><h1>Delete?</h1></td>
                <td><h1>Photo ID</h1></td>
                <td><h1>Photo</h1></td>
            </tr>
            <c:forEach items="${map}" var="map">
                <tr>
                    <td><input type="checkbox" name="${map.key}"></td>
                    <td><c:out value="${map.key}"/></td>
                    <td><img src="/photo/${map.key}" width="200" height="200"/></td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>
</body>
</html>