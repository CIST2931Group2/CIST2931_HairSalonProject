<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 3/18/2026
  Time: 6:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
  <title>Customer Registration</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<header class="site-header">
  <%@ include file="/includes/logo.jsp" %>
  <span class="site-title">Salon Appointment System</span>
</header>

<!-- ✅ Add navigation like dashboard -->
<jsp:include page="/includes/customer-nav.jsp" />

<%
  String success = request.getParameter("success");
  if ("accountCreated".equals(success)) {
%>
<div id="successPopup" style="
    position: fixed;
    top: 20%;
    left: 50%;
    transform: translateX(-50%);
    background-color: #e6ffe6;
    border: 1px solid green;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    z-index: 9999;
    box-shadow: 0 0 10px rgba(0,0,0,0.3);
">
  <p style="color: green; font-weight: bold;">
    Account created successfully! 🎉
  </p>

  <!-- ✅ improved: give choice instead of forcing redirect -->
  <div style="margin-top: 10px;">
    <button onclick="document.getElementById('successPopup').style.display='none'"
            class="btn-dashboard">
      Stay Here
    </button>

    <button onclick="window.location.href='<%= request.getContextPath() %>/customerDashboard'"
            class="btn-dashboard">
      Go to Dashboard
    </button>
  </div>
</div>

<script>
  setTimeout(() => {
    const popup = document.getElementById("successPopup");
    if (popup) popup.style.display = "none";
  }, 5000);

  // ✅ clean URL after showing message
  if (window.location.search.includes("success=accountCreated")) {
    window.history.replaceState({}, document.title, window.location.pathname);
  }
</script>
<% } %>

<main>
  <div class="dashboard-container">
    <div class="dashboard-card">

      <h2>Register New Account</h2>

      <% String error = (String) request.getAttribute("error"); %>
      <% if (error != null) { %>
      <p style="color:red;"><%= error %></p>
      <% } %>

      <form action="<%= request.getContextPath() %>/registerCustomer" method="post">

        <div class="form-group">
          <label for="firstName">First Name:</label>
          <input type="text" name="firstName" id="firstName" required>
        </div>

        <div class="form-group">
          <label for="lastName">Last Name:</label>
          <input type="text" name="lastName" id="lastName" required>
        </div>

        <div class="form-group">
          <label for="phone">Phone Number:</label>
          <input type="text" name="phone" id="phone" required>
        </div>

        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" name="email" id="email" required>
        </div>

        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" name="password" id="password" required>
        </div>

        <!-- ✅ Buttons styled like dashboard -->
        <div style="margin-top: 15px;">
          <button type="submit" class="btn-dashboard">Register</button>

          <a href="<%= request.getContextPath()%>/customerDashboard"
             class="btn-dashboard"
             style="margin-left: 10px; text-decoration: none;">
            Return to Dashboard
          </a>
        </div>

      </form>
    </div>
  </div>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>