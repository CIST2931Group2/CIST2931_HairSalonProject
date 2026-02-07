<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Weekly Schedule</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/admin-nav.jsp" />

<h2>Create Weekly Schedule</h2>

<form>
    <label>Week Start Date</label>
    <input type="date" name="weekStartDate">

    <label>Day of Week</label>
    <select name="dayOfWeek">
        <option>MON</option>
        <option>TUE</option>
        <option>WED</option>
        <option>THU</option>
        <option>FRI</option>
    </select>

    <label>Start Time</label>
    <input type="time">

    <label>End Time</label>
    <input type="time">

    <button>Add Schedule Block</button>
</form>

</body>
</html>

