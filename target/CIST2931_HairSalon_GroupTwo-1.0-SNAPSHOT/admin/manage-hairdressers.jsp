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

<main class = "flex-container">

    <h1 style="text-align:center;">Admin - Manage Hairdressers</h1>

    <br>

    <% if (error != null) { %>
    <p style="color:red;"><%= error %></p>
    <% } %>

    <% if (success != null) { %>
    <p style="color:green;"><%= success %></p>
    <% } %>

    <hr>
    <br>

    <!-- Add new hairdresser form -->
    <h2 style="text-align:center;">Add New Hairdresser</h2>

    <form class="form-container" method="post" action="<%= request.getContextPath() %>/adminAddHairdresser">

        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" placeholder="Enter First Name" required>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" placeholder="Enter Last Name" required>

        <label for="email">Email:</label></td>
        <input type="email" id="email" name="email" placeholder="example@domain.com" required>

        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" placeholder="555-555-5555">

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" placeholder="Enter password" required>

        <label for="specialties">Specialties:</label>
        <input type="text" id="specialties" name="specialties" placeholder="Enter the List divided by comma">

        <label for="bio">Bio:</label>
        <textarea id="bio" name="bio" rows="4" cols="40"></textarea>

        <button type="submit">Add Hairdresser</button>

    </form>

    <hr>
    <br>

    <!-- List of all current hairdressers with deactivate button -->
    <h2 style="text-align:center;">Current Hairdressers</h2>

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

</main>
</body>
</html>
