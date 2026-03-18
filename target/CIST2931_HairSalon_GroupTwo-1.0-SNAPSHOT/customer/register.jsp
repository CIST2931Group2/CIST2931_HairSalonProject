<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 3/11/2026
  Time: 7:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Customer Registration</title>
</head>
<body>

<h1>Register as a Customer</h1>

<%
  String error = (String) request.getAttribute("error");
  if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<% } %>

<form action="<%= request.getContextPath() %>/registerCustomer" method="post">
  <label for="firstName">First Name:</label>
  <input type="text" id="firstName" name="firstName" required /><br/><br/>

  <label for="lastName">Last Name:</label>
  <input type="text" id="lastName" name="lastName" required /><br/><br/>

  <label for="phone">Phone:</label>
  <input type="text" id="phone" name="phone" required /><br/><br/>

  <label for="email">Email:</label>
  <input type="email" id="email" name="email" required /><br/><br/>

  <label for="password">Password:</label>
  <input type="password" id="password" name="password" required /><br/><br/>

  <button type="submit">Register</button>
</form>

<p>Already have an account? <a href="<%= request.getContextPath() %>/login.jsp">Login here</a></p>

</body>
</html>