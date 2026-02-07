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
    <title>My Schedule</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/hairdresser-nav.jsp" />

<h2>My Schedule</h2>

<table>
    <tr>
        <th>Date</th>
        <th>Time</th>
        <th>Customer</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>2026-03-01</td>
        <td>10:00 - 11:00</td>
        <td>Jane Doe</td>
        <td>SCHEDULED</td>
    </tr>
</table>

</body>
</html>
