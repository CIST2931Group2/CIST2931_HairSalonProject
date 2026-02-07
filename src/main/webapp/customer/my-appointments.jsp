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
    <title>My Appointments</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/customer-nav.jsp" />

<h2>My Appointments</h2>

<h3>Upcoming Appointments</h3>
<table>
    <tr>
        <th>Date</th>
        <th>Time</th>
        <th>Hairdresser</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>2026-03-01</td>
        <td>10:00 - 11:00</td>
        <td>Stylist A</td>
        <td>SCHEDULED</td>
    </tr>
</table>

<h3>Past Appointments</h3>
<table>
    <tr>
        <th>Date</th>
        <th>Time</th>
        <th>Hairdresser</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>2026-02-10</td>
        <td>09:00 - 10:00</td>
        <td>Stylist B</td>
        <td>COMPLETED</td>
    </tr>
</table>

</body>
</html>

