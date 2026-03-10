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
  <title>Hairdresser Profile</title>
  <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<header class="site-header">
  <%@ include file="/includes/logo.jsp" %>
  <span class="site-title">Salon Appointment System</span>
</header>

<jsp:include page="/includes/hairdresser-nav.jsp" />

<h2>Hairdresser Profile</h2>

<!-- Error message -->
<%
  String error = (String) request.getAttribute("error");
  if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
  }
%>

<!-- Success message -->
<%
  String success = (String) request.getAttribute("success");
  if (success != null) {
%>
<p style="color:green;"><%= success %></p>
<%
  }
%>

<form method="post" action="<%= request.getContextPath() %>/hairdresserProfile">

  <table>

    <tr>
      <td>Email:</td>
      <td>
        <input type="email"
               value="<%= session.getAttribute("email") != null ? session.getAttribute("email") : "" %>"
               readonly>
      </td>
    </tr>

    <tr>
      <td>First Name:</td>
      <td>
        <input type="text" name="firstName"
               value="<%= hairdresser != null ? hairdresser.getFirstName() : "" %>"
               required>
      </td>
    </tr>

    <tr>
      <td>Last Name:</td>
      <td>
        <input type="text" name="lastName"
               value="<%= hairdresser != null ? hairdresser.getLastName() : "" %>"
               required>
      </td>
    </tr>

    <tr>
      <td>Phone:</td>
      <td>
        <input type="text" name="phone"
               value="<%= hairdresser != null ? hairdresser.getPhone() : "" %>">
      </td>
    </tr>

    <tr>
      <td>Specialties:</td>
      <td>
        <input type="text" name="specialties"
               value="<%= hairdresser != null ? hairdresser.getSpecialties() : "" %>">
      </td>
    </tr>

    <tr>
      <td>Bio:</td>
      <td>
        <textarea name="bio" rows="4" cols="40"><%= hairdresser != null ? hairdresser.getBio() : "" %></textarea>
      </td>
    </tr>

    <tr>
      <td></td>
      <td>
        <button type="submit">Save Profile</button>
      </td>
    </tr>

  </table>

</form>

<br>

<a href="<%= request.getContextPath() %>/hairdresser/dashboard.jsp">
  Back to Dashboard
</a>

</body>
</html>
