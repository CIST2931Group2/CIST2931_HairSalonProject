<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/09/2026
  Time: 7:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Hairdresser" %>

<%
    List<Hairdresser> hairdressers =
            (List<Hairdresser>) request.getAttribute("hairdressers");

    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");

// optional support if servlet redirects with ?error=...
    if (error == null) {
        error = request.getParameter("error");
    }
    if (success == null) {
        success = request.getParameter("success");
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Hairdressers</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>

<nav class="nav">
    <a href="<%= request.getContextPath() %>/adminManageHairdressers">My Dashboard</a>
    |
    <a href="<%= request.getContextPath() %>/adminSchedule">Manage Weekly Schedules</a>
    |
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
</nav>


<h1>Admin - Manage Hairdressers</h1>

<% if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

<% if (success != null) { %>
<p style="color:green;"><%= success %></p>
<% } %>

<hr>

<h2>Add New Hairdresser</h2>

<form method="post" action="<%= request.getContextPath() %>/adminAddHairdresser">

    <table>
        <tr>
            <td><label for="firstName">First Name:</label></td>
            <td><input type="text" id="firstName" name="firstName" required></td>
        </tr>

        <tr>
            <td><label for="lastName">Last Name:</label></td>
            <td><input type="text" id="lastName" name="lastName" required></td>
        </tr>

        <tr>
            <td><label for="email">Email:</label></td>
            <td><input type="email" id="email" name="email" required></td>
        </tr>

        <tr>
            <td><label for="phone">Phone:</label></td>
            <td><input type="text" id="phone" name="phone"></td>
        </tr>

        <tr>
            <td><label for="password">Password:</label></td>
            <td><input type="password" id="password" name="password" required></td>
        </tr>

        <tr>
            <td><label for="specialties">Specialties:</label></td>
            <td><input type="text" id="specialties" name="specialties"></td>
        </tr>

        <tr>
            <td><label for="bio">Bio:</label></td>
            <td>
                <textarea id="bio" name="bio" rows="4" cols="40"></textarea>
            </td>
        </tr>

        <tr>
            <td></td>
            <td><button type="submit">Add Hairdresser</button></td>
        </tr>
    </table>

</form>

<hr>

<h2>Current Hairdressers</h2>

<% if (hairdressers == null || hairdressers.isEmpty()) { %>

<p>No hairdressers found.</p>

<% } else { %>

<table class="hairdressers-table">
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Phone</th>
        <th>Specialties</th>
        <th>Action</th>
    </tr>

    <% for (Hairdresser h : hairdressers) { %>
    <tr>
        <td><%= h.getHairdresserId() %></td>
        <td><%= h.getFirstName() %></td>
        <td><%= h.getLastName() %></td>
        <td><%= h.getPhone() != null ? h.getPhone() : "" %></td>
        <td><%= h.getSpecialties() != null ? h.getSpecialties() : "" %></td>
        <td>
            <form method="post" action="<%= request.getContextPath() %>/adminDeactivateHairdresser" style="margin:0;">
                <input type="hidden" name="hairdresserId" value="<%= h.getHairdresserId() %>">
                <button type="submit">Deactivate</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>

<% } %>


</body>
</html>
