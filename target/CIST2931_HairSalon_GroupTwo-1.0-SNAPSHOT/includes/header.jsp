<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header class="site-header">
  <div class="header-container">
    <img src="<%= request.getContextPath() %>/images/logo.png"
         alt="Salon Logo"
         class="header-logo">

    <div class="header-text">
      <h1>Salon Appointment System</h1>
      <p class="tagline">Book appointments, manage schedules, and connect with your stylist</p>
    </div>

    <nav class="main-nav">
      <a href="<%= request.getContextPath() %>/index.jsp">Home</a>
      <% if (session.getAttribute("userId") == null) { %>
      <a href="<%= request.getContextPath() %>/login.jsp">Login</a>
      <% } else { %>
      <a href="<%= request.getContextPath() %>/logout">Logout</a>
      <% } %>
    </nav>
  </div>
</header>