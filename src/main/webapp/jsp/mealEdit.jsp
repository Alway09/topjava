<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>${meal.id == null ? "Add" : "Edit"} meal</h2>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <div>
        <h3>Date and time</h3>
        <fmt:parseDate type="both" value="${meal.dateTime}" var="parsedDateTime" pattern="yyyy-MM-dd'T'HH:mm"/>
        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" var="date"/>
        <input type="datetime-local" name="dateTime" value="${date}">
    </div>
    <div>
        <h3>Description</h3>
        <input type="text" name="description" value="${meal.description}" size=55">
    </div>
    <div>
        <h3>Calories</h3>
        <input type="text" name="calories" value="${meal.calories}" size=55">
    </div>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
