<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Book Appointment</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/customer-nav.jsp" />

<h2>Book an Appointment</h2>

<form>
    <label>Hairdresser</label>
    <select name="hairDresserID">
        <option>Stylist A</option>
        <option>Stylist B</option>
    </select>

    <label>Date</label>
    <input type="date" name="date">

    <label>Start Time</label>
    <input type="time" name="startTime">

    <label>End Time</label>
    <input type="time" name="endTime">

    <button type="submit">Schedule Appointment</button>
</form>

</body>
</html>

