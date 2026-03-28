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

    // added for deactivation
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Hairdressers</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
    <!-- Add icons for footer -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>

<jsp:include page="/includes/admin-nav.jsp" />

<main>
    <div class="dashboard-container" style="align-content: flex-start;">
    <h2 style="text-align:center;">Admin - Manage Hairdressers</h2>

    <% if (error != null) { %>
    <p style="color:red;"><%= error %></p>
    <% } %>

    <% if ("hairdresserAdded".equals(success)) { %>
    <p style="text-align: center; color:green; background:#e6ffe6; padding:10px; border-radius:5px;">
        Hairdresser added successfully.
    </p>
    <% } else if ("deactivated".equals(success)) { %>
    <p style="text-align: center; color:green; background:#e6ffe6; padding:10px; border-radius:5px;">
        Hairdresser <%= firstName %> <%= lastName %> was deactivated.

    <% } %>

    <div class="dashboard-card appointments-card">
    <!-- Add new hairdresser form -->
    <h3 style="text-align:center;">Add New Hairdresser</h3>
        <!-- removed class="form-container" -->
    <form method="post" action="<%= request.getContextPath() %>/adminAddHairdresser">

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

    </div>

    <div class="dashboard-card appointments-card">
    <!-- List of all current hairdressers with deactivate button -->
    <h3 style="text-align:center;">Current Hairdressers</h3>

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
                    <input type="hidden" name="firstName" value="<%= h.getFirstName() %>">
                    <input type="hidden" name="lastName" value="<%= h.getLastName() %>">
                    <button type="submit">Deactivate</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>

    <% } %>
    </div>
</div>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>
