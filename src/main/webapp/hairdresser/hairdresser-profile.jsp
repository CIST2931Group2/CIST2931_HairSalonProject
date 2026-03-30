<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/08/2026
  Time: 6:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Hairdresser" %>
<%
  Hairdresser hairdresser = (Hairdresser) request.getAttribute("hairdresser");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Hairdresser Profile</title>
  <link rel="stylesheet" href="css/styles.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <!-- Add icons for footer -->
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<header class="site-header">
  <%@ include file="/includes/logo.jsp" %>
  <span class="site-title">Salon Appointment System</span>
</header>

<!-- Navigation -->
<jsp:include page="/includes/hairdresser-nav.jsp" />

<main>
  <div class="dashboard-container" style="align-content: flex-start;">
  <h2 style="text-align: center;">My Profile</h2>

  <!-- Error message -->
  <%
    String error = (String) request.getAttribute("error");
    if (error != null) {
  %>
  <p style="text-align: center; color:red;background:#FFE6E6; padding:10px; border-radius:5px;"><%= error %></p>
  <%
    }
  %>

  <!-- Success message -->
  <%
    String success = (String) request.getAttribute("success");
    if (success != null) {
  %>
  <p style="text-align: center; color:green; background:#e6ffe6; padding:10px; border-radius:5px;"><%= success %></p>
  <%
    }
  %>

  <form class="form-container" method="post" action="<%= request.getContextPath() %>/hairdresserProfile">

    <label>Email:</label>
    <input type="email"
           value="<%= session.getAttribute("email") != null ? session.getAttribute("email") : "" %>"
           readonly>

    <label>First Name:</label>
    <input type="text" name="firstName"
           value="<%= hairdresser != null ? hairdresser.getFirstName() : "" %>"
           required>

    <label>Last Name:</label>
    <input type="text" name="lastName"
           value="<%= hairdresser != null ? hairdresser.getLastName() : "" %>"
           required>

    <label>Phone:</label>
    <input type="text" name="phone"
           value="<%= hairdresser != null ? hairdresser.getPhone() : "" %>">

    <label>Specialties:</label>
    <input type="text" name="specialties"
           value="<%= hairdresser != null ? hairdresser.getSpecialties() : "" %>">

    <label>Bio:</label>
    <textarea name="bio" rows="4" cols="40"><%= hairdresser != null ? hairdresser.getBio() : "" %></textarea>

    <button type="submit">Save Profile</button>

  </form>

  </div>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>
